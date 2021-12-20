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
package org.semanticweb.owlapi6.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.semanticweb.owlapi6.apitest.TestFiles.AANN;
import static org.semanticweb.owlapi6.apitest.TestFiles.ACALL;
import static org.semanticweb.owlapi6.apitest.TestFiles.ACHAS;
import static org.semanticweb.owlapi6.apitest.TestFiles.ACL;
import static org.semanticweb.owlapi6.apitest.TestFiles.ACLAND;
import static org.semanticweb.owlapi6.apitest.TestFiles.ACLOR;
import static org.semanticweb.owlapi6.apitest.TestFiles.ACNOT;
import static org.semanticweb.owlapi6.apitest.TestFiles.ACSOME;
import static org.semanticweb.owlapi6.apitest.TestFiles.ADALL;
import static org.semanticweb.owlapi6.apitest.TestFiles.ADEQ;
import static org.semanticweb.owlapi6.apitest.TestFiles.ADHAS;
import static org.semanticweb.owlapi6.apitest.TestFiles.ADMAX;
import static org.semanticweb.owlapi6.apitest.TestFiles.ADMIN;
import static org.semanticweb.owlapi6.apitest.TestFiles.ADONEOF;
import static org.semanticweb.owlapi6.apitest.TestFiles.ADSOME;
import static org.semanticweb.owlapi6.apitest.TestFiles.ALL;
import static org.semanticweb.owlapi6.apitest.TestFiles.AND;
import static org.semanticweb.owlapi6.apitest.TestFiles.ANDP;
import static org.semanticweb.owlapi6.apitest.TestFiles.ANNI;
import static org.semanticweb.owlapi6.apitest.TestFiles.ANNSHORT;
import static org.semanticweb.owlapi6.apitest.TestFiles.ANOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.AOEQ;
import static org.semanticweb.owlapi6.apitest.TestFiles.AOINV;
import static org.semanticweb.owlapi6.apitest.TestFiles.AOMAX;
import static org.semanticweb.owlapi6.apitest.TestFiles.AOMIN;
import static org.semanticweb.owlapi6.apitest.TestFiles.AONE;
import static org.semanticweb.owlapi6.apitest.TestFiles.AOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.AOPJ;
import static org.semanticweb.owlapi6.apitest.TestFiles.APD;
import static org.semanticweb.owlapi6.apitest.TestFiles.APR;
import static org.semanticweb.owlapi6.apitest.TestFiles.ASELF;
import static org.semanticweb.owlapi6.apitest.TestFiles.BLN;
import static org.semanticweb.owlapi6.apitest.TestFiles.CNOT;
import static org.semanticweb.owlapi6.apitest.TestFiles.DALL;
import static org.semanticweb.owlapi6.apitest.TestFiles.DAND;
import static org.semanticweb.owlapi6.apitest.TestFiles.DANN;
import static org.semanticweb.owlapi6.apitest.TestFiles.DB;
import static org.semanticweb.owlapi6.apitest.TestFiles.DC;
import static org.semanticweb.owlapi6.apitest.TestFiles.DD;
import static org.semanticweb.owlapi6.apitest.TestFiles.DDP;
import static org.semanticweb.owlapi6.apitest.TestFiles.DEQ;
import static org.semanticweb.owlapi6.apitest.TestFiles.DHAS;
import static org.semanticweb.owlapi6.apitest.TestFiles.DIFF;
import static org.semanticweb.owlapi6.apitest.TestFiles.DIND;
import static org.semanticweb.owlapi6.apitest.TestFiles.DISJDP;
import static org.semanticweb.owlapi6.apitest.TestFiles.DMAX;
import static org.semanticweb.owlapi6.apitest.TestFiles.DMIN;
import static org.semanticweb.owlapi6.apitest.TestFiles.DNOT;
import static org.semanticweb.owlapi6.apitest.TestFiles.DONEOF;
import static org.semanticweb.owlapi6.apitest.TestFiles.DOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.DOR;
import static org.semanticweb.owlapi6.apitest.TestFiles.DPR;
import static org.semanticweb.owlapi6.apitest.TestFiles.DPRAND;
import static org.semanticweb.owlapi6.apitest.TestFiles.DPRNOT;
import static org.semanticweb.owlapi6.apitest.TestFiles.DPROR;
import static org.semanticweb.owlapi6.apitest.TestFiles.DRA;
import static org.semanticweb.owlapi6.apitest.TestFiles.DSJC;
import static org.semanticweb.owlapi6.apitest.TestFiles.DSJOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.DSOME;
import static org.semanticweb.owlapi6.apitest.TestFiles.DTD;
import static org.semanticweb.owlapi6.apitest.TestFiles.DU;
import static org.semanticweb.owlapi6.apitest.TestFiles.EQC;
import static org.semanticweb.owlapi6.apitest.TestFiles.EQDP;
import static org.semanticweb.owlapi6.apitest.TestFiles.EQOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.FALSE;
import static org.semanticweb.owlapi6.apitest.TestFiles.FDP;
import static org.semanticweb.owlapi6.apitest.TestFiles.FIVE;
import static org.semanticweb.owlapi6.apitest.TestFiles.FOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.HAS;
import static org.semanticweb.owlapi6.apitest.TestFiles.HASKEY;
import static org.semanticweb.owlapi6.apitest.TestFiles.IFP;
import static org.semanticweb.owlapi6.apitest.TestFiles.INVERSE;
import static org.semanticweb.owlapi6.apitest.TestFiles.IOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.IRI;
import static org.semanticweb.owlapi6.apitest.TestFiles.IRII;
import static org.semanticweb.owlapi6.apitest.TestFiles.IRR;
import static org.semanticweb.owlapi6.apitest.TestFiles.LAB;
import static org.semanticweb.owlapi6.apitest.TestFiles.LABEL;
import static org.semanticweb.owlapi6.apitest.TestFiles.MAX;
import static org.semanticweb.owlapi6.apitest.TestFiles.MAXSIX;
import static org.semanticweb.owlapi6.apitest.TestFiles.MIN5;
import static org.semanticweb.owlapi6.apitest.TestFiles.MINMAX;
import static org.semanticweb.owlapi6.apitest.TestFiles.MINMXSIX;
import static org.semanticweb.owlapi6.apitest.TestFiles.NOT;
import static org.semanticweb.owlapi6.apitest.TestFiles.OEQ;
import static org.semanticweb.owlapi6.apitest.TestFiles.OMIN;
import static org.semanticweb.owlapi6.apitest.TestFiles.ONE;
import static org.semanticweb.owlapi6.apitest.TestFiles.OPD;
import static org.semanticweb.owlapi6.apitest.TestFiles.OPR;
import static org.semanticweb.owlapi6.apitest.TestFiles.OR;
import static org.semanticweb.owlapi6.apitest.TestFiles.SAME;
import static org.semanticweb.owlapi6.apitest.TestFiles.SELF;
import static org.semanticweb.owlapi6.apitest.TestFiles.SHORTRULE;
import static org.semanticweb.owlapi6.apitest.TestFiles.SIX;
import static org.semanticweb.owlapi6.apitest.TestFiles.SOME;
import static org.semanticweb.owlapi6.apitest.TestFiles.SUBA;
import static org.semanticweb.owlapi6.apitest.TestFiles.SUBC;
import static org.semanticweb.owlapi6.apitest.TestFiles.SUBD;
import static org.semanticweb.owlapi6.apitest.TestFiles.SUBO;
import static org.semanticweb.owlapi6.apitest.TestFiles.SUBOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.SYMM;
import static org.semanticweb.owlapi6.apitest.TestFiles.T;
import static org.semanticweb.owlapi6.apitest.TestFiles.TDT;
import static org.semanticweb.owlapi6.apitest.TestFiles.THING;
import static org.semanticweb.owlapi6.apitest.TestFiles.TOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.TOPDT;
import static org.semanticweb.owlapi6.apitest.TestFiles.TOPO;
import static org.semanticweb.owlapi6.apitest.TestFiles.TOPOP;
import static org.semanticweb.owlapi6.apitest.TestFiles.VAR1;
import static org.semanticweb.owlapi6.apitest.TestFiles.VAR2;
import static org.semanticweb.owlapi6.apitest.TestFiles.VAR3;
import static org.semanticweb.owlapi6.apitest.TestFiles.VAR4;
import static org.semanticweb.owlapi6.apitest.TestFiles.VAR5;
import static org.semanticweb.owlapi6.apitest.TestFiles.VAR6;
import static org.semanticweb.owlapi6.apitest.TestFiles.adp;
import static org.semanticweb.owlapi6.apitest.TestFiles.asymmetric;
import static org.semanticweb.owlapi6.apitest.TestFiles.classvar2;
import static org.semanticweb.owlapi6.apitest.TestFiles.diffvar2;
import static org.semanticweb.owlapi6.apitest.TestFiles.dlsaferule;
import static org.semanticweb.owlapi6.apitest.TestFiles.dpafalse;
import static org.semanticweb.owlapi6.apitest.TestFiles.dpdomain;
import static org.semanticweb.owlapi6.apitest.TestFiles.dpvar2;
import static org.semanticweb.owlapi6.apitest.TestFiles.opavar2;
import static org.semanticweb.owlapi6.apitest.TestFiles.plain;
import static org.semanticweb.owlapi6.apitest.TestFiles.v1;
import static org.semanticweb.owlapi6.apitest.TestFiles.v2;
import static org.semanticweb.owlapi6.apitest.TestFiles.v34;
import static org.semanticweb.owlapi6.apitest.TestFiles.var236;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.utility.OWLObjectComponentCollector;

