package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import java.util.List;
/*
 * Copyright (C) 2006, University of Manchester
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
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLSubObjectPropertyOfAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLObjectPropertyExpression subProperty;

    private List<OWLObjectPropertyExpression> propertyList;

    private OWLObjectPropertyExpression superProperty;


    public OWLSubObjectPropertyOfAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        if (subProperty == null && propertyList == null) {
            subProperty = handler.getOWLObject();
        } else if (superProperty == null) {
            superProperty = handler.getOWLObject();
        } else {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected two object property expression elements");
        }
    }


    public void handleChild(OWLSubObjectPropertyChainElementHandler handler) {
        propertyList = handler.getOWLObject();
    }


    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (subProperty != null) {
            return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, getAnnotations());
        } else {
            return getOWLDataFactory().getOWLSubPropertyChainOfAxiom(propertyList, superProperty, getAnnotations());
        }
    }
}
