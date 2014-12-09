package be.chiroelzestraat.business;

public class Entry {
    private int position, numberOfGames, wins, losses, positivePoints, negativePoints, rankingPoints;
    private String teamName;

    public Entry(int position, int numberOfGames, int wins, int losses, int positivePoints, int negativePoints, int rankingPoints, String teamName) {
        this.position = position;
        this.numberOfGames = numberOfGames;
        this.wins = wins;
        this.losses = losses;
        this.positivePoints = positivePoints;
        this.negativePoints = negativePoints;
        this.rankingPoints = rankingPoints;
        this.teamName = teamName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPositivePoints() {
        return positivePoints;
    }

    public void setPositivePoints(int positivePoints) {
        this.positivePoints = positivePoints;
    }

    public int getNegativePoints() {
        return negativePoints;
    }

    public void setNegativePoints(int negativePoints) {
        this.negativePoints = negativePoints;
    }

    public int getRankingPoints() {
        return rankingPoints;
    }

    public void setRankingPoints(int rankingPoints) {
        this.rankingPoints = rankingPoints;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

//    @Override
//    public String toString() {
//        return "Entry [position=" + position + ", numberOfGames=" + numberOfGames + ", teamName=" + teamName + "]";
//    }
}
