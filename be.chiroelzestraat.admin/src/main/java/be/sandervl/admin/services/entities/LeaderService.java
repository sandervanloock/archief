package be.sandervl.admin.services.entities;

import be.sandervl.admin.business.Leader;

public interface LeaderService {
    Leader save(Leader leader);

    Leader getLeaderById(Long id);
}
