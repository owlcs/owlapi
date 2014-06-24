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
package org.semanticweb.owlapi.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * A bidirectional short form provider that caches entity short forms. The
 * provider has various methods to add, remove, update entities in the cache and
 * also to rebuild the cache from scratch.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class CachingBidirectionalShortFormProvider implements
        BidirectionalShortFormProvider {

    private final Map<String, Set<OWLEntity>> shortForm2EntityMap = new HashMap<>();
    private final Map<OWLEntity, String> entity2ShortFormMap = new HashMap<>();

    protected CachingBidirectionalShortFormProvider() {}

    /**
     * Generates the short form for the specified entity. This short form will
     * be cached so that it can be retrieved efficiently and so that the entity
     * can be obtained from the short form. If the short form for the entity
     * changes then the cach must explicilty be updated using the {@code update}
     * method.
     * 
     * @param entity
     *        The entity whose short form should be generated.
     * @return short form
     */
    @Nonnull
    protected abstract String generateShortForm(@Nonnull OWLEntity entity);

    @Override
    public Set<String> getShortForms() {
        return CollectionFactory
                .getCopyOnRequestSetFromMutableCollection(shortForm2EntityMap
                        .keySet());
    }

    /**
     * Rebuilds the cache using entities obtained from the specified entity set
     * provider.
     * 
     * @param entitySetProvider
     *        The {@code OWLEntitySetProvider} that should be used to obtain the
     *        entities whose short forms will be cached.
     */
    protected void rebuild(OWLEntitySetProvider<OWLEntity> entitySetProvider) {
        shortForm2EntityMap.clear();
        entity2ShortFormMap.clear();
        for (OWLEntity entity : entitySetProvider.getEntities()) {
            assert entity != null;
            add(entity);
        }
    }

    /**
     * Adds an entity to the cache.
     * 
     * @param entity
     *        The entity to be added to the cache - the short form will
     *        automatically be generated and added to the cache.
     */
    public void add(@Nonnull OWLEntity entity) {
        String shortForm = generateShortForm(entity);
        entity2ShortFormMap.put(entity, shortForm);
        Set<OWLEntity> entities = shortForm2EntityMap.get(shortForm);
        if (entities == null) {
            // XXX what's the use of size 1? How likely it is to grow large?
            entities = new HashSet<>(1);
        }
        entities.add(entity);
        shortForm2EntityMap.put(shortForm, entities);
    }

    /**
     * Removes an entity and its short form from the cache.
     * 
     * @param entity
     *        The entity to be removed.
     */
    protected void remove(OWLEntity entity) {
        String shortForm = entity2ShortFormMap.remove(entity);
        if (shortForm != null) {
            shortForm2EntityMap.remove(shortForm);
        }
    }

    @Override
    public Set<OWLEntity> getEntities(String shortForm) {
        Set<OWLEntity> entities = shortForm2EntityMap.get(shortForm);
        if (entities != null) {
            return CollectionFactory
                    .getCopyOnRequestSetFromImmutableCollection(entities);
        } else {
            return CollectionFactory.emptySet();
        }
    }

    @Nullable
    @Override
    public OWLEntity getEntity(String shortForm) {
        Set<OWLEntity> entities = shortForm2EntityMap.get(shortForm);
        if (entities != null) {
            if (!entities.isEmpty()) {
                return entities.iterator().next();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getShortForm(OWLEntity entity) {
        String sf = entity2ShortFormMap.get(entity);
        if (sf != null) {
            return sf;
        } else {
            return generateShortForm(entity);
        }
    }

    @Override
    public void dispose() {
        shortForm2EntityMap.clear();
        entity2ShortFormMap.clear();
    }
}
