package be.chiroelzestraat.webservice;

import be.chiroelzestraat.business.Ranking;
import be.chiroelzestraat.services.GameService;
import be.chiroelzestraat.business.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Game> getAllGames(
            @RequestParam(required=false, value="date") String date,
            @RequestParam(required=false, value="type") String type
    ) {
        try {
            return gameService.getGame(simpleDateFormat.parse(date), Ranking.Type.fromName(type));
        } catch (ParseException e) {
            return gameService.getGames();
        }
    }
}
