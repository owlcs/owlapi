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
import org.semanticweb.owlapi.vocab.XSDVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDataFactoryImpl implements OWLDataFactory {

    private static OWLDataFactory instance = new OWLDataFactoryImpl();

    private static OWLClass OWL_THING = new OWLClassImpl(instance, OWLRDFVocabulary.OWL_THING.getIRI());

    private static OWLClass OWL_NOTHING = new OWLClassImpl(instance, OWLRDFVocabulary.OWL_NOTHING.getIRI());

    protected OWLDataFactoryInternals data;


    public OWLDataFactoryImpl() {
        data = new OWLDataFactoryInternalsImpl(this);
    }

    public static OWLDataFactory getInstance() {
        return instance;
    }


    public void purge() {
        data.purge();
    }

    /**
     * Gets an entity that has the specified IRI and is of the specified type.
     * @param entityType The type of the entity that will be returned
     * @param iri The IRI of the entity that will be returned
     * @return An entity that has the same IRI as this entity and is of the specified type
     */
    @SuppressWarnings("unchecked")
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType, IRI iri) {
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
        return data.getOWLClass(iri);
    }

    public OWLClass getOWLClass(String iri, PrefixManager prefixManager) {
        return getOWLClass(prefixManager.getIRI(iri));
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(String abbreviatedIRI, PrefixManager prefixManager) {
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
        return getOWLDatatype(prefixManager.getIRI(abbreviatedIRI));
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


    public OWLDatatype getTopDatatype() {
        return getOWLDatatype(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
    }


    public OWLDatatype getIntegerOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.INTEGER.getIRI());
    }


    public OWLDatatype getFloatOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.FLOAT.getIRI());
    }


    public OWLDatatype getDoubleOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.DOUBLE.getIRI());
    }


    public OWLDatatype getBooleanOWLDatatype() {
        return getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI());
    }

    public OWLDatatype getRDFPlainLiteral() {
        return getOWLDatatype(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
    }

    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return data.getOWLObjectProperty(iri);
    }

    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return data.getOWLDataProperty(iri);
    }

    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return data.getOWLNamedIndividual(iri);
    }

    public OWLDataProperty getOWLDataProperty(String curi, PrefixManager prefixManager) {
        return getOWLDataProperty(prefixManager.getIRI(curi));
    }


    public OWLNamedIndividual getOWLNamedIndividual(String curi, PrefixManager prefixManager) {
        return getOWLNamedIndividual(prefixManager.getIRI(curi));
    }


    public OWLObjectProperty getOWLObjectProperty(String curi, PrefixManager prefixManager) {
        return getOWLObjectProperty(prefixManager.getIRI(curi));
    }


    public OWLAnonymousIndividual getOWLAnonymousIndividual(String id) {
        if (id == null) {
            throw new NullPointerException("ID for anonymous individual is null");
        }
        return new OWLAnonymousIndividualImpl(this, NodeID.getNodeID(id));
    }

    /**
     * Gets an anonymous individual.  The node ID for the individual will be generated automatically
     * @return The anonymous individual
     */
    public OWLAnonymousIndividual getOWLAnonymousIndividual() {
        return new OWLAnonymousIndividualImpl(this, NodeID.getNodeID());
    }

    public OWLDatatype getOWLDatatype(IRI iri) {
        return data.getOWLDatatype(iri);
    }

    public OWLLiteral getOWLLiteral(String lexicalValue, OWLDatatype datatype) {
        OWLLiteral literal;
        if (datatype.isRDFPlainLiteral()) {
            int sep = lexicalValue.lastIndexOf('@');
            if (sep != -1) {
                String lex = lexicalValue.substring(0, sep);
                String lang = lexicalValue.substring(sep + 1);
                literal = new OWLLiteralImpl(this, lex, lang);
            }
            else {
                literal = new OWLLiteralImpl(this, lexicalValue, datatype);
            }
        }
        else {
            literal = new OWLLiteralImpl(this, lexicalValue, datatype);
        }
        return literal;
    }

    public OWLLiteral getOWLLiteral(String lexicalValue, OWL2Datatype datatype) {
        return getOWLLiteral(lexicalValue, getOWLDatatype(datatype.getIRI()));
    }

    public OWLLiteral getOWLLiteral(int value) {
        return new OWLLiteralImpl(this, Integer.toString(value), getOWLDatatype(XSDVocabulary.INTEGER.getIRI()));
    }


    public OWLLiteral getOWLLiteral(double value) {
        return new OWLLiteralImpl(this, Double.toString(value), getOWLDatatype(XSDVocabulary.DOUBLE.getIRI()));
    }


    public OWLLiteral getOWLLiteral(boolean value) {
        return new OWLLiteralImpl(this, Boolean.toString(value), getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI()));
    }


    public OWLLiteral getOWLLiteral(float value) {
        return new OWLLiteralImpl(this, Float.toString(value), getOWLDatatype(XSDVocabulary.FLOAT.getIRI()));
    }


    public OWLLiteral getOWLLiteral(String value) {
        return new OWLLiteralImpl(this, value, getOWLDatatype(XSDVocabulary.STRING.getIRI()));
    }


    public OWLLiteral getOWLLiteral(String literal, String lang) {
        if (literal == null) {
            throw new NullPointerException("literal argument is null");
        }
        String normalisedLang;
        if (lang == null) {
            normalisedLang = "";
        }
        else {
            normalisedLang = lang.trim().toLowerCase();
        }
        return new OWLLiteralImpl(this, literal, normalisedLang);
    }

    /**
     * @deprecated Use {@link #getOWLLiteral(String, org.semanticweb.owlapi.model.OWLDatatype)}
     */
    @Deprecated
    public OWLLiteral getOWLTypedLiteral(String literal, OWLDatatype datatype) {
        return getOWLLiteral(literal, datatype);
    }

    /**
     * @param literal The literal
     * @param datatype The OWL 2 Datatype that will type the literal
     * @return The typed literal
     * @deprecated Use {@link #getOWLLiteral(String, org.semanticweb.owlapi.vocab.OWL2Datatype)}
     *             Creates a typed literal that has the specified OWL 2 Datatype as its datatype
     */
    @Deprecated
    public OWLLiteral getOWLTypedLiteral(String literal, OWL2Datatype datatype) {
        return getOWLLiteral(literal, datatype);
    }

    /**
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the integer, and whose data type is xsd:integer.
     * @deprecated Use {@link #getOWLLiteral(int)}
     *             Convenience method that obtains a literal typed as an integer.
     */
    @Deprecated
	public OWLLiteral getOWLTypedLiteral(int value) {
        return getOWLLiteral(value);
    }

    /**
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the double, and whose data type is xsd:double.
     * @deprecated Use {@link #getOWLLiteral(double)}
     *             Convenience method that obtains a literal typed as a double.
     */
    @Deprecated
	public OWLLiteral getOWLTypedLiteral(double value) {
        return getOWLLiteral(value);
    }

    /**
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the boolean, and whose data type is xsd:boolean.
     * @deprecated Use {@link #getOWLLiteral(boolean)}
     *             Convenience method that obtains a literal typed as a boolean.
     */
    @Deprecated
	public OWLLiteral getOWLTypedLiteral(boolean value) {
        return getOWLLiteral(value);
    }

    /**
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the float, and whose data type is xsd:float.
     * @deprecated Use {@link #getOWLLiteral(float)}
     *             Convenience method that obtains a literal typed as a float.
     */
    @Deprecated
	public OWLLiteral getOWLTypedLiteral(float value) {
        return getOWLLiteral(value);
    }

    /**
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the string, and whose data type is xsd:string.
     * @deprecated Use {@link #getOWLLiteral(String)}
     *             Convenience method that obtains a literal typed as a string.
     */
    @Deprecated
    public OWLLiteral getOWLTypedLiteral(String value) {
        return getOWLLiteral(value);
    }

    /**
     * @param literal The string literal
     * @param lang The language tag.  The tag is formed according to <a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">BCP47</a>
     * but the OWL API will not check that the tag conforms to this specification - it is up to the caller to ensure this.  For backwards
     * compatibility, if the value of lang is <code>null</code> then this is equivalent to calling the getOWLStringLiteral(String literal)
     * method.
     * @return The OWLStringLiteral that represents the string literal with a language tag.
     * @deprecated Use {@link #getOWLLiteral(String, String)}
     *             Gets an OWLStringLiteral with a language tag.
     */
    @Deprecated
    public OWLLiteral getOWLStringLiteral(String literal, String lang) {
        return getOWLLiteral(literal, lang);
    }

    /**
     * @param literal The string literal
     * @return The string literal for the specfied string
     * @deprecated Use {@link #getOWLLiteral(String, String)} with the second parameter as the empty string ("").
     *             Gets a string literal without a language tag.
     */    
    @Deprecated
    public OWLLiteral getOWLStringLiteral(String literal) {
        return getOWLLiteral(literal, "");
    }

    public OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values) {
        return new OWLDataOneOfImpl(this, values);
    }


    public OWLDataOneOf getOWLDataOneOf(OWLLiteral... values) {
        return getOWLDataOneOf(CollectionFactory.createSet(values));
    }


    public OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange) {
        return new OWLDataComplementOfImpl(this, dataRange);
    }


    public OWLDataIntersectionOf getOWLDataIntersectionOf(OWLDataRange... dataRanges) {
        return getOWLDataIntersectionOf(CollectionFactory.createSet(dataRanges));
    }


    public OWLDataIntersectionOf getOWLDataIntersectionOf(Set<? extends OWLDataRange> dataRanges) {
        return new OWLDataIntersectionOfImpl(this, dataRanges);
    }


    public OWLDataUnionOf getOWLDataUnionOf(OWLDataRange... dataRanges) {
        return getOWLDataUnionOf(CollectionFactory.createSet(dataRanges));
    }


    public OWLDataUnionOf getOWLDataUnionOf(Set<? extends OWLDataRange> dataRanges) {
        return new OWLDataUnionOfImpl(this, dataRanges);
    }


    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype datatype, Set<OWLFacetRestriction> facets) {
        return new OWLDatatypeRestrictionImpl(this, datatype, facets);
    }


    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype datatype, OWLFacet facet, OWLLiteral typedConstant) {
        return new OWLDatatypeRestrictionImpl(this, datatype, Collections.singleton(getOWLFacetRestriction(facet, typedConstant)));
    }


    public OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange, OWLFacetRestriction... facetRestrictions) {
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
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(double maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MAX_INCLUSIVE, getOWLLiteral(maxInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(double minInclusive, double maxInclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, getOWLLiteral(minInclusive)), getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, maxInclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(double minExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(double maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), OWLFacet.MAX_EXCLUSIVE, getOWLLiteral(maxExclusive));
    }

    public OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(double minExclusive, double maxExclusive) {
        return getOWLDatatypeRestriction(getIntegerOWLDatatype(), getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, getOWLLiteral(minExclusive)), getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));
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
        return new OWLFacetRestrictionImpl(this, facet, facetValue);
    }


    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectIntersectionOfImpl(this, operands);
    }


    public OWLObjectIntersectionOf getOWLObjectIntersectionOf(OWLClassExpression... operands) {
        return getOWLObjectIntersectionOf(CollectionFactory.createSet(operands));
    }


    public OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange) {
        if(dataRange == null) {
            throw new NullPointerException("The filler of the restriction (dataRange) must not be null");
        }
        return new OWLDataAllValuesFromImpl(this, property, dataRange);
    }


    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property) {
        return new OWLDataExactCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        if(dataRange == null) {
            throw new NullPointerException("The filler of the restriction (dataRange) must not be null");
        }
        return new OWLDataExactCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property) {
        return new OWLDataMaxCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        if(dataRange == null) {
            throw new NullPointerException("The filler of the restriction (dataRange) must not be null");
        }
        return new OWLDataMaxCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property) {
        return new OWLDataMinCardinalityImpl(this, property, cardinality, getTopDatatype());
    }


    public OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange) {
        if(dataRange == null) {
            throw new NullPointerException("The filler of the restriction (dataRange) must not be null");
        }
        return new OWLDataMinCardinalityImpl(this, property, cardinality, dataRange);
    }


    public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange) {
        if(dataRange == null) {
            throw new NullPointerException("The filler of the restriction (dataRange) must not be null");
        }
        return new OWLDataSomeValuesFromImpl(this, property, dataRange);
    }


    public OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property, OWLLiteral value) {
        return new OWLDataHasValueImpl(this, property, value);
    }


    public OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand) {
        return new OWLObjectComplementOfImpl(this, operand);
    }


    public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        if(classExpression == null) {
            throw new NullPointerException("The filler of the restriction (classExpression) must not be null");
        }
        return new OWLObjectAllValuesFromImpl(this, property, classExpression);
    }


    public OWLObjectOneOf getOWLObjectOneOf(Set<? extends OWLIndividual> values) {
        return new OWLObjectOneOfImpl(this, values);
    }


    public OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals) {
        return getOWLObjectOneOf(CollectionFactory.createSet(individuals));
    }


    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property) {
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, OWL_THING);
    }


    public OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        if(classExpression == null) {
            throw new NullPointerException("The filler of the restriction (classExpression) must not be null");
        }
        return new OWLObjectExactCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property) {
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, OWL_THING);
    }


    public OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        if(classExpression == null) {
            throw new NullPointerException("The filler of the restriction (classExpression) must not be null");
        }
        return new OWLObjectMinCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property) {
        return new OWLObjectMaxCardinalityImpl(this, property, cardinality, OWL_THING);
    }


    public OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        if(classExpression == null) {
            throw new NullPointerException("The filler of the restriction (classExpression) must not be null");
        }
        return new OWLObjectMaxCardinalityImpl(this, property, cardinality, classExpression);
    }


    public OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property) {
        return new OWLObjectHasSelfImpl(this, property);
    }


    public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        if(classExpression == null) {
            throw new NullPointerException("The filler of the restriction (classExpression) must not be null");
        }
        return new OWLObjectSomeValuesFromImpl(this, property, classExpression);
    }


    public OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property, OWLIndividual individual) {
        return new OWLObjectHasValueImpl(this, property, individual);
    }


    public OWLObjectUnionOf getOWLObjectUnionOf(Set<? extends OWLClassExpression> operands) {
        return new OWLObjectUnionOfImpl(this, operands);
    }


    public OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands) {
        return getOWLObjectUnionOf(CollectionFactory.createSet(operands));
    }


    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression, Set<? extends OWLAnnotation> annotations) {
        return new OWLAsymmetricObjectPropertyAxiomImpl(this, propertyExpression, annotations);
    }


    public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression) {
        return getOWLAsymmetricObjectPropertyAxiom(propertyExpression, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLClassExpression domain, Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyDomainAxiomImpl(this, property, domain, annotations);
    }


    public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLClassExpression domain) {
        return getOWLDataPropertyDomainAxiom(property, domain, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery, OWLDataRange owlDataRange, Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyRangeAxiomImpl(this, propery, owlDataRange, annotations);
    }


    public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery, OWLDataRange owlDataRange) {
        return getOWLDataPropertyRangeAxiom(propery, owlDataRange, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations) {
        return new OWLSubDataPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {
        return getOWLSubDataPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    /**
     * Gets a declaration for an entity
     * @param owlEntity The declared entity.
     * @return The declaration axiom for the specified entity.
     * @throws NullPointerException if owlEntity is <code>null</code>
     */

    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity) {
        if (owlEntity == null) {
            throw new NullPointerException("owlEntity");
        }
        return getOWLDeclarationAxiom(owlEntity, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity, Set<? extends OWLAnnotation> annotations) {
        if (owlEntity == null) {
            throw new NullPointerException("owlEntity");
        }
        if (annotations == null) {
            throw new NullPointerException("annotations");
        }
        return new OWLDeclarationAxiomImpl(this, owlEntity, annotations);
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals, Set<? extends OWLAnnotation> annotations) {
        return new OWLDifferentIndividualsAxiomImpl(this, individuals, annotations);
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual... individuals) {
        return getOWLDifferentIndividualsAxiom(CollectionFactory.createSet(individuals));
    }


    public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals) {
        return getOWLDifferentIndividualsAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointClassesAxiomImpl(this, classExpressions, annotations);
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression... classExpressions) {
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLDisjointClassesAxiom(clses);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLDisjointDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        return getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        return getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLDisjointObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentClassesAxiomImpl(this, classExpressions, annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB) {
        return getOWLEquivalentClassesAxiom(clsA, clsB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB, Set<? extends OWLAnnotation> annotations) {
        return getOWLEquivalentClassesAxiom(CollectionFactory.createSet(clsA, clsB), annotations);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression... classExpressions) {
        Set<OWLClassExpression> clses = new HashSet<OWLClassExpression>();
        clses.addAll(Arrays.asList(classExpressions));
        return getOWLEquivalentClassesAxiom(clses);
    }


    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions) {
        return getOWLEquivalentClassesAxiom(classExpressions, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentDataPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties) {
        return getOWLEquivalentDataPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB) {
        return getOWLEquivalentDataPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB, Set<? extends OWLAnnotation> annotations) {
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }


    public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression... properties) {
        return getOWLEquivalentDataPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression... properties) {
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(properties));
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties) {
        return getOWLEquivalentObjectPropertiesAxiom(properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB) {
        return getOWLEquivalentObjectPropertiesAxiom(propertyA, propertyB, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB, Set<? extends OWLAnnotation> annotations) {
        return getOWLEquivalentObjectPropertiesAxiom(CollectionFactory.createSet(propertyA, propertyB), annotations);
    }


    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        return new OWLFunctionalDataPropertyAxiomImpl(this, property, annotations);
    }


    public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property) {
        return getOWLFunctionalDataPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        return new OWLFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI) {
        return new OWLImportsDeclarationImpl(importedOntologyIRI);
    }

    public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object, Set<? extends OWLAnnotation> annotations) {
        return new OWLDataPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
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
        return new OWLNegativeDataPropertyAssertionImplAxiom(this, subject, property, object, annotations);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object) {
        return getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        return new OWLNegativeObjectPropertyAssertionAxiomImpl(this, subject, property, object, annotations);
    }


    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object) {
        return getOWLObjectPropertyAssertionAxiom(property, individual, object, EMPTY_ANNOTATIONS_SET);
    }


    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual) {
        return getOWLClassAssertionAxiom(classExpression, individual, EMPTY_ANNOTATIONS_SET);
    }


    public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual, Set<? extends OWLAnnotation> annotations) {
        return new OWLClassAssertionImpl(this, individual, classExpression, annotations);
    }


    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLInverseFunctionalObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        return new OWLInverseFunctionalObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        return new OWLIrreflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLReflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLIrreflexiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property, OWLClassExpression classExpression, Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyDomainAxiomImpl(this, property, classExpression, annotations);
    }


    public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        return getOWLObjectPropertyDomainAxiom(property, classExpression, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property, OWLClassExpression range, Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyRangeAxiomImpl(this, property, range, annotations);
    }


    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property, OWLClassExpression range) {
        return getOWLObjectPropertyRangeAxiom(property, range, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations) {
        return new OWLSubObjectPropertyOfAxiomImpl(this, subProperty, superProperty, annotations);
    }


    public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {
        return getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        return new OWLReflexiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals, Set<? extends OWLAnnotation> annotations) {
        return new OWLSameIndividualAxiomImpl(this, individuals, annotations);
    }


    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(OWLIndividual... individuals) {
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        inds.addAll(Arrays.asList(individuals));
        return getOWLSameIndividualAxiom(inds);
    }


    public OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals) {
        return getOWLSameIndividualAxiom(individuals, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass, Set<? extends OWLAnnotation> annotations) {
        return new OWLSubClassOfAxiomImpl(this, subClass, superClass, annotations);
    }


    public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass) {
        return getOWLSubClassOfAxiom(subClass, superClass, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        return new OWLSymmetricObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLSymmetricObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations) {
        return new OWLTransitiveObjectPropertyAxiomImpl(this, property, annotations);
    }


    public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        return getOWLTransitiveObjectPropertyAxiom(property, EMPTY_ANNOTATIONS_SET);
    }


    public OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectPropertyExpression property) {
        return new OWLObjectInverseOfImpl(this, property);
    }


    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty, Set<? extends OWLAnnotation> annotations) {
        return new OWLInverseObjectPropertiesAxiomImpl(this, forwardProperty, inverseProperty, annotations);
    }


    public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty) {
        return getOWLInverseObjectPropertiesAxiom(forwardProperty, inverseProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations) {
        return new OWLSubPropertyChainAxiomImpl(this, chain, superProperty, annotations);
    }


    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {
        return getOWLSubPropertyChainOfAxiom(chain, superProperty, EMPTY_ANNOTATIONS_SET);
    }


    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, Set<? extends OWLPropertyExpression<?,?>> properties, Set<? extends OWLAnnotation> annotations) {
        return new OWLHasKeyAxiomImpl(this, ce, properties, annotations);
    }


    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, Set<? extends OWLPropertyExpression<?,?>> properties) {
        return getOWLHasKeyAxiom(ce, properties, EMPTY_ANNOTATIONS_SET);
    }


    public OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, OWLPropertyExpression<?,?>... properties) {
        return getOWLHasKeyAxiom(ce, CollectionFactory.createSet(properties));
    }


    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass, Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        return new OWLDisjointUnionAxiomImpl(this, owlClass, classExpressions, annotations);
    }


    public OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass, Set<? extends OWLClassExpression> classExpressions) {
        return getOWLDisjointUnionAxiom(owlClass, classExpressions, EMPTY_ANNOTATIONS_SET);
    }


    public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties, Set<? extends OWLAnnotation> annotations) {
        return new OWLEquivalentObjectPropertiesAxiomImpl(this, properties, annotations);
    }


    public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        return new OWLObjectPropertyAssertionAxiomImpl(this, individual, property, object, annotations);
    }


    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub, OWLAnnotationProperty sup) {
        return getOWLSubAnnotationPropertyOfAxiom(sub, sup, EMPTY_ANNOTATIONS_SET);
    }

    public OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub, OWLAnnotationProperty sup, Set<? extends OWLAnnotation> annotations) {
        return new OWLSubAnnotationPropertyOfAxiomImpl(this, sub, sup, annotations);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Annotations


    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return data.getOWLAnnotationProperty(iri);
    }

    /**
     * Gets an annotation
     * @param property the annotation property
     * @param value The annotation value
     * @return The annotation on the specified property with the specified value
     */
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value) {
        return getOWLAnnotation(property, value, EMPTY_ANNOTATIONS_SET);
    }


    /**
     * Gets an annotation
     * @param property the annotation property
     * @param value The annotation value
     * @param annotations Annotations on the annotation
     * @return The annotation on the specified property with the specified value
     */
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationImpl(this, property, value, annotations);
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject, OWLAnnotation annotation) {
        // PATCH: 	return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue(), annotation.getAnnotations());
        // ORIG: 	return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue());
        // The patch makes a difference for the owl, owlfs, rdfxml and turtle serializations of Annotation2.
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue(), annotation.getAnnotations());
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject, OWLAnnotation annotation, Set<? extends OWLAnnotation> annotations) {
        return getOWLAnnotationAssertionAxiom(annotation.getProperty(), subject, annotation.getValue(), annotations);
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value) {
        return getOWLAnnotationAssertionAxiom(property, subject, value, EMPTY_ANNOTATIONS_SET);
    }


    public OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations) {
        if (property == null) {
            throw new NullPointerException("Annotation property is null");
        }
        if (subject == null) {
            throw new NullPointerException("Annotation subject is null");
        }
        if (value == null) {
            throw new NullPointerException("Annotation value is null");
        }
        return new OWLAnnotationAssertionAxiomImpl(this, subject, property, value, annotations);
    }

    /**
     * Gets an annotation assertion that specifies that an IRI is deprecated.  The annotation property is
     * owl:deprecated and the value of the annotation is <code>"true"^^xsd:boolean</code>.  (See
     * <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Properties">Annotation Properties</a> in
     * the OWL 2 Specification
     * @param subject The IRI to be deprecated.
     * @return The annotation assertion that deprecates the specified IRI.
     */
    public OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(IRI subject) {
        return getOWLAnnotationAssertionAxiom(getOWLDeprecated(), subject, getOWLLiteral(true));
    }

    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop, IRI domain, Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationPropertyDomainAxiomImpl(this, prop, domain, annotations);
    }


    public OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop, IRI domain) {
        return getOWLAnnotationPropertyDomainAxiom(prop, domain, EMPTY_ANNOTATIONS_SET);
    }


    public OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop, IRI range, Set<? extends OWLAnnotation> annotations) {
        return new OWLAnnotationPropertyRangeAxiomImpl(this, prop, range, annotations);
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
     * @param iri The rule IRI - this parameter is IGNORED since OWL axioms do not have IRIs,
     * and is here for backwards compatability.
     * @param body The atoms that make up the body of the rule
     * @param head The atoms that make up the head of the rule
     * @deprecated Use either {@link #getSWRLRule(java.util.Set, java.util.Set, java.util.Set)} or
     *             {@link #getSWRLRule(java.util.Set, java.util.Set)} instead.
     *             Gets a SWRL rule which is named with a URI
     */
    @Deprecated
    public SWRLRule getSWRLRule(IRI iri, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head) {
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>(2);
        annos.add(getOWLAnnotation(getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/owlapi#iri")), getOWLLiteral(iri.toQuotedString())));
        return new SWRLRuleImpl(this, body, head, annos);
    }


    /**
     * @param nodeID The node ID
     * @param body The atoms that make up the body of the rule
     * @param head The atoms that make up the head of the rule
     * @deprecated Use either {@link #getSWRLRule(java.util.Set, java.util.Set, java.util.Set)} or
     *             {@link #getSWRLRule(java.util.Set, java.util.Set)} instead.
     */
    @Deprecated
    public SWRLRule getSWRLRule(NodeID nodeID, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head) {
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>(2);
        annos.add(getOWLAnnotation(getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/owlapi#nodeID")), getOWLLiteral(nodeID.toString())));
        return new SWRLRuleImpl(this, body, head, annos);
    }

    /**
     * Gets an anonymous SWRL Rule
     * @param body The atoms that make up the body
     * @param head The atoms that make up the head
     * @param annotations The annotations for the rule (may be an empty set)
     * @return An anonymous rule with the specified body and head
     */
    public SWRLRule getSWRLRule(Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head, Set<OWLAnnotation> annotations) {
        return new SWRLRuleImpl(this, body, head, annotations);
    }

    /**
     * Gets a SWRL rule which is anonymous - i.e. isn't named with a URI
     * @param antecedent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */

    public SWRLRule getSWRLRule(Set<? extends SWRLAtom> antecedent, Set<? extends SWRLAtom> consequent) {
        return new SWRLRuleImpl(this, antecedent, consequent);
    }


    /**
     * Gets a SWRL class atom, i.e.  C(x) where C is a class expression and
     * x is either an individual id or an i-variable
     * @param predicate The class expression that corresponds to the predicate
     * @param arg The argument (x)
     */

    public SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIArgument arg) {
        return new SWRLClassAtomImpl(this, predicate, arg);
    }


    /**
     * Gets a SWRL data range atom, i.e.  D(x) where D is an OWL data range and
     * x is either a constant or a d-variable
     * @param predicate The data range that corresponds to the predicate
     * @param arg The argument (x)
     */

    public SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate, SWRLDArgument arg) {
        return new SWRLDataRangeAtomImpl(this, predicate, arg);
    }


    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object
     * property (expression) and x and y are are either an individual id or
     * an i-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */

    public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIArgument arg0, SWRLIArgument arg1) {
        return new SWRLObjectPropertyAtomImpl(this, property, arg0, arg1);
    }


    /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data
     * property (expression) and x and y are are either a constant or
     * a d-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */

    public SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIArgument arg0, SWRLDArgument arg1) {
        return new SWRLDataPropertyAtomImpl(this, property, arg0, arg1);
    }


    /**
     * Creates a SWRL Built-In atom.
     * @param builtInIRI The SWRL builtIn (see SWRL W3 member submission)
     * @param args A non-empty set of SWRL D-Objects
     */

    public SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args) {
        return new SWRLBuiltInAtomImpl(this, builtInIRI, args);
    }


    /**
     * Gets a SWRLVariable.
     * @param var The id (IRI) of the variable
     * @return A SWRLVariable that has the name specified by the IRI
     */
    public SWRLVariable getSWRLVariable(IRI var) {
        return new SWRLVariableImpl(this, var);
    }

    /**
     * Gets a SWRL individual object.
     * @param individual The individual that is the object argument
     */

    public SWRLIndividualArgument getSWRLIndividualArgument(OWLIndividual individual) {
        return new SWRLIndividualArgumentImpl(this, individual);
    }


    /**
     * Gets a SWRL constant object.
     * @param literal The constant that is the object argument
     */

    public SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal) {
        return new SWRLLiteralArgumentImpl(this, literal);
    }


    public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument arg0, SWRLIArgument arg1) {
        return new SWRLDifferentIndividualsAtomImpl(this, arg0, arg1);
    }


    public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0, SWRLIArgument arg1) {
        return new SWRLSameIndividualAtomImpl(this, arg0, arg1);
    }


    private static Set<OWLAnnotation> EMPTY_ANNOTATIONS_SET = Collections.emptySet();


    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype, OWLDataRange dataRange) {
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange, EMPTY_ANNOTATIONS_SET);
    }


    public OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype, OWLDataRange dataRange, Set<? extends OWLAnnotation> annotations) {
        return new OWLDatatypeDefinitionAxiomImpl(this, datatype, dataRange, annotations);
    }
}
