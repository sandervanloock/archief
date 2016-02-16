package be.sandervl.admin.convert;

import be.sandervl.admin.business.upload.image.ChiroImage;
import be.sandervl.admin.business.upload.pdf.Pdf;
import be.sandervl.admin.repositories.upload.image.ChiroImageRepository;
import be.sandervl.admin.repositories.upload.pdf.PdfRepository;
import be.sandervl.admin.services.pdf.PdfService;
import be.sandervl.admin.services.upload.ftp.FTPTransferService;
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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sander on 14/09/2015.
 */
public class MultipartFilePdfConverter implements Converter<MultipartFile, Pdf> {

    private static Logger LOG = LoggerFactory.getLogger(MultipartFilePdfConverter.class);

    @Autowired
    @Lazy
    private PdfRepository pdfRepository;

    @Autowired
    @Lazy
    ChiroImageRepository chiroImageRepository;

    @Autowired
    @Lazy
    private FTPTransferService ftpTransferService;

    @Autowired
    @Lazy
    private PdfService pdfService;

    @Autowired
    @Lazy
    private Environment environment;

    @Override
    public Pdf convert(MultipartFile multipartFile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String currentFilePath = request.getParameter("entity.file.id");
        if (currentFilePath != null && StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            return pdfRepository.getOne(Long.valueOf(currentFilePath));
        }

        LOG.info("New multipart file is being converted " + multipartFile.getOriginalFilename());
        try {
            String uploadDirectory = environment.getProperty("upload.dir") != null ? environment.getProperty("upload.dir") : "";
            File file = new File(uploadDirectory + multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            URI url = ftpTransferService.transferFile(file);
            Pdf pdf = new Pdf();
            pdf.setPath(url.toString());
            ArrayList<ChiroImage> images = new ArrayList<>();
            List<URI> imageUris = pdfService.convert(file);
            for(URI uri : imageUris){
                ChiroImage e = new ChiroImage();
                e.setPath(uri.toString());
                images.add(e);
                chiroImageRepository.save(e);
            }
            pdf.setImages(images);
            pdfRepository.save(pdf);
            return pdf;
        } catch (IOException e) {
            LOG.error("Something went wrong converting mulitipart file to output stream", e);
        }
        return null;
    }
}
