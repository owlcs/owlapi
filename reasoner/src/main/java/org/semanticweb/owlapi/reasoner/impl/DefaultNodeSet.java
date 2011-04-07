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

package org.semanticweb.owlapi.reasoner.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public abstract class DefaultNodeSet<E extends OWLObject> implements NodeSet<E> {

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
        return CollectionFactory.getCopyOnRequestSet(nodes);
    }

    /**
     * Adds an entity to this <code>NodeSet</code> by wrapping it in a <code>Node</code>.
     * @param entity The entity to be added.  The entity will be wrapped in the <code>Node</code> and the <code>Node</code>
     * added to this set.  Must not be <code>null</code>.
     * @throws NullPointerException if <code>entity</code> is <code>null</code>.
     */
    public void addEntity(E entity) {
        if (entity == null) {
            throw new NullPointerException("Cannot add null to a NodeSet");
        }
        addNode(getNode(entity));
    }

    /**
     * Adds a <code>Node</code> to this set.
     * @param node The <code>Node</code> to be added.
     * @throws NullPointerException if <code>entity</code> is <code>null</code>.
     */
    public void addNode(Node<E> node) {
        if (node == null) {
            throw new NullPointerException("Cannot add null to a NodeSet");
        }
        nodes.add(node);
    }

    /**
     * Adds a collection of <code>Node</code>s to this set.
     * @param nodes The <code>Node</code>s to be added.  Note that if the collection is not a set then duplicate
     * <code>Node</code>s will be filtered out.
     */
    public void addAllNodes(Collection<Node<E>> nodes) {
        for (Node<E> node : nodes) {
            if (node != null) {
                this.nodes.add(node);
            }
        }
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
        for (E e : entities) {
            addNode(getNode(e));
        }
    }

    protected abstract DefaultNode<E> getNode(E entity);

    protected abstract DefaultNode<E> getNode(Set<E> entities);

    public Set<E> getFlattened() {
        Set<E> result = new HashSet<E>();
        for (Node<E> node : nodes) {
            result.addAll(node.getEntities());
        }
        return result;
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public boolean containsEntity(E e) {
        for (Node<E> node : nodes) {
            if (node.contains(e)) {
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
    
    @Override
    public String toString() {
    	
    	return "Nodeset"+this.nodes.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NodeSet)) {
            return false;
        }
        NodeSet<?> other = (NodeSet<?>) obj;
        return nodes.equals(other.getNodes());
    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }
}
