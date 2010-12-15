package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.Node;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLNamedIndividualNodeSet extends DefaultNodeSet<OWLNamedIndividual> {

    public OWLNamedIndividualNodeSet() {
    }

    public OWLNamedIndividualNodeSet(OWLNamedIndividual entity) {
        super(entity);
    }

    public OWLNamedIndividualNodeSet(Node<OWLNamedIndividual> owlNamedIndividualNode) {
        super(owlNamedIndividualNode);
    }

    public OWLNamedIndividualNodeSet(Set<Node<OWLNamedIndividual>> nodes) {
        super(nodes);
    }

    @Override
	protected DefaultNode<OWLNamedIndividual> getNode(OWLNamedIndividual entity) {
        return NodeFactory.getOWLNamedIndividualNode(entity);
    }

    @Override
	protected DefaultNode<OWLNamedIndividual> getNode(Set<OWLNamedIndividual> entities) {
        return NodeFactory.getOWLNamedIndividualNode(entities);
    }
}
