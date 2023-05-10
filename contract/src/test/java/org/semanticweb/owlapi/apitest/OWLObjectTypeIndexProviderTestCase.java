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

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.Builder;

class OWLObjectTypeIndexProviderTestCase {

    @Test
    void testAssertions() {
        assertEquals(2034, Builder.ann.typeIndex());
        assertEquals(2018, Builder.asymm.typeIndex());
        assertEquals(2037, Builder.annDom.typeIndex());
        assertEquals(2036, Builder.annRange.typeIndex());
        assertEquals(2005, Builder.ass.typeIndex());
        assertEquals(2005, Builder.assAnd.typeIndex());
        assertEquals(2005, Builder.assOr.typeIndex());
        assertEquals(2030, Builder.dRangeAnd.typeIndex());
        assertEquals(2030, Builder.dRangeOr.typeIndex());
        assertEquals(2005, Builder.assNot.typeIndex());
        assertEquals(2005, Builder.assNotAnon.typeIndex());
        assertEquals(2005, Builder.assSome.typeIndex());
        assertEquals(2005, Builder.assAll.typeIndex());
        assertEquals(2005, Builder.assHas.typeIndex());
        assertEquals(2005, Builder.assMin.typeIndex());
        assertEquals(2005, Builder.assMax.typeIndex());
        assertEquals(2005, Builder.assEq.typeIndex());
        assertEquals(2005, Builder.assHasSelf.typeIndex());
        assertEquals(2005, Builder.assOneOf.typeIndex());
        assertEquals(2005, Builder.assDSome.typeIndex());
        assertEquals(2005, Builder.assDAll.typeIndex());
        assertEquals(2005, Builder.assDHas.typeIndex());
        assertEquals(2005, Builder.assDMin.typeIndex());
        assertEquals(2005, Builder.assDMax.typeIndex());
        assertEquals(2005, Builder.assDEq.typeIndex());
        assertEquals(2030, Builder.dOneOf.typeIndex());
        assertEquals(2030, Builder.dNot.typeIndex());
        assertEquals(2030, Builder.dRangeRestrict.typeIndex());
        assertEquals(2010, Builder.assD.typeIndex());
        assertEquals(2010, Builder.assDPlain.typeIndex());
        assertEquals(2029, Builder.dDom.typeIndex());
        assertEquals(2030, Builder.dRange.typeIndex());
        assertEquals(2038, Builder.dDef.typeIndex());
        assertEquals(2000, Builder.decC.typeIndex());
        assertEquals(2000, Builder.decOp.typeIndex());
        assertEquals(2000, Builder.decDp.typeIndex());
        assertEquals(2000, Builder.decDt.typeIndex());
        assertEquals(2000, Builder.decAp.typeIndex());
        assertEquals(2000, Builder.decI.typeIndex());
        assertEquals(2007, Builder.assDi.typeIndex());
        assertEquals(2003, Builder.dc.typeIndex());
        assertEquals(2031, Builder.dDp.typeIndex());
        assertEquals(2024, Builder.dOp.typeIndex());
        assertEquals(2004, Builder.du.typeIndex());
        assertEquals(2001, Builder.ec.typeIndex());
        assertEquals(2026, Builder.eDp.typeIndex());
        assertEquals(2012, Builder.eOp.typeIndex());
        assertEquals(2028, Builder.fdp.typeIndex());
        assertEquals(2015, Builder.fop.typeIndex());
        assertEquals(2016, Builder.ifp.typeIndex());
        assertEquals(2014, Builder.iop.typeIndex());
        assertEquals(2021, Builder.irr.typeIndex());
        assertEquals(2011, Builder.ndp.typeIndex());
        assertEquals(2009, Builder.nop.typeIndex());
        assertEquals(2008, Builder.opa.typeIndex());
        assertEquals(2008, Builder.opaInv.typeIndex());
        assertEquals(2008, Builder.opaInvj.typeIndex());
        assertEquals(2022, Builder.oDom.typeIndex());
        assertEquals(2023, Builder.oRange.typeIndex());
        assertEquals(2025, Builder.chain.typeIndex());
        assertEquals(2020, Builder.ref.typeIndex());
        assertEquals(2006, Builder.same.typeIndex());
        assertEquals(2035, Builder.subAnn.typeIndex());
        assertEquals(2002, Builder.subClass.typeIndex());
        assertEquals(2027, Builder.subData.typeIndex());
        assertEquals(2013, Builder.subObject.typeIndex());
        assertEquals(2033, Builder.rule.typeIndex());
        assertEquals(2017, Builder.symm.typeIndex());
        assertEquals(2019, Builder.trans.typeIndex());
        assertEquals(2032, Builder.hasKey.typeIndex());
        assertEquals(2033, Builder.bigRule.typeIndex());
        assertEquals(1, Builder.onto.typeIndex());
    }
}
