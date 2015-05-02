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
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

import uk.ac.manchester.cs.owl.owlapi.*;

@SuppressWarnings({ "javadoc" })
public class OWLObjectTypeIndexProviderTestCase {

    Builder b = new Builder();
    OWLObjectTypeIndexProvider t = new OWLObjectTypeIndexProvider();

    @Test
    public void testAssertion1() {
        assertEquals(t.getTypeIndex(b.ann()), 2034);
    }

    @Test
    public void testAssertion2() {
        assertEquals(t.getTypeIndex(b.asymm()), 2018);
    }

    @Test
    public void testAssertion3() {
        assertEquals(t.getTypeIndex(b.annDom()), 2037);
    }

    @Test
    public void testAssertion4() {
        assertEquals(t.getTypeIndex(b.annRange()), 2036);
    }

    @Test
    public void testAssertion5() {
        assertEquals(t.getTypeIndex(b.ass()), 2005);
    }

    @Test
    public void testAssertion6() {
        assertEquals(t.getTypeIndex(b.assAnd()), 2005);
    }

    @Test
    public void testAssertion7() {
        assertEquals(t.getTypeIndex(b.assOr()), 2005);
    }

    @Test
    public void testAssertion8() {
        assertEquals(t.getTypeIndex(b.dRangeAnd()), 2030);
    }

    @Test
    public void testAssertion9() {
        assertEquals(t.getTypeIndex(b.dRangeOr()), 2030);
    }

    @Test
    public void testAssertion10() {
        assertEquals(t.getTypeIndex(b.assNot()), 2005);
    }

    @Test
    public void testAssertion11() {
        assertEquals(t.getTypeIndex(b.assNotAnon()), 2005);
    }

    @Test
    public void testAssertion12() {
        assertEquals(t.getTypeIndex(b.assSome()), 2005);
    }

    @Test
    public void testAssertion13() {
        assertEquals(t.getTypeIndex(b.assAll()), 2005);
    }

    @Test
    public void testAssertion14() {
        assertEquals(t.getTypeIndex(b.assHas()), 2005);
    }

    @Test
    public void testAssertion15() {
        assertEquals(t.getTypeIndex(b.assMin()), 2005);
    }

    @Test
    public void testAssertion16() {
        assertEquals(t.getTypeIndex(b.assMax()), 2005);
    }

    @Test
    public void testAssertion17() {
        assertEquals(t.getTypeIndex(b.assEq()), 2005);
    }

    @Test
    public void testAssertion18() {
        assertEquals(t.getTypeIndex(b.assHasSelf()), 2005);
    }

    @Test
    public void testAssertion19() {
        assertEquals(t.getTypeIndex(b.assOneOf()), 2005);
    }

    @Test
    public void testAssertion20() {
        assertEquals(t.getTypeIndex(b.assDSome()), 2005);
    }

    @Test
    public void testAssertion21() {
        assertEquals(t.getTypeIndex(b.assDAll()), 2005);
    }

    @Test
    public void testAssertion22() {
        assertEquals(t.getTypeIndex(b.assDHas()), 2005);
    }

    @Test
    public void testAssertion23() {
        assertEquals(t.getTypeIndex(b.assDMin()), 2005);
    }

    @Test
    public void testAssertion24() {
        assertEquals(t.getTypeIndex(b.assDMax()), 2005);
    }

    @Test
    public void testAssertion25() {
        assertEquals(t.getTypeIndex(b.assDEq()), 2005);
    }

    @Test
    public void testAssertion26() {
        assertEquals(t.getTypeIndex(b.dOneOf()), 2030);
    }

    @Test
    public void testAssertion27() {
        assertEquals(t.getTypeIndex(b.dNot()), 2030);
    }

    @Test
    public void testAssertion28() {
        assertEquals(t.getTypeIndex(b.dRangeRestrict()), 2030);
    }

    @Test
    public void testAssertion29() {
        assertEquals(t.getTypeIndex(b.assD()), 2010);
    }

