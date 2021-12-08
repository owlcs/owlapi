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
import java.util.List;
import java.util.Set;
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
        String ann = "<http://www.semanticweb.org/owlapi/test#ann>";
        String string = "http://www.w3.org/2001/XMLSchema#string";
        String datatype = "<http://www.semanticweb.org/owlapi/test#DT>";
        String dp = "<http://www.semanticweb.org/owlapi/test#p>";
        String iri = "<http://www.semanticweb.org/owlapi/test#iri>";
        String doubl = "http://www.w3.org/2001/XMLSchema#double";
        String ciri = "<http://www.semanticweb.org/owlapi/test#C>";
        String op = "<http://example.com/objectProperty>";
        String bool = "http://www.w3.org/2001/XMLSchema#boolean";
        String iIri = "<http://www.semanticweb.org/owlapi/test#i>";
        String j = "<http://www.semanticweb.org/owlapi/test#j>";
        String label = "rdfs:label";
        String thing = "owl:Thing";
        String topData = "owl:topDataProperty";
        String topObject = "owl:topObjectProperty";
        String diff = "owl:differentFrom";
        String same = "owl:sameAs";
        String lang = "http://www.w3.org/1999/02/22-rdf-syntax-ns#langString";

        return Stream.of(Arguments.of(b.dRange(), l(ann, string, datatype, dp)),
            Arguments.of(b.dDef(), l(ann, string, doubl, datatype)),
            Arguments.of(b.decC(), l(ann, string, ciri)),
            Arguments.of(b.decOp(), l(ann, op, string)),
            Arguments.of(b.decDp(), l(ann, string, dp)),
            Arguments.of(b.decDt(), l(ann, string, datatype)),
            Arguments.of(b.decAp(), l(ann, string)), Arguments.of(b.decI(), l(iIri, ann, string)),
            Arguments.of(b.assDi(), l(iIri, iri)), Arguments.of(b.dc(), l(ciri, iri)),
            Arguments.of(b.dDp(), l(ann, string, iri, dp)),
            Arguments.of(b.dOp(), l(ann, iri, op, string)),
            Arguments.of(b.du(), l(ann, string, ciri, iri)),
            Arguments.of(b.ec(), l(ann, string, ciri, iri)),
            Arguments.of(b.eDp(), l(ann, string, iri, dp)),
            Arguments.of(b.eOp(), l(ann, iri, op, string)),
            Arguments.of(b.fdp(), l(ann, string, dp)), Arguments.of(b.fop(), l(ann, op, string)),
            Arguments.of(b.ifp(), l(ann, op, string)), Arguments.of(b.iop(), l(ann, op, string)),
            Arguments.of(b.irr(), l(ann, op, string)),
            Arguments.of(b.ndp(), l(iIri, ann, bool, string, dp)),
            Arguments.of(b.nop(), l(iIri, ann, op, string)),
            Arguments.of(b.opa(), l(iIri, ann, op, string)),
            Arguments.of(b.opaInv(), l(iIri, ann, op, string)),
            Arguments.of(b.opaInvj(), l(j, iIri, ann, op, string)),
            Arguments.of(b.oDom(), l(ann, op, string, ciri)),
            Arguments.of(b.oRange(), l(ann, op, string, ciri)),
            Arguments.of(b.chain(), l(ann, iri, op, string)),
            Arguments.of(b.ref(), l(ann, op, string)),
            Arguments.of(b.same(), l(iIri, ann, string, iri)),
            Arguments.of(b.subAnn(), l(ann, string, label)),
            Arguments.of(b.subClass(), l(ann, thing, string, ciri)),
            Arguments.of(b.subData(), l(topData, dp)),
            Arguments.of(b.subObject(), l(ann, op, string, topObject)), Arguments.of(b.rule(), l()),
            Arguments.of(b.symm(), l(ann, op, string)), Arguments.of(b.trans(), l(ann, op, string)),
            Arguments.of(b.hasKey(), l(ann, iri, op, string, ciri, dp)),
            Arguments.of(b.bigRule(),
                l(iIri, ann, diff, bool, op, string, ciri, datatype, iri, same, dp)),
            Arguments.of(b.ann(), l(ann, bool, string)),
            Arguments.of(b.asymm(), l(ann, op, string)), Arguments.of(b.annDom(), l(ann, string)),
            Arguments.of(b.annRange(), l(ann, string)),
            Arguments.of(b.ass(), l(iIri, ann, string, ciri)),
            Arguments.of(b.assAnd(), l(iIri, ann, string, ciri, iri)),
            Arguments.of(b.assOr(), l(iIri, ann, string, ciri, iri)),
            Arguments.of(b.dRangeAnd(), l(ann, bool, string, datatype, dp)),
            Arguments.of(b.dRangeOr(), l(ann, bool, string, datatype, dp)),
            Arguments.of(b.assNot(), l(iIri, ann, string, ciri)),
            Arguments.of(b.assNotAnon(), l(ann, string, ciri)),
            Arguments.of(b.assSome(), l(iIri, ann, op, string, ciri)),
            Arguments.of(b.assAll(), l(iIri, ann, op, string, ciri)),
            Arguments.of(b.assHas(), l(iIri, ann, op, string)),
            Arguments.of(b.assMin(), l(iIri, ann, op, string, ciri)),
            Arguments.of(b.assMax(), l(iIri, ann, op, string, ciri)),
            Arguments.of(b.assEq(), l(iIri, ann, op, string, ciri)),
            Arguments.of(b.assHasSelf(), l(iIri, ann, op, string)),
            Arguments.of(b.assOneOf(), l(iIri, ann, string)),
            Arguments.of(b.assDSome(), l(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDAll(), l(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDHas(), l(iIri, ann, bool, string, dp)),
            Arguments.of(b.assDMin(), l(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDMax(), l(iIri, ann, string, datatype, dp)),
            Arguments.of(b.assDEq(), l(iIri, ann, string, datatype, dp)),
            Arguments.of(b.dOneOf(), l(ann, bool, string, dp)),
            Arguments.of(b.dNot(), l(ann, bool, string, dp)),
            Arguments.of(b.dRangeRestrict(), l(ann, string, doubl, dp)),
            Arguments.of(b.assD(), l(iIri, ann, bool, string, dp)),
            Arguments.of(b.assDPlain(), l(iIri, ann, lang, string, dp)),
            Arguments.of(b.dDom(), l(ann, string, ciri, dp)));
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAssertion(OWLAxiom ax, List<String> expected) {
        Set<OWLEntity> sig = new HashSet<>();
        OWLEntityCollector testsubject = new OWLEntityCollector(sig);
        ax.accept(testsubject);
        assertEquals(str(expected), str(sig));
    }
}
