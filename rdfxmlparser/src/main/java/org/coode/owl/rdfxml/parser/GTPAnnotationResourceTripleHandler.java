package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.XSDVocabulary;

import java.net.URI;
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
public class GTPAnnotationResourceTripleHandler extends AbstractResourceTripleHandler {

    public GTPAnnotationResourceTripleHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public boolean canHandleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        return false;
    }


    public boolean canHandle(URI subject, URI predicate, URI object) throws OWLException {
        return getConsumer().isAnnotationProperty(predicate);
    }


    public void handleTriple(URI subject, URI predicate, URI object) throws OWLException {
        if (getConsumer().isOntology(subject)) {
            // Annotation on an ontology!
            consumeTriple(subject, predicate, object);
            OWLOntology ontology = getConsumer().getOWLOntologyManager().getOntology(subject);
            OWLAnnotationAxiom ax = getDataFactory().getOWLOntologyAnnotationAxiom(ontology,
                                                                                   getDataFactory().getOWLObjectAnnotation(
                                                                                           predicate,
                                                                                           getConsumer().getOWLIndividual(
                                                                                                   object)));
            addAxiom(ax);
            return;
        }
        OWLAnnotation anno;

        if (getConsumer().isOntologyProperty(object)) {
            OWLConstant con = getDataFactory().getOWLTypedConstant(object.toString(),
                                                                   getDataFactory().getOWLDataType(XSDVocabulary.ANY_URI.getURI()));
            anno = getDataFactory().getOWLConstantAnnotation(predicate, con);
        }
        else if (getConsumer().isIndividual(object)) {
            OWLIndividual ind = getConsumer().getOWLIndividual(object);
            anno = getDataFactory().getOWLObjectAnnotation(predicate, ind);
        }
        else {
            // Plain URI
            OWLConstant con = getDataFactory().getOWLTypedConstant(object.toString(),
                                                                   getDataFactory().getOWLDataType(XSDVocabulary.ANY_URI.getURI()));
            anno = getDataFactory().getOWLConstantAnnotation(predicate, con);
        }
        OWLEntity entity = null;
        if (getConsumer().isClass(subject)) {
            entity = getDataFactory().getOWLClass(subject);
        }
        else if (getConsumer().isObjectPropertyOnly(subject)) {
            entity = getDataFactory().getOWLObjectProperty(subject);
        }
        else if (getConsumer().isDataPropertyOnly(subject)) {
            entity = getDataFactory().getOWLDataProperty(subject);
        }
        else if (getConsumer().isAnnotationProperty(subject)) {
            // Temp fix until spec is fixed
            getConsumer().getOntologyFormat().addAnnotationURIAnnotation(subject, anno);
            consumeTriple(subject, predicate, object);
        }
        else {
            entity = getConsumer().getOWLIndividual(subject);
        }
        if (entity != null) {
            consumeTriple(subject, predicate, object);

            OWLAxiom decAx = getDataFactory().getOWLEntityAnnotationAxiom(entity, anno);
            addAxiom(decAx);
        }
    }
}
