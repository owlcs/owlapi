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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLAnnotationImpl extends OWLObjectImplWithoutEntityAndAnonCaching
    implements OWLAnnotation {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final OWLAnnotationProperty property;
    @Nonnull
    private final OWLAnnotationValue value;
    @Nonnull
    private final List<OWLAnnotation> anns;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.ANNOTATION_TYPE_INDEX_BASE + 1;
    }

    /**
     * @param property
     *        annotation property
     * @param value
     *        annotation value
     * @param annotations
     *        annotations on the axiom
     */
    public OWLAnnotationImpl(@Nonnull OWLAnnotationProperty property,
        @Nonnull OWLAnnotationValue value,
        @Nonnull Set<? extends OWLAnnotation> annotations) {
        this.property = checkNotNull(property, "property cannot be null");
        this.value = checkNotNull(value, "value cannot be null");
        checkNotNull(annotations, "annotations cannot be null");
        anns = CollectionFactory.sortOptionally((Set<OWLAnnotation>) annotations);
    }

    @Override
    public Set<OWLAnnotation> getAnnotations() {
        return CollectionFactory
            .getCopyOnRequestSetFromImmutableCollection(anns);
    }

    @Override
    public OWLAnnotationProperty getProperty() {
        return property;
    }

    @Override
    public OWLAnnotationValue getValue() {
        return value;
    }

    @Override
    public OWLAnnotation getAnnotatedAnnotation(
        @Nonnull Set<OWLAnnotation> annotations) {
        if (annotations.isEmpty()) {
            return this;
        }
        Set<OWLAnnotation> merged = new HashSet<>(anns);
        merged.addAll(annotations);
        return new OWLAnnotationImpl(property, value, merged);
    }

    /**
     * Determines if this annotation is an annotation used to deprecate an IRI.
     * This is the case if the annotation property has an IRI of
     * {@code owl:deprecated} and the value of the annotation is
     * {@code "true"^^xsd:boolean}
     * 
     * @return {@code true} if this annotation is an annotation that can be used
     *         to deprecate an IRI, otherwise {@code false}.
     */
    @Override
    public boolean isDeprecatedIRIAnnotation() {
        return property.isDeprecated() && value instanceof OWLLiteral
            && ((OWLLiteral) value).isBoolean()
            && ((OWLLiteral) value).parseBoolean();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OWLAnnotationImpl) {
            OWLAnnotationImpl other = (OWLAnnotationImpl) obj;
            return other.getProperty().equals(property)
                && other.getValue().equals(value)
                && other.anns.equals(anns);
        }
        if (obj instanceof OWLAnnotation) {
            OWLAnnotation other = (OWLAnnotation) obj;
            return other.getProperty().equals(property)
                && other.getValue().equals(value)
                && other.getAnnotations().equals(getAnnotations());
        }
        return false;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotation other = (OWLAnnotation) object;
        int diff = getProperty().compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        } else {
            return getValue().compareTo(other.getValue());
        }
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
    public void accept(OWLAnnotationObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAnnotationObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        entities.add(property);
        addEntitiesFromAnnotationsToSet(anns, entities);
    }

    @Override
    public void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons) {
        addAnonymousIndividualsFromAnnotationsToSet(anns, anons);
        if (value instanceof OWLAnonymousIndividual) {
            OWLAnonymousIndividual anonymousIndividual = (OWLAnonymousIndividual) value;
            anons.add(anonymousIndividual);
        }
    }
}
