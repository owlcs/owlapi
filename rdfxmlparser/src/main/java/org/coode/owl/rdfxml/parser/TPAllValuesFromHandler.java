package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.model.OWLException;

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
 * Date: 01-Jul-2007<br><br>
 */
public class TPAllValuesFromHandler extends TriplePredicateHandler {

    public TPAllValuesFromHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ALL_VALUES_FROM.getURI());
    }


    public boolean canHandleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        OWLRDFConsumer consumer = getConsumer();
        URI propURI = consumer.getResourceObject(subject, OWLRDFVocabulary.OWL_ON_PROPERTY.getURI(), false);
        if(propURI == null) {
            return false;
        }
        if(!consumer.isAnonymousNode(object) || consumer.getDescriptionIfTranslated(object) != null) {
            // The filler is either a datatype or named class
            if(consumer.isObjectPropertyOnly(propURI)) {
                consumer.addOWLClass(object);
                consumer.addTriple(subject, predicate, object);
                consumer.translateDescription(subject);
                return true;
            }
            else if(consumer.isDataPropertyOnly(propURI)) {

            }

        }
        return false;
    }


    public void handleTriple(URI subject, URI predicate, URI object) throws OWLException {
    }
}
