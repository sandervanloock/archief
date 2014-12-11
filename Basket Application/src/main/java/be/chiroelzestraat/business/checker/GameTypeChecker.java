package be.chiroelzestraat.business.checker;

import be.chiroelzestraat.business.Game;
import be.chiroelzestraat.business.Ranking;

public class GameTypeChecker implements Checker<Game> {

    private final Ranking.Type type;

    public GameTypeChecker(Ranking.Type type){
        this.type = type;
    }
    @Override
    public boolean check(Game obj) {
        return obj.getType().equals(type);
    }
}
