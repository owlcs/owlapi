package org.semanticweb.owlapi6.model;

import static org.semanticweb.owlapi6.model.accessors.annotationsAsList;
import static org.semanticweb.owlapi6.model.accessors.bodyList;
import static org.semanticweb.owlapi6.model.accessors.facetRestrictionsAsList;
import static org.semanticweb.owlapi6.model.accessors.getAnnSubProperty;
import static org.semanticweb.owlapi6.model.accessors.getAnnSuperProperty;
import static org.semanticweb.owlapi6.model.accessors.getAnnotationValue;
import static org.semanticweb.owlapi6.model.accessors.getArgument;
import static org.semanticweb.owlapi6.model.accessors.getArguments;
import static org.semanticweb.owlapi6.model.accessors.getCardinality;
import static org.semanticweb.owlapi6.model.accessors.getClassExpression;
import static org.semanticweb.owlapi6.model.accessors.getDataRange;
import static org.semanticweb.owlapi6.model.accessors.getDatatype;
import static org.semanticweb.owlapi6.model.accessors.getDomain;
import static org.semanticweb.owlapi6.model.accessors.getEntity;
import static org.semanticweb.owlapi6.model.accessors.getFacet;
import static org.semanticweb.owlapi6.model.accessors.getFacetValue;
import static org.semanticweb.owlapi6.model.accessors.getFiller;
import static org.semanticweb.owlapi6.model.accessors.getFirstArgument;
import static org.semanticweb.owlapi6.model.accessors.getID;
import static org.semanticweb.owlapi6.model.accessors.getIRI;
import static org.semanticweb.owlapi6.model.accessors.getIndividual;
import static org.semanticweb.owlapi6.model.accessors.getInverse;
import static org.semanticweb.owlapi6.model.accessors.getLang;
import static org.semanticweb.owlapi6.model.accessors.getLiteral;
import static org.semanticweb.owlapi6.model.accessors.getObject;
import static org.semanticweb.owlapi6.model.accessors.getOntologyID;
import static org.semanticweb.owlapi6.model.accessors.getOperand;
import static org.semanticweb.owlapi6.model.accessors.getOperandsAsList;
import static org.semanticweb.owlapi6.model.accessors.getPredicate;
import static org.semanticweb.owlapi6.model.accessors.getProperty;
import static org.semanticweb.owlapi6.model.accessors.getPropertyChain;
import static org.semanticweb.owlapi6.model.accessors.getRange;
import static org.semanticweb.owlapi6.model.accessors.getSecondArgument;
import static org.semanticweb.owlapi6.model.accessors.getSubClass;
import static org.semanticweb.owlapi6.model.accessors.getSubProperty;
import static org.semanticweb.owlapi6.model.accessors.getSubject;
import static org.semanticweb.owlapi6.model.accessors.getSuperClass;
import static org.semanticweb.owlapi6.model.accessors.getSuperProperty;
import static org.semanticweb.owlapi6.model.accessors.getSuperPropertyChain;
import static org.semanticweb.owlapi6.model.accessors.getValue;
import static org.semanticweb.owlapi6.model.accessors.getWrappedLiteral;
import static org.semanticweb.owlapi6.model.accessors.headList;
import static org.semanticweb.owlapi6.model.accessors.specificHash;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.compareIterators;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.equalStreams;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;

/**
 * Enumenration for OWLObject types. Allows for enum like operations on all
 * axiom and expression types.
 */
