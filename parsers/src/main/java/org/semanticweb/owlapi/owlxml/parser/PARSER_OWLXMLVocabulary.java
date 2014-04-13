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
package org.semanticweb.owlapi.owlxml.parser;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public enum PARSER_OWLXMLVocabulary implements HasIRI {
//@formatter:off
    /** CLASS                               */  PARSER_CLASS                               (CLASS                               ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLClassElementHandler(handler); } },
    /** DATA_PROPERTY                       */  PARSER_DATA_PROPERTY                       (DATA_PROPERTY                       ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataPropertyElementHandler(handler); } },
    /** OBJECT_PROPERTY                     */  PARSER_OBJECT_PROPERTY                     (OBJECT_PROPERTY                     ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectPropertyElementHandler(handler); } },
    /** NAMED_INDIVIDUAL                    */  PARSER_NAMED_INDIVIDUAL                    (NAMED_INDIVIDUAL                    ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLIndividualElementHandler(handler); } },
    /** ENTITY_ANNOTATION                   */  PARSER_ENTITY_ANNOTATION                   (ENTITY_ANNOTATION                   ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new LegacyEntityAnnotationElementHandler(handler); } },
    /** ANNOTATION_PROPERTY                 */  PARSER_ANNOTATION_PROPERTY                 (ANNOTATION_PROPERTY                 ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLAnnotationPropertyElementHandler(handler); } },
    /** DATATYPE                            */  PARSER_DATATYPE                            (DATATYPE                            ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDatatypeElementHandler(handler); } },
    /** ANNOTATION                          */  PARSER_ANNOTATION                          (ANNOTATION                          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLAnnotationElementHandler(handler); } },
    /** ANONYMOUS_INDIVIDUAL                */  PARSER_ANONYMOUS_INDIVIDUAL                (ANONYMOUS_INDIVIDUAL                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLAnonymousIndividualElementHandler(handler); } },
    /** IMPORT                              */  PARSER_IMPORT                              (IMPORT                              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLImportsHandler(handler); } },
    /** ONTOLOGY                            */  PARSER_ONTOLOGY                            (ONTOLOGY                            ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLOntologyHandler(handler); } },
    /** LITERAL                             */  PARSER_LITERAL                             (LITERAL                             ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLLiteralElementHandler(handler); } },
    /** OBJECT_INVERSE_OF                   */  PARSER_OBJECT_INVERSE_OF                   (OBJECT_INVERSE_OF                   ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLInverseObjectPropertyElementHandler(handler); } },
    /** DATA_COMPLEMENT_OF                  */  PARSER_DATA_COMPLEMENT_OF                  (DATA_COMPLEMENT_OF                  ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataComplementOfElementHandler(handler); } },
    /** DATA_ONE_OF                         */  PARSER_DATA_ONE_OF                         (DATA_ONE_OF                         ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataOneOfElementHandler(handler); } },
    /** DATATYPE_RESTRICTION                */  PARSER_DATATYPE_RESTRICTION                (DATATYPE_RESTRICTION                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDatatypeRestrictionElementHandler(handler); } },
    /** FACET_RESTRICTION                   */  PARSER_FACET_RESTRICTION                   (FACET_RESTRICTION                   ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDatatypeFacetRestrictionElementHandler(handler); } },
    /** DATA_UNION_OF                       */  PARSER_DATA_UNION_OF                       (DATA_UNION_OF                       ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataUnionOfElementHandler(handler); } },
    /** DATA_INTERSECTION_OF                */  PARSER_DATA_INTERSECTION_OF                (DATA_INTERSECTION_OF                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataIntersectionOfElementHandler(handler); } },
    /** OBJECT_INTERSECTION_OF              */  PARSER_OBJECT_INTERSECTION_OF              (OBJECT_INTERSECTION_OF              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectIntersectionOfElementHandler(handler); } },
    /** OBJECT_UNION_OF                     */  PARSER_OBJECT_UNION_OF                     (OBJECT_UNION_OF                     ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectUnionOfElementHandler(handler); } },
    /** OBJECT_COMPLEMENT_OF                */  PARSER_OBJECT_COMPLEMENT_OF                (OBJECT_COMPLEMENT_OF                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectComplementOfElementHandler(handler); } },
    /** OBJECT_ONE_OF                       */  PARSER_OBJECT_ONE_OF                       (OBJECT_ONE_OF                       ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectOneOfElementHandler(handler); } },
    /** OBJECT_SOME_VALUES_FROM             */  PARSER_OBJECT_SOME_VALUES_FROM             (OBJECT_SOME_VALUES_FROM             ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectSomeValuesFromElementHandler(handler); } },
    /** OBJECT_ALL_VALUES_FROM              */  PARSER_OBJECT_ALL_VALUES_FROM              (OBJECT_ALL_VALUES_FROM              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectAllValuesFromElementHandler(handler); } },
    /** OBJECT_HAS_SELF                     */  PARSER_OBJECT_HAS_SELF                     (OBJECT_HAS_SELF                     ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectExistsSelfElementHandler(handler); } },
    /** OBJECT_HAS_VALUE                    */  PARSER_OBJECT_HAS_VALUE                    (OBJECT_HAS_VALUE                    ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectHasValueElementHandler(handler); } },
    /** OBJECT_MIN_CARDINALITY              */  PARSER_OBJECT_MIN_CARDINALITY              (OBJECT_MIN_CARDINALITY              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectMinCardinalityElementHandler(handler); } },
    /** OBJECT_EXACT_CARDINALITY            */  PARSER_OBJECT_EXACT_CARDINALITY            (OBJECT_EXACT_CARDINALITY            ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectExactCardinalityElementHandler(handler); } },
    /** OBJECT_MAX_CARDINALITY              */  PARSER_OBJECT_MAX_CARDINALITY              (OBJECT_MAX_CARDINALITY              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectMaxCardinalityElementHandler(handler); } },
    /** DATA_SOME_VALUES_FROM               */  PARSER_DATA_SOME_VALUES_FROM               (DATA_SOME_VALUES_FROM               ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataSomeValuesFromElementHandler(handler); } },
    /** DATA_ALL_VALUES_FROM                */  PARSER_DATA_ALL_VALUES_FROM                (DATA_ALL_VALUES_FROM                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataAllValuesFromElementHandler(handler); } },
    /** DATA_HAS_VALUE                      */  PARSER_DATA_HAS_VALUE                      (DATA_HAS_VALUE                      ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataHasValueElementHandler(handler); } },
    /** DATA_MIN_CARDINALITY                */  PARSER_DATA_MIN_CARDINALITY                (DATA_MIN_CARDINALITY                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataMinCardinalityElementHandler(handler); } },
    /** DATA_EXACT_CARDINALITY              */  PARSER_DATA_EXACT_CARDINALITY              (DATA_EXACT_CARDINALITY              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataExactCardinalityElementHandler(handler); } },
    /** DATA_MAX_CARDINALITY                */  PARSER_DATA_MAX_CARDINALITY                (DATA_MAX_CARDINALITY                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataMaxCardinalityElementHandler(handler); } },
    /** SUB_CLASS_OF                        */  PARSER_SUB_CLASS_OF                        (SUB_CLASS_OF                        ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLSubClassAxiomElementHandler(handler); } },
    /** EQUIVALENT_CLASSES                  */  PARSER_EQUIVALENT_CLASSES                  (EQUIVALENT_CLASSES                  ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLEquivalentClassesAxiomElementHandler(handler); } },
    /** DISJOINT_CLASSES                    */  PARSER_DISJOINT_CLASSES                    (DISJOINT_CLASSES                    ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDisjointClassesAxiomElementHandler(handler); } },
    /** DISJOINT_UNION                      */  PARSER_DISJOINT_UNION                      (DISJOINT_UNION                      ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDisjointUnionElementHandler(handler); } },
    /** UNION_OF                            */  PARSER_UNION_OF                            (UNION_OF                            ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLUnionOfElementHandler(handler); } },
    /** SUB_OBJECT_PROPERTY_OF              */  PARSER_SUB_OBJECT_PROPERTY_OF              (SUB_OBJECT_PROPERTY_OF              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLSubObjectPropertyOfAxiomElementHandler(handler); } },
    /** OBJECT_PROPERTY_CHAIN               */  PARSER_OBJECT_PROPERTY_CHAIN               (OBJECT_PROPERTY_CHAIN               ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLSubObjectPropertyChainElementHandler(handler); } },
    /** EQUIVALENT_OBJECT_PROPERTIES        */  PARSER_EQUIVALENT_OBJECT_PROPERTIES        (EQUIVALENT_OBJECT_PROPERTIES        ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLEquivalentObjectPropertiesAxiomElementHandler(             handler); } },
    /** DISJOINT_OBJECT_PROPERTIES          */  PARSER_DISJOINT_OBJECT_PROPERTIES          (DISJOINT_OBJECT_PROPERTIES          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDisjointObjectPropertiesAxiomElementHandler(             handler); } },
    /** OBJECT_PROPERTY_DOMAIN              */  PARSER_OBJECT_PROPERTY_DOMAIN              (OBJECT_PROPERTY_DOMAIN              ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectPropertyDomainElementHandler(handler); } },
    /** OBJECT_PROPERTY_RANGE               */  PARSER_OBJECT_PROPERTY_RANGE               (OBJECT_PROPERTY_RANGE               ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectPropertyRangeAxiomElementHandler(handler); } },
    /** INVERSE_OBJECT_PROPERTIES           */  PARSER_INVERSE_OBJECT_PROPERTIES           (INVERSE_OBJECT_PROPERTIES           ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLInverseObjectPropertiesAxiomElementHandler(             handler); } },
    /** FUNCTIONAL_OBJECT_PROPERTY          */  PARSER_FUNCTIONAL_OBJECT_PROPERTY          (FUNCTIONAL_OBJECT_PROPERTY          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLFunctionalObjectPropertyAxiomElementHandler(             handler); } },
    /** INVERSE_FUNCTIONAL_OBJECT_PROPERTY  */  PARSER_INVERSE_FUNCTIONAL_OBJECT_PROPERTY  (INVERSE_FUNCTIONAL_OBJECT_PROPERTY  ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLInverseFunctionalObjectPropertyAxiomElementHandler(             handler); } },
    /** SYMMETRIC_OBJECT_PROPERTY           */  PARSER_SYMMETRIC_OBJECT_PROPERTY           (SYMMETRIC_OBJECT_PROPERTY           ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLSymmetricObjectPropertyAxiomElementHandler(             handler); } },
    /** ASYMMETRIC_OBJECT_PROPERTY          */  PARSER_ASYMMETRIC_OBJECT_PROPERTY          (ASYMMETRIC_OBJECT_PROPERTY          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLAsymmetricObjectPropertyElementHandler(handler); } },
    /** REFLEXIVE_OBJECT_PROPERTY           */  PARSER_REFLEXIVE_OBJECT_PROPERTY           (REFLEXIVE_OBJECT_PROPERTY           ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLReflexiveObjectPropertyAxiomElementHandler(             handler); } },
    /** IRREFLEXIVE_OBJECT_PROPERTY         */  PARSER_IRREFLEXIVE_OBJECT_PROPERTY         (IRREFLEXIVE_OBJECT_PROPERTY         ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLIrreflexiveObjectPropertyAxiomElementHandler(             handler); } },
    /** TRANSITIVE_OBJECT_PROPERTY          */  PARSER_TRANSITIVE_OBJECT_PROPERTY          (TRANSITIVE_OBJECT_PROPERTY          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLTransitiveObjectPropertyAxiomElementHandler(             handler); } },
    /** SUB_DATA_PROPERTY_OF                */  PARSER_SUB_DATA_PROPERTY_OF                (SUB_DATA_PROPERTY_OF                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLSubDataPropertyOfAxiomElementHandler(handler); } },
    /** EQUIVALENT_DATA_PROPERTIES          */  PARSER_EQUIVALENT_DATA_PROPERTIES          (EQUIVALENT_DATA_PROPERTIES          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLEquivalentDataPropertiesAxiomElementHandler(             handler); } },
    /** DISJOINT_DATA_PROPERTIES            */  PARSER_DISJOINT_DATA_PROPERTIES            (DISJOINT_DATA_PROPERTIES            ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDisjointDataPropertiesAxiomElementHandler(handler); } },
    /** DATA_PROPERTY_DOMAIN                */  PARSER_DATA_PROPERTY_DOMAIN                (DATA_PROPERTY_DOMAIN                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataPropertyDomainAxiomElementHandler(handler); } },
    /** DATA_PROPERTY_RANGE                 */  PARSER_DATA_PROPERTY_RANGE                 (DATA_PROPERTY_RANGE                 ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataPropertyRangeAxiomElementHandler(handler); } },
    /** FUNCTIONAL_DATA_PROPERTY            */  PARSER_FUNCTIONAL_DATA_PROPERTY            (FUNCTIONAL_DATA_PROPERTY            ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLFunctionalDataPropertyAxiomElementHandler(handler); } },
    /** SAME_INDIVIDUAL                     */  PARSER_SAME_INDIVIDUAL                     (SAME_INDIVIDUAL                     ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLSameIndividualsAxiomElementHandler(handler); } },
    /** DIFFERENT_INDIVIDUALS               */  PARSER_DIFFERENT_INDIVIDUALS               (DIFFERENT_INDIVIDUALS               ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDifferentIndividualsAxiomElementHandler(handler); } },
    /** CLASS_ASSERTION                     */  PARSER_CLASS_ASSERTION                     (CLASS_ASSERTION                     ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLClassAssertionAxiomElementHandler(handler); } },
    /** OBJECT_PROPERTY_ASSERTION           */  PARSER_OBJECT_PROPERTY_ASSERTION           (OBJECT_PROPERTY_ASSERTION           ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLObjectPropertyAssertionAxiomElementHandler(             handler); } },
    /** DATA_PROPERTY_ASSERTION             */  PARSER_DATA_PROPERTY_ASSERTION             (DATA_PROPERTY_ASSERTION             ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDataPropertyAssertionAxiomElementHandler(handler); } },
    /** NEGATIVE_OBJECT_PROPERTY_ASSERTION  */  PARSER_NEGATIVE_OBJECT_PROPERTY_ASSERTION  (NEGATIVE_OBJECT_PROPERTY_ASSERTION  ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLNegativeObjectPropertyAssertionAxiomElementHandler(             handler); } },
    /** NEGATIVE_DATA_PROPERTY_ASSERTION    */  PARSER_NEGATIVE_DATA_PROPERTY_ASSERTION    (NEGATIVE_DATA_PROPERTY_ASSERTION    ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLNegativeDataPropertyAssertionAxiomElementHandler(             handler); } },
    /** HAS_KEY                             */  PARSER_HAS_KEY                             (HAS_KEY                             ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLHasKeyElementHandler(handler); } },
    /** DECLARATION                         */  PARSER_DECLARATION                         (DECLARATION                         ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDeclarationAxiomElementHandler(handler); } },
    /** ANNOTATION_ASSERTION                */  PARSER_ANNOTATION_ASSERTION                (ANNOTATION_ASSERTION                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLAnnotationAssertionElementHandler(handler); } },
    /** ANNOTATION_PROPERTY_DOMAIN          */  PARSER_ANNOTATION_PROPERTY_DOMAIN          (ANNOTATION_PROPERTY_DOMAIN          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLAnnotationPropertyDomainElementHandler(handler); } },
    /** ANNOTATION_PROPERTY_RANGE           */  PARSER_ANNOTATION_PROPERTY_RANGE           (ANNOTATION_PROPERTY_RANGE           ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLAnnotationPropertyRangeElementHandler(handler); } },
    /** SUB_ANNOTATION_PROPERTY_OF          */  PARSER_SUB_ANNOTATION_PROPERTY_OF          (SUB_ANNOTATION_PROPERTY_OF          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLSubAnnotationPropertyOfElementHandler(handler); } },
    /** DATATYPE_DEFINITION                 */  PARSER_DATATYPE_DEFINITION                 (DATATYPE_DEFINITION                 ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new OWLDatatypeDefinitionElementHandler(handler); } },
    /** IRI_ELEMENT                         */  PARSER_IRI_ELEMENT                         (IRI_ELEMENT                         ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new IRIElementHandler(handler); } },
    /** ABBREVIATED_IRI_ELEMENT             */  PARSER_ABBREVIATED_IRI_ELEMENT             (ABBREVIATED_IRI_ELEMENT             ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new AbbreviatedIRIElementHandler(handler); } },
    /** NODE_ID                             */  PARSER_NODE_ID                             (NODE_ID                             ),
    /** ANNOTATION_URI                      */  PARSER_ANNOTATION_URI                      (ANNOTATION_URI                      ),
    /** LABEL                               */  PARSER_LABEL                               (LABEL                               ),
    /** COMMENT                             */  PARSER_COMMENT                             (COMMENT                             ),
    /** DOCUMENTATION                       */  PARSER_DOCUMENTATION                       (DOCUMENTATION                       ),
    /** DATATYPE_FACET                      */  PARSER_DATATYPE_FACET                      (DATATYPE_FACET                      ),
    /** DATATYPE_IRI                        */  PARSER_DATATYPE_IRI                        (DATATYPE_IRI                        ),
    /** DATA_RANGE                          */  PARSER_DATA_RANGE                          (DATA_RANGE                          ),
    /** PREFIX                              */  PARSER_PREFIX                              (PREFIX                              ),
    /** NAME_ATTRIBUTE                      */  PARSER_NAME_ATTRIBUTE                      (NAME_ATTRIBUTE                      ),
    /** IRI_ATTRIBUTE                       */  PARSER_IRI_ATTRIBUTE                       (IRI_ATTRIBUTE                       ),
    /** ABBREVIATED_IRI_ATTRIBUTE           */  PARSER_ABBREVIATED_IRI_ATTRIBUTE           (ABBREVIATED_IRI_ATTRIBUTE           ),
    /** CARDINALITY_ATTRIBUTE               */  PARSER_CARDINALITY_ATTRIBUTE               (CARDINALITY_ATTRIBUTE               ),
    
    // Rules Extensions
    /** DL_SAFE_RULE                        */  PARSER_DL_SAFE_RULE                        (DL_SAFE_RULE                        ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLRuleElementHandler(handler); } },
    /** BODY                                */  PARSER_BODY                                (BODY                                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLAtomListElementHandler(handler); } },
    /** HEAD                                */  PARSER_HEAD                                (HEAD                                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLAtomListElementHandler(handler); } },
    /** CLASS_ATOM                          */  PARSER_CLASS_ATOM                          (CLASS_ATOM                          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLClassAtomElementHandler(handler); } },
    /** DATA_RANGE_ATOM                     */  PARSER_DATA_RANGE_ATOM                     (DATA_RANGE_ATOM                     ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLDataRangeAtomElementHandler(handler); } },
    /** OBJECT_PROPERTY_ATOM                */  PARSER_OBJECT_PROPERTY_ATOM                (OBJECT_PROPERTY_ATOM                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLObjectPropertyAtomElementHandler(handler); } },
    /** DATA_PROPERTY_ATOM                  */  PARSER_DATA_PROPERTY_ATOM                  (DATA_PROPERTY_ATOM                  ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLDataPropertyAtomElementHandler(handler); } },
    /** BUILT_IN_ATOM                       */  PARSER_BUILT_IN_ATOM                       (BUILT_IN_ATOM                       ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLBuiltInAtomElementHandler(handler); } },
    /** SAME_INDIVIDUAL_ATOM                */  PARSER_SAME_INDIVIDUAL_ATOM                (SAME_INDIVIDUAL_ATOM                ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLSameIndividualAtomElementHandler(handler); } },
    /** DIFFERENT_INDIVIDUALS_ATOM          */  PARSER_DIFFERENT_INDIVIDUALS_ATOM          (DIFFERENT_INDIVIDUALS_ATOM          ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLDifferentIndividualsAtomElementHandler(handler); } },
    /** VARIABLE                            */  PARSER_VARIABLE                            (VARIABLE                            ) { @Override public OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) { return new SWRLVariableElementHandler(handler); } },
    /** DESCRIPTION_GRAPH_RULE              */  PARSER_DESCRIPTION_GRAPH_RULE              (DESCRIPTION_GRAPH_RULE              );
