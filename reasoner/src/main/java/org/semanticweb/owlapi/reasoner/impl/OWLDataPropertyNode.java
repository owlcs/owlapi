package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.model.OWLDataProperty;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

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
public class OWLDataPropertyNode extends DefaultNode<OWLDataProperty> {
    
    
    private static final OWLDataProperty TOP_DATA_PROPERTY = OWLDataFactoryImpl.getInstance().getOWLTopDataProperty();
    
    private static final OWLDataPropertyNode TOP_NODE = new OWLDataPropertyNode(TOP_DATA_PROPERTY);

    private static final OWLDataProperty BOTTOM_DATA_PROPERTY = OWLDataFactoryImpl.getInstance().getOWLBottomDataProperty();
    
    private static final OWLDataPropertyNode BOTTOM_NODE = new OWLDataPropertyNode(BOTTOM_DATA_PROPERTY);

    public OWLDataPropertyNode() {
    }

    public OWLDataPropertyNode(OWLDataProperty entity) {
        super(entity);
    }

    public OWLDataPropertyNode(Set<OWLDataProperty> entities) {
        super(entities);
    }

    protected OWLDataProperty getTopEntity() {
        return TOP_DATA_PROPERTY;
    }

    protected OWLDataProperty getBottomEntity() {
        return BOTTOM_DATA_PROPERTY;
    }
    
    public static OWLDataPropertyNode getTopNode() {
        return TOP_NODE;
    }
    
    public static OWLDataPropertyNode getBottomNode() {
        return BOTTOM_NODE;
    }
}
