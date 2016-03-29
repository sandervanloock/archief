package be.sandervl.admin.installers;

import be.sandervl.admin.business.*;
import be.sandervl.admin.repositories.*;
import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.annotations.InstallerMethod;
import com.foreach.across.core.installers.InstallerPhase;
import com.foreach.across.core.installers.InstallerRunCondition;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;

@Installer(
        description = "Installs default chiro functions",
        phase = InstallerPhase.AfterContextBootstrap,
        runCondition = InstallerRunCondition.VersionDifferent,
        version = 1
)
public class InitalChiroFunctionInstaller {

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private FunctionOwnerRepository functionOwnerRepository;

    @InstallerMethod
    public void install() throws ParseException {
        createFunction("Groepsleider");
        createFunction("Kassier");
        createFunction("Verhuur");
        createFunction("Zangleider");
        createFunction("Container");
    }

    private void createFunction(String name) {
        Function function = new Function();
        function.setName(name);

        functionRepository.save(function);
    }

}