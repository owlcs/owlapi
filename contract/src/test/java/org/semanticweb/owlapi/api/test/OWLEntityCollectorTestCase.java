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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.OWLEntityCollector;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class OWLEntityCollectorTestCase {

    protected OWLAxiom object;
    protected String expected;

    public OWLEntityCollectorTestCase(OWLAxiom object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Nonnull
    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String> map = new LinkedHashMap<>();
        map.put(b.dRange(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.dDef(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, http://www.w3.org/2001/XMLSchema#double, <urn:test#datatype>]");
        map.put(b.decC(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.decOp(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.decDp(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#dp>]");
        map.put(b.decDt(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>]");
        map.put(b.decAp(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.decI(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.assDi(), "[<urn:test#i>, <urn:test#iri>]");
        map.put(b.dc(), "[<urn:test#c>, <urn:test#iri>]");
        map.put(b.dDp(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#iri>, <urn:test#dp>]");
        map.put(b.dOp(),
            "[<urn:test#ann>, <urn:test#iri>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.du(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>, <urn:test#iri>]");
        map.put(b.ec(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>, <urn:test#iri>]");
        map.put(b.eDp(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#iri>, <urn:test#dp>]");
        map.put(b.eOp(),
            "[<urn:test#ann>, <urn:test#iri>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.fdp(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#dp>]");
        map.put(b.fop(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.ifp(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.iop(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.irr(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.ndp(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string, <urn:test#dp>]");
        map.put(b.nop(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.opa(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.opaInv(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.opaInvj(),
            "[<urn:test#j>, <urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.oDom(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.oRange(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.chain(),
            "[<urn:test#ann>, <urn:test#iri>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.ref(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.same(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#iri>]");
        map.put(b.subAnn(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, rdfs:label]");
        map.put(b.subClass(),
            "[<urn:test#ann>, owl:Thing, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.subData(), "[owl:topDataProperty, <urn:test#dp>]");
        map.put(b.subObject(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, owl:topObjectProperty]");
        map.put(b.rule(), "[]");
        map.put(b.symm(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.trans(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.hasKey(),
            "[<urn:test#ann>, <urn:test#iri>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>, <urn:test#dp>]");
        map.put(b.bigRule(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>, <urn:test#datatype>, <urn:test#iri>, <urn:test#dp>]");
        map.put(b.ann(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.asymm(),
            "[<urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.annDom(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.annRange(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.ass(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assAnd(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>, <urn:test#iri>]");
        map.put(b.assOr(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>, <urn:test#iri>]");
        map.put(b.dRangeAnd(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.dRangeOr(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.assNot(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assNotAnon(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assSome(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assAll(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assHas(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.assMin(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assMax(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assEq(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>]");
        map.put(b.assHasSelf(),
            "[<urn:test#i>, <urn:test#ann>, <urn:test#op>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.assOneOf(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string]");
        map.put(b.assDSome(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.assDAll(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.assDHas(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string, <urn:test#dp>]");
        map.put(b.assDMin(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.assDMax(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.assDEq(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#datatype>, <urn:test#dp>]");
        map.put(b.dOneOf(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string, <urn:test#dp>]");
        map.put(b.dNot(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string, <urn:test#dp>]");
        map.put(b.dRangeRestrict(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, http://www.w3.org/2001/XMLSchema#double, <urn:test#dp>]");
        map.put(b.assD(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#boolean, http://www.w3.org/2001/XMLSchema#string, <urn:test#dp>]");
        map.put(b.assDPlain(),
            "[<urn:test#i>, <urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral, <urn:test#dp>]");
        map.put(b.dDom(),
            "[<urn:test#ann>, http://www.w3.org/2001/XMLSchema#string, <urn:test#c>, <urn:test#dp>]");
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        Set<OWLEntity> sig = new HashSet<>();
        OWLEntityCollector testsubject = new OWLEntityCollector(sig);
        object.accept(testsubject);
        String result = sig.toString();
        assertEquals(expected, result);
    }
}
