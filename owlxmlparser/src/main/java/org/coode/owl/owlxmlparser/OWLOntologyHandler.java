package org.coode.owl.owlxmlparser;

import org.semanticweb.owl.model.*;

import java.net.URI;
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
 * Date: 13-Dec-2006<br><br>
 */
public class OWLOntologyHandler extends AbstractOWLElementHandler<OWLOntology> {

    public OWLOntologyHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void startElement(String name) throws OWLXMLParserException {
    }


    public void attribute(String name, String value) throws OWLXMLParserException {
        if(name.equals("URI")) {
            try {
                getOWLOntologyManager().applyChange(new SetOntologyURI(getOntology(), URI.create(value)));
            }
            catch (OWLOntologyChangeException e) {
                throw new OWLXMLParserException(getLineNumber(), e);
            }
        }
    }


    public void handleChild(AbstractOWLAxiomElementHandler handler) throws OWLXMLParserException {
        try {
            getOWLOntologyManager().applyChange(new AddAxiom(getOntology(), handler.getOWLObject()));
            Set<OWLAnnotation> annotations = handler.getAnnotations();
            for(OWLAnnotation anno : annotations) {
                OWLAxiom ax = handler.getOWLObject();
                OWLAxiom annoAx = getOWLDataFactory().getOWLAxiomAnnotationAxiom(ax, anno);
                getOWLOntologyManager().addAxiom(getOntology(), annoAx);
            }
        }
        catch (OWLOntologyChangeException e) {
            throw new OWLXMLParserException(getLineNumber(), e);
        }
    }


    public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException {
        throw new OWLXMLParserElementNotFoundException(getLineNumber(), "Encountered a data range, but was expecting an axiom.");
    }


    public void handleChild(AbstractOWLDescriptionElementHandler handler) throws OWLXMLParserException {
        throw new OWLXMLParserElementNotFoundException(getLineNumber(), "Encountered a description, but was expecting an axiom.");
    }


    public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        OWLOntologyAnnotationAxiom ax = getOWLDataFactory().getOWLOntologyAnnotationAxiom(getOntology(), handler.getOWLObject());
        try {
            getOWLOntologyManager().applyChange(new AddAxiom(getOntology(), ax));
        }
        catch (OWLOntologyChangeException e) {
            throw new OWLXMLParserException(getLineNumber(), e);
        }
    }


    public void endElement() {
    }


    public OWLOntology getOWLObject() {
        return getOntology();
    }


    public void setParentHandler(OWLElementHandler handler) {

    }
}
