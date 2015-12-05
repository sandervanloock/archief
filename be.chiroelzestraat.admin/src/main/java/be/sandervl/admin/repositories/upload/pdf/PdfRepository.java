package be.sandervl.admin.repositories.upload.pdf;


import be.sandervl.admin.business.upload.pdf.Pdf;
import com.foreach.across.core.annotations.Exposed;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;

@Exposed
public interface PdfRepository extends IdBasedEntityJpaRepository<Pdf> {
}