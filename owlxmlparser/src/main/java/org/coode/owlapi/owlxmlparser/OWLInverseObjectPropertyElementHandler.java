package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
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
public class OWLInverseObjectPropertyElementHandler extends AbstractOWLObjectPropertyElementHandler {

    private OWLObjectPropertyExpression inverse;

    public OWLInverseObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        inverse = handler.getOWLObject();
    }


    protected void endObjectPropertyElement() throws OWLXMLParserException {
        if(inverse == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), OWLXMLVocabulary.OBJECT_INVERSE_OF.getShortName());
        }
        setOWLObjectPropertyExpression(getOWLDataFactory().getOWLObjectInverseOf(inverse));
    }
}
