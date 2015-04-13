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
package org.semanticweb.owlapi.reasoner.impl;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAPIStreamUtils;

import com.google.common.collect.Sets;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImplNoCache;


/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 * @param <E>
 *        the type of entities in the node
 */
public abstract class DefaultNode<E extends OWLObject> implements Node<E> {

    private static final OWLDataFactory DF = new OWLDataFactoryImpl(new OWLDataFactoryInternalsImplNoCache(false));
    @Nonnull
    protected static final OWLClass TOP_CLASS = DF.getOWLThing();
    @Nonnull
    protected static final OWLClassNode TOP_NODE = new OWLClassNode(TOP_CLASS);
    @Nonnull
    protected static final OWLClass BOTTOM_CLASS = DF.getOWLNothing();
    @Nonnull
    protected static final OWLClassNode BOTTOM_NODE = new OWLClassNode(
            BOTTOM_CLASS);
    @Nonnull
    protected static final OWLDataProperty TOP_DATA_PROPERTY = DF
            .getOWLTopDataProperty();
    @Nonnull
    protected static final OWLDataPropertyNode TOP_DATA_NODE = new OWLDataPropertyNode(
            TOP_DATA_PROPERTY);
    @Nonnull
    protected static final OWLDataProperty BOTTOM_DATA_PROPERTY = DF
            .getOWLBottomDataProperty();
    @Nonnull
    protected static final OWLDataPropertyNode BOTTOM_DATA_NODE = new OWLDataPropertyNode(
            BOTTOM_DATA_PROPERTY);
    @Nonnull
    protected static final OWLDatatype TOP_DATATYPE = DF.getTopDatatype();
    @Nonnull
    protected static final OWLObjectProperty TOP_OBJECT_PROPERTY = DF
            .getOWLTopObjectProperty();
    @Nonnull
    protected static final OWLObjectPropertyNode TOP_OBJECT_NODE = new OWLObjectPropertyNode(
            TOP_OBJECT_PROPERTY);
    @Nonnull
    protected static final OWLObjectProperty BOTTOM_OBJECT_PROPERTY = DF
            .getOWLBottomObjectProperty();
    @Nonnull
    protected static final OWLObjectPropertyNode BOTTOM_OBJECT_NODE = new OWLObjectPropertyNode(
            BOTTOM_OBJECT_PROPERTY);
    @Nonnull
    private final Set<E> entities = new HashSet<>(4);

    /**
     * @param entity
     *        the entity to add
     */
    public DefaultNode(@Nonnull E entity) {
        entities.add(checkNotNull(entity, "entity cannot be null"));
    }

    /**
     * @param entities
     *        the entities to add
     */
    public DefaultNode(@Nonnull Collection<E> entities) {
        this.entities.addAll(checkNotNull(entities, "entities cannot be null"));
    }

    /**
     * @param entities
     *        the entities to add
     */
    public DefaultNode(@Nonnull Stream<E> entities) {
        OWLAPIStreamUtils.add(this.entities,
                checkNotNull(entities, "entities cannot be null"));
    }

    protected DefaultNode() {}

    protected abstract E getTopEntity();

    protected abstract E getBottomEntity();

    /**
     * @param entity
     *        entity to be added
     */
    public void add(E entity) {
        entities.add(entity);
    }

    @Override
    public boolean isTopNode() {
        return entities.contains(getTopEntity());
    }

    @Override
    public boolean isBottomNode() {
        return entities.contains(getBottomEntity());
    }

    @Nonnull
    @Override
    public Stream<E> entities() {
        return entities.stream();
    }

    @Override
    public int getSize() {
        return entities.size();
    }

    @Override
    public boolean contains(E entity) {
        return entities.contains(entity);
    }

    @Nonnull
    @Override
    public Set<E> getEntitiesMinus(E e) {
        return CollectionFactory.copy(Sets.filter(entities,
                (input) -> !input.equals(e)));
    }

    @Nonnull
    @Override
    public Set<E> getEntitiesMinusTop() {
        return getEntitiesMinus(getTopEntity());
    }

    @Nonnull
    @Override
    public Set<E> getEntitiesMinusBottom() {
        return getEntitiesMinus(getBottomEntity());
    }

    @Override
    public boolean isSingleton() {
        return entities.size() == 1;
    }

    @Nonnull
    @Override
    public E getRepresentativeElement() {
        return entities.iterator().next();
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return entities.iterator();
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node( ");
        for (OWLObject entity : entities) {
            sb.append(entity);
            sb.append(' ');
        }
        sb.append(')');
        return verifyNotNull(sb.toString());
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
        @SuppressWarnings("unchecked")
        Node<E> other = (Node<E>) obj;
        return entities.equals(asSet(other.entities()));
    }

    @Override
    public int hashCode() {
        return entities.hashCode();
    }
}