//@formatter:on
    private final IRI iri;
    private final String shortName;

    PARSER_OWLXMLVocabulary(OWLXMLVocabulary name) {
        iri = IRI.create(Namespaces.OWL.toString(), name.getShortForm());
        shortName = name.getShortForm();
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /** @return short name */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param handler
     *        owlxml handler
     * @return element handler
     */
    public OWLElementHandler<?> createHandler(
            @SuppressWarnings("unused") OWLXMLParserHandler handler) {
        return null;
    }
}

@SuppressWarnings("unused")
abstract class OWLElementHandler<O> {

    OWLXMLParserHandler handler;
    OWLElementHandler<?> parentHandler;
    StringBuilder sb;
    String elementName;
    OWLDataFactory df;

    /** @return object */
    abstract O getOWLObject();

    /**
     * @throws UnloadableImportException
     *         if an import cannot be resolved
     */
    abstract void endElement();

    OWLElementHandler(OWLXMLParserHandler handler) {
        this.handler = handler;
        df = handler.getDataFactory();
    }

    IRI getIRIFromAttribute(String localName, String value) {
        if (localName.equals(IRI_ATTRIBUTE.getShortForm())) {
            return handler.getIRI(value);
        } else if (localName.equals(ABBREVIATED_IRI_ATTRIBUTE.getShortForm())) {
            return handler.getAbbreviatedIRI(value);
        } else if (localName.equals("URI")) {
            // Legacy
            return handler.getIRI(value);
        }
        ensureAttributeNotNull(null, IRI_ATTRIBUTE.getShortForm());
        return null;
    }

