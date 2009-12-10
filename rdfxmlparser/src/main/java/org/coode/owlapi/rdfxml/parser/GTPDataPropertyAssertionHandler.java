package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.semanticweb.owlapi.vocab.OWLFacet;
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
public class GTPDataPropertyAssertionHandler extends AbstractLiteralTripleHandler {

    public GTPDataPropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
        if (getConsumer().isAnnotationProperty(predicate) || getConsumer().isOntology(subject)) {
            return false;
        }
        return !OWLRDFVocabulary.BUILT_IN_VOCABULARY_IRIS.contains(predicate) && !OWLFacet.FACET_IRIS.contains(predicate);
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object) {
        return false;
    }


    public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
        addAxiom(getDataFactory().getOWLDataPropertyAssertionAxiom(translateDataProperty(predicate), translateIndividual(subject), object, getPendingAnnotations()
        ));
        consumeTriple(subject, predicate, object);
    }
}
