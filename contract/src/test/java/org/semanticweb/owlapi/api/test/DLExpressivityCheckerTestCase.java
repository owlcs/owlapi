/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.Construct.*;
import static org.semanticweb.owlapi.util.Languages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.Construct;
import org.semanticweb.owlapi.util.DLExpressivityChecker;
import org.semanticweb.owlapi.util.Languages;

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class DLExpressivityCheckerTestCase extends TestBase {

    private final OWLAxiom object;
    private final String expected;
    private List<Construct> constructs;
    private List<Languages> expressible;
    private List<Languages> within;
    private List<Languages> minimal;

    public DLExpressivityCheckerTestCase(OWLAxiom object, String expected, String expectedStrict,
        List<Construct> c, List<Languages> exp, List<Languages> within, List<Languages> min) {
        this.object = object;
        this.expected = expectedStrict;
        constructs = c;
        expressible = exp;
        this.within = within;
        minimal = min;
    }

    static <T> List<T> l(T... t) {
        return Arrays.asList(t);
    }

    @Parameterized.Parameters(name = "{index} {1} {0}")
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        return Arrays.asList(
        //@formatter:off
            new Object[] {b.assAll(),           "0 AL",       "UNIVRESTR" , l(UNIVRESTR), l(FL0), belowUniversal(), l(FL0) },
            new Object[] {b.dDef(),             "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.decC(),             "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.decOp(),            "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.decDp(),            "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.decDt(),            "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.decAp(),            "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.decI(),             "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.ec(),               "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.nop(),              "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.opa(),              "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.subAnn(),           "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.subClass(),         "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.rule(),             "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.hasKey(),           "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.bigRule(),          "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.ann(),              "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.annDom(),           "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.annRange(),         "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.ass(),              "1  AL",       ""          , empty(), l(FL0, EL), empty(), l(FL0, EL) },
            new Object[] {b.assDi(),            "2  ALCO",     "CUO"       , l(C, U, O), l(ALCO), belowCUO(), l(ALCO) },
            new Object[] {b.dc(),               "3  ALC",      "C"         , l(C), l(ALC), belowC(), l(ALC) },
            new Object[] {b.assNot(),           "3  ALC",      "C"         , l(C), l(ALC), belowC(), l(ALC) },
            new Object[] {b.assNotAnon(),       "3  ALC",      "C"         , l(C), l(ALC), belowC(), l(ALC) },
            new Object[] {b.dOp(),              "4  ALR",      "R"         , l(R), expressR(), belowR(), expressR() },
            new Object[] {b.irr(),              "4  ALR",      "R"         , l(R), expressR(), belowR(), expressR() },
            new Object[] {b.chain(),            "4  ALR",      "R"         , l(R), expressR(), belowR(), expressR() },
            new Object[] {b.ref(),              "4  ALR",      "R"         , l(R), expressR(), belowR(), expressR() },
            new Object[] {b.asymm(),            "4  ALR",      "R"         , l(R), expressR(), belowR(), expressR() },
            new Object[] {b.assHasSelf(),       "4  ALR",      "R"         , l(R), expressR(), belowR(), expressR() },
            new Object[] {b.dRange(),           "5  AL(D)",    "RRESTR(D)" , l(RRESTR, D), l(SROIQD), l(SROIQD), l(SROIQD) },
            new Object[] {b.dRangeAnd(),        "5  AL(D)",    "RRESTR(D)" , l(RRESTR, D), l(SROIQD), l(SROIQD), l(SROIQD) },
            new Object[] {b.dRangeOr(),         "5  AL(D)",    "RRESTR(D)" , l(RRESTR, D), l(SROIQD), l(SROIQD), l(SROIQD) },
            new Object[] {b.dOneOf(),           "5  AL(D)",    "RRESTR(D)" , l(RRESTR, D), l(SROIQD), l(SROIQD), l(SROIQD) },
            new Object[] {b.dNot(),             "5  AL(D)",    "RRESTR(D)" , l(RRESTR, D), l(SROIQD), l(SROIQD), l(SROIQD) },
            new Object[] {b.dRangeRestrict(),   "5  AL(D)",    "RRESTR(D)" , l(RRESTR, D), l(SROIQD), l(SROIQD), l(SROIQD) },
            new Object[] {b.dDom(),             "5  AL(D)",    "RRESTR(D)" , l(RRESTR, D), l(SROIQD), l(SROIQD), l(SROIQD) },
            new Object[] {b.du(),               "6  ALC",      "CU"        , l(C, U), l(ALC), belowCU(), l(ALC) },
            new Object[] {b.eDp(),              "7  ALH(D)",   "H(D)"      , l(H, D), l(ALCHD), belowHD(), l(ALCHD) },
            new Object[] {b.subData(),          "7  ALH(D)",   "H(D)"      , l(H, D), l(ALCHD), belowHD(), l(ALCHD) },
            new Object[] {b.eOp(),              "8  ALH",      "H"         , l(H), l(ALCH), belowH(), l(ALCH) },
            new Object[] {b.subObject(),        "8  ALH",      "H"         , l(H), l(ALCH), belowH(), l(ALCH) },
            new Object[] {b.fdp(),              "9 ALF(D)",   "F(D)"      , l(F, D), l(ALCFD), belowFD(), l(ALCFD) },
            new Object[] {b.fop(),              "10 ALF",      "F"         , l(F), l(ALCF), belowF(), l(ALCF) },
            new Object[] {b.ifp(),              "11 ALIF",     "IF"        , l(I, F), l(ALCIF), belowIF(), l(ALCIF) },
            new Object[] {b.iop(),              "12 ALI",      "I"         , l(I), l(ALCI), belowI(), l(ALCI) },
            new Object[] {b.opaInv(),           "12 ALI",      "I"         , l(I), l(ALCI), belowI(), l(ALCI) },
            new Object[] {b.opaInvj(),          "12 ALI",      "I"         , l(I), l(ALCI), belowI(), l(ALCI) },
            new Object[] {b.symm(),             "12 ALI",      "I"         , l(I), l(ALCI), belowI(), l(ALCI) },
            new Object[] {b.dDp(),              "13 AL(D)",    "(D)"       , l(D), l(ALCD), belowD(), l(ALCD) },
            new Object[] {b.ndp(),              "13 AL(D)",    "(D)"       , l(D), l(ALCD), belowD(), l(ALCD) },
            new Object[] {b.assDAll(),          "13 AL(D)",    "(D)"       , l(D), l(ALCD), belowD(), l(ALCD) },
            new Object[] {b.assDHas(),          "13 AL(D)",    "(D)"       , l(D), l(ALCD), belowD(), l(ALCD) },
            new Object[] {b.assD(),             "13 AL(D)",    "(D)"       , l(D), l(ALCD), belowD(), l(ALCD) },
            new Object[] {b.assDPlain(),        "13 AL(D)",    "(D)"       , l(D), l(ALCD), belowD(), l(ALCD) },
            new Object[] {b.same(),             "14 ALO",      "O"         , l(O), l(ALCO,ELPLUSPLUS), belowO(), l(ALCO,ELPLUSPLUS) },
            new Object[] {b.trans(),            "15 AL+",      "+"         , l(TRAN), l(S), belowTRAN(), l(S) },
            new Object[] {b.assAnd(),           "16 AL",       "CINT"      , l(CINT), l(FL0, EL), l(Languages.values()), l(FL0, EL) },
            new Object[] {b.assOr(),            "17 ALU",      "U"         , l(U), l(ALC), belowU(), l(ALC) },
            new Object[] {b.oDom(),             "18 AL",       "RRESTR"    , l(RRESTR), l(FL), l(FL, SROIQ, SROIQD), l(FL) },
            new Object[] {b.oRange(),           "18 AL",       "RRESTR"    , l(RRESTR), l(FL), l(FL, SROIQ, SROIQD), l(FL) },
            new Object[] {b.assSome(),          "19 ALE",      "E"         , l(E), l(EL), belowE(), l(EL) },
            new Object[] {b.assHas(),           "20 ALEO",     "EO"        , l(E, O), l(ALCO,ELPLUSPLUS), belowEO(), l(ALCO, ELPLUSPLUS) },
            new Object[] {b.assMin(),           "21 ALQ",      "Q"         , l(Q), l(ALCQ), belowQ(), l(ALCQ) },
            new Object[] {b.assMax(),           "21 ALQ",      "Q"         , l(Q), l(ALCQ), belowQ(), l(ALCQ) },
            new Object[] {b.assEq(),            "21 ALQ",      "Q"         , l(Q), l(ALCQ), belowQ(), l(ALCQ) },
            new Object[] {b.assOneOf(),         "22 ALUO",     "UO"        , l(U, O), l(ALCO), belowUO(), l(ALCO) },
            new Object[] {b.assDSome(),         "23 ALE(D)",   "E(D)"      , l(E, D), l(ALCD), belowED(), l(ALCD) },
            new Object[] {b.assDMin(),          "24 ALQ(D)",   "Q(D)"      , l(Q, D), l(ALCQD), belowQD(), l(ALCQD) },
            new Object[] {b.assDMax(),          "24 ALQ(D)",   "Q(D)"      , l(Q, D), l(ALCQD), belowQD(), l(ALCQD) },
            new Object[] {b.assDEq(),           "24 ALQ(D)",   "Q(D)"      , l(Q, D), l(ALCQD), belowQD(), l(ALCQD) }
            );
        //@formatter:on
    }

    protected static List<Languages> belowFD() {
        return l(SHFD, ALCROIFD, SRIFD, ALCHOFD, SHOIFD, ALCHFD, SOFD, ALCHIFD, SFD, ALCRIFD,
            SROIFD, ALCIFD, ALCOIFD, SOIFD, ALCHOIFD, ALCROFD, SHIFD, SROFD, ALCFD, SHOFD, SRFD,
            ALCRFD, ALCOFD, SIFD);
    }

    protected static List<Object> empty() {
        return l();
    }

    protected static List<Languages> belowED() {
        return l(ALCD, SHIND, SROIQD, ALCROIND, SROQD, SOIFD, ALCHOND, ALCROND, SQD, ALCQD, ALCIFD,
            SHOFD, SND, SHOQD, SHID, ALCOID, SROD, ALCROID, ALCOIQD, ALCHOIFD, SHND, ALCROIQD, SD,
            ALCOND, ALCHND, ALCOD, ALCROD, SROFD, ALCHOQD, ALCHIND, SHOIFD, SROIND, SIND, ALCRIND,
            SHIFD, ALCID, ALCRND, SRD, SROID, ALCHOIQD, SROND, ALCROQD, SHOD, ALCHD, ALCHIQD, SRFD,
            ALCRFD, SIQD, ALCRID, SHIQD, ALCOIND, SOND, SHQD, SOQD, ALCRD, SRND, SHD, ALCIND,
            ALCRIQD, ALCHID, ALCHOD, SOIQD, ALCOIFD, SFD, SOIND, ALCRIFD, SOID, ALCHFD, ALCHIFD,
            SOD, SROIFD, ALCOQD, SRIND, SIFD, ALCOFD, SRIFD, SRQD, SHOID, SHOND, SHOIQD, SRID,
            ALCROFD, ALCRQD, ALCIQD, SRIQD, SOFD, SHFD, ALCND, ALCHOFD, ALCROIFD, SHOIND, SID,
            ALCHQD, ALCHOID, ALCHOIND, ALCFD);
    }

    protected static List<Languages> belowUO() {
        return l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI,
            SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD,
            ALCROIND, SROQD, ALCHOF, SOIFD, ALCHOND, ALCHOIF, ALCROND, SHOFD, SHOQD, ALCOID, SROD,
            ALCROID, ALCOIQD, SROQ, ALCHOIFD, ALCROIQD, ALCROQ, ALCOND, ALCOD, ALCROD, SROFD, SROIN,
            ALCHOQD, SHOIFD, SROIND, SRON, SROI, SRO, SROID, ALCHOIQD, SROND, ALCROQD, ALCHO, SHOD,
            ALCROF, ALCHOIQ, ALCOIND, SOND, SOQD, ALCRON, ALCHOD, SOIQD, ALCOIFD, SOIND, SROF,
            SROIF, SOID, SOD, SROIFD, ALCOQD, ALCOFD, SHOID, SHOND, SHOIQD, ALCROIF, ALCHON,
            ALCROFD, ALCHOQ, SOFD, ALCROIQ, ALCROIN, ALCHOFD, ALCROIFD, SHOIND, ALCROI, ALCHOID,
            ALCHOIN, ALCHOIND, ALCHOI, ALCRO);
    }

    protected static List<Languages> belowEO() {
        return l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI,
            SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD,
            ELPLUSPLUS, ALCROIND, SROQD, ALCHOF, SOIFD, ALCHOND, ALCHOIF, ALCROND, SHOFD, SHOQD,
            ALCOID, SROD, ALCROID, ALCOIQD, SROQ, ALCHOIFD, ALCROIQD, ALCROQ, ALCOND, ALCOD, ALCROD,
            SROFD, SROIN, ALCHOQD, SHOIFD, SROIND, SRON, SROI, SRO, SROID, ALCHOIQD, SROND, ALCROQD,
            ALCHO, SHOD, ALCROF, ALCHOIQ, ALCOIND, SOND, SOQD, ALCRON, ALCHOD, SOIQD, ALCOIFD,
            SOIND, SROF, SROIF, SOID, SOD, SROIFD, ALCOQD, ALCOFD, SHOID, SHOND, SHOIQD, ALCROIF,
            ALCHON, ALCROFD, ALCHOQ, SOFD, ALCROIQ, ALCROIN, ALCHOFD, ALCROIFD, SHOIND, ALCROI,
            ALCHOID, ALCHOIN, ALCHOIND, ALCHOI, ALCRO);
    }

    protected static List<Languages> belowE() {
        return l(EL, ALE, ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN,
            ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN,
            SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ,
            SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD, ELPLUSPLUS, SOIFD, ALCHOND,
            ALCHOIF, SQD, ALCIFD, ALCHIN, SHOFD, SRIF, SND, SHOQD, SHID, ALCOID, ALCHOIFD, SHND,
            ALCROIQD, SD, ALCROQ, ALCH, ALCOD, SROIN, ALCHIND, SROIND, SRON, ALCHI, ALCID, ALCHIF,
            SRO, SROID, ALCRIF, ALCHOIQD, ALCRIN, ALCROQD, ALCHF, ALCROF, ALCHIQD, SRFD, ALCHN,
            SIQD, ALCRID, SHIQD, ALCOIND, SOQD, ALCRON, SHD, SR, ALCIND, ALCHID, ALCHOD, ALCOIFD,
            SRQ, SOIND, ALCRIFD, ALCHFD, SOD, ALCOQD, SRIND, ALCOFD, SRIFD, ALCRI, SHOND, SRID,
            ALCROIF, SRIQD, ALCRF, SOFD, SHFD, ALCROIN, ALCND, ALCHIQ, SHOIND, SID, SRIN, ALCHOID,
            ALCHOIN, ALCHOIND, ALCRQ, ALCHOI, ALCR, ALCROIND, SROQD, ALCHOF, ALCROND, ALCHQ, ALCQD,
            SROD, ALCROID, ALCOIQD, SROQ, ALCOND, ALCHND, ALCROD, SROFD, ALCHOQD, SHOIFD, ALCRIQ,
            SRF, SIND, ALCRIND, SHIFD, ALCRND, SRD, SROI, SROND, ALCHO, SHOD, ALCHD, ALCRFD,
            ALCHOIQ, SOND, SHQD, ALCRD, SRND, ALCRIQD, SOIQD, SFD, SROF, SROIF, SOID, SRN, ALCHIFD,
            SROIFD, SIFD, SRQD, SHOID, SRIQ, SHOIQD, ALCHON, ALCROFD, ALCHOQ, ALCRQD, ALCIQD,
            ALCROIQ, ALCHOFD, ALCROIFD, ALCHQD, ALCROI, SRI, ALCFD, ALCRN, ALCRO);
    }

    protected static List<Languages> belowU() {
        return l(ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ,
            ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI,
            SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI,
            SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD, SHOQD, SIQD, ALCRIFD, ALCOD, ALCHID,
            SOIFD, SRIFD, SRF, ALCHND, ALCOIND, ALCHFD, SROIND, ALCHOIN, ALCROID, SRFD, ALCRIND,
            SND, ALCHOIFD, ALCROIN, SOND, ALCHOI, SHOIFD, SROIFD, ALCOIQD, ALCROQ, SHIFD, ALCHIQ,
            SOFD, SOID, ALCROQD, SRIF, SFD, ALCIND, ALCHOFD, SRID, ALCROF, ALCRIQ, ALCHOIQD, SOIQD,
            SROID, ALCHOID, ALCHIND, ALCRQD, SHIQD, SHOND, ALCROIFD, ALCRQ, ALCHO, SRD, SRIND,
            ALCROIQD, SOQD, SHOFD, ALCRIQD, ALCOID, ALCHI, ALCHOD, ALCHON, ALCIQD, ALCROIF, ALCHIQD,
            ALCHOQ, SROF, SROD, SROIF, ALCRO, SHD, SROIN, ALCRF, ALCHOIQ, ALCROI, ALCRN, ALCRON,
            SHID, ALCHIFD, SIFD, SRI, ALCND, ALCID, ALCHQ, SIND, SOIND, ALCOQD, SD, SHND, SHOID,
            ALCRIN, ALCOIFD, ALCROD, ALCHIN, ALCFD, ALCROIQ, SROND, SHQD, SOD, SRON, SHOIND, ALCHIF,
            SROQD, SHOD, ALCHF, ALCRIF, SQD, ALCRI, ALCRD, SRN, ALCHD, ALCHOF, ALCROND, SRND,
            ALCHOIND, SRQD, SID, ALCH, SRIQ, SRO, ALCHOND, ALCOND, SROFD, ALCROFD, SRIQD, ALCRFD,
            SHOIQD, SHFD, SROQ, ALCRND, ALCRID, ALCROIND, SRQ, ALCIFD, ALCR, SROI, SR, ALCHN,
            ALCHQD, SRIN, ALCQD, ALCHOIF, ALCOFD, ALCHOQD);
    }

    protected static List<Languages> belowTRAN() {
        return l(S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH,
            SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN,
            SHIND, SROIQ, SROIQD, SHOQD, SHD, SROIN, SIQD, SOIFD, SHID, SRIFD, SRF, SIFD, SRI,
            SROIND, SRFD, SIND, SOIND, SND, SD, SHND, SHOID, SOND, SHOIFD, SROIFD, SROND, SHQD, SOD,
            SRON, SHOIND, SHIFD, SOFD, SROQD, SHOD, SQD, SRN, SOID, SRIF, SFD, SRND, SRQD, SID,
            SRID, SRIQ, SRO, SROFD, SRIQD, SOIQD, SHOIQD, SHFD, SROID, SHIQD, SHOND, SROQ, SRD,
            SRIND, SOQD, SHOFD, SRQ, SROI, SR, SROF, SRIN, SROD, SROIF);
    }

    protected static List<Languages> belowO() {
        return l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI,
            SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD,
            ELPLUSPLUS, ALCROF, SOND, ALCOQD, SHOIQD, ALCROI, ALCROND, ALCROIQD, SROIF, ALCHOD,
            ALCHOIN, ALCHOFD, SHOND, ALCHOI, ALCOFD, SROND, SHOID, ALCHON, ALCOIQD, SOQD, SOIQD,
            ALCHO, ALCRON, ALCROIQ, ALCROQD, ALCROFD, ALCOIFD, SHOD, ALCOID, ALCRO, SROFD, SROID,
            SHOIFD, ALCHOIQ, ALCROIN, ALCOND, ALCOD, SOIND, ALCHOF, ALCHOID, SROIN, ALCROIND,
            ALCROQ, ALCHOQD, SROQ, ALCOIND, ALCHOIF, ALCROID, ALCHOQ, ALCROIF, SROQD, ALCHOIQD,
            ALCHOIFD, SHOQD, ALCHOND, SROF, SOIFD, SOD, SROIFD, SHOFD, ALCROD, SRO, SOID, ALCROIFD,
            SHOIND, SROD, SRON, SROI, SROIND, ALCHOIND, SOFD);
    }

    protected static List<Languages> belowIF() {
        return l(ALCIF, ALCOIF, SIF, SOIF, SHIF, SHOIF, SOIFD, ALCRIFD, SRIF, ALCRIF, SROIFD,
            ALCROIF, SHOIFD, SROIF, ALCHIFD, ALCROIFD, ALCOIFD, SIFD, ALCIFD, ALCHOIFD, ALCHIF,
            ALCHOIF, SHIFD, SRIFD);
    }

    protected static List<Languages> belowF() {
        return l(ALCF, ALCOF, ALCIF, ALCOIF, SF, SOF, SIF, SOIF, SHF, SHOF, SHIF, SHOIF, SRF,
            ALCHOFD, ALCHIFD, ALCHIF, SROF, ALCHOIF, ALCRIFD, SHFD, SRFD, ALCRFD, SHIFD, SOIFD,
            SROIFD, ALCOIFD, ALCOFD, SRIFD, SFD, SRIF, ALCRIF, SROFD, SIFD, SHOFD, ALCROIF, ALCIFD,
            SHOIFD, ALCHOIFD, ALCROIFD, SOFD, ALCFD, ALCROF, ALCRF, SROIF, ALCHF, ALCROFD, ALCHFD,
            ALCHOF);
    }

    protected static List<Languages> belowCU() {
        return l(ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ,
            ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI,
            SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI,
            SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD, SRIQD, SRQD, SHIQD, ALCROQ, ALCH,
            ALCRIQD, SROQD, SROIF, ALCROD, ALCOD, SIQD, ALCFD, ALCHOD, ALCID, ALCROIQD, ALCROIF,
            SROIN, SHOFD, ALCRD, SRN, SRIN, SROD, ALCND, SRO, ALCRQD, SHND, SROFD, SD, ALCRIQ,
            ALCRO, SRQ, ALCROID, SHOIFD, SRIND, ALCHIND, ALCHOIND, SOND, SHD, ALCRIFD, SROIND,
            ALCHD, SOIQD, SROND, ALCHOQD, SROF, ALCOID, SND, SRFD, SRD, SIFD, ALCRIF, SHID, SROQ,
            SROI, ALCOIND, ALCROF, SHIFD, ALCHI, ALCHOF, ALCHOID, ALCROIQ, ALCHOIQ, ALCIQD, ALCROI,
            SRIF, ALCROND, ALCIFD, ALCHIQD, SOD, SHQD, ALCHQD, SFD, ALCHQ, ALCHOI, SHOIQD, SRND,
            ALCOND, ALCOFD, ALCROIFD, ALCHOIQD, SHOIND, SOIFD, SROID, ALCHOND, ALCOQD, ALCHID,
            ALCRFD, ALCRI, ALCHIFD, SRIFD, ALCRID, SRF, ALCQD, ALCRND, ALCHN, ALCRN, ALCHFD,
            ALCHOIFD, ALCRON, ALCIND, SID, SQD, ALCHF, SHFD, ALCHIF, ALCR, ALCRIN, SIND, SRON,
            SHOQD, ALCHIN, ALCROFD, ALCHON, ALCOIFD, ALCHIQ, ALCHOQ, ALCRF, SRIQ, ALCROQD, ALCROIN,
            SR, ALCHOFD, SROIFD, ALCRIND, SOID, SOIND, SHOND, SRID, SRI, ALCHOIN, SHOID, SOFD,
            ALCHND, ALCHOIF, ALCRQ, ALCROIND, ALCHO, SOQD, SHOD, ALCOIQD);
    }

    protected static List<Languages> belowCUO() {
        return l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI,
            SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD,
            ALCHOIND, ALCROI, SOIQD, ALCHOIQD, SHOIND, ALCROIFD, ALCHOQ, SOFD, ALCROFD, ALCOIND,
            SOND, SHOID, SROI, SHOD, ALCROIN, SROF, SHOFD, ALCROD, SOQD, SRO, ALCOFD, ALCROND,
            ALCHOI, ALCRON, SROQD, SROQ, ALCHON, ALCHOIQ, SOIFD, ALCHOD, SHOQD, ALCROID, SROIF,
            SROIN, ALCHOIF, SROIFD, SROD, ALCOID, ALCHOQD, SHOIQD, ALCRO, ALCROF, ALCROIQ, SHOIFD,
            SOID, SROFD, SROIND, ALCHOIN, SOD, ALCHOF, SHOND, SROID, ALCROIF, SRON, ALCROQ, SROND,
            ALCHOND, ALCHOID, ALCROQD, SOIND, ALCHO, ALCOIFD, ALCHOIFD, ALCROIQD, ALCOIQD, ALCOQD,
            ALCOD, ALCOND, ALCROIND, ALCHOFD);
    }

    protected static List<Languages> belowUniversal() {
        return l(FL0, FLMINUS, FL, AL, ALE, ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF,
            ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF,
            SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF,
            SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD, SOFD,
            ALCROIND, ALCHOIQD, SOND, SRIQD, ALCQD, ALCIFD, ALCOFD, SIND, SRIFD, SHND, SIQD, ALCHID,
            SROI, SRI, ALCHOQ, SRND, SRIQ, SROQ, SRIND, ALCHN, ALCHIF, ALCHO, ALCHOIND, ALCRO, SRO,
            ALCROND, SRID, SRQ, ALCRIN, SOIND, SHIFD, SROND, ALCOD, SROD, ALCHOID, SHOID, SRIN,
            ALCRIF, ALCROQ, ALCRIQD, SRON, ALCROQD, SHOD, ALCHI, ALCFD, SROFD, ALCROIQD, SROIFD, SD,
            ALCHOIFD, SRQD, SHOIQD, ALCHOD, SOIQD, SOD, SROID, ALCHOQD, ALCHIFD, SHFD, ALCHOI, SHQD,
            ALCIND, ALCHOIN, SROIF, ALCROID, SOQD, SROIN, ALCOID, ALCHF, ALCHOND, ALCRIQ, ALCROD,
            ALCR, ALCROIN, ALCHOF, ALCHND, ALCOIND, ALCRF, SQD, ALCHIND, ALCHIQD, SHOND, SFD, ALCRI,
            ALCRON, ALCROI, ALCHFD, SHIQD, ALCHOIF, ALCROIQ, ALCOQD, ALCOIQD, ALCROIFD, SIFD, ALCND,
            ALCRFD, ALCRQD, SRF, SHD, ALCHIQ, ALCHD, SHOQD, SRD, ALCHOIQ, SRIF, ALCRND, SHID,
            ALCRIND, SHOFD, ALCRD, SRN, SROF, ALCROIF, SOID, ALCRQ, SRFD, ALCOIFD, ALCHON, ALCHQD,
            ALCRIFD, ALCROF, SHOIFD, ALCRID, ALCHIN, ALCHOFD, ALCOND, SROQD, SHOIND, ALCRN, SND,
            ALCHQ, SID, ALCIQD, SOIFD, ALCH, SROIND, SR, ALCROFD, ALCID);
    }

    protected static List<Languages> belowQD() {
        return l(ALCRIQD, SOQD, SRIQD, ALCHIQD, ALCIQD, SHIQD, SRQD, SQD, SHOQD, SIQD, SHOIQD,
            ALCHOQD, ALCHOIQD, SHQD, ALCROIQD, ALCOQD, SOIQD, SROQD, ALCHQD, ALCOIQD, SROIQD,
            ALCRQD, ALCROQD, ALCQD);
    }

    protected static List<Languages> belowQ() {
        return l(ALCQ, ALCOQ, ALCIQ, ALCOIQ, SQ, SOQ, SIQ, SOIQ, SHQ, SHOQ, SHIQ, SHOIQ, SROIQ,
            SROIQD, SROQD, ALCRIQD, SQD, ALCHQ, SOIQD, ALCQD, SRQ, SHOQD, ALCOQD, ALCOIQD, SROQ,
            SRQD, ALCROIQD, ALCROQ, SRIQ, SHOIQD, ALCHOQ, ALCRQD, ALCHOQD, ALCIQD, SRIQD, ALCRIQ,
            ALCROIQ, ALCHIQ, ALCHOIQD, ALCHQD, ALCROQD, ALCRQ, ALCHIQD, SIQD, ALCHOIQ, SHIQD, SHQD,
            SOQD);
    }

    protected static List<Languages> belowD() {
        return l(ALCD, SHIND, SROIQD, ALCROIQD, ALCHOQD, SHOID, ALCRND, ALCROND, SROND, SOFD,
            ALCHID, ALCRID, ALCROQD, SHOIFD, ALCQD, ALCRFD, SQD, SHOD, SOQD, ALCOND, ALCIQD,
            ALCROIND, SHOIQD, ALCOQD, SHOFD, ALCHFD, SND, SOIQD, ALCRIQD, ALCHOND, ALCRIND, ALCHQD,
            SOIFD, ALCHOD, ALCHND, SHD, SHIFD, SROFD, ALCOIQD, ALCHOID, SHID, ALCOID, ALCHOFD,
            ALCROIFD, ALCOIND, ALCHIND, SRQD, ALCIFD, SD, ALCHOIND, SHIQD, SROQD, SROIND, ALCROFD,
            SHOQD, SID, SOD, ALCOIFD, ALCFD, ALCRQD, SHOIND, SRFD, SRND, SRIND, ALCHIQD, SROD,
            SROIFD, SHQD, ALCROD, ALCID, SOIND, ALCHIFD, ALCIND, SHFD, ALCRD, SOND, ALCND, ALCOD,
            SIND, SOID, SHND, SIQD, SRD, SHOND, ALCOFD, ALCHOIQD, ALCROID, SFD, SROID, ALCHD,
            ALCHOIFD, SIFD, SRIFD, SRIQD, ALCRIFD, SRID);
    }

    protected static List<Languages> belowH() {
        return l(SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN,
            SHOIQ, SHIN, SHIND, ALCHOF, SHIQD, SHOFD, ALCHQ, ALCHOD, ALCHIQ, ALCHOND, ALCHIND,
            ALCHI, ALCHD, SHOIFD, ALCHOIN, ALCHIFD, ALCHOQD, ALCHOIQ, SHOND, ALCHON, ALCHOIFD, SHID,
            ALCHIF, ALCHND, ALCHOIND, ALCHIN, ALCHOIQD, ALCHIQD, ALCHOFD, ALCHQD, SHFD, ALCHOQ,
            ALCHO, ALCHFD, SHOQD, SHND, ALCHOI, SHOIND, SHQD, SHOID, ALCHOID, ALCHN, ALCHID, SHOD,
            ALCH, SHOIQD, ALCHF, ALCHOIF, SHD, SHIFD);
    }

    protected static List<Languages> belowHD() {
        return l(SHIND, ALCHOIND, SHOQD, ALCHOD, ALCHD, ALCHOFD, SHIQD, SHND, ALCHND, SHOIFD,
            ALCHOIQD, SHID, SHOD, SHOND, ALCHIQD, ALCHOND, ALCHIFD, ALCHOIFD, SHQD, SHFD, SHIFD,
            ALCHOID, SHD, SHOID, ALCHIND, ALCHOQD, SHOFD, SHOIND, ALCHQD, ALCHFD, ALCHID, SHOIQD);
    }

    protected static List<Languages> belowI() {
        return l(ALCI, ALCOI, ALCIF, ALCIN, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, SI, SIF, SIN, SIQ, SOI,
            SOIF, SOIN, SOIQ, SHI, SHIF, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ,
            SROIQD, SIQD, ALCRID, SRI, SRIQD, ALCOIND, ALCHOIF, SROIND, ALCHOID, SOID, ALCIND, SRIQ,
            ALCRI, SOIND, ALCRIND, SRIF, SHIQD, ALCID, ALCRIN, SRIN, ALCIQD, ALCRIQ, ALCROIF,
            ALCHIN, ALCHOIQ, ALCHIQD, ALCHI, ALCHID, SHOIQD, ALCRIQD, SRID, SRIND, ALCHIFD, SROID,
            SHOID, ALCOIQD, ALCHIF, ALCROIQD, ALCRIFD, SIND, ALCROID, SHID, SHIFD, SOIFD, ALCHIND,
            SROIFD, SROI, ALCOIFD, SHOIND, SRIFD, SROIN, ALCROIND, ALCHOIN, ALCRIF, ALCHIQ, SIFD,
            SOIQD, ALCHOI, ALCROI, ALCROIQ, ALCOID, ALCIFD, SHOIFD, ALCHOIFD, SID, ALCROIFD,
            ALCHOIND, SROIF, ALCHOIQD, ALCROIN);
    }

    protected static List<Languages> belowC() {
        return l(ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ,
            ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI,
            SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI,
            SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD, ALCROIND, ALCOFD, ALCHOIQ, ALCRF, SR,
            ALCRIQ, ALCRD, SRIFD, ALCRO, SQD, SRI, ALCID, ALCH, SND, SOQD, SHOND, ALCRI, ALCHIND,
            ALCOND, ALCHIFD, SRIN, SOIND, SROIFD, SRO, SRF, ALCHOID, SROI, SROIN, ALCOID, ALCHIQD,
            SHND, ALCHOI, SROF, SHOIND, SHOD, SIQD, ALCQD, SHIQD, SOD, ALCHI, ALCHND, ALCHOIQD,
            ALCOIQD, ALCROF, SROIND, ALCRID, SFD, SHFD, ALCRFD, SROQD, SHOIFD, ALCROI, SHOQD,
            ALCRIQD, ALCFD, ALCHOD, ALCHF, ALCROIQD, SROFD, ALCHOIF, ALCRON, ALCROIF, ALCIND,
            ALCHIQ, SROD, ALCRIN, ALCHOQ, SID, ALCHQ, ALCHOND, ALCOQD, ALCRIFD, ALCRIF, ALCROFD,
            SRIQ, SRQ, ALCROD, ALCHD, ALCHN, SIFD, SRN, SRQD, SROND, ALCRIND, ALCHOF, SHOIQD,
            ALCHOFD, SHQD, ALCR, SD, SROID, SOID, ALCROIN, SRON, SRD, ALCRN, ALCRND, SOFD, ALCRQ,
            SRIND, ALCHOQD, ALCOIFD, ALCOD, SHIFD, ALCOIND, SIND, ALCHQD, ALCROQ, SOIQD, SHOFD,
            ALCHFD, ALCROQD, ALCHOIND, ALCHOIN, SROIF, ALCROID, ALCROIFD, ALCHIN, SROQ, SHID, SHD,
            ALCROND, ALCIFD, ALCIQD, ALCHOIFD, SRID, ALCHON, ALCRQD, SRFD, ALCROIQ, ALCHID, ALCND,
            SOIFD, SRND, SRIF, ALCHO, SHOID, ALCHIF, SOND, SRIQD);
    }

    protected static List<Languages> belowR() {
        return l(ELPLUSPLUS, SROIQ, SROIQD, SROFD, SRI, SRIQ, SRD, SROF, ALCROF, ALCROIQD, ALCRIN,
            ALCROIF, ALCRFD, ALCRIQ, SROQ, SROND, ALCR, ALCROI, SROIF, SRN, ALCRIF, ALCRI, SRIFD,
            SRIND, ALCRQD, SRF, SRIF, ALCRIND, ALCRID, ALCROID, ALCROND, ALCROIN, ALCRIFD, SROQD,
            ALCRND, ALCROIFD, SRFD, SRQD, SRIN, SROIFD, ALCRD, ALCRN, ALCRQ, ALCRON, ALCROIQ,
            ALCROIND, SRO, SROD, ALCROQ, SRON, ALCROFD, SRID, SRIQD, SRQ, ALCROQD, ALCRIQD, SRND,
            SROID, SROIN, ALCRF, ALCROD, SR, SROI, ALCRO, SROIND);
    }

    protected static List<Languages> expressR() {
        return l(ALCR, ELPLUSPLUS);
    }

    @Test
    public void testAssertion() {
        DLExpressivityChecker testsubject = new DLExpressivityChecker(ont());
        List<Construct> constructsFound = testsubject.getConstructs();
        if (constructsFound.isEmpty()) {
            return;
        }
        // if (!testsubject.getConstructs().isEmpty()) {
        List<Languages> below = new ArrayList<>();
        List<Languages> minimalLanguages = new ArrayList<>();
        for (Languages c : Languages.values()) {
            if (testsubject.isWithin(c)) {
                below.add(c);
            }
            if (testsubject.minimal(c)) {
                minimalLanguages.add(c);
            }
        }
        Collection<Languages> expressibleInLanguages = testsubject.expressibleInLanguages();
        assertEquals(delta("expressible", expressible, expressibleInLanguages), expressible,
            expressibleInLanguages);
        assertEquals(constructs, constructsFound);
        assertEquals(delta("below", within, below), new HashSet<>(within), new HashSet<>(below));
        assertEquals(expected, testsubject.getDescriptionLogicName());
        assertEquals(delta("minimal", minimal, minimalLanguages), new HashSet<>(minimal),
            new HashSet<>(minimalLanguages));
        // String message = constructsFound + "\t" + "expressible in " + expressibleInLanguages
        // + "\tminimal:\t" + minimalLanguages;// + "\twithin:\t" + below;
        // System.out.println(message);
    }

    private static String delta(String prefix, Collection<Languages> within2,
        Collection<Languages> below) {
        Set<Languages> onlyFirst = new HashSet<>(within2);
        onlyFirst.removeAll(below);
        Set<Languages> onlySecond = new HashSet<>(below);
        onlySecond.removeAll(within2);
        return prefix + "Only in first list: " + onlyFirst + "    Only in second list: \n"
            + onlySecond.stream().map(Languages::name).collect(Collectors.joining(", ")) + "\n\n";
    }

    public Set<OWLOntology> ont() {
        OWLOntology o = getOWLOntology();
        o.getOWLOntologyManager().addAxiom(o, object);
        Set<OWLOntology> singleton = Collections.singleton(o);
        return singleton;
    }
}
