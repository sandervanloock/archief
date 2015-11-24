package be.sandervl.test.admin.config;

import be.sandervl.admin.ChiroAdminModule;
import com.foreach.across.config.AcrossContextConfigurer;
import com.foreach.across.core.AcrossContext;
import com.foreach.across.modules.bootstrapui.BootstrapUiModule;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.AbstractHibernatePackageModule;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.hibernate.jpa.config.HibernateJpaConfiguration;
import com.foreach.across.test.AcrossTestWebConfiguration;
import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
//@EnableJpaRepositories(transactionManagerRef = HibernateJpaConfiguration.TRANSACTION_MANAGER, basePackageClasses = ChiroAdminModule.class)
@AcrossTestWebConfiguration
public class PersistenceContext implements AcrossContextConfigurer {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(environment.getRequiredProperty("db.driver"));
        dataSource.setJdbcUrl(environment.getRequiredProperty("db.url"));
        dataSource.setUsername(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));

        return dataSource;
    }

    public AcrossHibernateJpaModule acrossHibernateModule() {
        AcrossHibernateJpaModule module = new AcrossHibernateJpaModule();
        module.setHibernateProperty("hibernate.hbm2ddl.auto", "create-drop");

        return module;
    }

    @Override
    public void configure(AcrossContext context) {
        context.addModule(acrossHibernateModule());
        context.addModule(new ChiroAdminModule());
        context.addModule(new EntityModule());
        context.addModule(new BootstrapUiModule());
    }
}
