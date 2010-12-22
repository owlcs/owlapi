package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

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
        super(consumer, OWLRDFVocabulary.OWL_AXIOM.getIRI());
    }

    public TypeAxiomHandler(OWLRDFConsumer consumer, IRI typeIRI) {
        super(consumer, typeIRI);
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // We can't handle this is a streaming fashion, because we can't
        // be sure that the subject, predicate, object triples have been parsed.
        return false;
    }

    /**
     * Gets the IRI of the predicate of the triple that specifies the target of a reified axiom
     * @return The IRI, by default this is owl:annotatedTarget
     */
    protected OWLRDFVocabulary getTargetTriplePredicate() {
        return OWLRDFVocabulary.OWL_ANNOTATED_TARGET;
    }

    /**
     * Gets the IRI of the predicate of the triple that specifies that predicate of a reified axiom
     * @return The IRI, by default this is owl:annotatedProperty
     */
    protected OWLRDFVocabulary getPropertyTriplePredicate() {
        return OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY;
    }

    /**
     * Gets the IRI of the predicate of the triple that specifies the source of a reified axiom
     * @return The IRI, by default this is owl:annotatedSource
     */
    protected OWLRDFVocabulary getSourceTriplePredicate() {
        return OWLRDFVocabulary.OWL_ANNOTATED_SOURCE;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);


        IRI annotatedSource = getObjectOfSourceTriple(subject);
        IRI annotatedProperty = getObjectOfPropertyTriple(subject);
        IRI annotatedTarget = getObjectOfTargetTriple(subject);
        OWLLiteral annotatedTargetLiteral = null;
        if (annotatedTarget == null) {
            annotatedTargetLiteral = getTargetLiteral(subject);
        }

        Set<OWLAnnotation> annotations = getConsumer().translateAnnotations(subject);
        getConsumer().setPendingAnnotations(annotations);
        if (annotatedTarget != null) {
            getConsumer().handle(annotatedSource, annotatedProperty, annotatedTarget);
        }
        else {
            getConsumer().handle(annotatedSource, annotatedProperty, annotatedTargetLiteral);
        }
        if (!annotations.isEmpty()) {
            OWLAxiom ax = getConsumer().getLastAddedAxiom();
            getConsumer().applyChange(new RemoveAxiom(getConsumer().getOntology(), ax.getAxiomWithoutAnnotations()));
        }

    }

    protected OWLAxiom handleAxiomTriples(IRI subjectTriple, IRI predicateTriple, IRI objectTriple, Set<OWLAnnotation> annotations) {
        // Reconstitute the original triple from the reification triples
        return getConsumer().getLastAddedAxiom();
    }


    protected OWLAxiom handleAxiomTriples(IRI subjectTripleObject, IRI predicateTripleObject, OWLLiteral con, Set<OWLAnnotation> annotations) {
        getConsumer().handle(subjectTripleObject, predicateTripleObject, con);
        return getConsumer().getLastAddedAxiom();
    }

//    private Set<OWLAnnotation> translateAnnotations(IRI subject) {
//        Set<IRI> predicates = getConsumer().getPredicatesBySubject(subject);
//        predicates.remove(getSourceTriplePredicate());
//        predicates.remove(getPropertyTriplePredicate());
//        predicates.remove(getTargetTriplePredicate());
//        // We don't handle rdf:subject, rdf:predicate and rdf:object as synonymns - they might be genuinely in the
//        // ontology.
//        predicates.remove(OWLRDFVocabulary.RDF_SUBJECT.getIRI());
//        predicates.remove(OWLRDFVocabulary.RDF_PREDICATE.getIRI());
//        predicates.remove(OWLRDFVocabulary.RDF_OBJECT.getIRI());
//        predicates.remove(OWLRDFVocabulary.RDF_TYPE.getIRI());
//
//        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
//        for (IRI candidatePredicate : predicates) {
//            getConsumer().isAnnotationProperty(candidatePredicate);
//            annotations.addAll(getConsumer().translateAnnotations(subject));
//        }
//        return annotations;
//    }

    private OWLLiteral getTargetLiteral(IRI subject) {
        OWLLiteral con = getConsumer().getLiteralObject(subject, getTargetTriplePredicate(), true);
        if (con == null) {
            con = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.RDF_OBJECT.getIRI(), true);
        }
        return con;
    }


    /**
     * Gets the object of the target triple that has the specified main node
     * @param mainNode The main node
     * @return The object of the triple that has the specified mainNode as its subject and the IRI returned
     *         by the {@code TypeAxiomHandler#getSourceTriplePredicate()} method.  For backwards compatibility, a
     *         search will also be performed for triples whos subject is the specified mainNode and predicate rdf:object
     */
    private IRI getObjectOfTargetTriple(IRI mainNode) {
        IRI objectTripleObject = getConsumer().getResourceObject(mainNode, getTargetTriplePredicate(), true);
        if (objectTripleObject == null) {
            objectTripleObject = getConsumer().getResourceObject(mainNode, OWLRDFVocabulary.RDF_OBJECT, true);
        }
        if (objectTripleObject == null) {
            objectTripleObject = getConsumer().getResourceObject(mainNode, OWLRDFVocabulary.OWL_PROPERTY_CHAIN, true);
        }
        return objectTripleObject;
    }

    private IRI getObjectOfPropertyTriple(IRI subject) {
        IRI predicateTripleObject = getConsumer().getResourceObject(subject, getPropertyTriplePredicate(), true);
        if (predicateTripleObject == null) {
            predicateTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_PREDICATE, true);
        }
        return predicateTripleObject;
    }


    /**
     * Gets the source IRI for an annotated or reified axiom
     * @param mainNode The main node of the triple
     * @return The source object
     * @throws OWLRDFXMLParserMalformedNodeException
     *
     */
    private IRI getObjectOfSourceTriple(IRI mainNode) {
        IRI subjectTripleObject = getConsumer().getResourceObject(mainNode, getSourceTriplePredicate(), true);
        if (subjectTripleObject == null) {
            subjectTripleObject = getConsumer().getResourceObject(mainNode, OWLRDFVocabulary.RDF_SUBJECT, true);
        }
        return subjectTripleObject;
    }


}
