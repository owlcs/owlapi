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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Generates an ontology based on inferred axioms which are essentially supplied
 * by a reasoner. The generator can be configured with
 * {@code InferredAxiomGenerator}s which generate specific kinds of axioms e.g.
 * subclass axioms.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 */
public class InferredOntologyGenerator {

    // The reasoner which is used to compute the inferred axioms
    private final OWLReasoner reasoner;
    private final List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators;

    /**
     * @param reasoner
     *        the reasoner to use
     * @param axiomGenerators
     *        the axiom generators to use
     */
    public InferredOntologyGenerator(
            @Nonnull OWLReasoner reasoner,
            @Nonnull List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators) {
        this.reasoner = checkNotNull(reasoner, "reasoner cannot be null");
        this.axiomGenerators = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>(
                checkNotNull(axiomGenerators, "axiomGenerators cannot be null"));
    }

    /**
     * @param reasoner
     *        the reasoner to use
     */
    public InferredOntologyGenerator(@Nonnull OWLReasoner reasoner) {
        this(reasoner, generators());
    }

    @SuppressWarnings("unchecked")
    private static List<InferredAxiomGenerator<? extends OWLAxiom>>
            generators() {
        return Arrays.<InferredAxiomGenerator<? extends OWLAxiom>> asList(
                new InferredClassAssertionAxiomGenerator(),
                new InferredDataPropertyCharacteristicAxiomGenerator(),
                new InferredEquivalentClassAxiomGenerator(),
                new InferredEquivalentDataPropertiesAxiomGenerator(),
                new InferredEquivalentObjectPropertyAxiomGenerator(),
                new InferredInverseObjectPropertiesAxiomGenerator(),
                new InferredObjectPropertyCharacteristicAxiomGenerator(),
                new InferredPropertyAssertionGenerator(),
                new InferredSubClassAxiomGenerator(),
                new InferredSubDataPropertyAxiomGenerator(),
                new InferredSubObjectPropertyAxiomGenerator());
    }

    /** @return the axiom generators */
    @Nonnull
    public List<InferredAxiomGenerator<?>> getAxiomGenerators() {
        return new ArrayList<InferredAxiomGenerator<?>>(axiomGenerators);
    }

    /**
     * Adds a generator if it isn't already in the list of generators.
     * 
     * @param generator
     *        The generator to be added.
     */
    public void addGenerator(@Nonnull InferredAxiomGenerator<?> generator) {
        checkNotNull(generator, "generator cannot be null");
        if (!axiomGenerators.contains(generator)) {
            axiomGenerators.add(generator);
        }
    }

    /**
     * Removes a generator.
     * 
     * @param generator
     *        the generator to be removed
     */
    public void removeGenerator(@Nonnull InferredAxiomGenerator<?> generator) {
        checkNotNull(generator, "generator cannot be null");
        axiomGenerators.remove(generator);
    }

    /**
     * Adds 'inferred axioms' to an ontology using the generators that have been
     * registered with this {@code InferredAxiomGenerator}.
     * 
     * @param df
     *        data factory.
     * @param ontology
     *        The ontology which the inferred axioms will be added to
     * @throws OWLOntologyChangeException
     *         If there was a problem adding the inferred axioms to the
     *         specified ontology.
     */
    public void fillOntology(@Nonnull OWLDataFactory df,
            @Nonnull OWLOntology ontology) throws OWLOntologyChangeException {
        checkNotNull(df, "df cannot be null");
        checkNotNull(ontology, "ontology cannot be null");
        List<AddAxiom> changes = new ArrayList<AddAxiom>();
        for (InferredAxiomGenerator<? extends OWLAxiom> axiomGenerator : axiomGenerators) {
            for (OWLAxiom ax : axiomGenerator.createAxioms(df, reasoner)) {
                changes.add(new AddAxiom(ontology, ax));
            }
        }
        ontology.getOWLOntologyManager().applyChanges(changes);
    }
}
