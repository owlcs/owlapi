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

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.NNF;

class NNFTestCase {

    static Stream<Arguments> getData() {
        Builder b = new Builder();
        return Stream.of(Arguments.of(b.dRange(), TestFiles.nnfdRange),
            Arguments.of(b.dDef(), TestFiles.nnfdDef), Arguments.of(b.decC(), TestFiles.nnfdecC),
            Arguments.of(b.decOp(), TestFiles.nnfdecOp),
            Arguments.of(b.decDp(), TestFiles.nnfdecDp),
            Arguments.of(b.decDt(), TestFiles.nnfdecDt),
            Arguments.of(b.decAp(), TestFiles.nnfdecAp), Arguments.of(b.decI(), TestFiles.nnfdecI),
            Arguments.of(b.assDi(), TestFiles.nnfassDi), Arguments.of(b.dc(), TestFiles.nnfdc),
            Arguments.of(b.dDp(), TestFiles.nnfdDp), Arguments.of(b.dOp(), TestFiles.nnfdOp),
            Arguments.of(b.du(), TestFiles.nnfdu), Arguments.of(b.ec(), TestFiles.nnfec),
            Arguments.of(b.eDp(), TestFiles.nnfeDp), Arguments.of(b.eOp(), TestFiles.nnfeOp),
            Arguments.of(b.fdp(), TestFiles.nnffdp), Arguments.of(b.fop(), TestFiles.nnffop),
            Arguments.of(b.ifp(), TestFiles.nnfifp), Arguments.of(b.iop(), TestFiles.nnfiop),
            Arguments.of(b.irr(), TestFiles.nnfirr), Arguments.of(b.ndp(), TestFiles.nnfndp),
            Arguments.of(b.nop(), TestFiles.nnfnop), Arguments.of(b.opa(), TestFiles.nnfopa),
            Arguments.of(b.opaInv(), TestFiles.nnfopaInv),
            Arguments.of(b.opaInvj(), TestFiles.nnfopaInvj),
            Arguments.of(b.oDom(), TestFiles.nnfoDom),
            Arguments.of(b.oRange(), TestFiles.nnfoRange),
            Arguments.of(b.chain(), TestFiles.nnfchain), Arguments.of(b.ref(), TestFiles.nnfref),
            Arguments.of(b.same(), TestFiles.nnfsame),
            Arguments.of(b.subAnn(), TestFiles.nnfsubAnn),
            Arguments.of(b.subClass(), TestFiles.nnfsubClass),
            Arguments.of(b.subData(), TestFiles.nnfsubData),
            Arguments.of(b.subObject(), TestFiles.nnfsubObject),
            Arguments.of(b.rule(), TestFiles.nnfrule), Arguments.of(b.symm(), TestFiles.nnfsymm),
            Arguments.of(b.trans(), TestFiles.nnftrans),
            Arguments.of(b.hasKey(), TestFiles.nnfhasKey),
            Arguments.of(b.bigRule(), TestFiles.nnfbigRule),
            Arguments.of(b.ann(), TestFiles.nnfann), Arguments.of(b.asymm(), TestFiles.nnfasymm),
            Arguments.of(b.annDom(), TestFiles.nnfannDom),
            Arguments.of(b.annRange(), TestFiles.nnfannRange),
            Arguments.of(b.ass(), TestFiles.nnfass), Arguments.of(b.assAnd(), TestFiles.nnfassAnd),
            Arguments.of(b.assOr(), TestFiles.nnfassOr),
            Arguments.of(b.dRangeAnd(), TestFiles.nnfdRangeAnd),
            Arguments.of(b.dRangeOr(), TestFiles.nnfdRangeOr),
            Arguments.of(b.assNot(), TestFiles.nnfassNot),
            Arguments.of(b.assNotAnon(), TestFiles.nnfassNotAnon),
            Arguments.of(b.assSome(), TestFiles.nnfassSome),
            Arguments.of(b.assAll(), TestFiles.nnfassAll),
            Arguments.of(b.assHas(), TestFiles.nnfassHas),
            Arguments.of(b.assMin(), TestFiles.nnfassMin),
            Arguments.of(b.assMax(), TestFiles.nnfassMax),
            Arguments.of(b.assEq(), TestFiles.nnfassEq),
            Arguments.of(b.assHasSelf(), TestFiles.nnfassHasSelf),
            Arguments.of(b.assOneOf(), TestFiles.nnfassOneOf),
            Arguments.of(b.assDSome(), TestFiles.nnfassDSome),
            Arguments.of(b.assDAll(), TestFiles.nnfassDAll),
            Arguments.of(b.assDHas(), TestFiles.nnfassDHas),
            Arguments.of(b.assDMin(), TestFiles.nnfassDMin),
            Arguments.of(b.assDMax(), TestFiles.nnfassDMax),
            Arguments.of(b.assDEq(), TestFiles.nnfassDEq),
            Arguments.of(b.dOneOf(), TestFiles.nnfdOneOf),
            Arguments.of(b.dNot(), TestFiles.nnfdNot),
            Arguments.of(b.dRangeRestrict(), TestFiles.nnfdRangeRestrict),
            Arguments.of(b.assD(), TestFiles.nnfassD),
            Arguments.of(b.assDPlain(), TestFiles.nnfassDPlain),
            Arguments.of(b.dDom(), TestFiles.nnfdDom));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom object, String expected) {
        NNF testsubject = new NNF(OWLManager.getOWLDataFactory());
        String result = object.accept(testsubject).toString();
        assertEquals(expected, result);
    }
}
