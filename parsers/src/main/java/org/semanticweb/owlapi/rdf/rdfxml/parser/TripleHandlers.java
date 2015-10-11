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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.AbstractRDFPrefixDocumentFormat;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

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
        private final @Nonnull Map<IRI, BuiltInTypeHandler> builtInTypes;
        /**
         * Handler for triples that denote nodes which represent axioms. i.e.
         * owl:AllDisjointClasses owl:AllDisjointProperties owl:AllDifferent
         * owl:NegativePropertyAssertion owl:Axiom These need to be handled
         * separately from other types, because the base triples for annotated
         * axioms should be in the ontology before annotations on the annotated
         * versions of these axioms are parsed.
         */
        protected final @Nonnull Map<IRI, BuiltInTypeHandler> axiomTypes;
        /** Handlers for build in predicates */
        protected final @Nonnull Map<IRI, TriplePredicateHandler> predicates;
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
        protected final @Nonnull TPInverseOfHandler inverseOf;
        /** The non built in type handler. */
        private final @Nonnull TPTypeHandler nonBuiltInTypes;
        protected final @Nonnull OWLRDFConsumer consumer;

        HandlerAccessor(OWLRDFConsumer r) {
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

        void apply(IRI s, IRI p, OWLLiteral o) {
            if (OWLRDFConsumer.isGeneralPredicate(p)) {
                for (LiteralTripleHandler lhandler : literals) {
                    if (lhandler.canHandle(s, p, o)) {
                        lhandler.handleTriple(s, p, o);
                        return;
                    }
                }
            }
        }

        void apply(IRI s, IRI p, IRI o) {
            if (OWLRDFConsumer.isGeneralPredicate(p)) {
                for (ResourceTripleHandler handler : resources) {
                    if (handler.canHandle(s, p, o)) {
                        handler.handleTriple(s, p, o);
                        return;
                    }
                }
            }
        }

        void applyAnns(IRI s, IRI p, IRI o) {
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
        public void handleStreaming(IRI s, IRI p, IRI o) {
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

        public void handleStreaming(IRI s, IRI p, String literal, @Nullable IRI datatype, @Nullable String lang) {
            // Convert all literals to OWLConstants
            OWLLiteral con = consumer.getOWLLiteral(literal, datatype, lang);
            handleStreaming(s, p, con);
        }

        private void handleStreaming(IRI s, IRI p, OWLLiteral con) {
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
        protected void handle(IRI s, IRI p, IRI o) {
            if (p.equals(OWLRDFVocabulary.RDF_TYPE.getIRI())) {
                BuiltInTypeHandler typeHandler = builtInTypes.get(o);
                if (typeHandler != null) {
                    typeHandler.handleTriple(s, p, o);
                } else if (axiomTypes.get(o) == null) {
                    // C(a)
                    OWLIndividual ind = consumer.translateIndividual(s);
                    OWLClassExpression ce = consumer.translatorAccessor.translateClassExpression(o);
                    consumer.addAxiom(consumer.getDataFactory().getOWLClassAssertionAxiom(ce, ind,
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

        protected void handle(IRI s, IRI p, OWLLiteral o) {
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
        public Set<RDFTriple> mopUp() {
            // We need to mop up all remaining triples. These triples will be in
            // the triples by subject map. Other triples which reside in the
            // triples by predicate (single valued) triple aren't "root" triples
            // for axioms. First we translate all system triples, starting with
            // property ranges, then go for triples whose predicates are not
            // system/reserved vocabulary IRIs to translate these into ABox
            // assertions or annotationIRIs
            TriplePredicateHandler propertyRangeHandler = predicates.get(RDFS_RANGE.getIRI());
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

        private Set<RDFTriple> getRemainingTriples() {
            Set<RDFTriple> remaining = new HashSet<>();
            consumer.iterateResources((s, p, o) -> remaining
                .add(new RDFTriple(s, consumer.isAnonymousNode(s), p, o, consumer.isAnonymousNode(o))));
            consumer.iterateLiterals((s, p, o) -> remaining.add(new RDFTriple(s, consumer.isAnonymousNode(s), p, o)));
            return remaining;
        }

        private static List<ResourceTripleHandler> getResourceTripleHandlers(OWLRDFConsumer r) {
            return CollectionFactory.list((ResourceTripleHandler) new GTPObjectPropertyAssertionHandler(r),
                new GTPAnnotationResourceTripleHandler(r));
        }

        protected Map<IRI, TriplePredicateHandler> getPredicateHandlers(OWLRDFConsumer r) {
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

        public static Map<IRI, BuiltInTypeHandler> getAxiomTypeHandlers(OWLRDFConsumer r) {
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
        public static List<LiteralTripleHandler> getLiteralTripleHandlers(OWLRDFConsumer r) {
            return CollectionFactory.list((LiteralTripleHandler) new GTPDataPropertyAssertionHandler(r),
                new TPFirstLiteralHandler(r), new GTPAnnotationLiteralHandler(r));
        }

        private static void add(Map<IRI, BuiltInTypeHandler> m, BuiltInTypeHandler h) {
            m.put(h.getTypeIRI(), h);
        }

        private static void add(Map<IRI, TriplePredicateHandler> map, TriplePredicateHandler h) {
            map.put(h.getPredicateIRI(), h);
        }

        public static Map<IRI, BuiltInTypeHandler> getBasicTypeHandlers(OWLRDFConsumer r,
            OWLOntologyLoaderConfiguration config) {
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
    abstract static class AbstractNamedEquivalentClassAxiomHandler extends AbstractTriplePredicateHandler {

        AbstractNamedEquivalentClassAxiomHandler(OWLRDFConsumer consumer, IRI predicateIRI) {
            super(consumer, predicateIRI);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return super.canHandle(s, p, o) && !isAnon(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            add(df.getOWLEquivalentClassesAxiom(Sets.newHashSet(ce(s), translateEquivalentClass(o))));
        }

        protected abstract OWLClassExpression translateEquivalentClass(IRI mainNode);
    }

    abstract static class AbstractResourceTripleHandler extends AbstractTripleHandler implements ResourceTripleHandler {

        protected AbstractResourceTripleHandler(OWLRDFConsumer consumer) {
            super(consumer);
        }

        protected boolean eitherAnon(IRI s, IRI o) {
            return isAnon(s) || isAnon(o);
        }

        protected boolean bothClassOrDataRange(IRI s, IRI o) {
            return bothCe(s, o) || bothDataRange(s, o);
        }

        protected boolean bothDataRange(IRI s, IRI o) {
            return isDr(s) && isDr(o);
        }

        protected boolean bothCe(IRI s, IRI o) {
            return isCe(s) && isCe(o);
        }

        /**
         * @param s
         *        subject
         * @param o
         *        object
         */
        protected void inferTypes(IRI s, IRI o) {
            if (isCe(o)) {
                addCe(s, false);
            } else if (isDr(o)) {
                addDR(s, false);
            } else if (isCe(s)) {
                addCe(o, false);
            } else if (isDr(s)) {
                addDR(o, false);
            }
        }
    }

    static class AbstractTripleHandler {

        protected final @Nonnull OWLRDFConsumer consumer;
        private final @Nonnull TypeMatcher ceMatcher = node -> isClassExpressionStrict(node);
        private final @Nonnull TypeMatcher drMatcher = node -> isDataRangeStrict(node);
        private final @Nonnull TypeMatcher indMatcher = node -> true;
        protected final OWLDataFactory df;

        protected AbstractTripleHandler(OWLRDFConsumer consumer) {
            this.consumer = consumer;
            df = consumer.getDataFactory();
        }

        public OWLAnnotationSubject getSubject(IRI s) {
            if (isAnon(s)) {
                return consumer.getOWLAnonymousIndividual(s.toString());
            }
            return s;
        }

        protected Set<OWLAnnotation> anns() {
            return consumer.getPendingAnnotations();
        }

        protected void consume(IRI s, IRI p, IRI o) {
            consumer.consumeTriple(s, p, o);
        }

        protected void consume(IRI s, IRI p, OWLLiteral o) {
            consumer.consumeTriple(s, p, o);
        }

        public Set<OWLDataPropertyExpression> dps(IRI listNode) {
            return new HashSet<>(consumer.translatorAccessor.translateToDataPropertyList(listNode));
        }

        public Set<OWLObjectPropertyExpression> ops(IRI listNode) {
            return new HashSet<>(consumer.translatorAccessor.translateToObjectPropertyList(listNode));
        }

        protected boolean isStrict() {
            return consumer.getConfiguration().isStrict();
        }

        protected boolean isAnnotationPropertyOnly(IRI iri) {
            return consumer.isAnnotationPropertyOnly(iri);
        }

        protected boolean isApLax(IRI iri) {
            return consumer.isAnnotationProperty(iri);
        }

        protected boolean isCe(IRI iri) {
            return consumer.isClassExpression(iri);
        }

        protected boolean isDr(IRI iri) {
            return consumer.isDataRange(iri);
        }

        protected void addImport(OWLOntologyManager man, OWLImportsDeclaration i) {
            man.applyChange(new AddImport(consumer.getOntology(), i));
        }

        protected void addOntAnn(OWLOntologyManager man, OWLAnnotation ann) {
            man.applyChange(new AddOntologyAnnotation(consumer.getOntology(), ann));
        }

        protected void add(OWLAxiom axiom) {
            consumer.addAxiom(axiom);
        }

        protected Set<OWLAnnotation> anns(IRI s) {
            return consumer.translateAnnotations(s);
        }

        protected void addCe(IRI iri, boolean explicitlyTyped) {
            consumer.addClassExpression(iri, explicitlyTyped);
        }

        protected void addDR(IRI iri, boolean explicitlyTyped) {
            consumer.addDataRange(iri, explicitlyTyped);
        }

        protected void addAp(IRI iri, boolean explicitlyTyped) {
            consumer.addAnnotationProperty(iri, explicitlyTyped);
        }

        protected void addOp(IRI iri, boolean explicitlyTyped) {
            consumer.addObjectProperty(iri, explicitlyTyped);
        }

        protected void addDp(IRI iri, boolean explicitlyTyped) {
            consumer.addDataProperty(iri, explicitlyTyped);
        }

        protected void addR(IRI iri, boolean explicitlyTyped) {
            consumer.addOWLRestriction(iri, explicitlyTyped);
        }

        protected OWLClassExpression ce(IRI iri) {
            return consumer.translatorAccessor.translateClassExpression(iri);
        }

        protected OWLObjectPropertyExpression op(IRI iri) {
            return consumer.translateObjectPropertyExpression(iri);
        }

        protected OWLDataPropertyExpression dp(IRI iri) {
            return consumer.translateDataPropertyExpression(iri);
        }

        protected OWLDataRange dr(IRI iri) {
            return consumer.translateDataRange(iri);
        }

        protected OWLIndividual ind(IRI iri) {
            return consumer.translateIndividual(iri);
        }

        protected boolean isAnon(IRI node) {
            return consumer.isAnonymousNode(node);
        }

        protected boolean isResourcePresent(IRI mainNode, OWLRDFVocabulary p) {
            return getResourceObject(mainNode, p) != null;
        }

        protected @Nullable IRI getResourceObject(IRI mainNode, OWLRDFVocabulary p) {
            return consumer.getResourceObject(mainNode, p, false);
        }

        protected @Nullable IRI getRO(IRI mainNode, OWLRDFVocabulary p) {
            return consumer.getResourceObject(mainNode, p, true);
        }

        protected boolean isLiteralPresent(IRI mainNode, OWLRDFVocabulary p) {
            return consumer.getLiteralObject(mainNode, p, false) != null;
        }

        protected boolean isNonNegativeIntegerStrict(IRI mainNode, OWLRDFVocabulary p) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, p, false);
            if (literal == null) {
                return false;
            }
            return OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.matches(literal.getDatatype())
                && OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.isInLexicalSpace(literal.getLiteral());
        }

        protected boolean isNonNegativeIntegerLax(IRI mainNode, OWLRDFVocabulary p) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, p, false);
            if (literal == null) {
                return false;
            }
            return OWL2Datatype.XSD_INTEGER.isInLexicalSpace(verifyNotNull(literal.getLiteral().trim()));
        }

        protected int integer(IRI mainNode, OWLRDFVocabulary p) {
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

        protected boolean isClassExpressionStrict(IRI node) {
            return isCe(node) && !isDr(node);
        }

        protected boolean isClassExpressionStrict(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return o != null && isClassExpressionStrict(o);
        }

        protected boolean isCeLax(IRI mainNode) {
            return isCe(mainNode) || consumer.isParsedAllTriples() && !isDr(mainNode);
        }

        protected boolean isClassExpressionLax(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return o != null && isCeLax(o);
        }

        protected boolean isObjectPropertyStrict(IRI node) {
            return consumer.isObjectPropertyOnly(node);
        }

        protected boolean isObjectPropertyStrict(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return o != null && isObjectPropertyStrict(o);
        }

        protected boolean isOpLax(@Nullable IRI node) {
            if (node == null) {
                return false;
            }
            return consumer.isObjectProperty(node);
        }

        protected boolean isObjectPropertyLax(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return o != null && isOpLax(o);
        }

        protected boolean isDataPropertyStrict(IRI node) {
            return consumer.isDataPropertyOnly(node);
        }

        protected boolean isDataPropertyStrict(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return o != null && isDataPropertyStrict(o);
        }

        protected boolean isDPLax(IRI node) {
            return consumer.isDataProperty(node);
        }

        protected boolean isDPLax(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return o != null && isDPLax(o);
        }

        protected boolean isDataRangeStrict(@Nullable IRI node) {
            return node != null && isDr(node) && !isCe(node);
        }

        protected boolean isDataRangeStrict(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return isDataRangeStrict(o);
        }

        protected boolean isDrLax(IRI node) {
            return isDr(node);
        }

        protected boolean isDataRangeLax(IRI mainNode, OWLRDFVocabulary p) {
            IRI o = getResourceObject(mainNode, p);
            return o != null && isDrLax(mainNode);
        }

        protected boolean isClassExpressionListStrict(IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, ceMatcher, minSize);
        }

        protected boolean isDataRangeListStrict(IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, drMatcher, minSize);
        }

        protected boolean isIndividualListStrict(IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, indMatcher, minSize);
        }

        protected boolean isResourceListStrict(@Nullable IRI mainNode, TypeMatcher typeMatcher, int minSize) {
            if (mainNode == null) {
                return false;
            }
            IRI currentListNode = mainNode;
            Set<IRI> visitedListNodes = new HashSet<>();
            int size = 0;
            while (true) {
                IRI firstObject = getResourceObject(currentListNode, RDF_FIRST);
                if (firstObject == null) {
                    return false;
                }
                if (!typeMatcher.isTypeStrict(firstObject)) {
                    // Something in the list that is not of the required type
                    return false;
                } else {
                    size++;
                }
                IRI restObject = getResourceObject(currentListNode, RDF_REST);
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

    abstract static class AbstractBuiltInTypeHandler extends AbstractTriplePredicateHandler
        implements BuiltInTypeHandler {

        private final IRI typeIRI;

        protected AbstractBuiltInTypeHandler(OWLRDFConsumer consumer, IRI typeIRI) {
            super(consumer, RDF_TYPE.getIRI());
            this.typeIRI = typeIRI;
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return p.equals(RDF_TYPE.getIRI()) && o.equals(typeIRI);
        }

        @Override
        public IRI getTypeIRI() {
            return typeIRI;
        }
    }

    static class GTPAnnotationLiteralHandler extends AbstractTripleHandler implements LiteralTripleHandler {

        GTPAnnotationLiteralHandler(OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, OWLLiteral o) {
            return !isAnon(s) && !consumer.isAnnotation(s) && isApLax(p);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, OWLLiteral o) {
            if (isStrict()) {
                return isAnnotationPropertyOnly(p);
            }
            if (consumer.isAxiom(s) || consumer.isAnnotation(s)) {
                return false;
            }
            if (isApLax(p) || isAnon(s)) {
                return true;
            }
            return isCeLax(s) || isDrLax(s) || isOpLax(s) || isDPLax(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, OWLLiteral o) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(p);
            if (consumer.isOntology(s)) {
                consumer.addOntologyAnnotation(df.getOWLAnnotation(prop, o, anns()));
            } else {
                add(df.getOWLAnnotationAssertionAxiom(prop, getSubject(s), o, anns()));
            }
            consume(s, p, o);
        }
    }

    static class GTPAnnotationResourceTripleHandler extends AbstractResourceTripleHandler {

        GTPAnnotationResourceTripleHandler(OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                return false;
            }
            return !isAnon(s) && !isAnon(o) && isApLax(p);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return !consumer.isAxiom(s) && !consumer.isAnnotation(s)
                && (BUILT_IN_AP_IRIS.contains(p) || !p.isReservedVocabulary());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            OWLAnnotationValue value;
            if (isAnon(o)) {
                value = consumer.getOWLAnonymousIndividual(o.toString());
            } else {
                value = o;
            }
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(p);
            OWLAnnotation anno = df.getOWLAnnotation(prop, value);
            OWLAnnotationSubject subject = getSubject(s);
            if (consumer.isOntology(s)) {
                // Assume we annotation our ontology?
                consumer.addOntologyAnnotation(anno);
            } else {
                add(df.getOWLAnnotationAssertionAxiom(subject, anno, anns()));
            }
            consume(s, p, o);
        }
    }

    static class GTPDataPropertyAssertionHandler extends AbstractTripleHandler implements LiteralTripleHandler {

        GTPDataPropertyAssertionHandler(OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, OWLLiteral o) {
            if (isStrict()) {
                return isDataPropertyStrict(p);
            }
            // Handle annotation assertions as annotation assertions only!
            return isDPLax(p) && !isApLax(p);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, OWLLiteral o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, OWLLiteral o) {
            add(df.getOWLDataPropertyAssertionAxiom(dp(p), ind(s), o, anns()));
            consume(s, p, o);
        }
    }

    static class GTPLiteralTripleHandler extends AbstractTripleHandler implements LiteralTripleHandler {

        GTPLiteralTripleHandler(OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public void handleTriple(IRI s, IRI p, OWLLiteral o) {}

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, OWLLiteral o) {
            if (isStrict()) {
                return false;
            }
            return isApLax(p);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, OWLLiteral o) {
            return isAnnotationPropertyOnly(p) || isDataPropertyStrict(p);
        }
    }

    static class GTPObjectPropertyAssertionHandler extends AbstractResourceTripleHandler {

        GTPObjectPropertyAssertionHandler(OWLRDFConsumer consumer) {
            super(consumer);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                return isObjectPropertyStrict(p);
            }
            // Handle annotation assertions as annotation assertions only!
            return isOpLax(p) && !isAnnotationPropertyOnly(p);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(p)) {
                consume(s, p, o);
                add(df.getOWLObjectPropertyAssertionAxiom(op(p), ind(s), ind(o), anns()));
            }
        }
    }

    static class GTPResourceTripleHandler extends AbstractResourceTripleHandler {

        GTPResourceTripleHandler(OWLRDFConsumer consumer) {
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

        SKOSClassTripleHandler(OWLRDFConsumer consumer, SKOSVocabulary v) {
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

        TPAllValuesFromHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_VALUES_FROM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addR(s, false);
            IRI propIRI = getResourceObject(s, OWL_ON_PROPERTY);
            if (propIRI != null
                && (!isAnon(o) || consumer.translatorAccessor.getClassExpressionIfTranslated(o) != null)) {
                // The filler is either a datatype or named class
                if (isObjectPropertyStrict(propIRI)) {
                    addCe(o, false);
                    consumer.addTriple(s, p, o);
                    ce(s);
                    return true;
                } else if (isDataPropertyStrict(propIRI)) {}
            }
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}
    }

    static class TPAnnotatedPropertyHandler extends AbstractTriplePredicateHandler {

        TPAnnotatedPropertyHandler(OWLRDFConsumer consumer) {
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

    static class TPAnnotatedSourceHandler extends AbstractTriplePredicateHandler {

        TPAnnotatedSourceHandler(OWLRDFConsumer consumer) {
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

    static class TPAnnotatedTargetHandler extends AbstractTriplePredicateHandler {

        TPAnnotatedTargetHandler(OWLRDFConsumer consumer) {
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

    static class TPComplementOfHandler extends AbstractNamedEquivalentClassAxiomHandler {

        TPComplementOfHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_COMPLEMENT_OF.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addCe(s, false);
            addCe(o, false);
            return super.canHandleStreaming(s, p, o);
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectComplementOf(ce(mainNode));
        }
    }

    static class TPDatatypeComplementOfHandler extends AbstractTriplePredicateHandler {

        TPDatatypeComplementOfHandler(OWLRDFConsumer consumer) {
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
            addDR(s, false);
            addDR(o, false);
            return false;
        }
    }

    @Deprecated
    static class TPDeclaredAsHandler extends AbstractTriplePredicateHandler {

        TPDeclaredAsHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DECLARED_AS.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (o.equals(OWL_CLASS.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLClass(s), anns()));
            } else if (o.equals(OWL_OBJECT_PROPERTY.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLObjectProperty(s), anns()));
            } else if (o.equals(OWL_DATA_PROPERTY.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLDataProperty(s), anns()));
            } else if (o.equals(OWL_DATATYPE.getIRI())) {
                add(df.getOWLDeclarationAxiom(df.getOWLDatatype(s), anns()));
            }
        }
    }

    static class TPDifferentFromHandler extends AbstractTriplePredicateHandler {

        TPDifferentFromHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DIFFERENT_FROM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            add(df.getOWLDifferentIndividualsAxiom(Sets.newHashSet(ind(s), ind(o)), anns()));
            consume(s, p, o);
        }
    }

    static class TPDisjointUnionHandler extends AbstractTriplePredicateHandler {

        TPDisjointUnionHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DISJOINT_UNION_OF.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return super.canHandle(s, p, o) && !isAnon(s) && isCe(s);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addCe(s, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                OWLClass cls = (OWLClass) ce(s);
                Set<OWLClassExpression> classExpressions = consumer.translatorAccessor.translateToClassExpressionSet(o);
                add(df.getOWLDisjointUnionAxiom(cls, classExpressions, anns()));
                consume(s, p, o);
            }
        }
    }

    static class TPDisjointWithHandler extends AbstractTriplePredicateHandler {

        TPDisjointWithHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DISJOINT_WITH.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addCe(s, false);
            addCe(o, false);
            // NB: In strict parsing the above type triples won't get added
            // because they aren't explicit,
            // so we need an extra check to see if there are type triples for
            // the classes
            return !eitherAnon(s, o) && bothCe(s, o);
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return super.canHandle(s, p, o) && bothCe(s, o);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            add(df.getOWLDisjointClassesAxiom(Sets.newHashSet(ce(s), ce(o)), anns()));
            consume(s, p, o);
        }
    }

    static class TPDistinctMembersHandler extends AbstractTriplePredicateHandler {

        TPDistinctMembersHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DISTINCT_MEMBERS.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            // We need all of the list triples to be loaded :(
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            Set<OWLIndividual> inds = consumer.translatorAccessor.translateToIndividualSet(o);
            add(df.getOWLDifferentIndividualsAxiom(inds, anns()));
            consume(s, p, o);
        }
    }

    static class TPEquivalentClassHandler extends AbstractTriplePredicateHandler {

        TPEquivalentClassHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_EQUIVALENT_CLASS.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return super.canHandle(s, p, o) && bothClassOrDataRange(s, o);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return !isStrict() && !eitherAnon(s, o) && bothClassOrDataRange(s, o);
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
                if (isCeLax(s) && isCeLax(o)) {
                    translateEquivalentClasses(s, p, o);
                } else if (isDrLax(s) || isDrLax(o)) {
                    translateEquivalentDataRanges(s, p, o);
                }
            }
        }

        private void translateEquivalentDataRanges(IRI s, IRI p, IRI o) {
            OWLDatatype datatype = df.getOWLDatatype(s);
            OWLDataRange dataRange = dr(o);
            OWLDatatypeDefinitionAxiom def = df.getOWLDatatypeDefinitionAxiom(datatype, dataRange, anns());
            add(def);
            consume(s, p, o);
        }

        private void translateEquivalentClasses(IRI s, IRI p, IRI o) {
            add(df.getOWLEquivalentClassesAxiom(Sets.newHashSet(ce(s), ce(o)), anns()));
            consume(s, p, o);
        }
    }

    static class TPEquivalentPropertyHandler extends AbstractTriplePredicateHandler {

        TPEquivalentPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_EQUIVALENT_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            Set<OWLAnnotation> anns = anns();
            if (isOpLax(s) && isOpLax(o)) {
                add(df.getOWLEquivalentObjectPropertiesAxiom(Sets.newHashSet(op(s), op(o)), anns));
                consume(s, p, o);
            }
            if (isDPLax(s) && isDPLax(o)) {
                add(df.getOWLEquivalentDataPropertiesAxiom(Sets.newHashSet(dp(s), dp(o)), anns));
                consume(s, p, o);
            }
            // TODO: LOG ERROR
        }
    }

    static class TPFirstLiteralHandler extends AbstractTripleHandler implements LiteralTripleHandler {

        TPFirstLiteralHandler(OWLRDFConsumer consumer) {
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
            consume(s, p, o);
        }
    }

    static class TPFirstResourceHandler extends AbstractTriplePredicateHandler {

        TPFirstResourceHandler(OWLRDFConsumer consumer) {
            super(consumer, RDF_FIRST.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addFirst(s, o);
            consume(s, p, o);
        }
    }

    static class TPHasKeyHandler extends AbstractTriplePredicateHandler {

        private final OptimisedListTranslator<OWLPropertyExpression> listTranslator;

        TPHasKeyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_HAS_KEY.getIRI());
            listTranslator = Translators.getListTranslator(consumer);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addCe(s, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isCe(s)) {
                consume(s, p, o);
                OWLClassExpression ce = ce(s);
                Set<OWLPropertyExpression> props = listTranslator.translateToSet(o);
                add(df.getOWLHasKeyAxiom(ce, props, anns()));
            }
        }
    }

    static class TPHasValueHandler extends AbstractTriplePredicateHandler {

        TPHasValueHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_HAS_VALUE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addR(s, false);
            return false;
        }
    }

    static class TPImportsHandler extends AbstractTriplePredicateHandler {

        TPImportsHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_IMPORTS.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            consumer.addOntology(s);
            consumer.addOntology(o);
            OWLImportsDeclaration id = df.getOWLImportsDeclaration(o);
            consumer.addImport(id);
            if (!consumer.getConfiguration().isIgnoredImport(o)) {
                OWLOntologyManager man = consumer.getOWLOntologyManager();
                man.makeLoadImportRequest(id, consumer.getConfiguration());
                OWLOntology io = man.getImportedOntology(id);
                if (io != null) {
                    OWLDocumentFormat importedOntologyFormat = man.getOntologyFormat(io);
                    if (importedOntologyFormat instanceof AbstractRDFPrefixDocumentFormat && io.isAnonymous()) {
                        if (consumer.getConfiguration().getMissingOntologyHeaderStrategy() == INCLUDE_GRAPH) {
                            // We should have just included the triples rather
                            // than imported them. So,
                            // we remove the imports statement, add the axioms
                            // from the imported ontology to
                            // out importing ontology and remove the imported
                            // ontology.
                            // WHO EVER THOUGHT THAT THIS WAS A GOOD IDEA?
                            man.applyChange(new RemoveImport(consumer.getOntology(), id));
                            io.importsDeclarations().forEach(d -> addImport(man, d));
                            io.annotations().forEach(ann -> addOntAnn(man, ann));
                            io.axioms().forEach(ax -> add(ax));
                            man.removeOntology(io);
                        }
                    }
                }
                consumer.importsClosureChanged();
            }
        }
    }

    /** A handler for top level intersection classes. */
    static class TPIntersectionOfHandler extends AbstractNamedEquivalentClassAxiomHandler {

        TPIntersectionOfHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_INTERSECTION_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectIntersectionOf(consumer.translatorAccessor.translateToClassExpressionSet(mainNode));
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            if (isCe(s)) {
                addCe(o, false);
            } else if (isCe(o)) {
                addCe(s, false);
            } else if (isDr(s)) {
                addDR(o, false);
            } else if (isDr(o)) {
                addDR(s, false);
            }
            return super.canHandleStreaming(s, p, o);
        }
    }

    /** owl:inverseOf is used in both property expressions AND axioms. */
    static class TPInverseOfHandler extends AbstractTriplePredicateHandler {

        private boolean axiomParsingMode = false;

        TPInverseOfHandler(OWLRDFConsumer consumer) {
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
            addOp(s, false);
            addOp(o, false);
            return false;
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return super.canHandle(s, p, o) && isOpLax(s) && isOpLax(o);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // Only do axiom translation
            if (axiomParsingMode && isOpLax(s) && isOpLax(o)) {
                add(df.getOWLInverseObjectPropertiesAxiom(op(s), op(o), anns()));
                consume(s, p, o);
            }
        }
    }

    static class TPOnClassHandler extends AbstractTriplePredicateHandler {

        TPOnClassHandler(OWLRDFConsumer consumer) {
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
            addCe(o, false);
            return false;
        }
    }

    static class TPOnDataRangeHandler extends AbstractTriplePredicateHandler {

        TPOnDataRangeHandler(OWLRDFConsumer consumer) {
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
            addDR(o, true);
            return false;
        }
    }

    static class TPOnPropertyHandler extends AbstractTriplePredicateHandler {

        TPOnPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ON_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addR(s, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {}
    }

    static class TPOneOfHandler extends AbstractNamedEquivalentClassAxiomHandler {

        TPOneOfHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ONE_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectOneOf(consumer.translatorAccessor.translateToIndividualSet(mainNode));
        }
    }

    static class TPPropertyChainAxiomHandler extends AbstractTriplePredicateHandler {

        TPPropertyChainAxiomHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_PROPERTY_CHAIN_AXIOM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addOp(o, false);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            OWLObjectPropertyExpression superProp = op(s);
            List<OWLObjectPropertyExpression> chain = consumer.translatorAccessor.translateToObjectPropertyList(o);
            consume(s, p, o);
            add(df.getOWLSubPropertyChainOfAxiom(chain, superProp, anns()));
        }
    }

    static class TPPropertyDisjointWithHandler extends AbstractTriplePredicateHandler {

        TPPropertyDisjointWithHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_PROPERTY_DISJOINT_WITH.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return super.canHandle(s, p, o) && (isOpLax(s) && isOpLax(o) || isDPLax(s) && isDPLax(o));
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isDPLax(s) && isDPLax(o)) {
                add(df.getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(dp(s), dp(o)), anns()));
                consume(s, p, o);
            }
            if (isOpLax(s) && isOpLax(o)) {
                add(df.getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(op(s), op(o)), anns()));
                consume(s, p, o);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return false;
        }
    }

    static class TPPropertyDomainHandler extends AbstractTriplePredicateHandler {

        TPPropertyDomainHandler(OWLRDFConsumer consumer) {
            super(consumer, RDFS_DOMAIN.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(s) && isCe(o)) {
                translateObjectPropertyDomain(s, p, o);
            } else if (isDataPropertyStrict(s) && isCe(o)) {
                translateDataPropertyDomain(s, p, o);
            } else if (isApLax(s) && isCe(o) && !isAnon(o)) {
                translateAnnotationPropertyDomain(s, p, o);
            } else if (!isStrict()) {
                addAp(s, false);
                translateAnnotationPropertyDomain(s, p, o);
            }
        }

        private void translateAnnotationPropertyDomain(IRI s, IRI p, IRI o) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(s);
            add(df.getOWLAnnotationPropertyDomainAxiom(prop, o, anns()));
            // TODO: Handle anonymous domain - error?
            consume(s, p, o);
        }

        private void translateDataPropertyDomain(IRI s, IRI p, IRI o) {
            add(df.getOWLDataPropertyDomainAxiom(dp(s), ce(o), anns()));
            consume(s, p, o);
        }

        private void translateObjectPropertyDomain(IRI s, IRI p, IRI o) {
            add(df.getOWLObjectPropertyDomainAxiom(op(s), ce(o), anns()));
            consume(s, p, o);
        }
    }

    static class TPPropertyRangeHandler extends AbstractTriplePredicateHandler {

        TPPropertyRangeHandler(OWLRDFConsumer consumer) {
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
                } else if (isApLax(s) && !isAnon(o)) {
                    translateAsAnnotationPropertyRange(s, p, o);
                }
            } else if (isObjectPropertyStrict(s) && consumer.isClassExpression(o)) {
                translateAsObjectPropertyRange(s, p, o);
            } else if (isDataPropertyStrict(s) && consumer.isDataRange(o)) {
                translateAsDataPropertyRange(s, p, o);
            } else if (consumer.isAnnotationProperty(s) && !consumer.isAnonymousNode(o)) {
                translateAsAnnotationPropertyRange(s, p, o);
            } else if (isAnnotationPropertyOnly(s) && !isAnon(o)) {
                translateAsAnnotationPropertyRange(s, p, o);
            } else if (isCeLax(o)) {
                addOp(s, false);
                translateAsObjectPropertyRange(s, p, o);
            } else if (isDrLax(o)) {
                addDp(s, false);
                translateAsDataPropertyRange(s, p, o);
            } else if (isOpLax(s)) {
                addOp(s, false);
                translateAsObjectPropertyRange(s, p, o);
            } else if (isDPLax(s)) {
                addDp(s, false);
                translateAsDataPropertyRange(s, p, o);
            } else {
                addAp(s, false);
                translateAsAnnotationPropertyRange(s, p, o);
            }
        }

        private void translateAsAnnotationPropertyRange(IRI s, IRI p, IRI o) {
            OWLAnnotationProperty prop = df.getOWLAnnotationProperty(s);
            add(df.getOWLAnnotationPropertyRangeAxiom(prop, o, anns()));
            consume(s, p, o);
        }

        private void translateAsDataPropertyRange(IRI s, IRI p, IRI o) {
            OWLDataPropertyExpression property = dp(s);
            OWLDataRange dataRange = dr(o);
            add(df.getOWLDataPropertyRangeAxiom(property, dataRange, anns()));
            consume(s, p, o);
        }

        private void translateAsObjectPropertyRange(IRI s, IRI p, IRI o) {
            OWLObjectPropertyExpression property = op(s);
            OWLClassExpression range = ce(o);
            add(df.getOWLObjectPropertyRangeAxiom(property, range, anns()));
            consume(s, p, o);
        }
    }

    static class TPRestHandler extends AbstractTriplePredicateHandler {

        TPRestHandler(OWLRDFConsumer consumer) {
            super(consumer, RDF_REST.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!o.equals(RDF_NIL.getIRI())) {
                consumer.addRest(s, o);
            }
            consume(s, p, o);
        }
    }

    static class TPSameAsHandler extends AbstractTriplePredicateHandler {

        TPSameAsHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_SAME_AS.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            add(df.getOWLSameIndividualAxiom(Sets.newHashSet(ind(s), ind(o)), anns()));
            consume(s, p, o);
        }
    }

    static class TPSomeValuesFromHandler extends AbstractTriplePredicateHandler {

        TPSomeValuesFromHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_SOME_VALUES_FROM.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            handleTriple(s, p, o);
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            addR(s, false);
            if (isDr(o)) {
                IRI property = getResourceObject(s, OWL_ON_PROPERTY);
                if (property != null) {
                    addDp(property, false);
                }
            }
        }
    }

    /**
     * Handles rdfs:subClassOf triples. If handling is set to strict then the
     * triple is only consumed if the s and o are typed as classes.
     */
    static class TPSubClassOfHandler extends AbstractTriplePredicateHandler {

        TPSubClassOfHandler(OWLRDFConsumer consumer) {
            super(consumer, RDFS_SUBCLASS_OF.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return super.canHandle(s, p, o) && isTyped(s, o);
        }

        private boolean isTyped(IRI s, IRI o) {
            return isCe(s) && isCe(o);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addCe(s, false);
            addCe(o, false);
            return !isStrict() && !eitherAnon(s, o);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isStrict()) {
                if (isClassExpressionStrict(s) && isClassExpressionStrict(o)) {
                    translate(s, p, o);
                }
            } else {
                if (isCeLax(s) && isCeLax(o)) {
                    translate(s, p, o);
                }
            }
        }

        private void translate(IRI s, IRI p, IRI o) {
            OWLClassExpression subClass = ce(s);
            OWLClassExpression supClass = ce(o);
            OWLAxiom ax = df.getOWLSubClassOfAxiom(subClass, supClass, anns());
            add(ax);
            consume(s, p, o);
        }
    }

    static class TPSubPropertyOfHandler extends AbstractTriplePredicateHandler {

        TPSubPropertyOfHandler(OWLRDFConsumer consumer) {
            super(consumer, RDFS_SUB_PROPERTY_OF.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            if (isOpLax(o)) {
                addOp(s, false);
            } else if (isDPLax(o)) {
                addDp(o, false);
            } else if (isApLax(o)) {
                addAp(s, false);
            } else if (isOpLax(s)) {
                addOp(o, false);
            } else if (isDPLax(s)) {
                addDp(o, false);
            } else if (isApLax(s)) {
                addAp(o, false);
            }
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // First check for o property chain
            if (!isStrict() && consumer.hasPredicate(s, OWL_PROPERTY_CHAIN.getIRI())) {
                // Property chain
                IRI chainList = getRO(s, OWL_PROPERTY_CHAIN);
                List<OWLObjectPropertyExpression> properties = consumer.translatorAccessor
                    .translateToObjectPropertyList(verifyNotNull(chainList));
                add(df.getOWLSubPropertyChainOfAxiom(properties, op(o), anns()));
                consume(s, p, o);
            } else if (!isStrict() && consumer.hasPredicate(s, RDF_FIRST.getIRI())) {
                // Legacy o property chain representation
                List<OWLObjectPropertyExpression> properties = consumer.translatorAccessor
                    .translateToObjectPropertyList(s);
                add(df.getOWLSubPropertyChainOfAxiom(properties, op(o), anns()));
                consume(s, p, o);
            } else if (isOpLax(s) && isOpLax(o)) {
                translateSubObjectProperty(s, p, o);
            } else if (isDPLax(s) && isDPLax(o)) {
                translateSubDataProperty(s, p, o);
            } else if (!isStrict()) {
                if (consumer.isObjectProperty(o)) {
                    translateSubObjectProperty(s, p, o);
                } else if (consumer.isDataProperty(o)) {
                    translateSubDataProperty(s, p, o);
                } else {
                    OWLAnnotationProperty subAnnoProp = df.getOWLAnnotationProperty(s);
                    OWLAnnotationProperty superAnnoProp = df.getOWLAnnotationProperty(o);
                    add(df.getOWLSubAnnotationPropertyOfAxiom(subAnnoProp, superAnnoProp, anns()));
                }
                consume(s, p, o);
            }
        }

        private void translateSubObjectProperty(IRI s, IRI p, IRI o) {
            // Object - o
            add(df.getOWLSubObjectPropertyOfAxiom(op(s), op(o), anns()));
            consume(s, p, o);
        }

        private void translateSubDataProperty(IRI s, IRI p, IRI o) {
            // Data - Data
            add(df.getOWLSubDataPropertyOfAxiom(dp(s), dp(o), anns()));
            consume(s, p, o);
        }
    }

    static class TPTypeHandler extends AbstractTriplePredicateHandler {

        TPTypeHandler(OWLRDFConsumer consumer) {
            super(consumer, RDF_TYPE.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            // Can handle if o isn;t anonymous and either the o
            // IRI is owl:Thing, or it is not part of the build in vocabulary
            addCe(o, false);
            if (isAnon(o)) {
                return false;
            }
            if (o.isReservedVocabulary()) {
                return o.isThing();
            }
            return true;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (BUILT_IN_VOCABULARY_IRIS.contains(o) && !o.isThing()) {
                // Can't have instance of built in vocabulary!
                // Shall we throw an exception here?
                LOGGER.info("Individual of builtin type {}", o);
            }
            add(df.getOWLClassAssertionAxiom(ce(o), ind(s), anns()));
            consume(s, p, o);
        }
    }

    static class TPUnionOfHandler extends AbstractNamedEquivalentClassAxiomHandler {

        TPUnionOfHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_UNION_OF.getIRI());
        }

        @Override
        protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
            return df.getOWLObjectUnionOf(consumer.translatorAccessor.translateToClassExpressionSet(mainNode));
        }
    }

    static class TPVersionIRIHandler extends AbstractTriplePredicateHandler {

        TPVersionIRIHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_VERSION_IRI.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            OWLOntology ontology = consumer.getOntology();
            // only setup the versionIRI if it is null before this point
            if (!ontology.getOntologyID().getVersionIRI().isPresent()) {
                Optional<IRI> ontologyIRI = ontology.getOntologyID().getOntologyIRI();
                Optional<IRI> versionIRI = optional(o);
                // If there was no ontologyIRI before this point and the s
                // of this statement was not anonymous,
                // then use the s IRI as the ontology IRI, else we keep
                // the previous definition for the ontology IRI
                if (!ontologyIRI.isPresent() && !isAnon(s)) {
                    ontologyIRI = optional(s);
                }
                OWLOntologyID ontologyID = new OWLOntologyID(ontologyIRI, versionIRI);
                consumer.setOntologyID(ontologyID);
            }
            consume(s, p, o);
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            // Always apply at the end
            return false;
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return p.equals(OWL_VERSION_IRI.getIRI());
        }
    }

    abstract static class AbstractTriplePredicateHandler extends AbstractResourceTripleHandler
        implements TriplePredicateHandler {

        private final IRI predicateIRI;

        AbstractTriplePredicateHandler(OWLRDFConsumer consumer, IRI predicateIRI) {
            super(consumer);
            this.predicateIRI = predicateIRI;
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            inferTypes(s, o);
            return p.equals(predicateIRI);
        }

        @Override
        public IRI getPredicateIRI() {
            return predicateIRI;
        }
    }

    static class TypeAllDifferentHandler extends AbstractBuiltInTypeHandler {

        TypeAllDifferentHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DIFFERENT.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return super.canHandle(s, p, o) && isResourcePresent(s, OWL_MEMBERS);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI listNode = getRO(s, OWL_MEMBERS);
            if (listNode != null) {
                Set<OWLIndividual> inds = consumer.translatorAccessor.translateToIndividualSet(listNode);
                add(df.getOWLDifferentIndividualsAxiom(inds, anns()));
                consume(s, p, o);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }
    }

    static class TypeAllDisjointClassesHandler extends AbstractBuiltInTypeHandler {

        TypeAllDisjointClassesHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DISJOINT_CLASSES.getIRI());
        }

        @Override
        public boolean canHandle(IRI s, IRI p, IRI o) {
            return super.canHandle(s, p, o) && isResourcePresent(s, OWL_MEMBERS);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI listNode = getRO(s, OWL_MEMBERS);
            if (listNode != null) {
                Set<OWLClassExpression> desc = consumer.translatorAccessor.translateToClassExpressionSet(listNode);
                add(df.getOWLDisjointClassesAxiom(desc, anns(s)));
                consume(s, p, o);
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }
    }

    static class TypeAllDisjointPropertiesHandler extends AbstractBuiltInTypeHandler {

        TypeAllDisjointPropertiesHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ALL_DISJOINT_PROPERTIES.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            IRI listNode = verifyNotNull(getRO(s, OWL_MEMBERS));
            if (isOpLax(consumer.getFirstResource(listNode, false))) {
                Set<OWLObjectPropertyExpression> props = ops(listNode);
                consumer.addAxiom(df.getOWLDisjointObjectPropertiesAxiom(props, anns(s)));
            } else {
                Set<OWLDataPropertyExpression> props = dps(listNode);
                consumer.addAxiom(df.getOWLDisjointDataPropertiesAxiom(props, anns(s)));
            }
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }
    }

    static class TypeAnnotationHandler extends AbstractBuiltInTypeHandler {

        TypeAnnotationHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATION.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addAnnotationIRI(s);
        }
    }

    static class TypeAnnotationPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeAnnotationPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ANNOTATION_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                OWLAnnotationProperty property = df.getOWLAnnotationProperty(s);
                add(df.getOWLDeclarationAxiom(property, anns()));
                consume(s, p, o);
            }
            addAp(s, true);
        }
    }

    static class TypeAsymmetricPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeAsymmetricPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ASYMMETRIC_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addOp(s, false);
            return !isAnon(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(s)) {
                add(df.getOWLAsymmetricObjectPropertyAxiom(op(s), anns()));
                consume(s, p, o);
            }
        }
    }

    static class TypeAxiomHandler extends AbstractBuiltInTypeHandler {

        TypeAxiomHandler(OWLRDFConsumer consumer) {
            this(consumer, OWL_AXIOM.getIRI());
        }

        TypeAxiomHandler(OWLRDFConsumer consumer, IRI typeIRI) {
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
                consume(s, p, o);
                Set<OWLAnnotation> annotations = consumer.translateAnnotations(s);
                consumer.addPendingAnnotations(annotations);
                if (annotatedTarget != null) {
                    consumer.handlerAccessor.handle(annotatedSource, annotatedProperty, annotatedTarget);
                } else if (annotatedTargetLiteral != null) {
                    consumer.handlerAccessor.handle(annotatedSource, annotatedProperty, annotatedTargetLiteral);
                }
                if (!annotations.isEmpty()) {
                    OWLAxiom ax = consumer.getLastAddedAxiom();
                    consumer.removeAxiom(ax.getAxiomWithoutAnnotations());
                }
            }
        }

        @SuppressWarnings("unused")
        protected OWLAxiom handleAxiomTriples(IRI subjectTriple, IRI predicateTriple, IRI objectTriple,
            Set<OWLAnnotation> annotations) {
            // Reconstitute the original triple from the reification triples
            return consumer.getLastAddedAxiom();
        }

        protected OWLAxiom handleAxiomTriples(IRI subjectTripleObject, IRI predicateTripleObject, OWLLiteral con,
            @SuppressWarnings("unused") Set<OWLAnnotation> annotations) {
            consumer.handlerAccessor.handle(subjectTripleObject, predicateTripleObject, con);
            return consumer.getLastAddedAxiom();
        }

        private OWLLiteral getTargetLiteral(IRI s) {
            OWLLiteral con = consumer.getLiteralObject(s, getTargetTriplePredicate(), true);
            if (con == null) {
                con = consumer.getLiteralObject(s, RDF_OBJECT, true);
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
        private @Nullable IRI getObjectOfTargetTriple(IRI mainNode) {
            IRI objectTripleObject = consumer.getResourceObject(mainNode, getTargetTriplePredicate(), true);
            if (objectTripleObject == null) {
                objectTripleObject = getRO(mainNode, RDF_OBJECT);
            }
            if (objectTripleObject == null) {
                objectTripleObject = getRO(mainNode, OWL_PROPERTY_CHAIN);
            }
            return objectTripleObject;
        }

        private @Nullable IRI getObjectOfPropertyTriple(IRI s) {
            IRI predicateTripleObject = consumer.getResourceObject(s, getPropertyTriplePredicate(), true);
            if (predicateTripleObject == null) {
                predicateTripleObject = getRO(s, RDF_PREDICATE);
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
        private @Nullable IRI getObjectOfSourceTriple(IRI mainNode) {
            IRI subjectTripleObject = consumer.getResourceObject(mainNode, getSourceTriplePredicate(), true);
            if (subjectTripleObject == null) {
                subjectTripleObject = getRO(mainNode, RDF_SUBJECT);
            }
            return subjectTripleObject;
        }
    }

    static class TypeClassHandler extends AbstractBuiltInTypeHandler {

        TypeClassHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                OWLClass owlClass = df.getOWLClass(s);
                add(df.getOWLDeclarationAxiom(owlClass, anns()));
            }
            addCe(s, true);
        }
    }

    static class TypeDataPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeDataPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DATA_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                OWLDataProperty owlDataProperty = df.getOWLDataProperty(s);
                add(df.getOWLDeclarationAxiom(owlDataProperty, anns()));
            }
            addDp(s, true);
        }
    }

    static class TypeDataRangeHandler extends AbstractBuiltInTypeHandler {

        TypeDataRangeHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DATA_RANGE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                consume(s, p, o);
            }
        }
    }

    static class TypeDatatypeHandler extends AbstractBuiltInTypeHandler {

        TypeDatatypeHandler(OWLRDFConsumer consumer) {
            super(consumer, RDFS_DATATYPE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                OWLDatatype dt = df.getOWLDatatype(s);
                add(df.getOWLDeclarationAxiom(dt, anns()));
            }
            addDR(s, true);
        }
    }

    static class TypeDeprecatedClassHandler extends AbstractBuiltInTypeHandler {

        TypeDeprecatedClassHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DEPRECATED_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            addCe(s, false);
            consume(s, p, o);
            add(df.getDeprecatedOWLAnnotationAssertionAxiom(s));
        }
    }

    static class TypeDeprecatedPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeDeprecatedPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_DEPRECATED_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            add(df.getDeprecatedOWLAnnotationAssertionAxiom(s));
        }
    }

    static class TypeFunctionalPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeFunctionalPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_FUNCTIONAL_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            return false;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(s)) {
                add(df.getOWLFunctionalObjectPropertyAxiom(op(s), anns()));
                consume(s, p, o);
            }
            if (isDPLax(s)) {
                add(df.getOWLFunctionalDataPropertyAxiom(dp(s), anns()));
                consume(s, p, o);
            }
        }
    }

    static class TypeInverseFunctionalPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeInverseFunctionalPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_INVERSE_FUNCTIONAL_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.handlerAccessor.handle(s, p, OWL_OBJECT_PROPERTY.getIRI());
            return !isAnon(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(s)) {
                OWLObjectPropertyExpression property = op(s);
                add(df.getOWLInverseFunctionalObjectPropertyAxiom(property, anns()));
                consume(s, p, o);
            }
        }
    }

    static class TypeIrreflexivePropertyHandler extends AbstractBuiltInTypeHandler {

        TypeIrreflexivePropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_IRREFLEXIVE_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addOp(s, false);
            return !isAnon(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(s)) {
                add(df.getOWLIrreflexiveObjectPropertyAxiom(op(s), anns()));
                consume(s, p, o);
            }
        }
    }

    static class TypeListHandler extends AbstractBuiltInTypeHandler {

        TypeListHandler(OWLRDFConsumer consumer) {
            super(consumer, RDF_LIST.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
        }
    }

    static class TypeNamedIndividualHandler extends AbstractBuiltInTypeHandler {

        TypeNamedIndividualHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_NAMED_INDIVIDUAL.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                OWLNamedIndividual individual = df.getOWLNamedIndividual(s);
                add(df.getOWLDeclarationAxiom(individual, anns()));
            }
            consumer.addOWLNamedIndividual(s, true);
        }
    }

    static class TypeNegativeDataPropertyAssertionHandler extends AbstractBuiltInTypeHandler {

        TypeNegativeDataPropertyAssertionHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_NEGATIVE_DATA_PROPERTY_ASSERTION.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI source = source(s);
            IRI property = property(s);
            OWLLiteral target = target(s);
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLDataPropertyExpression prop = dp(property);
            consume(s, p, o);
            consumer.translateAnnotations(s);
            add(df.getOWLNegativeDataPropertyAssertionAxiom(prop, sourceInd, target, anns()));
        }

        OWLLiteral target(IRI s) {
            OWLLiteral target = consumer.getLiteralObject(s, OWL_TARGET_VALUE.getIRI(), true);
            if (target == null) {
                target = consumer.getLiteralObject(s, OWL_OBJECT.getIRI(), true);
            }
            if (target == null) {
                target = consumer.getLiteralObject(s, RDF_OBJECT, true);
            }
            return verifyNotNull(target);
        }

        IRI property(IRI s) {
            IRI property = getRO(s, OWL_ASSERTION_PROPERTY);
            if (property == null) {
                property = getRO(s, OWL_PREDICATE);
            }
            if (property == null) {
                property = getRO(s, RDF_PREDICATE);
            }
            return verifyNotNull(property);
        }

        IRI source(IRI s) {
            IRI source = getRO(s, OWL_SOURCE_INDIVIDUAL);
            if (source == null) {
                source = getRO(s, OWL_SUBJECT);
            }
            if (source == null) {
                source = getRO(s, RDF_SUBJECT);
            }
            return verifyNotNull(source);
        }
    }

    static class TypeNegativePropertyAssertionHandler extends AbstractBuiltInTypeHandler {

        TypeNegativePropertyAssertionHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI source = source(s);
            IRI property = property(s);
            Object target = target(s);
            Set<OWLAnnotation> annos = consumer.translateAnnotations(s);
            if (target instanceof OWLLiteral && (!isStrict() || isDPLax(property))) {
                translateNegativeDataPropertyAssertion(s, p, o, source, property, (OWLLiteral) target, annos);
            } else if (target instanceof IRI && (!isStrict() || isOpLax(property))) {
                translateNegativeObjectPropertyAssertion(s, p, o, source, property, (IRI) target, annos);
            }
            // TODO LOG ERROR
        }

        Object target(IRI s) {
            Object target = getRO(s, OWL_TARGET_INDIVIDUAL);
            if (target == null) {
                target = consumer.getLiteralObject(s, OWL_TARGET_VALUE.getIRI(), true);
            }
            if (target == null) {
                target = getRO(s, RDF_OBJECT);
            }
            if (target == null) {
                target = consumer.getLiteralObject(s, RDF_OBJECT, true);
            }
            return verifyNotNull(target);
        }

        IRI property(IRI s) {
            IRI property = getRO(s, OWL_ASSERTION_PROPERTY);
            if (property == null) {
                property = getRO(s, RDF_PREDICATE);
            }
            return verifyNotNull(property);
        }

        IRI source(IRI s) {
            IRI source = getRO(s, OWL_SOURCE_INDIVIDUAL);
            if (source == null) {
                source = getRO(s, RDF_SUBJECT);
            }
            return verifyNotNull(source);
        }

        private void translateNegativeObjectPropertyAssertion(IRI s, IRI p, IRI o, IRI source, IRI property, IRI target,
            Set<OWLAnnotation> annos) {
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLObjectPropertyExpression prop = op(property);
            OWLIndividual targetInd = consumer.getOWLIndividual(target);
            consume(s, p, o);
            add(df.getOWLNegativeObjectPropertyAssertionAxiom(prop, sourceInd, targetInd, annos));
        }

        private void translateNegativeDataPropertyAssertion(IRI s, IRI p, IRI o, IRI source, IRI property,
            OWLLiteral target, Set<OWLAnnotation> annos) {
            OWLIndividual sourceInd = consumer.getOWLIndividual(source);
            OWLDataPropertyExpression prop = dp(property);
            consume(s, p, o);
            add(df.getOWLNegativeDataPropertyAssertionAxiom(prop, sourceInd, target, annos));
        }
    }

    static class TypeObjectPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeObjectPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_OBJECT_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (!isAnon(s)) {
                OWLObjectProperty op = df.getOWLObjectProperty(s);
                add(df.getOWLDeclarationAxiom(op, anns()));
            }
            addOp(s, true);
        }
    }

    static class TypeOntologyHandler extends AbstractBuiltInTypeHandler {

        TypeOntologyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ONTOLOGY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            if (!isAnon(s) && consumer.getOntologies().isEmpty()) {
                // Set IRI if it is not null before this point, and make sure to
                // preserve the version IRI if it also existed before this point
                OWLOntology ont = consumer.getOntology();
                if (!ont.getOntologyID().getOntologyIRI().isPresent()) {
                    OWLOntologyID id = new OWLOntologyID(optional(s), ont.getOntologyID().getVersionIRI());
                    ont.applyChange(new SetOntologyID(ont, id));
                }
            }
            consumer.addOntology(s);
        }
    }

    static class TypeOntologyPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeOntologyPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_ONTOLOGY_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            // Add a type triple for an annotation property (Table 6 in Mapping
            // to RDF Graph Spec)
            consumer.handlerAccessor.handle(s, p, OWL_ANNOTATION_PROPERTY.getIRI());
        }
    }

    static class TypePropertyHandler extends AbstractBuiltInTypeHandler {

        TypePropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, RDF_PROPERTY.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // We need to consume this triple
            consume(s, p, o);
            LOGGER.info("Usage of rdf vocabulary: {} -> {} -> {}", s, p, o);
        }
    }

    static class TypeRDFSClassHandler extends AbstractBuiltInTypeHandler {

        TypeRDFSClassHandler(OWLRDFConsumer consumer) {
            super(consumer, RDFS_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // TODO: Change to rdfs:Class? (See table 5 in the spec)
            addCe(s, false);
            consume(s, p, o);
            if (!isStrict()) {
                consumer.handlerAccessor.handle(s, p, OWL_CLASS.getIRI());
            }
        }
    }

    static class TypeReflexivePropertyHandler extends AbstractBuiltInTypeHandler {

        TypeReflexivePropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_REFLEXIVE_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            addOp(s, false);
            return !isAnon(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(s)) {
                add(df.getOWLReflexiveObjectPropertyAxiom(op(s), anns()));
                consume(s, p, o);
            }
        }
    }

    static class TypeRestrictionHandler extends AbstractBuiltInTypeHandler {

        TypeRestrictionHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_RESTRICTION.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            addR(s, true);
            addCe(s, false);
        }
    }

    static class TypeSWRLAtomListHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLAtomListHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.ATOM_LIST.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
        }
    }

    static class TypeSWRLBuiltInAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLBuiltInAtomHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.BUILT_IN_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLBuiltInAtom(s);
            consume(s, p, o);
        }
    }

    static class TypeSWRLBuiltInHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLBuiltInHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.BUILT_IN_CLASS.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            // Just consume - I don't care about this
            consume(s, p, o);
        }
    }

    static class TypeSWRLClassAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLClassAtomHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.CLASS_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLClassAtom(s);
            consume(s, p, o);
        }
    }

    static class TypeSWRLDataRangeAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLDataRangeAtomHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DATA_RANGE_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLDataRangeAtom(s);
            consume(s, p, o);
        }
    }

    static class TypeSWRLDataValuedPropertyAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLDataValuedPropertyAtomHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DATAVALUED_PROPERTY_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            consumer.addSWRLDataPropertyAtom(s);
        }
    }

    static class TypeSWRLDifferentIndividualsAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLDifferentIndividualsAtomHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.DIFFERENT_INDIVIDUALS_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLDifferentFromAtom(s);
            consume(s, p, o);
        }
    }

    static class TypeSWRLImpHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLImpHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.IMP.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            IRI remapIRI = consumer.remapIRI(s);
            consume(remapIRI, p, o);
            consumer.addSWRLRule(remapIRI);
        }
    }

    static class TypeSWRLIndividualPropertyAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLIndividualPropertyAtomHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.INDIVIDUAL_PROPERTY_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            consumer.addSWRLIndividualPropertyAtom(s);
        }
    }

    static class TypeSWRLSameIndividualAtomHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLSameIndividualAtomHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.SAME_INDIVIDUAL_ATOM.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLSameAsAtom(s);
            consume(s, p, o);
        }
    }

    static class TypeSWRLVariableHandler extends AbstractBuiltInTypeHandler {

        TypeSWRLVariableHandler(OWLRDFConsumer consumer) {
            super(consumer, SWRLVocabulary.VARIABLE.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consumer.addSWRLVariable(s);
            consume(s, p, o);
        }
    }

    static class TypeSelfRestrictionHandler extends AbstractBuiltInTypeHandler {

        TypeSelfRestrictionHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_SELF_RESTRICTION.getIRI());
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            consume(s, p, o);
            addR(s, false);
            // Patch to new OWL syntax
            consumer.addTriple(s, OWL_HAS_SELF.getIRI(), df.getOWLLiteral(true));
        }
    }

    static class TypeSymmetricPropertyHandler extends AbstractBuiltInTypeHandler {

        TypeSymmetricPropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_SYMMETRIC_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            boolean isIRI = !isAnon(s);
            if (isIRI) {
                consumer.handlerAccessor.handle(s, p, OWL_OBJECT_PROPERTY.getIRI());
            }
            addOp(s, false);
            return isIRI;
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            if (isOpLax(s)) {
                add(df.getOWLSymmetricObjectPropertyAxiom(op(s), anns()));
                consume(s, p, o);
            }
        }
    }

    static class TypeTransitivePropertyHandler extends AbstractBuiltInTypeHandler {

        TypeTransitivePropertyHandler(OWLRDFConsumer consumer) {
            super(consumer, OWL_TRANSITIVE_PROPERTY.getIRI());
        }

        @Override
        public boolean canHandleStreaming(IRI s, IRI p, IRI o) {
            consumer.handlerAccessor.handle(s, p, OWL_OBJECT_PROPERTY.getIRI());
            return !isAnon(s);
        }

        @Override
        public void handleTriple(IRI s, IRI p, IRI o) {
            add(df.getOWLTransitiveObjectPropertyAxiom(op(s), anns()));
            consume(s, p, o);
        }
    }
}
