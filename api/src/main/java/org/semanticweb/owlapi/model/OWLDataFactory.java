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
package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group Date: 24-Oct-2006
 * <p/>
 * An interface for creating entities, class expressions and axioms. All methods
 * throw IllegalArgumentException if null values are passed where they are not
 * allowed in the documentation. */
public interface OWLDataFactory extends SWRLDataFactory {
    // Entities and data stuff
    /** Gets the built in owl:Thing class, which has a URI of
     * &lt;http://www.w3.org/2002/07/owl#Thing&gt;
     * 
     * @return The OWL Class corresponding to owl:Thing */
    @Nonnull
    OWLClass getOWLThing();

    /** Gets the built in owl:Nothing class, which has a URI of
     * &lt;http://www.w3.org/2002/07/owl#Nothing&gt;
     * 
     * @return The OWL Class corresponding to owl:Nothing */
    @Nonnull
    OWLClass getOWLNothing();

    /** @return the built in top object property */
    @Nonnull
    OWLObjectProperty getOWLTopObjectProperty();

    /** @return the built in top data property */
    @Nonnull
    OWLDataProperty getOWLTopDataProperty();

    /** @return the built in bottom object property */
    @Nonnull
    OWLObjectProperty getOWLBottomObjectProperty();

    /** @return the built in bottom data property */
    @Nonnull
    OWLDataProperty getOWLBottomDataProperty();

    /** Gets the built in data range corresponding to the top data type (like
     * owl:Thing but for data ranges), this datatype is rdfs:Literal, and has a
     * URI of $lt;http://www.w3.org/2000/01/rdf-schema#&gt;
     * 
     * @return The OWL Datatype corresponding to the top data type. */
    @Nonnull
    OWLDatatype getTopDatatype();

    /** Gets an entity that has the specified IRI and is of the specified type.
     * 
     * @param entityType
     *            The type of the entity that will be returned. Cannot be null.
     * @param iri
     *            The IRI of the entity that will be returned. Cannot be null.
     * @return An entity that has the same IRI as this entity and is of the
     *         specified type */
    @Nonnull
    <E extends OWLEntity> E getOWLEntity(@Nonnull EntityType<E> entityType,
            @Nonnull IRI iri);

    /** Gets an OWL class that has the specified IRI
     * 
     * @param iri
     *            The IRI of the class. Cannot be null.
     * @return The object representing the class that has the specified IRI */
    @Nonnull
    OWLClass getOWLClass(@Nonnull IRI iri);

    /** Gets an OWLClass that has an IRI that is obtained by expanding an
     * abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2
     * Structural Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *            The abbreviated IRI, which is of the form PREFIX_NAME:RC,
     *            where PREFIX_NAME may be the empty string (the default
     *            prefix). Cannot be null. Note that abbreviated IRIs always
     *            contain a colon as a delimiter, even if the prefix name is the
     *            empty string.
     * @param prefixManager
     *            The prefix manager that is responsible for mapping prefix
     *            names to prefix IRIs. Cannot be null.
     * @return An OWLClass that has the IRI obtained by expanding the specified
     *         abbreviated IRI using the specified prefix manager. </p> For
     *         example, suppose "m:Cat" was specified as the abbreviated IRI,
     *         the prefix manager would be used to obtain the IRI prefix for the
     *         "m:" prefix name, this prefix would then be concatenated with
     *         "Cat" to obtain the full IRI which would be the IRI of the
     *         OWLClass obtained by this method.
     * @throws OWLRuntimeException
     *             if the prefix name in the specified abbreviated IRI does not
     *             have a mapping to a prefix in the specified prefix manager. */
    @Nonnull
    OWLClass getOWLClass(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager prefixManager);

    /** Gets an OWL object property that has the specified IRI
     * 
     * @param iri
     *            The IRI of the object property to be obtained. Cannot be null.
     * @return The object representing the object property that has the
     *         specified IRI */
    @Nonnull
    OWLObjectProperty getOWLObjectProperty(@Nonnull IRI iri);

    /** Gets an OWLObjectProperty that has an IRI that is obtained by expanding
     * an abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2
     * Structural Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *            The abbreviated IRI, which is of the form PREFIX_NAME:RC,
     *            where PREFIX_NAME may be the empty string (the default
     *            prefix). Cannot be null. Note that abbreviated IRIs always
     *            contain a colon as a delimiter, even if the prefix name is the
     *            empty string.
     * @param prefixManager
     *            The prefix manager that is responsible for mapping prefix
     *            names to prefix IRIs. Cannot be null.
     * @return An OWLObjectProperty that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager.
     *         </p> For example, suppose "m:Cat" was specified as the
     *         abbreviated IRI, the prefix manager would be used to obtain the
     *         IRI prefix for the "m:" prefix name, this prefix would then be
     *         concatenated with "Cat" to obtain the full IRI which would be the
     *         IRI of the OWLObjectProperty obtained by this method.
     * @throws OWLRuntimeException
     *             if the prefix name in the specified abbreviated IRI does not
     *             have a mapping to a prefix in the specified prefix manager. */
    @Nonnull
    OWLObjectProperty getOWLObjectProperty(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager prefixManager);

    /** Gets the inverse of an object property.
     * 
     * @param property
     *            The property of which the inverse will be returned. Cannot be
     *            null.
     * @return The inverse of the specified object property */
    @Nonnull
    OWLObjectInverseOf
            getOWLObjectInverseOf(@Nonnull OWLObjectPropertyExpression property);

    /** Gets an OWL data property that has the specified IRI
     * 
     * @param iri
     *            The IRI of the data property to be obtained. Cannot be null.
     * @return The object representing the data property that has the specified
     *         IRI */
    @Nonnull
    OWLDataProperty getOWLDataProperty(@Nonnull IRI iri);

    /** Gets an OWLDataProperty that has an IRI that is obtained by expanding an
     * abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2
     * Structural Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *            The abbreviated IRI, which is of the form PREFIX_NAME:RC,
     *            where PREFIX_NAME may be the empty string (the default
     *            prefix). Note that abbreviated IRIs always contain a colon as
     *            a delimiter, even if the prefix name is the empty string.
     *            Cannot be null.
     * @param prefixManager
     *            The prefix manager that is responsible for mapping prefix
     *            names to prefix IRIs. Cannot be null.
     * @return An OWLDataProperty that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager.
     *         </p> For example, suppose "m:Cat" was specified as the
     *         abbreviated IRI, the prefix manager would be used to obtain the
     *         IRI prefix for the "m:" prefix name, this prefix would then be
     *         concatenated with "Cat" to obtain the full IRI which would be the
     *         IRI of the OWLDataProperty obtained by this method.
     * @throws OWLRuntimeException
     *             if the prefix name in the specified abbreviated IRI does not
     *             have a mapping to a prefix in the specified prefix manager. */
    @Nonnull
    OWLDataProperty getOWLDataProperty(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager prefixManager);

