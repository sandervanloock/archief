package be.sandervl.admin.services;

import twitter4j.Paging;
import twitter4j.Status;

import java.util.List;

public interface TwitterService {

    List<Status> getStatuses(Paging paging);
}