package be.sandervl.admin.services.api;

import be.sandervl.admin.business.CalendarOwner;
import com.google.api.services.calendar.model.Events;

public interface GoogleCalendarService {

    Events getEventsFromGroup(CalendarOwner group, int amount);
}
