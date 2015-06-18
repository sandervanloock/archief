package be.sandervl.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class FactoryBeanConfiration {

    @Autowired
    private Environment environment;

    @Bean
    public Twitter twitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(environment.getProperty("twitter.consumer.key"))
                .setOAuthConsumerSecret(environment.getProperty("twitter.consumer.secret"))
                .setOAuthAccessToken(environment.getProperty("twitter.oauth.access.token"))
                .setOAuthAccessTokenSecret(environment.getProperty("twitter.oauth.access.token.secret"));
        return new TwitterFactory(cb.build()).getInstance();
    }
}
