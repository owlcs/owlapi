package org.semanticweb.owlapi.model;

/**
 * Enumenration for OWLObject types. Allows for enum like operations on all axiom and expression
 * types.
 */
public enum OWLObjectType {
    // @formatter:off
    /** ONTOLOGY                    */  ONTOLOGY                    (0,   1,    OWLOntology.class),
    /** ASYMMETRIC                  */  ASYMMETRIC                  (3,   2018, OWLAsymmetricObjectPropertyAxiom.class),
    /** CLASS_ASSERTION             */  CLASS_ASSERTION             (7,   2005, OWLClassAssertionAxiom.class),
    /** DATA_ASSERTION              */  DATA_ASSERTION              (11,  2010, OWLDataPropertyAssertionAxiom.class),
    /** DATA_DOMAIN                 */  DATA_DOMAIN                 (13,  2029, OWLDataPropertyDomainAxiom.class),
    /** DATA_RANGE                  */  DATA_RANGE                  (17,  2030, OWLDataPropertyRangeAxiom.class),
    /** SUB_DATA                    */  SUB_DATA                    (19,  2027, OWLSubDataPropertyOfAxiom.class),
    /** DECLARATION                 */  DECLARATION                 (23,  2000, OWLDeclarationAxiom.class),
    /** DIFFERENT_INDIVIDUALS       */  DIFFERENT_INDIVIDUALS       (29,  2007, OWLDifferentIndividualsAxiom.class),
    /** DISJOINT_CLASSES            */  DISJOINT_CLASSES            (31,  2003, OWLDisjointClassesAxiom.class),
    /** DISJOINT_DATA               */  DISJOINT_DATA               (37,  2031, OWLDisjointDataPropertiesAxiom.class),
    /** DISJOINT_OBJECT             */  DISJOINT_OBJECT             (41,  2024, OWLDisjointObjectPropertiesAxiom.class),
    /** DISJOINT_UNION              */  DISJOINT_UNION              (43,  2004, OWLDisjointUnionAxiom.class),
    /** ANNOTATION_ASSERTION        */  ANNOTATION_ASSERTION        (47,  2034, OWLAnnotationAssertionAxiom.class),
    /** EQUIVALENT_CLASSES          */  EQUIVALENT_CLASSES          (53,  2001, OWLEquivalentClassesAxiom.class),
    /** EQUIVALENT_DATA             */  EQUIVALENT_DATA             (59,  2026, OWLEquivalentDataPropertiesAxiom.class),
    /** EQUIVALENT_OBJECT           */  EQUIVALENT_OBJECT           (61,  2012, OWLEquivalentObjectPropertiesAxiom.class),
    /** FUNCTIONAL_DATA             */  FUNCTIONAL_DATA             (67,  2028, OWLFunctionalDataPropertyAxiom.class),
    /** FUNCTIONAL_OBJECT           */  FUNCTIONAL_OBJECT           (71,  2015, OWLFunctionalObjectPropertyAxiom.class),
    /** INVERSE_FUNCTIONAL          */  INVERSE_FUNCTIONAL          (79,  2016, OWLInverseFunctionalObjectPropertyAxiom.class),
    /** INVERSE                     */  INVERSE                     (83,  2014, OWLInverseObjectPropertiesAxiom.class),
    /** IRREFLEXIVE                 */  IRREFLEXIVE                 (89,  2021, OWLIrreflexiveObjectPropertyAxiom.class),
    /** NEGATIVE_DATA_ASSERTION     */  NEGATIVE_DATA_ASSERTION     (97,  2011, OWLNegativeDataPropertyAssertionAxiom.class),
    /** NEGATIVE_OBJECT_ASSERTION   */  NEGATIVE_OBJECT_ASSERTION   (101, 2009, OWLNegativeObjectPropertyAssertionAxiom.class),
    /** OBJECT_ASSERTION            */  OBJECT_ASSERTION            (103, 2008, OWLObjectPropertyAssertionAxiom.class),
    /** SUB_PROPERTY_CHAIN          */  SUB_PROPERTY_CHAIN          (107, 2025, OWLSubPropertyChainOfAxiom.class),
    /** OBJECT_DOMAIN               */  OBJECT_DOMAIN               (109, 2022, OWLObjectPropertyDomainAxiom.class),
    /** OBJECT_RANGE                */  OBJECT_RANGE                (113, 2023, OWLObjectPropertyRangeAxiom.class),
    /** SUB_OBJECT                  */  SUB_OBJECT                  (127, 2013, OWLSubObjectPropertyOfAxiom.class),
    /** REFLEXIVE                   */  REFLEXIVE                   (131, 2020, OWLReflexiveObjectPropertyAxiom.class),
    /** SAME_INDIVIDUAL             */  SAME_INDIVIDUAL             (137, 2006, OWLSameIndividualAxiom.class),
    /** SUB_CLASS                   */  SUB_CLASS                   (139, 2002, OWLSubClassOfAxiom.class),
    /** SYMMETRIC                   */  SYMMETRIC                   (149, 2017, OWLSymmetricObjectPropertyAxiom.class),
    /** TRANSITIVE                  */  TRANSITIVE                  (151, 2019, OWLTransitiveObjectPropertyAxiom.class),
    /** CLASS                       */  CLASS                       (157, 1001, OWLClass.class),
    /** FORALL_DATA                 */  FORALL_DATA                 (163, 3013, OWLDataAllValuesFrom.class),
    /** EXACT_DATA                  */  EXACT_DATA                  (167, 3016, OWLDataExactCardinality.class),
    /** MAX_DATA                    */  MAX_DATA                    (173, 3017, OWLDataMaxCardinality.class),
    /** MIN_DATA                    */  MIN_DATA                    (179, 3015, OWLDataMinCardinality.class),
    /** SOME_DATA                   */  SOME_DATA                   (181, 3012, OWLDataSomeValuesFrom.class),
    /** HASVALUE_DATA               */  HASVALUE_DATA               (191, 3014, OWLDataHasValue.class),
    /** FORALL_OBJECT               */  FORALL_OBJECT               (193, 3006, OWLObjectAllValuesFrom.class),
    /** NOT_OBJECT                  */  NOT_OBJECT                  (197, 3003, OWLObjectComplementOf.class),
    /** EXACT_OBJECT                */  EXACT_OBJECT                (199, 3009, OWLObjectExactCardinality.class),
    /** AND_OBJECT                  */  AND_OBJECT                  (211, 3001, OWLObjectIntersectionOf.class),
    /** MAX_OBJECT                  */  MAX_OBJECT                  (223, 3010, OWLObjectMaxCardinality.class),
    /** MIN_OBJECT                  */  MIN_OBJECT                  (227, 3008, OWLObjectMinCardinality.class),
    /** ONEOF_OBJECT                */  ONEOF_OBJECT                (229, 3004, OWLObjectOneOf.class),
    /** HASSELF_OBJECT              */  HASSELF_OBJECT              (233, 3011, OWLObjectHasSelf.class),
    /** SOME_OBJECT                 */  SOME_OBJECT                 (239, 3005, OWLObjectSomeValuesFrom.class),
    /** OR_OBJECT                   */  OR_OBJECT                   (241, 3002, OWLObjectUnionOf.class),
    /** HASVALUE_OBJECT             */  HASVALUE_OBJECT             (251, 3007, OWLObjectHasValue.class),
    /** NOT_DATA                    */  NOT_DATA                    (257, 4002, OWLDataComplementOf.class),
    /** ONEOF_DATA                  */  ONEOF_DATA                  (263, 4003, OWLDataOneOf.class),
    /** DATATYPE                    */  DATATYPE                    (269, 4001, OWLDatatype.class),
    /** DATATYPE_RESTRICTION        */  DATATYPE_RESTRICTION        (271, 4006, OWLDatatypeRestriction.class),
    /** LITERAL                     */  LITERAL                     (277, 4008, OWLLiteral.class),
    /** DATA_PROPERTY               */  DATA_PROPERTY               (283, 1004, OWLDataProperty.class),
    /** OBJECT_PROPERTY             */  OBJECT_PROPERTY             (293, 1002, OWLObjectProperty.class),
    /** INVERSE_OBJECT              */  INVERSE_OBJECT              (307, 1003, OWLObjectInverseOf.class),
    /** NAMED_INDIVIDUAL            */  NAMED_INDIVIDUAL            (311, 1005, OWLNamedIndividual.class),
    /** FACET_RESTRICTION           */  FACET_RESTRICTION           (563, 4007, OWLFacetRestriction.class),
    /** SWRL_RULE                   */  SWRL_RULE                   (631, 2033, SWRLRule.class),
    /** SWRL_CLASS                  */  SWRL_CLASS                  (641, 6001, SWRLClassAtom.class),
    /** SWRL_DATA_RANGE             */  SWRL_DATA_RANGE             (643, 6002, SWRLDataRangeAtom.class),
    /** SWRL_OBJECT_PROPERTY        */  SWRL_OBJECT_PROPERTY        (647, 6003, SWRLObjectPropertyAtom.class),
    /** SWRL_DATA_PROPERTY          */  SWRL_DATA_PROPERTY          (653, 6004, SWRLDataPropertyAtom.class),
    /** SWRL_BUILTIN                */  SWRL_BUILTIN                (659, 6005, SWRLBuiltInAtom.class),
    /** SWRL_VARIABLE               */  SWRL_VARIABLE               (661, 6006, SWRLVariable.class),
    /** SWRL_INDIVIDUAL             */  SWRL_INDIVIDUAL             (677, 6007, SWRLIndividualArgument.class),
    /** SWRL_LITERAL                */  SWRL_LITERAL                (683, 6008, SWRLLiteralArgument.class),
    /** SWRL_DIFFERENT_INDIVIDUAL   */  SWRL_DIFFERENT_INDIVIDUAL   (797, 6010, SWRLDifferentIndividualsAtom.class),
    /** SWRL_SAME_INDIVIDUAL        */  SWRL_SAME_INDIVIDUAL        (811, 6009, SWRLSameIndividualAtom.class),
    /** HASKEY                      */  HASKEY                      (821, 2032, OWLHasKeyAxiom.class),
    /** ANNOTATION_DOMAIN           */  ANNOTATION_DOMAIN           (823, 2037, OWLAnnotationPropertyDomainAxiom.class),
    /** ANNOTATION_RANGE            */  ANNOTATION_RANGE            (827, 2036, OWLAnnotationPropertyRangeAxiom.class),
    /** SUB_ANNOTATION              */  SUB_ANNOTATION              (829, 2035, OWLSubAnnotationPropertyOfAxiom.class),
    /** AND_DATA                    */  AND_DATA                    (839, 4004, OWLDataIntersectionOf.class),
    /** OR_DATA                     */  OR_DATA                     (853, 4005, OWLDataUnionOf.class),
    /** ANNOTATION_PROPERTY         */  ANNOTATION_PROPERTY         (857, 1006, OWLAnnotationProperty.class),
    /** ANONYMOUS_INDIVIDUAL        */  ANONYMOUS_INDIVIDUAL        (859, 1007, OWLAnonymousIndividual.class),
    /** IRI                         */  IRI                         (863, 0,    IRI.class),
    /** ANNOTATION                  */  ANNOTATION                  (877, 5001, OWLAnnotation.class),
    /** DATATYPE_DEFINITION         */  DATATYPE_DEFINITION         (897, 2038, OWLDatatypeDefinitionAxiom.class);
    // @formatter:on

    private int hashIndex;
    private int typeIndex;
    private Class<? extends OWLObject> type;

    private OWLObjectType(int hashIndex, int typeIndex, Class<? extends OWLObject> c) {
        this.hashIndex = hashIndex;
        this.typeIndex = typeIndex;
        type = c;
    }

    /**
     * @return the type this member refers to
     */
    public Class<? extends OWLObject> getType() {
        return type;
    }

    /**
     * @return hash index
     */
    public int hashIndex() {
        return hashIndex;
    }

    /**
     * @return type index
     */
    public int typeIndex() {
        return typeIndex;
    }
}
