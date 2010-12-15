package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public abstract class InferredObjectPropertyAxiomGenerator<A extends OWLObjectPropertyAxiom> extends InferredEntityAxiomGenerator<OWLObjectProperty, A> {


    @Override
	protected Set<OWLObjectProperty> getEntities(OWLOntology ont) {
        return ont.getObjectPropertiesInSignature();
    }
}
