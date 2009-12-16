package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.OWLLogicalEntity;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.Node;

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
 * Date: 05-Dec-2009
 */
public abstract class DefaultNodeSet<E extends OWLLogicalEntity> implements NodeSet<E> {

    private Set<Node<E>> nodes = new HashSet<Node<E>>();

    public DefaultNodeSet() {
    }

    public DefaultNodeSet(E entity) {
        nodes.add(getNode(entity));
    }

    public DefaultNodeSet(Node<E> node) {
        nodes.add(node);
    }

    public DefaultNodeSet(Set<Node<E>> nodes) {
        this.nodes.addAll(nodes);
    }

    public Set<Node<E>> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }

    /**
     * Adds an entity to this <code>NodeSet</code> by wrapping it in a <code>Node</code>.
     * @param entity The entity to be added.  The entity will be wrapped in the <code>Node</code> and the <code>Node</code>
     * added to this set.
     */
    public void addEntity(E entity) {
        addNode(getNode(entity));
    }

    /**
     * Adds a <code>Node</code> to this set.
     * @param node The <code>Node</code> to be added.
     */
    public void addNode(Node<E> node) {
        nodes.add(node);
    }

    /**
     * Adds a collection of <code>Node</code>s to this set.
     * @param nodes The <code>Node</code>s to be added.  Note that if the collection is not a set then duplicate
     * <code>Node</code>s will be filtered out.
     */
    public void addAllNodes(Collection<Node<E>> nodes) {
        this.nodes.addAll(nodes);
    }

    /**
     * Adds the set of entities as a <code>Node</code> to this set.
     * @param entities The set of entities to be added.  The entities will be wrapped in a <code>Node</code>
     * which will be added to this <code>NodeSet</code>.
     */
    public void addSameEntities(Set<E> entities) {
        nodes.add(getNode(entities));
    }

    /**
     * Adds the specified entities as <code>Node</code>s to this set.
     * @param entities The entities to be added.  Each entity will be wrapped in a <code>Node</code> which will then
     * be added to this <code>NodeSet</code>.
     */
    public void addDifferentEntities(Set<E> entities) {
        for(E e : entities) {
            addNode(getNode(e));
        }
    }

    protected abstract DefaultNode<E> getNode(E entity);

    protected abstract DefaultNode<E> getNode(Set<E> entities);

    public Set<E> getFlattened() {
        Set<E> result = new HashSet<E>();
        for(Node<E> node : nodes) {
            result.addAll(node.getEntities());
        }
        return result;
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public boolean containsEntity(E e) {
        for(Node<E> node : nodes) {
            if(node.contains(e)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSingleton() {
        return nodes.size() == 1;
    }

    public boolean isTopSingleton() {
        return isSingleton() && nodes.iterator().next().isTopNode();
    }

    public boolean isBottomSingleton() {
        return isSingleton() && nodes.iterator().next().isBottomNode();
    }

    public Iterator<Node<E>> iterator() {
        return nodes.iterator();
    }
}
