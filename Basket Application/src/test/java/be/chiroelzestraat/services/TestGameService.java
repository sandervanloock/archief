package be.chiroelzestraat.services;

import be.chiroelzestraat.business.Game;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestGameService {

    @Autowired
    private GameService gameService;



    @Test
    public void testGetRanking() {
        List<Game> actualGames = gameService.getGames();

        Assert.assertNotNull(actualGames);
    }

}
