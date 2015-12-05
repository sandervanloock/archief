package be.sandervl.admin.convert;

import be.sandervl.admin.business.upload.image.Image;
import be.sandervl.admin.repositories.upload.image.ImageRepository;
import be.sandervl.admin.services.upload.imageserver.ImageServerTransferService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;


/**
 * Created by sander on 14/09/2015.
 */
public class MultipartFileImageConverter implements Converter<MultipartFile, Image> {

    private static Logger LOG = LoggerFactory.getLogger(MultipartFileImageConverter.class);

    @Autowired
    @Lazy
    ImageRepository fileUploadRepository;

    @Autowired
    @Lazy
    private ImageServerTransferService imageServerTransferService;

    @Autowired
    @Lazy
    private Environment environment;

    @Override
    public Image convert(MultipartFile multipartFile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String currentFilePath = request.getParameter("entity.file.id");
        if (currentFilePath != null && StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            return fileUploadRepository.getOne(Long.valueOf(currentFilePath));
        }

        LOG.info("New multipart file is being converted " + multipartFile.getOriginalFilename());
        try {
            String uploadDirectory = environment.getProperty("upload.dir") != null ? environment.getProperty("upload.dir") : "";
            File file = new File(uploadDirectory + multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            URI url = imageServerTransferService.transferFile(file);
            Image fileUpload = new Image();
            fileUpload.setPath(url.toString());
            fileUploadRepository.save(fileUpload);
            return fileUpload;
        } catch (IOException e) {
            LOG.error("Something went wrong converting mulitipart file to output stream", e);
        }
        return null;
    }
}
