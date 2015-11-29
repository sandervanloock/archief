package be.sandervl.admin.business;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class ChiroYear extends SettableIdBasedEntity<ChiroYear> {

    @Id
    @GeneratedValue(generator = "seq_chiroyear_id")
    @GenericGenerator(
            name = "seq_chiroyear_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_chiroyear_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @NotNull
    @Column(name="from_year")
    private Date from_year;

    @NotNull
    @Column(name="to_year")
    private Date to_year;

    @Column(name="theme")
    private String theme;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getFrom_year() {
        return from_year;
    }

    public void setFrom_year(Date from_year) {
        this.from_year = from_year;
    }

    public Date getTo_year() {
        return to_year;
    }

    public void setTo_year(Date to_year) {
        this.to_year = to_year;
    }
}

