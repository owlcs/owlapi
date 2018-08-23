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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.utility.DLExpressivityChecker;

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class DLExpressivityCheckerTestCase extends TestBase {

    private final OWLAxiom object;
    private final String expected;
    private String expectedStrict;

    public DLExpressivityCheckerTestCase(OWLAxiom object, String expected, String expectedStrict) {
        this.object = object;
        this.expected = expected;
        this.expectedStrict = expectedStrict;
    }

    @Parameterized.Parameters(name = "{index} {1} {0}")
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        return Arrays.asList(
        //@formatter:off
            new Object[] {b.dRange(),           "AL(D)",    "RRESTR(D)"},
            new Object[] {b.dDef(),             "AL",       ""},
            new Object[] {b.decC(),             "AL",       ""},
            new Object[] {b.decOp(),            "AL",       ""},
            new Object[] {b.decDp(),            "AL",       ""},
            new Object[] {b.decDt(),            "AL",       ""},
            new Object[] {b.decAp(),            "AL",       ""},
            new Object[] {b.decI(),             "AL",       ""},
            new Object[] {b.assDi(),            "ALCO",     "CUO"},
            new Object[] {b.dc(),               "ALC",      "C"},
            new Object[] {b.dDp(),              "AL(D)",    "(D)"},
            new Object[] {b.dOp(),              "ALR",      "R"},
            new Object[] {b.du(),               "ALC",      "CU"},
            new Object[] {b.ec(),               "AL",       ""},
            new Object[] {b.eDp(),              "ALH(D)",   "H(D)"},
            new Object[] {b.eOp(),              "ALH",      "H"},
            new Object[] {b.fdp(),              "ALF(D)",   "F(D)"},
            new Object[] {b.fop(),              "ALF",      "F"},
            new Object[] {b.ifp(),              "ALIF",     "IF"},
            new Object[] {b.iop(),              "ALI",      "I"},
            new Object[] {b.irr(),              "ALR",      "R"},
            new Object[] {b.ndp(),              "AL(D)",    "(D)"},
            new Object[] {b.nop(),              "AL",       ""},
            new Object[] {b.opa(),              "AL",       ""},
            new Object[] {b.opaInv(),           "ALI",      "I"},
            new Object[] {b.opaInvj(),          "ALI",      "I"},
            new Object[] {b.oDom(),             "AL",       "RRESTR"},
            new Object[] {b.oRange(),           "AL",       "RRESTR"},
            new Object[] {b.chain(),            "ALR",      "R"},
            new Object[] {b.ref(),              "ALR",      "R"},
            new Object[] {b.same(),             "ALO",      "O"},
            new Object[] {b.subAnn(),           "AL",       ""},
            new Object[] {b.subClass(),         "AL",       ""},
            new Object[] {b.subData(),          "ALH(D)",   "H(D)"},
            new Object[] {b.subObject(),        "ALH",      "H"},
            new Object[] {b.rule(),             "AL",       ""},
            new Object[] {b.symm(),             "ALI",      "I"},
            new Object[] {b.trans(),            "AL+",      "+"},
            new Object[] {b.hasKey(),           "AL",       ""},
            new Object[] {b.bigRule(),          "AL",       ""},
            new Object[] {b.ann(),              "AL",       ""},
            new Object[] {b.asymm(),            "ALR",      "R"},
            new Object[] {b.annDom(),           "AL",       ""},
            new Object[] {b.annRange(),         "AL",       ""},
            new Object[] {b.ass(),              "AL",       ""},
            new Object[] {b.assAnd(),           "AL",       "CINT"},
            new Object[] {b.assOr(),            "ALU",      "U"},
            new Object[] {b.dRangeAnd(),        "AL(D)",    "RRESTR(D)"},
            new Object[] {b.dRangeOr(),         "AL(D)",    "RRESTR(D)"},
            new Object[] {b.assNot(),           "ALC",      "C"},
            new Object[] {b.assNotAnon(),       "ALC",      "C"},
            new Object[] {b.assSome(),          "ALE",      "E"},
            new Object[] {b.assAll(),           "AL",       "UNIVRESTR"},
            new Object[] {b.assHas(),           "ALEO",     "EO"},
            new Object[] {b.assMin(),           "ALQ",      "Q"},
            new Object[] {b.assMax(),           "ALQ",      "Q"},
            new Object[] {b.assEq(),            "ALQ",      "Q"},
            new Object[] {b.assHasSelf(),       "ALR",      "R"},
            new Object[] {b.assOneOf(),         "ALUO",     "UO"},
            new Object[] {b.assDSome(),         "ALE(D)",   "E(D)"},
            new Object[] {b.assDAll(),          "AL(D)",    "(D)"},
            new Object[] {b.assDHas(),          "AL(D)",    "(D)"},
            new Object[] {b.assDMin(),          "ALQ(D)",   "Q(D)"},
            new Object[] {b.assDMax(),          "ALQ(D)",   "Q(D)"},
            new Object[] {b.assDEq(),           "ALQ(D)",   "Q(D)"},
            new Object[] {b.dOneOf(),           "AL(D)",    "RRESTR(D)"},
            new Object[] {b.dNot(),             "AL(D)",    "RRESTR(D)"},
            new Object[] {b.dRangeRestrict(),   "AL(D)",    "RRESTR(D)"},
            new Object[] {b.assD(),             "AL(D)",    "(D)"},
            new Object[] {b.assDPlain(),        "AL(D)",    "(D)"},
            new Object[] {b.dDom(),             "AL(D)",    "RRESTR(D)"}
            );
        //@formatter:on
    }

    @Test
    public void testAssertion() {
        DLExpressivityChecker testsubject = new DLExpressivityChecker(ont());
        assertEquals(expected, testsubject.getDescriptionLogicName());
        assertEquals(expectedStrict, testsubject.getDescriptionLogicName(false));
        System.out.println(
            "DLExpressivityCheckerTestCase.testAssertion() " + testsubject.getConstructs(false));
        for (DLExpressivityChecker.Construct c : DLExpressivityChecker.Construct.values()) {
            System.out.println(c + "\t" + testsubject.isBelow(c));
        }
    }

    public Set<OWLOntology> ont() {
        OWLOntology o = getOWLOntology();
        o.add(object);
        Set<OWLOntology> singleton = Collections.singleton(o);
        return singleton;
    }
}
