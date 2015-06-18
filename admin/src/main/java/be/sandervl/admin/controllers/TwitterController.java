package be.sandervl.admin.controllers;

import be.sandervl.admin.services.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TwitterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);

    @Autowired
    private TwitterService twitterService;

    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    public List<Status> leaders() {
        return twitterService.getStatuses();
    }

}
