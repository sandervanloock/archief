package be.sandervl.admin.services.upload;

import be.sandervl.admin.services.upload.ftp.FTPTransferHost;
import be.sandervl.admin.services.upload.ftp.FTPTransferService;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

public class TransferServiceTest {

    private FTPTransferService ftpTransferService;

    @Before
    public void setUp() throws Exception {
        FTPTransferHost transferHost = new FTPTransferHost();
        transferHost.setName("FTP - Chiro Elzestraat");
        transferHost.setHost("ftp.chiroelzestraat.be");
        transferHost.setUsername("chiroelzestraat.be");
        transferHost.setPassword("leider");

        ftpTransferService = new FTPTransferService(transferHost);

    }

    @Test
    public void testUploadFile() throws Exception {
        InputStream cpResource = this.getClass().getClassLoader().getResourceAsStream("DSCF1215.JPG");
        File tmpFile = File.createTempFile("DSCF1215",".JPG");
        FileUtils.copyInputStreamToFile(cpResource, tmpFile);

        URI actualResult = ftpTransferService.transferFile(tmpFile);

        Assert.assertNotNull(actualResult);
    }
}