package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.reasoner.Node;

import java.util.Set;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLObjectPropertyNodeSet extends DefaultNodeSet<OWLObjectProperty> {

    public OWLObjectPropertyNodeSet() {
    }

    public OWLObjectPropertyNodeSet(OWLObjectProperty entity) {
        super(entity);
    }

    public OWLObjectPropertyNodeSet(Node<OWLObjectProperty> owlObjectPropertyNode) {
        super(owlObjectPropertyNode);
    }

    public OWLObjectPropertyNodeSet(Set<Node<OWLObjectProperty>> nodes) {
        super(nodes);
    }

    protected DefaultNode<OWLObjectProperty> getNode(OWLObjectProperty entity) {
        return NodeFactory.getOWLObjectPropertyNode(entity);
    }

    protected DefaultNode<OWLObjectProperty> getNode(Set<OWLObjectProperty> entities) {
        return NodeFactory.getOWLObjectPropertyNode(entities);
    }
}
