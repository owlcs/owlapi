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

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/** Object property assertion provider. */
public interface ObjectAssertionProvider extends LiteralProvider {

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
            @Nonnull Set<OWLAnnotation> annotations);

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
                    @Nonnull Set<OWLAnnotation> annotations);
}