    /** Gets an OWL individual that has the specified IRI
     * 
     * @param iri
     *            The IRI of the individual to be obtained. Cannot be null.
     * @return The object representing the individual that has the specified IRI */
    @Nonnull
    OWLNamedIndividual getOWLNamedIndividual(@Nonnull IRI iri);

    /** Gets an OWLNamedIndividual that has an IRI that is obtained by expanding
     * an abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2
     * Structural Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *            The abbreviated IRI, which is of the form PREFIX_NAME:RC,
     *            where PREFIX_NAME may be the empty string (the default
     *            prefix). Note that abbreviated IRIs always contain a colon as
     *            a delimiter, even if the prefix name is the empty string.
     *            Cannot be null.
     * @param prefixManager
     *            The prefix manager that is responsible for mapping prefix
     *            names to prefix IRIs. Cannot be null.
     * @return An OWLNamedIndividual that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager.
     *         </p> For example, suppose "m:Cat" was specified as the
     *         abbreviated IRI, the prefix manager would be used to obtain the
     *         IRI prefix for the "m:" prefix name, this prefix would then be
     *         concatenated with "Cat" to obtain the full IRI which would be the
     *         IRI of the OWLNamedIndividual obtained by this method.
     * @throws OWLRuntimeException
     *             if the prefix name in the specified abbreviated IRI does not
     *             have a mapping to a prefix in the specified prefix manager. */
    @Nonnull
    OWLNamedIndividual getOWLNamedIndividual(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager prefixManager);

    /** Gets an anonymous individual that has a specific ID.
     * 
     * @param id
     *            The node ID. Note that the ID will be prefixed with _: if it
     *            is not specified with _: as a prefix. Cannot be null.
     * @return An anonymous individual. */
    @Nonnull
    OWLAnonymousIndividual getOWLAnonymousIndividual(@Nonnull String id);

    /** Gets an anonymous individual. The node ID for the individual will be
     * generated automatically
     * 
     * @return The anonymous individual */
    @Nonnull
    OWLAnonymousIndividual getOWLAnonymousIndividual();

    /** Gets an OWLAnnotationProperty that has the specified IRI
     * 
     * @param iri
     *            The IRI of the annotation property to be obtained. Cannot be
     *            null.
     * @return An OWLAnnotationProperty with the specified IRI */
    @Nonnull
    OWLAnnotationProperty getOWLAnnotationProperty(@Nonnull IRI iri);

    /** Gets an OWLAnnotationProperty that has an IRI that is obtained by
     * expanding an abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2
     * Structural Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *            The abbreviated IRI, which is of the form PREFIX_NAME:RC,
     *            where PREFIX_NAME may be the empty string (the default
     *            prefix). Note that abbreviated IRIs always contain a colon as
     *            a delimiter, even if the prefix name is the empty string.
     *            Cannot be null.
     * @param prefixManager
     *            The prefix manager that is responsible for mapping prefix
     *            names to prefix IRIs. Cannot be null.
     * @return An OWLAnnotationProperty that has the IRI obtained by expanding
     *         the specified abbreviated IRI using the specified prefix manager.
     *         </p> For example, suppose "m:Cat" was specified as the
     *         abbreviated IRI, the prefix manager would be used to obtain the
     *         IRI prefix for the "m:" prefix name, this prefix would then be
     *         concatenated with "Cat" to obtain the full IRI which would be the
     *         IRI of the OWLAnnotationProperty obtained by this method.
     * @throws OWLRuntimeException
     *             if the prefix name in the specified abbreviated IRI does not
     *             have a mapping to a prefix in the specified prefix manager. */
    @Nonnull
    OWLAnnotationProperty getOWLAnnotationProperty(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager prefixManager);

    /** Gets an annotation property that has an IRI corresponding to
     * <code>rdfs:label</code>.
     * 
     * @return An annotation property with an IRI of <code>rdfs:label</code>. */
    @Nonnull
    OWLAnnotationProperty getRDFSLabel();

    /** Gets an annotation property that has an IRI corresponding to
     * <code>rdfs:comment</code>.
     * 
     * @return An annotation property with an IRI of <code>rdfs:comment</code>. */
    @Nonnull
    OWLAnnotationProperty getRDFSComment();

    /** Gets an annotation property that has an IRI corresponding to
     * <code>rdfs:seeAlso</code>.
     * 
     * @return An annotation property with an IRI of <code>rdfs:seeAlso</code>. */
    @Nonnull
    OWLAnnotationProperty getRDFSSeeAlso();

    /** Gets an annotation property that has an IRI corresponding to
     * <code>rdfs:isDefinedBy</code>.
     * 
     * @return An annotation property with an IRI of
     *         <code>rdfs:isDefinedBy</code>. */
    @Nonnull
    OWLAnnotationProperty getRDFSIsDefinedBy();

    /** Gets an annotation property that has an IRI corresponding to
     * <code>owl:versionInfo</code>.
     * 
     * @return An annotation property with an IRI of
     *         <code>owl:versionInfo</code>. */
    @Nonnull
    OWLAnnotationProperty getOWLVersionInfo();

    /** Gets an annotation property that has an IRI corresponding to
     * <code>owl:backwardCompatibleWith</code>.
     * 
     * @return An annotation property with an IRI of
     *         <code>owl:backwardCompatibleWith</code>. */
    @Nonnull
    OWLAnnotationProperty getOWLBackwardCompatibleWith();

    /** Gets an annotation property that has an IRI corresponding to
     * <code>owl:incompatibleWith</code>.
     * 
     * @return An annotation property with an IRI of
     *         <code>owl:incompatibleWith</code>. */
    @Nonnull
    OWLAnnotationProperty getOWLIncompatibleWith();

