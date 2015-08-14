package be.sandervl.admin.repositories;

import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.business.Leader;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by sander on 07/06/2015.
 */
public interface LeaderRepository extends IdBasedEntityJpaRepository<Leader> {

    List<Leader> findAll();
}
