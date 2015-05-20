/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.reasoner;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * A set of {@link Node}s.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 * @param <E>
 *        the type of elements in the node set
 */
public interface NodeSet<E extends OWLObject> extends Iterable<Node<E>> {

    /**
     * A convenience method that gets all of the entities contained in the
     * {@code Nodes} in this {@code NodeSet}.
     * 
     * @return The union of the entities contained in the {@code Nodes} in this
     *         {@code NodeSet}.
     * @deprecated use {@link #entities()}
     */
    @Deprecated
    default Set<E> getFlattened() {
        return asSet(entities());
    }

    /**
     * A convenience method that gets all of the entities contained in the
     * {@code Nodes} in this {@code NodeSet}.
     * 
     * @return The union of the entities contained in the {@code Nodes} in this
     *         {@code NodeSet}.
     */
    Stream<E> entities();

    /**
     * @return true if the node set is empty
     */
    boolean isEmpty();

    /**
     * A convenience method that determines if this {@code NodeSet} contains a
     * specific entity.
     * 
     * @param e
     *        The entity to test for
     * @return {@code true} if this {@code NodeSet} contains a {@code Node} that
     *         contains the entity, {@code e}, and {@code false} if this
     *         {@code NodeSet} does not contain a {@code Node} that contains the
     *         entity, {@code e}.
     */
    boolean containsEntity(E e);

    /**
     * Determines if this {@code NodeSet} is a singleton. A {@code NodeSet} is a
     * singleton if it contains only one {@code Node}.
     * 
     * @return {@code true} if this {@code NodeSet} is a singleton, otherwise
     *         {@code false}.
     */
    boolean isSingleton();

    /**
     * Determines if this {@code NodeSet} is a singleton node that only contains
     * the top node (in a hierarchy).
     * 
     * @see org.semanticweb.owlapi.reasoner.Node#isTopNode()
     * @return {@code true} if this {@code NodeSet} is a singleton that contains
     *         only the top node, and {@code false} otherwise.
     */
    boolean isTopSingleton();

    /**
     * Determies if this {@code NodeSet} is a singleton that only contains the
     * bottom node (in a hierarchy).
     * 
     * @see org.semanticweb.owlapi.reasoner.Node#isBottomNode()
     * @return {@code true} if this {@code NodeSet} is a singleton that only
     *         contains a node that is the bottom node, otherwise {@code false}
     */
    boolean isBottomSingleton();

    /**
     * Gets the {@code Node}s contained in this {@code NodeSet}.
     * 
     * @return The set of {@code Node}s contained in this {@code NodeSet}. Note
     *         that this set will be an unmodifiable set.
     * @deprecated use {@link #nodes()}
     */
    @Deprecated
    default Set<Node<E>> getNodes() {
        return asSet(nodes());
    }

    /**
     * Gets the {@code Node}s contained in this {@code NodeSet}.
     * 
     * @return The set of {@code Node}s contained in this {@code NodeSet}. Note
     *         that this set will be an unmodifiable set.
     */
    Stream<Node<E>> nodes();
}
