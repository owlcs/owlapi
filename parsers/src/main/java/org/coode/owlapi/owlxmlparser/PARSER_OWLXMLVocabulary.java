/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
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
 * Copyright 2014, The University of Manchester
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
package org.coode.owlapi.owlxmlparser;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/** @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group
 * @since 2.0.0 */
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
        iri = IRI.create(Namespaces.OWL.toString(), name.getShortName());
        shortName = name.getShortName();
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /** @return short name */
    public String getShortName() {
        return shortName;
    }

    /** @param handler
     *            owlxml handler
     * @return element handler */
    public OWLElementHandler<?> createHandler(
            @SuppressWarnings("unused") OWLXMLParserHandler handler) {
        return null;
    }
}
