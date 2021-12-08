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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.api.test.baseclasses.TestBase.set;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;

class OWLClassExpressionCollectorTestCase {

    private static final String CI = "<http://www.semanticweb.org/owlapi/test#C>";
    private static final String IRII = "<http://www.semanticweb.org/owlapi/test#iri>";
    private static final String AND =
        "ObjectIntersectionOf(<http://www.semanticweb.org/owlapi/test#C> <http://www.semanticweb.org/owlapi/test#iri>)";
    private static final String OR =
        "ObjectUnionOf(<http://www.semanticweb.org/owlapi/test#C> <http://www.semanticweb.org/owlapi/test#iri>)";
    private static final String NOT =
        "ObjectComplementOf(<http://www.semanticweb.org/owlapi/test#C>)";
    private static final String SOME =
        "ObjectSomeValuesFrom(<http://example.com/objectProperty> <http://www.semanticweb.org/owlapi/test#C>)";
    private static final String ALL =
        "ObjectAllValuesFrom(<http://example.com/objectProperty> <http://www.semanticweb.org/owlapi/test#C>)";
    private static final String HAS =
        "ObjectHasValue(<http://example.com/objectProperty> <http://www.semanticweb.org/owlapi/test#i>)";
    private static final String OMIN =
        "ObjectMinCardinality(1 <http://example.com/objectProperty> <http://www.semanticweb.org/owlapi/test#C>)";
    private static final String MAX =
        "ObjectMaxCardinality(1 <http://example.com/objectProperty> <http://www.semanticweb.org/owlapi/test#C>)";
    private static final String OEQ =
        "ObjectExactCardinality(1 <http://example.com/objectProperty> <http://www.semanticweb.org/owlapi/test#C>)";
    private static final String SELF = "ObjectHasSelf(<http://example.com/objectProperty>)";
    private static final String ONE = "ObjectOneOf(<http://www.semanticweb.org/owlapi/test#i>)";
    private static final String DSOME =
        "DataSomeValuesFrom(<http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#DT>)";
    private static final String DALL =
        "DataAllValuesFrom(<http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#DT>)";
    private static final String DHAS =
        "DataHasValue(<http://www.semanticweb.org/owlapi/test#p> \"false\"^^xsd:boolean)";
    private static final String DMIN =
        "DataMinCardinality(1 <http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#DT>)";
    private static final String DMAX =
        "DataMaxCardinality(1 <http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#DT>)";
    private static final String DEQ =
        "DataExactCardinality(1 <http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#DT>)";
    private static final String THING = "owl:Thing";

    static Stream<Arguments> getData() {
        Builder b = new Builder();
        Set<String> empty = set();
        Set<String> ci = TestBase.set(CI);
        Set<String> ciIrii = set(CI, IRII);
        return Stream.of(Arguments.of(b.assSome(), set(CI, SOME)),
            Arguments.of(b.assAll(), set(CI, ALL)), Arguments.of(b.assHas(), set(HAS)),
            Arguments.of(b.assMin(), set(CI, OMIN)), Arguments.of(b.assMax(), set(CI, MAX)),
            Arguments.of(b.assEq(), set(CI, OEQ)), Arguments.of(b.assHasSelf(), set(SELF)),
            Arguments.of(b.assOneOf(), set(ONE)), Arguments.of(b.assDSome(), set(DSOME)),
            Arguments.of(b.assDAll(), set(DALL)), Arguments.of(b.assDHas(), set(DHAS)),
            Arguments.of(b.assDMin(), set(DMIN)), Arguments.of(b.assDMax(), set(DMAX)),
            Arguments.of(b.assDEq(), set(DEQ)), Arguments.of(b.dOneOf(), empty),
            Arguments.of(b.dNot(), empty), Arguments.of(b.dRangeRestrict(), empty),
            Arguments.of(b.assD(), empty), Arguments.of(b.assDPlain(), empty),
            Arguments.of(b.dDom(), ci), Arguments.of(b.bigRule(), ci),
            //
            Arguments.of(b.dRange(), empty), Arguments.of(b.dDef(), empty),
            Arguments.of(b.decC(), ci), Arguments.of(b.decOp(), empty),
            Arguments.of(b.decDp(), empty), Arguments.of(b.decDt(), empty),
            Arguments.of(b.decAp(), empty), Arguments.of(b.decI(), empty),
            Arguments.of(b.assDi(), empty), Arguments.of(b.dc(), ciIrii),
            Arguments.of(b.dDp(), empty), Arguments.of(b.dOp(), empty),
            Arguments.of(b.du(), ciIrii), Arguments.of(b.ec(), ciIrii),
            Arguments.of(b.eDp(), empty), Arguments.of(b.eOp(), empty),
            Arguments.of(b.fdp(), empty), Arguments.of(b.fop(), empty),
            Arguments.of(b.ifp(), empty), Arguments.of(b.iop(), empty),
            Arguments.of(b.irr(), empty), Arguments.of(b.ndp(), empty),
            Arguments.of(b.nop(), empty), Arguments.of(b.opa(), empty),
            Arguments.of(b.opaInv(), empty), Arguments.of(b.opaInvj(), empty),
            Arguments.of(b.oDom(), ci), Arguments.of(b.oRange(), ci),
            Arguments.of(b.chain(), empty), Arguments.of(b.ref(), empty),
            Arguments.of(b.same(), empty), Arguments.of(b.subAnn(), empty),
            Arguments.of(b.subClass(), set(THING, CI)), Arguments.of(b.subData(), empty),
            Arguments.of(b.subObject(), empty), Arguments.of(b.rule(), empty),
            Arguments.of(b.symm(), empty), Arguments.of(b.trans(), empty),
            Arguments.of(b.hasKey(), ci), Arguments.of(b.ann(), empty),
            Arguments.of(b.asymm(), empty), Arguments.of(b.annDom(), empty),
            Arguments.of(b.annRange(), empty), Arguments.of(b.ass(), ci),
            Arguments.of(b.assAnd(), set(CI, IRII, AND)),
            Arguments.of(b.assOr(), set(CI, IRII, OR)), Arguments.of(b.dRangeAnd(), empty),
            Arguments.of(b.dRangeOr(), empty), Arguments.of(b.assNot(), set(CI, NOT)),
            Arguments.of(b.assNotAnon(), set(CI, NOT)));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom object, Set<String> expected) {
        OWLClassExpressionCollector testsubject = new OWLClassExpressionCollector();
        Collection<OWLClassExpression> components = object.accept(testsubject);
        Set<String> strings = asUnorderedSet(components.stream().map(Object::toString));
        assertEquals(expected, strings);
    }
}
