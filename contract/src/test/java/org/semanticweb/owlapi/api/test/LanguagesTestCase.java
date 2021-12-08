package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import static org.semanticweb.owlapi.util.Languages.AL;
import static org.semanticweb.owlapi.util.Languages.ALC;
import static org.semanticweb.owlapi.util.Languages.ALCD;
import static org.semanticweb.owlapi.util.Languages.ALCF;
import static org.semanticweb.owlapi.util.Languages.ALCFD;
import static org.semanticweb.owlapi.util.Languages.ALCH;
import static org.semanticweb.owlapi.util.Languages.ALCHD;
import static org.semanticweb.owlapi.util.Languages.ALCHF;
import static org.semanticweb.owlapi.util.Languages.ALCHFD;
import static org.semanticweb.owlapi.util.Languages.ALCHI;
import static org.semanticweb.owlapi.util.Languages.ALCHID;
import static org.semanticweb.owlapi.util.Languages.ALCHIF;
import static org.semanticweb.owlapi.util.Languages.ALCHIFD;
import static org.semanticweb.owlapi.util.Languages.ALCHIN;
import static org.semanticweb.owlapi.util.Languages.ALCHIND;
import static org.semanticweb.owlapi.util.Languages.ALCHIQ;
import static org.semanticweb.owlapi.util.Languages.ALCHIQD;
import static org.semanticweb.owlapi.util.Languages.ALCHN;
import static org.semanticweb.owlapi.util.Languages.ALCHND;
import static org.semanticweb.owlapi.util.Languages.ALCHO;
import static org.semanticweb.owlapi.util.Languages.ALCHOD;
import static org.semanticweb.owlapi.util.Languages.ALCHOF;
import static org.semanticweb.owlapi.util.Languages.ALCHOFD;
import static org.semanticweb.owlapi.util.Languages.ALCHOI;
import static org.semanticweb.owlapi.util.Languages.ALCHOID;
import static org.semanticweb.owlapi.util.Languages.ALCHOIF;
import static org.semanticweb.owlapi.util.Languages.ALCHOIFD;
import static org.semanticweb.owlapi.util.Languages.ALCHOIN;
import static org.semanticweb.owlapi.util.Languages.ALCHOIND;
import static org.semanticweb.owlapi.util.Languages.ALCHOIQ;
import static org.semanticweb.owlapi.util.Languages.ALCHOIQD;
import static org.semanticweb.owlapi.util.Languages.ALCHON;
import static org.semanticweb.owlapi.util.Languages.ALCHOND;
import static org.semanticweb.owlapi.util.Languages.ALCHOQ;
import static org.semanticweb.owlapi.util.Languages.ALCHOQD;
import static org.semanticweb.owlapi.util.Languages.ALCHQ;
import static org.semanticweb.owlapi.util.Languages.ALCHQD;
import static org.semanticweb.owlapi.util.Languages.ALCI;
import static org.semanticweb.owlapi.util.Languages.ALCID;
import static org.semanticweb.owlapi.util.Languages.ALCIF;
import static org.semanticweb.owlapi.util.Languages.ALCIFD;
import static org.semanticweb.owlapi.util.Languages.ALCIN;
import static org.semanticweb.owlapi.util.Languages.ALCIND;
import static org.semanticweb.owlapi.util.Languages.ALCIQ;
import static org.semanticweb.owlapi.util.Languages.ALCIQD;
import static org.semanticweb.owlapi.util.Languages.ALCN;
import static org.semanticweb.owlapi.util.Languages.ALCND;
import static org.semanticweb.owlapi.util.Languages.ALCO;
import static org.semanticweb.owlapi.util.Languages.ALCOD;
import static org.semanticweb.owlapi.util.Languages.ALCOF;
import static org.semanticweb.owlapi.util.Languages.ALCOFD;
import static org.semanticweb.owlapi.util.Languages.ALCOI;
import static org.semanticweb.owlapi.util.Languages.ALCOID;
import static org.semanticweb.owlapi.util.Languages.ALCOIF;
import static org.semanticweb.owlapi.util.Languages.ALCOIFD;
import static org.semanticweb.owlapi.util.Languages.ALCOIN;
import static org.semanticweb.owlapi.util.Languages.ALCOIND;
import static org.semanticweb.owlapi.util.Languages.ALCOIQ;
import static org.semanticweb.owlapi.util.Languages.ALCOIQD;
import static org.semanticweb.owlapi.util.Languages.ALCON;
import static org.semanticweb.owlapi.util.Languages.ALCOND;
import static org.semanticweb.owlapi.util.Languages.ALCOQ;
import static org.semanticweb.owlapi.util.Languages.ALCOQD;
import static org.semanticweb.owlapi.util.Languages.ALCQ;
import static org.semanticweb.owlapi.util.Languages.ALCQD;
import static org.semanticweb.owlapi.util.Languages.ALCR;
import static org.semanticweb.owlapi.util.Languages.ALCRD;
import static org.semanticweb.owlapi.util.Languages.ALCRF;
import static org.semanticweb.owlapi.util.Languages.ALCRFD;
import static org.semanticweb.owlapi.util.Languages.ALCRI;
import static org.semanticweb.owlapi.util.Languages.ALCRID;
import static org.semanticweb.owlapi.util.Languages.ALCRIF;
import static org.semanticweb.owlapi.util.Languages.ALCRIFD;
import static org.semanticweb.owlapi.util.Languages.ALCRIN;
import static org.semanticweb.owlapi.util.Languages.ALCRIND;
import static org.semanticweb.owlapi.util.Languages.ALCRIQ;
import static org.semanticweb.owlapi.util.Languages.ALCRIQD;
import static org.semanticweb.owlapi.util.Languages.ALCRN;
import static org.semanticweb.owlapi.util.Languages.ALCRND;
import static org.semanticweb.owlapi.util.Languages.ALCRO;
import static org.semanticweb.owlapi.util.Languages.ALCROD;
import static org.semanticweb.owlapi.util.Languages.ALCROF;
import static org.semanticweb.owlapi.util.Languages.ALCROFD;
import static org.semanticweb.owlapi.util.Languages.ALCROI;
import static org.semanticweb.owlapi.util.Languages.ALCROID;
import static org.semanticweb.owlapi.util.Languages.ALCROIF;
import static org.semanticweb.owlapi.util.Languages.ALCROIFD;
import static org.semanticweb.owlapi.util.Languages.ALCROIN;
import static org.semanticweb.owlapi.util.Languages.ALCROIND;
import static org.semanticweb.owlapi.util.Languages.ALCROIQ;
import static org.semanticweb.owlapi.util.Languages.ALCROIQD;
import static org.semanticweb.owlapi.util.Languages.ALCRON;
import static org.semanticweb.owlapi.util.Languages.ALCROND;
import static org.semanticweb.owlapi.util.Languages.ALCROQ;
import static org.semanticweb.owlapi.util.Languages.ALCROQD;
import static org.semanticweb.owlapi.util.Languages.ALCRQ;
import static org.semanticweb.owlapi.util.Languages.ALCRQD;
import static org.semanticweb.owlapi.util.Languages.ALCRr;
import static org.semanticweb.owlapi.util.Languages.ALCRrD;
import static org.semanticweb.owlapi.util.Languages.ALCRrF;
import static org.semanticweb.owlapi.util.Languages.ALCRrFD;
import static org.semanticweb.owlapi.util.Languages.ALCRrN;
import static org.semanticweb.owlapi.util.Languages.ALCRrND;
import static org.semanticweb.owlapi.util.Languages.ALCRrO;
import static org.semanticweb.owlapi.util.Languages.ALCRrOD;
import static org.semanticweb.owlapi.util.Languages.ALCRrOF;
import static org.semanticweb.owlapi.util.Languages.ALCRrOFD;
import static org.semanticweb.owlapi.util.Languages.ALCRrON;
import static org.semanticweb.owlapi.util.Languages.ALCRrOND;
import static org.semanticweb.owlapi.util.Languages.ALCRrOQ;
import static org.semanticweb.owlapi.util.Languages.ALCRrOQD;
import static org.semanticweb.owlapi.util.Languages.ALCRrQ;
import static org.semanticweb.owlapi.util.Languages.ALCRrQD;
import static org.semanticweb.owlapi.util.Languages.ALE;
import static org.semanticweb.owlapi.util.Languages.EL;
import static org.semanticweb.owlapi.util.Languages.ELPLUSPLUS;
import static org.semanticweb.owlapi.util.Languages.FL;
import static org.semanticweb.owlapi.util.Languages.FL0;
import static org.semanticweb.owlapi.util.Languages.FLMINUS;
import static org.semanticweb.owlapi.util.Languages.S;
import static org.semanticweb.owlapi.util.Languages.SD;
import static org.semanticweb.owlapi.util.Languages.SF;
import static org.semanticweb.owlapi.util.Languages.SFD;
import static org.semanticweb.owlapi.util.Languages.SH;
import static org.semanticweb.owlapi.util.Languages.SHD;
import static org.semanticweb.owlapi.util.Languages.SHF;
import static org.semanticweb.owlapi.util.Languages.SHFD;
import static org.semanticweb.owlapi.util.Languages.SHI;
import static org.semanticweb.owlapi.util.Languages.SHID;
import static org.semanticweb.owlapi.util.Languages.SHIF;
import static org.semanticweb.owlapi.util.Languages.SHIFD;
import static org.semanticweb.owlapi.util.Languages.SHIN;
import static org.semanticweb.owlapi.util.Languages.SHIND;
import static org.semanticweb.owlapi.util.Languages.SHIQ;
import static org.semanticweb.owlapi.util.Languages.SHIQD;
import static org.semanticweb.owlapi.util.Languages.SHN;
import static org.semanticweb.owlapi.util.Languages.SHND;
import static org.semanticweb.owlapi.util.Languages.SHO;
import static org.semanticweb.owlapi.util.Languages.SHOD;
import static org.semanticweb.owlapi.util.Languages.SHOF;
import static org.semanticweb.owlapi.util.Languages.SHOFD;
import static org.semanticweb.owlapi.util.Languages.SHOI;
import static org.semanticweb.owlapi.util.Languages.SHOID;
import static org.semanticweb.owlapi.util.Languages.SHOIF;
import static org.semanticweb.owlapi.util.Languages.SHOIFD;
import static org.semanticweb.owlapi.util.Languages.SHOIN;
import static org.semanticweb.owlapi.util.Languages.SHOIND;
import static org.semanticweb.owlapi.util.Languages.SHOIQ;
import static org.semanticweb.owlapi.util.Languages.SHOIQD;
import static org.semanticweb.owlapi.util.Languages.SHON;
import static org.semanticweb.owlapi.util.Languages.SHOND;
import static org.semanticweb.owlapi.util.Languages.SHOQ;
import static org.semanticweb.owlapi.util.Languages.SHOQD;
import static org.semanticweb.owlapi.util.Languages.SHQ;
import static org.semanticweb.owlapi.util.Languages.SHQD;
import static org.semanticweb.owlapi.util.Languages.SI;
import static org.semanticweb.owlapi.util.Languages.SID;
import static org.semanticweb.owlapi.util.Languages.SIF;
import static org.semanticweb.owlapi.util.Languages.SIFD;
import static org.semanticweb.owlapi.util.Languages.SIN;
import static org.semanticweb.owlapi.util.Languages.SIND;
import static org.semanticweb.owlapi.util.Languages.SIQ;
import static org.semanticweb.owlapi.util.Languages.SIQD;
import static org.semanticweb.owlapi.util.Languages.SN;
import static org.semanticweb.owlapi.util.Languages.SND;
import static org.semanticweb.owlapi.util.Languages.SO;
import static org.semanticweb.owlapi.util.Languages.SOD;
import static org.semanticweb.owlapi.util.Languages.SOF;
import static org.semanticweb.owlapi.util.Languages.SOFD;
import static org.semanticweb.owlapi.util.Languages.SOI;
import static org.semanticweb.owlapi.util.Languages.SOID;
import static org.semanticweb.owlapi.util.Languages.SOIF;
import static org.semanticweb.owlapi.util.Languages.SOIFD;
import static org.semanticweb.owlapi.util.Languages.SOIN;
import static org.semanticweb.owlapi.util.Languages.SOIND;
import static org.semanticweb.owlapi.util.Languages.SOIQ;
import static org.semanticweb.owlapi.util.Languages.SOIQD;
import static org.semanticweb.owlapi.util.Languages.SON;
import static org.semanticweb.owlapi.util.Languages.SOND;
import static org.semanticweb.owlapi.util.Languages.SOQ;
import static org.semanticweb.owlapi.util.Languages.SOQD;
import static org.semanticweb.owlapi.util.Languages.SQ;
import static org.semanticweb.owlapi.util.Languages.SQD;
import static org.semanticweb.owlapi.util.Languages.SR;
import static org.semanticweb.owlapi.util.Languages.SRD;
import static org.semanticweb.owlapi.util.Languages.SRF;
import static org.semanticweb.owlapi.util.Languages.SRFD;
import static org.semanticweb.owlapi.util.Languages.SRI;
import static org.semanticweb.owlapi.util.Languages.SRID;
import static org.semanticweb.owlapi.util.Languages.SRIF;
import static org.semanticweb.owlapi.util.Languages.SRIFD;
import static org.semanticweb.owlapi.util.Languages.SRIN;
import static org.semanticweb.owlapi.util.Languages.SRIND;
import static org.semanticweb.owlapi.util.Languages.SRIQ;
import static org.semanticweb.owlapi.util.Languages.SRIQD;
import static org.semanticweb.owlapi.util.Languages.SRN;
import static org.semanticweb.owlapi.util.Languages.SRND;
import static org.semanticweb.owlapi.util.Languages.SRO;
import static org.semanticweb.owlapi.util.Languages.SROD;
import static org.semanticweb.owlapi.util.Languages.SROF;
import static org.semanticweb.owlapi.util.Languages.SROFD;
import static org.semanticweb.owlapi.util.Languages.SROI;
import static org.semanticweb.owlapi.util.Languages.SROID;
import static org.semanticweb.owlapi.util.Languages.SROIF;
import static org.semanticweb.owlapi.util.Languages.SROIFD;
import static org.semanticweb.owlapi.util.Languages.SROIN;
import static org.semanticweb.owlapi.util.Languages.SROIND;
import static org.semanticweb.owlapi.util.Languages.SROIQ;
import static org.semanticweb.owlapi.util.Languages.SROIQD;
import static org.semanticweb.owlapi.util.Languages.SRON;
import static org.semanticweb.owlapi.util.Languages.SROND;
import static org.semanticweb.owlapi.util.Languages.SROQ;
import static org.semanticweb.owlapi.util.Languages.SROQD;
import static org.semanticweb.owlapi.util.Languages.SRQ;
import static org.semanticweb.owlapi.util.Languages.SRQD;
import static org.semanticweb.owlapi.util.Languages.SRr;
import static org.semanticweb.owlapi.util.Languages.SRrD;
import static org.semanticweb.owlapi.util.Languages.SRrF;
import static org.semanticweb.owlapi.util.Languages.SRrFD;
import static org.semanticweb.owlapi.util.Languages.SRrN;
import static org.semanticweb.owlapi.util.Languages.SRrND;
import static org.semanticweb.owlapi.util.Languages.SRrO;
import static org.semanticweb.owlapi.util.Languages.SRrOD;
import static org.semanticweb.owlapi.util.Languages.SRrOF;
import static org.semanticweb.owlapi.util.Languages.SRrOFD;
import static org.semanticweb.owlapi.util.Languages.SRrON;
import static org.semanticweb.owlapi.util.Languages.SRrOND;
import static org.semanticweb.owlapi.util.Languages.SRrOQ;
import static org.semanticweb.owlapi.util.Languages.SRrOQD;
import static org.semanticweb.owlapi.util.Languages.SRrQ;
import static org.semanticweb.owlapi.util.Languages.SRrQD;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.DF;
import org.semanticweb.owlapi.util.Construct;
import org.semanticweb.owlapi.util.Languages;

