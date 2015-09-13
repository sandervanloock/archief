package be.sandervl.admin.views.elements;

import be.sandervl.admin.business.FileUpload;
import com.foreach.across.modules.entity.registry.EntityConfiguration;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyDescriptor;
import com.foreach.across.modules.entity.views.elements.ViewElementMode;
import com.foreach.across.modules.entity.views.elements.ViewElementTypeLookupStrategy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomViewElementTypeLookupStrategy implements ViewElementTypeLookupStrategy {

    /**
     * @return element type or null if none could be determined.
     */
    public String findElementType(EntityConfiguration entityConfiguration,
                                  EntityPropertyDescriptor descriptor,
                                  ViewElementMode viewElementMode) {
        if (descriptor.getPropertyType() != null && ClassUtils.isAssignable(FileUpload.class, descriptor.getPropertyType())) {
            return "file";
        }
        return null;
    }
}
