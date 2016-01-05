package uk.ac.manchester.cs.chainsaw;

/* This file is part of the JFact DL reasoner
 Copyright 2011 by Ignazio Palmisano, Dmitry Tsarkov, University of Manchester
 This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version. 
 This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301 USA*/
/**
 * sorted set of ints.
 * 
 * @author ignazio
 */
public interface FastSet {

    /**
     * @param e
     *        add e
     */
    void add(int e);

    /**
     * @param c
     *        all elements to add
     */
    void addAll(FastSet c);

    /** clear the set */
    void clear();

    /**
     * @param o
     *        int to check
     * @return true if o is contained
     */
    boolean contains(int o);

    /**
     * @param c
     *        eleemnts to check
     * @return true if all elements are contained
     */
    boolean containsAll(FastSet c);

    /** @return true if the set is empty */
    boolean isEmpty();

    /**
     * @param o
     *        element to remove
     */
    void remove(int o);

    /** @return size of the set */
    int size();

    /** @return this set as a int array */
    int[] toIntArray();

    /**
     * @param f
     *        set to check
     * @return true if this and f intersect
     */
    boolean intersect(FastSet f);

    /**
     * @param i
     *        position
     * @return element at position i
     */
    int get(int i);

    /**
     * @param o
     *        position to remove element from
     */
    void removeAt(int o);

    /**
     * remove all elements between the two positions
     * 
     * @param i
     *        beginning position
     * @param end
     *        end position
     */
    void removeAll(int i, int end);

    /**
     * @param values
     *        elements to remove
     */
    void removeAllValues(int... values);

    /**
     * @param c
     *        eleemnts to check
     * @return true if any of the elements is contained
     */
    public boolean containsAny(FastSet c);
}
