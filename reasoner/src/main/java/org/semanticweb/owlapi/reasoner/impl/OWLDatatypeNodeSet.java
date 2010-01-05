package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.OWLDatatype;
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
