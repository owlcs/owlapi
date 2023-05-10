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
import org.semanticweb.owlapi.apitest.baseclasses.Builder;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.utility.HornAxiomVisitorEx;

class HornAxiomVisitorExTestCase {

    static Collection<Object> getDataFalse() {
        return Arrays.asList(Builder.asymm, Builder.ass, Builder.assAnd, Builder.assOr,
            Builder.dRangeAnd, Builder.dRangeOr, Builder.assNot, Builder.assNotAnon,
            Builder.assSome, Builder.assAll, Builder.assHas, Builder.assMin, Builder.assMax,
            Builder.assEq, Builder.assHasSelf, Builder.assOneOf, Builder.assDSome, Builder.assDAll,
            Builder.assDHas, Builder.assDMin, Builder.assDMax, Builder.assDEq, Builder.dOneOf,
            Builder.dNot, Builder.dRangeRestrict, Builder.assD, Builder.assDPlain, Builder.dDom,
            Builder.dRange, Builder.dDef, Builder.assDi, Builder.dDp, Builder.dOp, Builder.eDp,
            Builder.fdp, Builder.irr, Builder.ndp, Builder.nop, Builder.opa, Builder.opaInv,
            Builder.opaInvj, Builder.chain, Builder.ref, Builder.same, Builder.subData,
            Builder.rule, Builder.hasKey, Builder.bigRule);
    }

    static Collection<Object> getDataTrue() {
        return Arrays.asList(Builder.ann, Builder.annDom, Builder.annRange, Builder.decC,
            Builder.decOp, Builder.decDp, Builder.decDt, Builder.decAp, Builder.decI, Builder.dc,
            Builder.du, Builder.ec, Builder.eOp, Builder.fop, Builder.ifp, Builder.iop,
            Builder.oDom, Builder.oRange, Builder.subAnn, Builder.subClass, Builder.subObject,
            Builder.symm, Builder.trans);
    }

    @ParameterizedTest
    @MethodSource("getDataTrue")
    void testAssertionTrue(OWLAxiom object) {
        HornAxiomVisitorEx testsubject = new HornAxiomVisitorEx();
        Boolean result = object.accept(testsubject);
        assertEquals(Boolean.TRUE, result);
    }

    @ParameterizedTest
    @MethodSource("getDataFalse")
    void testAssertionFalse(OWLAxiom object) {
        HornAxiomVisitorEx testsubject = new HornAxiomVisitorEx();
        Boolean result = object.accept(testsubject);
        assertEquals(Boolean.FALSE, result);
    }
}
