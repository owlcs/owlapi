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
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.utility.SimpleRenderer;

@RunWith(Parameterized.class)
public class VisitorsTestCase extends TestBase {

    private final OWLObject object;
    private final String expected;

    public VisitorsTestCase(OWLObject object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLObject, String> map = new LinkedHashMap<>();
        map.put(b.rule(), TestFiles.RULE);
        map.put(b.bigRule(), TestFiles.BIGRULE);
        map.put(b.onto(), TestFiles.ONTO);
        map.put(b.ann(), TestFiles.ANN);
        map.put(b.asymm(), TestFiles.ASYMM);
        map.put(b.annDom(), TestFiles.ANNDOM);
        map.put(b.annRange(), TestFiles.ANNRANGE);
        map.put(b.ass(), TestFiles.ASS);
        map.put(b.assAnd(), TestFiles.ASSAND);
        map.put(b.assOr(), TestFiles.ASSOR);
        map.put(b.dRangeAnd(), TestFiles.DRANGEAND);
        map.put(b.dRangeOr(), TestFiles.DRANGEOR);
        map.put(b.assNot(), TestFiles.ASSNOT);
        map.put(b.assNotAnon(), TestFiles.ASSNOTANON);
        map.put(b.assSome(), TestFiles.ASSSOME);
        map.put(b.assAll(), TestFiles.ASSALL);
        map.put(b.assHas(), TestFiles.ASSHAS);
        map.put(b.assMin(), TestFiles.ASSMIN);
        map.put(b.assMax(), TestFiles.ASSMAX);
        map.put(b.assEq(), TestFiles.ASSEQ);
        map.put(b.assHasSelf(), TestFiles.ASSHASSELF);
        map.put(b.assOneOf(), TestFiles.ASSONEOF);
        map.put(b.assDSome(), TestFiles.assDSome);
        map.put(b.assDAll(), TestFiles.assDAll);
        map.put(b.assDHas(), TestFiles.assDHas);
        map.put(b.assDMin(), TestFiles.assDMin);
        map.put(b.assDMax(), TestFiles.assDMax);
        map.put(b.assDEq(), TestFiles.assDEq);
        map.put(b.dOneOf(), TestFiles.dataOneOf);
        map.put(b.dNot(), TestFiles.dataNot);
        map.put(b.dRangeRestrict(), TestFiles.dRangeRestrict);
        map.put(b.assD(), TestFiles.assD);
        map.put(b.assDPlain(), TestFiles.assDPlain);
        map.put(b.dDom(), TestFiles.dDom);
        map.put(b.dRange(), TestFiles.dRange);
        map.put(b.dDef(), TestFiles.dDef);
        map.put(b.decC(), TestFiles.decC);
        map.put(b.decOp(), TestFiles.decOp);
        map.put(b.decDp(), TestFiles.decDp);
        map.put(b.decDt(), TestFiles.decDt);
        map.put(b.decAp(), TestFiles.decAp);
        map.put(b.decI(), TestFiles.decI);
        map.put(b.assDi(), TestFiles.assDi);
        map.put(b.dc(), TestFiles.disjointClasses);
        map.put(b.dDp(), TestFiles.disjointDp);
        map.put(b.dOp(), TestFiles.disjointOp);
        map.put(b.du(), TestFiles.disjointu);
        map.put(b.ec(), TestFiles.ec);
        map.put(b.eDp(), TestFiles.eDp);
        map.put(b.eOp(), TestFiles.eOp);
        map.put(b.fdp(), TestFiles.functionaldp);
        map.put(b.fop(), TestFiles.functionalop);
        map.put(b.ifp(), TestFiles.inversefp);
        map.put(b.iop(), TestFiles.inverseop);
        map.put(b.irr(), TestFiles.irreflexive);
        map.put(b.ndp(), TestFiles.ndp);
        map.put(b.nop(), TestFiles.nop);
        map.put(b.opa(), TestFiles.opa);
        map.put(b.opaInv(), TestFiles.OPAINV);
        map.put(b.opaInvj(), TestFiles.OPAINVJ);
        map.put(b.oDom(), TestFiles.ODOM);
        map.put(b.oRange(), TestFiles.ORANGE);
        map.put(b.chain(), TestFiles.CHAIN);
        map.put(b.ref(), TestFiles.REF);
        map.put(b.same(), TestFiles.SAME);
        map.put(b.subAnn(), TestFiles.SUBANN);
        map.put(b.subClass(), TestFiles.SUBCLASS);
        map.put(b.subData(), TestFiles.SUBDATA);
        map.put(b.subObject(), TestFiles.SUBOBJECT);
        map.put(b.symm(), TestFiles.SYMM);
        map.put(b.trans(), TestFiles.TRANS);
        map.put(b.hasKey(), TestFiles.HASKEY);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        String render = new SimpleRenderer().render(object);
        assertEquals(expected, render);
    }
}
