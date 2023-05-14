package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.utility.Construct.ATOMIC_NEGATION;
import static org.semanticweb.owlapi.utility.Construct.CONCEPT_COMPLEX_NEGATION;
import static org.semanticweb.owlapi.utility.Construct.CONCEPT_INTERSECTION;
import static org.semanticweb.owlapi.utility.Construct.CONCEPT_UNION;
import static org.semanticweb.owlapi.utility.Construct.D;
import static org.semanticweb.owlapi.utility.Construct.F;
import static org.semanticweb.owlapi.utility.Construct.FULL_EXISTENTIAL;
import static org.semanticweb.owlapi.utility.Construct.LIMITED_EXISTENTIAL;
import static org.semanticweb.owlapi.utility.Construct.N;
import static org.semanticweb.owlapi.utility.Construct.NOMINALS;
import static org.semanticweb.owlapi.utility.Construct.Q;
import static org.semanticweb.owlapi.utility.Construct.ROLE_COMPLEX;
import static org.semanticweb.owlapi.utility.Construct.ROLE_DOMAIN_RANGE;
import static org.semanticweb.owlapi.utility.Construct.ROLE_HIERARCHY;
import static org.semanticweb.owlapi.utility.Construct.ROLE_INVERSE;
import static org.semanticweb.owlapi.utility.Construct.ROLE_REFLEXIVITY_CHAINS;
import static org.semanticweb.owlapi.utility.Construct.ROLE_TRANSITIVE;
import static org.semanticweb.owlapi.utility.Construct.UNIVERSAL_RESTRICTION;
import static org.semanticweb.owlapi.utility.Languages.AL;
import static org.semanticweb.owlapi.utility.Languages.ALC;
import static org.semanticweb.owlapi.utility.Languages.ALCD;
import static org.semanticweb.owlapi.utility.Languages.ALCF;
import static org.semanticweb.owlapi.utility.Languages.ALCFD;
import static org.semanticweb.owlapi.utility.Languages.ALCH;
import static org.semanticweb.owlapi.utility.Languages.ALCHD;
import static org.semanticweb.owlapi.utility.Languages.ALCHF;
import static org.semanticweb.owlapi.utility.Languages.ALCHFD;
import static org.semanticweb.owlapi.utility.Languages.ALCHI;
import static org.semanticweb.owlapi.utility.Languages.ALCHID;
import static org.semanticweb.owlapi.utility.Languages.ALCHIF;
import static org.semanticweb.owlapi.utility.Languages.ALCHIFD;
import static org.semanticweb.owlapi.utility.Languages.ALCHIN;
import static org.semanticweb.owlapi.utility.Languages.ALCHIND;
import static org.semanticweb.owlapi.utility.Languages.ALCHIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCHIQD;
import static org.semanticweb.owlapi.utility.Languages.ALCHN;
import static org.semanticweb.owlapi.utility.Languages.ALCHND;
import static org.semanticweb.owlapi.utility.Languages.ALCHO;
import static org.semanticweb.owlapi.utility.Languages.ALCHOD;
import static org.semanticweb.owlapi.utility.Languages.ALCHOF;
import static org.semanticweb.owlapi.utility.Languages.ALCHOFD;
import static org.semanticweb.owlapi.utility.Languages.ALCHOI;
import static org.semanticweb.owlapi.utility.Languages.ALCHOID;
import static org.semanticweb.owlapi.utility.Languages.ALCHOIF;
import static org.semanticweb.owlapi.utility.Languages.ALCHOIFD;
import static org.semanticweb.owlapi.utility.Languages.ALCHOIN;
import static org.semanticweb.owlapi.utility.Languages.ALCHOIND;
import static org.semanticweb.owlapi.utility.Languages.ALCHOIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCHOIQD;
import static org.semanticweb.owlapi.utility.Languages.ALCHON;
import static org.semanticweb.owlapi.utility.Languages.ALCHOND;
import static org.semanticweb.owlapi.utility.Languages.ALCHOQ;
import static org.semanticweb.owlapi.utility.Languages.ALCHOQD;
import static org.semanticweb.owlapi.utility.Languages.ALCHQ;
import static org.semanticweb.owlapi.utility.Languages.ALCHQD;
import static org.semanticweb.owlapi.utility.Languages.ALCI;
import static org.semanticweb.owlapi.utility.Languages.ALCID;
import static org.semanticweb.owlapi.utility.Languages.ALCIF;
import static org.semanticweb.owlapi.utility.Languages.ALCIFD;
import static org.semanticweb.owlapi.utility.Languages.ALCIN;
import static org.semanticweb.owlapi.utility.Languages.ALCIND;
import static org.semanticweb.owlapi.utility.Languages.ALCIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCIQD;
import static org.semanticweb.owlapi.utility.Languages.ALCN;
import static org.semanticweb.owlapi.utility.Languages.ALCND;
import static org.semanticweb.owlapi.utility.Languages.ALCO;
import static org.semanticweb.owlapi.utility.Languages.ALCOD;
import static org.semanticweb.owlapi.utility.Languages.ALCOF;
import static org.semanticweb.owlapi.utility.Languages.ALCOFD;
import static org.semanticweb.owlapi.utility.Languages.ALCOI;
import static org.semanticweb.owlapi.utility.Languages.ALCOID;
import static org.semanticweb.owlapi.utility.Languages.ALCOIF;
import static org.semanticweb.owlapi.utility.Languages.ALCOIFD;
import static org.semanticweb.owlapi.utility.Languages.ALCOIN;
import static org.semanticweb.owlapi.utility.Languages.ALCOIND;
import static org.semanticweb.owlapi.utility.Languages.ALCOIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCOIQD;
import static org.semanticweb.owlapi.utility.Languages.ALCON;
import static org.semanticweb.owlapi.utility.Languages.ALCOND;
import static org.semanticweb.owlapi.utility.Languages.ALCOQ;
import static org.semanticweb.owlapi.utility.Languages.ALCOQD;
import static org.semanticweb.owlapi.utility.Languages.ALCQ;
import static org.semanticweb.owlapi.utility.Languages.ALCQD;
import static org.semanticweb.owlapi.utility.Languages.ALCR;
import static org.semanticweb.owlapi.utility.Languages.ALCRD;
import static org.semanticweb.owlapi.utility.Languages.ALCRF;
import static org.semanticweb.owlapi.utility.Languages.ALCRFD;
import static org.semanticweb.owlapi.utility.Languages.ALCRI;
import static org.semanticweb.owlapi.utility.Languages.ALCRID;
import static org.semanticweb.owlapi.utility.Languages.ALCRIF;
import static org.semanticweb.owlapi.utility.Languages.ALCRIFD;
import static org.semanticweb.owlapi.utility.Languages.ALCRIN;
import static org.semanticweb.owlapi.utility.Languages.ALCRIND;
import static org.semanticweb.owlapi.utility.Languages.ALCRIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCRIQD;
import static org.semanticweb.owlapi.utility.Languages.ALCRN;
import static org.semanticweb.owlapi.utility.Languages.ALCRND;
import static org.semanticweb.owlapi.utility.Languages.ALCRO;
import static org.semanticweb.owlapi.utility.Languages.ALCROD;
import static org.semanticweb.owlapi.utility.Languages.ALCROF;
import static org.semanticweb.owlapi.utility.Languages.ALCROFD;
import static org.semanticweb.owlapi.utility.Languages.ALCROI;
import static org.semanticweb.owlapi.utility.Languages.ALCROID;
import static org.semanticweb.owlapi.utility.Languages.ALCROIF;
import static org.semanticweb.owlapi.utility.Languages.ALCROIFD;
import static org.semanticweb.owlapi.utility.Languages.ALCROIN;
import static org.semanticweb.owlapi.utility.Languages.ALCROIND;
import static org.semanticweb.owlapi.utility.Languages.ALCROIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCROIQD;
import static org.semanticweb.owlapi.utility.Languages.ALCRON;
import static org.semanticweb.owlapi.utility.Languages.ALCROND;
import static org.semanticweb.owlapi.utility.Languages.ALCROQ;
import static org.semanticweb.owlapi.utility.Languages.ALCROQD;
import static org.semanticweb.owlapi.utility.Languages.ALCRQ;
import static org.semanticweb.owlapi.utility.Languages.ALCRQD;
import static org.semanticweb.owlapi.utility.Languages.ALCRr;
import static org.semanticweb.owlapi.utility.Languages.ALCRrD;
import static org.semanticweb.owlapi.utility.Languages.ALCRrF;
import static org.semanticweb.owlapi.utility.Languages.ALCRrFD;
import static org.semanticweb.owlapi.utility.Languages.ALCRrN;
import static org.semanticweb.owlapi.utility.Languages.ALCRrND;
import static org.semanticweb.owlapi.utility.Languages.ALCRrO;
import static org.semanticweb.owlapi.utility.Languages.ALCRrOD;
import static org.semanticweb.owlapi.utility.Languages.ALCRrOF;
import static org.semanticweb.owlapi.utility.Languages.ALCRrOFD;
import static org.semanticweb.owlapi.utility.Languages.ALCRrON;
import static org.semanticweb.owlapi.utility.Languages.ALCRrOND;
import static org.semanticweb.owlapi.utility.Languages.ALCRrOQ;
import static org.semanticweb.owlapi.utility.Languages.ALCRrOQD;
import static org.semanticweb.owlapi.utility.Languages.ALCRrQ;
import static org.semanticweb.owlapi.utility.Languages.ALCRrQD;
import static org.semanticweb.owlapi.utility.Languages.ALE;
import static org.semanticweb.owlapi.utility.Languages.EL;
import static org.semanticweb.owlapi.utility.Languages.ELPLUSPLUS;
import static org.semanticweb.owlapi.utility.Languages.FL;
import static org.semanticweb.owlapi.utility.Languages.FL0;
import static org.semanticweb.owlapi.utility.Languages.FLMINUS;
import static org.semanticweb.owlapi.utility.Languages.S;
import static org.semanticweb.owlapi.utility.Languages.SD;
import static org.semanticweb.owlapi.utility.Languages.SF;
import static org.semanticweb.owlapi.utility.Languages.SFD;
import static org.semanticweb.owlapi.utility.Languages.SH;
import static org.semanticweb.owlapi.utility.Languages.SHD;
import static org.semanticweb.owlapi.utility.Languages.SHF;
import static org.semanticweb.owlapi.utility.Languages.SHFD;
import static org.semanticweb.owlapi.utility.Languages.SHI;
import static org.semanticweb.owlapi.utility.Languages.SHID;
import static org.semanticweb.owlapi.utility.Languages.SHIF;
import static org.semanticweb.owlapi.utility.Languages.SHIFD;
import static org.semanticweb.owlapi.utility.Languages.SHIN;
import static org.semanticweb.owlapi.utility.Languages.SHIND;
import static org.semanticweb.owlapi.utility.Languages.SHIQ;
import static org.semanticweb.owlapi.utility.Languages.SHIQD;
import static org.semanticweb.owlapi.utility.Languages.SHN;
import static org.semanticweb.owlapi.utility.Languages.SHND;
import static org.semanticweb.owlapi.utility.Languages.SHO;
import static org.semanticweb.owlapi.utility.Languages.SHOD;
import static org.semanticweb.owlapi.utility.Languages.SHOF;
import static org.semanticweb.owlapi.utility.Languages.SHOFD;
import static org.semanticweb.owlapi.utility.Languages.SHOI;
import static org.semanticweb.owlapi.utility.Languages.SHOID;
import static org.semanticweb.owlapi.utility.Languages.SHOIF;
import static org.semanticweb.owlapi.utility.Languages.SHOIFD;
import static org.semanticweb.owlapi.utility.Languages.SHOIN;
import static org.semanticweb.owlapi.utility.Languages.SHOIND;
import static org.semanticweb.owlapi.utility.Languages.SHOIQ;
import static org.semanticweb.owlapi.utility.Languages.SHOIQD;
import static org.semanticweb.owlapi.utility.Languages.SHON;
import static org.semanticweb.owlapi.utility.Languages.SHOND;
import static org.semanticweb.owlapi.utility.Languages.SHOQ;
import static org.semanticweb.owlapi.utility.Languages.SHOQD;
import static org.semanticweb.owlapi.utility.Languages.SHQ;
import static org.semanticweb.owlapi.utility.Languages.SHQD;
import static org.semanticweb.owlapi.utility.Languages.SI;
import static org.semanticweb.owlapi.utility.Languages.SID;
import static org.semanticweb.owlapi.utility.Languages.SIF;
import static org.semanticweb.owlapi.utility.Languages.SIFD;
import static org.semanticweb.owlapi.utility.Languages.SIN;
import static org.semanticweb.owlapi.utility.Languages.SIND;
import static org.semanticweb.owlapi.utility.Languages.SIQ;
import static org.semanticweb.owlapi.utility.Languages.SIQD;
import static org.semanticweb.owlapi.utility.Languages.SN;
import static org.semanticweb.owlapi.utility.Languages.SND;
import static org.semanticweb.owlapi.utility.Languages.SO;
import static org.semanticweb.owlapi.utility.Languages.SOD;
import static org.semanticweb.owlapi.utility.Languages.SOF;
import static org.semanticweb.owlapi.utility.Languages.SOFD;
import static org.semanticweb.owlapi.utility.Languages.SOI;
import static org.semanticweb.owlapi.utility.Languages.SOID;
import static org.semanticweb.owlapi.utility.Languages.SOIF;
import static org.semanticweb.owlapi.utility.Languages.SOIFD;
import static org.semanticweb.owlapi.utility.Languages.SOIN;
import static org.semanticweb.owlapi.utility.Languages.SOIND;
import static org.semanticweb.owlapi.utility.Languages.SOIQ;
import static org.semanticweb.owlapi.utility.Languages.SOIQD;
import static org.semanticweb.owlapi.utility.Languages.SON;
import static org.semanticweb.owlapi.utility.Languages.SOND;
import static org.semanticweb.owlapi.utility.Languages.SOQ;
import static org.semanticweb.owlapi.utility.Languages.SOQD;
import static org.semanticweb.owlapi.utility.Languages.SQ;
import static org.semanticweb.owlapi.utility.Languages.SQD;
import static org.semanticweb.owlapi.utility.Languages.SR;
import static org.semanticweb.owlapi.utility.Languages.SRD;
import static org.semanticweb.owlapi.utility.Languages.SRF;
import static org.semanticweb.owlapi.utility.Languages.SRFD;
import static org.semanticweb.owlapi.utility.Languages.SRI;
import static org.semanticweb.owlapi.utility.Languages.SRID;
import static org.semanticweb.owlapi.utility.Languages.SRIF;
import static org.semanticweb.owlapi.utility.Languages.SRIFD;
import static org.semanticweb.owlapi.utility.Languages.SRIN;
import static org.semanticweb.owlapi.utility.Languages.SRIND;
import static org.semanticweb.owlapi.utility.Languages.SRIQ;
import static org.semanticweb.owlapi.utility.Languages.SRIQD;
import static org.semanticweb.owlapi.utility.Languages.SRN;
import static org.semanticweb.owlapi.utility.Languages.SRND;
import static org.semanticweb.owlapi.utility.Languages.SRO;
import static org.semanticweb.owlapi.utility.Languages.SROD;
import static org.semanticweb.owlapi.utility.Languages.SROF;
import static org.semanticweb.owlapi.utility.Languages.SROFD;
import static org.semanticweb.owlapi.utility.Languages.SROI;
import static org.semanticweb.owlapi.utility.Languages.SROID;
import static org.semanticweb.owlapi.utility.Languages.SROIF;
import static org.semanticweb.owlapi.utility.Languages.SROIFD;
import static org.semanticweb.owlapi.utility.Languages.SROIN;
import static org.semanticweb.owlapi.utility.Languages.SROIND;
import static org.semanticweb.owlapi.utility.Languages.SROIQ;
import static org.semanticweb.owlapi.utility.Languages.SROIQD;
import static org.semanticweb.owlapi.utility.Languages.SRON;
import static org.semanticweb.owlapi.utility.Languages.SROND;
import static org.semanticweb.owlapi.utility.Languages.SROQ;
import static org.semanticweb.owlapi.utility.Languages.SROQD;
import static org.semanticweb.owlapi.utility.Languages.SRQ;
import static org.semanticweb.owlapi.utility.Languages.SRQD;
import static org.semanticweb.owlapi.utility.Languages.SRr;
import static org.semanticweb.owlapi.utility.Languages.SRrD;
import static org.semanticweb.owlapi.utility.Languages.SRrF;
import static org.semanticweb.owlapi.utility.Languages.SRrFD;
import static org.semanticweb.owlapi.utility.Languages.SRrN;
import static org.semanticweb.owlapi.utility.Languages.SRrND;
import static org.semanticweb.owlapi.utility.Languages.SRrO;
import static org.semanticweb.owlapi.utility.Languages.SRrOD;
import static org.semanticweb.owlapi.utility.Languages.SRrOF;
import static org.semanticweb.owlapi.utility.Languages.SRrOFD;
import static org.semanticweb.owlapi.utility.Languages.SRrON;
import static org.semanticweb.owlapi.utility.Languages.SRrOND;
import static org.semanticweb.owlapi.utility.Languages.SRrOQ;
import static org.semanticweb.owlapi.utility.Languages.SRrOQD;
import static org.semanticweb.owlapi.utility.Languages.SRrQ;
import static org.semanticweb.owlapi.utility.Languages.SRrQD;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.utility.Construct;
import org.semanticweb.owlapi.utility.Languages;

