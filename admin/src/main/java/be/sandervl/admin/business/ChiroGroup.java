package be.sandervl.admin.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.File;
import java.util.Date;

@Entity
public class ChiroGroup extends CalendarOwner {

    @Column(name="start_hour")
    private Date startHour;

    @Column(name="end_hour")
    private Date endHour;

    @Column(name="logo")
    private File logo;

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }
}
