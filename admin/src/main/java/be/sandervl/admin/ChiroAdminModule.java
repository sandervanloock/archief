package be.sandervl.admin;

import be.sandervl.admin.business.Leader;
import be.sandervl.admin.installers.ChiroGroupInstaller;
import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.hibernate.provider.HibernatePackageConfiguringModule;
import com.foreach.across.modules.hibernate.provider.HibernatePackageRegistry;
import com.foreach.across.modules.web.AcrossWebModule;

@AcrossDepends(required = { AcrossWebModule.NAME, AcrossHibernateJpaModule.NAME })
public class ChiroAdminModule extends AcrossModule implements HibernatePackageConfiguringModule
{
    public static final String NAME = "ChiroAdminModule";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Chiroelzestraat Admin module";
    }

    public void configureHibernatePackage( HibernatePackageRegistry hibernatePackage ) {
        hibernatePackage.addPackageToScan( Leader.class );
    }

    @Override
    public Object[] getInstallers() {
        return new Object[]{
                ChiroGroupInstaller.class
        };
    }
}