class LanguagesTestCase extends TestBase {

    @ParameterizedTest
    @MethodSource({"expectedConstructs"})
    void same(Languages l, EnumSet<Construct> constructs) {
        assertEquals(constructs, l.components());
    }

    static Arguments a(Languages l, Construct... constructs) {
        return Arguments.of(l, EnumSet.copyOf(l(constructs)));
    }

    static Stream<Arguments> expectedConstructs() {
        return Stream.of(
        //@formatter:off
        a(FL0, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION),
        a(FLMINUS, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(FL, ROLE_DOMAIN_RANGE, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(AL, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(EL, ROLE_DOMAIN_RANGE, FULL_EXISTENTIAL, CONCEPT_INTERSECTION),
        a(ALE, FULL_EXISTENTIAL, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALC, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(S, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCH, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCHOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRr, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRrOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCR, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCRON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ALCROIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SH, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SHOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRr, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRrOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SR, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SRON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(SROIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL),
        a(ELPLUSPLUS, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_TRANSITIVE, D, CONCEPT_INTERSECTION));
    }
    //@formatter:on

    // utility to write the enum declarations for variations of construct combinations
    static void dump() {
        // ALC || S (ALC + TRAN)
        // H \subset R
        // O
        // I
        // F \subset N \subset Q
        // D
        for (String s1 : new String[] {"ALC", "S"}) {
            for (String s2 : new String[] {"", "H", "Rr", "R"}) {
                for (String s3 : new String[] {"", "O"}) {
                    for (String s4 : new String[] {"", "I"}) {
                        for (String s5 : new String[] {"", "F", "N", "Q"}) {
                            for (String s6 : new String[] {"", "D"}) {
                                List<String> list = l(s1, s2, s3, s4, s5, s6);
                                String name = list.stream().collect(Collectors.joining());
                                String starter = "    /** " + name + " language. */    ";
                                System.out.println(starter
                                    + name + "(\"" + name + "\"," + list.stream()
                                        .filter(s -> !s.isEmpty()).collect(Collectors.joining(", "))
                                    + "),");
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        for (Languages l : Languages.values()) {
            System.out.println(l.components().stream().map(Construct::name)
                .collect(Collectors.joining(", ", "same(" + l.name() + ", ", ");")));
        }
    }
}
