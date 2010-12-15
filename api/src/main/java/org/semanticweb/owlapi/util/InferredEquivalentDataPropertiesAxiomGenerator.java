package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredEquivalentDataPropertiesAxiomGenerator extends InferredDataPropertyAxiomGenerator<OWLEquivalentDataPropertiesAxiom> {


    @Override
	protected void addAxioms(OWLDataProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLEquivalentDataPropertiesAxiom> result) {
        Set<OWLDataProperty> props = new HashSet<OWLDataProperty>(reasoner.getEquivalentDataProperties(entity).getEntities());
        props.add(entity);
        result.add(dataFactory.getOWLEquivalentDataPropertiesAxiom(props));
    }


    public String getLabel() {
        return "Equivalent data properties";
    }
}
