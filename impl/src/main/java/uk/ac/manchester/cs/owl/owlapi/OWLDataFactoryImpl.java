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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.providers.ClassProvider;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLDataFactoryImpl
    implements OWLDataFactory, Serializable, ClassProvider {

    private static final long serialVersionUID = 40000L;
    // Distinguished Entities
    //@formatter:off 
    @Nonnull private static final OWLClass               OWL_THING                    = new OWLClassImpl(              OWLRDFVocabulary.OWL_THING.getIRI());
    @Nonnull private static final OWLClass               OWL_NOTHING                  = new OWLClassImpl(              OWLRDFVocabulary.OWL_NOTHING.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_LABEL                   = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_LABEL.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_COMMENT                 = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_SEE_ALSO                = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    @Nonnull private static final OWLAnnotationProperty  RDFS_IS_DEFINED_BY           = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_BACKWARD_COMPATIBLE_WITH = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_INCOMPATIBLE_WITH        = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_VERSION_INFO             = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    @Nonnull private static final OWLAnnotationProperty  OWL_DEPRECATED               = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    @Nonnull private static final OWLObjectProperty      OWL_TOP_OBJECT_PROPERTY      = new OWLObjectPropertyImpl(     OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    @Nonnull private static final OWLObjectProperty      OWL_BOTTOM_OBJECT_PROPERTY   = new OWLObjectPropertyImpl(     OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    @Nonnull private static final OWLDataProperty        OWL_TOP_DATA_PROPERTY        = new OWLDataPropertyImpl(       OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    @Nonnull private static final OWLDataProperty        OWL_BOTTOM_DATA_PROPERTY     = new OWLDataPropertyImpl(       OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    //@formatter:on
    private final OWLDataFactoryInternals dataFactoryInternals;

    /**
     * Constructs an OWLDataFactoryImpl that uses caching but no compression.
     */
    public OWLDataFactoryImpl() {
        this(new OWLDataFactoryInternalsImpl(false));
    }

    /**
     * @param dataFactoryInternals
     *        internals to use
     */
    @Inject
    public OWLDataFactoryImpl(OWLDataFactoryInternals dataFactoryInternals) {
        this.dataFactoryInternals = verifyNotNull(dataFactoryInternals);
    }

    @Override
    public void purge() {
        dataFactoryInternals.purge();
    }

    private static void checkAnnotations(@Nonnull Collection<OWLAnnotation> o) {
        checkIterableNotNull(o, "annotations cannot be null", true);
    }

    @Nonnull
    @Override
    public <E extends OWLEntity> E getOWLEntity(
        @Nonnull EntityType<E> entityType, IRI iri) {
        checkNotNull(entityType, "entityType cannot be null");
        checkNotNull(iri, "iri cannot be null");
        return entityType.buildEntity(iri, this);
    }

    @Override
    public OWLClass getOWLClass(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return dataFactoryInternals.getOWLClass(iri);
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSLabel() {
        return RDFS_LABEL;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSComment() {
        return RDFS_COMMENT;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSSeeAlso() {
        return RDFS_SEE_ALSO;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getRDFSIsDefinedBy() {
        return RDFS_IS_DEFINED_BY;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLVersionInfo() {
        return OWL_VERSION_INFO;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLBackwardCompatibleWith() {
        return OWL_BACKWARD_COMPATIBLE_WITH;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLIncompatibleWith() {
        return OWL_INCOMPATIBLE_WITH;
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLDeprecated() {
        return OWL_DEPRECATED;
    }

    @Nonnull
    @Override
    public OWLClass getOWLThing() {
        return OWL_THING;
    }

    @Nonnull
    @Override
    public OWLClass getOWLNothing() {
        return OWL_NOTHING;
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLBottomDataProperty() {
        return OWL_BOTTOM_DATA_PROPERTY;
    }

    @Nonnull
    @Override
    public OWLObjectProperty getOWLBottomObjectProperty() {
        return OWL_BOTTOM_OBJECT_PROPERTY;
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLTopDataProperty() {
        return OWL_TOP_DATA_PROPERTY;
    }

    @Nonnull
    @Override
    public OWLObjectProperty getOWLTopObjectProperty() {
        return OWL_TOP_OBJECT_PROPERTY;
    }

    @Override
    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return dataFactoryInternals.getOWLObjectProperty(iri);
    }

    @Override
    public OWLDataProperty getOWLDataProperty(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return dataFactoryInternals.getOWLDataProperty(iri);
    }

    @Override
    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return dataFactoryInternals.getOWLNamedIndividual(iri);
    }

    @Nonnull
    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId) {
        checkNotNull(nodeId, "id cannot be null");
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(nodeId));
    }

    @Nonnull
    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual() {
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(null));
    }

    @Override
    public OWLDatatype getOWLDatatype(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return dataFactoryInternals.getOWLDatatype(iri);
    }

    @Override
    public OWLLiteral getOWLLiteral(boolean value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Nonnull
    @Override
    public OWLDataOneOf getOWLDataOneOf(
        @Nonnull Set<? extends OWLLiteral> values) {
        checkIterableNotNull(values, "values cannot be null", true);
        return new OWLDataOneOfImpl(values);
    }

    @Nonnull
    @Override
    public OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        return new OWLDataComplementOfImpl(dataRange);
    }

    @Nonnull
    @Override
    public OWLDataIntersectionOf getOWLDataIntersectionOf(
        @Nonnull Collection<? extends OWLDataRange> dataRanges) {
        checkIterableNotNull(dataRanges, "dataRanges cannot be null", true);
        return new OWLDataIntersectionOfImpl(dataRanges);
    }

    @Nonnull
    @Override
    public OWLDataUnionOf getOWLDataUnionOf(
        @Nonnull Set<? extends OWLDataRange> dataRanges) {
        checkIterableNotNull(dataRanges, "dataRanges cannot be null", true);
        return new OWLDataUnionOfImpl(dataRanges);
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(
        OWLDatatype dataType,
        @Nonnull Set<OWLFacetRestriction> facetRestrictions) {
        checkNotNull(dataType, "datatype cannot be null");
        checkIterableNotNull(facetRestrictions, "facets", true);
        return new OWLDatatypeRestrictionImpl(dataType, facetRestrictions);
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(
        OWLDatatype dataType, OWLFacet facet, OWLLiteral typedLiteral) {
        checkNotNull(dataType, "datatype cannot be null");
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(typedLiteral, "typedConstant cannot be null");
        return new OWLDatatypeRestrictionImpl(dataType, CollectionFactory
            .createSet(getOWLFacetRestriction(facet, typedLiteral)));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
        OWLLiteral facetValue) {
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(facetValue, "facetValue cannot be null");
        return new OWLFacetRestrictionImpl(facet, facetValue);
    }

    @Nonnull
    @Override
    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(
        @Nonnull Collection<? extends OWLClassExpression> operands) {
        checkIterableNotNull(operands, "operands cannot be null", true);
        return new OWLObjectIntersectionOfImpl(operands);
    }

    @Nonnull
    @Override
    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLDataAllValuesFromImpl(property, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataExactCardinalityImpl(property, cardinality,
            getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLDataExactCardinalityImpl(property, cardinality,
            dataRange);
    }

    @Nonnull
    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMaxCardinalityImpl(property, cardinality,
            getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        checkNotNull(dataRange, "dataRange cannot be null");
        return new OWLDataMaxCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMinCardinalityImpl(property, cardinality,
            getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMinCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(
        OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLDataSomeValuesFromImpl(property, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataHasValue getOWLDataHasValue(
        OWLDataPropertyExpression property, OWLLiteral value) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        return new OWLDataHasValueImpl(property, value);
    }

    @Nonnull
    @Override
    public OWLObjectComplementOf getOWLObjectComplementOf(
        OWLClassExpression operand) {
        checkNotNull(operand, "operand");
        return new OWLObjectComplementOfImpl(operand);
    }

    @Nonnull
    @Override
    public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectAllValuesFromImpl(property, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectOneOf getOWLObjectOneOf(
        @Nonnull Set<? extends OWLIndividual> values) {
        checkIterableNotNull(values, "values cannot be null", true);
        return new OWLObjectOneOfImpl(values);
    }

    @Nonnull
    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(
        int cardinality, OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectExactCardinalityImpl(property, cardinality,
            OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(
        int cardinality, OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLObjectExactCardinalityImpl(property, cardinality,
            classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMinCardinalityImpl(property, cardinality,
            OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLObjectMinCardinalityImpl(property, cardinality,
            classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMaxCardinalityImpl(property, cardinality,
            OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMaxCardinalityImpl(property, cardinality,
            classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectHasSelf getOWLObjectHasSelf(
        OWLObjectPropertyExpression property) {
        checkNotNull(property, "property cannot be null");
        return new OWLObjectHasSelfImpl(property);
    }

    @Nonnull
    @Override
    public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectSomeValuesFromImpl(property, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectHasValue getOWLObjectHasValue(
        OWLObjectPropertyExpression property, OWLIndividual individual) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(individual, "individual cannot be null");
        return new OWLObjectHasValueImpl(property, individual);
    }

    @Nonnull
    @Override
    public OWLObjectUnionOf getOWLObjectUnionOf(
        @Nonnull Set<? extends OWLClassExpression> operands) {
        checkIterableNotNull(operands, "operands cannot be null", true);
        return new OWLObjectUnionOfImpl(operands);
    }

    @Nonnull
    @Override
    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression propertyExpression,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(propertyExpression, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLAsymmetricObjectPropertyAxiomImpl(propertyExpression,
            annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
        OWLDataPropertyExpression property, OWLClassExpression domain,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyDomainAxiomImpl(property, domain,
            annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
        OWLDataPropertyExpression property, OWLDataRange owlDataRange,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(owlDataRange, "owlDataRange cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyRangeAxiomImpl(property, owlDataRange,
            annotations);
    }

    @Nonnull
    @Override
    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
        OWLDataPropertyExpression subProperty,
        OWLDataPropertyExpression superProperty,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubDataPropertyOfAxiomImpl(subProperty, superProperty,
            annotations);
    }

    @Nonnull
    @Override
    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(owlEntity, "owlEntity cannot be null");
        checkAnnotations(annotations);
        return new OWLDeclarationAxiomImpl(owlEntity, annotations);
    }

    @Nonnull
    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
        @Nonnull Set<? extends OWLIndividual> individuals,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(individuals, "individuals cannot be null", true);
        checkAnnotations(annotations);
        return new OWLDifferentIndividualsAxiomImpl(individuals, annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        @Nonnull Set<? extends OWLClassExpression> classExpressions,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(classExpressions,
            "classExpressions cannot be null or contain null", true);
        checkAnnotations(annotations);
        // Hack to handle the case where classExpressions has only a single
        // member
        // which will usually be the result of :x owl:disjointWith :x .
        if (classExpressions.size() == 1) {
            Set<OWLClassExpression> modifiedClassExpressions = new HashSet<>(2);
            OWLClassExpression classExpression = classExpressions.iterator()
                .next();
            OWLClass addedClass = classExpression.isOWLThing() ? OWL_NOTHING
                : OWL_THING;
            modifiedClassExpressions.add(addedClass);
            modifiedClassExpressions.add(classExpression);
            return getOWLDisjointClassesAxiom(modifiedClassExpressions,
                makeSingletonDisjoinClassWarningAnnotation(annotations,
                    classExpression, addedClass));
        }
        return new OWLDisjointClassesAxiomImpl(classExpressions, annotations);
    }

    protected Set<OWLAnnotation> makeSingletonDisjoinClassWarningAnnotation(
        Set<OWLAnnotation> annotations, OWLClassExpression classExpression,
        OWLClassExpression addedClass) {
        Set<OWLAnnotation> modifiedAnnotations = new HashSet<>(
            annotations.size() + 1);
        modifiedAnnotations.addAll(annotations);
        String provenanceComment = String.format("%s on %s",
            VersionInfo.getVersionInfo().getGeneratedByMessage(),
            new SimpleDateFormat().format(new Date()));
        OWLAnnotation provenanceAnnotation = getOWLAnnotation(RDFS_COMMENT,
            getOWLLiteral(provenanceComment));
        Set<OWLAnnotation> metaAnnotations = Collections
            .singleton(provenanceAnnotation);
        String changeComment = String.format(
            "DisjointClasses(%s) replaced by DisjointClasses(%s %s)",
            classExpression, classExpression, addedClass);
        modifiedAnnotations.add(getOWLAnnotation(RDFS_COMMENT,
            getOWLLiteral(changeComment), metaAnnotations));
        return modifiedAnnotations;
    }

    @Nonnull
    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        @Nonnull Set<? extends OWLDataPropertyExpression> properties,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLDisjointDataPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLDisjointObjectPropertiesAxiomImpl(properties,
            annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
        @Nonnull Set<? extends OWLClassExpression> classExpressions,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(classExpressions,
            "classExpressions cannot be null", true);
        checkAnnotations(annotations);
        return new OWLEquivalentClassesAxiomImpl(classExpressions, annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
        @Nonnull Set<? extends OWLDataPropertyExpression> properties,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLEquivalentDataPropertiesAxiomImpl(properties,
            annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
        OWLDataPropertyExpression property,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLFunctionalDataPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLImportsDeclaration getOWLImportsDeclaration(
        IRI importedOntologyIRI) {
        checkNotNull(importedOntologyIRI, "importedOntologyIRI cannot be null");
        return new OWLImportsDeclarationImpl(importedOntologyIRI);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject,
        OWLLiteral object, @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyAssertionAxiomImpl(subject, property, object,
            annotations);
    }

    @Nonnull
    @Override
    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject,
        OWLLiteral object, @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLNegativeDataPropertyAssertionAxiomImpl(subject, property,
            object, annotations);
    }

    @Nonnull
    @Override
    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property, OWLIndividual subject,
        OWLIndividual object, @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(subject,
            property, object, annotations);
    }

    @Nonnull
    @Override
    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(
        OWLClassExpression classExpression, OWLIndividual individual,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(individual, "individual cannot be null");
        checkAnnotations(annotations);
        return new OWLClassAssertionAxiomImpl(individual, classExpression,
            annotations);
    }

    @Nonnull
    @Override
    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(property,
            annotations);
    }

    @Nonnull
    @Override
    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLIrreflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(classExpression, "classExpression cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyDomainAxiomImpl(property, classExpression,
            annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
        OWLObjectPropertyExpression property, OWLClassExpression range,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyRangeAxiomImpl(property, range,
            annotations);
    }

    @Nonnull
    @Override
    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
        OWLObjectPropertyExpression subProperty,
        OWLObjectPropertyExpression superProperty,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubObjectPropertyOfAxiomImpl(subProperty, superProperty,
            annotations);
    }

    @Nonnull
    @Override
    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLReflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(
        @Nonnull Set<? extends OWLIndividual> individuals,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(individuals, "individuals cannot be null", true);
        checkAnnotations(annotations);
        return new OWLSameIndividualAxiomImpl(individuals, annotations);
    }

    @Nonnull
    @Override
    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
        OWLClassExpression superClass,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(subClass, "subclass cannot be null");
        checkNotNull(superClass, "superclass cannot be null");
        checkAnnotations(annotations);
        return new OWLSubClassOfAxiomImpl(subClass, superClass, annotations);
    }

    @Nonnull
    @Override
    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLSymmetricObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLTransitiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLObjectInverseOf getOWLObjectInverseOf(
        OWLObjectPropertyExpression property) {
        checkNotNull(property, "property cannot be null");
        return new OWLObjectInverseOfImpl(property);
    }

    @Nonnull
    @Override
    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
        OWLObjectPropertyExpression forwardProperty,
        OWLObjectPropertyExpression inverseProperty,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(forwardProperty, "forwardProperty cannot be null");
        checkNotNull(inverseProperty, "inverseProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLInverseObjectPropertiesAxiomImpl(forwardProperty,
            inverseProperty, annotations);
    }

    @Nonnull
    @Override
    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
        @Nonnull List<? extends OWLObjectPropertyExpression> chain,
        OWLObjectPropertyExpression superProperty,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(superProperty, "superProperty cannot be null");
        checkIterableNotNull(chain, "chain cannot be null", true);
        checkAnnotations(annotations);
        return new OWLSubPropertyChainAxiomImpl(chain, superProperty,
            annotations);
    }

    @Nonnull
    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
        @Nonnull Set<? extends OWLPropertyExpression> objectProperties,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(ce, "classExpression cannot be null");
        checkIterableNotNull(objectProperties, "properties cannot be null",
            true);
        checkAnnotations(annotations);
        return new OWLHasKeyAxiomImpl(ce, objectProperties, annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
        @Nonnull Set<? extends OWLClassExpression> classExpressions,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(owlClass, "owlClass cannot be null");
        checkIterableNotNull(classExpressions,
            "classExpressions cannot be null", true);
        checkAnnotations(annotations);
        return new OWLDisjointUnionAxiomImpl(owlClass, classExpressions,
            annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
        @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLEquivalentObjectPropertiesAxiomImpl(properties,
            annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property, OWLIndividual individual,
        OWLIndividual object, @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(individual, "individual cannot be null");
        checkNotNull(object, "object cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyAssertionAxiomImpl(individual, property,
            object, annotations);
    }

    @Nonnull
    @Override
    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
        OWLAnnotationProperty sub, OWLAnnotationProperty sup,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(sub, "subProperty cannot be null");
        checkNotNull(sup, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubAnnotationPropertyOfAxiomImpl(sub, sup, annotations);
    }

    // Annotations
    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return dataFactoryInternals.getOWLAnnotationProperty(iri);
    }

    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
        OWLAnnotationValue value) {
        return new OWLAnnotationImplNotAnnotated(property, value);
    }

    @Nonnull
    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
        OWLAnnotationValue value, @Nonnull Stream<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        checkNotNull(annotations, "annotations cannot be null");
        return dataFactoryInternals.getOWLAnnotation(property, value,
            annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
        OWLAnnotationSubject subject, @Nonnull OWLAnnotation annotation) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject,
            annotation.getValue(), asList(annotation.annotations()));
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
        OWLAnnotationSubject subject, @Nonnull OWLAnnotation annotation,
        @Nonnull Collection<OWLAnnotation> annotations) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject,
            annotation.getValue(), annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
        OWLAnnotationProperty property, OWLAnnotationSubject subject,
        OWLAnnotationValue value,
        @Nonnull Collection<OWLAnnotation> annotations) {
        checkNotNull(subject, "subject cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationAssertionAxiomImpl(subject, property, value,
            annotations);
    }

    @Override
    public OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(
        IRI subject) {
        checkNotNull(subject, "subject cannot be null");
        return getOWLAnnotationAssertionAxiom(getOWLDeprecated(), subject,
            getOWLLiteral(true));
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
        OWLAnnotationProperty prop, IRI domain,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(prop, "property cannot be null");
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyDomainAxiomImpl(prop, domain,
            annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
        OWLAnnotationProperty prop, IRI range,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(prop, "property cannot be null");
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyRangeAxiomImpl(prop, range,
            annotations);
    }

    // SWRL
    @Nonnull
    @Override
    public SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> body,
        @Nonnull Set<? extends SWRLAtom> head,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkIterableNotNull(body, "body cannot be null", true);
        checkIterableNotNull(head, "head cannot be null", true);
        checkAnnotations(annotations);
        return new SWRLRuleImpl(body, head, annotations);
    }

    @Nonnull
    @Override
    public SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> body,
        @Nonnull Set<? extends SWRLAtom> head) {
        checkIterableNotNull(body, "antecedent cannot be null", true);
        checkIterableNotNull(head, "consequent cannot be null", true);
        return new SWRLRuleImpl(body, head);
    }

    @Nonnull
    @Override
    public SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate,
        SWRLIArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLClassAtomImpl(predicate, arg);
    }

    @Nonnull
    @Override
    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate,
        SWRLDArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLDataRangeAtomImpl(predicate, arg);
    }

    @Nonnull
    @Override
    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(
        OWLObjectPropertyExpression property, SWRLIArgument arg0,
        SWRLIArgument arg1) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLObjectPropertyAtomImpl(property, arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLDataPropertyAtom getSWRLDataPropertyAtom(
        OWLDataPropertyExpression property, SWRLIArgument arg0,
        SWRLDArgument arg1) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLDataPropertyAtomImpl(property, arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI,
        List<SWRLDArgument> args) {
        checkNotNull(builtInIRI, "builtInIRI cannot be null");
        checkNotNull(args, "args cannot be null");
        return new SWRLBuiltInAtomImpl(builtInIRI, args);
    }

    @Nonnull
    @Override
    public SWRLVariable getSWRLVariable(IRI var) {
        checkNotNull(var, "var cannot be null");
        return new SWRLVariableImpl(var);
    }

    @Nonnull
    @Override
    public SWRLIndividualArgument getSWRLIndividualArgument(
        OWLIndividual individual) {
        checkNotNull(individual, "individual cannot be null");
        return new SWRLIndividualArgumentImpl(individual);
    }

    @Nonnull
    @Override
    public SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal) {
        checkNotNull(literal, "literal");
        return new SWRLLiteralArgumentImpl(literal);
    }

    @Nonnull
    @Override
    public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(
        SWRLIArgument arg0, SWRLIArgument arg1) {
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLDifferentIndividualsAtomImpl(
            getOWLObjectProperty(OWLRDFVocabulary.OWL_DIFFERENT_FROM), arg0,
            arg1);
    }

    @Nonnull
    @Override
    public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0,
        SWRLIArgument arg1) {
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLSameIndividualAtomImpl(
            getOWLObjectProperty(OWLRDFVocabulary.OWL_SAME_AS), arg0, arg1);
    }

    @Nonnull
    @Override
    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(
        OWLDatatype datatype, OWLDataRange dataRange,
        @Nonnull Set<OWLAnnotation> annotations) {
        checkNotNull(datatype, "datatype cannot be null");
        checkNotNull(dataRange, "dataRange cannot be null");
        checkAnnotations(annotations);
        return new OWLDatatypeDefinitionAxiomImpl(datatype, dataRange,
            annotations);
    }

    @Override
    public OWLLiteral getOWLLiteral(String lexicalValue, OWLDatatype datatype) {
        checkNotNull(lexicalValue, "lexicalValue cannot be null");
        checkNotNull(datatype, "datatype cannot be null");
        return dataFactoryInternals.getOWLLiteral(lexicalValue, datatype);
    }

    @Override
    public OWLLiteral getOWLLiteral(int value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(double value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(float value) {
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(String value) {
        checkNotNull(value, "value cannot be null");
        return dataFactoryInternals.getOWLLiteral(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(String literal, String lang) {
        checkNotNull(literal, "literal cannot be null");
        return dataFactoryInternals.getOWLLiteral(literal, lang);
    }

    @Override
    public OWLDatatype getBooleanOWLDatatype() {
        return InternalizedEntities.XSDBOOLEAN;
    }

    @Override
    public OWLDatatype getStringOWLDatatype() {
        return InternalizedEntities.XSDSTRING;
    }

    @Override
    public OWLDatatype getDoubleOWLDatatype() {
        return InternalizedEntities.XSDDOUBLE;
    }

    @Override
    public OWLDatatype getFloatOWLDatatype() {
        return InternalizedEntities.XSDFLOAT;
    }

    @Override
    public OWLDatatype getIntegerOWLDatatype() {
        return InternalizedEntities.XSDINTEGER;
    }

    @Override
    public OWLDatatype getTopDatatype() {
        return InternalizedEntities.RDFSLITERAL;
    }

    @Override
    public OWLDatatype getRDFPlainLiteral() {
        return InternalizedEntities.PLAIN;
    }
}
