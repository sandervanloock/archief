package be.sandervl.admin.business;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sander on 29/03/2016.
 */
@Entity
public class FunctionOwner extends SettableIdBasedEntity<FunctionOwner> {

    @Id
    @GeneratedValue(generator = "seq_function_owner_id")
    @GenericGenerator(
            name = "seq_function_owner_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_function_owner_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private Leader leader;

    @ManyToOne
    @JoinColumn(name = "chiroyear_id", nullable = false)
    private ChiroYear chiroYear;

    @ManyToOne
    @JoinColumn(name="function_id",nullable = false)
    private Function function;

    @Override
    public void setId(Long aLong) {
        this.id = aLong;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public ChiroYear getChiroYear() {
        return chiroYear;
    }

    public void setChiroYear(ChiroYear chiroYear) {
        this.chiroYear = chiroYear;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
