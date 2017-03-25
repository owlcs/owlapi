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
package org.semanticweb.owlapi.model;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Annotations are used in the various types of annotation axioms, which bind annotations to their
 * subjects (i.e. axioms or declarations).<br>
 * An annotation is equal to another annotation if both objects have equal annotation URIs and have
 * equal annotation values.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLAnnotation
    extends OWLObject, HasAnnotations, HasProperty<OWLAnnotationProperty> {

    @Override
    default Stream<?> componentsWithoutAnnotations() {
        return Stream.of(getProperty(), getValue());
    }

    @Override
    default Stream<?> components() {
        return Stream.of(getProperty(), getValue(), annotations());
    }

    @Override
    default Stream<?> componentsAnnotationsFirst() {
        return Stream.of(annotations(), getProperty(), getValue());
    }

    @Override
    default OWLObjectType type() {
        return OWLObjectType.ANNOTATION;
    }

    /**
     * Gets the property that this annotation acts along.
     *
     * @return The annotation property
     */
    @Override
    OWLAnnotationProperty getProperty();

    /**
     * Gets the annotation value. The type of value will depend upon the type of the annotation e.g.
     * whether the annotation is an {@link org.semanticweb.owlapi.model.OWLLiteral}, an
     * {@link org.semanticweb.owlapi.model.IRI} or an
     * {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual}.
     *
     * @return The annotation value.
     * @see org.semanticweb.owlapi.model.OWLAnnotationValueVisitor
     * @see org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx
     */
    OWLAnnotationValue getValue();

    /**
     * Determines if this annotation is an annotation used to deprecate an IRI. This is the case if
     * the annotation property has an IRI of {@code owl:deprecated} and the value of the annotation
     * is {@code "true"^^xsd:boolean}
     *
     * @return {@code true} if this annotation is an annotation that can be used to deprecate an
     *         IRI, otherwise {@code false}.
     */
    boolean isDeprecatedIRIAnnotation();

    /**
     * Gets an OWLAnnotation which is a copy of this annotation but which has the specified
     * annotations.
     *
     * @param annotations The annotations
     * @return A copy of this annotation with the specified annotations annotating it
     */
    OWLAnnotation getAnnotatedAnnotation(Collection<OWLAnnotation> annotations);

    /**
     * Gets an OWLAnnotation which is a copy of this annotation but which has the specified
     * annotations.
     *
     * @param annotations The annotations
     * @return A copy of this annotation with the specified annotations annotating it
     */
    OWLAnnotation getAnnotatedAnnotation(Stream<OWLAnnotation> annotations);

    @Override
    default void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
