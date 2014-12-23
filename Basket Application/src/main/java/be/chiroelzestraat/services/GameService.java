package be.chiroelzestraat.services;

import be.chiroelzestraat.business.Game;
import be.chiroelzestraat.business.Ranking;

import java.util.Date;
import java.util.List;

public interface GameService {
    List<Game> getGames();

    List<Game> getSpecificGames(Date date, Ranking.Type type);
}
