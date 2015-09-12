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
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.HornAxiomVisitorEx;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class HornAxiomVisitorExTestCase {

    private final OWLAxiom object;
    private final Boolean b;

    public HornAxiomVisitorExTestCase(OWLAxiom object, Boolean b) {
        this.object = object;
        this.b = b;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLObject, Boolean> map = new LinkedHashMap<>();
        map.put(b.ann(), Boolean.TRUE);
        map.put(b.asymm(), Boolean.FALSE);
        map.put(b.annDom(), Boolean.TRUE);
        map.put(b.annRange(), Boolean.TRUE);
        map.put(b.ass(), Boolean.FALSE);
        map.put(b.assAnd(), Boolean.FALSE);
        map.put(b.assOr(), Boolean.FALSE);
        map.put(b.dRangeAnd(), Boolean.FALSE);
        map.put(b.dRangeOr(), Boolean.FALSE);
        map.put(b.assNot(), Boolean.FALSE);
        map.put(b.assNotAnon(), Boolean.FALSE);
        map.put(b.assSome(), Boolean.FALSE);
        map.put(b.assAll(), Boolean.FALSE);
        map.put(b.assHas(), Boolean.FALSE);
        map.put(b.assMin(), Boolean.FALSE);
        map.put(b.assMax(), Boolean.FALSE);
        map.put(b.assEq(), Boolean.FALSE);
        map.put(b.assHasSelf(), Boolean.FALSE);
        map.put(b.assOneOf(), Boolean.FALSE);
        map.put(b.assDSome(), Boolean.FALSE);
        map.put(b.assDAll(), Boolean.FALSE);
        map.put(b.assDHas(), Boolean.FALSE);
        map.put(b.assDMin(), Boolean.FALSE);
        map.put(b.assDMax(), Boolean.FALSE);
        map.put(b.assDEq(), Boolean.FALSE);
        map.put(b.dOneOf(), Boolean.FALSE);
        map.put(b.dNot(), Boolean.FALSE);
        map.put(b.dRangeRestrict(), Boolean.FALSE);
        map.put(b.assD(), Boolean.FALSE);
        map.put(b.assDPlain(), Boolean.FALSE);
        map.put(b.dDom(), Boolean.FALSE);
        map.put(b.dRange(), Boolean.FALSE);
        map.put(b.dDef(), Boolean.FALSE);
        map.put(b.decC(), Boolean.TRUE);
        map.put(b.decOp(), Boolean.TRUE);
        map.put(b.decDp(), Boolean.TRUE);
        map.put(b.decDt(), Boolean.TRUE);
        map.put(b.decAp(), Boolean.TRUE);
        map.put(b.decI(), Boolean.TRUE);
        map.put(b.assDi(), Boolean.FALSE);
        map.put(b.dc(), Boolean.TRUE);
        map.put(b.dDp(), Boolean.FALSE);
        map.put(b.dOp(), Boolean.FALSE);
        map.put(b.du(), Boolean.TRUE);
        map.put(b.ec(), Boolean.TRUE);
        map.put(b.eDp(), Boolean.FALSE);
        map.put(b.eOp(), Boolean.TRUE);
        map.put(b.fdp(), Boolean.FALSE);
        map.put(b.fop(), Boolean.TRUE);
        map.put(b.ifp(), Boolean.TRUE);
        map.put(b.iop(), Boolean.TRUE);
        map.put(b.irr(), Boolean.FALSE);
        map.put(b.ndp(), Boolean.FALSE);
        map.put(b.nop(), Boolean.FALSE);
        map.put(b.opa(), Boolean.FALSE);
        map.put(b.opaInv(), Boolean.FALSE);
        map.put(b.opaInvj(), Boolean.FALSE);
        map.put(b.oDom(), Boolean.TRUE);
        map.put(b.oRange(), Boolean.TRUE);
        map.put(b.chain(), Boolean.FALSE);
        map.put(b.ref(), Boolean.FALSE);
        map.put(b.same(), Boolean.FALSE);
        map.put(b.subAnn(), Boolean.TRUE);
        map.put(b.subClass(), Boolean.TRUE);
        map.put(b.subData(), Boolean.FALSE);
        map.put(b.subObject(), Boolean.TRUE);
        map.put(b.rule(), Boolean.FALSE);
        map.put(b.symm(), Boolean.TRUE);
        map.put(b.trans(), Boolean.TRUE);
        map.put(b.hasKey(), Boolean.FALSE);
        map.put(b.bigRule(), Boolean.FALSE);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        HornAxiomVisitorEx testsubject = new HornAxiomVisitorEx();
        Boolean result = object.accept(testsubject);
        assertEquals(b, result);
    }
}