public enum OWLObjectType {
    // @formatter:off
    /** ONTOLOGY                    */  ONTOLOGY                    (0,   1,    OWLOntology.class,                          "Ontology",                 getOntologyID),
    /** ASYMMETRIC                  */  ASYMMETRIC                  (3,   2018, OWLAsymmetricObjectPropertyAxiom.class,     "AsymmetricObjectProperty", getProperty),
    /** CLASS_ASSERTION             */  CLASS_ASSERTION             (7,   2005, OWLClassAssertionAxiom.class,               "ClassAssertion",           getClassExpression, getIndividual),
    /** DATA_ASSERTION              */  DATA_ASSERTION              (11,  2010, OWLDataPropertyAssertionAxiom.class,        "DataPropertyAssertion",    getProperty, getSubject, getObject),
    /** DATA_DOMAIN                 */  DATA_DOMAIN                 (13,  2029, OWLDataPropertyDomainAxiom.class,           "DataPropertyDomain",       getProperty, getDomain),
    /** DATA_RANGE                  */  DATA_RANGE                  (17,  2030, OWLDataPropertyRangeAxiom.class,            "DataPropertyRange",        getProperty, getRange),
    /** SUB_DATA                    */  SUB_DATA                    (19,  2027, OWLSubDataPropertyOfAxiom.class,            "SubDataPropertyOf",        getSubProperty, getSuperProperty),
    /** DECLARATION                 */  DECLARATION                 (23,  2000, OWLDeclarationAxiom.class,                  "Declaration",              getEntity),
    /** DIFFERENT_INDIVIDUALS       */  DIFFERENT_INDIVIDUALS       (29,  2007, OWLDifferentIndividualsAxiom.class,         "DifferentIndividuals",     getOperandsAsList),
    /** DISJOINT_CLASSES            */  DISJOINT_CLASSES            (31,  2003, OWLDisjointClassesAxiom.class,              "DisjointClasses",          getOperandsAsList),
    /** DISJOINT_DATA               */  DISJOINT_DATA               (37,  2031, OWLDisjointDataPropertiesAxiom.class,       "DisjointDataProperties",   getOperandsAsList),
    /** DISJOINT_OBJECT             */  DISJOINT_OBJECT             (41,  2024, OWLDisjointObjectPropertiesAxiom.class,     "DisjointObjectProperties", getOperandsAsList),
    /** DISJOINT_UNION              */  DISJOINT_UNION              (43,  2004, OWLDisjointUnionAxiom.class,                "DisjointUnion",            getClassExpression, getOperandsAsList),
    /** ANNOTATION_ASSERTION        */  ANNOTATION_ASSERTION        (47,  2034, OWLAnnotationAssertionAxiom.class,          "AnnotationAssertion",      getProperty, getSubject, getValue),
    /** EQUIVALENT_CLASSES          */  EQUIVALENT_CLASSES          (53,  2001, OWLEquivalentClassesAxiom.class,            "EquivalentClasses",        getOperandsAsList),
    /** EQUIVALENT_DATA             */  EQUIVALENT_DATA             (59,  2026, OWLEquivalentDataPropertiesAxiom.class,     "EquivalentDataProperties", getOperandsAsList),
    /** EQUIVALENT_OBJECT           */  EQUIVALENT_OBJECT           (61,  2012, OWLEquivalentObjectPropertiesAxiom.class,   "EquivalentObjectProperties",getOperandsAsList),
    /** FUNCTIONAL_DATA             */  FUNCTIONAL_DATA             (67,  2028, OWLFunctionalDataPropertyAxiom.class,       "FunctionalDataProperty",   getProperty),
    /** FUNCTIONAL_OBJECT           */  FUNCTIONAL_OBJECT           (71,  2015, OWLFunctionalObjectPropertyAxiom.class,     "FunctionalObjectProperty", getProperty),
    /** INVERSE_FUNCTIONAL          */  INVERSE_FUNCTIONAL          (79,  2016, OWLInverseFunctionalObjectPropertyAxiom.class,"InverseFunctionalObjectProperty",getProperty),
    /** INVERSE                     */  INVERSE                     (83,  2014, OWLInverseObjectPropertiesAxiom.class,      "InverseObjectProperties",  getOperandsAsList),
    /** IRREFLEXIVE                 */  IRREFLEXIVE                 (89,  2021, OWLIrreflexiveObjectPropertyAxiom.class,    "IrreflexiveObjectProperty",getProperty),
    /** NEGATIVE_DATA_ASSERTION     */  NEGATIVE_DATA_ASSERTION     (97,  2011, OWLNegativeDataPropertyAssertionAxiom.class,"NegativeDataPropertyAssertion",getProperty, getSubject, getObject),
    /** NEGATIVE_OBJECT_ASSERTION   */  NEGATIVE_OBJECT_ASSERTION   (101, 2009, OWLNegativeObjectPropertyAssertionAxiom.class,"NegativeObjectPropertyAssertion",getProperty, getSubject, getObject),
    /** OBJECT_ASSERTION            */  OBJECT_ASSERTION            (103, 2008, OWLObjectPropertyAssertionAxiom.class,      "ObjectPropertyAssertion",  getProperty, getSubject, getObject),
    /** SUB_PROPERTY_CHAIN          */  SUB_PROPERTY_CHAIN          (107, 2025, OWLSubPropertyChainOfAxiom.class,           "SubObjectPropertyOf",      getPropertyChain, getSuperPropertyChain),
    /** OBJECT_DOMAIN               */  OBJECT_DOMAIN               (109, 2022, OWLObjectPropertyDomainAxiom.class,         "ObjectPropertyDomain",     getProperty, getDomain),
    /** OBJECT_RANGE                */  OBJECT_RANGE                (113, 2023, OWLObjectPropertyRangeAxiom.class,          "ObjectPropertyRange",      getProperty, getRange),
    /** SUB_OBJECT                  */  SUB_OBJECT                  (127, 2013, OWLSubObjectPropertyOfAxiom.class,          "SubObjectPropertyOf",      getSubProperty, getSuperProperty),
    /** REFLEXIVE                   */  REFLEXIVE                   (131, 2020, OWLReflexiveObjectPropertyAxiom.class,      "ReflexiveObjectProperty",  getProperty),
    /** SAME_INDIVIDUAL             */  SAME_INDIVIDUAL             (137, 2006, OWLSameIndividualAxiom.class,               "SameIndividual",           getOperandsAsList),
    /** SUB_CLASS                   */  SUB_CLASS                   (139, 2002, OWLSubClassOfAxiom.class,                   "SubClassOf",               getSubClass, getSuperClass),
    /** SYMMETRIC                   */  SYMMETRIC                   (149, 2017, OWLSymmetricObjectPropertyAxiom.class,      "SymmetricObjectProperty",  getProperty),
    /** TRANSITIVE                  */  TRANSITIVE                  (151, 2019, OWLTransitiveObjectPropertyAxiom.class,     "TransitiveObjectProperty", getProperty),
    /** CLASS                       */  CLASS                       (157, 1001, OWLClass.class,                             "Class",                    getIRI),
    /** FORALL_DATA                 */  FORALL_DATA                 (163, 3013, OWLDataAllValuesFrom.class,                 "DataAllValuesFrom",        getProperty, getFiller),
    /** EXACT_DATA                  */  EXACT_DATA                  (167, 3016, OWLDataExactCardinality.class,              "DataExactCardinality",     getCardinality, getProperty, getFiller),
    /** MAX_DATA                    */  MAX_DATA                    (173, 3017, OWLDataMaxCardinality.class,                "DataMaxCardinality",       getCardinality, getProperty, getFiller),
    /** MIN_DATA                    */  MIN_DATA                    (179, 3015, OWLDataMinCardinality.class,                "DataMinCardinality",       getCardinality, getProperty, getFiller),
    /** SOME_DATA                   */  SOME_DATA                   (181, 3012, OWLDataSomeValuesFrom.class,                "DataSomeValuesFrom",       getProperty, getFiller),
    /** HASVALUE_DATA               */  HASVALUE_DATA               (191, 3014, OWLDataHasValue.class,                      "DataHasValue",             getProperty, getFiller),
    /** FORALL_OBJECT               */  FORALL_OBJECT               (193, 3006, OWLObjectAllValuesFrom.class,               "ObjectAllValuesFrom",      getProperty, getFiller),
    /** NOT_OBJECT                  */  NOT_OBJECT                  (197, 3003, OWLObjectComplementOf.class,                "ObjectComplementOf",       getOperand),
    /** EXACT_OBJECT                */  EXACT_OBJECT                (199, 3009, OWLObjectExactCardinality.class,            "ObjectExactCardinality",   getCardinality, getProperty, getFiller),
    /** AND_OBJECT                  */  AND_OBJECT                  (211, 3001, OWLObjectIntersectionOf.class,              "ObjectIntersectionOf",     getOperandsAsList),
    /** MAX_OBJECT                  */  MAX_OBJECT                  (223, 3010, OWLObjectMaxCardinality.class,              "ObjectMaxCardinality",     getCardinality, getProperty, getFiller),
    /** MIN_OBJECT                  */  MIN_OBJECT                  (227, 3008, OWLObjectMinCardinality.class,              "ObjectMinCardinality",     getCardinality, getProperty, getFiller),
    /** ONEOF_OBJECT                */  ONEOF_OBJECT                (229, 3004, OWLObjectOneOf.class,                       "ObjectOneOf",              getOperandsAsList),
    /** HASSELF_OBJECT              */  HASSELF_OBJECT              (233, 3011, OWLObjectHasSelf.class,                     "ObjectHasSelf",            getProperty),
    /** SOME_OBJECT                 */  SOME_OBJECT                 (239, 3005, OWLObjectSomeValuesFrom.class,              "ObjectSomeValuesFrom",     getProperty, getFiller),
    /** OR_OBJECT                   */  OR_OBJECT                   (241, 3002, OWLObjectUnionOf.class,                     "ObjectUnionOf",            getOperandsAsList),
    /** HASVALUE_OBJECT             */  HASVALUE_OBJECT             (251, 3007, OWLObjectHasValue.class,                    "ObjectHasValue",           getProperty, getFiller),
    /** NOT_DATA                    */  NOT_DATA                    (257, 4002, OWLDataComplementOf.class,                  "DataComplementOf",         getDataRange),
    /** ONEOF_DATA                  */  ONEOF_DATA                  (263, 4003, OWLDataOneOf.class,                         "DataOneOf",                getOperandsAsList),
    /** DATATYPE                    */  DATATYPE                    (269, 4001, OWLDatatype.class,                          "Datatype",                 getIRI),
    /** DATATYPE_RESTRICTION        */  DATATYPE_RESTRICTION        (271, 4006, OWLDatatypeRestriction.class,               "DatatypeRestriction",      getDatatype, facetRestrictionsAsList),
    /** LITERAL                     */  LITERAL                     (277, 4008, OWLLiteral.class,                           "",                         getDatatype, getLiteral, getLang) {
        @Override
        public int hashCode(OWLObject o) {
            int hash = hashIndex();
            OWLLiteral l = (OWLLiteral)o;
            hash = hashIteration(hash, l.getDatatype().hashCode());
            hash = hashIteration(hash, specificHash(l) * 65536);
            return hashIteration(hash, l.getLang().hashCode());
        }
    },
    /** DATA_PROPERTY               */  DATA_PROPERTY               (283, 1004, OWLDataProperty.class,                  "DataProperty",     getIRI),
    /** OBJECT_PROPERTY             */  OBJECT_PROPERTY             (293, 1002, OWLObjectProperty.class,                "ObjectProperty",   getIRI),
    /** INVERSE_OBJECT              */  INVERSE_OBJECT              (307, 1003, OWLObjectInverseOf.class,               "ObjectInverseOf",  getInverse),
    /** NAMED_INDIVIDUAL            */  NAMED_INDIVIDUAL            (311, 1005, OWLNamedIndividual.class,               "NamedIndividual",  getIRI),
    /** FACET_RESTRICTION           */  FACET_RESTRICTION           (563, 4007, OWLFacetRestriction.class,              "facetRestriction", getFacet, getFacetValue),
    /** SWRL_RULE                   */  SWRL_RULE                   (631, 2033, SWRLRule.class,                         "DLSafeRule",       bodyList, headList) {
        @Override
        public int hashCode(OWLObject o) {
                int hash = hashIndex();
                // head and body have an order that cannot be changed but it must not
                // affect equals() and hashCode()
                SWRLRule r = (SWRLRule)o;
                hash = hashIteration(hash, r.body().mapToInt(Object::hashCode).sum());
                hash = hashIteration(hash, r.head().mapToInt(Object::hashCode).sum());
                return hashIteration(hash, r.annotationsAsList().hashCode());
        }
    },
    /** SWRL_CLASS                  */  SWRL_CLASS                  (641, 6001, SWRLClassAtom.class,                    "ClassAtom",                getPredicate, getArgument),
    /** SWRL_DATA_RANGE             */  SWRL_DATA_RANGE             (643, 6002, SWRLDataRangeAtom.class,                "DataRangeAtom",            getPredicate, getArgument),
    /** SWRL_OBJECT_PROPERTY        */  SWRL_OBJECT_PROPERTY        (647, 6003, SWRLObjectPropertyAtom.class,           "ObjectPropertyAtom",       getPredicate, getFirstArgument, getSecondArgument),
    /** SWRL_DATA_PROPERTY          */  SWRL_DATA_PROPERTY          (653, 6004, SWRLDataPropertyAtom.class,             "DataPropertyAtom",         getPredicate, getFirstArgument, getSecondArgument),
    /** SWRL_BUILTIN                */  SWRL_BUILTIN                (659, 6005, SWRLBuiltInAtom.class,                  "BuiltInAtom",              getPredicate, getArguments),
    /** SWRL_VARIABLE               */  SWRL_VARIABLE               (661, 6006, SWRLVariable.class,                     "Variable",                 getIRI),
    /** SWRL_INDIVIDUAL             */  SWRL_INDIVIDUAL             (677, 6007, SWRLIndividualArgument.class,           "",                         getIndividual),
    /** SWRL_LITERAL                */  SWRL_LITERAL                (683, 6008, SWRLLiteralArgument.class,              "",                         getWrappedLiteral),
    /** SWRL_DIFFERENT_INDIVIDUAL   */  SWRL_DIFFERENT_INDIVIDUAL   (797, 6010, SWRLDifferentIndividualsAtom.class,     "DifferentFromAtom",        getPredicate, getFirstArgument, getSecondArgument),
    /** SWRL_SAME_INDIVIDUAL        */  SWRL_SAME_INDIVIDUAL        (811, 6009, SWRLSameIndividualAtom.class,           "SameAsAtom",               getPredicate, getFirstArgument, getSecondArgument),
    /** HASKEY                      */  HASKEY                      (821, 2032, OWLHasKeyAxiom.class,                   "HasKey",                   getClassExpression, getOperandsAsList),
    /** ANNOTATION_DOMAIN           */  ANNOTATION_DOMAIN           (823, 2037, OWLAnnotationPropertyDomainAxiom.class, "AnnotationPropertyDomain", getProperty, getDomain),
    /** ANNOTATION_RANGE            */  ANNOTATION_RANGE            (827, 2036, OWLAnnotationPropertyRangeAxiom.class,  "AnnotationPropertyRange",  getProperty, getRange),
    /** SUB_ANNOTATION              */  SUB_ANNOTATION              (829, 2035, OWLSubAnnotationPropertyOfAxiom.class,  "SubAnnotationPropertyOf",  getAnnSubProperty, getAnnSuperProperty),
    /** AND_DATA                    */  AND_DATA                    (839, 4004, OWLDataIntersectionOf.class,            "DataIntersectionOf",       getOperandsAsList),
    /** OR_DATA                     */  OR_DATA                     (853, 4005, OWLDataUnionOf.class,                   "DataUnionOf",              getOperandsAsList),
    /** ANNOTATION_PROPERTY         */  ANNOTATION_PROPERTY         (857, 1006, OWLAnnotationProperty.class,            "AnnotationProperty",       getIRI),
    /** ANONYMOUS_INDIVIDUAL        */  ANONYMOUS_INDIVIDUAL        (859, 1007, OWLAnonymousIndividual.class,           "",                         getID),
    /** IRI                         */  IRI                         (863, 0,    IRI.class,""),
    /** ANNOTATION                  */  ANNOTATION                  (877, 5001, OWLAnnotation.class,                    "Annotation",               getProperty, getAnnotationValue),
    /** DATATYPE_DEFINITION         */  DATATYPE_DEFINITION         (897, 2038, OWLDatatypeDefinitionAxiom.class,       "DatatypeDefinition",       getDatatype, getDataRange);
    // @formatter:on

