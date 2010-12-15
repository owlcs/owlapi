package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredInverseObjectPropertiesAxiomGenerator extends InferredObjectPropertyAxiomGenerator<OWLInverseObjectPropertiesAxiom> {


    @Override
	protected void addAxioms(OWLObjectProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLInverseObjectPropertiesAxiom> result) {
        for (OWLObjectPropertyExpression prop : reasoner.getInverseObjectProperties(entity)) {
            result.add(dataFactory.getOWLInverseObjectPropertiesAxiom(entity, prop));
        }
    }


    public String getLabel() {
        return "Inverse object properties";
    }
}
