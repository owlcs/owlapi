package org.semanticweb.owlapi.reasoner.impl;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWLDataFactoryVocabulary;

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
public class OWLObjectPropertyNode extends DefaultNode<OWLObjectPropertyExpression> {


    private static final OWLObjectProperty TOP_OBJECT_PROPERTY = OWLDataFactoryVocabulary.OWLTopObjectProperty;

    private static final OWLObjectPropertyNode TOP_NODE = new OWLObjectPropertyNode(TOP_OBJECT_PROPERTY);

    private static final OWLObjectProperty BOTTOM_OBJECT_PROPERTY = OWLDataFactoryVocabulary.OWLBottomObjectProperty;

    private static final OWLObjectPropertyNode BOTTOM_NODE = new OWLObjectPropertyNode(BOTTOM_OBJECT_PROPERTY);

    public OWLObjectPropertyNode() {
    }

    public OWLObjectPropertyNode(OWLObjectPropertyExpression entity) {
        super(entity);
    }

    public OWLObjectPropertyNode(Set<OWLObjectPropertyExpression> entities) {
        super(entities);
    }

    protected OWLObjectProperty getTopEntity() {
        return TOP_OBJECT_PROPERTY;
    }

    protected OWLObjectProperty getBottomEntity() {
        return BOTTOM_OBJECT_PROPERTY;
    }
    
    public static OWLObjectPropertyNode getTopNode() {
        return TOP_NODE;
    }
    
    public static OWLObjectPropertyNode getBottomNode() {
        return BOTTOM_NODE;
    }
}
