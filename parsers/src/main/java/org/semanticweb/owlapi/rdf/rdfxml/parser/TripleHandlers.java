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

import static org.semanticweb.owlapi.model.parameters.MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.RDFOntologyFormat;
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
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
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

    static final Logger logger = LoggerFactory.getLogger(TripleHandlers.class);

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
        private final Map<IRI, TriplePredicateHandler> predicates;
        /**
         * Handlers for general literal triples (i.e. triples which have
         * predicates that are not part of the built in OWL/RDFS/RDF vocabulary.
         * Such triples either constitute annotationIRIs of relationships
         * between an individual and a data literal (typed or untyped)
         */
        protected List<LiteralTripleHandler> literals;
        /**
         * Handlers for general resource triples (i.e. triples which have
         * predicates that are not part of the built in OWL/RDFS/RDF vocabulary.
         * Such triples either constitute annotationIRIs or relationships
         * between an individual and another individual.
         */
        protected List<ResourceTripleHandler> resources;
        /** The inverse of handler. */
        @Nonnull
        protected TPInverseOfHandler inverseOf;
        /** The non built in type handler. */
        @Nonnull
        private TPTypeHandler nonBuiltInTypes;
        @Nonnull
        protected OWLRDFConsumer consumer;

        @SuppressWarnings("null")
        public HandlerAccessor(@Nonnull OWLRDFConsumer r) {
            consumer = r;
            builtInTypes = getBasicTypeHandlers(r, r.getConfiguration());
            axiomTypes = getAxiomTypeHandlers(r);
            predicates = getPredicateHandlers(r);
            literals = getLiteralTripleHandlers(r);
            // General resource/object triples - i.e. triples which have a
            // predicate
            // that is not a built in IRI. Annotation properties get precedence
            // over object properties, so that if we have the statement
            // a:A a:foo a:B and a:foo
            // is typed as both an annotation and data property then the
            // statement will be translated as an annotation on a:A
            resources = getResourceTripleHandlers(r);
        }

        void apply(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull OWLLiteral object) {
            if (consumer.isGeneralPredicate(predicate)) {
                for (LiteralTripleHandler lhandler : literals) {
                    if (lhandler.canHandle(subject, predicate, object)) {
                        lhandler.handleTriple(subject, predicate, object);
                        return;
                    }
                }
            }
        }

        void apply(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            if (consumer.isGeneralPredicate(predicate)) {
                for (ResourceTripleHandler handler : resources) {
                    if (handler.canHandle(subject, predicate, object)) {
                        handler.handleTriple(subject, predicate, object);
                        return;
                    }
                }
            }
        }

        void applyAnnotations(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            BuiltInTypeHandler builtInTypeHandler = axiomTypes.get(object);
            if (builtInTypeHandler != null
                    && builtInTypeHandler.canHandle(subject, predicate, object)) {
                builtInTypeHandler.handleTriple(subject, predicate, object);
            }
        }

        public void consumeNonReservedPredicateTriples() {
            consumer.iterateResourceTriples(new ResourceTripleIterator() {

                @Override
                public void handleResourceTriple(IRI subject,
                        @Nonnull IRI predicate, IRI object) {
                    apply(subject, predicate, object);
                }
            });
            consumer.iterateLiteralTriples(new LiteralTripleIterator() {

                @Override
                public void handleLiteralTriple(IRI subject,
                        @Nonnull IRI predicate, OWLLiteral object) {
                    apply(subject, predicate, object);
                }
            });
        }

        public void consumeAnnotatedAxioms() {
            consumer.iterateResourceTriples(new ResourceTripleIterator() {

                @Override
                public void handleResourceTriple(IRI subject, IRI predicate,
                        IRI object) {
                    applyAnnotations(subject, predicate, object);
                }
            });
        }

        /**
         * Called when a resource triple has been parsed.
         * 
         * @param subject
         *        The subject of the triple that has been parsed
         * @param predicate
         *        The predicate of the triple that has been parsed
         * @param object
         *        The object of the triple that has been parsed
         */
        public void handleStreaming(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            boolean consumed = false;
            if (predicate.equals(RDF_TYPE.getIRI())) {
                BuiltInTypeHandler handler = builtInTypes.get(object);
                if (handler != null) {
                    if (handler.canHandleStreaming(subject, predicate, object)) {
                        handler.handleTriple(subject, predicate, object);
                        consumed = true;
                    }
                } else if (axiomTypes.get(object) == null) {
                    // Not a built in type
                    consumer.addOWLNamedIndividual(subject, false);
                    if (nonBuiltInTypes.canHandleStreaming(subject, predicate,
                            object)) {
                        nonBuiltInTypes
                                .handleTriple(subject, predicate, object);
                        consumed = true;
                    }
                } else {
                    consumer.addAxiom(subject);
                }
            } else {
                ResourceTripleHandler handler = predicates.get(predicate);
                if (handler != null) {
                    if (handler.canHandleStreaming(subject, predicate, object)) {
                        handler.handleTriple(subject, predicate, object);
                        consumed = true;
                    }
                } else {
                    for (ResourceTripleHandler resHandler : resources) {
                        if (resHandler.canHandleStreaming(subject, predicate,
                                object)) {
                            resHandler.handleTriple(subject, predicate, object);
                            consumed = true;
                            break;
                        }
                    }
                }
            }
            if (!consumed) {
                // Not consumed, so add the triple
                consumer.addTriple(subject, predicate, object);
            }
        }

        public void handleStreaming(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull String literal, IRI datatype,
                String lang) {
            // Convert all literals to OWLConstants
            OWLLiteral con = consumer.getOWLLiteral(literal, datatype, lang);
            handleStreaming(subject, predicate, con);
        }

        private void handleStreaming(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull OWLLiteral con) {
            for (LiteralTripleHandler handler : literals) {
                if (handler.canHandleStreaming(subject, predicate, con)) {
                    handler.handleTriple(subject, predicate, con);
                    return;
                }
            }
            consumer.addTriple(subject, predicate, con);
        }

        /**
         * Handles triples in a non-streaming mode. Type triples whose type is
         * an axiom type, are NOT handled.
         * 
         * @param subject
         *        The subject of the triple
         * @param predicate
         *        The predicate of the triple
         * @param object
         *        The object of the triple
         */
        protected void handle(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            if (predicate.equals(OWLRDFVocabulary.RDF_TYPE.getIRI())) {
                BuiltInTypeHandler typeHandler = builtInTypes.get(object);
                if (typeHandler != null) {
                    typeHandler.handleTriple(subject, predicate, object);
                } else if (axiomTypes.get(object) == null) {
                    // C(a)
                    OWLIndividual ind = consumer.translateIndividual(subject);
                    OWLClassExpression ce = consumer.translatorAccessor
                            .translateClassExpression(object);
                    consumer.addAxiom(consumer.getDataFactory()
                            .getOWLClassAssertionAxiom(ce, ind,
                                    consumer.getPendingAnnotations()));
                }
            } else {
                TriplePredicateHandler handler = predicates.get(predicate);
                if (handler != null
                        && handler.canHandle(subject, predicate, object)) {
                    handler.handleTriple(subject, predicate, object);
                } else {
                    for (ResourceTripleHandler resHandler : resources) {
                        if (resHandler.canHandle(subject, predicate, object)) {
                            resHandler.handleTriple(subject, predicate, object);
                            break;
                        }
                    }
                }
            }
        }

        /**
         * Handle.
         * 
         * @param subject
         *        the subject
         * @param predicate
         *        the predicate
         * @param object
         *        the object
         */
        protected void handle(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull OWLLiteral object) {
            for (LiteralTripleHandler handler : literals) {
                if (handler.canHandle(subject, predicate, object)) {
                    handler.handleTriple(subject, predicate, object);
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
            consumer.iterateResourceTriples(new ResourceTripleIterator() {

                @Override
                public void handleResourceTriple(@Nonnull IRI subject,
                        @Nonnull IRI predicate, @Nonnull IRI object) {
                    TriplePredicateHandler propertyRangeHandler = getPredicateHandlers(
                            consumer).get(RDFS_RANGE.getIRI());
                    if (propertyRangeHandler.canHandle(subject, predicate,
                            object)) {
                        propertyRangeHandler.handleTriple(subject, predicate,
                                object);
                    }
                }
            });
            // Now handle non-reserved predicate triples
            consumeNonReservedPredicateTriples();
            // Now axiom annotations
            consumeAnnotatedAxioms();
            consumer.iterateResourceTriples(new ResourceTripleIterator() {

                @Override
                public void handleResourceTriple(@Nonnull IRI subject,
                        @Nonnull IRI predicate, IRI object) {
                    handle(subject, predicate, object);
                }
            });
            consumer.iterateLiteralTriples(new LiteralTripleIterator() {

                @Override
                public void handleLiteralTriple(IRI subject, IRI predicate,
                        OWLLiteral object) {
                    handle(subject, predicate, object);
                }
            });
            // Inverse property axioms
            inverseOf.setAxiomParsingMode(true);
            consumer.iterateResourceTriples(new ResourceTripleIterator() {

                @Override
                public void handleResourceTriple(IRI subject,
                        @Nonnull IRI predicate, IRI object) {
                    if (inverseOf.canHandle(subject, predicate, object)) {
                        inverseOf.handleTriple(subject, predicate, object);
                    }
                }
            });
            return getRemainingTriples();
        }

        @Nonnull
        private Set<RDFTriple> getRemainingTriples() {
            final Set<RDFTriple> remainingTriples = new HashSet<RDFTriple>();
            consumer.iterateResourceTriples(new ResourceTripleIterator() {

                @Override
                public void handleResourceTriple(IRI subject, IRI predicate,
                        IRI object) {
                    remainingTriples.add(new RDFTriple(subject, consumer
                            .isAnonymousNode(subject), predicate, object,
                            consumer.isAnonymousNode(object)));
                }
            });
            consumer.iterateLiteralTriples(new LiteralTripleIterator() {

                @Override
                public void handleLiteralTriple(@Nonnull IRI subject,
                        @Nonnull IRI predicate, @Nonnull OWLLiteral object) {
                    remainingTriples.add(new RDFTriple(subject, consumer
                            .isAnonymousNode(subject), predicate, object));
                }
            });
            return remainingTriples;
        }

        @SuppressWarnings("null")
        @Nonnull
        private static List<ResourceTripleHandler> getResourceTripleHandlers(
                @Nonnull OWLRDFConsumer r) {
            return Arrays
                    .asList((ResourceTripleHandler) new GTPObjectPropertyAssertionHandler(
                            r), new GTPAnnotationResourceTripleHandler(r));
        }

        @Nonnull
        protected Map<IRI, TriplePredicateHandler> getPredicateHandlers(
                @Nonnull OWLRDFConsumer r) {
            Map<IRI, TriplePredicateHandler> predicateHandlers = new ConcurrentHashMap<IRI, TriplePredicateHandler>();
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
            nonBuiltInTypes = new TPTypeHandler(r);
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
            inverseOf = new TPInverseOfHandler(r);
            add(predicateHandlers, inverseOf);
            add(predicateHandlers, new TPOnPropertyHandler(r));
            add(predicateHandlers, new TPOnClassHandler(r));
            add(predicateHandlers, new TPOnDataRangeHandler(r));
            add(predicateHandlers, new TPComplementOfHandler(r));
            add(predicateHandlers, new TPDatatypeComplementOfHandler(r));
            return predicateHandlers;
        }

        @Nonnull
        public Map<IRI, BuiltInTypeHandler> getAxiomTypeHandlers(
                @Nonnull OWLRDFConsumer r) {
            Map<IRI, BuiltInTypeHandler> map = new ConcurrentHashMap<IRI, BuiltInTypeHandler>();
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
        @SuppressWarnings("null")
        @Nonnull
        public List<LiteralTripleHandler> getLiteralTripleHandlers(
                @Nonnull OWLRDFConsumer r) {
            return Arrays.asList(
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
        public Map<IRI, BuiltInTypeHandler> getBasicTypeHandlers(
                @Nonnull OWLRDFConsumer r,
                @Nonnull OWLOntologyLoaderConfiguration config) {
            Map<IRI, BuiltInTypeHandler> map = new ConcurrentHashMap<IRI, BuiltInTypeHandler>();
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
    static abstract class AbstractNamedEquivalentClassAxiomHandler extends
            AbstractTriplePredicateHandler {

        public AbstractNamedEquivalentClassAxiomHandler(
                @Nonnull OWLRDFConsumer consumer, IRI predicateIRI) {
            super(consumer, predicateIRI);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            return super.canHandle(subject, predicate, object)
                    && !isAnonymous(subject);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
            operands.add(translateClassExpression(subject));
            operands.add(translateEquivalentClass(object));
            addAxiom(df.getOWLEquivalentClassesAxiom(operands));
        }

        protected abstract OWLClassExpression translateEquivalentClass(
                @Nonnull IRI mainNode);
    }

    static abstract class AbstractResourceTripleHandler extends
            AbstractTripleHandler implements ResourceTripleHandler {

        protected AbstractResourceTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        protected boolean isSubjectOrObjectAnonymous(@Nonnull IRI subject,
                @Nonnull IRI object) {
            return isAnonymous(subject) || isAnonymous(object);
        }

        protected boolean
                isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(
                        IRI subject, IRI object) {
            return isSubjectAndObjectClassExpression(subject, object)
                    || isSubjectAndObjectDataRange(subject, object);
        }

        protected boolean isSubjectAndObjectDataRange(IRI subject, IRI object) {
            return consumer.isDataRange(subject)
                    && consumer.isDataRange(object);
        }

        protected boolean isSubjectAndObjectClassExpression(IRI subject,
                IRI object) {
            return consumer.isClassExpression(subject)
                    && consumer.isClassExpression(object);
        }

        /**
         * @param subject
         *        subject
         * @param object
         *        object
         */
        protected void inferTypes(IRI subject, IRI object) {
            if (consumer.isClassExpression(object)) {
                consumer.addClassExpression(subject, false);
            } else if (consumer.isDataRange(object)) {
                consumer.addDataRange(subject, false);
            } else if (consumer.isClassExpression(subject)) {
                consumer.addClassExpression(object, false);
            } else if (consumer.isDataRange(subject)) {
                consumer.addDataRange(object, false);
            }
        }
    }

    static class AbstractTripleHandler {

        @Nonnull
        protected final OWLRDFConsumer consumer;
        @Nonnull
        private ClassExpressionMatcher classExpressionMatcher = new ClassExpressionMatcher();
        @Nonnull
        private DataRangeMatcher dataRangeMatcher = new DataRangeMatcher();
        @Nonnull
        private IndividualMatcher individualMatcher = new IndividualMatcher();
        protected final OWLDataFactory df;

        protected AbstractTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            this.consumer = consumer;
            df = consumer.getDataFactory();
        }

        @Nonnull
        protected Set<OWLAnnotation> getPendingAnnotations() {
            return consumer.getPendingAnnotations();
        }

        protected void consumeTriple(IRI subject, IRI predicate, IRI object) {
            consumer.consumeTriple(subject, predicate, object);
        }

        protected void consumeTriple(IRI subject, IRI predicate,
                OWLLiteral object) {
            consumer.consumeTriple(subject, predicate, object);
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

        protected void addAxiom(@Nonnull OWLAxiom axiom) {
            consumer.addAxiom(axiom);
        }

        @Nonnull
        protected OWLClassExpression translateClassExpression(@Nonnull IRI IRI) {
            return consumer.translatorAccessor.translateClassExpression(IRI);
        }

        @Nonnull
        protected OWLObjectPropertyExpression translateObjectProperty(
                @Nonnull IRI IRI) {
            return consumer.translateObjectPropertyExpression(IRI);
        }

        @Nonnull
        protected OWLDataPropertyExpression translateDataProperty(
                @Nonnull IRI IRI) {
            return consumer.translateDataPropertyExpression(IRI);
        }

        @Nonnull
        protected OWLDataRange translateDataRange(@Nonnull IRI IRI) {
            return consumer.translateDataRange(IRI);
        }

        @Nonnull
        protected OWLIndividual translateIndividual(@Nonnull IRI IRI) {
            return consumer.translateIndividual(IRI);
        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        protected boolean isAnonymous(@Nonnull IRI node) {
            return consumer.isAnonymousNode(node);
        }

        protected boolean isResourcePresent(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            return consumer.getResourceObject(mainNode, predicate, false) != null;
        }

        protected boolean isLiteralPresent(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            return consumer.getLiteralObject(mainNode, predicate, false) != null;
        }

        protected boolean isRestrictionStrict(@Nonnull IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isRestrictionLax(@Nonnull IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isNonNegativeIntegerStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate,
                    false);
            if (literal == null) {
                return false;
            }
            OWLDatatype datatype = literal.getDatatype();
            OWL2Datatype nni = OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
            return datatype.getIRI().equals(nni.getIRI())
                    && nni.isInLexicalSpace(literal.getLiteral());
        }

        @SuppressWarnings("null")
        protected boolean isNonNegativeIntegerLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate,
                    false);
            if (literal == null) {
                return false;
            }
            return OWL2Datatype.XSD_INTEGER.isInLexicalSpace(literal
                    .getLiteral().trim());
        }

        protected int translateInteger(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate,
                    true);
            if (literal == null) {
                return 0;
            }
            try {
                return Integer.parseInt(literal.getLiteral().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        protected boolean isClassExpressionStrict(@Nonnull IRI node) {
            return consumer.isClassExpression(node)
                    && !consumer.isDataRange(node);
        }

        protected boolean isClassExpressionStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isClassExpressionStrict(object);
        }

        protected boolean isClassExpressionLax(@Nonnull IRI mainNode) {
            return consumer.isClassExpression(mainNode)
                    || consumer.isParsedAllTriples()
                    && !consumer.isDataRange(mainNode);
        }

        protected boolean isClassExpressionLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isClassExpressionLax(object);
        }

        protected boolean isObjectPropertyStrict(@Nonnull IRI node) {
            return consumer.isObjectPropertyOnly(node);
        }

        protected boolean isObjectPropertyStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isObjectPropertyStrict(object);
        }

        protected boolean isObjectPropertyLax(@Nonnull IRI node) {
            return consumer.isObjectProperty(node);
        }

        protected boolean isObjectPropertyLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isObjectPropertyLax(object);
        }

        protected boolean isDataPropertyStrict(@Nonnull IRI node) {
            return consumer.isDataPropertyOnly(node);
        }

        protected boolean isDataPropertyStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataPropertyStrict(object);
        }

        protected boolean isDataPropertyLax(@Nonnull IRI node) {
            return consumer.isDataProperty(node);
        }

        protected boolean isDataPropertyLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataPropertyLax(object);
        }

        protected boolean isDataRangeStrict(@Nonnull IRI node) {
            return consumer.isDataRange(node)
                    && !consumer.isClassExpression(node);
        }

        protected boolean isDataRangeStrict(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            assert object != null;
            return isDataRangeStrict(object);
        }

        protected boolean isDataRangeLax(@Nonnull IRI node) {
            return consumer.isDataRange(node);
        }

        protected boolean isDataRangeLax(@Nonnull IRI mainNode,
                @Nonnull OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataRangeLax(mainNode);
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
            Set<IRI> visitedListNodes = new HashSet<IRI>();
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

        class ClassExpressionMatcher implements TypeMatcher {

            public ClassExpressionMatcher() {}

            @Override
            public boolean isTypeStrict(@Nonnull IRI node) {
                return isClassExpressionStrict(node);
            }
        }

        class DataRangeMatcher implements TypeMatcher {

            public DataRangeMatcher() {}

            @Override
            public boolean isTypeStrict(@Nonnull IRI node) {
                return isDataRangeStrict(node);
            }
        }

        class IndividualMatcher implements TypeMatcher {

            public IndividualMatcher() {}

            @SuppressWarnings("unused")
            @Override
            public boolean isTypeStrict(@Nonnull IRI node) {
                return true;
            }
        }
    }

    static abstract class AbstractBuiltInTypeHandler extends
            AbstractTriplePredicateHandler implements BuiltInTypeHandler {

        private IRI typeIRI;

        protected AbstractBuiltInTypeHandler(@Nonnull OWLRDFConsumer consumer,
                IRI typeIRI) {
            super(consumer, RDF_TYPE.getIRI());
            this.typeIRI = typeIRI;
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            return predicate.equals(RDF_TYPE.getIRI())
                    && object.equals(typeIRI);
        }

        @Override
        public IRI getTypeIRI() {
            return typeIRI;
        }
    }

    static class GTPAnnotationLiteralHandler extends AbstractTripleHandler
            implements LiteralTripleHandler {

        public GTPAnnotationLiteralHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandleStreaming(IRI subject, IRI predicate,
                OWLLiteral object) {
            return !isAnonymous(subject) && !consumer.isAnnotation(subject)
                    && consumer.isAnnotationProperty(predicate);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
            if (isStrict()) {
                return isAnnotationPropertyOnly(predicate);
            }
            boolean axiom = consumer.isAxiom(subject);
            if (axiom) {
                return false;
            }
            boolean annotation = consumer.isAnnotation(subject);
            if (annotation) {
                return false;
            }
            if (consumer.isAnnotationProperty(predicate)) {
                return true;
            }
            if (!isAnonymous(subject)) {
                if (isClassExpressionLax(subject)) {
                    return true;
                }
                if (isDataRangeLax(subject)) {
                    return true;
                }
                if (isObjectPropertyLax(subject)) {
                    return true;
                }
                if (isDataPropertyLax(subject)) {
                    return true;
                }
                return false;
            }
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, IRI predicate,
                OWLLiteral object) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(predicate);
            OWLAnnotationSubject annotationSubject;
            if (isAnonymous(subject)) {
                annotationSubject = df.getOWLAnonymousIndividual(subject
                        .toString());
            } else {
                annotationSubject = subject;
            }
            if (consumer.isOntology(subject)) {
                consumer.addOntologyAnnotation(df.getOWLAnnotation(prop,
                        object, getPendingAnnotations()));
            } else {
                OWLAnnotationAssertionAxiom ax = df
                        .getOWLAnnotationAssertionAxiom(prop,
                                annotationSubject, object,
                                getPendingAnnotations());
                addAxiom(ax);
            }
            consumeTriple(subject, predicate, object);
        }
    }

    static class GTPAnnotationResourceTripleHandler extends
            AbstractResourceTripleHandler {

        public GTPAnnotationResourceTripleHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            if (isStrict()) {
                return false;
            } else {
                return !isAnonymous(subject) && !isAnonymous(object)
                        && consumer.isAnnotationProperty(predicate);
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            boolean builtInAnnotationProperty = BUILT_IN_ANNOTATION_PROPERTY_IRIS
                    .contains(predicate);
            return !consumer.isAxiom(subject)
                    && !consumer.isAnnotation(subject)
                    && (builtInAnnotationProperty || !predicate
                            .isReservedVocabulary());
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, IRI predicate,
                @Nonnull IRI object) {
            OWLAnnotationValue value;
            if (isAnonymous(object)) {
                value = df.getOWLAnonymousIndividual(object.toString());
            } else {
                value = object;
            }
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(predicate);
            OWLAnnotation anno = df.getOWLAnnotation(prop, value);
            OWLAnnotationSubject annoSubject;
            if (isAnonymous(subject)) {
                annoSubject = df.getOWLAnonymousIndividual(subject.toString());
            } else {
                annoSubject = subject;
            }
            if (consumer.isOntology(subject)) {
                // Assume we annotation our ontology?
                consumer.addOntologyAnnotation(anno);
            } else {
                OWLAxiom decAx = df.getOWLAnnotationAssertionAxiom(annoSubject,
                        anno, getPendingAnnotations());
                addAxiom(decAx);
            }
            consumeTriple(subject, predicate, object);
        }
    }

    static class GTPDataPropertyAssertionHandler extends AbstractTripleHandler
            implements LiteralTripleHandler {

        public GTPDataPropertyAssertionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
            if (isStrict()) {
                return isDataPropertyStrict(predicate);
            } else {
                // Handle annotation assertions as annotation assertions only!
                return isDataPropertyLax(predicate)
                        && !consumer.isAnnotationProperty(predicate);
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandleStreaming(IRI subject, IRI predicate,
                OWLLiteral object) {
            return false;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, IRI predicate,
                OWLLiteral object) {
            addAxiom(df.getOWLDataPropertyAssertionAxiom(
                    translateDataProperty(predicate),
                    translateIndividual(subject), object,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class GTPLiteralTripleHandler extends AbstractTripleHandler
            implements LiteralTripleHandler {

        public GTPLiteralTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
            if (isStrict()) {} else {
                if (isAnnotationPropertyLax(predicate)) {} else {}
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandleStreaming(IRI subject, IRI predicate,
                OWLLiteral object) {
            if (isStrict()) {
                return false;
            }
            return isAnnotationPropertyLax(predicate);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
            return isAnnotationPropertyStrict(predicate)
                    || isDataPropertyStrict(predicate);
        }
    }

    static class GTPObjectPropertyAssertionHandler extends
            AbstractResourceTripleHandler {

        public GTPObjectPropertyAssertionHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, IRI object) {
            if (isStrict()) {
                return isObjectPropertyStrict(predicate);
            } else {
                // Handle annotation assertions as annotation assertions only!
                return isObjectPropertyLax(predicate)
                        && !isAnnotationPropertyOnly(predicate);
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, IRI predicate,
                @Nonnull IRI object) {
            if (consumer.isObjectProperty(predicate)) {
                consumeTriple(subject, predicate, object);
                addAxiom(df.getOWLObjectPropertyAssertionAxiom(
                        translateObjectProperty(predicate),
                        translateIndividual(subject),
                        translateIndividual(object), getPendingAnnotations()));
            }
        }
    }

    static class GTPResourceTripleHandler extends AbstractResourceTripleHandler {

        public GTPResourceTripleHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, IRI object) {
            return false;
        }
    }

    static class SKOSClassTripleHandler extends AbstractBuiltInTypeHandler {

        public SKOSClassTripleHandler(@Nonnull OWLRDFConsumer consumer,
                @Nonnull SKOSVocabulary v) {
            super(consumer, v.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            OWLIndividual ind = df.getOWLNamedIndividual(subject);
            OWLClass skosConcept = df.getOWLClass(object);
            addAxiom(df.getOWLClassAssertionAxiom(skosConcept, ind));
        }
    }

    static class TPAllValuesFromHandler extends AbstractTriplePredicateHandler {

        public TPAllValuesFromHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_VALUES_FROM.getIRI());
        }

        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addOWLRestriction(subject, false);
            IRI propIRI = consumer.getResourceObject(subject,
                    OWL_ON_PROPERTY.getIRI(), false);
            if (propIRI != null
                    && (!consumer.isAnonymousNode(object) || consumer.translatorAccessor
                            .getClassExpressionIfTranslated(object) != null)) {
                // The filler is either a datatype or named class
                if (consumer.isObjectPropertyOnly(propIRI)) {
                    consumer.addClassExpression(object, false);
                    consumer.addTriple(subject, predicate, object);
                    consumer.translatorAccessor
                            .translateClassExpression(subject);
                    return true;
                } else if (consumer.isDataPropertyOnly(propIRI)) {}
            }
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}
    }

    static class TPAnnotatedPropertyHandler extends
            AbstractTriplePredicateHandler {

        public TPAnnotatedPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATED_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addAnnotatedSource(object, subject);
            consumer.checkForAndProcessAnnotatedDeclaration(subject);
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}
    }

    static class TPAnnotatedSourceHandler extends
            AbstractTriplePredicateHandler {

        public TPAnnotatedSourceHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATED_SOURCE.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addAnnotatedSource(object, subject);
            consumer.checkForAndProcessAnnotatedDeclaration(subject);
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}
    }

    static class TPAnnotatedTargetHandler extends
            AbstractTriplePredicateHandler {

        public TPAnnotatedTargetHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATED_TARGET.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addAnnotatedSource(object, subject);
            consumer.checkForAndProcessAnnotatedDeclaration(subject);
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}
    }

    static class TPComplementOfHandler extends
            AbstractNamedEquivalentClassAxiomHandler {

        public TPComplementOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_COMPLEMENT_OF.getIRI());
        }

        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addClassExpression(subject, false);
            consumer.addClassExpression(object, false);
            return super.canHandleStreaming(subject, predicate, object);
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

        public TPDatatypeComplementOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DATATYPE_COMPLEMENT_OF.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addDataRange(subject, false);
            consumer.addDataRange(object, false);
            return false;
        }
    }

    @Deprecated
    static class TPDeclaredAsHandler extends AbstractTriplePredicateHandler {

        public TPDeclaredAsHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, DeprecatedVocabulary.OWL_DECLARED_AS);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @SuppressWarnings("unused")
        @Override
        public void
                handleTriple(IRI subject, IRI predicate, @Nonnull IRI object) {
            if (object.equals(OWL_CLASS.getIRI())) {
                addAxiom(df.getOWLDeclarationAxiom(df.getOWLClass(subject),
                        getPendingAnnotations()));
            } else if (object.equals(OWL_OBJECT_PROPERTY.getIRI())) {
                addAxiom(df.getOWLDeclarationAxiom(
                        df.getOWLObjectProperty(subject),
                        getPendingAnnotations()));
            } else if (object.equals(OWL_DATA_PROPERTY.getIRI())) {
                addAxiom(df
                        .getOWLDeclarationAxiom(df.getOWLDataProperty(subject),
                                getPendingAnnotations()));
            } else if (object.equals(OWL_DATATYPE.getIRI())) {
                addAxiom(df.getOWLDeclarationAxiom(df.getOWLDatatype(subject),
                        getPendingAnnotations()));
            }
        }
    }

    static class TPDifferentFromHandler extends AbstractTriplePredicateHandler {

        public TPDifferentFromHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DIFFERENT_FROM.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, IRI predicate,
                @Nonnull IRI object) {
            Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
            inds.add(translateIndividual(subject));
            inds.add(translateIndividual(object));
            addAxiom(df.getOWLDifferentIndividualsAxiom(inds,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPDisjointUnionHandler extends AbstractTriplePredicateHandler {

        public TPDisjointUnionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DISJOINT_UNION_OF.getIRI());
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            return super.canHandle(subject, predicate, object)
                    && !consumer.isAnonymousNode(subject)
                    && consumer.isClassExpression(subject);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addClassExpression(subject, false);
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!consumer.isAnonymousNode(subject)) {
                OWLClass cls = (OWLClass) translateClassExpression(subject);
                Set<OWLClassExpression> classExpressions = consumer.translatorAccessor
                        .translateToClassExpressionSet(object);
                addAxiom(df.getOWLDisjointUnionAxiom(cls, classExpressions,
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TPDisjointWithHandler extends AbstractTriplePredicateHandler {

        public TPDisjointWithHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DISJOINT_WITH.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addClassExpression(subject, false);
            consumer.addClassExpression(object, false);
            // NB: In strict parsing the above type triples won't get added
            // because
            // they aren't explicit,
            // so we need an extra check to see if there are type triples for
            // the
            // classes
            return !isSubjectOrObjectAnonymous(subject, object)
                    && isSubjectAndObjectClassExpression(subject, object);
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            return super.canHandle(subject, predicate, object)
                    && isSubjectAndObjectClassExpression(subject, object);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
            operands.add(translateClassExpression(subject));
            operands.add(translateClassExpression(object));
            addAxiom(df.getOWLDisjointClassesAxiom(operands,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPDistinctMembersHandler extends
            AbstractTriplePredicateHandler {

        public TPDistinctMembersHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DISTINCT_MEMBERS.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            // We need all of the list triples to be loaded :(
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            Set<OWLIndividual> inds = consumer.translatorAccessor
                    .translateToIndividualSet(object);
            addAxiom(df.getOWLDifferentIndividualsAxiom(inds,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPEquivalentClassHandler extends
            AbstractTriplePredicateHandler {

        public TPEquivalentClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_EQUIVALENT_CLASS.getIRI());
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            inferTypes(subject, object);
            return super.canHandle(subject, predicate, object)
                    && isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(
                            subject, object);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            inferTypes(subject, object);
            return !isStrict()
                    && !isSubjectOrObjectAnonymous(subject, object)
                    && isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(
                            subject, object);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (isStrict()) {
                if (isClassExpressionStrict(subject)
                        && isClassExpressionStrict(object)) {
                    translateEquivalentClasses(subject, predicate, object);
                } else if (isDataRangeStrict(subject)
                        && isDataRangeStrict(object)) {
                    translateEquivalentDataRanges(subject, predicate, object);
                }
            } else {
                if (isClassExpressionLax(subject)
                        && isClassExpressionLax(object)) {
                    translateEquivalentClasses(subject, predicate, object);
                } else if (isDataRangeLax(subject) || isDataRangeLax(object)) {
                    translateEquivalentDataRanges(subject, predicate, object);
                }
            }
        }

        private void translateEquivalentDataRanges(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            OWLDatatype datatype = df.getOWLDatatype(subject);
            OWLDataRange dataRange = consumer.translateDataRange(object);
            OWLDatatypeDefinitionAxiom def = df.getOWLDatatypeDefinitionAxiom(
                    datatype, dataRange, getPendingAnnotations());
            addAxiom(def);
            consumeTriple(subject, predicate, object);
        }

        private void translateEquivalentClasses(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
            operands.add(translateClassExpression(subject));
            operands.add(translateClassExpression(object));
            addAxiom(df.getOWLEquivalentClassesAxiom(operands,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPEquivalentPropertyHandler extends
            AbstractTriplePredicateHandler {

        public TPEquivalentPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_EQUIVALENT_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            Set<OWLAnnotation> pendingAnnotations = getPendingAnnotations();
            if (consumer.isObjectProperty(subject)
                    && consumer.isObjectProperty(object)) {
                Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
                props.add(translateObjectProperty(subject));
                props.add(translateObjectProperty(object));
                addAxiom(df.getOWLEquivalentObjectPropertiesAxiom(props,
                        pendingAnnotations));
                consumeTriple(subject, predicate, object);
            }
            if (consumer.isDataProperty(subject)
                    && consumer.isDataProperty(object)) {
                Set<OWLDataPropertyExpression> props = new HashSet<OWLDataPropertyExpression>();
                props.add(translateDataProperty(subject));
                props.add(translateDataProperty(object));
                addAxiom(df.getOWLEquivalentDataPropertiesAxiom(props,
                        pendingAnnotations));
                consumeTriple(subject, predicate, object);
            }
            // TODO: LOG ERROR
        }
    }

    static class TPFirstLiteralHandler extends AbstractTripleHandler implements
            LiteralTripleHandler {

        public TPFirstLiteralHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, @Nullable IRI predicate,
                OWLLiteral object) {
            return predicate != null && predicate.equals(RDF_FIRST.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandleStreaming(IRI subject, @Nullable IRI predicate,
                OWLLiteral object) {
            return predicate != null && predicate.equals(RDF_FIRST.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
            consumer.addFirst(subject, object);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPFirstResourceHandler extends AbstractTriplePredicateHandler {

        public TPFirstResourceHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_FIRST.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addFirst(subject, object);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPHasKeyHandler extends AbstractTriplePredicateHandler {

        private OptimisedListTranslator<OWLPropertyExpression> listTranslator;

        public TPHasKeyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_HAS_KEY.getIRI());
            listTranslator = Translators.getListTranslator(consumer);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addClassExpression(subject, false);
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isClassExpression(subject)) {
                consumeTriple(subject, predicate, object);
                OWLClassExpression ce = translateClassExpression(subject);
                Set<OWLPropertyExpression> props = listTranslator
                        .translateToSet(object);
                addAxiom(df.getOWLHasKeyAxiom(ce, props,
                        getPendingAnnotations()));
            }
        }
    }

    static class TPHasValueHandler extends AbstractTriplePredicateHandler {

        public TPHasValueHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_HAS_VALUE.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addOWLRestriction(subject, false);
            return false;
        }
    }

    static class TPImportsHandler extends AbstractTriplePredicateHandler {

        public TPImportsHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_IMPORTS.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            consumer.addOntology(subject);
            consumer.addOntology(object);
            OWLImportsDeclaration importsDeclaration = df
                    .getOWLImportsDeclaration(object);
            consumer.addImport(importsDeclaration);
            if (!consumer.getConfiguration().isIgnoredImport(object)) {
                OWLOntologyManager man = consumer.getOWLOntologyManager();
                man.makeLoadImportRequest(importsDeclaration,
                        consumer.getConfiguration());
                OWLOntology importedOntology = man
                        .getImportedOntology(importsDeclaration);
                if (importedOntology != null) {
                    OWLOntologyFormat importedOntologyFormat = man
                            .getOntologyFormat(importedOntology);
                    if (importedOntologyFormat instanceof RDFOntologyFormat
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
                                assert decl != null;
                                man.applyChange(new AddImport(consumer
                                        .getOntology(), decl));
                            }
                            for (OWLAnnotation anno : importedOntology
                                    .getAnnotations()) {
                                assert anno != null;
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

        public TPIntersectionOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_INTERSECTION_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectIntersectionOf(consumer.translatorAccessor
                    .translateToClassExpressionSet(mainNode));
        }

        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            if (consumer.isClassExpression(subject)) {
                consumer.addClassExpression(object, false);
            } else if (consumer.isClassExpression(object)) {
                consumer.addClassExpression(subject, false);
            } else if (consumer.isDataRange(subject)) {
                consumer.addDataRange(object, false);
            } else if (consumer.isDataRange(object)) {
                consumer.addDataRange(subject, false);
            }
            return super.canHandleStreaming(subject, predicate, object);
        }
    }

    /** owl:inverseOf is used in both property expressions AND axioms. */
    static class TPInverseOfHandler extends AbstractTriplePredicateHandler {

        private boolean axiomParsingMode = false;

        public TPInverseOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_INVERSE_OF.getIRI());
        }

        public boolean isAxiomParsingMode() {
            return axiomParsingMode;
        }

        public void setAxiomParsingMode(boolean axiomParsingMode) {
            this.axiomParsingMode = axiomParsingMode;
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addObjectProperty(subject, false);
            consumer.addObjectProperty(object, false);
            return false;
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            return super.canHandle(subject, predicate, object)
                    && consumer.isObjectProperty(subject)
                    && consumer.isObjectProperty(object);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            // Only do axiom translation
            if (axiomParsingMode && consumer.isObjectProperty(subject)
                    && consumer.isObjectProperty(object)) {
                addAxiom(df.getOWLInverseObjectPropertiesAxiom(
                        translateObjectProperty(subject),
                        translateObjectProperty(object),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TPOnClassHandler extends AbstractTriplePredicateHandler {

        public TPOnClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ON_CLASS.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addClassExpression(object, false);
            return false;
        }
    }

    static class TPOnDataRangeHandler extends AbstractTriplePredicateHandler {

        public TPOnDataRangeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ON_DATA_RANGE.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}

        @SuppressWarnings("unused")
        @Override
        public boolean canHandle(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addDataRange(object, true);
            return false;
        }
    }

    static class TPOnPropertyHandler extends AbstractTriplePredicateHandler {

        public TPOnPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ON_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addOWLRestriction(subject, false);
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {}
    }

    static class TPOneOfHandler extends
            AbstractNamedEquivalentClassAxiomHandler {

        public TPOneOfHandler(@Nonnull OWLRDFConsumer consumer) {
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

        public TPPropertyChainAxiomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_PROPERTY_CHAIN_AXIOM.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addObjectProperty(object, false);
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            OWLObjectPropertyExpression superProp = consumer
                    .translateObjectPropertyExpression(subject);
            List<OWLObjectPropertyExpression> chain = consumer.translatorAccessor
                    .translateToObjectPropertyList(object);
            consumeTriple(subject, predicate, object);
            Set<OWLAnnotation> annos = getPendingAnnotations();
            addAxiom(df.getOWLSubPropertyChainOfAxiom(chain, superProp, annos));
        }
    }

    static class TPPropertyDisjointWithHandler extends
            AbstractTriplePredicateHandler {

        public TPPropertyDisjointWithHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_PROPERTY_DISJOINT_WITH.getIRI());
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            inferTypes(subject, object);
            return super.canHandle(subject, predicate, object)
                    && (consumer.isObjectProperty(subject)
                            && consumer.isObjectProperty(object) || consumer
                            .isDataProperty(subject)
                            && consumer.isDataProperty(object));
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isDataProperty(subject)
                    && consumer.isDataProperty(object)) {
                addAxiom(df.getOWLDisjointDataPropertiesAxiom(CollectionFactory
                        .createSet(translateDataProperty(subject),
                                translateDataProperty(object)),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
            if (consumer.isObjectProperty(subject)
                    && consumer.isObjectProperty(object)) {
                addAxiom(df.getOWLDisjointObjectPropertiesAxiom(
                        CollectionFactory.createSet(
                                translateObjectProperty(subject),
                                translateObjectProperty(object)),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            inferTypes(subject, object);
            return false;
        }
    }

    static class TPPropertyDomainHandler extends AbstractTriplePredicateHandler {

        public TPPropertyDomainHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_DOMAIN.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(subject)
                    && consumer.isClassExpression(object)) {
                translateObjectPropertyDomain(subject, predicate, object);
            } else if (consumer.isDataPropertyOnly(subject)
                    && consumer.isClassExpression(object)) {
                translateDataPropertyDomain(subject, predicate, object);
            } else if (consumer.isAnnotationProperty(subject)
                    && consumer.isClassExpression(object)
                    && !consumer.isAnonymousNode(object)) {
                translateAnnotationPropertyDomain(subject, predicate, object);
            } else if (!isStrict()) {
                consumer.addAnnotationProperty(subject, false);
                translateAnnotationPropertyDomain(subject, predicate, object);
            }
        }

        private void translateAnnotationPropertyDomain(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(subject);
            addAxiom(df.getOWLAnnotationPropertyDomainAxiom(prop, object,
                    getPendingAnnotations()));
            // TODO: Handle anonymous domain - error?
            consumeTriple(subject, predicate, object);
        }

        private void translateDataPropertyDomain(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            addAxiom(df.getOWLDataPropertyDomainAxiom(
                    translateDataProperty(subject),
                    translateClassExpression(object), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }

        private void translateObjectPropertyDomain(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            addAxiom(df.getOWLObjectPropertyDomainAxiom(
                    translateObjectProperty(subject),
                    translateClassExpression(object), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPPropertyRangeHandler extends AbstractTriplePredicateHandler {

        public TPPropertyRangeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_RANGE.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            inferTypes(subject, object);
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (isStrict()) {
                if (isObjectPropertyStrict(subject)
                        && isClassExpressionStrict(object)) {
                    translateAsObjectPropertyRange(subject, predicate, object);
                } else if (isDataPropertyStrict(subject)
                        && isDataRangeStrict(object)) {
                    translateAsDataPropertyRange(subject, predicate, object);
                } else if (consumer.isAnnotationProperty(subject)
                        && !consumer.isAnonymousNode(object)) {
                    translateAsAnnotationPropertyRange(subject, predicate,
                            object);
                }
            } else {
                if (isAnnotationPropertyOnly(subject) && !isAnonymous(object)) {
                    translateAsAnnotationPropertyRange(subject, predicate,
                            object);
                } else if (isClassExpressionLax(object)) {
                    consumer.addObjectProperty(subject, false);
                    translateAsObjectPropertyRange(subject, predicate, object);
                } else if (isDataRangeLax(object)) {
                    consumer.addDataProperty(subject, false);
                    translateAsDataPropertyRange(subject, predicate, object);
                } else if (isObjectPropertyLax(subject)) {
                    consumer.addObjectProperty(subject, false);
                    translateAsObjectPropertyRange(subject, predicate, object);
                } else if (isDataPropertyLax(subject)) {
                    consumer.addDataProperty(subject, false);
                    translateAsDataPropertyRange(subject, predicate, object);
                } else {
                    consumer.addAnnotationProperty(subject, false);
                    translateAsAnnotationPropertyRange(subject, predicate,
                            object);
                }
            }
        }

        private void translateAsAnnotationPropertyRange(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(subject);
            addAxiom(df.getOWLAnnotationPropertyRangeAxiom(prop, object,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }

        private void translateAsDataPropertyRange(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            OWLDataPropertyExpression property = translateDataProperty(subject);
            OWLDataRange dataRange = translateDataRange(object);
            addAxiom(df.getOWLDataPropertyRangeAxiom(property, dataRange,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }

        private void translateAsObjectPropertyRange(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            OWLObjectPropertyExpression property = translateObjectProperty(subject);
            OWLClassExpression range = translateClassExpression(object);
            addAxiom(df.getOWLObjectPropertyRangeAxiom(property, range,
                    getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPRestHandler extends AbstractTriplePredicateHandler {

        public TPRestHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_REST.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @Override
        public void
                handleTriple(IRI subject, IRI predicate, @Nonnull IRI object) {
            if (!object.equals(RDF_NIL.getIRI())) {
                consumer.addRest(subject, object);
            }
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPSameAsHandler extends AbstractTriplePredicateHandler {

        public TPSameAsHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_SAME_AS.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, IRI predicate,
                @Nonnull IRI object) {
            Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
            inds.add(translateIndividual(subject));
            inds.add(translateIndividual(object));
            addAxiom(df.getOWLSameIndividualAxiom(inds,
                    consumer.getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPSomeValuesFromHandler extends AbstractTriplePredicateHandler {

        public TPSomeValuesFromHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_SOME_VALUES_FROM.getIRI());
        }

        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            handleTriple(subject, predicate, object);
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addOWLRestriction(subject, false);
            if (consumer.isDataRange(object)) {
                IRI property = consumer.getResourceObject(subject,
                        OWL_ON_PROPERTY.getIRI(), false);
                if (property != null) {
                    consumer.addDataProperty(property, false);
                }
            }
        }
    }

    /**
     * Handles rdfs:subClassOf triples. If handling is set to strict then the
     * triple is only consumed if the subject and object are typed as classes.
     */
    static class TPSubClassOfHandler extends AbstractTriplePredicateHandler {

        public TPSubClassOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_SUBCLASS_OF.getIRI());
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            return super.canHandle(subject, predicate, object)
                    && isTyped(subject, object);
        }

        private boolean isTyped(IRI subject, IRI object) {
            return consumer.isClassExpression(subject)
                    && consumer.isClassExpression(object);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addClassExpression(subject, false);
            consumer.addClassExpression(object, false);
            return !isStrict() && !isSubjectOrObjectAnonymous(subject, object);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (isStrict()) {
                if (isClassExpressionStrict(subject)
                        && isClassExpressionStrict(object)) {
                    translate(subject, predicate, object);
                }
            } else {
                if (isClassExpressionLax(subject)
                        && isClassExpressionLax(object)) {
                    translate(subject, predicate, object);
                }
            }
        }

        private void translate(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            OWLClassExpression subClass = translateClassExpression(subject);
            OWLClassExpression supClass = translateClassExpression(object);
            Set<OWLAnnotation> pendingAnnotations = consumer
                    .getPendingAnnotations();
            OWLAxiom ax = df.getOWLSubClassOfAxiom(subClass, supClass,
                    pendingAnnotations);
            addAxiom(ax);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPSubPropertyOfHandler extends AbstractTriplePredicateHandler {

        public TPSubPropertyOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_SUB_PROPERTY_OF.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(object)) {
                consumer.addObjectProperty(subject, false);
            } else if (consumer.isDataProperty(object)) {
                consumer.addDataProperty(object, false);
            } else if (consumer.isAnnotationProperty(object)) {
                consumer.addAnnotationProperty(subject, false);
            } else if (consumer.isObjectProperty(subject)) {
                consumer.addObjectProperty(object, false);
            } else if (consumer.isDataProperty(subject)) {
                consumer.addDataProperty(object, false);
            } else if (consumer.isAnnotationProperty(subject)) {
                consumer.addAnnotationProperty(object, false);
            }
            return false;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            // First check for object property chain
            if (!isStrict()
                    && consumer.hasPredicate(subject,
                            DeprecatedVocabulary.OWL_PROPERTY_CHAIN)) {
                // Property chain
                IRI chainList = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.OWL_PROPERTY_CHAIN, true);
                assert chainList != null;
                List<OWLObjectPropertyExpression> properties = consumer.translatorAccessor
                        .translateToObjectPropertyList(chainList);
                addAxiom(df.getOWLSubPropertyChainOfAxiom(properties,
                        translateObjectProperty(object),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            } else if (!isStrict()
                    && consumer.hasPredicate(subject, RDF_FIRST.getIRI())) {
                // Legacy object property chain representation
                List<OWLObjectPropertyExpression> properties = consumer.translatorAccessor
                        .translateToObjectPropertyList(subject);
                addAxiom(df.getOWLSubPropertyChainOfAxiom(properties,
                        translateObjectProperty(object),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            } else if (consumer.isObjectProperty(subject)
                    && consumer.isObjectProperty(object)) {
                translateSubObjectProperty(subject, predicate, object);
            } else if (consumer.isDataProperty(subject)
                    && consumer.isDataProperty(object)) {
                translateSubDataProperty(subject, predicate, object);
            } else if (!isStrict()) {
                OWLAnnotationProperty subAnnoProp = df
                        .getOWLAnnotationProperty(subject);
                OWLAnnotationProperty superAnnoProp = df
                        .getOWLAnnotationProperty(object);
                addAxiom(df.getOWLSubAnnotationPropertyOfAxiom(subAnnoProp,
                        superAnnoProp, getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }

        private void translateSubObjectProperty(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            // Object - object
            addAxiom(df.getOWLSubObjectPropertyOfAxiom(
                    translateObjectProperty(subject),
                    translateObjectProperty(object), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }

        private void translateSubDataProperty(@Nonnull IRI subject,
                @Nonnull IRI predicate, @Nonnull IRI object) {
            // Data - Data
            addAxiom(df.getOWLSubDataPropertyOfAxiom(
                    translateDataProperty(subject),
                    translateDataProperty(object), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPTypeHandler extends AbstractTriplePredicateHandler {

        public TPTypeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_TYPE.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean canHandleStreaming(IRI subject, IRI predicate,
                @Nonnull IRI object) {
            // Can handle if object isn;t anonymous and either the object
            // IRI is owl:Thing, or it is not part of the build in vocabulary
            consumer.addClassExpression(object, false);
            if (isAnonymous(object)) {
                return false;
            }
            if (object.isReservedVocabulary()) {
                return object.equals(OWL_THING.getIRI());
            }
            return true;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, IRI predicate,
                @Nonnull IRI object) {
            if (BUILT_IN_VOCABULARY_IRIS.contains(object)
                    && !object.equals(OWL_THING.getIRI())) {
                // Can't have instance of built in vocabulary!
                // Shall we throw an exception here?
                logger.info("Individual of builtin type {}", object);
            }
            addAxiom(df.getOWLClassAssertionAxiom(
                    translateClassExpression(object),
                    translateIndividual(subject), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    static class TPUnionOfHandler extends
            AbstractNamedEquivalentClassAxiomHandler {

        public TPUnionOfHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_UNION_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectUnionOf(consumer.translatorAccessor
                    .translateToClassExpressionSet(mainNode));
        }
    }

    static class TPVersionIRIHandler extends AbstractTriplePredicateHandler {

        public TPVersionIRIHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_VERSION_IRI.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            OWLOntology ontology = consumer.getOntology();
            // only setup the versionIRI if it is null before this point
            if (!ontology.getOntologyID().getVersionIRI().isPresent()) {
                Optional<IRI> ontologyIRI = ontology.getOntologyID()
                        .getOntologyIRI();
                Optional<IRI> versionIRI = Optional.of(object);
                // If there was no ontologyIRI before this point and the subject
                // of this statement was not anonymous,
                // then use the subject IRI as the ontology IRI, else we keep
                // the previous definition for the ontology IRI
                if (!ontologyIRI.isPresent() && !isAnonymous(subject)) {
                    ontologyIRI = Optional.of(subject);
                }
                OWLOntologyID ontologyID = new OWLOntologyID(ontologyIRI,
                        versionIRI);
                consumer.setOntologyID(ontologyID);
            }
            consumeTriple(subject, predicate, object);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            // Always apply at the end
            return false;
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            return predicate.equals(OWL_VERSION_IRI.getIRI());
        }
    }

    static abstract class AbstractTriplePredicateHandler extends
            AbstractResourceTripleHandler implements TriplePredicateHandler {

        private IRI predicateIRI;

        public AbstractTriplePredicateHandler(@Nonnull OWLRDFConsumer consumer,
                IRI predicateIRI) {
            super(consumer);
            this.predicateIRI = predicateIRI;
        }

        @Override
        public boolean
                canHandle(IRI subject, @Nonnull IRI predicate, IRI object) {
            inferTypes(subject, object);
            return predicate.equals(predicateIRI);
        }

        @Override
        public IRI getPredicateIRI() {
            return predicateIRI;
        }
    }

    static class TypeAllDifferentHandler extends AbstractBuiltInTypeHandler {

        public TypeAllDifferentHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DIFFERENT.getIRI());
        }

        @Override
        public boolean canHandle(IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            return super.canHandle(subject, predicate, object)
                    && consumer.getResourceObject(subject, OWL_MEMBERS, false) != null;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            IRI listNode = consumer.getResourceObject(subject,
                    OWL_MEMBERS.getIRI(), true);
            if (listNode != null) {
                Set<OWLIndividual> inds = consumer.translatorAccessor
                        .translateToIndividualSet(listNode);
                addAxiom(df.getOWLDifferentIndividualsAxiom(inds,
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }
    }

    static class TypeAllDisjointClassesHandler extends
            AbstractBuiltInTypeHandler {

        public TypeAllDisjointClassesHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DISJOINT_CLASSES.getIRI());
        }

        @Override
        public boolean canHandle(IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            return super.canHandle(subject, predicate, object)
                    && consumer.getResourceObject(subject, OWL_MEMBERS, false) != null;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            IRI listNode = consumer.getResourceObject(subject,
                    OWL_MEMBERS.getIRI(), true);
            if (listNode != null) {
                Set<OWLClassExpression> desc = consumer.translatorAccessor
                        .translateToClassExpressionSet(listNode);
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(subject);
                addAxiom(df.getOWLDisjointClassesAxiom(desc, annotations));
                consumeTriple(subject, predicate, object);
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }
    }

    static class TypeAllDisjointPropertiesHandler extends
            AbstractBuiltInTypeHandler {

        public TypeAllDisjointPropertiesHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DISJOINT_PROPERTIES.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            IRI listNode = consumer.getResourceObject(subject,
                    OWL_MEMBERS.getIRI(), true);
            assert listNode != null;
            if (consumer.isObjectProperty(consumer.getFirstResource(listNode,
                    false))) {
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(subject);
                List<OWLObjectPropertyExpression> props = consumer.translatorAccessor
                        .translateToObjectPropertyList(listNode);
                consumer.addAxiom(df.getOWLDisjointObjectPropertiesAxiom(
                        new HashSet<OWLObjectPropertyExpression>(props),
                        annotations));
            } else {
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(subject);
                List<OWLDataPropertyExpression> props = consumer.translatorAccessor
                        .translateToDataPropertyList(listNode);
                consumer.addAxiom(df.getOWLDisjointDataPropertiesAxiom(
                        new HashSet<OWLDataPropertyExpression>(props),
                        annotations));
            }
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }
    }

    static class TypeAnnotationHandler extends AbstractBuiltInTypeHandler {

        public TypeAnnotationHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATION.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addAnnotationIRI(subject);
        }
    }

    static class TypeAnnotationPropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeAnnotationPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATION_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!isAnonymous(subject)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLAnnotationProperty property = df
                        .getOWLAnnotationProperty(subject);
                addAxiom(df.getOWLDeclarationAxiom(property, annos));
                consumeTriple(subject, predicate, object);
            }
            consumer.addAnnotationProperty(subject, true);
        }
    }

    static class TypeAsymmetricPropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeAsymmetricPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ASYMMETRIC_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addObjectProperty(subject, false);
            return !isAnonymous(subject);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(subject)) {
                addAxiom(df.getOWLAsymmetricObjectPropertyAxiom(
                        translateObjectProperty(subject),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TypeAxiomHandler extends AbstractBuiltInTypeHandler {

        public TypeAxiomHandler(@Nonnull OWLRDFConsumer consumer) {
            this(consumer, OWL_AXIOM.getIRI());
        }

        public TypeAxiomHandler(@Nonnull OWLRDFConsumer consumer, IRI typeIRI) {
            super(consumer, typeIRI);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            // We can't handle this is a streaming fashion, because we can't
            // be sure that the subject, predicate, object triples have been
            // parsed.
            consumer.addAxiom(subject);
            return false;
        }

        /**
         * Gets the IRI of the predicate of the triple that specifies the target
         * of a reified axiom
         * 
         * @return The IRI, by default this is owl:annotatedTarget
         */
        protected OWLRDFVocabulary getTargetTriplePredicate() {
            return OWL_ANNOTATED_TARGET;
        }

        /**
         * Gets the IRI of the predicate of the triple that specifies that
         * predicate of a reified axiom
         * 
         * @return The IRI, by default this is owl:annotatedProperty
         */
        protected OWLRDFVocabulary getPropertyTriplePredicate() {
            return OWL_ANNOTATED_PROPERTY;
        }

        /**
         * Gets the IRI of the predicate of the triple that specifies the source
         * of a reified axiom
         * 
         * @return The IRI, by default this is owl:annotatedSource
         */
        protected OWLRDFVocabulary getSourceTriplePredicate() {
            return OWL_ANNOTATED_SOURCE;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            IRI annotatedSource = getObjectOfSourceTriple(subject);
            IRI annotatedProperty = getObjectOfPropertyTriple(subject);
            IRI annotatedTarget = getObjectOfTargetTriple(subject);
            OWLLiteral annotatedTargetLiteral = null;
            if (annotatedTarget == null) {
                annotatedTargetLiteral = getTargetLiteral(subject);
            }
            // check that other conditions are not invalid
            if (annotatedSource != null && annotatedProperty != null) {
                consumeTriple(subject, predicate, object);
                Set<OWLAnnotation> annotations = consumer
                        .translateAnnotations(subject);
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

        @SuppressWarnings("null")
        @Nonnull
        private OWLLiteral getTargetLiteral(IRI subject) {
            OWLLiteral con = consumer.getLiteralObject(subject,
                    getTargetTriplePredicate(), true);
            if (con == null) {
                con = consumer.getLiteralObject(subject,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            return con;
        }

        /**
         * Gets the object of the target triple that has the specified main node
         * 
         * @param mainNode
         *        The main node
         * @return The object of the triple that has the specified mainNode as
         *         its subject and the IRI returned by the
         *         {@code TypeAxiomHandler#getSourceTriplePredicate()} method.
         *         For backwards compatibility, a search will also be performed
         *         for triples whos subject is the specified mainNode and
         *         predicate rdf:object
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
        private IRI getObjectOfPropertyTriple(IRI subject) {
            IRI predicateTripleObject = consumer.getResourceObject(subject,
                    getPropertyTriplePredicate(), true);
            if (predicateTripleObject == null) {
                predicateTripleObject = consumer.getResourceObject(subject,
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

        public TypeClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_CLASS.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!isAnonymous(subject)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLClass owlClass = df.getOWLClass(subject);
                addAxiom(df.getOWLDeclarationAxiom(owlClass, annos));
            }
            consumer.addClassExpression(subject, true);
        }
    }

    static class TypeDataPropertyHandler extends AbstractBuiltInTypeHandler {

        public TypeDataPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DATA_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!isAnonymous(subject)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLDataProperty owlDataProperty = df
                        .getOWLDataProperty(subject);
                addAxiom(df.getOWLDeclarationAxiom(owlDataProperty, annos));
            }
            consumer.addDataProperty(subject, true);
        }
    }

    static class TypeDataRangeHandler extends AbstractBuiltInTypeHandler {

        public TypeDataRangeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DATA_RANGE.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!isAnonymous(subject)) {
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TypeDatatypeHandler extends AbstractBuiltInTypeHandler {

        public TypeDatatypeHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_DATATYPE.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!consumer.isAnonymousNode(subject)) {
                OWLDatatype dt = df.getOWLDatatype(subject);
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                addAxiom(df.getOWLDeclarationAxiom(dt, annos));
            }
            consumer.addDataRange(subject, true);
        }
    }

    static class TypeDeprecatedClassHandler extends AbstractBuiltInTypeHandler {

        public TypeDeprecatedClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DEPRECATED_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addClassExpression(subject, false);
            consumeTriple(subject, predicate, object);
            addAxiom(df.getDeprecatedOWLAnnotationAssertionAxiom(subject));
        }
    }

    static class TypeDeprecatedPropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeDeprecatedPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_DEPRECATED_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            addAxiom(df.getDeprecatedOWLAnnotationAssertionAxiom(subject));
        }
    }

    static class TypeFunctionalPropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeFunctionalPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_FUNCTIONAL_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(subject)) {
                addAxiom(df.getOWLFunctionalObjectPropertyAxiom(
                        translateObjectProperty(subject),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
            if (consumer.isDataProperty(subject)) {
                addAxiom(df
                        .getOWLFunctionalDataPropertyAxiom(
                                translateDataProperty(subject),
                                getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TypeInverseFunctionalPropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeInverseFunctionalPropertyHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_INVERSE_FUNCTIONAL_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.handlerAccessor.handle(subject, predicate,
                    OWL_OBJECT_PROPERTY.getIRI());
            return !isAnonymous(subject);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(subject)) {
                OWLObjectPropertyExpression property = translateObjectProperty(subject);
                addAxiom(df.getOWLInverseFunctionalObjectPropertyAxiom(
                        property, getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TypeIrreflexivePropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeIrreflexivePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_IRREFLEXIVE_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addObjectProperty(subject, false);
            return !isAnonymous(subject);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(subject)) {
                addAxiom(df.getOWLIrreflexiveObjectPropertyAxiom(
                        translateObjectProperty(subject),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TypeListHandler extends AbstractBuiltInTypeHandler {

        public TypeListHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_LIST.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeNamedIndividualHandler extends AbstractBuiltInTypeHandler {

        public TypeNamedIndividualHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_NAMED_INDIVIDUAL.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!isAnonymous(subject)) {
                Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
                OWLNamedIndividual individual = df
                        .getOWLNamedIndividual(subject);
                addAxiom(df.getOWLDeclarationAxiom(individual, annos));
            }
            consumer.addOWLNamedIndividual(subject, true);
        }
    }

    static class TypeNegativeDataPropertyAssertionHandler extends
            AbstractBuiltInTypeHandler {

        public TypeNegativeDataPropertyAssertionHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer,
                    DeprecatedVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @Override
        public void handleTriple(@Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object) {
            IRI source = source(subject);
            IRI property = property(subject);
            OWLLiteral target = target(subject);
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLDataPropertyExpression prop = consumer
                    .translateDataPropertyExpression(property);
            consumeTriple(subject, predicate, object);
            consumer.translateAnnotations(subject);
            Set<OWLAnnotation> annos = consumer.getPendingAnnotations();
            addAxiom(df.getOWLNegativeDataPropertyAssertionAxiom(prop,
                    sourceInd, target, annos));
        }

        @SuppressWarnings("null")
        @Nonnull
        OWLLiteral target(IRI subject) {
            OWLLiteral target = consumer.getLiteralObject(subject,
                    OWL_TARGET_VALUE.getIRI(), true);
            if (target == null) {
                target = consumer.getLiteralObject(subject,
                        DeprecatedVocabulary.OWL_OBJECT, true);
            }
            if (target == null) {
                target = consumer.getLiteralObject(subject,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            return target;
        }

        @SuppressWarnings("null")
        @Nonnull
        IRI property(IRI subject) {
            IRI property = consumer.getResourceObject(subject,
                    OWL_ASSERTION_PROPERTY.getIRI(), true);
            if (property == null) {
                property = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.OWL_PREDICATE, true);
            }
            if (property == null) {
                property = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.RDF_PREDICATE, true);
            }
            return property;
        }

        @SuppressWarnings("null")
        @Nonnull
        IRI source(IRI subject) {
            IRI source = consumer.getResourceObject(subject,
                    OWL_SOURCE_INDIVIDUAL.getIRI(), true);
            if (source == null) {
                source = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.OWL_SUBJECT, true);
            }
            if (source == null) {
                source = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.RDF_SUBJECT, true);
            }
            return source;
        }
    }

    static class TypeNegativePropertyAssertionHandler extends
            AbstractBuiltInTypeHandler {

        public TypeNegativePropertyAssertionHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return false;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            IRI source = source(subject);
            IRI property = property(subject);
            Object target = target(subject);
            Set<OWLAnnotation> annos = consumer.translateAnnotations(subject);
            if (target instanceof OWLLiteral
                    && (!isStrict() || consumer.isDataProperty(property))) {
                translateNegativeDataPropertyAssertion(subject, predicate,
                        object, source, property, (OWLLiteral) target, annos);
            } else if (target instanceof IRI
                    && (!isStrict() || consumer.isObjectProperty(property))) {
                translateNegativeObjectPropertyAssertion(subject, predicate,
                        object, source, property, (IRI) target, annos);
            }
            // TODO LOG ERROR
        }

        @SuppressWarnings("null")
        @Nonnull
        Object target(IRI subject) {
            Object target = consumer.getResourceObject(subject,
                    OWL_TARGET_INDIVIDUAL.getIRI(), true);
            if (target == null) {
                target = consumer.getLiteralObject(subject,
                        OWL_TARGET_VALUE.getIRI(), true);
            }
            if (target == null) {
                target = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            if (target == null) {
                target = consumer.getLiteralObject(subject,
                        DeprecatedVocabulary.RDF_OBJECT, true);
            }
            return target;
        }

        @SuppressWarnings("null")
        @Nonnull
        IRI property(IRI subject) {
            IRI property = consumer.getResourceObject(subject,
                    OWL_ASSERTION_PROPERTY.getIRI(), true);
            if (property == null) {
                property = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.RDF_PREDICATE, true);
            }
            return property;
        }

        @SuppressWarnings("null")
        @Nonnull
        IRI source(IRI subject) {
            IRI source = consumer.getResourceObject(subject,
                    OWL_SOURCE_INDIVIDUAL.getIRI(), true);
            if (source == null) {
                source = consumer.getResourceObject(subject,
                        DeprecatedVocabulary.RDF_SUBJECT, true);
            }
            return source;
        }

        private void translateNegativeObjectPropertyAssertion(
                @Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object, @Nonnull IRI source,
                @Nonnull IRI property, @Nonnull IRI target,
                @Nonnull Set<OWLAnnotation> annos) {
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLObjectPropertyExpression prop = consumer
                    .translateObjectPropertyExpression(property);
            OWLIndividual targetInd = consumer.getOWLIndividual(target);
            consumeTriple(subject, predicate, object);
            addAxiom(df.getOWLNegativeObjectPropertyAssertionAxiom(prop,
                    sourceInd, targetInd, annos));
        }

        private void translateNegativeDataPropertyAssertion(
                @Nonnull IRI subject, @Nonnull IRI predicate,
                @Nonnull IRI object, @Nonnull IRI source,
                @Nonnull IRI property, @Nonnull OWLLiteral target,
                @Nonnull Set<OWLAnnotation> annos) {
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLDataPropertyExpression prop = consumer
                    .translateDataPropertyExpression(property);
            consumeTriple(subject, predicate, object);
            addAxiom(df.getOWLNegativeDataPropertyAssertionAxiom(prop,
                    sourceInd, target, annos));
        }
    }

    static class TypeObjectPropertyHandler extends AbstractBuiltInTypeHandler {

        public TypeObjectPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_OBJECT_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (!isAnonymous(subject)) {
                OWLObjectProperty owlObjectProperty = df
                        .getOWLObjectProperty(subject);
                Set<OWLAnnotation> annos = getPendingAnnotations();
                addAxiom(df.getOWLDeclarationAxiom(owlObjectProperty, annos));
            }
            consumer.addObjectProperty(subject, true);
        }
    }

    static class TypeOntologyHandler extends AbstractBuiltInTypeHandler {

        public TypeOntologyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_ONTOLOGY.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            if (!isAnonymous(subject) && consumer.getOntologies().isEmpty()) {
                // Set IRI if it is not null before this point, and make sure to
                // preserve the version IRI if it also existed before this point
                if (!consumer.getOntology().getOntologyID().getOntologyIRI()
                        .isPresent()) {
                    OWLOntologyID id = new OWLOntologyID(Optional.of(subject),
                            consumer.getOntology().getOntologyID()
                                    .getVersionIRI());
                    consumer.applyChange(new SetOntologyID(consumer
                            .getOntology(), id));
                }
            }
            consumer.addOntology(subject);
        }
    }

    static class TypeOntologyPropertyHandler extends AbstractBuiltInTypeHandler {

        public TypeOntologyPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, DeprecatedVocabulary.OWL_ONTOLOGY_PROPERTY);
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            return true;
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            // Add a type triple for an annotation property (Table 6 in Mapping
            // to RDF Graph Spec)
            consumer.handlerAccessor.handle(subject, predicate,
                    OWL_ANNOTATION_PROPERTY.getIRI());
        }
    }

    static class TypePropertyHandler extends AbstractBuiltInTypeHandler {

        public TypePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDF_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            // We need to consume this triple
            consumeTriple(subject, predicate, object);
            logger.info("Usage of rdf vocabulary: {} -> {} -> {}", subject,
                    predicate, object);
        }
    }

    static class TypeRDFSClassHandler extends AbstractBuiltInTypeHandler {

        public TypeRDFSClassHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, RDFS_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            // TODO: Change to rdfs:Class? (See table 5 in the spec)
            consumer.addClassExpression(subject, false);
            consumeTriple(subject, predicate, object);
            if (!isStrict()) {
                consumer.handlerAccessor.handle(subject, predicate,
                        OWL_CLASS.getIRI());
            }
        }
    }

    static class TypeReflexivePropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeReflexivePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_REFLEXIVE_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.addObjectProperty(subject, false);
            return !isAnonymous(subject);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(subject)) {
                addAxiom(df.getOWLReflexiveObjectPropertyAxiom(
                        translateObjectProperty(subject),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TypeRestrictionHandler extends AbstractBuiltInTypeHandler {

        public TypeRestrictionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_RESTRICTION.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            consumer.addOWLRestriction(subject, true);
            consumer.addClassExpression(subject, false);
        }
    }

    static class TypeSWRLAtomListHandler extends AbstractBuiltInTypeHandler {

        public TypeSWRLAtomListHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.ATOM_LIST.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSWRLBuiltInAtomHandler extends AbstractBuiltInTypeHandler {

        public TypeSWRLBuiltInAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.BUILT_IN_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addSWRLBuiltInAtom(subject);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSWRLBuiltInHandler extends AbstractBuiltInTypeHandler {

        public TypeSWRLBuiltInHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.BUILT_IN_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            // Just consume - I don't care about this
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSWRLClassAtomHandler extends AbstractBuiltInTypeHandler {

        public TypeSWRLClassAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.CLASS_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addSWRLClassAtom(subject);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSWRLDataRangeAtomHandler extends
            AbstractBuiltInTypeHandler {

        public TypeSWRLDataRangeAtomHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DATA_RANGE_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addSWRLDataRangeAtom(subject);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSWRLDataValuedPropertyAtomHandler extends
            AbstractBuiltInTypeHandler {

        public TypeSWRLDataValuedPropertyAtomHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DATAVALUED_PROPERTY_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            consumer.addSWRLDataPropertyAtom(subject);
        }
    }

    static class TypeSWRLDifferentIndividualsAtomHandler extends
            AbstractBuiltInTypeHandler {

        public TypeSWRLDifferentIndividualsAtomHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DIFFERENT_INDIVIDUALS_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addSWRLDifferentFromAtom(subject);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSWRLImpHandler extends AbstractBuiltInTypeHandler {

        public TypeSWRLImpHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.IMP.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            consumer.addSWRLRule(subject);
        }
    }

    static class TypeSWRLIndividualPropertyAtomHandler extends
            AbstractBuiltInTypeHandler {

        public TypeSWRLIndividualPropertyAtomHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.INDIVIDUAL_PROPERTY_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            consumer.addSWRLIndividualPropertyAtom(subject);
        }
    }

    static class TypeSWRLSameIndividualAtomHandler extends
            AbstractBuiltInTypeHandler {

        public TypeSWRLSameIndividualAtomHandler(
                @Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.SAME_INDIVIDUAL_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addSWRLSameAsAtom(subject);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSWRLVariableHandler extends AbstractBuiltInTypeHandler {

        public TypeSWRLVariableHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.VARIABLE.getIRI());
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumer.addSWRLVariable(subject);
            consumeTriple(subject, predicate, object);
        }
    }

    static class TypeSelfRestrictionHandler extends AbstractBuiltInTypeHandler {

        public TypeSelfRestrictionHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, DeprecatedVocabulary.OWL_SELF_RESTRICTION);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            consumeTriple(subject, predicate, object);
            consumer.addOWLRestriction(subject, false);
            // Patch to new OWL syntax
            consumer.addTriple(subject, OWL_HAS_SELF.getIRI(),
                    df.getOWLLiteral(true));
        }
    }

    static class TypeSymmetricPropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeSymmetricPropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_SYMMETRIC_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            if (!isAnonymous(subject)) {
                consumer.handlerAccessor.handle(subject, predicate,
                        OWL_OBJECT_PROPERTY.getIRI());
            }
            consumer.addObjectProperty(subject, false);
            return !isAnonymous(subject);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            if (consumer.isObjectProperty(subject)) {
                addAxiom(df.getOWLSymmetricObjectPropertyAxiom(
                        translateObjectProperty(subject),
                        getPendingAnnotations()));
                consumeTriple(subject, predicate, object);
            }
        }
    }

    static class TypeTransitivePropertyHandler extends
            AbstractBuiltInTypeHandler {

        public TypeTransitivePropertyHandler(@Nonnull OWLRDFConsumer consumer) {
            super(consumer, OWL_TRANSITIVE_PROPERTY.getIRI());
        }

        @SuppressWarnings("unused")
        @Override
        public boolean
                canHandleStreaming(IRI subject, IRI predicate, IRI object) {
            consumer.handlerAccessor.handle(subject, predicate,
                    OWL_OBJECT_PROPERTY.getIRI());
            return !isAnonymous(subject);
        }

        @Override
        public void handleTriple(IRI subject, IRI predicate, IRI object) {
            addAxiom(df.getOWLTransitiveObjectPropertyAxiom(
                    translateObjectProperty(subject), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }
}
