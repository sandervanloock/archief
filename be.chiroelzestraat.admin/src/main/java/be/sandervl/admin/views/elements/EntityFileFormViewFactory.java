package be.sandervl.admin.views.elements;

import be.sandervl.admin.business.FileUpload;
import com.foreach.across.modules.bootstrapui.elements.Grid;
import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.bootstrapui.elements.builder.FormViewElementBuilder;
import com.foreach.across.modules.entity.controllers.EntityControllerAttributes;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyDescriptor;
import com.foreach.across.modules.entity.support.EntityMessageCodeResolver;
import com.foreach.across.modules.entity.views.*;
import com.foreach.across.modules.entity.views.support.EntityMessages;
import com.foreach.across.modules.entity.web.EntityLinkBuilder;
import com.foreach.across.modules.web.ui.ViewElements;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by sander on 21/11/2015.
 */
public class EntityFileFormViewFactory<V extends ViewCreationContext> extends EntityFormViewFactory<V> {

    public static final String FORM_NAME = "entityForm";
    public static final String FORM_LEFT = "entityForm-left";
    public static final String FORM_RIGHT = "entityForm-right";

    public EntityFileFormViewFactory() {
        setViewElementMode(ViewElementMode.FORM_WRITE);
    }

    @Override
    protected EntityFormView createEntityView(ModelMap model) {
        return new EntityFormView(model);
    }


    @Override
    protected ViewElements buildViewElements(V viewCreationContext,
                                             EntityViewElementBuilderContext<EntityFormView> viewElementBuilderContext,
                                             EntityMessageCodeResolver messageCodeResolver) {
        ViewElements elements
                = super.buildViewElements(viewCreationContext, viewElementBuilderContext, messageCodeResolver);

        EntityLinkBuilder linkBuilder = viewElementBuilderContext.getEntityView().getEntityLinkBuilder();
        EntityMessages messages = viewElementBuilderContext.getEntityView().getEntityMessages();
        FormViewElementBuilder builder = bootstrapUi.form()
                .name(FORM_NAME)
                .commandAttribute(EntityControllerAttributes.VIEW_REQUEST)
                .post()
                .multipart()
                .noValidate()
                .action(buildActionUrl(viewElementBuilderContext))
                .add(
                        bootstrapUi.row()
                                .add(
                                        bootstrapUi.column(Grid.Device.MD.width(Grid.Width.HALF))
                                                .name(FORM_LEFT)
                                                .addAll(elements)
                                )
                                .add(
                                        bootstrapUi.column(Grid.Device.MD.width(Grid.Width.HALF))
                                                .name(FORM_RIGHT)
                                )
                )
                .add(
                        bootstrapUi.container()
                                .name("buttons")
                                .add(
                                        bootstrapUi.button()
                                                .name("btn-save")
                                                .style(Style.PRIMARY)
                                                .submit()
                                                .text(messages.messageWithFallback("actions.save"))
                                )
                                .add(
                                        bootstrapUi.button()
                                                .name("btn-cancel")
                                                .link(linkBuilder.overview())
                                                .text(
                                                        messages.messageWithFallback("actions.cancel")
                                                )
                                )
                );
        Predicate<EntityPropertyDescriptor> fileUploadPropertyPredicate = p -> p.getClass().isAssignableFrom(FileUpload.class);
        List<EntityPropertyDescriptor> properties = viewCreationContext.getEntityConfiguration().getPropertyRegistry().getProperties();
        if (properties.stream().filter(fileUploadPropertyPredicate).findAny().isPresent()) {
            builder.multipart();
        }
        return builder.build(viewElementBuilderContext);
    }


    private String buildActionUrl(EntityViewElementBuilderContext<EntityFormView> viewElementBuilderContext) {
        EntityFormView formView = viewElementBuilderContext.getEntityView();

        if (formView.isUpdate()) {
            return formView.getEntityLinkBuilder().update(formView.getOriginalEntity());
        }

        return formView.getEntityLinkBuilder().create();
    }
}
