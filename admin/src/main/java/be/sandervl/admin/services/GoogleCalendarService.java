package be.sandervl.admin.services;

import be.sandervl.admin.business.ChiroGroup;
import com.google.api.services.calendar.model.Events;

public interface GoogleCalendarService {

    Events getEventsFromGroup(ChiroGroup group);
}
