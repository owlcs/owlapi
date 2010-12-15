package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.UnloadableImportException;

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

    private Set<OWLAnnotation> annotations;

    private OWLAnnotationProperty property;

    private OWLAnnotationValue object;

    public OWLAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        getParentHandler().handleChild(this);
    }

    @Override
	public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if(annotations == null) {
            annotations = new HashSet<OWLAnnotation>();
        }
        annotations.add(handler.getOWLObject());
    }

    @Override
	public void handleChild(OWLAnonymousIndividualElementHandler handler) throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
	public void handleChild(OWLLiteralElementHandler handler) throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
	public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException {
        property = handler.getOWLObject();
    }

    @Override
	public void handleChild(AbstractIRIElementHandler handler) throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    public OWLAnnotation getOWLObject() {
        if (annotations == null) {
            return getOWLDataFactory().getOWLAnnotation(property, object);
        }
        else {
            return getOWLDataFactory().getOWLAnnotation(property, object, annotations);
        }
    }


    @Override
	public boolean isTextContentPossible() {
        return false;
    }
}
