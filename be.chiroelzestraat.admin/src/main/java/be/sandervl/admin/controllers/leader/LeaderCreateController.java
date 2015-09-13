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
package be.sandervl.admin.controllers.leader;

import be.sandervl.admin.business.FileUpload;
import be.sandervl.admin.business.Leader;
import be.sandervl.admin.services.entities.LeaderService;
import be.sandervl.admin.services.upload.TransferServiceManager;
import com.foreach.across.modules.adminweb.AdminWeb;
import com.foreach.across.modules.adminweb.annotations.AdminWebController;
import com.foreach.across.modules.adminweb.menu.AdminMenu;
import com.foreach.across.modules.adminweb.menu.EntityAdminMenu;
import com.foreach.across.modules.entity.controllers.EntityViewRequest;
import com.foreach.across.modules.entity.controllers.ViewRequestValidator;
import com.foreach.across.modules.entity.controllers.entity.EntityControllerSupport;
import com.foreach.across.modules.entity.registry.EntityConfiguration;
import com.foreach.across.modules.entity.registry.EntityConfigurationImpl;
import com.foreach.across.modules.entity.registry.EntityRegistry;
import com.foreach.across.modules.entity.views.EntityFormView;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.web.menu.MenuFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

@AdminWebController
@RequestMapping(LeaderCreateController.PATH)
public class LeaderCreateController extends EntityControllerSupport{

    private static Logger LOG = LoggerFactory.getLogger(LeaderCreateController.class);

    public static final String PATH = "/entities/leader/create";

    @Autowired
    private AdminWeb adminWeb;

    @Autowired
    private MenuFactory menuFactory;

    @Autowired
    private LeaderService leaderService;

    @Autowired
    private TransferServiceManager transferServiceManager;

    @Autowired
    protected EntityRegistry entityRegistry;

    @Autowired
    private ViewRequestValidator viewRequestValidator;

    @ModelAttribute(VIEW_REQUEST)
    public Object buildViewRequest(
            NativeWebRequest request,
            ModelMap model ) {

        EntityConfiguration<Leader> entityConfiguration = entityRegistry.getEntityConfiguration(Leader.class);
        return super.buildViewRequest( entityConfiguration, true, true, null, request, model );
    }

    @InitBinder(VIEW_REQUEST)
    @Override
    protected void initBinder( EntityConfiguration<?> nullableEntityConfiguration,
                               WebRequest request,
                               WebDataBinder binder ) {
        request.setAttribute(  EntityViewRequest.class.getName() + ".DataBinder", binder, RequestAttributes.SCOPE_REQUEST );

        EntityConfiguration<Leader> entityConfiguration = entityRegistry.getEntityConfiguration(Leader.class);
        binder.setMessageCodesResolver( entityConfiguration.getEntityMessageCodeResolver() );
        binder.setValidator( viewRequestValidator );

        initViewFactoryBinder( request );
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showCreateEntityForm(
            @ModelAttribute(VIEW_REQUEST) EntityViewRequest viewRequest,
            NativeWebRequest request,
            ModelMap model ) {
        EntityConfiguration<Leader> entityConfiguration = entityRegistry.getEntityConfiguration(Leader.class);
        EntityView view = viewRequest.createView( model );

        view.setEntityMenu( menuFactory.buildMenu( new EntityAdminMenu<>( entityConfiguration.getEntityType() ) ) );

        if ( view.getPageTitle() == null ) {
            view.setPageTitle( view.getEntityMessages().createPageTitle() );
        }

        return "th/admin/leader/edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(VIEW_REQUEST) @Valid Leader leader,
                           BindingResult bindingResult,
                           RedirectAttributes re,
                           AdminMenu adminMenu,
                           Model model,
                           MultipartHttpServletRequest request) {
        MultipartFile multipartFile = request.getFile("entity.avatar");
        try {
            File avatar = new File(multipartFile.getOriginalFilename());
            avatar.createNewFile();
            FileOutputStream fos = new FileOutputStream(avatar);
            fos.write(multipartFile.getBytes());
            fos.close();
            URI test = transferServiceManager.uploadFile(avatar);
            FileUpload fileUpload = new FileUpload();
            fileUpload.setPath(test.toString());
            leader.setAvatar(fileUpload);
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
        return EntityFormView.CREATE_VIEW_NAME;
    }
}
