package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;

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
public abstract class AbstractNaryBooleanClassExpressionElementHandler extends AbstractClassExpressionElementHandler {

    private Set<OWLClassExpression> operands;

    public AbstractNaryBooleanClassExpressionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        operands = new HashSet<OWLClassExpression>();
    }


    public void handleChild(AbstractClassExpressionElementHandler handler) {
        operands.add(handler.getOWLObject());
    }


    protected void endClassExpressionElement() throws OWLXMLParserException {
        if (operands.size() >= 2) {
            setClassExpression(createClassExpression(operands));
        }
        else if(operands.size() == 1) {
            setClassExpression(operands.iterator().next());
        }
        else {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Found zero child elements of an " + getElementName() + " element. At least 2 class expression elements are required as child elements of " + getElementName() + " elements");
        }
    }

    protected abstract OWLClassExpression createClassExpression(Set<OWLClassExpression> operands);

}
