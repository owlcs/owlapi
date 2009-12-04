package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.Node;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Collections;
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
 * Date: 04-Dec-2009
 */
public class NodeSetImpl<E extends OWLLogicalEntity> implements NodeSet<E> {

    private Set<E> flattened;

    private Set<Node<E>> nodes;

    private NodeSetImpl(E entity) {
        this(NodeImpl.createOWLNode(entity));
    }

    private NodeSetImpl(Node<E> singleton) {
        nodes = Collections.singleton(singleton);
    }

    private NodeSetImpl(Set<Node<E>> nodes) {
        this.nodes = new HashSet<Node<E>>(nodes);
    }


    private NodeSetImpl(Set<E> entities, boolean flat) {
        this.flattened = new HashSet<E>(entities);
    }

    public static <E extends OWLLogicalEntity> NodeSet<E> createSingleton(E entity) {
        return new NodeSetImpl<E>(entity);
    }

    public static <E extends OWLLogicalEntity> NodeSet<E> createSingleton(Node<E> node) {
        return new NodeSetImpl<E>(node);
    }

    /**
     * Creates a NodeSet from a set of Nodes.
     *
     * @param nodes The nodes that will be contained in the NodeSet
     * @return A NodeSet containing the specified nodes
     */
    public static <E extends OWLLogicalEntity> NodeSet<E> createNodeSetFromNodes(Set<Node<E>> nodes) {
        return new NodeSetImpl<E>(nodes);
    }

    /**
     * Creates a Node set from a set of entities.  None of the entities in the set are equivalent.
     *
     * @param entities The entities
     * @return A NodeSet containing the specified entities.
     */
    public static <E extends OWLLogicalEntity> NodeSet<E> createNodeSetFromEnties(Set<E> entities) {
        return new NodeSetImpl<E>(entities, true);
    }


    public Set<E> getFlattened() {
        if (flattened != null) {
            return flattened;
        }
        else {
            Set<E> result = new HashSet<E>();
            for (Node<E> node : nodes) {
                result.addAll(node.getEntities());
            }
            return result;
        }
    }

    public boolean containsEntity(E e) {
        if (flattened != null) {
            return flattened.contains(e);
        }
        else {
            for (Node<E> node : nodes) {
                if (node.contains(e)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isSingleton() {
        if (flattened != null) {
            return flattened.size() == 1;
        }
        else {
            return nodes.size() == 1;
        }
    }

    public boolean isTopSingleton() {
        if (flattened != null) {
            return flattened.size() == 1 && flattened.iterator().next().isTopEntity();
        }
        else {
            return nodes.size() == 1 && nodes.iterator().next().isTopNode();
        }
    }

    public boolean isBottomSingleton() {
        if (flattened != null) {
            return flattened.size() == 1 && flattened.iterator().next().isBottomEntity();
        }
        else {
            return nodes.size() == 1 && nodes.iterator().next().isBottomNode();
        }
    }

    public int size() {
        if (flattened != null) {
            flattened.size();
        }
        return 0;
    }

    public boolean isEmpty() {
        if (flattened != null) {
            return flattened.isEmpty();
        }
        else {
            return nodes.isEmpty();
        }
    }

    public Iterator<Node<E>> iterator() {
        if (flattened != null) {
            return new FlattenedNodeIterator();
        }
        else {
            return nodes.iterator();
        }
    }

    private static final NodeSet<OWLClass> emptyClassNodeSet = createNodeSetFromEnties(new HashSet<OWLClass>(0));

    private static final NodeSet<OWLObjectProperty> emptyObjectPropertyNodeSet = createNodeSetFromEnties(new HashSet<OWLObjectProperty>(0));

    private static final NodeSet<OWLDataProperty> emptyDataPropertyNodeSet = createNodeSetFromEnties(new HashSet<OWLDataProperty>(0));

    private static final NodeSet<OWLNamedIndividual> emptyNamedIndividualNodeSet = createNodeSetFromEnties(new HashSet<OWLNamedIndividual>(0));

    public static NodeSet<OWLClass> emptyOWLClassNodeSet() {
        return emptyClassNodeSet;
    }

    public static NodeSet<OWLObjectProperty> emptyOWLObjectPropertyNodeSet() {
        return emptyObjectPropertyNodeSet;
    }

    public static NodeSet<OWLDataProperty> emptyOWLDataPropertyNodeSet() {
        return emptyDataPropertyNodeSet;
    }

    public static NodeSet<OWLNamedIndividual> emptyOWLNamedIndividualNodeSet() {
        return emptyNamedIndividualNodeSet;
    }


    private class FlattenedNodeIterator implements Iterator<Node<E>> {

        private Iterator<E> flattenedIterator = flattened.iterator();


        public boolean hasNext() {
            return flattenedIterator.hasNext();
        }

        public Node<E> next() {
            return NodeImpl.createOWLNode(flattenedIterator.next());
        }

        public void remove() {
            // Do nothing? Exception?
        }
    }

}
