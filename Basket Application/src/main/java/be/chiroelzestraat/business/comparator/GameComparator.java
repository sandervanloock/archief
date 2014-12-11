package be.chiroelzestraat.business.comparator;

import be.chiroelzestraat.business.Game;

import java.util.Comparator;

public class GameComparator implements Comparator<Game> {
    @Override
    public int compare(Game o1, Game o2) {
        if (o1.getType() != o2.getType()) {
            return o1.getType().compareTo(o2.getType());
        }
        return o1.getDate().compareTo(o2.getDate());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
