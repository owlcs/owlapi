package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 * <p/>
 * Generates inferred disjoint axioms - note that this currently uses a very simple
 * inefficient algorithm.
 */
public class InferredDisjointClassesAxiomGenerator extends InferredClassAxiomGenerator<OWLDisjointClassesAxiom> {


    @Override
	protected void addAxioms(OWLClass entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<OWLDisjointClassesAxiom> result) {
        for (OWLClass cls : getAllEntities(reasoner)) {
            if (!cls.equals(entity)) {
                if (!reasoner.isSatisfiable(dataFactory.getOWLObjectIntersectionOf(CollectionFactory.createSet(entity, cls)))) {
                    result.add(dataFactory.getOWLDisjointClassesAxiom(entity, cls));
                }
            }
        }
    }


    public String getLabel() {
        return "Disjoint classes";
    }
}
