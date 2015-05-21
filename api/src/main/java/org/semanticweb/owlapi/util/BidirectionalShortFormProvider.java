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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * A short form provider which is capable of translating back and forth between
 * entities and their short forms.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface BidirectionalShortFormProvider extends ShortFormProvider {

    /**
     * For a given short form this method obtains the entities which have this
     * short form.
     * 
     * @param shortForm
     *        The short form of the entities that will be retrieved.
     * @return The set of entities that have the specified short form. If there
     *         are no entities which have the specified short form then an empty
     *         set will be returned.
     * @deprecated use {@link #entities(String)}
     */
    @Deprecated
    default Set<OWLEntity> getEntities(String shortForm) {
        return asSet(entities(shortForm));
    }

    /**
     * For a given short form this method obtains the entities which have this
     * short form.
     * 
     * @param shortForm
     *        The short form of the entities that will be retrieved.
     * @return The set of entities that have the specified short form. If there
     *         are no entities which have the specified short form then an empty
     *         set will be returned.
     */
    Stream<OWLEntity> entities(String shortForm);

    /**
     * A convenience method which gets an entity from its short form.
     * 
     * @param shortForm
     *        The short form of the entity.
     * @return The actual entity or {@code null} if there is no entity which has
     *         the specified short form. If the specified short form corresponds
     *         to more than one entity then an entity will be chosen by the
     *         implementation of the short form provider.
     */
    @Nullable
    OWLEntity getEntity(String shortForm);

    /**
     * Gets all of the short forms that are mapped to entities.
     * 
     * @return A set, which contains the strings representing the short forms of
     *         entities for which there is a mapping.
     * @deprecated use {@link #shortForms()}
     */
    @Deprecated
    default Set<String> getShortForms() {
        return asSet(shortForms());
    }

    /**
     * Gets all of the short forms that are mapped to entities.
     * 
     * @return A set, which contains the strings representing the short forms of
     *         entities for which there is a mapping.
     */
    Stream<String> shortForms();
}
