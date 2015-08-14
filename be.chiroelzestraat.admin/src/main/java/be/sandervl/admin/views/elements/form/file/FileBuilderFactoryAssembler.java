package be.sandervl.admin.views.elements.form.file;

import com.foreach.across.modules.entity.views.elements.form.FormElementBuilderFactoryAssemblerSupport;

/**
 * Created by sander on 08/08/2015.
 */
public class FileBuilderFactoryAssembler extends FormElementBuilderFactoryAssemblerSupport<FileFormElementBuilder>
{
    public FileBuilderFactoryAssembler() {
        super( FileFormElementBuilder.class, "file" );
    }
}

