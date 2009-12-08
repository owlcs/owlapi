package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.HashSet;
import java.util.Set;
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
public class TPEquivalentPropertyHandler extends TriplePredicateHandler {

    private static final Logger logger = Logger.getLogger(TPEquivalentPropertyHandler.class.getName());

    public TPEquivalentPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_EQUIVALENT_PROPERTY.getIRI());
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return (getConsumer().isObjectPropertyOnly(subject) &&
                getConsumer().isObjectPropertyOnly(object)) ||
                (getConsumer().isDataPropertyOnly(subject) &&
                        getConsumer().isDataPropertyOnly(object));
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // If either is an object property then translate as object properties
        if (getConsumer().isObjectPropertyOnly(subject) ||
                getConsumer().isObjectPropertyOnly(object)) {
            translateEquivalentObjectProperties(subject, predicate, object);
        } else if (getConsumer().isDataPropertyOnly(subject) ||
                getConsumer().isDataPropertyOnly(object)) {
            Set<OWLDataPropertyExpression> props = new HashSet<OWLDataPropertyExpression>();
            props.add(translateDataProperty(subject));
            props.add(translateDataProperty(object));
            addAxiom(getDataFactory().getOWLEquivalentDataPropertiesAxiom(props, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        } else {
            // Assume object!?
            translateEquivalentObjectProperties(subject, predicate, object);
            logger.fine("Assuming equivalent object properties because property types " +
                    "are ambiguous: " + subject + " <-> " + object);
        }
    }


    private void translateEquivalentObjectProperties(IRI subject, IRI predicate, IRI object) throws OWLOntologyChangeException {
        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
        props.add(translateObjectProperty(subject));
        props.add(translateObjectProperty(object));
        addAxiom(getDataFactory().getOWLEquivalentObjectPropertiesAxiom(props, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
