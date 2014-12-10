package be.chiroelzestraat.business;

import java.util.Date;

public class Game {

    private String team1, team2;
    private Date date;
    private int score1, score2;
    private Ranking.Type type;

    public void setType(Ranking.Type type) {
        this.type = type;
    }

    public Ranking.Type getType() {
        return type;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTeam2() {
        return team2;
    }

    public String getTeam1() {
        return team1;
    }

    public Date getDate() {
        return date;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
}
