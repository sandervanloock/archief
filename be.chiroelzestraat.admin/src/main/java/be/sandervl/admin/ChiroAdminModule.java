package be.sandervl.admin;

import be.sandervl.admin.business.Leader;
import be.sandervl.admin.installers.InitalChiroDataInstaller;
import be.sandervl.admin.installers.InitialContextAndResolutionInstaller;
import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.hibernate.provider.HibernatePackageConfiguringModule;
import com.foreach.across.modules.hibernate.provider.HibernatePackageRegistry;
import com.foreach.across.modules.web.AcrossWebModule;
import com.foreach.imageserver.core.ImageServerCoreModule;

@AcrossDepends(required = { AcrossWebModule.NAME, AcrossHibernateJpaModule.NAME, EntityModule.NAME, ImageServerCoreModule.NAME})
    public class ChiroAdminModule extends AcrossModule implements HibernatePackageConfiguringModule
{
    public static final String NAME = "ChiroAdminModule";
    private static final String RESOURCES = "admin";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Chiroelzestraat Admin module";
    }

    @Override
    public String getResourcesKey() {
        return RESOURCES;
    }

    public void configureHibernatePackage( HibernatePackageRegistry hibernatePackage ) {
        hibernatePackage.addPackageToScan( Leader.class );
    }

    @Override
    public Object[] getInstallers() {
        return new Object[]{
                InitalChiroDataInstaller.class,
                InitialContextAndResolutionInstaller.class
        };
    }
}
