package be.sandervl.test.admin.services.pdf;

import be.sandervl.admin.services.pdf.PdfService;
import be.sandervl.admin.services.pdf.PdfServiceImpl;
import be.sandervl.admin.services.upload.imageserver.ImageServerTransferService;
import com.foreach.imageserver.core.ImageServerCoreModuleSettings;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Created by sander on 16/02/2016.
 */
public class PdfServiceImplTest {

    private ImageServerTransferService imageServerTransferService;
    private Environment environment;

    private PdfService pdfService;

    @Before
    public void setUp() throws Exception {
        imageServerTransferService = mock(ImageServerTransferService.class);
        environment = mock(Environment.class);

        pdfService = new PdfServiceImpl();

        setField(pdfService, "imageServerTransferService", imageServerTransferService);
        setField(pdfService, "environment", environment);

        when(imageServerTransferService.transferFile(any(File.class))).thenReturn(URI.create(""));
        when(environment.getProperty(ImageServerCoreModuleSettings.IMAGE_STORE_FOLDER)).thenReturn("C:/Projects/chiro-site/be.chiroelzestraat.admin/uploads");
    }


    @Test
    public void convertImageShouldReturnNothing() throws Exception {
        File image = new ClassPathResource("DSCF1215.JPG").getFile();
        List<URI> actualUris = pdfService.convert(image);

        assertEquals(0, actualUris.size());

        verify(imageServerTransferService, never()).transferFile(any(File.class));

    }

    @Test
    public void convert() throws Exception {
        File image = new ClassPathResource("test.pdf").getFile();
        List<URI> actualUris = pdfService.convert(image);

        assertEquals(24, actualUris.size());

        verify(imageServerTransferService, times(24)).transferFile(any(File.class));

    }
}