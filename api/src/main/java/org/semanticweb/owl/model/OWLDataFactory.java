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
 * An interface for creating entities, descriptions and axioms.
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


    /**
     * Gets an OWL class that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     *
     * @param curie            The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     *                         and is used to expand the specified compact URI (CURIE).
     * @return An OWL class that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:Cat" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "Cat" to obtain the full URI
     *         which would be the URI of the OWL class obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     *                             in the specified namespace manager.
     */
    OWLClass getOWLClass(String curie, NamespaceManager namespaceManager);


    /**
     * Gets an OWL object property that has the specified URI
     *
     * @param uri The URI of the object property to be obtained
     * @return The object representing the object property that has the specified URI
     */
    OWLObjectProperty getOWLObjectProperty(URI uri);


    /**
     * Gets an OWL object property that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     *
     * @param curie            The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     *                         and is used to expand the specified compact URI (CURIE).
     * @return An OWL object property that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:pet" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "pet" to obtain the full URI
     *         which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     *                             in the specified namespace manager.
     */
    OWLObjectProperty getOWLObjectProperty(String curie, NamespaceManager namespaceManager);


    /**
     * Gets an OWL data property that has the specified URI
     *
     * @param uri The URI of the data property to be obtained
     * @return The object representing the data property that has the specified URI
     */
    OWLDataProperty getOWLDataProperty(URI uri);


    /**
     * Gets an OWL data property that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     *
     * @param curie            The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     *                         and is used to expand the specified compact URI (CURIE).
     * @return An OWL data property that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:age" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "age" to obtain the full URI
     *         which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     *                             in the specified namespace manager.
     */
    OWLDataProperty getOWLDataProperty(String curie, NamespaceManager namespaceManager);


    /**
     * Gets an OWL individual that has the specified URI
     *
     * @param uri The URI of the individual to be obtained
     * @return The object representing the individual that has the specified URI
     */
    OWLNamedIndividual getOWLIndividual(URI uri);


    /**
     * Gets an OWL individual that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     *
     * @param curie            The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     *                         and is used to expand the specified compact URI (CURIE).
     * @return An OWL individual that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:person" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "person" to obtain the full URI
     *         which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     *                             in the specified namespace manager.
     */
    OWLIndividual getOWLIndividual(String curie, NamespaceManager namespaceManager);


    OWLAnonymousIndividual getOWLAnonymousIndividual(String id);

    OWLObjectPropertyInverse getOWLObjectPropertyInverse(OWLObjectPropertyExpression property);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Literals
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////


    OWLTypedLiteral getTypedLiteral(String literal, OWLDatatype datatype);


    /**
     * Convenience method that obtains a literal typed as an integer.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the integer, and whose data type is xsd:integer.
     */
    OWLTypedLiteral getTypedLiteral(int value);


    /**
     * Convenience method that obtains a literal typed as a double.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the double, and whose data type is xsd:double.
     */
    OWLTypedLiteral getTypedLiteral(double value);


    /**
     * Convenience method that obtains a literal typed as a boolean.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the boolean, and whose data type is xsd:boolean.
     */
    OWLTypedLiteral getTypedLiteral(boolean value);


    /**
     * Convenience method that obtains a literal typed as a float.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the float, and whose data type is xsd:float.
     */
    OWLTypedLiteral getTypedLiteral(float value);

    /**
     * Convenience method that obtains a literal typed as a string.
     *
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the string, and whose data type is xsd:string.
     */
    OWLTypedLiteral getTypedLiteral(String value);


    /**
     * Gets an OWLRDFTextLiteral.  That is, a string with a language tag
     *
     * @param literal The string literal
     * @param lang    The language.  Must not be <code>null</code>
     * @return The OWLRDFTextLiteral that represent the string with a language tag
     */
    OWLRDFTextLiteral getRDFTextLiteral(String literal, String lang);

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
    OWLDatatype getDatatype(URI uri);

    /**
     * A convenience method that obtains the datatype that represents integers.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#integer&gt;
     *
     * @return An object representing an integer datatype.
     */
    OWLDatatype getIntegerDatatype();


    /**
     * A convenience method that obtains the datatype that represents floats.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#float&gt;
     *
     * @return An object representing the float datatype.
     */
    OWLDatatype getFloatDatatype();


    /**
     * A convenience method that obtains the datatype that represents doubles.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#double&gt;
     *
     * @return An object representing a double datatype.
     */
    OWLDatatype getDoubleDatatype();


    /**
     * A convenience method that obtains the datatype that represents booleans.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#boolean&gt;
     *
     * @return An object representing the boolean datatype.
     */
    OWLDatatype getBooleanDatatype();

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
    OWLDataOneOf getDataOneOf(Set<? extends OWLLiteral> values);


    /**
     * Gets an OWLDataOneOf  <a href="http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals">(see spec)</a>
     *
     * @param values The set of values that the data one of should contain
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getDataOneOf(OWLLiteral... values);


    /**
     * Gets an OWLDataComplementOf <a href="http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Complement_of_Data_Range">(see spec)</a>
     *
     * @param dataRange The datarange to be complemented
     * @return An OWLDataComplementOf of the specified data range
     */
    OWLDataComplementOf getDataComplementOf(OWLDataRange dataRange);


    OWLDatatypeRestriction getDatatypeRestriction(OWLDatatype dataRange,
                                                  Set<OWLFacetRestriction> facetRestrictions);


    OWLDatatypeRestriction getDatatypeRestriction(OWLDatatype dataRange,
                                                  OWLFacet facet,
                                                  OWLLiteral typedliteral);

    OWLDatatypeRestriction getDatatypeRestriction(OWLDatatype dataRange,
                                                  OWLFacetRestriction... facetRestrictions);


    OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                            OWLLiteral facetValue);

    OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                            int facetValue);

    OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                            double facetValue);

    OWLFacetRestriction getFacetRestriction(OWLFacet facet,
                                            float facetValue);

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Class Expressions
    //
    ////////////////////////////////////////////////////////////////////////////////////


    OWLObjectIntersectionOf getObjectIntersectionOf(Set<? extends OWLClassExpression> operands);


    OWLObjectIntersectionOf getObjectIntersectionOf(OWLClassExpression... operands);

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
    OWLDataSomeValuesFrom getDataSomeValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataAllValuesFrom getDataAllValuesFrom(OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataExactCardinality getDataExactCardinality(OWLDataPropertyExpression property,
                                                    int cardinality);


    OWLDataExactCardinality getDataExactCardinality(OWLDataPropertyExpression property,
                                                    int cardinality, OWLDataRange dataRange);


    OWLDataMaxCardinality getDataMaxCardinality(OWLDataPropertyExpression property,
                                                int cardinality);


    OWLDataMaxCardinality getDataMaxCardinality(OWLDataPropertyExpression property,
                                                int cardinality, OWLDataRange dataRange);


    OWLDataMinCardinality getDataMinCardinality(OWLDataPropertyExpression property,
                                                int cardinality);


    OWLDataMinCardinality getDataMinCardinality(OWLDataPropertyExpression property,
                                                int cardinality, OWLDataRange dataRange);


    OWLDataValueRestriction getDataHasValue(OWLDataPropertyExpression property, OWLLiteral value);


    OWLObjectComplementOf getObjectComplementOf(OWLClassExpression operand);


    OWLObjectOneOf getObjectOneOf(Set<OWLIndividual> values);


    OWLObjectOneOf getObjectOneOf(OWLIndividual... individuals);

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Object restrictions
    //
    ////////////////////////////////////////////////////////////////////////////////////

    OWLObjectAllValuesFrom getObjectAllValuesFrom(OWLObjectPropertyExpression property,
                                                  OWLClassExpression classExpression);


    /**
     * Gets an OWLObjectSomeValuesFrom restriction
     *
     * @param property        The object property that the restriction acts along
     * @param classExpression The class expression that is the filler
     * @return An OWLObjectSomeValuesFrom restriction along the specified property with the specified filler
     */
    OWLObjectSomeValuesFrom getObjectSomeValuesFrom(OWLObjectPropertyExpression property,
                                                    OWLClassExpression classExpression);


    OWLObjectExactCardinality getObjectExactCardinality(OWLObjectPropertyExpression property,
                                                        int cardinality);


    OWLObjectExactCardinality getObjectExactCardinality(OWLObjectPropertyExpression property,
                                                        int cardinality,
                                                        OWLClassExpression classExpression);


    OWLObjectMinCardinality getObjectMinCardinality(OWLObjectPropertyExpression property,
                                                    int cardinality);


    OWLObjectMinCardinality getObjectMinCardinality(OWLObjectPropertyExpression property,
                                                    int cardinality,
                                                    OWLClassExpression classExpression);


    OWLObjectMaxCardinality getObjectMaxCardinality(OWLObjectPropertyExpression property,
                                                    int cardinality);


    OWLObjectMaxCardinality getObjectMaxCardinality(OWLObjectPropertyExpression property,
                                                    int cardinality,
                                                    OWLClassExpression classExpression);


    OWLObjectHasSelf getObjectHasSelf(OWLObjectPropertyExpression property);


    OWLObjectHasValue getObjectHasValue(OWLObjectPropertyExpression property,
                                        OWLIndividual individual);


    OWLObjectUnionOf getObjectUnionOf(Set<? extends OWLClassExpression> operands);


    OWLObjectUnionOf getObjectUnionOf(OWLClassExpression... operands);

    /////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axioms
    //
    /////////////////////////////////////////////////////////////////////////////////////////////

    OWLDeclaration getDeclaration(OWLEntity owlEntity, OWLAnnotation... annotations);


    OWLAsymmetricObjectPropertyAxiom getAsymmetricObjectProperty(OWLObjectPropertyExpression propertyExpression, OWLAnnotation... anntations);


    OWLDataPropertyDomainAxiom getDataPropertyDomain(OWLDataPropertyExpression property, OWLClassExpression domain, OWLAnnotation... annotations);


    OWLDataPropertyRangeAxiom getDataPropertyRange(OWLDataPropertyExpression propery, OWLDataRange owlDataRange, OWLAnnotation... annotations);


    OWLSubDataPropertyOfAxiom getSubDataPropertyOf(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty, OWLAnnotation... annotations);


    OWLDifferentIndividualsAxiom getDifferentIndividuals(Set<? extends OWLIndividual> individuals, OWLAnnotation... annotations);

    OWLDifferentIndividualsAxiom getDifferentIndividuals(OWLIndividual... individuals);


    OWLDisjointClassesAxiom getDisjointClasses(Set<? extends OWLClassExpression> descriptions, OWLAnnotation... annotations);

    OWLDisjointClassesAxiom getDisjointClasses(OWLClassExpression... classExpressions);


    OWLDisjointDataPropertiesAxiom getDisjointDataProperties(Set<? extends OWLDataPropertyExpression> properties, OWLAnnotation... annotations);

    OWLDisjointDataPropertiesAxiom getDisjointDataProperties(OWLDataPropertyExpression... dataProperties);


    OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(Set<? extends OWLObjectPropertyExpression> properties, OWLAnnotation... annotations);

    OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(OWLObjectPropertyExpression... properties);

    OWLDisjointUnionAxiom getDisjointUnion(OWLClass owlClass, Set<? extends OWLClassExpression> descriptions, OWLAnnotation... annotations);


    OWLEquivalentClassesAxiom getEquivalentClasses(Set<? extends OWLClassExpression> descriptions, OWLAnnotation... annotations);

    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression clsA, OWLClassExpression clsB, OWLAnnotation... annotations);


    OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(Set<? extends OWLDataPropertyExpression> properties, OWLAnnotation... annotations);

    OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression... properties);


    OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(Set<? extends OWLObjectPropertyExpression> properties, OWLAnnotation... annotations);

    OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression... properties);


    OWLFunctionalDataPropertyAxiom getFunctionalDataProperty(OWLDataPropertyExpression property, OWLAnnotation... annotations);


    OWLFunctionalObjectPropertyAxiom getFunctionalObjectProperty(OWLObjectPropertyExpression property, OWLAnnotation... annotations);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           OWLLiteral object,
                                                           OWLAnnotation... annotations);

    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           int value,
                                                           OWLAnnotation... annotations);

    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           double value,
                                                           OWLAnnotation... annotations);

    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           float value,
                                                           OWLAnnotation... annotations);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           boolean value,
                                                           OWLAnnotation... annotations);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           String value,
                                                           OWLAnnotation... annotations);

    OWLNegativeDataPropertyAssertionAxiom getNegativeDataPropertyAssertion(OWLIndividual subject,
                                                                           OWLDataPropertyExpression property,
                                                                           OWLLiteral object,
                                                                           OWLAnnotation... annotations);


    OWLNegativeObjectPropertyAssertionAxiom getNegativeObjectPropertyAssertion(OWLIndividual subject,
                                                                               OWLObjectPropertyExpression property,
                                                                               OWLIndividual object,
                                                                               OWLAnnotation... annotations);


    OWLObjectPropertyAssertionAxiom getObjectPropertyAssertion(OWLIndividual individual,
                                                               OWLObjectPropertyExpression property,
                                                               OWLIndividual object,
                                                               OWLAnnotation... annotations);


    OWLClassAssertionAxiom getClassAssertion(OWLIndividual individual, OWLClassExpression classExpression,
                                             OWLAnnotation... annotations);

    OWLInverseFunctionalObjectPropertyAxiom getInverseFunctionalObjectProperty(
            OWLObjectPropertyExpression property,
            OWLAnnotation... annotations);


    OWLIrreflexiveObjectPropertyAxiom getIrreflexiveObjectProperty(OWLObjectPropertyExpression property,
                                                                   OWLAnnotation... annotations);


    OWLObjectPropertyDomainAxiom getObjectPropertyDomain(OWLObjectPropertyExpression property,
                                                         OWLClassExpression classExpression,
                                                         OWLAnnotation... annotations);


    OWLObjectPropertyRangeAxiom getObjectPropertyRange(OWLObjectPropertyExpression property,
                                                       OWLClassExpression range,
                                                       OWLAnnotation... annotations);


    OWLSubObjectPropertyOfAxiom getSubObjectPropertyOf(OWLObjectPropertyExpression subProperty,
                                                       OWLObjectPropertyExpression superProperty,
                                                       OWLAnnotation... annotations);


    OWLReflexiveObjectPropertyAxiom getReflexiveObjectProperty(OWLObjectPropertyExpression property,
                                                               OWLAnnotation... annotations);


    OWLSameIndividualsAxiom getSameIndividuals(Set<OWLIndividual> individuals,
                                               OWLAnnotation... annotations);


    OWLSubClassOfAxiom getSubClassOf(OWLClassExpression subClass, OWLClassExpression superClass,
                                     OWLAnnotation... annotations);

    OWLSubClassOfAxiom getSubClassOf(String subClass, String superClass, NamespaceManager namespaceManager,
                                     OWLAnnotation... annotations);

    OWLSymmetricObjectPropertyAxiom getSymmetricObjectProperty(OWLObjectPropertyExpression property,
                                                               OWLAnnotation... annotations);


    OWLTransitiveObjectPropertyAxiom getTransitiveObjectProperty(OWLObjectPropertyExpression property,
                                                                 OWLAnnotation... annotations);


    OWLComplextSubPropertyAxiom getObjectPropertyChainSubProperty(
            List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty,
            OWLAnnotation... annotations);


    OWLInverseObjectPropertiesAxiom getInverseObjectProperties(OWLObjectPropertyExpression forwardProperty,
                                                               OWLObjectPropertyExpression inverseProperty,
                                                               OWLAnnotation... annotations);

    OWLHasKeyAxiom getHasKey(OWLClassExpression ce, Set<? extends OWLObjectPropertyExpression> objectProperties, Set<? extends OWLDataPropertyExpression> dataProperties,
                             OWLAnnotation... annotations);

    OWLHasKeyAxiom getHasKey(OWLClassExpression ce, OWLObjectPropertyExpression... properties);

    OWLHasKeyAxiom getHasKey(OWLClassExpression ce, OWLDataPropertyExpression... properties);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotations

    OWLAnnotationProperty getAnnotationProperty(URI uri);

    OWLAnnotation getAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value, OWLAnnotation... annotations);

    OWLAnnotation getAnnotation(URI property, OWLAnnotationValue value, OWLAnnotation... annotations);

    OWLAnnotation getAnnotation(OWLAnnotationProperty property, String literal, String lang, OWLAnnotation... annotations);

    OWLAnnotation getAnnotation(URI property, String literal, String lang, OWLAnnotation... annotations);

    OWLAnnotation getAnnotation(OWLAnnotationProperty property, URI uri, OWLAnnotation... annotations);

    OWLAnnotation getAnnotation(URI property, URI uri, OWLAnnotation... annotations);


    OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, OWLAnnotation annotation);

    OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, OWLAnnotationProperty property, String literal, String lang);

    OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, URI propertyURI, String literal, String lang);

    OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, OWLAnnotationProperty property, OWLLiteral literal);

    OWLAnnotationAssertionAxiom getAnnotationAssertion(URI subject, URI propertyURI, OWLLiteral literal);


    OWLImportsDeclaration getImportsDeclaration(OWLOntology subject, URI importedOntologyURI);


    OWLDataUnionOf getDataUnionOf(Set<? extends OWLDataRange> dataRanges);

    OWLDataUnionOf getDataUnionOf(OWLDataRange... dataRanges);

    OWLDataIntersectionOf getDataIntersectionOf(Set<? extends OWLDataRange> dataRanges);

    OWLDataIntersectionOf getDataIntersectionOf(OWLDataRange... dataRanges);

    OWLAnnotationPropertyDomain getAnnotationPropertyDomain(OWLAnnotationProperty prop, IRI domain);

    OWLAnnotationPropertyRange getAnnotationPropertyRange(OWLAnnotationProperty prop, IRI range);

    OWLSubAnnotationPropertyOf getSubAnnotationPropertyOf(OWLAnnotationProperty sub, OWLAnnotationProperty sup);
}