    IRI getIRIFromElement(String elementLocalName, String textContent) {
        if (elementLocalName.equals(IRI_ELEMENT.getShortForm())) {
            return handler.getIRI(textContent.trim());
        } else if (elementLocalName.equals(ABBREVIATED_IRI_ELEMENT
                .getShortForm())) {
            return handler.getAbbreviatedIRI(textContent.trim());
        }
        throw new OWLXMLParserException(handler, elementLocalName
                + " is not an IRI element");
    }

    /**
     * @param handler
     *        element handler
     */
    void setParentHandler(OWLElementHandler<?> handler) {
        this.parentHandler = handler;
    }

    OWLElementHandler<?> getParentHandler() {
        return parentHandler;
    }

    /**
     * @param localName
     *        local attribute name
     * @param value
     *        attribute value
     */
    void attribute(String localName, String value) {}

    /**
     * @param name
     *        element name
     */
    void startElement(String name) {
        sb = null;
        elementName = name;
    }

    String getElementName() {
        return elementName;
    }

    /**
     * @param h
     *        element handler
     */
    void handleChild(AbstractOWLAxiomElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(AbstractClassExpressionElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(AbstractOWLDataRangeHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLDataPropertyElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLIndividualElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLLiteralElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLAnnotationElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLSubObjectPropertyChainElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLDatatypeFacetRestrictionElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLAnnotationPropertyElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(OWLAnonymousIndividualElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(AbstractIRIElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(SWRLVariableElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(SWRLAtomElementHandler h) {}

    /**
     * @param h
     *        element handler
     */
    void handleChild(SWRLAtomListElementHandler h) {}

    void ensureNotNull(Object element, String message) {
        if (element == null) {
            throw new OWLXMLParserElementNotFoundException(handler, message);
        }
    }

    void ensureAttributeNotNull(Object element, String message) {
        if (element == null) {
            throw new OWLXMLParserAttributeNotFoundException(handler, message);
        }
    }

    /**
     * @param chars
     *        chars to handle
     * @param start
     *        start index
     * @param length
     *        end index
     */
    void handleChars(char[] chars, int start, int length) {
        if (isTextContentPossible()) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(chars, start, length);
        }
    }

    /** @return text handled */
    String getText() {
        if (sb == null) {
            return "";
        } else {
            return sb.toString();
        }
    }

    /** @return true if text can be contained */
    boolean isTextContentPossible() {
        return false;
    }
}

abstract class AbstractClassExpressionElementHandler extends
        OWLElementHandler<OWLClassExpression> {

    OWLClassExpression desc;

    AbstractClassExpressionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void endElement() {
        endClassExpressionElement();
        getParentHandler().handleChild(this);
    }

    abstract void endClassExpressionElement();

    void setClassExpression(OWLClassExpression desc) {
        this.desc = desc;
    }

    @Override
    OWLClassExpression getOWLObject() {
        return desc;
    }
}

abstract class AbstractClassExpressionFillerRestriction extends
        AbstractObjectRestrictionElementHandler<OWLClassExpression> {

    AbstractClassExpressionFillerRestriction(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        setFiller(h.getOWLObject());
    }
}

abstract class AbstractClassExpressionOperandAxiomElementHandler extends
        AbstractOperandAxiomElementHandler<OWLClassExpression> {

    AbstractClassExpressionOperandAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        addOperand(h.getOWLObject());
    }
}

abstract class AbstractDataCardinalityRestrictionElementHandler extends
        AbstractDataRangeFillerRestrictionElementHandler {

    int cardinality;

    AbstractDataCardinalityRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void attribute(String localName, String value) {
        if (localName.equals("cardinality")) {
            cardinality = Integer.parseInt(value);
        }
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        setFiller(df.getTopDatatype());
    }

    int getCardinality() {
        return cardinality;
    }
}

abstract class AbstractDataRangeFillerRestrictionElementHandler extends
        AbstractDataRestrictionElementHandler<OWLDataRange> {

    AbstractDataRangeFillerRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        setFiller(h.getOWLObject());
    }
}

abstract class AbstractNaryBooleanClassExpressionElementHandler extends
        AbstractClassExpressionElementHandler {

    Set<OWLClassExpression> operands;

    AbstractNaryBooleanClassExpressionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        operands = new HashSet<OWLClassExpression>();
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        operands.add(h.getOWLObject());
    }

    @Override
    void endClassExpressionElement() {
        if (operands.size() >= 2) {
            setClassExpression(createClassExpression(operands));
        } else if (operands.size() == 1) {
            setClassExpression(operands.iterator().next());
        } else {
            String template = "Found zero child elements of an %s element. At least 2 class expression elements are required as child elements of %s elements";
            ensureNotNull(null,
                    String.format(template, getElementName(), getElementName()));
        }
    }

    abstract OWLClassExpression createClassExpression(
            Set<OWLClassExpression> expressions);
}

abstract class AbstractIRIElementHandler extends OWLElementHandler<IRI> {

    AbstractIRIElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }
}

abstract class AbstractDataRestrictionElementHandler<F extends OWLObject>
        extends AbstractRestrictionElementHandler<OWLDataPropertyExpression, F> {

    AbstractDataRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        setProperty(h.getOWLObject());
    }
}

abstract class AbstractObjectRestrictionElementHandler<F extends OWLObject>
        extends
        AbstractRestrictionElementHandler<OWLObjectPropertyExpression, F> {

    AbstractObjectRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        setProperty(h.getOWLObject());
    }
}

abstract class AbstractOperandAxiomElementHandler<O extends OWLObject> extends
        AbstractOWLAxiomElementHandler {

    Set<O> operands = new HashSet<O>();

    AbstractOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        operands.clear();
    }

    Set<O> getOperands() {
        return operands;
    }

    void addOperand(O operand) {
        operands.add(operand);
    }
}

