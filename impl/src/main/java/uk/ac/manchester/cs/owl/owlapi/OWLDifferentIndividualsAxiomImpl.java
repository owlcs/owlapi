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

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLDifferentIndividualsAxiomImpl extends OWLNaryIndividualAxiomImpl
    implements OWLDifferentIndividualsAxiom {

    /**
     * @param individuals
     *        individuals
     * @param annotations
     *        annotations on the axiom
     */
    public OWLDifferentIndividualsAxiomImpl(Collection<? extends OWLIndividual> individuals,
        Collection<OWLAnnotation> annotations) {
        super(individuals, annotations);
    }

    @Override
    public OWLDifferentIndividualsAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLDifferentIndividualsAxiomImpl(individuals, NO_ANNOTATIONS);
    }

    @Override
    public OWLDifferentIndividualsAxiom getAnnotatedAxiom(Stream<OWLAnnotation> anns) {
        return new OWLDifferentIndividualsAxiomImpl(individuals, mergeAnnos(anns));
    }

    @Override
    public Set<OWLDifferentIndividualsAxiom> asPairwiseAxioms() {
        Set<OWLDifferentIndividualsAxiom> result = new HashSet<>();
        for (int i = 0; i < individuals.size() - 1; i++) {
            for (int j = i + 1; j < individuals.size(); j++) {
                OWLIndividual indI = individuals.get(i);
                OWLIndividual indJ = individuals.get(j);
                result.add(
                    new OWLDifferentIndividualsAxiomImpl(new HashSet<>(Arrays.asList(indI, indJ)), NO_ANNOTATIONS));
            }
        }
        return result;
    }

    @Override
    public Set<OWLDifferentIndividualsAxiom> splitToAnnotatedPairs() {
        if (individuals.size() == 2) {
            return Collections.singleton(this);
        }
        Set<OWLDifferentIndividualsAxiom> result = new HashSet<>();
        for (int i = 0; i < individuals.size() - 1; i++) {
            OWLIndividual indI = individuals.get(i);
            OWLIndividual indJ = individuals.get(i + 1);
            result.add(new OWLDifferentIndividualsAxiomImpl(Arrays.asList(indI, indJ), annotations));
        }
        return result;
    }

    @Override
    public boolean containsAnonymousIndividuals() {
        return individuals().anyMatch(i -> i.isAnonymous());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return obj instanceof OWLDifferentIndividualsAxiom;
    }

    @Override
    public Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms() {
        List<OWLClassExpression> nominalsList = new ArrayList<>();
        individuals().forEach(i -> nominalsList.add(new OWLObjectOneOfImpl(i)));
        Set<OWLSubClassOfAxiom> result = new HashSet<>();
        for (int i = 0; i < nominalsList.size() - 1; i++) {
            for (int j = i + 1; j < nominalsList.size(); j++) {
                OWLClassExpression ceI = nominalsList.get(i);
                OWLClassExpression ceJ = nominalsList.get(j).getObjectComplementOf();
                result.add(new OWLSubClassOfAxiomImpl(ceI, ceJ, NO_ANNOTATIONS));
            }
        }
        return result;
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.DIFFERENT_INDIVIDUALS;
    }
}
