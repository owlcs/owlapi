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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;


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


    @Override
	public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        entity = null;
        if (entityAnnotations != null) {
            entityAnnotations.clear();
        }
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        entity = (OWLClass) handler.getOWLObject();
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }


    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }


    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }


    @Override
	public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject();
    }


    @Override
	public void handleChild(OWLIndividualElementHandler handler) {
        entity = handler.getOWLObject();
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLDeclarationAxiom(entity, getAnnotations());
    }


    @Override
	public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if (entity == null) {
            super.handleChild(handler);
        }
        else {
            if (entityAnnotations == null) {
                entityAnnotations = new HashSet<OWLAnnotation>();
            }
            entityAnnotations.add(handler.getOWLObject());
        }
    }

    public Set<OWLAnnotation> getEntityAnnotations() {
        if (entityAnnotations == null) {
            return Collections.emptySet();
        }
        else {
            return entityAnnotations;
        }
    }
}
