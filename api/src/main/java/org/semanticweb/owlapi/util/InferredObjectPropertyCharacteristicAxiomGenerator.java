package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredObjectPropertyCharacteristicAxiomGenerator extends InferredObjectPropertyAxiomGenerator<OWLObjectPropertyCharacteristicAxiom> {


    @Override
	protected void addAxioms(OWLObjectProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLObjectPropertyCharacteristicAxiom> result) {
        addIfEntailed(dataFactory.getOWLFunctionalObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLInverseFunctionalObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLSymmetricObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLAsymmetricObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLTransitiveObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLReflexiveObjectPropertyAxiom(entity), reasoner, result);
        addIfEntailed(dataFactory.getOWLIrreflexiveObjectPropertyAxiom(entity), reasoner, result);
    }

    protected void addIfEntailed(OWLObjectPropertyCharacteristicAxiom axiom, OWLReasoner reasoner, Set<OWLObjectPropertyCharacteristicAxiom> result) {
        if(reasoner.isEntailmentCheckingSupported(axiom.getAxiomType()) && reasoner.isEntailed(axiom)) {
            result.add(axiom);
        }
    }

    public String getLabel() {
        return "Object property characteristics";
    }
}
