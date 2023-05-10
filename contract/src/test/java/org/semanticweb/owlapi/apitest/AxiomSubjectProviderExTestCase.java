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
import org.semanticweb.owlapi.apitest.baseclasses.Builder;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.utility.AxiomSubjectProviderEx;

class AxiomSubjectProviderExTestCase extends TestBase {


    static Stream<Arguments> getData() {
        String I = INDIVIDUALS.I.getIRI().toQuotedString();
        String DP = DATAPROPS.DP.getIRI().toQuotedString();
        String ANN = ANNPROPS.ap.getIRI().toQuotedString();
        String OP = OBJPROPS.OP.getIRI().toQuotedString();
        String c = CLASSES.C.getIRI().toQuotedString();
        return Stream.of(of(Builder.dDp, IRIS.iri.toQuotedString()), of(Builder.dOp, OP),
            of(Builder.du, c), of(Builder.ec, c), of(Builder.eDp, IRIS.iri.toQuotedString()),
            of(Builder.eOp, OP), of(Builder.fdp, DP), of(Builder.fop, OP), of(Builder.ifp, OP),
            of(Builder.iop, OP), of(Builder.irr, OP), of(Builder.ndp, I), of(Builder.nop, I),
            of(Builder.opa, I), of(Builder.opaInv, I), of(Builder.opaInvj, I), of(Builder.oDom, OP),
            of(Builder.oRange, OP), of(Builder.chain, OP), of(Builder.ref, OP), of(Builder.same, I),
            of(Builder.subAnn, ANN), of(Builder.subClass, c), of(Builder.subData, DP),
            of(Builder.subObject, OP), of(Builder.rule, TestFiles.BUILT_IN_ATOM),
            of(Builder.symm, OP), of(Builder.trans, OP), of(Builder.hasKey, c),
            of(Builder.bigRule, TestFiles.BUILT_IN_ATOM), of(Builder.ann, IRIS.iri.toString()),
            of(Builder.asymm, OP), of(Builder.annDom, ANN), of(Builder.annRange, ANN),
            of(Builder.ass, I), of(Builder.assAnd, I), of(Builder.assOr, I),
            of(Builder.dRangeAnd, DP), of(Builder.dRangeOr, DP), of(Builder.assNot, I),
            of(Builder.assNotAnon, "_:id"), of(Builder.assSome, I), of(Builder.assAll, I),
            of(Builder.assHas, I), of(Builder.assMin, I), of(Builder.assMax, I),
            of(Builder.assEq, I), of(Builder.assHasSelf, I), of(Builder.assOneOf, I),
            of(Builder.assDSome, I), of(Builder.assDAll, I), of(Builder.assDHas, I),
            of(Builder.assDMin, I), of(Builder.assDMax, I), of(Builder.assDEq, I),
            of(Builder.dOneOf, DP), of(Builder.dNot, DP), of(Builder.dRangeRestrict, DP),
            of(Builder.assD, I), of(Builder.assDPlain, I), of(Builder.dDom, DP),
            of(Builder.dRange, DP), of(Builder.dDef, TestFiles.doubl), of(Builder.decC, c),
            of(Builder.decOp, OP), of(Builder.decDp, DP),
            of(Builder.decDt, DATATYPES.DT.getIRI().toQuotedString()), of(Builder.decAp, ANN),
            of(Builder.decI, I), of(Builder.assDi, I), of(Builder.dc, c));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom object, String expected) {
        AxiomSubjectProviderEx testsubject = new AxiomSubjectProviderEx();
        String result = object.accept(testsubject).toString();
        assertEquals(expected, result);
    }
}
