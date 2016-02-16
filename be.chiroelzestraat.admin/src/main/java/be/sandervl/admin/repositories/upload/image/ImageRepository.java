package be.sandervl.admin.repositories.upload.image;


import be.sandervl.admin.business.upload.image.ChiroImage;
import com.foreach.across.core.annotations.Exposed;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;

@Exposed
public interface ImageRepository extends IdBasedEntityJpaRepository<ChiroImage> {
}