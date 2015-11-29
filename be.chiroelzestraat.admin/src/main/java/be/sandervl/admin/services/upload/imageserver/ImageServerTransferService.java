package be.sandervl.admin.services.upload.imageserver;

import be.sandervl.admin.services.upload.AbstractTransferService;
import com.foreach.imageserver.client.ImageServerClient;
import com.foreach.imageserver.client.RemoteImageServerClient;
import com.foreach.imageserver.dto.ImageInfoDto;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by sander on 28/11/2015.
 */
public class ImageServerTransferService extends AbstractTransferService<ImageServerHost> {

    private static Logger LOG = LoggerFactory.getLogger(ImageServerTransferService.class);

    private ImageServerClient imageServerClient;

    public ImageServerTransferService(ImageServerHost transferHost) {
        super(transferHost);
        imageServerClient = new RemoteImageServerClient(transferHost.getHost(), transferHost.getAccessToken());
    }


    @Override
    public URI transferFile(File file) {
        String url = "";
        try {
            String imageKey = transferHost.getImageServerKeyPrefix() + ":" + UUID.randomUUID().toString();
            ImageInfoDto image = imageServerClient.loadImage(imageKey, IOUtils.toByteArray(new FileInputStream(file)), new Date());
            url = imageServerClient.imageUrl(image.getExternalId(), "default",0,0);
            return new URI(url);
        } catch (URISyntaxException e) {
            LOG.error("Malformed string while converting to URI: " + url, e);
        } catch (FileNotFoundException e) {
            LOG.error("File not found " + file.getAbsoluteFile(), e);
        } catch (IOException e) {
            LOG.error("Could not convert to file bytearray " + file, e);
        }
        return null;
    }
}
