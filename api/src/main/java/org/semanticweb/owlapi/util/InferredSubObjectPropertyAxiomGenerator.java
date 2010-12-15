package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredSubObjectPropertyAxiomGenerator extends InferredObjectPropertyAxiomGenerator<OWLSubObjectPropertyOfAxiom> {


    @Override
	protected void addAxioms(OWLObjectProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLSubObjectPropertyOfAxiom> result) {
        for (OWLObjectPropertyExpression prop : reasoner.getSuperObjectProperties(entity, true).getFlattened()) {
            result.add(dataFactory.getOWLSubObjectPropertyOfAxiom(entity, prop));
        }
    }


    public String getLabel() {
        return "Sub object properties";
    }
}
