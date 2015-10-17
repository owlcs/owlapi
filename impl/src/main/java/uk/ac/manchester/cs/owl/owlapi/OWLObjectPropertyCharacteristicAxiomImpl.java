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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLObjectPropertyCharacteristicAxiomImpl extends OWLPropertyAxiomImpl
        implements OWLObjectPropertyCharacteristicAxiom {

    private final @Nonnull OWLObjectPropertyExpression property;

    /**
     * @param property
     *        property
     * @param annotations
     *        annotations
     */
    public OWLObjectPropertyCharacteristicAxiomImpl(OWLObjectPropertyExpression property,
            Collection<OWLAnnotation> annotations) {
        super(annotations);
        this.property = checkNotNull(property, "property cannot be null");
    }

    @Override
    public OWLObjectPropertyExpression getProperty() {
        return property;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLObjectPropertyCharacteristicAxiom)) {
            return false;
        }
        return ((OWLObjectPropertyCharacteristicAxiom) obj).getProperty().equals(property);
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        return property.compareTo(((OWLObjectPropertyCharacteristicAxiom) object).getProperty());
    }
}
