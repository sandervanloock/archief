
package be.sandervl.admin.config;

import be.sandervl.admin.services.upload.TransferServiceManager;
import be.sandervl.admin.services.upload.ftp.FTPTransferHost;
import be.sandervl.admin.services.upload.ftp.FTPTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by sander on 12/08/2015.
 */
@Configuration
public class TransferServiceConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public TransferServiceManager transferServiceManager(){
        TransferServiceManager transferServiceManager = new TransferServiceManager();
        FTPTransferHost transferHost = new FTPTransferHost();
        transferHost.setName("FTP - Chiro Elzestraat");
        transferHost.setHost("ftp.chiroelzestraat.be");
        transferHost.setUsername("chiroelzestraat.be");
        transferHost.setPassword("leider");
        transferHost.setBasePath("http://www.chiroelzestraat.be");
        transferHost.setUploadDirectory("/chirojongens/uploads/admin");

        FTPTransferService transferService = new FTPTransferService(transferHost);

        transferServiceManager.register(transferService);

        return transferServiceManager;
    }

}
