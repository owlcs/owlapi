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
     * @return The OWL Class corresponding to owl:Thing
     */
    OWLClass getOWLThing();


    /**
     * Gets the built in owl:Nothing class, which has a URI of &lt;http://www.w3.org/2002/07/owl#Nothing&gt;
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
     * @return The OWL Datatype corresponding to the top data type.
     */
    OWLDatatype getTopDatatype();


    /**
     * Gets an OWL class that has the specified URI
     * @param uri The URI of the class
     * @return The object representing the class that has the specified URI
     */
    OWLClass getOWLClass(URI uri);


    /**
     * Gets an OWL class that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     * @param curie The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     * and is used to expand the specified compact URI (CURIE).
     * @return An OWL class that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:Cat" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "Cat" to obtain the full URI
     *         which would be the URI of the OWL class obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLClass getOWLClass(String curie,
                         NamespaceManager namespaceManager);


    /**
     * Gets an OWL object property that has the specified URI
     * @param uri The URI of the object property to be obtained
     * @return The object representing the object property that has the specified URI
     */
    OWLObjectProperty getOWLObjectProperty(URI uri);


    /**
     * Gets an OWL object property that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     * @param curie The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     * and is used to expand the specified compact URI (CURIE).
     * @return An OWL object property that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:pet" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "pet" to obtain the full URI
     *         which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLObjectProperty getOWLObjectProperty(String curie,
                                           NamespaceManager namespaceManager);


    OWLObjectPropertyInverse getOWLObjectPropertyInverse(OWLObjectPropertyExpression property);


    /**
     * Gets an OWL data property that has the specified URI
     * @param uri The URI of the data property to be obtained
     * @return The object representing the data property that has the specified URI
     */
    OWLDataProperty getOWLDataProperty(URI uri);


    /**
     * Gets an OWL data property that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     * @param curie The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     * and is used to expand the specified compact URI (CURIE).
     * @return An OWL data property that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:age" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "age" to obtain the full URI
     *         which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLDataProperty getOWLDataProperty(String curie,
                                       NamespaceManager namespaceManager);


    /**
     * Gets an OWL individual that has the specified URI
     * @param uri The URI of the individual to be obtained
     * @return The object representing the individual that has the specified URI
     */
    OWLNamedIndividual getOWLNamedIndividual(URI uri);


    /**
     * Gets an OWL individual that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     * @param curie The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     * and is used to expand the specified compact URI (CURIE).
     * @return An OWL individual that has the URI obtained by expanding the specified CURIE using the specified namespace
     *         manager.  For example, suppose "m:person" was specified the CURIE, the namespaceManager would be used to obtain
     *         the namespace for the "m" prefix, this namespace would then be concatenated with "person" to obtain the full URI
     *         which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLNamedIndividual getOWLNamedIndividual(String curie,
                                             NamespaceManager namespaceManager);


    OWLAnonymousIndividual getAnonymousIndividual(String id);


    OWLAnnotationProperty getOWLAnnotationProperty(URI uri);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Literals
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////


    OWLTypedLiteral getTypedLiteral(String literal,
                                    OWLDatatype datatype);


    /**
     * Convenience method that obtains a literal typed as an integer.
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the integer, and whose data type is xsd:integer.
     */
    OWLTypedLiteral getTypedLiteral(int value);


    /**
     * Convenience method that obtains a literal typed as a double.
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the double, and whose data type is xsd:double.
     */
    OWLTypedLiteral getTypedLiteral(double value);


    /**
     * Convenience method that obtains a literal typed as a boolean.
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the boolean, and whose data type is xsd:boolean.
     */
    OWLTypedLiteral getTypedLiteral(boolean value);


    /**
     * Convenience method that obtains a literal typed as a float.
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the float, and whose data type is xsd:float.
     */
    OWLTypedLiteral getTypedLiteral(float value);


    /**
     * Convenience method that obtains a literal typed as a string.
     * @param value The value of the literal
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     *         value of the string, and whose data type is xsd:string.
     */
    OWLTypedLiteral getTypedLiteral(String value);


    /**
     * Gets an OWLRDFTextLiteral.  That is, a string with a language tag
     * @param literal The string literal
     * @param lang The language.  Must not be <code>null</code>
     * @return The OWLRDFTextLiteral that represent the string with a language tag
     */
    OWLRDFTextLiteral getRDFTextLiteral(String literal,
                                        String lang);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Datatypes (Named data ranges)
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets an OWL datatype that has the specified URI
     * @param uri The URI of the datatype to be obtained
     * @return The object representing the datatype that has the specified URI
     */
    OWLDatatype getOWLDatatype(URI uri);


    /**
     * A convenience method that obtains the datatype that represents integers.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#integer&gt;
     * @return An object representing an integer datatype.
     */
    OWLDatatype getIntegerDatatype();


    /**
     * A convenience method that obtains the datatype that represents floats.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#float&gt;
     * @return An object representing the float datatype.
     */
    OWLDatatype getFloatDatatype();


    /**
     * A convenience method that obtains the datatype that represents doubles.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#double&gt;
     * @return An object representing a double datatype.
     */
    OWLDatatype getDoubleDatatype();


    /**
     * A convenience method that obtains the datatype that represents booleans.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#boolean&gt;
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
     * @param values The set of values that the data one of should contain
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values);


    /**
     * Gets an OWLDataOneOf  <a href="http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Enumeration_of_Literals">(see spec)</a>
     * @param values The set of values that the data one of should contain
     * @return A data one of that enumerates the specified set of values
     */
    OWLDataOneOf getOWLDataOneOf(OWLLiteral... values);


    /**
     * Gets an OWLDataComplementOf <a href="http://www.w3.org/TR/2008/WD-owl2-syntax-20081202/#Complement_of_Data_Range">(see spec)</a>
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


    OWLDataUnionOf getDataUnionOf(Set<? extends OWLDataRange> dataRanges);


    OWLDataUnionOf getDataUnionOf(OWLDataRange... dataRanges);


    OWLDataIntersectionOf getDataIntersectionOf(Set<? extends OWLDataRange> dataRanges);


    OWLDataIntersectionOf getDataIntersectionOf(OWLDataRange... dataRanges);

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
     * @param property The property that the restriction acts along
     * @param dataRange The data range that is the filler
     * @return An OWLDataSomeValuesFrom restriction that acts along the specified property and has the specified filler
     */
    OWLDataSomeValuesFrom getDataSomeValuesFrom(OWLDataPropertyExpression property,
                                                OWLDataRange dataRange);


    OWLDataAllValuesFrom getDataAllValuesFrom(OWLDataPropertyExpression property,
                                              OWLDataRange dataRange);


    OWLDataExactCardinality getDataExactCardinality(OWLDataPropertyExpression property,
                                                    int cardinality);


    OWLDataExactCardinality getDataExactCardinality(OWLDataPropertyExpression property,
                                                    int cardinality,
                                                    OWLDataRange dataRange);


    OWLDataMaxCardinality getDataMaxCardinality(OWLDataPropertyExpression property,
                                                int cardinality);


    OWLDataMaxCardinality getDataMaxCardinality(OWLDataPropertyExpression property,
                                                int cardinality,
                                                OWLDataRange dataRange);


    OWLDataMinCardinality getDataMinCardinality(OWLDataPropertyExpression property,
                                                int cardinality);


    OWLDataMinCardinality getDataMinCardinality(OWLDataPropertyExpression property,
                                                int cardinality,
                                                OWLDataRange dataRange);


    OWLDataHasValue getDataHasValue(OWLDataPropertyExpression property,
                                    OWLLiteral value);


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
     * @param property The object property that the restriction acts along
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
     * @param owlEntity The declared entity.
     * @return The declaration axiom for the specified entity.
     * @throws NullPointerException if owlEntity is <code>null</code>
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity);


    /**
     * Gets a declaration with zero or more annotations for an entity
     * @param owlEntity The declared entity
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


    OWLSubClassOfAxiom getSubClassOf(OWLClassExpression subClass,
                                     OWLClassExpression superClass);


    OWLSubClassOfAxiom getSubClassOf(OWLClassExpression subClass,
                                     OWLClassExpression superClass,
                                     Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLClassExpression> classExpressions,
                                                           Set<? extends OWLAnnotation> annotations);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLClassExpression... classExpressions);


    OWLEquivalentClassesAxiom getOWLEquivalentClasses(OWLClassExpression clsA,
                                                      OWLClassExpression clsB);


    OWLEquivalentClassesAxiom getOWLEquivalentClasses(OWLClassExpression clsA,
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


    OWLSubObjectPropertyOfAxiom getSubObjectPropertyOf(OWLObjectPropertyExpression subProperty,
                                                       OWLObjectPropertyExpression superProperty);


    OWLSubObjectPropertyOfAxiom getSubObjectPropertyOf(OWLObjectPropertyExpression subProperty,
                                                       OWLObjectPropertyExpression superProperty,
                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubPropertyChainOfAxiom getSubPropertyChainOf(List<? extends OWLObjectPropertyExpression> chain,
                                                     OWLObjectPropertyExpression superProperty);


    OWLSubPropertyChainOfAxiom getSubPropertyChainOf(List<? extends OWLObjectPropertyExpression> chain,
                                                     OWLObjectPropertyExpression superProperty,
                                                     Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(Set<? extends OWLObjectPropertyExpression> properties);


    OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(Set<? extends OWLObjectPropertyExpression> properties,
                                                                     Set<? extends OWLAnnotation> annotations);


    OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression... properties);


    OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression propertyA,
                                                                     OWLObjectPropertyExpression propertyB);


    OWLEquivalentObjectPropertiesAxiom getEquivalentObjectProperties(OWLObjectPropertyExpression propertyA,
                                                                     OWLObjectPropertyExpression propertyB,
                                                                     Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(Set<? extends OWLObjectPropertyExpression> properties);


    OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(OWLObjectPropertyExpression... properties);


    OWLDisjointObjectPropertiesAxiom getDisjointObjectProperties(Set<? extends OWLObjectPropertyExpression> properties,
                                                                 Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLInverseObjectPropertiesAxiom getInverseObjectProperties(OWLObjectPropertyExpression forwardProperty,
                                                               OWLObjectPropertyExpression inverseProperty);


    OWLInverseObjectPropertiesAxiom getInverseObjectProperties(OWLObjectPropertyExpression forwardProperty,
                                                               OWLObjectPropertyExpression inverseProperty,
                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyDomainAxiom getObjectPropertyDomain(OWLObjectPropertyExpression property,
                                                         OWLClassExpression classExpression);


    OWLObjectPropertyDomainAxiom getObjectPropertyDomain(OWLObjectPropertyExpression property,
                                                         OWLClassExpression classExpression,
                                                         Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyRangeAxiom getObjectPropertyRange(OWLObjectPropertyExpression property,
                                                       OWLClassExpression range);


    OWLObjectPropertyRangeAxiom getObjectPropertyRange(OWLObjectPropertyExpression property,
                                                       OWLClassExpression range,
                                                       Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLFunctionalObjectPropertyAxiom getFunctionalObjectProperty(OWLObjectPropertyExpression property);


    OWLFunctionalObjectPropertyAxiom getFunctionalObjectProperty(OWLObjectPropertyExpression property,
                                                                 Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLInverseFunctionalObjectPropertyAxiom getInverseFunctionalObjectProperty(OWLObjectPropertyExpression property);


    OWLInverseFunctionalObjectPropertyAxiom getInverseFunctionalObjectProperty(OWLObjectPropertyExpression property,
                                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLReflexiveObjectPropertyAxiom getReflexiveObjectProperty(OWLObjectPropertyExpression property);


    OWLReflexiveObjectPropertyAxiom getReflexiveObjectProperty(OWLObjectPropertyExpression property,
                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLIrreflexiveObjectPropertyAxiom getIrreflexiveObjectProperty(OWLObjectPropertyExpression property);


    OWLIrreflexiveObjectPropertyAxiom getIrreflexiveObjectProperty(OWLObjectPropertyExpression property,
                                                                   Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSymmetricObjectPropertyAxiom getSymmetricObjectProperty(OWLObjectPropertyExpression property);


    OWLSymmetricObjectPropertyAxiom getSymmetricObjectProperty(OWLObjectPropertyExpression property,
                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLAsymmetricObjectPropertyAxiom getAsymmetricObjectProperty(OWLObjectPropertyExpression propertyExpression);


    OWLAsymmetricObjectPropertyAxiom getAsymmetricObjectProperty(OWLObjectPropertyExpression propertyExpression,
                                                                 Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLTransitiveObjectPropertyAxiom getTransitiveObjectProperty(OWLObjectPropertyExpression property);


    OWLTransitiveObjectPropertyAxiom getTransitiveObjectProperty(OWLObjectPropertyExpression property,
                                                                 Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Data property axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSubDataPropertyOfAxiom getSubDataPropertyOf(OWLDataPropertyExpression subProperty,
                                                   OWLDataPropertyExpression superProperty);


    OWLSubDataPropertyOfAxiom getSubDataPropertyOf(OWLDataPropertyExpression subProperty,
                                                   OWLDataPropertyExpression superProperty,
                                                   Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(Set<? extends OWLDataPropertyExpression> properties);


    OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(Set<? extends OWLDataPropertyExpression> properties,
                                                                 Set<? extends OWLAnnotation> annotations);


    OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression... properties);


    OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression propertyA,
                                                                 OWLDataPropertyExpression propertyB);


    OWLEquivalentDataPropertiesAxiom getEquivalentDataProperties(OWLDataPropertyExpression propertyA,
                                                                 OWLDataPropertyExpression propertyB,
                                                                 Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDisjointDataPropertiesAxiom getDisjointDataProperties(OWLDataPropertyExpression... dataProperties);


    OWLDisjointDataPropertiesAxiom getDisjointDataProperties(Set<? extends OWLDataPropertyExpression> properties);


    OWLDisjointDataPropertiesAxiom getDisjointDataProperties(Set<? extends OWLDataPropertyExpression> properties,
                                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyDomainAxiom getDataPropertyDomain(OWLDataPropertyExpression property,
                                                     OWLClassExpression domain);


    OWLDataPropertyDomainAxiom getDataPropertyDomain(OWLDataPropertyExpression property,
                                                     OWLClassExpression domain,
                                                     Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyRangeAxiom getDataPropertyRange(OWLDataPropertyExpression propery,
                                                   OWLDataRange owlDataRange);


    OWLDataPropertyRangeAxiom getDataPropertyRange(OWLDataPropertyExpression propery,
                                                   OWLDataRange owlDataRange,
                                                   Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLFunctionalDataPropertyAxiom getFunctionalDataProperty(OWLDataPropertyExpression property);


    OWLFunctionalDataPropertyAxiom getFunctionalDataProperty(OWLDataPropertyExpression property,
                                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Data axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // TODO: Datatype Definitions


    OWLHasKeyAxiom getHasKey(OWLClassExpression ce,
                             Set<? extends OWLPropertyExpression> objectProperties);


    OWLHasKeyAxiom getHasKey(OWLClassExpression ce,
                             OWLPropertyExpression... properties);


    OWLHasKeyAxiom getHasKey(OWLClassExpression ce,
                             Set<? extends OWLPropertyExpression> objectProperties,
                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////    Assertion (Individual) axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLSameIndividualsAxiom getSameIndividuals(Set<? extends OWLIndividual> individuals);


    OWLSameIndividualsAxiom getSameIndividuals(OWLIndividual... individual);


    OWLSameIndividualsAxiom getSameIndividuals(Set<? extends OWLIndividual> individuals,
                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDifferentIndividualsAxiom getDifferentIndividuals(Set<? extends OWLIndividual> individuals);


    OWLDifferentIndividualsAxiom getDifferentIndividuals(OWLIndividual... individuals);


    OWLDifferentIndividualsAxiom getDifferentIndividuals(Set<? extends OWLIndividual> individuals,
                                                         Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLClassAssertionAxiom getClassAssertion(OWLIndividual individual,
                                             OWLClassExpression classExpression);


    OWLClassAssertionAxiom getClassAssertion(OWLIndividual individual,
                                             OWLClassExpression classExpression,
                                             Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLObjectPropertyAssertionAxiom getObjectPropertyAssertion(OWLIndividual individual,
                                                               OWLObjectPropertyExpression property,
                                                               OWLIndividual object);


    OWLObjectPropertyAssertionAxiom getObjectPropertyAssertion(OWLIndividual individual,
                                                               OWLObjectPropertyExpression property,
                                                               OWLIndividual object,
                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLNegativeObjectPropertyAssertionAxiom getNegativeObjectPropertyAssertion(OWLIndividual subject,
                                                                               OWLObjectPropertyExpression property,
                                                                               OWLIndividual object);


    OWLNegativeObjectPropertyAssertionAxiom getNegativeObjectPropertyAssertion(OWLIndividual subject,
                                                                               OWLObjectPropertyExpression property,
                                                                               OWLIndividual object,
                                                                               Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           OWLLiteral object);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           OWLLiteral object,
                                                           Set<? extends OWLAnnotation> annotations);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           int value);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           double value);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           float value);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           boolean value);


    OWLDataPropertyAssertionAxiom getDataPropertyAssertion(OWLIndividual subject,
                                                           OWLDataPropertyExpression property,
                                                           String value);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLNegativeDataPropertyAssertionAxiom getNegativeDataPropertyAssertion(OWLIndividual subject,
                                                                           OWLDataPropertyExpression property,
                                                                           OWLLiteral object);


    OWLNegativeDataPropertyAssertionAxiom getNegativeDataPropertyAssertion(OWLIndividual subject,
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
     * @param property the annotation property
     * @param value The annotation value
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getAnnotation(OWLAnnotationProperty property,
                                OWLAnnotationValue value);


    /**
     * Gets an annotation
     * @param property the annotation property
     * @param value The annotation value
     * @param annotations Annotations on the annotation
     * @return The annotation on the specified property with the specified value
     */
    OWLAnnotation getAnnotation(OWLAnnotationProperty property,
                                OWLAnnotationValue value,
                                Set<? extends OWLAnnotation> annotations);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Annotation axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                       OWLAnnotationProperty property,
                                                       OWLAnnotationValue value);


    OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                       OWLAnnotation annotation);


    OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                       OWLAnnotationProperty property,
                                                       OWLAnnotationValue value,
                                                       Set<? extends OWLAnnotation> annotations);


    OWLAnnotationAssertionAxiom getAnnotationAssertion(OWLAnnotationSubject subject,
                                                       OWLAnnotation annotation,
                                                       Set<? extends OWLAnnotation> annotations);


    OWLImportsDeclaration getImportsDeclaration(OWLOntology subject,
                                                URI importedOntologyURI);


    OWLAnnotationPropertyDomain getAnnotationPropertyDomain(OWLAnnotationProperty prop,
                                                            IRI domain);


    OWLAnnotationPropertyRange getAnnotationPropertyRange(OWLAnnotationProperty prop,
                                                          IRI range);


    OWLSubAnnotationPropertyOf getSubAnnotationPropertyOf(OWLAnnotationProperty sub,
                                                          OWLAnnotationProperty sup);
}