    @Test
    public void testAssertion30() {
        assertEquals(t.getTypeIndex(b.assDPlain()), 2010);
    }

    @Test
    public void testAssertion31() {
        assertEquals(t.getTypeIndex(b.dDom()), 2029);
    }

    @Test
    public void testAssertion32() {
        assertEquals(t.getTypeIndex(b.dRange()), 2030);
    }

    @Test
    public void testAssertion33() {
        assertEquals(t.getTypeIndex(b.dDef()), 2038);
    }

    @Test
    public void testAssertion34() {
        assertEquals(t.getTypeIndex(b.decC()), 2000);
    }

    @Test
    public void testAssertion35() {
        assertEquals(t.getTypeIndex(b.decOp()), 2000);
    }

    @Test
    public void testAssertion36() {
        assertEquals(t.getTypeIndex(b.decDp()), 2000);
    }

    @Test
    public void testAssertion37() {
        assertEquals(t.getTypeIndex(b.decDt()), 2000);
    }

    @Test
    public void testAssertion38() {
        assertEquals(t.getTypeIndex(b.decAp()), 2000);
    }

    @Test
    public void testAssertion39() {
        assertEquals(t.getTypeIndex(b.decI()), 2000);
    }

    @Test
    public void testAssertion40() {
        assertEquals(t.getTypeIndex(b.assDi()), 2007);
    }

    @Test
    public void testAssertion41() {
        assertEquals(t.getTypeIndex(b.dc()), 2003);
    }

    @Test
    public void testAssertion42() {
        assertEquals(t.getTypeIndex(b.dDp()), 2031);
    }

    @Test
    public void testAssertion43() {
        assertEquals(t.getTypeIndex(b.dOp()), 2024);
    }

    @Test
    public void testAssertion44() {
        assertEquals(t.getTypeIndex(b.du()), 2004);
    }

    @Test
    public void testAssertion45() {
        assertEquals(t.getTypeIndex(b.ec()), 2001);
    }

    @Test
    public void testAssertion46() {
        assertEquals(t.getTypeIndex(b.eDp()), 2026);
    }

    @Test
    public void testAssertion47() {
        assertEquals(t.getTypeIndex(b.eOp()), 2012);
    }

    @Test
    public void testAssertion48() {
        assertEquals(t.getTypeIndex(b.fdp()), 2028);
    }

    @Test
    public void testAssertion49() {
        assertEquals(t.getTypeIndex(b.fop()), 2015);
    }

    @Test
    public void testAssertion50() {
        assertEquals(t.getTypeIndex(b.ifp()), 2016);
    }

    @Test
    public void testAssertion51() {
        assertEquals(t.getTypeIndex(b.iop()), 2014);
    }

    @Test
    public void testAssertion52() {
        assertEquals(t.getTypeIndex(b.irr()), 2021);
    }

    @Test
    public void testAssertion53() {
        assertEquals(t.getTypeIndex(b.ndp()), 2011);
    }

    @Test
    public void testAssertion54() {
        assertEquals(t.getTypeIndex(b.nop()), 2009);
    }

    @Test
    public void testAssertion55() {
        assertEquals(t.getTypeIndex(b.opa()), 2008);
    }

    @Test
    public void testAssertion56() {
        assertEquals(t.getTypeIndex(b.opaInv()), 2008);
    }

    @Test
    public void testAssertion57() {
        assertEquals(t.getTypeIndex(b.opaInvj()), 2008);
    }

    @Test
    public void testAssertion58() {
        assertEquals(t.getTypeIndex(b.oDom()), 2022);
    }

    @Test
    public void testAssertion59() {
        assertEquals(t.getTypeIndex(b.oRange()), 2023);
    }

    @Test
    public void testAssertion60() {
        assertEquals(t.getTypeIndex(b.chain()), 2025);
    }

    @Test
    public void testAssertion61() {
        assertEquals(t.getTypeIndex(b.ref()), 2020);
    }

    @Test
    public void testAssertion62() {
        assertEquals(t.getTypeIndex(b.same()), 2006);
    }

