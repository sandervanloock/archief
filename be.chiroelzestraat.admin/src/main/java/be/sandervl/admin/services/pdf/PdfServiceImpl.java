package be.sandervl.admin.services.pdf;

import be.sandervl.admin.services.upload.imageserver.ImageServerTransferService;
import com.foreach.imageserver.core.ImageServerCoreModuleSettings;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    private static Logger LOG = LoggerFactory.getLogger(PdfService.class);

    @Autowired
    private ImageServerTransferService imageServerTransferService;

    @Autowired
    private Environment environment;

    /**
     * @See http://stackoverflow.com/questions/550129/export-pdf-pages-to-a-series-of-images-in-java for detailed explanations
     */
    @Override
    public List<URI> convert(File file) {
        List<URI> result = new ArrayList<>();
        if (!FilenameUtils.getExtension(file.getName()).equals("pdf")) {
            LOG.error(String.format("Convert is being called for a non-pdf file %s", file.getName()));
            return result;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            PDFFile pdffile = new PDFFile(buf);
            int numPgs = pdffile.getNumPages();
            for (int i = 0; i < numPgs; i++) {
                // draw the first page to an image
                PDFPage page = pdffile.getPage(i);
                // get the width and height for the doc at the default zoom
                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
                // generate the image
                Image img = page.getImage(rect.width, rect.height, // width & height
                        rect, // clip rect
                        null, // null for the ImageObserver
                        true, // fill background with white
                        true // block until drawing is done
                );
                // save it as a file
                BufferedImage bImg = toBufferedImage(img);
                File yourImageFile = new File(environment.getProperty(ImageServerCoreModuleSettings.IMAGE_STORE_FOLDER) + "/page_" + i + ".png");
                ImageIO.write(bImg, "png", yourImageFile);
                result.add(imageServerTransferService.transferFile(yourImageFile));
                yourImageFile.delete();
            }
        } catch (FileNotFoundException e) {
            LOG.error(String.format("PDF file not found %s", file), e);
        } catch (IOException e) {
            LOG.error(String.format("PDF file not found %s", file), e);
        }
        return result;
    }

    private BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent
        // Pixels
        boolean hasAlpha = hasAlpha(image);
        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    private boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }
        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }
        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }
}
