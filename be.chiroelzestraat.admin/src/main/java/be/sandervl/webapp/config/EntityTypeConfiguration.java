package be.sandervl.webapp.config;

import be.sandervl.admin.views.UploadRepositoryEntityViewsBuilder;
import be.sandervl.admin.views.elements.CustomViewElementTypeLookupStrategy;
import be.sandervl.admin.views.elements.EntityFileFormViewFactory;
import be.sandervl.admin.views.elements.form.file.FileFormElementBuilderFactory;
import com.foreach.across.core.annotations.Exposed;
import com.foreach.across.modules.entity.registrars.repository.RepositoryEntityViewsBuilder;
import com.foreach.across.modules.entity.views.EntityFormViewFactory;
import com.foreach.across.modules.entity.views.ViewElementTypeLookupStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by sander on 08/08/2015.
 */
public class EntityTypeConfiguration {

    @Bean
    public ViewElementTypeLookupStrategy viewElementTypeLookupStrategy(){
        return new CustomViewElementTypeLookupStrategy();
    }

    @Bean
    public FileFormElementBuilderFactory fileFormElementBuilderFactory(){
        return new FileFormElementBuilderFactory();
    }
}
