package be.sandervl.admin.services.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterServiceImpl implements TwitterService {

    @Autowired(required=false)
    private Twitter twitter;

    public TwitterServiceImpl() {

    }

    @Cacheable("twitterCache")
    public List<Status> getStatuses(Paging paging) {
        try {
            return twitter.getHomeTimeline(paging);
        } catch (TwitterException e) {
            return new ArrayList<Status>();
        }
    }

}
