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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;

import com.google.common.collect.Sets;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class OWLClassExpressionCollectorTestCase {

    private static final String CI = "<urn:test#c>";
    private static final String IRII = "<urn:test#iri>";
    private static final String AND = "ObjectIntersectionOf(<urn:test#c> <urn:test#iri>)";
    private static final String OR = "ObjectUnionOf(<urn:test#c> <urn:test#iri>)";
    private static final String NOT = "ObjectComplementOf(<urn:test#c>)";
    private static final String SOME = "ObjectSomeValuesFrom(<urn:test#op> <urn:test#c>)";
    private static final String ALL = "ObjectAllValuesFrom(<urn:test#op> <urn:test#c>)";
    private static final String HAS = "ObjectHasValue(<urn:test#op> <urn:test#i>)";
    private static final String OMIN = "ObjectMinCardinality(1 <urn:test#op> <urn:test#c>)";
    private static final String MAX = "ObjectMaxCardinality(1 <urn:test#op> <urn:test#c>)";
    private static final String OEQ = "ObjectExactCardinality(1 <urn:test#op> <urn:test#c>)";
    private static final String SELF = "ObjectHasSelf(<urn:test#op>)";
    private static final String ONE = "ObjectOneOf(<urn:test#i>)";
    private static final String DSOME = "DataSomeValuesFrom(<urn:test#dp> <urn:test#datatype>)";
    private static final String DALL = "DataAllValuesFrom(<urn:test#dp> <urn:test#datatype>)";
    private static final String DHAS = "DataHasValue(<urn:test#dp> \"false\"^^xsd:boolean)";
    private static final String DMIN = "DataMinCardinality(1 <urn:test#dp> <urn:test#datatype>)";
    private static final String DMAX = "DataMaxCardinality(1 <urn:test#dp> <urn:test#datatype>)";
    private static final String DEQ = "DataExactCardinality(1 <urn:test#dp> <urn:test#datatype>)";
    private static final String THING = "owl:Thing";
    private OWLAxiom object;
    private Set<String> expected;

    public OWLClassExpressionCollectorTestCase(OWLAxiom object,
        String[] expected) {
        this.object = object;
        this.expected = Sets.newHashSet(expected);
    }

    @Nonnull
    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String[]> map = new LinkedHashMap<>();
        String[] empty = new String[] {};
        map.put(b.dRange(), empty);
        map.put(b.dDef(), empty);
        map.put(b.decC(), new String[] { CI });
        map.put(b.decOp(), empty);
        map.put(b.decDp(), empty);
        map.put(b.decDt(), empty);
        map.put(b.decAp(), empty);
        map.put(b.decI(), empty);
        map.put(b.assDi(), empty);
        map.put(b.dc(), new String[] { CI, IRII });
        map.put(b.dDp(), empty);
        map.put(b.dOp(), empty);
        map.put(b.du(), new String[] { CI, IRII });
        map.put(b.ec(), new String[] { CI, IRII });
        map.put(b.eDp(), empty);
        map.put(b.eOp(), empty);
        map.put(b.fdp(), empty);
        map.put(b.fop(), empty);
        map.put(b.ifp(), empty);
        map.put(b.iop(), empty);
        map.put(b.irr(), empty);
        map.put(b.ndp(), empty);
        map.put(b.nop(), empty);
        map.put(b.opa(), empty);
        map.put(b.opaInv(), empty);
        map.put(b.opaInvj(), empty);
        map.put(b.oDom(), new String[] { CI });
        map.put(b.oRange(), new String[] { CI });
        map.put(b.chain(), empty);
        map.put(b.ref(), empty);
        map.put(b.same(), empty);
        map.put(b.subAnn(), empty);
        map.put(b.subClass(), new String[] { THING, CI });
        map.put(b.subData(), empty);
        map.put(b.subObject(), empty);
        map.put(b.rule(), empty);
        map.put(b.symm(), empty);
        map.put(b.trans(), empty);
        map.put(b.hasKey(), new String[] { CI });
        map.put(b.ann(), empty);
        map.put(b.asymm(), empty);
        map.put(b.annDom(), empty);
        map.put(b.annRange(), empty);
        map.put(b.ass(), new String[] { CI });
        map.put(b.assAnd(), new String[] { CI, IRII, AND });
        map.put(b.assOr(), new String[] { CI, IRII, OR });
        map.put(b.dRangeAnd(), empty);
        map.put(b.dRangeOr(), empty);
        map.put(b.assNot(), new String[] { CI, NOT });
        map.put(b.assNotAnon(), new String[] { CI, NOT });
        map.put(b.assSome(), new String[] { CI, SOME });
        map.put(b.assAll(), new String[] { CI, ALL });
        map.put(b.assHas(), new String[] { HAS });
        map.put(b.assMin(), new String[] { CI, OMIN });
        map.put(b.assMax(), new String[] { CI, MAX });
        map.put(b.assEq(), new String[] { CI, OEQ });
        map.put(b.assHasSelf(), new String[] { SELF });
        map.put(b.assOneOf(), new String[] { ONE });
        map.put(b.assDSome(), new String[] { DSOME });
        map.put(b.assDAll(), new String[] { DALL });
        map.put(b.assDHas(), new String[] { DHAS });
        map.put(b.assDMin(), new String[] { DMIN });
        map.put(b.assDMax(), new String[] { DMAX });
        map.put(b.assDEq(), new String[] { DEQ });
        map.put(b.dOneOf(), empty);
        map.put(b.dNot(), empty);
        map.put(b.dRangeRestrict(), empty);
        map.put(b.assD(), empty);
        map.put(b.assDPlain(), empty);
        map.put(b.dDom(), new String[] { CI });
        map.put(b.bigRule(), new String[] { CI });
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        OWLClassExpressionCollector testsubject = new OWLClassExpressionCollector();
        Set<OWLClassExpression> components = object.accept(testsubject);
        Set<String> strings = asSet(components.stream().map(c -> c.toString()));
        assertEquals(expected, strings);
    }
}
