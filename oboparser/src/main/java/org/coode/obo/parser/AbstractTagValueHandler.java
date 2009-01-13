package org.coode.obo.parser;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.XSDVocabulary;

import java.net.URI;
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
        try {
            consumer.getOWLOntologyManager().applyChange(change);
        }
        catch (OWLOntologyChangeException e) {
            logger.severe(e.getMessage());
        }
    }


    public OBOConsumer getConsumer() {
        return consumer;
    }


    public OWLDataFactory getDataFactory() {
        return consumer.getOWLOntologyManager().getOWLDataFactory();
    }


    public URI getURIFromValue(String s) {
        return consumer.getURI(s);
    }


    public OWLClass getClassFromId(String s) {
        return getDataFactory().getOWLClass(getURIFromValue(s));
    }


    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(getURIFromValue(consumer.getCurrentId()));
    }


    protected OWLClass getOWLClass(String id) {
        return getDataFactory().getOWLClass(getURIFromValue(id));
    }


    protected OWLObjectProperty getOWLObjectProperty(String id) {
        return getDataFactory().getOWLObjectProperty(getURIFromValue(id));
    }


    protected OWLDescription getOWLClassOrRestriction(String termList) {
        StringTokenizer tok = new StringTokenizer(termList, " ", false);
        String id0 = null;
        String id1 = null;
        id0 = tok.nextToken();
        if (tok.hasMoreTokens()) {
            id1 = tok.nextToken();
        }
        if (id1 == null) {
            return getDataFactory().getOWLClass(getURIFromValue(id0));
        }
        else {
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(getURIFromValue(id0));
            OWLClass filler = getDataFactory().getOWLClass(getURIFromValue(id1));
            return getDataFactory().getOWLObjectSomeRestriction(prop, filler);
        }
    }


    protected OWLConstant getUntypedConstant(String literal) throws OWLException {
        return getDataFactory().getOWLUntypedConstant(literal);
    }


    protected OWLConstant getBooleanConstant(Boolean b) {
        if (b) {
            return getDataFactory().getOWLTypedConstant("true",
                                                        getDataFactory().getOWLDataType(XSDVocabulary.BOOLEAN.getURI()));
        }
        else {
            return getDataFactory().getOWLTypedConstant("false",
                                                        getDataFactory().getOWLDataType(XSDVocabulary.BOOLEAN.getURI()));
        }
    }


    protected void addAnnotation(String id, String uriID, OWLConstant value) {
        OWLAnnotation anno = getDataFactory().getOWLConstantAnnotation(getURIFromValue(uriID), value);
        OWLEntity ent = null;
        if (getConsumer().isTerm()) {
            ent = getDataFactory().getOWLClass(getURIFromValue(id));
        }
        else if (getConsumer().isTypedef()) {
            ent = getDataFactory().getOWLObjectProperty(getURIFromValue(id));
        }
        else {
            ent = getDataFactory().getOWLIndividual(getURIFromValue(id));
        }
        OWLAxiom ax = getDataFactory().getOWLEntityAnnotationAxiom(ent, anno);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
