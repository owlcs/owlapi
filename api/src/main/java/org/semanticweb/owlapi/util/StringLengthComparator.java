package org.semanticweb.owlapi.util;

import java.io.Serializable;
import java.util.Comparator;

/** String comparator that takes length into account before natural ordering. */
public class StringLengthComparator implements Comparator<String>, Serializable {

    private static final long serialVersionUID = 30406L;

    @Override
    public int compare(String o1, String o2) {
        int diff = o1.length() - o2.length();
        if (diff != 0) {
            return diff;
        }
        return o1.compareTo(o2);
    }
}
