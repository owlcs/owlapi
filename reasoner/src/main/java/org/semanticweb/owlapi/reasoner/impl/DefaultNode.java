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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.reasoner.Node;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public abstract class DefaultNode<E extends OWLObject> implements Node<E> {

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
        if (entities.size() > 0) {
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
        for (OWLObject entity : entities) {
            sb.append(entity);
            sb.append(" ");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj == null) {
			return false;
		}
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node<?> other = (Node<?>) obj;
        return entities.equals(other.getEntities());
    }

    @Override
    public int hashCode() {
        return entities.hashCode();
    }
}
