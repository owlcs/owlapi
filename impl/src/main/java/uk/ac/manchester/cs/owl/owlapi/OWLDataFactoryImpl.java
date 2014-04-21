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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLDataFactoryImpl implements OWLDataFactory, Serializable,
        OWLClassProvider {

    private static final long serialVersionUID = 40000L;
    // Distinguished Entities
    //@formatter:off 
    private static final OWLClass               OWL_THING                    = new OWLClassImpl(              OWLRDFVocabulary.OWL_THING.getIRI());
    private static final OWLClass               OWL_NOTHING                  = new OWLClassImpl(              OWLRDFVocabulary.OWL_NOTHING.getIRI());
    private static final OWLAnnotationProperty  RDFS_LABEL                   = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_LABEL.getIRI());
    private static final OWLAnnotationProperty  RDFS_COMMENT                 = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    private static final OWLAnnotationProperty  RDFS_SEE_ALSO                = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    private static final OWLAnnotationProperty  RDFS_IS_DEFINED_BY           = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    private static final OWLAnnotationProperty  OWL_BACKWARD_COMPATIBLE_WITH = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    private static final OWLAnnotationProperty  OWL_INCOMPATIBLE_WITH        = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    private static final OWLAnnotationProperty  OWL_VERSION_INFO             = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    private static final OWLAnnotationProperty  OWL_DEPRECATED               = new OWLAnnotationPropertyImpl( OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    private static final OWLObjectProperty      OWL_TOP_OBJECT_PROPERTY      = new OWLObjectPropertyImpl(     OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    private static final OWLObjectProperty      OWL_BOTTOM_OBJECT_PROPERTY   = new OWLObjectPropertyImpl(     OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    private static final OWLDataProperty        OWL_TOP_DATA_PROPERTY        = new OWLDataPropertyImpl(       OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    private static final OWLDataProperty        OWL_BOTTOM_DATA_PROPERTY     = new OWLDataPropertyImpl(       OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    //@formatter:off 
    protected OWLDataFactoryInternals data;

    /** default constructor */
    public OWLDataFactoryImpl() {
        this(true, false);
    }

    /**
     * @param cache
     *        true if objects should be cached
     * @param useCompression
     *        true if literals should be compressed
     */
    public OWLDataFactoryImpl(boolean cache, boolean useCompression) {
        if (cache) {
            data = new OWLDataFactoryInternalsImpl(useCompression);
        } else {
            data = new InternalsNoCache(useCompression);
        }
    }

    @Override
    public void purge() {
        data.purge();
    }

    private void checkAnnotations(@Nonnull Set<? extends OWLAnnotation> o) {
        checkNull(o, "annotations cannot be null", true);
    }

    private void checkNull(@Nonnull Collection<?> o, String name, boolean emptyAllowed) {
        checkNotNull(o, name + " cannot be null");
        if (!emptyAllowed && o.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

    private void checkNull(@Nonnull Object[] o, String name, boolean emptyAllowed) {
        checkNotNull(o, name + " cannot be null");
        if (!emptyAllowed && o.length == 0) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <E extends OWLEntity> E getOWLEntity(@Nonnull EntityType<E> entityType,
            @Nonnull IRI iri) {
        checkNotNull(entityType, "entityType cannot be null");
        checkNotNull(iri, "iri cannot be null");
        E ret = null;
        if (entityType.equals(EntityType.CLASS)) {
            ret = (E) getOWLClass(iri);
        } else if (entityType.equals(EntityType.OBJECT_PROPERTY)) {
            ret = (E) getOWLObjectProperty(iri);
        } else if (entityType.equals(EntityType.DATA_PROPERTY)) {
            ret = (E) getOWLDataProperty(iri);
        } else if (entityType.equals(EntityType.ANNOTATION_PROPERTY)) {
            ret = (E) getOWLAnnotationProperty(iri);
        } else if (entityType.equals(EntityType.NAMED_INDIVIDUAL)) {
            ret = (E) getOWLNamedIndividual(iri);
        } else if (entityType.equals(EntityType.DATATYPE)) {
            ret = (E) getOWLDatatype(iri);
        }
        if (ret != null) {
            return ret;
        }
        throw new OWLRuntimeException("Entity type not recognized: "
                + entityType + " for iri " + iri);
    }

    @Nonnull
    @Override
    public OWLClass getOWLClass(@Nonnull IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return data.getOWLClass(iri);
    }

    @Nonnull
    @Override
    public OWLClass getOWLClass(@Nonnull String iri, @Nonnull PrefixManager prefixManager) {
        checkNotNull(iri, "iri cannot be null");
        checkNotNull(prefixManager, "prefixManager cannot be null");
        return getOWLClass(prefixManager.getIRI(iri));
    }

    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(
            @Nonnull String abbreviatedIRI, @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, "abbreviatedIRI cannot be null");
        checkNotNull(prefixManager, "prefixManager cannot be null");
        return getOWLAnnotationProperty(prefixManager.getIRI(abbreviatedIRI));
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
    public OWLDatatype getOWLDatatype(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager prefixManager) {
        checkNotNull(abbreviatedIRI, "abbreviatedIRI cannot be null");
        checkNotNull(prefixManager, "prefixManager cannot be null");
        return data.getOWLDatatype(prefixManager.getIRI(abbreviatedIRI));
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

    @Nonnull
    @Override
    public OWLObjectProperty getOWLObjectProperty(@Nonnull IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return data.getOWLObjectProperty(iri);
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLDataProperty(@Nonnull IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return data.getOWLDataProperty(iri);
    }

    @Nonnull
    @Override
    public OWLNamedIndividual getOWLNamedIndividual(@Nonnull IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return data.getOWLNamedIndividual(iri);
    }

    @Nonnull
    @Override
    public OWLDataProperty getOWLDataProperty(@Nonnull String curi,
            @Nonnull PrefixManager prefixManager) {
        checkNotNull(curi, "curi canno be null");
        checkNotNull(prefixManager, "prefixManager cannot be null");
        return getOWLDataProperty(prefixManager.getIRI(curi));
    }

    @Nonnull
    @Override
    public OWLNamedIndividual getOWLNamedIndividual(@Nonnull String curi,
            @Nonnull PrefixManager prefixManager) {
        checkNotNull(curi, "curi canno be null");
        checkNotNull(prefixManager, "prefixManager cannot be null");
        return getOWLNamedIndividual(prefixManager.getIRI(curi));
    }

    @Nonnull
    @Override
    public OWLObjectProperty getOWLObjectProperty(@Nonnull String curi,
            @Nonnull PrefixManager prefixManager) {
        checkNotNull(curi, "curi canno be null");
        checkNotNull(prefixManager, "prefixManager cannot be null");
        return getOWLObjectProperty(prefixManager.getIRI(curi));
    }

    @Nonnull
    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(@Nonnull String id) {
        checkNotNull(id, "id cannot be null");
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(id));
    }

    @Nonnull
    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual() {
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(null));
    }

    @Nonnull
    @Override
    public OWLDatatype getOWLDatatype(@Nonnull IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return data.getOWLDatatype(iri);
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(@Nonnull String lexicalValue, @Nonnull OWL2Datatype datatype) {
        checkNotNull(lexicalValue, "lexicalValue cannot be null");
        checkNotNull(datatype, "datatype cannot be null");
        return getOWLLiteral(lexicalValue, getOWLDatatype(datatype.getIRI()));
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(boolean value) {
        return data.getOWLLiteral(value);
    }

    @Nonnull
    @Override
    public OWLDataOneOf getOWLDataOneOf(@Nonnull Set<? extends OWLLiteral> values) {
        checkNull(values, "values", true);
        return new OWLDataOneOfImpl(values);
    }

    @Nonnull
    @Override
    public OWLDataOneOf getOWLDataOneOf(@Nonnull OWLLiteral... values) {
        checkNull(values, "values", true);
        return getOWLDataOneOf(CollectionFactory.createSet(values));
    }

    @Nonnull
    @Override
    public OWLDataComplementOf getOWLDataComplementOf(@Nonnull OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        return new OWLDataComplementOfImpl(dataRange);
    }

    @Nonnull
    @Override
    public OWLDataIntersectionOf getOWLDataIntersectionOf(
            @Nonnull OWLDataRange... dataRanges) {
        checkNull(dataRanges, "dataRanges", true);
        return getOWLDataIntersectionOf(CollectionFactory.createSet(dataRanges));
    }

    @Nonnull
    @Override
    public OWLDataIntersectionOf getOWLDataIntersectionOf(
            @Nonnull Set<? extends OWLDataRange> dataRanges) {
        checkNull(dataRanges, "dataRanges", true);
        return new OWLDataIntersectionOfImpl(dataRanges);
    }

    @Nonnull
    @Override
    public OWLDataUnionOf getOWLDataUnionOf(@Nonnull OWLDataRange... dataRanges) {
        checkNull(dataRanges, "dataRanges", true);
        return getOWLDataUnionOf(CollectionFactory.createSet(dataRanges));
    }

    @Nonnull
    @Override
    public OWLDataUnionOf getOWLDataUnionOf(
            @Nonnull Set<? extends OWLDataRange> dataRanges) {
        checkNull(dataRanges, "dataRanges", true);
        return new OWLDataUnionOfImpl(dataRanges);
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(
            @Nonnull OWLDatatype datatype, @Nonnull Set<OWLFacetRestriction> facets) {
        checkNotNull(datatype, "datatype cannot be null");
        checkNull(facets, "facets", true);
        return new OWLDatatypeRestrictionImpl(datatype, facets);
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(
            @Nonnull OWLDatatype datatype, @Nonnull OWLFacet facet, @Nonnull OWLLiteral typedConstant) {
        checkNotNull(datatype, "datatype cannot be null");
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(typedConstant, "typedConstant cannot be null");
        return new OWLDatatypeRestrictionImpl(datatype,
                Collections.singleton(getOWLFacetRestriction(facet,
                        typedConstant)));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeRestriction(
            @Nonnull OWLDatatype dataRange, @Nonnull OWLFacetRestriction... facetRestrictions) {
        checkNull(facetRestrictions, "facetRestrictions", true);
        return getOWLDatatypeRestriction(dataRange,
                CollectionFactory.createSet(facetRestrictions));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(
            int minInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(
            int maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MAX_INCLUSIVE, getOWLLiteral(maxInclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(
            int minInclusive, int maxInclusive) {
        return getOWLDatatypeRestriction(
                getIntegerOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE,
                        getOWLLiteral(minInclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(
            int minExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(
            int maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(),
                OWLFacet.MAX_EXCLUSIVE, getOWLLiteral(maxExclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(
            int minExclusive, int maxExclusive) {
        return getOWLDatatypeRestriction(
                getIntegerOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE,
                        getOWLLiteral(minExclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(
            double minInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(
            double maxInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MAX_INCLUSIVE, getOWLLiteral(maxInclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(
            double minInclusive, double maxInclusive) {
        return getOWLDatatypeRestriction(
                getDoubleOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE,
                        getOWLLiteral(minInclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(
            double minExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(
            double maxExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(),
                OWLFacet.MAX_EXCLUSIVE, getOWLLiteral(maxExclusive));
    }

    @Nonnull
    @Override
    public OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(
            double minExclusive, double maxExclusive) {
        return getOWLDatatypeRestriction(
                getDoubleOWLDatatype(),
                getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE,
                        getOWLLiteral(minExclusive)),
                getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            int facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            double facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            float facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    @Nonnull
    @Override
    public OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            @Nonnull OWLLiteral facetValue) {
        checkNotNull(facet, "facet cannot be null");
        checkNotNull(facetValue, "facetValue cannot be null");
        return new OWLFacetRestrictionImpl(facet, facetValue);
    }

    @Nonnull
    @Override
    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(
            @Nonnull Set<? extends OWLClassExpression> operands) {
        checkNull(operands, "operands", true);
        return new OWLObjectIntersectionOfImpl(operands);
    }

    @Nonnull
    @Override
    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(
            @Nonnull OWLClassExpression... operands) {
        checkNull(operands, "operands", true);
        return getOWLObjectIntersectionOf(CollectionFactory.createSet(operands));
    }

    @Nonnull
    @Override
    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLDataAllValuesFromImpl(property, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataExactCardinalityImpl(property, cardinality,
                getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLDataExactCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMaxCardinalityImpl(property, cardinality,
                getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        checkNotNull(dataRange, "dataRange cannot be null");
        return new OWLDataMaxCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMinCardinalityImpl(property, cardinality,
                getTopDatatype());
    }

    @Nonnull
    @Override
    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLDataMinCardinalityImpl(property, cardinality, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange) {
        checkNotNull(dataRange, "dataRange cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLDataSomeValuesFromImpl(property, dataRange);
    }

    @Nonnull
    @Override
    public OWLDataHasValue getOWLDataHasValue(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLLiteral value) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        return new OWLDataHasValueImpl(property, value);
    }

    @Nonnull
    @Override
    public OWLObjectComplementOf getOWLObjectComplementOf(
            @Nonnull OWLClassExpression operand) {
        checkNotNull(operand, "operand");
        return new OWLObjectComplementOfImpl(operand);
    }

    @Nonnull
    @Override
    public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectAllValuesFromImpl(property, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectOneOf
            getOWLObjectOneOf(@Nonnull Set<? extends OWLIndividual> values) {
        checkNull(values, "values", true);
        return new OWLObjectOneOfImpl(values);
    }

    @Nonnull
    @Override
    public OWLObjectOneOf getOWLObjectOneOf(@Nonnull OWLIndividual... individuals) {
        checkNull(individuals, "individuals", true);
        return getOWLObjectOneOf(CollectionFactory.createSet(individuals));
    }

    @Nonnull
    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(
            int cardinality, @Nonnull OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectExactCardinalityImpl(property, cardinality,
                OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectExactCardinality getOWLObjectExactCardinality(
            int cardinality, @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLObjectExactCardinalityImpl(property, cardinality,
                classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMinCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNegative(cardinality, "cardinality cannot be negative");
        return new OWLObjectMinCardinalityImpl(property, cardinality,
                classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMaxCardinalityImpl(property, cardinality, OWL_THING);
    }

    @Nonnull
    @Override
    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression) {
        checkNotNegative(cardinality, "cardinality cannot be negative");
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectMaxCardinalityImpl(property, cardinality,
                classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectHasSelf getOWLObjectHasSelf(
            @Nonnull OWLObjectPropertyExpression property) {
        checkNotNull(property, "property cannot be null");
        return new OWLObjectHasSelfImpl(property);
    }

    @Nonnull
    @Override
    public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(property, "property cannot be null");
        return new OWLObjectSomeValuesFromImpl(property, classExpression);
    }

    @Nonnull
    @Override
    public OWLObjectHasValue getOWLObjectHasValue(
            @Nonnull OWLObjectPropertyExpression property, @Nonnull OWLIndividual individual) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(individual, "individual cannot be null");
        return new OWLObjectHasValueImpl(property, individual);
    }

    @Nonnull
    @Override
    public OWLObjectUnionOf getOWLObjectUnionOf(
            @Nonnull Set<? extends OWLClassExpression> operands) {
        checkNull(operands, "operands", true);
        return new OWLObjectUnionOfImpl(operands);
    }

    @Nonnull
    @Override
    public OWLObjectUnionOf getOWLObjectUnionOf(@Nonnull OWLClassExpression... operands) {
        checkNull(operands, "operands", true);
        return getOWLObjectUnionOf(CollectionFactory.createSet(operands));
    }

    @Nonnull
    @Override
    public OWLAsymmetricObjectPropertyAxiom
            getOWLAsymmetricObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLAsymmetricObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLAsymmetricObjectPropertyAxiom
            getOWLAsymmetricObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression propertyExpression) {
        return getOWLAsymmetricObjectPropertyAxiom(propertyExpression,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLClassExpression domain,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyDomainAxiomImpl(property, domain, annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLClassExpression domain) {
        return getOWLDataPropertyDomainAxiom(property, domain,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange owlDataRange,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(owlDataRange, "owlDataRange cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyRangeAxiomImpl(property, owlDataRange,
                annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange owlDataRange) {
        return getOWLDataPropertyRangeAxiom(property, owlDataRange,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
            @Nonnull OWLDataPropertyExpression subProperty,
            @Nonnull OWLDataPropertyExpression superProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubDataPropertyOfAxiomImpl(subProperty, superProperty,
                annotations);
    }

    @Nonnull
    @Override
    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
            @Nonnull OWLDataPropertyExpression subProperty,
            @Nonnull OWLDataPropertyExpression superProperty) {
        return getOWLSubDataPropertyOfAxiom(subProperty, superProperty,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDeclarationAxiom getOWLDeclarationAxiom(@Nonnull OWLEntity owlEntity) {
        return getOWLDeclarationAxiom(owlEntity, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDeclarationAxiom getOWLDeclarationAxiom(@Nonnull OWLEntity owlEntity,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(owlEntity, "owlEntity cannot be null");
        checkAnnotations(annotations);
        return new OWLDeclarationAxiomImpl(owlEntity, annotations);
    }

    @Nonnull
    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(individuals, "individuals", true);
        checkAnnotations(annotations);
        return new OWLDifferentIndividualsAxiomImpl(individuals, annotations);
    }

    @Nonnull
    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull OWLIndividual... individuals) {
        checkNull(individuals, "individuals", true);
        return getOWLDifferentIndividualsAxiom(CollectionFactory
                .createSet(individuals));
    }

    @Nonnull
    @Override
    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals) {
        return getOWLDifferentIndividualsAxiom(individuals,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(classExpressions, "classExpressions", true);
        checkAnnotations(annotations);
        return new OWLDisjointClassesAxiomImpl(classExpressions, annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointClassesAxiom(classExpressions,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            @Nonnull OWLClassExpression... classExpressions) {
        checkNull(classExpressions, "classExpressions", true);
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLDisjointClassesAxiom(clses);
    }

    @Nonnull
    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            @Nonnull Set<? extends OWLDataPropertyExpression> properties,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties", true);
        checkAnnotations(annotations);
        return new OWLDisjointDataPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            @Nonnull Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLDisjointDataPropertiesAxiom(properties,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            @Nonnull OWLDataPropertyExpression... properties) {
        checkNull(properties, "properties", true);
        return getOWLDisjointDataPropertiesAxiom(CollectionFactory
                .createSet(properties));
    }

    @Nonnull
    @Override
    public OWLDisjointObjectPropertiesAxiom
            getOWLDisjointObjectPropertiesAxiom(
                    @Nonnull OWLObjectPropertyExpression... properties) {
        checkNull(properties, "properties", true);
        return getOWLDisjointObjectPropertiesAxiom(CollectionFactory
                .createSet(properties));
    }

    @Nonnull
    @Override
    public OWLDisjointObjectPropertiesAxiom
            getOWLDisjointObjectPropertiesAxiom(
                    @Nonnull Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLDisjointObjectPropertiesAxiom(properties,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDisjointObjectPropertiesAxiom
            getOWLDisjointObjectPropertiesAxiom(
                    @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties", true);
        checkAnnotations(annotations);
        return new OWLDisjointObjectPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(classExpressions, "classExpressions", true);
        checkAnnotations(annotations);
        return new OWLEquivalentClassesAxiomImpl(classExpressions, annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression clsA, @Nonnull OWLClassExpression clsB) {
        checkNotNull(clsA, "clsA cannot be null");
        checkNotNull(clsB, "clsB cannot be null");
        return getOWLEquivalentClassesAxiom(clsA, clsB, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression clsA, @Nonnull OWLClassExpression clsB,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(clsA, "clsA cannot be null");
        checkNotNull(clsB, "clsB cannot be null");
        return getOWLEquivalentClassesAxiom(
                CollectionFactory.createSet(clsA, clsB), annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression... classExpressions) {
        checkNull(classExpressions, "classExpressions", true);
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLEquivalentClassesAxiom(clses);
    }

    @Nonnull
    @Override
    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return getOWLEquivalentClassesAxiom(classExpressions,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull Set<? extends OWLDataPropertyExpression> properties,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties", true);
        checkAnnotations(annotations);
        return new OWLEquivalentDataPropertiesAxiomImpl(properties, annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLEquivalentDataPropertiesAxiom(properties,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull OWLDataPropertyExpression propertyA,
                    @Nonnull OWLDataPropertyExpression propertyB) {
        return getOWLEquivalentDataPropertiesAxiom(propertyA, propertyB,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull OWLDataPropertyExpression propertyA,
                    @Nonnull OWLDataPropertyExpression propertyB,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(propertyA, "propertyA cannot be null");
        checkNotNull(propertyB, "propertyB cannot be null");
        return getOWLEquivalentDataPropertiesAxiom(
                CollectionFactory.createSet(propertyA, propertyB), annotations);
    }

    @Nonnull
    @Override
    public OWLEquivalentDataPropertiesAxiom
            getOWLEquivalentDataPropertiesAxiom(
                    @Nonnull OWLDataPropertyExpression... properties) {
        checkNull(properties, "properties", true);
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory
                .createSet(properties));
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull OWLObjectPropertyExpression... properties) {
        checkNull(properties, "properties", true);
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory
                .createSet(properties));
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLEquivalentObjectPropertiesAxiom(properties,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull OWLObjectPropertyExpression propertyA,
                    @Nonnull OWLObjectPropertyExpression propertyB) {
        checkNotNull(propertyA, "propertyA cannot be null");
        checkNotNull(propertyB, "propertyB cannot be null");
        return getOWLEquivalentObjectPropertiesAxiom(propertyA, propertyB,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull OWLObjectPropertyExpression propertyA,
                    @Nonnull OWLObjectPropertyExpression propertyB,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(propertyA, "propertyA cannot be null");
        checkNotNull(propertyB, "propertyB cannot be null");
        return getOWLEquivalentObjectPropertiesAxiom(
                CollectionFactory.createSet(propertyA, propertyB), annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLFunctionalDataPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            @Nonnull OWLDataPropertyExpression property) {
        return getOWLFunctionalDataPropertyAxiom(property,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLFunctionalObjectPropertyAxiom
            getOWLFunctionalObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLFunctionalObjectPropertyAxiom
            getOWLFunctionalObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLFunctionalObjectPropertyAxiom(property,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLImportsDeclaration getOWLImportsDeclaration(
            @Nonnull IRI importedOntologyIRI) {
        checkNotNull(importedOntologyIRI, "importedOntologyIRI cannot be null");
        return new OWLImportsDeclarationImpl(importedOntologyIRI);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            @Nonnull OWLLiteral object, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLDataPropertyAssertionAxiomImpl(subject, property, object,
                annotations);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            @Nonnull OWLLiteral object) {
        return getOWLDataPropertyAssertionAxiom(property, subject, object,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom
            getOWLDataPropertyAssertionAxiom(
                    @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
                    int value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            double value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            float value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            boolean value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            @Nonnull String value) {
        return getOWLDataPropertyAssertionAxiom(property, subject,
                getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLNegativeDataPropertyAssertionAxiom
            getOWLNegativeDataPropertyAssertionAxiom(
                    @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
                    @Nonnull OWLLiteral object) {
        return getOWLNegativeDataPropertyAssertionAxiom(property, subject,
                object, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public
            OWLNegativeDataPropertyAssertionAxiom
            getOWLNegativeDataPropertyAssertionAxiom(
                    @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
                    @Nonnull OWLLiteral object, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLNegativeDataPropertyAssertionAxiomImpl(subject, property,
                object, annotations);
    }

    @Nonnull
    @Override
    public OWLNegativeObjectPropertyAssertionAxiom
            getOWLNegativeObjectPropertyAssertionAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull OWLIndividual subject, @Nonnull OWLIndividual object) {
        return getOWLNegativeObjectPropertyAssertionAxiom(property, subject,
                object, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLNegativeObjectPropertyAssertionAxiom
            getOWLNegativeObjectPropertyAssertionAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull OWLIndividual subject, @Nonnull OWLIndividual object,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(object, "object cannot be null");
        checkNotNull(subject, "subject cannot be null");
        checkAnnotations(annotations);
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(subject,
                property, object, annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property, @Nonnull OWLIndividual subject,
            @Nonnull OWLIndividual object) {
        return getOWLObjectPropertyAssertionAxiom(property, subject, object,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(
            @Nonnull OWLClassExpression classExpression, @Nonnull OWLIndividual individual) {
        return getOWLClassAssertionAxiom(classExpression, individual,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(
            @Nonnull OWLClassExpression classExpression, @Nonnull OWLIndividual individual,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNotNull(individual, "individual cannot be null");
        checkAnnotations(annotations);
        return new OWLClassAssertionAxiomImpl(individual, classExpression,
                annotations);
    }

    @Nonnull
    @Override
    public OWLInverseFunctionalObjectPropertyAxiom
            getOWLInverseFunctionalObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLInverseFunctionalObjectPropertyAxiom(property,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLInverseFunctionalObjectPropertyAxiom
            getOWLInverseFunctionalObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(property,
                annotations);
    }

    @Nonnull
    @Override
    public OWLIrreflexiveObjectPropertyAxiom
            getOWLIrreflexiveObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLIrreflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property) {
        return getOWLReflexiveObjectPropertyAxiom(property,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLIrreflexiveObjectPropertyAxiom
            getOWLIrreflexiveObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLIrreflexiveObjectPropertyAxiom(property,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(classExpression, "classExpression cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyDomainAxiomImpl(property, classExpression,
                annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression) {
        return getOWLObjectPropertyDomainAxiom(property, classExpression,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            @Nonnull OWLObjectPropertyExpression property, @Nonnull OWLClassExpression range,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLObjectPropertyRangeAxiomImpl(property, range, annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            @Nonnull OWLObjectPropertyExpression property, @Nonnull OWLClassExpression range) {
        return getOWLObjectPropertyRangeAxiom(property, range,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
            @Nonnull OWLObjectPropertyExpression subProperty,
            @Nonnull OWLObjectPropertyExpression superProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubObjectPropertyOfAxiomImpl(subProperty, superProperty,
                annotations);
    }

    @Nonnull
    @Override
    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
            @Nonnull OWLObjectPropertyExpression subProperty,
            @Nonnull OWLObjectPropertyExpression superProperty) {
        return getOWLSubObjectPropertyOfAxiom(subProperty, superProperty,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLReflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(individuals, "individuals", true);
        checkAnnotations(annotations);
        return new OWLSameIndividualAxiomImpl(individuals, annotations);
    }

    @Nonnull
    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull OWLIndividual... individuals) {
        checkNull(individuals, "individuals", true);
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        inds.addAll(Arrays.asList(individuals));
        return getOWLSameIndividualAxiom(inds);
    }

    @Nonnull
    @Override
    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals) {
        return getOWLSameIndividualAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(
            @Nonnull OWLClassExpression subClass, @Nonnull OWLClassExpression superClass,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subClass, "subclass cannot be null");
        checkNotNull(superClass, "superclass cannot be null");
        checkAnnotations(annotations);
        return new OWLSubClassOfAxiomImpl(subClass, superClass, annotations);
    }

    @Nonnull
    @Override
    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(
            @Nonnull OWLClassExpression subClass, @Nonnull OWLClassExpression superClass) {
        return getOWLSubClassOfAxiom(subClass, superClass,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLSymmetricObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property) {
        return getOWLSymmetricObjectPropertyAxiom(property,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLTransitiveObjectPropertyAxiom
            getOWLTransitiveObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkAnnotations(annotations);
        return new OWLTransitiveObjectPropertyAxiomImpl(property, annotations);
    }

    @Nonnull
    @Override
    public OWLTransitiveObjectPropertyAxiom
            getOWLTransitiveObjectPropertyAxiom(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getOWLTransitiveObjectPropertyAxiom(property,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLObjectInverseOf getOWLObjectInverseOf(
            @Nonnull OWLObjectPropertyExpression property) {
        checkNotNull(property, "property cannot be null");
        return new OWLObjectInverseOfImpl(property);
    }

    @Nonnull
    @Override
    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression forwardProperty,
            @Nonnull OWLObjectPropertyExpression inverseProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(forwardProperty, "forwardProperty cannot be null");
        checkNotNull(inverseProperty, "inverseProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLInverseObjectPropertiesAxiomImpl(forwardProperty,
                inverseProperty, annotations);
    }

    @Nonnull
    @Override
    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression forwardProperty,
            @Nonnull OWLObjectPropertyExpression inverseProperty) {
        return getOWLInverseObjectPropertiesAxiom(forwardProperty,
                inverseProperty, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
            @Nonnull List<? extends OWLObjectPropertyExpression> chain,
            @Nonnull OWLObjectPropertyExpression superProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(superProperty, "superProperty cannot be null");
        checkNull(chain, "chain", true);
        checkAnnotations(annotations);
        return new OWLSubPropertyChainAxiomImpl(chain, superProperty,
                annotations);
    }

    @Nonnull
    @Override
    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
            @Nonnull List<? extends OWLObjectPropertyExpression> chain,
            @Nonnull OWLObjectPropertyExpression superProperty) {
        return getOWLSubPropertyChainOfAxiom(chain, superProperty,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression classExpression,
            @Nonnull Set<? extends OWLPropertyExpression> properties,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNull(properties, "properties", true);
        checkAnnotations(annotations);
        return new OWLHasKeyAxiomImpl(classExpression, properties, annotations);
    }

    @Nonnull
    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression ce,
            @Nonnull Set<? extends OWLPropertyExpression> properties) {
        return getOWLHasKeyAxiom(ce, properties, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression classExpression,
            @Nonnull OWLPropertyExpression... properties) {
        checkNotNull(classExpression, "classExpression cannot be null");
        checkNull(properties, "properties", true);
        return getOWLHasKeyAxiom(classExpression,
                CollectionFactory.createSet(properties));
    }

    @Nonnull
    @Override
    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(@Nonnull OWLClass owlClass,
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(owlClass, "owlClass cannot be null");
        checkNull(classExpressions, "classExpressions", true);
        checkAnnotations(annotations);
        return new OWLDisjointUnionAxiomImpl(owlClass, classExpressions,
                annotations);
    }

    @Nonnull
    @Override
    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(@Nonnull OWLClass owlClass,
            @Nonnull Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointUnionAxiom(owlClass, classExpressions,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLEquivalentObjectPropertiesAxiom
            getOWLEquivalentObjectPropertiesAxiom(
                    @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
                    @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties", true);
        checkAnnotations(annotations);
        return new OWLEquivalentObjectPropertiesAxiomImpl(properties,
                annotations);
    }

    @Nonnull
    @Override
    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property, @Nonnull OWLIndividual individual,
            @Nonnull OWLIndividual object, @Nonnull Set<? extends OWLAnnotation> annotations) {
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
            @Nonnull OWLAnnotationProperty sub, @Nonnull OWLAnnotationProperty sup) {
        return getOWLSubAnnotationPropertyOfAxiom(sub, sup,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
            @Nonnull OWLAnnotationProperty subProperty,
            @Nonnull OWLAnnotationProperty superProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subProperty, "subProperty cannot be null");
        checkNotNull(superProperty, "superProperty cannot be null");
        checkAnnotations(annotations);
        return new OWLSubAnnotationPropertyOfAxiomImpl(subProperty,
                superProperty, annotations);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Annotations
    @Nonnull
    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(@Nonnull IRI iri) {
        checkNotNull(iri, "iri cannot be null");
        return data.getOWLAnnotationProperty(iri);
    }

    @Nonnull
    @Override
    public OWLAnnotation getOWLAnnotation(@Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationValue value) {
        return getOWLAnnotation(property, value, EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLAnnotation getOWLAnnotation(@Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationValue value, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationImpl(property, value, annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationSubject subject, @Nonnull OWLAnnotation annotation) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(),
                subject, annotation.getValue(), annotation.getAnnotations());
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationSubject subject, @Nonnull OWLAnnotation annotation,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(annotation, "annotation cannot be null");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(),
                subject, annotation.getValue(), annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationProperty property, @Nonnull OWLAnnotationSubject subject,
            @Nonnull OWLAnnotationValue value) {
        return getOWLAnnotationAssertionAxiom(property, subject, value,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationProperty property, @Nonnull OWLAnnotationSubject subject,
            @Nonnull OWLAnnotationValue value, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(subject, "subject cannot be null");
        checkNotNull(property, "property cannot be null");
        checkNotNull(value, "value cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationAssertionAxiomImpl(subject, property, value,
                annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationAssertionAxiom
            getDeprecatedOWLAnnotationAssertionAxiom(@Nonnull IRI subject) {
        checkNotNull(subject, "subject cannot be null");
        return getOWLAnnotationAssertionAxiom(getOWLDeprecated(), subject,
                getOWLLiteral(true));
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyDomainAxiom
            getOWLAnnotationPropertyDomainAxiom(@Nonnull OWLAnnotationProperty property,
                    @Nonnull IRI domain, @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(domain, "domain cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyDomainAxiomImpl(property, domain,
                annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyDomainAxiom
            getOWLAnnotationPropertyDomainAxiom(@Nonnull OWLAnnotationProperty prop,
                    @Nonnull IRI domain) {
        return getOWLAnnotationPropertyDomainAxiom(prop, domain,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            @Nonnull OWLAnnotationProperty property, @Nonnull IRI range,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(range, "range cannot be null");
        checkAnnotations(annotations);
        return new OWLAnnotationPropertyRangeAxiomImpl(property, range,
                annotations);
    }

    @Nonnull
    @Override
    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            @Nonnull OWLAnnotationProperty prop, @Nonnull IRI range) {
        return getOWLAnnotationPropertyRangeAxiom(prop, range,
                EMPTY_ANNOTATIONS_SET);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Nonnull
    @Override
    public SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> body,
            @Nonnull Set<? extends SWRLAtom> head, @Nonnull Set<OWLAnnotation> annotations) {
        checkNull(body, "body", true);
        checkNull(head, "head", true);
        checkAnnotations(annotations);
        return new SWRLRuleImpl(body, head, annotations);
    }

    @Nonnull
    @Override
    public SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> antecedent,
            @Nonnull Set<? extends SWRLAtom> consequent) {
        checkNull(antecedent, "antecedent", true);
        checkNull(consequent, "consequent", true);
        return new SWRLRuleImpl(antecedent, consequent);
    }

    @Nonnull
    @Override
    public SWRLClassAtom getSWRLClassAtom(@Nonnull OWLClassExpression predicate,
            @Nonnull SWRLIArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLClassAtomImpl(predicate, arg);
    }

    @Nonnull
    @Override
    public SWRLDataRangeAtom getSWRLDataRangeAtom(@Nonnull OWLDataRange predicate,
            @Nonnull SWRLDArgument arg) {
        checkNotNull(predicate, "predicate cannot be null");
        checkNotNull(arg, "arg cannot be null");
        return new SWRLDataRangeAtomImpl(predicate, arg);
    }

    @Nonnull
    @Override
    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(
            @Nonnull OWLObjectPropertyExpression property, @Nonnull SWRLIArgument arg0,
            @Nonnull SWRLIArgument arg1) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLObjectPropertyAtomImpl(property, arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLDataPropertyAtom getSWRLDataPropertyAtom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull SWRLIArgument arg0,
            @Nonnull SWRLDArgument arg1) {
        checkNotNull(property, "property cannot be null");
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLDataPropertyAtomImpl(property, arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLBuiltInAtom getSWRLBuiltInAtom(@Nonnull IRI builtInIRI,
            @Nonnull List<SWRLDArgument> args) {
        checkNotNull(builtInIRI, "builtInIRI cannot be null");
        checkNotNull(args, "args cannot be null");
        return new SWRLBuiltInAtomImpl(builtInIRI, args);
    }

    @Nonnull
    @Override
    public SWRLVariable getSWRLVariable(@Nonnull IRI var) {
        checkNotNull(var, "var cannot be null");
        return new SWRLVariableImpl(var);
    }

    @Nonnull
    @Override
    public SWRLIndividualArgument getSWRLIndividualArgument(
            @Nonnull OWLIndividual individual) {
        checkNotNull(individual, "individual cannot be null");
        return new SWRLIndividualArgumentImpl(individual);
    }

    @Nonnull
    @Override
    public SWRLLiteralArgument getSWRLLiteralArgument(@Nonnull OWLLiteral literal) {
        checkNotNull(literal, "literal");
        return new SWRLLiteralArgumentImpl(literal);
    }

    @Nonnull
    @Override
    public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(
            @Nonnull SWRLIArgument arg0, @Nonnull SWRLIArgument arg1) {
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLDifferentIndividualsAtomImpl(
                getOWLObjectProperty(OWLRDFVocabulary.OWL_DIFFERENT_FROM
                        .getIRI()),
                arg0, arg1);
    }

    @Nonnull
    @Override
    public SWRLSameIndividualAtom getSWRLSameIndividualAtom(@Nonnull SWRLIArgument arg0,
            @Nonnull SWRLIArgument arg1) {
        checkNotNull(arg0, "arg0 cannot be null");
        checkNotNull(arg1, "arg1 cannot be null");
        return new SWRLSameIndividualAtomImpl(
                getOWLObjectProperty(OWLRDFVocabulary.OWL_SAME_AS.getIRI()),
                arg0, arg1);
    }

    private static final Set<OWLAnnotation> EMPTY_ANNOTATIONS_SET = Collections
            .emptySet();

    @Nonnull
    @Override
    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(
            @Nonnull OWLDatatype datatype, @Nonnull OWLDataRange dataRange) {
        checkNotNull(datatype, "datatype cannot be null");
        checkNotNull(dataRange, "dataRange cannot be null");
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange,
                EMPTY_ANNOTATIONS_SET);
    }

    @Nonnull
    @Override
    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(
            @Nonnull OWLDatatype datatype, @Nonnull OWLDataRange dataRange,
            @Nonnull Set<? extends OWLAnnotation> annotations) {
        checkNotNull(datatype, "datatype cannot be null");
        checkNotNull(dataRange, "dataRange cannot be null");
        checkAnnotations(annotations);
        return new OWLDatatypeDefinitionAxiomImpl(datatype, dataRange,
                annotations);
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(@Nonnull String lexicalValue, @Nonnull OWLDatatype datatype) {
        checkNotNull(lexicalValue, "lexicalValue cannot be null");
        checkNotNull(datatype, "datatype cannot be null");
        return data.getOWLLiteral(lexicalValue, datatype);
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(int value) {
        return data.getOWLLiteral(value);
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(double value) {
        return data.getOWLLiteral(value);
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(float value) {
        return data.getOWLLiteral(value);
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(@Nonnull String value) {
        checkNotNull(value, "value cannot be null");
        return data.getOWLLiteral(value);
    }

    @Nonnull
    @Override
    public OWLLiteral getOWLLiteral(@Nonnull String literal, String lang) {
        checkNotNull(literal, "literal cannot be null");
        return data.getOWLLiteral(literal, lang);
    }

    @Nonnull
    @Override
    public OWLDatatype getBooleanOWLDatatype() {
        return data.getBooleanOWLDatatype();
    }

    @Nonnull
    @Override
    public OWLDatatype getDoubleOWLDatatype() {
        return data.getDoubleOWLDatatype();
    }

    @Nonnull
    @Override
    public OWLDatatype getFloatOWLDatatype() {
        return data.getFloatOWLDatatype();
    }

    @Nonnull
    @Override
    public OWLDatatype getIntegerOWLDatatype() {
        return data.getIntegerOWLDatatype();
    }

    @Nonnull
    @Override
    public OWLDatatype getTopDatatype() {
        return data.getTopDatatype();
    }

    @Nonnull
    @Override
    public OWLDatatype getRDFPlainLiteral() {
        return data.getRDFPlainLiteral();
    }
}
