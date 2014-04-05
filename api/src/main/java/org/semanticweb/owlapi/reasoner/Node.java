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

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * Represents a node (set) of entities. The entities in a node are equivalent to
 * each other. <br>
 * <h3>Nodes in hierarchies</h3> In the OWL API, a reasoner treats a class
 * hierarchy, an object property hierarchy or a data property hierarchy as a
 * hierarchy (directed acyclic graph - DAG) of {@code Nodes}. Each node contains
 * entities that are equivalent to each other. A hierarchy contains a <i>top
 * node</i>, which is the ancestor of all nodes in the hierarchy, and a
 * <i>bottom node</i> In a class hierarchy, the nodes contain {@code OWLClass}
 * objects. The top node contains {@code owl:Thing} (and any other named classes
 * that are equivalent to {@code owl:Thing}). The bottom node contains
 * {@code owl:Nothing} (and any other named classes that are equivalent to
 * {@code owl:Nothing} - these classes are <i>unsatisfiable</i> In an object
 * property hierarchy, the nodes contain {@code OWLObjectProperty} objects. The
 * top node contains {@code owl:topObjectProperty} (and any other named object
 * properties that are equivalent to {@code owl:topObjectProperty}). The bottom
 * node contains {@code owl:bottomObjectProperty} (and any other named object
 * properties that are equivalent to {@code owl:bottomObjectProperty}). <br>
 * In a data property hierarchy, the nodes contain {@code OWLDataProperty}
 * objects. The top node contains {@code owl:topDataProperty} (and any other
 * data properties that are equivalent to {@code owl:topDataProperty}). The
 * bottom node contains {@code owl:bottomDataProperty} (and any other data
 * properties that are equivalent to {@code owl:bottomDataProperty}). <br>
 * <h4>Class Hierarchy Example</h4> The figure below shows an example class
 * hierarchy. Each box in the hierarchy represents a {@code Node}. In this case
 * the top node contains {@code owl:Thing} and the bottom node contains
 * {@code owl:Nothing} because the nodes in the hierarchy are {@code OWLClass}
 * nodes. In this case, class {@code G} is equivalent to {@code owl:Thing} so it
 * appears as an entity in the top node. Similarly, class {@code K} is
 * unsatisfiable, so it is equivalent to {@code owl:Nothing} and therefore
 * appears in the bottom node containing {@code owl:Nothing}. <br>
 * <img src="../../../../doc-files/hierarchy.png" alt="hierarchy">
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 * @param <E>
 *        the type of elements represented in the Node
 */
public interface Node<E extends OWLObject> extends Iterable<E> {

    /**
     * Determines if this node represents the top node (in a hierarchy). For a
     * named class node, the top node is the node that contains
     * {@code owl:Thing}. For an object property node, the top node is the node
     * that contains {@code owl:topObjectProperty}. For a data property node,
     * the top node is the node that contains {@code owl:topDataProperty}
     * 
     * @return {@code true} if this node is an {@code OWLClass} node and it
     *         contains {@code owl:Thing}. <br>
     *         {@code true} if this node is an {@code OWLObjectProperty} node
     *         and it contains {@code owl:topObjectProperty}. <br>
     *         {@code true} if this node is an {@code OWLDataProperty} node and
     *         it contains {@code owl:topDataProperty}. <br>
     *         {@code false} if none of the above.
     */
    boolean isTopNode();

    /**
     * Determines if this node represents the bottom node (in a hierarchy). For
     * a named class node, the bottom node is the node that contains
     * {@code owl:Nothing}. For an object property node, the bottom node is the
     * node that contains {@code owl:bottomObjectProperty}. For a data property
     * node, the bottom node is the node that contains
     * {@code owl:bottomDataProperty}
     * 
     * @return {@code true} if this node is an {@code OWLClass} node and it
     *         contains {@code owl:Nothing}. <br>
     *         {@code true} if this node is an {@code OWLObjectProperty} node
     *         and it contains {@code owl:bottomObjectProperty}. <br>
     *         {@code true} if this node is an {@code OWLDataProperty} node and
     *         it contains {@code owl:bottomDataProperty}. <br>
     *         {@code false} if none of the above.
     */
    boolean isBottomNode();

    /**
     * Gets the entities contained in this node. The entities are equivalent to
     * each other.
     * 
     * @return The set of entities contained in this {@code Node}.
     */
    Set<E> getEntities();

    /**
     * Gets the number of entities contained in this {@code Node}.
     * 
     * @return The number of entities contained in this node.
     */
    int getSize();

    /**
     * Determines if this node contains the specified entity.
     * 
     * @param entity
     *        The entity to check for
     * @return {@code true} if this node contains {@code entity}, or
     *         {@code false} if this node does not contain {@code entity}
     */
    boolean contains(E entity);

    /**
     * Gets the entities contained in this node minus the specified entitie
     * {@code e}. This essentially returns the entities that are returned by
     * {@link #getEntities()} minus the specified entity {@code e}
     * 
     * @param e
     *        The entity that, is contained within this node, but should not be
     *        included in the return set.
     * @return The set of entities that are contained in this node minus the
     *         specified entity, {@code e}. If {@code e} is not contained within
     *         this node then the full set of entities returned is the same as
     *         that returned by {@link #getEntities()}
     */
    Set<E> getEntitiesMinus(E e);

    /**
     * Gets the entities contained in this node minus the top entity. For a node
     * of named classes the top entity is {@code owl:Thing}. For a node of
     * object properties the top entity is {@code owl:topObjectProperty}. For a
     * node of data properties the top entity is {@code owl:topDataProperty}
     * 
     * @return The set of entities contained within this node minus the top
     *         entity. If this node does not contain the top entity then the set
     *         of entities returned is the same as that returned by
     *         {@link #getEntities()}.
     */
    Set<E> getEntitiesMinusTop();

    /**
     * Gets the entities contained in this node minus the bottom entity. For a
     * node of named classes the bottom entity is {@code owl:Nothing}. For a
     * node of object properties the bottom entity is
     * {@code owl:bottomObjectProperty}. For a node of data properties the
     * bottom entity is {@code owl:bottomDataProperty}
     * 
     * @return The set of entities contained within this node minus the bottom
     *         entity. If this node does not contain the bottom entity then the
     *         set of entities returned is the same as that returned by
     *         {@link #getEntities()}.
     */
    Set<E> getEntitiesMinusBottom();

    /**
     * Determines if this {@code Node} contains just one entity.
     * 
     * @return {@code true} if this {@code Node} contains just one entity,
     *         otherwise {@code false}
     */
    boolean isSingleton();

    /**
     * Gets one of the entities contained in this entity set. If this is a
     * singleton set it will be the one and only entity.
     * 
     * @return An entity from the set of entities contained within this node
     * @throws RuntimeException
     *         if this node is empty (it does not contain any entities).
     */
    E getRepresentativeElement();
}
