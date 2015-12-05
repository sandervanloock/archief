package be.sandervl.admin.repositories.upload.image;

import be.sandervl.admin.business.upload.image.UploadPicture;
import com.foreach.across.core.annotations.Exposed;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Exposed
public interface UploadPictureRepository extends IdBasedEntityJpaRepository<UploadPicture> {

    @Query("select t from UploadPicture t where t.online_date <= CURRENT_TIMESTAMP and t.offline_date >= CURRENT_TIMESTAMP and t.active=1")
    List<UploadPicture> findByDateBetweenOnlineDateAndOfflineDateAndActiveIsTrue();

}
