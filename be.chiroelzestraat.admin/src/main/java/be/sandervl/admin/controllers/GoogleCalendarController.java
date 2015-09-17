package be.sandervl.admin.controllers;

import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.repositories.ChiroGroupRepository;
import be.sandervl.admin.services.api.GoogleCalendarService;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService calendarService;

    @Autowired
    private ChiroGroupRepository chiroGroupRepository;

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public Events getEventFromAllGroups() {
        Events result = new Events();
        result.setItems(new ArrayList<Event>());
        for(ChiroGroup groups: chiroGroupRepository.findAll()){
            List<Event> items = calendarService.getEventsFromGroup(groups, Integer.MAX_VALUE).getItems();
            if(items!=null){
                result.getItems().addAll(items);
            }
        }
        return result;
    }

    @RequestMapping(value = "/event/{group}", method = RequestMethod.GET)
    public Events getEventFromGroup(
            @PathVariable("group") String groupName
    ) {
        ChiroGroup group = chiroGroupRepository.findByName(groupName.toUpperCase());
        return calendarService.getEventsFromGroup(group,Integer.MAX_VALUE);
    }
}