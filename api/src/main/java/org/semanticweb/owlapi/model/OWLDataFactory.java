package org.semanticweb.owlapi.model;


import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * An interface for creating entities, class expressions and axioms.
 */
public interface OWLDataFactory extends SWRLDataFactory {


    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Entities and data stuff
    //
    ////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the built in owl:Thing class, which has a URI of &lt;http://www.w3.org/2002/07/owl#Thing&gt;
     *
     * @return The OWL Class corresponding to owl:Thing
     */
    OWLClass getOWLThing();


    /**
     * Gets the built in owl:Nothing class, which has a URI of &lt;http://www.w3.org/2002/07/owl#Nothing&gt;
     *
     * @return The OWL Class corresponding to owl:Nothing
     */
    OWLClass getOWLNothing();


    OWLObjectProperty getOWLTopObjectProperty();


    OWLDataProperty getOWLTopDataProperty();


    OWLObjectProperty getOWLBottomObjectProperty();


    OWLDataProperty getOWLBottomDataProperty();


    /**
     * Gets the built in data range corresponding to the top data type (like owl:Thing but for data ranges),
     * this datatype is rdfs:Literal, and has a URI of $lt;http://www.w3.org/2000/01/rdf-schema#&gt;
     *
     * @return The OWL Datatype corresponding to the top data type.
     */
    OWLDatatype getTopDatatype();


