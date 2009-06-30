package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLClassExpression;

import java.util.Set;
import java.util.HashSet;
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
 * Date: 20-May-2009
 */
public class OWLHasKeyElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLClassExpression ce;

    private Set<OWLPropertyExpression> props = new HashSet<OWLPropertyExpression>();

    public OWLHasKeyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        props.clear();
    }

    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        ce = handler.getOWLObject();
    }

    public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        props.add(handler.getOWLObject());
    }

    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        props.add(handler.getOWLObject());
    }

    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLHasKeyAxiom(ce, props, getAnnotations());
    }
}
