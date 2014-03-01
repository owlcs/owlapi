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
package org.coode.owlapi.obo.parser;

import java.util.StringTokenizer;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 10-Jan-2007
 */
public abstract class AbstractTagValueHandler implements TagValueHandler {

    private String tag;
    private OBOConsumer consumer;

    /**
     * @param tag
     *        tag
     * @param consumer
     *        consumer
     */
    public AbstractTagValueHandler(String tag, OBOConsumer consumer) {
        this.tag = tag;
        this.consumer = consumer;
    }

    @Override
    public String getTagName() {
        return tag;
    }

    /** @return manager */
    public OWLOntologyManager getOWLOntologyManager() {
        return consumer.getOWLOntologyManager();
    }

    /** @return ontology */
    public OWLOntology getOntology() {
        return consumer.getOntology();
    }

    /**
     * @param change
     *        change
     */
    public void applyChange(OWLOntologyChange change) {
        consumer.getOWLOntologyManager().applyChange(change);
    }

    /** @return consumer */
    public OBOConsumer getConsumer() {
        return consumer;
    }

    /** @return data factory */
    public OWLDataFactory getDataFactory() {
        return consumer.getOWLOntologyManager().getOWLDataFactory();
    }

    /**
     * @param vocabulary
     *        vocabulary
     * @return iri
     */
    public IRI getTagIRI(OBOVocabulary vocabulary) {
        return consumer.getIRIFromTagName(vocabulary.getName());
    }

    /**
     * Gets an IRI for a tag name. This is a helper method, which ultimately
     * calls {@link OBOConsumer#getIRIFromTagName(String)}.
     * 
     * @param tagName
     *        The tag name.
     * @return The IRI corresponding to the tag name.
     */
    public IRI getTagIRI(String tagName) {
        return consumer.getIRIFromTagName(tagName);
    }

    /**
     * @param id
     *        id
     * @return iri
     */
    public IRI getIRIFromOBOId(String id) {
        return consumer.getIRIFromOBOId(id);
    }

    // public IRI getIRIFromSymbolicId(String symbolicId) {
    // return consumer.getIRIFromSymbolicId(symbolicId);
    // }
    /**
     * Gets an {@link OWLAnnotation} for a tag value pair.
     * 
     * @param tagName
     *        The tag name.
     * @param value
     *        The tag value. Note that the tag value is un-escaped and stripped
     *        of double quotes if they exist.
     * @return An {@link OWLAnnotation} that is formed by converting the tagName
     *         to an IRI and then to an {@link OWLAnnotationProperty} and the
     *         value to an {@link OWLLiteral}.
     */
    public OWLAnnotation getAnnotationForTagValuePair(String tagName,
            String value) {
        IRI tagIRI = getTagIRI(tagName);
        OWLDataFactory df = getDataFactory();
        OWLAnnotationProperty annotationProperty = df
                .getOWLAnnotationProperty(tagIRI);
        String unescapedString = getUnquotedString(value);
        OWLLiteral annotationValue = df.getOWLLiteral(unescapedString);
        return df.getOWLAnnotation(annotationProperty, annotationValue);
    }

    /**
     * @param s
     *        id
     * @return class
     */
    public OWLClass getClassFromId(String s) {
        return getDataFactory().getOWLClass(getIRIFromOBOId(s));
    }

    /** @return current class */
    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(
                getIRIFromOBOId(consumer.getCurrentId()));
    }

    protected OWLClass getOWLClass(String id) {
        return getDataFactory().getOWLClass(getIRIFromOBOId(id));
    }

    protected OWLObjectProperty getOWLObjectProperty(String id) {
        return getDataFactory().getOWLObjectProperty(getIRIFromOBOId(id));
    }

    protected String getUnquotedString(String value) {
        String unquotedString;
        if (value.startsWith("\"") && value.endsWith("\"")) {
            unquotedString = value.substring(1, value.length() - 1);
        } else {
            unquotedString = value;
        }
        return unquotedString;
    }

    protected OWLClassExpression getOWLClassOrRestriction(String termList) {
        StringTokenizer tok = new StringTokenizer(termList, " ", false);
        String id0 = null;
        String id1 = null;
        id0 = tok.nextToken();
        if (tok.hasMoreTokens()) {
            id1 = tok.nextToken();
        }
        if (id1 == null) {
            return getDataFactory().getOWLClass(getIRIFromOBOId(id0));
        } else {
            IRI propertyIRI = getConsumer()
                    .getRelationIRIFromSymbolicIdOrOBOId(id0);
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(
                    propertyIRI);
            OWLClass filler = getDataFactory()
                    .getOWLClass(getIRIFromOBOId(id1));
            return getDataFactory().getOWLObjectSomeValuesFrom(prop, filler);
        }
    }

    protected OWLLiteral getBooleanConstant(boolean b) {
        return getDataFactory().getOWLLiteral(b);
    }

    protected void addAnnotation(String id, String uriID, OWLLiteral value) {
        IRI subject = getIRIFromOBOId(id);
        OWLAnnotationProperty annotationProperty = getDataFactory()
                .getOWLAnnotationProperty(getIRIFromOBOId(uriID));
        OWLAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(
                annotationProperty, subject, value);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
