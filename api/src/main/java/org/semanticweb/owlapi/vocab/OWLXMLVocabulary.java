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

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public enum OWLXMLVocabulary implements HasShortForm, HasIRI, HasPrefixedName {
//@formatter:off
    /** CLASS                               */  CLASS                               ("Class"),
    /** DATA_PROPERTY                       */  DATA_PROPERTY                       ("DataProperty"),
    /** OBJECT_PROPERTY                     */  OBJECT_PROPERTY                     ("ObjectProperty"),
    /** NAMED_INDIVIDUAL                    */  NAMED_INDIVIDUAL                    ("NamedIndividual"),
    /** ENTITY_ANNOTATION                   */  ENTITY_ANNOTATION                   ("EntityAnnotation"),
    /** ANNOTATION_PROPERTY                 */  ANNOTATION_PROPERTY                 ("AnnotationProperty"),
    /** DATATYPE                            */  DATATYPE                            ("Datatype"),
    /** ANNOTATION                          */  ANNOTATION                          ("Annotation"),
    /** ANONYMOUS_INDIVIDUAL                */  ANONYMOUS_INDIVIDUAL                ("AnonymousIndividual"),
    /** NODE_ID                             */  NODE_ID                             ("nodeID"),
    /** ANNOTATION_URI                      */  ANNOTATION_URI                      ("annotationURI"),
    /** IMPORT                              */  IMPORT                              ("Import"),
    /** LABEL                               */  LABEL                               ("Label"),
    /** COMMENT                             */  COMMENT                             ("Comment"),
    /** DOCUMENTATION                       */  DOCUMENTATION                       ("Documentation"),
    /** ONTOLOGY                            */  ONTOLOGY                            ("Ontology"),
    /** LITERAL                             */  LITERAL                             ("Literal"),
    /** OBJECT_INVERSE_OF                   */  OBJECT_INVERSE_OF                   ("ObjectInverseOf"),
    /** DATA_COMPLEMENT_OF                  */  DATA_COMPLEMENT_OF                  ("DataComplementOf"),
    /** DATA_ONE_OF                         */  DATA_ONE_OF                         ("DataOneOf"),
    /** DATATYPE_RESTRICTION                */  DATATYPE_RESTRICTION                ("DatatypeRestriction"),
    /** FACET_RESTRICTION                   */  FACET_RESTRICTION                   ("FacetRestriction"),
    /** DATA_UNION_OF                       */  DATA_UNION_OF                       ("DataUnionOf"),
    /** DATA_INTERSECTION_OF                */  DATA_INTERSECTION_OF                ("DataIntersectionOf"),
    /** DATATYPE_FACET                      */  DATATYPE_FACET                      ("facet"),
    /** DATATYPE_IRI                        */  DATATYPE_IRI                        ("datatypeIRI"),
    /** DATA_RANGE                          */  DATA_RANGE                          ("DataRange"),
    /** OBJECT_INTERSECTION_OF              */  OBJECT_INTERSECTION_OF              ("ObjectIntersectionOf"),
    /** OBJECT_UNION_OF                     */  OBJECT_UNION_OF                     ("ObjectUnionOf"),
    /** OBJECT_COMPLEMENT_OF                */  OBJECT_COMPLEMENT_OF                ("ObjectComplementOf"),
    /** OBJECT_ONE_OF                       */  OBJECT_ONE_OF                       ("ObjectOneOf"),
    /** OBJECT_SOME_VALUES_FROM             */  OBJECT_SOME_VALUES_FROM             ("ObjectSomeValuesFrom"),
    /** OBJECT_ALL_VALUES_FROM              */  OBJECT_ALL_VALUES_FROM              ("ObjectAllValuesFrom"),
    /** OBJECT_HAS_SELF                     */  OBJECT_HAS_SELF                     ("ObjectHasSelf"),
    /** OBJECT_HAS_VALUE                    */  OBJECT_HAS_VALUE                    ("ObjectHasValue"),
    /** OBJECT_MIN_CARDINALITY              */  OBJECT_MIN_CARDINALITY              ("ObjectMinCardinality"),
    /** OBJECT_EXACT_CARDINALITY            */  OBJECT_EXACT_CARDINALITY            ("ObjectExactCardinality"),
    /** OBJECT_MAX_CARDINALITY              */  OBJECT_MAX_CARDINALITY              ("ObjectMaxCardinality"),
    /** DATA_SOME_VALUES_FROM               */  DATA_SOME_VALUES_FROM               ("DataSomeValuesFrom"),
    /** DATA_ALL_VALUES_FROM                */  DATA_ALL_VALUES_FROM                ("DataAllValuesFrom"),
    /** DATA_HAS_VALUE                      */  DATA_HAS_VALUE                      ("DataHasValue"),
    /** DATA_MIN_CARDINALITY                */  DATA_MIN_CARDINALITY                ("DataMinCardinality"),
    /** DATA_EXACT_CARDINALITY              */  DATA_EXACT_CARDINALITY              ("DataExactCardinality"),
    /** DATA_MAX_CARDINALITY                */  DATA_MAX_CARDINALITY                ("DataMaxCardinality"),
    /** SUB_CLASS_OF                        */  SUB_CLASS_OF                        ("SubClassOf"),
    /** EQUIVALENT_CLASSES                  */  EQUIVALENT_CLASSES                  ("EquivalentClasses"),
    /** DISJOINT_CLASSES                    */  DISJOINT_CLASSES                    ("DisjointClasses"),
    /** DISJOINT_UNION                      */  DISJOINT_UNION                      ("DisjointUnion"),
    /** UNION_OF                            */  UNION_OF                            ("UnionOf"),
    /** SUB_OBJECT_PROPERTY_OF              */  SUB_OBJECT_PROPERTY_OF              ("SubObjectPropertyOf"),
    /** OBJECT_PROPERTY_CHAIN               */  OBJECT_PROPERTY_CHAIN               ("ObjectPropertyChain"),
    /** EQUIVALENT_OBJECT_PROPERTIES        */  EQUIVALENT_OBJECT_PROPERTIES        ("EquivalentObjectProperties"),
    /** DISJOINT_OBJECT_PROPERTIES          */  DISJOINT_OBJECT_PROPERTIES          ("DisjointObjectProperties"),
    /** OBJECT_PROPERTY_DOMAIN              */  OBJECT_PROPERTY_DOMAIN              ("ObjectPropertyDomain"),
    /** OBJECT_PROPERTY_RANGE               */  OBJECT_PROPERTY_RANGE               ("ObjectPropertyRange"),
    /** INVERSE_OBJECT_PROPERTIES           */  INVERSE_OBJECT_PROPERTIES           ("InverseObjectProperties"),
    /** FUNCTIONAL_OBJECT_PROPERTY          */  FUNCTIONAL_OBJECT_PROPERTY          ("FunctionalObjectProperty"),
    /** INVERSE_FUNCTIONAL_OBJECT_PROPERTY  */  INVERSE_FUNCTIONAL_OBJECT_PROPERTY  ("InverseFunctionalObjectProperty"),
    /** SYMMETRIC_OBJECT_PROPERTY           */  SYMMETRIC_OBJECT_PROPERTY           ("SymmetricObjectProperty"),
    /** ASYMMETRIC_OBJECT_PROPERTY          */  ASYMMETRIC_OBJECT_PROPERTY          ("AsymmetricObjectProperty"),
    /** REFLEXIVE_OBJECT_PROPERTY           */  REFLEXIVE_OBJECT_PROPERTY           ("ReflexiveObjectProperty"),
    /** IRREFLEXIVE_OBJECT_PROPERTY         */  IRREFLEXIVE_OBJECT_PROPERTY         ("IrreflexiveObjectProperty"),
    /** TRANSITIVE_OBJECT_PROPERTY          */  TRANSITIVE_OBJECT_PROPERTY          ("TransitiveObjectProperty"),
    /** SUB_DATA_PROPERTY_OF                */  SUB_DATA_PROPERTY_OF                ("SubDataPropertyOf"),
    /** EQUIVALENT_DATA_PROPERTIES          */  EQUIVALENT_DATA_PROPERTIES          ("EquivalentDataProperties"),
    /** DISJOINT_DATA_PROPERTIES            */  DISJOINT_DATA_PROPERTIES            ("DisjointDataProperties"),
    /** DATA_PROPERTY_DOMAIN                */  DATA_PROPERTY_DOMAIN                ("DataPropertyDomain"),
    /** DATA_PROPERTY_RANGE                 */  DATA_PROPERTY_RANGE                 ("DataPropertyRange"),
    /** FUNCTIONAL_DATA_PROPERTY            */  FUNCTIONAL_DATA_PROPERTY            ("FunctionalDataProperty"),
    /** SAME_INDIVIDUAL                     */  SAME_INDIVIDUAL                     ("SameIndividual"),
    /** DIFFERENT_INDIVIDUALS               */  DIFFERENT_INDIVIDUALS               ("DifferentIndividuals"),
    /** CLASS_ASSERTION                     */  CLASS_ASSERTION                     ("ClassAssertion"),
    /** OBJECT_PROPERTY_ASSERTION           */  OBJECT_PROPERTY_ASSERTION           ("ObjectPropertyAssertion"),
    /** DATA_PROPERTY_ASSERTION             */  DATA_PROPERTY_ASSERTION             ("DataPropertyAssertion"),
    /** NEGATIVE_OBJECT_PROPERTY_ASSERTION  */  NEGATIVE_OBJECT_PROPERTY_ASSERTION  ("NegativeObjectPropertyAssertion"),
    /** NEGATIVE_DATA_PROPERTY_ASSERTION    */  NEGATIVE_DATA_PROPERTY_ASSERTION    ("NegativeDataPropertyAssertion"),
    /** HAS_KEY                             */  HAS_KEY                             ("HasKey"),
    /** DECLARATION                         */  DECLARATION                         ("Declaration"),
    /** ANNOTATION_ASSERTION                */  ANNOTATION_ASSERTION                ("AnnotationAssertion"),
    /** ANNOTATION_PROPERTY_DOMAIN          */  ANNOTATION_PROPERTY_DOMAIN          ("AnnotationPropertyDomain"),
    /** ANNOTATION_PROPERTY_RANGE           */  ANNOTATION_PROPERTY_RANGE           ("AnnotationPropertyRange"),
    /** SUB_ANNOTATION_PROPERTY_OF          */  SUB_ANNOTATION_PROPERTY_OF          ("SubAnnotationPropertyOf"),
    /** DATATYPE_DEFINITION                 */  DATATYPE_DEFINITION                 ("DatatypeDefinition"),
    /** PREFIX                              */  PREFIX                              ("Prefix"),
    /** NAME_ATTRIBUTE                      */  NAME_ATTRIBUTE                      ("name"),
    /** IRI_ATTRIBUTE                       */  IRI_ATTRIBUTE                       ("IRI"),
    /** ABBREVIATED_IRI_ATTRIBUTE           */  ABBREVIATED_IRI_ATTRIBUTE           ("abbreviatedIRI"),
    /** IRI_ELEMENT                         */  IRI_ELEMENT                         ("IRI"),
    /** ABBREVIATED_IRI_ELEMENT             */  ABBREVIATED_IRI_ELEMENT             ("AbbreviatedIRI"),
    /** CARDINALITY_ATTRIBUTE               */  CARDINALITY_ATTRIBUTE               ("cardinality"),
    
    // Rules Extensions
    /** DL_SAFE_RULE                        */  DL_SAFE_RULE                        ("DLSafeRule"),
    /** BODY                                */  BODY                                ("Body"),
    /** HEAD                                */  HEAD                                ("Head"),
    /** CLASS_ATOM                          */  CLASS_ATOM                          ("ClassAtom"),
    /** DATA_RANGE_ATOM                     */  DATA_RANGE_ATOM                     ("DataRangeAtom"),
    /** OBJECT_PROPERTY_ATOM                */  OBJECT_PROPERTY_ATOM                ("ObjectPropertyAtom"),
    /** DATA_PROPERTY_ATOM                  */  DATA_PROPERTY_ATOM                  ("DataPropertyAtom"),
    /** BUILT_IN_ATOM                       */  BUILT_IN_ATOM                       ("BuiltInAtom"),
    /** SAME_INDIVIDUAL_ATOM                */  SAME_INDIVIDUAL_ATOM                ("SameIndividualAtom"),
    /** DIFFERENT_INDIVIDUALS_ATOM          */  DIFFERENT_INDIVIDUALS_ATOM          ("DifferentIndividualsAtom"),
    /** VARIABLE                            */  VARIABLE                            ("Variable"),
    /** DESCRIPTION_GRAPH_RULE              */  DESCRIPTION_GRAPH_RULE              ("DescriptionGraphRule");
//@formatter:on
    private final IRI iri;
    private final String shortName;
    private final String prefixedName;

    OWLXMLVocabulary(String name) {
        iri = IRI.create(Namespaces.OWL.toString(), name);
        shortName = name;
        prefixedName = Namespaces.OWL.getPrefixName() + ":" + name;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public String toString() {
        return iri.toString();
    }

    static final Set<IRI> BUILT_IN_IRIS;
    static {
        BUILT_IN_IRIS = new HashSet<IRI>();
        for (OWLRDFVocabulary v : OWLRDFVocabulary.values()) {
            BUILT_IN_IRIS.add(v.getIRI());
        }
    }

    @Override
    public String getShortForm() {
        return shortName;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }
}
