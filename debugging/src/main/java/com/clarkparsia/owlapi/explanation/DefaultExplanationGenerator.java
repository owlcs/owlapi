/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, Clark & Parsia, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, Clark & Parsia, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.owlapi.explanation;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;


/**
 * Author: Matthew Horridge<br>
 * Clark & Parsia, LLC<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Jan-2008<br><br>
 */
public class DefaultExplanationGenerator implements ExplanationGenerator {

    private OWLDataFactory dataFactory;

    private MultipleExplanationGenerator gen;


    public DefaultExplanationGenerator(OWLOntologyManager man, OWLReasonerFactory reasonerFactory, OWLOntology ontology,
                                       ExplanationProgressMonitor progressMonitor) {
        this(man, reasonerFactory, ontology, reasonerFactory.createNonBufferingReasoner(ontology), progressMonitor);
    }


    public DefaultExplanationGenerator(OWLOntologyManager man, OWLReasonerFactory reasonerFactory, OWLOntology ontology,
                                       OWLReasoner reasoner, ExplanationProgressMonitor progressMonitor) {
        this.dataFactory = man.getOWLDataFactory();
        BlackBoxExplanation singleGen = new BlackBoxExplanation(ontology, reasonerFactory, reasoner);
        gen = new HSTExplanationGenerator(singleGen);
        if (progressMonitor != null) {
            gen.setProgressMonitor(progressMonitor);
        }
    }


    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass) {
        return gen.getExplanation(unsatClass);
    }


    public Set<OWLAxiom> getExplanation(OWLAxiom axiom) {
        SatisfiabilityConverter converter = new SatisfiabilityConverter(dataFactory);
        return getExplanation(converter.convert(axiom));
    }


    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass) {
        return gen.getExplanations(unsatClass);
    }


    public Set<Set<OWLAxiom>> getExplanations(OWLAxiom axiom) {
        SatisfiabilityConverter converter = new SatisfiabilityConverter(dataFactory);
        return getExplanations(converter.convert(axiom));
    }


    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass, int maxExplanations) {
        return gen.getExplanations(unsatClass, maxExplanations);
    }


    public Set<Set<OWLAxiom>> getExplanations(OWLAxiom axiom, int maxExplanations) throws OWLException {
        SatisfiabilityConverter converter = new SatisfiabilityConverter(dataFactory);
        return getExplanations(converter.convert(axiom), maxExplanations);
    }
}
