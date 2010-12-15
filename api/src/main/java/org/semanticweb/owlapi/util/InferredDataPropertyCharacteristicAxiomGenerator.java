package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 * <p/>
 * Generates inferred data property characteristics.
 */
public class InferredDataPropertyCharacteristicAxiomGenerator extends InferredDataPropertyAxiomGenerator<OWLDataPropertyCharacteristicAxiom> {


    @Override
	protected void addAxioms(OWLDataProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLDataPropertyCharacteristicAxiom> result) {
        OWLFunctionalDataPropertyAxiom axiom = dataFactory.getOWLFunctionalDataPropertyAxiom(entity);
        if (reasoner.isEntailed(axiom) && reasoner.isEntailed(axiom)) {
            result.add(axiom);
        }
    }


    public String getLabel() {
        return "Data property characteristics";
    }
}
