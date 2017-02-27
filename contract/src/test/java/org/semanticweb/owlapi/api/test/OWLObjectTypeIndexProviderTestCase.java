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

import org.junit.Test;

@SuppressWarnings({"javadoc"})
public class OWLObjectTypeIndexProviderTestCase {

    Builder b = new Builder();

    @Test
    public void testAssertions() {
        assertEquals(b.ann().typeIndex(), 2034);
        assertEquals(b.asymm().typeIndex(), 2018);
        assertEquals(b.annDom().typeIndex(), 2037);
        assertEquals(b.annRange().typeIndex(), 2036);
        assertEquals(b.ass().typeIndex(), 2005);
        assertEquals(b.assAnd().typeIndex(), 2005);
        assertEquals(b.assOr().typeIndex(), 2005);
        assertEquals(b.dRangeAnd().typeIndex(), 2030);
        assertEquals(b.dRangeOr().typeIndex(), 2030);
        assertEquals(b.assNot().typeIndex(), 2005);
        assertEquals(b.assNotAnon().typeIndex(), 2005);
        assertEquals(b.assSome().typeIndex(), 2005);
        assertEquals(b.assAll().typeIndex(), 2005);
        assertEquals(b.assHas().typeIndex(), 2005);
        assertEquals(b.assMin().typeIndex(), 2005);
        assertEquals(b.assMax().typeIndex(), 2005);
        assertEquals(b.assEq().typeIndex(), 2005);
        assertEquals(b.assHasSelf().typeIndex(), 2005);
        assertEquals(b.assOneOf().typeIndex(), 2005);
        assertEquals(b.assDSome().typeIndex(), 2005);
        assertEquals(b.assDAll().typeIndex(), 2005);
        assertEquals(b.assDHas().typeIndex(), 2005);
        assertEquals(b.assDMin().typeIndex(), 2005);
        assertEquals(b.assDMax().typeIndex(), 2005);
        assertEquals(b.assDEq().typeIndex(), 2005);
        assertEquals(b.dOneOf().typeIndex(), 2030);
        assertEquals(b.dNot().typeIndex(), 2030);
        assertEquals(b.dRangeRestrict().typeIndex(), 2030);
        assertEquals(b.assD().typeIndex(), 2010);
        assertEquals(b.assDPlain().typeIndex(), 2010);
        assertEquals(b.dDom().typeIndex(), 2029);
        assertEquals(b.dRange().typeIndex(), 2030);
        assertEquals(b.dDef().typeIndex(), 2038);
        assertEquals(b.decC().typeIndex(), 2000);
        assertEquals(b.decOp().typeIndex(), 2000);
        assertEquals(b.decDp().typeIndex(), 2000);
        assertEquals(b.decDt().typeIndex(), 2000);
        assertEquals(b.decAp().typeIndex(), 2000);
        assertEquals(b.decI().typeIndex(), 2000);
        assertEquals(b.assDi().typeIndex(), 2007);
        assertEquals(b.dc().typeIndex(), 2003);
        assertEquals(b.dDp().typeIndex(), 2031);
        assertEquals(b.dOp().typeIndex(), 2024);
        assertEquals(b.du().typeIndex(), 2004);
        assertEquals(b.ec().typeIndex(), 2001);
        assertEquals(b.eDp().typeIndex(), 2026);
        assertEquals(b.eOp().typeIndex(), 2012);
        assertEquals(b.fdp().typeIndex(), 2028);
        assertEquals(b.fop().typeIndex(), 2015);
        assertEquals(b.ifp().typeIndex(), 2016);
        assertEquals(b.iop().typeIndex(), 2014);
        assertEquals(b.irr().typeIndex(), 2021);
        assertEquals(b.ndp().typeIndex(), 2011);
        assertEquals(b.nop().typeIndex(), 2009);
        assertEquals(b.opa().typeIndex(), 2008);
        assertEquals(b.opaInv().typeIndex(), 2008);
        assertEquals(b.opaInvj().typeIndex(), 2008);
        assertEquals(b.oDom().typeIndex(), 2022);
        assertEquals(b.oRange().typeIndex(), 2023);
        assertEquals(b.chain().typeIndex(), 2025);
        assertEquals(b.ref().typeIndex(), 2020);
        assertEquals(b.same().typeIndex(), 2006);
        assertEquals(b.subAnn().typeIndex(), 2035);
        assertEquals(b.subClass().typeIndex(), 2002);
        assertEquals(b.subData().typeIndex(), 2027);
        assertEquals(b.subObject().typeIndex(), 2013);
        assertEquals(b.rule().typeIndex(), 2033);
        assertEquals(b.symm().typeIndex(), 2017);
        assertEquals(b.trans().typeIndex(), 2019);
        assertEquals(b.hasKey().typeIndex(), 2032);
        assertEquals(b.bigRule().typeIndex(), 2033);
        assertEquals(b.onto().typeIndex(), 1);
    }
}
