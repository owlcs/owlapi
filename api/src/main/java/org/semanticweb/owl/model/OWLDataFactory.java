package org.semanticweb.owl.model;

import org.semanticweb.owl.vocab.OWLRestrictedDataRangeFacetVocabulary;

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



    /**
     * Gets the built in data range corresponding to the top data type (like owl:Thing but for data ranges),
     * this datatype is rdfs:Literal, and has a URI of $lt;http://www.w3.org/2000/01/rdf-schema#&gt;
     * @return The OWL Datatype corresponding to the top data type.
     */
    OWLDataType getTopDataType();


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
     * manager.  For example, suppose "m:Cat" was specified the CURIE, the namespaceManager would be used to obtain
     * the namespace for the "m" prefix, this namespace would then be concatenated with "Cat" to obtain the full URI
     * which would be the URI of the OWL class obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLClass getOWLClass(String curie, NamespaceManager namespaceManager);


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
     * manager.  For example, suppose "m:pet" was specified the CURIE, the namespaceManager would be used to obtain
     * the namespace for the "m" prefix, this namespace would then be concatenated with "pet" to obtain the full URI
     * which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLObjectProperty getOWLObjectProperty(String curie, NamespaceManager namespaceManager);


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
     * manager.  For example, suppose "m:age" was specified the CURIE, the namespaceManager would be used to obtain
     * the namespace for the "m" prefix, this namespace would then be concatenated with "age" to obtain the full URI
     * which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLDataProperty getOWLDataProperty(String curie, NamespaceManager namespaceManager);


    /**
     * Gets an OWL individual that has the specified URI
     * @param uri The URI of the individual to be obtained
     * @return The object representing the individual that has the specified URI
     */
    OWLIndividual getOWLIndividual(URI uri);


    /**
     * Gets an OWL individual that has a URI that is obtained by expanding a compact URI (CURIE) using a specified
     * prefix namespace mapping.
     * @param curie The compact URI.
     * @param namespaceManager The namespace manager that is responsible for mapping namespace prefixes to namespaces,
     * and is used to expand the specified compact URI (CURIE).
     * @return An OWL individual that has the URI obtained by expanding the specified CURIE using the specified namespace
     * manager.  For example, suppose "m:person" was specified the CURIE, the namespaceManager would be used to obtain
     * the namespace for the "m" prefix, this namespace would then be concatenated with "person" to obtain the full URI
     * which would be the URI of the OWL object property obtained by this method.
     * @throws OWLRuntimeException if the namespace prefix in the specified CURIE does not have a mapping to a namespace
     * in the specified namespace manager.
     */
    OWLIndividual getOWLIndividual(String curie, NamespaceManager namespaceManager);

    OWLIndividual getOWLAnonymousIndividual(URI anonId);


    /**
     * Gets an OWL datatype that has the specified URI
     * @param uri The URI of the datatype to be obtained
     * @return The object representing the datatype that has the specified URI
     */
    OWLDataType getOWLDataType(URI uri);

    /**
     * A convenience method that obtains the datatype that represents integers.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#integer&gt;
     * @return An object representing an integer datatype.
     */
    OWLDataType getIntegerDataType();


    /**
     * A convenience method that obtains the datatype that represents floats.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#float&gt;
     * @return An object representing the float datatype.
     */
    OWLDataType getFloatDataType();


    /**
     * A convenience method that obtains the datatype that represents doubles.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#double&gt;
     * @return An object representing a double datatype.
     */
    OWLDataType getDoubleDataType();


    /**
     * A convenience method that obtains the datatype that represents booleans.  This datatype will have the URI of
     * &lt;http://www.w3.org/2001/XMLSchema#boolean&gt;
     * @return An object representing the boolean datatype.
     */
    OWLDataType getBooleanDataType();

    
    OWLTypedLiteral getOWLTypedConstant(String literal, OWLDataType dataType);


    /**
     * Convenience method that obtains a constant typed as an integer.
     * @param value The value of the constant
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     * value of the integer, and whose data type is xsd:integer.
     */
    OWLTypedLiteral getOWLTypedConstant(int value);


    /**
     * Convenience method that obtains a constant typed as a double.
     * @param value The value of the constant
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     * value of the double, and whose data type is xsd:double.
     */
    OWLTypedLiteral getOWLTypedConstant(double value);


    /**
     * Convenience method that obtains a constant typed as a boolean.
     * @param value The value of the constant
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     * value of the boolean, and whose data type is xsd:boolean.
     */
    OWLTypedLiteral getOWLTypedConstant(boolean value);


    /**
     * Convenience method that obtains a constant typed as a float.
     * @param value The value of the constant
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     * value of the float, and whose data type is xsd:float.
     */
    OWLTypedLiteral getOWLTypedConstant(float value);

    /**
     * Convenience method that obtains a constant typed as a string.
     * @param value The value of the constant
     * @return An <code>OWLTypedConstant</code> whose literal is the lexical
     * value of the string, and whose data type is xsd:string.
     */
    OWLTypedLiteral getOWLTypedConstant(String value);

    OWLRDFTextLiteral getOWLUntypedConstant(String literal);


    OWLRDFTextLiteral getOWLUntypedConstant(String literal, String lang);


    OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values);


    OWLDataOneOf getOWLDataOneOf(OWLLiteral... values);


    OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange);


    OWLDataRangeRestriction getOWLDataRangeRestriction(OWLDataRange dataRange,
                                                       Set<OWLDataRangeFacetRestriction> facetRestrictions);


    OWLDataRangeRestriction getOWLDataRangeRestriction(OWLDataRange dataRange,
                                                       OWLRestrictedDataRangeFacetVocabulary facet,
                                                       OWLTypedLiteral typedConstant);

    OWLDataRangeRestriction getOWLDataRangeRestriction(OWLDataRange dataRange,
                                                       OWLDataRangeFacetRestriction ... facetRestrictions);


    OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet,
                                                                 OWLTypedLiteral facetValue);

    OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet,
                                                                 int facetValue);

    OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet,
                                                                 double facetValue);

    OWLDataRangeFacetRestriction getOWLDataRangeFacetRestriction(OWLRestrictedDataRangeFacetVocabulary facet,
                                                                 float facetValue);


    OWLObjectPropertyInverse getOWLObjectPropertyInverse(OWLObjectPropertyExpression property);

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Descriptions
    //
    ////////////////////////////////////////////////////////////////////////////////////


    OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<? extends OWLDescription> operands);


    OWLObjectIntersectionOf getOWLObjectIntersectionOf(OWLDescription... operands);


    OWLDataAllRestriction getOWLDataAllRestriction(OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataExactCardinalityRestriction getOWLDataExactCardinalityRestriction(OWLDataPropertyExpression property,
                                                                             int cardinality);


    OWLDataExactCardinalityRestriction getOWLDataExactCardinalityRestriction(OWLDataPropertyExpression property,
                                                                             int cardinality, OWLDataRange dataRange);


    OWLDataMaxCardinalityRestriction getOWLDataMaxCardinalityRestriction(OWLDataPropertyExpression property,
                                                                         int cardinality);


    OWLDataMaxCardinalityRestriction getOWLDataMaxCardinalityRestriction(OWLDataPropertyExpression property,
                                                                         int cardinality, OWLDataRange dataRange);


    OWLDataMinCardinalityRestriction getOWLDataMinCardinalityRestriction(OWLDataPropertyExpression property,
                                                                         int cardinality);


    OWLDataMinCardinalityRestriction getOWLDataMinCardinalityRestriction(OWLDataPropertyExpression property,
                                                                         int cardinality, OWLDataRange dataRange);


    OWLDataSomeRestriction getOWLDataSomeRestriction(OWLDataPropertyExpression property, OWLDataRange dataRange);


    OWLDataValueRestriction getOWLDataValueRestriction(OWLDataPropertyExpression property, OWLLiteral value);


    OWLObjectComplementOf getOWLObjectComplementOf(OWLDescription operand);


    OWLObjectAllRestriction getOWLObjectAllRestriction(OWLObjectPropertyExpression property,
                                                       OWLDescription description);


    OWLObjectOneOf getOWLObjectOneOf(Set<OWLIndividual> values);


    OWLObjectOneOf getOWLObjectOneOf(OWLIndividual... individuals);


    OWLObjectExactCardinalityRestriction getOWLObjectExactCardinalityRestriction(OWLObjectPropertyExpression property,
                                                                                 int cardinality);


    OWLObjectExactCardinalityRestriction getOWLObjectExactCardinalityRestriction(OWLObjectPropertyExpression property,
                                                                                 int cardinality,
                                                                                 OWLDescription description);


    OWLObjectMinCardinalityRestriction getOWLObjectMinCardinalityRestriction(OWLObjectPropertyExpression property,
                                                                             int cardinality);


    OWLObjectMinCardinalityRestriction getOWLObjectMinCardinalityRestriction(OWLObjectPropertyExpression property,
                                                                             int cardinality,
                                                                             OWLDescription description);


    OWLObjectMaxCardinalityRestriction getOWLObjectMaxCardinalityRestriction(OWLObjectPropertyExpression property,
                                                                             int cardinality);


    OWLObjectMaxCardinalityRestriction getOWLObjectMaxCardinalityRestriction(OWLObjectPropertyExpression property,
                                                                             int cardinality,
                                                                             OWLDescription description);


    OWLObjectSelfRestriction getOWLObjectSelfRestriction(OWLObjectPropertyExpression property);


    OWLObjectSomeRestriction getOWLObjectSomeRestriction(OWLObjectPropertyExpression property,
                                                         OWLDescription description);


    OWLObjectValueRestriction getOWLObjectValueRestriction(OWLObjectPropertyExpression property,
                                                           OWLIndividual individual);


    OWLObjectUnionOf getOWLObjectUnionOf(Set<? extends OWLDescription> operands);


    OWLObjectUnionOf getOWLObjectUnionOf(OWLDescription... operands);

    /////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axioms
    //
    /////////////////////////////////////////////////////////////////////////////////////////////


    OWLAntiSymmetricObjectPropertyAxiom getOWLAntiSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLDescription domain);


    OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression propery,
                                                           OWLDataRange owlDataRange);


    OWLDataSubPropertyAxiom getOWLSubDataPropertyAxiom(OWLDataPropertyExpression subProperty,
                                                       OWLDataPropertyExpression superProperty);


    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity);


    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<OWLIndividual> individuals);

    OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual ... individuals);


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<? extends OWLDescription> descriptions);


    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLDescription clsA, OWLDescription clsB);

    OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLDescription clsA, OWLDescription ... descriptions);


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties);


    OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression ... properties);

    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties);

    OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression ... properties);


    OWLDisjointUnionAxiom getOWLDisjointUnionAxiom(OWLClass owlClass, Set<? extends OWLDescription> descriptions);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<? extends OWLDescription> descriptions);


    OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(OWLDescription clsA, OWLDescription clsB);


    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(
            Set<? extends OWLDataPropertyExpression> properties);

    OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(OWLDataPropertyExpression ... properties);


    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
            Set<? extends OWLObjectPropertyExpression> properties);

    OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(OWLObjectPropertyExpression ... properties);


    OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property);


    OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLImportsDeclaration getOWLImportsDeclarationAxiom(OWLOntology subject, URI importedOntologyURI);


    OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                   OWLDataPropertyExpression property,
                                                                   OWLLiteral object);

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

    OWLNegativeDataPropertyAssertionAxiom getOWLNegativeDataPropertyAssertionAxiom(OWLIndividual subject,
                                                                                   OWLDataPropertyExpression property,
                                                                                   OWLLiteral object);


    OWLNegativeObjectPropertyAssertionAxiom getOWLNegativeObjectPropertyAssertionAxiom(OWLIndividual subject,
                                                                                       OWLObjectPropertyExpression property,
                                                                                       OWLIndividual object);


    OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLIndividual individual,
                                                                       OWLObjectPropertyExpression property,
                                                                       OWLIndividual object);


    OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLIndividual individual, OWLDescription description);

    OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
            OWLObjectPropertyExpression property);


    OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
                                                                 OWLDescription description);


    OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
                                                               OWLDescription range);


    OWLObjectSubPropertyAxiom getOWLSubObjectPropertyAxiom(OWLObjectPropertyExpression subProperty,
                                                           OWLObjectPropertyExpression superProperty);


    OWLReflexiveObjectPropertyAxiom getOWLReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLSameIndividualsAxiom getOWLSameIndividualsAxiom(Set<OWLIndividual> individuals);


    OWLSubClassAxiom getOWLSubClassAxiom(OWLDescription subClass, OWLDescription superClass);

    OWLSubClassAxiom getOWLSubClassAxiom(String subClass, String superClass, NamespaceManager namespaceManager);

    OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property);


    OWLDeprecatedClassAxiom getOWLDeprecatedClassAxiom(OWLClass owlClass);


    OWLDeprecatedObjectPropertyAxiom getOWLDeprecatedObjectPropertyAxiom(OWLObjectProperty property);


    OWLDeprecatedDataPropertyAxiom getOWLDeprecatedDataPropertyAxiom(OWLDataProperty property);


    OWLObjectPropertyChainSubPropertyAxiom getOWLObjectPropertyChainSubPropertyAxiom(
            List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty);


    OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression forwardProperty,
                                                                       OWLObjectPropertyExpression inverseProperty);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotations


    OWLEntityAnnotationAxiom getOWLEntityAnnotationAxiom(OWLEntity entity, OWLAnnotation annotation);

    OWLEntityAnnotationAxiom getOWLEntityAnnotationAxiom(OWLEntity entity, URI annotationURI, OWLLiteral value);

    OWLEntityAnnotationAxiom getOWLEntityAnnotationAxiom(OWLEntity entity, URI annotationURI, OWLIndividual value);


    OWLAxiomAnnotationAxiom getOWLAxiomAnnotationAxiom(OWLAxiom axiom, OWLAnnotation annotation);


    OWLConstantAnnotation getOWLConstantAnnotation(URI annotationURI, OWLLiteral literal);


    OWLObjectAnnotation getOWLObjectAnnotation(URI annotationURI, OWLIndividual individual);


    /**
     * Gets a label annotation. This is an annotation that has a URI
     * which corresponds to rdfs:label
     * @param label The label content
     */
    OWLLabelAnnotation getOWLLabelAnnotation(String label);


    /**
     * Gets a label annotation
     * @param label    The label content
     * @param language The language of the label
     */
    OWLLabelAnnotation getOWLLabelAnnotation(String label, String language);


    /**
     * Gets a comment annotation.  This is an annotation with a URI
     * that corresponds to rdfs:comment
     */
    OWLCommentAnnotation getCommentAnnotation(String comment);


    /**
     * Gets a comment annotation with an attached language tag.
     * @param comment  The comment content
     * @param langauge The langauge that the comment is in
     */
    OWLCommentAnnotation getCommentAnnotation(String comment, String langauge);


    OWLOntologyAnnotationAxiom getOWLOntologyAnnotationAxiom(OWLOntology subject, OWLAnnotation annotation);
}

