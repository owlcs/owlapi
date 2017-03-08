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

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class MaximumModalDepthFinderTestCase {

    private final OWLObject object;
    private final Integer expected;

    public MaximumModalDepthFinderTestCase(OWLObject object, Integer expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLObject, Integer> map = new LinkedHashMap<>();
        Integer zero = Integer.valueOf(0);
        Integer one = Integer.valueOf(1);
        map.put(b.ann(), zero);
        map.put(b.asymm(), zero);
        map.put(b.annDom(), zero);
        map.put(b.annRange(), zero);
        map.put(b.ass(), zero);
        map.put(b.assAnd(), zero);
        map.put(b.assOr(), zero);
        map.put(b.dRangeAnd(), zero);
        map.put(b.dRangeOr(), zero);
        map.put(b.assNot(), zero);
        map.put(b.assNotAnon(), zero);
        map.put(b.assSome(), one);
        map.put(b.assAll(), one);
        map.put(b.assHas(), zero);
        map.put(b.assMin(), one);
        map.put(b.assMax(), one);
        map.put(b.assEq(), one);
        map.put(b.assHasSelf(), one);
        map.put(b.assOneOf(), zero);
        map.put(b.assDSome(), one);
        map.put(b.assDAll(), one);
        map.put(b.assDHas(), one);
        map.put(b.assDMin(), one);
        map.put(b.assDMax(), one);
        map.put(b.assDEq(), one);
        map.put(b.dOneOf(), zero);
        map.put(b.dNot(), zero);
        map.put(b.dRangeRestrict(), zero);
        map.put(b.assD(), zero);
        map.put(b.assDPlain(), zero);
        map.put(b.dDom(), zero);
        map.put(b.dRange(), zero);
        map.put(b.dDef(), zero);
        map.put(b.decC(), zero);
        map.put(b.decOp(), zero);
        map.put(b.decDp(), zero);
        map.put(b.decDt(), zero);
        map.put(b.decAp(), zero);
        map.put(b.decI(), zero);
        map.put(b.assDi(), zero);
        map.put(b.dc(), zero);
        map.put(b.dDp(), zero);
        map.put(b.dOp(), zero);
        map.put(b.du(), zero);
        map.put(b.ec(), zero);
        map.put(b.eDp(), zero);
        map.put(b.eOp(), zero);
        map.put(b.fdp(), zero);
        map.put(b.fop(), zero);
        map.put(b.ifp(), zero);
        map.put(b.iop(), zero);
        map.put(b.irr(), zero);
        map.put(b.ndp(), zero);
        map.put(b.nop(), zero);
        map.put(b.opa(), zero);
        map.put(b.opaInv(), zero);
        map.put(b.opaInvj(), zero);
        map.put(b.oDom(), zero);
        map.put(b.oRange(), zero);
        map.put(b.chain(), zero);
        map.put(b.ref(), zero);
        map.put(b.same(), zero);
        map.put(b.subAnn(), zero);
        map.put(b.subClass(), zero);
        map.put(b.subData(), zero);
        map.put(b.subObject(), zero);
        map.put(b.rule(), zero);
        map.put(b.symm(), zero);
        map.put(b.trans(), zero);
        map.put(b.hasKey(), zero);
        map.put(b.bigRule(), zero);
        map.put(b.onto(), zero);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] {k, v}));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        MaximumModalDepthFinder testsubject = new MaximumModalDepthFinder();
        Integer i = object.accept(testsubject);
        assertEquals(expected, i);
    }
}
