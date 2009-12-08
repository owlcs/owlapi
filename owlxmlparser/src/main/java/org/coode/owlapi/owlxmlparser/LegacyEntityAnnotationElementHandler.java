package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
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
 * Date: 21-May-2009
 */
public class LegacyEntityAnnotationElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLEntity entity;

    private OWLAnnotation annotation;

    public LegacyEntityAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        OWLAnnotation anno = annotation;
        annotation = null;
        entity = null;
        return getOWLDataFactory().getOWLAnnotationAssertionAxiom(anno.getProperty(), entity.getIRI(), anno.getValue());
    }

    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject().asOWLClass();
    }

    public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject().asOWLDataProperty();
    }

    public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject();
    }

    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject().asOWLObjectProperty();
    }

    public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if (entity == null) {
            super.handleChild(handler);
        }
        else {
            annotation = handler.getOWLObject();
        }
    }
}