    /** Gets an annotation property that has an IRI corresponding to
     * <code>owl:backwardCompatibleWith</code>.
     * 
     * @return An annotation property with an IRI of
     *         <code>owl:backwardCompatibleWith</code>. */
    @Nonnull
    OWLAnnotationProperty getOWLDeprecated();

    /** Gets the rdf:PlainLiteral datatype.
     * 
     * @return The datatype with an IRI of <code>rdf:PlainLiteral</code> */
    @Nonnull
    OWLDatatype getRDFPlainLiteral();

    /** Gets an OWLDatatype that has the specified IRI
     * 
     * @param iri
     *            The IRI of the datatype to be obtained. Cannot be null.
     * @return The object representing the datatype that has the specified IRI */
    @Nonnull
    OWLDatatype getOWLDatatype(@Nonnull IRI iri);

    /** Gets an OWLDatatype that has an IRI that is obtained by expanding an
     * abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2
     * Structural Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *            The abbreviated IRI, which is of the form PREFIX_NAME:RC,
     *            where PREFIX_NAME may be the empty string (the default
     *            prefix). Note that abbreviated IRIs always contain a colon as
     *            a delimiter, even if the prefix name is the empty string.
     *            Cannot be null.
     * @param prefixManager
     *            The prefix manager that is responsible for mapping prefix
     *            names to prefix IRIs. Cannot be null.
     * @return An OWLDatatype that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager.
     *         </p> For example, suppose "m:Cat" was specified as the
     *         abbreviated IRI, the prefix manager would be used to obtain the
     *         IRI prefix for the "m:" prefix name, this prefix would then be
     *         concatenated with "Cat" to obtain the full IRI which would be the
     *         IRI of the OWLDatatype obtained by this method.
     * @throws OWLRuntimeException
     *             if the prefix name in the specified abbreviated IRI does not
     *             have a mapping to a prefix in the specified prefix manager. */
    @Nonnull
    OWLDatatype getOWLDatatype(@Nonnull String abbreviatedIRI,
            @Nonnull PrefixManager prefixManager);

    /** A convenience method that obtains the datatype that represents integers.
     * This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#integer&gt;
     * 
     * @return An object representing an integer datatype. */
    @Nonnull
    OWLDatatype getIntegerOWLDatatype();

    /** A convenience method that obtains the datatype that represents floats.
     * This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#float&gt;
     * 
     * @return An object representing the float datatype. */
    @Nonnull
    OWLDatatype getFloatOWLDatatype();

    /** A convenience method that obtains the datatype that represents doubles.
     * This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#double&gt;
     * 
     * @return An object representing a double datatype. */
    @Nonnull
    OWLDatatype getDoubleOWLDatatype();

    /** A convenience method that obtains the datatype that represents the
     * boolean datatype. This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#boolean&gt;
     * 
     * @return An object representing the boolean datatype. */
    @Nonnull
    OWLDatatype getBooleanOWLDatatype();