    private int hashIndex;
    private int typeIndex;
    private Class<? extends OWLObject> type;
    private String name;
    private Function<OWLObject, Object>[] components;
    private Function<OWLObject, Object>[] componentsAnnotationsFirst;
    private Function<OWLObject, Object>[] componentsAnnotationsLast;

    @SafeVarargs
    @SuppressWarnings("unchecked")
    private OWLObjectType(int hashIndex, int typeIndex, Class<? extends OWLObject> c, String name,
        Function<OWLObject, Object>... components) {
        this.hashIndex = hashIndex;
        this.typeIndex = typeIndex;
        type = c;
        this.name = name;
        this.components = components;
        componentsAnnotationsFirst = components;
        componentsAnnotationsLast = components;
        if (HasAnnotations.class.isAssignableFrom(type)) {
            componentsAnnotationsFirst = new Function[components.length + 1];
            componentsAnnotationsLast = new Function[components.length + 1];
            componentsAnnotationsFirst[0] = annotationsAsList;
            componentsAnnotationsLast[components.length] = annotationsAsList;
            System.arraycopy(components, 0, componentsAnnotationsFirst, 1, components.length);
            System.arraycopy(components, 0, componentsAnnotationsLast, 0, components.length);
        }
    }

    /** @return name */
    public String getName() {
        return name;
    }

