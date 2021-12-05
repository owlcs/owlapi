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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.apitest.TestFiles.AANN;
import static org.semanticweb.owlapi.apitest.TestFiles.ACALL;
import static org.semanticweb.owlapi.apitest.TestFiles.ACHAS;
import static org.semanticweb.owlapi.apitest.TestFiles.ACL;
import static org.semanticweb.owlapi.apitest.TestFiles.ACLAND;
import static org.semanticweb.owlapi.apitest.TestFiles.ACLOR;
import static org.semanticweb.owlapi.apitest.TestFiles.ACNOT;
import static org.semanticweb.owlapi.apitest.TestFiles.ACSOME;
import static org.semanticweb.owlapi.apitest.TestFiles.ADALL;
import static org.semanticweb.owlapi.apitest.TestFiles.ADEQ;
import static org.semanticweb.owlapi.apitest.TestFiles.ADHAS;
import static org.semanticweb.owlapi.apitest.TestFiles.ADMAX;
import static org.semanticweb.owlapi.apitest.TestFiles.ADMIN;
import static org.semanticweb.owlapi.apitest.TestFiles.ADONEOF;
import static org.semanticweb.owlapi.apitest.TestFiles.ADSOME;
import static org.semanticweb.owlapi.apitest.TestFiles.ALL;
import static org.semanticweb.owlapi.apitest.TestFiles.AND;
import static org.semanticweb.owlapi.apitest.TestFiles.ANDP;
import static org.semanticweb.owlapi.apitest.TestFiles.ANNI;
import static org.semanticweb.owlapi.apitest.TestFiles.ANNSHORT;
import static org.semanticweb.owlapi.apitest.TestFiles.ANOP;
import static org.semanticweb.owlapi.apitest.TestFiles.AOEQ;
import static org.semanticweb.owlapi.apitest.TestFiles.AOINV;
import static org.semanticweb.owlapi.apitest.TestFiles.AOMAX;
import static org.semanticweb.owlapi.apitest.TestFiles.AOMIN;
import static org.semanticweb.owlapi.apitest.TestFiles.AONE;
import static org.semanticweb.owlapi.apitest.TestFiles.AOP;
import static org.semanticweb.owlapi.apitest.TestFiles.AOPJ;
import static org.semanticweb.owlapi.apitest.TestFiles.APD;
import static org.semanticweb.owlapi.apitest.TestFiles.APR;
import static org.semanticweb.owlapi.apitest.TestFiles.ASELF;
import static org.semanticweb.owlapi.apitest.TestFiles.BLN;
import static org.semanticweb.owlapi.apitest.TestFiles.C;
import static org.semanticweb.owlapi.apitest.TestFiles.CI;
import static org.semanticweb.owlapi.apitest.TestFiles.CNOT;
import static org.semanticweb.owlapi.apitest.TestFiles.DALL;
import static org.semanticweb.owlapi.apitest.TestFiles.DAND;
import static org.semanticweb.owlapi.apitest.TestFiles.DANN;
import static org.semanticweb.owlapi.apitest.TestFiles.DB;
import static org.semanticweb.owlapi.apitest.TestFiles.DC;
import static org.semanticweb.owlapi.apitest.TestFiles.DD;
import static org.semanticweb.owlapi.apitest.TestFiles.DDP;
import static org.semanticweb.owlapi.apitest.TestFiles.DEQ;
import static org.semanticweb.owlapi.apitest.TestFiles.DHAS;
import static org.semanticweb.owlapi.apitest.TestFiles.DIFF;
import static org.semanticweb.owlapi.apitest.TestFiles.DIND;
import static org.semanticweb.owlapi.apitest.TestFiles.DISJDP;
import static org.semanticweb.owlapi.apitest.TestFiles.DMAX;
import static org.semanticweb.owlapi.apitest.TestFiles.DMIN;
import static org.semanticweb.owlapi.apitest.TestFiles.DNOT;
import static org.semanticweb.owlapi.apitest.TestFiles.DONEOF;
import static org.semanticweb.owlapi.apitest.TestFiles.DOP;
import static org.semanticweb.owlapi.apitest.TestFiles.DOR;
import static org.semanticweb.owlapi.apitest.TestFiles.DP;
import static org.semanticweb.owlapi.apitest.TestFiles.DPI;
import static org.semanticweb.owlapi.apitest.TestFiles.DPR;
import static org.semanticweb.owlapi.apitest.TestFiles.DPRAND;
import static org.semanticweb.owlapi.apitest.TestFiles.DPRNOT;
import static org.semanticweb.owlapi.apitest.TestFiles.DPROR;
import static org.semanticweb.owlapi.apitest.TestFiles.DRA;
import static org.semanticweb.owlapi.apitest.TestFiles.DSJC;
import static org.semanticweb.owlapi.apitest.TestFiles.DSJOP;
import static org.semanticweb.owlapi.apitest.TestFiles.DSOME;
import static org.semanticweb.owlapi.apitest.TestFiles.DT;
import static org.semanticweb.owlapi.apitest.TestFiles.DTD;
import static org.semanticweb.owlapi.apitest.TestFiles.DTI;
import static org.semanticweb.owlapi.apitest.TestFiles.DU;
import static org.semanticweb.owlapi.apitest.TestFiles.EQC;
import static org.semanticweb.owlapi.apitest.TestFiles.EQDP;
import static org.semanticweb.owlapi.apitest.TestFiles.EQOP;
import static org.semanticweb.owlapi.apitest.TestFiles.FDP;
import static org.semanticweb.owlapi.apitest.TestFiles.FOP;
import static org.semanticweb.owlapi.apitest.TestFiles.HAS;
import static org.semanticweb.owlapi.apitest.TestFiles.HASKEY;
import static org.semanticweb.owlapi.apitest.TestFiles.I;
import static org.semanticweb.owlapi.apitest.TestFiles.IFP;
import static org.semanticweb.owlapi.apitest.TestFiles.II;
import static org.semanticweb.owlapi.apitest.TestFiles.INVERSE;
import static org.semanticweb.owlapi.apitest.TestFiles.IOP;
import static org.semanticweb.owlapi.apitest.TestFiles.IRI;
import static org.semanticweb.owlapi.apitest.TestFiles.IRII;
import static org.semanticweb.owlapi.apitest.TestFiles.IRR;
import static org.semanticweb.owlapi.apitest.TestFiles.MAX;
import static org.semanticweb.owlapi.apitest.TestFiles.MAXSIX;
import static org.semanticweb.owlapi.apitest.TestFiles.MIN5;
import static org.semanticweb.owlapi.apitest.TestFiles.MINMAX;
import static org.semanticweb.owlapi.apitest.TestFiles.MINMXSIX;
import static org.semanticweb.owlapi.apitest.TestFiles.NOT;
import static org.semanticweb.owlapi.apitest.TestFiles.OEQ;
import static org.semanticweb.owlapi.apitest.TestFiles.OMIN;
import static org.semanticweb.owlapi.apitest.TestFiles.ONE;
import static org.semanticweb.owlapi.apitest.TestFiles.OP;
import static org.semanticweb.owlapi.apitest.TestFiles.OPD;
import static org.semanticweb.owlapi.apitest.TestFiles.OPI;
import static org.semanticweb.owlapi.apitest.TestFiles.OPR;
import static org.semanticweb.owlapi.apitest.TestFiles.OR;
import static org.semanticweb.owlapi.apitest.TestFiles.R;
import static org.semanticweb.owlapi.apitest.TestFiles.SAME;
import static org.semanticweb.owlapi.apitest.TestFiles.SELF;
import static org.semanticweb.owlapi.apitest.TestFiles.SHORTRULE;
import static org.semanticweb.owlapi.apitest.TestFiles.SOME;
import static org.semanticweb.owlapi.apitest.TestFiles.SUBA;
import static org.semanticweb.owlapi.apitest.TestFiles.SUBC;
import static org.semanticweb.owlapi.apitest.TestFiles.SUBD;
import static org.semanticweb.owlapi.apitest.TestFiles.SUBO;
import static org.semanticweb.owlapi.apitest.TestFiles.SUBOP;
import static org.semanticweb.owlapi.apitest.TestFiles.SYMM;
import static org.semanticweb.owlapi.apitest.TestFiles.T;
import static org.semanticweb.owlapi.apitest.TestFiles.VAR1;
import static org.semanticweb.owlapi.apitest.TestFiles.adp;
import static org.semanticweb.owlapi.apitest.TestFiles.asymmetric;
import static org.semanticweb.owlapi.apitest.TestFiles.classvar2;
import static org.semanticweb.owlapi.apitest.TestFiles.diffvar2;
import static org.semanticweb.owlapi.apitest.TestFiles.dlsaferule;
import static org.semanticweb.owlapi.apitest.TestFiles.dpafalse;
import static org.semanticweb.owlapi.apitest.TestFiles.dpdomain;
import static org.semanticweb.owlapi.apitest.TestFiles.dpvar2;
import static org.semanticweb.owlapi.apitest.TestFiles.opavar2;
import static org.semanticweb.owlapi.apitest.TestFiles.v1;
import static org.semanticweb.owlapi.apitest.TestFiles.v2;
import static org.semanticweb.owlapi.apitest.TestFiles.v3;
import static org.semanticweb.owlapi.apitest.TestFiles.v34;
import static org.semanticweb.owlapi.apitest.TestFiles.var2;
import static org.semanticweb.owlapi.apitest.TestFiles.var236;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectComponentCollector;

