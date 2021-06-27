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
import static org.semanticweb.owlapi.apitest.TestFiles.plain;
import static org.semanticweb.owlapi.apitest.TestFiles.v1;
import static org.semanticweb.owlapi.apitest.TestFiles.v2;
import static org.semanticweb.owlapi.apitest.TestFiles.v3;
import static org.semanticweb.owlapi.apitest.TestFiles.v34;
import static org.semanticweb.owlapi.apitest.TestFiles.var2;
import static org.semanticweb.owlapi.apitest.TestFiles.var236;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectComponentCollector;

class OWLObjectComponentCollectorTestCase {

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

    static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String[]> map = new LinkedHashMap<>();
        map.put(b.assDPlain(), new String[] {plain, DP, I, DPI, II, adp, plain, "\"string\"@en"});
        map.put(b.dRange(), new String[] {DT, DP, DPI, DPR, DTI});
        map.put(b.dDef(), new String[] {DB, DT, DTD, DB, DTI});
        map.put(b.decC(), new String[] {C, CI, DC});
        map.put(b.decOp(), new String[] {OP, OPI, DOP});
        map.put(b.decDp(), new String[] {DP, DPI, DDP});
        map.put(b.decDt(), new String[] {DT, DD, DTI});
        map.put(b.decAp(), new String[] {ANNSHORT, ANNI, DANN});
        map.put(b.decI(), new String[] {I, II, DIND});
        map.put(b.assDi(), new String[] {I, IRI, II, IRII, DIFF});
        map.put(b.dc(), new String[] {C, IRI, CI, IRII, DSJC});
        map.put(b.dDp(), new String[] {DP, IRI, DPI, IRII, DISJDP});
        map.put(b.dOp(), new String[] {IRI, OP, IRII, OPI, DSJOP});
        map.put(b.du(), new String[] {C, IRI, CI, IRII, DU});
        map.put(b.ec(), new String[] {C, IRI, CI, IRII, EQC});
        map.put(b.eDp(), new String[] {DP, IRI, DPI, IRII, EQDP});
        map.put(b.eOp(), new String[] {IRI, OP, IRII, OPI, EQOP});
        map.put(b.fdp(), new String[] {DP, DPI, FDP});
        map.put(b.fop(), new String[] {OP, OPI, FOP});
        map.put(b.ifp(), new String[] {OP, OPI, IFP});
        map.put(b.iop(), new String[] {OP, OPI, IOP});
        map.put(b.irr(), new String[] {OP, OPI, IRR});
        map.put(b.ndp(), new String[] {BLN, DP, I, DPI, II, ANDP, FALSE, BLN});
        map.put(b.nop(), new String[] {I, OP, OPI, II, ANOP});
        map.put(b.opa(), new String[] {I, OP, OPI, II, AOP});
        map.put(b.opaInv(), new String[] {I, OP, OPI, INVERSE, II, AOINV});
        map.put(b.opaInvj(),
            new String[] {I, "urn:test:test#j", OP, OPI, INVERSE, II, "<urn:test:test#j>", AOPJ});
        map.put(b.oDom(), new String[] {C, OP, CI, OPI, OPD});
        map.put(b.oRange(), new String[] {C, OP, CI, OPI, OPR});
        map.put(b.chain(), new String[] {IRI, OP, IRII, OPI, SUBO});
        map.put(b.ref(), new String[] {OP, OPI, R});
        map.put(b.same(), new String[] {I, IRI, II, IRII, SAME});
        map.put(b.subAnn(), new String[] {LABEL, ANNSHORT, LAB, ANNI, SUBA});
        map.put(b.subClass(), new String[] {TOP, C, THING, CI, SUBC});
        map.put(b.subData(), new String[] {TOPDT, DP, TDT, DPI, SUBD});
        map.put(b.subObject(), new String[] {TOPOP, OP, TOPO, OPI, SUBOP});
        map.put(b.rule(), new String[] {SHORTRULE, v34, var236, v3, v4, var5, var6});
        map.put(b.symm(), new String[] {OP, OPI, SYMM});
        map.put(b.trans(), new String[] {OP, OPI, T});
        map.put(b.hasKey(), new String[] {C, DP, IRI, OP, CI, IRII, OPI, DPI, HASKEY});
        map.put(b.ann(), new String[] {IRI, AANN});
        map.put(b.asymm(), new String[] {OP, OPI, asymmetric});
        map.put(b.annDom(), new String[] {ANNSHORT, IRI, ANNI, APD});
        map.put(b.annRange(), new String[] {ANNSHORT, IRI, ANNI, APR});
        map.put(b.ass(), new String[] {C, I, CI, II, ACL});
        map.put(b.assAnd(), new String[] {C, I, IRI, CI, IRII, II, ACLAND, AND});
        map.put(b.assOr(), new String[] {C, I, IRI, CI, IRII, II, ACLOR, OR});
        map.put(b.dRangeAnd(),
            new String[] {BLN, DT, DP, DPI, DPRAND, DTI, DONEOF, DAND, FALSE, BLN});
        map.put(b.dRangeOr(), new String[] {BLN, DT, DP, DPI, DOR, DPROR, DTI, DONEOF, FALSE, BLN});
        map.put(b.assNot(), new String[] {C, I, CI, II, CNOT, NOT});
        map.put(b.assNotAnon(), new String[] {C, CI, "_:id", ACNOT, NOT});
        map.put(b.assSome(), new String[] {C, I, OP, CI, OPI, II, ACSOME, SOME});
        map.put(b.assAll(), new String[] {C, I, OP, CI, OPI, II, ACALL, ALL});
        map.put(b.assHas(), new String[] {I, OP, OPI, II, ACHAS, HAS});
        map.put(b.assMin(), new String[] {C, I, OP, CI, OPI, II, AOMIN, OMIN});
        map.put(b.assMax(), new String[] {C, I, OP, CI, OPI, II, AOMAX, MAX});
        map.put(b.assEq(), new String[] {C, I, OP, CI, OPI, II, AOEQ, OEQ});
        map.put(b.assHasSelf(), new String[] {I, OP, OPI, II, ASELF, SELF});
        map.put(b.assOneOf(), new String[] {I, II, AONE, ONE});
        map.put(b.assDSome(), new String[] {DP, I, DPI, II, ADSOME, DSOME});
        map.put(b.assDAll(), new String[] {DP, I, DPI, II, ADALL, DALL});
        map.put(b.assDHas(), new String[] {DP, I, DPI, II, ADHAS, DHAS});
        map.put(b.assDMin(), new String[] {DP, I, DPI, II, ADMIN, DMIN});
        map.put(b.assDMax(), new String[] {DP, I, DPI, II, ADMAX, DMAX});
        map.put(b.assDEq(), new String[] {DP, I, DPI, II, ADEQ, DEQ});
        map.put(b.dOneOf(), new String[] {BLN, DP, DPI, ADONEOF, FALSE, BLN, DONEOF});
        map.put(b.dNot(), new String[] {BLN, DP, DPI, DPRNOT, DNOT, FALSE, BLN, DONEOF});
        map.put(b.dRangeRestrict(),
            new String[] {DB, DP, DPI, MINMAX, MINMXSIX, MIN5, MAXSIX, FIVE, SIX, DB});
        map.put(b.assD(), new String[] {BLN, DP, I, DPI, II, dpafalse, FALSE, BLN});
        map.put(b.assDPlain(), new String[] {plain, DP, I, DPI, II, adp, plain, "\"string\"@en"});
        map.put(b.dDom(), new String[] {DP, DPI, dpdomain});
        map.put(b.bigRule(),
            new String[] {FALSE, var6, var5, v1, v4, v34, v3, v2, var2, OPI, var236, FALSE,
                diffvar2, DP, VAR1, CI, DT, BLN, IRII, opavar2, DRA, II, BLN, dpvar2, OP, C, IRI,
                classvar2, IRII, I, dlsaferule, DTI, II, DPI});
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] {k, v}));
        return toReturn;
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom object, String[] expected) {
        OWLObjectComponentCollector testsubject = new OWLObjectComponentCollector();
        Collection<OWLObject> components = testsubject.getComponents(object);
        Set<String> strings = asUnorderedSet(components.stream().map(Object::toString));
        assertEquals(new HashSet<>(Arrays.asList(expected)), strings);
    }
}
