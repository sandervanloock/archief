package be.sandervl.admin.business;

import be.sandervl.admin.hibernate.validator.BlankOrPattern;
import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by sander on 30/07/2015.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class CalendarOwner extends SettableIdBasedEntity<CalendarOwner> {

    @Id
    @GeneratedValue(generator = "seq_chirogroup_id")
    @GenericGenerator(
            name = "seq_chirogroup_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_chirogroup_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @Column(name = "calendar_id")
    @BlankOrPattern(regexp = "^\\w*@group.calendar.google.com$")
    private String calendarId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }
}
