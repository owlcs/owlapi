/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.rdf.rdfxml.parser.HandlerFunction.NO_OP_IRI;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DIFFERENT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DISJOINT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DISJOINT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ASYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_AXIOM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DEPRECATED_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DEPRECATED_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_FUNCTIONAL_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INVERSE_FUNCTIONAL_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_IRREFLEXIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MEMBERS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONTOLOGY_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_REFLEXIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SELF_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TRANSITIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_LIST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ATOM_LIST;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN_CLASS;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATAVALUED_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.IMP;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.INDIVIDUAL_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.VARIABLE;

import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;

enum AbstractBuiltInTypeHandler
    implements BuiltInTypeHandler, ResourceTripleHandler, TriplePredicateHandler {
    //@formatter:off
    TypeAnnotationHandler            (OWL_ANNOTATION,          (c, s, p, o) -> c.handleAnnotationTriple(s, p, o)),
    TypeAnnotationPropertyHandler    (OWL_ANNOTATION_PROPERTY, (c, s, p, o) -> c.handleAnnotationPropertyTriple(s, p, o)),
    TypeClassHandler                 (OWL_CLASS,               (c, s, p, o) -> c.handleClassDeclarationTriple(s, p, o)),
    TypeDataPropertyHandler          (OWL_DATA_PROPERTY,       (c, s, p, o) -> c.handleDataPropertyTriple(s, p, o)),
    TypeDataRangeHandler             (OWL_DATA_RANGE,          (c, s, p, o) -> c.handleDatarangeTriple(s, p, o)),
    TypeDatatypeHandler              (RDFS_DATATYPE,           (c, s, p, o) -> c.handleDatatypeTriple(s, p, o)),
    TypeDeprecatedClassHandler       (OWL_DEPRECATED_CLASS,    (c, s, p, o) -> c.handleDeprecatedClassTriple(s, p, o)),
    TypeDeprecatedPropertyHandler    (OWL_DEPRECATED_PROPERTY, (c, s, p, o) -> c.handleDeprecatedPropertyTriple(s, p, o)),
    TypeNamedIndividualHandler       (OWL_NAMED_INDIVIDUAL,    (c, s, p, o) -> c.handleNamedIndividualTriple(s, p, o)),
    TypeListHandler                  (RDF_LIST,                (c, s, p, o) -> c.tripleIndex.consumeTriple(s, p, o)),
    TypeObjectPropertyHandler        (OWL_OBJECT_PROPERTY,     (c, s, p, o) -> c.handleObjectTriple(s, p, o)),
    TypeOntologyHandler              (OWL_ONTOLOGY,            (c, s, p, o) -> c.handleOntologyTriple(s, p, o)),
    TypeOntologyPropertyHandler      (OWL_ONTOLOGY_PROPERTY,   (c, s, p, o) -> c.handleOntologyPropertyTriple(s, p, o)),
    TypePropertyHandler              (RDF_PROPERTY,            (c, s, p, o) -> c.handleTypeTriple(s, p, o)),
    TypeRDFSClassHandler             (RDFS_CLASS,              (c, s, p, o) -> c.handleClassTriple(s, p, o)),
    TypeRestrictionHandler           (OWL_RESTRICTION,         (c, s, p, o) -> c.handleRestrictionTriple(s, p, o)),
    TypeSWRLAtomListHandler          (ATOM_LIST,               (c, s, p, o) -> c.tripleIndex.consumeTriple(s, p, o)),
    TypeSWRLBuiltInAtomHandler       (BUILT_IN_ATOM,           (c, s, p, o) -> c.handleSWRLBuiltinTriple(s, p, o)),
    TypeSWRLBuiltInHandler           (BUILT_IN_CLASS,          (c, s, p, o) -> c.tripleIndex.consumeTriple(s, p, o)),
    TypeSWRLClassAtomHandler         (CLASS_ATOM,              (c, s, p, o) -> c.handleSWRLClassAtomTriple(s, p, o)),
    TypeSWRLDataRangeAtomHandler     (DATA_RANGE_ATOM,         (c, s, p, o) -> c.handleSWRLDatarangeTriple(s, p, o)),
    TypeSWRLImpHandler               (IMP,                     (c, s, p, o) -> c.handleSWRLTriple(s, p, o)),
    TypeSWRLSameIndividualAtomHandler(SAME_INDIVIDUAL_ATOM,    (c, s, p, o) -> c.handleSWRLSameAsTriple(s, p, o)),
    TypeSWRLVariableHandler          (VARIABLE,                (c, s, p, o) -> c.handleSWRLVariableTriple(s, p, o)),
    TypeSelfRestrictionHandler       (OWL_SELF_RESTRICTION,    (c, s, p, o) -> c.handleSelfTriple(s, p, o)),
    TypeNegativeDataPropertyAssertionHandler(OWL_NEGATIVE_DATA_PROPERTY_ASSERTION, (c, s, p, o) -> c.handleNegDataAssertionTriple(s, p, o)),
    TypeNegativePropertyAssertionHandler    (OWL_NEGATIVE_PROPERTY_ASSERTION,      (c, s, p, o) -> c.handleNegAssertionTriple(s, p, o)),
    TypeSWRLDataValuedPropertyAtomHandler   (DATAVALUED_PROPERTY_ATOM,             (c, s, p, o) -> c.handleSWRLDataPropertyAtomTriple(s, p, o)),
    TypeSWRLDifferentIndividualsAtomHandler (DIFFERENT_INDIVIDUALS_ATOM,           (c, s, p, o) -> c.handleSWRLDifferentTriple(s, p, o)),
    TypeSWRLIndividualPropertyAtomHandler   (INDIVIDUAL_PROPERTY_ATOM,             (c, s, p, o) -> c.handleSWRLIndividualTriple(s, p, o)),
    //@formatter:on
    TypeAllDifferentHandler(OWL_ALL_DIFFERENT,
        (c, s, p, o) -> defaultCanHandle(c, OWL_ALL_DIFFERENT, s, p, o)
            && c.canHandleAllDifferent(s),
        NO_OP_IRI,
        (c, s, p, o) -> c.handleAllDifferentTriple(s, p, o)), TypeAllDisjointClassesHandler(
            OWL_ALL_DISJOINT_CLASSES,
            (c, s, p, o) -> defaultCanHandle(c, OWL_ALL_DISJOINT_CLASSES, s, p, o)
                && c.tripleIndex.isResourcePresent(s, OWL_MEMBERS),
            NO_OP_IRI,
            (c, s, p, o) -> c.handleAllDisjointClassesTriple(s, p,
                o)), TypeAllDisjointPropertiesHandler(OWL_ALL_DISJOINT_PROPERTIES,
                    (c, s, p, o) -> defaultCanHandle(c, OWL_ALL_DISJOINT_PROPERTIES, s, p, o),
                    NO_OP_IRI,
                    (c, s, p, o) -> c.handleAllDisjointTriple(s, p,
                        o)), TypeFunctionalPropertyHandler(OWL_FUNCTIONAL_PROPERTY,
                            (c, s, p, o) -> defaultCanHandle(c, OWL_FUNCTIONAL_PROPERTY, s, p, o),
                            NO_OP_IRI,
                            (c, s, p, o) -> c.handleFunctionalTriple(s, p,
                                o)), TypeAsymmetricPropertyHandler(
                                    OWL_ASYMMETRIC_PROPERTY,
                                    (c, s, p, o) -> defaultCanHandle(c, OWL_ASYMMETRIC_PROPERTY, s,
                                        p, o),
                                    (c, s, p, o) -> c.canHandleAsymmetricStreaming(s),
                                    (c, s, p, o) -> c.handleAsymmetricTriple(s, p, o)), TypeAH(
                                        OWL_AXIOM,
                                        (c, s, p, o) -> defaultCanHandle(c, OWL_AXIOM, s, p, o),
                                        (c, s, p, o) -> c.canHandleAxiomStreaming(s),
                                        (c, s, p, o) -> c.handletypeaxiomTriple(s, p,
                                            o)), TypeInverseFunctionalPropertyHandler(
                                                OWL_INVERSE_FUNCTIONAL_PROPERTY,
                                                (c, s, p, o) -> defaultCanHandle(c,
                                                    OWL_INVERSE_FUNCTIONAL_PROPERTY, s, p, o),
                                                (c, s, p, o) -> c
                                                    .canHandleInverseFunctionalStreaming(s, p),
                                                (c, s, p, o) -> c.handleInverseFunctionalTriple(s,
                                                    p, o)), TypeIrreflexivePropertyHandler(
                                                        OWL_IRREFLEXIVE_PROPERTY,
                                                        (c, s, p, o) -> defaultCanHandle(c,
                                                            OWL_IRREFLEXIVE_PROPERTY, s, p, o),
                                                        (c, s, p, o) -> c
                                                            .canHandleIrreflexiveStreaming(s),
                                                        (c, s, p, o) -> c.handleIrreflexiveTriple(s,
                                                            p, o)), TypeReflexivePropertyHandler(
                                                                OWL_REFLEXIVE_PROPERTY,
                                                                (c, s, p, o) -> defaultCanHandle(c,
                                                                    OWL_REFLEXIVE_PROPERTY, s, p,
                                                                    o),
                                                                (c, s, p, o) -> c
                                                                    .canHandleReflexiveStream(s),
                                                                (c, s, p, o) -> c
                                                                    .handleReflexiveTriple(s, p,
                                                                        o)), TypeSymmetricPropertyHandler(
                                                                            OWL_SYMMETRIC_PROPERTY,
                                                                            (c, s, p,
                                                                                o) -> defaultCanHandle(
                                                                                    c,
                                                                                    OWL_SYMMETRIC_PROPERTY,
                                                                                    s, p, o),
                                                                            (c, s, p, o) -> c
                                                                                .canHandleSymmetricStreaming(
                                                                                    s, p),
                                                                            (c, s, p, o) -> c
                                                                                .handleSymmetricTriple(
                                                                                    s, p,
                                                                                    o)), TypeTransitivePropertyHandler(
                                                                                        OWL_TRANSITIVE_PROPERTY,
                                                                                        (c, s, p,
                                                                                            o) -> defaultCanHandle(
                                                                                                c,
                                                                                                OWL_TRANSITIVE_PROPERTY,
                                                                                                s,
                                                                                                p,
                                                                                                o),
                                                                                        (c, s, p,
                                                                                            o) -> c
                                                                                                .canHandleTransitiveStreaming(
                                                                                                    s,
                                                                                                    p),
                                                                                        (c, s, p,
                                                                                            o) -> c
                                                                                                .handleTransitiveTriple(
                                                                                                    s,
                                                                                                    p,
                                                                                                    o));

    public static EnumSet<AbstractBuiltInTypeHandler> strict = EnumSet.of(
        TypeOntologyPropertyHandler, TypeAsymmetricPropertyHandler, TypeClassHandler,
        TypeObjectPropertyHandler, TypeDataPropertyHandler, TypeDatatypeHandler,
        TypeFunctionalPropertyHandler, TypeInverseFunctionalPropertyHandler,
        TypeIrreflexivePropertyHandler, TypeReflexivePropertyHandler, TypeSymmetricPropertyHandler,
        TypeTransitivePropertyHandler, TypeRestrictionHandler, TypeListHandler,
        TypeAnnotationPropertyHandler, TypeDeprecatedClassHandler, TypeDeprecatedPropertyHandler,
        TypeDataRangeHandler, TypeOntologyHandler, TypeNegativeDataPropertyAssertionHandler,
        TypeRDFSClassHandler, TypeSelfRestrictionHandler, TypePropertyHandler,
        TypeNamedIndividualHandler, TypeAnnotationHandler);
    public static EnumSet<AbstractBuiltInTypeHandler> nonStrict =
        EnumSet.of(TypeSWRLAtomListHandler, TypeSWRLBuiltInAtomHandler, TypeSWRLBuiltInHandler,
            TypeSWRLClassAtomHandler, TypeSWRLDataRangeAtomHandler,
            TypeSWRLDataValuedPropertyAtomHandler, TypeSWRLDifferentIndividualsAtomHandler,
            TypeSWRLImpHandler, TypeSWRLIndividualPropertyAtomHandler,
            TypeSWRLSameIndividualAtomHandler, TypeSWRLVariableHandler);
    HasIRI typeIRI;
    HandlerFunction<IRI> canHandle;
    HandlerFunction<IRI> canHandleStreaming;
    HandlerFunction<IRI> handle;

    AbstractBuiltInTypeHandler(HasIRI i, HandlerFunction<IRI> handle) {
        this(i, (c, s, p, o) -> defaultCanHandle(c, i, s, p, o), (c, s, p, o) -> true, handle);
    }

    AbstractBuiltInTypeHandler(HasIRI i, HandlerFunction<IRI> canHandle,
        HandlerFunction<IRI> canHandleStreaming, HandlerFunction<IRI> handle) {
        typeIRI = i;
        this.canHandle = canHandle;
        this.canHandleStreaming = canHandleStreaming;
        this.handle = handle;
    }

    // XXX performance: these maps can be precomputed
    public static Map<IRI, BuiltInTypeHandler> handlers(boolean strictConfig) {
        return toMap(
            strictConfig ? strict.stream() : Stream.concat(strict.stream(), nonStrict.stream()));
    }

    public static Map<IRI, BuiltInTypeHandler> axioms() {
        return toMap(Stream.of(TypeAH, TypeAllDifferentHandler, TypeAllDisjointClassesHandler,
            TypeAllDisjointPropertiesHandler, TypeNegativePropertyAssertionHandler));
    }

    protected static Map<IRI, BuiltInTypeHandler> toMap(
        Stream<? extends BuiltInTypeHandler> stream) {
        return stream.collect(Collectors.toConcurrentMap(BuiltInTypeHandler::getTypeIRI, x -> x));
    }

    public static boolean defaultCanHandle(OWLRDFConsumer c, HasIRI type, IRI s, IRI p, IRI o) {
        c.iris.inferTypes(s, o);
        return RDF_TYPE.getIRI().equals(p) && type.getIRI().equals(o);
    }

    @Override
    public IRI getPredicateIRI() {
        return RDF_TYPE.getIRI();
    }

    @Override
    public final boolean canHandleStreaming(OWLRDFConsumer c, IRI s, IRI p, IRI o) {
        return canHandleStreaming.handle(c, s, p, o);
    }

    @Override
    public final boolean canHandle(OWLRDFConsumer c, IRI s, IRI p, IRI o) {
        return canHandle.handle(c, s, p, o);
    }

    @Override
    public final boolean handleTriple(OWLRDFConsumer c, IRI s, IRI p, IRI o) {
        return handle.handle(c, s, p, o);
    }

    @Override
    public IRI getTypeIRI() {
        return typeIRI.getIRI();
    }
}
