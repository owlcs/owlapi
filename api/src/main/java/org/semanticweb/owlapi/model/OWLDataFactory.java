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

import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * An interface for creating entities, class expressions and axioms. All methods
 * throw NullPointerException if null values are passed where they are not
 * allowed in the documentation.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group Date: 24-Oct-2006
 */
public interface OWLDataFactory extends SWRLDataFactory, OWLEntityProvider,
        OWLEntityByTypeProvider, OWLAnonymousIndividualProvider,
        OWLAnonymousIndividualByIdProvider {

    // //////////////////////////////////////////////////////////////////////////////////
    //
    // Entities and data stuff
    //
    // //////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the built in owl:Thing class, which has a URI of
     * &lt;http://www.w3.org/2002/07/owl#Thing&gt;
     * 
     * @return The OWL Class corresponding to owl:Thing
     */
    OWLClass getOWLThing();

    /**
     * Gets the built in owl:Nothing class, which has a URI of
     * &lt;http://www.w3.org/2002/07/owl#Nothing&gt;
     * 
     * @return The OWL Class corresponding to owl:Nothing
     */
    OWLClass getOWLNothing();

    /** @return the built in top object property */
    OWLObjectProperty getOWLTopObjectProperty();

    /** @return the built in top data property */
    OWLDataProperty getOWLTopDataProperty();

    /** @return the built in bottom object property */
    OWLObjectProperty getOWLBottomObjectProperty();

    /** @return the built in bottom data property */
    OWLDataProperty getOWLBottomDataProperty();

    /**
     * Gets the built in data range corresponding to the top data type (like
     * owl:Thing but for data ranges), this datatype is rdfs:Literal, and has a
     * URI of $lt;http://www.w3.org/2000/01/rdf-schema#&gt;
     * 
     * @return The OWL Datatype corresponding to the top data type.
     */
    OWLDatatype getTopDatatype();

    /**
     * Gets an entity that has the specified IRI and is of the specified type.
     * 
     * @param entityType
     *        The type of the entity that will be returned. Cannot be null.
     * @param iri
     *        The IRI of the entity that will be returned. Cannot be null.
     * @return An entity that has the same IRI as this entity and is of the
     *         specified type
     * @param <E>
     *        entity type
     */
    @Override
    <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType, IRI iri);

    /**
     * Gets an OWL class that has the specified IRI
     * 
     * @param iri
     *        The IRI of the class. Cannot be null.
     * @return The object representing the class that has the specified IRI
     */
    @Override
    OWLClass getOWLClass(IRI iri);

    /**
     * Gets an OWLClass that has an IRI that is obtained by expanding an
     * abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/owl2-syntax/#IRIs">The OWL 2 Structural
     * Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *        The abbreviated IRI, which is of the form PREFIX_NAME:RC, where
     *        PREFIX_NAME may be the empty string (the default prefix). Cannot
     *        be null. Note that abbreviated IRIs always contain a colon as a
     *        delimiter, even if the prefix name is the empty string.
     * @param prefixManager
     *        The prefix manager that is responsible for mapping prefix names to
     *        prefix IRIs. Cannot be null.
     * @return An OWLClass that has the IRI obtained by expanding the specified
     *         abbreviated IRI using the specified prefix manager. <br>
     *         For example, suppose "m:Cat" was specified as the abbreviated
     *         IRI, the prefix manager would be used to obtain the IRI prefix
     *         for the "m:" prefix name, this prefix would then be concatenated
     *         with "Cat" to obtain the full IRI which would be the IRI of the
     *         OWLClass obtained by this method.
     * @throws OWLRuntimeException
     *         if the prefix name in the specified abbreviated IRI does not have
     *         a mapping to a prefix in the specified prefix manager.
     */
    OWLClass getOWLClass(String abbreviatedIRI, PrefixManager prefixManager);

    /**
     * Gets an OWL object property that has the specified IRI
     * 
     * @param iri
     *        The IRI of the object property to be obtained. Cannot be null.
     * @return The object representing the object property that has the
     *         specified IRI
     */
    @Override
    OWLObjectProperty getOWLObjectProperty(IRI iri);

    /**
     * Gets an OWLObjectProperty that has an IRI that is obtained by expanding
     * an abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/owl2-syntax/#IRIs">The OWL 2 Structural
     * Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *        The abbreviated IRI, which is of the form PREFIX_NAME:RC, where
     *        PREFIX_NAME may be the empty string (the default prefix). Cannot
     *        be null. Note that abbreviated IRIs always contain a colon as a
     *        delimiter, even if the prefix name is the empty string.
     * @param prefixManager
     *        The prefix manager that is responsible for mapping prefix names to
     *        prefix IRIs. Cannot be null.
     * @return An OWLObjectProperty that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager. <br>
     *         For example, suppose "m:Cat" was specified as the abbreviated
     *         IRI, the prefix manager would be used to obtain the IRI prefix
     *         for the "m:" prefix name, this prefix would then be concatenated
     *         with "Cat" to obtain the full IRI which would be the IRI of the
     *         OWLObjectProperty obtained by this method.
     * @throws OWLRuntimeException
     *         if the prefix name in the specified abbreviated IRI does not have
     *         a mapping to a prefix in the specified prefix manager.
     */
    OWLObjectProperty getOWLObjectProperty(String abbreviatedIRI,
            PrefixManager prefixManager);

    /**
     * Gets the inverse of an object property.
     * 
     * @param property
     *        The property of which the inverse will be returned. Cannot be
     *        null.
     * @return The inverse of the specified object property
     */
    OWLObjectInverseOf getOWLObjectInverseOf(
            OWLObjectPropertyExpression property);

    /**
     * Gets an OWL data property that has the specified IRI
     * 
     * @param iri
     *        The IRI of the data property to be obtained. Cannot be null.
     * @return The object representing the data property that has the specified
     *         IRI
     */
    @Override
    OWLDataProperty getOWLDataProperty(IRI iri);

    /**
     * Gets an OWLDataProperty that has an IRI that is obtained by expanding an
     * abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/owl2-syntax/#IRIs">The OWL 2 Structural
     * Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *        The abbreviated IRI, which is of the form PREFIX_NAME:RC, where
     *        PREFIX_NAME may be the empty string (the default prefix). Note
     *        that abbreviated IRIs always contain a colon as a delimiter, even
     *        if the prefix name is the empty string. Cannot be null.
     * @param prefixManager
     *        The prefix manager that is responsible for mapping prefix names to
     *        prefix IRIs. Cannot be null.
     * @return An OWLDataProperty that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager. <br>
     *         For example, suppose "m:Cat" was specified as the abbreviated
     *         IRI, the prefix manager would be used to obtain the IRI prefix
     *         for the "m:" prefix name, this prefix would then be concatenated
     *         with "Cat" to obtain the full IRI which would be the IRI of the
     *         OWLDataProperty obtained by this method.
     * @throws OWLRuntimeException
     *         if the prefix name in the specified abbreviated IRI does not have
     *         a mapping to a prefix in the specified prefix manager.
     */
    OWLDataProperty getOWLDataProperty(String abbreviatedIRI,
            PrefixManager prefixManager);

    /**
     * Gets an OWL individual that has the specified IRI
     * 
     * @param iri
     *        The IRI of the individual to be obtained. Cannot be null.
     * @return The object representing the individual that has the specified IRI
     */
    @Override
    OWLNamedIndividual getOWLNamedIndividual(IRI iri);

    /**
     * Gets an OWLNamedIndividual that has an IRI that is obtained by expanding
     * an abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/owl2-syntax/#IRIs">The OWL 2 Structural
     * Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *        The abbreviated IRI, which is of the form PREFIX_NAME:RC, where
     *        PREFIX_NAME may be the empty string (the default prefix). Note
     *        that abbreviated IRIs always contain a colon as a delimiter, even
     *        if the prefix name is the empty string. Cannot be null.
     * @param prefixManager
     *        The prefix manager that is responsible for mapping prefix names to
     *        prefix IRIs. Cannot be null.
     * @return An OWLNamedIndividual that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager. <br>
     *         For example, suppose "m:Cat" was specified as the abbreviated
     *         IRI, the prefix manager would be used to obtain the IRI prefix
     *         for the "m:" prefix name, this prefix would then be concatenated
     *         with "Cat" to obtain the full IRI which would be the IRI of the
     *         OWLNamedIndividual obtained by this method.
     * @throws OWLRuntimeException
     *         if the prefix name in the specified abbreviated IRI does not have
     *         a mapping to a prefix in the specified prefix manager.
     */
    OWLNamedIndividual getOWLNamedIndividual(String abbreviatedIRI,
            PrefixManager prefixManager);

    /**
     * Gets an anonymous individual that has a specific ID.
     * 
     * @param id
     *        The node ID. Note that the ID will be prefixed with _: if it is
     *        not specified with _: as a prefix. Cannot be null.
     * @return An anonymous individual.
     */
    @Override
    OWLAnonymousIndividual getOWLAnonymousIndividual(String id);

    /**
     * Gets an anonymous individual. The node ID for the individual will be
     * generated automatically
     * 
     * @return The anonymous individual
     */
    @Override
    OWLAnonymousIndividual getOWLAnonymousIndividual();

    /**
     * Gets an OWLAnnotationProperty that has the specified IRI
     * 
     * @param iri
     *        The IRI of the annotation property to be obtained. Cannot be null.
     * @return An OWLAnnotationProperty with the specified IRI
     */
    @Override
    OWLAnnotationProperty getOWLAnnotationProperty(IRI iri);

    /**
     * Gets an OWLAnnotationProperty that has an IRI that is obtained by
     * expanding an abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/owl2-syntax/#IRIs">The OWL 2 Structural
     * Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *        The abbreviated IRI, which is of the form PREFIX_NAME:RC, where
     *        PREFIX_NAME may be the empty string (the default prefix). Note
     *        that abbreviated IRIs always contain a colon as a delimiter, even
     *        if the prefix name is the empty string. Cannot be null.
     * @param prefixManager
     *        The prefix manager that is responsible for mapping prefix names to
     *        prefix IRIs. Cannot be null.
     * @return An OWLAnnotationProperty that has the IRI obtained by expanding
     *         the specified abbreviated IRI using the specified prefix manager. <br>
     *         For example, suppose "m:Cat" was specified as the abbreviated
     *         IRI, the prefix manager would be used to obtain the IRI prefix
     *         for the "m:" prefix name, this prefix would then be concatenated
     *         with "Cat" to obtain the full IRI which would be the IRI of the
     *         OWLAnnotationProperty obtained by this method.
     * @throws OWLRuntimeException
     *         if the prefix name in the specified abbreviated IRI does not have
     *         a mapping to a prefix in the specified prefix manager.
     */
    OWLAnnotationProperty getOWLAnnotationProperty(String abbreviatedIRI,
            PrefixManager prefixManager);

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code rdfs:label}.
     * 
     * @return An annotation property with an IRI of {@code rdfs:label}.
     */
    OWLAnnotationProperty getRDFSLabel();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code rdfs:comment}.
     * 
     * @return An annotation property with an IRI of {@code rdfs:comment}.
     */
    OWLAnnotationProperty getRDFSComment();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code rdfs:seeAlso}.
     * 
     * @return An annotation property with an IRI of {@code rdfs:seeAlso}.
     */
    OWLAnnotationProperty getRDFSSeeAlso();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code rdfs:isDefinedBy}.
     * 
     * @return An annotation property with an IRI of {@code rdfs:isDefinedBy}.
     */
    OWLAnnotationProperty getRDFSIsDefinedBy();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code owl:versionInfo}.
     * 
     * @return An annotation property with an IRI of {@code owl:versionInfo}.
     */
    OWLAnnotationProperty getOWLVersionInfo();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code owl:backwardCompatibleWith}.
     * 
     * @return An annotation property with an IRI of
     *         {@code owl:backwardCompatibleWith}.
     */
    OWLAnnotationProperty getOWLBackwardCompatibleWith();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code owl:incompatibleWith}.
     * 
     * @return An annotation property with an IRI of
     *         {@code owl:incompatibleWith}.
     */
    OWLAnnotationProperty getOWLIncompatibleWith();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code owl:backwardCompatibleWith}.
     * 
     * @return An annotation property with an IRI of
     *         {@code owl:backwardCompatibleWith}.
     */
    OWLAnnotationProperty getOWLDeprecated();

    /**
     * Gets the rdf:PlainLiteral datatype.
     * 
     * @return The datatype with an IRI of {@code rdf:PlainLiteral}
     */
    OWLDatatype getRDFPlainLiteral();

    /**
     * Gets an OWLDatatype that has the specified IRI
     * 
     * @param iri
     *        The IRI of the datatype to be obtained. Cannot be null.
     * @return The object representing the datatype that has the specified IRI
     */
    @Override
    OWLDatatype getOWLDatatype(IRI iri);

    /**
     * Gets an OWLDatatype that has an IRI that is obtained by expanding an
     * abbreviated name using an appropriate prefix mapping. See <a
     * href="http://www.w3.org/TR/owl2-syntax/#IRIs">The OWL 2 Structural
     * Specification</a> for more details.
     * 
     * @param abbreviatedIRI
     *        The abbreviated IRI, which is of the form PREFIX_NAME:RC, where
     *        PREFIX_NAME may be the empty string (the default prefix). Note
     *        that abbreviated IRIs always contain a colon as a delimiter, even
     *        if the prefix name is the empty string. Cannot be null.
     * @param prefixManager
     *        The prefix manager that is responsible for mapping prefix names to
     *        prefix IRIs. Cannot be null.
     * @return An OWLDatatype that has the IRI obtained by expanding the
     *         specified abbreviated IRI using the specified prefix manager. <br>
     *         For example, suppose "m:Cat" was specified as the abbreviated
     *         IRI, the prefix manager would be used to obtain the IRI prefix
     *         for the "m:" prefix name, this prefix would then be concatenated
     *         with "Cat" to obtain the full IRI which would be the IRI of the
     *         OWLDatatype obtained by this method.
     * @throws OWLRuntimeException
     *         if the prefix name in the specified abbreviated IRI does not have
     *         a mapping to a prefix in the specified prefix manager.
     */
    OWLDatatype getOWLDatatype(String abbreviatedIRI,
            PrefixManager prefixManager);

    /**
     * A convenience method that obtains the datatype that represents integers.
     * This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#integer&gt;
     * 
     * @return An object representing an integer datatype.
     */
    OWLDatatype getIntegerOWLDatatype();

    /**
     * A convenience method that obtains the datatype that represents floats.
     * This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#float&gt;
     * 
     * @return An object representing the float datatype.
     */
    OWLDatatype getFloatOWLDatatype();

    /**
     * A convenience method that obtains the datatype that represents doubles.
     * This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#double&gt;
     * 
     * @return An object representing a double datatype.
     */
    OWLDatatype getDoubleOWLDatatype();

    /**
     * A convenience method that obtains the datatype that represents the
     * boolean datatype. This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#boolean&gt;
     * 
     * @return An object representing the boolean datatype.
     */
    OWLDatatype getBooleanOWLDatatype();

    // ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Literals
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets an {@code OWLLiteral}, which has the specified lexical value, and is
     * typed with the specified datatype.
     * 
     * @param lexicalValue
     *        The lexical value. Cannot be null.
     * @param datatype
     *        The datatype. Cannot be null.
     * @return An OWLLiteral with the specified lexical value and specified
     *         datatype. If the datatype is {@code rdf:PlainLiteral}, and the
     *         lexical value contains a language tag then the language tag will
     *         be parsed out of the lexical value. For example,
     *         "abc@en"^^rdf:PlainLiteral would be parsed into a lexical value
     *         of "abc" and a language tag of "en".
     */
    OWLLiteral getOWLLiteral(String lexicalValue, OWLDatatype datatype);

    /**
     * Gets an {@code OWLLiteral}, which has the specified lexical value, and is
     * typed with the specified datatype.
     * 
     * @param lexicalValue
     *        The lexical value. Cannot be null.
     * @param datatype
     *        The datatype. Cannot be null.
     * @return An OWLLiteral with the specified lexical value and specified
     *         datatype. If the datatype is {@code rdf:PlainLiteral}, and the
     *         lexical value contains a language tag then the language tag will
     *         be parsed out of the lexical value. For example,
     *         "abc@en"^^rdf:PlainLiteral would be parsed into a lexical value
     *         of "abc" and a language tag of "en".
     */
    OWLLiteral getOWLLiteral(String lexicalValue, OWL2Datatype datatype);

    /**
     * Convenience method that obtains a literal typed as an integer.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the integer, and whose data type is xsd:integer.
     */
    OWLLiteral getOWLLiteral(int value);

    /**
     * Convenience method that obtains a literal typed as a double.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the double, and whose data type is xsd:double.
     */
    OWLLiteral getOWLLiteral(double value);

    /**
     * Convenience method that obtains a literal typed as a boolean.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the boolean, and whose data type is xsd:boolean.
     */
    OWLLiteral getOWLLiteral(boolean value);

    /**
     * Convenience method that obtains a literal typed as a float.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the float, and whose data type is xsd:float.
     */
    OWLLiteral getOWLLiteral(float value);

    /**
     * Gets a literal that has the specified lexical value, and has the datatype
     * xsd:string. The literal will not have a language tag.
     * 
     * @param value
     *        The lexical value of the literal. Cannot be null.
     * @return A literal (without a language tag) that has a datatype of
     *         xsd:string.
     */
    OWLLiteral getOWLLiteral(String value);

    /**
     * Gets an OWLLiteral with a language tag. The datatype of this literal will
     * have an IRI of rdf:PlainLiteral (
     * {@link org.semanticweb.owlapi.vocab.OWLRDFVocabulary#RDF_PLAIN_LITERAL}).
     * 
     * @param literal
     *        The string literal. Cannot be null.
     * @param lang
     *        The language tag. The empty string may be specified to indicate an
     *        empty language tag. Leading and trailing white space will be
     *        removed from the tag and the tag will be normalised to LOWER CASE.
     *        If {@code lang} is {@code null} then {@code lang} will be
     *        converted to the empty string (for backwards compatibility). If
     *        not empty, the tag is formed according to <a
     *        href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">BCP47</a> but
     *        the OWL API will not check that the tag conforms to this
     *        specification - it is up to the caller to ensure this.
     * @return The OWLLiteral that represents the string literal with a
     *         (possibly empty) language tag.
     */
    OWLLiteral getOWLLiteral(String literal, String lang);

    // /////////////////////////////////////////////////////////////
    /**
     * @param literal
     *        The literal. Cannot be null.
     * @param datatype
     *        The datatype. Cannot be null.
     * @return The typed literal
     * @deprecated Use {@link #getOWLLiteral(String, OWLDatatype)}
     */
    @Deprecated
    OWLLiteral getOWLTypedLiteral(String literal, OWLDatatype datatype);

    /**
     * @param literal
     *        The literal. Cannot be null.
     * @param datatype
     *        The OWL 2 Datatype that will type the literal. Cannot be null.
     * @return The typed literal
     * @deprecated Use
     *             {@link #getOWLLiteral(String, org.semanticweb.owlapi.vocab.OWL2Datatype)}
     *             Creates a typed literal that has the specified OWL 2 Datatype
     *             as its datatype
     */
    @Deprecated
    OWLLiteral getOWLTypedLiteral(String literal, OWL2Datatype datatype);

    /**
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the integer, and whose data type is xsd:integer.
     * @deprecated Use {@link #getOWLLiteral(int)} Convenience method that
     *             obtains a literal typed as an integer.
     */
    @Deprecated
    OWLLiteral getOWLTypedLiteral(int value);

    /**
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the double, and whose data type is xsd:double.
     * @deprecated Use {@link #getOWLLiteral(double)} Convenience method that
     *             obtains a literal typed as a double.
     */
    @Deprecated
    OWLLiteral getOWLTypedLiteral(double value);

    /**
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the boolean, and whose data type is xsd:boolean.
     * @deprecated Use {@link #getOWLLiteral(boolean)} Convenience method that
     *             obtains a literal typed as a boolean.
     */
    @Deprecated
    OWLLiteral getOWLTypedLiteral(boolean value);

    /**
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the float, and whose data type is xsd:float.
     * @deprecated Use {@link #getOWLLiteral(float)} Convenience method that
     *             obtains a literal typed as a float.
     */
    @Deprecated
    OWLLiteral getOWLTypedLiteral(float value);

    /**
     * @param value
     *        The value of the literal. Cannot be null.
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the string, and whose data type is xsd:string.
     * @deprecated Use {@link #getOWLLiteral(String)} Convenience method that
     *             obtains a literal typed as a string.
     */
    @Deprecated
    OWLLiteral getOWLTypedLiteral(String value);

    /**
     * @param literal
     *        The string literal. Cannot be null.
     * @param lang
     *        The language tag. The tag is formed according to <a
     *        href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">BCP47</a> but
     *        the OWL API will not check that the tag conforms to this
     *        specification - it is up to the caller to ensure this. For
     *        backwards compatibility, if the value of lang is {@code null} then
     *        this is equivalent to calling the getOWLStringLiteral(String
     *        literal) method.
     * @return The OWLStringLiteral that represents the string literal with a
     *         language tag.
     * @deprecated Use {@link #getOWLLiteral(String, String)} Gets an
     *             OWLStringLiteral with a language tag.
     */
    @Deprecated
    OWLLiteral getOWLStringLiteral(String literal, String lang);

    /**
     * @param literal
     *        The string literal. Cannot be null.
     * @return The string literal for the specified string
     * @deprecated Use {@link #getOWLLiteral(String, String)} with the second
     *             parameter as the empty string (""). Gets a string literal
     *             without a language tag.
     */
    @Deprecated
    OWLLiteral getOWLStringLiteral(String literal);

    // ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data ranges
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets an OWLDataOneOf <a href=
     * "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals"
     * >(see spec)</a>
     * 
     * @param values
     *        The set of values that the data one of should contain. Cannot be
     *        null.
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values);

    /**
     * Gets an OWLDataOneOf <a href=
     * "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals"
     * >(see spec)</a>
     * 
     * @param values
     *        The set of values that the data one of should contain. Cannot be
     *        null or contain null values.
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getOWLDataOneOf(OWLLiteral... values);

    /**
     * Gets an OWLDataComplementOf <a href=
     * "http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Complement_of_Data_Range"
     * >(see spec)</a>
     * 
     * @param dataRange
     *        The datarange to be complemented. Cannot be null.
     * @return An OWLDataComplementOf of the specified data range
     */
    OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange);

    /**
     * OWLDatatypeRestriction <a
     * href="http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions">see
     * spec</a>
     * 
     * @param dataRange
     *        Cannot be null.
     * @param facetRestrictions
     *        Cannot be null.
     * @return an OWLDatatypeRestriction for the specified data type and
     *         restrictions
     */
    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange,
            Set<OWLFacetRestriction> facetRestrictions);

    /**
     * OWLDatatypeRestriction <a
     * href="http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions">see
     * spec</a>
     * 
     * @param dataRange
     *        Cannot be null.
     * @param facet
     *        Cannot be null.
     * @param typedLiteral
     *        Cannot be null.
     * @return an OWLDatatypeRestriction with given value for the specified
     *         facet
     */
    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange,
            OWLFacet facet, OWLLiteral typedLiteral);

    /**
     * @param dataRange
     *        Cannot be null.
     * @param facetRestrictions
     *        Cannot be null or contain nulls.
     * @return an OWLDatatypeRestriction for the specified data type and
     *         restrictions
     */
    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange,
            OWLFacetRestriction... facetRestrictions);

    /**
     * Creates a datatype restriction on xsd:integer with a minInclusive facet
     * restriction
     * 
     * @param minInclusive
     *        The value of the min inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(
            int minInclusive);

    /**
     * Creates a datatype restriction on xsd:integer with a maxInclusive facet
     * restriction
     * 
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(
            int maxInclusive);

    /**
     * Creates a datatype restriction on xsd:integer with min and max inclusive
     * facet restrictions
     * 
     * @param minInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype.
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(
            int minInclusive, int maxInclusive);

    /**
     * Creates a datatype restriction on xsd:integer with a minExclusive facet
     * restriction
     * 
     * @param minExclusive
     *        The value of the min exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(
            int minExclusive);

    /**
     * Creates a datatype restriction on xsd:integer with a maxExclusive facet
     * restriction
     * 
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(
            int maxExclusive);

    /**
     * Creates a datatype restriction on xsd:integer with min and max exclusive
     * facet restrictions
     * 
     * @param minExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype.
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:integer} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:integer}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(
            int minExclusive, int maxExclusive);

    /**
     * Creates a datatype restriction on xsd:double with a minInclusive facet
     * restriction
     * 
     * @param minInclusive
     *        The value of the min inclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(
            double minInclusive);

    /**
     * Creates a datatype restriction on xsd:double with a maxInclusive facet
     * restriction
     * 
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(
            double maxInclusive);

    /**
     * Creates a datatype restriction on xsd:double with min and max inclusive
     * facet restrictions
     * 
     * @param minInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype.
     * @param maxInclusive
     *        The value of the max inclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet
     *         value specified by the {@code minInclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet
     *         value specified by the {@code maxInclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(
            double minInclusive, double maxInclusive);

    /**
     * Creates a datatype restriction on xsd:double with a minExclusive facet
     * restriction
     * 
     * @param minExclusive
     *        The value of the min exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(
            double minExclusive);

    /**
     * Creates a datatype restriction on xsd:double with a maxExclusive facet
     * restriction
     * 
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(
            double maxExclusive);

    /**
     * Creates a datatype restriction on xsd:double with min and max exclusive
     * facet restrictions
     * 
     * @param minExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype.
     * @param maxExclusive
     *        The value of the max exclusive facet restriction that will be
     *        applied to the {@code xsd:double} datatype
     * @return An {@code OWLDatatypeRestriction} that restricts the
     *         {@code xsd:double}
     *         {@link org.semanticweb.owlapi.model.OWLDatatype} with a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet
     *         value specified by the {@code minExclusive} parameter and a
     *         {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet
     *         value specified by the {@code maxExclusive} parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(
            double minExclusive, double maxExclusive);

    /**
     * @param facet
     *        Cannot be null.
     * @param facetValue
     *        Cannot be null.
     * @return an OWLFacetRestriction on specified facet and value
     */
    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
            OWLLiteral facetValue);

    /**
     * @param facet
     *        Cannot be null.
     * @param facetValue
     *        value for facet
     * @return an OWLFacetRestriction on specified facet and value
     */
    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, int facetValue);

    /**
     * @param facet
     *        Cannot be null.
     * @param facetValue
     *        Cannot be null.
     * @return an OWLFacetRestriction on specified facet and value
     */
    OWLFacetRestriction
            getOWLFacetRestriction(OWLFacet facet, double facetValue);

    /**
     * @param facet
     *        Cannot be null.
     * @param facetValue
     *        Cannot be null.
     * @return an OWLFacetRestriction on specified facet and value
     */
    OWLFacetRestriction
            getOWLFacetRestriction(OWLFacet facet, float facetValue);

    /**
     * @param dataRanges
     *        Cannot be null or contain nulls.
     * @return an OWLDataUnionOf on the specified dataranges
     */
    OWLDataUnionOf getOWLDataUnionOf(Set<? extends OWLDataRange> dataRanges);

    /**
     * @param dataRanges
     *        Cannot be null or contain nulls.
     * @return an OWLDataUnionOf on the specified dataranges
     */
    OWLDataUnionOf getOWLDataUnionOf(OWLDataRange... dataRanges);

    /**
     * @param dataRanges
     *        Cannot be null or contain nulls.
     * @return an OWLDataIntersectionOf on the specified dataranges
     */
    OWLDataIntersectionOf getOWLDataIntersectionOf(
            Set<? extends OWLDataRange> dataRanges);

    /**
     * @param dataRanges
     *        Cannot be null or contain nulls.
     * @return an OWLDataIntersectionOf on the specified dataranges
     */
    OWLDataIntersectionOf getOWLDataIntersectionOf(OWLDataRange... dataRanges);

    // //////////////////////////////////////////////////////////////////////////////////
    //
    // Class Expressions
    //
    // //////////////////////////////////////////////////////////////////////////////////
    /**
     * @param operands
     *        Cannot be null or contain nulls.
     * @return an OWLObjectIntersectionOf on the specified operands
     */
    OWLObjectIntersectionOf getOWLObjectIntersectionOf(
            Set<? extends OWLClassExpression> operands);

    /**
     * @param operands
     *        Cannot be null or contain nulls.
     * @return an OWLObjectIntersectionOf on the specified operands
     */
    OWLObjectIntersectionOf getOWLObjectIntersectionOf(
            OWLClassExpression... operands);

    // //////////////////////////////////////////////////////////////////////////////////
    //
    // Data restrictions
    //
    // //////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets an OWLDataSomeValuesFrom restriction
     * 
     * @param property
     *        The property that the restriction acts along. Cannot be null.
     * @param dataRange
     *        The data range that is the filler. Cannot be null.
     * @return An OWLDataSomeValuesFrom restriction that acts along the
     *         specified property and has the specified filler
     */
    OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(
            OWLDataPropertyExpression property, OWLDataRange dataRange);

    /**
     * @param property
     *        Cannot be null.
     * @param dataRange
     *        Cannot be null.
     * @return An OWLDataAllValuesFrom restriction that acts along the specified
     *         property and has the specified filler
     */
    OWLDataAllValuesFrom getOWLDataAllValuesFrom(
            OWLDataPropertyExpression property, OWLDataRange dataRange);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
            OWLDataPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        Cannot be null.
     * @param dataRange
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLDataExactCardinality getOWLDataExactCardinality(int cardinality,
            OWLDataPropertyExpression property, OWLDataRange dataRange);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
            OWLDataPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        Cannot be null.
     * @param dataRange
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality,
            OWLDataPropertyExpression property, OWLDataRange dataRange);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
            OWLDataPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        Cannot be null.
     * @param dataRange
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLDataMinCardinality getOWLDataMinCardinality(int cardinality,
            OWLDataPropertyExpression property, OWLDataRange dataRange);

    /**
     * @param property
     *        Cannot be null
     * @param value
     *        Cannot be null.
     * @return a HasValue restriction with specified property and value
     */
    OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property,
            OWLLiteral value);

    /**
     * @param operand
     *        Cannot be null.
     * @return the complement of the specified argument
     */
    OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand);

    /**
     * @param values
     *        Cannot be null or contain nulls.
     * @return a OneOf expression on specified individuals
     */
    OWLObjectOneOf getOWLObjectOneOf(Set<? extends OWLIndividual> values);

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @return a OneOf expression on specified individuals
     */
    OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals);

    // //////////////////////////////////////////////////////////////////////////////////
    //
    // Object restrictions
    //
    // //////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param classExpression
     *        Cannot be null.
     * @return an AllValuesFrom on specified property and class expression
     */
    OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(
            OWLObjectPropertyExpression property,
            OWLClassExpression classExpression);

    /**
     * Gets an OWLObjectSomeValuesFrom restriction
     * 
     * @param property
     *        The object property that the restriction acts along. Cannot be
     *        null.
     * @param classExpression
     *        The class expression that is the filler. Cannot be null.
     * @return An OWLObjectSomeValuesFrom restriction along the specified
     *         property with the specified filler
     */
    OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(
            OWLObjectPropertyExpression property,
            OWLClassExpression classExpression);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
            OWLObjectPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        Cannot be null.
     * @param classExpression
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality,
            OWLObjectPropertyExpression property,
            OWLClassExpression classExpression);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
            OWLObjectPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        Cannot be null.
     * @param classExpression
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality,
            OWLObjectPropertyExpression property,
            OWLClassExpression classExpression);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
            OWLObjectPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        Cannot be null.
     * @param classExpression
     *        Cannot be null.
     * @return an ExactCardinality on the specified property
     */
    OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality,
            OWLObjectPropertyExpression property,
            OWLClassExpression classExpression);

    /**
     * @param property
     *        Cannot be null.
     * @return a ObjectHasSelf class expression on the specified property
     */
    OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param individual
     *        Cannot be null.
     * @return a HasValue restriction with specified property and value
     */
    OWLObjectHasValue getOWLObjectHasValue(
            OWLObjectPropertyExpression property, OWLIndividual individual);

    /**
     * @param operands
     *        cannot be null or contain nulls.
     * @return a class union over the specified arguments
     */
    OWLObjectUnionOf getOWLObjectUnionOf(
            Set<? extends OWLClassExpression> operands);

    /**
     * @param operands
     *        cannot be null or contain nulls.
     * @return a class union over the specified arguments
     */
    OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands);

    /****************************************************************************************************************/
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // //// Axioms
    // ////
    // ////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets a declaration for an entity
     * 
     * @param owlEntity
     *        The declared entity. Cannot be null.
     * @return The declaration axiom for the specified entity.
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity);

    /**
     * Gets a declaration with zero or more annotations for an entity
     * 
     * @param owlEntity
     *        The declared entity. Cannot be null.
     * @param annotations
     *        A possibly empty set of annotations. Cannot be null or contain
     *        nulls.
     * @return The declaration axiom for the specified entity which is annotated
     *         with the specified annotations
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Class Axioms
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param subClass
     *        Cannot be null.
     * @param superClass
     *        Cannot be null.
     * @return a subclass axiom with no annotations
     */
    OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
            OWLClassExpression superClass);

    /**
     * @param subClass
     *        Cannot be null.
     * @param superClass
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a subclass axiom with specified annotations
     */
    OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
            OWLClassExpression superClass,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param classExpressions
     *        Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations
     */
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            Set<? extends OWLClassExpression> classExpressions);

    /**
     * @param classExpressions
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and
     *         annotations
     */
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            Set<? extends OWLClassExpression> classExpressions,
            Set<? extends OWLAnnotation> annotations);

    /**
     * @param classExpressions
     *        Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations
     */
    // TODO there is a limitation here, no way to use a vararg and annotate the
    // axiom as well
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            OWLClassExpression... classExpressions);

    /**
     * @param clsA
     *        Cannot be null.
     * @param clsB
     *        Cannot be null.
     * @return an equivalent classes axiom with specified operands and no
     *         annotations (special case with only two operands)
     */
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            OWLClassExpression clsA, OWLClassExpression clsB);

    /**
     * @param clsA
     *        Cannot be null.
     * @param clsB
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an equivalent classes axiom with specified operands and
     *         annotations (special case with only two operands)
     */
    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(
            OWLClassExpression clsA, OWLClassExpression clsB,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param classExpressions
     *        Cannot be null or contain nulls.
     * @return a disjoint class axiom with no annotations
     */
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            Set<? extends OWLClassExpression> classExpressions);

    /**
     * @param classExpressions
     *        Cannot be null or contain nulls.
     * @return a disjoint class axiom with no annotations
     */
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            OWLClassExpression... classExpressions);

    /**
     * @param classExpressions
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a disjoint class axiom with annotations
     */
    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(
            Set<? extends OWLClassExpression> classExpressions,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param owlClass
     *        left hand side of the axiom. Cannot be null.
     * @param classExpressions
     *        right hand side of the axiom. Cannot be null or contain nulls.
     * @return a disjoint union axiom
     */
    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
            Set<? extends OWLClassExpression> classExpressions);

    /**
     * @param owlClass
     *        left hand side of the axiom. Cannot be null.
     * @param classExpressions
     *        right hand side of the axiom. Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a disjoint union axiom with annotations
     */
    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
            Set<? extends OWLClassExpression> classExpressions,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Object property axioms
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param subProperty
     *        Cannot be null.
     * @param superProperty
     *        Cannot be null.
     * @return a subproperty axiom
     */
    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
            OWLObjectPropertyExpression subProperty,
            OWLObjectPropertyExpression superProperty);

    /**
     * @param subProperty
     *        Cannot be null.
     * @param superProperty
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a subproperty axiom with annotations
     */
    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(
            OWLObjectPropertyExpression subProperty,
            OWLObjectPropertyExpression superProperty,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param chain
     *        Cannot be null or contain nulls.
     * @param superProperty
     *        Cannot be null.
     * @return a subproperty chain axiom
     */
    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
            List<? extends OWLObjectPropertyExpression> chain,
            OWLObjectPropertyExpression superProperty);

    /**
     * @param chain
     *        Cannot be null or contain nulls.
     * @param superProperty
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a subproperty chain axiom
     */
    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(
            List<? extends OWLObjectPropertyExpression> chain,
            OWLObjectPropertyExpression superProperty,
            Set<? extends OWLAnnotation> annotations);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties
     */
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties and
     *         annotations
     */
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties,
            Set<? extends OWLAnnotation> annotations);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties
     */
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            OWLObjectPropertyExpression... properties);

    /**
     * @param propertyA
     *        Cannot be null.
     * @param propertyB
     *        Cannot be null.
     * @return an equivalent properties axiom with specified properties
     */
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            OWLObjectPropertyExpression propertyA,
            OWLObjectPropertyExpression propertyB);

    /**
     * @param propertyA
     *        Cannot be null.
     * @param propertyB
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an equivalent properties axiom with specified properties and
     *         annotations
     */
    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            OWLObjectPropertyExpression propertyA,
            OWLObjectPropertyExpression propertyB,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties
     */
    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties
     */
    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            OWLObjectPropertyExpression... properties);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a disjoint object properties axiom with specified properties and
     *         annotations
     */
    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param forwardProperty
     *        Cannot be null.
     * @param inverseProperty
     *        Cannot be null.
     * @return an inverse object property axiom
     */
    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
            OWLObjectPropertyExpression forwardProperty,
            OWLObjectPropertyExpression inverseProperty);

    /**
     * @param forwardProperty
     *        Cannot be null.
     * @param inverseProperty
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an inverse object property axiom with annotations
     */
    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(
            OWLObjectPropertyExpression forwardProperty,
            OWLObjectPropertyExpression inverseProperty,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param classExpression
     *        Cannot be null.
     * @return an object property domain axiom
     */
    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
            OWLObjectPropertyExpression property,
            OWLClassExpression classExpression);

    /**
     * @param property
     *        Cannot be null.
     * @param classExpression
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an object property domain axiom with annotations
     */
    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
            OWLObjectPropertyExpression property,
            OWLClassExpression classExpression,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param range
     *        Cannot be null.
     * @return an object property range axiom
     */
    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            OWLObjectPropertyExpression property, OWLClassExpression range);

    /**
     * @param property
     *        Cannot be null.
     * @param range
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an object property range axiom with annotations
     */
    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(
            OWLObjectPropertyExpression property, OWLClassExpression range,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @return a functional object property axiom
     */
    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
            OWLObjectPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a functional object property axiom with annotations
     */
    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(
            OWLObjectPropertyExpression property,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @return an inverse functional object property axiom
     */
    OWLInverseFunctionalObjectPropertyAxiom
            getOWLInverseFunctionalObjectPropertyAxiom(
                    OWLObjectPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an inverse functional object property axiom with annotations
     */
    OWLInverseFunctionalObjectPropertyAxiom
            getOWLInverseFunctionalObjectPropertyAxiom(
                    OWLObjectPropertyExpression property,
                    Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @return a reflexive object property axiom
     */
    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a reflexive object property axiom with annotations
     */
    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @return an irreflexive object property axiom
     */
    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an irreflexive object property axiom with annotations
     */
    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @return a symmetric property axiom
     */
    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            OWLObjectPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a symmetric property axiom with annotations
     */
    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(
            OWLObjectPropertyExpression property,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param propertyExpression
     *        Cannot be null.
     * @return an asymmetric object property axiom on the specified argument
     */
    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
            OWLObjectPropertyExpression propertyExpression);

    /**
     * @param propertyExpression
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an asymmetric object property axiom on the specified argument
     *         with annotations
     */
    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(
            OWLObjectPropertyExpression propertyExpression,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @return a transitive object property axiom on the specified argument
     */
    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a transitive object property axiom on the specified argument with
     *         annotations
     */
    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(
            OWLObjectPropertyExpression property,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Data property axioms
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param subProperty
     *        Cannot be null.
     * @param superProperty
     *        Cannot be null.
     * @return a subproperty axiom
     */
    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
            OWLDataPropertyExpression subProperty,
            OWLDataPropertyExpression superProperty);

    /**
     * @param subProperty
     *        Cannot be null.
     * @param superProperty
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a subproperty axiom with annotations
     */
    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(
            OWLDataPropertyExpression subProperty,
            OWLDataPropertyExpression superProperty,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent data properties axiom
     */
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties);

    /**
     * @param properties
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an equivalent data properties axiom with annotations
     */
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties,
            Set<? extends OWLAnnotation> annotations);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return an equivalent data properties axiom
     */
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            OWLDataPropertyExpression... properties);

    /**
     * @param propertyA
     *        Cannot be null.
     * @param propertyB
     *        Cannot be null.
     * @return an equivalent data properties axiom
     */
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            OWLDataPropertyExpression propertyA,
            OWLDataPropertyExpression propertyB);

    /**
     * @param propertyA
     *        Cannot be null.
     * @param propertyB
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an equivalent data properties axiom with annotations
     */
    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            OWLDataPropertyExpression propertyA,
            OWLDataPropertyExpression propertyB,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param dataProperties
     *        Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties
     */
    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            OWLDataPropertyExpression... dataProperties);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties
     */
    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties);

    /**
     * @param properties
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a disjoint data properties axiom with specified properties and
     *         annotations
     */
    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param domain
     *        Cannot be null.
     * @return a data property domain axiom
     */
    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
            OWLDataPropertyExpression property, OWLClassExpression domain);

    /**
     * @param property
     *        Cannot be null.
     * @param domain
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a data property domain axiom with annotations
     */
    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
            OWLDataPropertyExpression property, OWLClassExpression domain,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param owlDataRange
     *        Cannot be null.
     * @return a data property range axiom
     */
    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            OWLDataPropertyExpression property, OWLDataRange owlDataRange);

    /**
     * @param property
     *        Cannot be null.
     * @param owlDataRange
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a data property range axiom with annotations
     */
    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(
            OWLDataPropertyExpression property, OWLDataRange owlDataRange,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @return a functional data property axiom
     */
    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            OWLDataPropertyExpression property);

    /**
     * @param property
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a functional data property axiom with annotations
     */
    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(
            OWLDataPropertyExpression property,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Data axioms
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param ce
     *        Cannot be null.
     * @param properties
     *        Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments
     */
    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
            Set<? extends OWLPropertyExpression<?, ?>> properties);

    /**
     * @param ce
     *        Cannot be null.
     * @param properties
     *        Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments
     */
    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
            OWLPropertyExpression<?, ?>... properties);

    /**
     * @param ce
     *        Cannot be null.
     * @param objectProperties
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a hasKey axiom on given arguments and annotations
     */
    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
            Set<? extends OWLPropertyExpression<?, ?>> objectProperties,
            Set<? extends OWLAnnotation> annotations);

    /**
     * @param datatype
     *        Cannot be null.
     * @param dataRange
     *        Cannot be null.
     * @return a datatype definition axiom
     */
    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(
            OWLDatatype datatype, OWLDataRange dataRange);

    /**
     * @param datatype
     *        Cannot be null.
     * @param dataRange
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a datatype definition axiom with annotations
     */
    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(
            OWLDatatype datatype, OWLDataRange dataRange,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Assertion (Individual) axioms
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @return a same individuals axiom with specified individuals
     */
    OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            Set<? extends OWLIndividual> individuals);

    /**
     * @param individual
     *        Cannot be null.
     * @return a same individuals axiom with specified individuals
     */
    OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            OWLIndividual... individual);

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a same individuals axiom with specified individuals and
     *         annotations
     */
    OWLSameIndividualAxiom getOWLSameIndividualAxiom(
            Set<? extends OWLIndividual> individuals,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals
     */
    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            Set<? extends OWLIndividual> individuals);

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals
     */
    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            OWLIndividual... individuals);

    /**
     * @param individuals
     *        Cannot be null or contain nulls.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a different individuals axiom with specified individuals and
     *         annotations
     */
    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(
            Set<? extends OWLIndividual> individuals,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param classExpression
     *        Cannot be null.
     * @param individual
     *        Cannot be null.
     * @return a class assertion axiom
     */
    OWLClassAssertionAxiom getOWLClassAssertionAxiom(
            OWLClassExpression classExpression, OWLIndividual individual);

    /**
     * @param classExpression
     *        Cannot be null.
     * @param individual
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a class assertion axiom with annotations
     */
    OWLClassAssertionAxiom getOWLClassAssertionAxiom(
            OWLClassExpression classExpression, OWLIndividual individual,
            Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param individual
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @return an object property assertion
     */
    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            OWLObjectPropertyExpression property, OWLIndividual individual,
            OWLIndividual object);

    /**
     * @param property
     *        Cannot be null.
     * @param individual
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an object property assertion with annotations
     */
    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(
            OWLObjectPropertyExpression property, OWLIndividual individual,
            OWLIndividual object, Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @return a negative property assertion axiom on given arguments
     */
    OWLNegativeObjectPropertyAssertionAxiom
            getOWLNegativeObjectPropertyAssertionAxiom(
                    OWLObjectPropertyExpression property,
                    OWLIndividual subject, OWLIndividual object);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a negative property assertion axiom on given arguments with
     *         annotations
     */
    OWLNegativeObjectPropertyAssertionAxiom
            getOWLNegativeObjectPropertyAssertionAxiom(
                    OWLObjectPropertyExpression property,
                    OWLIndividual subject, OWLIndividual object,
                    Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @return a data property assertion
     */
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            OWLDataPropertyExpression property, OWLIndividual subject,
            OWLLiteral object);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a data property assertion with annotations
     */
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            OWLDataPropertyExpression property, OWLIndividual subject,
            OWLLiteral object, Set<? extends OWLAnnotation> annotations);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param value
     *        Cannot be null.
     * @return a data property assertion
     */
    OWLDataPropertyAssertionAxiom
            getOWLDataPropertyAssertionAxiom(
                    OWLDataPropertyExpression property, OWLIndividual subject,
                    int value);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param value
     *        Cannot be null.
     * @return a data property assertion
     */
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            OWLDataPropertyExpression property, OWLIndividual subject,
            double value);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param value
     *        Cannot be null.
     * @return a data property assertion
     */
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            OWLDataPropertyExpression property, OWLIndividual subject,
            float value);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param value
     *        Cannot be null.
     * @return a data property assertion
     */
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            OWLDataPropertyExpression property, OWLIndividual subject,
            boolean value);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param value
     *        Cannot be null.
     * @return a data property assertion
     */
    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(
            OWLDataPropertyExpression property, OWLIndividual subject,
            String value);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @return a negative property assertion axiom on given arguments
     */
    OWLNegativeDataPropertyAssertionAxiom
            getOWLNegativeDataPropertyAssertionAxiom(
                    OWLDataPropertyExpression property, OWLIndividual subject,
                    OWLLiteral object);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param object
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a negative property assertion axiom on given arguments with
     *         annotations
     */
            OWLNegativeDataPropertyAssertionAxiom
            getOWLNegativeDataPropertyAssertionAxiom(
                    OWLDataPropertyExpression property, OWLIndividual subject,
                    OWLLiteral object, Set<? extends OWLAnnotation> annotations);

    /****************************************************************************************************************/
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // //// Annotations
    // ////
    // ////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets an annotation
     * 
     * @param property
     *        the annotation property. Cannot be null.
     * @param value
     *        The annotation value. Cannot be null.
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
            OWLAnnotationValue value);

    /**
     * Gets an annotation
     * 
     * @param property
     *        the annotation property. Cannot be null.
     * @param value
     *        The annotation value. Cannot be null.
     * @param annotations
     *        Annotations on the annotation. Cannot be null or contain nulls.
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
            OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //
    // // Annotation axioms
    // //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param value
     *        Cannot be null.
     * @return an annotation assertion axiom
     */
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            OWLAnnotationProperty property, OWLAnnotationSubject subject,
            OWLAnnotationValue value);

    /**
     * @param subject
     *        Cannot be null.
     * @param annotation
     *        Cannot be null.
     * @return an annotation assertion axiom
     */
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            OWLAnnotationSubject subject, OWLAnnotation annotation);

    /**
     * @param property
     *        Cannot be null.
     * @param subject
     *        Cannot be null.
     * @param value
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations
     */
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            OWLAnnotationProperty property, OWLAnnotationSubject subject,
            OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations);

    /**
     * @param subject
     *        Cannot be null.
     * @param annotation
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an annotation assertion axiom - with annotations
     */
    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(
            OWLAnnotationSubject subject, OWLAnnotation annotation,
            Set<? extends OWLAnnotation> annotations);

    /**
     * Gets an annotation assertion that specifies that an IRI is deprecated.
     * The annotation property is owl:deprecated and the value of the annotation
     * is {@code "true"^^xsd:boolean}. (See <a href=
     * "http://www.w3.org/TR/owl2-syntax/#Annotation_Properties" >Annotation
     * Properties</a> in the OWL 2 Specification
     * 
     * @param subject
     *        The IRI to be deprecated. Cannot be null.
     * @return The annotation assertion that deprecates the specified IRI.
     */
    OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(
            IRI subject);

    /**
     * @param importedOntologyIRI
     *        Cannot be null.
     * @return an imports declaration
     */
    OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI);

    /**
     * @param prop
     *        Cannot be null.
     * @param domain
     *        Cannot be null.
     * @return an annotation property domain assertion
     */
    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
            OWLAnnotationProperty prop, IRI domain);

    /**
     * @param prop
     *        Cannot be null.
     * @param domain
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an annotation property domain assertion with annotations
     */
    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
            OWLAnnotationProperty prop, IRI domain,
            Set<? extends OWLAnnotation> annotations);

    /**
     * @param prop
     *        Cannot be null.
     * @param range
     *        Cannot be null.
     * @return an annotation property range assertion
     */
    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            OWLAnnotationProperty prop, IRI range);

    /**
     * @param prop
     *        Cannot be null.
     * @param range
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return an annotation property range assertion with annotations
     */
    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(
            OWLAnnotationProperty prop, IRI range,
            Set<? extends OWLAnnotation> annotations);

    /**
     * @param sub
     *        Cannot be null.
     * @param sup
     *        Cannot be null.
     * @return a sub annotation property axiom with specified properties
     */
    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
            OWLAnnotationProperty sub, OWLAnnotationProperty sup);

    /**
     * @param sub
     *        Cannot be null.
     * @param sup
     *        Cannot be null.
     * @param annotations
     *        Cannot be null or contain nulls.
     * @return a sub annotation property axiom with specified properties and
     *         annotations
     */
    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(
            OWLAnnotationProperty sub, OWLAnnotationProperty sup,
            Set<? extends OWLAnnotation> annotations);

    /** Empty all caches */
    void purge();
}
