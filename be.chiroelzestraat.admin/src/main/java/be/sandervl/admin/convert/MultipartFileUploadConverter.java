package be.sandervl.admin.convert;

import be.sandervl.admin.business.FileUpload;
import be.sandervl.admin.repositories.FileUploadRepository;
import be.sandervl.admin.services.upload.TransferServiceManager;
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
public class MultipartFileUploadConverter implements Converter<MultipartFile, FileUpload> {

    private static Logger LOG = LoggerFactory.getLogger(MultipartFileUploadConverter.class);

    @Autowired
    @Lazy
    FileUploadRepository fileUploadRepository;

    @Autowired
    @Lazy
    private TransferServiceManager transferServiceManager;

    @Autowired
    @Lazy
    private Environment environment;

    @Override
    public FileUpload convert(MultipartFile multipartFile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String currentAvatarPath = request.getParameter("entity.avatar.id");
        if (currentAvatarPath != null && StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            return fileUploadRepository.getOne(Long.valueOf(currentAvatarPath));
        }

        LOG.info("New multipart file is being converted "+multipartFile.getOriginalFilename());
        try {
            String uploadDirectory = environment.getProperty("upload.dir") != null ? environment.getProperty("upload.dir") : "";
            File file = new File(uploadDirectory + multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            URI url = transferServiceManager.uploadFile(file);
            FileUpload fileUpload = new FileUpload();
            fileUpload.setPath(url.toString());
            fileUploadRepository.save(fileUpload);
            return fileUpload;
        } catch (IOException e) {
            LOG.error("Something went wrong converting mulitipart file to output stream", e);
        }
        return null;
    }
}