abstract class AbstractOWLAssertionAxiomElementHandler<P extends OWLPropertyExpression, O extends OWLObject>
        extends AbstractOWLAxiomElementHandler {

    OWLIndividual subject;
    P property;
    O object;

    AbstractOWLAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    OWLIndividual getSubject() {
        return subject;
    }

    P getProperty() {
        return property;
    }

    O getObject() {
        return object;
    }

    void setSubject(OWLIndividual subject) {
        this.subject = subject;
    }

    void setProperty(P property) {
        this.property = property;
    }

    void setObject(O object) {
        this.object = object;
    }
}

abstract class AbstractOWLAxiomElementHandler extends
        OWLElementHandler<OWLAxiom> {

    OWLAxiom axiom;
    Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();

    AbstractOWLAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom getOWLObject() {
        return axiom;
    }

    void setAxiom(OWLAxiom axiom) {
        this.axiom = axiom;
    }

    @Override
    void startElement(String name) {
        annotations.clear();
    }

    @Override
    void handleChild(OWLAnnotationElementHandler h) {
        annotations.add(h.getOWLObject());
    }

    @Override
    void endElement() {
        setAxiom(createAxiom());
        getParentHandler().handleChild(this);
    }

    Set<OWLAnnotation> getAnnotations() {
        return annotations;
    }

    abstract OWLAxiom createAxiom();
}

abstract class AbstractOWLDataPropertyAssertionAxiomElementHandler
        extends
        AbstractOWLAssertionAxiomElementHandler<OWLDataPropertyExpression, OWLLiteral> {

    AbstractOWLDataPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler h) {
        setSubject(h.getOWLObject());
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        setSubject(h.getOWLObject());
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        setProperty(h.getOWLObject());
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        setObject(h.getOWLObject());
    }
}

abstract class AbstractOWLDataPropertyOperandAxiomElementHandler extends
        AbstractOperandAxiomElementHandler<OWLDataPropertyExpression> {

    AbstractOWLDataPropertyOperandAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        addOperand(h.getOWLObject());
    }
}

abstract class AbstractOWLDataRangeHandler extends
        OWLElementHandler<OWLDataRange> {

    OWLDataRange dataRange;

    AbstractOWLDataRangeHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    void setDataRange(OWLDataRange dataRange) {
        this.dataRange = dataRange;
    }

    @Override
    OWLDataRange getOWLObject() {
        return dataRange;
    }

    @Override
    void endElement() {
        endDataRangeElement();
        getParentHandler().handleChild(this);
    }

    abstract void endDataRangeElement();
}

abstract class AbstractOWLIndividualOperandAxiomElementHandler extends
        AbstractOperandAxiomElementHandler<OWLIndividual> {

    AbstractOWLIndividualOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        addOperand(h.getOWLObject());
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler h) {
        addOperand(h.getOWLObject());
    }
}

abstract class AbstractOWLObjectCardinalityElementHandler extends
        AbstractClassExpressionFillerRestriction {

    int cardinality;

    AbstractOWLObjectCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        setFiller(df.getOWLThing());
    }

    @Override
    void attribute(String localName, String value) {
        if (localName.equals("cardinality")) {
            cardinality = Integer.parseInt(value);
        }
    }

    @Override
    OWLClassExpression createRestriction() {
        return createCardinalityRestriction();
    }

    abstract OWLClassExpression createCardinalityRestriction();

    int getCardinality() {
        return cardinality;
    }
}

abstract class AbstractOWLObjectPropertyAssertionAxiomElementHandler
        extends
        AbstractOWLAssertionAxiomElementHandler<OWLObjectPropertyExpression, OWLIndividual> {

    AbstractOWLObjectPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler h) {
        if (getSubject() == null) {
            setSubject(h.getOWLObject());
        } else if (getObject() == null) {
            setObject(h.getOWLObject());
        }
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        if (getSubject() == null) {
            setSubject(h.getOWLObject());
        } else if (getObject() == null) {
            setObject(h.getOWLObject());
        }
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        setProperty(h.getOWLObject());
    }
}

abstract class AbstractOWLObjectPropertyCharacteristicAxiomElementHandler
        extends
        AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLObjectPropertyExpression> {

    AbstractOWLObjectPropertyCharacteristicAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        setProperty(h.getOWLObject());
    }
}

abstract class AbstractOWLObjectPropertyElementHandler extends
        OWLElementHandler<OWLObjectPropertyExpression> {

    OWLObjectPropertyExpression property;

    AbstractOWLObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void endElement() {
        endObjectPropertyElement();
        getParentHandler().handleChild(this);
    }

    void setOWLObjectPropertyExpression(OWLObjectPropertyExpression prop) {
        property = prop;
    }

    @Override
    OWLObjectPropertyExpression getOWLObject() {
        return property;
    }

    abstract void endObjectPropertyElement();
}

abstract class AbstractOWLObjectPropertyOperandAxiomElementHandler extends
        AbstractOperandAxiomElementHandler<OWLObjectPropertyExpression> {

    AbstractOWLObjectPropertyOperandAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        addOperand(h.getOWLObject());
    }
}

abstract class AbstractOWLPropertyCharacteristicAxiomElementHandler<P extends OWLObject>
        extends AbstractOWLAxiomElementHandler {

    P property;

    AbstractOWLPropertyCharacteristicAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    void setProperty(P property) {
        this.property = property;
    }

    P getProperty() {
        return property;
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(property, "property element");
        return createPropertyCharacteristicAxiom();
    }

    abstract OWLAxiom createPropertyCharacteristicAxiom();
}

abstract class AbstractRestrictionElementHandler<P extends OWLPropertyExpression, F extends OWLObject>
        extends AbstractClassExpressionElementHandler {

    P property;
    F filler;

    AbstractRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    void setProperty(P prop) {
        property = prop;
    }

    P getProperty() {
        return property;
    }

    F getFiller() {
        return filler;
    }

    void setFiller(F filler) {
        this.filler = filler;
    }

    @Override
    void endClassExpressionElement() {
        setClassExpression(createRestriction());
    }

    abstract OWLClassExpression createRestriction();
}

class AbbreviatedIRIElementHandler extends AbstractIRIElementHandler {

    AbbreviatedIRIElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    IRI iri;

    @Override
    boolean isTextContentPossible() {
        return true;
    }

    @Override
    IRI getOWLObject() {
        return iri;
    }

    @Override
    void endElement() {
        String iriText = getText().trim();
        iri = handler.getAbbreviatedIRI(iriText);
        getParentHandler().handleChild(this);
    }
}

class IRIElementHandler extends AbstractIRIElementHandler {

    IRIElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    IRI iri;

    @Override
    boolean isTextContentPossible() {
        return true;
    }

    @Override
    IRI getOWLObject() {
        return iri;
    }

    @Override
    void endElement() {
        String iriText = getText().trim();
        iri = handler.getIRI(iriText);
        getParentHandler().handleChild(this);
    }
}

class OWLUnionOfElementHandler extends OWLElementHandler<OWLClassExpression> {

    OWLUnionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        // We simply pass on to our parent, which MUST be an OWLDisjointUnionOf
        getParentHandler().handleChild(h);
    }

    @Override
    void endElement() {}

    @Override
    OWLClassExpression getOWLObject() {
        throw new OWLRuntimeException(
                "getOWLObject should not be called on OWLUnionOfElementHandler");
    }
}

class LegacyEntityAnnotationElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLEntity entity;
    OWLAnnotation annotation;

    LegacyEntityAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        OWLAnnotationAssertionAxiom toReturn = df
                .getOWLAnnotationAssertionAxiom(annotation.getProperty(),
                        entity.getIRI(), annotation.getValue());
        annotation = null;
        entity = null;
        return toReturn;
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        entity = h.getOWLObject().asOWLClass();
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        entity = h.getOWLObject().asOWLDataProperty();
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        entity = h.getOWLObject();
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        entity = h.getOWLObject().asOWLObjectProperty();
    }

    @Override
    void handleChild(OWLAnnotationElementHandler h) {
        if (entity == null) {
            super.handleChild(h);
        } else {
            annotation = h.getOWLObject();
        }
    }
}

class OWLAnnotationAssertionElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLAnnotationAssertionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    OWLAnnotationSubject subject = null;
    OWLAnnotationValue object = null;
    OWLAnnotationProperty property = null;

    @Override
    void handleChild(AbstractIRIElementHandler h) {
        if (subject == null) {
            subject = h.getOWLObject();
        } else {
            object = h.getOWLObject();
        }
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler h) {
        if (subject == null) {
            subject = h.getOWLObject();
        } else {
            object = h.getOWLObject();
        }
    }

    @Override
    void handleChild(OWLAnnotationPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        object = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLAnnotationAssertionAxiom(property, subject, object,
                getAnnotations());
    }
}

class OWLAnnotationElementHandler extends OWLElementHandler<OWLAnnotation> {

    Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
    OWLAnnotationProperty property;
    OWLAnnotationValue object;

    OWLAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
    }

    @Override
    void endElement() {
        getParentHandler().handleChild(this);
    }

    @Override
    void attribute(String localName, String value) {
        super.attribute(localName, value);
        // Legacy Handling
        if (localName.equals(ANNOTATION_URI.getShortForm())) {
            IRI iri = handler.getIRI(value);
            property = df.getOWLAnnotationProperty(iri);
        }
    }

    @Override
    void handleChild(OWLAnnotationElementHandler h) {
        annotations.add(h.getOWLObject());
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler h) {
        object = h.getOWLObject();
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        object = h.getOWLObject();
    }

    @Override
    void handleChild(OWLAnnotationPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    void handleChild(AbstractIRIElementHandler h) {
        object = h.getOWLObject();
    }

    @Override
    OWLAnnotation getOWLObject() {
        return df.getOWLAnnotation(property, object, annotations);
    }

    @Override
    boolean isTextContentPossible() {
        return false;
    }
}

class OWLAnnotationPropertyDomainElementHandler extends
        AbstractOWLAxiomElementHandler {

    IRI domain;
    OWLAnnotationProperty property;

    OWLAnnotationPropertyDomainElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractIRIElementHandler h) {
        domain = h.getOWLObject();
    }

    @Override
    void handleChild(OWLAnnotationPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(property, "Expected annotation property element");
        ensureNotNull(domain, "Expected iri element");
        return df.getOWLAnnotationPropertyDomainAxiom(property, domain,
                getAnnotations());
    }
}

class OWLAnnotationPropertyElementHandler extends
        OWLElementHandler<OWLAnnotationProperty> {

    OWLAnnotationProperty prop;
    IRI iri;

    OWLAnnotationPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAnnotationProperty getOWLObject() {
        return prop;
    }

    @Override
    void attribute(String localName, String value) {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    void endElement() {
        prop = df.getOWLAnnotationProperty(iri);
        getParentHandler().handleChild(this);
    }
}

class OWLAnnotationPropertyRangeElementHandler extends
        AbstractOWLAxiomElementHandler {

    IRI range;
    OWLAnnotationProperty property;

    OWLAnnotationPropertyRangeElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractIRIElementHandler h) {
        range = h.getOWLObject();
    }

    @Override
    void handleChild(OWLAnnotationPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(property, "Expected annotation property element");
        ensureNotNull(range, "Expected IRI element");
        return df.getOWLAnnotationPropertyRangeAxiom(property, range,
                getAnnotations());
    }
}

class OWLAnonymousIndividualElementHandler extends
        OWLElementHandler<OWLAnonymousIndividual> {

    OWLAnonymousIndividual ind;

    OWLAnonymousIndividualElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAnonymousIndividual getOWLObject() {
        return ind;
    }

    @Override
    void attribute(String localName, String value) {
        if (localName.equals(NODE_ID.getShortForm())) {
            ind = df.getOWLAnonymousIndividual(value.trim());
        } else {
            super.attribute(localName, value);
        }
    }

    @Override
    void endElement() {
        getParentHandler().handleChild(this);
    }
}

class OWLAsymmetricObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLAsymmetricObjectPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLAsymmetricObjectPropertyAxiom(getProperty());
    }
}

class OWLAsymmetricObjectPropertyElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLAsymmetricObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLAsymmetricObjectPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

class OWLClassAssertionAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLIndividual individual;
    OWLClassExpression classExpression;

    OWLClassAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        classExpression = h.getOWLObject();
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        individual = h.getOWLObject();
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler h) {
        individual = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(individual, "individual element");
        ensureNotNull(classExpression, "classExpression kind element");
        return df.getOWLClassAssertionAxiom(classExpression, individual,
                getAnnotations());
    }
}

class OWLClassElementHandler extends AbstractClassExpressionElementHandler {

    IRI iri;

    OWLClassElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void attribute(String localName, String value) {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    void endClassExpressionElement() {
        ensureAttributeNotNull(iri, "IRI");
        setClassExpression(df.getOWLClass(iri));
    }
}

class OWLDataAllValuesFromElementHandler extends
        AbstractDataRangeFillerRestrictionElementHandler {

    OWLDataAllValuesFromElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLDataAllValuesFrom(getProperty(), getFiller());
    }
}

class OWLDataComplementOfElementHandler extends AbstractOWLDataRangeHandler {

    OWLDataRange operand;

    OWLDataComplementOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        operand = h.getOWLObject();
    }

    @Override
    void endDataRangeElement() {
        ensureNotNull(operand, "data range element");
        setDataRange(df.getOWLDataComplementOf(operand));
    }
}

class OWLDataExactCardinalityElementHandler extends
        AbstractDataCardinalityRestrictionElementHandler {

    OWLDataExactCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLDataExactCardinality(getCardinality(), getProperty(),
                getFiller());
    }
}

class OWLDataHasValueElementHandler extends
        AbstractDataRestrictionElementHandler<OWLLiteral> {

    OWLDataHasValueElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        setFiller(h.getOWLObject());
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLDataHasValue(getProperty(), getFiller());
    }
}

class OWLDataIntersectionOfElementHandler extends AbstractOWLDataRangeHandler {

    Set<OWLDataRange> dataRanges = new HashSet<OWLDataRange>();

    OWLDataIntersectionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        dataRanges.add(h.getOWLObject());
    }

    @Override
    void endDataRangeElement() {
        setDataRange(df.getOWLDataIntersectionOf(dataRanges));
    }
}

class OWLDataMaxCardinalityElementHandler extends
        AbstractDataCardinalityRestrictionElementHandler {

    OWLDataMaxCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLDataMaxCardinality(getCardinality(), getProperty(),
                getFiller());
    }
}

class OWLDataMinCardinalityElementHandler extends
        AbstractDataCardinalityRestrictionElementHandler {

    OWLDataMinCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLDataMinCardinality(getCardinality(), getProperty(),
                getFiller());
    }
}

class OWLDataOneOfElementHandler extends AbstractOWLDataRangeHandler {

    Set<OWLLiteral> constants;

    OWLDataOneOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        constants = new HashSet<OWLLiteral>();
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        constants.add(h.getOWLObject());
    }

    @Override
    void endDataRangeElement() {
        if (constants.isEmpty()) {
            ensureNotNull(null, "data oneOf element");
        }
        setDataRange(df.getOWLDataOneOf(constants));
    }
}

class OWLDataPropertyAssertionAxiomElementHandler extends
        AbstractOWLDataPropertyAssertionAxiomElementHandler {

    OWLDataPropertyAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDataPropertyAssertionAxiom(getProperty(), getSubject(),
                getObject(), getAnnotations());
    }
}

class OWLDataPropertyDomainAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLClassExpression domain;
    OWLDataPropertyExpression property;

    OWLDataPropertyDomainAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        domain = h.getOWLObject();
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(property, "data property element");
        ensureNotNull(domain, "class expression element");
        return df.getOWLDataPropertyDomainAxiom(property, domain,
                getAnnotations());
    }
}

class OWLDataPropertyElementHandler extends
        OWLElementHandler<OWLDataPropertyExpression> {

    OWLDataPropertyExpression prop;
    IRI iri;

    OWLDataPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLDataPropertyExpression getOWLObject() {
        return prop;
    }

    @Override
    void attribute(String localName, String value) {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    void endElement() {
        prop = df.getOWLDataProperty(iri);
        getParentHandler().handleChild(this);
    }
}

class OWLDataPropertyRangeAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLDataPropertyExpression property;
    OWLDataRange range;

    OWLDataPropertyRangeAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        range = h.getOWLObject();
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(property, "data property element");
        ensureNotNull(range, "data range element");
        return df.getOWLDataPropertyRangeAxiom(property, range,
                getAnnotations());
    }
}

class OWLDataRestrictionElementHandler extends AbstractOWLDataRangeHandler {

    OWLLiteral constant;
    IRI facetIRI;

    OWLDataRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        dataRange = h.getOWLObject();
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        constant = h.getOWLObject();
    }

    @Override
    void attribute(String localName, String value) {
        super.attribute(localName, value);
        if (localName.equals("facet")) {
            facetIRI = handler.getIRI(value);
        }
    }

    @Override
    void endDataRangeElement() {
        ensureNotNull(dataRange, "data range element");
        ensureNotNull(constant, "typed constant element");
        setDataRange(df.getOWLDatatypeRestriction((OWLDatatype) dataRange,
                OWLFacet.getFacet(facetIRI), constant));
    }
}

class OWLDataSomeValuesFromElementHandler extends
        AbstractDataRangeFillerRestrictionElementHandler {

    OWLDataSomeValuesFromElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLDataSomeValuesFrom(getProperty(), getFiller());
    }
}

class OWLDataUnionOfElementHandler extends AbstractOWLDataRangeHandler {

    Set<OWLDataRange> dataRanges = new HashSet<OWLDataRange>();

    OWLDataUnionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        dataRanges.add(h.getOWLObject());
    }

    @Override
    void endDataRangeElement() {
        setDataRange(df.getOWLDataUnionOf(dataRanges));
    }
}

class OWLDatatypeDefinitionElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLDatatype datatype;
    OWLDataRange dataRange;

    OWLDatatypeDefinitionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        OWLDataRange handledDataRange = h.getOWLObject();
        if (handledDataRange.isDatatype() && datatype == null) {
            datatype = handledDataRange.asOWLDatatype();
        } else {
            dataRange = handledDataRange;
        }
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDatatypeDefinitionAxiom(datatype, dataRange,
                getAnnotations());
    }
}

