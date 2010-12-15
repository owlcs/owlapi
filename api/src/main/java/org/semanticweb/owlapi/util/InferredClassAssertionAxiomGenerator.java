package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 * <p/>
 * Generates <code>OWLClassAssertionsAxiom</code>s for inferred individual types.
 */
public class InferredClassAssertionAxiomGenerator extends InferredIndividualAxiomGenerator<OWLClassAssertionAxiom> {


    @Override
	protected void addAxioms(OWLNamedIndividual entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLClassAssertionAxiom> result) {
        for (OWLClass type : reasoner.getTypes(entity, false).getFlattened()) {
            result.add(dataFactory.getOWLClassAssertionAxiom(type, entity));
        }
    }


    public String getLabel() {
        return "Class assertions (individual types)";
    }
}
