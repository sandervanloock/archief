package be.sandervl.admin.services.upload;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TransferServiceManager {
    private List<TransferService> transferServices;

    public TransferServiceManager(){
        this.transferServices = new ArrayList<TransferService>();
    }

    public URI uploadFile(File file){
        for(TransferService transferService: transferServices){
            return transferService.transferFile(file);
        }
        return null;
    }

    public void register(TransferService transferService){
        this.transferServices.add(transferService);
    }

    public void unregister(TransferService transferService){
        this.transferServices.remove(transferService);
    }


}
