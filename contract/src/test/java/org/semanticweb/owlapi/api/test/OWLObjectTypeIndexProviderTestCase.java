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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnonymousIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataExactCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataHasValueImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataMaxCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataMinCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataOneOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLFacetRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectExactCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectHasSelfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectHasValueImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectInverseOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectMaxCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectMinCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectOneOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLBuiltInAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLClassAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDataPropertyAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDataRangeAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDifferentIndividualsAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLIndividualArgumentImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLLiteralArgumentImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLObjectPropertyAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLSameIndividualAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLVariableImpl;

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
        map.put(b.onto(), 1);
        map.put(visitorMock(OWLObjectIntersectionOfImpl.class), 3001);
        map.put(visitorMock(OWLObjectUnionOfImpl.class), 3002);
        map.put(visitorMock(OWLObjectComplementOfImpl.class), 3003);
        map.put(visitorMock(OWLObjectOneOfImpl.class), 3004);
        map.put(visitorMock(OWLObjectSomeValuesFromImpl.class), 3005);
        map.put(visitorMock(OWLObjectAllValuesFromImpl.class), 3006);
        map.put(visitorMock(OWLObjectHasValueImpl.class), 3007);
        map.put(visitorMock(OWLObjectMinCardinalityImpl.class), 3008);
        map.put(visitorMock(OWLObjectExactCardinalityImpl.class), 3009);
        map.put(visitorMock(OWLObjectMaxCardinalityImpl.class), 3010);
        map.put(visitorMock(OWLObjectHasSelfImpl.class), 3011);
        map.put(visitorMock(OWLDataSomeValuesFromImpl.class), 3012);
        map.put(visitorMock(OWLDataAllValuesFromImpl.class), 3013);
        map.put(visitorMock(OWLDataHasValueImpl.class), 3014);
        map.put(visitorMock(OWLDataMinCardinalityImpl.class), 3015);
        map.put(visitorMock(OWLDataExactCardinalityImpl.class), 3016);
        map.put(visitorMock(OWLDataMaxCardinalityImpl.class), 3017);
        map.put(visitorMock(OWLDataComplementOfImpl.class), 4002);
        map.put(visitorMock(OWLDataOneOfImpl.class), 4003);
        map.put(visitorMock(OWLDataIntersectionOfImpl.class), 4004);
        map.put(visitorMock(OWLDataUnionOfImpl.class), 2005);
        map.put(visitorMock(OWLDatatypeRestrictionImpl.class), 4006);
        map.put(visitorMock(OWLFacetRestrictionImpl.class), 4007);
        map.put(visitorMock(OWLAnnotationImpl.class), 5001);
        map.put(visitorMock(SWRLClassAtomImpl.class), 6001);
        map.put(visitorMock(SWRLObjectPropertyAtomImpl.class), 6003);
        map.put(visitorMock(SWRLDataPropertyAtomImpl.class), 6004);
        map.put(visitorMock(SWRLBuiltInAtomImpl.class), 6005);
        map.put(visitorMock(SWRLVariableImpl.class), 6006);
        map.put(visitorMock(SWRLIndividualArgumentImpl.class), 6007);
        map.put(visitorMock(SWRLLiteralArgumentImpl.class), 6008);
        map.put(visitorMock(SWRLSameIndividualAtomImpl.class), 6009);
        map.put(visitorMock(SWRLDifferentIndividualsAtomImpl.class), 6010);
        map.put(visitorMock(IRI.class), 0);
        map.put(visitorMock(OWLClassImpl.class), 1001);
        map.put(visitorMock(OWLObjectPropertyImpl.class), 1002);
        map.put(visitorMock(OWLObjectInverseOfImpl.class), 1003);
        map.put(visitorMock(OWLDataPropertyImpl.class), 1004);
        map.put(visitorMock(OWLNamedIndividualImpl.class), 1005);
        map.put(visitorMock(OWLAnnotationPropertyImpl.class), 1006);
        map.put(visitorMock(OWLAnonymousIndividualImpl.class), 1007);
        map.put(visitorMock(OWLNamedIndividualImpl.class), 1005);
        map.put(visitorMock(OWLDatatypeImpl.class), 4001);
        map.put(visitorMock(OWLLiteralImpl.class), 4008);
        map.put(visitorMock(SWRLDataRangeAtomImpl.class), 6002);
        Collection<Object[]> toReturn = new ArrayList<>();
        for (Map.Entry<OWLObject, Integer> e : map.entrySet()) {
            toReturn.add(new Object[] { e.getKey(), e.getValue() });
        }
        return toReturn;
    }

    private static <T extends OWLObject> T visitorMock(Class<T> t) {
        T mock = mock(t);
        doCallRealMethod().when(mock).accept(any(OWLObjectVisitor.class));
        return mock;
    }

    @Test
    public void testAssertion() {
        OWLObjectTypeIndexProvider testsubject = new OWLObjectTypeIndexProvider();
        int i = testsubject.getTypeIndex(object);
        assertEquals(expected, i);
    }
}
