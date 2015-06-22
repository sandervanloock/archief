package be.sandervl.admin.controllers;

import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.services.GoogleCalendarService;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService calendarService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public Events leaders() {
        return calendarService.getEventsFromGroup(ChiroGroup.ASPIRANTEN);
    }
}
