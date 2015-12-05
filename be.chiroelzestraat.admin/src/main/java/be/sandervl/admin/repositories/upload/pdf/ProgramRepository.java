package be.sandervl.admin.repositories.upload.pdf;


import be.sandervl.admin.business.upload.image.UploadPicture;
import be.sandervl.admin.business.upload.pdf.Program;
import com.foreach.across.core.annotations.Exposed;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Exposed
public interface ProgramRepository extends IdBasedEntityJpaRepository<Program> {

    @Query("select t from Program t where t.online_date <= CURRENT_TIMESTAMP and t.offline_date >= CURRENT_TIMESTAMP and t.active=1")
    List<Program> findByDateBetweenOnlineDateAndOfflineDateAndActiveIsTrue();
}