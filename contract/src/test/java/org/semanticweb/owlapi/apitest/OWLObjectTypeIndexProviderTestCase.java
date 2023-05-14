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

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.baseclasses.Builder;
import org.semanticweb.owlapi.model.OWLObject;

class OWLObjectTypeIndexProviderTestCase {

    @ParameterizedTest
    @MethodSource("values")
    void equal(int i, OWLObject o) {
        assertEquals(i, o.typeIndex());
    }

    static Stream<Arguments> values() {
        return Stream.of(Arguments.of(Integer.valueOf(2034), Builder.ann),
            Arguments.of(Integer.valueOf(2018), Builder.asymm),
            Arguments.of(Integer.valueOf(2037), Builder.annDom),
            Arguments.of(Integer.valueOf(2036), Builder.annRange),
            Arguments.of(Integer.valueOf(2005), Builder.ass),
            Arguments.of(Integer.valueOf(2005), Builder.assAnd),
            Arguments.of(Integer.valueOf(2005), Builder.assOr),
            Arguments.of(Integer.valueOf(2030), Builder.dRangeAnd),
            Arguments.of(Integer.valueOf(2030), Builder.dRangeOr),
            Arguments.of(Integer.valueOf(2005), Builder.assNot),
            Arguments.of(Integer.valueOf(2005), Builder.assNotAnon),
            Arguments.of(Integer.valueOf(2005), Builder.assSome),
            Arguments.of(Integer.valueOf(2005), Builder.assAll),
            Arguments.of(Integer.valueOf(2005), Builder.assHas),
            Arguments.of(Integer.valueOf(2005), Builder.assMin),
            Arguments.of(Integer.valueOf(2005), Builder.assMax),
            Arguments.of(Integer.valueOf(2005), Builder.assEq),
            Arguments.of(Integer.valueOf(2005), Builder.assHasSelf),
            Arguments.of(Integer.valueOf(2005), Builder.assOneOf),
            Arguments.of(Integer.valueOf(2005), Builder.assDSome),
            Arguments.of(Integer.valueOf(2005), Builder.assDAll),
            Arguments.of(Integer.valueOf(2005), Builder.assDHas),
            Arguments.of(Integer.valueOf(2005), Builder.assDMin),
            Arguments.of(Integer.valueOf(2005), Builder.assDMax),
            Arguments.of(Integer.valueOf(2005), Builder.assDEq),
            Arguments.of(Integer.valueOf(2030), Builder.dOneOf),
            Arguments.of(Integer.valueOf(2030), Builder.dNot),
            Arguments.of(Integer.valueOf(2030), Builder.dRangeRestrict),
            Arguments.of(Integer.valueOf(2010), Builder.assD),
            Arguments.of(Integer.valueOf(2010), Builder.assDPlain),
            Arguments.of(Integer.valueOf(2029), Builder.dDom),
            Arguments.of(Integer.valueOf(2030), Builder.dRange),
            Arguments.of(Integer.valueOf(2038), Builder.dDef),
            Arguments.of(Integer.valueOf(2000), Builder.decC),
            Arguments.of(Integer.valueOf(2000), Builder.decOp),
            Arguments.of(Integer.valueOf(2000), Builder.decDp),
            Arguments.of(Integer.valueOf(2000), Builder.decDt),
            Arguments.of(Integer.valueOf(2000), Builder.decAp),
            Arguments.of(Integer.valueOf(2000), Builder.decI),
            Arguments.of(Integer.valueOf(2007), Builder.assDi),
            Arguments.of(Integer.valueOf(2003), Builder.dc),
            Arguments.of(Integer.valueOf(2031), Builder.dDp),
            Arguments.of(Integer.valueOf(2024), Builder.dOp),
            Arguments.of(Integer.valueOf(2004), Builder.du),
            Arguments.of(Integer.valueOf(2001), Builder.ec),
            Arguments.of(Integer.valueOf(2026), Builder.eDp),
            Arguments.of(Integer.valueOf(2012), Builder.eOp),
            Arguments.of(Integer.valueOf(2028), Builder.fdp),
            Arguments.of(Integer.valueOf(2015), Builder.fop),
            Arguments.of(Integer.valueOf(2016), Builder.ifp),
            Arguments.of(Integer.valueOf(2014), Builder.iop),
            Arguments.of(Integer.valueOf(2021), Builder.irr),
            Arguments.of(Integer.valueOf(2011), Builder.ndp),
            Arguments.of(Integer.valueOf(2009), Builder.nop),
            Arguments.of(Integer.valueOf(2008), Builder.opa),
            Arguments.of(Integer.valueOf(2008), Builder.opaInv),
            Arguments.of(Integer.valueOf(2008), Builder.opaInvj),
            Arguments.of(Integer.valueOf(2022), Builder.oDom),
            Arguments.of(Integer.valueOf(2023), Builder.oRange),
            Arguments.of(Integer.valueOf(2025), Builder.chain),
            Arguments.of(Integer.valueOf(2020), Builder.ref),
            Arguments.of(Integer.valueOf(2006), Builder.same),
            Arguments.of(Integer.valueOf(2035), Builder.subAnn),
            Arguments.of(Integer.valueOf(2002), Builder.subClass),
            Arguments.of(Integer.valueOf(2027), Builder.subData),
            Arguments.of(Integer.valueOf(2013), Builder.subObject),
            Arguments.of(Integer.valueOf(2033), Builder.rule),
            Arguments.of(Integer.valueOf(2017), Builder.symm),
            Arguments.of(Integer.valueOf(2019), Builder.trans),
            Arguments.of(Integer.valueOf(2032), Builder.hasKey),
            Arguments.of(Integer.valueOf(2033), Builder.bigRule),
            Arguments.of(Integer.valueOf(1), Builder.onto));
    }
}
