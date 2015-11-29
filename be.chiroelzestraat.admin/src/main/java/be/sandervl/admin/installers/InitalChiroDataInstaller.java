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

    @Autowired
    private ChiroYearRepository chiroYearRepository;

    @InstallerMethod
    public void install() throws ParseException {
        createChiroGroup("SPEELCLUB", "9uilvdi3iggvkbl34p5pklav8c@group.calendar.google.com");
        createChiroGroup("RAKKERS", "80k5sp0pj8973vrdj5lafga33g@group.calendar.google.com");
        createChiroGroup("TOPPERS", "vtuadhst93b1h7879gogidqepk@group.calendar.google.com");
        createChiroGroup("KERELS", "25bpqoqdk4j0a6p4m68uqea3ig@group.calendar.google.com");
        ChiroGroup aspiranten = createChiroGroup("ASPIRANTEN", "tn24fuu5fdnpe5mar6qlmuk9f4@group.calendar.google.com");
        createCalendarOwner("VERHUUR", "bms3ile8seb4hobrk4vq31nafo@group.calendar.google.com");

        Leader sander = createLeader("Sander","Van Loock",new Date(),"Valkstraat","58","Sint-Katelijne-Waver",2860);

        String[] parsePatterns = {"yyyy-MM-dd HH:mm:ss"};
        Date fromYear = DateUtils.parseDate("2012-09-21 00:00:00", parsePatterns);
        Date toYear = DateUtils.parseDate("2013-09-21 00:00:00", parsePatterns);
        ChiroYear chiroYear20122013 = createChiroYear(fromYear,toYear,"UNKNOWN");

        createLeaderShip(aspiranten,sander,chiroYear20122013);
    }

    private ChiroYear createChiroYear(Date fromYear, Date toYear, String theme){
        ChiroYear chiroYear = new ChiroYear();
        chiroYear.setFrom_year(fromYear);
        chiroYear.setTo_year(toYear);
        chiroYear.setTheme(theme);
        return chiroYearRepository.save(chiroYear);
    }

    private LeaderShip createLeaderShip(ChiroGroup chirogroup,Leader leader,ChiroYear chiroYear){
        LeaderShip s = new LeaderShip();
        s.setChiroGroup(chirogroup);
        s.setLeader(leader);
        s.setChiroYear(chiroYear);
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
