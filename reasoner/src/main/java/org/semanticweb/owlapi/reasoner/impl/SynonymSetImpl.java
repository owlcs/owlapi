package org.semanticweb.owlapi.reasoner.impl;


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
 */
public class SynonymSetImpl<E> {

    private Set<E> delegate;
    
    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * default initial capacity (16) and load factor (0.75).
     */
    public SynonymSetImpl() {
        delegate = Collections.emptySet();
    }

    /**
     * Creates a singleton synonym set
     * @param e The one and only element
     */
    public SynonymSetImpl(E e) {
        delegate = Collections.singleton(e);
    }

    /**
     * Constructs a new set containing the elements in the specified
     * collection.  The <tt>HashMap</tt> is created with default load factor
     * (0.75) and an initial capacity sufficient to contain the elements in
     * the specified collection.
     * @param c the collection whose elements are to be placed into this set.
     * @throws NullPointerException if the specified collection is null.
     */
    public SynonymSetImpl(Collection<? extends E> c) {
        if (c.isEmpty()) {
            delegate = Collections.emptySet();
        }
        else if(c.size() == 1) {
            E e = c.iterator().next();
            delegate = Collections.singleton(e);
        }
        else {
            delegate = Collections.unmodifiableSet(new HashSet<E>(c));
        }
    }

    /**
     * Determines if this set of synonyms is a singleton set.
     * @return <code>true</code> if this synonym set is a singleton set, otherwise <code>false</code>
     */
    public boolean isSingleton() {
        return delegate.size() == 1;
    }

    /**
     * Gets the one and only element if this set of synonyms is a singleton set
     * @return the one and only element if this set is a singleton set.  If this set is not a singleton set
     *         then a runtime exception will be thrown
     * @see #isSingleton()
     */
    public E getSingletonElement() {
        if (!isSingleton()) {
            throw new RuntimeException("Not a singleton set");
        }
        return delegate.iterator().next();
    }

    /**
     * Returns an iterator over the elements in this set.  The elements
     * are returned in no particular order.
     * @return an Iterator over the elements in this set.
     * @see java.util.ConcurrentModificationException
     */
    public Iterator<E> iterator() {
        // Iterator won't let anything be removed because we wrap the delegate in an unmod collection
        return delegate.iterator();
    }

    /**
     * Adds the specified element to this set if it is not already
     * present.
     * @param o element to be added to this set.
     * @return <tt>true</tt> if the set did not already contain the specified
     *         element.
     */
    public boolean add(E o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the specified element from this set if it is present.
     * @param o object to be removed from this set, if present.
     * @return <tt>true</tt> if the set contained the specified element.
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes all of the elements from this set.
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes from this set all of its elements that are contained in
     * the specified collection (optional operation).<p>
     * This implementation determines which is the smaller of this set
     * and the specified collection, by invoking the <tt>size</tt>
     * method on each.  If this set has fewer elements, then the
     * implementation iterates over this set, checking each element
     * returned by the iterator in turn to see if it is contained in
     * the specified collection.  If it is so contained, it is removed
     * from this set with the iterator's <tt>remove</tt> method.  If
     * the specified collection has fewer elements, then the
     * implementation iterates over the specified collection, removing
     * from this set each element returned by the iterator, using this
     * set's <tt>remove</tt> method.<p>
     * Note that this implementation will throw an
     * <tt>UnsupportedOperationException</tt> if the iterator returned by the
     * <tt>iterator</tt> method does not implement the <tt>remove</tt> method.
     * @param c elements to be removed from this set.
     * @return <tt>true</tt> if this set changed as a result of the call.
     * @throws UnsupportedOperationException removeAll is not supported
     *                                       by this set.
     * @throws NullPointerException          if the specified collection is null.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds all of the elements in the specified collection to this collection
     * (optional operation).  The behavior of this operation is undefined if
     * the specified collection is modified while the operation is in
     * progress.  (This implies that the behavior of this call is undefined if
     * the specified collection is this collection, and this collection is
     * nonempty.) <p>
     * This implementation iterates over the specified collection, and adds
     * each object returned by the iterator to this collection, in turn.<p>
     * Note that this implementation will throw an
     * <tt>UnsupportedOperationException</tt> unless <tt>add</tt> is
     * overridden (assuming the specified collection is non-empty).
     * @param c collection whose elements are to be added to this collection.
     * @return <tt>true</tt> if this collection changed as a result of the
     *         call.
     * @throws UnsupportedOperationException if this collection does not
     *                                       support the <tt>addAll</tt> method.
     * @throws NullPointerException          if the specified collection is null.
     * @see #add(Object)
     */
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Retains only the elements in this collection that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this collection all of its elements that are not contained in the
     * specified collection. <p>
     * This implementation iterates over this collection, checking each
     * element returned by the iterator in turn to see if it's contained
     * in the specified collection.  If it's not so contained, it's removed
     * from this collection with the iterator's <tt>remove</tt> method.<p>
     * Note that this implementation will throw an
     * <tt>UnsupportedOperationException</tt> if the iterator returned by the
     * <tt>iterator</tt> method does not implement the <tt>remove</tt> method
     * and this collection contains one or more elements not present in the
     * specified collection.
     * @param c elements to be retained in this collection.
     * @return <tt>true</tt> if this collection changed as a result of the
     *         call.
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> method
     *                                       is not supported by this Collection.
     * @throws NullPointerException          if the specified collection is null.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     * @return the number of elements in this set (its cardinality).
     */
    public int size() {
        return delegate.size();
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     * @return <tt>true</tt> if this set contains no elements.
     */
    public boolean isEmpty() {
        return delegate.isEmpty();
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
        return delegate.contains(o);
    }

    /**
     * Returns an array containing all of the elements in this set.
     * Obeys the general contract of the <tt>Collection.toArray</tt> method.
     * @return an array containing all of the elements in this set.
     */
    public Object[] toArray() {
        return delegate.toArray();
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
        return delegate.toArray(a);
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
        return delegate.containsAll(c);
    }
}