class OWLObjectComponentCollectorTestCase extends TestBase {

    static Stream<Arguments> getData() {
        String IString = INDIVIDUALS.I.getIRI().toString();
        String DPI = DATAPROPS.DP.getIRI().toString();
        String DTI = DATATYPES.DT.getIRI().toString();
        String CI = CLASSES.C.getIRI().toString();
        String OPI = OBJPROPS.OP.getIRI().toString();
        return Stream.of(
            of(Builder.assDPlain,
                l(plain, DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, adp, "\"string\"@en")),
            of(Builder.dRange, l(DATATYPES.DT, DATAPROPS.DP, DPI, DPR, DTI)),
            of(Builder.dDef, l(DB, DATATYPES.DT, DTD, DTI)), of(Builder.decC, l(CLASSES.C, CI, DC)),
            of(Builder.decOp, l(OBJPROPS.OP, OPI, DOP)),
            of(Builder.decDp, l(DATAPROPS.DP, DPI, DDP)),
            of(Builder.decDt, l(DATATYPES.DT, DD, DTI)), of(Builder.decAp, l(ANNSHORT, ANNI, DANN)),
            of(Builder.decI, l(INDIVIDUALS.I, IString, DIND)),
            of(Builder.assDi, l(INDIVIDUALS.I, IRI, IString, IRII, DIFF)),
            of(Builder.dc, l(CLASSES.C, IRI, CI, IRII, DSJC)),
            of(Builder.dDp, l(DATAPROPS.DP, IRI, DPI, IRII, DISJDP)),
            of(Builder.dOp, l(IRI, OBJPROPS.OP, IRII, OPI, DSJOP)),
            of(Builder.du, l(CLASSES.C, IRI, CI, IRII, DU)),
            of(Builder.ec, l(CLASSES.C, IRI, CI, IRII, EQC)),
            of(Builder.eDp, l(DATAPROPS.DP, IRI, DPI, IRII, EQDP)),
            of(Builder.eOp, l(IRI, OBJPROPS.OP, IRII, OPI, EQOP)),
            of(Builder.fdp, l(DATAPROPS.DP, DPI, FDP)), of(Builder.fop, l(OBJPROPS.OP, OPI, FOP)),
            of(Builder.ifp, l(OBJPROPS.OP, OPI, IFP)), of(Builder.iop, l(OBJPROPS.OP, OPI, IOP)),
            of(Builder.irr, l(OBJPROPS.OP, OPI, IRR)),
            of(Builder.ndp, l(BLN, DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, ANDP, FALSE)),
            of(Builder.nop, l(INDIVIDUALS.I, OBJPROPS.OP, OPI, IString, ANOP)),
            of(Builder.opa, l(INDIVIDUALS.I, OBJPROPS.OP, OPI, IString, AOP)),
            of(Builder.opaInv, l(INDIVIDUALS.I, OBJPROPS.OP, OPI, INVERSE, IString, AOINV)),
            of(Builder.opaInvj,
                l(INDIVIDUALS.I, INDIVIDUALS.J.getIRI().toString(), OBJPROPS.OP, OPI, INVERSE,
                    IString, INDIVIDUALS.J.toString(), AOPJ)),
            of(Builder.oDom, l(CLASSES.C, OBJPROPS.OP, CI, OPI, OPD)),
            of(Builder.oRange, l(CLASSES.C, OBJPROPS.OP, CI, OPI, OPR)),
            of(Builder.chain, l(IRI, OBJPROPS.OP, IRII, OPI, SUBO)),
            of(Builder.ref, l(OBJPROPS.OP, OPI, TestFiles.R)),
            of(Builder.same, l(INDIVIDUALS.I, IRI, IString, IRII, SAME)),
            of(Builder.subAnn, l(LABEL, ANNSHORT, LAB, ANNI, SUBA)),
            of(Builder.subClass, l(TOP, CLASSES.C, THING, CI, SUBC)),
            of(Builder.subData, l(TOPDT, DATAPROPS.DP, TDT, DPI, SUBD)),
            of(Builder.subObject, l(TOPOP, OBJPROPS.OP, TOPO, OPI, SUBOP)),
            of(Builder.rule, l(SHORTRULE, v34, var236, VAR3, VAR4, VAR5, VAR6)),
            of(Builder.symm, l(OBJPROPS.OP, OPI, SYMM)), of(Builder.trans, l(OBJPROPS.OP, OPI, T)),
            of(Builder.hasKey,
                l(CLASSES.C, DATAPROPS.DP, IRI, OBJPROPS.OP, CI, IRII, OPI, DPI, HASKEY)),
            of(Builder.ann, l(IRI, AANN)), of(Builder.asymm, l(OBJPROPS.OP, OPI, asymmetric)),
            of(Builder.annDom, l(ANNSHORT, IRI, ANNI, APD)),
            of(Builder.annRange, l(ANNSHORT, IRI, ANNI, APR)),
            of(Builder.ass, l(CLASSES.C, INDIVIDUALS.I, CI, IString, ACL)),
            of(Builder.assAnd, l(CLASSES.C, INDIVIDUALS.I, IRI, CI, IRII, IString, ACLAND, AND)),
            of(Builder.assOr, l(CLASSES.C, INDIVIDUALS.I, IRI, CI, IRII, IString, ACLOR, OR)),
            of(Builder.dRangeAnd,
                l(BLN, DATATYPES.DT, DATAPROPS.DP, DPI, DPRAND, DTI, DONEOF, DAND, FALSE)),
            of(Builder.dRangeOr,
                l(BLN, DATATYPES.DT, DATAPROPS.DP, DPI, DOR, DPROR, DTI, DONEOF, FALSE)),
            of(Builder.assNot, l(CLASSES.C, INDIVIDUALS.I, CI, IString, CNOT, NOT)),
            of(Builder.assNotAnon, l(CLASSES.C, CI, "_:id", ACNOT, NOT)),
            of(Builder.assSome,
                l(CLASSES.C, INDIVIDUALS.I, OBJPROPS.OP, CI, OPI, IString, ACSOME, SOME)),
            of(Builder.assAll,
                l(CLASSES.C, INDIVIDUALS.I, OBJPROPS.OP, CI, OPI, IString, ACALL, ALL)),
            of(Builder.assHas, l(INDIVIDUALS.I, OBJPROPS.OP, OPI, IString, ACHAS, HAS)),
            of(Builder.assMin,
                l(CLASSES.C, INDIVIDUALS.I, OBJPROPS.OP, CI, OPI, IString, AOMIN, OMIN)),
            of(Builder.assMax,
                l(CLASSES.C, INDIVIDUALS.I, OBJPROPS.OP, CI, OPI, IString, AOMAX, MAX)),
            of(Builder.assEq,
                l(CLASSES.C, INDIVIDUALS.I, OBJPROPS.OP, CI, OPI, IString, AOEQ, OEQ)),
            of(Builder.assHasSelf, l(INDIVIDUALS.I, OBJPROPS.OP, OPI, IString, ASELF, SELF)),
            of(Builder.assOneOf, l(INDIVIDUALS.I, IString, AONE, ONE)),
            of(Builder.assDSome, l(DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, ADSOME, DSOME)),
            of(Builder.assDAll, l(DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, ADALL, DALL)),
            of(Builder.assDHas, l(DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, ADHAS, DHAS)),
            of(Builder.assDMin, l(DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, ADMIN, DMIN)),
            of(Builder.assDMax, l(DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, ADMAX, DMAX)),
            of(Builder.assDEq, l(DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, ADEQ, DEQ)),
            of(Builder.dOneOf, l(BLN, DATAPROPS.DP, DPI, ADONEOF, FALSE, DONEOF)),
            of(Builder.dNot, l(BLN, DATAPROPS.DP, DPI, DPRNOT, DNOT, FALSE, DONEOF)),
            of(Builder.dRangeRestrict,
                l(DB, DATAPROPS.DP, DPI, MINMAX, MINMXSIX, MIN5, MAXSIX, FIVE, SIX)),
            of(Builder.assD, l(BLN, DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, dpafalse, FALSE)),
            of(Builder.assDPlain,
                l(plain, DATAPROPS.DP, INDIVIDUALS.I, DPI, IString, adp, plain, "\"string\"@en")),
            of(Builder.dDom, l(DATAPROPS.DP, DPI, dpdomain)),
            of(Builder.bigRule,
                l(FALSE, VAR6, VAR5, v1, VAR4, v34, VAR3, v2, VAR2, OPI, var236, FALSE, diffvar2,
                    DATAPROPS.DP, VAR1, CI, DATATYPES.DT, BLN, IRII, opavar2, DRA, IString, BLN,
                    dpvar2, OBJPROPS.OP, CLASSES.C, IRI, classvar2, IRII, INDIVIDUALS.I, dlsaferule,
                    DTI, IString, DPI)));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom ax, List<String> expected) {
        OWLObjectComponentCollector testsubject = new OWLObjectComponentCollector();
        Collection<OWLObject> components = testsubject.getComponents(ax);
        assertEquals(str(expected), str(components));
    }
}