    /**
     * @param o
     *        object for which hash code is required
     * @return hash code computed on all components
     */
    public int hashCode(OWLObject o) {
        int hash = hashIndex();
        for (Function<OWLObject, Object> f : componentsAnnotationsLast) {
            hash = hashIteration(hash, f.apply(o).hashCode());
        }
        return hash;
    }

    /**
     * Iteration for hash codes
     * 
     * @param a
     *        existing hash
     * @param b
     *        hash to add
     * @return new hash
     */
    static int hashIteration(int a, int b) {
        return a * 37 + b;
    }

    /**
     * @param o
     *        owl object to navigate
     * @return components as a stream. The stream is ordered (by visit order)
     *         but not sorted.
     */
    public Stream<?> components(OWLObject o) {
        return Stream.of(components).map(f -> f.apply(o));
    }

    /**
     * @param o
     *        owl object to navigate
     * @return components as a stream; for objects that can have annotations on
     *         them, the annotation list appears first. The stream is ordered
     *         (by visit order) but not sorted.
     */
    public Stream<?> componentsAnnotationsFirst(OWLObject o) {
        return Stream.of(componentsAnnotationsFirst).map(f -> f.apply(o));
    }

    /**
     * @param o
     *        owl object to navigate
     * @return components as a stream; for objects that can have annotations on
     *         them, the annotation list appears last. The stream is ordered (by
     *         visit order) but not sorted.
     */
    public Stream<?> componentsAnnotationsLast(OWLObject o) {
        return Stream.of(componentsAnnotationsFirst).map(f -> f.apply(o));
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

    /**
     * @param first
     *        an object
     * @param second
     *        an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other. Semantics
     *         is the same as {@link java.util.Objects#equals(Object, Object)}.
     */
    public static boolean equals(@Nullable OWLObject first, @Nullable OWLObject second) {
        if (second == first) {
            return true;
        }
        if (second == null || first == null) {
            return false;
        }
        if (first.type() != second.type() || first.hashCode() != second.hashCode()) {
            return false;
        }
        Function<OWLObject, Object>[] functions = first.type().componentsAnnotationsLast;
        for (Function<OWLObject, Object> f : functions) {
            Object o1 = f.apply(first);
            Object o2 = f.apply(second);
            if (o1 instanceof Stream && o2 instanceof Stream) {
                if (!equalStreams((Stream<?>) o1, (Stream<?>) o2)) {
                    return false;
                }
            } else {
                if (!Objects.equals(o1, o2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param first
     *        first element to compare
     * @param second
     *        second element to compare
     * @return comparison between two objects
     */
    public static int compareTo(OWLObject first, OWLObject second) {
        if (first == second) {
            return 0;
        }
        int diff = Integer.compare(first.typeIndex(), second.typeIndex());
        if (diff != 0) {
            return diff;
        }
        Function<OWLObject, Object>[] functions = first.type().componentsAnnotationsLast;
        for (Function<OWLObject, Object> f : functions) {
            diff = compare(f.apply(first), f.apply(second));
            if (diff != 0) {
                return diff;
            }
        }
        return diff;
    }

    private static final String INCOMPARABLE = "Incomparable types: '%s' with class %s, '%s' with class %s found while comparing iterators";

    @SuppressWarnings("unchecked")
    private static int compare(Object o1, Object o2) {
        if (o1 instanceof Stream && o2 instanceof Stream) {
            return compareIterators(((Stream<?>) o1).iterator(), ((Stream<?>) o2).iterator());
        } else if (o1 instanceof Collection && o2 instanceof Collection) {
            return compareIterators(((Collection<?>) o1).iterator(), ((Collection<?>) o2).iterator());
        } else if (o1 instanceof Comparable && o2 instanceof Comparable) {
            return ((Comparable<Object>) o1).compareTo(o2);
        }
        throw new IllegalArgumentException(String.format(INCOMPARABLE, o1, o1.getClass(), o2, o2.getClass()));
    }
}

interface accessors {

    static int specificHash(OWLLiteral l) {
        try {
            if (l.isInteger()) {
                return l.parseInteger();
            }
            if (l.isDouble()) {
                return (int) l.parseDouble();
            }
            if (l.isFloat()) {
                return (int) l.parseFloat();
            }
            if (l.isBoolean()) {
                return l.parseBoolean() ? 1 : 0;
            }
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            // it is possible that a literal does not have a value that's valid
            // for its datatype; not very useful for a consistent ontology but
            // some W3C reasoner tests use them
        }
        return l.getLiteral().hashCode();
    }

    Function<OWLObject, Object> annotationsAsList = o -> ((HasAnnotations) o).annotationsAsList();
    Function<OWLObject, Object> getLiteral = o -> ((OWLLiteral) o).getLiteral();
    Function<OWLObject, Object> getWrappedLiteral = o -> ((SWRLLiteralArgument) o).getLiteral();
    Function<OWLObject, Object> getLang = o -> ((OWLLiteral) o).getLang();
    Function<OWLObject, Object> getFacet = o -> ((OWLFacetRestriction) o).getFacet();
    Function<OWLObject, Object> getFacetValue = o -> ((OWLFacetRestriction) o).getFacetValue();
    Function<OWLObject, Object> getOntologyID = o -> ((HasOntologyID) o).getOntologyID();
    Function<OWLObject, Object> getPropertyChain = o -> ((OWLSubPropertyChainOfAxiom) o).getPropertyChain();
    Function<OWLObject, Object> getSuperPropertyChain = o -> ((OWLSubPropertyChainOfAxiom) o).getSuperProperty();
    Function<OWLObject, Object> getInverse = o -> ((OWLObjectInverseOf) o).getInverse();
    Function<OWLObject, Object> getSubProperty = o -> ((OWLSubPropertyAxiom<?>) o).getSubProperty();
    Function<OWLObject, Object> getSuperProperty = o -> ((OWLSubPropertyAxiom<?>) o).getSuperProperty();
    Function<OWLObject, Object> getAnnSubProperty = o -> ((OWLSubAnnotationPropertyOfAxiom) o).getSubProperty();
    Function<OWLObject, Object> getAnnSuperProperty = o -> ((OWLSubAnnotationPropertyOfAxiom) o).getSuperProperty();
    Function<OWLObject, Object> getSubClass = o -> ((OWLSubClassOfAxiom) o).getSubClass();
    Function<OWLObject, Object> getSuperClass = o -> ((OWLSubClassOfAxiom) o).getSuperClass();
    Function<OWLObject, Object> getFirstArgument = o -> ((SWRLBinaryAtom<?, ?>) o).getFirstArgument();
    Function<OWLObject, Object> getSecondArgument = o -> ((SWRLBinaryAtom<?, ?>) o).getSecondArgument();
    Function<OWLObject, Object> getPredicate = o -> ((SWRLAtom) o).getPredicate();
    Function<OWLObject, Object> getArguments = o -> ((SWRLBuiltInAtom) o).argumentsAsList();
    Function<OWLObject, Object> getArgument = o -> ((SWRLUnaryAtom<?>) o).getArgument();
    Function<OWLObject, Object> getIRI = o -> ((HasIRI) o).getIRI();
    Function<OWLObject, Object> getEntity = o -> ((OWLDeclarationAxiom) o).getEntity();
    Function<OWLObject, Object> getOperandsAsList = o -> ((HasOperands<?>) o).getOperandsAsList();
    Function<OWLObject, Object> facetRestrictionsAsList = o -> ((OWLDatatypeRestriction) o).facetRestrictionsAsList();
    Function<OWLObject, Object> getOperand = o -> ((OWLObjectComplementOf) o).getOperand();
    Function<OWLObject, Object> getID = o -> ((OWLAnonymousIndividual) o).getID();
    Function<OWLObject, Object> bodyList = o -> ((SWRLRule) o).bodyList();
    Function<OWLObject, Object> headList = o -> ((SWRLRule) o).headList();
    Function<OWLObject, Object> getIndividual = o -> ((HasIndividual) o).getIndividual();
    Function<OWLObject, Object> getClassExpression = o -> ((HasClassExpression) o).getClassExpression();
    Function<OWLObject, Object> getProperty = o -> ((HasProperty<?>) o).getProperty();
    Function<OWLObject, Object> getCardinality = o -> Integer.valueOf(((HasCardinality) o).getCardinality());
    Function<OWLObject, Object> getFiller = o -> ((HasFiller<?>) o).getFiller();
    Function<OWLObject, Object> getAnnotationValue = o -> ((OWLAnnotation) o).getValue();
    Function<OWLObject, Object> getSubject = o -> ((HasSubject<?>) o).getSubject();
    Function<OWLObject, Object> getObject = o -> ((HasObject<?>) o).getObject();
    Function<OWLObject, Object> getValue = o -> ((OWLAnnotationAssertionAxiom) o).getValue();
    Function<OWLObject, Object> getDomain = o -> ((HasDomain<?>) o).getDomain();
    Function<OWLObject, Object> getRange = o -> ((HasRange<?>) o).getRange();
    Function<OWLObject, Object> getDatatype = o -> ((HasDatatype) o).getDatatype();
    Function<OWLObject, Object> getDataRange = o -> ((HasDataRange) o).getDataRange();
}
