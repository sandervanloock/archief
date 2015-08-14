package be.sandervl.admin.services.api;

import be.sandervl.admin.business.ChiroGroup;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Events;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    @Autowired(required=false)
    private com.google.api.services.calendar.Calendar calendarClient;

    @Override
    @Cacheable(value="calendarCache")
    public Events getEventsFromGroup(ChiroGroup group, int amount) {
        try {
            if(StringUtils.isNotEmpty(group.getCalendarId())){
                DateTime timeMax = new DateTime(new Date());
                return calendarClient.events().list(group.getCalendarId()).setTimeMin(timeMax).setMaxResults(amount).execute();
            }
            return new Events();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
