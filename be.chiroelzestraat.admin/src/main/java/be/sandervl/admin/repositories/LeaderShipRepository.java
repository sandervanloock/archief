package be.sandervl.admin.repositories;

import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.business.Leader;
import be.sandervl.admin.business.LeaderShip;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by sander on 07/06/2015.
 */
public interface LeaderShipRepository extends IdBasedEntityJpaRepository<LeaderShip> {

    public Set<LeaderShip> findByChiroGroup(ChiroGroup group);

}
