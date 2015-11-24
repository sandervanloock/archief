package be.sandervl.admin.views.elements.form.file;

import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.builder.AbstractNodeViewElementBuilder;
import com.foreach.across.modules.web.ui.elements.builder.AbstractVoidNodeViewElementBuilder;

/**
 * Created by sander on 08/08/2015.
 */
public class FileFormElementBuilder extends AbstractNodeViewElementBuilder<FileFormElement, FileFormElementBuilder> {

    @Override
    protected FileFormElement createElement(ViewElementBuilderContext viewElementBuilderContext) {
        FileFormElement element = super.apply(new FileFormElement(), viewElementBuilderContext);
        element.setName("file");
        element.setCustomTemplate("th/admin/formFields");
        return element;
    }
}
