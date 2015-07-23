package be.sandervl.admin.services;

import be.sandervl.admin.business.ChiroGroup;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Events;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    @Autowired
    private com.google.api.services.calendar.Calendar calendarClient;

    @Override
    public Events getEventsFromGroup(ChiroGroup group, Date startDate) {
        try {
            if(StringUtils.isNotEmpty(group.getCalendarId())){
                DateTime timeMax = new DateTime(startDate);
                return calendarClient.events().list(group.getCalendarId()).setTimeMax(timeMax).setMaxResults(1).execute();
            }
            return new Events();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
