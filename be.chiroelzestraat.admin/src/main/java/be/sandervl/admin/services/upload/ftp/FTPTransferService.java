package be.sandervl.admin.services.upload.ftp;

import be.sandervl.admin.services.upload.AbstractTransferService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by sander on 11/08/2015.
 */
public class FTPTransferService extends AbstractTransferService<FTPTransferHost> {

    private static Logger LOG = LoggerFactory.getLogger(FTPTransferService.class);

    public FTPTransferService(FTPTransferHost transferHost) {
        super(transferHost);
    }

    @Override
    public URI transferFile(File file) {
        StringBuilder result = new StringBuilder();
        result.append(transferHost.getBasePath());
        result.append(transferHost.getUploadDirectory());
        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        ftp.configure(config);
        boolean error = false;
        try {
            int reply;
            ftp.connect(transferHost.getHost());
            if (!ftp.login(transferHost.getUsername(), transferHost.getPassword())) {
                ftp.logout();
                return null;
            }
            ftp.changeWorkingDirectory(transferHost.getUploadDirectory());
            LOG.info("Connected to " + transferHost.getName() + ".");
            LOG.info(ftp.getReplyString());

            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                LOG.error("FTP server refused connection.");
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftp.storeFile(file.getName(), new FileInputStream(file));
            result.append("/");
            result.append(file.getName());
            ftp.logout();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                    LOG.info("Successfully disconnected for FTP ", transferHost.getName());
                    return new URI(result.toString());
                } catch (IOException ioe) {
                    LOG.error("Something went wrong while disconnecting from FTP ", transferHost.getName(), ioe.getStackTrace());
                    return null;
                } catch (URISyntaxException e) {
                    LOG.error("Something went wrong while forming the URI after upload string: ", result, e.getStackTrace());
                    e.printStackTrace();
                }
            }
        }
        try {
            return new URI(result.toString());
        } catch (URISyntaxException e) {
            LOG.error("Something went wrong while forming the URI after upload string: ", result, e.getStackTrace());
            return null;
        }
    }
}