class OWLDatatypeElementHandler extends AbstractOWLDataRangeHandler {

    IRI iri;

    OWLDatatypeElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void attribute(String localName, String value) {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    void endDataRangeElement() {
        ensureAttributeNotNull(iri, "IRI");
        setDataRange(df.getOWLDatatype(iri));
    }
}

class OWLDatatypeFacetRestrictionElementHandler extends
        OWLElementHandler<OWLFacetRestriction> {

    OWLFacet facet;
    OWLLiteral constant;

    OWLDatatypeFacetRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        constant = h.getOWLObject();
    }

    @Override
    void attribute(String localName, String value) {
        if (localName.equals("facet")) {
            facet = OWLFacet.getFacet(IRI.create(value));
        }
    }

    @Override
    void endElement() {
        getParentHandler().handleChild(this);
    }

    @Override
    OWLFacetRestriction getOWLObject() {
        return df.getOWLFacetRestriction(facet, constant);
    }
}

class OWLDatatypeRestrictionElementHandler extends AbstractOWLDataRangeHandler {

    OWLDatatype restrictedDataRange;
    Set<OWLFacetRestriction> facetRestrictions;

    OWLDatatypeRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        facetRestrictions = new HashSet<OWLFacetRestriction>();
    }

    @Override
    void endDataRangeElement() {
        setDataRange(df.getOWLDatatypeRestriction(restrictedDataRange,
                facetRestrictions));
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        OWLDataRange dr = h.getOWLObject();
        if (dr.isDatatype()) {
            restrictedDataRange = dr.asOWLDatatype();
        }
    }

    @Override
    void handleChild(OWLDatatypeFacetRestrictionElementHandler h) {
        facetRestrictions.add(h.getOWLObject());
    }
}

class OWLDeclarationAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    OWLEntity entity;
    // XXX this set seems unused
    Set<OWLAnnotation> entityAnnotations = new HashSet<OWLAnnotation>();

    OWLDeclarationAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        entity = null;
        entityAnnotations.clear();
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        entity = (OWLClass) h.getOWLObject();
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        entity = (OWLEntity) h.getOWLObject();
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        entity = (OWLEntity) h.getOWLObject();
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {
        entity = (OWLEntity) h.getOWLObject();
    }

    @Override
    void handleChild(OWLAnnotationPropertyElementHandler h) {
        entity = h.getOWLObject();
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        entity = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDeclarationAxiom(entity, getAnnotations());
    }

    @Override
    void handleChild(OWLAnnotationElementHandler h) {
        if (entity == null) {
            super.handleChild(h);
        } else {
            entityAnnotations.add(h.getOWLObject());
        }
    }
}

class OWLDifferentIndividualsAxiomElementHandler extends
        AbstractOWLIndividualOperandAxiomElementHandler {

    OWLDifferentIndividualsAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDifferentIndividualsAxiom(getOperands(),
                getAnnotations());
    }
}

class OWLDisjointClassesAxiomElementHandler extends
        AbstractClassExpressionOperandAxiomElementHandler {

    OWLDisjointClassesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDisjointClassesAxiom(getOperands(), getAnnotations());
    }
}

class OWLDisjointDataPropertiesAxiomElementHandler extends
        AbstractOWLDataPropertyOperandAxiomElementHandler {

    OWLDisjointDataPropertiesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDisjointDataPropertiesAxiom(getOperands(),
                getAnnotations());
    }
}

class OWLDisjointObjectPropertiesAxiomElementHandler extends
        AbstractOWLObjectPropertyOperandAxiomElementHandler {

    OWLDisjointObjectPropertiesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDisjointObjectPropertiesAxiom(getOperands(),
                getAnnotations());
    }
}

class OWLDisjointUnionElementHandler extends AbstractOWLAxiomElementHandler {

    OWLClass cls;
    Set<OWLClassExpression> classExpressions = new HashSet<OWLClassExpression>();

    OWLDisjointUnionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLDisjointUnionAxiom(cls, classExpressions,
                getAnnotations());
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        if (cls == null) {
            OWLClassExpression desc = h.getOWLObject();
            cls = (OWLClass) desc;
        } else {
            classExpressions.add(h.getOWLObject());
        }
    }
}

class OWLEquivalentClassesAxiomElementHandler extends
        AbstractClassExpressionOperandAxiomElementHandler {

    OWLEquivalentClassesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLEquivalentClassesAxiom(getOperands(), getAnnotations());
    }
}

class OWLEquivalentDataPropertiesAxiomElementHandler extends
        AbstractOWLDataPropertyOperandAxiomElementHandler {

    OWLEquivalentDataPropertiesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLEquivalentDataPropertiesAxiom(getOperands(),
                getAnnotations());
    }
}

class OWLEquivalentObjectPropertiesAxiomElementHandler extends
        AbstractOWLObjectPropertyOperandAxiomElementHandler {

    OWLEquivalentObjectPropertiesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLEquivalentObjectPropertiesAxiom(getOperands(),
                getAnnotations());
    }
}

class OWLFunctionalDataPropertyAxiomElementHandler
        extends
        AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLDataPropertyExpression> {

    OWLFunctionalDataPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        setProperty(h.getOWLObject());
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        ensureNotNull(getProperty(), "Expected data property element");
        return df.getOWLFunctionalDataPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

class OWLFunctionalObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLFunctionalObjectPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLFunctionalObjectPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

class OWLHasKeyElementHandler extends AbstractOWLAxiomElementHandler {

    OWLClassExpression ce;
    Set<OWLPropertyExpression> props = new HashSet<OWLPropertyExpression>();

    OWLHasKeyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        props.clear();
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        ce = h.getOWLObject();
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        props.add(h.getOWLObject());
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        props.add(h.getOWLObject());
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLHasKeyAxiom(ce, props, getAnnotations());
    }
}

class OWLIndividualElementHandler extends OWLElementHandler<OWLNamedIndividual> {

    OWLNamedIndividual individual;
    IRI name;

    OWLIndividualElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLNamedIndividual getOWLObject() {
        return individual;
    }

    @Override
    void attribute(String localName, String value) {
        name = getIRIFromAttribute(localName, value);
    }

    @Override
    void endElement() {
        // URI uri = getNameAttribute();
        individual = df.getOWLNamedIndividual(name);
        getParentHandler().handleChild(this);
    }
}

class OWLInverseFunctionalObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLInverseFunctionalObjectPropertyAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLInverseFunctionalObjectPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

class OWLInverseObjectPropertiesAxiomElementHandler extends
        AbstractOWLObjectPropertyOperandAxiomElementHandler {

    OWLInverseObjectPropertiesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        Set<OWLObjectPropertyExpression> props = getOperands();
        if (props.size() > 2 || props.size() < 1) {
            ensureNotNull(null,
                    "Expected 2 object property expression elements");
        }
        Iterator<OWLObjectPropertyExpression> it = props.iterator();
        OWLObjectPropertyExpression propA = it.next();
        OWLObjectPropertyExpression propB;
        if (it.hasNext()) {
            propB = it.next();
        } else {
            // Syntactic variant of symmetric property
            propB = propA;
        }
        return df.getOWLInverseObjectPropertiesAxiom(propA, propB,
                getAnnotations());
    }
}

class OWLInverseObjectPropertyElementHandler extends
        AbstractOWLObjectPropertyElementHandler {

    OWLObjectPropertyExpression inverse;

    OWLInverseObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        inverse = h.getOWLObject();
    }

    @Override
    void endObjectPropertyElement() {
        ensureNotNull(inverse, OBJECT_INVERSE_OF.getShortForm());
        setOWLObjectPropertyExpression(df.getOWLObjectInverseOf(inverse));
    }
}

class OWLIrreflexiveObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLIrreflexiveObjectPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLIrreflexiveObjectPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

class OWLLiteralElementHandler extends OWLElementHandler<OWLLiteral> {

    OWLLiteral literal;
    IRI iri;
    String lang;

    OWLLiteralElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
    }

    @Override
    void attribute(String localName, String value) {
        if (localName.equals(DATATYPE_IRI.getShortForm())) {
            iri = handler.getIRI(value);
        } else if (localName.equals("lang")) {
            lang = value;
        }
    }

    @Override
    void endElement() {
        if (iri != null && !iri.isPlainLiteral()) {
            literal = df.getOWLLiteral(getText(), df.getOWLDatatype(iri));
        } else {
            literal = df.getOWLLiteral(getText(), lang);
        }
        lang = null;
        iri = null;
        getParentHandler().handleChild(this);
    }

    @Override
    OWLLiteral getOWLObject() {
        return literal;
    }

    @Override
    boolean isTextContentPossible() {
        return true;
    }
}

class OWLNegativeDataPropertyAssertionAxiomElementHandler extends
        AbstractOWLDataPropertyAssertionAxiomElementHandler {

    OWLNegativeDataPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLNegativeDataPropertyAssertionAxiom(getProperty(),
                getSubject(), getObject(), getAnnotations());
    }
}

class OWLNegativeObjectPropertyAssertionAxiomElementHandler extends
        AbstractOWLObjectPropertyAssertionAxiomElementHandler {

    OWLNegativeObjectPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLNegativeObjectPropertyAssertionAxiom(getProperty(),
                getSubject(), getObject(), getAnnotations());
    }
}

