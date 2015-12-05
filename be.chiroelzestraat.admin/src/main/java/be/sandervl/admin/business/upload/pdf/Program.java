package be.sandervl.admin.business.upload.pdf;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Table(name="ChiroProgram")
public class Program extends SettableIdBasedEntity<Program> {

    private static Logger LOG = LoggerFactory.getLogger(Program.class);

    @Id
    @GeneratedValue(generator = "seq_upload_program_id")
    @GenericGenerator(
            name = "seq_upload_program_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_upload_program_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="online_date")
    private Date online_date;

    @Column(name="offline_date")
    private Date offline_date;

    @Column(name="active")
    private boolean active;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_upload_id")
    private Pdf file;

    @Override
    public void setId(Long aLong) {
        this.id = aLong;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOnline_date() {
        return online_date;
    }

    public void setOnline_date(Date online_date) {
        this.online_date = online_date;
    }

    public Date getOffline_date() {
        return offline_date;
    }

    public void setOffline_date(Date offline_date) {
        this.offline_date = offline_date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Pdf getFile() {
        return file;
    }

    public void setFile(Pdf file) {
        this.file = file;
    }
}
