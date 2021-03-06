package be.sandervl.admin.controllers.rest;

import be.sandervl.admin.business.upload.image.ChiroImage;
import be.sandervl.admin.business.upload.image.UploadPicture;
import be.sandervl.admin.repositories.upload.image.UploadPictureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadPictureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadPictureController.class);

    @Autowired
    private UploadPictureRepository uploadPictureRepository;

    @RequestMapping(value = "/picture/latest", method = RequestMethod.GET)
    public ChiroImage uploadPicture() {
        List<UploadPicture> pictures =
                uploadPictureRepository.findByDateBetweenOnlineDateAndOfflineDateAndActiveIsTrue();
        if (pictures.size() > 0) {
            return pictures.get(0).getFile();
        }
        return null;
    }
}
