package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.Node;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLObjectPropertyNodeSet extends DefaultNodeSet<OWLObjectPropertyExpression> {

    public OWLObjectPropertyNodeSet() {
    }

    public OWLObjectPropertyNodeSet(OWLObjectPropertyExpression entity) {
        super(entity);
    }

    public OWLObjectPropertyNodeSet(Node<OWLObjectPropertyExpression> owlObjectPropertyNode) {
        super(owlObjectPropertyNode);
    }

    public OWLObjectPropertyNodeSet(Set<Node<OWLObjectPropertyExpression>> nodes) {
        super(nodes);
    }

    @Override
	protected DefaultNode<OWLObjectPropertyExpression> getNode(OWLObjectPropertyExpression entity) {
        return NodeFactory.getOWLObjectPropertyNode(entity);
    }

    @Override
	protected DefaultNode<OWLObjectPropertyExpression> getNode(Set<OWLObjectPropertyExpression> entities) {
        return NodeFactory.getOWLObjectPropertyNode(entities);
    }
}