    // Literals
    /** Gets an <code>OWLLiteral</code>, which has the specified lexical value,
     * and is typed with the specified datatype.
     * 
     * @param lexicalValue
     *            The lexical value. Cannot be null.
     * @param datatype
     *            The datatype. Cannot be null.
     * @return An OWLLiteral with the specified lexical value and specified
     *         datatype. If the datatype is <code>rdf:PlainLiteral</code>, and
     *         the lexical value contains a language tag then the language tag
     *         will be parsed out of the lexical value. For example,
     *         "abc@en"^^rdf:PlainLiteral would be parsed into a lexical value
     *         of "abc" and a language tag of "en". */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String lexicalValue, @Nonnull OWLDatatype datatype);

    /** Gets an <code>OWLLiteral</code>, which has the specified lexical value,
     * and is typed with the specified datatype.
     * 
     * @param lexicalValue
     *            The lexical value. Cannot be null.
     * @param datatype
     *            The datatype. Cannot be null.
     * @return An OWLLiteral with the specified lexical value and specified
     *         datatype. If the datatype is <code>rdf:PlainLiteral</code>, and
     *         the lexical value contains a language tag then the language tag
     *         will be parsed out of the lexical value. For example,
     *         "abc@en"^^rdf:PlainLiteral would be parsed into a lexical value
     *         of "abc" and a language tag of "en". */
    @Nonnull
    OWLLiteral
            getOWLLiteral(@Nonnull String lexicalValue, @Nonnull OWL2Datatype datatype);

    /** Convenience method that obtains a literal typed as an integer.
     * 
     * @param value
     *            The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the integer, and whose data type is xsd:integer. */
    @Nonnull
    OWLLiteral getOWLLiteral(int value);

    /** Convenience method that obtains a literal typed as a double.
     * 
     * @param value
     *            The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the double, and whose data type is xsd:double. */
    @Nonnull
    OWLLiteral getOWLLiteral(double value);

    /** Convenience method that obtains a literal typed as a boolean.
     * 
     * @param value
     *            The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the boolean, and whose data type is xsd:boolean. */
    @Nonnull
    OWLLiteral getOWLLiteral(boolean value);

    /** Convenience method that obtains a literal typed as a float.
     * 
     * @param value
     *            The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the float, and whose data type is xsd:float. */
    @Nonnull
    OWLLiteral getOWLLiteral(float value);

    /** Gets a literal that has the specified lexical value, and has the datatype
     * xsd:string. The literal will not have a language tag.
     * 
     * @param value
     *            The lexical value of the literal. Cannot be null.
     * @return A literal (without a language tag) that has a datatype of
     *         xsd:string. */
    @Nonnull
    OWLLiteral getOWLLiteral(String value);

    /** Gets an OWLLiteral with a language tag. The datatype of this literal will
     * have an IRI of rdf:PlainLiteral (
     * {@link org.semanticweb.owlapi.vocab.OWLRDFVocabulary#RDF_PLAIN_LITERAL}).
     * 
     * @param literal
     *            The string literal. Cannot be null.
     * @param lang
     *            The language tag. The empty string may be specified to
     *            indicate an empty language tag. Leading and trailing white
     *            space will be removed from the tag and the tag will be
     *            normalised to LOWER CASE. If <code>lang</code> is
     *            <code>null</code> then <code>lang</code> will be converted to
     *            the empty string (for backwards compatibility). If not empty,
     *            the tag is formed according to <a
     *            href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">BCP47</a>
     *            but the OWL API will not check that the tag conforms to this
     *            specification - it is up to the caller to ensure this.
     * @return The OWLLiteral that represents the string literal with a
     *         (possibly empty) language tag. */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String literal, @Nullable String lang);

    // Data ranges
    /** Gets an OWLDataOneOf <a href=
     * "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals"
     * >(see spec)</a>
     * 
     * @param values
     *            The set of values that the data one of should contain. Cannot
     *            be null.
     * @return A data one of that enumerates the specified set of values */
    @Nonnull
    OWLDataOneOf getOWLDataOneOf(@Nonnull Set<? extends OWLLiteral> values);

    /** Gets an OWLDataOneOf <a href=
     * "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals"
     * >(see spec)</a>
     * 
     * @param values
     *            The set of values that the data one of should contain. Cannot
     *            be null or contain null values.
     * @return A data one of that enumerates the specified set of values */
    @Nonnull
    OWLDataOneOf getOWLDataOneOf(@Nonnull OWLLiteral... values);

    /** Gets an OWLDataComplementOf <a href=
     * "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Complement_of_Data_Range"
     * >(see spec)</a>
     * 
     * @param dataRange
     *            The datarange to be complemented. Cannot be null.
     * @return An OWLDataComplementOf of the specified data range */
    @Nonnull
    OWLDataComplementOf getOWLDataComplementOf(@Nonnull OWLDataRange dataRange);

    /** OWLDatatypeRestriction <a
     * href="http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions">see
     * spec</a>
     * 
     * @param dataRange
     *            Cannot be null.
     * @param facetRestrictions
     *            Cannot be null.
     * @return an OWLDatatypeRestriction for the specified data type and
     *         restrictions */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeRestriction(@Nonnull OWLDatatype dataRange,
            @Nonnull Set<OWLFacetRestriction> facetRestrictions);

    /** OWLDatatypeRestriction <a
     * href="http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions">see
     * spec</a>
     * 
     * @param dataRange
     *            Cannot be null.
     * @param facet
     *            Cannot be null.
     * @param typedLiteral
     *            Cannot be null.
     * @return an OWLDatatypeRestriction with given value for the specified
     *         facet */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeRestriction(@Nonnull OWLDatatype dataRange,
            @Nonnull OWLFacet facet, @Nonnull OWLLiteral typedLiteral);

    /** @param dataRange
     *            Cannot be null.
     * @param facetRestrictions
     *            Cannot be null or contain nulls.
     * @return an OWLDatatypeRestriction for the specified data type and
     *         restrictions */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeRestriction(@Nonnull OWLDatatype dataRange,
            @Nonnull OWLFacetRestriction... facetRestrictions);

    /** Creates a datatype restriction on xsd:integer with a minInclusive facet
     * restriction
     * 
     * @param minInclusive
     *            The value of the min inclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:integer</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the <code>minInclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(int minInclusive);

    /** Creates a datatype restriction on xsd:integer with a maxInclusive facet
     * restriction
     * 
     * @param maxInclusive
     *            The value of the max inclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:integer</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the <code>maxInclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(int maxInclusive);

    /** Creates a datatype restriction on xsd:integer with min and max inclusive
     * facet restrictions
     * 
     * @param minInclusive
     *            The value of the max inclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype.
     * @param maxInclusive
     *            The value of the max inclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:integer</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the <code>minInclusive</code> parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the <code>maxInclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(int minInclusive,
            int maxInclusive);

    /** Creates a datatype restriction on xsd:integer with a minExclusive facet
     * restriction
     * 
     * @param minExclusive
     *            The value of the min exclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:integer</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the <code>minExclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(int minExclusive);

    /** Creates a datatype restriction on xsd:integer with a maxExclusive facet
     * restriction
     * 
     * @param maxExclusive
     *            The value of the max exclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:integer</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the <code>maxExclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(int maxExclusive);

    /** Creates a datatype restriction on xsd:integer with min and max exclusive
     * facet restrictions
     * 
     * @param minExclusive
     *            The value of the max exclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype.
     * @param maxExclusive
     *            The value of the max exclusive facet restriction that will be
     *            applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:integer</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the <code>minExclusive</code> parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the <code>maxExclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(int minExclusive,
            int maxExclusive);

    /** Creates a datatype restriction on xsd:double with a minInclusive facet
     * restriction
     * 
     * @param minInclusive
     *            The value of the min inclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:double</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the <code>minInclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(double minInclusive);

    /** Creates a datatype restriction on xsd:double with a maxInclusive facet
     * restriction
     * 
     * @param maxInclusive
     *            The value of the max inclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:double</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the <code>maxInclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(double maxInclusive);

    /** Creates a datatype restriction on xsd:double with min and max inclusive
     * facet restrictions
     * 
     * @param minInclusive
     *            The value of the max inclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype.
     * @param maxInclusive
     *            The value of the max inclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:double</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the <code>minInclusive</code> parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the <code>maxInclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(double minInclusive,
            double maxInclusive);

    /** Creates a datatype restriction on xsd:double with a minExclusive facet
     * restriction
     * 
     * @param minExclusive
     *            The value of the min exclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:double</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the <code>minExclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(double minExclusive);

    /** Creates a datatype restriction on xsd:double with a maxExclusive facet
     * restriction
     * 
     * @param maxExclusive
     *            The value of the max exclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:double</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the <code>maxExclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(double maxExclusive);

    /** Creates a datatype restriction on xsd:double with min and max exclusive
     * facet restrictions
     * 
     * @param minExclusive
     *            The value of the max exclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype.
     * @param maxExclusive
     *            The value of the max exclusive facet restriction that will be
     *            applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the
     *         <code>xsd:double</code>
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the <code>minExclusive</code> parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the <code>maxExclusive</code> parameter. */
    @Nonnull
    OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(double minExclusive,
            double maxExclusive);

    /** @param facet
     *            Cannot be null.
     * @param facetValue
     *            Cannot be null.
     * @return an OWLFacetRestriction on specified facet and value */
    @Nonnull
    OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet,
            @Nonnull OWLLiteral facetValue);

    /** @param facet
     *            Cannot be null.
     * @param facetValue
     * @return an OWLFacetRestriction on specified facet and value */
    @Nonnull
    OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet, int facetValue);

    /** @param facet
     *            Cannot be null.
     * @param facetValue
     *            Cannot be null.
     * @return an OWLFacetRestriction on specified facet and value */
    @Nonnull
    OWLFacetRestriction
            getOWLFacetRestriction(@Nonnull OWLFacet facet, double facetValue);

    /** @param facet
     *            Cannot be null.
     * @param facetValue
     *            Cannot be null.
     * @return an OWLFacetRestriction on specified facet and value */
    @Nonnull
    OWLFacetRestriction getOWLFacetRestriction(@Nonnull OWLFacet facet, float facetValue);

    /** @param dataRanges
     *            Cannot be null or contain nulls.
     * @return an OWLDataUnionOf on the specified dataranges */
    @Nonnull
    OWLDataUnionOf getOWLDataUnionOf(@Nonnull Set<? extends OWLDataRange> dataRanges);

    /** @param dataRanges
     *            Cannot be null or contain nulls.
     * @return an OWLDataUnionOf on the specified dataranges */
    @Nonnull
    OWLDataUnionOf getOWLDataUnionOf(@Nonnull OWLDataRange... dataRanges);

    /** @param dataRanges
     *            Cannot be null or contain nulls.
     * @return an OWLDataIntersectionOf on the specified dataranges */
    @Nonnull
    OWLDataIntersectionOf getOWLDataIntersectionOf(
            @Nonnull Set<? extends OWLDataRange> dataRanges);

    /** @param dataRanges
     *            Cannot be null or contain nulls.
     * @return an OWLDataIntersectionOf on the specified dataranges */
    @Nonnull
    OWLDataIntersectionOf getOWLDataIntersectionOf(@Nonnull OWLDataRange... dataRanges);

    // Class Expressions
    /** @param operands
     *            Cannot be null or contain nulls.
     * @return an OWLObjectIntersectionOf on the specified operands */
    @Nonnull
    OWLObjectIntersectionOf getOWLObjectIntersectionOf(
            @Nonnull Set<? extends OWLClassExpression> operands);

    /** @param operands
     *            Cannot be null or contain nulls.
     * @return an OWLObjectIntersectionOf on the specified operands */
    @Nonnull
    OWLObjectIntersectionOf getOWLObjectIntersectionOf(
            @Nonnull OWLClassExpression... operands);

    // Data restrictions
    /** Gets an OWLDataSomeValuesFrom restriction
     * 
     * @param property
     *            The property that the restriction acts along. Cannot be null.
     * @param dataRange
     *            The data range that is the filler. Cannot be null.
     * @return An OWLDataSomeValuesFrom restriction that acts along the
     *         specified property and has the specified filler */
    @Nonnull
    OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange);

    /** @param property
     *            Cannot be null.
     * @param dataRange
     *            Cannot be null.
     * @return An OWLDataAllValuesFrom restriction that acts along the specified
     *         property and has the specified filler */
    @Nonnull
    OWLDataAllValuesFrom getOWLDataAllValuesFrom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange);

    /** @param cardinality
     *            Cannot be negative
     * @param property
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property);

    /** @param cardinality
     *            Cannot be negative
     * @param property
     *            Cannot be null.
     * @param dataRange
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange);

    /** @param cardinality
     *            Cannot be negative
     * @param property
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property);

    /** @param cardinality
     *            Cannot be negative
     * @param property
     *            Cannot be null.
     * @param dataRange
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange);

    /** @param cardinality
     *            Cannot be negative
     * @param property
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property);

    /** @param cardinality
     *            Cannot be negative.
     * @param property
     *            Cannot be null.
     * @param dataRange
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLDataRange dataRange);

    /** @param property
     *            Cannot be null
     * @param value
     *            Cannot be null.
     * @return a HasValue restriction with specified property and value */
    @Nonnull
    OWLDataHasValue getOWLDataHasValue(@Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLLiteral value);

    /** @param operand
     *            Cannot be null.
     * @return the complement of the specified argument */
    @Nonnull
    OWLObjectComplementOf getOWLObjectComplementOf(@Nonnull OWLClassExpression operand);

    /** @param values
     *            Cannot be null or contain nulls.
     * @return a OneOf expression on specified individuals */
    @Nonnull
    OWLObjectOneOf getOWLObjectOneOf(@Nonnull Set<? extends OWLIndividual> values);

    /** @param individuals
     *            Cannot be null or contain nulls.
     * @return a OneOf expression on specified individuals */
    @Nonnull
    OWLObjectOneOf getOWLObjectOneOf(@Nonnull OWLIndividual... individuals);

    // Object restrictions
    /** @param property
     *            Cannot be null.
     * @param classExpression
     *            Cannot be null.
     * @return an AllValuesFrom on specified property and class expression */
    @Nonnull
    OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /** Gets an OWLObjectSomeValuesFrom restriction
     * 
     * @param property
     *            The object property that the restriction acts along. Cannot be
     *            null.
     * @param classExpression
     *            The class expression that is the filler. Cannot be null.
     * @return An OWLObjectSomeValuesFrom restriction along the specified
     *         property with the specified filler */
    @Nonnull
    OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /** @param cardinality
     *            Cannot be negative.
     * @param property
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property);

    /** @param cardinality
     *            Cannot be negative.
     * @param property
     *            Cannot be null.
     * @param classExpression
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /** @param cardinality
     *            Cannot be negative.
     * @param property
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property);

    /** @param cardinality
     *            Cannot be negative.
     * @param property
     *            Cannot be null.
     * @param classExpression
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /** @param cardinality
     *            Cannot be negative.
     * @param property
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property);

    /** @param cardinality
     *            Cannot be negative.
     * @param property
     *            Cannot be null.
     * @param classExpression
     *            Cannot be null.
     * @return an ExactCardinality on the specified property */
    @Nonnull
    OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /** @param property
     *            Cannot be null.
     * @return a ObjectHasSelf class expression on the specified property */
    @Nonnull
    OWLObjectHasSelf getOWLObjectHasSelf(@Nonnull OWLObjectPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param individual
     *            Cannot be null.
     * @return a HasValue restriction with specified property and value */
    @Nonnull
    OWLObjectHasValue getOWLObjectHasValue(@Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual individual);

    /** @param operands
     *            cannot be null or contain nulls.
     * @return a class union over the specified arguments */
    @Nonnull
    OWLObjectUnionOf getOWLObjectUnionOf(
            @Nonnull Set<? extends OWLClassExpression> operands);

    /** @param operands
     *            cannot be null or contain nulls.
     * @return a class union over the specified arguments */
    @Nonnull
    OWLObjectUnionOf getOWLObjectUnionOf(@Nonnull OWLClassExpression... operands);

    // Axioms
    /** Gets a declaration for an entity
     * 
     * @param owlEntity
     *            The declared entity. Cannot be null.
     * @return The declaration axiom for the specified entity. */
    @Nonnull
    OWLDeclarationAxiom getOWLDeclarationAxiom(@Nonnull OWLEntity owlEntity);

    /** Gets a declaration with zero or more annotations for an entity
     * 
     * @param owlEntity
     *            The declared entity. Cannot be null.
     * @param annotations
     *            A possibly empty set of annotations. Cannot be null or contain
     *            nulls.
     * @return The declaration axiom for the specified entity which is annotated
     *         with the specified annotations */
    @Nonnull
    OWLDeclarationAxiom getOWLDeclarationAxiom(@Nonnull OWLEntity owlEntity,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    // Class Axioms
    /** @param subClass
     *            Cannot be null.
     * @param superClass
     *            Cannot be null.
     * @return a subclass axiom with no annotations */
    @Nonnull
    OWLSubClassOfAxiom getOWLSubClassOfAxiom(@Nonnull OWLClassExpression subClass,
            @Nonnull OWLClassExpression superClass);

    /** @param subClass
     *            Cannot be null.
     * @param superClass
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a subclass axiom with specified annotations */
    @Nonnull
    OWLSubClassOfAxiom getOWLSubClassOfAxiom(@Nonnull OWLClassExpression subClass,
            @Nonnull OWLClassExpression superClass,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param classExpressions
     *            Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations */
    @Nonnull
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions);

    /** @param classExpressions
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and
     *         annotations */
    @Nonnull
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param classExpressions
     *            Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations */
    @Nonnull
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression... classExpressions);

    /** @param clsA
     *            Cannot be null.
     * @param clsB
     *            Cannot be null.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations (special case with only two operands) */
    @Nonnull
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression clsA, @Nonnull OWLClassExpression clsB);

    /** @param clsA
     *            Cannot be null.
     * @param clsB
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and
     *         annotations (special case with only two operands) */
    @Nonnull
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            @Nonnull OWLClassExpression clsA, @Nonnull OWLClassExpression clsB,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param classExpressions
     *            Cannot be null or contain nulls.
     * @return a disjoint class axiom with no annotations */
    @Nonnull
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions);

    /** @param classExpressions
     *            Cannot be null or contain nulls.
     * @return a disjoint class axiom with no annotations */
    @Nonnull
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            @Nonnull OWLClassExpression... classExpressions);

    /** @param classExpressions
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a disjoint class axiom with annotations */
    @Nonnull
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param owlClass
     *            left hand side of the axiom. Cannot be null.
     * @param classExpressions
     *            right hand side of the axiom. Cannot be null or contain nulls.
     * @return a disjoint union axiom */
    @Nonnull
    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(@Nonnull OWLClass owlClass,
            @Nonnull Set<? extends OWLClassExpression> classExpressions);

    /** @param owlClass
     *            left hand side of the axiom. Cannot be null.
     * @param classExpressions
     *            right hand side of the axiom. Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a disjoint union axiom with annotations */
    @Nonnull
    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(@Nonnull OWLClass owlClass,
            @Nonnull Set<? extends OWLClassExpression> classExpressions,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    // Object property axioms
    /** @param subProperty
     *            Cannot be null.
     * @param superProperty
     *            Cannot be null.
     * @return a subproperty axiom */
    @Nonnull
    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
            @Nonnull OWLObjectPropertyExpression subProperty,
            @Nonnull OWLObjectPropertyExpression superProperty);

    /** @param subProperty
     *            Cannot be null.
     * @param superProperty
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a subproperty axiom with annotations */
    @Nonnull
    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
            @Nonnull OWLObjectPropertyExpression subProperty,
            @Nonnull OWLObjectPropertyExpression superProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param chain
     *            Cannot be null or contain nulls.
     * @param superProperty
     *            Cannot be null.
     * @return a subproperty chain axiom */
    @Nonnull
    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
            @Nonnull List<? extends OWLObjectPropertyExpression> chain,
            @Nonnull OWLObjectPropertyExpression superProperty);

    /** @param chain
     *            Cannot be null or contain nulls.
     * @param superProperty
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a subproperty chain axiom */
    @Nonnull
    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
            @Nonnull List<? extends OWLObjectPropertyExpression> chain,
            @Nonnull OWLObjectPropertyExpression superProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties */
    @Nonnull
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            @Nonnull Set<? extends OWLObjectPropertyExpression> properties);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties and
     *         annotations */
    @Nonnull
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties */
    @Nonnull
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression... properties);

    /** @param propertyA
     *            Cannot be null.
     * @param propertyB
     *            Cannot be null.
     * @return an equivalent properties axiom with specified properties */
    @Nonnull
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression propertyA,
            @Nonnull OWLObjectPropertyExpression propertyB);

    /** @param propertyA
     *            Cannot be null.
     * @param propertyB
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties and
     *         annotations */
    @Nonnull
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression propertyA,
            @Nonnull OWLObjectPropertyExpression propertyB,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties */
    @Nonnull
    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            @Nonnull Set<? extends OWLObjectPropertyExpression> properties);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties */
    @Nonnull
    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression... properties);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties and
     *         annotations */
    @Nonnull
    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            @Nonnull Set<? extends OWLObjectPropertyExpression> properties,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param forwardProperty
     *            Cannot be null.
     * @param inverseProperty
     *            Cannot be null.
     * @return an inverse object property axiom */
    @Nonnull
    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression forwardProperty,
            @Nonnull OWLObjectPropertyExpression inverseProperty);

    /** @param forwardProperty
     *            Cannot be null.
     * @param inverseProperty
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an inverse object property axiom with annotations */
    @Nonnull
    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
            @Nonnull OWLObjectPropertyExpression forwardProperty,
            @Nonnull OWLObjectPropertyExpression inverseProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param classExpression
     *            Cannot be null.
     * @return an object property domain axiom */
    @Nonnull
    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /** @param property
     *            Cannot be null.
     * @param classExpression
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an object property domain axiom with annotations */
    @Nonnull
    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param range
     *            Cannot be null.
     * @return an object property range axiom */
    @Nonnull
    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression range);

    /** @param property
     *            Cannot be null.
     * @param range
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an object property range axiom with annotations */
    @Nonnull
    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression range,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @return a functional object property axiom */
    @Nonnull
    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a functional object property axiom with annotations */
    @Nonnull
    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @return an inverse functional object property axiom */
    @Nonnull
    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an inverse functional object property axiom with annotations */
    @Nonnull
    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @return a reflexive object property axiom */
    @Nonnull
    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a reflexive object property axiom with annotations */
    @Nonnull
    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @return an irreflexive object property axiom */
    @Nonnull
    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an irreflexive object property axiom with annotations */
    @Nonnull
    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @return a symmetric property axiom */
    @Nonnull
    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a symmetric property axiom with annotations */
    @Nonnull
    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param propertyExpression
     *            Cannot be null.
     * @return an asymmetric object property axiom on the specified argument */
    @Nonnull
    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression propertyExpression);

    /** @param propertyExpression
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an asymmetric object property axiom on the specified argument
     *         with annotations */
    @Nonnull
    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression propertyExpression,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @return a transitive object property axiom on the specified argument */
    @Nonnull
    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a transitive object property axiom on the specified argument with
     *         annotations */
    @Nonnull
    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    // Data property axioms
    /** @param subProperty
     *            Cannot be null.
     * @param superProperty
     *            Cannot be null.
     * @return a subproperty axiom */
    @Nonnull
    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
            @Nonnull OWLDataPropertyExpression subProperty,
            @Nonnull OWLDataPropertyExpression superProperty);

    /** @param subProperty
     *            Cannot be null.
     * @param superProperty
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a subproperty axiom with annotations */
    @Nonnull
    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
            @Nonnull OWLDataPropertyExpression subProperty,
            @Nonnull OWLDataPropertyExpression superProperty,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @return an equivalent data properties axiom */
    @Nonnull
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            @Nonnull Set<? extends OWLDataPropertyExpression> properties);

    /** @param properties
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an equivalent data properties axiom with annotations */
    @Nonnull
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            @Nonnull Set<? extends OWLDataPropertyExpression> properties,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @return an equivalent data properties axiom */
    @Nonnull
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            @Nonnull OWLDataPropertyExpression... properties);

    /** @param propertyA
     *            Cannot be null.
     * @param propertyB
     *            Cannot be null.
     * @return an equivalent data properties axiom */
    @Nonnull
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            @Nonnull OWLDataPropertyExpression propertyA,
            @Nonnull OWLDataPropertyExpression propertyB);

    /** @param propertyA
     *            Cannot be null.
     * @param propertyB
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an equivalent data properties axiom with annotations */
    @Nonnull
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            @Nonnull OWLDataPropertyExpression propertyA,
            @Nonnull OWLDataPropertyExpression propertyB,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param dataProperties
     *            Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties */
    @Nonnull
    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            @Nonnull OWLDataPropertyExpression... dataProperties);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties */
    @Nonnull
    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            @Nonnull Set<? extends OWLDataPropertyExpression> properties);

    /** @param properties
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties and
     *         annotations */
    @Nonnull
    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            @Nonnull Set<? extends OWLDataPropertyExpression> properties,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param domain
     *            Cannot be null.
     * @return a data property domain axiom */
    @Nonnull
    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLClassExpression domain);

    /** @param property
     *            Cannot be null.
     * @param domain
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a data property domain axiom with annotations */
    @Nonnull
    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLClassExpression domain,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param owlDataRange
     *            Cannot be null.
     * @return a data property range axiom */
    @Nonnull
    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange owlDataRange);

    /** @param property
     *            Cannot be null.
     * @param owlDataRange
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a data property range axiom with annotations */
    @Nonnull
    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange owlDataRange,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @return a functional data property axiom */
    @Nonnull
    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            @Nonnull OWLDataPropertyExpression property);

    /** @param property
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a functional data property axiom with annotations */
    @Nonnull
    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    // Data axioms
    /** @param ce
     *            Cannot be null.
     * @param properties
     *            Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments */
    @Nonnull
    OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression ce,
            @Nonnull Set<? extends OWLPropertyExpression> properties);

    /** @param ce
     *            Cannot be null.
     * @param properties
     *            Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments */
    @Nonnull
    OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression ce,
            @Nonnull OWLPropertyExpression... properties);

    /** @param ce
     *            Cannot be null.
     * @param objectProperties
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments and annotations */
    @Nonnull
    OWLHasKeyAxiom getOWLHasKeyAxiom(@Nonnull OWLClassExpression ce,
            @Nonnull Set<? extends OWLPropertyExpression> objectProperties,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param datatype
     *            Cannot be null.
     * @param dataRange
     *            Cannot be null.
     * @return a datatype definition axiom */
    @Nonnull
    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(
            @Nonnull OWLDatatype datatype, @Nonnull OWLDataRange dataRange);

    /** @param datatype
     *            Cannot be null.
     * @param dataRange
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a datatype definition axiom with annotations */
    @Nonnull
    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(
            @Nonnull OWLDatatype datatype, @Nonnull OWLDataRange dataRange,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    // Assertion (Individual) axioms
    /** @param individuals
     *            Cannot be null or contain nulls.
     * @return a same individuals axiom with specified individuals */
    @Nonnull
    OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals);

    /** @param individual
     *            Cannot be null.
     * @return a same individuals axiom with specified individuals */
    @Nonnull
    OWLSameIndividualAxiom
            getOWLSameIndividualAxiom(@Nonnull OWLIndividual... individual);

    /** @param individuals
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a same individuals axiom with specified individuals and
     *         annotations */
    @Nonnull
    OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param individuals
     *            Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals */
    @Nonnull
    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals);

    /** @param individuals
     *            Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals */
    @Nonnull
    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull OWLIndividual... individuals);

    /** @param individuals
     *            Cannot be null or contain nulls.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals and
     *         annotations */
    @Nonnull
    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            @Nonnull Set<? extends OWLIndividual> individuals,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param classExpression
     *            Cannot be null.
     * @param individual
     *            Cannot be null.
     * @return a class assertion axiom */
    @Nonnull
    OWLClassAssertionAxiom
            getOWLClassAssertionAxiom(@Nonnull OWLClassExpression classExpression,
                    @Nonnull OWLIndividual individual);

    /** @param classExpression
     *            Cannot be null.
     * @param individual
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a class assertion axiom with annotations */
    @Nonnull
    OWLClassAssertionAxiom getOWLClassAssertionAxiom(
            @Nonnull OWLClassExpression classExpression,
            @Nonnull OWLIndividual individual,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param individual
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @return an object property assertion */
    @Nonnull
    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual individual, @Nonnull OWLIndividual object);

    /** @param property
     *            Cannot be null.
     * @param individual
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an object property assertion with annotations */
    @Nonnull
    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual individual, @Nonnull OWLIndividual object,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @return a negative property assertion axiom on given arguments */
    @Nonnull
    OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual subject, @Nonnull OWLIndividual object);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a negative property assertion axiom on given arguments with
     *         annotations */
    @Nonnull
    OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLIndividual subject, @Nonnull OWLIndividual object,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @return a data property assertion */
    @Nonnull
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            @Nonnull OWLLiteral object);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a data property assertion with annotations */
    @Nonnull
    OWLDataPropertyAssertionAxiom
            getOWLDataPropertyAssertionAxiom(@Nonnull OWLDataPropertyExpression property,
                    @Nonnull OWLIndividual subject, @Nonnull OWLLiteral object,
                    @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param value
     *            Cannot be null.
     * @return a data property assertion */
    @Nonnull
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            int value);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param value
     *            Cannot be null.
     * @return a data property assertion */
    @Nonnull
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            double value);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param value
     *            Cannot be null.
     * @return a data property assertion */
    @Nonnull
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            float value);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param value
     *            Cannot be null.
     * @return a data property assertion */
    @Nonnull
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            boolean value);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param value
     *            Cannot be null.
     * @return a data property assertion */
    @Nonnull
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            @Nonnull String value);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @return a negative property assertion axiom on given arguments */
    @Nonnull
    OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(
            @Nonnull OWLDataPropertyExpression property, @Nonnull OWLIndividual subject,
            @Nonnull OWLLiteral object);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param object
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a negative property assertion axiom on given arguments with
     *         annotations */
    @Nonnull
    OWLNegativeDataPropertyAssertionAxiom
            getOWLNegativeDataPropertyAssertionAxiom(
                    @Nonnull OWLDataPropertyExpression property,
                    @Nonnull OWLIndividual subject, @Nonnull OWLLiteral object,
                    @Nonnull Set<? extends OWLAnnotation> annotations);

    // Annotations
    /** Gets an annotation
     * 
     * @param property
     *            the annotation property. Cannot be null.
     * @param value
     *            The annotation value. Cannot be null.
     * @return The annotation on the specified property with the specified value */
    @Nonnull
    OWLAnnotation getOWLAnnotation(@Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationValue value);

    /** Gets an annotation
     * 
     * @param property
     *            the annotation property. Cannot be null.
     * @param value
     *            The annotation value. Cannot be null.
     * @param annotations
     *            Annotations on the annotation. Cannot be null or contain
     *            nulls.
     * @return The annotation on the specified property with the specified value */
    @Nonnull
    OWLAnnotation getOWLAnnotation(@Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationValue value,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    // Annotation axioms
    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param value
     *            Cannot be null.
     * @return an annotation assertion axiom */
    @Nonnull
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationSubject subject, @Nonnull OWLAnnotationValue value);

    /** @param subject
     *            Cannot be null.
     * @param annotation
     *            Cannot be null.
     * @return an annotation assertion axiom */
    @Nonnull
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationSubject subject, @Nonnull OWLAnnotation annotation);

    /** @param property
     *            Cannot be null.
     * @param subject
     *            Cannot be null.
     * @param value
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations */
    @Nonnull
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationSubject subject, @Nonnull OWLAnnotationValue value,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param subject
     *            Cannot be null.
     * @param annotation
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations */
    @Nonnull
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            @Nonnull OWLAnnotationSubject subject, @Nonnull OWLAnnotation annotation,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** Gets an annotation assertion that specifies that an IRI is deprecated.
     * The annotation property is owl:deprecated and the value of the annotation
     * is <code>"true"^^xsd:boolean</code>. (See <a href=
     * "http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Properties"
     * >Annotation Properties</a> in the OWL 2 Specification
     * 
     * @param subject
     *            The IRI to be deprecated. Cannot be null.
     * @return The annotation assertion that deprecates the specified IRI. */
    @Nonnull
    OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(
            @Nonnull IRI subject);

    /** @param importedOntologyIRI
     *            Cannot be null.
     * @return an imports declaration */
    @Nonnull
    OWLImportsDeclaration getOWLImportsDeclaration(@Nonnull IRI importedOntologyIRI);

    /** @param prop
     *            Cannot be null.
     * @param domain
     *            Cannot be null.
     * @return an annotation property domain assertion */
    @Nonnull
    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
            @Nonnull OWLAnnotationProperty prop, @Nonnull IRI domain);

    /** @param prop
     *            Cannot be null.
     * @param domain
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an annotation property domain assertion with annotations */
    @Nonnull
    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
            @Nonnull OWLAnnotationProperty prop, @Nonnull IRI domain,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param prop
     *            Cannot be null.
     * @param range
     *            Cannot be null.
     * @return an annotation property range assertion */
    @Nonnull
    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            @Nonnull OWLAnnotationProperty prop, @Nonnull IRI range);

    /** @param prop
     *            Cannot be null.
     * @param range
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return an annotation property range assertion with annotations */
    @Nonnull
    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            @Nonnull OWLAnnotationProperty prop, @Nonnull IRI range,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** @param sub
     *            Cannot be null.
     * @param sup
     *            Cannot be null.
     * @return a sub annotation property axiom with specified properties */
    @Nonnull
    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
            @Nonnull OWLAnnotationProperty sub, @Nonnull OWLAnnotationProperty sup);

    /** @param sub
     *            Cannot be null.
     * @param sup
     *            Cannot be null.
     * @param annotations
     *            Cannot be null or contain nulls.
     * @return a sub annotation property axiom with specified properties and
     *         annotations */
    @Nonnull
    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
            @Nonnull OWLAnnotationProperty sub, @Nonnull OWLAnnotationProperty sup,
            @Nonnull Set<? extends OWLAnnotation> annotations);

    /** Empty all caches */
    void purge();
}
