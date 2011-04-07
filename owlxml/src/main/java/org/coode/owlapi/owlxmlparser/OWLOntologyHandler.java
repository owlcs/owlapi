/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */  @SuppressWarnings("unused")
public class OWLOntologyHandler extends AbstractOWLElementHandler<OWLOntology> {

    public OWLOntologyHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
    }


    @Override
	public void attribute(String name, String value) throws OWLParserException {
        if (name.equals("ontologyIRI")) {
            OWLOntologyID newID = new OWLOntologyID(IRI.create(value), getOntology().getOntologyID().getVersionIRI());
            getOWLOntologyManager().applyChange(new SetOntologyID(getOntology(), newID));
        }
        if(name.equals("versionIRI")) {
            OWLOntologyID newID = new OWLOntologyID(getOntology().getOntologyID().getOntologyIRI(), IRI.create(value));
            getOWLOntologyManager().applyChange(new SetOntologyID(getOntology(), newID));
        }
    }


    @Override
	public void handleChild(AbstractOWLAxiomElementHandler handler) throws OWLXMLParserException {
        OWLAxiom axiom = handler.getOWLObject();
        if(!axiom.isAnnotationAxiom() || getConfiguration().isLoadAnnotationAxioms()) {
            getOWLOntologyManager().applyChange(new AddAxiom(getOntology(), axiom));
        }
    }


    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException {
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
    }


    @Override
	public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        getOWLOntologyManager().applyChange(new AddOntologyAnnotation(getOntology(), handler.getOWLObject()));
    }


    public void endElement() throws OWLParserException, UnloadableImportException {
    }


    public OWLOntology getOWLObject() {
        return getOntology();
    }


    @Override
	public void setParentHandler(OWLElementHandler<?> handler) {

    }
}
