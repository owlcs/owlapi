package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.reasoner.Node;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLDataPropertyNodeSet extends DefaultNodeSet<OWLDataProperty> {

    public OWLDataPropertyNodeSet() {
    }

    public OWLDataPropertyNodeSet(OWLDataProperty entity) {
        super(entity);
    }

    public OWLDataPropertyNodeSet(Node<OWLDataProperty> owlDataPropertyNode) {
        super(owlDataPropertyNode);
    }

    public OWLDataPropertyNodeSet(Set<Node<OWLDataProperty>> nodes) {
        super(nodes);
    }

    @Override
	protected DefaultNode<OWLDataProperty> getNode(OWLDataProperty entity) {
        return NodeFactory.getOWLDataPropertyNode(entity);
    }

    @Override
	protected DefaultNode<OWLDataProperty> getNode(Set<OWLDataProperty> entities) {
        return NodeFactory.getOWLDataPropertyNode(entities);
    }
}
