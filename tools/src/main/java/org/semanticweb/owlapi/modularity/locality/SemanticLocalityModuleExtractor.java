/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2020, Marc Robin Nolte
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.modularity.locality;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * Class to extract modules based on semantic locality.
 *
 * @author Marc Robin Nolte
 *
 */
public final class SemanticLocalityModuleExtractor extends LocalityModuleExtractor {



    /**
     * The manager to instantiate an {@link OWLReasoner}.
     */
    private final OWLReasonerFactory reasonerFactory;

    /**
     * The factory to instantiate an {@link OWLReasoner}.
     */
    private final OWLOntologyManager ontologyManager;

    private SemanticLocalityEvaluator topEvaluator;
    private SemanticLocalityEvaluator botEvaluator;

    /**
     * Instantiates a new {@link SemanticLocalityEvaluator}.
     *
     *
     * @param localityClass   The {@link LocalityClass} to use
     * @param axiomBase       The axiom base of the new {@link SemanticLocalityModuleExtractor}
     * @param ontologyManager The manager that should be used to instantiate an {@link OWLReasoner}
     * @param reasonerFactory The factory that should be used to instantiate an {@link OWLReasoner}
     */
    public SemanticLocalityModuleExtractor(LocalityClass localityClass, Stream<OWLAxiom> axiomBase,
        OWLOntologyManager ontologyManager, OWLReasonerFactory reasonerFactory) {
        super(localityClass, axiomBase);
        initEvaluators();

        this.reasonerFactory = reasonerFactory;
        this.ontologyManager = ontologyManager;
    }

    private void initEvaluators() {
        if (getLocalityClass() == LocalityClass.BOTTOM || getLocalityClass() == LocalityClass.STAR) {
            botEvaluator = new SemanticLocalityEvaluator(LocalityClass.BOTTOM, ontologyManager, reasonerFactory);
        }
        if (getLocalityClass() == LocalityClass.TOP || getLocalityClass() == LocalityClass.STAR) {
            topEvaluator = new SemanticLocalityEvaluator(LocalityClass.TOP, ontologyManager, reasonerFactory);
        }
    }

    /**
     * Disposes the reasoner and the empty ontology that is used to check whether axioms are tautologies via calling {@link OWLReasoner#dispose()}.
     * Must be called after this SemanticLocalityModuleExtractor is no longer used
     * to free resources that are allocated by the reasoner and the ontology.
     */
    public void dispose() {
        if(botEvaluator != null) {
            botEvaluator.dispose();
        }
        if(topEvaluator != null) {
            topEvaluator.dispose();
        }

    }

    @Override
    protected LocalityEvaluator bottomEvaluator() {
        return botEvaluator;
    }

    @Override
    protected LocalityEvaluator topEvaluator() {
        return topEvaluator;
    }

}
