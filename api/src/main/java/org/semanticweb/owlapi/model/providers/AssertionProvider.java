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
package org.semanticweb.owlapi.model.providers;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;

/** Assertion provider interface. */
public interface AssertionProvider extends LiteralProvider {

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @return a same individuals axiom with specified individuals
     */
    @Nonnull
    default OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals) {
        return getOWLSameIndividualAxiom(individuals, Collections.emptySet());
    }

    /**
     * @param individual
     *        individual
     * @return a same individuals axiom with specified individuals
     */
    @Nonnull
    default OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull OWLIndividual... individual) {
        checkIterableNotNull(individual, "individuals cannot be null", true);
        Set<OWLIndividual> inds = new HashSet<>();
        inds.addAll(Arrays.asList(individual));
        return getOWLSameIndividualAxiom(inds);
    }

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a same individuals axiom with specified individuals and
     *         annotations
     */
    @Nonnull
    OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals
     */
    @Nonnull
    default OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals) {
        return getOWLDifferentIndividualsAxiom(individuals,
                Collections.emptySet());
    }

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals
     */
    @Nonnull
    default OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull OWLIndividual... individuals) {
        checkIterableNotNull(individuals, "individuals cannot be null", true);
        return getOWLDifferentIndividualsAxiom(CollectionFactory
                .createSet(individuals));
    }

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals and
     *         annotations
     */
    @Nonnull
    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * @param classExpression
     *        class Expression
     * @param individual
     *        individual
     * @return a class assertion axiom
     */
    @Nonnull
    default OWLClassAssertionAxiom getOWLClassAssertionAxiom(
            @Nonnull OWLClassExpression classExpression,
            @Nonnull OWLIndividual individual) {
        return getOWLClassAssertionAxiom(classExpression, individual,
                Collections.emptySet());
    }

    /**
     * @param classExpression
     *        class Expression
     * @param individual
     *        individual
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a class assertion axiom with annotations
     */
    @Nonnull
    OWLClassAssertionAxiom getOWLClassAssertionAxiom(
            @Nonnull OWLClassExpression classExpression,
            @Nonnull OWLIndividual individual,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @param individual
     *        individual
     * @param object
     *        object
     * @return an object property assertion
     */
    @Nonnull
    default OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual individual, @Nonnull OWLIndividual object) {
        return getOWLObjectPropertyAssertionAxiom(property, individual, object,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param individual
     *        individual
     * @param object
     *        object
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an object property assertion with annotations
     */
    @Nonnull
    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual individual, @Nonnull OWLIndividual object,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param object
     *        object
     * @return a negative property assertion axiom on given arguments
     */
    @Nonnull
    default OWLNegativeObjectPropertyAssertionAxiom
            getOWLNegativeObjectPropertyAssertionAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull OWLIndividual subject,
                    @Nonnull OWLIndividual object) {
        return getOWLNegativeObjectPropertyAssertionAxiom(property, subject,
                object, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param object
     *        object
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a negative property assertion axiom on given arguments with
     *         annotations
     */
    @Nonnull
    OWLNegativeObjectPropertyAssertionAxiom
            getOWLNegativeObjectPropertyAssertionAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull OWLIndividual subject,
                    @Nonnull OWLIndividual object,
                    @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param object
     *        object
     * @return a data property assertion
     */
    @Nonnull
    default OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual subject, @Nonnull OWLLiteral object) {
        return getOWLDataPropertyAssertionAxiom(property, subject, object,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param object
     *        object
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a data property assertion with annotations
     */
    @Nonnull
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual subject, @Nonnull OWLLiteral object,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param value
     *        value
     * @return a data property assertion
     */
    @Nonnull
    default OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual subject, int value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param value
     *        value
     * @return a data property assertion
     */
    @Nonnull
    default OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual subject, double value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param value
     *        value
     * @return a data property assertion
     */
    @Nonnull
    default OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual subject, float value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param value
     *        value
     * @return a data property assertion
     */
    @Nonnull
    default OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual subject, boolean value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param value
     *        value
     * @return a data property assertion
     */
    @Nonnull
    default OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLIndividual subject, @Nonnull String value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param object
     *        object
     * @return a negative property assertion axiom on given arguments
     */
    @Nonnull
    default OWLNegativeDataPropertyAssertionAxiom
            getOWLNegativeDataPropertyAssertionAxiom(
                    @Nonnull OWLDataPropertyExpression property,
                    @Nonnull OWLIndividual subject, @Nonnull OWLLiteral object) {
        return getOWLNegativeDataPropertyAssertionAxiom(property, subject,
                object, Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param object
     *        object
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a negative property assertion axiom on given arguments with
     *         annotations
     */
    @Nonnull
    OWLNegativeDataPropertyAssertionAxiom
            getOWLNegativeDataPropertyAssertionAxiom(
                    @Nonnull OWLDataPropertyExpression property,
                    @Nonnull OWLIndividual subject, @Nonnull OWLLiteral object,
                    @Nonnull Set<? extends OWLAnnotation> annotations);

    // Annotation axioms
    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param value
     *        value
     * @return an annotation assertion axiom
     */
    @Nonnull
    default OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationSubject subject,
            @Nonnull OWLAnnotationValue value) {
        return getOWLAnnotationAssertionAxiom(property, subject, value,
                Collections.emptySet());
    }

    /**
     * @param subject
     *        subject
     * @param annotation
     *        annotation
     * @return an annotation assertion axiom
     */
    @Nonnull
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationSubject subject,
            @Nonnull OWLAnnotation annotation);

    /**
     * @param property
     *        property
     * @param subject
     *        subject
     * @param value
     *        value
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations
     */
    @Nonnull
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationSubject subject,
            @Nonnull OWLAnnotationValue value,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * @param subject
     *        subject
     * @param annotation
     *        annotation
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations
     */
    @Nonnull
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationSubject subject,
            @Nonnull OWLAnnotation annotation,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /**
     * Gets an annotation assertion that specifies that an IRI is deprecated.
     * The annotation property is owl:deprecated and the value of the annotation
     * is {@code "true"^^xsd:boolean}. (See <a href=
     * "http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Properties"
     * >Annotation Properties</a> in the OWL 2 Specification
     * 
     * @param subject
     *        The IRI to be deprecated.
     * @return The annotation assertion that deprecates the specified IRI.
     */
    @Nonnull
    OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(
            @Nonnull IRI subject);
}
