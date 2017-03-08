/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;

/**
 * @author Matthew Horridge, Clark &amp; Parsia, LLC, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class DefaultExplanationGenerator implements ExplanationGenerator {

    private final OWLDataFactory dataFactory;
    private final MultipleExplanationGenerator gen;

    /**
     * Instantiates a new default explanation generator.
     *
     * @param man manager
     * @param reasonerFactory reasoner factory
     * @param ontology ontology to reason on
     * @param progressMonitor progress monitor
     */
    public DefaultExplanationGenerator(OWLOntologyManager man, OWLReasonerFactory reasonerFactory,
        OWLOntology ontology, ExplanationProgressMonitor progressMonitor) {
        this(man, reasonerFactory, ontology,
            checkNotNull(reasonerFactory, "reasonerFactory cannot be null")
                .createNonBufferingReasoner(ontology),
            progressMonitor);
    }

    /**
     * Instantiates a new default explanation generator.
     *
     * @param man manager
     * @param reasonerFactory reasoner factory
     * @param ontology ontology to reason on
     * @param reasoner the reasoner to use
     * @param progressMonitor progress monitor
     */
    public DefaultExplanationGenerator(OWLOntologyManager man, OWLReasonerFactory reasonerFactory,
        OWLOntology ontology, OWLReasoner reasoner,
        @Nullable ExplanationProgressMonitor progressMonitor) {
        dataFactory = checkNotNull(man, "man cannot be null").getOWLDataFactory();
        BlackBoxExplanation singleGen =
            new BlackBoxExplanation(checkNotNull(ontology, "ontology cannot be null"),
                checkNotNull(reasonerFactory,
                    "reasonerFactory cannot be null"),
                checkNotNull(reasoner, "reasoner cannot be null"));
        gen = new HSTExplanationGenerator(singleGen);
        if (progressMonitor != null) {
            gen.setProgressMonitor(progressMonitor);
        }
    }

    @Override
    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass) {
        return gen.getExplanation(unsatClass);
    }

    /**
     * Gets the explanation.
     *
     * @param axiom the axiom to explain
     * @return the explanation
     */
    public Set<OWLAxiom> getExplanation(OWLAxiom axiom) {
        SatisfiabilityConverter converter = new SatisfiabilityConverter(dataFactory);
        return getExplanation(converter.convert(checkNotNull(axiom, "axiom cannot be null")));
    }

    @Override
    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass) {
        return gen.getExplanations(unsatClass);
    }

    /**
     * Gets the explanations.
     *
     * @param axiom the axiom to explain
     * @return the set of explanations
     */
    public Set<Set<OWLAxiom>> getExplanations(OWLAxiom axiom) {
        SatisfiabilityConverter converter = new SatisfiabilityConverter(dataFactory);
        return getExplanations(converter.convert(checkNotNull(axiom, "axiom cannot be null")));
    }

    @Override
    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass, int maxExplanations) {
        return gen.getExplanations(unsatClass, maxExplanations);
    }

    /**
     * Gets the explanations.
     *
     * @param axiom the axiom to explain
     * @param maxExplanations max number of explanations
     * @return the set of explanations
     */
    public Set<Set<OWLAxiom>> getExplanations(OWLAxiom axiom, int maxExplanations) {
        SatisfiabilityConverter converter = new SatisfiabilityConverter(dataFactory);
        return getExplanations(converter.convert(checkNotNull(axiom, "axiom cannot be null")),
            maxExplanations);
    }
}
