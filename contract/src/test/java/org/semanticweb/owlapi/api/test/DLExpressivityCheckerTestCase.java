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
import static org.semanticweb.owlapi.utility.Construct.C;
import static org.semanticweb.owlapi.utility.Construct.CINT;
import static org.semanticweb.owlapi.utility.Construct.D;
import static org.semanticweb.owlapi.utility.Construct.E;
import static org.semanticweb.owlapi.utility.Construct.F;
import static org.semanticweb.owlapi.utility.Construct.H;
import static org.semanticweb.owlapi.utility.Construct.I;
import static org.semanticweb.owlapi.utility.Construct.O;
import static org.semanticweb.owlapi.utility.Construct.Q;
import static org.semanticweb.owlapi.utility.Construct.R;
import static org.semanticweb.owlapi.utility.Construct.RRESTR;
import static org.semanticweb.owlapi.utility.Construct.TRAN;
import static org.semanticweb.owlapi.utility.Construct.U;
import static org.semanticweb.owlapi.utility.Construct.UNIVRESTR;
import static org.semanticweb.owlapi.utility.Languages.AL;
import static org.semanticweb.owlapi.utility.Languages.ALC;
import static org.semanticweb.owlapi.utility.Languages.ALCD;
import static org.semanticweb.owlapi.utility.Languages.ALCF;
import static org.semanticweb.owlapi.utility.Languages.ALCI;
import static org.semanticweb.owlapi.utility.Languages.ALCIF;
import static org.semanticweb.owlapi.utility.Languages.ALCIN;
import static org.semanticweb.owlapi.utility.Languages.ALCIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCN;
import static org.semanticweb.owlapi.utility.Languages.ALCO;
import static org.semanticweb.owlapi.utility.Languages.ALCOF;
import static org.semanticweb.owlapi.utility.Languages.ALCOI;
import static org.semanticweb.owlapi.utility.Languages.ALCOIF;
import static org.semanticweb.owlapi.utility.Languages.ALCOIN;
import static org.semanticweb.owlapi.utility.Languages.ALCOIQ;
import static org.semanticweb.owlapi.utility.Languages.ALCON;
import static org.semanticweb.owlapi.utility.Languages.ALCOQ;
import static org.semanticweb.owlapi.utility.Languages.ALCQ;
import static org.semanticweb.owlapi.utility.Languages.ALE;
import static org.semanticweb.owlapi.utility.Languages.EL;
import static org.semanticweb.owlapi.utility.Languages.ELPLUSPLUS;
import static org.semanticweb.owlapi.utility.Languages.FL;
import static org.semanticweb.owlapi.utility.Languages.FL0;
import static org.semanticweb.owlapi.utility.Languages.FLMINUS;
import static org.semanticweb.owlapi.utility.Languages.S;
import static org.semanticweb.owlapi.utility.Languages.SF;
import static org.semanticweb.owlapi.utility.Languages.SH;
import static org.semanticweb.owlapi.utility.Languages.SHF;
import static org.semanticweb.owlapi.utility.Languages.SHI;
import static org.semanticweb.owlapi.utility.Languages.SHIF;
import static org.semanticweb.owlapi.utility.Languages.SHIN;
import static org.semanticweb.owlapi.utility.Languages.SHIND;
import static org.semanticweb.owlapi.utility.Languages.SHIQ;
import static org.semanticweb.owlapi.utility.Languages.SHN;
import static org.semanticweb.owlapi.utility.Languages.SHO;
import static org.semanticweb.owlapi.utility.Languages.SHOF;
import static org.semanticweb.owlapi.utility.Languages.SHOI;
import static org.semanticweb.owlapi.utility.Languages.SHOIF;
import static org.semanticweb.owlapi.utility.Languages.SHOIN;
import static org.semanticweb.owlapi.utility.Languages.SHOIQ;
import static org.semanticweb.owlapi.utility.Languages.SHON;
import static org.semanticweb.owlapi.utility.Languages.SHOQ;
import static org.semanticweb.owlapi.utility.Languages.SHQ;
import static org.semanticweb.owlapi.utility.Languages.SI;
import static org.semanticweb.owlapi.utility.Languages.SIF;
import static org.semanticweb.owlapi.utility.Languages.SIN;
import static org.semanticweb.owlapi.utility.Languages.SIQ;
import static org.semanticweb.owlapi.utility.Languages.SN;
import static org.semanticweb.owlapi.utility.Languages.SO;
import static org.semanticweb.owlapi.utility.Languages.SOF;
import static org.semanticweb.owlapi.utility.Languages.SOI;
import static org.semanticweb.owlapi.utility.Languages.SOIF;
import static org.semanticweb.owlapi.utility.Languages.SOIN;
import static org.semanticweb.owlapi.utility.Languages.SOIQ;
import static org.semanticweb.owlapi.utility.Languages.SON;
import static org.semanticweb.owlapi.utility.Languages.SOQ;
import static org.semanticweb.owlapi.utility.Languages.SQ;
import static org.semanticweb.owlapi.utility.Languages.SROIQ;
import static org.semanticweb.owlapi.utility.Languages.SROIQD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.utility.Construct;
import org.semanticweb.owlapi.utility.DLExpressivityChecker;
import org.semanticweb.owlapi.utility.Languages;

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
        List<Construct> c, List<Languages> exp, List<Languages> below, List<Languages> min) {
        this.object = object;
        this.expected = expectedStrict;
        constructs = c;
        expressible = exp;
        within = below;
        minimal = min;
    }

    static <T> List<T> l(T... t) {
        return Arrays.asList(t);
    }

    @Parameterized.Parameters(name = "{index} {1} {0}")
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        List<Languages> fl0el = l(FL0, EL);
        List<Object> empty = l();
        List<Construct> i = l(I);
        List<Construct> rrestrd = l(RRESTR, D);
        List<Languages> sroiqd = l(SROIQD);
        List<Construct> cuo = l(C, U, O);
        List<Construct> c = l(C);
        List<Construct> d = l(D);
        List<Construct> r = l(R);
        List<Construct> cu = l(C, U);
        List<Construct> hd = l(H, D);
        List<Construct> h = l(H);
        List<Construct> rrestr = l(RRESTR);
        List<Construct> o = l(O);
        List<Construct> f = l(F);
        List<Construct> lif = l(I, F);
        List<Construct> fd = l(F, D);
        List<Construct> t = l(TRAN);
        List<Construct> cint = l(CINT);
        List<Construct> u = l(U);
        List<Construct> q = l(Q);
        List<Construct> uo = l(U, O);
        List<Construct> qd = l(Q, D);
        List<Construct> ed = l(E, D);
        List<Construct> e = l(E);
        List<Construct> univ = l(UNIVRESTR);
        List<Construct> eo = l(E, O);
        List<Languages> elplusplus = l(ELPLUSPLUS);
        List<Languages> alcf = l(ALCF);
        List<Languages> shind = l(SHIND);
        return Arrays.asList(
        //@formatter:off
            new Object[] {b.dRange(),           "1  AL(D)",    "RRESTR(D)" , rrestrd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.dDef(),             "2  AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.decC(),             "3  AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.decOp(),            "4  AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.decDp(),            "5  AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.decDt(),            "6  AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.decAp(),            "7  AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.decI(),             "8  AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.assDi(),            "9  ALCO",     "CUO"       , cuo, l(ALCO), l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI, SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD), l(ALCO) },
            new Object[] {b.dc(),               "10 ALC",      "C"         , c, l(ALC), l(ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALC) },
            new Object[] {b.dDp(),              "11 AL(D)",    "(D)"       , d, l(ALCD), l(ALCD, SHIND, SROIQD), l(ALCD) },
            new Object[] {b.dOp(),              "12 ALR",      "R"         , r, elplusplus, elplusplus, elplusplus },
            new Object[] {b.du(),               "13 ALC",      "CU"        , cu, l(ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, S), l(ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, S) },
            new Object[] {b.ec(),               "14 AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.eDp(),              "15 ALH(D)",   "H(D)"      , hd, shind, shind, shind },
            new Object[] {b.eOp(),              "16 ALH",      "H"         , h, l(SH), l(SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND), l(SH) },
            new Object[] {b.fdp(),              "17 ALF(D)",   "F(D)"      , fd, empty, empty, empty },
            new Object[] {b.fop(),              "18 ALF",      "F"         , f, alcf, l(ALCF, ALCOF, ALCIF, ALCOIF, SF, SOF, SIF, SOIF, SHF, SHOF, SHIF, SHOIF), alcf },
            new Object[] {b.ifp(),              "19 ALIF",     "IF"        , lif, l(ALCIF), l(ALCIF, ALCOIF, SIF, SOIF, SHIF, SHOIF), l(ALCIF) },
            new Object[] {b.iop(),              "20 ALI",      "I"         , i, l(ALCI), l(ALCI, ALCOI, ALCIF, ALCIN, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, SI, SIF, SIN, SIQ, SOI, SOIF, SOIN, SOIQ, SHI, SHIF, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALCI) },
            new Object[] {b.irr(),              "21 ALR",      "R"         , r, elplusplus, elplusplus, elplusplus },
            new Object[] {b.ndp(),              "22 AL(D)",    "(D)"       , d, l(ALCD), l(ALCD, SHIND, SROIQD), l(ALCD) },
            new Object[] {b.nop(),              "23 AL",       ""          , empty, fl0el, empty, fl0el },
            new Object[] {b.opa(),              "24 AL",       ""          , empty, fl0el, empty, fl0el },
            new Object[] {b.opaInv(),           "25 ALI",      "I"         , i, l(ALCI), l(ALCI, ALCOI, ALCIF, ALCIN, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, SI, SIF, SIN, SIQ, SOI, SOIF, SOIN, SOIQ, SHI, SHIF, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALCI) },
            new Object[] {b.opaInvj(),          "26 ALI",      "I"         , i, l(ALCI), l(ALCI, ALCOI, ALCIF, ALCIN, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, SI, SIF, SIN, SIQ, SOI, SOIF, SOIN, SOIQ, SHI, SHIF, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALCI) },
            new Object[] {b.oDom(),             "27 AL",       "RRESTR"    , rrestr, l(FL), l(FL, SROIQ, SROIQD), l(FL) },
            new Object[] {b.oRange(),           "28 AL",       "RRESTR"    , rrestr, l(FL), l(FL, SROIQ, SROIQD), l(FL) },
            new Object[] {b.chain(),            "29 ALR",      "R"         , r, elplusplus, elplusplus, elplusplus },
            new Object[] {b.ref(),              "30 ALR",      "R"         , r, elplusplus, elplusplus, elplusplus },
            new Object[] {b.same(),             "31 ALO",      "O"         , o, l(ALCO,ELPLUSPLUS), l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI, SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD, ELPLUSPLUS), l(ALCO,ELPLUSPLUS) },
            new Object[] {b.subAnn(),           "32 AL",       ""          , empty, fl0el, empty, fl0el },
            new Object[] {b.subClass(),         "33 AL",       ""          , empty, fl0el, empty, fl0el },
            new Object[] {b.subData(),          "34 ALH(D)",   "H(D)"      , hd, shind, shind, shind },
            new Object[] {b.subObject(),        "35 ALH",      "H"         , h, l(SH), l(SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND), l(SH) },
            new Object[] {b.rule(),             "36 AL",       ""          , empty, fl0el, empty, fl0el },
            new Object[] {b.symm(),             "37 ALI",      "I"         , i, l(ALCI), l(ALCI, ALCOI, ALCIF, ALCIN, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, SI, SIF, SIN, SIQ, SOI, SOIF, SOIN, SOIQ, SHI, SHIF, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALCI) },
            new Object[] {b.trans(),            "38 AL+",      "+"         , t, l(S), l(S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(S) },
            new Object[] {b.hasKey(),           "39 AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.bigRule(),          "40 AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.ann(),              "41 AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.asymm(),            "42 ALR",      "R"         , r, elplusplus, elplusplus, elplusplus },
            new Object[] {b.annDom(),           "43 AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.annRange(),         "44 AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.ass(),              "45 AL",       ""          , empty, fl0el, l(), fl0el },
            new Object[] {b.assAnd(),           "46 AL",       "CINT"      , cint, fl0el, l(Languages.values()), fl0el },
            new Object[] {b.assOr(),            "47 ALU",      "U"         , u, l(ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, S), l(ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, S) },
            new Object[] {b.dRangeAnd(),        "48 AL(D)",    "RRESTR(D)" , rrestrd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.dRangeOr(),         "49 AL(D)",    "RRESTR(D)" , rrestrd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.assNot(),           "50 ALC",      "C"         , c, l(ALC), l(ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALC) },
            new Object[] {b.assNotAnon(),       "51 ALC",      "C"         , c, l(ALC), l(ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(ALC) },
            new Object[] {b.assSome(),          "52 ALE",      "E"         , e, l(EL), l(EL, ALE, ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD, ELPLUSPLUS), l(EL) },
            new Object[] {b.assAll(),           "53 AL",       "UNIVRESTR" , univ, l(FL0), l(FL0, FLMINUS, FL, AL, ALE, ALC, ALCD, ALCQ, ALCN, ALCF, ALCI, ALCO, ALCOI, ALCOF, ALCIF, ALCIN, ALCON, ALCOQ, ALCIQ, ALCOIQ, ALCOIN, ALCOIF, S, SI, SO, SF, SN, SQ, SOF, SIF, SON, SIN, SOQ, SIQ, SOI, SOIF, SOIN, SOIQ, SH, SHF, SHN, SHO, SHI, SHOF, SHIF, SHON, SHQ, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SHIN, SHIND, SROIQ, SROIQD), l(FL0) },
            new Object[] {b.assHas(),           "54 ALEO",     "EO"        , eo, l(ALCO,ELPLUSPLUS), l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI, SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD, ELPLUSPLUS), l(ALCO, ELPLUSPLUS) },
            new Object[] {b.assMin(),           "55 ALQ",      "Q"         , q, l(ALCQ), l(ALCQ, ALCOQ, ALCIQ, ALCOIQ, SQ, SOQ, SIQ, SOIQ, SHQ, SHOQ, SHOIQ, SROIQ, SROIQD), l(ALCQ) },
            new Object[] {b.assMax(),           "56 ALQ",      "Q"         , q, l(ALCQ), l(ALCQ, ALCOQ, ALCIQ, ALCOIQ, SQ, SOQ, SIQ, SOIQ, SHQ, SHOQ, SHOIQ, SROIQ, SROIQD), l(ALCQ) },
            new Object[] {b.assEq(),            "57 ALQ",      "Q"         , q, l(ALCQ), l(ALCQ, ALCOQ, ALCIQ, ALCOIQ, SQ, SOQ, SIQ, SOIQ, SHQ, SHOQ, SHOIQ, SROIQ, SROIQD), l(ALCQ) },
            new Object[] {b.assHasSelf(),       "58 ALR",      "R"         , r, elplusplus, elplusplus, elplusplus },
            new Object[] {b.assOneOf(),         "59 ALUO",     "UO"        , uo, l(ALCO), l(ALCO, ALCOI, ALCOF, ALCON, ALCOQ, ALCOIQ, ALCOIN, ALCOIF, SO, SOF, SON, SOQ, SOI, SOIF, SOIN, SOIQ, SHO, SHOF, SHON, SHOQ, SHIQ, SHOI, SHOIF, SHOIN, SHOIQ, SROIQ, SROIQD), l(ALCO) },
            new Object[] {b.assDSome(),         "60 ALE(D)",   "E(D)"      , ed, l(ALCD), l(ALCD, SHIND, SROIQD), l(ALCD) },
            new Object[] {b.assDAll(),          "61 AL(D)",    "(D)"       , d, l(ALCD), l(ALCD, SHIND, SROIQD), l(ALCD) },
            new Object[] {b.assDHas(),          "62 AL(D)",    "(D)"       , d, l(ALCD), l(ALCD, SHIND, SROIQD), l(ALCD) },
            new Object[] {b.assDMin(),          "63 ALQ(D)",   "Q(D)"      , qd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.assDMax(),          "64 ALQ(D)",   "Q(D)"      , qd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.assDEq(),           "65 ALQ(D)",   "Q(D)"      , qd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.dOneOf(),           "66 AL(D)",    "RRESTR(D)" , rrestrd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.dNot(),             "67 AL(D)",    "RRESTR(D)" , rrestrd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.dRangeRestrict(),   "68 AL(D)",    "RRESTR(D)" , rrestrd, sroiqd, sroiqd, sroiqd },
            new Object[] {b.assD(),             "69 AL(D)",    "(D)"       , d, l(ALCD), l(ALCD, SHIND, SROIQD), l(ALCD) },
            new Object[] {b.assDPlain(),        "70 AL(D)",    "(D)"       , d, l(ALCD), l(ALCD, SHIND, SROIQD), l(ALCD) },
            new Object[] {b.dDom(),             "71 AL(D)",    "RRESTR(D)" , rrestrd, sroiqd, sroiqd, sroiqd }
            );
        //@formatter:on
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
        assertEquals(expressible, expressibleInLanguages);
        assertEquals(constructs, constructsFound);
        assertEquals(within, below);
        assertEquals(expected, testsubject.getDescriptionLogicName());
        assertEquals(minimal, minimalLanguages);
        // String message = constructsFound + "\t" + "expressible in " + expressibleInLanguages
        // + "\tminimal:\t" + minimalLanguages + "\twithin:\t" + below;
        // System.out.println(message);
    }

    public Set<OWLOntology> ont() {
        OWLOntology o = getOWLOntology();
        o.add(object);
        Set<OWLOntology> singleton = Collections.singleton(o);
        return singleton;
    }
}
