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
package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.utility.MaximumModalDepthFinder;

class MaximumModalDepthFinderTestCase {
    static Collection<Object> getDataOne() {
        return Arrays.asList(Builder.assSome, Builder.assAll, Builder.assMin, Builder.assMax,
            Builder.assEq, Builder.assHasSelf, Builder.assDSome, Builder.assDAll, Builder.assDHas,
            Builder.assDMin, Builder.assDMax, Builder.assDEq);

    }

    static Collection<Object> getDataZero() {
        return Arrays.asList(Builder.ann, Builder.asymm, Builder.annDom, Builder.annRange,
            Builder.ass, Builder.assAnd, Builder.assOr, Builder.dRangeAnd, Builder.dRangeOr,
            Builder.assNot, Builder.assNotAnon, Builder.assHas, Builder.assOneOf, Builder.dOneOf,
            Builder.dNot, Builder.dRangeRestrict, Builder.assD, Builder.assDPlain, Builder.dDom,
            Builder.dRange, Builder.dDef, Builder.decC, Builder.decOp, Builder.decDp, Builder.decDt,
            Builder.decAp, Builder.decI, Builder.assDi, Builder.dc, Builder.dDp, Builder.dOp,
            Builder.du, Builder.ec, Builder.eDp, Builder.eOp, Builder.fdp, Builder.fop, Builder.ifp,
            Builder.iop, Builder.irr, Builder.ndp, Builder.nop, Builder.opa, Builder.opaInv,
            Builder.opaInvj, Builder.oDom, Builder.oRange, Builder.chain, Builder.ref, Builder.same,
            Builder.subAnn, Builder.subClass, Builder.subData, Builder.subObject, Builder.rule,
            Builder.symm, Builder.trans, Builder.hasKey, Builder.bigRule, Builder.onto);
    }

    @ParameterizedTest
    @MethodSource("getDataZero")
    void testAssertionZero(OWLObject object) {
        MaximumModalDepthFinder testsubject = new MaximumModalDepthFinder();
        Integer i = object.accept(testsubject);
        assertEquals(Integer.valueOf(0), i);
    }

    @ParameterizedTest
    @MethodSource("getDataOne")
    void testAssertionOne(OWLObject object) {
        MaximumModalDepthFinder testsubject = new MaximumModalDepthFinder();
        Integer i = object.accept(testsubject);
        assertEquals(Integer.valueOf(1), i);
    }
}
