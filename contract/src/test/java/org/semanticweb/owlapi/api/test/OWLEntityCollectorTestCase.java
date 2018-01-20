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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.OWLEntityCollector;

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class OWLEntityCollectorTestCase {

    protected OWLAxiom object;
    protected List<String> expected;

    public OWLEntityCollectorTestCase(OWLAxiom object, String[] expected) {
        this.object = object;
        this.expected = new ArrayList<>(Arrays.asList(expected));
        this.expected.sort(null);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String[]> map = new LinkedHashMap<>();
        String ann = "<urn:test:test#ann>";
        String string = "http://www.w3.org/2001/XMLSchema#string";
        String datatype = "<urn:test:test#datatype>";
        String dp = "<urn:test:test#dp>";
        String iri = "<urn:test:test#iri>";
        String doubl = "http://www.w3.org/2001/XMLSchema#double";
        String c = "<urn:test:test#c>";
        String op = "<urn:test:test#op>";
        String bool = "http://www.w3.org/2001/XMLSchema#boolean";
        String i = "<urn:test:test#i>";
        String j = "<urn:test:test#j>";
        String label = "rdfs:label";
        String thing = "owl:Thing";
        String topData = "owl:topDataProperty";
        String topObject = "owl:topObjectProperty";
        String diff = "owl:differentFrom";
        String same = "owl:sameAs";
        String lang = "http://www.w3.org/1999/02/22-rdf-syntax-ns#langString";


        map.put(b.dRange(), new String[] {ann, string, datatype, dp});
        map.put(b.dDef(), new String[] {ann, string, doubl, datatype});
        map.put(b.decC(), new String[] {ann, string, c});
        map.put(b.decOp(), new String[] {ann, op, string});
        map.put(b.decDp(), new String[] {ann, string, dp});
        map.put(b.decDt(), new String[] {ann, string, datatype});
        map.put(b.decAp(), new String[] {ann, string});
        map.put(b.decI(), new String[] {i, ann, string});
        map.put(b.assDi(), new String[] {i, iri});
        map.put(b.dc(), new String[] {c, iri});
        map.put(b.dDp(), new String[] {ann, string, iri, dp});
        map.put(b.dOp(), new String[] {ann, iri, op, string});
        map.put(b.du(), new String[] {ann, string, c, iri});
        map.put(b.ec(), new String[] {ann, string, c, iri});
        map.put(b.eDp(), new String[] {ann, string, iri, dp});
        map.put(b.eOp(), new String[] {ann, iri, op, string});
        map.put(b.fdp(), new String[] {ann, string, dp});
        map.put(b.fop(), new String[] {ann, op, string});
        map.put(b.ifp(), new String[] {ann, op, string});
        map.put(b.iop(), new String[] {ann, op, string});
        map.put(b.irr(), new String[] {ann, op, string});
        map.put(b.ndp(), new String[] {i, ann, bool, string, dp});
        map.put(b.nop(), new String[] {i, ann, op, string});
        map.put(b.opa(), new String[] {i, ann, op, string});
        map.put(b.opaInv(), new String[] {i, ann, op, string});
        map.put(b.opaInvj(), new String[] {j, i, ann, op, string});
        map.put(b.oDom(), new String[] {ann, op, string, c});
        map.put(b.oRange(), new String[] {ann, op, string, c});
        map.put(b.chain(), new String[] {ann, iri, op, string});
        map.put(b.ref(), new String[] {ann, op, string});
        map.put(b.same(), new String[] {i, ann, string, iri});
        map.put(b.subAnn(), new String[] {ann, string, label});
        map.put(b.subClass(), new String[] {ann, thing, string, c});
        map.put(b.subData(), new String[] {topData, dp});
        map.put(b.subObject(), new String[] {ann, op, string, topObject});
        map.put(b.rule(), new String[] {""});
        map.put(b.symm(), new String[] {ann, op, string});
        map.put(b.trans(), new String[] {ann, op, string});
        map.put(b.hasKey(), new String[] {ann, iri, op, string, c, dp});
        map.put(b.bigRule(),
            new String[] {i, ann, diff, bool, op, string, c, datatype, iri, same, dp});
        map.put(b.ann(), new String[] {ann, bool, string});
        map.put(b.asymm(), new String[] {ann, op, string});
        map.put(b.annDom(), new String[] {ann, string});
        map.put(b.annRange(), new String[] {ann, string});
        map.put(b.ass(), new String[] {i, ann, string, c});
        map.put(b.assAnd(), new String[] {i, ann, string, c, iri});
        map.put(b.assOr(), new String[] {i, ann, string, c, iri});
        map.put(b.dRangeAnd(), new String[] {ann, bool, string, datatype, dp});
        map.put(b.dRangeOr(), new String[] {ann, bool, string, datatype, dp});
        map.put(b.assNot(), new String[] {i, ann, string, c});
        map.put(b.assNotAnon(), new String[] {ann, string, c});
        map.put(b.assSome(), new String[] {i, ann, op, string, c});
        map.put(b.assAll(), new String[] {i, ann, op, string, c});
        map.put(b.assHas(), new String[] {i, ann, op, string});
        map.put(b.assMin(), new String[] {i, ann, op, string, c});
        map.put(b.assMax(), new String[] {i, ann, op, string, c});
        map.put(b.assEq(), new String[] {i, ann, op, string, c});
        map.put(b.assHasSelf(), new String[] {i, ann, op, string});
        map.put(b.assOneOf(), new String[] {i, ann, string});
        map.put(b.assDSome(), new String[] {i, ann, string, datatype, dp});
        map.put(b.assDAll(), new String[] {i, ann, string, datatype, dp});
        map.put(b.assDHas(), new String[] {i, ann, bool, string, dp});
        map.put(b.assDMin(), new String[] {i, ann, string, datatype, dp});
        map.put(b.assDMax(), new String[] {i, ann, string, datatype, dp});
        map.put(b.assDEq(), new String[] {i, ann, string, datatype, dp});
        map.put(b.dOneOf(), new String[] {ann, bool, string, dp});
        map.put(b.dNot(), new String[] {ann, bool, string, dp});
        map.put(b.dRangeRestrict(), new String[] {ann, string, doubl, dp});
        map.put(b.assD(), new String[] {i, ann, bool, string, dp});
        map.put(b.assDPlain(), new String[] {i, ann, lang, string, dp});
        map.put(b.dDom(), new String[] {ann, string, c, dp});
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] {k, v}));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        List<OWLEntity> sig = new ArrayList<>();
        OWLEntityCollector testsubject = new OWLEntityCollector(sig);
        object.accept(testsubject);
        List<String> result = asList(sig.stream().map(p -> p.toString()).distinct().sorted());
        assertEquals(expected.toString(), result.toString());
    }
}
