package be.sandervl.admin.config;

import be.sandervl.admin.ChiroAdminModule;
import com.foreach.across.modules.hibernate.jpa.config.HibernateJpaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by sander on 07/06/2015.
 */
@Configuration
@EnableJpaRepositories(transactionManagerRef = HibernateJpaConfiguration.TRANSACTION_MANAGER, basePackageClasses = ChiroAdminModule.class)
public class ChiroAdminConfiguration {
}
