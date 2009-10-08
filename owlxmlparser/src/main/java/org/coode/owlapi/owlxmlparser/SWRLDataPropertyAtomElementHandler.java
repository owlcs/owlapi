package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.SWRLDArgument;
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
 * Date: 08-Oct-2009
 */
public class SWRLDataPropertyAtomElementHandler extends SWRLAtomElementHandler {


    private OWLDataPropertyExpression prop;

    private SWRLIArgument arg0 = null;

    private SWRLDArgument arg1 = null;


    public SWRLDataPropertyAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        prop = handler.getOWLObject();
    }

    @Override
    public void handleChild(SWRLIndividualVariableElementHandler handler) throws OWLXMLParserException {
        arg0 = handler.getOWLObject();
    }

    @Override
    public void handleChild(SWRLLiteralVariableElementHandler handler) throws OWLXMLParserException {
        arg1 = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLConstantElementHandler handler) throws OWLXMLParserException {
        arg1 = getOWLDataFactory().getSWRLLiteralArgument(handler.getOWLObject());
    }

    public void endElement() throws OWLXMLParserException {
        setAtom(getOWLDataFactory().getSWRLDataPropertyAtom(prop, arg0, arg1));
        getParentHandler().handleChild(this);
    }
}
