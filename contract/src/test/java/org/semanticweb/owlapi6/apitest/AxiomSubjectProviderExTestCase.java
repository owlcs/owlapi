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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.utility.AxiomSubjectProviderEx;

@RunWith(Parameterized.class)
public class AxiomSubjectProviderExTestCase {

    private static final String iri_ir = "urn:test:test#iri";
    private static final String builtin = "BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>))";
    private static final String iri_op = "<urn:test:test#op>";
    private static final String iri_iri = "<urn:test:test#iri>";
    private static final String iri_c = "<urn:test:test#c>";
    private static final String iri_ann = "<urn:test:test#ann>";
    private static final String iri_dp = "<urn:test:test#dp>";
    private static final String iri_i = "<urn:test:test#i>";
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
        map.put(b.dDp(), iri_dp);
        map.put(b.dOp(), iri_iri);
        map.put(b.du(), iri_c);
        map.put(b.ec(), iri_c);
        map.put(b.eDp(), iri_dp);
        map.put(b.eOp(), iri_iri);
        map.put(b.fdp(), iri_dp);
        map.put(b.fop(), iri_op);
        map.put(b.ifp(), iri_op);
        map.put(b.iop(), iri_op);
        map.put(b.irr(), iri_op);
        map.put(b.ndp(), iri_i);
        map.put(b.nop(), iri_i);
        map.put(b.opa(), iri_i);
        map.put(b.opaInv(), iri_i);
        map.put(b.opaInvj(), iri_i);
        map.put(b.oDom(), iri_op);
        map.put(b.oRange(), iri_op);
        map.put(b.chain(), iri_op);
        map.put(b.ref(), iri_op);
        map.put(b.same(), iri_i);
        map.put(b.subAnn(), iri_ann);
        map.put(b.subClass(), iri_c);
        map.put(b.subData(), iri_dp);
        map.put(b.subObject(), iri_op);
        map.put(b.rule(), builtin);
        map.put(b.symm(), iri_op);
        map.put(b.trans(), iri_op);
        map.put(b.hasKey(), iri_c);
        map.put(b.bigRule(),
            builtin);
        map.put(b.ann(), iri_ir);
        map.put(b.asymm(), iri_op);
        map.put(b.annDom(), iri_ann);
        map.put(b.annRange(), iri_ann);
        map.put(b.ass(), iri_i);
        map.put(b.assAnd(), iri_i);
        map.put(b.assOr(), iri_i);
        map.put(b.dRangeAnd(), iri_dp);
        map.put(b.dRangeOr(), iri_dp);
        map.put(b.assNot(), iri_i);
        map.put(b.assNotAnon(), "_:id");
        map.put(b.assSome(), iri_i);
        map.put(b.assAll(), iri_i);
        map.put(b.assHas(), iri_i);
        map.put(b.assMin(), iri_i);
        map.put(b.assMax(), iri_i);
        map.put(b.assEq(), iri_i);
        map.put(b.assHasSelf(), iri_i);
        map.put(b.assOneOf(), iri_i);
        map.put(b.assDSome(), iri_i);
        map.put(b.assDAll(), iri_i);
        map.put(b.assDHas(), iri_i);
        map.put(b.assDMin(), iri_i);
        map.put(b.assDMax(), iri_i);
        map.put(b.assDEq(), iri_i);
        map.put(b.dOneOf(), iri_dp);
        map.put(b.dNot(), iri_dp);
        map.put(b.dRangeRestrict(), iri_dp);
        map.put(b.assD(), iri_i);
        map.put(b.assDPlain(), iri_i);
        map.put(b.dDom(), iri_dp);
        map.put(b.dRange(), iri_dp);
        map.put(b.dDef(), "http://www.w3.org/2001/XMLSchema#double");
        map.put(b.decC(), iri_c);
        map.put(b.decOp(), iri_op);
        map.put(b.decDp(), iri_dp);
        map.put(b.decDt(), "<urn:test:test#datatype>");
        map.put(b.decAp(), iri_ann);
        map.put(b.decI(), iri_i);
        map.put(b.assDi(), iri_i);
        map.put(b.dc(), iri_c);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        AxiomSubjectProviderEx testsubject = new AxiomSubjectProviderEx();
        String result = object.accept(testsubject).toString();
        assertEquals(expected, result);
    }
}
