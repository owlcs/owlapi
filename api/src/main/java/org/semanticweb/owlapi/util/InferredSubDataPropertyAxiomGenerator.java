package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredSubDataPropertyAxiomGenerator extends InferredDataPropertyAxiomGenerator<OWLSubDataPropertyOfAxiom> {


    @Override
	protected void addAxioms(OWLDataProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLSubDataPropertyOfAxiom> result) {
        for (OWLDataProperty prop : reasoner.getSuperDataProperties(entity, true).getFlattened()) {
            result.add(dataFactory.getOWLSubDataPropertyOfAxiom(entity, prop));
        }
    }


    public String getLabel() {
        return "Sub data properties";
    }
}
