package be.chiroelzestraat.services;

import be.chiroelzestraat.business.Ranking;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestScoreService {

    private RankingService rankingService;

    @Before
    public void setupTests(){
         rankingService = new RankingServiceImpl();
    }

    @Test
    public void testGetRanking(){
        List<Ranking> actualRankings = rankingService.getRankings();

        Assert.assertNotNull(actualRankings);
        Assert.assertEquals(10, actualRankings.size());

        Assert.assertTrue(actualRankings.get(0).containsTeam("Gulle Trakteert Dames"));
        Assert.assertTrue(actualRankings.get(1).containsTeam("Dames BBC Putte"));
        Assert.assertTrue(actualRankings.get(2).containsTeam("Shoot"));
        Assert.assertTrue(actualRankings.get(3).containsTeam("Flam"));
        Assert.assertTrue(actualRankings.get(4).containsTeam("Gembo"));
        Assert.assertTrue(actualRankings.get(5).containsTeam("Bushwackers"));
        Assert.assertTrue(actualRankings.get(6).containsTeam("Klavermannen"));
        Assert.assertTrue(actualRankings.get(7).containsTeam("Greyhoundz"));
        Assert.assertTrue(actualRankings.get(8).containsTeam("Musketiers"));
        Assert.assertTrue(actualRankings.get(9).containsTeam("Dageraad"));
    }

}
