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
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class OWLAnnotationPropertyRangeAxiomImpl extends
        OWLAxiomImplWithoutEntityAndAnonCaching implements
        OWLAnnotationPropertyRangeAxiom {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final OWLAnnotationProperty property;
    @Nonnull
    protected final IRI range;

    /**
     * @param property
     *        property
     * @param range
     *        range
     * @param annotations
     *        annotations on the axiom
     */
    public OWLAnnotationPropertyRangeAxiomImpl(
            @Nonnull OWLAnnotationProperty property, @Nonnull IRI range,
            @Nonnull Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.property = checkNotNull(property, "property cannot be null");
        this.range = checkNotNull(range, "range cannot be null");
    }

    @Override
    public void addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        entities.add(property);
    }

    @Override
    public void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons) {}

    @Override
    public OWLAnnotationPropertyRangeAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLAnnotationPropertyRangeAxiomImpl(getProperty(),
                getRange(), NO_ANNOTATIONS);
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom getAnnotatedAxiom(
            Set<OWLAnnotation> annotations) {
        return new OWLAnnotationPropertyRangeAxiomImpl(getProperty(),
                getRange(), mergeAnnos(annotations));
    }

    @Override
    public OWLAnnotationProperty getProperty() {
        return property;
    }

    @Override
    public IRI getRange() {
        return range;
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_PROPERTY_RANGE;
    }

    @Override
    public boolean isLogicalAxiom() {
        return false;
    }

    @Override
    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) object;
        int diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return range.compareTo(other.getRange());
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        // superclass is responsible for null, identity, owlaxiom type and
        // annotations
        if (!(obj instanceof OWLAnnotationPropertyRangeAxiom)) {
            return false;
        }
        OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) obj;
        return property.equals(other.getProperty())
                && range.equals(other.getRange());
    }
}
