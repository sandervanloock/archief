package be.sandervl.admin.controllers;

import be.sandervl.admin.business.Leader;
import be.sandervl.admin.repositories.LeaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderController.class);

    @Autowired
    private LeaderRepository leaderRepository;

    @RequestMapping(value = "/leader", method = RequestMethod.GET)
    public List<Leader> leaders() {
        return leaderRepository.findAll();
    }

    @RequestMapping(value = "/leader/{id}", method = RequestMethod.GET)
    public Leader leader(
            @PathVariable int id
    ) {
        return leaderRepository.findOne(Long.valueOf(id));
    }
}
