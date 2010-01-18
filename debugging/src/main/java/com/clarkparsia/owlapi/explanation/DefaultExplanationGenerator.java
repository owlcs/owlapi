package com.clarkparsia.owlapi.explanation;

import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
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
