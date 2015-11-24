package be.sandervl.admin.config;

import be.sandervl.admin.ChiroAdminModule;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class FactoryBeanConfiration {

    @Autowired
    private Environment environment;

    private static final Logger LOG = LoggerFactory.getLogger(FactoryBeanConfiration.class);

    @Bean
    public Twitter twitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(environment.getProperty("twitter.consumer.key"))
                .setOAuthConsumerSecret(environment.getProperty("twitter.consumer.secret"))
                .setOAuthAccessToken(environment.getProperty("twitter.oauth.access.token"))
                .setOAuthAccessTokenSecret(environment.getProperty("twitter.oauth.access.token.secret"));
        return new TwitterFactory(cb.build()).getInstance();
    }

    @Bean
    public Calendar googleCalendar() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("config/google-access-key.p12").getFile());
            JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
            NetHttpTransport transport = new NetHttpTransport();
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(transport)
                    .setJsonFactory(jacksonFactory)
                    .setServiceAccountId(environment.getProperty("google.calendar.email"))
                    .setServiceAccountPrivateKeyFromP12File(file)
                    .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
                    .build();
            return new Calendar.Builder(transport, jacksonFactory, credential).setApplicationName(ChiroAdminModule.NAME).build();
        } catch (IOException e) {
            LOG.error("failed to read google-acces-key.p12", e);
        } catch (GeneralSecurityException e) {
            LOG.error("An error occured creating a Google Credential", e);
        } catch (Exception e) {
            LOG.error("Something went wrong creating the Google Calendar bean",e);
        }
        return null;
    }

}
