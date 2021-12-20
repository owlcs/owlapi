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

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.utility.SimpleRenderer;

class VisitorsTestCase extends TestBase {

    static Stream<Arguments> getData() {
        return Stream.of(of(Builder.rule, TestFiles.RULE), of(Builder.bigRule, TestFiles.BIGRULE),
            of(Builder.onto, TestFiles.ONTO), of(Builder.ann, TestFiles.ANN),
            of(Builder.asymm, TestFiles.ASYMM), of(Builder.annDom, TestFiles.ANNDOM),
            of(Builder.annRange, TestFiles.ANNRANGE), of(Builder.ass, TestFiles.ASS),
            of(Builder.assAnd, TestFiles.ASSAND), of(Builder.assOr, TestFiles.ASSOR),
            of(Builder.dRangeAnd, TestFiles.DRANGEAND), of(Builder.dRangeOr, TestFiles.DRANGEOR),
            of(Builder.assNot, TestFiles.ASSNOT), of(Builder.assNotAnon, TestFiles.ASSNOTANON),
            of(Builder.assSome, TestFiles.ASSSOME), of(Builder.assAll, TestFiles.ASSALL),
            of(Builder.assHas, TestFiles.ASSHAS), of(Builder.assMin, TestFiles.ASSMIN),
            of(Builder.assMax, TestFiles.ASSMAX), of(Builder.assEq, TestFiles.ASSEQ),
            of(Builder.assHasSelf, TestFiles.ASSHASSELF), of(Builder.assOneOf, TestFiles.ASSONEOF),
            of(Builder.assDSome, TestFiles.assDSome), of(Builder.assDAll, TestFiles.assDAll),
            of(Builder.assDHas, TestFiles.assDHas), of(Builder.assDMin, TestFiles.assDMin),
            of(Builder.assDMax, TestFiles.assDMax), of(Builder.assDEq, TestFiles.assDEq),
            of(Builder.dOneOf, TestFiles.dataOneOf), of(Builder.dNot, TestFiles.dataNot),
            of(Builder.dRangeRestrict, TestFiles.dRangeRestrict), of(Builder.assD, TestFiles.assD),
            of(Builder.assDPlain, TestFiles.assDPlain), of(Builder.dDom, TestFiles.dDom),
            of(Builder.dRange, TestFiles.dRange), of(Builder.dDef, TestFiles.dDef),
            of(Builder.decC, TestFiles.decC), of(Builder.decOp, TestFiles.decOp),
            of(Builder.decDp, TestFiles.decDp), of(Builder.decDt, TestFiles.decDt),
            of(Builder.decAp, TestFiles.decAp), of(Builder.decI, TestFiles.decI),
            of(Builder.assDi, TestFiles.assDi), of(Builder.dc, TestFiles.disjointClasses),
            of(Builder.dDp, TestFiles.disjointDp), of(Builder.dOp, TestFiles.disjointOp),
            of(Builder.du, TestFiles.disjointu), of(Builder.ec, TestFiles.ec),
            of(Builder.eDp, TestFiles.eDp), of(Builder.eOp, TestFiles.eOp),
            of(Builder.fdp, TestFiles.functionaldp), of(Builder.fop, TestFiles.functionalop),
            of(Builder.ifp, TestFiles.inversefp), of(Builder.iop, TestFiles.inverseop),
            of(Builder.irr, TestFiles.irreflexive), of(Builder.ndp, TestFiles.ndp),
            of(Builder.nop, TestFiles.nop), of(Builder.opa, TestFiles.opa),
            of(Builder.opaInv, TestFiles.OPAINV), of(Builder.opaInvj, TestFiles.OPAINVJ),
            of(Builder.oDom, TestFiles.ODOM), of(Builder.oRange, TestFiles.ORANGE),
            of(Builder.chain, TestFiles.CHAIN), of(Builder.ref, TestFiles.REF),
            of(Builder.same, TestFiles.SAME), of(Builder.subAnn, TestFiles.SUBANN),
            of(Builder.subClass, TestFiles.SUBCLASS), of(Builder.subData, TestFiles.SUBDATA),
            of(Builder.subObject, TestFiles.SUBOBJECT), of(Builder.symm, TestFiles.SYMM),
            of(Builder.trans, TestFiles.TRANS), of(Builder.hasKey, TestFiles.HASKEY));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLObject ax, String expected) {
        String render = new SimpleRenderer().render(ax);
        assertEquals(expected, render);
    }
}
