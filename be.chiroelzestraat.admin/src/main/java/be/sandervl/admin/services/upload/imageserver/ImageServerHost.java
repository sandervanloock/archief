package be.sandervl.admin.services.upload.imageserver;

import be.sandervl.admin.services.upload.TransferHost;

/**
 * Created by sander on 28/11/2015.
 */
public class ImageServerHost extends TransferHost {

    private String accessToken;
    private String imageServerKeyPrefix;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getImageServerKeyPrefix() {
        return imageServerKeyPrefix;
    }

    public void setImageServerKeyPrefix(String imageServerKeyPrefix) {
        this.imageServerKeyPrefix = imageServerKeyPrefix;
    }
}
