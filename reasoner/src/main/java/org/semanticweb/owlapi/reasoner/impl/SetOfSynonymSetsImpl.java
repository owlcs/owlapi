package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.reasoner.SetOfSynonymSets;
import org.semanticweb.owlapi.reasoner.SynonymSet;

import java.util.*;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Aug-2009
 *
 * An implementation of SetOfSynonymSets.  The implementation optimises the case where all of the
 * synonym sets are singletons to minimise storage and object creation overhead.
 */
public abstract class SetOfSynonymSetsImpl<E, S extends SynonymSet<E>> implements SetOfSynonymSets<E, S> {

    private Set<E> singletons = null;

    private Set<S> synonymSets = null;

    protected abstract S createSingleton(E e);

    /**
     * A convenience method that gets all of the synonyms contained in the synonym sets in this set.
     * @return The union of the synonyms contained in the synonyms sets in this set.
     */
    public Set<E> getFlattened() {
        if(singletons != null) {
            return singletons;
        }
        Set<E> result = new HashSet<E>();
        for(SynonymSet<E> synSet : synonymSets) {
            result.addAll(synSet);
        }
        return result;
    }

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     * @return the number of elements in this set (its cardinality).
     */
    public int size() {
        if(singletons != null) {
            return singletons.size();
        }
        return synonymSets.size();
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     * @return <tt>true</tt> if this set contains no elements.
     */
    public boolean isEmpty() {
        if(singletons != null) {
            return singletons.isEmpty();
        }
        return synonymSets.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.  More
     * formally, returns <tt>true</tt> if and only if this set contains an
     * element <code>e</code> such that <code>(o==null ? e==null :
     * o.equals(e))</code>.
     * @param o element whose presence in this set is to be tested.
     * @return <tt>true</tt> if this set contains the specified element.
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this set (optional).
     * @throws NullPointerException if the specified element is null and this
     *                              set does not support null elements (optional).
     */
    public boolean contains(Object o) {
        if(!(o instanceof SynonymSet)) {
            return false;
        }
        SynonymSet other = (SynonymSet) o;
        if(singletons != null) {
            return other.isSingleton() && singletons.contains(other.getSingletonElement());
        }
        else {
            return synonymSets.contains(o);
        }
    }

    /**
     * Returns an iterator over the elements in this set.  The elements are
     * returned in no particular order (unless this set is an instance of some
     * class that provides a guarantee).
     * @return an iterator over the elements in this set.
     */
    public Iterator<S> iterator() {
        if(singletons != null) {
            return new Iterator<S>() {

                Iterator<E> singletonsIterator = singletons.iterator();

                /**
                 * Returns <tt>true</tt> if the iteration has more elements. (In other
                 * words, returns <tt>true</tt> if <tt>next</tt> would return an element
                 * rather than throwing an exception.)
                 * @return <tt>true</tt> if the iterator has more elements.
                 */
                public boolean hasNext() {
                    return singletonsIterator.hasNext();
                }

                /**
                 * Returns the next element in the iteration.  Calling this method
                 * repeatedly until the {@link #hasNext()} method returns false will
                 * return each element in the underlying collection exactly once.
                 * @return the next element in the iteration.
                 * @throws java.util.NoSuchElementException
                 *          iteration has no more elements.
                 */
                public S next() {
                    E singleton = singletonsIterator.next();
                    return createSingleton(singleton);
                }

                /**
                 * Removes from the underlying collection the last element returned by the
                 * iterator (optional operation).  This method can be called only once per
                 * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
                 * the underlying collection is modified while the iteration is in
                 * progress in any way other than by calling this method.
                 * @throws UnsupportedOperationException if the <tt>remove</tt>
                 *                                       operation is not supported by this Iterator.
                 * @throws IllegalStateException         if the <tt>next</tt> method has not
                 *                                       yet been called, or the <tt>remove</tt> method has already
                 *                                       been called after the last call to the <tt>next</tt>
                 *                                       method.
                 */
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        else {
            return synonymSets.iterator();
        }
    }

    /**
     * Returns an array containing all of the elements in this set.
     * Obeys the general contract of the <tt>Collection.toArray</tt> method.
     * @return an array containing all of the elements in this set.
     */
    public Object[] toArray() {
        if(singletons != null) {
            // Make up
            Object [] result = new Object [singletons.size()];
            int i = 0;
            for(E o : singletons) {
                result[i] = createSingleton(o);
                i++;
            }
            return result;
        }
        else {
            return synonymSets.toArray();
        }
    }

    /**
     * Returns an array containing all of the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * Obeys the general contract of the
     * <tt>Collection.toArray(Object[])</tt> method.
     * @param a the array into which the elements of this set are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of this set.
     * @throws ArrayStoreException  the runtime type of a is not a supertype
     *                              of the runtime type of every element in this set.
     * @throws NullPointerException if the specified array is <tt>null</tt>.
     */
    public <T> T[] toArray(T[] a) {
        if(singletons != null) {
            T[] result;
            if(a.length == singletons.size()) {
                result = a;
            }
            else {
                result = a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), singletons.size());
            }
            int i = 0;
            for(E e : singletons) {
                result [i] = (T) createSingleton(e);
            }
            return result;
        }
        else {
            return synonymSets.toArray(a);
        }
    }

    /**
     * Adds the specified element to this set if it is not already present
     * (optional operation).  More formally, adds the specified element,
     * <code>o</code>, to this set if this set contains no element
     * <code>e</code> such that <code>(o==null ? e==null :
     * o.equals(e))</code>.  If this set already contains the specified
     * element, the call leaves this set unchanged and returns <tt>false</tt>.
     * In combination with the restriction on constructors, this ensures that
     * sets never contain duplicate elements.<p>
     * The stipulation above does not imply that sets must accept all
     * elements; sets may refuse to add any particular element, including
     * <tt>null</tt>, and throwing an exception, as described in the
     * specification for <tt>Collection.add</tt>.  Individual set
     * implementations should clearly document any restrictions on the
     * elements that they may contain.
     * @param o element to be added to this set.
     * @return <tt>true</tt> if this set did not already contain the specified
     *         element.
     * @throws UnsupportedOperationException if the <tt>add</tt> method is not
     *                                       supported by this set.
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this set.
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not support null elements.
     * @throws IllegalArgumentException      if some aspect of the specified element
     *                                       prevents it from being added to this set.
     */
    public boolean add(S o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the specified element from this set if it is present (optional
     * operation).  More formally, removes an element <code>e</code> such that
     * <code>(o==null ?  e==null : o.equals(e))</code>, if the set contains
     * such an element.  Returns <tt>true</tt> if the set contained the
     * specified element (or equivalently, if the set changed as a result of
     * the call).  (The set will not contain the specified element once the
     * call returns.)
     * @param o object to be removed from this set, if present.
     * @return true if the set contained the specified element.
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this set (optional).
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not support null elements (optional).
     * @throws UnsupportedOperationException if the <tt>remove</tt> method is
     *                                       not supported by this set.
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns <tt>true</tt> if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns <tt>true</tt> if it is a <i>subset</i> of this set.
     * @param c collection to be checked for containment in this set.
     * @return <tt>true</tt> if this set contains all of the elements of the
     *         specified collection.
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              set (optional).
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this set does not support null
     *                              elements (optional).
     * @throws NullPointerException if the specified collection is
     *                              <tt>null</tt>.
     * @see #contains(Object)
     */
    public boolean containsAll(Collection<?> c) {
        if(c == null) {
            throw new NullPointerException();
        }
        for(Object o : c) {
            if(!contains(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present (optional operation).  If the specified
     * collection is also a set, the <tt>addAll</tt> operation effectively
     * modifies this set so that its value is the <i>union</i> of the two
     * sets.  The behavior of this operation is unspecified if the specified
     * collection is modified while the operation is in progress.
     * @param c collection whose elements are to be added to this set.
     * @return <tt>true</tt> if this set changed as a result of the call.
     * @throws UnsupportedOperationException if the <tt>addAll</tt> method is
     *                                       not supported by this set.
     * @throws ClassCastException            if the class of some element of the
     *                                       specified collection prevents it from being added to this
     *                                       set.
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this set does not support null
     *                                       elements, or if the specified collection is <tt>null</tt>.
     * @throws IllegalArgumentException      if some aspect of some element of the
     *                                       specified collection prevents it from being added to this
     *                                       set.
     * @see #add(Object)
     */
    public boolean addAll(Collection<? extends S> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this set all of its elements that are not contained in the
     * specified collection.  If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * <i>intersection</i> of the two sets.
     * @param c collection that defines which elements this set will retain.
     * @return <tt>true</tt> if this collection changed as a result of the
     *         call.
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> method
     *                                       is not supported by this Collection.
     * @throws ClassCastException            if the types of one or more elements in this
     *                                       set are incompatible with the specified collection
     *                                       (optional).
     * @throws NullPointerException          if this set contains a null element and
     *                                       the specified collection does not support null elements
     *                                       (optional).
     * @throws NullPointerException          if the specified collection is
     *                                       <tt>null</tt>.
     * @see #remove(Object)
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     * @param c collection that defines which elements will be removed from
     *          this set.
     * @return <tt>true</tt> if this set changed as a result of the call.
     * @throws UnsupportedOperationException if the <tt>removeAll</tt>
     *                                       method is not supported by this Collection.
     * @throws ClassCastException            if the types of one or more elements in this
     *                                       set are incompatible with the specified collection
     *                                       (optional).
     * @throws NullPointerException          if this set contains a null element and
     *                                       the specified collection does not support null elements
     *                                       (optional).
     * @throws NullPointerException          if the specified collection is
     *                                       <tt>null</tt>.
     * @see #remove(Object)
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes all of the elements from this set (optional operation).
     * This set will be empty after this call returns (unless it throws an
     * exception).
     * @throws UnsupportedOperationException if the <tt>clear</tt> method
     *                                       is not supported by this set.
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
