package be.sandervl.admin.business;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class LeaderShip extends SettableIdBasedEntity<LeaderShip> {

    @Id
    @GeneratedValue(generator = "seq_leadership_id")
    @GenericGenerator(
            name = "seq_leadership_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_leadership_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private Leader leader;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "chirogroup_id", nullable = false)
    private ChiroGroup chiroGroup;

    @ManyToOne
    @JoinColumn(name = "chiroyear_id", nullable = true)
    private ChiroYear chiroYear;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public ChiroGroup getChiroGroup() {
        return chiroGroup;
    }

    public void setChiroGroup(ChiroGroup chiroGroup) {
        this.chiroGroup = chiroGroup;
    }

    public ChiroYear getChiroYear() {
        return chiroYear;
    }

    public void setChiroYear(ChiroYear chiroYear) {
        this.chiroYear = chiroYear;
    }
}

