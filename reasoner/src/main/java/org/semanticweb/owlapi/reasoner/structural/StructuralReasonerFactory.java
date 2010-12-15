package org.semanticweb.owlapi.reasoner.structural;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 25-Jan-2010
 */
public class StructuralReasonerFactory implements OWLReasonerFactory {

    public String getReasonerName() {
        return "Structural Reasoner";
    }

    public OWLReasoner createNonBufferingReasoner(OWLOntology ontology) {
        return createNonBufferingReasoner(ontology, new SimpleConfiguration());
    }

    public OWLReasoner createReasoner(OWLOntology ontology) {
        return createReasoner(ontology, new SimpleConfiguration());
    }

    public OWLReasoner createNonBufferingReasoner(OWLOntology ontology, OWLReasonerConfiguration config) throws IllegalConfigurationException {
        return new StructuralReasoner(ontology, config, BufferingMode.NON_BUFFERING);
    }

    public OWLReasoner createReasoner(OWLOntology ontology, OWLReasonerConfiguration config) throws IllegalConfigurationException {
        return new StructuralReasoner(ontology, config, BufferingMode.BUFFERING);
    }
}
