package be.sandervl.webapp.config;

import be.sandervl.admin.controllers.LeaderController;
import be.sandervl.admin.views.elements.CustomViewElementTypeLookupStrategy;
import be.sandervl.admin.views.elements.form.file.FileBuilderFactoryAssembler;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyRegistry;
import com.foreach.across.modules.entity.views.elements.ViewElementTypeLookupStrategy;
import com.foreach.across.modules.entity.views.elements.form.FormElementBuilderFactoryAssemblerSupport;
import com.foreach.across.modules.entity.views.elements.form.FormElementBuilderSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sander on 08/08/2015.
 */
@Configuration
public class EntityTypeConfiguration {

    @Bean
    public ViewElementTypeLookupStrategy viewElementTypeLookupStrategy(){
        return new CustomViewElementTypeLookupStrategy();
    }

    @Bean
    public FormElementBuilderFactoryAssemblerSupport<? extends FormElementBuilderSupport> fileBuilderFactoryAssembler(){
        return new FileBuilderFactoryAssembler();
    }
}
