package org.semanticweb.owlapi.model;

/**
 * Enumenration for OWLObject types. Allows for enum like operations on all axiom and expression
 * types.
 */
public enum OWLObjectType {
    // @formatter:off
    /** ONTOLOGY                    */  ONTOLOGY                    (0,   1),
    /** ASYMMETRIC                  */  ASYMMETRIC                  (3,   2018),
    /** CLASS_ASSERTION             */  CLASS_ASSERTION             (7,   2005),
    /** DATA_ASSERTION              */  DATA_ASSERTION              (11,  2010),
    /** DATA_DOMAIN                 */  DATA_DOMAIN                 (13,  2029),
    /** DATA_RANGE                  */  DATA_RANGE                  (17,  2030),
    /** SUB_DATA                    */  SUB_DATA                    (19,  2027),
    /** DECLARATION                 */  DECLARATION                 (23,  2000),
    /** DIFFERENT_INDIVIDUALS       */  DIFFERENT_INDIVIDUALS       (29,  2007),
    /** DISJOINT_CLASSES            */  DISJOINT_CLASSES            (31,  2003),
    /** DISJOINT_DATA               */  DISJOINT_DATA               (37,  2031),
    /** DISJOINT_OBJECT             */  DISJOINT_OBJECT             (41,  2024),
    /** DISJOINT_UNION              */  DISJOINT_UNION              (43,  2004),
    /** ANNOTATION_ASSERTION        */  ANNOTATION_ASSERTION        (47,  2034),
    /** EQUIVALENT_CLASSES          */  EQUIVALENT_CLASSES          (53,  2001),
    /** EQUIVALENT_DATA             */  EQUIVALENT_DATA             (59,  2026),
    /** EQUIVALENT_OBJECT           */  EQUIVALENT_OBJECT           (61,  2012),
    /** FUNCTIONAL_DATA             */  FUNCTIONAL_DATA             (67,  2028),
    /** FUNCTIONAL_OBJECT           */  FUNCTIONAL_OBJECT           (71,  2015),
    /** INVERSE_FUNCTIONAL          */  INVERSE_FUNCTIONAL          (79,  2016),
    /** INVERSE                     */  INVERSE                     (83,  2014),
    /** IRREFLEXIVE                 */  IRREFLEXIVE                 (89,  2021),
    /** NEGATIVE_DATA_ASSERTION     */  NEGATIVE_DATA_ASSERTION     (97,  2011),
    /** NEGATIVE_OBJECT_ASSERTION   */  NEGATIVE_OBJECT_ASSERTION   (101, 2009),
    /** OBJECT_ASSERTION            */  OBJECT_ASSERTION            (103, 2008),
    /** SUB_PROPERTY_CHAIN          */  SUB_PROPERTY_CHAIN          (107, 2025),
    /** OBJECT_DOMAIN               */  OBJECT_DOMAIN               (109, 2022),
    /** OBJECT_RANGE                */  OBJECT_RANGE                (113, 2023),
    /** SUB_OBJECT                  */  SUB_OBJECT                  (127, 2013),
    /** REFLEXIVE                   */  REFLEXIVE                   (131, 2020),
    /** SAME_INDIVIDUAL             */  SAME_INDIVIDUAL             (137, 2006),
    /** SUB_CLASS                   */  SUB_CLASS                   (139, 2002),
    /** SYMMETRIC                   */  SYMMETRIC                   (149, 2017),
    /** TRANSITIVE                  */  TRANSITIVE                  (151, 2019),
    /** CLASS                       */  CLASS                       (157, 1001),
    /** FORALL_DATA                 */  FORALL_DATA                 (163, 3013),
    /** EXACT_DATA                  */  EXACT_DATA                  (167, 3016),
    /** MAX_DATA                    */  MAX_DATA                    (173, 3017),
    /** MIN_DATA                    */  MIN_DATA                    (179, 3015),
    /** SOME_DATA                   */  SOME_DATA                   (181, 3012),
    /** HASVALUE_DATA               */  HASVALUE_DATA               (191, 3014),
    /** FORALL_OBJECT               */  FORALL_OBJECT               (193, 3006),
    /** NOT_OBJECT                  */  NOT_OBJECT                  (197, 3003),
    /** EXACT_OBJECT                */  EXACT_OBJECT                (199, 3009),
    /** AND_OBJECT                  */  AND_OBJECT                  (211, 3001),
    /** MAX_OBJECT                  */  MAX_OBJECT                  (223, 3010),
    /** MIN_OBJECT                  */  MIN_OBJECT                  (227, 3008),
    /** ONEOF_OBJECT                */  ONEOF_OBJECT                (229, 3004),
    /** HASSELF_OBJECT              */  HASSELF_OBJECT              (233, 3011),
    /** SOME_OBJECT                 */  SOME_OBJECT                 (239, 3005),
    /** OR_OBJECT                   */  OR_OBJECT                   (241, 3002),
    /** HASVALUE_OBJECT             */  HASVALUE_OBJECT             (251, 3007),
    /** NOT_DATA                    */  NOT_DATA                    (257, 4002),
    /** ONEOF_DATA                  */  ONEOF_DATA                  (263, 4003),
    /** DATATYPE                    */  DATATYPE                    (269, 4001),
    /** DATATYPE_RESTRICTION        */  DATATYPE_RESTRICTION        (271, 4006),
    /** LITERAL                     */  LITERAL                     (277, 4008),
    /** DATA_PROPERTY               */  DATA_PROPERTY               (283, 1004),
    /** OBJECT_PROPERTY             */  OBJECT_PROPERTY             (293, 1002),
    /** INVERSE_OBJECT              */  INVERSE_OBJECT              (307, 1003),
    /** NAMED_INDIVIDUAL            */  NAMED_INDIVIDUAL            (311, 1005),
    /** FACET_RESTRICTION           */  FACET_RESTRICTION           (563, 4007),
    /** SWRL_RULE                   */  SWRL_RULE                   (631, 2033),
    /** SWRL_CLASS                  */  SWRL_CLASS                  (641, 6001),
    /** SWRL_DATA_RANGE             */  SWRL_DATA_RANGE             (643, 6002),
    /** SWRL_OBJECT_PROPERTY        */  SWRL_OBJECT_PROPERTY        (647, 6003),
    /** SWRL_DATA_PROPERTY          */  SWRL_DATA_PROPERTY          (653, 6004),
    /** SWRL_BUILTIN                */  SWRL_BUILTIN                (659, 6005),
    /** SWRL_VARIABLE               */  SWRL_VARIABLE               (661, 6006),
    /** SWRL_INDIVIDUAL             */  SWRL_INDIVIDUAL             (677, 6007),
    /** SWRL_LITERAL                */  SWRL_LITERAL                (683, 6008),
    /** SWRL_DIFFERENT_INDIVIDUAL   */  SWRL_DIFFERENT_INDIVIDUAL   (797, 6010),
    /** SWRL_SAME_INDIVIDUAL        */  SWRL_SAME_INDIVIDUAL        (811, 6009),
    /** HASKEY                      */  HASKEY                      (821, 2032),
    /** ANNOTATION_DOMAIN           */  ANNOTATION_DOMAIN           (823, 2037),
    /** ANNOTATION_RANGE            */  ANNOTATION_RANGE            (827, 2036),
    /** SUB_ANNOTATION              */  SUB_ANNOTATION              (829, 2035),
    /** AND_DATA                    */  AND_DATA                    (839, 4004),
    /** OR_DATA                     */  OR_DATA                     (853, 4005),
    /** ANNOTATION_PROPERTY         */  ANNOTATION_PROPERTY         (857, 1006),
    /** ANONYMOUS_INDIVIDUAL        */  ANONYMOUS_INDIVIDUAL        (859, 1007),
    /** IRI                         */  IRI                         (863, 0),
    /** ANNOTATION                  */  ANNOTATION                  (877, 5001),
    /** DATATYPE_DEFINITION         */  DATATYPE_DEFINITION         (897, 2038);
    // @formatter:on

    private int hashIndex;
    private int typeIndex;

    private OWLObjectType(int hashIndex, int typeIndex) {
        this.hashIndex = hashIndex;
        this.typeIndex = typeIndex;
    }

    /**
     * @return type index
     */
    public int typeIndex() {
        return typeIndex;
    }

    /**
     * @return hash index
     */
    public int hashIndex() {
        return hashIndex;
    }
}
