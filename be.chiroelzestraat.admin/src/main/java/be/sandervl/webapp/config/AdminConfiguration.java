package be.sandervl.webapp.config;

import be.sandervl.admin.ChiroAdminModule;
import com.foreach.across.config.AcrossContextConfigurer;
import com.foreach.across.config.EnableAcrossContext;
import com.foreach.across.core.AcrossContext;
import com.foreach.across.modules.adminweb.AdminWebModule;
import com.foreach.across.modules.adminweb.AdminWebModuleSettings;
import com.foreach.across.modules.applicationinfo.ApplicationInfoModule;
import com.foreach.across.modules.applicationinfo.ApplicationInfoModuleSettings;
import com.foreach.across.modules.debugweb.DebugWebModule;
import com.foreach.across.modules.ehcache.EhcacheModule;
import com.foreach.across.modules.ehcache.EhcacheModuleSettings;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.config.PersistenceContextInView;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModuleSettings;
import com.foreach.across.modules.properties.PropertiesModule;
import com.foreach.across.modules.spring.security.SpringSecurityModule;
import com.foreach.across.modules.user.UserModule;
import com.foreach.across.modules.web.AcrossWebModule;
import com.foreach.across.modules.web.AcrossWebViewSupport;
import com.foreach.common.spring.logging.LogbackConfigurer;
import com.foreach.imageserver.admin.ImageServerAdminWebModule;
import com.foreach.imageserver.admin.ImageServerAdminWebModuleSettings;
import com.foreach.imageserver.core.ImageServerCoreModule;
import com.foreach.imageserver.core.ImageServerCoreModuleSettings;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@EnableAcrossContext
@PropertySource({"classpath:config/${environment.type}/common.properties",
        "classpath:config/general.properties"})
@ComponentScan("be.sandervl.webapp.config")
public class AdminConfiguration implements AcrossContextConfigurer {

    @Autowired
    private Environment environment;

    @Bean
    public LogbackConfigurer logbackConfigurer(
            @Value("classpath:logback.xml") Resource defaultConfig, @Value("classpath:logback.xml") Resource environmentConfig) {
        return new LogbackConfigurer(environment.getRequiredProperty("log.dir"), defaultConfig, environmentConfig);
    }

