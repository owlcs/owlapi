package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredPropertyAssertionGenerator extends InferredIndividualAxiomGenerator<OWLPropertyAssertionAxiom> {


    @Override
	protected void addAxioms(OWLNamedIndividual entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLPropertyAssertionAxiom> result) {
        for (OWLObjectProperty prop : reasoner.getRootOntology().getObjectPropertiesInSignature(true)) {
            for (OWLNamedIndividual value : reasoner.getObjectPropertyValues(entity, prop).getFlattened()) {
                result.add(dataFactory.getOWLObjectPropertyAssertionAxiom(prop, entity, value));
            }

        }
        for (OWLDataProperty prop : reasoner.getRootOntology().getDataPropertiesInSignature(true)) {
            for (OWLLiteral value : reasoner.getDataPropertyValues(entity, prop)) {
                result.add(dataFactory.getOWLDataPropertyAssertionAxiom(prop, entity, value));
            }

        }


    }


    public String getLabel() {
        return "Property assertions (property values)";
    }
}
