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
package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.utility.NNF;

class NNFTestCase extends TestBase {

    static Stream<Arguments> getData() {
        return Stream.of(of(Builder.dRange, TestFiles.nnfdRange),
            of(Builder.dDef, TestFiles.nnfdDef), of(Builder.decC, TestFiles.nnfdecC),
            of(Builder.decOp, TestFiles.nnfdecOp), of(Builder.decDp, TestFiles.nnfdecDp),
            of(Builder.decDt, TestFiles.nnfdecDt), of(Builder.decAp, TestFiles.nnfdecAp),
            of(Builder.decI, TestFiles.nnfdecI), of(Builder.assDi, TestFiles.nnfassDi),
            of(Builder.dc, TestFiles.nnfdc), of(Builder.dDp, TestFiles.nnfdDp),
            of(Builder.dOp, TestFiles.nnfdOp), of(Builder.du, TestFiles.nnfdu),
            of(Builder.ec, TestFiles.nnfec), of(Builder.eDp, TestFiles.nnfeDp),
            of(Builder.eOp, TestFiles.nnfeOp), of(Builder.fdp, TestFiles.nnffdp),
            of(Builder.fop, TestFiles.nnffop), of(Builder.ifp, TestFiles.nnfifp),
            of(Builder.iop, TestFiles.nnfiop), of(Builder.irr, TestFiles.nnfirr),
            of(Builder.ndp, TestFiles.nnfndp), of(Builder.nop, TestFiles.nnfnop),
            of(Builder.opa, TestFiles.nnfopa), of(Builder.opaInv, TestFiles.nnfopaInv),
            of(Builder.opaInvj, TestFiles.nnfopaInvj), of(Builder.oDom, TestFiles.nnfoDom),
            of(Builder.oRange, TestFiles.nnfoRange), of(Builder.chain, TestFiles.nnfchain),
            of(Builder.ref, TestFiles.nnfref), of(Builder.same, TestFiles.nnfsame),
            of(Builder.subAnn, TestFiles.nnfsubAnn), of(Builder.subClass, TestFiles.nnfsubClass),
            of(Builder.subData, TestFiles.nnfsubData),
            of(Builder.subObject, TestFiles.nnfsubObject), of(Builder.rule, TestFiles.nnfrule),
            of(Builder.symm, TestFiles.nnfsymm), of(Builder.trans, TestFiles.nnftrans),
            of(Builder.hasKey, TestFiles.nnfhasKey), of(Builder.bigRule, TestFiles.nnfbigRule),
            of(Builder.ann, TestFiles.nnfann), of(Builder.asymm, TestFiles.nnfasymm),
            of(Builder.annDom, TestFiles.nnfannDom), of(Builder.annRange, TestFiles.nnfannRange),
            of(Builder.ass, TestFiles.nnfass), of(Builder.assAnd, TestFiles.nnfassAnd),
            of(Builder.assOr, TestFiles.nnfassOr), of(Builder.dRangeAnd, TestFiles.nnfdRangeAnd),
            of(Builder.dRangeOr, TestFiles.nnfdRangeOr), of(Builder.assNot, TestFiles.nnfassNot),
            of(Builder.assNotAnon, TestFiles.nnfassNotAnon),
            of(Builder.assSome, TestFiles.nnfassSome), of(Builder.assAll, TestFiles.nnfassAll),
            of(Builder.assHas, TestFiles.nnfassHas), of(Builder.assMin, TestFiles.nnfassMin),
            of(Builder.assMax, TestFiles.nnfassMax), of(Builder.assEq, TestFiles.nnfassEq),
            of(Builder.assHasSelf, TestFiles.nnfassHasSelf),
            of(Builder.assOneOf, TestFiles.nnfassOneOf),
            of(Builder.assDSome, TestFiles.nnfassDSome), of(Builder.assDAll, TestFiles.nnfassDAll),
            of(Builder.assDHas, TestFiles.nnfassDHas), of(Builder.assDMin, TestFiles.nnfassDMin),
            of(Builder.assDMax, TestFiles.nnfassDMax), of(Builder.assDEq, TestFiles.nnfassDEq),
            of(Builder.dOneOf, TestFiles.nnfdOneOf), of(Builder.dNot, TestFiles.nnfdNot),
            of(Builder.dRangeRestrict, TestFiles.nnfdRangeRestrict),
            of(Builder.assD, TestFiles.nnfassD), of(Builder.assDPlain, TestFiles.nnfassDPlain),
            of(Builder.dDom, TestFiles.nnfdDom));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom ax, String expected) {
        NNF testsubject = new NNF(df);
        String result = ax.accept(testsubject).toString();
        assertEquals(expected, result);
    }
}
