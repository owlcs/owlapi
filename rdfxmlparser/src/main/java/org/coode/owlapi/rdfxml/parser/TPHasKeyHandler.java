package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.Set;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class TPHasKeyHandler extends TriplePredicateHandler {

    private OptimisedListTranslator<OWLPropertyExpression> listTranslator;

    public TPHasKeyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_HAS_KEY.getIRI());
        this.listTranslator = new OptimisedListTranslator<OWLPropertyExpression>(getConsumer(), new HasKeyListItemTranslator(getConsumer()));
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        OWLClassExpression ce = translateClassExpression(subject);
        Set<OWLPropertyExpression> props = listTranslator.translateToSet(object);
        addAxiom(getDataFactory().getOWLHasKeyAxiom(ce, props, getPendingAnnotations()));
    }
}
