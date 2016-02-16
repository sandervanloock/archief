package be.sandervl.admin.business.upload.pdf;

import be.sandervl.admin.business.upload.image.ChiroImage;
import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pdf extends SettableIdBasedEntity<Pdf> {

    @Id
    @GeneratedValue(generator = "seq_pdf_upload_id")
    @GenericGenerator(
            name = "seq_pdf_upload_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_pdf_upload_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @Column(name="path")
    private String path;

    @OneToMany
    private List<ChiroImage> images;

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

    public List<ChiroImage> getImages() {
        return images;
    }

    public void setImages(List<ChiroImage> images) {
        this.images = images;
    }
}
