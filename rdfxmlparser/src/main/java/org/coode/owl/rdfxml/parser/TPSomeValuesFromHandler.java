package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 08-Jan-2007<br><br>
 */
public class TPSomeValuesFromHandler extends TriplePredicateHandler {

    public static boolean eagerConsume = false;

    protected static int count = 0;


    public TPSomeValuesFromHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_SOME_VALUES_FROM.getURI());
        count = 0;
    }


    public boolean canHandleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        eagerConsume = false;
        count++;
        // We either need to know that
        // 1) the property is an object property and the filler is a class
        // 2) the property is a datatype prop and the filler is a datatype
        OWLRDFConsumer consumer = getConsumer();
        if (!consumer.isAnonymousNode(object)) {
            URI obj = consumer.getResourceObject(subject, OWLRDFVocabulary.OWL_ON_PROPERTY.getURI(), false);
            if (obj != null) {
                // Our object is either a class or a datatype
                if (consumer.isObjectPropertyOnly(obj) || consumer.isClass(object)) {
                    // The object MUST be a named class
                    consumer.addOWLClass(object);
                    consumer.addOWLObjectProperty(obj);
                    eagerConsume = true;
                    // In order to translate the restriction we need to add the triple.
                    consumer.addTriple(subject, predicate, object);
                    translateDescription(subject);
                    consumer.consumeTriple(subject, OWLRDFVocabulary.OWL_ON_PROPERTY.getURI(), obj);
                    eagerConsume = false;
                    return true;
                }
            }
        }

        return false;
    }


    public void handleTriple(URI subject, URI predicate, URI object) throws OWLException {
    }
}
