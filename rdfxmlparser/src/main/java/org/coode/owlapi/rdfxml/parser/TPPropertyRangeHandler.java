package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.logging.Logger;
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
 * Date: 08-Dec-2006<br><br>
 */
public class TPPropertyRangeHandler extends TriplePredicateHandler {


    public TPPropertyRangeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_RANGE.getIRI());
    }


    public boolean canHandleStreaming(IRI subject,
                                      IRI predicate,
                                      IRI object) {
        return false;
    }


    public void handleTriple(IRI subject,
                             IRI predicate,
                             IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectProperty(subject) && getConsumer().isClassExpression(object)) {
            OWLObjectPropertyExpression property = translateObjectProperty(subject);
            OWLClassExpression range = translateClassExpression(object);
            addAxiom(getDataFactory().getOWLObjectPropertyRangeAxiom(property, range, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if (getConsumer().isDataProperty(subject) && getConsumer().isDataRange(object)) {
            OWLDataPropertyExpression property = translateDataProperty(subject);
            OWLDataRange dataRange = translateDataRange(object);
            addAxiom(getDataFactory().getOWLDataPropertyRangeAxiom(property, dataRange, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if (getConsumer().isAnnotationProperty(subject) && !getConsumer().isAnonymousNode(object)) {
            OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
            addAxiom(getDataFactory().getOWLAnnotationPropertyRangeAxiom(prop, object, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if(!isStrict()) {
            // TODO: Handle the case where the object is anonymous
            OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
            addAxiom(getDataFactory().getOWLAnnotationPropertyRangeAxiom(prop, object, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

}
