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

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class OWLObjectTypeIndexProviderTestCase extends TestBase {

    private OWLObject object;
    private int expected;

    public OWLObjectTypeIndexProviderTestCase(OWLObject object, int expected) {
        this.object = object;
        this.expected = expected;
    }

    @Nonnull
    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLObject, Integer> map = new LinkedHashMap<>();
        map.put(b.ann(), 2034);
        map.put(b.asymm(), 2018);
        map.put(b.annDom(), 2037);
        map.put(b.annRange(), 2036);
        map.put(b.ass(), 2005);
        map.put(b.assAnd(), 2005);
        map.put(b.assOr(), 2005);
        map.put(b.dRangeAnd(), 2030);
        map.put(b.dRangeOr(), 2030);
        map.put(b.assNot(), 2005);
        map.put(b.assNotAnon(), 2005);
        map.put(b.assSome(), 2005);
        map.put(b.assAll(), 2005);
        map.put(b.assHas(), 2005);
        map.put(b.assMin(), 2005);
        map.put(b.assMax(), 2005);
        map.put(b.assEq(), 2005);
        map.put(b.assHasSelf(), 2005);
        map.put(b.assOneOf(), 2005);
        map.put(b.assDSome(), 2005);
        map.put(b.assDAll(), 2005);
        map.put(b.assDHas(), 2005);
        map.put(b.assDMin(), 2005);
        map.put(b.assDMax(), 2005);
        map.put(b.assDEq(), 2005);
        map.put(b.dOneOf(), 2030);
        map.put(b.dNot(), 2030);
        map.put(b.dRangeRestrict(), 2030);
        map.put(b.assD(), 2010);
        map.put(b.assDPlain(), 2010);
        // 30
        map.put(b.dDom(), 2029);
        map.put(b.dRange(), 2030);
        map.put(b.dDef(), 2038);
        map.put(b.decC(), 2000);
        map.put(b.decOp(), 2000);
        map.put(b.decDp(), 2000);
        map.put(b.decDt(), 2000);
        map.put(b.decAp(), 2000);
        map.put(b.decI(), 2000);
        map.put(b.assDi(), 2007);
        map.put(b.dc(), 2003);
        map.put(b.dDp(), 2031);
        map.put(b.dOp(), 2024);
        map.put(b.du(), 2004);
        map.put(b.ec(), 2001);
        map.put(b.eDp(), 2026);
        // 46
        map.put(b.eOp(), 2012);
        map.put(b.fdp(), 2028);
        map.put(b.fop(), 2015);
        map.put(b.ifp(), 2016);
        map.put(b.iop(), 2014);
        map.put(b.irr(), 2021);
        map.put(b.ndp(), 2011);
        map.put(b.nop(), 2009);
        map.put(b.opa(), 2008);
        map.put(b.opaInv(), 2008);
        map.put(b.opaInvj(), 2008);
        map.put(b.oDom(), 2022);
        map.put(b.oRange(), 2023);
        map.put(b.chain(), 2025);
        map.put(b.ref(), 2020);
        map.put(b.same(), 2006);
        map.put(b.subAnn(), 2035);
        map.put(b.subClass(), 2002);
        map.put(b.subData(), 2027);
        map.put(b.subObject(), 2013);
        map.put(b.rule(), 2033);
        map.put(b.symm(), 2017);
        map.put(b.trans(), 2019);
        map.put(b.hasKey(), 2032);
        map.put(b.bigRule(), 2033);
        Collection<Object[]> toReturn = new ArrayList<>();
        for (Map.Entry<OWLObject, Integer> e : map.entrySet()) {
            toReturn.add(new Object[] { e.getKey(), e.getValue() });
        }
        return toReturn;
    }

    @Test
    public void testAssertion() {
        OWLObjectTypeIndexProvider testsubject = new OWLObjectTypeIndexProvider();
        int i = testsubject.getTypeIndex(object);
        assertEquals(expected, i);
    }
}
