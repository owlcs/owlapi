package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 * <p/>
 * Generates inferred equivalent classes axioms.
 */
public class InferredEquivalentClassAxiomGenerator extends InferredClassAxiomGenerator<OWLEquivalentClassesAxiom> {


    @Override
	protected void addAxioms(OWLClass entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLEquivalentClassesAxiom> result) {
        Set<OWLClassExpression> equivalentClasses = new HashSet<OWLClassExpression>(reasoner.getEquivalentClasses(entity).getEntities());
        equivalentClasses.add(entity);
        if (equivalentClasses.size() > 1) {
            result.add(dataFactory.getOWLEquivalentClassesAxiom(equivalentClasses));
        }
    }


    public String getLabel() {
        return "Equivalent classes";
    }
}
