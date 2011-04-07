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
import org.semanticweb.owlapi.vocab.XSDVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public abstract class AbstractTagValueHandler implements TagValueHandler {

    //private Logger logger = Logger.getLogger(AbstractTagValueHandler.class.getName());

    private String tag;

    private OBOConsumer consumer;


    public AbstractTagValueHandler(String tag, OBOConsumer consumer) {
        this.tag = tag;
        this.consumer = consumer;
    }

    public String getTag() {
        return tag;
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return consumer.getOWLOntologyManager();
    }


    public OWLOntology getOntology() {
        return consumer.getOntology();
    }


    public void applyChange(OWLOntologyChange change) {
        consumer.getOWLOntologyManager().applyChange(change);
    }


    public OBOConsumer getConsumer() {
        return consumer;
    }


    public OWLDataFactory getDataFactory() {
        return consumer.getOWLOntologyManager().getOWLDataFactory();
    }


//    public IRI getIRIFromValue(String s) {
//        return consumer.getIRI(s);
//    }

    public IRI getTagIRI(String tagName) {
        return consumer.getTagIRI(tagName);
    }

    public IRI getIdIRI(String id) {
        return consumer.getIdIRI(id);
    }

    public OWLClass getClassFromId(String s) {
        return getDataFactory().getOWLClass(getIdIRI(s));
    }


    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(getIdIRI(consumer.getCurrentId()));
    }


    protected OWLClass getOWLClass(String id) {
        return getDataFactory().getOWLClass(getIdIRI(id));
    }


    protected OWLObjectProperty getOWLObjectProperty(String id) {
        return getDataFactory().getOWLObjectProperty(getIdIRI(id));
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
            return getDataFactory().getOWLClass(getIdIRI(id0));
        } else {
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(getIdIRI(id0));
            OWLClass filler = getDataFactory().getOWLClass(getIdIRI(id1));
            return getDataFactory().getOWLObjectSomeValuesFrom(prop, filler);
        }
    }


    protected OWLLiteral getBooleanConstant(Boolean b) {
        if (b) {
            return getDataFactory().getOWLLiteral("true",
                    getDataFactory().getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI()));
        } else {
            return getDataFactory().getOWLLiteral("false",
                    getDataFactory().getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI()));
        }
    }


    protected void addAnnotation(String id, String uriID, OWLLiteral value) {
        IRI subject = getIdIRI(id);
        OWLAnnotationProperty annotationProperty = getDataFactory().getOWLAnnotationProperty(getIdIRI(uriID));
        OWLAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(annotationProperty, subject, value);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
