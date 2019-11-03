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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.utility.StructuralTransformation;

@RunWith(Parameterized.class)
public class StructuralTransformationTestCase {

    public static final String strtranssame = "[]";
    private final OWLAxiom object;
    private final String expected;

    public StructuralTransformationTestCase(OWLAxiom object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String> map = new LinkedHashMap<>();
        map.put(b.dRange(), TestFiles.strtransdRange);
        map.put(b.dDef(), TestFiles.strtransdDef);
        map.put(b.decC(), TestFiles.strtransdecC);
        map.put(b.decOp(), TestFiles.strtransdecOp);
        map.put(b.decDp(), TestFiles.strtransdecDp);
        map.put(b.decDt(), TestFiles.strtransdecDt);
        map.put(b.decAp(), TestFiles.strtransdecAp);
        map.put(b.decI(), TestFiles.strtransdecI);
        map.put(b.dDp(), TestFiles.strtransdDp);
        map.put(b.dOp(), TestFiles.strtransdOp);
        map.put(b.eDp(), TestFiles.strtranseDp);
        map.put(b.eOp(), TestFiles.strtranseOp);
        map.put(b.fdp(), TestFiles.strtransfdp);
        map.put(b.fop(), TestFiles.strtransfop);
        map.put(b.ifp(), TestFiles.strtransifp);
        map.put(b.iop(), TestFiles.strtransiop);
        map.put(b.irr(), TestFiles.strtransirr);
        map.put(b.opa(), TestFiles.strtransopa);
        map.put(b.opaInv(), TestFiles.strtransopaInv);
        map.put(b.opaInvj(), TestFiles.strtransopaInvj);
        map.put(b.oDom(), TestFiles.strtransoDom);
        map.put(b.oRange(), TestFiles.strtransoRange);
        map.put(b.chain(), TestFiles.strtranschain);
        map.put(b.ref(), TestFiles.strtransref);
        map.put(b.same(), strtranssame);
        map.put(b.subAnn(), TestFiles.strtranssubAnn);
        map.put(b.subClass(), TestFiles.strtranssubClass);
        map.put(b.subData(), TestFiles.strtranssubData);
        map.put(b.subObject(), TestFiles.strtranssubObject);
        map.put(b.rule(), TestFiles.strtransrule);
        map.put(b.symm(), TestFiles.strtranssymm);
        map.put(b.trans(), TestFiles.strtranstrans);
        map.put(b.hasKey(), TestFiles.strtranshasKey);
        map.put(b.bigRule(), TestFiles.strtransbigRule);
        map.put(b.ann(), TestFiles.strtransann);
        map.put(b.asymm(), TestFiles.strtransasymm);
        map.put(b.annDom(), TestFiles.strtransannDom);
        map.put(b.annRange(), TestFiles.strtransannRange);
        map.put(b.dRangeAnd(), TestFiles.strtransdRangeAnd);
        map.put(b.dRangeOr(), TestFiles.strtransdRangeOr);
        map.put(b.dOneOf(), TestFiles.strtransdOneOf);
        map.put(b.dNot(), TestFiles.strtransdNot);
        map.put(b.dRangeRestrict(), TestFiles.strtransdRangeRestrict);
        map.put(b.assD(), TestFiles.strtransassD);
        map.put(b.assDPlain(), TestFiles.strtransassDPlain);
        map.put(b.dDom(), TestFiles.strtransdDom);
        map.put(b.dc(), TestFiles.strtransdc);
        map.put(b.du(), TestFiles.strtransdu);
        map.put(b.ec(), TestFiles.strtransec);
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        StructuralTransformation testsubject = new StructuralTransformation(OWLManager.getOWLDataFactory());
        Set<OWLAxiom> singleton = Collections.singleton(object);
        String result = new TreeSet<>(testsubject.getTransformedAxioms(singleton)).toString();
        assertEquals(expected.replace(",", ",\n"), result.replace(",", ",\n"));
    }
}
