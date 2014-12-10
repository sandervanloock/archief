package be.chiroelzestraat.business;

import java.util.ArrayList;
import java.util.List;

public class Ranking {

    private Type type;
    private String name;

    private List<Entry> entriesList;

    public Ranking() {
        this.entriesList = new ArrayList<Entry>();
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Entry> getEntriesList() {
        return entriesList;
    }

    public void setEntriesList(List<Entry> entriesList) {
        this.entriesList = entriesList;
    }

    public void addEntry(Entry entry) {
        entriesList.add(entry);
    }

    public boolean containsTeam(String teamName) {
        boolean result = false;
        for (Entry entry : entriesList) {
            if (entry.getTeamName().equals(teamName)) {
                return true;
            }
        }
        return result;
    }

    public static enum Type {
        HEREN_3B("Heren 3B"),
        DAMES_1("Dames 1"),
        DAMES_2("Dames 2"),
        HEREN_1("Heren 1"),
        HEREN_2A("Heren 2A"),
        HEREN_2B("Heren 2B"),
        HEREN_3A("Heren 3A"),
        HEREN_4A("Heren 4A"),
        HEREN_4B("Heren 4B"),
        HEREN_ERE("Heren Ere");

        Type(String cleanName) {
            this.cleanName = cleanName;
        }

        private String cleanName;

        public static Type fromName(String b) {
            for (Ranking.Type type : Type.values()) {
                if (type.cleanName.equals(b)) {
                    return type;
                }
            }
            return null;
        }

        public String getCleanName() {
            return cleanName;
        }

        public void setCleanName(String cleanName) {
            this.cleanName = cleanName;
        }

        @Override
        public String toString() {
            return this.cleanName;
        }
    }
}
