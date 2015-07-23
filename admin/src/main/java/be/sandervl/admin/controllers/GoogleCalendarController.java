package be.sandervl.admin.controllers;

import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.services.GoogleCalendarService;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService calendarService;

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public Events getEventFromAllGroups() {
        Events result = new Events();
        result.setItems(new ArrayList<Event>());
        for(ChiroGroup groups: ChiroGroup.values()){
            List<Event> items = calendarService.getEventsFromGroup(groups, new Date()).getItems();
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
        return calendarService.getEventsFromGroup(ChiroGroup.valueOf(groupName.toUpperCase()),new Date());
    }
}