    /**
     * Gets an entity that has the specified IRI and is of the specified type.
     *
     * @param entityType The type of the entity that will be returned
     * @param iri The IRI of the entity that will be returned
     * @return An entity that has the same IRI as this entity and is of the specified type
     */
    <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType, IRI iri);

    /**
     * Gets an OWL class that has the specified IRI
     *
     * @param iri The IRI of the class
     * @return The object representing the class that has the specified IRI
     */
    OWLClass getOWLClass(IRI iri);

    /**
     * Gets an OWLClass that has an IRI that is obtained by expanding an abbreviated name using an appropriate
     * prefix mapping.  See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2 Structural Specification</a>
     * for more details.
     *
     * @param abbreviatedIRI The abbreviated IRI, which is of the form PREFIX_NAME:RC, where PREFIX_NAME may
     * be the empty string (the default prefix).  Note that abbreviated IRIs always contain a colon as a delimiter,
     * even if the prefix name is the empty string. 
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefix IRIs.
     * @return An OWLClass that has the IRI obtained by expanding the specified abbreviated IRI using the specified prefix
     *         manager.
     *         </p>
     *         For example, suppose "m:Cat" was specified as the abbreviated IRI, the prefix manager would be used to obtain
     *         the IRI prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full IRI
     *         which would be the IRI of the OWLClass obtained by this method.
     *
     * @throws OWLRuntimeException if the prefix name in the specified abbreviated IRI does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLClass getOWLClass(String abbreviatedIRI, PrefixManager prefixManager);


    /**
     * Gets an OWL object property that has the specified IRI
     *
     * @param iri The IRI of the object property to be obtained
     * @return The object representing the object property that has the specified IRI
     */
    OWLObjectProperty getOWLObjectProperty(IRI iri);


    /**
     * Gets an OWLObjectProperty that has an IRI that is obtained by expanding an abbreviated name using an appropriate
     * prefix mapping.  See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2 Structural Specification</a>
     * for more details.
     *
     * @param abbreviatedIRI The abbreviated IRI, which is of the form PREFIX_NAME:RC, where PREFIX_NAME may
     * be the empty string (the default prefix).  Note that abbreviated IRIs always contain a colon as a delimiter,
     * even if the prefix name is the empty string.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefix IRIs.
     * @return An OWLObjectProperty that has the IRI obtained by expanding the specified abbreviated IRI using the specified prefix
     *         manager.
     *         </p>
     *         For example, suppose "m:Cat" was specified as the abbreviated IRI, the prefix manager would be used to obtain
     *         the IRI prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full IRI
     *         which would be the IRI of the OWLObjectProperty obtained by this method.
     *
     * @throws OWLRuntimeException if the prefix name in the specified abbreviated IRI does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLObjectProperty getOWLObjectProperty(String abbreviatedIRI, PrefixManager prefixManager);



    /**
     * Gets the inverse of an object property.
     *
     * @param property The property of which the inverse will be returned
     * @return The inverse of the specified object property
     */
    OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectPropertyExpression property);


    /**
     * Gets an OWL data property that has the specified IRI
     *
     * @param iri The IRI of the data property to be obtained
     * @return The object representing the data property that has the specified IRI
     */
    OWLDataProperty getOWLDataProperty(IRI iri);


    /**
     * Gets an OWLDataProperty that has an IRI that is obtained by expanding an abbreviated name using an appropriate
     * prefix mapping.  See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2 Structural Specification</a>
     * for more details.
     *
     * @param abbreviatedIRI The abbreviated IRI, which is of the form PREFIX_NAME:RC, where PREFIX_NAME may
     * be the empty string (the default prefix).  Note that abbreviated IRIs always contain a colon as a delimiter,
     * even if the prefix name is the empty string.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefix IRIs.
     * @return An OWLDataProperty that has the IRI obtained by expanding the specified abbreviated IRI using the specified prefix
     *         manager.
     *         </p>
     *         For example, suppose "m:Cat" was specified as the abbreviated IRI, the prefix manager would be used to obtain
     *         the IRI prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full IRI
     *         which would be the IRI of the OWLDataProperty obtained by this method.
     *
     * @throws OWLRuntimeException if the prefix name in the specified abbreviated IRI does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLDataProperty getOWLDataProperty(String abbreviatedIRI, PrefixManager prefixManager);


    /**
     * Gets an OWL individual that has the specified IRI
     *
     * @param iri The IRI of the individual to be obtained
     * @return The object representing the individual that has the specified IRI
     */
    OWLNamedIndividual getOWLNamedIndividual(IRI iri);

    /**
     * Gets an OWLNamedIndividual that has an IRI that is obtained by expanding an abbreviated name using an appropriate
     * prefix mapping.  See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2 Structural Specification</a>
     * for more details.
     *
     * @param abbreviatedIRI The abbreviated IRI, which is of the form PREFIX_NAME:RC, where PREFIX_NAME may
     * be the empty string (the default prefix).  Note that abbreviated IRIs always contain a colon as a delimiter,
     * even if the prefix name is the empty string.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefix IRIs.
     * @return An OWLNamedIndividual that has the IRI obtained by expanding the specified abbreviated IRI using the specified prefix
     *         manager.
     *         </p>
     *         For example, suppose "m:Cat" was specified as the abbreviated IRI, the prefix manager would be used to obtain
     *         the IRI prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full IRI
     *         which would be the IRI of the OWLNamedIndividual obtained by this method.
     *
     * @throws OWLRuntimeException if the prefix name in the specified abbreviated IRI does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLNamedIndividual getOWLNamedIndividual(String abbreviatedIRI, PrefixManager prefixManager);


    /**
     * Gets an anonymous individual that has a specific ID.
     *
     * @param id The node ID.  Note that the ID will be preixed with _: if it is not specifed with
     * _: as a prefix
     * @return An anonymous individual.
     */
    OWLAnonymousIndividual getOWLAnonymousIndividual(String id);

    /**
     * Gets an anonymous individual.  The node ID for the individual will be generated automatically
     *
     * @return The anonymous individual
     */
    OWLAnonymousIndividual getOWLAnonymousIndividual();


    /**
     * Gets an OWLAnnotationProperty that has the specified IRI
     * @param iri The IRI of the annotation property to be obtained
     * @return An OWLAnnotationProperty with the specified IRI
     */
    OWLAnnotationProperty getOWLAnnotationProperty(IRI iri);
    
    /**
     * Gets an OWLAnnotationProperty that has an IRI that is obtained by expanding an abbreviated name using an appropriate
     * prefix mapping.  See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2 Structural Specification</a>
     * for more details.
     *
     * @param abbreviatedIRI The abbreviated IRI, which is of the form PREFIX_NAME:RC, where PREFIX_NAME may
     * be the empty string (the default prefix).  Note that abbreviated IRIs always contain a colon as a delimiter,
     * even if the prefix name is the empty string.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefix IRIs.
     * @return An OWLAnnotationProperty that has the IRI obtained by expanding the specified abbreviated IRI using the specified prefix
     *         manager.
     *         </p>
     *         For example, suppose "m:Cat" was specified as the abbreviated IRI, the prefix manager would be used to obtain
     *         the IRI prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full IRI
     *         which would be the IRI of the OWLAnnotationProperty obtained by this method.
     *
     * @throws OWLRuntimeException if the prefix name in the specified abbreviated IRI does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLAnnotationProperty getOWLAnnotationProperty(String abbreviatedIRI, PrefixManager prefixManager);

    /**
     * Gets an annotation property that has an IRI corresponding to <code>rdfs:label</code>.
     * @return An annotation property with an IRI of <code>rdfs:label</code>.
     */
    OWLAnnotationProperty getRDFSLabel();

    /**
     * Gets an annotation property that has an IRI corresponding to <code>rdfs:comment</code>.
     * @return An annotation property with an IRI of <code>rdfs:comment</code>.
     */
    OWLAnnotationProperty getRDFSComment();

    /**
     * Gets an annotation property that has an IRI corresponding to <code>rdfs:seeAlso</code>.
     * @return An annotation property with an IRI of <code>rdfs:seeAlso</code>.
     */
    OWLAnnotationProperty getRDFSSeeAlso();

    /**
     * Gets an annotation property that has an IRI corresponding to <code>rdfs:isDefinedBy</code>.
     * @return An annotation property with an IRI of <code>rdfs:isDefinedBy</code>.
     */
    OWLAnnotationProperty getRDFSIsDefinedBy();

    /**
     * Gets an annotation property that has an IRI corresponding to <code>owl:versionInfo</code>.
     * @return An annotation property with an IRI of <code>owl:versionInfo</code>.
     */
    OWLAnnotationProperty getOWLVersionInfo();

    /**
     * Gets an annotation property that has an IRI corresponding to <code>owl:backwardCompatibleWith</code>.
     * @return An annotation property with an IRI of <code>owl:backwardCompatibleWith</code>.
     */
    OWLAnnotationProperty getOWLBackwardCompatibleWith();

    /**
     * Gets an annotation property that has an IRI corresponding to <code>owl:incompatibleWith</code>.
     * @return An annotation property with an IRI of <code>owl:incompatibleWith</code>.
     */
    OWLAnnotationProperty getOWLIncompatibleWith();

    /**
     * Gets an annotation property that has an IRI corresponding to <code>owl:backwardCompatibleWith</code>.
     * @return An annotation property with an IRI of <code>owl:backwardCompatibleWith</code>.
     */
    OWLAnnotationProperty getOWLDeprecated();

    /**
     * Gets an OWLDatatype that has the specified IRI
     *
     * @param iri The IRI of the datatype to be obtained
     * @return The object representing the datatype that has the specified IRI
     */
    OWLDatatype getOWLDatatype(IRI iri);

    /**
     * Gets an OWLDatatype that has an IRI that is obtained by expanding an abbreviated name using an appropriate
     * prefix mapping.  See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#IRIs">The OWL 2 Structural Specification</a>
     * for more details.
     *
     * @param abbreviatedIRI The abbreviated IRI, which is of the form PREFIX_NAME:RC, where PREFIX_NAME may
     * be the empty string (the default prefix).  Note that abbreviated IRIs always contain a colon as a delimiter,
     * even if the prefix name is the empty string.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefix IRIs.
     * @return An OWLDatatype that has the IRI obtained by expanding the specified abbreviated IRI using the specified prefix
     *         manager.
     *         </p>
     *         For example, suppose "m:Cat" was specified as the abbreviated IRI, the prefix manager would be used to obtain
     *         the IRI prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full IRI
     *         which would be the IRI of the OWLDatatype obtained by this method.
     *
     * @throws OWLRuntimeException if the prefix name in the specified abbreviated IRI does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLDatatype getOWLDatatype(String abbreviatedIRI, PrefixManager prefixManager);
    

    /**
     * A convenience method that obtains the datatype that represents integers.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#integer&gt;
     *
     * @return An object representing an integer datatype.
     */
    OWLDatatype getIntegerOWLDatatype();


    /**
     * A convenience method that obtains the datatype that represents floats.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#float&gt;
     *
     * @return An object representing the float datatype.
     */
    OWLDatatype getFloatOWLDatatype();


    /**
     * A convenience method that obtains the datatype that represents doubles.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#double&gt;
     *
     * @return An object representing a double datatype.
     */
    OWLDatatype getDoubleOWLDatatype();


    /**
     * A convenience method that obtains the datatype that represents booleans.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#boolean&gt;
     *
     * @return An object representing the boolean datatype.
     */
    OWLDatatype getBooleanOWLDatatype();


    /////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Literals
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////


    OWLTypedLiteral getOWLTypedLiteral(String literal, OWLDatatype datatype);

    /**
     * Creates a typed literal that has the specified OWL 2 Datatype as its datatype
     * @param literal The literal
     * @param datatype The OWL 2 Datatype that will type the literal
     * @return The typed literal
     */
    OWLTypedLiteral getOWLTypedLiteral(String literal, OWL2Datatype datatype);

    /**
     * Convenience method that obtains a literal typed as an integer.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the integer, and whose data type is xsd:integer.
     */
    OWLTypedLiteral getOWLTypedLiteral(int value);


    /**
     * Convenience method that obtains a literal typed as a double.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the double, and whose data type is xsd:double.
     */
    OWLTypedLiteral getOWLTypedLiteral(double value);


    /**
     * Convenience method that obtains a literal typed as a boolean.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the boolean, and whose data type is xsd:boolean.
     */
    OWLTypedLiteral getOWLTypedLiteral(boolean value);


    /**
     * Convenience method that obtains a literal typed as a float.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the float, and whose data type is xsd:float.
     */
    OWLTypedLiteral getOWLTypedLiteral(float value);


    /**
     * Convenience method that obtains a literal typed as a string.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the string, and whose data type is xsd:string.
     */
    OWLTypedLiteral getOWLTypedLiteral(String value);


    /**
     * Gets an OWLStringLiteral with a language tag.
     *
     * @param literal The string literal
     * @param lang The language tag.  The tag is formed according to <a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">BCP47</a>
     * but the OWL API will not check that the tag conforms to this specification - it is up to the caller to ensure this.  For backwards
     * compatibility, if the value of lang is <code>null</code> then this is equivalent to calling the getOWLStringLiteral(String literal)
     * method.
     * @return The OWLStringLiteral that represents the string literal with a language tag.
     */
    OWLStringLiteral getOWLStringLiteral(String literal, String lang);

    /**
     * Gets a string literal without a language tag.
     *
     * @param literal The string literal
     * @return The string literal for the specfied string
     */
    OWLStringLiteral getOWLStringLiteral(String literal);


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Data ranges
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets an OWLDataOneOf <a href="http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals">(see spec)</a>
     *
     * @param values The set of values that the data one of should contain
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values);


    /**
     * Gets an OWLDataOneOf  <a href="http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals">(see spec)</a>
     *
     * @param values The set of values that the data one of should contain
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getOWLDataOneOf(OWLLiteral... values);


    /**
     * Gets an OWLDataComplementOf <a href="http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Complement_of_Data_Range">(see spec)</a>
     *
     * @param dataRange The datarange to be complemented
     * @return An OWLDataComplementOf of the specified data range
     */
    OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange);


    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange, Set<OWLFacetRestriction> facetRestrictions);


    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange, OWLFacet facet, OWLLiteral typedliteral);


    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange, OWLFacetRestriction... facetRestrictions);


    /**
     * Creates a datatype restriction on xsd:integer with a minInclusive facet restriction
     * @param minInclusive The value of the min inclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:integer</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet value specified by the <code>minInclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(int minInclusive);

    /**
     * Creates a datatype restriction on xsd:integer with a maxInclusive facet restriction
     * @param maxInclusive The value of the max inclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:integer</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet value specified by the <code>maxInclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(int maxInclusive);

    /**
     * Creates a datatype restriction on xsd:integer with min and max inclusive facet restrictions
     * @param minInclusive The value of the max inclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype.
     * @param maxInclusive The value of the max inclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:integer</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a  {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet value specified by the <code>minInclusive</code>
     * parameter and a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet value specified by the <code>maxInclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(int minInclusive, int maxInclusive);


    /**
     * Creates a datatype restriction on xsd:integer with a minExclusive facet restriction
     * @param minExclusive The value of the min exclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:integer</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet value specified by the <code>minExclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(int minExclusive);

    /**
     * Creates a datatype restriction on xsd:integer with a maxExclusive facet restriction
     * @param maxExclusive The value of the max exclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:integer</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet value specified by the <code>maxExclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(int maxExclusive);

    /**
     * Creates a datatype restriction on xsd:integer with min and max exclusive facet restrictions
     * @param minExclusive The value of the max exclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype.
     * @param maxExclusive The value of the max exclusive facet restriction that will be applied to the <code>xsd:integer</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:integer</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a  {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet value specified by the <code>minExclusive</code>
     * parameter and a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet value specified by the <code>maxExclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(int minExclusive, int maxExclusive);



    /**
     * Creates a datatype restriction on xsd:double with a minInclusive facet restriction
     * @param minInclusive The value of the min inclusive facet restriction that will be applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:double</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet value specified by the <code>minInclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinInclusiveRestriction(double minInclusive);

    /**
     * Creates a datatype restriction on xsd:double with a maxInclusive facet restriction
     * @param maxInclusive The value of the max inclusive facet restriction that will be applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:double</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet value specified by the <code>maxInclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxInclusiveRestriction(double maxInclusive);

    /**
     * Creates a datatype restriction on xsd:double with min and max inclusive facet restrictions
     * @param minInclusive The value of the max inclusive facet restriction that will be applied to the <code>xsd:double</code> datatype.
     * @param maxInclusive The value of the max inclusive facet restriction that will be applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:double</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a  {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_INCLUSIVE} facet value specified by the <code>minInclusive</code>
     * parameter and a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_INCLUSIVE} facet value specified by the <code>maxInclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxInclusiveRestriction(double minInclusive, double maxInclusive);


    /**
     * Creates a datatype restriction on xsd:double with a minExclusive facet restriction
     * @param minExclusive The value of the min exclusive facet restriction that will be applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:double</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet value specified by the <code>minExclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinExclusiveRestriction(double minExclusive);

    /**
     * Creates a datatype restriction on xsd:double with a maxExclusive facet restriction
     * @param maxExclusive The value of the max exclusive facet restriction that will be applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:double</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet value specified by the <code>maxExclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMaxExclusiveRestriction(double maxExclusive);

    /**
     * Creates a datatype restriction on xsd:double with min and max exclusive facet restrictions
     * @param minExclusive The value of the max exclusive facet restriction that will be applied to the <code>xsd:double</code> datatype.
     * @param maxExclusive The value of the max exclusive facet restriction that will be applied to the <code>xsd:double</code> datatype
     * @return An <code>OWLDatatypeRestriction</code> that restricts the <code>xsd:double</code> {@link org.semanticweb.owlapi.model.OWLDatatype} with
     * a  {@link org.semanticweb.owlapi.vocab.OWLFacet#MIN_EXCLUSIVE} facet value specified by the <code>minExclusive</code>
     * parameter and a {@link org.semanticweb.owlapi.vocab.OWLFacet#MAX_EXCLUSIVE} facet value specified by the <code>maxExclusive</code>
     * parameter.
     */
    OWLDatatypeRestriction getOWLDatatypeMinMaxExclusiveRestriction(double minExclusive, double maxExclusive);
    


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, OWLLiteral facetValue);


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, int facetValue);


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, double facetValue);


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet, float facetValue);


    OWLDataUnionOf getOWLDataUnionOf(Set<? extends OWLDataRange> dataRanges);


    OWLDataUnionOf getOWLDataUnionOf(OWLDataRange... dataRanges);


    OWLDataIntersectionOf getOWLDataIntersectionOf(Set<? extends OWLDataRange> dataRanges);


    OWLDataIntersectionOf getOWLDataIntersectionOf(OWLDataRange... dataRanges);

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Class Expressions
    //
    ////////////////////////////////////////////////////////////////////////////////////


    OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<? extends OWLClassExpression> operands);


    OWLObjectIntersectionOf getOWLObjectIntersectionOf(OWLClassExpression... operands);

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Data restrictions
    //
    ////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets an OWLDataSomeValuesFrom restriction
     *
     * @param property The property that the restriction acts along
     * @param dataRange The data range that is the filler
     * @return An OWLDataSomeValuesFrom restriction that acts along the specified property and has the specified filler
     */
    OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property);


    OWLDataExactCardinality getOWLDataExactCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property);


    OWLDataMaxCardinality getOWLDataMaxCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property);


    OWLDataMinCardinality getOWLDataMinCardinality(int cardinality, OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property, OWLLiteral value);


    OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand);


    OWLObjectOneOf getOWLObjectOneOf(Set<? extends OWLIndividual> values);


    OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals);

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Object restrictions
    //
    ////////////////////////////////////////////////////////////////////////////////////


    OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression classExpression);


    /**
     * Gets an OWLObjectSomeValuesFrom restriction
     *
     * @param property The object property that the restriction acts along
     * @param classExpression The class expression that is the filler
     * @return An OWLObjectSomeValuesFrom restriction along the specified property with the specified filler
     */
    OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression classExpression);


    OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property);


    OWLObjectExactCardinality getOWLObjectExactCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression);


    OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property);


    OWLObjectMinCardinality getOWLObjectMinCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression);


    OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property);


    OWLObjectMaxCardinality getOWLObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression property, OWLClassExpression classExpression);


    OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property);


    OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property, OWLIndividual individual);


    OWLObjectUnionOf getOWLObjectUnionOf(Set<? extends OWLClassExpression> operands);


    OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands);

    /****************************************************************************************************************/
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////
    //////
    //////    Axioms
    //////
    //////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets a declaration for an entity
     *
     * @param owlEntity The declared entity.
     * @return The declaration axiom for the specified entity.
     *
     * @throws NullPointerException if owlEntity is <code>null</code>
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity);


    /**
     * Gets a declaration with zero or more annotations for an entity
     *
     * @param owlEntity The declared entity
     * @param annotations A possibly empty set of annotations
     * @return The declaration axiom for the specified entity which is annotated with the specified annotations
     *
     * @throws NullPointerException if owlEntity or annotations is <code>null</code>
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Class Axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass);


    OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression... classExpressions);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions);


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression... classExpressions);


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass, Set<? extends OWLClassExpression> classExpressions);


    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass, Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Object property axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty);


    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty);


    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties, Set<? extends OWLAnnotation> annotations);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression... properties);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA, OWLObjectPropertyExpression propertyB, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties);


    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression... properties);


    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty);


    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty, OWLObjectPropertyExpression inverseProperty, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property, OWLClassExpression classExpression);


    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property, OWLClassExpression classExpression, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property, OWLClassExpression range);


    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property, OWLClassExpression range, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression);


    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Data property axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty);


    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties, Set<? extends OWLAnnotation> annotations);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression... properties);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA, OWLDataPropertyExpression propertyB, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression... dataProperties);


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties);


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLClassExpression domain);


    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLClassExpression domain, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery, OWLDataRange owlDataRange);


    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery, OWLDataRange owlDataRange, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property);


    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Data axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, Set<? extends OWLPropertyExpression> properties);


    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, OWLPropertyExpression... properties);


    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce, Set<? extends OWLPropertyExpression> objectProperties, Set<? extends OWLAnnotation> annotations);

    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype, OWLDataRange dataRange);

    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype, OWLDataRange dataRange, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Assertion (Individual) axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals);


    OWLSameIndividualAxiom getOWLSameIndividualAxiom(OWLIndividual... individual);


    OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals);


    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual... individuals);


    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual);


    OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object);


    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual individual, OWLIndividual object, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object);


    OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLObjectPropertyExpression property, OWLIndividual subject, OWLIndividual object, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object, Set<? extends OWLAnnotation> annotations);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, int value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, double value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, float value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, boolean value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, String value);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object);


    OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLDataPropertyExpression property, OWLIndividual subject, OWLLiteral object, Set<? extends OWLAnnotation> annotations);

    /****************************************************************************************************************/
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////
    //////
    //////    Annotations
    //////
    //////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets an annotation
     *
     * @param property the annotation property
     * @param value The annotation value
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value);


    /**
     * Gets an annotation
     *
     * @param property the annotation property
     * @param value The annotation value
     * @param annotations Annotations on the annotation
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations);


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Annotation axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value);


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject, OWLAnnotation annotation);


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationProperty property, OWLAnnotationSubject subject, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations);


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject, OWLAnnotation annotation, Set<? extends OWLAnnotation> annotations);


    /**
     * Gets an annotation assertion that specifies that an IRI is deprecated.  The annotation property is
     * owl:deprecated and the value of the annotation is <code>"true"^^xsd:boolean</code>.  (See
     * <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Properties">Annotation Properties</a> in
     * the OWL 2 Specification
     *
     * @param subject The IRI to be deprecated.
     * @return The annotation assertion that deprecates the specified IRI.
     */
    OWLAnnotationAssertionAxiom getDeprecatedOWLAnnotationAssertionAxiom(IRI subject);


    OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI);


    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop, IRI domain);


    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop, IRI domain, Set<? extends OWLAnnotation> annotations);


    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop, IRI range);


    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop, IRI range, Set<? extends OWLAnnotation> annotations);

    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub, OWLAnnotationProperty sup);

    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub, OWLAnnotationProperty sup, Set<? extends OWLAnnotation> annotations);
}

