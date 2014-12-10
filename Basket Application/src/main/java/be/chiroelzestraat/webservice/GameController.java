package be.chiroelzestraat.webservice;

import be.chiroelzestraat.services.GameService;
import be.chiroelzestraat.business.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Game> getAllGames() {
        return gameService.getGames();
    }
}