class OWLObjectAllValuesFromElementHandler extends
        AbstractClassExpressionFillerRestriction {

    OWLObjectAllValuesFromElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLObjectAllValuesFrom(getProperty(), getFiller());
    }
}

class OWLObjectComplementOfElementHandler extends
        AbstractClassExpressionElementHandler {

    OWLClassExpression operand;

    OWLObjectComplementOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        operand = h.getOWLObject();
    }

    @Override
    void endClassExpressionElement() {
        ensureNotNull(operand, "class expression element");
        setClassExpression(df.getOWLObjectComplementOf(operand));
    }
}

class OWLObjectExactCardinalityElementHandler extends
        AbstractOWLObjectCardinalityElementHandler {

    OWLObjectExactCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createCardinalityRestriction() {
        return df.getOWLObjectExactCardinality(getCardinality(), getProperty(),
                getFiller());
    }
}

class OWLObjectExistsSelfElementHandler extends
        AbstractClassExpressionElementHandler {

    OWLObjectPropertyExpression property;

    OWLObjectExistsSelfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    void endClassExpressionElement() {
        ensureNotNull(property,
                "Was expecting object property expression element");
        setClassExpression(df.getOWLObjectHasSelf(property));
    }
}

class OWLObjectHasValueElementHandler extends
        AbstractObjectRestrictionElementHandler<OWLIndividual> {

    OWLObjectHasValueElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        setFiller(h.getOWLObject());
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler h) {
        setFiller(h.getOWLObject());
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLObjectHasValue(getProperty(), getFiller());
    }
}

class OWLObjectIntersectionOfElementHandler extends
        AbstractNaryBooleanClassExpressionElementHandler {

    OWLObjectIntersectionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createClassExpression(Set<OWLClassExpression> ops) {
        return df.getOWLObjectIntersectionOf(ops);
    }
}

class OWLObjectMaxCardinalityElementHandler extends
        AbstractOWLObjectCardinalityElementHandler {

    OWLObjectMaxCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createCardinalityRestriction() {
        return df.getOWLObjectMaxCardinality(getCardinality(), getProperty(),
                getFiller());
    }
}

class OWLObjectMinCardinalityElementHandler extends
        AbstractOWLObjectCardinalityElementHandler {

    OWLObjectMinCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createCardinalityRestriction() {
        return df.getOWLObjectMinCardinality(getCardinality(), getProperty(),
                getFiller());
    }
}

class OWLObjectOneOfElementHandler extends
        AbstractClassExpressionElementHandler {

    Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();

    OWLObjectOneOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        individuals.add(h.getOWLObject());
    }

    @Override
    void endClassExpressionElement() {
        if (individuals.size() < 1) {
            ensureNotNull(null,
                    "Expected at least one individual in object oneOf");
        }
        setClassExpression(df.getOWLObjectOneOf(individuals));
    }
}

class OWLObjectPropertyAssertionAxiomElementHandler extends
        AbstractOWLObjectPropertyAssertionAxiomElementHandler {

    OWLObjectPropertyAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLObjectPropertyAssertionAxiom(getProperty(),
                getSubject(), getObject(), getAnnotations());
    }
}

class OWLObjectPropertyDomainElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLClassExpression domain;
    OWLObjectPropertyExpression property;

    OWLObjectPropertyDomainElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        domain = h.getOWLObject();
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(property, "Expected object property element");
        ensureNotNull(domain, "Expected class expression element");
        return df.getOWLObjectPropertyDomainAxiom(property, domain,
                getAnnotations());
    }
}

class OWLObjectPropertyElementHandler extends
        AbstractOWLObjectPropertyElementHandler {

    IRI iri;

    OWLObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void attribute(String localName, String value) {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    void endObjectPropertyElement() {
        setOWLObjectPropertyExpression(df.getOWLObjectProperty(iri));
    }
}

class OWLObjectPropertyRangeAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLClassExpression range;
    OWLObjectPropertyExpression property;

    OWLObjectPropertyRangeAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        range = h.getOWLObject();
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        property = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(property, OBJECT_PROPERTY.getShortForm());
        ensureNotNull(range, "OWL class expression element");
        return df.getOWLObjectPropertyRangeAxiom(property, range,
                getAnnotations());
    }
}

class OWLObjectSomeValuesFromElementHandler extends
        AbstractClassExpressionFillerRestriction {

    OWLObjectSomeValuesFromElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createRestriction() {
        return df.getOWLObjectSomeValuesFrom(getProperty(), getFiller());
    }
}

class OWLObjectUnionOfElementHandler extends
        AbstractNaryBooleanClassExpressionElementHandler {

    OWLObjectUnionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLClassExpression createClassExpression(Set<OWLClassExpression> ops) {
        return df.getOWLObjectUnionOf(ops);
    }
}

class OWLReflexiveObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLReflexiveObjectPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLReflexiveObjectPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

class OWLSameIndividualsAxiomElementHandler extends
        AbstractOWLIndividualOperandAxiomElementHandler {

    OWLSameIndividualsAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLSameIndividualAxiom(getOperands(), getAnnotations());
    }
}

class OWLSubAnnotationPropertyOfElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLAnnotationProperty subProperty = null;
    OWLAnnotationProperty superProperty = null;

    OWLSubAnnotationPropertyOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLAnnotationPropertyElementHandler h) {
        if (subProperty == null) {
            subProperty = h.getOWLObject();
        } else if (superProperty == null) {
            superProperty = h.getOWLObject();
        } else {
            ensureNotNull(null, "two annotation properties elements");
        }
    }

    @Override
    OWLAxiom createAxiom() {
        ensureNotNull(subProperty, "AnnotationProperty for sub property");
        ensureNotNull(superProperty, "AnnotationProperty for super property");
        return df.getOWLSubAnnotationPropertyOfAxiom(subProperty,
                superProperty, getAnnotations());
    }
}

class OWLSubClassAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    OWLClassExpression subClass;
    OWLClassExpression supClass;

    OWLSubClassAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {
        super.startElement(name);
        subClass = null;
        supClass = null;
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        if (subClass == null) {
            subClass = h.getOWLObject();
        } else if (supClass == null) {
            supClass = h.getOWLObject();
        }
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLSubClassOfAxiom(subClass, supClass, getAnnotations());
    }
}

class OWLSubDataPropertyOfAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLDataPropertyExpression subProperty;
    OWLDataPropertyExpression superProperty;

    OWLSubDataPropertyOfAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        if (subProperty == null) {
            subProperty = h.getOWLObject();
        } else if (superProperty == null) {
            superProperty = h.getOWLObject();
        } else {
            ensureNotNull(null, "two data property expression elements");
        }
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getOWLSubDataPropertyOfAxiom(subProperty, superProperty,
                getAnnotations());
    }
}

class OWLSubObjectPropertyChainElementHandler extends
        OWLElementHandler<List<OWLObjectPropertyExpression>> {

    List<OWLObjectPropertyExpression> propertyList;

    OWLSubObjectPropertyChainElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        propertyList = new ArrayList<OWLObjectPropertyExpression>();
    }

    @Override
    void endElement() {
        getParentHandler().handleChild(this);
    }

    @Override
    List<OWLObjectPropertyExpression> getOWLObject() {
        return propertyList;
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        propertyList.add(h.getOWLObject());
    }
}

class OWLSubObjectPropertyOfAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    OWLObjectPropertyExpression subProperty;
    List<OWLObjectPropertyExpression> propertyList;
    OWLObjectPropertyExpression superProperty;

    OWLSubObjectPropertyOfAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        if (subProperty == null && propertyList == null) {
            subProperty = h.getOWLObject();
        } else if (superProperty == null) {
            superProperty = h.getOWLObject();
        } else {
            ensureNotNull(null,
                    "Expected two object property expression elements");
        }
    }

    @Override
    void handleChild(OWLSubObjectPropertyChainElementHandler h) {
        propertyList = h.getOWLObject();
    }

    @Override
    OWLAxiom createAxiom() {
        if (subProperty != null) {
            return df.getOWLSubObjectPropertyOfAxiom(subProperty,
                    superProperty, getAnnotations());
        } else {
            return df.getOWLSubPropertyChainOfAxiom(propertyList,
                    superProperty, getAnnotations());
        }
    }
}

class OWLSymmetricObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLSymmetricObjectPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLSymmetricObjectPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

class OWLTransitiveObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    OWLTransitiveObjectPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createPropertyCharacteristicAxiom() {
        return df.getOWLTransitiveObjectPropertyAxiom(getProperty(),
                getAnnotations());
    }
}

abstract class SWRLAtomElementHandler extends OWLElementHandler<SWRLAtom> {

    SWRLAtom atom;

    SWRLAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    void setAtom(SWRLAtom atom) {
        this.atom = atom;
    }

    @Override
    SWRLAtom getOWLObject() {
        return atom;
    }
}

class SWRLAtomListElementHandler extends OWLElementHandler<List<SWRLAtom>> {

    List<SWRLAtom> atoms = new ArrayList<SWRLAtom>();

    SWRLAtomListElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(SWRLAtomElementHandler h) {
        atoms.add(h.getOWLObject());
    }

    @Override
    List<SWRLAtom> getOWLObject() {
        return atoms;
    }

    @Override
    void endElement() {
        getParentHandler().handleChild(this);
    }
}

class SWRLBuiltInAtomElementHandler extends SWRLAtomElementHandler {

    IRI iri;
    List<SWRLDArgument> args = new ArrayList<SWRLDArgument>();

    SWRLBuiltInAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void attribute(String localName, String value) {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    void handleChild(SWRLVariableElementHandler h) {
        args.add(h.getOWLObject());
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        args.add(df.getSWRLLiteralArgument(h.getOWLObject()));
    }

    @Override
    void endElement() {
        setAtom(df.getSWRLBuiltInAtom(iri, args));
        getParentHandler().handleChild(this);
    }
}

class SWRLClassAtomElementHandler extends SWRLAtomElementHandler {

    OWLClassExpression ce;
    SWRLIArgument arg;

    SWRLClassAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(SWRLVariableElementHandler h) {
        arg = h.getOWLObject();
    }

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {
        ce = h.getOWLObject();
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        arg = df.getSWRLIndividualArgument(h.getOWLObject());
    }

    @Override
    void endElement() {
        setAtom(df.getSWRLClassAtom(ce, arg));
        getParentHandler().handleChild(this);
    }
}

class SWRLDataPropertyAtomElementHandler extends SWRLAtomElementHandler {

    OWLDataPropertyExpression prop;
    SWRLIArgument arg0 = null;
    SWRLDArgument arg1 = null;

    SWRLDataPropertyAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(OWLDataPropertyElementHandler h) {
        prop = h.getOWLObject();
    }

    @Override
    void handleChild(SWRLVariableElementHandler h) {
        if (arg0 == null) {
            arg0 = h.getOWLObject();
        } else if (arg1 == null) {
            arg1 = h.getOWLObject();
        }
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        arg1 = df.getSWRLLiteralArgument(h.getOWLObject());
    }

    @Override
    void handleChild(OWLIndividualElementHandler _handler) {
        arg0 = df.getSWRLIndividualArgument(_handler.getOWLObject());
    }

    @Override
    void handleChild(OWLAnonymousIndividualElementHandler _handler) {
        arg0 = df.getSWRLIndividualArgument(_handler.getOWLObject());
    }

    @Override
    void endElement() {
        setAtom(df.getSWRLDataPropertyAtom(prop, arg0, arg1));
        getParentHandler().handleChild(this);
    }
}

class SWRLDataRangeAtomElementHandler extends SWRLAtomElementHandler {

    OWLDataRange prop;
    SWRLDArgument arg1 = null;

    SWRLDataRangeAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler _handler) {
        prop = _handler.getOWLObject();
    }

    @Override
    void handleChild(SWRLVariableElementHandler h) {
        arg1 = h.getOWLObject();
    }

    @Override
    void handleChild(OWLLiteralElementHandler h) {
        arg1 = df.getSWRLLiteralArgument(h.getOWLObject());
    }

    @Override
    void endElement() {
        setAtom(df.getSWRLDataRangeAtom(prop, arg1));
        getParentHandler().handleChild(this);
    }
}

class SWRLDifferentIndividualsAtomElementHandler extends SWRLAtomElementHandler {

    SWRLIArgument arg0;
    SWRLIArgument arg1;

    SWRLDifferentIndividualsAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(SWRLVariableElementHandler h) {
        if (arg0 == null) {
            arg0 = h.getOWLObject();
        } else if (arg1 == null) {
            arg1 = h.getOWLObject();
        }
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        if (arg0 == null) {
            arg0 = df.getSWRLIndividualArgument(h.getOWLObject());
        } else if (arg1 == null) {
            arg1 = df.getSWRLIndividualArgument(h.getOWLObject());
        }
    }

    @Override
    void endElement() {
        setAtom(df.getSWRLDifferentIndividualsAtom(arg0, arg1));
        getParentHandler().handleChild(this);
    }
}

class SWRLObjectPropertyAtomElementHandler extends SWRLAtomElementHandler {

    OWLObjectPropertyExpression prop;
    SWRLIArgument arg0 = null;
    SWRLIArgument arg1 = null;

    SWRLObjectPropertyAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(AbstractOWLObjectPropertyElementHandler h) {
        prop = h.getOWLObject();
    }

    @Override
    void handleChild(SWRLVariableElementHandler h) {
        if (arg0 == null) {
            arg0 = h.getOWLObject();
        } else if (arg1 == null) {
            arg1 = h.getOWLObject();
        }
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        if (arg0 == null) {
            arg0 = df.getSWRLIndividualArgument(h.getOWLObject());
        } else if (arg1 == null) {
            arg1 = df.getSWRLIndividualArgument(h.getOWLObject());
        }
    }

    @Override
    void endElement() {
        setAtom(df.getSWRLObjectPropertyAtom(prop, arg0, arg1));
        getParentHandler().handleChild(this);
    }
}

class SWRLRuleElementHandler extends AbstractOWLAxiomElementHandler {

    Set<SWRLAtom> body = null;
    Set<SWRLAtom> head = null;

    SWRLRuleElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    OWLAxiom createAxiom() {
        return df.getSWRLRule(body, head, getAnnotations());
    }

    @Override
    void handleChild(SWRLAtomListElementHandler h) {
        if (body == null) {
            body = new LinkedHashSet<SWRLAtom>(h.getOWLObject());
        } else if (head == null) {
            head = new LinkedHashSet<SWRLAtom>(h.getOWLObject());
        }
    }
}

class SWRLSameIndividualAtomElementHandler extends SWRLAtomElementHandler {

    SWRLIArgument arg0;
    SWRLIArgument arg1;

    SWRLSameIndividualAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void handleChild(SWRLVariableElementHandler h) {
        if (arg0 == null) {
            arg0 = h.getOWLObject();
        } else if (arg1 == null) {
            arg1 = h.getOWLObject();
        }
    }

    @Override
    void handleChild(OWLIndividualElementHandler h) {
        if (arg0 == null) {
            arg0 = df.getSWRLIndividualArgument(h.getOWLObject());
        } else if (arg1 == null) {
            arg1 = df.getSWRLIndividualArgument(h.getOWLObject());
        }
    }

    @Override
    void endElement() {
        setAtom(df.getSWRLSameIndividualAtom(arg0, arg1));
        getParentHandler().handleChild(this);
    }
}

class SWRLVariableElementHandler extends OWLElementHandler<SWRLVariable> {

    SWRLVariableElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    IRI iri;

    @Override
    void endElement() {
        getParentHandler().handleChild(this);
    }

    @Override
    void attribute(String localName, String value) {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    SWRLVariable getOWLObject() {
        if (iri != null) {
            return df.getSWRLVariable(iri);
        }
        return null;
    }
}

class OWLOntologyHandler extends OWLElementHandler<OWLOntology> {

    OWLOntologyHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void startElement(String name) {}

    @Override
    void attribute(String name, String value) {
        if (name.equals("ontologyIRI")) {
            OWLOntologyID newID = new OWLOntologyID(IRI.create(value), handler
                    .getOntology().getOntologyID().getVersionIRI());
            handler.getOWLOntologyManager().applyChange(
                    new SetOntologyID(handler.getOntology(), newID));
        }
        if (name.equals("versionIRI")) {
            OWLOntologyID newID = new OWLOntologyID(handler.getOntology()
                    .getOntologyID().getOntologyIRI(), IRI.create(value));
            handler.getOWLOntologyManager().applyChange(
                    new SetOntologyID(handler.getOntology(), newID));
        }
    }

    @Override
    void handleChild(AbstractOWLAxiomElementHandler h) {
        OWLAxiom axiom = h.getOWLObject();
        if (!axiom.isAnnotationAxiom()
                || handler.getConfiguration().isLoadAnnotationAxioms()) {
            handler.getOWLOntologyManager().applyChange(
                    new AddAxiom(handler.getOntology(), axiom));
        }
    }

    @Override
    void handleChild(AbstractOWLDataRangeHandler h) {}

    @Override
    void handleChild(AbstractClassExpressionElementHandler h) {}

    @Override
    void handleChild(OWLAnnotationElementHandler h) {
        handler.getOWLOntologyManager().applyChange(
                new AddOntologyAnnotation(handler.getOntology(), h
                        .getOWLObject()));
    }

    @Override
    void endElement() {}

    @Override
    OWLOntology getOWLObject() {
        return handler.getOntology();
    }

    @Override
    void setParentHandler(OWLElementHandler<?> handler) {}
}

class OWLImportsHandler extends OWLElementHandler<OWLOntology> {

    OWLImportsHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    void endElement() {
        IRI ontIRI = handler.getIRI(getText().trim());
        OWLImportsDeclaration decl = df.getOWLImportsDeclaration(ontIRI);
        handler.getOWLOntologyManager().applyChange(
                new AddImport(handler.getOntology(), decl));
        handler.getOWLOntologyManager().makeLoadImportRequest(decl,
                handler.getConfiguration());
    }

    @Override
    OWLOntology getOWLObject() {
        return null;
    }

    @Override
    boolean isTextContentPossible() {
        return true;
    }
}
