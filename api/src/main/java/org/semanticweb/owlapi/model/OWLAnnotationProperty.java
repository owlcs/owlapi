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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Represents an <a href=
 * "http://www.w3.org/TR/owl2-syntax/#Annotation_Properties"
 * >AnnotationProperty</a> in the OWL 2 specification.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public interface OWLAnnotationProperty extends OWLProperty {

    /**
     * Determines if this annotation property has an IRI corresponding to
     * {@code rdfs:comment}.
     * 
     * @return {@code true} if the IRI of this annotation property is
     *         {@code rdfs:comment}, where {@code rdfs:} expands to the usual
     *         prefix, otherwise {@code false}.
     */
    default boolean isComment() {
        return getIRI().equals(OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    }

    /**
     * Determines if this annotation property has an IRI corresponding to
     * {@code rdfs:label}.
     * 
     * @return {@code true} if the IRI of this annotation property is
     *         {@code rdfs:label}, where {@code rdfs:} expands to the usual
     *         prefix, otherwise {@code false}.
     */
    default boolean isLabel() {
        return getIRI().equals(OWLRDFVocabulary.RDFS_LABEL.getIRI());
    }

    /**
     * Determines if this annotation property has an IRI corresponding to
     * {@code owl:deprecated}. An annotation along the {@code owl:deprecated}
     * property which has a value of {@code "true"^^xsd:boolean} can be used to
     * deprecate IRIs. (See <a href
     * ="http://www.w3.org/TR/owl2-syntax/#Annotation_Properties">Section 5.5
     * </a> of the OWL 2 specification.
     * 
     * @return {@code true} if the IRI of this annotation property is
     *         {@code owl:deprecated}, where {@code owl:} expands to the usual
     *         prefix, otherwise {@code false}.
     */
    default boolean isDeprecated() {
        return getIRI().equals(OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    }

    @Override
    default void accept(@Nonnull OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(@Nonnull OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default void accept(@Nonnull OWLPropertyExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(@Nonnull OWLPropertyExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default void accept(@Nonnull OWLEntityVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(@Nonnull OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default void accept(@Nonnull OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(@Nonnull OWLNamedObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