class OWLObjectComponentCollectorTestCase {

    private static final String PLAIN_LITERAL =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral";
    private static final String FALSE = "\"false\"^^xsd:boolean";
    static final String FIVE = "\"5.0\"^^xsd:double";
    static final String SIX = "\"6.0\"^^xsd:double";
    static final String LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
    static final String LAB = "rdfs:label";
    static final String TOP = "http://www.w3.org/2002/07/owl#Thing";
    static final String THING = "owl:Thing";
    static final String TOPDT = "http://www.w3.org/2002/07/owl#topDataProperty";
    static final String TDT = "owl:topDataProperty";
    static final String TOPOP = "http://www.w3.org/2002/07/owl#topObjectProperty";
    static final String TOPO = "owl:topObjectProperty";
    static final String var6 = "Variable(<urn:swrl:var#var6>)";
    static final String var5 = "Variable(<urn:swrl:var#var5>)";
    static final String v4 = "Variable(<urn:swrl:var#var4>)";

    static Set<String> set(String... s) {
        return new HashSet<>(Arrays.asList(s));
    }

    static Stream<Arguments> getData() {
        Builder b = new Builder();
        return Stream.of(
            Arguments.of(b.assDPlain(), set(PLAIN_LITERAL, DP, I, DPI, II, adp, "\"string\"@en")),
            Arguments.of(b.dRange(), set(DT, DP, DPI, DPR, DTI)),
            Arguments.of(b.dDef(), set(DB, DT, DTD, DB, DTI)),
            Arguments.of(b.decC(), set(C, CI, DC)), Arguments.of(b.decOp(), set(OP, OPI, DOP)),
            Arguments.of(b.decDp(), set(DP, DPI, DDP)), Arguments.of(b.decDt(), set(DT, DD, DTI)),
            Arguments.of(b.decAp(), set(ANNSHORT, ANNI, DANN)),
            Arguments.of(b.decI(), set(I, II, DIND)),
            Arguments.of(b.assDi(), set(I, IRI, II, IRII, DIFF)),
            Arguments.of(b.dc(), set(C, IRI, CI, IRII, DSJC)),
            Arguments.of(b.dDp(), set(DP, IRI, DPI, IRII, DISJDP)),
            Arguments.of(b.dOp(), set(IRI, OP, IRII, OPI, DSJOP)),
            Arguments.of(b.du(), set(C, IRI, CI, IRII, DU)),
            Arguments.of(b.ec(), set(C, IRI, CI, IRII, EQC)),
            Arguments.of(b.eDp(), set(DP, IRI, DPI, IRII, EQDP)),
            Arguments.of(b.eOp(), set(IRI, OP, IRII, OPI, EQOP)),
            Arguments.of(b.fdp(), set(DP, DPI, FDP)), Arguments.of(b.fop(), set(OP, OPI, FOP)),
            Arguments.of(b.ifp(), set(OP, OPI, IFP)), Arguments.of(b.iop(), set(OP, OPI, IOP)),
            Arguments.of(b.irr(), set(OP, OPI, IRR)),
            Arguments.of(b.ndp(), set(BLN, DP, I, DPI, II, ANDP, FALSE, BLN)),
            Arguments.of(b.nop(), set(I, OP, OPI, II, ANOP)),
            Arguments.of(b.opa(), set(I, OP, OPI, II, AOP)),
            Arguments.of(b.opaInv(), set(I, OP, OPI, INVERSE, II, AOINV)),
            Arguments.of(b.opaInvj(),
                set(I, "urn:test:test#j", OP, OPI, INVERSE, II, "<urn:test:test#j>", AOPJ)),
            Arguments.of(b.oDom(), set(C, OP, CI, OPI, OPD)),
            Arguments.of(b.oRange(), set(C, OP, CI, OPI, OPR)),
            Arguments.of(b.chain(), set(IRI, OP, IRII, OPI, SUBO)),
            Arguments.of(b.ref(), set(OP, OPI, R)),
            Arguments.of(b.same(), set(I, IRI, II, IRII, SAME)),
            Arguments.of(b.subAnn(), set(LABEL, ANNSHORT, LAB, ANNI, SUBA)),
            Arguments.of(b.subClass(), set(TOP, C, THING, CI, SUBC)),
            Arguments.of(b.subData(), set(TOPDT, DP, TDT, DPI, SUBD)),
            Arguments.of(b.subObject(), set(TOPOP, OP, TOPO, OPI, SUBOP)),
            Arguments.of(b.rule(), set(SHORTRULE, v34, var236, v3, v4, var5, var6)),
            Arguments.of(b.symm(), set(OP, OPI, SYMM)), Arguments.of(b.trans(), set(OP, OPI, T)),
            Arguments.of(b.hasKey(), set(C, DP, IRI, OP, CI, IRII, OPI, DPI, HASKEY)),
            Arguments.of(b.ann(), set(IRI, AANN)),
            Arguments.of(b.asymm(), set(OP, OPI, asymmetric)),
            Arguments.of(b.annDom(), set(ANNSHORT, IRI, ANNI, APD)),
            Arguments.of(b.annRange(), set(ANNSHORT, IRI, ANNI, APR)),
            Arguments.of(b.ass(), set(C, I, CI, II, ACL)),
            Arguments.of(b.assAnd(), set(C, I, IRI, CI, IRII, II, ACLAND, AND)),
            Arguments.of(b.assOr(), set(C, I, IRI, CI, IRII, II, ACLOR, OR)),
            Arguments.of(b.dRangeAnd(),
                set(BLN, DT, DP, DPI, DPRAND, DTI, DONEOF, DAND, FALSE, BLN)),
            Arguments.of(b.dRangeOr(), set(BLN, DT, DP, DPI, DOR, DPROR, DTI, DONEOF, FALSE, BLN)),
            Arguments.of(b.assNot(), set(C, I, CI, II, CNOT, NOT)),
            Arguments.of(b.assNotAnon(), set(C, CI, "_:id", ACNOT, NOT)),
            Arguments.of(b.assSome(), set(C, I, OP, CI, OPI, II, ACSOME, SOME)),
            Arguments.of(b.assAll(), set(C, I, OP, CI, OPI, II, ACALL, ALL)),
            Arguments.of(b.assHas(), set(I, OP, OPI, II, ACHAS, HAS)),
            Arguments.of(b.assMin(), set(C, I, OP, CI, OPI, II, AOMIN, OMIN)),
            Arguments.of(b.assMax(), set(C, I, OP, CI, OPI, II, AOMAX, MAX)),
            Arguments.of(b.assEq(), set(C, I, OP, CI, OPI, II, AOEQ, OEQ)),
            Arguments.of(b.assHasSelf(), set(I, OP, OPI, II, ASELF, SELF)),
            Arguments.of(b.assOneOf(), set(I, II, AONE, ONE)),
            Arguments.of(b.assDSome(), set(DP, I, DPI, II, ADSOME, DSOME)),
            Arguments.of(b.assDAll(), set(DP, I, DPI, II, ADALL, DALL)),
            Arguments.of(b.assDHas(), set(DP, I, DPI, II, ADHAS, DHAS)),
            Arguments.of(b.assDMin(), set(DP, I, DPI, II, ADMIN, DMIN)),
            Arguments.of(b.assDMax(), set(DP, I, DPI, II, ADMAX, DMAX)),
            Arguments.of(b.assDEq(), set(DP, I, DPI, II, ADEQ, DEQ)),
            Arguments.of(b.dOneOf(), set(BLN, DP, DPI, ADONEOF, FALSE, BLN, DONEOF)),
            Arguments.of(b.dNot(), set(BLN, DP, DPI, DPRNOT, DNOT, FALSE, BLN, DONEOF)),
            Arguments.of(b.dRangeRestrict(),
                set(DB, DP, DPI, MINMAX, MINMXSIX, MIN5, MAXSIX, FIVE, SIX, DB)),
            Arguments.of(b.assD(), set(BLN, DP, I, DPI, II, dpafalse, FALSE, BLN)),
            Arguments.of(b.assDPlain(), set(PLAIN_LITERAL, DP, I, DPI, II, adp, "\"string\"@en")),
            Arguments.of(b.dDom(), set(DP, DPI, dpdomain)),
            Arguments.of(b.bigRule(),
                set(FALSE, var6, var5, v1, v4, v34, v3, v2, var2, OPI, var236, FALSE, diffvar2, DP,
                    VAR1, CI, DT, BLN, IRII, opavar2, DRA, II, BLN, dpvar2, OP, C, IRI, classvar2,
                    IRII, I, dlsaferule, DTI, II, DPI)));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom object, Set<Object> expected) {
        OWLObjectComponentCollector testsubject = new OWLObjectComponentCollector();
        Collection<OWLObject> components = testsubject.getComponents(object);
        Set<String> strings = components.stream().map(Object::toString).collect(Collectors.toSet());
        assertEquals(str(expected), str(strings));
    }

    protected String str(Set<?> expected) {
        return new TreeSet<>(expected.stream().map(Object::toString).collect(Collectors.toList()))
            .toString().replace(", ", "\n");
    }
}
