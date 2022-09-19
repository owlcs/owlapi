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

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.utility.AxiomSubjectProviderEx;

class AxiomSubjectProviderExTestCase {

    private static final String TEST_IRI = "urn:test:test#iri";
    private static final String BUILT_IN_ATOM =
        "BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>))";
    private static final String IRI = "<urn:test:test#iri>";
    private static final String I = "<urn:test:test#i>";
    private static final String C = "<urn:test:test#c>";
    private static final String DP = "<urn:test:test#dp>";
    private static final String ANN = "<urn:test:test#ann>";
    private static final String OP = "<urn:test:test#op>";

    static Stream<Arguments> getData() {
        Builder b = new Builder();
        return Stream.of(Arguments.of(b.dDp(), DP), Arguments.of(b.dOp(), IRI),
            Arguments.of(b.du(), C), Arguments.of(b.ec(), C), Arguments.of(b.eDp(), DP),
            Arguments.of(b.eOp(), IRI), Arguments.of(b.fdp(), DP), Arguments.of(b.fop(), OP),
            Arguments.of(b.ifp(), OP), Arguments.of(b.iop(), OP), Arguments.of(b.irr(), OP),
            Arguments.of(b.ndp(), I), Arguments.of(b.nop(), I), Arguments.of(b.opa(), I),
            Arguments.of(b.opaInv(), I), Arguments.of(b.opaInvj(), I), Arguments.of(b.oDom(), OP),
            Arguments.of(b.oRange(), OP), Arguments.of(b.chain(), OP), Arguments.of(b.ref(), OP),
            Arguments.of(b.same(), I), Arguments.of(b.subAnn(), ANN), Arguments.of(b.subClass(), C),
            Arguments.of(b.subData(), DP), Arguments.of(b.subObject(), OP),
            Arguments.of(b.rule(), BUILT_IN_ATOM), Arguments.of(b.symm(), OP),
            Arguments.of(b.trans(), OP), Arguments.of(b.hasKey(), C),
            Arguments.of(b.bigRule(), BUILT_IN_ATOM), Arguments.of(b.ann(), TEST_IRI),
            Arguments.of(b.asymm(), OP), Arguments.of(b.annDom(), ANN),
            Arguments.of(b.annRange(), ANN), Arguments.of(b.ass(), I), Arguments.of(b.assAnd(), I),
            Arguments.of(b.assOr(), I), Arguments.of(b.dRangeAnd(), DP),
            Arguments.of(b.dRangeOr(), DP), Arguments.of(b.assNot(), I),
            Arguments.of(b.assNotAnon(), "_:id"), Arguments.of(b.assSome(), I),
            Arguments.of(b.assAll(), I), Arguments.of(b.assHas(), I), Arguments.of(b.assMin(), I),
            Arguments.of(b.assMax(), I), Arguments.of(b.assEq(), I),
            Arguments.of(b.assHasSelf(), I), Arguments.of(b.assOneOf(), I),
            Arguments.of(b.assDSome(), I), Arguments.of(b.assDAll(), I),
            Arguments.of(b.assDHas(), I), Arguments.of(b.assDMin(), I),
            Arguments.of(b.assDMax(), I), Arguments.of(b.assDEq(), I), Arguments.of(b.dOneOf(), DP),
            Arguments.of(b.dNot(), DP), Arguments.of(b.dRangeRestrict(), DP),
            Arguments.of(b.assD(), I), Arguments.of(b.assDPlain(), I), Arguments.of(b.dDom(), DP),
            Arguments.of(b.dRange(), DP),
            Arguments.of(b.dDef(), "http://www.w3.org/2001/XMLSchema#double"),
            Arguments.of(b.decC(), C), Arguments.of(b.decOp(), OP), Arguments.of(b.decDp(), DP),
            Arguments.of(b.decDt(), "<urn:test:test#datatype>"), Arguments.of(b.decAp(), ANN),
            Arguments.of(b.decI(), I), Arguments.of(b.assDi(), I), Arguments.of(b.dc(), C));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom object, String expected) {
        AxiomSubjectProviderEx testsubject = new AxiomSubjectProviderEx();
        String result = object.accept(testsubject).toString();
        assertEquals(expected, result);
    }
}
