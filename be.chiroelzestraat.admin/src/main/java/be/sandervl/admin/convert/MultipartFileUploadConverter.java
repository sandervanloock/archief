package be.sandervl.admin.convert;

import be.sandervl.admin.business.FileUpload;
import be.sandervl.admin.repositories.FileUploadRepository;
import be.sandervl.admin.services.upload.TransferServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public FileUpload convert(MultipartFile multipartFile) {
        try {
            File file = new File(multipartFile.getOriginalFilename());
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            URI url = transferServiceManager.uploadFile(file);
            FileUpload fileUpload = new FileUpload();
            fileUpload.setPath(url.toString());
            fileUploadRepository.save(fileUpload);
            return fileUpload;
        }
        catch (IOException e) {
            LOG.error("Something went wrong converting mulitipart file to output stream",e.getMessage(),e.getStackTrace());
        }
        return null;
    }
}
