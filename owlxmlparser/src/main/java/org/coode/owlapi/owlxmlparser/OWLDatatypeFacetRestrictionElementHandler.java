package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.io.OWLParserException;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 10-Apr-2007<br><br>
 */
public class OWLDatatypeFacetRestrictionElementHandler extends AbstractOWLElementHandler<OWLFacetRestriction> {

    private OWLFacet facet;

    private OWLTypedLiteral constant;

    public OWLDatatypeFacetRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void handleChild(OWLLiteralElementHandler handler) throws OWLXMLParserException {
        OWLLiteral con = handler.getOWLObject();
        if (con.isOWLTypedLiteral()) {
            constant = (OWLTypedLiteral) handler.getOWLObject();
        } else {
            throw new OWLXMLParserException("Found untyped constant " + constant + ", expected typed constant", getLineNumber(), getColumnNumber());
        }
    }

    public void attribute(String localName, String value) throws OWLParserException {
        if (localName.equals("facet")) {
            facet = OWLFacet.getFacet(IRI.create(value));
        }
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        getParentHandler().handleChild(this);
    }


    public OWLFacetRestriction getOWLObject() {
        return getOWLDataFactory().getOWLFacetRestriction(facet, constant);
    }
}
