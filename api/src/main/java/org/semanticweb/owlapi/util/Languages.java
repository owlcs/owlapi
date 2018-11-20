package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.Construct.ATOMNEG;
import static org.semanticweb.owlapi.util.Construct.C;
import static org.semanticweb.owlapi.util.Construct.CINT;
import static org.semanticweb.owlapi.util.Construct.D;
import static org.semanticweb.owlapi.util.Construct.E;
import static org.semanticweb.owlapi.util.Construct.F;
import static org.semanticweb.owlapi.util.Construct.H;
import static org.semanticweb.owlapi.util.Construct.I;
import static org.semanticweb.owlapi.util.Construct.LIMEXIST;
import static org.semanticweb.owlapi.util.Construct.N;
import static org.semanticweb.owlapi.util.Construct.O;
import static org.semanticweb.owlapi.util.Construct.Q;
import static org.semanticweb.owlapi.util.Construct.R;
import static org.semanticweb.owlapi.util.Construct.RRESTR;
import static org.semanticweb.owlapi.util.Construct.TRAN;
import static org.semanticweb.owlapi.util.Construct.U;
import static org.semanticweb.owlapi.util.Construct.UNIVRESTR;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

/** Enumeration of known DL languages */
public enum Languages {
    //@formatter:off
    /**
     * FL0 - A sub-language of FL-, which is obtained by disallowing limited existential
     * quantification.
     */
    FL0("FL0", CINT, UNIVRESTR),
    /**
     * FL- - A sub-language of FL, which is obtained by disallowing role restriction. This is
     * equivalent to AL without atomic negation.
     */
    FLMINUS("FL-", FL0, LIMEXIST),
    /**
     * FL - Frame based description language, allows:
     * <ul>
     * <li>Concept intersection</li>
     * <li>Universal restrictions</li>
     * <li>Limited existential quantification</li>
     * <li>Role restriction</li>
     * </ul>
     */
    FL("FL", FLMINUS, RRESTR),
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
    AL("AL", FLMINUS, ATOMNEG),
    /**
     * EL - Existential language, allows:
     * <ul>
     * <li>Concept intersection</li>
     * <li>Existential restrictions (of full existential quantification)</li>
     * </ul>
     */
    EL("EL", CINT, E),
    /** ALE language */         ALE("ALE", AL, E),
    /** ALC language */         ALC("ALC", ALE, C, U),
    /** ALCD language. */       ALCD("ALCD", ALC, D),
    /** ALCF language. */       ALCF("ALCF", ALC, F),
    /** ALCFD language. */      ALCFD("ALCFD", ALC, F, D),
    /** ALCN language. */       ALCN("ALCN", ALC, N),
    /** ALCND language. */      ALCND("ALCND", ALC, N, D),
    /** ALCQ language. */       ALCQ("ALCQ", ALC, Q),
    /** ALCQD language. */      ALCQD("ALCQD", ALC, Q, D),
    /** ALCI language. */       ALCI("ALCI", ALC, I),
    /** ALCID language. */      ALCID("ALCID", ALC, I, D),
    /** ALCIF language. */      ALCIF("ALCIF", ALC, I, F),
    /** ALCIFD language. */     ALCIFD("ALCIFD", ALC, I, F, D),
    /** ALCIN language. */      ALCIN("ALCIN", ALC, I, N),
    /** ALCIND language. */     ALCIND("ALCIND", ALC, I, N, D),
    /** ALCIQ language. */      ALCIQ("ALCIQ", ALC, I, Q),
    /** ALCIQD language. */     ALCIQD("ALCIQD", ALC, I, Q, D),
    /** ALCO language. */       ALCO("ALCO", ALC, O),
    /** ALCOD language. */      ALCOD("ALCOD", ALC, O, D),
    /** ALCOF language. */      ALCOF("ALCOF", ALC, O, F),
    /** ALCOFD language. */     ALCOFD("ALCOFD", ALC, O, F, D),
    /** ALCON language. */      ALCON("ALCON", ALC, O, N),
    /** ALCOND language. */     ALCOND("ALCOND", ALC, O, N, D),
    /** ALCOQ language. */      ALCOQ("ALCOQ", ALC, O, Q),
    /** ALCOQD language. */     ALCOQD("ALCOQD", ALC, O, Q, D),
    /** ALCOI language. */      ALCOI("ALCOI", ALC, O, I),
    /** ALCOID language. */     ALCOID("ALCOID", ALC, O, I, D),
    /** ALCOIF language. */     ALCOIF("ALCOIF", ALC, O, I, F),
    /** ALCOIFD language. */    ALCOIFD("ALCOIFD", ALC, O, I, F, D),
    /** ALCOIN language. */     ALCOIN("ALCOIN", ALC, O, I, N),
    /** ALCOIND language. */    ALCOIND("ALCOIND", ALC, O, I, N, D),
    /** ALCOIQ language. */     ALCOIQ("ALCOIQ", ALC, O, I, Q),
    /** ALCOIQD language. */    ALCOIQD("ALCOIQD", ALC, O, I, Q, D),
    /** ALCH language. */       ALCH("ALCH", ALC, H),
    /** ALCHD language. */      ALCHD("ALCHD", ALC, H, D),
    /** ALCHF language. */      ALCHF("ALCHF", ALC, H, F),
    /** ALCHFD language. */     ALCHFD("ALCHFD", ALC, H, F, D),
    /** ALCHN language. */      ALCHN("ALCHN", ALC, H, N),
    /** ALCHND language. */     ALCHND("ALCHND", ALC, H, N, D),
    /** ALCHQ language. */      ALCHQ("ALCHQ", ALC, H, Q),
    /** ALCHQD language. */     ALCHQD("ALCHQD", ALC, H, Q, D),
    /** ALCHI language. */      ALCHI("ALCHI", ALC, H, I),
    /** ALCHID language. */     ALCHID("ALCHID", ALC, H, I, D),
    /** ALCHIF language. */     ALCHIF("ALCHIF", ALC, H, I, F),
    /** ALCHIFD language. */    ALCHIFD("ALCHIFD", ALC, H, I, F, D),
    /** ALCHIN language. */     ALCHIN("ALCHIN", ALC, H, I, N),
    /** ALCHIND language. */    ALCHIND("ALCHIND", ALC, H, I, N, D),
    /** ALCHIQ language. */     ALCHIQ("ALCHIQ", ALC, H, I, Q),
    /** ALCHIQD language. */    ALCHIQD("ALCHIQD", ALC, H, I, Q, D),
    /** ALCHO language. */      ALCHO("ALCHO", ALC, H, O),
    /** ALCHOD language. */     ALCHOD("ALCHOD", ALC, H, O, D),
    /** ALCHOF language. */     ALCHOF("ALCHOF", ALC, H, O, F),
    /** ALCHOFD language. */    ALCHOFD("ALCHOFD", ALC, H, O, F, D),
    /** ALCHON language. */     ALCHON("ALCHON", ALC, H, O, N),
    /** ALCHOND language. */    ALCHOND("ALCHOND", ALC, H, O, N, D),
    /** ALCHOQ language. */     ALCHOQ("ALCHOQ", ALC, H, O, Q),
    /** ALCHOQD language. */    ALCHOQD("ALCHOQD", ALC, H, O, Q, D),
    /** ALCHOI language. */     ALCHOI("ALCHOI", ALC, H, O, I),
    /** ALCHOID language. */    ALCHOID("ALCHOID", ALC, H, O, I, D),
    /** ALCHOIF language. */    ALCHOIF("ALCHOIF", ALC, H, O, I, F),
    /** ALCHOIFD language. */   ALCHOIFD("ALCHOIFD", ALC, H, O, I, F, D),
    /** ALCHOIN language. */    ALCHOIN("ALCHOIN", ALC, H, O, I, N),
    /** ALCHOIND language. */   ALCHOIND("ALCHOIND", ALC, H, O, I, N, D),
    /** ALCHOIQ language. */    ALCHOIQ("ALCHOIQ", ALC, H, O, I, Q),
    /** ALCHOIQD language. */   ALCHOIQD("ALCHOIQD", ALC, H, O, I, Q, D),
    /** ALCR language. */       ALCR("ALCR", ALC, R),
    /** ALCRD language. */      ALCRD("ALCRD", ALC, R, D),
    /** ALCRF language. */      ALCRF("ALCRF", ALC, R, F),
    /** ALCRFD language. */     ALCRFD("ALCRFD", ALC, R, F, D),
    /** ALCRN language. */      ALCRN("ALCRN", ALC, R, N),
    /** ALCRND language. */     ALCRND("ALCRND", ALC, R, N, D),
    /** ALCRQ language. */      ALCRQ("ALCRQ", ALC, R, Q),
    /** ALCRQD language. */     ALCRQD("ALCRQD", ALC, R, Q, D),
    /** ALCRI language. */      ALCRI("ALCRI", ALC, R, I),
    /** ALCRID language. */     ALCRID("ALCRID", ALC, R, I, D),
    /** ALCRIF language. */     ALCRIF("ALCRIF", ALC, R, I, F),
    /** ALCRIFD language. */    ALCRIFD("ALCRIFD", ALC, R, I, F, D),
    /** ALCRIN language. */     ALCRIN("ALCRIN", ALC, R, I, N),
    /** ALCRIND language. */    ALCRIND("ALCRIND", ALC, R, I, N, D),
    /** ALCRIQ language. */     ALCRIQ("ALCRIQ", ALC, R, I, Q),
    /** ALCRIQD language. */    ALCRIQD("ALCRIQD", ALC, R, I, Q, D),
    /** ALCRO language. */      ALCRO("ALCRO", ALC, R, O),
    /** ALCROD language. */     ALCROD("ALCROD", ALC, R, O, D),
    /** ALCROF language. */     ALCROF("ALCROF", ALC, R, O, F),
    /** ALCROFD language. */    ALCROFD("ALCROFD", ALC, R, O, F, D),
    /** ALCRON language. */     ALCRON("ALCRON", ALC, R, O, N),
    /** ALCROND language. */    ALCROND("ALCROND", ALC, R, O, N, D),
    /** ALCROQ language. */     ALCROQ("ALCROQ", ALC, R, O, Q),
    /** ALCROQD language. */    ALCROQD("ALCROQD", ALC, R, O, Q, D),
    /** ALCROI language. */     ALCROI("ALCROI", ALC, R, O, I),
    /** ALCROID language. */    ALCROID("ALCROID", ALC, R, O, I, D),
    /** ALCROIF language. */    ALCROIF("ALCROIF", ALC, R, O, I, F),
    /** ALCROIFD language. */   ALCROIFD("ALCROIFD", ALC, R, O, I, F, D),
    /** ALCROIN language. */    ALCROIN("ALCROIN", ALC, R, O, I, N),
    /** ALCROIND language. */   ALCROIND("ALCROIND", ALC, R, O, I, N, D),
    /** ALCROIQ language. */    ALCROIQ("ALCROIQ", ALC, R, O, I, Q),
    /** ALCROIQD language. */   ALCROIQD("ALCROIQD", ALC, R, O, I, Q, D),
    /** S language. */          S("S", ALC, TRAN),
    /** SD language. */         SD("SD", S, D),
    /** SF language. */         SF("SF", S, F),
    /** SFD language. */        SFD("SFD", S, F, D),
    /** SN language. */         SN("SN", S, N),
    /** SND language. */        SND("SND", S, N, D),
    /** SQ language. */         SQ("SQ", S, Q),
    /** SQD language. */        SQD("SQD", S, Q, D),
    /** SI language. */         SI("SI", S, I),
    /** SID language. */        SID("SID", S, I, D),
    /** SIF language. */        SIF("SIF", S, I, F),
    /** SIFD language. */       SIFD("SIFD", S, I, F, D),
    /** SIN language. */        SIN("SIN", S, I, N),
    /** SIND language. */       SIND("SIND", S, I, N, D),
    /** SIQ language. */        SIQ("SIQ", S, I, Q),
    /** SIQD language. */       SIQD("SIQD", S, I, Q, D),
    /** SO language. */         SO("SO", S, O),
    /** SOD language. */        SOD("SOD", S, O, D),
    /** SOF language. */        SOF("SOF", S, O, F),
    /** SOFD language. */       SOFD("SOFD", S, O, F, D),
    /** SON language. */        SON("SON", S, O, N),
    /** SOND language. */       SOND("SOND", S, O, N, D),
    /** SOQ language. */        SOQ("SOQ", S, O, Q),
    /** SOQD language. */       SOQD("SOQD", S, O, Q, D),
    /** SOI language. */        SOI("SOI", S, O, I),
    /** SOID language. */       SOID("SOID", S, O, I, D),
    /** SOIF language. */       SOIF("SOIF", S, O, I, F),
    /** SOIFD language. */      SOIFD("SOIFD", S, O, I, F, D),
    /** SOIN language. */       SOIN("SOIN", S, O, I, N),
    /** SOIND language. */      SOIND("SOIND", S, O, I, N, D),
    /** SOIQ language. */       SOIQ("SOIQ", S, O, I, Q),
    /** SOIQD language. */      SOIQD("SOIQD", S, O, I, Q, D),
    /** SH language. */         SH("SH", S, H),
    /** SHD language. */        SHD("SHD", S, H, D),
    /** SHF language. */        SHF("SHF", S, H, F),
    /** SHFD language. */       SHFD("SHFD", S, H, F, D),
    /** SHN language. */        SHN("SHN", S, H, N),
    /** SHND language. */       SHND("SHND", S, H, N, D),
    /** SHQ language. */        SHQ("SHQ", S, H, Q),
    /** SHQD language. */       SHQD("SHQD", S, H, Q, D),
    /** SHI language. */        SHI("SHI", S, H, I),
    /** SHID language. */       SHID("SHID", S, H, I, D),
    /** SHIF language. */       SHIF("SHIF", S, H, I, F),
    /** SHIFD language. */      SHIFD("SHIFD", S, H, I, F, D),
    /** SHIN language. */       SHIN("SHIN", S, H, I, N),
    /** SHIND language. */      SHIND("SHIND", S, H, I, N, D),
    /** SHIQ language. */       SHIQ("SHIQ", S, H, I, Q),
    /** SHIQD language. */      SHIQD("SHIQD", S, H, I, Q, D),
    /** SHO language. */        SHO("SHO", S, H, O),
    /** SHOD language. */       SHOD("SHOD", S, H, O, D),
    /** SHOF language. */       SHOF("SHOF", S, H, O, F),
    /** SHOFD language. */      SHOFD("SHOFD", S, H, O, F, D),
    /** SHON language. */       SHON("SHON", S, H, O, N),
    /** SHOND language. */      SHOND("SHOND", S, H, O, N, D),
    /** SHOQ language. */       SHOQ("SHOQ", S, H, O, Q),
    /** SHOQD language. */      SHOQD("SHOQD", S, H, O, Q, D),
    /** SHOI language. */       SHOI("SHOI", S, H, O, I),
    /** SHOID language. */      SHOID("SHOID", S, H, O, I, D),
    /** SHOIF language. */      SHOIF("SHOIF", S, H, O, I, F),
    /** SHOIFD language. */     SHOIFD("SHOIFD", S, H, O, I, F, D),
    /** SHOIN language. */      SHOIN("SHOIN", S, H, O, I, N),
    /** SHOIND language. */     SHOIND("SHOIND", S, H, O, I, N, D),
    /** SHOIQ language. */      SHOIQ("SHOIQ", S, H, O, I, Q),
    /** SHOIQD language. */     SHOIQD("SHOIQD", S, H, O, I, Q, D),
    /** SR language. */         SR("SR", S, R),
    /** SRD language. */        SRD("SRD", S, R, D),
    /** SRF language. */        SRF("SRF", S, R, F),
    /** SRFD language. */       SRFD("SRFD", S, R, F, D),
    /** SRN language. */        SRN("SRN", S, R, N),
    /** SRND language. */       SRND("SRND", S, R, N, D),
    /** SRQ language. */        SRQ("SRQ", S, R, Q),
    /** SRQD language. */       SRQD("SRQD", S, R, Q, D),
    /** SRI language. */        SRI("SRI", S, R, I),
    /** SRID language. */       SRID("SRID", S, R, I, D),
    /** SRIF language. */       SRIF("SRIF", S, R, I, F),
    /** SRIFD language. */      SRIFD("SRIFD", S, R, I, F, D),
    /** SRIN language. */       SRIN("SRIN", S, R, I, N),
    /** SRIND language. */      SRIND("SRIND", S, R, I, N, D),
    /** SRIQ language. */       SRIQ("SRIQ", S, R, I, Q),
    /** SRIQD language. */      SRIQD("SRIQD", S, R, I, Q, D),
    /** SRO language. */        SRO("SRO", S, R, O),
    /** SROD language. */       SROD("SROD", S, R, O, D),
    /** SROF language. */       SROF("SROF", S, R, O, F),
    /** SROFD language. */      SROFD("SROFD", S, R, O, F, D),
    /** SRON language. */       SRON("SRON", S, R, O, N),
    /** SROND language. */      SROND("SROND", S, R, O, N, D),
    /** SROQ language. */       SROQ("SROQ", S, R, O, Q),
    /** SROQD language. */      SROQD("SROQD", S, R, O, Q, D),
    /** SROI language. */       SROI("SROI", S, R, O, I),
    /** SROID language. */      SROID("SROID", S, R, O, I, D),
    /** SROIF language. */      SROIF("SROIF", S, R, O, I, F),
    /** SROIFD language. */     SROIFD("SROIFD", S, R, O, I, F, D),
    /** SROIN language. */      SROIN("SROIN", S, R, O, I, N),
    /** SROIND language. */     SROIND("SROIND", S, R, O, I, N, D),
    /** SROIQ language. */      SROIQ("SROIQ", S, RRESTR, R, O, I, Q),
    /** SROIQD language. */     SROIQD("SROIQD", S, RRESTR, R, O, I, Q, D),
    /** EL++ - Alias ELRO. */   ELPLUSPLUS("EL++", CINT, E, R, O);
    //@formatter:on

    private final String s;
    EnumSet<Construct> components;

    Languages(String s, Languages ancestor, Construct c, Construct... components) {
        this.s = s;
        this.components = EnumSet.of(c, components);
        this.components.addAll(ancestor.components());
    }

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

    public boolean hasAllConstructs(Construct... constructs) {
        return components.containsAll(Arrays.asList(constructs));
    }

    public boolean hasAllConstructs(Collection<Construct> constructs) {
        return components.containsAll(constructs);
    }
}
