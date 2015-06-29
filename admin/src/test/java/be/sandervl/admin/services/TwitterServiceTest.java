package be.sandervl.admin.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
