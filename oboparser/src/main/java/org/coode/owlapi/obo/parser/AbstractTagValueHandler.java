package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.StringTokenizer;
import java.util.logging.Logger;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 10-Jan-2007<br><br>
 */
public abstract class AbstractTagValueHandler implements TagValueHandler {

    private Logger logger = Logger.getLogger(AbstractTagValueHandler.class.getName());

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


    public IRI getIRIFromValue(String s) {
        return consumer.getIRI(s);
    }


    public OWLClass getClassFromId(String s) {
        return getDataFactory().getOWLClass(getIRIFromValue(s));
    }


    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(getIRIFromValue(consumer.getCurrentId()));
    }


    protected OWLClass getOWLClass(String id) {
        return getDataFactory().getOWLClass(getIRIFromValue(id));
    }


    protected OWLObjectProperty getOWLObjectProperty(String id) {
        return getDataFactory().getOWLObjectProperty(getIRIFromValue(id));
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
            return getDataFactory().getOWLClass(getIRIFromValue(id0));
        } else {
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(getIRIFromValue(id0));
            OWLClass filler = getDataFactory().getOWLClass(getIRIFromValue(id1));
            return getDataFactory().getOWLObjectSomeValuesFrom(prop, filler);
        }
    }


    protected OWLLiteral getBooleanConstant(Boolean b) {
        if (b) {
            return getDataFactory().getOWLTypedLiteral("true",
                    getDataFactory().getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI()));
        } else {
            return getDataFactory().getOWLTypedLiteral("false",
                    getDataFactory().getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI()));
        }
    }


    protected void addAnnotation(String id, String uriID, OWLLiteral value) {
        IRI subject = getIRIFromValue(id);
        OWLAnnotationProperty annotationProperty = getDataFactory().getOWLAnnotationProperty(getIRIFromValue(uriID));
        OWLAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(annotationProperty, subject, value);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
