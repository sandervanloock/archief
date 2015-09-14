package be.sandervl.webapp.config;

import be.sandervl.admin.convert.MultipartFileUploadConverter;
import com.foreach.across.core.annotations.Exposed;
import com.foreach.common.spring.convert.CustomConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionServiceConfiguration {

    static class CustomFormattingConversionServiceFactoryBean extends CustomConversionServiceFactoryBean {
        @Override
        protected GenericConversionService createConversionService() {
            return new DefaultFormattingConversionService();
        }

        @Override
        public Class<? extends ConversionService> getObjectType() {
            return DefaultFormattingConversionService.class;
        }
    }

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionService = new CustomFormattingConversionServiceFactoryBean();
        Set<Object> converters = new HashSet<>();
        converters.add(multipartFileUploadConverter());

        conversionService.setConverters(converters);
        return conversionService;
    }

    @Bean
    public MultipartFileUploadConverter multipartFileUploadConverter() {
        return new MultipartFileUploadConverter();
    }
}
