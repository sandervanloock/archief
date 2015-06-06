package be.sandervl.webapp.config;

import be.sandervl.modules.AdminModule;
import com.foreach.across.config.AcrossContextConfigurer;
import com.foreach.across.config.EnableAcrossContext;
import com.foreach.across.core.AcrossContext;
import com.foreach.across.module.applicationinfo.ApplicationInfoModule;
import com.foreach.across.module.applicationinfo.ApplicationInfoModuleSettings;
import com.foreach.across.modules.debugweb.DebugWebModule;
import com.foreach.across.modules.web.AcrossWebModule;
import com.foreach.across.modules.web.AcrossWebViewSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableAcrossContext
public class AdminConfiguration implements AcrossContextConfigurer {

    @Bean
    public DataSource acrossDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:/hsql/tgcdemo");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Override
    public void configure(AcrossContext context) {
        context.addModule(acrossWebModule());
        context.addModule(new AdminModule());
        context.addModule(debugWebModule());
        context.addModule(applicationInfoModule());
    }

    private ApplicationInfoModule applicationInfoModule() {
        ApplicationInfoModule applicationInfoModule = new ApplicationInfoModule();

        applicationInfoModule.setProperty(ApplicationInfoModuleSettings.APPLICATION_ID, "chiroelzestraat.admin");
        applicationInfoModule.setProperty(ApplicationInfoModuleSettings.APPLICATION_NAME, "chiroelzestraat.admin");

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
}
