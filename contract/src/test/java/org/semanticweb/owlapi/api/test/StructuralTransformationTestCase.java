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

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.StructuralTransformation;

class StructuralTransformationTestCase extends TestBase {
    public static final String strtranssame = "";

    static Stream<Arguments> getData() {
        Builder b = new Builder();
        return Stream.of(Arguments.of(b.dRange(), TestFiles.strtransdRange),
            Arguments.of(b.dDef(), TestFiles.strtransdDef),
            Arguments.of(b.decC(), TestFiles.strtransdecC),
            Arguments.of(b.decOp(), TestFiles.strtransdecOp),
            Arguments.of(b.decDp(), TestFiles.strtransdecDp),

            Arguments.of(b.decDt(), TestFiles.strtransdecDt),
            Arguments.of(b.decAp(), TestFiles.strtransdecAp),
            Arguments.of(b.decI(), TestFiles.strtransdecI),
            Arguments.of(b.dDp(), TestFiles.strtransdDp),
            Arguments.of(b.dOp(), TestFiles.strtransdOp),

            Arguments.of(b.eDp(), TestFiles.strtranseDp),
            Arguments.of(b.eOp(), TestFiles.strtranseOp),
            Arguments.of(b.fdp(), TestFiles.strtransfdp),
            Arguments.of(b.fop(), TestFiles.strtransfop),
            Arguments.of(b.ifp(), TestFiles.strtransifp),

            Arguments.of(b.iop(), TestFiles.strtransiop),
            Arguments.of(b.irr(), TestFiles.strtransirr),
            Arguments.of(b.opa(), TestFiles.strtransopa),
            Arguments.of(b.opaInv(), TestFiles.strtransopaInv),
            Arguments.of(b.opaInvj(), TestFiles.strtransopaInvj),

            Arguments.of(b.oDom(), TestFiles.strtransoDom),
            Arguments.of(b.oRange(), TestFiles.strtransoRange),
            Arguments.of(b.chain(), TestFiles.strtranschain),
            Arguments.of(b.ref(), TestFiles.strtransref), Arguments.of(b.same(), strtranssame),

            Arguments.of(b.subAnn(), TestFiles.strtranssubAnn),
            Arguments.of(b.subClass(), TestFiles.strtranssubClass),
            Arguments.of(b.subData(), TestFiles.strtranssubData),
            Arguments.of(b.subObject(), TestFiles.strtranssubObject),
            Arguments.of(b.rule(), TestFiles.strtransrule),

            Arguments.of(b.symm(), TestFiles.strtranssymm),
            Arguments.of(b.trans(), TestFiles.strtranstrans),
            Arguments.of(b.hasKey(), TestFiles.strtranshasKey),
            Arguments.of(b.bigRule(), TestFiles.strtransbigRule),
            Arguments.of(b.ann(), TestFiles.strtransann),

            Arguments.of(b.asymm(), TestFiles.strtransasymm),
            Arguments.of(b.annDom(), TestFiles.strtransannDom),
            Arguments.of(b.annRange(), TestFiles.strtransannRange),
            Arguments.of(b.dRangeAnd(), TestFiles.strtransdRangeAnd),
            Arguments.of(b.dRangeOr(), TestFiles.strtransdRangeOr),

            Arguments.of(b.dOneOf(), TestFiles.strtransdOneOf),
            Arguments.of(b.dNot(), TestFiles.strtransdNot),
            Arguments.of(b.dRangeRestrict(), TestFiles.strtransdRangeRestrict),
            Arguments.of(b.assD(), TestFiles.strtransassD),
            Arguments.of(b.assDPlain(), TestFiles.strtransassDPlain),

            Arguments.of(b.dDom(), TestFiles.strtransdDom),
            Arguments.of(b.dc(), TestFiles.strtransdc), Arguments.of(b.du(), TestFiles.strtransdu),
            Arguments.of(b.ec(), TestFiles.strtransec));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom ax, String expected) {
        StructuralTransformation testsubject = new StructuralTransformation(df);
        Set<OWLAxiom> singleton = Collections.singleton(ax);
        String result = str(testsubject.getTransformedAxioms(singleton));
        assertEquals(expected, result);
    }
}
