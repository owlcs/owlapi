package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.OWLLogicalEntity;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.Node;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
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
        nodes.addAll(nodes);
    }

    public void addEntity(E entity) {
        addNode(getNode(entity));
    }

    public void addNode(Node<E> node) {
        nodes.add(node);
    }

    public void addSameEntities(Set<E> entities) {
        nodes.add(getNode(entities));
    }

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
