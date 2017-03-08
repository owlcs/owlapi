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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLDataProperty;

import uk.ac.manchester.cs.owl.owlapi.InternalizedEntities;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class OWLDataPropertyNode extends DefaultNode<OWLDataProperty> {

    /**
     * Default constructor.
     */
    public OWLDataPropertyNode() {
        super();
    }

    /**
     * @param entity the entity to be contained
     */
    public OWLDataPropertyNode(OWLDataProperty entity) {
        super(entity);
    }

    /**
     * @param entities the entities to be contained
     */
    public OWLDataPropertyNode(Collection<OWLDataProperty> entities) {
        super(entities);
    }

    /**
     * @param entities the entities to be contained
     */
    public OWLDataPropertyNode(Stream<OWLDataProperty> entities) {
        super(entities);
    }

    /**
     * @return singleton top node
     */
    public static OWLDataPropertyNode getTopNode() {
        return TOP_DATA_NODE;
    }

    /**
     * @return singleton bottom node
     */
    public static OWLDataPropertyNode getBottomNode() {
        return BOTTOM_DATA_NODE;
    }

    @Override
    protected Optional<OWLDataProperty> getTopEntity() {
        return Optional.of(InternalizedEntities.OWL_TOP_DATA_PROPERTY);
    }

    @Override
    protected Optional<OWLDataProperty> getBottomEntity() {
        return Optional.of(InternalizedEntities.OWL_BOTTOM_DATA_PROPERTY);
    }
}
