package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

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
 * Date: 11-Dec-2006<br><br>
 */
public class TypeNegativePropertyAssertionHandler extends BuiltInTypeHandler {

    public TypeNegativePropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION.getURI());
    }

    public boolean canHandleStreaming(URI subject, URI predicate, URI object) {
        return false;
    }

    public void handleTriple(URI subject, URI predicate, URI object) {
        URI source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_SOURCE_INDIVIDUAL.getURI(), true);
        if (source == null) {
            source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_SUBJECT.getURI(), true);
        }
        if (source == null) {
            source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_SUBJECT.getURI(), true);
        }

        URI property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_ASSERTION_PROPERTY.getURI(), true);
        if (property == null) {
            property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_PREDICATE.getURI(), true);
        }
        if (property == null) {
            property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_PREDICATE.getURI(), true);
        }
        Object target = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_TARGET_INDIVIDUAL.getURI(), true);
        if (target == null) {
            target = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_OBJECT.getURI(), true);
        }
        if (target == null) {
            target = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
        }
        if (target == null) {
            target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.OWL_OBJECT.getURI(), true);
        }
        if (target == null) {
            target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
        }
        if (target == null) {
            target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.OWL_TARGET_VALUE.getURI(), true);
        }
        getConsumer().translateAnnotations(subject);
        Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
        if(target instanceof OWLLiteral) {
            OWLIndividual sourceInd = getConsumer().getOWLIndividual(source);
            OWLDataPropertyExpression prop = getConsumer().translateDataPropertyExpression(property);
            OWLLiteral lit = (OWLLiteral) target;
            consumeTriple(subject, predicate, object);
            addAxiom(getDataFactory().getOWLNegativeDataPropertyAssertionAxiom(prop, sourceInd, lit, annos));
        }
        else {
            OWLIndividual sourceInd = getConsumer().getOWLIndividual(source);
            OWLObjectPropertyExpression prop = getConsumer().translateObjectPropertyExpression(property);
            OWLIndividual targetInd = getConsumer().getOWLIndividual((URI) target);
            consumeTriple(subject, predicate, object);
            addAxiom(getDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(prop, sourceInd, targetInd, annos));
        }

    }
}
