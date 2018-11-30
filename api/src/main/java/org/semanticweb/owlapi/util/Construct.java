package org.semanticweb.owlapi.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Construct enum.
 */
public enum Construct {
    //@formatter:off
    /** Role restrictions (domain 
     * and range on data and 
     * object properties). */
    ROLE_DOMAIN_RANGE           ("RRESTR"),
    /** Atomic negations. */
    ATOMIC_NEGATION             ("NEG"),
    /** Complex concept negation. */
    CONCEPT_COMPLEX_NEGATION    ("C"),
    /** Concept union. */
    CONCEPT_UNION               ("U"),
    /** Concept 
     * intersections. */
    CONCEPT_INTERSECTION        ("CINT"),
    /** Universal 
     * restrictions. */
    UNIVERSAL_RESTRICTION       ("UNIVRESTR"),
    /** Limited existential
     * quantifications (Top
     * only). */
    LIMITED_EXISTENTIAL         ("LIMEXIST"),
    /** Full existential 
     * qualification (existential 
     * restrictions that have 
     * fillers other than top. */
    FULL_EXISTENTIAL            ("E", LIMITED_EXISTENTIAL),
    /** Role hierarchy 
     * (rdfs:subPropertyOf). */
    ROLE_HIERARCHY          ("H"),
    /** Transitive roles. */
    ROLE_TRANSITIVE             ("+"),
    /** Reflexivity and property 
     * chains. */
    ROLE_REFLEXIVITY_CHAINS     ("Rr", ROLE_HIERARCHY, ROLE_TRANSITIVE),
    /** Complex role inclusion 
     * axioms; irreflexivity; 
     * role disjointness. */
    ROLE_COMPLEX                ("R", ROLE_REFLEXIVITY_CHAINS, ROLE_HIERARCHY, ROLE_TRANSITIVE),
    /** Nominals. (Enumerated 
     * classes of object value 
     * restrictions: owl:oneOf, 
     * owl:hasValue). */
    NOMINALS                    ("O"),
    /** Inverse properties. */
    ROLE_INVERSE                ("I"),
    /** Functional properties, a 
     * special case of uniqueness 
     * quantification. */
    F                           ("F"),
    /** Cardinality restrictions 
     * (owl:cardinality, 
     * owl:maxCardinality), a 
     * special case of counting 
     * quantification. Filler 
     * can only be top. */
    N                           ("N", F),
    /** Qualified cardinality 
     * restrictions (available 
     * in OWL 2, cardinality 
     * restrictions that have 
     * fillers other than top). */
    Q                           ("Q", N, F),
    /** Use of data properties, 
     * data values or data types. */
    D                           ("(D)"),
    ;
    //@formatter:on

    /**
     * Constructs incompatible with each other - if one is found in an ontology, the other cannot be
     * found. E.g., if Rr and I are found, then Rr is upgraded to R.
     */
    public static EnumSet<Construct> incompatibleRoleFetures =
        EnumSet.of(ROLE_REFLEXIVITY_CHAINS, ROLE_INVERSE);

    private final String s;
    Set<Construct> includedConstructs = Collections.emptySet();

    Construct(String s, Construct... components) {
        this.s = s;
        if (components.length > 0) {
            includedConstructs = new HashSet<>(Arrays.asList(components));
        }
    }

    /**
     * @return constructs occurring within this name. Plain constructs have no components.
     */
    public Set<Construct> includedConstructs() {
        return includedConstructs;
    }

    /**
     * If this construct subsumes another construct (e.g., R subsumes role hierarchy and role
     * transitivity), then the subsumed construct is removed from the set.
     * 
     * @param constructs constructs to trim
     */
    public void removeSubsumedConstructs(Set<Construct> constructs) {
        if (includedConstructs.isEmpty()) {
            return;
        }
        if (constructs.contains(this)) {
            constructs.removeAll(includedConstructs);
        }
    }

    /**
     * Remove all redundant constructs from the input.
     * 
     * @param constructs constructs to trim
     */
    public static void trim(Set<Construct> constructs) {
        for (Construct c : values()) {
            c.removeSubsumedConstructs(constructs);
        }
    }

    @Override
    public String toString() {
        return s;
    }
}
