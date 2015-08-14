package be.sandervl.admin.controllers;

import be.sandervl.admin.services.api.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Paging;
import twitter4j.Status;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TwitterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);

    @Autowired
    private TwitterService twitterService;

    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    public List<Status> getTweets(
            @RequestParam(value="amount",required = false) Integer amount
    ) {
        if(amount==null){
            amount=20;
        }
        Paging paging = new Paging();
        paging.setCount(amount);
        List<Status> statuses = twitterService.getStatuses(paging);

        return statuses;
    }

}
