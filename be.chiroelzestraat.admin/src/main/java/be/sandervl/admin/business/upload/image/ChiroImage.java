package be.sandervl.admin.business.upload.image;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChiroImage extends SettableIdBasedEntity<ChiroImage> {

    @Id
    @GeneratedValue(generator = "seq_chiro_image_id")
    @GenericGenerator(
            name = "seq_chiro_image_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_chiro_image_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @Column(name="path")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setId(Long aLong) {
        this.id = aLong;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
