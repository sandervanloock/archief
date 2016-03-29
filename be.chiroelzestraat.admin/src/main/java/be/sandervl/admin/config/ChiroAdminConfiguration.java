package be.sandervl.admin.config;

import be.sandervl.admin.ChiroAdminModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.core.context.support.AcrossModuleMessageSource;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.entity.controllers.ViewRequestValidator;
import com.foreach.across.modules.hibernate.jpa.config.HibernateJpaConfiguration;
import com.foreach.across.modules.spring.security.SpringSecurityModule;
import com.foreach.across.modules.user.UserModule;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@AcrossDepends(required = {EntityModule.NAME, SpringSecurityModule.NAME, UserModule.NAME})
@EnableJpaRepositories(transactionManagerRef = HibernateJpaConfiguration.TRANSACTION_MANAGER, basePackageClasses = ChiroAdminModule.class)
@ComponentScan({"be.sandervl.admin.services,be.sandervl.admin.controllers"})
public class ChiroAdminConfiguration {
    @Bean
    protected ViewRequestValidator viewRequestValidator() {
        return new ViewRequestValidator();
    }

    @Bean
    public MessageSource messageSource() {
        return new AcrossModuleMessageSource();
    }
}
