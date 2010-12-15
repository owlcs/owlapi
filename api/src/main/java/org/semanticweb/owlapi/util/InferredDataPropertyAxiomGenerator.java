package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public abstract class InferredDataPropertyAxiomGenerator<A extends OWLDataPropertyAxiom> extends InferredEntityAxiomGenerator<OWLDataProperty, A> {

    @Override
	protected Set<OWLDataProperty> getEntities(OWLOntology ont) {
        return ont.getDataPropertiesInSignature();
    }
}
