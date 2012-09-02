/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.manchester.cs.owl.owlapi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br>
 * <br>
 */
public class OWLDataFactoryImpl implements OWLDataFactory, Serializable {


    private static final long serialVersionUID = 30402L;

    private static final String ABBREVIATED_IRI = "abbreviatedIRI";

    private static final String ID2 = "id";

    private static final String CURI2 = "curi";

    private static final String PREFIX_MANAGER = "prefixManager";

    private static final String IRI2 = "iri";

    private static final String VALUE2 = "value";

    private static final String DATATYPE2 = "datatype";

    private static final String LITERAL2 = "literal";

    private static final String VALUES2 = "values";

    private static final String DATA_RANGES = "dataRanges";

    private static final String DATA_RANGE = "dataRange";

    private static final OWLDataFactory instance = new OWLDataFactoryImpl(false, false);

    private static final OWLClass OWL_THING = new OWLClassImpl(
            OWLRDFVocabulary.OWL_THING.getIRI());

    private static final OWLClass OWL_NOTHING = new OWLClassImpl(
            OWLRDFVocabulary.OWL_NOTHING.getIRI());

    protected OWLDataFactoryInternals data;

    @SuppressWarnings("javadoc")
    public OWLDataFactoryImpl() {
        this(true, false);
    }

    @SuppressWarnings("javadoc")
    public OWLDataFactoryImpl(boolean cache, boolean useCompression) {
        if (cache) {
            data = new OWLDataFactoryInternalsImpl(this, useCompression);
        }
        else {
            data = new InternalsNoCache(this, useCompression);
        }
    }

    /**
     * @return singleton instance
     * @deprecated Do not create data factories directly; use
     *             OWLOntologyManager::getOWLDataFactory()
     */
    @Deprecated
    public static OWLDataFactory getInstance() {
        System.err.println("OWLDataFactoryImpl.getInstance() WARNING: you should not use the implementation directly; this static method is here for backwards compatibility only");
        return instance;
    }

    public void purge() {
        data.purge();
    }

    private void checkNull(Object o, String name) {
        if (o == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
        if (o instanceof Iterable) {
            int i = 0;
            for (Object t : (Iterable<?>) o) {
                if (t == null) {
                    throw new IllegalArgumentException(name + " element " + i + " cannot be null");
                }
                i++;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType, IRI iri) {
        checkNull(entityType, "entityType");
        checkNull(iri, IRI2);
        E ret = null;
        if (entityType.equals(EntityType.CLASS)) {
            ret = (E) getOWLClass(iri);
        }
        else if (entityType.equals(EntityType.OBJECT_PROPERTY)) {
            ret = (E) getOWLObjectProperty(iri);
        }
        else if (entityType.equals(EntityType.DATA_PROPERTY)) {
            ret = (E) getOWLDataProperty(iri);
        }
        else if (entityType.equals(EntityType.ANNOTATION_PROPERTY)) {
            ret = (E) getOWLAnnotationProperty(iri);
        }
        else if (entityType.equals(EntityType.NAMED_INDIVIDUAL)) {
            ret = (E) getOWLNamedIndividual(iri);
        }
        else if (entityType.equals(EntityType.DATATYPE)) {
            ret = (E) getOWLDatatype(iri);
        }
        return ret;
    }

    public OWLClass getOWLClass(IRI iri) {
        checkNull(iri, IRI2);
        return data.getOWLClass(iri);
    }

    public OWLClass getOWLClass(String iri, PrefixManager prefixManager) {
        checkNull(iri, IRI2);
        checkNull(prefixManager, PREFIX_MANAGER);
        return getOWLClass(prefixManager.getIRI(iri));
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(String abbreviatedIRI, PrefixManager prefixManager) {
        checkNull(abbreviatedIRI, ABBREVIATED_IRI);
        checkNull(prefixManager, PREFIX_MANAGER);
        return getOWLAnnotationProperty(prefixManager.getIRI(abbreviatedIRI));
    }

    public OWLAnnotationProperty getRDFSLabel() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());
    }

    public OWLAnnotationProperty getRDFSComment() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    }

    public OWLAnnotationProperty getRDFSSeeAlso() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    }

