package be.sandervl.admin.services.upload;

import java.io.File;
import java.net.URI;

public interface TransferService<T extends TransferHost> {

    URI transferFile(File file);

}
