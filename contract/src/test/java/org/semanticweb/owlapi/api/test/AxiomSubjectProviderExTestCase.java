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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.AxiomSubjectProviderEx;

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class AxiomSubjectProviderExTestCase {

    private static final String TEST_IRI = "urn:test:test#iri";
    private static final String BUILT_IN_ATOM =
        "BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>) )";
    private static final String IRI = "<urn:test:test#iri>";
    private static final String I = "<urn:test:test#i>";
    private static final String C = "<urn:test:test#c>";
    private static final String DP = "<urn:test:test#dp>";
    private static final String ANN = "<urn:test:test#ann>";
    private static final String OP = "<urn:test:test#op>";
    private final OWLAxiom object;
    private final String expected;

    public AxiomSubjectProviderExTestCase(OWLAxiom object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String> map = new LinkedHashMap<>();
        map.put(b.dDp(), DP);
        map.put(b.dOp(), IRI);
        map.put(b.du(), C);
        map.put(b.ec(), C);
        map.put(b.eDp(), DP);
        map.put(b.eOp(), IRI);
        map.put(b.fdp(), DP);
        map.put(b.fop(), OP);
        map.put(b.ifp(), OP);
        map.put(b.iop(), OP);
        map.put(b.irr(), OP);
        map.put(b.ndp(), I);
        map.put(b.nop(), I);
        map.put(b.opa(), I);
        map.put(b.opaInv(), I);
        map.put(b.opaInvj(), I);
        map.put(b.oDom(), OP);
        map.put(b.oRange(), OP);
        map.put(b.chain(), OP);
        map.put(b.ref(), OP);
        map.put(b.same(), I);
        map.put(b.subAnn(), ANN);
        map.put(b.subClass(), C);
        map.put(b.subData(), DP);
        map.put(b.subObject(), OP);
        map.put(b.rule(), BUILT_IN_ATOM);
        map.put(b.symm(), OP);
        map.put(b.trans(), OP);
        map.put(b.hasKey(), C);
        map.put(b.bigRule(), BUILT_IN_ATOM);
        map.put(b.ann(), TEST_IRI);
        map.put(b.asymm(), OP);
        map.put(b.annDom(), ANN);
        map.put(b.annRange(), ANN);
        map.put(b.ass(), I);
        map.put(b.assAnd(), I);
        map.put(b.assOr(), I);
        map.put(b.dRangeAnd(), DP);
        map.put(b.dRangeOr(), DP);
        map.put(b.assNot(), I);
        map.put(b.assNotAnon(), "_:id");
        map.put(b.assSome(), I);
        map.put(b.assAll(), I);
        map.put(b.assHas(), I);
        map.put(b.assMin(), I);
        map.put(b.assMax(), I);
        map.put(b.assEq(), I);
        map.put(b.assHasSelf(), I);
        map.put(b.assOneOf(), I);
        map.put(b.assDSome(), I);
        map.put(b.assDAll(), I);
        map.put(b.assDHas(), I);
        map.put(b.assDMin(), I);
        map.put(b.assDMax(), I);
        map.put(b.assDEq(), I);
        map.put(b.dOneOf(), DP);
        map.put(b.dNot(), DP);
        map.put(b.dRangeRestrict(), DP);
        map.put(b.assD(), I);
        map.put(b.assDPlain(), I);
        map.put(b.dDom(), DP);
        map.put(b.dRange(), DP);
        map.put(b.dDef(), "http://www.w3.org/2001/XMLSchema#double");
        map.put(b.decC(), C);
        map.put(b.decOp(), OP);
        map.put(b.decDp(), DP);
        map.put(b.decDt(), "<urn:test:test#datatype>");
        map.put(b.decAp(), ANN);
        map.put(b.decI(), I);
        map.put(b.assDi(), I);
        map.put(b.dc(), C);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] {k, v}));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        AxiomSubjectProviderEx testsubject = new AxiomSubjectProviderEx();
        String result = object.accept(testsubject).toString();
        assertEquals(expected, result);
    }
}
