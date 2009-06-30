package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;

import java.net.URI;
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
 * Date: 10-Dec-2006<br><br>
 */
public class AbstractTripleHandler {

    private OWLRDFConsumer consumer;


    public AbstractTripleHandler(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLRDFConsumer getConsumer() {
        return consumer;
    }

    protected Set<OWLAnnotation> getPendingAnnotations() {
        return consumer.getPendingAnnotations();
    }

    protected void consumeTriple(URI subject, URI predicate, URI object) {
        consumer.consumeTriple(subject, predicate, object);
    }

    protected void consumeTriple(URI subject, URI predicate, OWLLiteral object) {
        consumer.consumeTriple(subject, predicate, object);
    }


    protected boolean isAnonymous(URI uri) {
        return getConsumer().isAnonymousNode(uri);
    }


    protected void addAxiom(OWLAxiom axiom) {
        consumer.addAxiom(axiom);
    }


    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }


    protected OWLClassExpression translateClassExpression(URI uri) {
        return consumer.translateClassExpression(uri);
    }


    protected OWLObjectPropertyExpression translateObjectProperty(URI uri) {
        return consumer.translateObjectPropertyExpression(uri);
    }


    protected OWLDataPropertyExpression translateDataProperty(URI uri) {
        return consumer.translateDataPropertyExpression(uri);
    }


    protected OWLDataRange translateDataRange(URI uri) {
        return consumer.translateDataRange(uri);
    }


    protected OWLIndividual translateIndividual(URI uri) {
        return consumer.translateIndividual(uri);
    }
}
