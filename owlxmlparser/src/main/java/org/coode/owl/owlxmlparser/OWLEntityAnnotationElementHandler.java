package org.coode.owl.owlxmlparser;

import org.semanticweb.owl.model.*;
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
 * Date: 20-Mar-2007<br><br>
 */
public class OWLEntityAnnotationElementHandler extends AbstractOWLAxiomElementHandler {

    public OWLEntityAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    private OWLEntity entity;

    private OWLAnnotation annotation;


    public void handleChild(OWLAnnotationElementHandler handler) {
        annotation = handler.getOWLObject();
    }


    public void handleChild(OWLIndividualElementHandler handler) {
        entity = handler.getOWLObject();
    }


    public void handleChild(AbstractOWLDescriptionElementHandler handler) throws OWLXMLParserException {
        if (handler.getOWLObject().isAnonymous()) {
            throw new OWLXMLParserException(getLineNumber(), "Anonymous classes are not allowed in annotations");
        }
        entity = (OWLClass) handler.getOWLObject();
    }


    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        if (handler.getOWLObject().isAnonymous()) {
            throw new OWLXMLParserException(getLineNumber(), "Property expressions are not allowed in annotations");
        }
        entity = (OWLObjectProperty) handler.getOWLObject();
    }


    public void handleChild(OWLDataPropertyElementHandler handler) {
        entity = (OWLDataProperty) handler.getOWLObject();
    }


    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLEntityAnnotationAxiom(entity, annotation);
    }
}
