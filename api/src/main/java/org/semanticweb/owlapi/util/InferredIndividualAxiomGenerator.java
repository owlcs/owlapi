package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public abstract class InferredIndividualAxiomGenerator<A extends OWLIndividualAxiom> extends InferredEntityAxiomGenerator<OWLNamedIndividual, A> {


    @Override
	protected Set<OWLNamedIndividual> getEntities(OWLOntology ont) {
        return ont.getIndividualsInSignature();
    }


}
