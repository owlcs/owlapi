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
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.MaximumModalDepthFinder;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class MaximumModalDepthFinderTestCase {

    private final OWLObject object;
    private final int expected;

    public MaximumModalDepthFinderTestCase(OWLObject object, int expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLObject, Integer> map = new LinkedHashMap<>();
        map.put(b.ann(), 0);
        map.put(b.asymm(), 0);
        map.put(b.annDom(), 0);
        map.put(b.annRange(), 0);
        map.put(b.ass(), 0);
        map.put(b.assAnd(), 0);
        map.put(b.assOr(), 0);
        map.put(b.dRangeAnd(), 0);
        map.put(b.dRangeOr(), 0);
        map.put(b.assNot(), 0);
        map.put(b.assNotAnon(), 0);
        map.put(b.assSome(), 1);
        map.put(b.assAll(), 1);
        map.put(b.assHas(), 0);
        map.put(b.assMin(), 1);
        map.put(b.assMax(), 1);
        map.put(b.assEq(), 1);
        map.put(b.assHasSelf(), 1);
        map.put(b.assOneOf(), 0);
        map.put(b.assDSome(), 1);
        map.put(b.assDAll(), 1);
        map.put(b.assDHas(), 1);
        map.put(b.assDMin(), 1);
        map.put(b.assDMax(), 1);
        map.put(b.assDEq(), 1);
        map.put(b.dOneOf(), 0);
        map.put(b.dNot(), 0);
        map.put(b.dRangeRestrict(), 0);
        map.put(b.assD(), 0);
        map.put(b.assDPlain(), 0);
        map.put(b.dDom(), 0);
        map.put(b.dRange(), 0);
        map.put(b.dDef(), 0);
        map.put(b.decC(), 0);
        map.put(b.decOp(), 0);
        map.put(b.decDp(), 0);
        map.put(b.decDt(), 0);
        map.put(b.decAp(), 0);
        map.put(b.decI(), 0);
        map.put(b.assDi(), 0);
        map.put(b.dc(), 0);
        map.put(b.dDp(), 0);
        map.put(b.dOp(), 0);
        map.put(b.du(), 0);
        map.put(b.ec(), 0);
        map.put(b.eDp(), 0);
        map.put(b.eOp(), 0);
        map.put(b.fdp(), 0);
        map.put(b.fop(), 0);
        map.put(b.ifp(), 0);
        map.put(b.iop(), 0);
        map.put(b.irr(), 0);
        map.put(b.ndp(), 0);
        map.put(b.nop(), 0);
        map.put(b.opa(), 0);
        map.put(b.opaInv(), 0);
        map.put(b.opaInvj(), 0);
        map.put(b.oDom(), 0);
        map.put(b.oRange(), 0);
        map.put(b.chain(), 0);
        map.put(b.ref(), 0);
        map.put(b.same(), 0);
        map.put(b.subAnn(), 0);
        map.put(b.subClass(), 0);
        map.put(b.subData(), 0);
        map.put(b.subObject(), 0);
        map.put(b.rule(), 0);
        map.put(b.symm(), 0);
        map.put(b.trans(), 0);
        map.put(b.hasKey(), 0);
        map.put(b.bigRule(), 0);
        map.put(b.onto(), 0);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        MaximumModalDepthFinder testsubject = new MaximumModalDepthFinder();
        int i = object.accept(testsubject);
        assertEquals(expected, i);
    }
}
