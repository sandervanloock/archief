package be.sandervl.admin.repositories;

import be.sandervl.admin.business.CalendarOwner;
import be.sandervl.admin.business.UploadPicture;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UploadPictureRepository extends IdBasedEntityJpaRepository<UploadPicture> {

    @Query("select t from UploadPicture t where t.online_date <= CURRENT_TIMESTAMP and t.offline_date >= CURRENT_TIMESTAMP and t.active=1")
    Iterable<UploadPicture> findByDateBetweenOnlineDateAndOfflineDateAndActiveIsTrue();

}
