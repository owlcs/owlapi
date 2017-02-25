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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNegative;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_BACKWARD_COMPATIBLE_WITH;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_BOTTOM_DATA_PROPERTY;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_BOTTOM_OBJECT_PROPERTY;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_DEPRECATED;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_INCOMPATIBLE_WITH;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_NOTHING;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_THING;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_TOP_DATA_PROPERTY;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_TOP_OBJECT_PROPERTY;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.OWL_VERSION_INFO;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.RDFS_COMMENT;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.RDFS_IS_DEFINED_BY;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.RDFS_LABEL;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.RDFS_SEE_ALSO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.providers.ClassProvider;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLDataFactoryImpl implements OWLDataFactory, Serializable, ClassProvider {

    private final boolean useCompression;
    private transient OWLDataFactoryInternals dataFactoryInternals;

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        dataFactoryInternals = new OWLDataFactoryInternalsImpl(useCompression);
    }

    /**
     * Constructs an OWLDataFactoryImpl that uses caching but no compression.
     */
    public OWLDataFactoryImpl() {
        this(false);
    }

    /**
     * @param useCompression true if compression should be used
     */
    @Inject
    public OWLDataFactoryImpl(@CompressionEnabled boolean useCompression) {
        this.useCompression = useCompression;
        dataFactoryInternals = new OWLDataFactoryInternalsImpl(this.useCompression);
    }

    @Override
    public void purge() {
        dataFactoryInternals.purge();
    }

    private static void checkAnnotations(Collection<OWLAnnotation> o) {
        checkIterableNotNull(o, "annotations cannot be null", true);
    }

    @Override
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType, IRI iri) {
        checkNotNull(entityType, "entityType cannot be null");
        checkNotNull(iri, "iri cannot be null");
        return entityType.buildEntity(iri, this);
    }

    @Override
    public OWLClass getOWLClass(IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return dataFactoryInternals.getOWLClass(iri);
    }

    @Override
    public OWLAnnotationProperty getRDFSLabel() {
        return RDFS_LABEL;
    }

    @Override
    public OWLAnnotationProperty getRDFSComment() {
        return RDFS_COMMENT;
    }

    @Override
    public OWLAnnotationProperty getRDFSSeeAlso() {
        return RDFS_SEE_ALSO;
    }

    @Override
    public OWLAnnotationProperty getRDFSIsDefinedBy() {
        return RDFS_IS_DEFINED_BY;
    }

    @Override
    public OWLAnnotationProperty getOWLVersionInfo() {
        return OWL_VERSION_INFO;
    }

    @Override
    public OWLAnnotationProperty getOWLBackwardCompatibleWith() {
        return OWL_BACKWARD_COMPATIBLE_WITH;
    }

    @Override
    public OWLAnnotationProperty getOWLIncompatibleWith() {
        return OWL_INCOMPATIBLE_WITH;
    }

    @Override
    public OWLAnnotationProperty getOWLDeprecated() {
        return OWL_DEPRECATED;
    }

    @Override
    public OWLClass getOWLThing() {
        return OWL_THING;
    }

    @Override
    public OWLClass getOWLNothing() {
        return OWL_NOTHING;
    }

    @Override
    public OWLDataProperty getOWLBottomDataProperty() {
        return OWL_BOTTOM_DATA_PROPERTY;
    }

    @Override
    public OWLObjectProperty getOWLBottomObjectProperty() {
        return OWL_BOTTOM_OBJECT_PROPERTY;
    }

    @Override
    public OWLDataProperty getOWLTopDataProperty() {
        return OWL_TOP_DATA_PROPERTY;
    }

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

    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId) {
        checkNotNull(nodeId, "id cannot be null");
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(nodeId));
    }

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

    @Override
    public OWLDataOneOf getOWLDataOneOf(Stream<? extends OWLLiteral> values) {
        return new OWLDataOneOfImpl(values);
    }

    @Override
    public OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        return new OWLDataComplementOfImpl(dataRange);
    }

    @Override
    public OWLDataComplementOf getOWLDataComplementOf(OWL2Datatype dataRange) {
        return getOWLDataComplementOf(dataRange.getDatatype(this));
    }

    @Override
    public OWLDataIntersectionOf getOWLDataIntersectionOf(
        Stream<? extends OWLDataRange> dataRanges) {
        return new OWLDataIntersectionOfImpl(dataRanges.map(x -> x));
    }

    @Override
    public OWLDataUnionOf getOWLDataUnionOf(Stream<? extends OWLDataRange> dataRanges) {
        return new OWLDataUnionOfImpl(dataRanges.map(x -> x));
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataType,
        Collection<OWLFacetRestriction> facetRestrictions) {
        checkNotNull(dataType, "datatype cannot be null");
        checkIterableNotNull(facetRestrictions, "facets", true);
        return new OWLDatatypeRestrictionImpl(dataType, facetRestrictions);
    }

    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataType, OWLFacet facet,
        OWLLiteral typedLiteral) {
        checkNotNull(dataType, "datatype cannot be null");
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(typedLiteral, "typedConstant cannot be null");
        return new OWLDatatypeRestrictionImpl(dataType,
            CollectionFactory.createSet(getOWLFacetRestriction(facet,
                typedLiteral)));
    }

    @Override
    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, OWLLiteral facetValue) {
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(facetValue, "facetValue cannot be null");
        return new OWLFacetRestrictionImpl(facet, facetValue);
    }

    @Override
    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(
        Stream<? extends OWLClassExpression> operands) {
        return new OWLObjectIntersectionOfImpl(operands.map(x -> x));
    }

    @Override
    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property,
        OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLDataAllValuesFromImpl(property, dataRange);
    }

    @Override
    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property,
        OWL2Datatype dataRange) {
        return getOWLDataAllValuesFrom(property, dataRange.getDatatype(this));
    }

    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataExactCardinalityImpl(property, cardinality, getTopDatatype());
    }

    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
        OWLDataPropertyExpression property,
        OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLDataExactCardinalityImpl(property, cardinality, dataRange);
    }

    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
        OWLDataPropertyExpression property,
        OWL2Datatype dataRange) {
        return getOWLDataExactCardinality(cardinality, property, dataRange.getDatatype(this));
    }

    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMaxCardinalityImpl(property, cardinality, getTopDatatype());
    }

    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
        OWLDataPropertyExpression property,
        OWLDataRange dataRange) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        checkNotNull(dataRange, "dataRange cannot be null");
        return new OWLDataMaxCardinalityImpl(property, cardinality, dataRange);
    }

    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
        OWLDataPropertyExpression property,
        OWL2Datatype dataRange) {
        return getOWLDataMaxCardinality(cardinality, property, dataRange.getDatatype(this));
    }

    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
        OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMinCardinalityImpl(property, cardinality, getTopDatatype());
    }

    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
        OWLDataPropertyExpression property,
        OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMinCardinalityImpl(property, cardinality, dataRange);
    }

    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
        OWLDataPropertyExpression property,
        OWL2Datatype dataRange) {
        return getOWLDataMinCardinality(cardinality, property, dataRange.getDatatype(this));
    }

    @Override
    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property,
        OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLDataSomeValuesFromImpl(property, dataRange);
    }

    @Override
    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property,
        OWL2Datatype dataRange) {
        return getOWLDataSomeValuesFrom(property, dataRange.getDatatype(this));
    }

    @Override
    public OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property,
        OWLLiteral value) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        return new OWLDataHasValueImpl(property, value);
    }

    @Override
    public OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand) {
        checkNotNull(operand, "operand");
        return new OWLObjectComplementOfImpl(operand);
    }

    @Override
    public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectAllValuesFromImpl(property, classExpression);
    }

    @Override
    public OWLObjectOneOf getOWLObjectOneOf(Stream<? extends OWLIndividual> values) {
        return new OWLObjectOneOfImpl(values.map(x -> x));
    }

    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectExactCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLObjectExactCardinalityImpl(property, cardinality, classExpression);
    }

    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMinCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLObjectMinCardinalityImpl(property, cardinality, classExpression);
    }

    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
        OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMaxCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMaxCardinalityImpl(property, cardinality, classExpression);
    }

    @Override
    public OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property) {
        checkNotNull(property, "property cannot be null");
        return new OWLObjectHasSelfImpl(property);
    }

    @Override
    public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property,
        OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectSomeValuesFromImpl(property, classExpression);
    }

    @Override
    public OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property,
        OWLIndividual individual) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(individual, "individual cannot be null");
        return new OWLObjectHasValueImpl(property, individual);
    }

    @Override
    public OWLObjectUnionOf getOWLObjectUnionOf(Stream<? extends OWLClassExpression> operands) {
        return new OWLObjectUnionOfImpl(operands.map(x -> x));
    }

    @Override
    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression propertyExpression, Collection<OWLAnnotation> annotations) {
        checkNotNull(propertyExpression, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLAsymmetricObjectPropertyAxiomImpl(propertyExpression, annotations);
    }

    @Override
    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
        OWLDataPropertyExpression property,
        OWLClassExpression domain, Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyDomainAxiomImpl(property, domain, annotations);
    }

    @Override
    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
        OWLDataPropertyExpression property,
        OWLDataRange owlDataRange, Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(owlDataRange, "owlDataRange cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyRangeAxiomImpl(property, owlDataRange, annotations);
    }

    @Override
    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
        OWLDataPropertyExpression property,
        OWL2Datatype owlDataRange, Collection<OWLAnnotation> annotations) {
        return getOWLDataPropertyRangeAxiom(property, owlDataRange.getDatatype(this), annotations);
    }

    @Override
    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
        OWLDataPropertyExpression subProperty,
        OWLDataPropertyExpression superProperty, Collection<OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubDataPropertyOfAxiomImpl(subProperty, superProperty, annotations);
    }

    @Override
    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(owlEntity, "owlEntity cannot be null");
        checkAnnotations(annotations);
        return new OWLDeclarationAxiomImpl(owlEntity, annotations);
    }

    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
        Collection<? extends OWLIndividual> individuals,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(individuals, "individuals cannot be null", true);
        checkAnnotations(annotations);
        return new OWLDifferentIndividualsAxiomImpl(individuals, annotations);
    }

    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
        Collection<? extends OWLClassExpression> classExpressions,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(classExpressions, "classExpressions cannot be null or contain null",
            true);
        checkAnnotations(annotations);
        // Hack to handle the case where classExpressions has only a single
        // member
        // which will usually be the result of :x owl:disjointWith :x .
        if (classExpressions.size() == 1) {
            Set<OWLClassExpression> modifiedClassExpressions = new HashSet<>(2);
            OWLClassExpression classExpression = classExpressions.iterator().next();
            OWLClass addedClass = classExpression.isOWLThing() ? OWL_NOTHING : OWL_THING;
            modifiedClassExpressions.add(addedClass);
            modifiedClassExpressions.add(classExpression);
            return getOWLDisjointClassesAxiom(modifiedClassExpressions,
                makeSingletonDisjoinClassWarningAnnotation(
                    annotations, classExpression, addedClass));
        }
        return new OWLDisjointClassesAxiomImpl(classExpressions, annotations);
    }

    protected Set<OWLAnnotation> makeSingletonDisjoinClassWarningAnnotation(
        Collection<OWLAnnotation> annotations,
        OWLClassExpression classExpression, OWLClassExpression addedClass) {
        Set<OWLAnnotation> modifiedAnnotations = new HashSet<>(annotations.size() + 1);
        modifiedAnnotations.addAll(annotations);
        String provenanceComment = String
            .format("%s on %s", VersionInfo.getVersionInfo().getGeneratedByMessage(),
                new SimpleDateFormat().format(new Date()));
        OWLAnnotation provenanceAnnotation = getOWLAnnotation(RDFS_COMMENT,
            getOWLLiteral(provenanceComment));
        Set<OWLAnnotation> metaAnnotations = Collections.singleton(provenanceAnnotation);
        String changeComment = String
            .format("DisjointClasses(%s) replaced by DisjointClasses(%s %s)", classExpression,
                classExpression, addedClass);
        modifiedAnnotations
            .add(getOWLAnnotation(RDFS_COMMENT, getOWLLiteral(changeComment), metaAnnotations));
        return modifiedAnnotations;
    }

    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
        Collection<? extends OWLDataPropertyExpression> properties,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLDisjointDataPropertiesAxiomImpl(properties, annotations);
    }

    @Override
    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
        Collection<? extends OWLObjectPropertyExpression> properties,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLDisjointObjectPropertiesAxiomImpl(properties, annotations);
    }

    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
        Collection<? extends OWLClassExpression> classExpressions,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(classExpressions, "classExpressions cannot be null", true);
        checkAnnotations(annotations);
        return new OWLEquivalentClassesAxiomImpl(classExpressions, annotations);
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
        Collection<? extends OWLDataPropertyExpression> properties,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLEquivalentDataPropertiesAxiomImpl(properties, annotations);
    }

    @Override
    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
        OWLDataPropertyExpression property,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLFunctionalDataPropertyAxiomImpl(property, annotations);
    }

    @Override
    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    @Override
    public OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI) {
        checkNotNull(importedOntologyIRI, "importedOntologyIRI cannot be null");
        return new OWLImportsDeclarationImpl(importedOntologyIRI);
    }

    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property,
        OWLIndividual subject, OWLLiteral object, Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyAssertionAxiomImpl(subject, property, object, annotations);
    }

    @Override
    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(
        OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLNegativeDataPropertyAssertionAxiomImpl(subject, property, object,
            annotations);
    }

    @Override
    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(subject, property, object,
            annotations);
    }

    @Override
    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression,
        OWLIndividual individual, Collection<OWLAnnotation> annotations) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(individual, "individual cannot be null");
        checkAnnotations(annotations);
        return new OWLClassAssertionAxiomImpl(individual, classExpression, annotations);
    }

    @Override
    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
        OWLObjectPropertyExpression property, Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    @Override
    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLIrreflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Override
    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
        OWLObjectPropertyExpression property,
        OWLClassExpression classExpression, Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(classExpression, "classExpression cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyDomainAxiomImpl(property, classExpression, annotations);
    }

    @Override
    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
        OWLObjectPropertyExpression property,
        OWLClassExpression range, Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyRangeAxiomImpl(property, range, annotations);
    }

    @Override
    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
        OWLObjectPropertyExpression subProperty,
        OWLObjectPropertyExpression superProperty, Collection<OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubObjectPropertyOfAxiomImpl(subProperty, superProperty, annotations);
    }

    @Override
    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLReflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(
        Collection<? extends OWLIndividual> individuals,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(individuals, "individuals cannot be null", true);
        checkAnnotations(annotations);
        return new OWLSameIndividualAxiomImpl(individuals, annotations);
    }

    @Override
    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
        OWLClassExpression superClass,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(subClass, "subclass cannot be null");
        checkNotNull(superClass, "superclass cannot be null");
        checkAnnotations(annotations);
        return new OWLSubClassOfAxiomImpl(subClass, superClass, annotations);
    }

    @Override
    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLSymmetricObjectPropertyAxiomImpl(property, annotations);
    }

    @Override
    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
        OWLObjectPropertyExpression property,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLTransitiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Override
    public OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectProperty property) {
        checkNotNull(property, "property cannot be null");
        return new OWLObjectInverseOfImpl(property);
    }

    @Override
    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
        OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(forwardProperty, "forwardProperty cannot be null");
        checkNotNull(inverseProperty, "inverseProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLInverseObjectPropertiesAxiomImpl(forwardProperty, inverseProperty,
            annotations);
    }

    @Override
    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
        List<? extends OWLObjectPropertyExpression> chain,
        OWLObjectPropertyExpression superProperty, Collection<OWLAnnotation> annotations) {
        checkNotNull(superProperty, "superProperty cannot be null");
        checkIterableNotNull(chain, "chain cannot be null", true);
        checkAnnotations(annotations);
        return new OWLSubPropertyChainAxiomImpl(chain, superProperty, annotations);
    }

    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
        Collection<? extends OWLPropertyExpression> objectProperties,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(ce, "classExpression cannot be null");
        checkIterableNotNull(objectProperties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLHasKeyAxiomImpl(ce, objectProperties, annotations);
    }

    @Override
    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
        Stream<? extends OWLClassExpression> classExpressions,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(owlClass, "owlClass cannot be null");
        checkAnnotations(annotations);
        return new OWLDisjointUnionAxiomImpl(owlClass, classExpressions.map(x -> x), annotations);
    }

    @Override
    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
        Collection<? extends OWLObjectPropertyExpression> properties,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(properties, "properties cannot be null", true);
        checkAnnotations(annotations);
        return new OWLEquivalentObjectPropertiesAxiomImpl(properties, annotations);
    }

    @Override
    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
        OWLObjectPropertyExpression property,
        OWLIndividual individual, OWLIndividual object, Collection<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(individual, "individual cannot be null");
        checkNotNull(object, "object cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyAssertionAxiomImpl(individual, property, object, annotations);
    }

    @Override
    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
        OWLAnnotationProperty sub,
        OWLAnnotationProperty sup, Collection<OWLAnnotation> annotations) {
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

    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value,
        Stream<OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        checkNotNull(annotations, "annotations cannot be null");
        return dataFactoryInternals.getOWLAnnotation(property, value, annotations);
    }

    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
        OWLAnnotation annotation) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject,
            annotation.getValue(), asList(
                annotation.annotations()));
    }

    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
        OWLAnnotation annotation, Collection<OWLAnnotation> annotations) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject,
            annotation.getValue(), annotations);
    }

    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
        OWLAnnotationProperty property,
        OWLAnnotationSubject subject, OWLAnnotationValue value,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(subject, "subject cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationAssertionAxiomImpl(subject, property, value, annotations);
    }

    @Override
    public OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(IRI subject) {
        checkNotNull(subject, "subject cannot be null");
        return getOWLAnnotationAssertionAxiom(getOWLDeprecated(), subject, getOWLLiteral(true));
    }

    @Override
    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
        OWLAnnotationProperty prop, IRI domain,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(prop, "property cannot be null");
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyDomainAxiomImpl(prop, domain, annotations);
    }

    @Override
    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
        OWLAnnotationProperty prop, IRI range,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(prop, "property cannot be null");
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyRangeAxiomImpl(prop, range, annotations);
    }

    // SWRL
    @Override
    public SWRLRule getSWRLRule(Collection<? extends SWRLAtom> body,
        Collection<? extends SWRLAtom> head,
        Collection<OWLAnnotation> annotations) {
        checkIterableNotNull(body, "body cannot be null", true);
        checkIterableNotNull(head, "head cannot be null", true);
        checkAnnotations(annotations);
        return new SWRLRuleImpl(body, head, annotations);
    }

    @Override
    public SWRLRule getSWRLRule(Collection<? extends SWRLAtom> body,
        Collection<? extends SWRLAtom> head) {
        checkIterableNotNull(body, "antecedent cannot be null", true);
        checkIterableNotNull(head, "consequent cannot be null", true);
        return new SWRLRuleImpl(body, head);
    }

    @Override
    public SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLClassAtomImpl(predicate, arg);
    }

    @Override
    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate, SWRLDArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLDataRangeAtomImpl(predicate, arg);
    }

    @Override
    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWL2Datatype predicate, SWRLDArgument arg) {
        return getSWRLDataRangeAtom(predicate.getDatatype(this), arg);
    }

    @Override
    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property,
        SWRLIArgument arg0,
        SWRLIArgument arg1) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLObjectPropertyAtomImpl(property, arg0, arg1);
    }

    @Override
    public SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property,
        SWRLIArgument arg0,
        SWRLDArgument arg1) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLDataPropertyAtomImpl(property, arg0, arg1);
    }

    @Override
    public SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args) {
        checkNotNull(builtInIRI, "builtInIRI cannot be null");
        checkNotNull(args, "args cannot be null");
        return new SWRLBuiltInAtomImpl(builtInIRI, args);
    }

    @Override
    public SWRLVariable getSWRLVariable(IRI var) {
        checkNotNull(var, "var cannot be null");
        return new SWRLVariableImpl(var);
    }

    @Override
    public SWRLIndividualArgument getSWRLIndividualArgument(OWLIndividual individual) {
        checkNotNull(individual, "individual cannot be null");
        return new SWRLIndividualArgumentImpl(individual);
    }

    @Override
    public SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal) {
        checkNotNull(literal, "literal");
        return new SWRLLiteralArgumentImpl(literal);
    }

    @Override
    public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument arg0,
        SWRLIArgument arg1) {
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLDifferentIndividualsAtomImpl(
            getOWLObjectProperty(OWLRDFVocabulary.OWL_DIFFERENT_FROM), arg0,
            arg1);
    }

    @Override
    public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0,
        SWRLIArgument arg1) {
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLSameIndividualAtomImpl(getOWLObjectProperty(OWLRDFVocabulary.OWL_SAME_AS),
            arg0, arg1);
    }

    @Override
    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWLDataRange dataRange,
        Collection<OWLAnnotation> annotations) {
        checkNotNull(datatype, "datatype cannot be null");
        checkNotNull(dataRange, "dataRange cannot be null");
        checkAnnotations(annotations);
        return new OWLDatatypeDefinitionAxiomImpl(datatype, dataRange, annotations);
    }

    @Override
    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWL2Datatype dataRange,
        Collection<OWLAnnotation> annotations) {
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange.getDatatype(this), annotations);
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
    public OWLLiteral getOWLLiteral(String literal, @Nullable String lang) {
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
