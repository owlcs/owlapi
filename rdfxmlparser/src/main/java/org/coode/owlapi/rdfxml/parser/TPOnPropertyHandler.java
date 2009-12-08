package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
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
public class TPOnPropertyHandler extends TriplePredicateHandler {

    protected static int count = 0;

    public TPOnPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI());
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addRestriction(subject);
        count++;
//        OWLRDFConsumer consumer = getConsumer();
//        IRI obj = consumer.getResourceObject(subject, OWLRDFVocabulary.OWL_SOME_VALUES_FROM.getIRI(), false);
//        if (obj != null ) {
//            if (!consumer.isAnonymousNode(object) && (consumer.isObjectPropertyOnly(object) || consumer.isClass(obj))) {
//                if(!isAnonymous(obj)) {
//                    consumer.addOWLObjectProperty(object);
//                    consumer.addTriple(subject, predicate, object);
//                    translateClassExpression(subject);
//                    System.out.println("***** CONSUMED! ****");
//                    return true;
//                }
//    //            if (obj != null) {
//    //                 Our object is either a class or a datatype
//    //                if (consumer.isObjectPropertyOnly(obj) || consumer.isClass(object)) {
//    //                     The object MUST be a named class
//    //                    consumer.addOWLClass(object);
//    //                    consumer.addOWLObjectProperty(obj);
//    //                    eagerConsume = true;
//    //                     In order to translate the restriction we need to add the triple.
//    //                    consumer.addTriple(subject, predicate, object);
//    //                    translateClassExpression(subject);
//    //                    consumer.consumeTriple(subject, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI(), obj);
//    //                    eagerConsume = false;
//    //                    return true;
//    //                }
//                }
//        }
        return false;   
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }
}
