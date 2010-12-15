package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredSubClassAxiomGenerator extends InferredClassAxiomGenerator<OWLSubClassOfAxiom> {

    @Override
	protected void addAxioms(OWLClass entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLSubClassOfAxiom> result) {
        if (reasoner.isSatisfiable(entity)) {
            for (OWLClass sup : reasoner.getSuperClasses(entity, true).getFlattened()) {
                result.add(dataFactory.getOWLSubClassOfAxiom(entity, sup));
            }
        }
        else {
            result.add(dataFactory.getOWLSubClassOfAxiom(entity, dataFactory.getOWLNothing()));
        }
    }


    public String getLabel() {
        return "Subclasses";
    }
}
