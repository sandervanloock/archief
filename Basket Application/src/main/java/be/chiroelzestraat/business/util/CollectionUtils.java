package be.chiroelzestraat.business.util;

import be.chiroelzestraat.business.Checker;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionUtils {

    // put this in some class
    public static <T> Collection<T> findAll(Collection<T> coll, Checker<T> chk) {
        LinkedList<T> l = new LinkedList<T>();
        for (T obj : coll) {
            if (chk.check(obj))
                l.add(obj);
        }
        return l;
    }
}
