/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
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
 * Copyright 2014, The University of Manchester
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

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/** @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group
 * @since 2.0.0
 * @param <O>
 *            handled type */
public abstract class AbstractOWLElementHandler<O> implements
        OWLElementHandler<O> {
    protected OWLXMLParserHandler handler;
    private OWLElementHandler<?> parentHandler;
    private StringBuilder sb;
    private String elementName;

    /** @param handler
     *            owlxml handler */
    protected AbstractOWLElementHandler(OWLXMLParserHandler handler) {
        this.handler = handler;
    }

    /** @param localName
     *            localName
     * @param value
     *            value
     * @return iri */
    protected IRI getIRIFromAttribute(String localName, String value) {
        if (localName.equals(IRI_ATTRIBUTE.getShortName())) {
            return handler.getIRI(value);
        } else if (localName.equals(ABBREVIATED_IRI_ATTRIBUTE.getShortName())) {
            return handler.getAbbreviatedIRI(value);
        } else if (localName.equals("URI")) {
            // Legacy
            return handler.getIRI(value);
        }
        ensureAttributeNotNull(null, IRI_ATTRIBUTE.getShortName());
        return null;
    }

    protected IRI
            getIRIFromElement(String elementLocalName, String textContent) {
        if (elementLocalName.equals(IRI_ELEMENT.getShortName())) {
            return handler.getIRI(textContent.trim());
        } else if (elementLocalName.equals(ABBREVIATED_IRI_ELEMENT
                .getShortName())) {
            return handler.getAbbreviatedIRI(textContent.trim());
        }
        throw new OWLXMLParserException(handler, elementLocalName
                + " is not an IRI element");
    }

    protected OWLOntologyManager getOWLOntologyManager() {
        return handler.getOWLOntologyManager();
    }

    protected OWLOntology getOntology() {
        return handler.getOntology();
    }

    protected OWLDataFactory getOWLDataFactory() {
        return handler.getDataFactory();
    }

    @Override
    public void setParentHandler(OWLElementHandler<?> handler) {
        this.parentHandler = handler;
    }

    protected OWLElementHandler<?> getParentHandler() {
        return parentHandler;
    }

    @Override
    public void attribute(String localName, String value) {}

    @Override
    public void startElement(String name) {
        sb = null;
        elementName = name;
    }

    protected String getElementName() {
        return elementName;
    }

    @Override
    public void handleChild(AbstractOWLAxiomElementHandler _handler) {}

    @Override
    public void handleChild(AbstractClassExpressionElementHandler _handler) {}

    @Override
    public void handleChild(AbstractOWLDataRangeHandler _handler) {}

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler _handler) {}

    @Override
    public void handleChild(OWLDataPropertyElementHandler _handler) {}

    @Override
    public void handleChild(OWLIndividualElementHandler _handler) {}

    @Override
    public void handleChild(OWLLiteralElementHandler _handler) {}

    @Override
    public void handleChild(OWLAnnotationElementHandler _handler) {}

    @Override
    public void handleChild(OWLSubObjectPropertyChainElementHandler _handler) {}

    @Override
    public void handleChild(OWLDatatypeFacetRestrictionElementHandler _handler) {}

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler _handler) {}

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler _handler) {}

    @Override
    public void handleChild(AbstractIRIElementHandler _handler) {}

    @Override
    public void handleChild(SWRLVariableElementHandler _handler) {}

    @Override
    public void handleChild(SWRLAtomElementHandler _handler) {}

    @Override
    public void handleChild(SWRLAtomListElementHandler _handler) {}

    protected void ensureNotNull(Object element, String message) {
        if (element == null) {
            throw new OWLXMLParserElementNotFoundException(handler, message);
        }
    }

    protected void ensureAttributeNotNull(Object element, String message) {
        if (element == null) {
            throw new OWLXMLParserAttributeNotFoundException(handler, message);
        }
    }

    @Override
    public void handleChars(char[] chars, int start, int length) {
        if (isTextContentPossible()) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(chars, start, length);
        }
    }

    @Override
    public String getText() {
        if (sb == null) {
            return "";
        } else {
            return sb.toString();
        }
    }

    @Override
    public boolean isTextContentPossible() {
        return false;
    }
}
