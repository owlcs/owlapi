package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
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
 * Date: 10-Dec-2006<br><br>
 */
public class GTPAnnotationResourceTripleHandler extends AbstractResourceTripleHandler {

    public GTPAnnotationResourceTripleHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return !isAnonymous(subject) && getConsumer().isAnnotationProperty(predicate);
    }


    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return getConsumer().isAnnotationProperty(predicate) || getConsumer().isOntology(subject);
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {

        OWLAnnotation anno = getDataFactory().getOWLAnnotation(getDataFactory().getOWLAnnotationProperty(predicate),
                                                            object);
        if(getConsumer().isOntology(subject)) {
            // Assume we annotation our ontology?
            getConsumer().addOntologyAnnotation(anno);
        }
        else {
            OWLAxiom decAx = getDataFactory().getOWLAnnotationAssertionAxiom(subject, anno, getPendingAnnotations());
            addAxiom(decAx);
        }
//        
//            // Add as annotation
//            OWLAnnotationSubject annoSubject;
//            if(getConsumer().isAnonymousNode(subject)) {
//                annoSubject = getDataFactory().getOWLAnonymousIndividual(subject.toString());
//            }
//            else {
//                annoSubject = subject;
//            }
//            OWLAnnotationValue annoValue;
//            if(getConsumer().isAnonymousNode(object)) {
//                annoValue = getDataFactory().getOWLAnonymousIndividual(object.toString());
//            }
//            else {
//                annoValue = object;
//            }
//            addAxiom(getDataFactory().getOWLAnnotationAssertionAxiom(getDataFactory().getOWLAnnotationProperty(predicate), annoSubject, annoValue));
    }
}
