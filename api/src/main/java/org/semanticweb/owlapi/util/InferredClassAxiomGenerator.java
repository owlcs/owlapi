package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public abstract class InferredClassAxiomGenerator<A extends OWLClassAxiom> extends InferredEntityAxiomGenerator<OWLClass, A> {

    @Override
	protected Set<OWLClass> getEntities(OWLOntology ont) {
        return ont.getClassesInSignature();
    }
}
