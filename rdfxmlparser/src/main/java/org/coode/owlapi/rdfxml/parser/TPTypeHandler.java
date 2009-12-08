package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;

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
public class TPTypeHandler extends TriplePredicateHandler {

    private static final Logger logger = Logger.getLogger(TPTypeHandler.class.getName());

    public TPTypeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDF_TYPE.getIRI());
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // Can handle if object isn;t anonymous and either the object
        // IRI is owl:Thing, or it is not part of the build in vocabulary
        return !isAnonymous(object) &&
                (object.equals(OWLRDFVocabulary.OWL_THING.getIRI()) ||
                        !OWLRDFVocabulary.BUILT_IN_VOCABULARY_IRIS.contains(object));
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (OWLRDFVocabulary.BUILT_IN_VOCABULARY_IRIS.contains(object)) {
            if (!object.equals(OWLRDFVocabulary.OWL_THING.getIRI())) {
                // Can't have instance of built in vocabulary!
                // Shall we throw an exception here?
                logger.fine("Individual of builtin type " + object);
            }
        }
        addAxiom(getDataFactory().getOWLClassAssertionAxiom(translateClassExpression(object), translateIndividual(subject), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
