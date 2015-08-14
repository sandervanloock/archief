package be.sandervl.admin.services.upload.ftp;

import be.sandervl.admin.services.upload.TransferHost;

/**
 * Created by sander on 12/08/2015.
 */
public class FTPTransferHost extends TransferHost {
    private String username,password,uploadDirectory,basePath;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUploadDirectory() {
        return uploadDirectory;
    }

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
