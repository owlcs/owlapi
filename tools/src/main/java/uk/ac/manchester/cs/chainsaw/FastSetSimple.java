package uk.ac.manchester.cs.chainsaw;

/* This file is part of the JFact DL reasoner
 Copyright 2011 by Ignazio Palmisano, Dmitry Tsarkov, University of Manchester
 This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version. 
 This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301 USA*/
import java.util.Arrays;

import javax.annotation.Nullable;

/** @author ignazio */
public class FastSetSimple implements FastSet {

    private static final int limit = 5;

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        if (size() > 0) {
            b.append(get(0));
        }
        for (int i = 1; i < size(); i++) {
            b.append(',');
            b.append(' ');
            b.append(get(i));
        }
        b.append(']');
        return b.toString();
    }

    protected @Nullable int[] values;
    protected int size = 0;
    protected static final int defaultSize = 16;

    @SuppressWarnings("null")
    protected int insertionIndex(int key) {
        assert values != null;
        if (key < values[0]) {
            return -1;
        }
        if (key > values[size - 1]) {
            return -size - 1;
        }
        int lowerbound = 0;
        if (size < limit) {
            for (; lowerbound < size; lowerbound++) {
                if (values[lowerbound] > key) {
                    return -lowerbound - 1;
                }
                if (values[lowerbound] == key) {
                    return lowerbound;
                }
            }
            return -lowerbound - 1;
        }
        int upperbound = size - 1;
        while (lowerbound <= upperbound) {
            int delta = upperbound - lowerbound;
            int intermediate = lowerbound + delta / 2;
            if (values[intermediate] == key) {
                return intermediate;
            }
            if (values[intermediate] < key) {
                lowerbound = intermediate + 1;
            } else {
                upperbound = intermediate - 1;
            }
        }
        return -lowerbound - 1;
    }

    /** default constructor */
    public FastSetSimple() {}

    /**
     * @param c1
     *        c1
     * @param c2
     *        c2
     */
    @SuppressWarnings("null")
    public FastSetSimple(FastSetSimple c1, FastSetSimple c2) {
        values = new int[(c1.size + c2.size) / defaultSize * defaultSize + defaultSize];
        int i = 0;
        int j = 0;
        int index = 0;
        for (; i < c1.size && j < c2.size; index++) {
            if (c1.values[i] < c2.values[j]) {
                values[index] = c1.values[i];
                i++;
            } else if (c2.values[j] < c1.values[i]) {
                values[index] = c2.values[j];
                j++;
            }
            // the result must be a set: equal elements advance both indexes
            else if (c1.values[i] == c2.values[j]) {
                values[index] = c1.values[i];
                i++;
                j++;
            }
        }
        // remaining elements in one set or the other
        if (i < c1.size) {
            for (; i < c1.size; i++, index++) {
                values[index] = c1.values[i];
            }
            // new size here
            size = index;
        } else {
            for (; j < c2.size; j++, index++) {
                values[index] = c2.values[j];
            }
            // new size here
            size = index;
        }
    }

    @Override
    public int get(int i) {
        if (values != null) {
            return values[i];
        }
        throw new IllegalArgumentException("Illegal argument " + i + ": no such element");
    }

    protected void init() {
        values = new int[defaultSize];
        size = 0;
    }

    @SuppressWarnings("null")
    @Override
    public void add(int e) {
        int pos = -1;
        if (values == null) {
            init();
            // pos stays at -1, in an empty set that's the place to start - it
            // will become 0
        } else {
            // else find the right place
            pos = insertionIndex(e);
        }
        if (pos > -1) {
            return;
        }
        int i = -pos - 1;
        // i is now the insertion point
        if (i >= values.length || size >= values.length) {
            // no space left, increase
            values = Arrays.copyOf(values, values.length + defaultSize);
        }
        // size ensured, shift and insert now
        for (int j = size - 1; j >= i; j--) {
            values[j + 1] = values[j];
        }
        values[i] = e;
        // increase used size
        size++;
    }

    @SuppressWarnings("null")
    @Override
    public void addAll(FastSet c) {
        if (c.isEmpty()) {
            return;
        }
        // merge two sorted arrays: how bad can it be?
        if (values == null) {
            // extreme case: just copy the other set
            values = Arrays.copyOf(((FastSetSimple) c).values, c.size());
            size = c.size();
            return;
        }
        int newsize = size + c.size();
        int[] merge = new int[newsize / defaultSize * defaultSize + defaultSize];
        int i = 0;
        int j = 0;
        int index = 0;
        for (; i < size() && j < c.size(); index++) {
            if (values[i] < c.get(j)) {
                merge[index] = values[i];
                i++;
            } else if (c.get(j) < values[i]) {
                merge[index] = c.get(j);
                j++;
            }
            // the result must be a set: equal elements advance both indexes
            else if (values[i] == c.get(j)) {
                merge[index] = values[i];
                i++;
                j++;
            }
        }
        // remaining elements in one set or the other
        if (i < size()) {
            for (; i < size(); i++, index++) {
                merge[index] = values[i];
            }
            // new size here
            newsize = index;
        } else {
            for (; j < c.size(); j++, index++) {
                merge[index] = c.get(j);
            }
            // new size here
            newsize = index;
        }
        values = merge;
        size = newsize;
    }

    @Override
    public void clear() {
        values = null;
        size = 0;
    }

    @Override
    public boolean contains(int o) {
        if (values != null) {
            int i = insertionIndex(o);
            boolean toReturn = i > -1;
            return toReturn;
        }
        return false;
    }

    @Override
    public boolean containsAll(FastSet c) {
        if (c.isEmpty()) {
            return true;
        }
        if (isEmpty()) {
            return false;
        }
        if (c.size() > size) {
            return false;
        }
        if (get(0) > c.get(0) || get(size - 1) < c.get(c.size() - 1)) {
            // c boundaries are outside this set
            return false;
        }
        int i = 0;
        int j = 0;
        int currentValue;
        while (j < c.size()) {
            currentValue = c.get(j);
            boolean found = false;
            while (i < size) {
                if (get(i) == currentValue) {
                    // found the current value, next element in c - increase j
                    found = true;
                    break;
                }
                if (get(i) > currentValue) {
                    // found a value larger than the value it's looking for - c
                    // is not contained
                    return false;
                }
                // get(i) is < than current value: check next i
                i++;
            }
            if (!found) {
                // finished exploring this and currentValue was not found - it
                // happens if currentValue < any element in this set
                return false;
            }
            j++;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return values == null;
    }

    @Override
    public boolean containsAny(FastSet c) {
        if (c.isEmpty() || size == 0) {
            return false;
        }
        int i = 0;
        int j = 0;
        int currentValue;
        while (j < c.size()) {
            currentValue = c.get(j);
            while (i < size) {
                if (get(i) == currentValue) {
                    // found the current value, next element in c - increase j
                    return true;
                }
                if (get(i) > currentValue) {
                    // found a value larger than the value it's looking for - c
                    // is not contained
                    break;
                }
                // get(i) is < than current value: check next i
                i++;
            }
            j++;
        }
        return false;
    }

    @Override
    public void remove(int o) {
        if (values == null) {
            return;
        }
        int i = insertionIndex(o);
        removeAt(i);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] toIntArray() {
        if (values == null) {
            return new int[0];
        }
        return Arrays.copyOf(values, size);
    }

    @Override
    public boolean intersect(FastSet f) {
        return containsAny(f);
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
            FastSet arg = (FastSet) arg0;
            if (size != arg.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (arg.get(i) != get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @SuppressWarnings("null")
    @Override
    public void removeAt(int i) {
        if (values == null) {
            return;
        }
        if (i > -1 && i < size) {
            if (size == 1) {
                values = null;
                size = 0;
                return;
            }
            for (int j = i; j < size - 1; j++) {
                values[j] = values[j + 1];
            }
            size--;
        }
        if (size == 0) {
            values = null;
        }
    }

    @SuppressWarnings("null")
    @Override
    public void removeAll(int i, int end) {
        if (values == null) {
            return;
        }
        if (end < -1 || end < i || end > size || i < -1 || i > size) {
            throw new IllegalArgumentException("illegal arguments: " + i + " " + end
                + " size: " + size);
        }
        if (size == 1 || i == 0 && end == size) {
            values = null;
            size = 0;
            return;
        }
        if (end == size) {
            size = i;
        } else {
            int delta = end - i;
            for (int j = i; j < size - delta; j++) {
                values[j] = values[j + delta];
            }
            size -= delta;
        }
        if (size == 0) {
            values = null;
        }
    }

    @SuppressWarnings("null")
    @Override
    public void removeAllValues(int... vals) {
        if (values == null) {
            return;
        }
        if (vals.length == 1) {
            remove(vals[0]);
            return;
        }
        Arrays.sort(vals);
        int originalsize = size;
        for (int i = 0, j = 0; i < originalsize && j < vals.length; i++) {
            if (values[i] == vals[j]) {
                values[i] = Integer.MAX_VALUE;
                size--;
                j++;
            }
        }
        if (size == 0) {
            values = null;
        } else {
            Arrays.sort(values, 0, originalsize);
        }
    }
}
