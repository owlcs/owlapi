package org.semanticweb.owlapi.metrics;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class ReferencedIndividualCount extends ObjectCountMetric<OWLNamedIndividual> {


    public ReferencedIndividualCount(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    @Override
	protected String getObjectTypeName() {
        return "Individual";
    }


    @Override
	protected Set<OWLNamedIndividual> getObjects(OWLOntology ont) {
        return ont.getIndividualsInSignature();
    }
}
