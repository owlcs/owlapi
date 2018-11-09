package org.semanticweb.owlapi.utility;

import static org.semanticweb.owlapi.utility.Construct.ATOMNEG;
import static org.semanticweb.owlapi.utility.Construct.C;
import static org.semanticweb.owlapi.utility.Construct.CINT;
import static org.semanticweb.owlapi.utility.Construct.D;
import static org.semanticweb.owlapi.utility.Construct.E;
import static org.semanticweb.owlapi.utility.Construct.F;
import static org.semanticweb.owlapi.utility.Construct.H;
import static org.semanticweb.owlapi.utility.Construct.I;
import static org.semanticweb.owlapi.utility.Construct.LIMEXIST;
import static org.semanticweb.owlapi.utility.Construct.N;
import static org.semanticweb.owlapi.utility.Construct.O;
import static org.semanticweb.owlapi.utility.Construct.Q;
import static org.semanticweb.owlapi.utility.Construct.R;
import static org.semanticweb.owlapi.utility.Construct.RRESTR;
import static org.semanticweb.owlapi.utility.Construct.TRAN;
import static org.semanticweb.owlapi.utility.Construct.U;
import static org.semanticweb.owlapi.utility.Construct.UNIVRESTR;

import java.util.EnumSet;

/** Enumeration of known DL languages */
public enum Languages {
    /**
     * FL0 - A sub-language of FL-, which is obtained by disallowing limited existential
     * quantification.
     */
    FL0("FL0", CINT, UNIVRESTR),
    /**
     * FL- - A sub-language of FL, which is obtained by disallowing role restriction. This is
     * equivalent to AL without atomic negation.
     */
    FLMINUS("FL-", CINT, UNIVRESTR, LIMEXIST),
    /**
     * FL - Frame based description language, allows:
     * <ul>
     * <li>Concept intersection</li>
     * <li>Universal restrictions</li>
     * <li>Limited existential quantification</li>
     * <li>Role restriction</li>
     * </ul>
     */
    FL("FL", CINT, UNIVRESTR, LIMEXIST, RRESTR),
    /**
     * AL - Attributive language. This is the base language which allows:
     * <ul>
     * <li>Atomic negation (negation of concept names that do not appear on the left-hand side of
     * axioms)</li>
     * <li>Concept intersection</li>
     * <li>Universal restrictions</li>
     * <li>Limited existential quantification</li>
     * </ul>
     */
    AL("AL", ATOMNEG, CINT, UNIVRESTR, LIMEXIST),
    /**
     * EL - Existential language, allows:
     * <ul>
     * <li>Concept intersection</li>
     * <li>Existential restrictions (of full existential quantification)</li>
     * </ul>
     */
    EL("EL", CINT, E),
    /** ALE language */
    ALE("ALE", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, E),
    /** ALC language */
    ALC("ALC", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, E),
    /** ALCD language */
    ALCD("ALCD", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, D),
    /** ALCQ language */
    ALCQ("ALCQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, Q),
    /** ALCN language */
    ALCN("ALCN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, N),
    /** ALCF language */
    ALCF("ALCF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, F),
    /** ALCI language */
    ALCI("ALCI", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, I),
    /** ALCO language */
    ALCO("ALCO", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O),
    /** ALCOI language */
    ALCOI("ALCOI", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O, I),
    /** ALCOF language */
    ALCOF("ALCOF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O, F),
    /** ALCIF language */
    ALCIF("ALCIF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, I, F),
    /** ALCIN language */
    ALCIN("ALCIN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, I, N),
    /** ALCON language */
    ALCON("ALCON", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O, N),
    /** ALCOQ language */
    ALCOQ("ALCOQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O, Q),
    /** ALCIQ language */
    ALCIQ("ALCIQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, I, Q),
    /** ALCOIQ language */
    ALCOIQ("ALCOIQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O, I, Q),
    /** ALCOIN language */
    ALCOIN("ALCOIN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O, I, N),
    /** ALCOIF language */
    ALCOIF("ALCOIF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, O, I, F),
    /** S - ALC with transitive roles. */
    S("S", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN),
    /** SI */
    SI("SI", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, I),
    /** SO */
    SO("SO", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O),
    /** SF */
    SF("SF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, F),
    /** SN */
    SN("SN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, N),
    /** SQ */
    SQ("SQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, Q),
    /** SOF */
    SOF("SOF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O, F),
    /** SIF */
    SIF("SIF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, I, F),
    /** SON */
    SON("SON", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O, N),
    /** SIN */
    SIN("SIN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, I, N),
    /** SOQ */
    SOQ("SOQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O, Q),
    /** SIQ */
    SIQ("SIQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, I, Q),
    /** SOI */
    SOI("SOI", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O, I),
    /** SOIF */
    SOIF("SOIF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O, I, F),
    /** SOIN */
    SOIN("SOIN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O, I, N),
    /** SOIQ */
    SOIQ("SOIQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, O, I, Q),
    /** SH */
    SH("SH", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H),
    /** SHF */
    SHF("SHF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, F),
    /** SHN */
    SHN("SHN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, N),
    /** SHO */
    SHO("SHO", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O),
    /** SHI */
    SHI("SHI", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, I),
    /** SHOF */
    SHOF("SHOF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O, F),
    /** SHIF */
    SHIF("SHIF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, I, F),
    /** SHON */
    SHON("SHON", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O, N),
    /** SHQ */
    SHQ("SHQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, Q),
    /** SHOQ */
    SHOQ("SHOQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O, Q),
    /** SHIQ */
    SHIQ("SHIQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, I, O),
    /** SHOI */
    SHOI("SHOI", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O, I),
    /** SHOIF */
    SHOIF("SHOIF", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O, I, F),
    /** SHOIN */
    SHOIN("SHOIN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O, I, N),
    /** SHOIQ */
    SHOIQ("SHOIQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, O, I, Q),
    /** SHIN. */
    SHIN("SHIN", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, I, N),
    /** SHIND. */
    SHIND("SHIND", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, H, I, N, D),
    /** SROIQ. */
    SROIQ("SROIQ", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, RRESTR, O, I, Q),
    /** SROIQD. */
    SROIQD("SROIQD", ATOMNEG, CINT, UNIVRESTR, LIMEXIST, C, U, E, TRAN, RRESTR, O, I, Q, D),
    /** EL++ - Alias for ELRO. */
    ELPLUSPLUS("EL++", CINT, E, R, O);

    private final String s;
    //@formatter:on
    EnumSet<Construct> components;

    Languages(String s, Construct c, Construct... components) {
        this.s = s;
        this.components = EnumSet.of(c, components);
    }

    /**
     * @return constructs occurring within this name. Plain constructs have no components.
     */
    public EnumSet<Construct> components() {
        return components;
    }

    @Override
    public String toString() {
        return s;
    }

    /**
     * @param l language to check
     * @return true if this is a sublanguage of l
     */
    public boolean isSubLanguageOf(Languages l) {
        // assumption: no two languages have the same set of constructs
        return this != l && l.components.containsAll(components);
    }
}
