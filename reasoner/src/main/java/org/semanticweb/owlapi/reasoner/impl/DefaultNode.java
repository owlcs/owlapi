package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.OWLLogicalEntity;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.reasoner.Node;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
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
public abstract class DefaultNode<E extends OWLLogicalEntity> implements Node<E> {

    private Set<E> entities = new HashSet<E>(4);

    public DefaultNode(E entity) {
        this.entities.add(entity);
    }

    public DefaultNode(Set<E> entities) {
        this.entities.addAll(entities);
    }

    protected DefaultNode() {
    }

    protected abstract E getTopEntity();

    protected abstract E getBottomEntity();

    public void add(E entity) {
        entities.add(entity);
    }

    public boolean isTopNode() {
        return entities.contains(getTopEntity());
    }

    public boolean isBottomNode() {
        return entities.contains(getBottomEntity());
    }

    public Set<E> getEntities() {
        return entities;
    }

    public int getSize() {
        return entities.size();
    }

    public boolean contains(E entity) {
        return entities.contains(entity);
    }

    public Set<E> getEntitiesMinus(E E) {
        HashSet<E> result = new HashSet<E>(entities);
        result.remove(E);
        return result;
    }

    public Set<E> getEntitiesMinusTop() {
        return getEntitiesMinus(getTopEntity());
    }

    public Set<E> getEntitiesMinusBottom() {
        return getEntitiesMinus(getBottomEntity());
    }

    public boolean isSingleton() {
        return entities.size() == 1;
    }

    public E getRepresentativeElement() {
        if(entities.size() > 0) {
            return entities.iterator().next();
        }
        else {
            return null;
        }
    }

    public Iterator<E> iterator() {
        return entities.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node( ");
        for(OWLEntity entity : entities) {
            sb.append(entity);
            sb.append(" ");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Node)) {
            return false;
        }
        Node other = (Node) obj;
        return entities.equals(other.getEntities());
    }

    @Override
    public int hashCode() {
        return entities.hashCode();
    }
}
