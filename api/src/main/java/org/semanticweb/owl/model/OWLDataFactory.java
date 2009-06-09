package org.semanticweb.owl.model;


import org.semanticweb.owl.vocab.OWLFacet;

import java.net.URI;
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


    /**
     * Gets an IRI for a given URI.  Note that this method is pass through - if the specified URI is <code>null</code>
     * then the value returned will be <code>null</code>
     *
     * @param uri The URI (may be <code>null</code>)
     * @return An IRI representing the specified URI, or <code>null</code> if the specified URI
     *         was <code>null</code>
     */
    IRI getIRI(URI uri);

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
     * Gets an OWL class that has the specified URI
     *
     * @param uri The URI of the class
     * @return The object representing the class that has the specified URI
     */
    OWLClass getOWLClass(URI uri);

    OWLClass getOWLClass(IRI iri);

    /**
     * Gets an OWL class that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix mapping.
     *
     * @param curie         The compact URI.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefixes,
     *                      and is used to expand the specified compact URI (CURIE).
     * @return An OWL class that has the URI obtained by expanding the specified CURIE using the specified prefix
     *         manager.  For example, suppose "m:Cat" was specified the CURIE, the prefix manager would be used to obtain
     *         the prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full URI
     *         which would be the URI of the OWL class obtained by this method.
     * @throws OWLRuntimeException if the prefix name in the specified CURIE does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLClass getOWLClass(String curie,
                         PrefixManager prefixManager);


    /**
     * Gets an OWL object property that has the specified URI
     *
     * @param uri The URI of the object property to be obtained
     * @return The object representing the object property that has the specified URI
     */
    OWLObjectProperty getOWLObjectProperty(URI uri);


    /**
     * Gets an OWL object property that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix mapping.
     *
     * @param curie         The compact URI.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefixes,
     *                      and is used to expand the specified compact URI (CURIE).
     * @return An OWL object property that has the URI obtained by expanding the specified CURIE using the specified prefix
     *         manager.  For example, suppose "m:Cat" was specified the CURIE, the prefix manager would be used to obtain
     *         the prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full URI
     *         which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the prefix name in the specified CURIE does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLObjectProperty getOWLObjectProperty(String curie,
                                           PrefixManager prefixManager);

    OWLObjectProperty getOWLObjectProperty(IRI iri);

    OWLObjectPropertyInverse getOWLObjectPropertyInverse(OWLObjectPropertyExpression property);


    /**
     * Gets an OWL data property that has the specified URI
     *
     * @param uri The URI of the data property to be obtained
     * @return The object representing the data property that has the specified URI
     */
    OWLDataProperty getOWLDataProperty(URI uri);

    OWLDataProperty getOWLDataProperty(IRI iri);


    /**
     * Gets an OWL data property that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix mapping.
     *
     * @param curie         The compact URI.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefixes,
     *                      and is used to expand the specified compact URI (CURIE).
     * @return An OWL data property that has the URI obtained by expanding the specified CURIE using the specified prefix
     *         manager.  For example, suppose "m:Cat" was specified the CURIE, the prefix manager would be used to obtain
     *         the prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full URI
     *         which would be the URI of the OWL data property obtained by this method.
     * @throws OWLRuntimeException if the prefix name in the specified CURIE does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLDataProperty getOWLDataProperty(String curie,
                                       PrefixManager prefixManager);


    /**
     * Gets an OWL individual that has the specified URI
     *
     * @param uri The URI of the individual to be obtained
     * @return The object representing the individual that has the specified URI
     */
    OWLNamedIndividual getOWLNamedIndividual(URI uri);

    OWLNamedIndividual getOWLNamedIndividual(IRI iri);

    /**
     * Gets an OWL named individual that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix mapping.
     *
     * @param curie         The compact URI.
     * @param prefixManager The prefix manager that is responsible for mapping prefix names to prefixes,
     *                      and is used to expand the specified compact URI (CURIE).
     * @return An OWL named individual that has the URI obtained by expanding the specified CURIE using the specified prefix
     *         manager.  For example, suppose "m:Cat" was specified the CURIE, the prefix manager would be used to obtain
     *         the prefix for the "m:" prefix name, this prefix would then be concatenated with "Cat" to obtain the full URI
     *         which would be the URI of the OWL named individual obtained by this method.
     * @throws OWLRuntimeException if the prefix name in the specified CURIE does not have a mapping to a prefix
     *                             in the specified prefix manager.
     */
    OWLNamedIndividual getOWLNamedIndividual(String curie,
                                             PrefixManager prefixManager);


    /**
     * Gets an anonymous individual that has a specific ID.
     * @param id The node ID.  Note that the ID will be preixed with _: if it is not specifed with
     * _: as a prefix
     * @return An anonymous individual.
     */
    OWLAnonymousIndividual getOWLAnonymousIndividual(String id);

    /**
     * Gets an anonymous individual.  The node ID for the individual will be generated automatically
     * @return The anonymous individual
     */
    OWLAnonymousIndividual getOWLAnonymousIndividual();

    OWLAnnotationProperty getOWLAnnotationProperty(URI uri);

    OWLAnnotationProperty getOWLAnnotationProperty(IRI iri);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Literals
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////


    OWLTypedLiteral getOWLTypedLiteral(String literal,
                                       OWLDatatype datatype);


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
     * Gets an OWLStringLiteral.  That is, a string possibly with a language tag
     *
     * @param literal The quoted string
     * @param lang    The language.
     * @return The OWLStringLiteral that represent the string possibly with a language tag
     */
    OWLStringLiteral getOWLStringLiteral(String literal,
                                         String lang);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Datatypes (Named data ranges)
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets an OWL datatype that has the specified URI
     *
     * @param uri The URI of the datatype to be obtained
     * @return The object representing the datatype that has the specified URI
     */
    OWLDatatype getOWLDatatype(URI uri);

    OWLDatatype getOWLDatatype(IRI iri);

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


    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange,
                                                     Set<OWLFacetRestriction> facetRestrictions);


    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange,
                                                     OWLFacet facet,
                                                     OWLLiteral typedliteral);


    OWLDatatypeRestriction getOWLDatatypeRestriction(OWLDatatype dataRange,
                                                     OWLFacetRestriction... facetRestrictions);


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                               OWLLiteral facetValue);


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                               int facetValue);


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                               double facetValue);


    OWLFacetRestriction getOWLFacetRestriction(OWLFacet facet,
                                               float facetValue);


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
     * @param property  The property that the restriction acts along
     * @param dataRange The data range that is the filler
     * @return An OWLDataSomeValuesFrom restriction that acts along the specified property and has the specified filler
     */
    OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property,
                                                   OWLDataRange dataRange);


    OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property,
                                                 OWLDataRange dataRange);


    OWLDataExactCardinality getOWLDataExactCardinality(OWLDataPropertyExpression property,
                                                       int cardinality);


    OWLDataExactCardinality getOWLDataExactCardinality(OWLDataPropertyExpression property,
                                                       int cardinality,
                                                       OWLDataRange dataRange);


    OWLDataMaxCardinality getOWLDataMaxCardinality(OWLDataPropertyExpression property,
                                                   int cardinality);


    OWLDataMaxCardinality getOWLDataMaxCardinality(OWLDataPropertyExpression property,
                                                   int cardinality,
                                                   OWLDataRange dataRange);


    OWLDataMinCardinality getOWLDataMinCardinality(OWLDataPropertyExpression property,
                                                   int cardinality);


    OWLDataMinCardinality getOWLDataMinCardinality(OWLDataPropertyExpression property,
                                                   int cardinality,
                                                   OWLDataRange dataRange);


    OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property,
                                       OWLLiteral value);


    OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression operand);


    OWLObjectOneOf getOWLObjectOneOf(Set<? extends OWLIndividual> values);


    OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals);

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Object restrictions
    //
    ////////////////////////////////////////////////////////////////////////////////////


    OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property,
                                                     OWLClassExpression classExpression);


    /**
     * Gets an OWLObjectSomeValuesFrom restriction
     *
     * @param property        The object property that the restriction acts along
     * @param classExpression The class expression that is the filler
     * @return An OWLObjectSomeValuesFrom restriction along the specified property with the specified filler
     */
    OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property,
                                                       OWLClassExpression classExpression);


    OWLObjectExactCardinality getOWLObjectExactCardinality(OWLObjectPropertyExpression property,
                                                           int cardinality);


    OWLObjectExactCardinality getOWLObjectExactCardinality(OWLObjectPropertyExpression property,
                                                           int cardinality,
                                                           OWLClassExpression classExpression);


    OWLObjectMinCardinality getOWLObjectMinCardinality(OWLObjectPropertyExpression property,
                                                       int cardinality);


    OWLObjectMinCardinality getOWLObjectMinCardinality(OWLObjectPropertyExpression property,
                                                       int cardinality,
                                                       OWLClassExpression classExpression);


    OWLObjectMaxCardinality getOWLObjectMaxCardinality(OWLObjectPropertyExpression property,
                                                       int cardinality);


    OWLObjectMaxCardinality getOWLObjectMaxCardinality(OWLObjectPropertyExpression property,
                                                       int cardinality,
                                                       OWLClassExpression classExpression);


    OWLObjectHasSelf getOWLObjectHasSelf(OWLObjectPropertyExpression property);


    OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property,
                                           OWLIndividual individual);


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
     * @throws NullPointerException if owlEntity is <code>null</code>
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity);


    /**
     * Gets a declaration with zero or more annotations for an entity
     *
     * @param owlEntity   The declared entity
     * @param annotations A possibly empty set of annotations
     * @return The declaration axiom for the specified entity which is annotated with the specified annotations
     * @throws NullPointerException if owlEntity or annotations is <code>null</code>
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity,
                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Class Axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
                                             OWLClassExpression superClass);


    OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass,
                                             OWLClassExpression superClass,
                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions,
                                                           Set<? extends OWLAnnotation> annotations);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression... classExpressions);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA,
                                                           OWLClassExpression clsB);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA,
                                                           OWLClassExpression clsB,
                                                           Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions);


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression... classExpressions);


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLClassExpression> classExpressions,
                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
                                                   Set<? extends OWLClassExpression> classExpressions);


    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass,
                                                   Set<? extends OWLClassExpression> classExpressions,
                                                   Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Object property axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty,
                                                               OWLObjectPropertyExpression superProperty);


    OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty,
                                                               OWLObjectPropertyExpression superProperty,
                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain,
                                                             OWLObjectPropertyExpression superProperty);


    OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom(List<? extends OWLObjectPropertyExpression> chain,
                                                             OWLObjectPropertyExpression superProperty,
                                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties,
                                                                             Set<? extends OWLAnnotation> annotations);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression... properties);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA,
                                                                             OWLObjectPropertyExpression propertyB);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression propertyA,
                                                                             OWLObjectPropertyExpression propertyB,
                                                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties);


    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression... properties);


    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<? extends OWLObjectPropertyExpression> properties,
                                                                         Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty,
                                                                       OWLObjectPropertyExpression inverseProperty);


    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty,
                                                                       OWLObjectPropertyExpression inverseProperty,
                                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
                                                                 OWLClassExpression classExpression);


    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
                                                                 OWLClassExpression classExpression,
                                                                 Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
                                                               OWLClassExpression range);


    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
                                                               OWLClassExpression range,
                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                         Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                           Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression);


    OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression propertyExpression,
                                                                         Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property,
                                                                         Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Data property axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty,
                                                           OWLDataPropertyExpression superProperty);


    OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty,
                                                           OWLDataPropertyExpression superProperty,
                                                           Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties,
                                                                         Set<? extends OWLAnnotation> annotations);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression... properties);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA,
                                                                         OWLDataPropertyExpression propertyB);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression propertyA,
                                                                         OWLDataPropertyExpression propertyB,
                                                                         Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression... dataProperties);


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties);


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<? extends OWLDataPropertyExpression> properties,
                                                                     Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property,
                                                             OWLClassExpression domain);


    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property,
                                                             OWLClassExpression domain,
                                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery,
                                                           OWLDataRange owlDataRange);


    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery,
                                                           OWLDataRange owlDataRange,
                                                           Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property);


    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property,
                                                                     Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Data axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
                                     Set<? extends OWLPropertyExpression> properties);


    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
                                     OWLPropertyExpression... properties);


    OWLHasKeyAxiom getOWLHasKeyAxiom(OWLClassExpression ce,
                                     Set<? extends OWLPropertyExpression> objectProperties,
                                     Set<? extends OWLAnnotation> annotations);

    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype, OWLDataRange dataRange);

    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
                                                             OWLDataRange dataRange,
                                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Assertion (Individual) axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals);


    OWLSameIndividualAxiom getOWLSameIndividualAxiom(OWLIndividual... individual);


    OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<? extends OWLIndividual> individuals,
                                                     Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals);


    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual... individuals);


    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<? extends OWLIndividual> individuals,
                                                                 Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual);


    OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClassExpression classExpression, OWLIndividual individual, Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLIndividual individual,
                                                                       OWLObjectPropertyExpression property,
                                                                       OWLIndividual object);


    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLIndividual individual,
                                                                       OWLObjectPropertyExpression property,
                                                                       OWLIndividual object,
                                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLIndividual subject,
                                                                                       OWLObjectPropertyExpression property,
                                                                                       OWLIndividual object);


    OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLIndividual subject,
                                                                                       OWLObjectPropertyExpression property,
                                                                                       OWLIndividual object,
                                                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   OWLLiteral object);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   OWLLiteral object,
                                                                   Set<? extends OWLAnnotation> annotations);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   int value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   double value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   float value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   boolean value);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   String value);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                                   OWLDataPropertyExpression property,
                                                                                   OWLLiteral object);


    OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                                   OWLDataPropertyExpression property,
                                                                                   OWLLiteral object,
                                                                                   Set<? extends OWLAnnotation> annotations);

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
     * @param value    The annotation value
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
                                   OWLAnnotationValue value);


    /**
     * Gets an annotation
     *
     * @param property    the annotation property
     * @param value       The annotation value
     * @param annotations Annotations on the annotation
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property,
                                   OWLAnnotationValue value,
                                   Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Annotation axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
                                                               OWLAnnotationProperty property,
                                                               OWLAnnotationValue value);


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
                                                               OWLAnnotation annotation);


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
                                                               OWLAnnotationProperty property,
                                                               OWLAnnotationValue value,
                                                               Set<? extends OWLAnnotation> annotations);


    OWLAnnotationAssertionAxiom getOWLAnnotationAssertionAxiom(OWLAnnotationSubject subject,
                                                               OWLAnnotation annotation,
                                                               Set<? extends OWLAnnotation> annotations);


    OWLImportsDeclaration getOWLImportsDeclaration(URI importedOntologyURI);

    OWLImportsDeclaration getOWLImportsDeclaration(IRI importedOntologyIRI);


    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop,
                                                                         IRI domain);


    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop,
                                                                         IRI domain,
                                                                         Set<? extends OWLAnnotation> annotations);


    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop,
                                                                       IRI range);


    OWLAnnotationPropertyRangeAxiom getOWLAnnotationPropertyRangeAxiom(OWLAnnotationProperty prop,
                                                                       IRI range,
                                                                       Set<? extends OWLAnnotation> annotations);

    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub,
                                                                       OWLAnnotationProperty sup);

    OWLSubAnnotationPropertyOfAxiom getOWLSubAnnotationPropertyOfAxiom(OWLAnnotationProperty sub,
                                                                       OWLAnnotationProperty sup,
                                                                       Set<? extends OWLAnnotation> annotations);
}

