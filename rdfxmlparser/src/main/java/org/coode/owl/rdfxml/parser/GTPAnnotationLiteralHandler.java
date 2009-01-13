package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.io.RDFXMLOntologyFormat;
import org.semanticweb.owl.model.*;

import java.net.URI;
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
 * Date: 09-Dec-2006<br><br>
 */
public class GTPAnnotationLiteralHandler extends AbstractLiteralTripleHandler {

    private static final Logger logger = Logger.getLogger(GTPAnnotationLiteralHandler.class.getName());


    public GTPAnnotationLiteralHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public boolean canHandleStreaming(URI subject, URI predicate, OWLConstant object) throws OWLException {
        // If the property is an annotation prop AND
        // we know the type of the subject, we can create
        // the appropriate declaration and entity annotation axiom in streaming mode
        if (!getConsumer().isAnnotationProperty(predicate)) {
            return false;
        }
        return getConsumer().isClass(subject) || getConsumer().isObjectPropertyOnly(subject) || getConsumer().isDataPropertyOnly(
                subject);
    }


    public boolean canHandle(URI subject, URI predicate, OWLConstant object) throws OWLException {
        return getConsumer().isAnnotationProperty(predicate);
    }


    public void handleTriple(URI subject, URI predicate, OWLConstant object) throws OWLException {
        consumeTriple(subject, predicate, object);
        if (getConsumer().isOntology(subject)) {
            // Annotation on an ontology!
            consumeTriple(subject, predicate, object);
            OWLOntology ontology = getConsumer().getOWLOntologyManager().getOntology(subject);
            if (ontology != null) {
                OWLAnnotationAxiom ax = getDataFactory().getOWLOntologyAnnotationAxiom(ontology,
                                                                                       getDataFactory().getOWLConstantAnnotation(
                                                                                               predicate,
                                                                                               object));
                addAxiom(ax);
            }
            else {
                logger.warning("Annotation on ontology " + subject + " but could not obtain ontology!");
            }
            return;
        }

        OWLAnnotation annotation = getDataFactory().getOWLConstantAnnotation(predicate, object);
        OWLEntity entity = null;
        if (getConsumer().isClass(subject)) {
            entity = getDataFactory().getOWLClass(subject);
        }
        else if (getConsumer().isObjectPropertyOnly(subject)) {
            entity = getDataFactory().getOWLObjectProperty(subject);
        }
        else if (getConsumer().isDataPropertyOnly(subject)) {
            entity = getDataFactory().getOWLDataProperty(subject);
        }
        else if (getConsumer().isAnnotationProperty(subject)) {
            // An annotation on an annotation property - what the hell do we do here?
            // 18th Dec 2006: I can't find anyway of dealing with this!
            RDFXMLOntologyFormat format = getConsumer().getOntologyFormat();
            format.addAnnotationURIAnnotation(subject, annotation);
            consumeTriple(subject, predicate, object);
        }
        else {
            // The annotation will either be on an individual or on an
            // axiom
            OWLAxiom ax = getConsumer().getAxiom(subject);
            if (ax != null) {
                consumeTriple(subject, predicate, object);
                OWLAxiomAnnotationAxiom annoAx = getDataFactory().getOWLAxiomAnnotationAxiom(ax, annotation);
                addAxiom(annoAx);
                return;
            }
            else {
                // Individual?
                entity = getConsumer().getOWLIndividual(subject);
            }
        }
        if (entity != null) {
            consumeTriple(subject, predicate, object);
            OWLAxiom decAx = getDataFactory().getOWLEntityAnnotationAxiom(entity, annotation);
            addAxiom(decAx);
        }
    }
}