    @Test
    public void testAssertion63() {
        assertEquals(t.getTypeIndex(b.subAnn()), 2035);
    }

    @Test
    public void testAssertion64() {
        assertEquals(t.getTypeIndex(b.subClass()), 2002);
    }

    @Test
    public void testAssertion65() {
        assertEquals(t.getTypeIndex(b.subData()), 2027);
    }

    @Test
    public void testAssertion66() {
        assertEquals(t.getTypeIndex(b.subObject()), 2013);
    }

    @Test
    public void testAssertion67() {
        assertEquals(t.getTypeIndex(b.rule()), 2033);
    }

    @Test
    public void testAssertion68() {
        assertEquals(t.getTypeIndex(b.symm()), 2017);
    }

    @Test
    public void testAssertion69() {
        assertEquals(t.getTypeIndex(b.trans()), 2019);
    }

    @Test
    public void testAssertion70() {
        assertEquals(t.getTypeIndex(b.hasKey()), 2032);
    }

    @Test
    public void testAssertion71() {
        assertEquals(t.getTypeIndex(b.bigRule()), 2033);
    }

    @Test
    public void testAssertion72() {
        assertEquals(t.getTypeIndex(b.onto()), 1);
    }

    @Test
    public void testAssertion73() {
        OWLObjectIntersectionOfImpl mock = mock(
        OWLObjectIntersectionOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3001);
    }

    @Test
    public void testAssertion75() {
        OWLObjectUnionOfImpl mock = mock(OWLObjectUnionOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3002);
    }

    @Test
    public void testAssertion76() {
        OWLObjectComplementOfImpl mock = mock(OWLObjectComplementOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3003);
    }

    @Test
    public void testAssertion78() {
        OWLObjectOneOfImpl mock = mock(OWLObjectOneOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3004);
    }

    @Test
    public void testAssertion79() {
        OWLObjectSomeValuesFromImpl mock = mock(
        OWLObjectSomeValuesFromImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3005);
    }

    @Test
    public void testAssertion81() {
        OWLObjectAllValuesFromImpl mock = mock(
        OWLObjectAllValuesFromImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3006);
    }

    @Test
    public void testAssertion83() {
        OWLObjectHasValueImpl mock = mock(OWLObjectHasValueImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3007);
    }

    @Test
    public void testAssertion84() {
        OWLObjectMinCardinalityImpl mock = mock(
        OWLObjectMinCardinalityImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3008);
    }

    @Test
    public void testAssertion86() {
        OWLObjectExactCardinalityImpl mock = mock(
        OWLObjectExactCardinalityImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3009);
    }

    @Test
    public void testAssertion88() {
        OWLObjectMaxCardinalityImpl mock = mock(
        OWLObjectMaxCardinalityImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3010);
    }

    @Test
    public void testAssertion90() {
        OWLObjectHasSelfImpl mock = mock(OWLObjectHasSelfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3011);
    }

    @Test
    public void testAssertion91() {
        OWLDataSomeValuesFromImpl mock = mock(OWLDataSomeValuesFromImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3012);
    }

    @Test
    public void testAssertion93() {
        OWLDataAllValuesFromImpl mock = mock(OWLDataAllValuesFromImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3013);
    }

    @Test
    public void testAssertion94() {
        OWLDataHasValueImpl mock = mock(OWLDataHasValueImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3014);
    }

    @Test
    public void testAssertion95() {
        OWLDataMinCardinalityImpl mock = mock(OWLDataMinCardinalityImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3015);
    }

    @Test
    public void testAssertion97() {
        OWLDataExactCardinalityImpl mock = mock(
        OWLDataExactCardinalityImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3016);
    }

    @Test
    public void testAssertion99() {
        OWLDataMaxCardinalityImpl mock = mock(OWLDataMaxCardinalityImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 3017);
    }

    @Test
    public void testAssertion101() {
        OWLDataComplementOfImpl mock = mock(OWLDataComplementOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 4002);
    }

