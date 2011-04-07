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
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public abstract class AbstractOWLElementHandler<O> implements OWLElementHandler<O> {

    private OWLXMLParserHandler handler;

    private OWLElementHandler<?> parentHandler;


    private StringBuilder sb;

    private String elementName;


    protected AbstractOWLElementHandler(OWLXMLParserHandler handler) {
        this.handler = handler;
    }

    public OWLOntologyLoaderConfiguration getConfiguration() {
        return handler.getConfiguration();
    }

    public IRI getIRIFromAttribute(String localName, String value) throws OWLParserException {
        if(localName.equals(OWLXMLVocabulary.IRI_ATTRIBUTE.getShortName())) {
            return getIRI(value);
        }
        else if(localName.equals(OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE.getShortName())) {
            return getAbbreviatedIRI(value);
        }
        else if(localName.equals("URI")) {
            // Legacy
            return getIRI(value);
        }
        throw new OWLXMLParserAttributeNotFoundException(getLineNumber(), getColumnNumber(), OWLXMLVocabulary.IRI_ATTRIBUTE.getShortName());
    }


    public IRI getIRIFromElement(String elementLocalName, String textContent) throws OWLParserException {
        if(elementLocalName.equals(OWLXMLVocabulary.IRI_ELEMENT.getShortName())) {
            return handler.getIRI(textContent.trim());
        }
        else if(elementLocalName.equals(OWLXMLVocabulary.ABBREVIATED_IRI_ELEMENT.getShortName())) {
            return handler.getAbbreviatedIRI(textContent.trim());
        }
        throw new OWLXMLParserException(elementLocalName + " is not an IRI element", getLineNumber(), getColumnNumber());
    }


    protected OWLOntologyManager getOWLOntologyManager() throws OWLXMLParserException {
        return handler.getOWLOntologyManager();
    }


    protected OWLOntology getOntology() {
        return handler.getOntology();
    }


    protected OWLDataFactory getOWLDataFactory() {
        return handler.getDataFactory();
    }


    public void setParentHandler(OWLElementHandler<?> handler) {
        this.parentHandler = handler;
    }


    protected OWLElementHandler<?> getParentHandler() {
        return parentHandler;
    }

    @SuppressWarnings("unused")
    public void attribute(String localName, String value) throws OWLParserException {

    }

    protected IRI getIRI(String iri) throws OWLParserException {
        return handler.getIRI(iri);
    }

    protected IRI getAbbreviatedIRI(String abbreviatedIRI) throws OWLParserException {
        return handler.getAbbreviatedIRI(abbreviatedIRI);
    }

//    protected URI getIRI(String uri) throws OWLXMLParserException {
//        return handler.getIRI(uri);
//    }

    protected int getLineNumber() {
        return handler.getLineNumber();
    }

    protected int getColumnNumber() {
        return handler.getColumnNumber();
    }

    // TODO: Make final
    public void startElement(String name) throws OWLXMLParserException {
        sb = null;
        elementName = name;
    }

    protected String getElementName() throws OWLXMLParserException {
        return elementName;
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractOWLAxiomElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractClassExpressionElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractOWLDataRangeHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractOWLObjectPropertyElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLDataPropertyElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLIndividualElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLLiteralElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLAnnotationElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLSubObjectPropertyChainElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLDatatypeFacetRestrictionElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(OWLAnnotationPropertyElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(OWLAnonymousIndividualElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(AbstractIRIElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(SWRLVariableElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(SWRLAtomElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(SWRLAtomListElementHandler _handler) throws OWLXMLParserException {
    }

    final public void handleChars(char[] chars, int start, int length) {
        if (isTextContentPossible()) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(chars, start, length);
        }
    }


    public String getText() {
        if (sb == null) {
            return "";
        }
        else {
            return sb.toString();
        }
    }


    public boolean isTextContentPossible() {
        return false;
    }
}
