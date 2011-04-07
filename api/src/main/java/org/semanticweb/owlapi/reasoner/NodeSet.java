/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.reasoner;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Aug-2009
 *
 * </p>
 * A set of {@link Node}s.
 */
public interface NodeSet<E extends OWLObject> extends Iterable<Node<E>> {


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
