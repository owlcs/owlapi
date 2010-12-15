package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLNamedIndividualNode extends DefaultNode<OWLNamedIndividual> {

    public OWLNamedIndividualNode() {
    }

    public OWLNamedIndividualNode(OWLNamedIndividual entity) {
        super(entity);
    }

    public OWLNamedIndividualNode(Set<OWLNamedIndividual> entities) {
        super(entities);
    }

    @Override
	protected OWLNamedIndividual getTopEntity() {
        return null;
    }

    @Override
	protected OWLNamedIndividual getBottomEntity() {
        return null;
    }
}
