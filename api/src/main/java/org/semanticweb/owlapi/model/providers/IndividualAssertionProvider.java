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

import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;

/** Individual (sameAs and differentFrom) assertion provider. */
public interface IndividualAssertionProvider extends LiteralProvider {

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
        return getOWLSameIndividualAxiom(createSet(individual));
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
            @Nonnull Set<OWLAnnotation> annotations);

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
            @Nonnull Set<OWLAnnotation> annotations);
}
