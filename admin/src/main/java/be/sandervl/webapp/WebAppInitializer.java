package be.sandervl.webapp;

import be.sandervl.webapp.config.AdminConfiguration;
import com.foreach.across.modules.web.context.AcrossWebApplicationContext;
import com.foreach.across.modules.web.servlet.AbstractAcrossServletInitializer;

/**
 * Created by sander on 03/06/2015.
 */
public class WebAppInitializer extends AbstractAcrossServletInitializer {

    @Override
    protected void configure(AcrossWebApplicationContext acrossWebApplicationContext) {
        acrossWebApplicationContext.register(AdminConfiguration.class);
    }
}
