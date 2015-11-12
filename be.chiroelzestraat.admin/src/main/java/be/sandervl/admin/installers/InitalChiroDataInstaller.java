package be.sandervl.admin.installers;

import be.sandervl.admin.business.CalendarOwner;
import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.business.Leader;
import be.sandervl.admin.business.LeaderShip;
import be.sandervl.admin.repositories.CalendarOwnerRepository;
import be.sandervl.admin.repositories.ChiroGroupRepository;
import be.sandervl.admin.repositories.LeaderRepository;
import be.sandervl.admin.repositories.LeaderShipRepository;
import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.annotations.InstallerMethod;
import com.foreach.across.core.installers.InstallerPhase;
import com.foreach.across.core.installers.InstallerRunCondition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Installer(
        description = "Installs default chirogroups",
        phase = InstallerPhase.AfterContextBootstrap,
        runCondition = InstallerRunCondition.VersionDifferent,
        version = 1
)
public class InitalChiroDataInstaller {

    @Autowired
    private ChiroGroupRepository chiroGroupRepository;

    @Autowired
    private CalendarOwnerRepository calendarOwnerRepository;

    @Autowired
    private LeaderRepository leaderRepository;

    @Autowired
    private LeaderShipRepository leaderShipRepository;

    @InstallerMethod
    public void install() {
        ChiroGroup speelclub = createChiroGroup("SPEELCLUB", "ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com");
        createChiroGroup("RAKKERS", "80k5sp0pj8973vrdj5lafga33g@group.calendar.google.com");
        createChiroGroup("TOPPERS", "vtuadhst93b1h7879gogidqepk@group.calendar.google.com");
        createChiroGroup("KERELS", "25bpqoqdk4j0a6p4m68uqea3ig@group.calendar.google.com");
        createChiroGroup("ASPIRANTEN", "9aomc83dom4mbdq10m5t8vnkpo@group.calendar.google.com");
        createCalendarOwner("VERHUUR", "bms3ile8seb4hobrk4vq31nafo@group.calendar.google.com");
        createCalendarOwner("TEST", "52on50vspdubfmmbdenbamr0k0@group.calendar.google.com");

        Leader sander = createLeader("Sander","Van Loock",new Date(),"Valkstraat","58","Sint-Katelijne-Waver",2860);

        createLeaderShip(speelclub,sander);
    }

    private LeaderShip createLeaderShip(ChiroGroup chirogroup,Leader leader){
        LeaderShip s = new LeaderShip();
        s.setChiroGroup(chirogroup);
        s.setLeader(leader);
        return leaderShipRepository.save(s);
    }

    private Leader createLeader(String firstName,String lastName,Date birthDay,String street,String number,String city,
                               int zipCode){
        Leader sander = new Leader();
        sander.setFirstName(firstName);
        sander.setLastName(lastName);
        sander.setBirthDay(birthDay);
        sander.setStreet(street);
        sander.setNumber(number);
        sander.setCity(city);
        sander.setZipCode(zipCode);
        return leaderRepository.save(sander);
    }

    public ChiroGroup createChiroGroup(String name, String calendarId) {
        ChiroGroup chiroGroup = new ChiroGroup();
        chiroGroup.setName(name);
        chiroGroup.setCalendarId(calendarId);
        return chiroGroupRepository.save(chiroGroup);
    }

    public void createCalendarOwner(String name, String calendarId) {
        CalendarOwner calendarOwner = new CalendarOwner();
        calendarOwner.setName(calendarId);
        calendarOwner.setCalendarId(calendarId);
        calendarOwnerRepository.save(calendarOwner);
    }
}
