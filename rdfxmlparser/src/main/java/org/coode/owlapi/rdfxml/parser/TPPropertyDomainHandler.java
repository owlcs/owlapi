package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
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
public class TPPropertyDomainHandler extends TriplePredicateHandler {

    public TPPropertyDomainHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_DOMAIN.getIRI());
    }


    public boolean canHandleStreaming(IRI subject,
                                      IRI predicate,
                                      IRI object) {
        // Need to parse everything to make sure
        return false;
    }


    public void handleTriple(IRI subject,
                             IRI predicate,
                             IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectPropertyOnly(subject)) {
            translateObjectPropertyDomain(subject, predicate, object);
        }
        else if (getConsumer().isDataPropertyOnly(subject)) {
            translateDataPropertyDomain(subject, predicate, object);
        }
        else if (getConsumer().isAnnotationProperty(subject)) {
            OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
            addAxiom(getDataFactory().getOWLAnnotationPropertyDomainAxiom(prop, object, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else {
            // See if there are any range triples that we can peek at
            IRI rangeIRI = getConsumer().getResourceObject(subject, predicate, false);
            if (getConsumer().isDataRange(rangeIRI)) {
                translateDataPropertyDomain(subject, predicate, object);
            }
            else {
                // Oh well, let's just assume object property
                translateObjectPropertyDomain(subject, predicate, object);
            }
        }
    }


    private void translateDataPropertyDomain(IRI subject,
                                             IRI predicate,
                                             IRI object) throws OWLOntologyChangeException {
        addAxiom(getDataFactory().getOWLDataPropertyDomainAxiom(translateDataProperty(subject), translateClassExpression(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }


    private void translateObjectPropertyDomain(IRI subject,
                                               IRI predicate,
                                               IRI object) throws OWLOntologyChangeException {
        addAxiom(getDataFactory().getOWLObjectPropertyDomainAxiom(translateObjectProperty(subject), translateClassExpression(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
