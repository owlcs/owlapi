package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
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

    private static final Logger logger = Logger.getLogger(TPPropertyRangeHandler.class.getName());


    public TPPropertyRangeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_RANGE.getIRI());
    }


    public boolean canHandleStreaming(IRI subject,
                                      IRI predicate,
                                      IRI object) {
        if (!isAnonymous(object)) {
            if (getConsumer().isObjectPropertyOnly(subject)) {
                return true;
            }
            else if (getConsumer().isDataPropertyOnly(subject)) {
                return true;
            }
        }
        return false;
    }


    public void handleTriple(IRI subject,
                             IRI predicate,
                             IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectPropertyOnly(subject)) {
            translateObjectPropertyRange(subject, object, predicate);
        }
        else if (getConsumer().isDataPropertyOnly(subject)) {
            addAxiom(getDataFactory().getOWLDataPropertyRangeAxiom(translateDataProperty(subject), translateDataRange(object), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if(getConsumer().isAnnotationProperty(subject)) {
            OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
            addAxiom(getDataFactory().getOWLAnnotationPropertyRangeAxiom(prop, object, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else {
            if (getConsumer().isDataRange(object)) {
                // Assume data property
                logger.fine("Assuming data property because range appears to be datatype: " + subject + " -> " + predicate + " -> " + object);
                addAxiom(getDataFactory().getOWLDataPropertyRangeAxiom(translateDataProperty(subject), translateDataRange(object), getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
            else if (getConsumer().isClass(object)) {
                // Assume object property
                logger.fine("Assuming object property because range appears to be a class: " + subject + " -> " + predicate + " -> " + object);
                translateObjectPropertyRange(subject, object, predicate);
            }
            else {
                // Right - just assume an object property!
                logger.fine("Unable to determine range type.  Assuming object property: " + subject + " -> " + predicate + " -> " + object);
                translateObjectPropertyRange(subject, object, predicate);
            }
        }
    }


    private void translateObjectPropertyRange(IRI subject,
                                              IRI object,
                                              IRI predicate) throws OWLOntologyChangeException {
        addAxiom(getDataFactory().getOWLObjectPropertyRangeAxiom(translateObjectProperty(subject), translateClassExpression(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
