package be.chiroelzestraat.services;

import be.chiroelzestraat.business.Game;
import be.chiroelzestraat.business.Ranking;
import junit.framework.Assert;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestGameService {

    private GameService gameService;

    @Before
    public void setupTest() {
        gameService = new GameServiceImpl();
    }

    @Test
    public void testGetAllGames() {
        List<Game> actualGames = gameService.getGames();

        Assert.assertNotNull(actualGames);

    }

    @Test
    public void testGetOneGame() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        Date expectedDate = formatter.parse("31/10/2014 21:15:07");
        Ranking.Type expectedType = Ranking.Type.HEREN_3B;

        List<Game> actualGames = gameService.getGame(expectedDate, expectedType);

        Assert.assertNotNull(actualGames);
        for(Game game: actualGames){
            Assert.assertNotNull(game);
            Assert.assertEquals(expectedType, game.getType());
            Assert.assertEquals(DateUtils.truncate(expectedDate,Calendar.DATE), DateUtils.truncate(game.getDate(),Calendar.DATE));
        }
    }

    @Test
    public void testGetOneGameNullType() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        Date expectedDate = formatter.parse("31/10/2014 21:15:07");
        Ranking.Type expectedType = null;

        List<Game> actualGames = gameService.getGame(expectedDate, expectedType);

        Assert.assertNotNull(actualGames);
        Assert.assertTrue(actualGames.size()>2);
        for(Game game: actualGames){
            Assert.assertNotNull(game);
            Assert.assertNotNull(game.getType());
            Assert.assertEquals(DateUtils.truncate(expectedDate,Calendar.DATE), DateUtils.truncate(game.getDate(),Calendar.DATE));
        }
    }

}
