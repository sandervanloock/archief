package com.foreach.imageserver.test.standalone.module;

import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.core.context.configurer.AnnotatedClassConfigurer;
import com.foreach.across.core.context.configurer.ApplicationContextConfigurer;
import com.foreach.imageserver.core.ImageServerCoreModule;
import com.foreach.imageserver.test.standalone.module.config.StandaloneConfiguration;
import com.foreach.imageserver.test.standalone.module.installers.InitialContextAndResolutionInstaller;

import java.util.Set;

/**
 * @author Arne Vandamme
 */
@AcrossDepends(required = ImageServerCoreModule.NAME)
public class StandaloneWebModule extends AcrossModule
{
	public static final String NAME = "StandaloneImageServerTestModule";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return "Create some test data for ImageServer installation.";
	}

	@Override
	protected void registerDefaultApplicationContextConfigurers( Set<ApplicationContextConfigurer> contextConfigurers ) {
		contextConfigurers.add( new AnnotatedClassConfigurer( StandaloneConfiguration.class ) );
	}

	@Override
	public Object[] getInstallers() {
		return new Object[] { InitialContextAndResolutionInstaller.class };
	}
}
