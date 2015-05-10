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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class OWLAnnotationPropertyImpl extends OWLObjectImpl implements
    OWLAnnotationProperty {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final IRI iri;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.ANNOTATION_PROPERTY;
    }

    /**
     * @param i
     *        iri for property
     */
    public OWLAnnotationPropertyImpl(@Nonnull IRI i) {
        iri = checkNotNull(i, "i cannot be null");
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public EntityType<?> getEntityType() {
        return EntityType.ANNOTATION_PROPERTY;
    }

    @Override
    public boolean isType(EntityType<?> entityType) {
        return getEntityType().equals(entityType);
    }

    @Override
    public String toStringID() {
        return iri.toString();
    }

    @Override
    protected int compareObjectOfSameType(@Nonnull OWLObject object) {
        return iri.compareTo(((OWLAnnotationProperty) object).getIRI());
    }

    @Override
    public boolean isBuiltIn() {
        return OWLRDFVocabulary.BUILT_IN_AP_IRIS.contains(getIRI());
    }

    @Override
    public boolean isOWLAnnotationProperty() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLAnnotationProperty)) {
            return false;
        }
        OWLAnnotationProperty other = (OWLAnnotationProperty) obj;
        return iri.equals(other.getIRI());
    }
}
