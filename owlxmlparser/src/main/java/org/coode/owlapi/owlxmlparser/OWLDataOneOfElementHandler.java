package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLTypedLiteral;

import java.util.HashSet;
import java.util.Set;
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
public class OWLDataOneOfElementHandler extends AbstractOWLDataRangeHandler {

    Set<OWLTypedLiteral> constants;

    public OWLDataOneOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        constants = new HashSet<OWLTypedLiteral>();
    }


    public void handleChild(OWLLiteralElementHandler handler) {
        if (handler.getOWLObject().isOWLTypedLiteral()) {
            constants.add((OWLTypedLiteral) handler.getOWLObject());
        } else {
            // Type as string?
            OWLLiteral currentLiteral = handler.getOWLObject();
            constants.add(getOWLDataFactory().getOWLTypedLiteral(currentLiteral.getLiteral()));
        }
    }


    protected void endDataRangeElement() throws OWLXMLParserException {
        if (constants.isEmpty()) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "data oneOf element");
        }
        setDataRange(getOWLDataFactory().getOWLDataOneOf(constants));
    }
}
