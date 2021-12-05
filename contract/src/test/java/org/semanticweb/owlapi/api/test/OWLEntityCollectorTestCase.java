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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.OWLEntityCollector;

class OWLEntityCollectorTestCase extends TestBase {

    static Stream<Arguments> getData() {
        Builder b = new Builder();
        String ann = "<urn:test:test#ann>";
        String string = "http://www.w3.org/2001/XMLSchema#string";
        String datatype = "<urn:test:test#datatype>";
        String dp = "<urn:test:test#dp>";
        String iri = "<urn:test:test#iri>";
        String doubl = "http://www.w3.org/2001/XMLSchema#double";
        String ciri = "<urn:test:test#c>";
        String op = "<urn:test:test#op>";
        String bool = "http://www.w3.org/2001/XMLSchema#boolean";
        String iIri = "<urn:test:test#i>";
        String j = "<urn:test:test#j>";
        String label = "rdfs:label";
        String thing = "owl:Thing";
        String topData = "owl:topDataProperty";
        String topObject = "owl:topObjectProperty";
        String diff = "owl:differentFrom";
        String same = "owl:sameAs";
        String lang = "http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral";

        return Stream.of(Arguments.of(b.dRange(), set(ann, string, datatype, dp)),
            Arguments.of(b.dDef(), set(ann, string, doubl, datatype)),
            Arguments.of(b.decC(), set(ann, string, ciri)),
            Arguments.of(b.decOp(), set(ann, op, string)),
            Arguments.of(b.decDp(), set(ann, string, dp)),
            Arguments.of(b.decDt(), set(ann, string, datatype)),
            Arguments.of(b.decAp(), set(ann, string)),
            Arguments.of(b.decI(), set(iIri, ann, string)), Arguments.of(b.assDi(), set(iIri, iri)),
            Arguments.of(b.dc(), set(ciri, iri)), Arguments.of(b.dDp(), set(ann, string, iri, dp)),
            Arguments.of(b.dOp(), set(ann, iri, op, string)),
            Arguments.of(b.du(), set(ann, string, ciri, iri)),
            Arguments.of(b.ec(), set(ann, string, ciri, iri)),
            Arguments.of(b.eDp(), set(ann, string, iri, dp)),
            Arguments.of(b.eOp(), set(ann, iri, op, string)),
            Arguments.of(b.fdp(), set(ann, string, dp)),
            Arguments.of(b.fop(), set(ann, op, string)),
            Arguments.of(b.ifp(), set(ann, op, string)),
            Arguments.of(b.iop(), set(ann, op, string)),
            Arguments.of(b.irr(), set(ann, op, string)),
            Arguments.of(b.ndp(), set(iIri, ann, bool, string, dp)),
            Arguments.of(b.nop(), set(iIri, ann, op, string)),
            Arguments.of(b.opa(), set(iIri, ann, op, string)),
            Arguments.of(b.opaInv(), set(iIri, ann, op, string)),
            Arguments.of(b.opaInvj(), set(j, iIri, ann, op, string)),
            Arguments.of(b.oDom(), set(ann, op, string, ciri)),
            Arguments.of(b.oRange(), set(ann, op, string, ciri)),
            Arguments.of(b.chain(), set(ann, iri, op, string)),
            Arguments.of(b.ref(), set(ann, op, string)),
            Arguments.of(b.same(), set(iIri, ann, string, iri)),
            Arguments.of(b.subAnn(), set(ann, string, label)),
            Arguments.of(b.subClass(), set(ann, thing, string, ciri)),
            Arguments.of(b.subData(), set(topData, dp)),
            Arguments.of(b.subObject(), set(ann, op, string, topObject)),
            Arguments.of(b.rule(), set()), Arguments.of(b.symm(), set(ann, op, string)),
            Arguments.of(b.trans(), set(ann, op, string)),
            Arguments.of(b.hasKey(), set(ann, iri, op, string, ciri, dp)),
            Arguments.of(b.bigRule(),
                set(iIri, ann, diff, bool, op, string, ciri, datatype, iri, same, dp)),
            Arguments.of(b.ann(), set(ann, bool, string)),
            Arguments.of(b.asymm(), set(ann, op, string)),
            Arguments.of(b.annDom(), set(ann, string)),
            Arguments.of(b.annRange(), set(ann, string)),
            Arguments.of(b.ass(), set(iIri, ann, string, ciri)),
            Arguments.of(b.assAnd(), set(iIri, ann, string, ciri, iri)),
            Arguments.of(b.assOr(), set(iIri, ann, string, ciri, iri)),
            Arguments.of(b.dRangeAnd(), set(ann, bool, string, datatype, dp)),
            Arguments.of(b.dRangeOr(), set(ann, bool, string, datatype, dp)),
            Arguments.of(b.assNot(), set(iIri, ann, string, ciri)),
            Arguments.of(b.assNotAnon(), set(ann, string, ciri)),
            Arguments.of(b.assSome(), set(iIri, ann, op, string, ciri)),
            Arguments.of(b.assAll(), set(iIri, ann, op, string, ciri)),
            Arguments.of(b.assHas(), set(iIri, ann, op, string)),
            Arguments.of(b.assMin(), set(iIri, ann, op, string, ciri)),
            Arguments.of(b.assMax(), set(iIri, ann, op, string, ciri)),
            Arguments.of(b.assEq(), set(iIri, ann, op, string, ciri)),
            Arguments.of(b.assHasSelf(), set(iIri, ann, op, string)),
            Arguments.of(b.assOneOf(), set(iIri, ann, string)),
            Arguments.of(b.assDSome(), set(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDAll(), set(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDHas(), set(iIri, ann, bool, string, dp)),
            Arguments.of(b.assDMin(), set(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDMax(), set(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDEq(), set(iIri, ann, string, datatype, dp)),
            Arguments.of(b.dOneOf(), set(ann, bool, string, dp)),
            Arguments.of(b.dNot(), set(ann, bool, string, dp)),
            Arguments.of(b.dRangeRestrict(), set(ann, string, doubl, dp)),
            Arguments.of(b.assD(), set(iIri, ann, bool, string, dp)),
            Arguments.of(b.assDPlain(), set(iIri, ann, lang, string, dp)),
            Arguments.of(b.dDom(), set(ann, string, ciri, dp)));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom object, Set<String> expected) {
        Set<OWLEntity> sig = new HashSet<>();
        OWLEntityCollector testsubject = new OWLEntityCollector(sig);
        object.accept(testsubject);
        Set<String> result = sig.stream().map(Object::toString).collect(Collectors.toSet());
        assertEquals(expected, result);
    }
}
