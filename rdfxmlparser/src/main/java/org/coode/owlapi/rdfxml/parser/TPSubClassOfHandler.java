package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

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
 * Date: 08-Dec-2006<br><br>
 */
public class TPSubClassOfHandler extends TriplePredicateHandler {

    public static int potentiallyConsumedTiples = 0;

    public TPSubClassOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_SUBCLASS_OF.getIRI());
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        if (!getConsumer().isAnonymousNode(subject)) {
            if (getConsumer().isAnonymousNode(object)) {
                OWLClassExpression superClass = getConsumer().getClassExpressionIfTranslated(object);
                if (superClass != null) {
                    potentiallyConsumedTiples++;
                    return true;
                }
            }
        }
        getConsumer().addOWLClass(subject);
        getConsumer().addOWLClass(object);
        return !isSubjectOrObjectAnonymous(subject, object);
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        OWLClassExpression subClass = translateClassExpression(subject);
        OWLClassExpression supClass = translateClassExpression(object);
        Set<OWLAnnotation> pendingAnnotations = getConsumer().getPendingAnnotations();
        OWLAxiom ax = getDataFactory().getOWLSubClassOfAxiom(subClass, supClass, pendingAnnotations);
        addAxiom(ax);
        consumeTriple(subject, predicate, object);
    }
}
