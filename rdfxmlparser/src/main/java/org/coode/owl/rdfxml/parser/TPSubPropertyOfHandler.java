package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLObjectPropertyExpression;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.List;
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
public class TPSubPropertyOfHandler extends TriplePredicateHandler {

    public TPSubPropertyOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF.getURI());
    }


    public boolean canHandleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        return false;
    }


    public void handleTriple(URI subject, URI predicate, URI object) throws OWLException {

        // First check for object property chain
        if(getConsumer().hasPredicate(subject, OWLRDFVocabulary.OWL_PROPERTY_CHAIN.getURI())) {
            // Property chain
            URI chainList = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_PROPERTY_CHAIN.getURI(), true);
            List<OWLObjectPropertyExpression> properties = getConsumer().translateToObjectPropertyList(chainList);
            addAxiom(getDataFactory().getOWLObjectPropertyChainSubPropertyAxiom(properties,
                                                                                translateObjectProperty(object)));
            consumeTriple(subject, predicate, object);
        }
        else if(getConsumer().isList(subject, false)) {
            // Legacy object property chain representation
            List<OWLObjectPropertyExpression> properties = getConsumer().translateToObjectPropertyList(subject);
            addAxiom(getDataFactory().getOWLObjectPropertyChainSubPropertyAxiom(properties,
                                                                                translateObjectProperty(object)));
            consumeTriple(subject, predicate, object);
        }
        // If any one of the properties is an object property then assume both are
        else if (getConsumer().isObjectPropertyOnly(subject) || getConsumer().isObjectPropertyOnly(object)) {
            translateSubObjectProperty(subject, predicate, object);
        }
        // If any one of the properties is a data property then assume both are
        else if (getConsumer().isDataPropertyOnly(subject) && getConsumer().isDataPropertyOnly(object)) {
            translateSubDataProperty(subject, predicate, object);
        }
        else {
            // Check for range statements
            URI subPropRange = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDFS_RANGE.getURI(), false);
            if (subPropRange != null) {
                if (getConsumer().isDataRange(subPropRange)) {
                    // Data - Data
                    translateSubDataProperty(subject, predicate, object);
                }
                else {
                    translateSubObjectProperty(subject, predicate, object);
                }
                return;
            }

            URI supPropRange = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDFS_RANGE.getURI(), false);
            if (supPropRange != null) {
                if (getConsumer().isDataRange(supPropRange)) {
                    // Data - Data
                    translateSubDataProperty(subject, predicate, object);
                }
                else {
                    translateSubObjectProperty(subject, predicate, object);
                }
                return;
            }

            // Can't  guess from range - assume object, object!
            translateSubObjectProperty(subject, predicate, object);
        }
    }


    private void translateSubObjectProperty(URI subject, URI predicate, URI object) throws OWLException {
        // Object - object
        addAxiom(getDataFactory().getOWLSubObjectPropertyAxiom(translateObjectProperty(subject),
                                                               translateObjectProperty(object)));
        consumeTriple(subject, predicate, object);
    }


    private void translateSubDataProperty(URI subject, URI predicate, URI object) throws OWLException {
        // Data - Data
        addAxiom(getDataFactory().getOWLSubDataPropertyAxiom(translateDataProperty(subject),
                                                             translateDataProperty(object)));
        consumeTriple(subject, predicate, object);
    }
}
