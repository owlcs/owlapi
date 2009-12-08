package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.io.OWLParserException;

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
public abstract class AbstractOWLAxiomElementHandler extends AbstractOWLElementHandler<OWLAxiom> {

    private OWLAxiom axiom;

    private Set<OWLAnnotation> annotations;

    public AbstractOWLAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public OWLAxiom getOWLObject() {
        return axiom;
    }


    public void setAxiom(OWLAxiom axiom) {
        this.axiom = axiom;
    }


    public void startElement(String name) throws OWLXMLParserException {
        if (annotations != null) {
            annotations.clear();
        }
    }


    public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if(annotations == null) {
            annotations = new HashSet<OWLAnnotation>();
        }
        annotations.add(handler.getOWLObject());
    }


    final public void endElement() throws OWLParserException, UnloadableImportException {
        setAxiom(createAxiom());
        getParentHandler().handleChild(this);
    }

    public Set<OWLAnnotation> getAnnotations() {
        if(annotations == null) {
            return Collections.emptySet();
        }
        else {
            return annotations;
        }
    }



    protected abstract OWLAxiom createAxiom() throws OWLXMLParserException;

}
