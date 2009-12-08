package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.io.OWLParserException;
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
public class SWRLClassAtomElementHandler extends SWRLAtomElementHandler {

    private OWLClassExpression ce;

    private SWRLIArgument arg;

    public SWRLClassAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler) throws OWLXMLParserException {
        arg = handler.getOWLObject();
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        ce = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException {
        arg = getOWLDataFactory().getSWRLIndividualArgument(handler.getOWLObject());
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLClassAtom(ce, arg));
        getParentHandler().handleChild(this);
    }
}
