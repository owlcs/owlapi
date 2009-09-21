package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLEntity;

import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
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
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 * <p/>
 * A bidirectional short form provider that caches entity short forms.  The provider
 * has various methods to add, remove, update entities in the cache and also to rebuild
 * the cache from scratch.
 */
public abstract class CachingBidirectionalShortFormProvider implements BidirectionalShortFormProvider {

    private Map<String, Set<OWLEntity>> shortForm2EntityMap;

    private Map<OWLEntity, String> entity2ShortFormMap;


    protected CachingBidirectionalShortFormProvider() {
        shortForm2EntityMap = new HashMap<String, Set<OWLEntity>>();
        entity2ShortFormMap = new HashMap<OWLEntity, String>();
    }


    /**
     * Generates the short form for the specified entity.  This
     * short form will be cached so that it can be retrieved
     * efficiently and so that the entity can be obtained from the
     * short form.  If the short form for the entity changes then
     * the cach must explicilty be updated using the <code>update</code>
     * method.
     * @param entity The entity whose short form should be generated.
     */
    protected abstract String generateShortForm(OWLEntity entity);


    /**
     * Gets all of the short forms that are cached by this short form
     * provider.
     * @return A set of strings that represent all of the cached short forms.
     */
    public Set<String> getShortForms() {
       return Collections.unmodifiableSet(shortForm2EntityMap.keySet());
    }


    /**
     * Rebuilds the cache using entities obtained from the specified
     * entity set provider.
     * @param entitySetProvider The <code>OWLEntitySetProvider</code>
     *                          that should be used to obtain the entities whose short forms
     *                          will be cached.
     */
    public void rebuild(OWLEntitySetProvider<OWLEntity> entitySetProvider) {
        shortForm2EntityMap.clear();
        entity2ShortFormMap.clear();
        for (OWLEntity entity : entitySetProvider.getEntities()) {
            add(entity);
        }
    }


    /**
     * Adds an entity to the cache.
     * @param entity The entity to be added to the cache - the short
     *               form will automatically be generated and added to the cache.
     */
    public void add(OWLEntity entity) {
        String shortForm = generateShortForm(entity);
        entity2ShortFormMap.put(entity, shortForm);
        Set<OWLEntity> entities = shortForm2EntityMap.get(shortForm);
        if (entities == null) {
            entities = new HashSet<OWLEntity>(1);
        }
        entities.add(entity);
        shortForm2EntityMap.put(shortForm, entities);
    }


    /**
     * Removes an entity and its short form from the cache.
     * @param entity The entity to be removed.
     */
    public void remove(OWLEntity entity) {
        String shortForm = entity2ShortFormMap.remove(entity);
        if (shortForm != null) {
            shortForm2EntityMap.remove(shortForm);
        }
    }


    /**
     * If the short form for a cached entity has changed then this
     * method may be used to update the cached short form mapping for
     * the entity.
     * @param entity The entity whose short form should be updated.
     */
    public void update(OWLEntity entity) {
        remove(entity);
        add(entity);
    }


    public Set<OWLEntity> getEntities(String shortForm) {
        Set<OWLEntity> entities = shortForm2EntityMap.get(shortForm);
        if (entities != null) {
            return Collections.unmodifiableSet(entities);
        }
        else {
            return Collections.emptySet();
        }
    }


    public OWLEntity getEntity(String shortForm) {
        Set<OWLEntity> entities = shortForm2EntityMap.get(shortForm);
        if (entities != null) {
            if (!entities.isEmpty()) {
                return entities.iterator().next();
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }


    public String getShortForm(OWLEntity entity) {
        String sf = entity2ShortFormMap.get(entity);
        if(sf != null) {
            return sf;
        }
        else {
            return generateShortForm(entity);
        }
    }


    public void dispose() {
        shortForm2EntityMap.clear();
        entity2ShortFormMap.clear();
    }
}
