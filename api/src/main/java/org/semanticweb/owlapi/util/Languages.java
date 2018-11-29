package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.Construct.ATOMIC_NEGATION;
import static org.semanticweb.owlapi.util.Construct.CONCEPT_COMPLEX_NEGATION;
import static org.semanticweb.owlapi.util.Construct.CONCEPT_INTERSECTION;
import static org.semanticweb.owlapi.util.Construct.CONCEPT_UNION;
import static org.semanticweb.owlapi.util.Construct.D;
import static org.semanticweb.owlapi.util.Construct.F;
import static org.semanticweb.owlapi.util.Construct.FULL_EXISTENTIAL;
import static org.semanticweb.owlapi.util.Construct.LIMITED_EXISTENTIAL;
import static org.semanticweb.owlapi.util.Construct.N;
import static org.semanticweb.owlapi.util.Construct.NOMINALS;
import static org.semanticweb.owlapi.util.Construct.Q;
import static org.semanticweb.owlapi.util.Construct.ROLE_COMPLEX;
import static org.semanticweb.owlapi.util.Construct.ROLE_DOMAIN_RANGE;
import static org.semanticweb.owlapi.util.Construct.ROLE_HIERARCHY;
import static org.semanticweb.owlapi.util.Construct.ROLE_INVERSE;
import static org.semanticweb.owlapi.util.Construct.ROLE_REFLEXIVITY_CHAINS;
import static org.semanticweb.owlapi.util.Construct.ROLE_TRANSITIVE;
import static org.semanticweb.owlapi.util.Construct.UNIVERSAL_RESTRICTION;

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
    FL0("FL0", CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION),
    /**
     * FL- - A sub-language of FL, which is obtained by disallowing role restriction. This is
     * equivalent to AL without atomic negation.
     */
    FLMINUS("FL-", FL0, LIMITED_EXISTENTIAL),
    /**
     * FL - Frame based description language, allows:
     * <ul>
     * <li>Concept intersection</li>
     * <li>Universal restrictions</li>
     * <li>Limited existential quantification</li>
     * <li>Role restriction</li>
     * </ul>
     */
    FL("FL", FLMINUS, ROLE_DOMAIN_RANGE),
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
    AL("AL", FLMINUS, ATOMIC_NEGATION),
    /**
     * EL - Existential language, allows:
     * <ul>
     * <li>Concept intersection</li>
     * <li>Existential restrictions (of full existential quantification)</li>
     * </ul>
     */
    EL("EL", CONCEPT_INTERSECTION, FULL_EXISTENTIAL, ROLE_DOMAIN_RANGE),
    /** ALE language */         ALE("ALE", AL, FULL_EXISTENTIAL),
    /** ALC language */         ALC("ALC", ALE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, ROLE_DOMAIN_RANGE),
    /** S language. */          S("S", ALC, ROLE_TRANSITIVE),
    /** ALCD language. */    ALCD("ALCD",ALC, D),
    /** ALCF language. */    ALCF("ALCF",ALC, F),
    /** ALCFD language. */    ALCFD("ALCFD",ALC, F, D),
    /** ALCN language. */    ALCN("ALCN",ALC, N),
    /** ALCND language. */    ALCND("ALCND",ALC, N, D),
    /** ALCQ language. */    ALCQ("ALCQ",ALC, Q),
    /** ALCQD language. */    ALCQD("ALCQD",ALC, Q, D),
    /** ALCI language. */    ALCI("ALCI",ALC, ROLE_INVERSE),
    /** ALCID language. */    ALCID("ALCID",ALC, ROLE_INVERSE, D),
    /** ALCIF language. */    ALCIF("ALCIF",ALC, ROLE_INVERSE, F),
    /** ALCIFD language. */    ALCIFD("ALCIFD",ALC, ROLE_INVERSE, F, D),
    /** ALCIN language. */    ALCIN("ALCIN",ALC, ROLE_INVERSE, N),
    /** ALCIND language. */    ALCIND("ALCIND",ALC, ROLE_INVERSE, N, D),
    /** ALCIQ language. */    ALCIQ("ALCIQ",ALC, ROLE_INVERSE, Q),
    /** ALCIQD language. */    ALCIQD("ALCIQD",ALC, ROLE_INVERSE, Q, D),
    /** ALCO language. */    ALCO("ALCO",ALC, NOMINALS),
    /** ALCOD language. */    ALCOD("ALCOD",ALC, NOMINALS, D),
    /** ALCOF language. */    ALCOF("ALCOF",ALC, NOMINALS, F),
    /** ALCOFD language. */    ALCOFD("ALCOFD",ALC, NOMINALS, F, D),
    /** ALCON language. */    ALCON("ALCON",ALC, NOMINALS, N),
    /** ALCOND language. */    ALCOND("ALCOND",ALC, NOMINALS, N, D),
    /** ALCOQ language. */    ALCOQ("ALCOQ",ALC, NOMINALS, Q),
    /** ALCOQD language. */    ALCOQD("ALCOQD",ALC, NOMINALS, Q, D),
    /** ALCOI language. */    ALCOI("ALCOI",ALC, NOMINALS, ROLE_INVERSE),
    /** ALCOID language. */    ALCOID("ALCOID",ALC, NOMINALS, ROLE_INVERSE, D),
    /** ALCOIF language. */    ALCOIF("ALCOIF",ALC, NOMINALS, ROLE_INVERSE, F),
    /** ALCOIFD language. */    ALCOIFD("ALCOIFD",ALC, NOMINALS, ROLE_INVERSE, F, D),
    /** ALCOIN language. */    ALCOIN("ALCOIN",ALC, NOMINALS, ROLE_INVERSE, N),
    /** ALCOIND language. */    ALCOIND("ALCOIND",ALC, NOMINALS, ROLE_INVERSE, N, D),
    /** ALCOIQ language. */    ALCOIQ("ALCOIQ",ALC, NOMINALS, ROLE_INVERSE, Q),
    /** ALCOIQD language. */    ALCOIQD("ALCOIQD",ALC, NOMINALS, ROLE_INVERSE, Q, D),
    /** ALCH language. */    ALCH("ALCH",ALC, ROLE_HIERARCHY),
    /** ALCHD language. */    ALCHD("ALCHD",ALC, ROLE_HIERARCHY, D),
    /** ALCHF language. */    ALCHF("ALCHF",ALC, ROLE_HIERARCHY, F),
    /** ALCHFD language. */    ALCHFD("ALCHFD",ALC, ROLE_HIERARCHY, F, D),
    /** ALCHN language. */    ALCHN("ALCHN",ALC, ROLE_HIERARCHY, N),
    /** ALCHND language. */    ALCHND("ALCHND",ALC, ROLE_HIERARCHY, N, D),
    /** ALCHQ language. */    ALCHQ("ALCHQ",ALC, ROLE_HIERARCHY, Q),
    /** ALCHQD language. */    ALCHQD("ALCHQD",ALC, ROLE_HIERARCHY, Q, D),
    /** ALCHI language. */    ALCHI("ALCHI",ALC, ROLE_HIERARCHY, ROLE_INVERSE),
    /** ALCHID language. */    ALCHID("ALCHID",ALC, ROLE_HIERARCHY, ROLE_INVERSE, D),
    /** ALCHIF language. */    ALCHIF("ALCHIF",ALC, ROLE_HIERARCHY, ROLE_INVERSE, F),
    /** ALCHIFD language. */    ALCHIFD("ALCHIFD",ALC, ROLE_HIERARCHY, ROLE_INVERSE, F, D),
    /** ALCHIN language. */    ALCHIN("ALCHIN",ALC, ROLE_HIERARCHY, ROLE_INVERSE, N),
    /** ALCHIND language. */    ALCHIND("ALCHIND",ALC, ROLE_HIERARCHY, ROLE_INVERSE, N, D),
    /** ALCHIQ language. */    ALCHIQ("ALCHIQ",ALC, ROLE_HIERARCHY, ROLE_INVERSE, Q),
    /** ALCHIQD language. */    ALCHIQD("ALCHIQD",ALC, ROLE_HIERARCHY, ROLE_INVERSE, Q, D),
    /** ALCHO language. */    ALCHO("ALCHO",ALC, ROLE_HIERARCHY, NOMINALS),
    /** ALCHOD language. */    ALCHOD("ALCHOD",ALC, ROLE_HIERARCHY, NOMINALS, D),
    /** ALCHOF language. */    ALCHOF("ALCHOF",ALC, ROLE_HIERARCHY, NOMINALS, F),
    /** ALCHOFD language. */    ALCHOFD("ALCHOFD",ALC, ROLE_HIERARCHY, NOMINALS, F, D),
    /** ALCHON language. */    ALCHON("ALCHON",ALC, ROLE_HIERARCHY, NOMINALS, N),
    /** ALCHOND language. */    ALCHOND("ALCHOND",ALC, ROLE_HIERARCHY, NOMINALS, N, D),
    /** ALCHOQ language. */    ALCHOQ("ALCHOQ",ALC, ROLE_HIERARCHY, NOMINALS, Q),
    /** ALCHOQD language. */    ALCHOQD("ALCHOQD",ALC, ROLE_HIERARCHY, NOMINALS, Q, D),
    /** ALCHOI language. */    ALCHOI("ALCHOI",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE),
    /** ALCHOID language. */    ALCHOID("ALCHOID",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, D),
    /** ALCHOIF language. */    ALCHOIF("ALCHOIF",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F),
    /** ALCHOIFD language. */    ALCHOIFD("ALCHOIFD",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, D),
    /** ALCHOIN language. */    ALCHOIN("ALCHOIN",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N),
    /** ALCHOIND language. */    ALCHOIND("ALCHOIND",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, D),
    /** ALCHOIQ language. */    ALCHOIQ("ALCHOIQ",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q),
    /** ALCHOIQD language. */    ALCHOIQD("ALCHOIQD",ALC, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, D),
    /** ALCRr language. */    ALCRr("ALCRr",ALC, ROLE_REFLEXIVITY_CHAINS),
    /** ALCRrD language. */    ALCRrD("ALCRrD",ALC, ROLE_REFLEXIVITY_CHAINS, D),
    /** ALCRrF language. */    ALCRrF("ALCRrF",ALC, ROLE_REFLEXIVITY_CHAINS, F),
    /** ALCRrFD language. */    ALCRrFD("ALCRrFD",ALC, ROLE_REFLEXIVITY_CHAINS, F, D),
    /** ALCRrN language. */    ALCRrN("ALCRrN",ALC, ROLE_REFLEXIVITY_CHAINS, N),
    /** ALCRrND language. */    ALCRrND("ALCRrND",ALC, ROLE_REFLEXIVITY_CHAINS, N, D),
    /** ALCRrQ language. */    ALCRrQ("ALCRrQ",ALC, ROLE_REFLEXIVITY_CHAINS, Q),
    /** ALCRrQD language. */    ALCRrQD("ALCRrQD",ALC, ROLE_REFLEXIVITY_CHAINS, Q, D),
    /** ALCRrO language. */    ALCRrO("ALCRrO",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS),
    /** ALCRrOD language. */    ALCRrOD("ALCRrOD",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS, D),
    /** ALCRrOF language. */    ALCRrOF("ALCRrOF",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F),
    /** ALCRrOFD language. */    ALCRrOFD("ALCRrOFD",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, D),
    /** ALCRrON language. */    ALCRrON("ALCRrON",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N),
    /** ALCRrOND language. */    ALCRrOND("ALCRrOND",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, D),
    /** ALCRrOQ language. */    ALCRrOQ("ALCRrOQ",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q),
    /** ALCRrOQD language. */    ALCRrOQD("ALCRrOQD",ALC, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, D),
    /** ALCR language. */    ALCR("ALCR",ALC, ROLE_COMPLEX),
    /** ALCRD language. */    ALCRD("ALCRD",ALC, ROLE_COMPLEX, D),
    /** ALCRF language. */    ALCRF("ALCRF",ALC, ROLE_COMPLEX, F),
    /** ALCRFD language. */    ALCRFD("ALCRFD",ALC, ROLE_COMPLEX, F, D),
    /** ALCRN language. */    ALCRN("ALCRN",ALC, ROLE_COMPLEX, N),
    /** ALCRND language. */    ALCRND("ALCRND",ALC, ROLE_COMPLEX, N, D),
    /** ALCRQ language. */    ALCRQ("ALCRQ",ALC, ROLE_COMPLEX, Q),
    /** ALCRQD language. */    ALCRQD("ALCRQD",ALC, ROLE_COMPLEX, Q, D),
    /** ALCRI language. */    ALCRI("ALCRI",ALC, ROLE_COMPLEX, ROLE_INVERSE),
    /** ALCRID language. */    ALCRID("ALCRID",ALC, ROLE_COMPLEX, ROLE_INVERSE, D),
    /** ALCRIF language. */    ALCRIF("ALCRIF",ALC, ROLE_COMPLEX, ROLE_INVERSE, F),
    /** ALCRIFD language. */    ALCRIFD("ALCRIFD",ALC, ROLE_COMPLEX, ROLE_INVERSE, F, D),
    /** ALCRIN language. */    ALCRIN("ALCRIN",ALC, ROLE_COMPLEX, ROLE_INVERSE, N),
    /** ALCRIND language. */    ALCRIND("ALCRIND",ALC, ROLE_COMPLEX, ROLE_INVERSE, N, D),
    /** ALCRIQ language. */    ALCRIQ("ALCRIQ",ALC, ROLE_COMPLEX, ROLE_INVERSE, Q),
    /** ALCRIQD language. */    ALCRIQD("ALCRIQD",ALC, ROLE_COMPLEX, ROLE_INVERSE, Q, D),
    /** ALCRO language. */    ALCRO("ALCRO",ALC, ROLE_COMPLEX, NOMINALS),
    /** ALCROD language. */    ALCROD("ALCROD",ALC, ROLE_COMPLEX, NOMINALS, D),
    /** ALCROF language. */    ALCROF("ALCROF",ALC, ROLE_COMPLEX, NOMINALS, F),
    /** ALCROFD language. */    ALCROFD("ALCROFD",ALC, ROLE_COMPLEX, NOMINALS, F, D),
    /** ALCRON language. */    ALCRON("ALCRON",ALC, ROLE_COMPLEX, NOMINALS, N),
    /** ALCROND language. */    ALCROND("ALCROND",ALC, ROLE_COMPLEX, NOMINALS, N, D),
    /** ALCROQ language. */    ALCROQ("ALCROQ",ALC, ROLE_COMPLEX, NOMINALS, Q),
    /** ALCROQD language. */    ALCROQD("ALCROQD",ALC, ROLE_COMPLEX, NOMINALS, Q, D),
    /** ALCROI language. */    ALCROI("ALCROI",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE),
    /** ALCROID language. */    ALCROID("ALCROID",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, D),
    /** ALCROIF language. */    ALCROIF("ALCROIF",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F),
    /** ALCROIFD language. */    ALCROIFD("ALCROIFD",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, D),
    /** ALCROIN language. */    ALCROIN("ALCROIN",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N),
    /** ALCROIND language. */    ALCROIND("ALCROIND",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, D),
    /** ALCROIQ language. */    ALCROIQ("ALCROIQ",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q),
    /** ALCROIQD language. */    ALCROIQD("ALCROIQD",ALC, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, D),
    /** SD language. */    SD("SD",S, D),
    /** SF language. */    SF("SF",S, F),
    /** SFD language. */    SFD("SFD",S, F, D),
    /** SN language. */    SN("SN",S, N),
    /** SND language. */    SND("SND",S, N, D),
    /** SQ language. */    SQ("SQ",S, Q),
    /** SQD language. */    SQD("SQD",S, Q, D),
    /** SI language. */    SI("SI",S, ROLE_INVERSE),
    /** SID language. */    SID("SID",S, ROLE_INVERSE, D),
    /** SIF language. */    SIF("SIF",S, ROLE_INVERSE, F),
    /** SIFD language. */    SIFD("SIFD",S, ROLE_INVERSE, F, D),
    /** SIN language. */    SIN("SIN",S, ROLE_INVERSE, N),
    /** SIND language. */    SIND("SIND",S, ROLE_INVERSE, N, D),
    /** SIQ language. */    SIQ("SIQ",S, ROLE_INVERSE, Q),
    /** SIQD language. */    SIQD("SIQD",S, ROLE_INVERSE, Q, D),
    /** SO language. */    SO("SO",S, NOMINALS),
    /** SOD language. */    SOD("SOD",S, NOMINALS, D),
    /** SOF language. */    SOF("SOF",S, NOMINALS, F),
    /** SOFD language. */    SOFD("SOFD",S, NOMINALS, F, D),
    /** SON language. */    SON("SON",S, NOMINALS, N),
    /** SOND language. */    SOND("SOND",S, NOMINALS, N, D),
    /** SOQ language. */    SOQ("SOQ",S, NOMINALS, Q),
    /** SOQD language. */    SOQD("SOQD",S, NOMINALS, Q, D),
    /** SOI language. */    SOI("SOI",S, NOMINALS, ROLE_INVERSE),
    /** SOID language. */    SOID("SOID",S, NOMINALS, ROLE_INVERSE, D),
    /** SOIF language. */    SOIF("SOIF",S, NOMINALS, ROLE_INVERSE, F),
    /** SOIFD language. */    SOIFD("SOIFD",S, NOMINALS, ROLE_INVERSE, F, D),
    /** SOIN language. */    SOIN("SOIN",S, NOMINALS, ROLE_INVERSE, N),
    /** SOIND language. */    SOIND("SOIND",S, NOMINALS, ROLE_INVERSE, N, D),
    /** SOIQ language. */    SOIQ("SOIQ",S, NOMINALS, ROLE_INVERSE, Q),
    /** SOIQD language. */    SOIQD("SOIQD",S, NOMINALS, ROLE_INVERSE, Q, D),
    /** SH language. */    SH("SH",S, ROLE_HIERARCHY),
    /** SHD language. */    SHD("SHD",S, ROLE_HIERARCHY, D),
    /** SHF language. */    SHF("SHF",S, ROLE_HIERARCHY, F),
    /** SHFD language. */    SHFD("SHFD",S, ROLE_HIERARCHY, F, D),
    /** SHN language. */    SHN("SHN",S, ROLE_HIERARCHY, N),
    /** SHND language. */    SHND("SHND",S, ROLE_HIERARCHY, N, D),
    /** SHQ language. */    SHQ("SHQ",S, ROLE_HIERARCHY, Q),
    /** SHQD language. */    SHQD("SHQD",S, ROLE_HIERARCHY, Q, D),
    /** SHI language. */    SHI("SHI",S, ROLE_HIERARCHY, ROLE_INVERSE),
    /** SHID language. */    SHID("SHID",S, ROLE_HIERARCHY, ROLE_INVERSE, D),
    /** SHIF language. */    SHIF("SHIF",S, ROLE_HIERARCHY, ROLE_INVERSE, F),
    /** SHIFD language. */    SHIFD("SHIFD",S, ROLE_HIERARCHY, ROLE_INVERSE, F, D),
    /** SHIN language. */    SHIN("SHIN",S, ROLE_HIERARCHY, ROLE_INVERSE, N),
    /** SHIND language. */    SHIND("SHIND",S, ROLE_HIERARCHY, ROLE_INVERSE, N, D),
    /** SHIQ language. */    SHIQ("SHIQ",S, ROLE_HIERARCHY, ROLE_INVERSE, Q),
    /** SHIQD language. */    SHIQD("SHIQD",S, ROLE_HIERARCHY, ROLE_INVERSE, Q, D),
    /** SHO language. */    SHO("SHO",S, ROLE_HIERARCHY, NOMINALS),
    /** SHOD language. */    SHOD("SHOD",S, ROLE_HIERARCHY, NOMINALS, D),
    /** SHOF language. */    SHOF("SHOF",S, ROLE_HIERARCHY, NOMINALS, F),
    /** SHOFD language. */    SHOFD("SHOFD",S, ROLE_HIERARCHY, NOMINALS, F, D),
    /** SHON language. */    SHON("SHON",S, ROLE_HIERARCHY, NOMINALS, N),
    /** SHOND language. */    SHOND("SHOND",S, ROLE_HIERARCHY, NOMINALS, N, D),
    /** SHOQ language. */    SHOQ("SHOQ",S, ROLE_HIERARCHY, NOMINALS, Q),
    /** SHOQD language. */    SHOQD("SHOQD",S, ROLE_HIERARCHY, NOMINALS, Q, D),
    /** SHOI language. */    SHOI("SHOI",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE),
    /** SHOID language. */    SHOID("SHOID",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, D),
    /** SHOIF language. */    SHOIF("SHOIF",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F),
    /** SHOIFD language. */    SHOIFD("SHOIFD",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, D),
    /** SHOIN language. */    SHOIN("SHOIN",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N),
    /** SHOIND language. */    SHOIND("SHOIND",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, D),
    /** SHOIQ language. */    SHOIQ("SHOIQ",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q),
    /** SHOIQD language. */    SHOIQD("SHOIQD",S, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, D),
    /** SRr language. */    SRr("SRr",S, ROLE_REFLEXIVITY_CHAINS),
    /** SRrD language. */    SRrD("SRrD",S, ROLE_REFLEXIVITY_CHAINS, D),
    /** SRrF language. */    SRrF("SRrF",S, ROLE_REFLEXIVITY_CHAINS, F),
    /** SRrFD language. */    SRrFD("SRrFD",S, ROLE_REFLEXIVITY_CHAINS, F, D),
    /** SRrN language. */    SRrN("SRrN",S, ROLE_REFLEXIVITY_CHAINS, N),
    /** SRrND language. */    SRrND("SRrND",S, ROLE_REFLEXIVITY_CHAINS, N, D),
    /** SRrQ language. */    SRrQ("SRrQ",S, ROLE_REFLEXIVITY_CHAINS, Q),
    /** SRrQD language. */    SRrQD("SRrQD",S, ROLE_REFLEXIVITY_CHAINS, Q, D),
    /** SRrO language. */    SRrO("SRrO",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS),
    /** SRrOD language. */    SRrOD("SRrOD",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS, D),
    /** SRrOF language. */    SRrOF("SRrOF",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F),
    /** SRrOFD language. */    SRrOFD("SRrOFD",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, D),
    /** SRrON language. */    SRrON("SRrON",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N),
    /** SRrOND language. */    SRrOND("SRrOND",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, D),
    /** SRrOQ language. */    SRrOQ("SRrOQ",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q),
    /** SRrOQD language. */    SRrOQD("SRrOQD",S, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, D),
    /** SR language. */    SR("SR",S, ROLE_COMPLEX),
    /** SRD language. */    SRD("SRD",S, ROLE_COMPLEX, D),
    /** SRF language. */    SRF("SRF",S, ROLE_COMPLEX, F),
    /** SRFD language. */    SRFD("SRFD",S, ROLE_COMPLEX, F, D),
    /** SRN language. */    SRN("SRN",S, ROLE_COMPLEX, N),
    /** SRND language. */    SRND("SRND",S, ROLE_COMPLEX, N, D),
    /** SRQ language. */    SRQ("SRQ",S, ROLE_COMPLEX, Q),
    /** SRQD language. */    SRQD("SRQD",S, ROLE_COMPLEX, Q, D),
    /** SRI language. */    SRI("SRI",S, ROLE_COMPLEX, ROLE_INVERSE),
    /** SRID language. */    SRID("SRID",S, ROLE_COMPLEX, ROLE_INVERSE, D),
    /** SRIF language. */    SRIF("SRIF",S, ROLE_COMPLEX, ROLE_INVERSE, F),
    /** SRIFD language. */    SRIFD("SRIFD",S, ROLE_COMPLEX, ROLE_INVERSE, F, D),
    /** SRIN language. */    SRIN("SRIN",S, ROLE_COMPLEX, ROLE_INVERSE, N),
    /** SRIND language. */    SRIND("SRIND",S, ROLE_COMPLEX, ROLE_INVERSE, N, D),
    /** SRIQ language. */    SRIQ("SRIQ",S, ROLE_COMPLEX, ROLE_INVERSE, Q),
    /** SRIQD language. */    SRIQD("SRIQD",S, ROLE_COMPLEX, ROLE_INVERSE, Q, D),
    /** SRO language. */    SRO("SRO",S, ROLE_COMPLEX, NOMINALS),
    /** SROD language. */    SROD("SROD",S, ROLE_COMPLEX, NOMINALS, D),
    /** SROF language. */    SROF("SROF",S, ROLE_COMPLEX, NOMINALS, F),
    /** SROFD language. */    SROFD("SROFD",S, ROLE_COMPLEX, NOMINALS, F, D),
    /** SRON language. */    SRON("SRON",S, ROLE_COMPLEX, NOMINALS, N),
    /** SROND language. */    SROND("SROND",S, ROLE_COMPLEX, NOMINALS, N, D),
    /** SROQ language. */    SROQ("SROQ",S, ROLE_COMPLEX, NOMINALS, Q),
    /** SROQD language. */    SROQD("SROQD",S, ROLE_COMPLEX, NOMINALS, Q, D),
    /** SROI language. */    SROI("SROI",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE),
    /** SROID language. */    SROID("SROID",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, D),
    /** SROIF language. */    SROIF("SROIF",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F),
    /** SROIFD language. */    SROIFD("SROIFD",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, D),
    /** SROIN language. */    SROIN("SROIN",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N),
    /** SROIND language. */    SROIND("SROIND",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, D),
    /** SROIQ language. */    SROIQ("SROIQ",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q),
    /** SROIQD language. */    SROIQD("SROIQD",S, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, D),
    /** EL++ - Alias ELRO. */   ELPLUSPLUS("EL++", CONCEPT_INTERSECTION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_TRANSITIVE, D);
    //@formatter:on

    private final String s;
    EnumSet<Construct> components;

    Languages(String s, Languages ancestor, Construct c, Construct... components) {
        this.s = s;
        this.components = EnumSet.of(c, components);
        this.components.addAll(ancestor.components());
        if (this.components.containsAll(Construct.incompatibleRoleFetures)) {
            throw new IllegalArgumentException("Incompatible constructs: ["
                + Construct.incompatibleRoleFetures + "] cannot appear together.");
        }
    }

    Languages(String s, Construct c, Construct... components) {
        this.s = s;
        this.components = EnumSet.of(c, components);
        if (this.components.containsAll(Construct.incompatibleRoleFetures)) {
            throw new IllegalArgumentException("Incompatible constructs: ["
                + Construct.incompatibleRoleFetures + "] cannot appear together.");
        }
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

    /**
     * @param constructs constructs to compare
     * @return true if all constructs appear in the components
     */
    public boolean hasAllConstructs(Construct... constructs) {
        return components.containsAll(Arrays.asList(constructs));
    }

    /**
     * @param constructs constructs to compare
     * @return true if all constructs appear in the components
     */
    public boolean hasAllConstructs(Collection<Construct> constructs) {
        return components.containsAll(constructs);
    }
}
