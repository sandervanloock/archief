package be.sandervl.admin.repositories;


import be.sandervl.admin.business.FileUpload;
import com.foreach.across.core.annotations.Exposed;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;

@Exposed
public interface FileUploadRepository extends IdBasedEntityJpaRepository<FileUpload> {
}