package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLLiteral;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

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
public class TypeAxiomHandler extends BuiltInTypeHandler {

    public TypeAxiomHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_AXIOM.getURI());
    }

    public TypeAxiomHandler(OWLRDFConsumer consumer, URI typeURI) {
        super(consumer, typeURI);
    }

    public boolean canHandleStreaming(URI subject, URI predicate, URI object) throws OWLException {
        // We can't handle this is a streaming fashion, because we can't
        // be sure that the subject, predicate, object triples have been parsed.
        return false;
    }


    public void handleTriple(URI subject, URI predicate, URI object) throws OWLException {
        consumeTriple(subject, predicate, object);


        URI annotatedSource = getAnnotatedSource(subject);
        URI annotatedProperty = getAnnotatedProperty(subject);
        URI annotatedTarget = getAnnotatedTarget(subject);

        Set<OWLAnnotation> annotations = translatedAxiomAnnotations(subject);
        getConsumer().setPendingAnnotations(annotations);
        if (annotatedTarget != null) {
            getConsumer().handle(annotatedSource, annotatedProperty, annotatedTarget);
        } else {
            getConsumer().handle(annotatedSource, annotatedProperty, annotatedTarget);
        }

    }

    protected OWLAxiom handleAxiomTriples(URI subjectTriple, URI predicateTriple, URI objectTriple, Set<OWLAnnotation> annotations) throws
            OWLException {
        // Reconstitute the original triple from the reification triples
        return getConsumer().getLastAddedAxiom();
    }


    protected OWLAxiom handleAxiomTriples(URI subjectTripleObject, URI predicateTripleObject, OWLLiteral con, Set<OWLAnnotation> annotations) throws
            OWLException {
        getConsumer().handle(subjectTripleObject, predicateTripleObject, con);
        return getConsumer().getLastAddedAxiom();
    }

    private Set<OWLAnnotation> translatedAxiomAnnotations(URI subject) {
        Set<URI> predicates = getConsumer().getPredicatesBySubject(subject);
        predicates.remove(OWLRDFVocabulary.OWL_ANNOTATED_SOURCE.getURI());
        predicates.remove(OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY.getURI());
        predicates.remove(OWLRDFVocabulary.OWL_ANNOTATED_TARGET.getURI());
        // We don't handle rdf:subject, rdf:predicate and rdf:object as synonymns - they might be genuinely in the
        // ontology.
        predicates.remove(OWLRDFVocabulary.RDF_SUBJECT.getURI());
        predicates.remove(OWLRDFVocabulary.RDF_PREDICATE.getURI());
        predicates.remove(OWLRDFVocabulary.RDF_OBJECT.getURI());
        predicates.remove(OWLRDFVocabulary.RDF_TYPE.getURI());

        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        for (URI candidatePredicate : predicates) {
            getConsumer().isAnnotationProperty(candidatePredicate);
            annotations.addAll(getConsumer().translateAnnotations(subject));
        }
        return annotations;
    }

    private OWLLiteral getAnnotatedTargetLiteral(URI subject) throws OWLRDFXMLParserMalformedNodeException {
        OWLLiteral con = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.OWL_ANNOTATED_TARGET.getURI(), true);
        if (con == null) {
            con = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
        }
        if (con == null) {
            throw new OWLRDFXMLParserMalformedNodeException("missing owl:object triple.");
        }
        return con;
    }

    private URI getAnnotatedTarget(URI subject) {
        URI objectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_ANNOTATED_TARGET.getURI(), true);
        if (objectTripleObject == null) {
            objectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
        }
        return objectTripleObject;
    }

    private URI getAnnotatedProperty(URI subject) throws OWLRDFXMLParserMalformedNodeException {
        URI predicateTripleObject = getConsumer().getResourceObject(subject,
                OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY.getURI(),
                true);
        if (predicateTripleObject == null) {
            predicateTripleObject = getConsumer().getResourceObject(subject,
                    OWLRDFVocabulary.RDF_PREDICATE.getURI(),
                    true);
        }
        if (predicateTripleObject == null) {
            throw new OWLRDFXMLParserMalformedNodeException("missing owl:predicate triple.");
        }
        return predicateTripleObject;
    }

    private URI getAnnotatedSource(URI subject) throws OWLRDFXMLParserMalformedNodeException {
        URI subjectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_ANNOTATED_SOURCE.getURI(), true);
        if (subjectTripleObject == null) {
            subjectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_SUBJECT.getURI(), true);
        }
        if (subjectTripleObject == null) {
            throw new OWLRDFXMLParserMalformedNodeException("missing owl:subject triple.");
        }
        return subjectTripleObject;
    }


}
