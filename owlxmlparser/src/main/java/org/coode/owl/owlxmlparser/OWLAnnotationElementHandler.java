package org.coode.owl.owlxmlparser;

import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLLiteral;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.vocab.OWLXMLVocabulary;

import java.net.URI;

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
 * Date: 18-Dec-2006<br><br>
 */
public class OWLAnnotationElementHandler extends AbstractOWLElementHandler<OWLAnnotation> {

    private OWLAnnotation annotation;

    private URI uri;

    private String datatype;

    private OWLLiteral literal;

    private OWLIndividual individual;

    private String annoURIAttName = OWLXMLVocabulary.ANNOTATION_URI.getShortName();

    public OWLAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        literal = null;
        individual = null;
    }


    public void attribute(String localName, String value) throws OWLXMLParserException {
        if(localName.equals(annoURIAttName)) {
            uri = getURI(value);
        }
        else if(localName.equals("Datatype")) {
            datatype = value;
        }
    }


    public void endElement() throws OWLXMLParserException {
        if(literal == null) {
            if(individual == null) {
                OWLXMLParserException ex = new OWLXMLParserException(getLineNumber(), "Expected literal or individual as annotation content");
                ex.setLineNumber(getLineNumber());
                throw ex;
            }
            else {
                annotation = getOWLDataFactory().getOWLObjectAnnotation(uri, individual);
            }
        }
        else {
            annotation = getOWLDataFactory().getOWLConstantAnnotation(uri, literal);
        }
        getParentHandler().handleChild(this);
    }


    public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException {
        individual = handler.getOWLObject();
    }


    public void handleChild(OWLConstantElementHandler handler) throws OWLXMLParserException {
        literal = handler.getOWLObject();
    }

//    public void handleChars(OWLConstantElementHandler handler) {
//        literal = handler.getOWLObject();
//    }
//
//
//    public void handleChars(OWLIndividualElementHandler handler) {
//        individual = handler.getOWLObject();
//    }


    public OWLAnnotation getOWLObject() {
        return annotation;
    }


    public boolean isTextContentPossible() {
        return false;
    }
}
