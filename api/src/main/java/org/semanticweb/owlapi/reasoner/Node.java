package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLEntity;

import java.util.Set;
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
 * Date: 03-Jul-2009
 *
 * Represents a node (in a hierarchy), such as a class hierarchy, which contains elements that are equivalent.
 * For example a node containing classes represents the fact that the classes are equivalent to each other.
 * Hierarchy nodes are immutable.
 */
public interface Node<E extends OWLEntity> extends Set<E> {

    /**
     * Determines if this node represents the top node (in a hierarchy).  For a class node this corresponds
     * to the node that contains owl:Thing.  For an object property node this represents the node that contains
     * owl:topObjectProperty.  For a data property hierarchy node this represents the node that contains
     * owl:topDataProperty.
     * @return <code>true</code> if this node is the top node in the hierarchy, or <code>false</code> if this node
     * is not the top node in the hierarchy.
     */
    boolean isTopNode();

    /**
     * Determines if this node represents the bottom node in the hierarchy.  For a class hierarchy this corresponds to
     * the node that represents owl:Nothing
     * @return <code>true</code> if this node is the bottom node in the hierarchy, or <code>false</code> if this node
     * is not the bottom node in the hierarchy
     */
    boolean isBottomNode();

    /**
     * Gets the elements contained in this node.  The elements are equivalent to each other.
     * @return The set of elements
     */
    Set<E> getEquivalentElements();

    /**
     * Determines if this set of synonyms is a singleton set.
     * @return <code>true</code> if this synonym set is a singleton set, otherwise <code>false</code>
     */
    boolean isSingleton();

    /**
     * Gets the one and only element if this set of synonyms is a singleton set
     * @return the one and only element if this set is a singleton set.  If this set is not a singleton set
     * then a runtime exception will be thrown
     * @see #isSingleton()
     */
    E getSingletonElement();

    /**
     * Gets one of the elements contained in this synonym set.
     * @return An element from the set of synonyms
     */
    E getRepresentativeElement();
}
