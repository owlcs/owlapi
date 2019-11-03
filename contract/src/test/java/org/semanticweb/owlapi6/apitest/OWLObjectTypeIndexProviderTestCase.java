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

import org.junit.Test;

public class OWLObjectTypeIndexProviderTestCase {

    Builder b = new Builder();

    @Test
    public void testAssertions() {
        assertEquals(2034, b.ann().typeIndex());
        assertEquals(2018, b.asymm().typeIndex());
        assertEquals(2037, b.annDom().typeIndex());
        assertEquals(2036, b.annRange().typeIndex());
        assertEquals(2005, b.ass().typeIndex());
        assertEquals(2005, b.assAnd().typeIndex());
        assertEquals(2005, b.assOr().typeIndex());
        assertEquals(2030, b.dRangeAnd().typeIndex());
        assertEquals(2030, b.dRangeOr().typeIndex());
        assertEquals(2005, b.assNot().typeIndex());
        assertEquals(2005, b.assNotAnon().typeIndex());
        assertEquals(2005, b.assSome().typeIndex());
        assertEquals(2005, b.assAll().typeIndex());
        assertEquals(2005, b.assHas().typeIndex());
        assertEquals(2005, b.assMin().typeIndex());
        assertEquals(2005, b.assMax().typeIndex());
        assertEquals(2005, b.assEq().typeIndex());
        assertEquals(2005, b.assHasSelf().typeIndex());
        assertEquals(2005, b.assOneOf().typeIndex());
        assertEquals(2005, b.assDSome().typeIndex());
        assertEquals(2005, b.assDAll().typeIndex());
        assertEquals(2005, b.assDHas().typeIndex());
        assertEquals(2005, b.assDMin().typeIndex());
        assertEquals(2005, b.assDMax().typeIndex());
        assertEquals(2005, b.assDEq().typeIndex());
        assertEquals(2030, b.dOneOf().typeIndex());
        assertEquals(2030, b.dNot().typeIndex());
        assertEquals(2030, b.dRangeRestrict().typeIndex());
        assertEquals(2010, b.assD().typeIndex());
        assertEquals(2010, b.assDPlain().typeIndex());
        assertEquals(2029, b.dDom().typeIndex());
        assertEquals(2030, b.dRange().typeIndex());
        assertEquals(2038, b.dDef().typeIndex());
        assertEquals(2000, b.decC().typeIndex());
        assertEquals(2000, b.decOp().typeIndex());
        assertEquals(2000, b.decDp().typeIndex());
        assertEquals(2000, b.decDt().typeIndex());
        assertEquals(2000, b.decAp().typeIndex());
        assertEquals(2000, b.decI().typeIndex());
        assertEquals(2007, b.assDi().typeIndex());
        assertEquals(2003, b.dc().typeIndex());
        assertEquals(2031, b.dDp().typeIndex());
        assertEquals(2024, b.dOp().typeIndex());
        assertEquals(2004, b.du().typeIndex());
        assertEquals(2001, b.ec().typeIndex());
        assertEquals(2026, b.eDp().typeIndex());
        assertEquals(2012, b.eOp().typeIndex());
        assertEquals(2028, b.fdp().typeIndex());
        assertEquals(2015, b.fop().typeIndex());
        assertEquals(2016, b.ifp().typeIndex());
        assertEquals(2014, b.iop().typeIndex());
        assertEquals(2021, b.irr().typeIndex());
        assertEquals(2011, b.ndp().typeIndex());
        assertEquals(2009, b.nop().typeIndex());
        assertEquals(2008, b.opa().typeIndex());
        assertEquals(2008, b.opaInv().typeIndex());
        assertEquals(2008, b.opaInvj().typeIndex());
        assertEquals(2022, b.oDom().typeIndex());
        assertEquals(2023, b.oRange().typeIndex());
        assertEquals(2025, b.chain().typeIndex());
        assertEquals(2020, b.ref().typeIndex());
        assertEquals(2006, b.same().typeIndex());
        assertEquals(2035, b.subAnn().typeIndex());
        assertEquals(2002, b.subClass().typeIndex());
        assertEquals(2027, b.subData().typeIndex());
        assertEquals(2013, b.subObject().typeIndex());
        assertEquals(2033, b.rule().typeIndex());
        assertEquals(2017, b.symm().typeIndex());
        assertEquals(2019, b.trans().typeIndex());
        assertEquals(2032, b.hasKey().typeIndex());
        assertEquals(2033, b.bigRule().typeIndex());
        assertEquals(1, b.onto().typeIndex());
    }
}
