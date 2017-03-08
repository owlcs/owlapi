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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.DLExpressivityChecker;

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class DLExpressivityCheckerTestCase extends TestBase {

    private final OWLAxiom object;
    private final String expected;

    public DLExpressivityCheckerTestCase(OWLAxiom object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String> map = new LinkedHashMap<>();
        map.put(b.dRange(), "AL(D)");
        map.put(b.dDef(), "AL");
        map.put(b.decC(), "AL");
        map.put(b.decOp(), "AL");
        map.put(b.decDp(), "AL");
        map.put(b.decDt(), "AL");
        map.put(b.decAp(), "AL");
        map.put(b.decI(), "AL");
        map.put(b.assDi(), "ALCO");
        map.put(b.dc(), "ALC");
        map.put(b.dDp(), "AL(D)");
        map.put(b.dOp(), "ALR");
        map.put(b.du(), "ALC");
        map.put(b.ec(), "AL");
        map.put(b.eDp(), "ALH(D)");
        map.put(b.eOp(), "ALH");
        map.put(b.fdp(), "ALF(D)");
        map.put(b.fop(), "ALF");
        map.put(b.ifp(), "ALIF");
        map.put(b.iop(), "ALI");
        map.put(b.irr(), "ALR");
        map.put(b.ndp(), "AL(D)");
        map.put(b.nop(), "AL");
        map.put(b.opa(), "AL");
        map.put(b.opaInv(), "ALI");
        map.put(b.opaInvj(), "ALI");
        map.put(b.oDom(), "AL");
        map.put(b.oRange(), "AL");
        map.put(b.chain(), "ALR");
        map.put(b.ref(), "ALR");
        map.put(b.same(), "ALO");
        map.put(b.subAnn(), "AL");
        map.put(b.subClass(), "AL");
        map.put(b.subData(), "ALH(D)");
        map.put(b.subObject(), "ALH");
        map.put(b.rule(), "AL");
        map.put(b.symm(), "ALI");
        map.put(b.trans(), "AL+");
        map.put(b.hasKey(), "AL");
        map.put(b.bigRule(), "AL");
        map.put(b.ann(), "AL");
        map.put(b.asymm(), "ALR");
        map.put(b.annDom(), "AL");
        map.put(b.annRange(), "AL");
        map.put(b.ass(), "AL");
        map.put(b.assAnd(), "AL");
        map.put(b.assOr(), "ALU");
        map.put(b.dRangeAnd(), "AL(D)");
        map.put(b.dRangeOr(), "AL(D)");
        map.put(b.assNot(), "ALC");
        map.put(b.assNotAnon(), "ALC");
        map.put(b.assSome(), "ALE");
        map.put(b.assAll(), "AL");
        map.put(b.assHas(), "ALEO");
        map.put(b.assMin(), "ALQ");
        map.put(b.assMax(), "ALQ");
        map.put(b.assEq(), "ALQ");
        map.put(b.assHasSelf(), "ALR");
        map.put(b.assOneOf(), "ALUO");
        map.put(b.assDSome(), "ALE(D)");
        map.put(b.assDAll(), "AL(D)");
        map.put(b.assDHas(), "AL(D)");
        map.put(b.assDMin(), "ALQ(D)");
        map.put(b.assDMax(), "ALQ(D)");
        map.put(b.assDEq(), "ALQ(D)");
        map.put(b.dOneOf(), "AL(D)");
        map.put(b.dNot(), "AL(D)");
        map.put(b.dRangeRestrict(), "AL(D)");
        map.put(b.assD(), "AL(D)");
        map.put(b.assDPlain(), "AL(D)");
        map.put(b.dDom(), "AL(D)");
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] {k, v}));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        DLExpressivityChecker testsubject = new DLExpressivityChecker(ont());
        String result = testsubject.getDescriptionLogicName();
        assertEquals(expected, result);
    }

    public Set<OWLOntology> ont() {
        OWLOntology o = getOWLOntology();
        o.add(object);
        Set<OWLOntology> singleton = Collections.singleton(o);
        return singleton;
    }
}
