package be.sandervl.admin.services;

import be.sandervl.admin.business.ChiroGroup;
import com.google.api.services.calendar.model.Events;

import java.util.Date;

public interface GoogleCalendarService {

    Events getEventsFromGroup(ChiroGroup group, int amount);
}
