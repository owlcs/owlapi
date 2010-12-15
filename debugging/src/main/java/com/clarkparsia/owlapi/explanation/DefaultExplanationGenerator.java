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
