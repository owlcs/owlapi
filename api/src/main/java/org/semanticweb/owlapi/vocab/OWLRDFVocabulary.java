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
package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public enum OWLRDFVocabulary implements HasShortForm, HasIRI, HasPrefixedName {
    //@formatter:off
    // OWL Vocab
    /** http://www.w3.org/2002/07/owl#Thing.                */    OWL_THING(Namespaces.OWL, "Thing"),
    /** http://www.w3.org/2002/07/owl#Nothing.              */    OWL_NOTHING(Namespaces.OWL, "Nothing"),
    /** http://www.w3.org/2002/07/owl#Class.                */    OWL_CLASS(Namespaces.OWL, "Class"),
    /** http://www.w3.org/2002/07/owl#Ontology.             */    OWL_ONTOLOGY(Namespaces.OWL, "Ontology"),
    /** http://www.w3.org/2002/07/owl#imports.              */    OWL_IMPORTS(Namespaces.OWL, "imports"),
    /** http://www.w3.org/2002/07/owl#versionIRI.           */    OWL_VERSION_IRI(Namespaces.OWL, "versionIRI"),
    /** http://www.w3.org/2002/07/owl#versionInfo.          */    OWL_VERSION_INFO(Namespaces.OWL, "versionInfo"),
    /** http://www.w3.org/2002/07/owl#equivalentClass.      */    OWL_EQUIVALENT_CLASS(Namespaces.OWL, "equivalentClass"),
    /** http://www.w3.org/2002/07/owl#ObjectProperty.       */    OWL_OBJECT_PROPERTY(Namespaces.OWL, "ObjectProperty"),
    /** http://www.w3.org/2002/07/owl#DatatypeProperty.     */    OWL_DATA_PROPERTY(Namespaces.OWL, "DatatypeProperty"),
    /** http://www.w3.org/2002/07/owl#FunctionalProperty.   */    OWL_FUNCTIONAL_PROPERTY(Namespaces.OWL, "FunctionalProperty"),
    /** http://www.w3.org/2002/07/owl#AsymmetricProperty.   */    OWL_ASYMMETRIC_PROPERTY(Namespaces.OWL, "AsymmetricProperty"),
    /** http://www.w3.org/2002/07/owl#SymmetricProperty.    */    OWL_SYMMETRIC_PROPERTY(Namespaces.OWL, "SymmetricProperty"),
    /** http://www.w3.org/2002/07/owl#Restriction.          */    OWL_RESTRICTION(Namespaces.OWL, "Restriction"),
    /** http://www.w3.org/2002/07/owl#onProperty.           */    OWL_ON_PROPERTY(Namespaces.OWL, "onProperty"),
    /** http://www.w3.org/2002/07/owl#intersectionOf.       */    OWL_INTERSECTION_OF(Namespaces.OWL, "intersectionOf"),
    /** http://www.w3.org/2002/07/owl#unionOf.              */    OWL_UNION_OF(Namespaces.OWL, "unionOf"),
    /** http://www.w3.org/2002/07/owl#allValuesFrom.        */    OWL_ALL_VALUES_FROM(Namespaces.OWL, "allValuesFrom", "toClass"),
    /** http://www.w3.org/2002/07/owl#someValuesFrom.       */    OWL_SOME_VALUES_FROM(Namespaces.OWL, "someValuesFrom", "hasClass"),
    /** http://www.w3.org/2002/07/owl#hasValue.             */    OWL_HAS_VALUE(Namespaces.OWL, "hasValue"),
    /** http://www.w3.org/2002/07/owl#disjointWith.         */    OWL_DISJOINT_WITH(Namespaces.OWL, "disjointWith"),
    /** http://www.w3.org/2002/07/owl#oneOf.                */    OWL_ONE_OF(Namespaces.OWL, "oneOf"),
    /** http://www.w3.org/2002/07/owl#hasSelf.              */    OWL_HAS_SELF(Namespaces.OWL, "hasSelf"),
    /** http://www.w3.org/2002/07/owl#disjointUnionOf.      */    OWL_DISJOINT_UNION_OF(Namespaces.OWL, "disjointUnionOf"),
    /** http://www.w3.org/2002/07/owl#minCardinality.       */    OWL_MIN_CARDINALITY(Namespaces.OWL, "minCardinality", "minCardinalityQ"),
    /** http://www.w3.org/2002/07/owl#cardinality.          */    OWL_CARDINALITY(Namespaces.OWL, "cardinality", "cardinalityQ"),
    /** http://www.w3.org/2002/07/owl#qualifiedCardinality. */    OWL_QUALIFIED_CARDINALITY(Namespaces.OWL, "qualifiedCardinality"),
    /** http://www.w3.org/2002/07/owl#AnnotationProperty.   */    OWL_ANNOTATION_PROPERTY(Namespaces.OWL, "AnnotationProperty"),
    /** http://www.w3.org/2002/07/owl#Annotation.           */    OWL_ANNOTATION(Namespaces.OWL, "Annotation"),
    /** http://www.w3.org/2002/07/owl#Individual.           */    OWL_INDIVIDUAL(Namespaces.OWL, "Individual"),
    /** http://www.w3.org/2002/07/owl#NamedIndividual.      */    OWL_NAMED_INDIVIDUAL(Namespaces.OWL, "NamedIndividual"),
    /** http://www.w3.org/2002/07/owl#Datatype.             */    OWL_DATATYPE(Namespaces.OWL, "Datatype"),
    /** http://www.w3.org/2000/01/rdf-schema#subClassOf.    */    RDFS_SUBCLASS_OF(Namespaces.RDFS, "subClassOf"),
    /** http://www.w3.org/2000/01/rdf-schema#subPropertyOf. */    RDFS_SUB_PROPERTY_OF(Namespaces.RDFS, "subPropertyOf"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#type.    */    RDF_TYPE(Namespaces.RDF, "type"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#nil.     */    RDF_NIL(Namespaces.RDF, "nil"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#rest.    */    RDF_REST(Namespaces.RDF, "rest"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#first.   */    RDF_FIRST(Namespaces.RDF, "first"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#List.    */    RDF_LIST(Namespaces.RDF, "List"),
    /** http://www.w3.org/2002/07/owl#maxCardinality.       */    OWL_MAX_CARDINALITY(Namespaces.OWL, "maxCardinality", "maxCardinalityQ"),
    /** http://www.w3.org/2000/01/rdf-schema#label.         */    RDFS_LABEL(Namespaces.RDFS, "label"),
    /** http://www.w3.org/2000/01/rdf-schema#comment.       */    RDFS_COMMENT(Namespaces.RDFS, "comment"),
    /** http://www.w3.org/2000/01/rdf-schema#seeAlso.       */    RDFS_SEE_ALSO(Namespaces.RDFS, "seeAlso"),
    /** http://www.w3.org/2000/01/rdf-schema#isDefinedBy.   */    RDFS_IS_DEFINED_BY(Namespaces.RDFS, "isDefinedBy"),
    /** http://www.w3.org/2000/01/rdf-schema#Resource.      */    RDFS_RESOURCE(Namespaces.RDFS, "Resource"),
    /** http://www.w3.org/2000/01/rdf-schema#Literal.       */    RDFS_LITERAL(Namespaces.RDFS, "Literal"),
    /** http://www.w3.org/2000/01/rdf-schema#subClassOf.    */    RDFS_DATATYPE(Namespaces.RDFS, "Datatype"),
    /** http://www.w3.org/2002/07/owl#TransitiveProperty.   */    OWL_TRANSITIVE_PROPERTY(Namespaces.OWL, "TransitiveProperty"),
    /** http://www.w3.org/2002/07/owl#ReflexiveProperty.    */    OWL_REFLEXIVE_PROPERTY(Namespaces.OWL, "ReflexiveProperty"),
    /** http://www.w3.org/2002/07/owl#IrreflexiveProperty.  */    OWL_IRREFLEXIVE_PROPERTY(Namespaces.OWL, "IrreflexiveProperty"),
    /** http://www.w3.org/2002/07/owl#inverseOf.            */    OWL_INVERSE_OF(Namespaces.OWL, "inverseOf"),
    /** http://www.w3.org/2002/07/owl#complementOf.         */    OWL_COMPLEMENT_OF(Namespaces.OWL, "complementOf"),
    /** http://www.w3.org/2002/07/owl#datatypeComplementOf. */    OWL_DATATYPE_COMPLEMENT_OF(Namespaces.OWL, "datatypeComplementOf"),
    /** http://www.w3.org/2002/07/owl#AllDifferent.         */    OWL_ALL_DIFFERENT(Namespaces.OWL, "AllDifferent"),
    /** http://www.w3.org/2002/07/owl#distinctMembers.      */    OWL_DISTINCT_MEMBERS(Namespaces.OWL, "distinctMembers"),
    /** http://www.w3.org/2002/07/owl#sameAs.               */    OWL_SAME_AS(Namespaces.OWL, "sameAs"),
    /** http://www.w3.org/2002/07/owl#differentFrom.        */    OWL_DIFFERENT_FROM(Namespaces.OWL, "differentFrom"),
    /** http://www.w3.org/2002/07/owl#DeprecatedProperty.   */    OWL_DEPRECATED_PROPERTY(Namespaces.OWL, "DeprecatedProperty"),
    /** http://www.w3.org/2002/07/owl#equivalentProperty.   */    OWL_EQUIVALENT_PROPERTY(Namespaces.OWL, "equivalentProperty", "samePropertyAs"),
    /** http://www.w3.org/2002/07/owl#DeprecatedClass.      */    OWL_DEPRECATED_CLASS(Namespaces.OWL, "DeprecatedClass"),
    /** http://www.w3.org/2002/07/owl#DataRange.            */    OWL_DATA_RANGE(Namespaces.OWL, "DataRange"),
    /** http://www.w3.org/2000/01/rdf-schema#domain.        */    RDFS_DOMAIN(Namespaces.RDFS, "domain"),
    /** http://www.w3.org/2000/01/rdf-schema#range.         */    RDFS_RANGE(Namespaces.RDFS, "range"),
    /** http://www.w3.org/2000/01/rdf-schema#Class.         */    RDFS_CLASS(Namespaces.RDFS, "Class"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#Property.*/    RDF_PROPERTY(Namespaces.RDF, "Property"),
    /** http://www.w3.org/2002/07/owl#priorVersion.         */    OWL_PRIOR_VERSION(Namespaces.OWL, "priorVersion"),
    /** http://www.w3.org/2002/07/owl#deprecated.           */    OWL_DEPRECATED(Namespaces.OWL, "deprecated"),
    /** http://www.w3.org/2002/07/owl#incompatibleWith.     */    OWL_INCOMPATIBLE_WITH(Namespaces.OWL, "incompatibleWith"),
    /** http://www.w3.org/2002/07/owl#propertyDisjointWith. */    OWL_PROPERTY_DISJOINT_WITH(Namespaces.OWL, "propertyDisjointWith"),
    /** http://www.w3.org/2002/07/owl#onClass.              */    OWL_ON_CLASS(Namespaces.OWL, "onClass", "hasClassQ"),
    /** http://www.w3.org/2002/07/owl#onDataRange.          */    OWL_ON_DATA_RANGE(Namespaces.OWL, "onDataRange"),
    /** http://www.w3.org/2002/07/owl#onDatatype.           */    OWL_ON_DATA_TYPE(Namespaces.OWL, "onDatatype"),
    /** http://www.w3.org/2002/07/owl#withRestrictions.     */    OWL_WITH_RESTRICTIONS(Namespaces.OWL, "withRestrictions"),
    /** http://www.w3.org/2002/07/owl#Axiom.                */    OWL_AXIOM(Namespaces.OWL, "Axiom"),
    /** http://www.w3.org/2002/07/owl#propertyChainAxiom.   */    OWL_PROPERTY_CHAIN_AXIOM(Namespaces.OWL, "propertyChainAxiom"),
    /** http://www.w3.org/2002/07/owl#AllDisjointClasses.   */    OWL_ALL_DISJOINT_CLASSES(Namespaces.OWL, "AllDisjointClasses"),
    /** http://www.w3.org/2002/07/owl#members.              */    OWL_MEMBERS(Namespaces.OWL, "members"),
    /** http://www.w3.org/2002/07/owl#AllDisjointProperties.*/    OWL_ALL_DISJOINT_PROPERTIES(Namespaces.OWL, "AllDisjointProperties"),
    /** http://www.w3.org/2002/07/owl#topObjectProperty.    */    OWL_TOP_OBJECT_PROPERTY(Namespaces.OWL, "topObjectProperty"),
    /** http://www.w3.org/2002/07/owl#bottomObjectProperty. */    OWL_BOTTOM_OBJECT_PROPERTY(Namespaces.OWL, "bottomObjectProperty"),
    /** http://www.w3.org/2002/07/owl#topDataProperty.      */    OWL_TOP_DATA_PROPERTY(Namespaces.OWL, "topDataProperty"),
    /** http://www.w3.org/2002/07/owl#bottomDataProperty.   */    OWL_BOTTOM_DATA_PROPERTY(Namespaces.OWL, "bottomDataProperty"),
    /** http://www.w3.org/2002/07/owl#hasKey.               */    OWL_HAS_KEY(Namespaces.OWL, "hasKey"),
    /** http://www.w3.org/2002/07/owl#annotatedSource.      */    OWL_ANNOTATED_SOURCE(Namespaces.OWL, "annotatedSource"),
    /** http://www.w3.org/2002/07/owl#annotatedProperty.    */    OWL_ANNOTATED_PROPERTY(Namespaces.OWL, "annotatedProperty"),
    /** http://www.w3.org/2002/07/owl#annotatedTarget.      */    OWL_ANNOTATED_TARGET(Namespaces.OWL, "annotatedTarget"),
    /** http://www.w3.org/2002/07/owl#sourceIndividual.     */    OWL_SOURCE_INDIVIDUAL(Namespaces.OWL, "sourceIndividual"),
    /** http://www.w3.org/2002/07/owl#assertionProperty.    */    OWL_ASSERTION_PROPERTY(Namespaces.OWL, "assertionProperty"),
    /** http://www.w3.org/2002/07/owl#targetIndividual.     */    OWL_TARGET_INDIVIDUAL(Namespaces.OWL, "targetIndividual"),
    /** http://www.w3.org/2002/07/owl#targetValue.          */    OWL_TARGET_VALUE(Namespaces.OWL, "targetValue"),

    /** http://www.w3.org/2002/07/owl#InverseFunctionalProperty.      */    OWL_INVERSE_FUNCTIONAL_PROPERTY(Namespaces.OWL, "InverseFunctionalProperty"),
    /** http://www.w3.org/2002/07/owl#minQualifiedCardinality.        */    OWL_MIN_QUALIFIED_CARDINALITY(Namespaces.OWL, "minQualifiedCardinality"),
    /** http://www.w3.org/2002/07/owl#maxQualifiedCardinality.        */    OWL_MAX_QUALIFIED_CARDINALITY(Namespaces.OWL, "maxQualifiedCardinality"),
    /** http://www.w3.org/2002/07/owl#NegativePropertyAssertion.      */    OWL_NEGATIVE_PROPERTY_ASSERTION(Namespaces.OWL, "NegativePropertyAssertion"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#langString.        */    RDF_LANG_STRING(Namespaces.RDF, "langString"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral.      */    RDF_PLAIN_LITERAL(Namespaces.RDF, "PlainLiteral"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#Description.       */    RDF_DESCRIPTION(Namespaces.RDF, "Description"),
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral.        */    RDF_XML_LITERAL(Namespaces.RDF, "XMLLiteral"),
    /** http://www.w3.org/2002/07/owl#backwardCompatibleWith.         */    OWL_BACKWARD_COMPATIBLE_WITH(Namespaces.OWL, "backwardCompatibleWith"),
    /** http://www.w3.org/2002/07/owl#inverseObjectPropertyExpression. */    OWL_INVERSE_OBJECT_PROPERTY_EXPRESSION(Namespaces.OWL,"inverseObjectPropertyExpression"),

    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#OntologyProperty.                 */      OWL_ONTOLOGY_PROPERTY(Namespaces.OWL, "OntologyProperty"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#AntisymmetricProperty.            */      OWL_ANTI_SYMMETRIC_PROPERTY(Namespaces.OWL, "AntisymmetricProperty"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#DataRestriction.                  */      OWL_DATA_RESTRICTION(Namespaces.OWL, "DataRestriction"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#ObjectRestriction.                */      OWL_OBJECT_RESTRICTION(Namespaces.OWL, "ObjectRestriction"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#SelfRestriction.                  */      OWL_SELF_RESTRICTION(Namespaces.OWL, "SelfRestriction"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#declaredAs.                       */      OWL_DECLARED_AS(Namespaces.OWL, "declaredAs"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#NegativeObjectPropertyAssertion.  */      OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION(Namespaces.OWL, "NegativeObjectPropertyAssertion"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#NegativeDataPropertyAssertion.    */      OWL_NEGATIVE_DATA_PROPERTY_ASSERTION(Namespaces.OWL, "NegativeDataPropertyAssertion"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/1999/02/22-rdf-syntax-ns#subject.             */      RDF_SUBJECT(Namespaces.RDF, "subject"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate.           */      RDF_PREDICATE(Namespaces.RDF, "predicate"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/1999/02/22-rdf-syntax-ns#object.              */      RDF_OBJECT(Namespaces.RDF, "object"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#subject.                          */      OWL_SUBJECT(Namespaces.OWL, "subject"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#predicate.                        */      OWL_PREDICATE(Namespaces.OWL, "predicate"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#object.                           */      OWL_OBJECT(Namespaces.OWL, "object"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#objectPropertyDomain.             */      OWL_OBJECT_PROPERTY_DOMAIN(Namespaces.OWL, "objectPropertyDomain"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#dataPropertyDomain.               */      OWL_DATA_PROPERTY_DOMAIN(Namespaces.OWL, "dataPropertyDomain"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#dataPropertyRange.                */      OWL_DATA_PROPERTY_RANGE(Namespaces.OWL, "dataPropertyRange"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#objectPropertyRange.              */      OWL_OBJECT_PROPERTY_RANGE(Namespaces.OWL, "objectPropertyRange"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#subObjectPropertyOf.              */      OWL_SUB_OBJECT_PROPERTY_OF(Namespaces.OWL, "subObjectPropertyOf"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#subDataPropertyOf.                */      OWL_SUB_DATA_PROPERTY_OF(Namespaces.OWL, "subDataPropertyOf"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#disjointDataProperties.           */      OWL_DISJOINT_DATA_PROPERTIES(Namespaces.OWL, "disjointDataProperties"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#disjointObjectProperties.         */      OWL_DISJOINT_OBJECT_PROPERTIES(Namespaces.OWL, "disjointObjectProperties"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#equivalentDataProperty.           */      OWL_EQUIVALENT_DATA_PROPERTIES(Namespaces.OWL, "equivalentDataProperty"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#equivalentObjectProperty.         */      OWL_EQUIVALENT_OBJECT_PROPERTIES(Namespaces.OWL, "equivalentObjectProperty"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#FunctionalDataProperty.           */      OWL_FUNCTIONAL_DATA_PROPERTY(Namespaces.OWL, "FunctionalDataProperty"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#FunctionalObjectProperty.         */      OWL_FUNCTIONAL_OBJECT_PROPERTY(Namespaces.OWL, "FunctionalObjectProperty"),
    /** Deprecated vocabulary: here for backwards compatibility http://www.w3.org/2002/07/owl#propertyChain.                    */      OWL_PROPERTY_CHAIN(Namespaces.OWL, "propertyChain");
    //@formatter:on

    /**
     * Set of all IRIs for this enum values.
     */
    public static final Set<IRI> BUILT_IN_VOCABULARY_IRIS =
        asSet(Stream.of(values()).map(HasIRI::getIRI));
    /**
     * Set of members with DAML+OIL compatibility.
     */
    public static final Set<OWLRDFVocabulary> DAML_COMPATIBILITY = EnumSet.of(OWL_ALL_VALUES_FROM,
        OWL_SOME_VALUES_FROM, OWL_EQUIVALENT_PROPERTY, OWL_ON_CLASS, OWL_CARDINALITY,
        OWL_MAX_CARDINALITY, OWL_MIN_CARDINALITY, RDFS_SUBCLASS_OF, OWL_IMPORTS, RDFS_RANGE,
        OWL_HAS_VALUE, RDF_TYPE, RDFS_DOMAIN, OWL_VERSION_INFO, RDFS_COMMENT, OWL_ON_PROPERTY,
        OWL_RESTRICTION, OWL_CLASS, OWL_THING, OWL_NOTHING, OWL_MIN_CARDINALITY, OWL_CARDINALITY,
        OWL_MAX_CARDINALITY, OWL_INVERSE_OF, OWL_COMPLEMENT_OF, OWL_UNION_OF, OWL_INTERSECTION_OF,
        RDFS_LABEL, OWL_OBJECT_PROPERTY, OWL_DATA_PROPERTY);
    /**
     * Entity types.
     */
    public static final Set<IRI> entityTypes =
        asSet(Stream.of(OWL_CLASS, OWL_OBJECT_PROPERTY, OWL_DATA_PROPERTY, OWL_ANNOTATION_PROPERTY,
            RDFS_DATATYPE, OWL_NAMED_INDIVIDUAL).map(HasIRI::getIRI));
    /**
     * label , comment , versionInfo , backwardCompatibleWith , priorVersion , seeAlso , isDefinedBy
     * , incompatibleWith , deprecated.
     */
    public static final Set<IRI> BUILT_IN_AP_IRIS = asSet(Stream.of(RDFS_LABEL, RDFS_COMMENT,
        OWL_VERSION_INFO, OWL_BACKWARD_COMPATIBLE_WITH, OWL_PRIOR_VERSION, RDFS_SEE_ALSO,
        RDFS_IS_DEFINED_BY, OWL_INCOMPATIBLE_WITH, OWL_DEPRECATED).map(OWLRDFVocabulary::getIRI));
    private static String DAML_NAMESPACE = "http://www.daml.org/2001/03/daml+oil#";
    private final IRI iri;
    private final Namespaces namespace;
    private final String shortName;
    private final String prefixedName;
    private final String damlName;

    OWLRDFVocabulary(Namespaces namespace, String shortName) {
        this(namespace, shortName, null);
    }

    OWLRDFVocabulary(Namespaces namespace, String shortName, String damlName) {
        this.namespace = namespace;
        this.shortName = shortName;
        this.damlName = damlName;
        prefixedName = namespace.getPrefixName() + ':' + shortName;
        iri = IRI.create(namespace.toString(), shortName);
    }

    /**
     * Determines if the specified IRI is an IRI corresponding to owl:Class, owl:DatatypeProperty,
     * rdfs:Datatype, owl:ObjectProperty, owl:AnnotationProperty, or owl:NamedIndividual.
     *
     * @param iri The IRI to check
     * @return {@code true} if the IRI corresponds to a built in OWL entity IRI otherwise {@code
     * false}.
     */
    public static boolean isEntityTypeIRI(IRI iri) {
        return entityTypes.contains(iri);
    }

    /**
     * @return DAML alternative IRI
     */
    public IRI getDAMLIRI() {
        return IRI.create(DAML_NAMESPACE, damlName == null ? shortName : damlName);
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /**
     * @return the entry namespace
     */
    public Namespaces getNamespace() {
        return namespace;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }

    @Override
    public String getShortForm() {
        return shortName;
    }

    @Override
    public String toString() {
        return iri.toString();
    }
}
