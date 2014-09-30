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

import static org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.AbstractRDFPrefixDocumentFormat;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class TripleHandlers {

    static final Logger LOGGER = LoggerFactory.getLogger(TripleHandlers.class);

    private TripleHandlers() {}

    static class HandlerAccessor {

        /** Handlers for built in types */
        @Nonnull
        private final Map<IRI, BuiltInTypeHandler> builtInTypes;
        /**
         * Handler for triples that denote nodes which represent axioms. i.e.
         * owl:AllDisjointClasses owl:AllDisjointProperties owl:AllDifferent
         * owl:NegativePropertyAssertion owl:Axiom These need to be handled
         * separately from other types, because the base triples for annotated
         * axioms should be in the ontology before annotations on the annotated
         * versions of these axioms are parsed.
         */
        @Nonnull
        protected final Map<IRI, BuiltInTypeHandler> axiomTypes;
        /** Handlers for build in predicates */
        @Nonnull
        protected final Map<IRI, TriplePredicateHandler> predicates;
        /**
         * Handlers for general literal triples (i.e. triples which have
         * predicates that are not part of the built in OWL/RDFS/RDF vocabulary.
         * Such triples either constitute annotationIRIs of relationships
         * between an individual and a data literal (typed or untyped)
         */
        protected final List<LiteralTripleHandler> literals;
        /**
         * Handlers for general resource triples (i.e. triples which have
         * predicates that are not part of the built in OWL/RDFS/RDF vocabulary.
         * Such triples either constitute annotationIRIs or relationships
         * between an individual and another individual.
         */
        protected final List<ResourceTripleHandler> resources;
        /** The inverse of handler. */
        @Nonnull
        protected final TPInverseOfHandler inverseOf;
        /** The non built in type handler. */
        @Nonnull
        private final TPTypeHandler nonBuiltInTypes;
        @Nonnull
        protected final OWLRDFConsumer consumer;

        HandlerAccessor(@Nonnull OWLRDFConsumer r) {
            consumer = r;
            builtInTypes = getBasicTypeHandlers(r, r.getConfiguration());
            axiomTypes = getAxiomTypeHandlers(r);
            inverseOf = new TPInverseOfHandler(r);
            nonBuiltInTypes = new TPTypeHandler(r);
            predicates = getPredicateHandlers(r);
            literals = getLiteralTripleHandlers(r);
            // General resource/object triples - i.e. triples which have a
            // predicate that is not a built in IRI. Annotation properties get
            // precedence over object properties, so that if we have
            // a:A a:foo a:B and a:foo
            // is typed as both an annotation and data property then the
            // statement will be translated as an annotation on a:A
            resources = getResourceTripleHandlers(r);
        }

        void apply(@Nonnull IRI s, @Nonnull IRI p, @Nonnull OWLLiteral o) {
            if (OWLRDFConsumer.isGeneralPredicate(p)) {
                for (LiteralTripleHandler lhandler : literals) {
                    if (lhandler.canHandle(s, p, o)) {
                        lhandler.handleTriple(s, p, o);
                        return;
                    }
                }
            }
        }

        void apply(@Nonnull IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            if (OWLRDFConsumer.isGeneralPredicate(p)) {
                for (ResourceTripleHandler handler : resources) {
                    if (handler.canHandle(s, p, o)) {
                        handler.handleTriple(s, p, o);
                        return;
                    }
                }
            }
        }

        void applyAnns(@Nonnull IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            BuiltInTypeHandler handler = axiomTypes.get(o);
            if (handler != null && handler.canHandle(s, p, o)) {
                handler.handleTriple(s, p, o);
            }
        }

        public void consumeNonReservedPredicateTriples() {
            consumer.iterateResources((s, p, o) -> apply(s, p, o));
            consumer.iterateLiterals((s, p, o) -> apply(s, p, o));
        }

        public void consumeAnnotatedAxioms() {
            consumer.iterateResources((s, p, o) -> applyAnns(s, p, o));
        }

        /**
         * Called when a resource triple has been parsed.
         * 
         * @param s
         *        The subject of the triple that has been parsed
         * @param p
         *        The predicate of the triple that has been parsed
         * @param o
         *        The object of the triple that has been parsed
         */
        public void handleStreaming(@Nonnull IRI s, @Nonnull IRI p,
                @Nonnull IRI o) {
            boolean consumed = false;
            if (p.equals(RDF_TYPE.getIRI())) {
                BuiltInTypeHandler handler = builtInTypes.get(o);
                if (handler != null) {
                    if (handler.canHandleStreaming(s, p, o)) {
                        handler.handleTriple(s, p, o);
                        consumed = true;
                    }
                } else if (axiomTypes.get(o) == null) {
                    // Not a built in type
                    consumer.addOWLNamedIndividual(s, false);
                    if (nonBuiltInTypes.canHandleStreaming(s, p, o)) {
                        nonBuiltInTypes.handleTriple(s, p, o);
                        consumed = true;
                    }
                } else {
                    consumer.addAxiom(s);
                }
            } else {
                ResourceTripleHandler handler = predicates.get(p);
                if (handler != null) {
                    if (handler.canHandleStreaming(s, p, o)) {
                        handler.handleTriple(s, p, o);
                        consumed = true;
                    }
                } else {
                    for (ResourceTripleHandler r : resources) {
                        if (r.canHandleStreaming(s, p, o)) {
                            r.handleTriple(s, p, o);
                            consumed = true;
                            break;
                        }
                    }
                }
            }
            if (!consumed) {
                // Not consumed, so add the triple
                consumer.addTriple(s, p, o);
            }
        }

        public void handleStreaming(@Nonnull IRI s, @Nonnull IRI p,
                @Nonnull String literal, IRI datatype, String lang) {
            // Convert all literals to OWLConstants
            OWLLiteral con = consumer.getOWLLiteral(literal, datatype, lang);
            handleStreaming(s, p, con);
        }

        private void handleStreaming(@Nonnull IRI s, @Nonnull IRI p,
                @Nonnull OWLLiteral con) {
            for (LiteralTripleHandler handler : literals) {
                if (handler.canHandleStreaming(s, p, con)) {
                    handler.handleTriple(s, p, con);
                    return;
                }
            }
            consumer.addTriple(s, p, con);
        }

        /**
         * Handles triples in a non-streaming mode. Type triples whose type is
         * an axiom type, are NOT handled.
         * 
         * @param s
         *        The subject of the triple
         * @param p
         *        The predicate of the triple
         * @param o
         *        The object of the triple
         */
        protected void handle(@Nonnull IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            if (p.equals(OWLRDFVocabulary.RDF_TYPE.getIRI())) {
                BuiltInTypeHandler typeHandler = builtInTypes.get(o);
                if (typeHandler != null) {
                    typeHandler.handleTriple(s, p, o);
                } else if (axiomTypes.get(o) == null) {
                    // C(a)
                    OWLIndividual ind = consumer.translateIndividual(s);
                    OWLClassExpression ce = consumer.translatorAccessor
                            .translateClassExpression(o);
                    consumer.addAxiom(consumer.getDataFactory()
                            .getOWLClassAssertionAxiom(ce, ind,
                                    consumer.getPendingAnnotations()));
                }
            } else {
                TriplePredicateHandler handler = predicates.get(p);
                if (handler != null && handler.canHandle(s, p, o)) {
                    handler.handleTriple(s, p, o);
                } else {
                    for (ResourceTripleHandler resHandler : resources) {
                        if (resHandler.canHandle(s, p, o)) {
                            resHandler.handleTriple(s, p, o);
                            break;
                        }
                    }
                }
            }
        }

        protected void handle(@Nonnull IRI s, @Nonnull IRI p,
                @Nonnull OWLLiteral o) {
            for (LiteralTripleHandler handler : literals) {
                if (handler.canHandle(s, p, o)) {
                    handler.handleTriple(s, p, o);
                    break;
                }
            }
        }

        /**
         * We need to mop up all remaining triples. These triples will be in the
         * triples by subject map. Other triples which reside in the triples by
         * predicate (single valued) triple aren't "root" triples for axioms.
         * First we translate all system triples and then go for triples whose
         * predicates are not system/reserved vocabulary IRIs to translate these
         * into ABox assertions or annotationIRIs
         * 
         * @return any remaining triples
         */
        @Nonnull
        public Set<RDFTriple> mopUp() {
            // We need to mop up all remaining triples. These triples will be in
            // the triples by subject map. Other triples which reside in the
            // triples by predicate (single valued) triple aren't "root" triples
            // for axioms. First we translate all system triples, starting with
            // property ranges, then go for triples whose predicates are not
            // system/reserved vocabulary IRIs to translate these into ABox
            // assertions or annotationIRIs
            TriplePredicateHandler propertyRangeHandler = predicates
                    .get(RDFS_RANGE.getIRI());
            consumer.iterateResources((s, p, o) -> {
                if (propertyRangeHandler.canHandle(s, p, o)) {
                    propertyRangeHandler.handleTriple(s, p, o);
                }
            });
            // Now handle non-reserved predicate triples
            consumeNonReservedPredicateTriples();
            // Now axiom annotations
            consumeAnnotatedAxioms();
            consumer.iterateResources((s, p, o) -> handle(s, p, o));
            consumer.iterateLiterals((s, p, o) -> handle(s, p, o));
            // Inverse property axioms
            inverseOf.setAxiomParsingMode(true);
            consumer.iterateResources((s, p, o) -> {
                if (inverseOf.canHandle(s, p, o)) {
                    inverseOf.handleTriple(s, p, o);
                }
            });
            return getRemainingTriples();
        }

        @Nonnull
        private Set<RDFTriple> getRemainingTriples() {
            Set<RDFTriple> remaining = new HashSet<>();
            consumer.iterateResources((s, p, o) -> remaining.add(new RDFTriple(
                    s, consumer.isAnonymousNode(s), p, o, consumer
                            .isAnonymousNode(o))));
            consumer.iterateLiterals((s, p, o) -> remaining.add(new RDFTriple(
                    s, consumer.isAnonymousNode(s), p, o)));
            return remaining;
        }

        @Nonnull
        private static List<ResourceTripleHandler> getResourceTripleHandlers(
                @Nonnull OWLRDFConsumer r) {
            return CollectionFactory
                    .list((ResourceTripleHandler) new GTPObjectPropertyAssertionHandler(
                            r), new GTPAnnotationResourceTripleHandler(r));
        }

        @Nonnull
        protected Map<IRI, TriplePredicateHandler> getPredicateHandlers(
                @Nonnull OWLRDFConsumer r) {
            Map<IRI, TriplePredicateHandler> predicateHandlers = new ConcurrentHashMap<>();
            add(predicateHandlers, new TPDifferentFromHandler(r));
            add(predicateHandlers, new TPDisjointUnionHandler(r));
            add(predicateHandlers, new TPDisjointWithHandler(r));
            add(predicateHandlers, new TPEquivalentClassHandler(r));
            add(predicateHandlers, new TPEquivalentPropertyHandler(r));
            add(predicateHandlers, new TPPropertyDomainHandler(r));
            add(predicateHandlers, new TPPropertyRangeHandler(r));
            add(predicateHandlers, new TPSameAsHandler(r));
            add(predicateHandlers, new TPSubClassOfHandler(r));
            add(predicateHandlers, new TPSubPropertyOfHandler(r));
            add(predicateHandlers, nonBuiltInTypes);
            add(predicateHandlers, new TPDistinctMembersHandler(r));
            add(predicateHandlers, new TPImportsHandler(r));
            add(predicateHandlers, new TPIntersectionOfHandler(r));
            add(predicateHandlers, new TPUnionOfHandler(r));
            add(predicateHandlers, new TPComplementOfHandler(r));
            add(predicateHandlers, new TPOneOfHandler(r));
            add(predicateHandlers, new TPSomeValuesFromHandler(r));
            add(predicateHandlers, new TPAllValuesFromHandler(r));
            add(predicateHandlers, new TPRestHandler(r));
            add(predicateHandlers, new TPFirstResourceHandler(r));
            add(predicateHandlers, new TPDeclaredAsHandler(r));
            add(predicateHandlers, new TPHasKeyHandler(r));
            add(predicateHandlers, new TPVersionIRIHandler(r));
            add(predicateHandlers, new TPPropertyChainAxiomHandler(r));
            add(predicateHandlers, new TPAnnotatedSourceHandler(r));
            add(predicateHandlers, new TPAnnotatedPropertyHandler(r));
            add(predicateHandlers, new TPAnnotatedTargetHandler(r));
            add(predicateHandlers, new TPPropertyDisjointWithHandler(r));
            add(predicateHandlers, inverseOf);
            add(predicateHandlers, new TPOnPropertyHandler(r));
            add(predicateHandlers, new TPOnClassHandler(r));
            add(predicateHandlers, new TPOnDataRangeHandler(r));
            add(predicateHandlers, new TPComplementOfHandler(r));
            add(predicateHandlers, new TPDatatypeComplementOfHandler(r));
            return predicateHandlers;
        }

        @Nonnull
        public static Map<IRI, BuiltInTypeHandler> getAxiomTypeHandlers(
                @Nonnull OWLRDFConsumer r) {
            Map<IRI, BuiltInTypeHandler> map = new ConcurrentHashMap<>();
            add(map, new TypeAxiomHandler(r));
            add(map, new TypeAllDifferentHandler(r));
            add(map, new TypeAllDisjointClassesHandler(r));
            add(map, new TypeAllDisjointPropertiesHandler(r));
            add(map, new TypeNegativePropertyAssertionHandler(r));
            return map;
        }

        /**
         * General literal triples - i.e. triples which have a predicate that is
         * not a built in IRI. Annotation properties get precedence over data
         * properties, so that if we have the statement<br>
         * a:A a:foo a:B<br>
         * and a:foo is typed as both an annotation and data property then the
         * statement will be translated as an annotation on a:A
         * 
         * @param r
         *        consumer
         * @return handlers
         */
        @Nonnull
        public static List<LiteralTripleHandler> getLiteralTripleHandlers(
                @Nonnull OWLRDFConsumer r) {
            return CollectionFactory.list(
                    (LiteralTripleHandler) new GTPDataPropertyAssertionHandler(
                            r), new TPFirstLiteralHandler(r),
                    new GTPAnnotationLiteralHandler(r));
        }

        private static void add(@Nonnull Map<IRI, BuiltInTypeHandler> m,
                @Nonnull BuiltInTypeHandler h) {
            m.put(h.getTypeIRI(), h);
        }

        private static void add(@Nonnull Map<IRI, TriplePredicateHandler> map,
                @Nonnull TriplePredicateHandler h) {
            map.put(h.getPredicateIRI(), h);
        }

        @Nonnull
        public static Map<IRI, BuiltInTypeHandler> getBasicTypeHandlers(
                @Nonnull OWLRDFConsumer r,
                @Nonnull OWLOntologyLoaderConfiguration config) {
            Map<IRI, BuiltInTypeHandler> map = new ConcurrentHashMap<>();
            add(map, new TypeOntologyPropertyHandler(r));
            add(map, new TypeAsymmetricPropertyHandler(r));
            add(map, new TypeClassHandler(r));
            add(map, new TypeObjectPropertyHandler(r));
            add(map, new TypeDataPropertyHandler(r));
            add(map, new TypeDatatypeHandler(r));
            add(map, new TypeFunctionalPropertyHandler(r));
            add(map, new TypeInverseFunctionalPropertyHandler(r));
            add(map, new TypeIrreflexivePropertyHandler(r));
            add(map, new TypeReflexivePropertyHandler(r));
            add(map, new TypeSymmetricPropertyHandler(r));
            add(map, new TypeTransitivePropertyHandler(r));
            add(map, new TypeRestrictionHandler(r));
            add(map, new TypeListHandler(r));
            add(map, new TypeAnnotationPropertyHandler(r));
            add(map, new TypeDeprecatedClassHandler(r));
            add(map, new TypeDeprecatedPropertyHandler(r));
            add(map, new TypeDataRangeHandler(r));
            add(map, new TypeOntologyHandler(r));
            add(map, new TypeNegativeDataPropertyAssertionHandler(r));
            add(map, new TypeRDFSClassHandler(r));
            add(map, new TypeSelfRestrictionHandler(r));
            add(map, new TypePropertyHandler(r));
            add(map, new TypeNamedIndividualHandler(r));
            add(map, new TypeAnnotationHandler(r));
            if (!config.isStrict()) {
                add(map, new TypeSWRLAtomListHandler(r));
                add(map, new TypeSWRLBuiltInAtomHandler(r));
                add(map, new TypeSWRLBuiltInHandler(r));
                add(map, new TypeSWRLClassAtomHandler(r));
                add(map, new TypeSWRLDataRangeAtomHandler(r));
                add(map, new TypeSWRLDataValuedPropertyAtomHandler(r));
                add(map, new TypeSWRLDifferentIndividualsAtomHandler(r));
                add(map, new TypeSWRLImpHandler(r));
                add(map, new TypeSWRLIndividualPropertyAtomHandler(r));
                add(map, new TypeSWRLSameIndividualAtomHandler(r));
                add(map, new TypeSWRLVariableHandler(r));
            }
            return map;
        }
    }

    /**
     * A base handler for equivalent class axioms where the axiom is stated in a
     * direct way without an equivalent class triple. For example A
     * intersectionOf (C or C)
     */
    abstract static class AbstractNamedEquivalentClassAxiomHandler extends
            AbstractTriplePredicateHandler {

        AbstractNamedEquivalentClassAxiomHandler(
                @Nonnull OWLRDFConsumer consumer, IRI predicateIRI) {
            super(consumer, predicateIRI);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            return super.canHandle(s, p, o) && !isAnonymous(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            Set<OWLClassExpression> operands = new HashSet<>();
            operands.add(translateClassExpression(s));
            operands.add(translateEquivalentClass(o));
            add(df.getOWLEquivalentClassesAxiom(operands));
        }

        protected abstract OWLClassExpression translateEquivalentClass(
                @Nonnull IRI mainNode);
    }

    abstract static class AbstractResourceTripleHandler extends
            AbstractTripleHandler implements ResourceTripleHandler {

        protected AbstractResourceTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        protected boolean isSubjectOrObjectAnonymous(@Nonnull IRI s,
                @Nonnull IRI o) {
            return isAnonymous(s) || isAnonymous(o);
        }

        protected boolean
                isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(
                        IRI s, IRI o) {
            return isSubjectAndObjectClassExpression(s, o)
                    || isSubjectAndObjectDataRange(s, o);
        }

        protected boolean isSubjectAndObjectDataRange(IRI s, IRI o) {
            return consumer.isDataRange(s) && consumer.isDataRange(o);
        }

        protected boolean isSubjectAndObjectClassExpression(IRI s, IRI o) {
            return consumer.isClassExpression(s)
                    && consumer.isClassExpression(o);
        }

        /**
         * @param s
         *        subject
         * @param o
         *        object
         */
        protected void inferTypes(IRI s, IRI o) {
            if (consumer.isClassExpression(o)) {
                consumer.addClassExpression(s, false);
            } else if (consumer.isDataRange(o)) {
                consumer.addDataRange(s, false);
            } else if (consumer.isClassExpression(s)) {
                consumer.addClassExpression(o, false);
            } else if (consumer.isDataRange(s)) {
                consumer.addDataRange(o, false);
            }
        }
    }

    static class AbstractTripleHandler {

        @Nonnull
        protected final OWLRDFConsumer consumer;
        @Nonnull
        private final TypeMatcher classExpressionMatcher = node -> isClassExpressionStrict(node);
        @Nonnull
        private final TypeMatcher dataRangeMatcher = node -> isDataRangeStrict(node);
        @Nonnull
        private final TypeMatcher individualMatcher = node -> true;
        protected final OWLDataFactory df;

        protected AbstractTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            this.consumer = consumer;
            df = consumer.getDataFactory();
        }

        @Nonnull
        protected Set<OWLAnnotation> anns() {
            return consumer.getPendingAnnotations();
        }

        protected void consumeTriple(IRI s, IRI p, IRI o) {
            consumer.consumeTriple(s, p, o);
        }

        protected void consumeTriple(IRI s, IRI p, OWLLiteral o) {
            consumer.consumeTriple(s, p, o);
        }

        protected boolean isStrict() {
            return consumer.getConfiguration().isStrict();
        }

        protected boolean isObjectPropertyOnly(IRI iri) {
            return consumer.isObjectPropertyOnly(iri);
        }

        protected boolean isDataPropertyOnly(IRI iri) {
            return consumer.isDataPropertyOnly(iri);
        }

        protected boolean isAnnotationPropertyOnly(IRI iri) {
            return consumer.isAnnotationPropertyOnly(iri);
        }

        protected boolean isAnnotationPropertyStrict(IRI iri) {
            return consumer.isAnnotationPropertyOnly(iri);
        }

        protected boolean isAnnotationPropertyLax(IRI iri) {
            return consumer.isAnnotationProperty(iri);
        }

        protected void add(@Nonnull OWLAxiom axiom) {
            consumer.addAxiom(axiom);
        }

        @Nonnull
        protected OWLClassExpression translateClassExpression(@Nonnull IRI iri) {
            return consumer.translatorAccessor.translateClassExpression(iri);
        }

        @Nonnull
        protected OWLObjectPropertyExpression translateObjectProperty(
                @Nonnull IRI iri) {
            return consumer.translateObjectPropertyExpression(iri);
        }

        @Nonnull
        protected OWLDataPropertyExpression translateDataProperty(
                @Nonnull IRI iri) {
            return consumer.translateDataPropertyExpression(iri);
        }

        @Nonnull
        protected OWLDataRange translateDataRange(@Nonnull IRI iri) {
            return consumer.translateDataRange(iri);
        }

        @Nonnull
        protected OWLIndividual translateIndividual(@Nonnull IRI iri) {
            return consumer.translateIndividual(iri);
        }

        protected boolean isAnonymous(@Nonnull IRI node) {
            return consumer.isAnonymousNode(node);
        }

        protected boolean isResourcePresent(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            return consumer.getResourceObject(mainNode, p, false) != null;
        }

        protected boolean isLiteralPresent(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            return consumer.getLiteralObject(mainNode, p, false) != null;
        }

        protected boolean isRestrictionStrict(@Nonnull IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isRestrictionLax(@Nonnull IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isNonNegativeIntegerStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, p, false);
            if (literal == null) {
                return false;
            }
            OWLDatatype datatype = literal.getDatatype();
            OWL2Datatype nni = OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
            return datatype.getIRI().equals(nni.getIRI())
                    && nni.isInLexicalSpace(literal.getLiteral());
        }

        protected boolean isNonNegativeIntegerLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, p, false);
            if (literal == null) {
                return false;
            }
            return OWL2Datatype.XSD_INTEGER
                    .isInLexicalSpace(verifyNotNull(literal.getLiteral().trim()));
        }

        protected int translateInteger(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, p, true);
            if (literal == null) {
                return 0;
            }
            try {
                return Integer.parseInt(literal.getLiteral().trim());
            } catch (@SuppressWarnings("unused") NumberFormatException e) {
                return 0;
            }
        }

        protected boolean isClassExpressionStrict(@Nonnull IRI node) {
            return consumer.isClassExpression(node)
                    && !consumer.isDataRange(node);
        }

        protected boolean isClassExpressionStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return o != null && isClassExpressionStrict(o);
        }

        protected boolean isClassExpressionLax(@Nonnull IRI mainNode) {
            return consumer.isClassExpression(mainNode)
                    || consumer.isParsedAllTriples()
                    && !consumer.isDataRange(mainNode);
        }

        protected boolean isClassExpressionLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return o != null && isClassExpressionLax(o);
        }

        protected boolean isObjectPropertyStrict(@Nonnull IRI node) {
            return consumer.isObjectPropertyOnly(node);
        }

        protected boolean isObjectPropertyStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return o != null && isObjectPropertyStrict(o);
        }

        protected boolean isObjectPropertyLax(@Nonnull IRI node) {
            return consumer.isObjectProperty(node);
        }

        protected boolean isObjectPropertyLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return o != null && isObjectPropertyLax(o);
        }

        protected boolean isDataPropertyStrict(@Nonnull IRI node) {
            return consumer.isDataPropertyOnly(node);
        }

        protected boolean isDataPropertyStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return o != null && isDataPropertyStrict(o);
        }

        protected boolean isDataPropertyLax(@Nonnull IRI node) {
            return consumer.isDataProperty(node);
        }

        protected boolean isDataPropertyLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return o != null && isDataPropertyLax(o);
        }

        protected boolean isDataRangeStrict(@Nonnull IRI node) {
            return consumer.isDataRange(node)
                    && !consumer.isClassExpression(node);
        }

        protected boolean isDataRangeStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return isDataRangeStrict(o);
        }

        protected boolean isDataRangeLax(@Nonnull IRI node) {
            return consumer.isDataRange(node);
        }

        protected boolean isDataRangeLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary p) {
            IRI o = consumer.getResourceObject(mainNode, p, false);
            return o != null && isDataRangeLax(mainNode);
        }

        protected boolean isClassExpressionListStrict(@Nonnull IRI mainNode,
                int minSize) {
            return isResourceListStrict(mainNode, classExpressionMatcher,
                    minSize);
        }

        protected boolean isDataRangeListStrict(@Nonnull IRI mainNode,
                int minSize) {
            return isResourceListStrict(mainNode, dataRangeMatcher, minSize);
        }

        protected boolean isIndividualListStrict(@Nonnull IRI mainNode,
                int minSize) {
            return isResourceListStrict(mainNode, individualMatcher, minSize);
        }

        protected boolean isResourceListStrict(@Nullable IRI mainNode,
                @Nonnull TypeMatcher typeMatcher, int minSize) {
            if (mainNode == null) {
                return false;
            }
            IRI currentListNode = mainNode;
            Set<IRI> visitedListNodes = new HashSet<>();
            int size = 0;
            while (true) {
                IRI firstObject = consumer.getResourceObject(currentListNode,
                        RDF_FIRST, false);
                if (firstObject == null) {
                    return false;
                }
                if (!typeMatcher.isTypeStrict(firstObject)) {
                    // Something in the list that is not of the required type
                    return false;
                } else {
                    size++;
                }
                IRI restObject = consumer.getResourceObject(currentListNode,
                        RDF_REST, false);
                if (visitedListNodes.contains(restObject)) {
                    // Cycle - Non-terminating
                    return false;
                }
                if (restObject == null) {
                    // Not terminated properly
                    return false;
                }
                if (restObject.equals(RDF_NIL.getIRI())) {
                    // Terminated properly
                    return size >= minSize;
                }
                // Carry on
                visitedListNodes.add(restObject);
                currentListNode = restObject;
            }
        }
    }

    abstract static class AbstractBuiltInTypeHandler extends
            AbstractTriplePredicateHandler implements BuiltInTypeHandler {

        private final IRI typeIRI;

        protected AbstractBuiltInTypeHandler(@Nonnull OWLRDFConsumer consumer,
                IRI typeIRI) {
            super(consumer, RDF_TYPE.getIRI());
            this.typeIRI = typeIRI;
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            return p.equals(RDF_TYPE.getIRI()) && o.equals(typeIRI);
        }

        @Override
        public IRI getTypeIRI() {
            return typeIRI;
        }
    }

    static class GTPAnnotationLiteralHandler extends AbstractTripleHandler
            implements LiteralTripleHandler {

        GTPAnnotationLiteralHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, OWLLiteral o) {
            return !isAnonymous(s) && !consumer.isAnnotation(s)
                    && consumer.isAnnotationProperty(p);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, OWLLiteral o) {
            if (isStrict()) {
                return isAnnotationPropertyOnly(p);
            }
            boolean axiom = consumer.isAxiom(s);
            if (axiom) {
                return false;
            }
            boolean annotation = consumer.isAnnotation(s);
            if (annotation) {
                return false;
            }
            if (consumer.isAnnotationProperty(p)) {
                return true;
            }
            if (!isAnonymous(s)) {
                if (isClassExpressionLax(s)) {
                    return true;
                }
                if (isDataRangeLax(s)) {
                    return true;
                }
                if (isObjectPropertyLax(s)) {
                    return true;
                }
                if (isDataPropertyLax(s)) {
                    return true;
                }
                return false;
            }
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI s, IRI p, OWLLiteral o) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(p);
            OWLAnnotationSubject annotationSubject;
            if (isAnonymous(s)) {
                annotationSubject = df.getOWLAnonymousIndividual(s.toString());
            } else {
                annotationSubject = s;
            }
            if (consumer.isOntology(s)) {
                consumer.addOntologyAnnotation(df.getOWLAnnotation(prop, o,
                        anns()));
            } else {
                OWLAnnotationAssertionAxiom ax = df
                        .getOWLAnnotationAssertionAxiom(prop,
                                annotationSubject, o, anns());
                add(ax);
            }
            consumeTriple(s, p, o);
        }
    }

    static class GTPAnnotationResourceTripleHandler extends
            AbstractResourceTripleHandler {

        GTPAnnotationResourceTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                return false;
            } else {
                return !isAnonymous(s) && !isAnonymous(o)
                        && consumer.isAnnotationProperty(p);
            }
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            boolean builtInAnnotationProperty = BUILT_IN_ANNOTATION_PROPERTY_IRIS
                    .contains(p);
            return !consumer.isAxiom(s) && !consumer.isAnnotation(s)
                    && (builtInAnnotationProperty || !p.isReservedVocabulary());
        }

        @Override
        public void handleTriple(@Nonnull IRI s, IRI p, @Nonnull IRI o) {
            OWLAnnotationValue value;
            if (isAnonymous(o)) {
                value = df.getOWLAnonymousIndividual(o.toString());
            } else {
                value = o;
            }
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(p);
            OWLAnnotation anno = df.getOWLAnnotation(prop, value);
            OWLAnnotationSubject annoSubject;
            if (isAnonymous(s)) {
                annoSubject = df.getOWLAnonymousIndividual(s.toString());
            } else {
                annoSubject = s;
            }
            if (consumer.isOntology(s)) {
                // Assume we annotation our ontology?
                consumer.addOntologyAnnotation(anno);
            } else {
                OWLAxiom decAx = df.getOWLAnnotationAssertionAxiom(annoSubject,
                        anno, anns());
                add(decAx);
            }
            consumeTriple(s, p, o);
        }
    }

    static class GTPDataPropertyAssertionHandler extends AbstractTripleHandler
            implements LiteralTripleHandler {

        GTPDataPropertyAssertionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, OWLLiteral o) {
            if (isStrict()) {
                return isDataPropertyStrict(p);
            } else {
                // Handle annotation assertions as annotation assertions only!
                return isDataPropertyLax(p)
                        && !consumer.isAnnotationProperty(p);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, OWLLiteral o) {
            return false;
        }

        @Override
        public void handleTriple(@Nonnull IRI s, IRI p, OWLLiteral o) {
            add(df.getOWLDataPropertyAssertionAxiom(translateDataProperty(p),
                    translateIndividual(s), o, anns()));
            consumeTriple(s, p, o);
        }
    }

    static class GTPLiteralTripleHandler extends AbstractTripleHandler
            implements LiteralTripleHandler {

        GTPLiteralTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public void handleTriple(IRI s, IRI p, OWLLiteral o) {
            if (isStrict()) {} else {
                if (isAnnotationPropertyLax(p)) {} else {}
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, OWLLiteral o) {
            if (isStrict()) {
                return false;
            }
            return isAnnotationPropertyLax(p);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, OWLLiteral o) {
            return isAnnotationPropertyStrict(p) || isDataPropertyStrict(p);
        }
    }

    static class GTPObjectPropertyAssertionHandler extends
            AbstractResourceTripleHandler {

        GTPObjectPropertyAssertionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                return isObjectPropertyStrict(p);
            } else {
                // Handle annotation assertions as annotation assertions only!
                return isObjectPropertyLax(p) && !isAnnotationPropertyOnly(p);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(@Nonnull IRI s, IRI p, @Nonnull IRI o) {
            if (consumer.isObjectProperty(p)) {
                consumeTriple(s, p, o);
                add(df.getOWLObjectPropertyAssertionAxiom(
                        translateObjectProperty(p), translateIndividual(s),
                        translateIndividual(o), anns()));
            }
        }
    }

    static class GTPResourceTripleHandler extends AbstractResourceTripleHandler {

        GTPResourceTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return false;
        }
    }

    static class SKOSClassTripleHandler extends AbstractBuiltInTypeHandler {

        SKOSClassTripleHandler(@Nonnull OWLRDFConsumer consumer,
                @Nonnull SKOSVocabulary v) {
            super(consumer, v.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            OWLIndividual ind = df.getOWLNamedIndividual(s);
            OWLClass skosConcept = df.getOWLClass(o);
            add(df.getOWLClassAssertionAxiom(skosConcept, ind));
        }
    }

    static class TPAllValuesFromHandler extends AbstractTriplePredicateHandler {

        TPAllValuesFromHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_VALUES_FROM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addOWLRestriction(s, false);
            IRI propIRI = consumer.getResourceObject(s,
                    OWL_ON_PROPERTY.getIRI(), false);
            if (propIRI != null
                    && (!consumer.isAnonymousNode(o) || consumer.translatorAccessor
                            .getClassExpressionIfTranslated(o) != null)) {
                // The filler is either a datatype or named class
                if (consumer.isObjectPropertyOnly(propIRI)) {
                    consumer.addClassExpression(o, false);
                    consumer.addTriple(s, p, o);
                    consumer.translatorAccessor.translateClassExpression(s);
                    return true;
                } else if (consumer.isDataPropertyOnly(propIRI)) {}
            }
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}
    }

    static class TPAnnotatedPropertyHandler extends
            AbstractTriplePredicateHandler {

        TPAnnotatedPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATED_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addAnnotatedSource(o, s);
            consumer.checkForAndProcessAnnotatedDeclaration(s);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}
    }

    static class TPAnnotatedSourceHandler extends
            AbstractTriplePredicateHandler {

        TPAnnotatedSourceHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATED_SOURCE.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addAnnotatedSource(o, s);
            consumer.checkForAndProcessAnnotatedDeclaration(s);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}
    }

    static class TPAnnotatedTargetHandler extends
            AbstractTriplePredicateHandler {

        TPAnnotatedTargetHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATED_TARGET.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addAnnotatedSource(o, s);
            consumer.checkForAndProcessAnnotatedDeclaration(s);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}
    }

    static class TPComplementOfHandler extends
            AbstractNamedEquivalentClassAxiomHandler {

        TPComplementOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_COMPLEMENT_OF.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addClassExpression(s, false);
            consumer.addClassExpression(o, false);
            return super.canHandleStreaming(s, p, o);
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(
                @Nonnull IRI mainNode) {
            return df.getOWLObjectComplementOf(consumer.translatorAccessor
                    .translateClassExpression(mainNode));
        }
    }

    static class TPDatatypeComplementOfHandler extends
            AbstractTriplePredicateHandler {

        TPDatatypeComplementOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DATATYPE_COMPLEMENT_OF.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addDataRange(s, false);
            consumer.addDataRange(o, false);
            return false;
        }
    }

    @Deprecated
    static class TPDeclaredAsHandler extends AbstractTriplePredicateHandler {

        TPDeclaredAsHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, DeprecatedVocabulary.OWL_DECLARED_AS);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, @Nonnull IRI o) {
            if (o.equals(OWL_CLASS.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLClass(s), anns()));
            } else if (o.equals(OWL_OBJECT_PROPERTY.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLObjectProperty(s),
                        anns()));
            } else if (o.equals(OWL_DATA_PROPERTY.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLDataProperty(s), anns()));
            } else if (o.equals(OWL_DATATYPE.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLDatatype(s), anns()));
            }
        }
    }

    static class TPDifferentFromHandler extends AbstractTriplePredicateHandler {

        TPDifferentFromHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DIFFERENT_FROM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI s, IRI p, @Nonnull IRI o) {
            Set<OWLIndividual> inds = new HashSet<>();
            inds.add(translateIndividual(s));
            inds.add(translateIndividual(o));
            add(df.getOWLDifferentIndividualsAxiom(inds, anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPDisjointUnionHandler extends AbstractTriplePredicateHandler {

        TPDisjointUnionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DISJOINT_UNION_OF.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            return super.canHandle(s, p, o) && !consumer.isAnonymousNode(s)
                    && consumer.isClassExpression(s);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addClassExpression(s, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!consumer.isAnonymousNode(s)) {
                OWLClass cls = (OWLClass) translateClassExpression(s);
                Set<OWLClassExpression> classExpressions = consumer.translatorAccessor
                        .translateToClassExpressionSet(o);
                add(df.getOWLDisjointUnionAxiom(cls, classExpressions, anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TPDisjointWithHandler extends AbstractTriplePredicateHandler {

        TPDisjointWithHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DISJOINT_WITH.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addClassExpression(s, false);
            consumer.addClassExpression(o, false);
            // NB: In strict parsing the above type triples won't get added
            // because
            // they aren't explicit,
            // so we need an extra check to see if there are type triples for
            // the
            // classes
            return !isSubjectOrObjectAnonymous(s, o)
                    && isSubjectAndObjectClassExpression(s, o);
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            return super.canHandle(s, p, o)
                    && isSubjectAndObjectClassExpression(s, o);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            Set<OWLClassExpression> operands = new HashSet<>();
            operands.add(translateClassExpression(s));
            operands.add(translateClassExpression(o));
            add(df.getOWLDisjointClassesAxiom(operands, anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPDistinctMembersHandler extends
            AbstractTriplePredicateHandler {

        TPDistinctMembersHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DISTINCT_MEMBERS.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            // We need all of the list triples to be loaded :(
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            Set<OWLIndividual> inds = consumer.translatorAccessor
                    .translateToIndividualSet(o);
            add(df.getOWLDifferentIndividualsAxiom(inds, anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPEquivalentClassHandler extends
            AbstractTriplePredicateHandler {

        TPEquivalentClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_EQUIVALENT_CLASS.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            inferTypes(s, o);
            return super.canHandle(s, p, o)
                    && isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(
                            s, o);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return !isStrict()
                    && !isSubjectOrObjectAnonymous(s, o)
                    && isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(
                            s, o);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                if (isClassExpressionStrict(s) && isClassExpressionStrict(o)) {
                    translateEquivalentClasses(s, p, o);
                } else if (isDataRangeStrict(s) && isDataRangeStrict(o)) {
                    translateEquivalentDataRanges(s, p, o);
                }
            } else {
                if (isClassExpressionLax(s) && isClassExpressionLax(o)) {
                    translateEquivalentClasses(s, p, o);
                } else if (isDataRangeLax(s) || isDataRangeLax(o)) {
                    translateEquivalentDataRanges(s, p, o);
                }
            }
        }

        private void translateEquivalentDataRanges(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o) {
            OWLDatatype datatype = df.getOWLDatatype(s);
            OWLDataRange dataRange = consumer.translateDataRange(o);
            OWLDatatypeDefinitionAxiom def = df.getOWLDatatypeDefinitionAxiom(
                    datatype, dataRange, anns());
            add(def);
            consumeTriple(s, p, o);
        }

        private void translateEquivalentClasses(@Nonnull IRI s, @Nonnull IRI p,
                @Nonnull IRI o) {
            Set<OWLClassExpression> operands = new HashSet<>();
            operands.add(translateClassExpression(s));
            operands.add(translateClassExpression(o));
            add(df.getOWLEquivalentClassesAxiom(operands, anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPEquivalentPropertyHandler extends
            AbstractTriplePredicateHandler {

        TPEquivalentPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_EQUIVALENT_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            Set<OWLAnnotation> pendingAnnotations = anns();
            if (consumer.isObjectProperty(s) && consumer.isObjectProperty(o)) {
                Set<OWLObjectPropertyExpression> props = new HashSet<>();
                props.add(translateObjectProperty(s));
                props.add(translateObjectProperty(o));
                add(df.getOWLEquivalentObjectPropertiesAxiom(props,
                        pendingAnnotations));
                consumeTriple(s, p, o);
            }
            if (consumer.isDataProperty(s) && consumer.isDataProperty(o)) {
                Set<OWLDataPropertyExpression> props = new HashSet<>();
                props.add(translateDataProperty(s));
                props.add(translateDataProperty(o));
                add(df.getOWLEquivalentDataPropertiesAxiom(props,
                        pendingAnnotations));
                consumeTriple(s, p, o);
            }
            // TODO: LOG ERROR
        }
    }

    static class TPFirstLiteralHandler extends AbstractTripleHandler implements
            LiteralTripleHandler {

        TPFirstLiteralHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandle(IRI s, @Nullable IRI p, OWLLiteral o) {
            return p != null && p.equals(RDF_FIRST.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, @Nullable IRI p, OWLLiteral o) {
            return p != null && p.equals(RDF_FIRST.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, OWLLiteral o) {
            consumer.addFirst(s, o);
            consumeTriple(s, p, o);
        }
    }

    static class TPFirstResourceHandler extends AbstractTriplePredicateHandler {

        TPFirstResourceHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_FIRST.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addFirst(s, o);
            consumeTriple(s, p, o);
        }
    }

    static class TPHasKeyHandler extends AbstractTriplePredicateHandler {

        private final OptimisedListTranslator<OWLPropertyExpression> listTranslator;

        TPHasKeyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_HAS_KEY.getIRI());
            listTranslator = Translators.getListTranslator(consumer);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addClassExpression(s, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isClassExpression(s)) {
                consumeTriple(s, p, o);
                OWLClassExpression ce = translateClassExpression(s);
                Set<OWLPropertyExpression> props = listTranslator
                        .translateToSet(o);
                add(df.getOWLHasKeyAxiom(ce, props, anns()));
            }
        }
    }

    static class TPHasValueHandler extends AbstractTriplePredicateHandler {

        TPHasValueHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_HAS_VALUE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addOWLRestriction(s, false);
            return false;
        }
    }

    static class TPImportsHandler extends AbstractTriplePredicateHandler {

        TPImportsHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_IMPORTS.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            consumer.addOntology(s);
            consumer.addOntology(o);
            OWLImportsDeclaration importsDeclaration = df
                    .getOWLImportsDeclaration(o);
            consumer.addImport(importsDeclaration);
            if (!consumer.getConfiguration().isIgnoredImport(o)) {
                OWLOntologyManager man = consumer.getOWLOntologyManager();
                man.makeLoadImportRequest(importsDeclaration,
                        consumer.getConfiguration());
                OWLOntology importedOntology = man
                        .getImportedOntology(importsDeclaration);
                if (importedOntology != null) {
                    OWLDocumentFormat importedOntologyFormat = man
                            .getOntologyFormat(importedOntology);
                    if (importedOntologyFormat instanceof AbstractRDFPrefixDocumentFormat
                            && importedOntology.isAnonymous()) {
                        if (consumer.getConfiguration()
                                .getMissingOntologyHeaderStrategy() == INCLUDE_GRAPH) {
                            // We should have just included the triples rather
                            // than imported them. So,
                            // we remove the imports statement, add the axioms
                            // from the imported ontology to
                            // out importing ontology and remove the imported
                            // ontology.
                            // WHO EVER THOUGHT THAT THIS WAS A GOOD IDEA?
                            man.applyChange(new RemoveImport(consumer
                                    .getOntology(), importsDeclaration));
                            for (OWLImportsDeclaration decl : importedOntology
                                    .getImportsDeclarations()) {
                                man.applyChange(new AddImport(consumer
                                        .getOntology(), decl));
                            }
                            for (OWLAnnotation anno : importedOntology
                                    .getAnnotations()) {
                                man.applyChange(new AddOntologyAnnotation(
                                        consumer.getOntology(), anno));
                            }
                            for (OWLAxiom ax : importedOntology.getAxioms()) {
                                consumer.addAxiom(ax);
                            }
                            man.removeOntology(importedOntology);
                        }
                    }
                }
                consumer.importsClosureChanged();
            }
        }
    }

    /** A handler for top level intersection classes. */
    static class TPIntersectionOfHandler extends
            AbstractNamedEquivalentClassAxiomHandler {

        TPIntersectionOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_INTERSECTION_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectIntersectionOf(consumer.translatorAccessor
                    .translateToClassExpressionSet(mainNode));
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            if (consumer.isClassExpression(s)) {
                consumer.addClassExpression(o, false);
            } else if (consumer.isClassExpression(o)) {
                consumer.addClassExpression(s, false);
            } else if (consumer.isDataRange(s)) {
                consumer.addDataRange(o, false);
            } else if (consumer.isDataRange(o)) {
                consumer.addDataRange(s, false);
            }
            return super.canHandleStreaming(s, p, o);
        }
    }

    /** owl:inverseOf is used in both property expressions AND axioms. */
    static class TPInverseOfHandler extends AbstractTriplePredicateHandler {

        private boolean axiomParsingMode = false;

        TPInverseOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_INVERSE_OF.getIRI());
        }

        public boolean isAxiomParsingMode() {
            return axiomParsingMode;
        }

        public void setAxiomParsingMode(boolean axiomParsingMode) {
            this.axiomParsingMode = axiomParsingMode;
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addObjectProperty(s, false);
            consumer.addObjectProperty(o, false);
            return false;
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            return super.canHandle(s, p, o) && consumer.isObjectProperty(s)
                    && consumer.isObjectProperty(o);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // Only do axiom translation
            if (axiomParsingMode && consumer.isObjectProperty(s)
                    && consumer.isObjectProperty(o)) {
                add(df.getOWLInverseObjectPropertiesAxiom(
                        translateObjectProperty(s), translateObjectProperty(o),
                        anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TPOnClassHandler extends AbstractTriplePredicateHandler {

        TPOnClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ON_CLASS.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addClassExpression(o, false);
            return false;
        }
    }

    static class TPOnDataRangeHandler extends AbstractTriplePredicateHandler {

        TPOnDataRangeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ON_DATA_RANGE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addDataRange(o, true);
            return false;
        }
    }

    static class TPOnPropertyHandler extends AbstractTriplePredicateHandler {

        TPOnPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ON_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addOWLRestriction(s, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}
    }

    static class TPOneOfHandler extends
            AbstractNamedEquivalentClassAxiomHandler {

        TPOneOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ONE_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectOneOf(consumer.translatorAccessor
                    .translateToIndividualSet(mainNode));
        }
    }

    static class TPPropertyChainAxiomHandler extends
            AbstractTriplePredicateHandler {

        TPPropertyChainAxiomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_PROPERTY_CHAIN_AXIOM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addObjectProperty(o, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            OWLObjectPropertyExpression superProp = consumer
                    .translateObjectPropertyExpression(s);
            List<OWLObjectPropertyExpression> chain = consumer.translatorAccessor
                    .translateToObjectPropertyList(o);
            consumeTriple(s, p, o);
            Set<OWLAnnotation> annos = anns();
            add(df.getOWLSubPropertyChainOfAxiom(chain, superProp, annos));
        }
    }

    static class TPPropertyDisjointWithHandler extends
            AbstractTriplePredicateHandler {

        TPPropertyDisjointWithHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_PROPERTY_DISJOINT_WITH.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            inferTypes(s, o);
            return super.canHandle(s, p, o)
                    && (consumer.isObjectProperty(s)
                            && consumer.isObjectProperty(o) || consumer
                            .isDataProperty(s) && consumer.isDataProperty(o));
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isDataProperty(s) && consumer.isDataProperty(o)) {
                add(df.getOWLDisjointDataPropertiesAxiom(CollectionFactory
                        .createSet(translateDataProperty(s),
                                translateDataProperty(o)), anns()));
                consumeTriple(s, p, o);
            }
            if (consumer.isObjectProperty(s) && consumer.isObjectProperty(o)) {
                add(df.getOWLDisjointObjectPropertiesAxiom(CollectionFactory
                        .createSet(translateObjectProperty(s),
                                translateObjectProperty(o)), anns()));
                consumeTriple(s, p, o);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return false;
        }
    }

    static class TPPropertyDomainHandler extends AbstractTriplePredicateHandler {

        TPPropertyDomainHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_DOMAIN.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(s) && consumer.isClassExpression(o)) {
                translateObjectPropertyDomain(s, p, o);
            } else if (consumer.isDataPropertyOnly(s)
                    && consumer.isClassExpression(o)) {
                translateDataPropertyDomain(s, p, o);
            } else if (consumer.isAnnotationProperty(s)
                    && consumer.isClassExpression(o)
                    && !consumer.isAnonymousNode(o)) {
                translateAnnotationPropertyDomain(s, p, o);
            } else if (!isStrict()) {
                consumer.addAnnotationProperty(s, false);
                translateAnnotationPropertyDomain(s, p, o);
            }
        }

        private void translateAnnotationPropertyDomain(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(s);
            add(df.getOWLAnnotationPropertyDomainAxiom(prop, o, anns()));
            // TODO: Handle anonymous domain - error?
            consumeTriple(s, p, o);
        }

        private void translateDataPropertyDomain(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o) {
            add(df.getOWLDataPropertyDomainAxiom(translateDataProperty(s),
                    translateClassExpression(o), anns()));
            consumeTriple(s, p, o);
        }

        private void translateObjectPropertyDomain(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o) {
            add(df.getOWLObjectPropertyDomainAxiom(translateObjectProperty(s),
                    translateClassExpression(o), anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPPropertyRangeHandler extends AbstractTriplePredicateHandler {

        TPPropertyRangeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_RANGE.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                if (isObjectPropertyStrict(s) && isClassExpressionStrict(o)) {
                    translateAsObjectPropertyRange(s, p, o);
                } else if (isDataPropertyStrict(s) && isDataRangeStrict(o)) {
                    translateAsDataPropertyRange(s, p, o);
                } else if (consumer.isAnnotationProperty(s)
                        && !consumer.isAnonymousNode(o)) {
                    translateAsAnnotationPropertyRange(s, p, o);
                }
            } else {
                if (isAnnotationPropertyOnly(s) && !isAnonymous(o)) {
                    translateAsAnnotationPropertyRange(s, p, o);
                } else if (isClassExpressionLax(o)) {
                    consumer.addObjectProperty(s, false);
                    translateAsObjectPropertyRange(s, p, o);
                } else if (isDataRangeLax(o)) {
                    consumer.addDataProperty(s, false);
                    translateAsDataPropertyRange(s, p, o);
                } else if (isObjectPropertyLax(s)) {
                    consumer.addObjectProperty(s, false);
                    translateAsObjectPropertyRange(s, p, o);
                } else if (isDataPropertyLax(s)) {
                    consumer.addDataProperty(s, false);
                    translateAsDataPropertyRange(s, p, o);
                } else {
                    consumer.addAnnotationProperty(s, false);
                    translateAsAnnotationPropertyRange(s, p, o);
                }
            }
        }

        private void translateAsAnnotationPropertyRange(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(s);
            add(df.getOWLAnnotationPropertyRangeAxiom(prop, o, anns()));
            consumeTriple(s, p, o);
        }

        private void translateAsDataPropertyRange(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o) {
            OWLDataPropertyExpression property = translateDataProperty(s);
            OWLDataRange dataRange = translateDataRange(o);
            add(df.getOWLDataPropertyRangeAxiom(property, dataRange, anns()));
            consumeTriple(s, p, o);
        }

        private void translateAsObjectPropertyRange(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o) {
            OWLObjectPropertyExpression property = translateObjectProperty(s);
            OWLClassExpression range = translateClassExpression(o);
            add(df.getOWLObjectPropertyRangeAxiom(property, range, anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPRestHandler extends AbstractTriplePredicateHandler {

        TPRestHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_REST.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, @Nonnull IRI o) {
            if (!o.equals(RDF_NIL.getIRI())) {
                consumer.addRest(s, o);
            }
            consumeTriple(s, p, o);
        }
    }

    static class TPSameAsHandler extends AbstractTriplePredicateHandler {

        TPSameAsHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_SAME_AS.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI s, IRI p, @Nonnull IRI o) {
            Set<OWLIndividual> inds = new HashSet<>();
            inds.add(translateIndividual(s));
            inds.add(translateIndividual(o));
            add(df.getOWLSameIndividualAxiom(inds,
                    consumer.getPendingAnnotations()));
            consumeTriple(s, p, o);
        }
    }

    static class TPSomeValuesFromHandler extends AbstractTriplePredicateHandler {

        TPSomeValuesFromHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_SOME_VALUES_FROM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            handleTriple(s, p, o);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addOWLRestriction(s, false);
            if (consumer.isDataRange(o)) {
                IRI property = consumer.getResourceObject(s,
                        OWL_ON_PROPERTY.getIRI(), false);
                if (property != null) {
                    consumer.addDataProperty(property, false);
                }
            }
        }
    }

    /**
     * Handles rdfs:subClassOf triples. If handling is set to strict then the
     * triple is only consumed if the s and o are typed as classes.
     */
    static class TPSubClassOfHandler extends AbstractTriplePredicateHandler {

        TPSubClassOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_SUBCLASS_OF.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            return super.canHandle(s, p, o) && isTyped(s, o);
        }

        private boolean isTyped(IRI s, IRI o) {
            return consumer.isClassExpression(s)
                    && consumer.isClassExpression(o);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addClassExpression(s, false);
            consumer.addClassExpression(o, false);
            return !isStrict() && !isSubjectOrObjectAnonymous(s, o);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                if (isClassExpressionStrict(s) && isClassExpressionStrict(o)) {
                    translate(s, p, o);
                }
            } else {
                if (isClassExpressionLax(s) && isClassExpressionLax(o)) {
                    translate(s, p, o);
                }
            }
        }

        private void translate(@Nonnull IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            OWLClassExpression subClass = translateClassExpression(s);
            OWLClassExpression supClass = translateClassExpression(o);
            Set<OWLAnnotation> pendingAnnotations = consumer
                    .getPendingAnnotations();
            OWLAxiom ax = df.getOWLSubClassOfAxiom(subClass, supClass,
                    pendingAnnotations);
            add(ax);
            consumeTriple(s, p, o);
        }
    }

    static class TPSubPropertyOfHandler extends AbstractTriplePredicateHandler {

        TPSubPropertyOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_SUB_PROPERTY_OF.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(o)) {
                consumer.addObjectProperty(s, false);
            } else if (consumer.isDataProperty(o)) {
                consumer.addDataProperty(o, false);
            } else if (consumer.isAnnotationProperty(o)) {
                consumer.addAnnotationProperty(s, false);
            } else if (consumer.isObjectProperty(s)) {
                consumer.addObjectProperty(o, false);
            } else if (consumer.isDataProperty(s)) {
                consumer.addDataProperty(o, false);
            } else if (consumer.isAnnotationProperty(s)) {
                consumer.addAnnotationProperty(o, false);
            }
            return false;
        }

        @Override
        public void
                handleTriple(@Nonnull IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            // First check for o property chain
            if (!isStrict()
                    && consumer.hasPredicate(s,
                            DeprecatedVocabulary.OWL_PROPERTY_CHAIN)) {
                // Property chain
                IRI chainList = consumer.getResourceObject(s,
                        DeprecatedVocabulary.OWL_PROPERTY_CHAIN, true);
                List<OWLObjectPropertyExpression> properties = consumer.translatorAccessor
                        .translateToObjectPropertyList(chainList);
                add(df.getOWLSubPropertyChainOfAxiom(properties,
                        translateObjectProperty(o), anns()));
                consumeTriple(s, p, o);
            } else if (!isStrict()
                    && consumer.hasPredicate(s, RDF_FIRST.getIRI())) {
                // Legacy o property chain representation
                List<OWLObjectPropertyExpression> properties = consumer.translatorAccessor
                        .translateToObjectPropertyList(s);
                add(df.getOWLSubPropertyChainOfAxiom(properties,
                        translateObjectProperty(o), anns()));
                consumeTriple(s, p, o);
            } else if (consumer.isObjectProperty(s)
                    && consumer.isObjectProperty(o)) {
                translateSubObjectProperty(s, p, o);
            } else if (consumer.isDataProperty(s) && consumer.isDataProperty(o)) {
                translateSubDataProperty(s, p, o);
            } else if (!isStrict()) {
                OWLAnnotationProperty subAnnoProp = df
                        .getOWLAnnotationProperty(s);
                OWLAnnotationProperty superAnnoProp = df
                        .getOWLAnnotationProperty(o);
                add(df.getOWLSubAnnotationPropertyOfAxiom(subAnnoProp,
                        superAnnoProp, anns()));
                consumeTriple(s, p, o);
            }
        }

        private void translateSubObjectProperty(@Nonnull IRI s, @Nonnull IRI p,
                @Nonnull IRI o) {
            // Object - o
            add(df.getOWLSubObjectPropertyOfAxiom(translateObjectProperty(s),
                    translateObjectProperty(o), anns()));
            consumeTriple(s, p, o);
        }

        private void translateSubDataProperty(@Nonnull IRI s, @Nonnull IRI p,
                @Nonnull IRI o) {
            // Data - Data
            add(df.getOWLSubDataPropertyOfAxiom(translateDataProperty(s),
                    translateDataProperty(o), anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPTypeHandler extends AbstractTriplePredicateHandler {

        TPTypeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_TYPE.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, @Nonnull IRI o) {
            // Can handle if o isn;t anonymous and either the o
            // IRI is owl:Thing, or it is not part of the build in vocabulary
            consumer.addClassExpression(o, false);
            if (isAnonymous(o)) {
                return false;
            }
            if (o.isReservedVocabulary()) {
                return o.equals(OWL_THING.getIRI());
            }
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI s, IRI p, @Nonnull IRI o) {
            if (BUILT_IN_VOCABULARY_IRIS.contains(o)
                    && !o.equals(OWL_THING.getIRI())) {
                // Can't have instance of built in vocabulary!
                // Shall we throw an exception here?
                LOGGER.info("Individual of builtin type {}", o);
            }
            add(df.getOWLClassAssertionAxiom(translateClassExpression(o),
                    translateIndividual(s), anns()));
            consumeTriple(s, p, o);
        }
    }

    static class TPUnionOfHandler extends
            AbstractNamedEquivalentClassAxiomHandler {

        TPUnionOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_UNION_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectUnionOf(consumer.translatorAccessor
                    .translateToClassExpressionSet(mainNode));
        }
    }

    static class TPVersionIRIHandler extends AbstractTriplePredicateHandler {

        TPVersionIRIHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_VERSION_IRI.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            OWLOntology ontology = consumer.getOntology();
            // only setup the versionIRI if it is null before this point
            if (!ontology.getOntologyID().getVersionIRI().isPresent()) {
                Optional<IRI> ontologyIRI = ontology.getOntologyID()
                        .getOntologyIRI();
                Optional<IRI> versionIRI = Optional.of(o);
                // If there was no ontologyIRI before this point and the s
                // of this statement was not anonymous,
                // then use the s IRI as the ontology IRI, else we keep
                // the previous definition for the ontology IRI
                if (!ontologyIRI.isPresent() && !isAnonymous(s)) {
                    ontologyIRI = Optional.of(s);
                }
                OWLOntologyID ontologyID = new OWLOntologyID(ontologyIRI,
                        versionIRI);
                consumer.setOntologyID(ontologyID);
            }
            consumeTriple(s, p, o);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            // Always apply at the end
            return false;
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            return p.equals(OWL_VERSION_IRI.getIRI());
        }
    }

    abstract static class AbstractTriplePredicateHandler extends
            AbstractResourceTripleHandler implements TriplePredicateHandler {

        private final IRI predicateIRI;

        AbstractTriplePredicateHandler(@Nonnull OWLRDFConsumer consumer,
                IRI predicateIRI) {
            super(consumer);
            this.predicateIRI = predicateIRI;
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, IRI o) {
            inferTypes(s, o);
            return p.equals(predicateIRI);
        }

        @Override
        public IRI getPredicateIRI() {
            return predicateIRI;
        }
    }

    static class TypeAllDifferentHandler extends AbstractBuiltInTypeHandler {

        TypeAllDifferentHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DIFFERENT.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            return super.canHandle(s, p, o)
                    && consumer.getResourceObject(s, OWL_MEMBERS, false) != null;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI listNode = consumer.getResourceObject(s, OWL_MEMBERS.getIRI(),
                    true);
            if (listNode != null) {
                Set<OWLIndividual> inds = consumer.translatorAccessor
                        .translateToIndividualSet(listNode);
                add(df.getOWLDifferentIndividualsAxiom(inds, anns()));
                consumeTriple(s, p, o);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }
    }

    static class TypeAllDisjointClassesHandler extends
            AbstractBuiltInTypeHandler {

        TypeAllDisjointClassesHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DISJOINT_CLASSES.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            return super.canHandle(s, p, o)
                    && consumer.getResourceObject(s, OWL_MEMBERS, false) != null;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI listNode = consumer.getResourceObject(s, OWL_MEMBERS.getIRI(),
                    true);
            if (listNode != null) {
                Set<OWLClassExpression> desc = consumer.translatorAccessor
                        .translateToClassExpressionSet(listNode);
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(s);
                add(df.getOWLDisjointClassesAxiom(desc, annotations));
                consumeTriple(s, p, o);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }
    }

    static class TypeAllDisjointPropertiesHandler extends
            AbstractBuiltInTypeHandler {

        TypeAllDisjointPropertiesHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DISJOINT_PROPERTIES.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            IRI listNode = consumer.getResourceObject(s, OWL_MEMBERS.getIRI(),
                    true);
            if (consumer.isObjectProperty(consumer.getFirstResource(listNode,
                    false))) {
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(s);
                List<OWLObjectPropertyExpression> props = consumer.translatorAccessor
                        .translateToObjectPropertyList(listNode);
                consumer.addAxiom(df.getOWLDisjointObjectPropertiesAxiom(
                        new HashSet<>(props), annotations));
            } else {
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(s);
                List<OWLDataPropertyExpression> props = consumer.translatorAccessor
                        .translateToDataPropertyList(listNode);
                consumer.addAxiom(df.getOWLDisjointDataPropertiesAxiom(
                        new HashSet<>(props), annotations));
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }
    }

    static class TypeAnnotationHandler extends AbstractBuiltInTypeHandler {

        TypeAnnotationHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATION.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addAnnotationIRI(s);
        }
    }

    static class TypeAnnotationPropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeAnnotationPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATION_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnonymous(s)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLAnnotationProperty property = df.getOWLAnnotationProperty(s);
                add(df.getOWLDeclarationAxiom(property, annos));
                consumeTriple(s, p, o);
            }
            consumer.addAnnotationProperty(s, true);
        }
    }

    static class TypeAsymmetricPropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeAsymmetricPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ASYMMETRIC_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addObjectProperty(s, false);
            return !isAnonymous(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(s)) {
                add(df.getOWLAsymmetricObjectPropertyAxiom(
                        translateObjectProperty(s), anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TypeAxiomHandler extends AbstractBuiltInTypeHandler {

        TypeAxiomHandler(@Nonnull OWLRDFConsumer consumer) {
            this(consumer, OWL_AXIOM.getIRI());
        }

        TypeAxiomHandler(@Nonnull OWLRDFConsumer consumer, IRI typeIRI) {
            super(consumer, typeIRI);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            // We can't handle this is a streaming fashion, because we can't
            // be sure that the s, p, o triples have been
            // parsed.
            consumer.addAxiom(s);
            return false;
        }

        /**
         * Gets the IRI of the p of the triple that specifies the target of a
         * reified axiom
         * 
         * @return The IRI, by default this is owl:annotatedTarget
         */
        protected static OWLRDFVocabulary getTargetTriplePredicate() {
            return OWL_ANNOTATED_TARGET;
        }

        /**
         * Gets the IRI of the p of the triple that specifies that p of a
         * reified axiom
         * 
         * @return The IRI, by default this is owl:annotatedProperty
         */
        protected static OWLRDFVocabulary getPropertyTriplePredicate() {
            return OWL_ANNOTATED_PROPERTY;
        }

        /**
         * Gets the IRI of the p of the triple that specifies the source of a
         * reified axiom
         * 
         * @return The IRI, by default this is owl:annotatedSource
         */
        protected static OWLRDFVocabulary getSourceTriplePredicate() {
            return OWL_ANNOTATED_SOURCE;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI annotatedSource = getObjectOfSourceTriple(s);
            IRI annotatedProperty = getObjectOfPropertyTriple(s);
            IRI annotatedTarget = getObjectOfTargetTriple(s);
            OWLLiteral annotatedTargetLiteral = null;
            if (annotatedTarget == null) {
                annotatedTargetLiteral = getTargetLiteral(s);
            }
            // check that other conditions are not invalid
            if (annotatedSource != null && annotatedProperty != null) {
                consumeTriple(s, p, o);
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(s);
                consumer.setPendingAnnotations(annotations);
                if (annotatedTarget != null) {
                    consumer.handlerAccessor.handle(annotatedSource,
                            annotatedProperty, annotatedTarget);
                } else if (annotatedTargetLiteral != null) {
                    consumer.handlerAccessor.handle(annotatedSource,
                            annotatedProperty, annotatedTargetLiteral);
                }
                if (!annotations.isEmpty()) {
                    OWLAxiom ax = consumer.getLastAddedAxiom();
                    consumer.removeAxiom(ax.getAxiomWithoutAnnotations());
                }
            }
        }

        @SuppressWarnings("unused")
        protected OWLAxiom handleAxiomTriples(IRI subjectTriple,
                IRI predicateTriple, IRI objectTriple,
                Set<OWLAnnotation> annotations) {
            // Reconstitute the original triple from the reification triples
            return consumer.getLastAddedAxiom();
        }

        protected OWLAxiom handleAxiomTriples(@Nonnull IRI subjectTripleObject,
                @Nonnull IRI predicateTripleObject, @Nonnull OWLLiteral con,
                @SuppressWarnings("unused") Set<OWLAnnotation> annotations) {
            consumer.handlerAccessor.handle(subjectTripleObject,
                    predicateTripleObject, con);
            return consumer.getLastAddedAxiom();
        }

        @Nonnull
        private OWLLiteral getTargetLiteral(IRI s) {
            OWLLiteral con = consumer.getLiteralObject(s,
                    getTargetTriplePredicate(), true);
            if (con == null) {
                con = consumer.getLiteralObject(s,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            return verifyNotNull(con);
        }

        /**
         * Gets the object of the target triple that has the specified main node
         * 
         * @param mainNode
         *        The main node
         * @return The object of the triple that has the specified mainNode as
         *         its s and the IRI returned by the
         *         {@code TypeAxiomHandler#getSourceTriplePredicate()} method.
         *         For backwards compatibility, a search will also be performed
         *         for triples whos s is the specified mainNode and p rdf:object
         */
        @Nullable
        private IRI getObjectOfTargetTriple(IRI mainNode) {
            IRI objectTripleObject = consumer.getResourceObject(mainNode,
                    getTargetTriplePredicate(), true);
            if (objectTripleObject == null) {
                objectTripleObject = consumer.getResourceObject(mainNode,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            if (objectTripleObject == null) {
                objectTripleObject = consumer.getResourceObject(mainNode,
                        DeprecatedVocabulary.OWL_PROPERTY_CHAIN, true);
            }
            return objectTripleObject;
        }

        @Nullable
        private IRI getObjectOfPropertyTriple(IRI s) {
            IRI predicateTripleObject = consumer.getResourceObject(s,
                    getPropertyTriplePredicate(), true);
            if (predicateTripleObject == null) {
                predicateTripleObject = consumer.getResourceObject(s,
                        DeprecatedVocabulary.RDF_PREDICATE, true);
            }
            return predicateTripleObject;
        }

        /**
         * Gets the source IRI for an annotated or reified axiom
         * 
         * @param mainNode
         *        The main node of the triple
         * @return The source object
         * @throws OWLRDFXMLParserMalformedNodeException
         *         malformed node
         */
        @Nullable
        private IRI getObjectOfSourceTriple(IRI mainNode) {
            IRI subjectTripleObject = consumer.getResourceObject(mainNode,
                    getSourceTriplePredicate(), true);
            if (subjectTripleObject == null) {
                subjectTripleObject = consumer.getResourceObject(mainNode,
                        DeprecatedVocabulary.RDF_SUBJECT, true);
            }
            return subjectTripleObject;
        }
    }

    static class TypeClassHandler extends AbstractBuiltInTypeHandler {

        TypeClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnonymous(s)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLClass owlClass = df.getOWLClass(s);
                add(df.getOWLDeclarationAxiom(owlClass, annos));
            }
            consumer.addClassExpression(s, true);
        }
    }

    static class TypeDataPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeDataPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DATA_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnonymous(s)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLDataProperty owlDataProperty = df.getOWLDataProperty(s);
                add(df.getOWLDeclarationAxiom(owlDataProperty, annos));
            }
            consumer.addDataProperty(s, true);
        }
    }

    static class TypeDataRangeHandler extends AbstractBuiltInTypeHandler {

        TypeDataRangeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DATA_RANGE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnonymous(s)) {
                consumeTriple(s, p, o);
            }
        }
    }

    static class TypeDatatypeHandler extends AbstractBuiltInTypeHandler {

        TypeDatatypeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_DATATYPE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!consumer.isAnonymousNode(s)) {
                OWLDatatype dt = df.getOWLDatatype(s);
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                add(df.getOWLDeclarationAxiom(dt, annos));
            }
            consumer.addDataRange(s, true);
        }
    }

    static class TypeDeprecatedClassHandler extends AbstractBuiltInTypeHandler {

        TypeDeprecatedClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DEPRECATED_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addClassExpression(s, false);
            consumeTriple(s, p, o);
            add(df.getDeprecatedOWLAnnotationAssertionAxiom(s));
        }
    }

    static class TypeDeprecatedPropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeDeprecatedPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DEPRECATED_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            add(df.getDeprecatedOWLAnnotationAssertionAxiom(s));
        }
    }

    static class TypeFunctionalPropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeFunctionalPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_FUNCTIONAL_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(s)) {
                add(df.getOWLFunctionalObjectPropertyAxiom(
                        translateObjectProperty(s), anns()));
                consumeTriple(s, p, o);
            }
            if (consumer.isDataProperty(s)) {
                add(df.getOWLFunctionalDataPropertyAxiom(
                        translateDataProperty(s), anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TypeInverseFunctionalPropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeInverseFunctionalPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_INVERSE_FUNCTIONAL_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.handlerAccessor.handle(s, p, OWL_OBJECT_PROPERTY.getIRI());
            return !isAnonymous(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(s)) {
                OWLObjectPropertyExpression property = translateObjectProperty(s);
                add(df.getOWLInverseFunctionalObjectPropertyAxiom(property,
                        anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TypeIrreflexivePropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeIrreflexivePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_IRREFLEXIVE_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addObjectProperty(s, false);
            return !isAnonymous(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(s)) {
                add(df.getOWLIrreflexiveObjectPropertyAxiom(
                        translateObjectProperty(s), anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TypeListHandler extends AbstractBuiltInTypeHandler {

        TypeListHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_LIST.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
        }
    }

    static class TypeNamedIndividualHandler extends AbstractBuiltInTypeHandler {

        TypeNamedIndividualHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_NAMED_INDIVIDUAL.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnonymous(s)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLNamedIndividual individual = df.getOWLNamedIndividual(s);
                add(df.getOWLDeclarationAxiom(individual, annos));
            }
            consumer.addOWLNamedIndividual(s, true);
        }
    }

    static class TypeNegativeDataPropertyAssertionHandler extends
            AbstractBuiltInTypeHandler {

        TypeNegativeDataPropertyAssertionHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer,
                    DeprecatedVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void
                handleTriple(@Nonnull IRI s, @Nonnull IRI p, @Nonnull IRI o) {
            IRI source = source(s);
            IRI property = property(s);
            OWLLiteral target = target(s);
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLDataPropertyExpression prop = consumer
                    .translateDataPropertyExpression(property);
            consumeTriple(s, p, o);
            consumer.translateAnnotations(s);
            Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
            add(df.getOWLNegativeDataPropertyAssertionAxiom(prop, sourceInd,
                    target, annos));
        }

        @Nonnull
        OWLLiteral target(IRI s) {
            OWLLiteral target = consumer.getLiteralObject(s,
                    OWL_TARGET_VALUE.getIRI(), true);
            if (target == null) {
                target = consumer.getLiteralObject(s,
                        DeprecatedVocabulary.OWL_OBJECT, true);
            }
            if (target == null) {
                target = consumer.getLiteralObject(s,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            return verifyNotNull(target);
        }

        @Nonnull
        IRI property(IRI s) {
            IRI property = consumer.getResourceObject(s,
                    OWL_ASSERTION_PROPERTY.getIRI(), true);
            if (property == null) {
                property = consumer.getResourceObject(s,
                        DeprecatedVocabulary.OWL_PREDICATE, true);
            }
            if (property == null) {
                property = consumer.getResourceObject(s,
                        DeprecatedVocabulary.RDF_PREDICATE, true);
            }
            return verifyNotNull(property);
        }

        @Nonnull
        IRI source(IRI s) {
            IRI source = consumer.getResourceObject(s,
                    OWL_SOURCE_INDIVIDUAL.getIRI(), true);
            if (source == null) {
                source = consumer.getResourceObject(s,
                        DeprecatedVocabulary.OWL_SUBJECT, true);
            }
            if (source == null) {
                source = consumer.getResourceObject(s,
                        DeprecatedVocabulary.RDF_SUBJECT, true);
            }
            return verifyNotNull(source);
        }
    }

    static class TypeNegativePropertyAssertionHandler extends
            AbstractBuiltInTypeHandler {

        TypeNegativePropertyAssertionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI source = source(s);
            IRI property = property(s);
            Object target = target(s);
            Set<OWLAnnotation> annos = consumer.translateAnnotations(s);
            if (target instanceof OWLLiteral
                    && (!isStrict() || consumer.isDataProperty(property))) {
                translateNegativeDataPropertyAssertion(s, p, o, source,
                        property, (OWLLiteral) target, annos);
            } else if (target instanceof IRI
                    && (!isStrict() || consumer.isObjectProperty(property))) {
                translateNegativeObjectPropertyAssertion(s, p, o, source,
                        property, (IRI) target, annos);
            }
            // TODO LOG ERROR
        }

        @Nonnull
        Object target(IRI s) {
            Object target = consumer.getResourceObject(s,
                    OWL_TARGET_INDIVIDUAL.getIRI(), true);
            if (target == null) {
                target = consumer.getLiteralObject(s,
                        OWL_TARGET_VALUE.getIRI(), true);
            }
            if (target == null) {
                target = consumer.getResourceObject(s,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            if (target == null) {
                target = consumer.getLiteralObject(s,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            return verifyNotNull(target);
        }

        @Nonnull
        IRI property(IRI s) {
            IRI property = consumer.getResourceObject(s,
                    OWL_ASSERTION_PROPERTY.getIRI(), true);
            if (property == null) {
                property = consumer.getResourceObject(s,
                        DeprecatedVocabulary.RDF_PREDICATE, true);
            }
            return verifyNotNull(property);
        }

        @Nonnull
        IRI source(IRI s) {
            IRI source = consumer.getResourceObject(s,
                    OWL_SOURCE_INDIVIDUAL.getIRI(), true);
            if (source == null) {
                source = consumer.getResourceObject(s,
                        DeprecatedVocabulary.RDF_SUBJECT, true);
            }
            return verifyNotNull(source);
        }

        private void translateNegativeObjectPropertyAssertion(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o, @Nonnull IRI source,
                @Nonnull IRI property, @Nonnull IRI target,
                @Nonnull Set<OWLAnnotation> annos) {
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLObjectPropertyExpression prop = consumer
                    .translateObjectPropertyExpression(property);
            OWLIndividual targetInd = consumer.getOWLIndividual(target);
            consumeTriple(s, p, o);
            add(df.getOWLNegativeObjectPropertyAssertionAxiom(prop, sourceInd,
                    targetInd, annos));
        }

        private void translateNegativeDataPropertyAssertion(@Nonnull IRI s,
                @Nonnull IRI p, @Nonnull IRI o, @Nonnull IRI source,
                @Nonnull IRI property, @Nonnull OWLLiteral target,
                @Nonnull Set<OWLAnnotation> annos) {
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLDataPropertyExpression prop = consumer
                    .translateDataPropertyExpression(property);
            consumeTriple(s, p, o);
            add(df.getOWLNegativeDataPropertyAssertionAxiom(prop, sourceInd,
                    target, annos));
        }
    }

    static class TypeObjectPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeObjectPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_OBJECT_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnonymous(s)) {
                OWLObjectProperty owlObjectProperty = df
                        .getOWLObjectProperty(s);
                Set<OWLAnnotation> annos = anns();
                add(df.getOWLDeclarationAxiom(owlObjectProperty, annos));
            }
            consumer.addObjectProperty(s, true);
        }
    }

    static class TypeOntologyHandler extends AbstractBuiltInTypeHandler {

        TypeOntologyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ONTOLOGY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            if (!isAnonymous(s) && consumer.getOntologies().isEmpty()) {
                // Set IRI if it is not null before this point, and make sure to
                // preserve the version IRI if it also existed before this point
                OWLOntology ont = consumer.getOntology();
                if (!ont.getOntologyID().getOntologyIRI().isPresent()) {
                    OWLOntologyID id = new OWLOntologyID(Optional.of(s), ont
                            .getOntologyID().getVersionIRI());
                    ont.applyChange(new SetOntologyID(ont, id));
                }
            }
            consumer.addOntology(s);
        }
    }

    static class TypeOntologyPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeOntologyPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, DeprecatedVocabulary.OWL_ONTOLOGY_PROPERTY);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            // Add a type triple for an annotation property (Table 6 in Mapping
            // to RDF Graph Spec)
            consumer.handlerAccessor.handle(s, p,
                    OWL_ANNOTATION_PROPERTY.getIRI());
        }
    }

    static class TypePropertyHandler extends AbstractBuiltInTypeHandler {

        TypePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // We need to consume this triple
            consumeTriple(s, p, o);
            LOGGER.info("Usage of rdf vocabulary: {} -> {} -> {}", s, p, o);
        }
    }

    static class TypeRDFSClassHandler extends AbstractBuiltInTypeHandler {

        TypeRDFSClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // TODO: Change to rdfs:Class? (See table 5 in the spec)
            consumer.addClassExpression(s, false);
            consumeTriple(s, p, o);
            if (!isStrict()) {
                consumer.handlerAccessor.handle(s, p, OWL_CLASS.getIRI());
            }
        }
    }

    static class TypeReflexivePropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeReflexivePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_REFLEXIVE_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.addObjectProperty(s, false);
            return !isAnonymous(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(s)) {
                add(df.getOWLReflexiveObjectPropertyAxiom(
                        translateObjectProperty(s), anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TypeRestrictionHandler extends AbstractBuiltInTypeHandler {

        TypeRestrictionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_RESTRICTION.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            consumer.addOWLRestriction(s, true);
            consumer.addClassExpression(s, false);
        }
    }

    static class TypeSWRLAtomListHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLAtomListHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.ATOM_LIST.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
        }
    }

    static class TypeSWRLBuiltInAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLBuiltInAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.BUILT_IN_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLBuiltInAtom(s);
            consumeTriple(s, p, o);
        }
    }

    static class TypeSWRLBuiltInHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLBuiltInHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.BUILT_IN_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // Just consume - I don't care about this
            consumeTriple(s, p, o);
        }
    }

    static class TypeSWRLClassAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLClassAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.CLASS_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLClassAtom(s);
            consumeTriple(s, p, o);
        }
    }

    static class TypeSWRLDataRangeAtomHandler extends
            AbstractBuiltInTypeHandler {

        TypeSWRLDataRangeAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DATA_RANGE_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLDataRangeAtom(s);
            consumeTriple(s, p, o);
        }
    }

    static class TypeSWRLDataValuedPropertyAtomHandler extends
            AbstractBuiltInTypeHandler {

        TypeSWRLDataValuedPropertyAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DATAVALUED_PROPERTY_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            consumer.addSWRLDataPropertyAtom(s);
        }
    }

    static class TypeSWRLDifferentIndividualsAtomHandler extends
            AbstractBuiltInTypeHandler {

        TypeSWRLDifferentIndividualsAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DIFFERENT_INDIVIDUALS_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLDifferentFromAtom(s);
            consumeTriple(s, p, o);
        }
    }

    static class TypeSWRLImpHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLImpHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.IMP.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI remapIRI = consumer.remapIRI(s);
            consumeTriple(remapIRI, p, o);
            consumer.addSWRLRule(remapIRI);
        }
    }

    static class TypeSWRLIndividualPropertyAtomHandler extends
            AbstractBuiltInTypeHandler {

        TypeSWRLIndividualPropertyAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.INDIVIDUAL_PROPERTY_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            consumer.addSWRLIndividualPropertyAtom(s);
        }
    }

    static class TypeSWRLSameIndividualAtomHandler extends
            AbstractBuiltInTypeHandler {

        TypeSWRLSameIndividualAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.SAME_INDIVIDUAL_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLSameAsAtom(s);
            consumeTriple(s, p, o);
        }
    }

    static class TypeSWRLVariableHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLVariableHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.VARIABLE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLVariable(s);
            consumeTriple(s, p, o);
        }
    }

    static class TypeSelfRestrictionHandler extends AbstractBuiltInTypeHandler {

        TypeSelfRestrictionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, DeprecatedVocabulary.OWL_SELF_RESTRICTION);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumeTriple(s, p, o);
            consumer.addOWLRestriction(s, false);
            // Patch to new OWL syntax
            consumer.addTriple(s, OWL_HAS_SELF.getIRI(), df.getOWLLiteral(true));
        }
    }

    static class TypeSymmetricPropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeSymmetricPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_SYMMETRIC_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            if (!isAnonymous(s)) {
                consumer.handlerAccessor.handle(s, p,
                        OWL_OBJECT_PROPERTY.getIRI());
            }
            consumer.addObjectProperty(s, false);
            return !isAnonymous(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (consumer.isObjectProperty(s)) {
                add(df.getOWLSymmetricObjectPropertyAxiom(
                        translateObjectProperty(s), anns()));
                consumeTriple(s, p, o);
            }
        }
    }

    static class TypeTransitivePropertyHandler extends
            AbstractBuiltInTypeHandler {

        TypeTransitivePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_TRANSITIVE_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.handlerAccessor.handle(s, p, OWL_OBJECT_PROPERTY.getIRI());
            return !isAnonymous(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            add(df.getOWLTransitiveObjectPropertyAxiom(
                    translateObjectProperty(s), anns()));
            consumeTriple(s, p, o);
        }
    }
}
