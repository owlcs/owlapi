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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.*;

import java.util.*;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
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

    protected static final Logger LOGGER = LoggerFactory.getLogger(Translators.class);

    private Translators() {}

    static OptimisedListTranslator<OWLPropertyExpression> getListTranslator(OWLRDFConsumer consumer) {
        return new OptimisedListTranslator<>(consumer, new HasKeyListItemTranslator(consumer));
    }

    static class TranslatorAccessor {

        /**
         * A translator for lists of class expressions (such lists are used in
         * intersections, unions etc.).
         */
        private final OptimisedListTranslator<OWLClassExpression> classExpressionListTranslator;
        /** The class expression translators. */
        private final List<ClassExpressionTranslator> classExpressionTranslators = new ArrayList<>();
        /**
         * A translator for individual lists (such lists are used in object
         * oneOf constructs).
         */
        private final OptimisedListTranslator<OWLIndividual> individualListTranslator;
        /** The object property list translator. */
        private final OptimisedListTranslator<OWLObjectPropertyExpression> objectPropertyListTranslator;
        /** The constant list translator. */
        private final OptimisedListTranslator<OWLLiteral> constantListTranslator;
        /** The data property list translator. */
        private final OptimisedListTranslator<OWLDataPropertyExpression> dataPropertyListTranslator;
        /** The data range list translator. */
        private final OptimisedListTranslator<OWLDataRange> dataRangeListTranslator;
        /** The face restriction list translator. */
        private final OptimisedListTranslator<OWLFacetRestriction> faceRestrictionListTranslator;
        private final OWLRDFConsumer consumer;

        TranslatorAccessor(OWLRDFConsumer r) {
            consumer = r;
            classExpressionListTranslator = new OptimisedListTranslator<>(r,
                    new ClassExpressionListItemTranslator(r, this));
            individualListTranslator = new OptimisedListTranslator<>(r, new IndividualListItemTranslator(r));
            constantListTranslator = new OptimisedListTranslator<>(r, new TypedConstantListItemTranslator());
            objectPropertyListTranslator = new OptimisedListTranslator<>(r, new ObjectPropertyListItemTranslator(r));
            dataPropertyListTranslator = new OptimisedListTranslator<>(r, new DataPropertyListItemTranslator(r));
            dataRangeListTranslator = new OptimisedListTranslator<>(r, new DataRangeListItemTranslator(r));
            faceRestrictionListTranslator = new OptimisedListTranslator<>(r,
                    new OWLFacetRestrictionListItemTranslator(r));
            classExpressionTranslators.add(new NamedClassTranslator(r, this));
            classExpressionTranslators.add(new ObjectIntersectionOfTranslator(r, this));
            classExpressionTranslators.add(new ObjectUnionOfTranslator(r, this));
            classExpressionTranslators.add(new ObjectComplementOfTranslator(r, this));
            classExpressionTranslators.add(new ObjectOneOfTranslator(r, this));
            classExpressionTranslators.add(new ObjectSomeValuesFromTranslator(r, this));
            classExpressionTranslators.add(new ObjectAllValuesFromTranslator(r, this));
            classExpressionTranslators.add(new ObjectHasValueTranslator(r, this));
            classExpressionTranslators.add(new ObjectHasSelfTranslator(r, this));
            classExpressionTranslators.add(new ObjectMinQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new ObjectMaxQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new ObjectQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new ObjectMinCardinalityTranslator(r, this));
            classExpressionTranslators.add(new ObjectMaxCardinalityTranslator(r, this));
            classExpressionTranslators.add(new ObjectCardinalityTranslator(r, this));
            classExpressionTranslators.add(new DataSomeValuesFromTranslator(r, this));
            classExpressionTranslators.add(new DataAllValuesFromTranslator(r, this));
            classExpressionTranslators.add(new DataHasValueTranslator(r, this));
            classExpressionTranslators.add(new DataMinQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new DataMaxQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new DataQualifiedCardinalityTranslator(r, this));
            classExpressionTranslators.add(new DataMinCardinalityTranslator(r, this));
            classExpressionTranslators.add(new DataMaxCardinalityTranslator(r, this));
            classExpressionTranslators.add(new DataCardinalityTranslator(r, this));
        }

        protected Set<OWLClassExpression> translateToClassExpressionSet(IRI mainNode) {
            return classExpressionListTranslator.translateToSet(mainNode);
        }

        private OWLClassExpression translateClassExpressionInternal(IRI mainNode) {
            // Some optimisations...
            // We either have a class or a restriction
            Mode mode = consumer.getConfiguration().isStrict() ? Mode.STRICT : Mode.LAX;
            for (ClassExpressionTranslator translator : classExpressionTranslators) {
                if (translator.matches(mainNode, mode)) {
                    return translator.translate(mainNode);
                }
            }
            if (!consumer.isAnonymousNode(mainNode)) {
                return consumer.getDataFactory().getOWLClass(mainNode);
            } else {
                return consumer.generateAndLogParseError(EntityType.CLASS, mainNode);
            }
        }

        public void consumeSWRLRules(Set<IRI> swrlRules) {
            SWRLRuleTranslator translator = new SWRLRuleTranslator(consumer, this);
            swrlRules.forEach(i -> translator.translateRule(i));
        }

        public @Nullable OWLClassExpression getClassExpressionIfTranslated(IRI mainNode) {
            return translatedClassExpression.get(mainNode);
        }

        public void cleanup() {
            translatedClassExpression.clear();
        }

        protected List<OWLObjectPropertyExpression> translateToObjectPropertyList(IRI mainNode) {
            return objectPropertyListTranslator.translateList(mainNode);
        }

        protected List<OWLDataPropertyExpression> translateToDataPropertyList(IRI mainNode) {
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

        protected Set<OWLFacetRestriction> translateToFacetRestrictionSet(IRI mainNode) {
            return faceRestrictionListTranslator.translateToSet(mainNode);
        }

        private final Map<IRI, OWLClassExpression> translatedClassExpression = new HashMap<>();

        protected OWLClassExpression translateClassExpression(IRI mainNode) {
            OWLClassExpression ce = translatedClassExpression.get(mainNode);
            if (ce == null) {
                ce = translateClassExpressionInternal(mainNode);
                translatedClassExpression.put(mainNode, ce);
            }
            return ce;
        }
    }

    abstract static class AbstractClassExpressionTranslator implements ClassExpressionTranslator {

        private final OWLRDFConsumer consumer;
        private final ClassExpressionMatcher classExpressionMatcher = new ClassExpressionMatcher();
        private final DataRangeMatcher dataRangeMatcher = new DataRangeMatcher();
        private final IndividualMatcher individualMatcher = new IndividualMatcher();
        protected final TranslatorAccessor accessor;

        protected AbstractClassExpressionTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
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

        protected boolean isResourcePresent(IRI mainNode, OWLRDFVocabulary predicate) {
            return consumer.getResourceObject(mainNode, predicate, false) != null;
        }

        protected boolean isLiteralPresent(IRI mainNode, OWLRDFVocabulary predicate) {
            return consumer.getLiteralObject(mainNode, predicate, false) != null;
        }

        protected boolean isRestrictionStrict(IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isRestrictionLax(IRI node) {
            return consumer.isRestriction(node);
        }

        protected boolean isNonNegativeIntegerStrict(IRI mainNode, OWLRDFVocabulary predicate) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, false);
            if (literal == null) {
                return false;
            }
            OWLDatatype datatype = literal.getDatatype();
            OWL2Datatype nni = OWL2Datatype.XSD_NON_NEGATIVE_INTEGER;
            return datatype.getIRI().equals(nni.getIRI()) && nni.isInLexicalSpace(literal.getLiteral());
        }

        protected boolean isNonNegativeIntegerLax(IRI mainNode, OWLRDFVocabulary predicate) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, false);
            if (literal == null) {
                return false;
            }
            return OWL2Datatype.XSD_INTEGER.isInLexicalSpace(verifyNotNull(literal.getLiteral().trim()));
        }

        protected int translateInteger(IRI mainNode, OWLRDFVocabulary predicate) {
            OWLLiteral literal = consumer.getLiteralObject(mainNode, predicate, true);
            if (literal == null) {
                return 0;
            }
            try {
                return Integer.parseInt(literal.getLiteral().trim());
            } catch (@SuppressWarnings("unused") NumberFormatException e) {
                return 0;
            }
        }

        protected boolean isClassExpressionStrict(@Nullable IRI node) {
            return node != null && consumer.isClassExpression(node) && !consumer.isDataRange(node);
        }

        protected boolean isClassExpressionStrict(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isClassExpressionStrict(object);
        }

        protected boolean isClassExpressionLax(IRI mainNode) {
            return consumer.isClassExpression(mainNode)
                    || consumer.isParsedAllTriples() && !consumer.isDataRange(mainNode);
        }

        protected boolean isClassExpressionLax(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isClassExpressionLax(object);
        }

        protected boolean isObjectPropertyStrict(IRI node) {
            return consumer.isObjectPropertyOnly(node);
        }

        protected boolean isObjectPropertyStrict(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isObjectPropertyStrict(object);
        }

        protected boolean isObjectPropertyLax(IRI node) {
            return consumer.isObjectProperty(node);
        }

        protected boolean isObjectPropertyLax(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isObjectPropertyLax(object);
        }

        protected boolean isDataPropertyStrict(IRI node) {
            return consumer.isDataPropertyOnly(node);
        }

        protected boolean isDataPropertyStrict(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataPropertyStrict(object);
        }

        protected boolean isDataPropertyLax(IRI node) {
            return consumer.isDataProperty(node);
        }

        protected boolean isDataPropertyLax(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataPropertyLax(object);
        }

        protected boolean isDataRangeStrict(IRI node) {
            return consumer.isDataRange(node) && !consumer.isClassExpression(node);
        }

        protected boolean isDataRangeStrict(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataRangeStrict(object);
        }

        protected boolean isDataRangeLax(IRI node) {
            return consumer.isParsedAllTriples() && consumer.isDataRange(node);
        }

        protected boolean isDataRangeLax(IRI mainNode, OWLRDFVocabulary predicate) {
            IRI object = consumer.getResourceObject(mainNode, predicate, false);
            return object != null && isDataRangeLax(object);
        }

        protected boolean isClassExpressionListStrict(@Nullable IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, classExpressionMatcher, minSize);
        }

        protected boolean isDataRangeListStrict(IRI mainNode, int minSize) {
            return isResourceListStrict(mainNode, dataRangeMatcher, minSize);
        }

        protected boolean isIndividualListStrict(@Nullable IRI mainNode, int minSize) {
            return mainNode != null && isResourceListStrict(mainNode, individualMatcher, minSize);
        }

        protected boolean isResourceListStrict(@Nullable IRI mainNode, TypeMatcher typeMatcher, int minSize) {
            if (mainNode == null) {
                return false;
            }
            IRI currentListNode = mainNode;
            Set<IRI> visitedListNodes = new HashSet<>();
            int size = 0;
            while (true) {
                IRI firstObject = consumer.getResourceObject(currentListNode, RDF_FIRST, false);
                if (firstObject == null) {
                    return false;
                }
                if (!typeMatcher.isTypeStrict(firstObject)) {
                    // Something in the list that is not of the required type
                    return false;
                } else {
                    size++;
                }
                IRI restObject = consumer.getResourceObject(currentListNode, RDF_REST, false);
                if (restObject == null) {
                    // Not terminated properly
                    return false;
                }
                if (visitedListNodes.contains(restObject)) {
                    // Cycle - Non-terminating
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

            ClassExpressionMatcher() {}

            @Override
            public boolean isTypeStrict(IRI node) {
                return isClassExpressionStrict(node);
            }
        }

        private class DataRangeMatcher implements TypeMatcher {

            DataRangeMatcher() {}

            @Override
            public boolean isTypeStrict(IRI node) {
                return isDataRangeStrict(node);
            }
        }

        private class IndividualMatcher implements TypeMatcher {

            IndividualMatcher() {}

            @Override
            public boolean isTypeStrict(IRI node) {
                return true;
            }
        }
    }

    static class ClassExpressionListItemTranslator implements ListItemTranslator<OWLClassExpression> {

        private final OWLRDFConsumer consumer;
        protected final TranslatorAccessor accessor;

        ClassExpressionListItemTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            this.consumer = consumer;
            this.accessor = accessor;
        }

        @Override
        public @Nullable OWLClassExpression translate(IRI firstObject) {
            consumer.addClassExpression(firstObject, false);
            return accessor.translateClassExpression(firstObject);
        }

        @Override
        public @Nullable OWLClassExpression translate(OWLLiteral firstObject) {
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

    static class DataAllValuesFromTranslator extends AbstractClassExpressionTranslator {

        DataAllValuesFromTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isDataRangeLax(mainNode, OWL_ALL_VALUES_FROM) && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isDataPropertyLax(mainNode, OWL_ON_PROPERTY) && isResourcePresent(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public OWLDataAllValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ALL_VALUES_FROM, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            OWLDataRange filler = getConsumer().translateDataRange(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLDataAllValuesFrom(property, filler);
        }
    }

    static class DataCardinalityTranslator extends AbstractClassExpressionTranslator {

        DataCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_CARDINALITY) && isDataPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLDataExactCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_DATA_RANGE, true);
            if (fillerIRI != null && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLDataRange filler = getConsumer().translateDataRange(fillerIRI);
                return getDataFactory().getOWLDataExactCardinality(cardi, property, filler);
            } else {
                return getDataFactory().getOWLDataExactCardinality(cardi, property);
            }
        }
    }

    static class DataHasValueTranslator extends AbstractClassExpressionTranslator {

        DataHasValueTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isLiteralPresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isLiteralPresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public OWLDataHasValue translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            OWLLiteral lit = getConsumer().getLiteralObject(mainNode, OWL_HAS_VALUE, true);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            return getDataFactory().getOWLDataHasValue(property, verifyNotNull(lit));
        }
    }

    static class DataMaxCardinalityTranslator extends AbstractClassExpressionTranslator {

        DataMaxCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MAX_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MAX_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLDataMaxCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MAX_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_DATA_RANGE, true);
            if (fillerIRI != null && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLDataRange filler = getConsumer().translateDataRange(fillerIRI);
                return getDataFactory().getOWLDataMaxCardinality(cardi, property, filler);
            } else {
                return getDataFactory().getOWLDataMaxCardinality(cardi, property);
            }
        }
    }

    static class DataMaxQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

        DataMaxQualifiedCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MAX_QUALIFIED_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MAX_QUALIFIED_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY) && isDataRangeLax(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public OWLDataMaxCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MAX_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_DATA_RANGE, true);
            OWLDataRange filler = getConsumer().translateDataRange(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLDataMaxCardinality(cardi, property, filler);
        }
    }

    static class DataMinCardinalityTranslator extends AbstractClassExpressionTranslator {

        DataMinCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MIN_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MIN_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLDataMinCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MIN_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_DATA_RANGE, true);
            if (fillerIRI != null && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLDataRange filler = getConsumer().translateDataRange(fillerIRI);
                return getDataFactory().getOWLDataMinCardinality(cardi, property, filler);
            } else {
                return getDataFactory().getOWLDataMinCardinality(cardi, property);
            }
        }
    }

    static class DataMinQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

        DataMinQualifiedCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MIN_QUALIFIED_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY) && isDataRangeStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MIN_QUALIFIED_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY) && isDataRangeLax(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public OWLDataMinCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MIN_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_DATA_RANGE, true);
            OWLDataRange filler = getConsumer().translateDataRange(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLDataMinCardinality(cardi, property, filler);
        }
    }

    static class DataPropertyListItemTranslator implements ListItemTranslator<OWLDataPropertyExpression> {

        private final OWLRDFConsumer consumer;

        DataPropertyListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public @Nullable OWLDataPropertyExpression translate(IRI firstObject) {
            consumer.addDataProperty(firstObject, false);
            return consumer.getOWLDataProperty(firstObject);
        }

        @Override
        public @Nullable OWLDataPropertyExpression translate(OWLLiteral firstObject) {
            return null;
        }
    }

    static class DataQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

        DataQualifiedCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_QUALIFIED_CARDINALITY)
                    && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY) && isDataRangeStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_QUALIFIED_CARDINALITY)
                    && isDataPropertyLax(mainNode, OWL_ON_PROPERTY) && isDataRangeLax(mainNode, OWL_ON_DATA_RANGE);
        }

        @Override
        public OWLDataExactCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_DATA_RANGE, true);
            OWLDataRange filler = getConsumer().translateDataRange(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLDataExactCardinality(cardi, property, filler);
        }
    }

    static class DataRangeListItemTranslator implements ListItemTranslator<OWLDataRange> {

        private final OWLRDFConsumer consumer;

        DataRangeListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public @Nullable OWLDataRange translate(OWLLiteral firstObject) {
            return null;
        }

        @Override
        public @Nullable OWLDataRange translate(IRI firstObject) {
            return consumer.translateDataRange(firstObject);
        }
    }

    static class DataSomeValuesFromTranslator extends AbstractClassExpressionTranslator {

        DataSomeValuesFromTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isDataPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isDataRangeStrict(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isDataRangeLax(mainNode, OWL_SOME_VALUES_FROM) && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isDataPropertyLax(mainNode, OWL_ON_PROPERTY)
                            && isResourcePresent(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public OWLDataSomeValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLDataPropertyExpression property = getConsumer()
                    .translateDataPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_SOME_VALUES_FROM, true);
            OWLDataRange filler = getConsumer().translateDataRange(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLDataSomeValuesFrom(property, filler);
        }
    }

    static class HasKeyListItemTranslator implements ListItemTranslator<OWLPropertyExpression> {

        private final OWLRDFConsumer consumer;

        HasKeyListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public @Nullable OWLPropertyExpression translate(OWLLiteral firstObject) {
            return null;
        }

        @Override
        public @Nullable OWLPropertyExpression translate(IRI firstObject) {
            if (consumer.isObjectPropertyOnly(firstObject)) {
                return consumer.getDataFactory().getOWLObjectProperty(firstObject);
            }
            if (consumer.isDataPropertyOnly(firstObject)) {
                return consumer.getDataFactory().getOWLDataProperty(firstObject);
            }
            // If neither condition was true, the property has been illegally
            // punned, or is untyped
            // use the first translation available, since there is no way to
            // know which is correct
            OWLPropertyExpression property = null;
            if (consumer.isObjectProperty(firstObject)) {
                LOGGER.warn("Property {} has been punned illegally: found declaration as OWLObjectProperty",
                        firstObject);
                property = consumer.getDataFactory().getOWLObjectProperty(firstObject);
            }
            if (consumer.isDataProperty(firstObject)) {
                LOGGER.warn("Property {} has been punned illegally: found declaration as OWLDataProperty", firstObject);
                if (property == null) {
                    property = consumer.getDataFactory().getOWLDataProperty(firstObject);
                }
            }
            if (consumer.isAnnotationProperty(firstObject)) {
                LOGGER.warn("Property {} has been punned illegally: found declaration as OWLAnnotationProperty",
                        firstObject);
                if (property == null) {
                    property = consumer.getDataFactory().getOWLAnnotationProperty(firstObject);
                }
            }
            // if there is no declaration for the property at this point, warn
            // and consider it a datatype property.
            // This matches existing behaviour.
            if (property == null) {
                LOGGER.warn("Property {} is undeclared at this point in parsing: typing as OWLDataProperty",
                        firstObject);
                property = consumer.getDataFactory().getOWLDataProperty(firstObject);
            }
            return property;
        }
    }

    static class IndividualListItemTranslator implements ListItemTranslator<OWLIndividual> {

        private final OWLRDFConsumer consumer;

        IndividualListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public @Nullable OWLIndividual translate(IRI firstObject) {
            return consumer.translateIndividual(firstObject);
        }

        @Override
        public @Nullable OWLIndividual translate(OWLLiteral firstObject) {
            LOGGER.info("Cannot translate list item to individual, because rdf:first triple is a literal triple");
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
        @Nullable
        O translate(IRI firstObject);

        /**
         * @param firstObject
         *        firstObject
         * @return translated item
         */
        @Nullable
        O translate(OWLLiteral firstObject);
    }

    static class NamedClassTranslator extends AbstractClassExpressionTranslator {

        NamedClassTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
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

    static class OWLFacetRestrictionListItemTranslator implements ListItemTranslator<OWLFacetRestriction> {

        private final OWLRDFConsumer consumer;

        OWLFacetRestrictionListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public @Nullable OWLFacetRestriction translate(OWLLiteral firstObject) {
            return null;
        }

        @Override
        public @Nullable OWLFacetRestriction translate(IRI firstObject) {
            for (OWLFacet facet : OWLFacet.values()) {
                OWLLiteral lit = consumer.getLiteralObject(firstObject, facet.getIRI(), true);
                if (lit != null) {
                    return consumer.getDataFactory().getOWLFacetRestriction(facet, lit);
                }
            }
            return null;
        }
    }

    static class OWLObjectPropertyExpressionListItemTranslator
            implements ListItemTranslator<OWLObjectPropertyExpression> {

        private final OWLRDFConsumer consumer;

        OWLObjectPropertyExpressionListItemTranslator(OWLRDFConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public @Nullable OWLObjectPropertyExpression translate(IRI firstObject) {
            return consumer.translateObjectPropertyExpression(firstObject);
        }

        @Override
        public @Nullable OWLObjectPropertyExpression translate(OWLLiteral firstObject) {
            LOGGER.info(
                    "Cannot translate list item as an object property, because rdf:first triple is a literal triple");
            return null;
        }
    }

    static class ObjectAllValuesFromTranslator extends AbstractClassExpressionTranslator {

        ObjectAllValuesFromTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isClassExpressionLax(mainNode, OWL_ALL_VALUES_FROM) && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isObjectPropertyLax(mainNode) && isResourcePresent(mainNode, OWL_ALL_VALUES_FROM);
        }

        @Override
        public OWLObjectAllValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ALL_VALUES_FROM, true);
            OWLClassExpression filler = accessor.translateClassExpression(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLObjectAllValuesFrom(property, filler);
        }
    }

    static class ObjectCardinalityTranslator extends AbstractClassExpressionTranslator {

        ObjectCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_CARDINALITY) && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLObjectExactCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
            if (fillerIRI != null && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLClassExpression filler = accessor.translateClassExpression(fillerIRI);
                return getDataFactory().getOWLObjectExactCardinality(cardi, property, filler);
            } else {
                return getDataFactory().getOWLObjectExactCardinality(cardi, property);
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
    static class ObjectComplementOfTranslator extends AbstractClassExpressionTranslator {

        ObjectComplementOfTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI complementOfIRI = getConsumer().getResourceObject(mainNode, OWL_COMPLEMENT_OF, false);
            return isClassExpressionStrict(mainNode) && isClassExpressionStrict(complementOfIRI);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_COMPLEMENT_OF) && isClassExpressionLax(mainNode);
        }

        @Override
        public OWLObjectComplementOf translate(IRI mainNode) {
            IRI complementOfObject = getConsumer().getResourceObject(mainNode, OWL_COMPLEMENT_OF, true);
            OWLClassExpression operand = accessor.translateClassExpression(verifyNotNull(complementOfObject));
            return getDataFactory().getOWLObjectComplementOf(operand);
        }
    }

    static class ObjectHasSelfTranslator extends AbstractClassExpressionTranslator {

        ObjectHasSelfTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            OWLLiteral literal = getConsumer().getLiteralObject(mainNode, OWL_HAS_SELF.getIRI(), false);
            return literal != null && isStrictBooleanTrueLiteral(literal)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        private static boolean isStrictBooleanTrueLiteral(OWLLiteral literal) {
            return OWL2Datatype.XSD_BOOLEAN.getIRI().equals(literal.getDatatype().getIRI())
                    && literal.getLiteral().toLowerCase(Locale.ENGLISH).equals("true");
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_ON_PROPERTY) && isLiteralPresent(mainNode, OWL_HAS_SELF);
        }

        @Override
        public OWLObjectHasSelf translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            getConsumer().getLiteralObject(mainNode, OWL_HAS_SELF, true);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            return getDataFactory().getOWLObjectHasSelf(property);
        }
    }

    static class ObjectHasValueTranslator extends AbstractClassExpressionTranslator {

        ObjectHasValueTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isResourcePresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_ON_PROPERTY) && isResourcePresent(mainNode, OWL_HAS_VALUE);
        }

        @Override
        public OWLObjectHasValue translate(IRI mainNode) {
            IRI value = getConsumer().getResourceObject(mainNode, OWL_HAS_VALUE, true);
            OWLIndividual individual = getConsumer().translateIndividual(verifyNotNull(value));
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
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
    static class ObjectIntersectionOfTranslator extends AbstractClassExpressionTranslator {

        ObjectIntersectionOfTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode, OWL_INTERSECTION_OF, false);
            return isClassExpressionStrict(mainNode) && isClassExpressionListStrict(listNode, 2);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_INTERSECTION_OF);
        }

        @Override
        public OWLObjectIntersectionOf translate(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode, OWL_INTERSECTION_OF, true);
            Set<OWLClassExpression> classExpressions = accessor.translateToClassExpressionSet(verifyNotNull(listNode));
            return getDataFactory().getOWLObjectIntersectionOf(classExpressions);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectMaxCardinalityTranslator extends AbstractClassExpressionTranslator {

        ObjectMaxCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MAX_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MAX_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLObjectMaxCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MAX_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
            if (fillerIRI != null && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLClassExpression filler = accessor.translateClassExpression(fillerIRI);
                return getDataFactory().getOWLObjectMaxCardinality(cardi, property, filler);
            } else {
                return getDataFactory().getOWLObjectMaxCardinality(cardi, property);
            }
        }
    }

    /**
     * @author Matthew Horridge, The University of Manchester, Bio-Health
     *         Informatics Group
     * @since 3.1.0
     */
    static class ObjectMaxQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

        ObjectMaxQualifiedCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MAX_QUALIFIED_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MAX_QUALIFIED_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY) && isClassExpressionLax(mainNode, OWL_ON_CLASS);
        }

        @Override
        public OWLObjectMaxCardinality translate(IRI mainNode) {
            int cardi = translateInteger(mainNode, OWL_MAX_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
            OWLClassExpression filler = accessor.translateClassExpression(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLObjectMaxCardinality(cardi, property, filler);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectMinCardinalityTranslator extends AbstractClassExpressionTranslator {

        ObjectMinCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MIN_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MIN_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY);
        }

        @Override
        public OWLObjectMinCardinality translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            int cardi = translateInteger(mainNode, OWL_MIN_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
            if (fillerIRI != null && !getConsumer().getConfiguration().isStrict()) {
                // Be tolerant
                OWLClassExpression filler = accessor.translateClassExpression(fillerIRI);
                return getDataFactory().getOWLObjectMinCardinality(cardi, property, filler);
            } else {
                return getDataFactory().getOWLObjectMinCardinality(cardi, property);
            }
        }
    }

    static class ObjectMinQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

        ObjectMinQualifiedCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_MIN_QUALIFIED_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_MIN_QUALIFIED_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY) && isClassExpressionLax(mainNode, OWL_ON_CLASS);
        }

        @Override
        public OWLObjectMinCardinality translate(IRI mainNode) {
            int cardi = translateInteger(mainNode, OWL_MIN_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
            OWLClassExpression filler = accessor.translateClassExpression(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLObjectMinCardinality(cardi, property, filler);
        }
    }

    static class ObjectOneOfTranslator extends AbstractClassExpressionTranslator {

        ObjectOneOfTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode, OWL_ONE_OF, false);
            return isIndividualListStrict(listNode, 1);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_ONE_OF);
        }

        @Override
        public OWLObjectOneOf translate(IRI mainNode) {
            IRI oneOfObject = getConsumer().getResourceObject(mainNode, OWL_ONE_OF, true);
            Set<OWLIndividual> individuals = accessor.translateToIndividualSet(verifyNotNull(oneOfObject));
            return getDataFactory().getOWLObjectOneOf(individuals);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectPropertyListItemTranslator implements ListItemTranslator<OWLObjectPropertyExpression> {

        private final OWLRDFConsumer consumer;

        ObjectPropertyListItemTranslator(OWLRDFConsumer consumer) {
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
        public @Nullable OWLObjectPropertyExpression translate(IRI firstObject) {
            consumer.addObjectProperty(firstObject, false);
            return consumer.translateObjectPropertyExpression(firstObject);
        }

        @Override
        public @Nullable OWLObjectPropertyExpression translate(OWLLiteral firstObject) {
            LOGGER.info(
                    "Cannot translate list item as an object property, because rdf:first triple is a literal triple");
            return null;
        }
    }

    /**
     * @author Matthew Horridge, The University of Manchester, Bio-Health
     *         Informatics Group
     * @since 3.1.0
     */
    static class ObjectQualifiedCardinalityTranslator extends AbstractClassExpressionTranslator {

        ObjectQualifiedCardinalityTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isNonNegativeIntegerStrict(mainNode, OWL_QUALIFIED_CARDINALITY)
                    && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_ON_CLASS);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isNonNegativeIntegerLax(mainNode, OWL_QUALIFIED_CARDINALITY)
                    && isObjectPropertyLax(mainNode, OWL_ON_PROPERTY) && isClassExpressionLax(mainNode, OWL_ON_CLASS);
        }

        @Override
        public OWLObjectExactCardinality translate(IRI mainNode) {
            int cardi = translateInteger(mainNode, OWL_QUALIFIED_CARDINALITY);
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_ON_CLASS, true);
            OWLClassExpression filler = accessor.translateClassExpression(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLObjectExactCardinality(cardi, property, filler);
        }
    }

    /**
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectSomeValuesFromTranslator extends AbstractClassExpressionTranslator {

        ObjectSomeValuesFromTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            return isRestrictionStrict(mainNode) && isObjectPropertyStrict(mainNode, OWL_ON_PROPERTY)
                    && isClassExpressionStrict(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isClassExpressionLax(mainNode, OWL_SOME_VALUES_FROM) && isResourcePresent(mainNode, OWL_ON_PROPERTY)
                    || isObjectPropertyLax(mainNode) && isResourcePresent(mainNode, OWL_SOME_VALUES_FROM);
        }

        @Override
        public OWLObjectSomeValuesFrom translate(IRI mainNode) {
            getConsumer().consumeTriple(mainNode, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
            IRI propertyIRI = getConsumer().getResourceObject(mainNode, OWL_ON_PROPERTY, true);
            OWLObjectPropertyExpression property = getConsumer()
                    .translateObjectPropertyExpression(verifyNotNull(propertyIRI));
            IRI fillerIRI = getConsumer().getResourceObject(mainNode, OWL_SOME_VALUES_FROM, true);
            OWLClassExpression filler = accessor.translateClassExpression(verifyNotNull(fillerIRI));
            return getDataFactory().getOWLObjectSomeValuesFrom(property, filler);
        }
    }

    /**
     * Translates a set of triples to an {@code OWLUnionOf}.
     * 
     * @author Matthew Horridge, The University Of Manchester, Bio-Health
     *         Informatics Group
     * @since 2.0.0
     */
    static class ObjectUnionOfTranslator extends AbstractClassExpressionTranslator {

        ObjectUnionOfTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            super(consumer, accessor);
        }

        @Override
        public boolean matchesStrict(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode, OWL_UNION_OF, false);
            return isClassExpressionStrict(mainNode) && isClassExpressionListStrict(listNode, 2);
        }

        @Override
        public boolean matchesLax(IRI mainNode) {
            return isResourcePresent(mainNode, OWL_UNION_OF);
        }

        @Override
        public OWLObjectUnionOf translate(IRI mainNode) {
            IRI listNode = getConsumer().getResourceObject(mainNode, OWL_UNION_OF, true);
            Set<OWLClassExpression> classExpressions = accessor.translateToClassExpressionSet(verifyNotNull(listNode));
            return getDataFactory().getOWLObjectUnionOf(classExpressions);
        }
    }

    static class SWRLAtomListItemTranslator implements ListItemTranslator<SWRLAtom> {

        private final OWLRDFConsumer consumer;
        protected final OWLDataFactory dataFactory;
        protected final TranslatorAccessor accessor;

        SWRLAtomListItemTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            this.consumer = consumer;
            this.accessor = accessor;
            dataFactory = consumer.getDataFactory();
        }

        @Override
        public @Nullable SWRLAtom translate(IRI firstObject) {
            if (consumer.isSWRLBuiltInAtom(firstObject)) {
                return builtin(firstObject);
            } else if (consumer.isSWRLClassAtom(firstObject)) {
                return classAtom(firstObject);
            } else if (consumer.isSWRLDataRangeAtom(firstObject)) {
                return dataRangeAtom(firstObject);
            } else if (consumer.isSWRLDataValuedPropertyAtom(firstObject)) {
                return dataValueAtom(firstObject);
            } else if (consumer.isSWRLIndividualPropertyAtom(firstObject)) {
                return individualAtom(firstObject);
            } else if (consumer.isSWRLSameAsAtom(firstObject)) {
                return sameAsAtom(firstObject);
            } else if (consumer.isSWRLDifferentFromAtom(firstObject)) {
                return differentFromAtom(firstObject);
            }
            throw new OWLRuntimeException("Don't know how to translate SWRL Atom: " + firstObject);
        }

        protected SWRLAtom differentFromAtom(IRI firstObject) {
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getIRI());
            SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getIRI());
            return dataFactory.getSWRLDifferentIndividualsAtom(arg1, arg2);
        }

        protected SWRLAtom sameAsAtom(IRI firstObject) {
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getIRI());
            SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getIRI());
            return dataFactory.getSWRLSameIndividualAtom(arg1, arg2);
        }

        protected SWRLAtom individualAtom(IRI firstObject) {
            IRI objectPropertyIRI = consumer.getResourceObject(firstObject, PROPERTY_PREDICATE.getIRI(), true);
            if (objectPropertyIRI == null) {
                throw new OWLRuntimeException(
                        "Don't know how to translate SWRL Atom: object property IRI is null " + firstObject);
            }
            OWLObjectPropertyExpression prop = consumer.translateObjectPropertyExpression(objectPropertyIRI);
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getIRI());
            SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getIRI());
            return dataFactory.getSWRLObjectPropertyAtom(prop, arg1, arg2);
        }

        protected SWRLAtom dataValueAtom(IRI firstObject) {
            IRI dataPropertyIRI = consumer.getResourceObject(firstObject, PROPERTY_PREDICATE.getIRI(), true);
            if (dataPropertyIRI == null) {
                throw new OWLRuntimeException(
                        "Don't know how to translate SWRL Atom: data property IRI is null " + firstObject);
            }
            OWLDataPropertyExpression prop = consumer.translateDataPropertyExpression(dataPropertyIRI);
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getIRI());
            SWRLDArgument arg2 = translateSWRLAtomDObject(firstObject, ARGUMENT_2.getIRI());
            return dataFactory.getSWRLDataPropertyAtom(prop, arg1, arg2);
        }

        protected SWRLAtom dataRangeAtom(IRI firstObject) {
            // DR(?x) or DR(val)
            IRI dataRangeIRI = consumer.getResourceObject(firstObject, DATA_RANGE.getIRI(), true);
            if (dataRangeIRI == null) {
                throw new OWLRuntimeException(
                        "Don't know how to translate SWRL Atom: data range IRI is null " + firstObject);
            }
            OWLDataRange dataRange = consumer.translateDataRange(dataRangeIRI);
            SWRLDArgument dObject = translateSWRLAtomDObject(firstObject, ARGUMENT_1.getIRI());
            return dataFactory.getSWRLDataRangeAtom(dataRange, dObject);
        }

        protected SWRLAtom classAtom(IRI firstObject) {
            // C(?x) or C(ind)
            IRI classIRI = consumer.getResourceObject(firstObject, CLASS_PREDICATE.getIRI(), true);
            if (classIRI == null) {
                throw new OWLRuntimeException(
                        "Don't know how to translate SWRL Atom: class IRI is null " + firstObject);
            }
            OWLClassExpression desc = accessor.translateClassExpression(classIRI);
            SWRLIArgument iObject = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getIRI());
            return dataFactory.getSWRLClassAtom(desc, iObject);
        }

        protected SWRLAtom builtin(IRI firstObject) {
            IRI builtInIRI = consumer.getResourceObject(firstObject, BUILT_IN.getIRI(), true);
            IRI mainIRI = consumer.getResourceObject(firstObject, ARGUMENTS.getIRI(), true);
            OptimisedListTranslator<SWRLDArgument> listTranslator = new OptimisedListTranslator<>(consumer,
                    new SWRLAtomDObjectListItemTranslator());
            List<SWRLDArgument> args = listTranslator.translateList(verifyNotNull(mainIRI));
            return dataFactory.getSWRLBuiltInAtom(verifyNotNull(builtInIRI), args);
        }

        @Override
        public @Nullable SWRLAtom translate(OWLLiteral firstObject) {
            throw new OWLRuntimeException("Unexpected literal in atom list: " + firstObject);
        }

        private SWRLIArgument translateSWRLAtomIObject(IRI mainIRI, IRI argPredicateIRI) {
            IRI argIRI = consumer.getResourceObject(mainIRI, argPredicateIRI, true);
            if (argIRI != null) {
                if (consumer.isSWRLVariable(argIRI)) {
                    return dataFactory.getSWRLVariable(argIRI);
                } else {
                    return dataFactory.getSWRLIndividualArgument(consumer.getOWLIndividual(argIRI));
                }
            } else {
                throw new OWLRuntimeException(
                        "Cannot translate SWRL Atom I-Object for " + argPredicateIRI + " Triple not found.");
            }
        }

        private SWRLDArgument translateSWRLAtomDObject(IRI mainIRI, IRI argPredicateIRI) {
            IRI argIRI = consumer.getResourceObject(mainIRI, argPredicateIRI, true);
            if (argIRI != null) {
                // Must be a variable -- double check
                if (!consumer.isSWRLVariable(argIRI)) {
                    LOGGER.info("Expected SWRL variable for SWRL Data Object: {} (possibly untyped)", argIRI);
                }
                return dataFactory.getSWRLVariable(argIRI);
            } else {
                // Must be a literal
                OWLLiteral con = consumer.getLiteralObject(mainIRI, argPredicateIRI, true);
                if (con != null) {
                    return dataFactory.getSWRLLiteralArgument(con);
                }
            }
            throw new IllegalStateException("Could not translate SWRL Atom D-Object");
        }

        private class SWRLAtomDObjectListItemTranslator implements ListItemTranslator<SWRLDArgument> {

            SWRLAtomDObjectListItemTranslator() {}

            @Override
            public @Nullable SWRLDArgument translate(IRI firstObject) {
                return dataFactory.getSWRLVariable(firstObject);
            }

            @Override
            public @Nullable SWRLDArgument translate(OWLLiteral firstObject) {
                return dataFactory.getSWRLLiteralArgument(firstObject);
            }
        }
    }

    static class SWRLRuleTranslator {

        private final OWLRDFConsumer consumer;
        private final OptimisedListTranslator<SWRLAtom> listTranslator;

        SWRLRuleTranslator(OWLRDFConsumer consumer, TranslatorAccessor accessor) {
            this.consumer = consumer;
            listTranslator = new OptimisedListTranslator<>(consumer,
                    new SWRLAtomListItemTranslator(consumer, accessor));
        }

        /**
         * @param mainNode
         *        rule to translate
         */
        public void translateRule(IRI mainNode) {
            IRI remappedNode = consumer.remapIRI(mainNode);
            Set<OWLAnnotation> annotations = new HashSet<>();
            Set<IRI> predicates = consumer.getPredicatesBySubject(remappedNode);
            for (IRI i : predicates) {
                if (consumer.isAnnotationProperty(i)) {
                    OWLAnnotationProperty p = consumer.getDataFactory().getOWLAnnotationProperty(i);
                    OWLLiteral literal = consumer.getLiteralObject(remappedNode, i, true);
                    while (literal != null) {
                        annotations.add(consumer.getDataFactory().getOWLAnnotation(p, literal));
                        literal = consumer.getLiteralObject(remappedNode, i, true);
                    }
                }
            }
            Set<SWRLAtom> consequent = Collections.emptySet();
            IRI ruleHeadIRI = consumer.getResourceObject(remappedNode, SWRLVocabulary.HEAD.getIRI(), true);
            if (ruleHeadIRI != null) {
                consequent = listTranslator.translateToSet(ruleHeadIRI);
            }
            Set<SWRLAtom> antecedent = Collections.emptySet();
            IRI ruleBodyIRI = consumer.getResourceObject(remappedNode, SWRLVocabulary.BODY.getIRI(), true);
            if (ruleBodyIRI != null) {
                antecedent = listTranslator.translateToSet(ruleBodyIRI);
            }
            SWRLRule rule = null;
            if (!consumer.isAnonymousNode(remappedNode)) {
                rule = consumer.getDataFactory().getSWRLRule(antecedent, consequent, annotations);
            } else {
                rule = consumer.getDataFactory().getSWRLRule(antecedent, consequent, annotations);
            }
            consumer.addAxiom(rule);
        }
    }

    static class TypedConstantListItemTranslator implements ListItemTranslator<OWLLiteral> {

        @Override
        public @Nullable OWLLiteral translate(IRI firstObject) {
            LOGGER.info("Cannot translate list item to a constant because rdf:first triple is a resource triple");
            return null;
        }

        @Override
        public @Nullable OWLLiteral translate(OWLLiteral firstObject) {
            return firstObject;
        }
    }
}