class LanguagesTestCase {
    private static EnumSet<Construct> set(Construct... constructs) {
        return EnumSet.copyOf(DF.l(constructs));
    }

    private static void same(Languages l, Construct... constructs) {
        assertEquals(set(constructs), l.components());
    }

    @Test
    void shouldFindExpectedConstructs() {
        same(FL0, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION);
        same(FLMINUS, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(FL, ROLE_DOMAIN_RANGE, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(AL, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(EL, ROLE_DOMAIN_RANGE, FULL_EXISTENTIAL, CONCEPT_INTERSECTION);
        same(ALE, FULL_EXISTENTIAL, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALC, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(S, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, D,
            ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F,
            ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F,
            D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N,
            ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N,
            D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q,
            ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q,
            D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCH, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCHOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCHOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRr, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRrD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRrOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCR, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCRI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCROD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCROF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCROFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCRON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCROND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(ALCROQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ALCROIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F,
            ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, F,
            ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N,
            ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, N,
            ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q,
            ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL, Q,
            ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION,
            LIMITED_EXISTENTIAL);
        same(SOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SH, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SHOIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_HIERARCHY, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRr, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrOD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrOF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrOFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrOND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrOQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRrOQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_REFLEXIVITY_CHAINS, NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SR, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRO, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, F, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SRON, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, N, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION, CONCEPT_INTERSECTION,
            UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROI, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROID, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROIF, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROIFD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, F, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROIN, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROIND, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, N, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROIQ, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(SROIQD, ROLE_DOMAIN_RANGE, CONCEPT_COMPLEX_NEGATION, CONCEPT_UNION, FULL_EXISTENTIAL,
            ROLE_COMPLEX, NOMINALS, ROLE_INVERSE, Q, ROLE_TRANSITIVE, D, ATOMIC_NEGATION,
            CONCEPT_INTERSECTION, UNIVERSAL_RESTRICTION, LIMITED_EXISTENTIAL);
        same(ELPLUSPLUS, FULL_EXISTENTIAL, ROLE_COMPLEX, NOMINALS, ROLE_TRANSITIVE, D,
            CONCEPT_INTERSECTION);
    }

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
                                List<String> list = DF.l(s1, s2, s3, s4, s5, s6);
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
