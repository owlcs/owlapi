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
import static org.semanticweb.owlapi6.apitest.TestFiles.bool;
import static org.semanticweb.owlapi6.apitest.TestFiles.doubl;
import static org.semanticweb.owlapi6.apitest.TestFiles.lang;
import static org.semanticweb.owlapi6.apitest.TestFiles.string;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.DF;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.utility.OWLEntityCollector;

class OWLEntityCollectorTestCase extends TestBase {

    static Stream<Arguments> getData() {
        String ann = DF.ANNPROPS.ap.getIRI().toQuotedString();
        String datatype = DF.DATATYPES.DT.getIRI().toQuotedString();
        String dp = DF.DATAPROPS.DP.getIRI().toQuotedString();
        String iri = DF.IRIS.iri.toQuotedString();
        String ciri = DF.CLASSES.C.getIRI().toQuotedString();
        String op = DF.OBJPROPS.OP.getIRI().toQuotedString();
        String iIri = DF.INDIVIDUALS.I.getIRI().toQuotedString();
        String j = DF.INDIVIDUALS.J.getIRI().toQuotedString();

        return Stream.of(of(Builder.dRange, l(ann, string, datatype, dp)),
            of(Builder.dDef, l(ann, string, doubl, datatype)),
            of(Builder.decC, l(ann, string, ciri)), of(Builder.decOp, l(ann, op, string)),
            of(Builder.decDp, l(ann, string, dp)), of(Builder.decDt, l(ann, string, datatype)),
            of(Builder.decAp, l(ann, string)), of(Builder.decI, l(iIri, ann, string)),
            of(Builder.assDi, l(iIri, iri)), of(Builder.dc, l(ciri, iri)),
            of(Builder.dDp, l(ann, string, iri, dp)), of(Builder.dOp, l(ann, iri, op, string)),
            of(Builder.du, l(ann, string, ciri, iri)), of(Builder.ec, l(ann, string, ciri, iri)),
            of(Builder.eDp, l(ann, string, iri, dp)), of(Builder.eOp, l(ann, iri, op, string)),
            of(Builder.fdp, l(ann, string, dp)), of(Builder.fop, l(ann, op, string)),
            of(Builder.ifp, l(ann, op, string)), of(Builder.iop, l(ann, op, string)),
            of(Builder.irr, l(ann, op, string)), of(Builder.ndp, l(iIri, ann, bool, string, dp)),
            of(Builder.nop, l(iIri, ann, op, string)), of(Builder.opa, l(iIri, ann, op, string)),
            of(Builder.opaInv, l(iIri, ann, op, string)),
            of(Builder.opaInvj, l(j, iIri, ann, op, string)),
            of(Builder.oDom, l(ann, op, string, ciri)),
            of(Builder.oRange, l(ann, op, string, ciri)),
            of(Builder.chain, l(ann, iri, op, string)), of(Builder.ref, l(ann, op, string)),
            of(Builder.same, l(iIri, ann, string, iri)),
            of(Builder.subAnn, l(ann, string, "rdfs:label")),
            of(Builder.subClass, l(ann, "owl:Thing", string, ciri)),
            of(Builder.subData, l("owl:topDataProperty", dp)),
            of(Builder.subObject, l(ann, op, string, "owl:topObjectProperty")),
            of(Builder.rule, l()), of(Builder.symm, l(ann, op, string)),
            of(Builder.trans, l(ann, op, string)),
            of(Builder.hasKey, l(ann, iri, op, string, ciri, dp)),
            of(Builder.bigRule,
                l(iIri, ann, "owl:differentFrom", bool, op, string, ciri, datatype, iri,
                    "owl:sameAs", dp)),
            of(Builder.ann, l(ann, bool, string)), of(Builder.asymm, l(ann, op, string)),
            of(Builder.annDom, l(ann, string)), of(Builder.annRange, l(ann, string)),
            of(Builder.ass, l(iIri, ann, string, ciri)),
            of(Builder.assAnd, l(iIri, ann, string, ciri, iri)),
            of(Builder.assOr, l(iIri, ann, string, ciri, iri)),
            of(Builder.dRangeAnd, l(ann, bool, string, datatype, dp)),
            of(Builder.dRangeOr, l(ann, bool, string, datatype, dp)),
            of(Builder.assNot, l(iIri, ann, string, ciri)),
            of(Builder.assNotAnon, l(ann, string, ciri)),
            of(Builder.assSome, l(iIri, ann, op, string, ciri)),
            of(Builder.assAll, l(iIri, ann, op, string, ciri)),
            of(Builder.assHas, l(iIri, ann, op, string)),
            of(Builder.assMin, l(iIri, ann, op, string, ciri)),
            of(Builder.assMax, l(iIri, ann, op, string, ciri)),
            of(Builder.assEq, l(iIri, ann, op, string, ciri)),
            of(Builder.assHasSelf, l(iIri, ann, op, string)),
            of(Builder.assOneOf, l(iIri, ann, string)),
            of(Builder.assDSome, l(iIri, ann, string, datatype, dp)),
            of(Builder.assDAll, l(iIri, ann, string, datatype, dp)),
            of(Builder.assDHas, l(iIri, ann, bool, string, dp)),
            of(Builder.assDMin, l(iIri, ann, string, datatype, dp)),
            of(Builder.assDMax, l(iIri, ann, string, datatype, dp)),
            of(Builder.assDEq, l(iIri, ann, string, datatype, dp)),
            of(Builder.dOneOf, l(ann, bool, string, dp)),
            of(Builder.dNot, l(ann, bool, string, dp)),
            of(Builder.dRangeRestrict, l(ann, string, doubl, dp)),
            of(Builder.assD, l(iIri, ann, bool, string, dp)),
            of(Builder.assDPlain, l(iIri, ann, lang, string, dp)),
            of(Builder.dDom, l(ann, string, ciri, dp)));
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
