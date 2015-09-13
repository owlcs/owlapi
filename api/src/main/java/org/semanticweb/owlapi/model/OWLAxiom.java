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

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Represents <a href="http://www.w3.org/TR/owl2-syntax/#Axioms">Axioms</a> in
 * the OWL 2 Specification.<br>
 * An OWL ontology contains a set of axioms. These axioms can be annotation
 * axioms, declaration axioms, imports axioms or logical axioms
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLAxiom extends OWLObject, HasAnnotations {

    /**
     * @param visitor
     *        visitor to accept
     */
    void accept(OWLAxiomVisitor visitor);

    /**
     * @param visitor
     *        visitor to accept
     * @param <O>
     *        visitor return type
     * @return visitor value
     */
    <O> O accept(OWLAxiomVisitorEx<O> visitor);

    /**
     * Gets an axiom that is structurally equivalent to this axiom without
     * annotations. This essentially returns a version of this axiom stripped of
     * any annotations
     * 
     * @return The annotationless version of this axiom
     */
    OWLAxiom getAxiomWithoutAnnotations();

    /**
     * Gets a copy of this axiom that is annotated with the specified
     * annotations. If this axiom has any annotations on it they will be merged
     * with the specified set of annotations. Note that this axiom will not be
     * modified (or removed from any ontologies).
     * 
     * @param annotations
     *        The annotations that will be added to existing annotations to
     *        annotate the copy of this axiom
     * @return A copy of this axiom that has the specified annotations plus any
     *         existing annotations returned by the
     *         {@code OWLAxiom#getAnnotations()} method.
     */
    default OWLAxiom getAnnotatedAxiom(Collection<OWLAnnotation> annotations) {
        return getAnnotatedAxiom(annotations.stream());
    }

    /**
     * Gets a copy of this axiom that is annotated with the specified
     * annotations. If this axiom has any annotations on it they will be merged
     * with the specified set of annotations. Note that this axiom will not be
     * modified (or removed from any ontologies).
     * 
     * @param annotations
     *        The annotations that will be added to existing annotations to
     *        annotate the copy of this axiom
     * @return A copy of this axiom that has the specified annotations plus any
     *         existing annotations returned by the
     *         {@code OWLAxiom#getAnnotations()} method.
     */
    OWLAxiom getAnnotatedAxiom(Stream<OWLAnnotation> annotations);

    /**
     * Determines if another axiom is equal to this axiom not taking into
     * consideration the annotations on the axiom
     * 
     * @param axiom
     *        The axiom to test if equal
     * @return {@code true} if {@code axiom} without annotations is equal to
     *         this axiom without annotations otherwise {@code false}.
     */
    default boolean equalsIgnoreAnnotations(OWLAxiom axiom) {
        return getAxiomWithoutAnnotations().equals(axiom.getAxiomWithoutAnnotations());
    }

    /**
     * Determines if this axiom is a logical axiom. Logical axioms are defined
     * to be axioms other than both declaration axioms (including imports
     * declarations) and annotation axioms.
     * 
     * @return {@code true} if the axiom is a logical axiom, {@code false} if
     *         the axiom is not a logical axiom.
     */
    default boolean isLogicalAxiom() {
        return false;
    }

    /**
     * Determines if this axioms in an annotation axiom (an instance of
     * {@code OWLAnnotationAxiom})
     * 
     * @return {@code true} if this axiom is an instance of
     *         {@code OWLAnnotationAxiom}, otherwise {@code false}.
     * @since 3.2
     */
    default boolean isAnnotationAxiom() {
        return false;
    }

    /**
     * Determines if this axiom has any annotations on it
     * 
     * @return {@code true} if this axiom has annotations on it, otherwise
     *         {@code false}
     */
    boolean isAnnotated();

    /**
     * Gets the axiom type for this axiom.
     * 
     * @return The axiom type that corresponds to the type of this axiom.
     */
    AxiomType<?> getAxiomType();

    /**
     * Determines if this axiom is one of the specified types
     * 
     * @param axiomTypes
     *        The axiom types to check for
     * @return {@code true} if this axiom is one of the specified types,
     *         otherwise {@code false}
     * @since 3.0
     */
    default boolean isOfType(AxiomType<?>... axiomTypes) {
        return isOfType(Arrays.stream(axiomTypes));
    }

    /**
     * Determines if this axiom is one of the specified types
     * 
     * @param types
     *        The axiom types to check for
     * @return {@code true} if this axioms is one of the specified types,
     *         otherwise {@code false}
     * @since 3.0
     */
    default boolean isOfType(Collection<AxiomType<?>> types) {
        return types.contains(getAxiomType());
    }

    /**
     * Determines if this axiom is one of the specified types
     * 
     * @param types
     *        The axiom types to check for
     * @return {@code true} if this axioms is one of the specified types,
     *         otherwise {@code false}
     * @since 3.0
     */
    default boolean isOfType(Stream<AxiomType<?>> types) {
        return types.anyMatch((x) -> getAxiomType().equals(x));
    }

    /**
     * Gets this axioms in negation normal form. i.e. any class expressions
     * involved in this axiom are converted into negation normal form.
     * 
     * @return The axiom in negation normal form.
     */
    OWLAxiom getNNF();
}
