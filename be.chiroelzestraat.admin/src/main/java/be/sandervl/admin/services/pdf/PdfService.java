package be.sandervl.admin.services.pdf;

import com.foreach.imageserver.core.business.Image;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * Created by sander on 16/02/2016.
 */
public interface PdfService {

    List<URI> convert(File file);

}