    public OWLAnnotationProperty getRDFSIsDefinedBy() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    }

    public OWLAnnotationProperty getOWLVersionInfo() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    }

    public OWLAnnotationProperty getOWLBackwardCompatibleWith() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    }

    public OWLAnnotationProperty getOWLIncompatibleWith() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    }

    public OWLAnnotationProperty getOWLDeprecated() {
        return getOWLAnnotationProperty(OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    }

    public OWLDatatype getOWLDatatype(String abbreviatedIRI, PrefixManager prefixManager) {
        checkNull(abbreviatedIRI, ABBREVIATED_IRI);
        checkNull(prefixManager, PREFIX_MANAGER);
        return data.getOWLDatatype(prefixManager.getIRI(abbreviatedIRI));
    }

    public OWLClass getOWLThing() {
        return OWL_THING;
    }

    public OWLClass getOWLNothing() {
        return OWL_NOTHING;
    }

    public OWLDataProperty getOWLBottomDataProperty() {
        return getOWLDataProperty(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    }

    public OWLObjectProperty getOWLBottomObjectProperty() {
        return getOWLObjectProperty(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    }

    public OWLDataProperty getOWLTopDataProperty() {
        return getOWLDataProperty(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    }

    public OWLObjectProperty getOWLTopObjectProperty() {
        return getOWLObjectProperty(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    }


    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        checkNull(iri, IRI2);
        return data.getOWLObjectProperty(iri);
    }

    public OWLDataProperty getOWLDataProperty(IRI iri) {
        checkNull(iri, IRI2);
        return data.getOWLDataProperty(iri);
    }

    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        checkNull(iri, IRI2);
        return data.getOWLNamedIndividual(iri);
    }

    public OWLDataProperty getOWLDataProperty(String curi, PrefixManager prefixManager) {
        checkNull(curi, CURI2);
        checkNull(prefixManager, PREFIX_MANAGER);
        return getOWLDataProperty(prefixManager.getIRI(curi));
    }

    public OWLNamedIndividual getOWLNamedIndividual(String curi, PrefixManager prefixManager) {
        checkNull(curi, CURI2);
        checkNull(prefixManager, PREFIX_MANAGER);
        return getOWLNamedIndividual(prefixManager.getIRI(curi));
    }

    public OWLObjectProperty getOWLObjectProperty(String curi, PrefixManager prefixManager) {
        checkNull(curi, CURI2);
        checkNull(prefixManager, PREFIX_MANAGER);
        return getOWLObjectProperty(prefixManager.getIRI(curi));
    }

    public OWLAnonymousIndividual getOWLAnonymousIndividual(String id) {
        checkNull(id, ID2);
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID(id));
    }

    public OWLAnonymousIndividual getOWLAnonymousIndividual() {
        return new OWLAnonymousIndividualImpl(NodeID.getNodeID());
    }

    public OWLDatatype getOWLDatatype(IRI iri) {
        checkNull(iri, IRI2);
        return data.getOWLDatatype(iri);
    }

    public OWLLiteral getOWLLiteral(String lexicalValue, OWL2Datatype datatype) {
        checkNull(lexicalValue, "lexicalValue");
        checkNull(datatype, DATATYPE2);
        return getOWLLiteral(lexicalValue, getOWLDatatype(datatype.getIRI()));
    }

    public OWLLiteral getOWLLiteral(boolean value) {
        return data.getOWLLiteral(value);
    }

    @Deprecated
    public OWLLiteral getOWLTypedLiteral(String literal, OWLDatatype datatype) {
        checkNull(literal, LITERAL2);
        checkNull(datatype, DATATYPE2);
        return getOWLLiteral(literal, datatype);
    }

    @Deprecated
    public OWLLiteral getOWLTypedLiteral(String literal, OWL2Datatype datatype) {
        checkNull(literal, LITERAL2);
        checkNull(datatype, DATATYPE2);
        return getOWLLiteral(literal, datatype);
    }

    @Deprecated
    public OWLLiteral getOWLTypedLiteral(int value) {
        return getOWLLiteral(value);
    }

    @Deprecated
    public OWLLiteral getOWLTypedLiteral(double value) {
        return getOWLLiteral(value);
    }

    @Deprecated
    public OWLLiteral getOWLTypedLiteral(boolean value) {
        return getOWLLiteral(value);
    }

    @Deprecated
    public OWLLiteral getOWLTypedLiteral(float value) {
        return getOWLLiteral(value);
    }

    @Deprecated
    public OWLLiteral getOWLTypedLiteral(String value) {
        return getOWLLiteral(value);
    }

    @Deprecated
    public OWLLiteral getOWLStringLiteral(String literal, String lang) {
        return getOWLLiteral(literal, lang);
    }

    @Deprecated
    public OWLLiteral getOWLStringLiteral(String literal) {
        return getOWLLiteral(literal, "");
    }

    public OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values) {
        checkNull(values, VALUES2);
        return new OWLDataOneOfImpl(values);
    }

    public OWLDataOneOf getOWLDataOneOf(OWLLiteral... values) {
        checkNull(values, VALUES2);
        return getOWLDataOneOf(CollectionFactory.createSet(values));
    }

    public OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange) {
        checkNull(dataRange, DATA_RANGE);
        return new OWLDataComplementOfImpl(dataRange);
    }

    public OWLDataIntersectionOf getOWLDataIntersectionOf(OWLDataRange... dataRanges) {
        checkNull(dataRanges, DATA_RANGES);
        return getOWLDataIntersectionOf(CollectionFactory.createSet(dataRanges));
    }

    public OWLDataIntersectionOf getOWLDataIntersectionOf(Set<? extends OWLDataRange> dataRanges) {
        checkNull(dataRanges, DATA_RANGES);
        return new OWLDataIntersectionOfImpl(dataRanges);
    }

    public OWLDataUnionOf getOWLDataUnionOf(OWLDataRange... dataRanges) {
        checkNull(dataRanges, DATA_RANGES);
        return getOWLDataUnionOf(CollectionFactory.createSet(dataRanges));
    }

    public OWLDataUnionOf getOWLDataUnionOf(Set<? extends OWLDataRange> dataRanges) {
        checkNull(dataRanges, DATA_RANGES);
        return new OWLDataUnionOfImpl(dataRanges);
    }

    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype datatype, Set<OWLFacetRestriction> facets) {
        checkNull(datatype, DATATYPE2);
        checkNull(facets, "facets");
        return new OWLDatatypeRestrictionImpl(datatype, facets);
    }

    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype datatype, OWLFacet facet, OWLLiteral typedConstant) {
        checkNull(datatype, DATATYPE2);
        checkNull(facet, "facet");
        checkNull(typedConstant, "typedConstant");
        return new OWLDatatypeRestrictionImpl(datatype, Collections.singleton(getOWLFacetRestriction(facet, typedConstant)));
    }

    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange, OWLFacetRestriction... facetRestrictions) {
        checkNull(facetRestrictions, "facetRestrictions");
        return getOWLDatatypeRestriction(dataRange, CollectionFactory.createSet(facetRestrictions));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(int minInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(int maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MAX_INCLUSIVE, getOWLLiteral(maxInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(int minInclusive, int maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive)), getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(int minExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(int maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MAX_EXCLUSIVE, getOWLLiteral(maxExclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(int minExclusive, int maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive)), getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(double minInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(double maxInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MAX_INCLUSIVE, getOWLLiteral(maxInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(double minInclusive, double maxInclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive)), getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(double minExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(double maxExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), OWLFacet.MAX_EXCLUSIVE, getOWLLiteral(maxExclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(double minExclusive, double maxExclusive) {
        return getOWLDatatypeRestriction(getDoubleOWLDatatype(), getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive)), getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
    }

    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, int facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, double facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, float facetValue) {
        return getOWLFacetRestriction(facet, getOWLLiteral(facetValue));
    }

    public OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, OWLLiteral facetValue) {
        checkNull(facet, "facet");
        checkNull(facetValue, "facetValue");
        return new OWLFacetRestrictionImpl(facet, facetValue);
    }

    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<? extends OWLClassExpression> operands) {
        checkNull(operands, "operands");
        return new OWLObjectIntersectionOfImpl(operands);
    }

    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(OWLClassExpression... operands) {
        checkNull(operands, "operands");
        return getOWLObjectIntersectionOf(CollectionFactory.createSet(operands));
    }

    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNull(dataRange, DATA_RANGE);
        checkNull(property, "property");
        return new OWLDataAllValuesFromImpl(property, dataRange);
    }

    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        return new OWLDataExactCardinalityImpl(property, cardinality, getTopDatatype());
    }

    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNull(dataRange, DATA_RANGE);
        checkNull(property, "property");
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        return new OWLDataExactCardinalityImpl(property, cardinality, dataRange);
    }

    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        return new OWLDataMaxCardinalityImpl(property, cardinality, getTopDatatype());
    }

    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        checkNull(dataRange, DATA_RANGE);
        return new OWLDataMaxCardinalityImpl(property, cardinality, dataRange);
    }

    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        return new OWLDataMinCardinalityImpl(property, cardinality, getTopDatatype());
    }

    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNull(dataRange, DATA_RANGE);
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        return new OWLDataMinCardinalityImpl(property, cardinality, dataRange);
    }

    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange) {
        checkNull(dataRange, DATA_RANGE);
        checkNull(property, "property");
        return new OWLDataSomeValuesFromImpl(property, dataRange);
    }

    public OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property, OWLLiteral value) {
        checkNull(property, "property");
        checkNull(value, VALUE2);
        return new OWLDataHasValueImpl(property, value);
    }

    public OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand) {
        checkNull(operand, "operand");
        return new OWLObjectComplementOfImpl(operand);
    }

    public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        checkNull(classExpression, "classExpression");
        checkNull(property, "property");
        return new OWLObjectAllValuesFromImpl(property, classExpression);
    }

    public OWLObjectOneOf getOWLObjectOneOf(Set<? extends OWLIndividual> values) {
        checkNull(values, VALUES2);
        return new OWLObjectOneOfImpl(values);
    }

    public OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals) {
        checkNull(individuals, "individuals");
        return getOWLObjectOneOf(CollectionFactory.createSet(individuals));
    }

    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        return new OWLObjectExactCardinalityImpl(property, cardinality, OWL_THING);
    }

    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        checkNull(classExpression, "classExpression");
        checkNull(property, "property");
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        return new OWLObjectExactCardinalityImpl(property, cardinality, classExpression);
    }

    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        return new OWLObjectMinCardinalityImpl(property, cardinality, OWL_THING);
    }

    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        checkNull(classExpression, "classExpression");
        checkNull(property, "property");
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        return new OWLObjectMinCardinalityImpl(property, cardinality, classExpression);
    }

    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(property, "property");
        return new OWLObjectMaxCardinalityImpl(property, cardinality, OWL_THING);
    }

    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        if (cardinality < 0) {
            throw new IllegalArgumentException("cardinality cannot be negative");
        }
        checkNull(classExpression, "classExpression");
        checkNull(property, "property");
        return new OWLObjectMaxCardinalityImpl(property, cardinality, classExpression);
    }

    public OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property) {
        checkNull(property, "property");
        return new OWLObjectHasSelfImpl(property);
    }

    public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        checkNull(classExpression, "classExpression");
        checkNull(property, "property");
        return new OWLObjectSomeValuesFromImpl(property, classExpression);
    }

    public OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property, OWLIndividual individual) {
        checkNull(property, "property");
        checkNull(individual, "individual");
        return new OWLObjectHasValueImpl(property, individual);
    }

    public OWLObjectUnionOf getOWLObjectUnionOf(Set<? extends OWLClassExpression> operands) {
        checkNull(operands, "operands");
        return new OWLObjectUnionOfImpl(operands);
    }

    public OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands) {
        checkNull(operands, "operands");
        return getOWLObjectUnionOf(CollectionFactory.createSet(operands));
    }

    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression, Set<? extends OWLAnnotation> annotations) {
        checkNull(propertyExpression, "propertyExpression");
        checkNull(annotations, "annotations");
        return new OWLAsymmetricObjectPropertyAxiomImpl(propertyExpression, annotations);
    }

    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression) {
        return getOWLAsymmetricObjectPropertyAxiom(propertyExpression, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLClassExpression domain, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(domain, "domain");
        checkNull(annotations, "annotations");
        return new OWLDataPropertyDomainAxiomImpl(property, domain, annotations);
    }

    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLClassExpression domain) {
        return getOWLDataPropertyDomainAxiom(property, domain, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression property, OWLDataRange owlDataRange, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(owlDataRange, "owlDataRange");
        checkNull(annotations, "annotations");
        return new OWLDataPropertyRangeAxiomImpl(property, owlDataRange, annotations);
    }

    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression property, OWLDataRange owlDataRange) {
        return getOWLDataPropertyRangeAxiom(property, owlDataRange, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations) {
        checkNull(subProperty, "subProperty");
        checkNull(superProperty, "superProperty");
        checkNull(annotations, "annotations");
        return new OWLSubDataPropertyOfAxiomImpl(subProperty, superProperty, annotations);
    }

    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {
        return getOWLSubDataPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity) {
        return getOWLDeclarationAxiom(owlEntity, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity, Set<? extends OWLAnnotation> annotations) {
        checkNull(owlEntity, "owlEntity");
        checkNull(annotations, "annotations");
        return new OWLDeclarationAxiomImpl(owlEntity, annotations);
    }

    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals, Set<? extends OWLAnnotation> annotations) {
        checkNull(individuals, "individuals");
        checkNull(annotations, "annotations");
        return new OWLDifferentIndividualsAxiomImpl(individuals, annotations);
    }

    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual... individuals) {
        checkNull(individuals, "individuals");
        return getOWLDifferentIndividualsAxiom(CollectionFactory.createSet(individuals));
    }

    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals) {
        return getOWLDifferentIndividualsAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        checkNull(classExpressions, "classExpressions");
        checkNull(annotations, "annotations");
        return new OWLDisjointClassesAxiomImpl(classExpressions, annotations);
    }

    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression... classExpressions) {
        checkNull(classExpressions, "classExpressions");
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLDisjointClassesAxiom(clses);
    }

    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties");
        checkNull(annotations, "annotations");
        return new OWLDisjointDataPropertiesAxiomImpl(properties, annotations);
    }

    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLDisjointDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        checkNull(properties, "properties");
        return getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        checkNull(properties, "properties");
        return getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLDisjointObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties");
        checkNull(annotations, "annotations");
        return new OWLDisjointObjectPropertiesAxiomImpl(properties, annotations);
    }

    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        checkNull(classExpressions, "classExpressions");
        checkNull(annotations, "annotations");
        return new OWLEquivalentClassesAxiomImpl(classExpressions, annotations);
    }

    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB) {
        checkNull(clsA, "clsA");
        checkNull(clsB, "clsB");
        return getOWLEquivalentClassesAxiom(clsA, clsB, EMPTY_ANNOTATIONS_SET);
    }

    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB, Set<? extends OWLAnnotation> annotations) {
        checkNull(clsA, "clsA");
        checkNull(clsB, "clsB");
        return getOWLEquivalentClassesAxiom(CollectionFactory.createSet(clsA, clsB), annotations);
    }

    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression... classExpressions) {
        checkNull(classExpressions, "classExpressions");
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLEquivalentClassesAxiom(clses);
    }

    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions) {
        return getOWLEquivalentClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }

    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties");
        checkNull(annotations, "annotations");
        return new OWLEquivalentDataPropertiesAxiomImpl(properties, annotations);
    }

    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLEquivalentDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB) {
        return getOWLEquivalentDataPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }

    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB, Set<? extends OWLAnnotation> annotations) {
        checkNull(propertyA, "propertyA");
        checkNull(propertyB, "propertyB");
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }

    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        checkNull(properties, "properties");
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        checkNull(properties, "properties");
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }

    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLEquivalentObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }

    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB) {
        checkNull(propertyA, "propertyA");
        checkNull(propertyB, "propertyB");
        return getOWLEquivalentObjectPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }

    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB, Set<? extends OWLAnnotation> annotations) {
        checkNull(propertyA, "propertyA");
        checkNull(propertyB, "propertyB");
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }

    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(annotations, "annotations");
        return new OWLFunctionalDataPropertyAxiomImpl(property, annotations);
    }

    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property) {
        return getOWLFunctionalDataPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(annotations, "annotations");
        return new OWLFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    public OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI) {
        checkNull(importedOntologyIRI, "importedOntologyIRI");
        return new OWLImportsDeclarationImpl(importedOntologyIRI);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(object, "object");
        checkNull(subject, "subject");
        checkNull(annotations, "annotations");
        return new OWLDataPropertyAssertionAxiomImpl(subject, property, object, annotations);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object) {
        return getOWLDataPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, int value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, double value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, float value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, boolean value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, String value) {
        return getOWLDataPropertyAssertionAxiom(property, subject, getOWLLiteral(value), EMPTY_ANNOTATIONS_SET);
    }

    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object) {
        return getOWLNegativeDataPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }

    public OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(object, "object");
        checkNull(subject, "subject");
        checkNull(annotations, "annotations");
        return new OWLNegativeDataPropertyAssertionImplAxiom(subject, property, object, annotations);
    }

    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object) {
        return getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }

    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(object, "object");
        checkNull(subject, "subject");
        checkNull(annotations, "annotations");
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(subject, property, object, annotations);
    }

    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object) {
        return getOWLObjectPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }

    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual) {
        return getOWLClassAssertionAxiom(classExpression, individual, EMPTY_ANNOTATIONS_SET);
    }

    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual, Set<? extends OWLAnnotation> annotations) {
        checkNull(classExpression, "classExpression");
        checkNull(individual, "individual");
        checkNull(annotations, "annotations");
        return new OWLClassAssertionImpl(individual, classExpression, annotations);
    }

    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLInverseFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(annotations, "annotations");
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(property, annotations);
    }

    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(annotations, "annotations");
        return new OWLIrreflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLReflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLIrreflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property, OWLClassExpression classExpression, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(classExpression, "classExpression");
        checkNull(annotations, "annotations");
        return new OWLObjectPropertyDomainAxiomImpl(property, classExpression, annotations);
    }

    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        return getOWLObjectPropertyDomainAxiom(property, classExpression, EMPTY_ANNOTATIONS_SET);
    }

    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property, OWLClassExpression range, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(range, "range");
        checkNull(annotations, "annotations");
        return new OWLObjectPropertyRangeAxiomImpl(property, range, annotations);
    }

    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property, OWLClassExpression range) {
        return getOWLObjectPropertyRangeAxiom(property, range, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations) {
        checkNull(subProperty, "subProperty");
        checkNull(superProperty, "superProperty");
        checkNull(annotations, "annotations");
        return new OWLSubObjectPropertyOfAxiomImpl(subProperty, superProperty, annotations);
    }

    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {
        return getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }

    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(annotations, "annotations");
        return new OWLReflexiveObjectPropertyAxiomImpl(property, annotations);
    }

    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals, Set<? extends OWLAnnotation> annotations) {
        checkNull(individuals, "individuals");
        checkNull(annotations, "annotations");
        return new OWLSameIndividualAxiomImpl(individuals, annotations);
    }

    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(OWLIndividual... individuals) {
        checkNull(individuals, "individuals");
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        inds.addAll(Arrays.asList(individuals));
        return getOWLSameIndividualAxiom(inds);
    }

    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals) {
        return getOWLSameIndividualAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass, Set<? extends OWLAnnotation> annotations) {
        checkNull(subClass, "subclass");
        checkNull(superClass, "superclass");
        checkNull(annotations, "annotations");
        return new OWLSubClassOfAxiomImpl(subClass, superClass, annotations);
    }

    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass) {
        return getOWLSubClassOfAxiom(subClass, superClass, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(annotations, "annotations");
        return new OWLSymmetricObjectPropertyAxiomImpl(property, annotations);
    }

    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLSymmetricObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(annotations, "annotations");
        return new OWLTransitiveObjectPropertyAxiomImpl(property, annotations);
    }

    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLTransitiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }

    public OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectPropertyExpression property) {
        checkNull(property, "property");
        return new OWLObjectInverseOfImpl(property);
    }

    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty, Set<? extends OWLAnnotation> annotations) {
        checkNull(forwardProperty, "forwardProperty");
        checkNull(inverseProperty, "inverseProperty");
        checkNull(annotations, "annotations");
        return new OWLInverseObjectPropertiesAxiomImpl(forwardProperty, inverseProperty, annotations);
    }

    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty) {
        return getOWLInverseObjectPropertiesAxiom(forwardProperty, inverseProperty, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations) {
        checkNull(superProperty, "superProperty");
        checkNull(chain, "chain");
        checkNull(annotations, "annotations");
        return new OWLSubPropertyChainAxiomImpl(chain, superProperty, annotations);
    }

    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {
        return getOWLSubPropertyChainOfAxiom(chain, superProperty, EMPTY_ANNOTATIONS_SET);
    }

    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, Set<? extends OWLPropertyExpression<?, ?>> properties, Set<? extends OWLAnnotation> annotations) {
        checkNull(ce, "ce");
        checkNull(properties, "properties");
        checkNull(annotations, "annotations");
        return new OWLHasKeyAxiomImpl(ce, properties, annotations);
    }

    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, Set<? extends OWLPropertyExpression<?, ?>> properties) {
        return getOWLHasKeyAxiom(ce, properties, EMPTY_ANNOTATIONS_SET);
    }

    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, OWLPropertyExpression<?, ?>... properties) {
        checkNull(ce, "ce");
        checkNull(properties, "properties");
        return getOWLHasKeyAxiom(ce, CollectionFactory.createSet(properties));
    }

    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass, Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        checkNull(owlClass, "owlClass");
        checkNull(classExpressions, "classExpressions");
        checkNull(annotations, "annotations");
        return new OWLDisjointUnionAxiomImpl(owlClass, classExpressions, annotations);
    }

    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass, Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointUnionAxiom(owlClass, classExpressions, EMPTY_ANNOTATIONS_SET);
    }

    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        checkNull(properties, "properties");
        checkNull(annotations, "annotations");
        return new OWLEquivalentObjectPropertiesAxiomImpl(properties, annotations);
    }

    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(individual, "individual");
        checkNull(object, "object");
        checkNull(annotations, "annotations");
        return new OWLObjectPropertyAssertionAxiomImpl(individual, property, object, annotations);
    }

    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub, OWLAnnotationProperty sup) {
        return getOWLSubAnnotationPropertyOfAxiom(sub, sup, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub, OWLAnnotationProperty sup, Set<? extends OWLAnnotation> annotations) {
        checkNull(sub, "sub");
        checkNull(sup, "sup");
        checkNull(annotations, "annotations");
        return new OWLSubAnnotationPropertyOfAxiomImpl(sub, sup, annotations);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Annotations
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        checkNull(iri, IRI2);
        return data.getOWLAnnotationProperty(iri);
    }

    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value) {
        return getOWLAnnotation(property, value, EMPTY_ANNOTATIONS_SET);
    }

    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations) {
        checkNull(property, "property");
        checkNull(value, VALUE2);
        checkNull(annotations, "annotations");
        return new OWLAnnotationImpl(property, value, annotations);
    }

    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject, OWLAnnotation annotation) {
        // PATCH: 	return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue(), annotation.getAnnotations());
        // ORIG: 	return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue());
        // The patch makes a difference for the owl, owlfs, rdfxml and turtle serializations of Annotation2.
        checkNull(annotation, "annotation");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue(), annotation.getAnnotations());
    }

    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject, OWLAnnotation annotation, Set<? extends OWLAnnotation> annotations) {
        checkNull(annotation, "annotation");
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue(), annotations);
    }

    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value) {
        return getOWLAnnotationAssertionAxiom(property, subject, value, EMPTY_ANNOTATIONS_SET);
    }

    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations) {
        checkNull(subject, "subject");
        checkNull(property, "property");
        checkNull(value, VALUE2);
        checkNull(annotations, "annotations");
        return new OWLAnnotationAssertionAxiomImpl(subject, property, value, annotations);
    }

    public OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(IRI subject) {
        checkNull(subject, "subject");
        return getOWLAnnotationAssertionAxiom(getOWLDeprecated(), subject, getOWLLiteral(true));
    }

    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop, IRI domain, Set<? extends OWLAnnotation> annotations) {
        checkNull(prop, "prop");
        checkNull(domain, "domain");
        checkNull(annotations, "annotations");
        return new OWLAnnotationPropertyDomainAxiomImpl(prop, domain, annotations);
    }

    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop, IRI domain) {
        return getOWLAnnotationPropertyDomainAxiom(prop, domain, EMPTY_ANNOTATIONS_SET);
    }

    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop, IRI range, Set<? extends OWLAnnotation> annotations) {
        checkNull(prop, "prop");
        checkNull(range, "range");
        checkNull(annotations, "annotations");
        return new OWLAnnotationPropertyRangeAxiomImpl(prop, range, annotations);
    }

    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop, IRI range) {
        return getOWLAnnotationPropertyRangeAxiom(prop, range, EMPTY_ANNOTATIONS_SET);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param iri The rule IRI - this parameter is IGNORED since OWL axioms do
     * not have IRIs, and is here for backwards compatability.
     * @param body The atoms that make up the body of the rule
     * @param head The atoms that make up the head of the rule
     * @deprecated Use either
     *             {@link #getSWRLRule(java.util.Set, java.util.Set, java.util.Set)}
     *             or {@link #getSWRLRule(java.util.Set, java.util.Set)}
     *             instead. Gets a SWRL rule which is named with a URI
     */
    @Deprecated
    public SWRLRule getSWRLRule(IRI iri, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head) {
        checkNull(iri, "iri");
        checkNull(body, "body");
        checkNull(head, "head");
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>(2);
        annos.add(getOWLAnnotation(getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/owlapi#iri")), getOWLLiteral(iri.toQuotedString())));
        return new SWRLRuleImpl(body, head, annos);
    }

    /**
     * @param nodeID The node ID
     * @param body The atoms that make up the body of the rule
     * @param head The atoms that make up the head of the rule
     * @deprecated Use either
     *             {@link #getSWRLRule(java.util.Set, java.util.Set, java.util.Set)}
     *             or {@link #getSWRLRule(java.util.Set, java.util.Set)}
     *             instead.
     */
    @Deprecated
    public SWRLRule getSWRLRule(NodeID nodeID, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head) {
        checkNull(head, "head");
        checkNull(body, "body");
        checkNull(nodeID, "nodeID");
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>(2);
        annos.add(getOWLAnnotation(getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/owlapi#nodeID")), getOWLLiteral(nodeID.toString())));
        return new SWRLRuleImpl(body, head, annos);
    }

    /**
     * Gets an anonymous SWRL Rule
     * @param body The atoms that make up the body
     * @param head The atoms that make up the head
     * @param annotations The annotations for the rule (may be an empty set)
     * @return An anonymous rule with the specified body and head
     */
    public SWRLRule getSWRLRule(Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head, Set<OWLAnnotation> annotations) {
        checkNull(body, "body");
        checkNull(head, "head");
        checkNull(annotations, "annotations");
        return new SWRLRuleImpl(body, head, annotations);
    }

    /**
     * Gets a SWRL rule which is anonymous - i.e. isn't named with a URI
     * @param antecedent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */
    public SWRLRule getSWRLRule(Set<? extends SWRLAtom> antecedent, Set<? extends SWRLAtom> consequent) {
        checkNull(antecedent, "antecedent");
        checkNull(consequent, "consequent");
        return new SWRLRuleImpl(antecedent, consequent);
    }

    /**
     * Gets a SWRL class atom, i.e. C(x) where C is a class expression and x is
     * either an individual id or an i-variable
     * @param predicate The class expression that corresponds to the predicate
     * @param arg The argument (x)
     */
    public SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIArgument arg) {
        checkNull(predicate, "predicate");
        checkNull(arg, "arg");
        return new SWRLClassAtomImpl(predicate, arg);
    }

    /**
     * Gets a SWRL data range atom, i.e. D(x) where D is an OWL data range and x
     * is either a constant or a d-variable
     * @param predicate The data range that corresponds to the predicate
     * @param arg The argument (x)
     */
    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate, SWRLDArgument arg) {
        checkNull(predicate, "predicate");
        checkNull(arg, "arg");
        return new SWRLDataRangeAtomImpl(predicate, arg);
    }

    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object
     * property (expression) and x and y are are either an individual id or an
     * i-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */
    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIArgument arg0, SWRLIArgument arg1) {
        checkNull(property, "property");
        checkNull(arg0, "arg0");
        checkNull(arg1, "arg1");
        return new SWRLObjectPropertyAtomImpl(property, arg0, arg1);
    }

    /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data
     * property (expression) and x and y are are either a constant or a
     * d-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */
    public SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIArgument arg0, SWRLDArgument arg1) {
        checkNull(property, "property");
        checkNull(arg0, "arg0");
        checkNull(arg1, "arg1");
        return new SWRLDataPropertyAtomImpl(property, arg0, arg1);
    }

    /**
     * Creates a SWRL Built-In atom.
     * @param builtInIRI The SWRL builtIn (see SWRL W3 member submission)
     * @param args A non-empty set of SWRL D-Objects
     */
    public SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args) {
        checkNull(builtInIRI, "builtInIRI");
        checkNull(args, "args");
        return new SWRLBuiltInAtomImpl(builtInIRI, args);
    }

    /**
     * Gets a SWRLVariable.
     * @param var The id (IRI) of the variable
     * @return A SWRLVariable that has the name specified by the IRI
     */
    public SWRLVariable getSWRLVariable(IRI var) {
        checkNull(var, "var");
        return new SWRLVariableImpl(var);
    }

    /**
     * Gets a SWRL individual object.
     * @param individual The individual that is the object argument
     */
    public SWRLIndividualArgument getSWRLIndividualArgument(OWLIndividual individual) {
        checkNull(individual, "individual");
        return new SWRLIndividualArgumentImpl(individual);
    }

    /**
     * Gets a SWRL constant object.
     * @param literal The constant that is the object argument
     */
    public SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal) {
        checkNull(literal, "literal");
        return new SWRLLiteralArgumentImpl(literal);
    }

    public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument arg0, SWRLIArgument arg1) {
        checkNull(arg0, "arg0");
        checkNull(arg1, "arg1");
        return new SWRLDifferentIndividualsAtomImpl(this, arg0, arg1);
    }

    public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0, SWRLIArgument arg1) {
        checkNull(arg0, "arg0");
        checkNull(arg1, "arg1");
        return new SWRLSameIndividualAtomImpl(this, arg0, arg1);
    }

    private static final Set<OWLAnnotation> EMPTY_ANNOTATIONS_SET = Collections.emptySet();

    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype, OWLDataRange dataRange) {
        checkNull(datatype, DATATYPE2);
        checkNull(dataRange, DATA_RANGE);
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange, EMPTY_ANNOTATIONS_SET);
    }

    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype, OWLDataRange dataRange, Set<? extends OWLAnnotation> annotations) {
        checkNull(datatype, DATATYPE2);
        checkNull(dataRange, DATA_RANGE);
        checkNull(annotations, "annotations");
        return new OWLDatatypeDefinitionAxiomImpl(datatype, dataRange, annotations);
    }

    public OWLLiteral getOWLLiteral(String lexicalValue, OWLDatatype datatype) {
        checkNull(lexicalValue, "lexicalValue");
        checkNull(datatype, DATATYPE2);
        return data.getOWLLiteral(lexicalValue, datatype);
    }

    public OWLLiteral getOWLLiteral(int value) {
        return data.getOWLLiteral(value);
    }

    public OWLLiteral getOWLLiteral(double value) {
        return data.getOWLLiteral(value);
    }

    public OWLLiteral getOWLLiteral(float value) {
        return data.getOWLLiteral(value);
    }

    public OWLLiteral getOWLLiteral(String value) {
        checkNull(value, "value");
        return data.getOWLLiteral(value);
    }

    public OWLLiteral getOWLLiteral(String literal, String lang) {
        checkNull(literal, LITERAL2);
        return data.getOWLLiteral(literal, lang);
    }

    public OWLDatatype getBooleanOWLDatatype() {

        return data.getBooleanOWLDatatype();
    }

    public OWLDatatype getDoubleOWLDatatype() {
        return data.getDoubleOWLDatatype();
    }

    public OWLDatatype getFloatOWLDatatype() {

        return data.getFloatOWLDatatype();
    }

    public OWLDatatype getIntegerOWLDatatype() {

        return data.getIntegerOWLDatatype();
    }

    public OWLDatatype getTopDatatype() {

        return data.getTopDatatype();
    }

    public OWLDatatype getRDFPlainLiteral() {

        return data.getRDFPlainLiteral();
    }
}
