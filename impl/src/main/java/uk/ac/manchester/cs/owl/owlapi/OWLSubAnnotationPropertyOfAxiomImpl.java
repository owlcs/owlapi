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
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class OWLSubAnnotationPropertyOfAxiomImpl extends OWLAxiomImpl implements OWLSubAnnotationPropertyOfAxiom {

    private final @Nonnull OWLAnnotationProperty subProperty;
    private final @Nonnull OWLAnnotationProperty superProperty;

    /**
     * @param subProperty
     *        sub property
     * @param superProperty
     *        super property
     * @param annotations
     *        annotations on the axiom
     */
    public OWLSubAnnotationPropertyOfAxiomImpl(OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty,
            Collection<OWLAnnotation> annotations) {
        super(annotations);
        this.subProperty = checkNotNull(subProperty, "subProperty cannot be null");
        this.superProperty = checkNotNull(superProperty, "superProperty cannot be null");
    }

    @Override
    public OWLSubAnnotationPropertyOfAxiom getAnnotatedAxiom(Stream<OWLAnnotation> anns) {
        return new OWLSubAnnotationPropertyOfAxiomImpl(getSubProperty(), getSuperProperty(), mergeAnnos(anns));
    }

    @Override
    public OWLSubAnnotationPropertyOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLSubAnnotationPropertyOfAxiomImpl(getSubProperty(), getSuperProperty(), NO_ANNOTATIONS);
    }

    @Override
    public OWLAnnotationProperty getSubProperty() {
        return subProperty;
    }

    @Override
    public OWLAnnotationProperty getSuperProperty() {
        return superProperty;
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.SUB_ANNOTATION_PROPERTY_OF;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLSubAnnotationPropertyOfAxiom other = (OWLSubAnnotationPropertyOfAxiom) object;
        int diff = subProperty.compareTo(other.getSubProperty());
        if (diff != 0) {
            return diff;
        }
        return superProperty.compareTo(other.getSuperProperty());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        // superclass is responsible for null, identity, owlaxiom type and
        // annotations
        if (!(obj instanceof OWLSubAnnotationPropertyOfAxiom)) {
            return false;
        }
        OWLSubAnnotationPropertyOfAxiom other = (OWLSubAnnotationPropertyOfAxiom) obj;
        return subProperty.equals(other.getSubProperty()) && superProperty.equals(other.getSuperProperty());
    }
}
