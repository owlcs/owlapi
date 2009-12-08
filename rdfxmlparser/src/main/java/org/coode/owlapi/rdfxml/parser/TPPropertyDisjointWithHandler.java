package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.util.CollectionFactory;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 09-Jul-2009
 */
public class TPPropertyDisjointWithHandler extends TriplePredicateHandler {

    public TPPropertyDisjointWithHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_PROPERTY_DISJOINT_WITH.getIRI());
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if(getConsumer().isDataPropertyOnly(subject) || getConsumer().isDataPropertyOnly(object)) {
            addAxiom(getDataFactory().getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(translateDataProperty(subject), translateDataProperty(object)), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if(getConsumer().isObjectPropertyOnly(subject) || getConsumer().isObjectPropertyOnly(object)) {
            addAxiom(getDataFactory().getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(translateObjectProperty(subject), translateObjectProperty(object)), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return (getConsumer().isObjectPropertyOnly(subject) && getConsumer().isObjectPropertyOnly(object)) ||
                (getConsumer().isDataPropertyOnly(subject) && getConsumer().isDataPropertyOnly(object));
    }
}
