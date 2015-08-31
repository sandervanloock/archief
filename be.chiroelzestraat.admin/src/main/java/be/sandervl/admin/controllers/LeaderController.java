/*
 * Copyright 2014 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.sandervl.admin.controllers;

import be.sandervl.admin.business.Leader;
import be.sandervl.admin.services.entities.LeaderService;
import be.sandervl.admin.services.upload.TransferServiceManager;
import com.foreach.across.modules.adminweb.AdminWeb;
import com.foreach.across.modules.adminweb.annotations.AdminWebController;
import com.foreach.across.modules.adminweb.menu.AdminMenu;
import com.foreach.across.modules.adminweb.menu.EntityAdminMenu;
import com.foreach.across.modules.entity.controllers.entity.EntityControllerSupport;
import com.foreach.across.modules.web.menu.MenuFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

@AdminWebController
@RequestMapping(LeaderController.PATH)
public class LeaderController extends EntityControllerSupport{

    private static Logger LOG = LoggerFactory.getLogger(LeaderController.class);

    public static final String PATH = "/entities/leader";

    @Autowired
    private AdminWeb adminWeb;

    @Autowired
    private MenuFactory menuFactory;

    @Autowired
    private LeaderService leaderService;

    @Autowired
    private TransferServiceManager transferServiceManager;

    @RequestMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("entityMenu", this.menuFactory.buildMenu(new EntityAdminMenu(Leader.class)));
        model.addAttribute("existing", Boolean.valueOf(false));
        model.addAttribute("leader", new Leader());
        return "th/entities/leader/edit";
    }

    @RequestMapping("/{id}")
    public String editUser( @PathVariable("id") long id, AdminMenu adminMenu, Model model ) {
        Leader leader = leaderService.getLeaderById(id);
        Leader leaderDto = leader.toDto();

        model.addAttribute( "entityMenu", menuFactory.buildMenu( new EntityAdminMenu<Leader>( Leader.class, leader ) ) );
        model.addAttribute( "existing", true );
        model.addAttribute( "leader", leaderDto );

        return "th/admin/leader/edit";
    }

    @RequestMapping(value = {"/create", "/{id}"}, method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("leader") @Valid Leader leader,
                           BindingResult bindingResult,
                           RedirectAttributes re,
                           AdminMenu adminMenu,
                           Model model,
                           MultipartHttpServletRequest request) {
        MultipartFile multipartFile = request.getFile("avatar-upload");
        File avatar = new File(multipartFile.getOriginalFilename());
        try {
            avatar.createNewFile();
            FileOutputStream fos = new FileOutputStream(avatar);
            fos.write(multipartFile.getBytes());
            fos.close();
            URI test = transferServiceManager.uploadFile(avatar);
            leader.setAvatar(test.toString());
        }
        catch (IOException e) {
            LOG.error("Something went wrong converting mulitipart file to output stream",e.getMessage(),e.getStackTrace());
        }
        if (!bindingResult.hasErrors()) {
            leaderService.save(leader);

            re.addAttribute("leaderId", leader.getId());

            return adminWeb.redirect("/entities/leader/{leaderId}");
        } else {
            Leader existing = null;

            if (!leader.isNew()) {
                existing = leaderService.getLeaderById(leader.getId());
            }

            model.addAttribute("entityMenu", menuFactory.buildMenu(new EntityAdminMenu<Leader>(Leader.class, existing)));
            model.addAttribute("errors", bindingResult.getAllErrors());

            model.addAttribute("existing", true);
            model.addAttribute("leader", leader);

            return "th/admin/leader/edit";
        }
    }

    @Override
    protected String getDefaultViewName() {
        return "leader";
    }
}
