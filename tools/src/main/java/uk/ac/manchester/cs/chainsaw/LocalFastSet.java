package uk.ac.manchester.cs.chainsaw;

import java.io.Serializable;
import java.util.BitSet;
import java.util.stream.IntStream;

/**
 * @author ignazio
 */
public class LocalFastSet implements FastSet, Serializable {

    private BitSet pos = new BitSet();

    private static int asPositive(int p) {
        return p >= 0 ? 2 * p : 1 - 2 * p;
    }

    @Override
    public int[] toIntArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAt(int o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAllValues(int... values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(int i, int end) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(int o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean intersect(FastSet f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int get(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAny(FastSet c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(FastSet c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(int o) {
        return pos.get(asPositive(o));
    }

    @Override
    public void clear() {
        pos.clear();
    }

    @Override
    public void addAll(FastSet c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(int e) {
        int asPositive = asPositive(e);
        if (!pos.get(asPositive)) {
            pos.set(asPositive);
            return true;
        }
        return false;
    }

    @Override
    public void completeSet(int value) {
        IntStream.range(0, value + 1).forEach(pos::set);
    }
}
