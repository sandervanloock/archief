package be.sandervl.modules;

import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.modules.web.AcrossWebModule;

@AcrossDepends(required = { AcrossWebModule.NAME})
public class AdminModule extends AcrossModule
{
    public static final String NAME = "AdminModule";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Test demo module";
    }

}
