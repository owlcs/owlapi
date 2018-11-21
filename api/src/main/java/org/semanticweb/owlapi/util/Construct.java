package org.semanticweb.owlapi.util;

/**
 * Construct enum.
 */
public enum Construct {
    //@formatter:off
    /** Role restrictions (domain and range on data and object properties). */
    RRESTR      ("RRESTR"),
    /** C - Complex concept negation. */
    C           ("C"),
    /** U - Concept union. */
    U           ("U"),
    /** E - Full existential qualification (existential restrictions that have fillers other than top. */
    E           ("E"),
    /** H - Role hierarchy (subproperties: rdfs:subPropertyOf). */
    H           ("H"),
    /** R - Limited complex role inclusion axioms; reflexivity and irreflexivity; role disjointness. */
    R           ("R"),
    /** O - Nominals. (Enumerated classes of object value restrictions: owl:oneOf, owl:hasValue). */
    O           ("O"),
    /** I - Inverse properties. */
    I           ("I"),
    /** N - Cardinality restrictions (owl:cardinality, owl:maxCardinality), a special case of counting quantification. */
    N           ("N"),
    /** Q - Qualified cardinality restrictions (available in OWL 2, cardinality restrictions that have fillers other than top). */
    Q           ("Q"),
    /** F - Functional properties, a special case of uniqueness quantification. */
    F           ("F"),
    /** TRAN - Transitive roles. */
    TRAN        ("+"),
    /** D - Use of datatype properties, data values or data types. */
    D           ("(D)"),
    /** Atomic negations. */
    ATOMNEG     ("NEG"),
    /** Concept intersections. */
    CINT        ("CINT"),
    /** Universal restrictions. */
    UNIVRESTR   ("UNIVRESTR"),
    /** Limited existential quantifications (Top only). */
    LIMEXIST    ("LIMEXIST");
    //@formatter:on
    private final String s;
    Construct[] components;

    Construct(String s, Construct... components) {
        this.s = s;
        this.components = components;
    }

    /**
     * @return constructs occurring within this name. Plain constructs have no components.
     */
    public Construct[] components() {
        return components;
    }

    @Override
    public String toString() {
        return s;
    }
}
