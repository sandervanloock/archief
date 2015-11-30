
package be.sandervl.admin.config;

import be.sandervl.admin.services.upload.TransferServiceManager;
import be.sandervl.admin.services.upload.ftp.FTPTransferHost;
import be.sandervl.admin.services.upload.ftp.FTPTransferService;
import be.sandervl.admin.services.upload.imageserver.ImageServerHost;
import be.sandervl.admin.services.upload.imageserver.ImageServerTransferService;
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

    /*@Bean
    public FTPTransferService ftpTransferService() {
        FTPTransferHost transferHost = new FTPTransferHost();
        transferHost.setName("FTP - Chiro Elzestraat");
        transferHost.setHost("ftp.chiroelzestraat.be");
        transferHost.setUsername("chiroelzestraat.be");
        transferHost.setPassword("leider");
        transferHost.setBasePath("http://www.chiroelzestraat.be");
        transferHost.setUploadDirectory("/chirojongens/uploads/admin");

        return new FTPTransferService(transferHost);
    }

    @Bean
    public TransferServiceManager transferServiceManager() {
        TransferServiceManager transferServiceManager = new TransferServiceManager();
        transferServiceManager.register(ftpTransferService());
        return transferServiceManager;
    } */

    @Bean
    public ImageServerTransferService imageServerTransferService() {
        ImageServerHost transferHost = new ImageServerHost();
        transferHost.setName("Image Server - Chiro Elzestraat");
        transferHost.setHost(environment.getProperty("imageserver.host"));
        transferHost.setAccessToken(environment.getProperty("imageserver.accesstoken"));
        transferHost.setImageServerKeyPrefix(environment.getProperty("imageserver.prefix"));
        return new ImageServerTransferService(transferHost);
    }

    @Bean
    public TransferServiceManager transferServiceManager() {
        TransferServiceManager transferServiceManager = new TransferServiceManager();
        transferServiceManager.register(imageServerTransferService());
        return transferServiceManager;
    }
}
