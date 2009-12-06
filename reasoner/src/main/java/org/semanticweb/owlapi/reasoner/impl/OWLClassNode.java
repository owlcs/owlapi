package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.OWLClass;

import java.util.Set;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
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
public class OWLClassNode extends DefaultNode<OWLClass> {

    private static final OWLClass TOP_CLASS = OWLDataFactoryImpl.getInstance().getOWLThing();

    private static final OWLClassNode TOP_NODE = new OWLClassNode(TOP_CLASS);

    private static final OWLClass BOTTOM_CLASS = OWLDataFactoryImpl.getInstance().getOWLNothing();
    
    private static final OWLClassNode BOTTOM_NODE = new OWLClassNode(BOTTOM_CLASS);

    public OWLClassNode(OWLClass entity) {
        super(entity);
    }

    public OWLClassNode(Set<OWLClass> entities) {
        super(entities);
    }

    public OWLClassNode() {
    }

    protected OWLClass getTopEntity() {
        return TOP_CLASS;
    }

    protected OWLClass getBottomEntity() {
        return BOTTOM_CLASS;
    }

    public static OWLClassNode getTopNode() {
        return TOP_NODE;
    }

    public static OWLClassNode getBottomNode() {
        return BOTTOM_NODE;
    }
}
