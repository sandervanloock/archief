package be.sandervl.admin.services;

import be.sandervl.admin.business.ChiroGroup;
import com.google.api.services.calendar.model.Events;
import org.apache.commons.lang3.StringUtils;
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
            if(StringUtils.isNotEmpty(group.getCalendarId())){
                return calendarClient.events().list(group.getCalendarId()).execute();
            }
            return new Events();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
