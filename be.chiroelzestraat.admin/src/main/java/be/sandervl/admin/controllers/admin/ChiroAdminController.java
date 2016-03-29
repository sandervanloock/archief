package be.sandervl.admin.controllers.admin;

import com.foreach.across.core.annotations.Event;
import com.foreach.across.modules.adminweb.annotations.AdminWebController;
import com.foreach.across.modules.spring.security.infrastructure.services.CurrentSecurityPrincipalProxy;
import com.foreach.across.modules.user.UserModule;
import com.foreach.across.modules.user.business.User;
import com.foreach.across.modules.user.security.CurrentUserProxy;
import com.foreach.across.modules.web.events.BuildMenuEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sander on 24/03/2016.
 */
@AdminWebController
public class ChiroAdminController {

    @Autowired
    private CurrentSecurityPrincipalProxy currentSecurityPrincipalProxy;

    @Event
    public void buildMenuEvent(BuildMenuEvent event) {
        event.builder().group("/chiro", "Chiro").and()
                .item("/chiro/manage", "Chiro", "/").and()
                .move("/entities/ChiroAdminModule", "/chiro/manage");
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(ModelMap map) {

        map.addAttribute("currentUser", currentSecurityPrincipalProxy.getPrincipal(User.class));

        return "th/admin/dashboard";
    }
}