    @Test
    public void testAssertion102() {
        OWLDataOneOfImpl mock = mock(OWLDataOneOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 4003);
    }

    @Test
    public void testAssertion103() {
        OWLDataIntersectionOfImpl mock = mock(OWLDataIntersectionOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 4004);
    }

    @Test
    public void testAssertion105() {
        OWLDataUnionOfImpl mock = mock(OWLDataUnionOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 2005);
    }

    @Test
    public void testAssertion106() {
        OWLDatatypeRestrictionImpl mock = mock(
        OWLDatatypeRestrictionImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 4006);
    }

    @Test
    public void testAssertion108() {
        OWLFacetRestrictionImpl mock = mock(OWLFacetRestrictionImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 4007);
    }

    @Test
    public void testAssertion109() {
        OWLAnnotationImpl mock = mock(OWLAnnotationImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 5001);
    }

    @Test
    public void testAssertion110() {
        SWRLClassAtomImpl mock = mock(SWRLClassAtomImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6001);
    }

    @Test
    public void testAssertion111() {
        SWRLObjectPropertyAtomImpl mock = mock(
        SWRLObjectPropertyAtomImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6003);
    }

    @Test
    public void testAssertion113() {
        SWRLDataPropertyAtomImpl mock = mock(SWRLDataPropertyAtomImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6004);
    }

    @Test
    public void testAssertion114() {
        SWRLBuiltInAtomImpl mock = mock(SWRLBuiltInAtomImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6005);
    }

    @Test
    public void testAssertion115() {
        SWRLVariableImpl mock = mock(SWRLVariableImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6006);
    }

    @Test
    public void testAssertion116() {
        SWRLIndividualArgumentImpl mock = mock(
        SWRLIndividualArgumentImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6007);
    }

    @Test
    public void testAssertion118() {
        SWRLLiteralArgumentImpl mock = mock(SWRLLiteralArgumentImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6008);
    }

    @Test
    public void testAssertion119() {
        SWRLSameIndividualAtomImpl mock = mock(
        SWRLSameIndividualAtomImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6009);
    }

    @Test
    public void testAssertion74() {
        SWRLDifferentIndividualsAtomImpl mock = mock(
        SWRLDifferentIndividualsAtomImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6010);
    }

    @Test
    public void testAssertion77() {
        IRI mock = mock(IRI.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 0);
    }

    @Test
    public void testAssertion80() {
        OWLClassImpl mock = mock(OWLClassImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1001);
    }

    @Test
    public void testAssertion82() {
        OWLObjectPropertyImpl mock = mock(OWLObjectPropertyImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1002);
    }

    @Test
    public void testAssertion89() {
        OWLObjectInverseOfImpl mock = mock(OWLObjectInverseOfImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1003);
    }

    @Test
    public void testAssertion92() {
        OWLDataPropertyImpl mock = mock(OWLDataPropertyImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1004);
    }

    @Test
    public void testAssertion96() {
        OWLNamedIndividualImpl mock = mock(OWLNamedIndividualImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1005);
    }

    @Test
    public void testAssertion98() {
        OWLAnnotationPropertyImpl mock = mock(OWLAnnotationPropertyImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1006);
    }

    @Test
    public void testAssertion104() {
        OWLAnonymousIndividualImpl mock = mock(
        OWLAnonymousIndividualImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1007);
    }

    @Test
    public void testAssertion112() {
        OWLNamedIndividualImpl mock = mock(OWLNamedIndividualImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 1005);
    }

    @Test
    public void testAssertion117() {
        OWLDatatypeImpl mock = mock(OWLDatatypeImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 4001);
    }

    @Test
    public void testAssertion120() {
        OWLLiteralImpl mock = mock(OWLLiteralImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 4008);
    }

    @Test
    public void testAssertion100() {
        SWRLDataRangeAtomImpl mock = mock(SWRLDataRangeAtomImpl.class);
        t.visit(mock);
        assertEquals(t.getTypeIndex(mock), 6002);
    }
}
