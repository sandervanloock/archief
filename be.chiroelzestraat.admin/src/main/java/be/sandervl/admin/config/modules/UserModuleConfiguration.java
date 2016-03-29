package be.sandervl.admin.config.modules;

import com.foreach.across.modules.user.UserModule;
import com.foreach.across.modules.user.UserModuleSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Created by sander on 29/03/2016.
 */
@Configuration
public class UserModuleConfiguration {

    @Bean
    @Profile("dev")
    public UserModule userModule() {
        UserModule userModule = new UserModule();
        userModule.setProperty( UserModuleSettings.PASSWORD_ENCODER, NoOpPasswordEncoder.getInstance() );
        return userModule;
    }
}
