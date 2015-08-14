package be.sandervl.admin.services;

import be.sandervl.admin.services.api.TwitterService;
import be.sandervl.admin.services.api.TwitterServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.*;

import java.util.List;

import static org.mockito.Mockito.verify;

public class TwitterServiceTest {

    @InjectMocks private TwitterService twitterService;

    @Mock
    private Twitter twitter;

    @Before
    public void setTup() {
        this.twitterService = new TwitterServiceImpl();

        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testGetStatuses() throws TwitterException {
        Paging paging = new Paging();
        paging.setCount(3);
        List<Status> actualStatuses = twitterService.getStatuses(paging);
        verify(twitter).getHomeTimeline(paging);
    }


}
