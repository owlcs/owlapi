package uk.ac.manchester.cs.chainsaw;

import java.io.Serializable;

import javax.annotation.Nullable;

abstract class AbstractFastSet implements FastSet, Serializable {

    public static final int LIMIT = 5;

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        if (size() > 0) {
            b.append(get(0));
        }
        for (int i = 1; i < size(); i++) {
            b.append(", ").append(get(i));
        }
        b.append(']');
        return b.toString();
    }

    @Override
    public boolean equals(@Nullable Object arg0) {
        if (arg0 == null) {
            return false;
        }
        if (this == arg0) {
            return true;
        }
        if (arg0 instanceof FastSet) {
            FastSet f = (FastSet) arg0;
            if (f.size() != size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (get(i) != f.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (size() == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 0; j < size(); j++) {
            i += get(j);
            i *= 37;
        }
        return i;
    }
}
