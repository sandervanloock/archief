package be.sandervl.admin.controllers;

import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.business.Leader;
import be.sandervl.admin.business.LeaderShip;
import be.sandervl.admin.business.api.ChiroGroupWrapper;
import be.sandervl.admin.repositories.ChiroGroupRepository;
import be.sandervl.admin.repositories.LeaderShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/group")
public class ChiroGroupController {

    @Autowired
    private ChiroGroupRepository chiroGroupRepository;

    @Autowired
    private LeaderShipRepository leaderShipRepository;

    @RequestMapping(value = "/{group}", method = RequestMethod.GET)
    public ChiroGroupWrapper getGroup(@PathVariable("group") String group){
        ChiroGroup chiroGroup = chiroGroupRepository.findByName(group.toUpperCase());
        Set<LeaderShip> leaderShips = leaderShipRepository.findByChiroGroup(chiroGroup);
        ChiroGroupWrapper result = new ChiroGroupWrapper();
        result.setChiroGroup(chiroGroup);
        Set<Leader> leaders = new HashSet<Leader>();
        for(LeaderShip leaderShip: leaderShips){
            leaders.add(leaderShip.getLeader());
        }
        result.setLeaders(leaders);
        return result;
    }
}
