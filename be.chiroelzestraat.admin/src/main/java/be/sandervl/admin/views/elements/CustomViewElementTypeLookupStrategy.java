package be.sandervl.admin.views.elements;

import be.sandervl.admin.business.FileUpload;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyDescriptor;
import com.foreach.across.modules.entity.views.ViewElementMode;
import com.foreach.across.modules.entity.views.ViewElementTypeLookupStrategy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomViewElementTypeLookupStrategy implements ViewElementTypeLookupStrategy {


    @Override
    public String findElementType(EntityPropertyDescriptor entityPropertyDescriptor, ViewElementMode viewElementMode) {
        if (entityPropertyDescriptor.getPropertyType() != null &&
                ClassUtils.isAssignable(FileUpload.class, entityPropertyDescriptor.getPropertyType()))
        {
            return "file";
        }
        return null;
    }
}