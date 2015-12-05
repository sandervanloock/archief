package be.sandervl.test.admin.repositories;

import be.sandervl.admin.business.upload.image.UploadPicture;
import be.sandervl.admin.repositories.upload.image.UploadPictureRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;

@DatabaseSetup("classpath:uploadPictureData.xml")
public class UploadPictureRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UploadPictureRepository uploadPictureRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAllReturnsAll() {
        List<UploadPicture> actualPictures = uploadPictureRepository.findAll();

        assertThat(actualPictures.size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void findByDateBetweenOnlineDateAndOfflineDateAndActiveIsTrueReturnsResults() throws Exception {
        List<UploadPicture> actualPictures =
                uploadPictureRepository.findByDateBetweenOnlineDateAndOfflineDateAndActiveIsTrue();

        assertThat(actualPictures.size(),is(2));
    }
}