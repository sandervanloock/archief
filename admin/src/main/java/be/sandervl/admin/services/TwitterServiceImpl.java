package be.sandervl.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterServiceImpl implements TwitterService {

    @Autowired
    private Twitter twitter;

    public TwitterServiceImpl() {

    }

    public List<Status> getStatuses(Paging paging) {
        try {
            return twitter.getHomeTimeline(paging);
        } catch (TwitterException e) {
            return new ArrayList<Status>();
        }
    }

}
