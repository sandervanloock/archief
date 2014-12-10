package be.chiroelzestraat.webservice;

import be.chiroelzestraat.services.RankingService;
import be.chiroelzestraat.services.RankingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:**/test-application-context.xml")
public class TestApiController {

    @Autowired
    private RankingService rankingService;

    MockMvc mockMvc;

    @Before
    public void init() {
        RankingController apiController = new RankingController();
        rankingService = new RankingServiceImpl();
        setField(apiController, "scoreService", rankingService);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    public void getAllRankings() throws Exception {
        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect((org.springframework.test.web.servlet.ResultMatcher) jsonPath("$.type", equals("Dames 1")));
    }

}
