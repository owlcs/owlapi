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

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import uk.ac.manchester.cs.owl.owlapi.InternalizedEntities;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class OWLObjectPropertyNode extends DefaultNode<OWLObjectPropertyExpression> {

    /**
     * Default constructor.
     */
    public OWLObjectPropertyNode() {
        super();
    }

    /**
     * @param entity property to include
     */
    public OWLObjectPropertyNode(OWLObjectPropertyExpression entity) {
        super(entity);
    }

    /**
     * @param entities properties to include
     */
    public OWLObjectPropertyNode(Collection<OWLObjectPropertyExpression> entities) {
        super(entities);
    }

    /**
     * @param entities properties to include
     */
    public OWLObjectPropertyNode(Stream<OWLObjectPropertyExpression> entities) {
        super(entities);
    }

    /**
     * @return top node
     */
    public static OWLObjectPropertyNode getTopNode() {
        return TOP_OBJECT_NODE;
    }

    /**
     * @return bottom node
     */
    public static OWLObjectPropertyNode getBottomNode() {
        return BOTTOM_OBJECT_NODE;
    }

    @Override
    protected Optional<OWLObjectPropertyExpression> getTopEntity() {
        return Optional.of(InternalizedEntities.OWL_TOP_OBJECT_PROPERTY);
    }

    @Override
    protected Optional<OWLObjectPropertyExpression> getBottomEntity() {
        return Optional.of(InternalizedEntities.OWL_BOTTOM_OBJECT_PROPERTY);
    }
}
