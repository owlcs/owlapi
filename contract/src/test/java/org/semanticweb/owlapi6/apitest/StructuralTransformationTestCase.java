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

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.utility.StructuralTransformation;

class StructuralTransformationTestCase extends TestBase {
    public static final String strtranssame = "";

    static Stream<Arguments> getData() {
        return Stream.of(of(Builder.dRange, TestFiles.strtransdRange),
            of(Builder.dDef, TestFiles.strtransdDef), of(Builder.decC, TestFiles.strtransdecC),
            of(Builder.decOp, TestFiles.strtransdecOp), of(Builder.decDp, TestFiles.strtransdecDp),
            of(Builder.decDt, TestFiles.strtransdecDt), of(Builder.decAp, TestFiles.strtransdecAp),
            of(Builder.decI, TestFiles.strtransdecI), of(Builder.dDp, TestFiles.strtransdDp),
            of(Builder.dOp, TestFiles.strtransdOp), of(Builder.eDp, TestFiles.strtranseDp),
            of(Builder.eOp, TestFiles.strtranseOp), of(Builder.fdp, TestFiles.strtransfdp),
            of(Builder.fop, TestFiles.strtransfop), of(Builder.ifp, TestFiles.strtransifp),
            of(Builder.iop, TestFiles.strtransiop), of(Builder.irr, TestFiles.strtransirr),
            of(Builder.opa, TestFiles.strtransopa), of(Builder.opaInv, TestFiles.strtransopaInv),
            of(Builder.opaInvj, TestFiles.strtransopaInvj),
            of(Builder.oDom, TestFiles.strtransoDom), of(Builder.oRange, TestFiles.strtransoRange),
            of(Builder.chain, TestFiles.strtranschain), of(Builder.ref, TestFiles.strtransref),
            of(Builder.same, strtranssame), of(Builder.subAnn, TestFiles.strtranssubAnn),
            of(Builder.subClass, TestFiles.strtranssubClass),
            of(Builder.subData, TestFiles.strtranssubData),
            of(Builder.subObject, TestFiles.strtranssubObject),
            of(Builder.rule, TestFiles.strtransrule), of(Builder.symm, TestFiles.strtranssymm),
            of(Builder.trans, TestFiles.strtranstrans),
            of(Builder.hasKey, TestFiles.strtranshasKey),
            of(Builder.bigRule, TestFiles.strtransbigRule), of(Builder.ann, TestFiles.strtransann),
            of(Builder.asymm, TestFiles.strtransasymm),
            of(Builder.annDom, TestFiles.strtransannDom),
            of(Builder.annRange, TestFiles.strtransannRange),
            of(Builder.dRangeAnd, TestFiles.strtransdRangeAnd),
            of(Builder.dRangeOr, TestFiles.strtransdRangeOr),
            of(Builder.dOneOf, TestFiles.strtransdOneOf), of(Builder.dNot, TestFiles.strtransdNot),
            of(Builder.dRangeRestrict, TestFiles.strtransdRangeRestrict),
            of(Builder.assD, TestFiles.strtransassD),
            of(Builder.assDPlain, TestFiles.strtransassDPlain),
            of(Builder.dDom, TestFiles.strtransdDom), of(Builder.dc, TestFiles.strtransdc),
            of(Builder.du, TestFiles.strtransdu), of(Builder.ec, TestFiles.strtransec));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom ax, String expected) {
        StructuralTransformation testsubject = new StructuralTransformation(df);
        Set<OWLAxiom> transformedAxioms = testsubject.getTransformedAxioms(set(ax));
        assertEquals(expected, str(transformedAxioms));
    }
}
