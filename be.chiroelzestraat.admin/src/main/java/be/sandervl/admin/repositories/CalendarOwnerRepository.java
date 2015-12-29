package be.sandervl.admin.repositories;

import be.sandervl.admin.business.CalendarOwner;
import be.sandervl.admin.business.ChiroGroup;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;

/**
 * Created by sander on 07/06/2015.
 */
public interface CalendarOwnerRepository extends IdBasedEntityJpaRepository<CalendarOwner> {

    CalendarOwner findByName(String name);

}
