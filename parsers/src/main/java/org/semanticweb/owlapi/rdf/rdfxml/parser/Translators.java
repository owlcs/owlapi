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

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class Translators {

    protected static final Logger logger = LoggerFactory
            .getLogger(Translators.class);

    static OptimisedListTranslator<OWLPropertyExpression> getListTranslator(
            OWLRDFConsumer consumer) {
        return new OptimisedListTranslator<OWLPropertyExpression>(consumer,
                new HasKeyListItemTranslator(consumer));
    }

    static class TranslatorAccessor {

        /**
         * A translator for lists of class expressions (such lists are used in
         * intersections, unions etc.)
         */
        private OptimisedListTranslator<OWLClassExpression> classExpressionListTranslator;
        /** The class expression translators. */
        private List<ClassExpressionTranslator> classExpressionTranslators = new ArrayList<ClassExpressionTranslator>();
        /**
         * A translator for individual lists (such lists are used in object
         * oneOf constructs)
         */
        private OptimisedListTranslator<OWLIndividual> individualListTranslator;
        /** The object property list translator. */
        private OptimisedListTranslator<OWLObjectPropertyExpression> objectPropertyListTranslator;
        /** The constant list translator. */
        private OptimisedListTranslator<OWLLiteral> constantListTranslator;
        /** The data property list translator. */
        private OptimisedListTranslator<OWLDataPropertyExpression> dataPropertyListTranslator;
        /** The data range list translator. */
        private OptimisedListTranslator<OWLDataRange> dataRangeListTranslator;
        /** The face restriction list translator. */
        private OptimisedListTranslator<OWLFacetRestriction> faceRestrictionListTranslator;
        private OWLRDFConsumer consumer;

        public TranslatorAccessor(OWLRDFConsumer r) {
            consumer = r;
            classExpressionListTranslator = new OptimisedListTranslator<OWLClassExpression>(
                    r, new ClassExpressionListItemTranslator(r, this));
            individualListTranslator = new OptimisedListTranslator<OWLIndividual>(
                    r, new IndividualListItemTranslator(r));
            constantListTranslator = new OptimisedListTranslator<OWLLiteral>(r,
                    new TypedConstantListItemTranslator());
            objectPropertyListTranslator = new OptimisedListTranslator<OWLObjectPropertyExpression>(
                    r, new ObjectPropertyListItemTranslator(r));
            dataPropertyListTranslator = new OptimisedListTranslator<OWLDataPropertyExpression>(
                    r, new DataPropertyListItemTranslator(r));
            dataRangeListTranslator = new OptimisedListTranslator<OWLDataRange>(
                    r, new DataRangeListItemTranslator(r));
            faceRestrictionListTranslator = new OptimisedListTranslator<OWLFacetRestriction>(
                    r, new OWLFacetRestrictionListItemTranslator(r));
            classExpressionTranslators.add(new NamedClassTranslator(r, this));
            classExpressionTranslators.add(new ObjectIntersectionOfTranslator(
                    r, this));
            classExpressionTranslators
                    .add(new ObjectUnionOfTranslator(r, this));
            classExpressionTranslators.add(new ObjectComplementOfTranslator(r,
                    this));
            classExpressionTranslators.add(new ObjectOneOfTranslator(r, this));
            classExpressionTranslators.add(new ObjectSomeValuesFromTranslator(
                    r, this));
            classExpressionTranslators.add(new ObjectAllValuesFromTranslator(r,
                    this));
            classExpressionTranslators
                    .add(new ObjectHasValueTranslator(r, this));
            classExpressionTranslators
                    .add(new ObjectHasSelfTranslator(r, this));
            classExpressionTranslators
                    .add(new ObjectMinQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators
                    .add(new ObjectMaxQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators
                    .add(new ObjectQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new ObjectMinCardinalityTranslator(
                    r, this));
            classExpressionTranslators.add(new ObjectMaxCardinalityTranslator(
                    r, this));
            classExpressionTranslators.add(new ObjectCardinalityTranslator(r,
                    this));
            classExpressionTranslators.add(new DataSomeValuesFromTranslator(r,
                    this));
            classExpressionTranslators.add(new DataAllValuesFromTranslator(r,
                    this));
            classExpressionTranslators.add(new DataHasValueTranslator(r, this));
            classExpressionTranslators
                    .add(new DataMinQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators
                    .add(new DataMaxQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators
                    .add(new DataQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new DataMinCardinalityTranslator(r,
                    this));
            classExpressionTranslators.add(new DataMaxCardinalityTranslator(r,
                    this));
            classExpressionTranslators.add(new DataCardinalityTranslator(r,
                    this));
        }

        protected Set<OWLClassExpression> translateToClassExpressionSet(
                IRI mainNode) {
            return classExpressionListTranslator.translateToSet(mainNode);
        }

        private OWLClassExpression
                translateClassExpressionInternal(IRI mainNode) {
            // Some optimisations...
            // We either have a class or a restriction
            Mode mode = consumer.getConfiguration().isStrict() ? Mode.STRICT
                    : Mode.LAX;
            for (ClassExpressionTranslator translator : classExpressionTranslators) {
                if (translator.matches(mainNode, mode)) {
                    return translator.translate(mainNode);
                }
            }
            if (!consumer.isAnonymousNode(mainNode)) {
                return consumer.getDataFactory().getOWLClass(mainNode);
            } else {
                return consumer.generateAndLogParseError(EntityType.CLASS,
                        mainNode);
            }
        }

        public void consumeSWRLRules(Set<IRI> swrlRules) {
            SWRLRuleTranslator translator = new SWRLRuleTranslator(consumer,
                    this);
            for (IRI ruleIRI : swrlRules) {
                translator.translateRule(ruleIRI);
            }
        }

        public OWLClassExpression getClassExpressionIfTranslated(IRI mainNode) {
            return translatedClassExpression.get(mainNode);
        }

        public void cleanup() {
            translatedClassExpression.clear();
        }

        protected List<OWLObjectPropertyExpression>
                translateToObjectPropertyList(IRI mainNode) {
            return objectPropertyListTranslator.translateList(mainNode);
        }

        protected List<OWLDataPropertyExpression> translateToDataPropertyList(
                IRI mainNode) {
            return dataPropertyListTranslator.translateList(mainNode);
        }

        protected Set<OWLLiteral> translateToConstantSet(IRI mainNode) {
            return constantListTranslator.translateToSet(mainNode);
        }

        protected Set<OWLIndividual> translateToIndividualSet(IRI mainNode) {
            return individualListTranslator.translateToSet(mainNode);
        }

        protected Set<OWLDataRange> translateToDataRangeSet(IRI mainNode) {
            return dataRangeListTranslator.translateToSet(mainNode);
        }

        protected Set<OWLFacetRestriction> translateToFacetRestrictionSet(
                IRI mainNode) {
            return faceRestrictionListTranslator.translateToSet(mainNode);
        }

        private Map<IRI, OWLClassExpression> translatedClassExpression = new HashMap<IRI, OWLClassExpression>();

        protected OWLClassExpression translateClassExpression(IRI mainNode) {
            OWLClassExpression ce = translatedClassExpression.get(mainNode);
            if (ce == null) {
                ce = translateClassExpressionInternal(mainNode);
                translatedClassExpression.put(mainNode, ce);
            }
            return ce;
        }
    }

    static abstract class AbstractClassExpressionTranslator implements
            ClassExpressionTranslator {

        private OWLRDFConsumer consumer;
        private ClassExpressionMatcher classExpressionMatcher = new ClassExpressionMatcher();
        private DataRangeMatcher dataRangeMatcher = new DataRangeMatcher();
        private IndividualMatcher individualMatcher = new IndividualMatcher();
        protected TranslatorAccessor accessor;

        protected AbstractClassExpressionTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            this.consumer = consumer;
            this.accessor = accessor;
        }

        @Override
        public boolean matches(IRI mainNode, Mode mode) {
            if (mode.equals(Mode.LAX)) {
                return matchesLax(mainNode);
            } else {
                return matchesStrict(mainNode);
            }
        }

        protected OWLRDFConsumer getConsumer() {
            return consumer;
        }

        protected OWLDataFactory getDataFactory() {
            return consumer.getDataFactory();
        }

        protected boolean isAnonymous(IRI node) {
            return consumer.isAnonymousNode(node);
        }

        protected boolean isResourcePresent(IRI mainNode,
                OWLRDFVocabulary predicate) {
            return consumer.getResourceObject(mainNode, predicate, false) != null;
        }

        protected boolean isLiteralPresent(IRI mainNode,
                OWLRDFVocabulary predicate) {
            return consumer.getLiteralObject(mainNode, predicate, false) != null;
        }

        protected boolean isRestrictionStrict(IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isRestrictionLax(IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isNonNegativeIntegerStrict(IRI mainNode,
                OWLRDFVocabulary predicate) {
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

        protected boolean isNonNegativeIntegerLax(IRI mainNode,
                OWLRDFVocabulary predicate) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate,
                    false);
            if (literal == null) {
                return false;
            }
            return OWL2Datatype.XSD_INTEGER.isInLexicalSpace(literal
                    .getLiteral().trim());
        }

        protected int
                translateInteger(IRI mainNode, OWLRDFVocabulary predicate) {
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

        protected boolean isClassExpressionStrict(IRI node) {
            return consumer.isClassExpression(node)
                    && !consumer.isDataRange(node);
        }

        protected boolean isClassExpressionStrict(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isClassExpressionStrict(object);
        }

        protected boolean isClassExpressionLax(IRI mainNode) {
            return consumer.isClassExpression(mainNode)
                    || consumer.isParsedAllTriples()
                    && !consumer.isDataRange(mainNode);
        }

        protected boolean isClassExpressionLax(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isClassExpressionLax(object);
        }

        protected boolean isObjectPropertyStrict(IRI node) {
            return consumer.isObjectPropertyOnly(node);
        }

        protected boolean isObjectPropertyStrict(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isObjectPropertyStrict(object);
        }

        protected boolean isObjectPropertyLax(IRI node) {
            return consumer.isObjectProperty(node);
        }

        protected boolean isObjectPropertyLax(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isObjectPropertyLax(object);
        }

        protected boolean isDataPropertyStrict(IRI node) {
            return consumer.isDataPropertyOnly(node);
        }

        protected boolean isDataPropertyStrict(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataPropertyStrict(object);
        }

        protected boolean isDataPropertyLax(IRI node) {
            return consumer.isDataProperty(node);
        }

        protected boolean isDataPropertyLax(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataPropertyLax(object);
        }

        protected boolean isDataRangeStrict(IRI node) {
            return consumer.isDataRange(node)
                    && !consumer.isClassExpression(node);
        }

        protected boolean isDataRangeStrict(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return isDataRangeStrict(object);
        }

        protected boolean isDataRangeLax(IRI node) {
            return consumer.isParsedAllTriples() && consumer.isDataRange(node);
        }

        protected boolean isDataRangeLax(IRI mainNode,
                OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataRangeLax(object);
        }

        protected boolean
                isClassExpressionListStrict(IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, classExpressionMatcher,
                    minSize);
        }

        protected boolean isDataRangeListStrict(IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, dataRangeMatcher, minSize);
        }

        protected boolean isIndividualListStrict(IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, individualMatcher, minSize);
        }

        protected boolean isResourceListStrict(IRI mainNode,
                TypeMatcher typeMatcher, int minSize) {
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

        private interface TypeMatcher {

            boolean isTypeStrict(IRI node);
        }

        private class ClassExpressionMatcher implements TypeMatcher {

            public ClassExpressionMatcher() {}

            @Override
            public boolean isTypeStrict(IRI node) {
                return isClassExpressionStrict(node);
            }
        }

        private class DataRangeMatcher implements TypeMatcher {

            public DataRangeMatcher() {}

            @Override
            public boolean isTypeStrict(IRI node) {
                return isDataRangeStrict(node);
            }
        }

        private class IndividualMatcher implements TypeMatcher {

            public IndividualMatcher() {}

            @Override
            public boolean isTypeStrict(IRI node) {
                return true;
            }
        }
    }

    static class ClassExpressionListItemTranslator implements
            ListItemTranslator<OWLClassExpression> {

        private OWLRDFConsumer consumer;
        protected TranslatorAccessor accessor;

        public ClassExpressionListItemTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            this.consumer = consumer;
            this.accessor = accessor;
        }

        @Override
        public OWLClassExpression translate(IRI iri) {
            consumer.addClassExpression(iri, false);
            return accessor.translateClassExpression(iri);
        }

        @Override
        public OWLClassExpression translate(OWLLiteral firstObject) {
            return consumer.getDataFactory().getOWLThing();
        }
    }

    /**
     * Give a node in an RDF graph, which represents the main node of an OWL
     * class expression, the {@code ClassExpressionTranslator} consumes the
     * triples that represent the class expression, and translates the triples
     * to the appropriate OWL API {@code OWLClassExpression} object.
     */
    public interface ClassExpressionTranslator {

        /**
         * @param mainNode
         *        mainNode
         * @param mode
         *        mode
         * @return true if parameter matches
         */
        boolean matches(IRI mainNode, Mode mode);

        /**
         * @param mainNode
         *        mainNode
         * @return true if parameter matches strictly
         */
        boolean matchesStrict(IRI mainNode);

        /**
         * @param mainNode
         *        mainNode
         * @return true if parameter matches in lax mode
         */
        boolean matchesLax(IRI mainNode);

        /**
         * Translates the specified main node into an {@code OWLClassExpression}
         * . All triples used in the translation are consumed.
         * 
         * @param mainNode
         *        The main node of the set of triples that represent the class
         *        expression.
         * @return The class expression that represents the translation.
         */
        OWLClassExpression translate(IRI mainNode);
    }

    static class DataAllValuesFromTranslator extends
            AbstractClassExpressionTranslator {

        public DataAllValuesFromTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isDataRangeLax(mainNode, OWL_ALL_VALUES_FROM)
                    && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isDataPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isResourcePresent(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public OWLDataAllValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerMainNode = getConsumer().getResourceObject(mainNode,
                    OWL_ALL_VALUES_FROM, true);
            OWLDataRange filler = getConsumer().translateDataRange(
                    fillerMainNode);
            return getDataFactory().getOWLDataAllValuesFrom(property, filler);
        }
    }

    static class DataCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public DataCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode, OWL_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLDataExactCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_DATA_RANGE, true);
            if (fillerIRI != null
                    && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLDataRange filler = getConsumer().translateDataRange(
                        fillerIRI);
                return getDataFactory().getOWLDataExactCardinality(cardi,
                        property, filler);
            } else {
                return getDataFactory().getOWLDataExactCardinality(cardi,
                        property);
            }
        }
    }

    static class DataHasValueTranslator extends
            AbstractClassExpressionTranslator {

        public DataHasValueTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isLiteralPresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isLiteralPresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public OWLDataHasValue translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            OWLLiteral lit = getConsumer().getLiteralObject(mainNode,
                    OWL_HAS_VALUE, true);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            return getDataFactory().getOWLDataHasValue(property, lit);
        }
    }

    static class DataMaxCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public DataMaxCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode, OWL_MAX_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MAX_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLDataMaxCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MAX_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_DATA_RANGE, true);
            if (fillerIRI != null
                    && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLDataRange filler = getConsumer().translateDataRange(
                        fillerIRI);
                return getDataFactory().getOWLDataMaxCardinality(cardi,
                        property, filler);
            } else {
                return getDataFactory().getOWLDataMaxCardinality(cardi,
                        property);
            }
        }
    }

    static class DataMaxQualifiedCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public DataMaxQualifiedCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode,
                            OWL_MAX_QUALIFIED_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode,
                    OWL_MAX_QUALIFIED_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeLax(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public OWLDataMaxCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode,
                    OWL_MAX_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_DATA_RANGE, true);
            OWLDataRange filler = getConsumer().translateDataRange(fillerIRI);
            return getDataFactory().getOWLDataMaxCardinality(cardi, property,
                    filler);
        }
    }

    static class DataMinCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public DataMinCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode, OWL_MIN_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MIN_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLDataMinCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MIN_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_DATA_RANGE, true);
            if (fillerIRI != null
                    && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLDataRange filler = getConsumer().translateDataRange(
                        fillerIRI);
                return getDataFactory().getOWLDataMinCardinality(cardi,
                        property, filler);
            } else {
                return getDataFactory().getOWLDataMinCardinality(cardi,
                        property);
            }
        }
    }

    static class DataMinQualifiedCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public DataMinQualifiedCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode,
                            OWL_MIN_QUALIFIED_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode,
                    OWL_MIN_QUALIFIED_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeLax(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public OWLDataMinCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode,
                    OWL_MIN_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_DATA_RANGE, true);
            OWLDataRange filler = getConsumer().translateDataRange(fillerIRI);
            return getDataFactory().getOWLDataMinCardinality(cardi, property,
                    filler);
        }
    }

    static class DataPropertyListItemTranslator implements
            ListItemTranslator<OWLDataPropertyExpression> {

        private OWLRDFConsumer consumer;

        public DataPropertyListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public OWLDataPropertyExpression translate(IRI firstObject) {
            consumer.addDataProperty(firstObject, false);
            return consumer.getOWLDataProperty(firstObject);
        }

        @Override
        public OWLDataPropertyExpression translate(OWLLiteral firstObject) {
            return null;
        }
    }

    static class DataQualifiedCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public DataQualifiedCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode,
                            OWL_QUALIFIED_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_QUALIFIED_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeLax(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public OWLDataExactCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_DATA_RANGE, true);
            OWLDataRange filler = getConsumer().translateDataRange(fillerIRI);
            return getDataFactory().getOWLDataExactCardinality(cardi, property,
                    filler);
        }
    }

    static class DataRangeListItemTranslator implements
            ListItemTranslator<OWLDataRange> {

        private OWLRDFConsumer consumer;

        public DataRangeListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public OWLDataRange translate(OWLLiteral firstObject) {
            return null;
        }

        @Override
        public OWLDataRange translate(IRI firstObject) {
            return consumer.translateDataRange(firstObject);
        }
    }

    static class DataSomeValuesFromTranslator extends
            AbstractClassExpressionTranslator {

        public DataSomeValuesFromTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isDataRangeLax(mainNode, OWL_SOME_VALUES_FROM)
                    && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isDataPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isResourcePresent(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public OWLDataSomeValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(propertyIRI);
            IRI fillerMainNode = getConsumer().getResourceObject(mainNode,
                    OWL_SOME_VALUES_FROM, true);
            OWLDataRange filler = getConsumer().translateDataRange(
                    fillerMainNode);
            return getDataFactory().getOWLDataSomeValuesFrom(property, filler);
        }
    }

    static class HasKeyListItemTranslator implements
            ListItemTranslator<OWLPropertyExpression> {

        private OWLRDFConsumer consumer;

        public HasKeyListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public OWLPropertyExpression translate(OWLLiteral firstObject) {
            return null;
        }

        @Override
        public OWLPropertyExpression translate(IRI firstObject) {
            if (consumer.isObjectPropertyOnly(firstObject)) {
                return consumer.getDataFactory().getOWLObjectProperty(
                        firstObject);
            }
            if (consumer.isDataPropertyOnly(firstObject)) {
                return consumer.getDataFactory()
                        .getOWLDataProperty(firstObject);
            }
            // If neither condition was true, the property has been illegally
            // punned, or is untyped
            // use the first translation available, since there is no way to
            // know which is correct
            OWLPropertyExpression property = null;
            if (consumer.isObjectProperty(firstObject)) {
                logger.warn(
                        "Property {} has been punned illegally: found declaration as OWLObjectProperty",
                        firstObject);
                property = consumer.getDataFactory().getOWLObjectProperty(
                        firstObject);
            }
            if (consumer.isDataProperty(firstObject)) {
                logger.warn(
                        "Property {} has been punned illegally: found declaration as OWLDataProperty",
                        firstObject);
                if (property == null) {
                    property = consumer.getDataFactory().getOWLDataProperty(
                            firstObject);
                }
            }
            if (consumer.isAnnotationProperty(firstObject)) {
                logger.warn(
                        "Property {} has been punned illegally: found declaration as OWLAnnotationProperty",
                        firstObject);
                if (property == null) {
                    property = consumer.getDataFactory()
                            .getOWLAnnotationProperty(firstObject);
                }
            }
            // if there is no declaration for the property at this point, warn
            // and consider it a datatype property.
            // This matches existing behaviour.
            if (property == null) {
                logger.warn(
                        "Property {} is undeclared at this point in parsing: typing as OWLDataProperty",
                        firstObject);
                property = consumer.getDataFactory().getOWLDataProperty(
                        firstObject);
            }
            return property;
        }
    }

    static class IndividualListItemTranslator implements
            ListItemTranslator<OWLIndividual> {

        private OWLRDFConsumer consumer;

        public IndividualListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public OWLIndividual translate(IRI IRI) {
            return consumer.translateIndividual(IRI);
        }

        @Override
        public OWLIndividual translate(OWLLiteral firstObject) {
            logger.info("Cannot translate list item to individual, because rdf:first triple is a literal triple");
            return null;
        }
    }

    /**
     * Translates and consumes an item in an RDF list.
     * 
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     * @param <O>
     *        type
     */
    public interface ListItemTranslator<O extends OWLObject> {

        /**
         * The rdf:first triple that represents the item to be translated. This
         * triple will point to something like a class expression, individual.
         * 
         * @param firstObject
         *        The rdf:first triple that points to the item to be translated.
         * @return The translated item.
         */
        O translate(IRI firstObject);

        /**
         * @param firstObject
         *        firstObject
         * @return translated item
         */
        O translate(OWLLiteral firstObject);
    }

    static class NamedClassTranslator extends AbstractClassExpressionTranslator {

        public NamedClassTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return !isAnonymous(mainNode) && isClassExpressionStrict(mainNode);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return !isAnonymous(mainNode);
        }

        /**
         * Translates the specified main node into an {@code OWLClassExpression}
         * . All triples used in the translation are consumed.
         * 
         * @param mainNode
         *        The main node of the set of triples that represent the class
         *        expression.
         * @return The class expression that represents the translation.
         */
        @Override
        public OWLClass translate(IRI mainNode) {
            return getConsumer().getOWLClass(mainNode);
        }
    }

    static class OWLFacetRestrictionListItemTranslator implements
            ListItemTranslator<OWLFacetRestriction> {

        private OWLRDFConsumer consumer;

        public OWLFacetRestrictionListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public OWLFacetRestriction translate(OWLLiteral firstObject) {
            return null;
        }

        @Override
        public OWLFacetRestriction translate(IRI firstObject) {
            for (OWLFacet facet : OWLFacet.values()) {
                OWLLiteral lit = consumer.getLiteralObject(firstObject,
                        facet.getIRI(), true);
                if (lit != null) {
                    return consumer.getDataFactory().getOWLFacetRestriction(
                            facet, lit);
                }
            }
            return null;
        }
    }

    static class OWLObjectPropertyExpressionListItemTranslator implements
            ListItemTranslator<OWLObjectPropertyExpression> {

        private OWLRDFConsumer consumer;

        public OWLObjectPropertyExpressionListItemTranslator(
                OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public OWLObjectPropertyExpression translate(IRI IRI) {
            return consumer.translateObjectPropertyExpression(IRI);
        }

        @Override
        public OWLObjectPropertyExpression translate(OWLLiteral firstObject) {
            logger.info("Cannot translate list item as an object property, because rdf:first triple is a literal triple");
            return null;
        }
    }

    static class ObjectAllValuesFromTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectAllValuesFromTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isClassExpressionLax(mainNode, OWL_ALL_VALUES_FROM)
                    && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isObjectPropertyLax(mainNode)
                    && isResourcePresent(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public OWLObjectAllValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerMainNode = getConsumer().getResourceObject(mainNode,
                    OWL_ALL_VALUES_FROM, true);
            OWLClassExpression filler = accessor
                    .translateClassExpression(fillerMainNode);
            return getDataFactory().getOWLObjectAllValuesFrom(property, filler);
        }
    }

    static class ObjectCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode, OWL_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLObjectExactCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_CLASS, true);
            if (fillerIRI != null
                    && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLClassExpression filler = accessor
                        .translateClassExpression(fillerIRI);
                return getDataFactory().getOWLObjectExactCardinality(cardi,
                        property, filler);
            } else {
                return getDataFactory().getOWLObjectExactCardinality(cardi,
                        property);
            }
        }
    }

    /**
     * Translates a set of triples that represent an {@code OWLComplementOf}
     * class expression.
     * 
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectComplementOfTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectComplementOfTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI complementOfIRI = getConsumer().getResourceObject(mainNode,
                    OWL_COMPLEMENT_OF, false);
            return isClassExpressionStrict(mainNode)
                    && isClassExpressionStrict(complementOfIRI);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_COMPLEMENT_OF)
                    && isClassExpressionLax(mainNode);
        }

        @Override
        public OWLObjectComplementOf translate(IRI mainNode) {
            IRI complementOfObject = getConsumer().getResourceObject(mainNode,
                    OWL_COMPLEMENT_OF, true);
            OWLClassExpression operand = accessor
                    .translateClassExpression(complementOfObject);
            return getDataFactory().getOWLObjectComplementOf(operand);
        }
    }

    static class ObjectHasSelfTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectHasSelfTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            OWLLiteral literal = getConsumer().getLiteralObject(mainNode,
                    OWL_HAS_SELF.getIRI(), false);
            return literal != null && isStrictBooleanTrueLiteral(literal)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        private boolean isStrictBooleanTrueLiteral(OWLLiteral literal) {
            return OWL2Datatype.XSD_BOOLEAN.getIRI().equals(
                    literal.getDatatype().getIRI())
                    && literal.getLiteral().toLowerCase(Locale.ENGLISH)
                            .equals("true");
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    && isLiteralPresent(mainNode, OWL_HAS_SELF);
        }

        @Override
        public OWLObjectHasSelf translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            getConsumer().getLiteralObject(mainNode, OWL_HAS_SELF, true);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            return getDataFactory().getOWLObjectHasSelf(property);
        }
    }

    static class ObjectHasValueTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectHasValueTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isResourcePresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    && isResourcePresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public OWLObjectHasValue translate(IRI mainNode) {
            IRI value = getConsumer().getResourceObject(mainNode,
                    OWL_HAS_VALUE, true);
            OWLIndividual individual = getConsumer().translateIndividual(value);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            return getDataFactory().getOWLObjectHasValue(property, individual);
        }
    }

    /**
     * A class expression translator which produces an {@code OWLIntersectionOf}
     * . This relies on the main node having an intersectionOf triple.
     * 
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectIntersectionOfTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectIntersectionOfTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode,
                    OWL_INTERSECTION_OF, false);
            return isClassExpressionStrict(mainNode)
                    && isClassExpressionListStrict(listNode, 2);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_INTERSECTION_OF);
        }

        @Override
        public OWLObjectIntersectionOf translate(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode,
                    OWL_INTERSECTION_OF, true);
            Set<OWLClassExpression> classExpressions = accessor
                    .translateToClassExpressionSet(listNode);
            return getDataFactory()
                    .getOWLObjectIntersectionOf(classExpressions);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectMaxCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectMaxCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode, OWL_MAX_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MAX_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLObjectMaxCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MAX_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_CLASS, true);
            if (fillerIRI != null
                    && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLClassExpression filler = accessor
                        .translateClassExpression(fillerIRI);
                return getDataFactory().getOWLObjectMaxCardinality(cardi,
                        property, filler);
            } else {
                return getDataFactory().getOWLObjectMaxCardinality(cardi,
                        property);
            }
        }
    }

    /**
     * @author Matthew Horridge, The University of Manchester, Bio-Health
     *         Informatics Group
     * @since 3.1.0
     */
    static class ObjectMaxQualifiedCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectMaxQualifiedCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode,
                            OWL_MAX_QUALIFIED_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode,
                    OWL_MAX_QUALIFIED_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionLax(mainNode, OWL_ON_CLASS);
        }

        @Override
        public OWLObjectMaxCardinality translate(IRI mainNode) {
            int cardi = translateInteger(mainNode,
                    OWL_MAX_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_CLASS, true);
            OWLClassExpression filler = accessor
                    .translateClassExpression(fillerIRI);
            return getDataFactory().getOWLObjectMaxCardinality(cardi, property,
                    filler);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectMinCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectMinCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode, OWL_MIN_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MIN_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLObjectMinCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MIN_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_CLASS, true);
            if (fillerIRI != null
                    && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLClassExpression filler = accessor
                        .translateClassExpression(fillerIRI);
                return getDataFactory().getOWLObjectMinCardinality(cardi,
                        property, filler);
            } else {
                return getDataFactory().getOWLObjectMinCardinality(cardi,
                        property);
            }
        }
    }

    static class ObjectMinQualifiedCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectMinQualifiedCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode,
                            OWL_MIN_QUALIFIED_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode,
                    OWL_MIN_QUALIFIED_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionLax(mainNode, OWL_ON_CLASS);
        }

        @Override
        public OWLObjectMinCardinality translate(IRI mainNode) {
            int cardi = translateInteger(mainNode,
                    OWL_MIN_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_CLASS, true);
            OWLClassExpression filler = accessor
                    .translateClassExpression(fillerIRI);
            return getDataFactory().getOWLObjectMinCardinality(cardi, property,
                    filler);
        }
    }

    static class ObjectOneOfTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectOneOfTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode,
                    OWL_ONE_OF, false);
            return isIndividualListStrict(listNode, 1);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_ONE_OF);
        }

        @Override
        public OWLObjectOneOf translate(IRI mainNode) {
            IRI oneOfObject = getConsumer().getResourceObject(mainNode,
                    OWL_ONE_OF, true);
            Set<OWLIndividual> individuals = accessor
                    .translateToIndividualSet(oneOfObject);
            return getDataFactory().getOWLObjectOneOf(individuals);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectPropertyListItemTranslator implements
            ListItemTranslator<OWLObjectPropertyExpression> {

        private OWLRDFConsumer consumer;

        public ObjectPropertyListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        /**
         * The rdf:first triple that represents the item to be translated. This
         * triple will point to something like a class expression, individual.
         * 
         * @param firstObject
         *        The rdf:first triple that points to the item to be translated.
         * @return The translated item.
         */
        @Override
        public OWLObjectPropertyExpression translate(IRI firstObject) {
            consumer.addObjectProperty(firstObject, false);
            return consumer.translateObjectPropertyExpression(firstObject);
        }

        @Override
        public OWLObjectPropertyExpression translate(OWLLiteral firstObject) {
            logger.info("Cannot translate list item as an object property, because rdf:first triple is a literal triple");
            return null;
        }
    }

    /**
     * @author Matthew Horridge, The University of Manchester, Bio-Health
     *         Informatics Group
     * @since 3.1.0
     */
    static class ObjectQualifiedCardinalityTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectQualifiedCardinalityTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isNonNegativeIntegerStrict(mainNode,
                            OWL_QUALIFIED_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_QUALIFIED_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionLax(mainNode, OWL_ON_CLASS);
        }

        @Override
        public OWLObjectExactCardinality translate(IRI mainNode) {
            int cardi = translateInteger(mainNode, OWL_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_CLASS, true);
            OWLClassExpression filler = accessor
                    .translateClassExpression(fillerIRI);
            return getDataFactory().getOWLObjectExactCardinality(cardi,
                    property, filler);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectSomeValuesFromTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectSomeValuesFromTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isClassExpressionLax(mainNode, OWL_SOME_VALUES_FROM)
                    && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isObjectPropertyLax(mainNode)
                    && isResourcePresent(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public OWLObjectSomeValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(),
                    OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode,
                    OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(propertyIRI);
            IRI fillerMainNode = getConsumer().getResourceObject(mainNode,
                    OWL_SOME_VALUES_FROM, true);
            OWLClassExpression filler = accessor
                    .translateClassExpression(fillerMainNode);
            return getDataFactory()
                    .getOWLObjectSomeValuesFrom(property, filler);
        }
    }

    /**
     * Translates a set of triples to an {@code OWLUnionOf}.
     * 
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectUnionOfTranslator extends
            AbstractClassExpressionTranslator {

        public ObjectUnionOfTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode,
                    OWL_UNION_OF, false);
            return isClassExpressionStrict(mainNode)
                    && isClassExpressionListStrict(listNode, 2);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_UNION_OF);
        }

        @Override
        public OWLObjectUnionOf translate(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode,
                    OWL_UNION_OF, true);
            Set<OWLClassExpression> classExpressions = accessor
                    .translateToClassExpressionSet(listNode);
            return getDataFactory().getOWLObjectUnionOf(classExpressions);
        }
    }

    static class SWRLAtomListItemTranslator implements
            ListItemTranslator<SWRLAtom> {

        private OWLRDFConsumer consumer;
        protected OWLDataFactory dataFactory;
        protected TranslatorAccessor accessor;

        public SWRLAtomListItemTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            this.consumer = consumer;
            this.accessor = accessor;
            dataFactory = consumer.getDataFactory();
        }

        @Override
        public SWRLAtom translate(IRI firstObject) {
            if (consumer.isSWRLBuiltInAtom(firstObject)) {
                IRI builtInIRI = consumer.getResourceObject(firstObject,
                        BUILT_IN.getIRI(), true);
                IRI mainIRI = consumer.getResourceObject(firstObject,
                        ARGUMENTS.getIRI(), true);
                OptimisedListTranslator<SWRLDArgument> listTranslator = new OptimisedListTranslator<SWRLDArgument>(
                        consumer, new SWRLAtomDObjectListItemTranslator());
                List<SWRLDArgument> args = listTranslator
                        .translateList(mainIRI);
                return dataFactory.getSWRLBuiltInAtom(builtInIRI, args);
            } else if (consumer.isSWRLClassAtom(firstObject)) {
                // C(?x) or C(ind)
                SWRLIArgument iObject = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_1.getIRI());
                IRI classIRI = consumer.getResourceObject(firstObject,
                        CLASS_PREDICATE.getIRI(), true);
                if (classIRI == null) {
                    throw new OWLRuntimeException(
                            "Don't know how to translate SWRL Atom: class IRI is null "
                                    + firstObject);
                }
                OWLClassExpression desc = accessor
                        .translateClassExpression(classIRI);
                return dataFactory.getSWRLClassAtom(desc, iObject);
            } else if (consumer.isSWRLDataRangeAtom(firstObject)) {
                // DR(?x) or DR(val)
                SWRLDArgument dObject = translateSWRLAtomDObject(firstObject,
                        ARGUMENT_1.getIRI());
                IRI dataRangeIRI = consumer.getResourceObject(firstObject,
                        DATA_RANGE.getIRI(), true);
                if (dataRangeIRI == null) {
                    throw new OWLRuntimeException(
                            "Don't know how to translate SWRL Atom: data range IRI is null "
                                    + firstObject);
                }
                OWLDataRange dataRange = consumer
                        .translateDataRange(dataRangeIRI);
                return dataFactory.getSWRLDataRangeAtom(dataRange, dObject);
            } else if (consumer.isSWRLDataValuedPropertyAtom(firstObject)) {
                SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_1.getIRI());
                SWRLDArgument arg2 = translateSWRLAtomDObject(firstObject,
                        ARGUMENT_2.getIRI());
                IRI dataPropertyIRI = consumer.getResourceObject(firstObject,
                        PROPERTY_PREDICATE.getIRI(), true);
                if (dataPropertyIRI == null) {
                    throw new OWLRuntimeException(
                            "Don't know how to translate SWRL Atom: data property IRI is null "
                                    + firstObject);
                }
                OWLDataPropertyExpression prop = consumer
                        .translateDataPropertyExpression(dataPropertyIRI);
                return dataFactory.getSWRLDataPropertyAtom(prop, arg1, arg2);
            } else if (consumer.isSWRLIndividualPropertyAtom(firstObject)) {
                SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_1.getIRI());
                SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_2.getIRI());
                IRI objectPropertyIRI = consumer.getResourceObject(firstObject,
                        PROPERTY_PREDICATE.getIRI(), true);
                if (objectPropertyIRI == null) {
                    throw new OWLRuntimeException(
                            "Don't know how to translate SWRL Atom: object property IRI is null "
                                    + firstObject);
                }
                OWLObjectPropertyExpression prop = consumer
                        .translateObjectPropertyExpression(objectPropertyIRI);
                return dataFactory.getSWRLObjectPropertyAtom(prop, arg1, arg2);
            } else if (consumer.isSWRLSameAsAtom(firstObject)) {
                SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_1.getIRI());
                SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_2.getIRI());
                return dataFactory.getSWRLSameIndividualAtom(arg1, arg2);
            } else if (consumer.isSWRLDifferentFromAtom(firstObject)) {
                SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_1.getIRI());
                SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject,
                        ARGUMENT_2.getIRI());
                return dataFactory.getSWRLDifferentIndividualsAtom(arg1, arg2);
            }
            throw new OWLRuntimeException(
                    "Don't know how to translate SWRL Atom: " + firstObject);
        }

        @Override
        public SWRLAtom translate(OWLLiteral firstObject) {
            throw new OWLRuntimeException("Unexpected literal in atom list: "
                    + firstObject);
        }

        private SWRLIArgument translateSWRLAtomIObject(IRI mainIRI,
                IRI argPredicateIRI) {
            IRI argIRI = consumer.getResourceObject(mainIRI, argPredicateIRI,
                    true);
            if (argIRI != null) {
                if (consumer.isSWRLVariable(argIRI)) {
                    return dataFactory.getSWRLVariable(argIRI);
                } else {
                    return dataFactory.getSWRLIndividualArgument(consumer
                            .getOWLIndividual(argIRI));
                }
            } else {
                throw new OWLRuntimeException(
                        "Cannot translate SWRL Atom I-Object for "
                                + argPredicateIRI + " Triple not found.");
            }
        }

        private SWRLDArgument translateSWRLAtomDObject(IRI mainIRI,
                IRI argPredicateIRI) {
            IRI argIRI = consumer.getResourceObject(mainIRI, argPredicateIRI,
                    true);
            if (argIRI != null) {
                // Must be a variable -- double check
                if (!consumer.isSWRLVariable(argIRI)) {
                    logger.info(
                            "Expected SWRL variable for SWRL Data Object: {} (possibly untyped)",
                            argIRI);
                }
                return dataFactory.getSWRLVariable(argIRI);
            } else {
                // Must be a literal
                OWLLiteral con = consumer.getLiteralObject(mainIRI,
                        argPredicateIRI, true);
                if (con != null) {
                    return dataFactory.getSWRLLiteralArgument(con);
                }
            }
            throw new IllegalStateException(
                    "Could not translate SWRL Atom D-Object");
        }

        private class SWRLAtomDObjectListItemTranslator implements
                ListItemTranslator<SWRLDArgument> {

            public SWRLAtomDObjectListItemTranslator() {}

            @Override
            public SWRLDArgument translate(IRI firstObject) {
                return dataFactory.getSWRLVariable(firstObject);
            }

            @Override
            public SWRLDArgument translate(OWLLiteral firstObject) {
                return dataFactory.getSWRLLiteralArgument(firstObject);
            }
        }
    }

    static class SWRLRuleTranslator {

        private OWLRDFConsumer consumer;
        private OptimisedListTranslator<SWRLAtom> listTranslator;

        public SWRLRuleTranslator(OWLRDFConsumer consumer,
                TranslatorAccessor accessor) {
            this.consumer = consumer;
            listTranslator = new OptimisedListTranslator<SWRLAtom>(consumer,
                    new SWRLAtomListItemTranslator(consumer, accessor));
        }

        /**
         * @param mainNode
         *        rule to translate
         */
        public void translateRule(IRI mainNode) {
            Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
            Set<IRI> predicates = consumer.getPredicatesBySubject(mainNode);
            for (IRI i : predicates) {
                if (consumer.isAnnotationProperty(i)) {
                    OWLAnnotationProperty p = consumer.getDataFactory()
                            .getOWLAnnotationProperty(i);
                    OWLLiteral literal = consumer.getLiteralObject(mainNode, i,
                            true);
                    while (literal != null) {
                        annotations.add(consumer.getDataFactory()
                                .getOWLAnnotation(p, literal));
                        literal = consumer.getLiteralObject(mainNode, i, true);
                    }
                }
            }
            Set<SWRLAtom> consequent = Collections.emptySet();
            // XXX annotations on rules are not parsed correctly
            IRI ruleHeadIRI = consumer.getResourceObject(mainNode,
                    SWRLVocabulary.HEAD.getIRI(), true);
            if (ruleHeadIRI != null) {
                consequent = listTranslator.translateToSet(ruleHeadIRI);
            }
            Set<SWRLAtom> antecedent = Collections.emptySet();
            IRI ruleBodyIRI = consumer.getResourceObject(mainNode,
                    SWRLVocabulary.BODY.getIRI(), true);
            if (ruleBodyIRI != null) {
                antecedent = listTranslator.translateToSet(ruleBodyIRI);
            }
            SWRLRule rule = null;
            if (!consumer.isAnonymousNode(mainNode)) {
                rule = consumer.getDataFactory().getSWRLRule(antecedent,
                        consequent, annotations);
            } else {
                rule = consumer.getDataFactory().getSWRLRule(antecedent,
                        consequent, annotations);
            }
            consumer.addAxiom(rule);
        }
    }

    static class TypedConstantListItemTranslator implements
            ListItemTranslator<OWLLiteral> {

        @Override
        public OWLLiteral translate(IRI firstObject) {
            logger.info("Cannot translate list item to a constant because rdf:first triple is a resource triple");
            return null;
        }

        @Override
        public OWLLiteral translate(OWLLiteral firstObject) {
            return firstObject;
        }
    }
}
