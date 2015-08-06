package be.sandervl.admin.installers;

import be.sandervl.admin.business.CalendarOwner;
import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.repositories.CalendarOwnerRepository;
import be.sandervl.admin.repositories.ChiroGroupRepository;
import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.annotations.InstallerMethod;
import com.foreach.across.core.installers.InstallerPhase;
import com.foreach.across.core.installers.InstallerRunCondition;
import org.springframework.beans.factory.annotation.Autowired;

@Installer(
        description = "Installs default chirogroups",
        phase = InstallerPhase.AfterContextBootstrap,
        runCondition = InstallerRunCondition.VersionDifferent,
        version = 1
)
public class ChiroGroupInstaller {

    @Autowired
    private ChiroGroupRepository chiroGroupRepository;

    @Autowired
    private CalendarOwnerRepository calendarOwnerRepository;

    @InstallerMethod
    public void install() {
        createChiroGroup("SPEELCLUB", "ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com");
        createChiroGroup("RAKKERS", "80k5sp0pj8973vrdj5lafga33g@group.calendar.google.com");
        createChiroGroup("TOPPERS", "vtuadhst93b1h7879gogidqepk@group.calendar.google.com");
        createChiroGroup("KERELS", "25bpqoqdk4j0a6p4m68uqea3ig@group.calendar.google.com");
        createChiroGroup("ASPIRANTEN", "9aomc83dom4mbdq10m5t8vnkpo@group.calendar.google.com");
        createCalendarOwner("VERHUUR", "bms3ile8seb4hobrk4vq31nafo@group.calendar.google.com");
        createCalendarOwner("TEST", "52on50vspdubfmmbdenbamr0k0@group.calendar.google.com");
    }

    public void createChiroGroup(String name, String calendarId) {
        ChiroGroup chiroGroup = new ChiroGroup();
        chiroGroup.setName(name);
        chiroGroup.setCalendarId(calendarId);
        chiroGroupRepository.save(chiroGroup);
    }

    public void createCalendarOwner(String name, String calendarId) {
        CalendarOwner calendarOwner = new CalendarOwner();
        calendarOwner.setName(calendarId);
        calendarOwner.setCalendarId(calendarId);
        calendarOwnerRepository.save(calendarOwner);
    }
}
