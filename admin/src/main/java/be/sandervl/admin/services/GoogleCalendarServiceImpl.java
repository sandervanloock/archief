package be.sandervl.admin.services;

import be.sandervl.admin.business.ChiroGroup;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    @Autowired
    private com.google.api.services.calendar.Calendar calendarClient;

    @Override
    public Events getEventsFromGroup(ChiroGroup group) {
        try {
            return calendarClient.events().list("52on50vspdubfmmbdenbamr0k0@group.calendar.google.com").execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
