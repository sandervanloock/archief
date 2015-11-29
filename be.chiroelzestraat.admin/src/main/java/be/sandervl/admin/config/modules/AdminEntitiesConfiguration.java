package be.sandervl.admin.config.modules;

import be.sandervl.admin.business.*;
import be.sandervl.admin.services.entities.LeaderService;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.registry.EntityModelImpl;
import com.foreach.across.modules.entity.registry.MutableEntityRegistry;
import com.foreach.across.modules.entity.views.EntityListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.core.CrudInvoker;

import java.io.Serializable;

@AcrossDepends(required = EntityModule.NAME)
@Configuration
public class AdminEntitiesConfiguration implements EntityConfigurer {

    @Autowired
    private MutableEntityRegistry entityRegistry;

    @Autowired
    private LeaderService leaderService;

    @Override
    public void configure( EntitiesConfigurationBuilder configuration ) {

//        configuration.entity(CalendarOwner.class).hide();

        configuration.entity(FileUpload.class).hide();

        //LEADER CONFIGURATION
        configuration.entity(Leader.class).listView(EntityListView.VIEW_NAME).properties(
                "firstName",
                "lastName"
        );

        configuration.entity(Leader.class).label("firstName");

        //CHIROGROUP CONFIGURATION
        configuration.entity(ChiroGroup.class).listView(EntityListView.VIEW_NAME).properties(
                "name"
        );

        configuration.entity(ChiroGroup.class).label("name");

        //LEADERSHIP CONFIGURATION
        configuration.entity(LeaderShip.class).listView(EntityListView.VIEW_NAME).properties(
                "leader.firstName",
                "leader.lastName",
                "chiroGroup.name",
                "chiroYear.from_year",
                "chiroYear.to_year"
        );

        configuration.entity(LeaderShip.class).label("leader");

        //UPLOAD PICTURE CONFIGURATION
        configuration.entity(UploadPicture.class).listView(EntityListView.VIEW_NAME).properties(
                "name",
                "online_date",
                "offline_date",
                "active"
        );

        configuration.entity(UploadPicture.class).label("name");

        //CHIRO YEAR CONFIGURATION
        configuration.entity(ChiroYear.class).listView(EntityListView.VIEW_NAME).properties(
                "from_year",
                "to_year",
                "theme"
        );

        configuration.entity(ChiroYear.class).label("from_year");

        // Use the LeaderService for persisting Leader - as that one takes care of avatar handling
        EntityModelImpl userModel = (EntityModelImpl) entityRegistry.getMutableEntityConfiguration( Leader.class )
                .getEntityModel();
        userModel.setCrudInvoker( new CrudInvoker<Leader>()
        {
            @Override
            public Leader invokeSave( Leader object ) {
                return leaderService.save( object );
            }

            @Override
            public Leader invokeFindOne( Serializable id ) {
                return leaderService.getLeaderById((Long) id);
            }
        } );
    }
}
