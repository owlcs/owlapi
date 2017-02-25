package uk.ac.manchester.cs.chainsaw;

/**
 * sorted set of ints.
 *
 * @author ignazio
 */
public interface FastSet {

    /**
     * @param e add e
     * @return true if set modified
     */
    boolean add(int e);

    /**
     * @param c all elements to add
     */
    void addAll(FastSet c);

    /**
     * clear the set
     */
    void clear();

    /**
     * @param o int to check
     * @return true if o is contained
     */
    boolean contains(int o);

    /**
     * @param c eleemnts to check
     * @return true if all elements are contained
     */
    boolean containsAll(FastSet c);

    /**
     * @return true if the set is empty
     */
    boolean isEmpty();

    /**
     * @param o element to remove
     */
    void remove(int o);

    /**
     * @return size of the set
     */
    int size();

    /**
     * @return this set as a int array
     */
    int[] toIntArray();

    /**
     * @param f set to check
     * @return true if this and f intersect
     */
    boolean intersect(FastSet f);

    /**
     * @param i position
     * @return element at position i
     */
    int get(int i);

    /**
     * @param o position to remove element from
     */
    void removeAt(int o);

    /**
     * remove all elements between the two positions
     *
     * @param i beginning position
     * @param end end position
     */
    void removeAll(int i, int end);

    /**
     * @param values elements to remove
     */
    void removeAllValues(int... values);

    /**
     * @param c eleemnts to check
     * @return true if any of the elements is contained
     */
    boolean containsAny(FastSet c);

    /**
     * @param value value
     */
    void completeSet(int value);
}
