package be.chiroelzestraat.services;

import be.chiroelzestraat.business.Entry;
import be.chiroelzestraat.business.Ranking;
import org.springframework.cache.annotation.Cacheable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    @Override
    @Cacheable(value="rankingCache")
    public List<Ranking> getRankings() {
        List<Ranking> result = new ArrayList<Ranking>();
        try {
            Document doc = Jsoup.connect("http://www.kavvv-basket.be/kavvv/ranking.aspx").get();
            Elements rankingTable = doc.select(".main table");
            Iterator<Element> rows = rankingTable.select("tr").iterator();
            while (rows.hasNext()) {
                Element row = rows.next();
                Iterator<Element> cells = row.select("td").iterator();
                //search for ranking header
                while (cells.hasNext()) {
                    Element cell = cells.next();
                    if (cell.attr("colspan").equals("8")) {
                        String name = cell.select("b").html();
                        Ranking.Type rankingType = Ranking.Type.fromName(name);
                        if (rankingType != null) {
                            Ranking ranking = new Ranking();
                            ranking.setType(rankingType);
                            ranking.setName(name);
                            result.add(ranking);
                            break;
                        }
                    }
                }
                int size = result.size();
                if (size > 0) {
                    tryParse(row, result.get(size - 1));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void tryParse(Element row, Ranking ranking) {
        Elements cells = row.select("td");
        if (cells.size() == 8) {
            int position = Integer.parseInt(cells.get(0).html());
            String teamName = cells.get(1).html();
            int numberOfGames = Integer.parseInt(cells.get(2).html());
            int wins = Integer.parseInt(cells.get(3).html());
            int losses = Integer.parseInt(cells.get(4).html());
            int positivePoints = Integer.parseInt(cells.get(5).html());
            int negativePoints = Integer.parseInt(cells.get(6).html());
            int rankingPoints = Integer.parseInt(cells.get(7).html());
            ranking.addEntry(new Entry(position, numberOfGames, wins, losses, positivePoints, negativePoints, rankingPoints, teamName));
        }
    }
}
