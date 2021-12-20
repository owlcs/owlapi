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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.DF;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.utility.OWLClassExpressionCollector;

class OWLClassExpressionCollectorTestCase extends TestBase {


    static Stream<Arguments> getData() {
        String CI = DF.CLASSES.C.getIRI().toQuotedString();
        String IRII = DF.IRIS.iri.toQuotedString();
        String dt = DF.DATATYPES.DT.getIRI().toQuotedString();
        String p = DF.DATAPROPS.DP.getIRI().toQuotedString();
        String op = DF.OBJPROPS.OP.getIRI().toQuotedString();
        String i = DF.INDIVIDUALS.I.getIRI().toQuotedString();
        Set<String> empty = set();
        Set<String> ci = TestBase.set(CI);
        Set<String> ciIrii = set(CI, IRII);
        return Stream.of(of(Builder.dRange, empty), of(Builder.dDef, empty), of(Builder.decC, ci),
            of(Builder.decOp, empty), of(Builder.decDp, empty), of(Builder.decDt, empty),
            of(Builder.decAp, empty), of(Builder.decI, empty), of(Builder.assDi, empty),
            of(Builder.dc, ciIrii), of(Builder.dDp, empty), of(Builder.dOp, empty),
            of(Builder.du, ciIrii), of(Builder.ec, ciIrii), of(Builder.eDp, empty),
            of(Builder.eOp, empty), of(Builder.fdp, empty), of(Builder.fop, empty),
            of(Builder.ifp, empty), of(Builder.iop, empty), of(Builder.irr, empty),
            of(Builder.ndp, empty), of(Builder.nop, empty), of(Builder.opa, empty),
            of(Builder.opaInv, empty), of(Builder.opaInvj, empty), of(Builder.oDom, ci),
            of(Builder.oRange, ci), of(Builder.chain, empty), of(Builder.ref, empty),
            of(Builder.same, empty), of(Builder.subAnn, empty),
            of(Builder.subClass, set("owl:Thing", CI)), of(Builder.subData, empty),
            of(Builder.subObject, empty), of(Builder.rule, empty), of(Builder.symm, empty),
            of(Builder.trans, empty), of(Builder.hasKey, ci), of(Builder.ann, empty),
            of(Builder.asymm, empty), of(Builder.annDom, empty), of(Builder.annRange, empty),
            of(Builder.ass, ci),
            of(Builder.assAnd, set(CI, IRII, "ObjectIntersectionOf(" + CI + " " + IRII + ")")),
            of(Builder.assOr, set(CI, IRII, "ObjectUnionOf(" + CI + " " + IRII + ")")),
            of(Builder.dRangeAnd, empty), of(Builder.dRangeOr, empty),
            of(Builder.assNot, set(CI, "ObjectComplementOf(" + CI + ")")),
            of(Builder.assNotAnon, set(CI, "ObjectComplementOf(" + CI + ")")),
            of(Builder.assSome, set(CI, "ObjectSomeValuesFrom(" + op + " " + CI + ")")),
            of(Builder.assAll, set(CI, "ObjectAllValuesFrom(" + op + " " + CI + ")")),
            of(Builder.assHas, set("ObjectHasValue(" + op + " " + i + ")")),
            of(Builder.assMin, set(CI, "ObjectMinCardinality(1 " + op + " " + CI + ")")),
            of(Builder.assMax, set(CI, "ObjectMaxCardinality(1 " + op + " " + CI + ")")),
            of(Builder.assEq, set(CI, "ObjectExactCardinality(1 " + op + " " + CI + ")")),
            of(Builder.assHasSelf, set("ObjectHasSelf(" + op + ")")),
            of(Builder.assOneOf, set("ObjectOneOf(" + i + ")")),
            of(Builder.assDSome, set("DataSomeValuesFrom(" + p + " " + dt + ")")),
            of(Builder.assDAll, set("DataAllValuesFrom(" + p + " " + dt + ")")),
            of(Builder.assDHas, set("DataHasValue(" + p + " \"false\"^^xsd:boolean)")),
            of(Builder.assDMin, set("DataMinCardinality(1 " + p + " " + dt + ")")),
            of(Builder.assDMax, set("DataMaxCardinality(1 " + p + " " + dt + ")")),
            of(Builder.assDEq, set("DataExactCardinality(1 " + p + " " + dt + ")")),
            of(Builder.dOneOf, empty), of(Builder.dNot, empty), of(Builder.dRangeRestrict, empty),
            of(Builder.assD, empty), of(Builder.assDPlain, empty), of(Builder.dDom, ci),
            of(Builder.bigRule, ci));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom ax, Set<String> expected) {
        OWLClassExpressionCollector testsubject = new OWLClassExpressionCollector();
        Collection<OWLClassExpression> components = ax.accept(testsubject);
        Set<String> strings = asUnorderedSet(components.stream().map(Object::toString));
        assertEquals(str(expected), str(components));
    }
}
