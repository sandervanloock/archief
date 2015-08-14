package be.sandervl.admin.services.upload;

import java.io.File;
import java.net.URI;

/**
 * Created by sander on 12/08/2015.
 */
public abstract class AbstractTransferService<T extends TransferHost> implements TransferService {

    protected T transferHost;

    public AbstractTransferService(T transferHost) {
        this.transferHost = transferHost;
    }

    public abstract URI transferFile(File file);

}
