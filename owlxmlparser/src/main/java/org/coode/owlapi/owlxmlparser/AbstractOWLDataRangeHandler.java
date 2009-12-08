package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.io.OWLParserException;
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
 * Date: 13-Dec-2006<br><br>
 */
public abstract class AbstractOWLDataRangeHandler extends AbstractOWLElementHandler<OWLDataRange> {

    private OWLDataRange dataRange;

    protected AbstractOWLDataRangeHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void setDataRange(OWLDataRange dataRange) {
        this.dataRange = dataRange;
    }


    final public OWLDataRange getOWLObject() {
        return dataRange;
    }


    final public void endElement() throws OWLParserException, UnloadableImportException {
        endDataRangeElement();
        getParentHandler().handleChild(this);
    }

    protected abstract void endDataRangeElement() throws OWLXMLParserException;

}
