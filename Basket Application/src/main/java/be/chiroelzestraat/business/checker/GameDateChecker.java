package be.chiroelzestraat.business.checker;

import be.chiroelzestraat.business.Game;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class GameDateChecker implements Checker<Game> {

    private Date date;

    public GameDateChecker(Date date) {
        this.date = date;
    }

    @Override
    public boolean check(Game obj) {
        return DateUtils.truncate(date,Calendar.DATE).equals(DateUtils.truncate(obj.getDate(), Calendar.DATE));
    }
}
