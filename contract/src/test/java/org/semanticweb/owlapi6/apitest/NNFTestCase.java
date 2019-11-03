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
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.utility.NNF;

@RunWith(Parameterized.class)
public class NNFTestCase {

    private final OWLAxiom object;
    private final String expected;

    public NNFTestCase(OWLAxiom object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String> map = new LinkedHashMap<>();
        map.put(b.dRange(), TestFiles.nnfdRange);
        map.put(b.dDef(), TestFiles.nnfdDef);
        map.put(b.decC(), TestFiles.nnfdecC);
        map.put(b.decOp(), TestFiles.nnfdecOp);
        map.put(b.decDp(), TestFiles.nnfdecDp);
        map.put(b.decDt(), TestFiles.nnfdecDt);
        map.put(b.decAp(), TestFiles.nnfdecAp);
        map.put(b.decI(), TestFiles.nnfdecI);
        map.put(b.assDi(), TestFiles.nnfassDi);
        map.put(b.dc(), TestFiles.nnfdc);
        map.put(b.dDp(), TestFiles.nnfdDp);
        map.put(b.dOp(), TestFiles.nnfdOp);
        map.put(b.du(), TestFiles.nnfdu);
        map.put(b.ec(), TestFiles.nnfec);
        map.put(b.eDp(), TestFiles.nnfeDp);
        map.put(b.eOp(), TestFiles.nnfeOp);
        map.put(b.fdp(), TestFiles.nnffdp);
        map.put(b.fop(), TestFiles.nnffop);
        map.put(b.ifp(), TestFiles.nnfifp);
        map.put(b.iop(), TestFiles.nnfiop);
        map.put(b.irr(), TestFiles.nnfirr);
        map.put(b.ndp(), TestFiles.nnfndp);
        map.put(b.nop(), TestFiles.nnfnop);
        map.put(b.opa(), TestFiles.nnfopa);
        map.put(b.opaInv(), TestFiles.nnfopaInv);
        map.put(b.opaInvj(), TestFiles.nnfopaInvj);
        map.put(b.oDom(), TestFiles.nnfoDom);
        map.put(b.oRange(), TestFiles.nnfoRange);
        map.put(b.chain(), TestFiles.nnfchain);
        map.put(b.ref(), TestFiles.nnfref);
        map.put(b.same(), TestFiles.nnfsame);
        map.put(b.subAnn(), TestFiles.nnfsubAnn);
        map.put(b.subClass(), TestFiles.nnfsubClass);
        map.put(b.subData(), TestFiles.nnfsubData);
        map.put(b.subObject(), TestFiles.nnfsubObject);
        map.put(b.rule(), TestFiles.nnfrule);
        map.put(b.symm(), TestFiles.nnfsymm);
        map.put(b.trans(), TestFiles.nnftrans);
        map.put(b.hasKey(), TestFiles.nnfhasKey);
        map.put(b.bigRule(), TestFiles.nnfbigRule);
        map.put(b.ann(), TestFiles.nnfann);
        map.put(b.asymm(), TestFiles.nnfasymm);
        map.put(b.annDom(), TestFiles.nnfannDom);
        map.put(b.annRange(), TestFiles.nnfannRange);
        map.put(b.ass(), TestFiles.nnfass);
        map.put(b.assAnd(), TestFiles.nnfassAnd);
        map.put(b.assOr(), TestFiles.nnfassOr);
        map.put(b.dRangeAnd(), TestFiles.nnfdRangeAnd);
        map.put(b.dRangeOr(), TestFiles.nnfdRangeOr);
        map.put(b.assNot(), TestFiles.nnfassNot);
        map.put(b.assNotAnon(), TestFiles.nnfassNotAnon);
        map.put(b.assSome(), TestFiles.nnfassSome);
        map.put(b.assAll(), TestFiles.nnfassAll);
        map.put(b.assHas(), TestFiles.nnfassHas);
        map.put(b.assMin(), TestFiles.nnfassMin);
        map.put(b.assMax(), TestFiles.nnfassMax);
        map.put(b.assEq(), TestFiles.nnfassEq);
        map.put(b.assHasSelf(), TestFiles.nnfassHasSelf);
        map.put(b.assOneOf(), TestFiles.nnfassOneOf);
        map.put(b.assDSome(), TestFiles.nnfassDSome);
        map.put(b.assDAll(), TestFiles.nnfassDAll);
        map.put(b.assDHas(), TestFiles.nnfassDHas);
        map.put(b.assDMin(), TestFiles.nnfassDMin);
        map.put(b.assDMax(), TestFiles.nnfassDMax);
        map.put(b.assDEq(), TestFiles.nnfassDEq);
        map.put(b.dOneOf(), TestFiles.nnfdOneOf);
        map.put(b.dNot(), TestFiles.nnfdNot);
        map.put(b.dRangeRestrict(), TestFiles.nnfdRangeRestrict);
        map.put(b.assD(), TestFiles.nnfassD);
        map.put(b.assDPlain(), TestFiles.nnfassDPlain);
        map.put(b.dDom(), TestFiles.nnfdDom);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        NNF testsubject = new NNF(OWLManager.getOWLDataFactory());
        String result = object.accept(testsubject).toString();
        assertEquals(expected, result);
    }
}
