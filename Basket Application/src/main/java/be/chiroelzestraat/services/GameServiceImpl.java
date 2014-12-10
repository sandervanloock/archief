package be.chiroelzestraat.services;

import be.chiroelzestraat.business.Game;
import be.chiroelzestraat.business.Ranking;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private Map<String, String> postData;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk.mm");

    @PostConstruct
    public void setup() {
        postData = new HashMap<String, String>();
        postData.put("ctl00$MainContent$tsm", "ctl00$MainContent$updPnl|ctl00$MainContent$cboWeekend");
        postData.put("__EVENTTARGET", "ctl00$MainContent$cboWeekend");
        postData.put("__ASYNCPOST", "true");
    }

    @Override
    public List<Game> getGames() {
        List<Game> result = new ArrayList<Game>();
        try {
            Document doc = Jsoup.connect("http://www.kavvv-basket.be/kavvv/results.aspx").get();
            String viewState = doc.select("#__VIEWSTATE").attr("value");
            String eventValidation = doc.select("#__EVENTVALIDATION").attr("value");
            String viewStateGenerator = doc.select("#__VIEWSTATEGENERATOR").attr("value");
            postData.put("__EVENTVALIDATION", eventValidation);
            postData.put("__VIEWSTATE", viewState);
            postData.put("__VIEWSTATEGENERATOR", viewStateGenerator);
            for (int weekend = 1; weekend <= 37; weekend++) {
                postData.put("ctl00$MainContent$cboWeekend", Integer.toString(weekend));
                doc = Jsoup.connect("http://www.kavvv-basket.be/kavvv/results.aspx")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
                        .data(postData)
                        .post();
                Elements rankingTable = doc.select("table");
                Iterator<Element> rows = rankingTable.select("tr").iterator();
                Ranking.Type rankingType = null;
                while (rows.hasNext()) {
                    Element row = rows.next();
                    Elements th = row.select("th");
                    if (th.size() == 1) {
                        String name = th.get(0).html();
                        rankingType = Ranking.Type.fromName(name);
                    }
                    if (rankingType != null) {
                        tryParse(row, result, rankingType);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(result, new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                if(o1.getType() != o2.getType()){
                    return o1.getType().compareTo(o2.getType());
                }
                return o1.getDate().compareTo(o2.getDate());
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        return result;
    }

    private void tryParse(Element row, List<Game> games, Ranking.Type type) {
        Game game = new Game();
        game.setType(type);
        Elements cells = row.select("td");
        if (cells.size() == 8) {
            try {
                String dateWithDay = cells.get(0).select("span").get(0).html();
                String time = cells.get(1).select("span").get(0).html();
                Date date = formatter.parse(dateWithDay.substring(3) + " " + time);
                game.setDate(date);
                String team1 = cells.get(2).select("span").get(0).html();
                game.setTeam1(team1);
                String team2 = cells.get(3).select("span").get(0).html();
                game.setTeam2(team2);
                int score1 = Integer.parseInt(cells.get(4).select("span").get(0).html());
                game.setScore1(score1);
                int score2 = Integer.parseInt(cells.get(6).select("span").get(0).html());
                game.setScore2(score2);
                games.add(game);
            } catch (ParseException e) {
//                e.printStackTrace();
            } catch(NumberFormatException e){
//                e.printStackTrace();
            }
        }
    }
}
