package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.reasoner.Node;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLClassNodeSet extends DefaultNodeSet<OWLClass> {

    public OWLClassNodeSet() {
    }

    public OWLClassNodeSet(OWLClass entity) {
        super(entity);
    }

    public OWLClassNodeSet(Node<OWLClass> owlClassNode) {
        super(owlClassNode);
    }

    public OWLClassNodeSet(Set<Node<OWLClass>> nodes) {
        super(nodes);
    }

    @Override
	protected DefaultNode<OWLClass> getNode(OWLClass entity) {
        return NodeFactory.getOWLClassNode(entity);
    }

    @Override
	protected DefaultNode<OWLClass> getNode(Set<OWLClass> entities) {
        return NodeFactory.getOWLClassNode(entities);
    }


}
