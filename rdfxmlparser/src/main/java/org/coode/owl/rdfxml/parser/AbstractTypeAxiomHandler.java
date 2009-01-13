package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLConstant;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;

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
        URI subjectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_SUBJECT.getURI(), true);
        if (subjectTripleObject == null) {
            throw new OWLRDFXMLParserMalformedNodeException("missing rdf:subject triple.");
        }
        URI predicateTripleObject = getConsumer().getResourceObject(subject,
                                                                    OWLRDFVocabulary.RDF_PREDICATE.getURI(),
                                                                    true);
        if (predicateTripleObject == null) {
            throw new OWLRDFXMLParserMalformedNodeException("missing rdf:predicate triple.");
        }
        URI objectTripleObject = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
        OWLAxiom ax = null;
        if (objectTripleObject != null) {
            ax = handleAxiomTriples(subjectTripleObject, predicateTripleObject, objectTripleObject);
        }
        else {
            OWLConstant con = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.RDF_OBJECT.getURI(), true);
            if (con == null) {
                throw new OWLRDFXMLParserMalformedNodeException("missing rdf:object triple.");
            }
            ax = handleAxiomTriples(subjectTripleObject, predicateTripleObject, con);
        }
        addAxiom(ax);
        getConsumer().addReifiedAxiom(subject, ax);

//        handleAnnotations(ax, subject);

    }

//    private void handleAnnotations(OWLAxiom axiom, URI mainNode) throws OWLException {
//        getConsumer().g
//        for(Triple triple : new HashSet<Triple>(getConsumer().getTriplesBySubject(mainNode))) {
//            if(getConsumer().isAnnotationProperty(triple.getPredicate())) {
//                consumeTriple(subject, predicate, object);
//                if (triple.isLiteralTriple()) {
//                    OWLConstantAnnotation anno = getDataFactory().getOWLConstantAnnotation(triple.getPredicate(), getConsumer().getConstant((LiteralTriple) triple));
//                    getDataFactory().getOWLAxiomAnnotationAxiom(axiom, anno);
//                }
//                else {
//                    OWLTypedConstant con = getDataFactory().getOWLTypedConstant(
//                            object.toString(),
//                            getDataFactory().getOWLDataType(XSDVocabulary.ANY_URI.getURIFromValue())
//                    );
//                    OWLConstantAnnotation anno = getDataFactory().getOWLConstantAnnotation(triple.getPredicate(), con);
//                    getDataFactory().getOWLAxiomAnnotationAxiom(axiom, anno);
//                }
//            }
//        }
//    }


    /**
     * Called in order to handle the reified triples that form the axiom.  Note that
     * these triples are consumed prior to this method being called.
     * @param subjectTripleObject   The subject triple object, pointing to the axiom subject
     * @param predicateTripleObject The predicate triple object, pointing to the axiom predicate
     * @param objectTripleObject    The object triple object, pointing to the axiom object
     */
    protected abstract OWLAxiom handleAxiomTriples(URI subjectTripleObject, URI predicateTripleObject,
                                                   URI objectTripleObject) throws OWLException;


    protected abstract OWLAxiom handleAxiomTriples(URI subjectTripleObject, URI predicateTripleObject,
                                                   OWLConstant con) throws OWLException;
}
