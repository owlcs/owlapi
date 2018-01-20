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
        map.put(b.dDp(), "<urn:test:test#dp>");
        map.put(b.dOp(), "<urn:test:test#iri>");
        map.put(b.du(), "<urn:test:test#c>");
        map.put(b.ec(), "<urn:test:test#c>");
        map.put(b.eDp(), "<urn:test:test#dp>");
        map.put(b.eOp(), "<urn:test:test#iri>");
        map.put(b.fdp(), "<urn:test:test#dp>");
        map.put(b.fop(), "<urn:test:test#op>");
        map.put(b.ifp(), "<urn:test:test#op>");
        map.put(b.iop(), "<urn:test:test#op>");
        map.put(b.irr(), "<urn:test:test#op>");
        map.put(b.ndp(), "<urn:test:test#i>");
        map.put(b.nop(), "<urn:test:test#i>");
        map.put(b.opa(), "<urn:test:test#i>");
        map.put(b.opaInv(), "<urn:test:test#i>");
        map.put(b.opaInvj(), "<urn:test:test#i>");
        map.put(b.oDom(), "<urn:test:test#op>");
        map.put(b.oRange(), "<urn:test:test#op>");
        map.put(b.chain(), "<urn:test:test#op>");
        map.put(b.ref(), "<urn:test:test#op>");
        map.put(b.same(), "<urn:test:test#i>");
        map.put(b.subAnn(), "<urn:test:test#ann>");
        map.put(b.subClass(), "<urn:test:test#c>");
        map.put(b.subData(), "<urn:test:test#dp>");
        map.put(b.subObject(), "<urn:test:test#op>");
        map.put(b.rule(),
            "BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>) )");
        map.put(b.symm(), "<urn:test:test#op>");
        map.put(b.trans(), "<urn:test:test#op>");
        map.put(b.hasKey(), "<urn:test:test#c>");
        map.put(b.bigRule(),
            "BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>) )");
        map.put(b.ann(), "urn:test:test#iri");
        map.put(b.asymm(), "<urn:test:test#op>");
        map.put(b.annDom(), "<urn:test:test#ann>");
        map.put(b.annRange(), "<urn:test:test#ann>");
        map.put(b.ass(), "<urn:test:test#i>");
        map.put(b.assAnd(), "<urn:test:test#i>");
        map.put(b.assOr(), "<urn:test:test#i>");
        map.put(b.dRangeAnd(), "<urn:test:test#dp>");
        map.put(b.dRangeOr(), "<urn:test:test#dp>");
        map.put(b.assNot(), "<urn:test:test#i>");
        map.put(b.assNotAnon(), "_:id");
        map.put(b.assSome(), "<urn:test:test#i>");
        map.put(b.assAll(), "<urn:test:test#i>");
        map.put(b.assHas(), "<urn:test:test#i>");
        map.put(b.assMin(), "<urn:test:test#i>");
        map.put(b.assMax(), "<urn:test:test#i>");
        map.put(b.assEq(), "<urn:test:test#i>");
        map.put(b.assHasSelf(), "<urn:test:test#i>");
        map.put(b.assOneOf(), "<urn:test:test#i>");
        map.put(b.assDSome(), "<urn:test:test#i>");
        map.put(b.assDAll(), "<urn:test:test#i>");
        map.put(b.assDHas(), "<urn:test:test#i>");
        map.put(b.assDMin(), "<urn:test:test#i>");
        map.put(b.assDMax(), "<urn:test:test#i>");
        map.put(b.assDEq(), "<urn:test:test#i>");
        map.put(b.dOneOf(), "<urn:test:test#dp>");
        map.put(b.dNot(), "<urn:test:test#dp>");
        map.put(b.dRangeRestrict(), "<urn:test:test#dp>");
        map.put(b.assD(), "<urn:test:test#i>");
        map.put(b.assDPlain(), "<urn:test:test#i>");
        map.put(b.dDom(), "<urn:test:test#dp>");
        map.put(b.dRange(), "<urn:test:test#dp>");
        map.put(b.dDef(), "http://www.w3.org/2001/XMLSchema#double");
        map.put(b.decC(), "<urn:test:test#c>");
        map.put(b.decOp(), "<urn:test:test#op>");
        map.put(b.decDp(), "<urn:test:test#dp>");
        map.put(b.decDt(), "<urn:test:test#datatype>");
        map.put(b.decAp(), "<urn:test:test#ann>");
        map.put(b.decI(), "<urn:test:test#i>");
        map.put(b.assDi(), "<urn:test:test#i>");
        map.put(b.dc(), "<urn:test:test#c>");
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
