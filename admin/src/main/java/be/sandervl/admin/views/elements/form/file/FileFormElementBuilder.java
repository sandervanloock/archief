package be.sandervl.admin.views.elements.form.file;

import com.foreach.across.modules.entity.views.elements.form.FormElementBuilderSupport;

/**
 * Created by sander on 08/08/2015.
 */
public class FileFormElementBuilder extends FormElementBuilderSupport<FileFormElement> {

    public FileFormElementBuilder() {
        super(FileFormElement.class);
        this.setCustomTemplate("th/admin/formFields");
    }
}
