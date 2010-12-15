package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class InferredEquivalentObjectPropertyAxiomGenerator extends InferredObjectPropertyAxiomGenerator<OWLEquivalentObjectPropertiesAxiom> {


    @Override
	protected void addAxioms(OWLObjectProperty entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLEquivalentObjectPropertiesAxiom> result) {
        Set<OWLObjectPropertyExpression> equivProps = new HashSet<OWLObjectPropertyExpression>(reasoner.getEquivalentObjectProperties(entity).getEntities());
        equivProps.add(entity);
        result.add(dataFactory.getOWLEquivalentObjectPropertiesAxiom(equivProps));
    }


    public String getLabel() {
        return "Equivalent object properties";
    }
}
