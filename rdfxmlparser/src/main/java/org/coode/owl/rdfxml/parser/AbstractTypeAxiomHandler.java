package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.vocab.XSDVocabulary;

import java.net.URI;
import java.util.Set;
import java.util.HashSet;

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
public abstract class AbstractTypeAxiomHandler extends BuiltInTypeHandler {

    public AbstractTypeAxiomHandler(OWLRDFConsumer consumer, URI typeURI) {
        super(consumer, typeURI);
    }


    public boolean canHandleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        // We can't handle this is a streaming fashion, because we can't
        // be sure that the subject, predicate, object triples have been parsed.
        return false;
    }


    public void handleTriple(URI subject, URI predicate, URI object) throws OWLException {
        consumeTriple(subject, predicate, object);
        Set<URI> predicates = getConsumer().getPredicatesBySubject(subject);
        predicates.remove(OWLRDFVocabulary.OWL_SUBJECT.getURI());
        predicates.remove(OWLRDFVocabulary.OWL_PREDICATE.getURI());
        predicates.remove(OWLRDFVocabulary.OWL_OBJECT.getURI());
        predicates.remove(OWLRDFVocabulary.RDF_SUBJECT.getURI());
        predicates.remove(OWLRDFVocabulary.RDF_PREDICATE.getURI());
        predicates.remove(OWLRDFVocabulary.RDF_OBJECT.getURI());
        predicates.remove(OWLRDFVocabulary.RDF_TYPE.getURI());

        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        for(URI candidatePredicate : predicates) {
            getConsumer().isAnnotationProperty(candidatePredicate);
            
        }
        URI subjectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_SUBJECT.getURI(), true);
        if(subjectTripleObject == null) {
            subjectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_SUBJECT.getURI(), true);
        }
        if (subjectTripleObject == null) {
            throw new OWLRDFXMLParserMalformedNodeException("missing owl:subject triple.");
        }

        URI predicateTripleObject = getConsumer().getResourceObject(subject,
                OWLRDFVocabulary.OWL_PREDICATE.getURI(),
                true);
        if(predicateTripleObject == null) {
            predicateTripleObject = getConsumer().getResourceObject(subject,
                OWLRDFVocabulary.RDF_PREDICATE.getURI(),
                true);
        }
        if (predicateTripleObject == null) {
            throw new OWLRDFXMLParserMalformedNodeException("missing owl:predicate triple.");
        }

        URI objectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_OBJECT.getURI(), true);
        if(objectTripleObject == null) {
            objectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
        }
        OWLAxiom ax = null;

        if (objectTripleObject != null) {
            ax = handleAxiomTriples(subjectTripleObject, predicateTripleObject, objectTripleObject, annotations);
        } else {
            OWLLiteral con = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.OWL_OBJECT.getURI(), true);
            if(con == null) {
                con = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
            }
            if (con == null) {
                throw new OWLRDFXMLParserMalformedNodeException("missing owl:object triple.");
            }
            ax = handleAxiomTriples(subjectTripleObject, predicateTripleObject, con, annotations);
        }
        addAxiom(ax);
        getConsumer().addReifiedAxiom(subject, ax);

//        handleAnnotations(ax, subject);

    }




    /**
     * Called in order to handle the reified triples that form the axiom.  Note that
     * these triples are consumed prior to this method being called.
     *
     * @param subjectTripleObject   The subject triple object, pointing to the axiom subject
     * @param predicateTripleObject The predicate triple object, pointing to the axiom predicate
     * @param objectTripleObject    The object triple object, pointing to the axiom object
     */
    protected abstract OWLAxiom handleAxiomTriples(URI subjectTripleObject, URI predicateTripleObject,
                                                   URI objectTripleObject, Set<OWLAnnotation> annotations) throws OWLException;


    protected abstract OWLAxiom handleAxiomTriples(URI subjectTripleObject, URI predicateTripleObject,
                                                   OWLLiteral con, Set<OWLAnnotation> annotations) throws OWLException;
}
