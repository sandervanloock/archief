package be.sandervl.admin.services.entities;

import be.sandervl.admin.business.Leader;
import be.sandervl.admin.repositories.LeaderRepository;
import be.sandervl.admin.services.upload.TransferService;
import be.sandervl.admin.services.upload.TransferServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by sander on 11/08/2015.
 */
@Service
public class LeaderServiceImpl implements LeaderService {
    private static Logger LOG = LoggerFactory.getLogger(LeaderService.class);

    @Autowired
    private LeaderRepository leaderRepository;

    @Override
    public Leader save(Leader leader) {
        return leaderRepository.save(leader);
    }

    @Override
    public Leader getLeaderById(Long id) {
        return leaderRepository.findOne(id);
    }
}
