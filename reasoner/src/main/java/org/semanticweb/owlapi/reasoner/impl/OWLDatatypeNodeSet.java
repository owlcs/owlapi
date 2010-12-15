package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.reasoner.Node;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Jan-2010
 */
public class OWLDatatypeNodeSet extends DefaultNodeSet<OWLDatatype> {

    public OWLDatatypeNodeSet() {
    }

    public OWLDatatypeNodeSet(OWLDatatype entity) {
        super(entity);
    }

    public OWLDatatypeNodeSet(Node<OWLDatatype> owlDatatypeNode) {
        super(owlDatatypeNode);
    }

    public OWLDatatypeNodeSet(Set<Node<OWLDatatype>> nodes) {
        super(nodes);
    }

    @Override
    protected DefaultNode<OWLDatatype> getNode(OWLDatatype entity) {
        return new OWLDatatypeNode(entity);
    }

    @Override
    protected DefaultNode<OWLDatatype> getNode(Set<OWLDatatype> entities) {
        return new OWLDatatypeNode(entities);
    }
}
