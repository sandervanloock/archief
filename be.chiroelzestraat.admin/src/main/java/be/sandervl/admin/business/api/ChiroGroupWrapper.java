package be.sandervl.admin.business.api;

import be.sandervl.admin.business.ChiroGroup;
import be.sandervl.admin.business.Leader;

import java.util.Set;

/**
 * Created by sander on 03/08/2015.
 */
public class ChiroGroupWrapper {
    private ChiroGroup chiroGroup;
    private Set<Leader> leaders;

    public ChiroGroup getChiroGroup() {
        return chiroGroup;
    }

    public void setChiroGroup(ChiroGroup chiroGroup) {
        this.chiroGroup = chiroGroup;
    }

    public Set<Leader> getLeaders() {
        return leaders;
    }

    public void setLeaders(Set<Leader> leaders) {
        this.leaders = leaders;
    }
}
