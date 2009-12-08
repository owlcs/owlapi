package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
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
public class OWLDeclarationAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLEntity entity;

    private Set<OWLAnnotation> entityAnnotations;

    public OWLDeclarationAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        entity = null;
        if (entityAnnotations != null) {
            entityAnnotations.clear();
        }
    }


    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        entity = (OWLClass) handler.getOWLObject();
    }


    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }


    public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }


    public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }


    public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }


    public void handleChild(OWLIndividualElementHandler handler) {
        entity = handler.getOWLObject();
    }


    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLDeclarationAxiom(entity, getAnnotations());
    }


    public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if (entity == null) {
            super.handleChild(handler);
        } else {
            if (entityAnnotations == null) {
                entityAnnotations = new HashSet<OWLAnnotation>();
            }
            entityAnnotations.add(handler.getOWLObject());
        }
    }

    public Set<OWLAnnotation> getEntityAnnotations() {
        if (entityAnnotations == null) {
            return Collections.emptySet();
        } else {
            return entityAnnotations;
        }
    }
}