    @Bean
    public DataSource acrossDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("database.driver"));
        dataSource.setUrl(environment.getProperty("database.url"));
        dataSource.setUsername(environment.getProperty("database.username"));
        dataSource.setPassword(environment.getProperty("database.password"));

        return dataSource;
    }

    @Override
    public void configure(AcrossContext context) {
        context.addModule(acrossWebModule());
        context.addModule(new ChiroAdminModule());
        context.addModule(debugWebModule());
        context.addModule(ehcacheModule());
        context.addModule(imageServerCoreModule());
        context.addModule(imageServerAdminModule());
        context.addModule(applicationInfoModule());
        context.addModule(userModule());
        context.addModule(adminWebModule());
        context.addModule(acrossHibernateJpaModule());
        context.addModule(propertiesModule());
        context.addModule(springSecurityModule());
        context.addModule(entityModule());
        context.setDevelopmentMode(true);
    }

    private EntityModule entityModule() {
        EntityModule entityModule = new EntityModule();
        entityModule.addApplicationContextConfigurer(EntityTypeConfiguration.class);
        return entityModule;
    }

    private PropertiesModule propertiesModule() {
        return new PropertiesModule();
    }

    private SpringSecurityModule springSecurityModule() {
        return new SpringSecurityModule();
    }

    private AcrossHibernateJpaModule acrossHibernateJpaModule() {
        AcrossHibernateJpaModule jpaModule = new AcrossHibernateJpaModule();
        jpaModule.setProperty(AcrossHibernateJpaModuleSettings.PERSISTENCE_CONTEXT_VIEW_HANDLER,
                PersistenceContextInView.FILTER);
        jpaModule.setHibernateProperty("hibernate.hbm2ddl.auto", "update");
        return jpaModule;
    }

    private AdminWebModule adminWebModule() {
        AdminWebModule adminWebModule = new AdminWebModule();
        adminWebModule.setRootPath("/secure");
//        adminWebModule.setProperty(AdminWebModuleSettings.REMEMBER_ME_KEY, "subscription-manager");
        adminWebModule.setProperty(AdminWebModuleSettings.DASHBOARD_PATH, "/dashboard");
        adminWebModule.setProperty(AdminWebModuleSettings.LOCALE_DEFAULT, LocaleUtils.toLocale("nl"));
        return adminWebModule;
    }

    private UserModule userModule() {
        return new UserModule();
    }

    private ApplicationInfoModule applicationInfoModule() {
        ApplicationInfoModule applicationInfoModule = new ApplicationInfoModule();

        applicationInfoModule.setProperty(ApplicationInfoModuleSettings.APPLICATION_ID, "chiroelzestraat.admin");
        applicationInfoModule.setProperty(ApplicationInfoModuleSettings.APPLICATION_NAME, "Admin Chiro Elzestraat");

        applicationInfoModule.setProperty(ApplicationInfoModuleSettings.ENVIRONMENT_ID, "development");
        applicationInfoModule.setProperty(ApplicationInfoModuleSettings.ENVIRONMENT_NAME, "development");

        return applicationInfoModule;
    }

    private AcrossWebModule acrossWebModule() {
        AcrossWebModule webModule = new AcrossWebModule();
        webModule.setViewsResourcePath("/static");
        webModule.setSupportViews(AcrossWebViewSupport.THYMELEAF);

        return webModule;
    }

    private DebugWebModule debugWebModule() {
        DebugWebModule debugWebModule = new DebugWebModule();
        debugWebModule.setRootPath("/debug");

        return debugWebModule;
    }

    private EhcacheModule ehcacheModule() {
        EhcacheModule ehcacheModule = new EhcacheModule();
        ehcacheModule.setProperty(EhcacheModuleSettings.CACHE_MANAGER_NAME, "chiroAdminCacheManager");
        ehcacheModule.setProperty(EhcacheModuleSettings.CONFIGURATION_RESOURCE, new ClassPathResource("/config/ehcache.xml"));
        return ehcacheModule;
    }

    private ImageServerCoreModule imageServerCoreModule() {
        ImageServerCoreModule coreModule = new ImageServerCoreModule();
        coreModule.setProperty(ImageServerCoreModuleSettings.IMAGE_STORE_FOLDER, environment.getProperty("imageServerCore.store.folder"));
        coreModule.setProperty(ImageServerCoreModuleSettings.PROVIDE_STACKTRACE, true);
        coreModule.setProperty(ImageServerCoreModuleSettings.IMAGEMAGICK_ENABLED, true);
        coreModule.setProperty(ImageServerCoreModuleSettings.IMAGEMAGICK_USE_GRAPHICSMAGICK, true);
        coreModule.setProperty(ImageServerCoreModuleSettings.IMAGEMAGICK_PATH, environment.getProperty("imageServerCore.transformers.imageMagick.path"));

        coreModule.setProperty(ImageServerCoreModuleSettings.ROOT_PATH, "/resources/images");
        coreModule.setProperty(ImageServerCoreModuleSettings.ACCESS_TOKEN, "standalone-access-token");

        return coreModule;
    }

    private ImageServerAdminWebModule imageServerAdminModule() {
        ImageServerAdminWebModule imageServerAdminWebModule = new ImageServerAdminWebModule();
        imageServerAdminWebModule.setProperty(ImageServerAdminWebModuleSettings.IMAGE_SERVER_URL,
                "/resources/images");
        imageServerAdminWebModule.setProperty(ImageServerAdminWebModuleSettings.ACCESS_TOKEN,
                "standalone-access-token");

        return imageServerAdminWebModule;
    }
}
