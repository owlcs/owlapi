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
package org.semanticweb.owlapi.model.axiomproviders;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;

/**
 * Annotation, datatype and object property range provider.
 */
public interface RangeAxiomProvider {

    /**
     * @param property
     *        property
     * @param range
     *        range
     * @return an object property range axiom
     */
    @Nonnull
    default OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression range) {
        return getOWLObjectPropertyRangeAxiom(property, range,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param range
     *        range
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an object property range axiom with annotations
     */
    @Nonnull
    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression range,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param property
     *        property
     * @param owlDataRange
     *        data range
     * @return a data property range axiom
     */
    @Nonnull
    default OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange owlDataRange) {
        return getOWLDataPropertyRangeAxiom(property, owlDataRange,
                Collections.emptySet());
    }

    /**
     * @param property
     *        property
     * @param owlDataRange
     *        data range
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return a data property range axiom with annotations
     */
    @Nonnull
    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange owlDataRange,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * @param prop
     *        prop
     * @param range
     *        range
     * @return an annotation property range assertion
     */
    @Nonnull
    default OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            @Nonnull OWLAnnotationProperty prop, @Nonnull IRI range) {
        return getOWLAnnotationPropertyRangeAxiom(prop, range,
                Collections.emptySet());
    }

    /**
     * @param prop
     *        prop
     * @param range
     *        range
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return an annotation property range assertion with annotations
     */
    @Nonnull
    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            @Nonnull OWLAnnotationProperty prop, @Nonnull IRI range,
            @Nonnull Set<OWLAnnotation> annotations);
}
