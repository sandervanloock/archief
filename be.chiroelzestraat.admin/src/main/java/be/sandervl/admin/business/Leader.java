package be.sandervl.admin.business;

import be.sandervl.admin.business.upload.image.ChiroImage;
import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Leader extends SettableIdBasedEntity<Leader> {

    private static Logger LOG = LoggerFactory.getLogger(Leader.class);

    @Id
    @GeneratedValue(generator = "seq_leader_id")
    @GenericGenerator(
            name = "seq_leader_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_leader_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "birth_day")
    private Date birthDay;

    @Column(name = "street")
    @Size(max = 255)
    private String street;

    @Column(name = "number")
    @Size(max = 4)
    private String number;

    @Column(name = "city")
    @Size(max = 255)
    private String city;

    @Column(name = "zip_code")
    @Min(0)
    @Max(9999)
    private int zipCode;

    @OneToOne
    @JoinColumn(name = "file_upload_id")
    private ChiroImage file;

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public ChiroImage getFile() {
        return file;
    }

    public void setFile(ChiroImage file) {
        this.file = file;
    }
}
