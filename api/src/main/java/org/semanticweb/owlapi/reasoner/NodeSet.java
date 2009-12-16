package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLogicalEntity;

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
 * Date: 01-Aug-2009
 *
 * </p>
 * A set of {@link Node}s.
 */
public interface NodeSet<E extends OWLLogicalEntity> extends Iterable<Node<E>> {


    /**
     * A convenience method that gets all of the entities contained in the <code>Nodes</code> in this <code>NodeSet</code>.
     * @return The union of the entities contained in the <code>Nodes</code> in this <code>NodeSet</code>.
     */
    Set<E> getFlattened();

    boolean isEmpty();

    /**
     * A convenience method that determines if this <code>NodeSet</code> contains a specific entity.
     * @param e The entity to test for
     * @return <code>true</code> if this <code>NodeSet</code> contains a <code>Node</code> that contains the entity,
     * <code>e</code>, and <code>false</code> if this <code>NodeSet</code> does not contain a <code>Node</code>
     * that contains the entity, <code>e</code>.
     */
    boolean containsEntity(E e);

    /**
     * Determines if this <code>NodeSet</code> is a singleton.  A <code>NodeSet</code> is a singleton if it contains
     * only one <code>Node</code>.
     * @return <code>true</code> if this <code>NodeSet</code> is a singleton, otherwise <code>false</code>.
     */
    boolean isSingleton();

    /**
     * Determines if this <code>NodeSet</code> is a singleton node that only contains the top node (in a hierarchy).
     * @see {@link Node#isTopNode()} 
     * @return <code>true</code> if this <code>NodeSet</code> is a singleton that contains only the top node, and
     * <code>false</code> otherwise.
     */
    boolean isTopSingleton();


    /**
     * Determies if this <code>NodeSet</code> is a singleton that only contains the bottom node (in a hierarchy).
     * @see {@link Node#isBottomNode()} 
     * @return <code>true</code> if this <code>NodeSet</code> is a singleton that only contains a node that is the
     * bottom node, otherwise <code>false</code>
     */
    boolean isBottomSingleton();

    /**
     * Gets the <code>Node</code>s contained in this <code>NodeSet</code>
     * @return The set of <code>Node</code>s contained in this <code>NodeSet</code>.  Note that this set will be
     * an unmodifiable set.
     */
    Set<Node<E>> getNodes();
}
