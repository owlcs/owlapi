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
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.SimpleRenderer;

@SuppressWarnings({"javadoc"})
@RunWith(Parameterized.class)
public class VisitorsTestCase {

    private final OWLObject object;
    private final String expected;

    public VisitorsTestCase(OWLObject object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLObject, String> map = new LinkedHashMap<>();
        map.put(b.rule(),
            "DLSafeRule( Body(BuiltInAtom(<urn:swrl:var#v1> Variable(<urn:swrl:var#var3>) Variable(<urn:swrl:var#var4>) )) Head(BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>) )) )");
        map.put(b.bigRule(),
            "DLSafeRule(Annotation(<urn:test:test#ann> \"test\"^^xsd:string)  Body(BuiltInAtom(<urn:swrl:var#v1> Variable(<urn:swrl:var#var3>) Variable(<urn:swrl:var#var4>) ) ClassAtom(<urn:test:test#c> Variable(<urn:swrl:var#var2>)) DataRangeAtom(<urn:test:test#datatype> Variable(<urn:swrl:var#var1>)) BuiltInAtom(<urn:test:test#iri> Variable(<urn:swrl:var#var1>) ) DifferentFromAtom(Variable(<urn:swrl:var#var2>) <urn:test:test#i>) SameAsAtom(Variable(<urn:swrl:var#var2>) <urn:test:test#iri>)) Head(BuiltInAtom(<urn:swrl:var#v2> Variable(<urn:swrl:var#var5>) Variable(<urn:swrl:var#var6>) ) DataPropertyAtom(<urn:test:test#dp> Variable(<urn:swrl:var#var2>) \"false\"^^xsd:boolean) ObjectPropertyAtom(<urn:test:test#op> Variable(<urn:swrl:var#var2>) Variable(<urn:swrl:var#var2>))) )");
        map.put(b.onto(),
            "Ontology(OntologyID(OntologyIRI(<urn:test:test#test>) VersionIRI(<null>)) [Axioms: 0] [Logical axioms: 0])");
        map.put(b.ann(),
            "AnnotationAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#ann> <urn:test:test#iri> \"false\"^^xsd:boolean)");
        map.put(b.asymm(),
            "AsymmetricObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)");
        map.put(b.annDom(), "AnnotationPropertyDomain(<urn:test:test#ann> <urn:test:test#iri>)");
        map.put(b.annRange(), "AnnotationPropertyRange(<urn:test:test#ann> <urn:test:test#iri>)");
        map.put(b.ass(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#c> <urn:test:test#i>)");
        map.put(b.assAnd(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectIntersectionOf(<urn:test:test#c> <urn:test:test#iri>) <urn:test:test#i>)");
        map.put(b.assOr(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectUnionOf(<urn:test:test#c> <urn:test:test#iri>) <urn:test:test#i>)");
        map.put(b.dRangeAnd(),
            "DataPropertyRange(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> DataIntersectionOf(<urn:test:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ))");
        map.put(b.dRangeOr(),
            "DataPropertyRange(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> DataUnionOf(<urn:test:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ))");
        map.put(b.assNot(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test:test#c>) <urn:test:test#i>)");
        map.put(b.assNotAnon(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test:test#c>) _:id)");
        map.put(b.assSome(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectSomeValuesFrom(<urn:test:test#op> <urn:test:test#c>) <urn:test:test#i>)");
        map.put(b.assAll(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectAllValuesFrom(<urn:test:test#op> <urn:test:test#c>) <urn:test:test#i>)");
        map.put(b.assHas(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectHasValue(<urn:test:test#op> <urn:test:test#i>) <urn:test:test#i>)");
        map.put(b.assMin(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectMinCardinality(1 <urn:test:test#op> <urn:test:test#c>) <urn:test:test#i>)");
        map.put(b.assMax(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectMaxCardinality(1 <urn:test:test#op> <urn:test:test#c>) <urn:test:test#i>)");
        map.put(b.assEq(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectExactCardinality(1 <urn:test:test#op> <urn:test:test#c>) <urn:test:test#i>)");
        map.put(b.assHasSelf(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectHasSelf(<urn:test:test#op>) <urn:test:test#i>)");
        map.put(b.assOneOf(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectOneOf(<urn:test:test#i>) <urn:test:test#i>)");
        map.put(b.assDSome(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataSomeValuesFrom(<urn:test:test#dp> <urn:test:test#datatype>) <urn:test:test#i>)");
        map.put(b.assDAll(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataAllValuesFrom(<urn:test:test#dp> <urn:test:test#datatype>) <urn:test:test#i>)");
        map.put(b.assDHas(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataHasValue(<urn:test:test#dp> \"false\"^^xsd:boolean) <urn:test:test#i>)");
        map.put(b.assDMin(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataMinCardinality(1 <urn:test:test#dp> <urn:test:test#datatype>) <urn:test:test#i>)");
        map.put(b.assDMax(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataMaxCardinality(1 <urn:test:test#dp> <urn:test:test#datatype>) <urn:test:test#i>)");
        map.put(b.assDEq(),
            "ClassAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataExactCardinality(1 <urn:test:test#dp> <urn:test:test#datatype>) <urn:test:test#i>)");
        map.put(b.dOneOf(),
            "DataPropertyRange(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> DataOneOf(\"false\"^^xsd:boolean ))");
        map.put(b.dNot(),
            "DataPropertyRange(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> DataComplementOf(DataOneOf(\"false\"^^xsd:boolean )))");
        map.put(b.dRangeRestrict(),
            "DataPropertyRange(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> DataRangeRestriction(xsd:double facetRestriction(minExclusive \"5.0\"^^xsd:double) facetRestriction(maxExclusive \"6.0\"^^xsd:double)))");
        map.put(b.assD(),
            "DataPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#i> \"false\"^^xsd:boolean)");
        map.put(b.assDPlain(),
            "DataPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#i> \"string\"@en)");
        map.put(b.dDom(),
            "DataPropertyDomain(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#c>)");
        map.put(b.dRange(),
            "DataPropertyRange(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#datatype>)");
        map.put(b.dDef(),
            "DatatypeDefinition(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#datatype> xsd:double)");
        map.put(b.decC(),
            "Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) Class(<urn:test:test#c>))");
        map.put(b.decOp(),
            "Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectProperty(<urn:test:test#op>))");
        map.put(b.decDp(),
            "Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) DataProperty(<urn:test:test#dp>))");
        map.put(b.decDt(),
            "Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) Datatype(<urn:test:test#datatype>))");
        map.put(b.decAp(),
            "Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) AnnotationProperty(<urn:test:test#ann>))");
        map.put(b.decI(),
            "Declaration(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) NamedIndividual(<urn:test:test#i>))");
        map.put(b.assDi(), "DifferentIndividuals(<urn:test:test#i> <urn:test:test#iri> )");
        map.put(b.dc(), "DisjointClasses(<urn:test:test#c> <urn:test:test#iri>)");
        map.put(b.dDp(),
            "DisjointDataProperties(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#iri> )");
        map.put(b.dOp(),
            "DisjointObjectProperties(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#iri> <urn:test:test#op> )");
        map.put(b.du(),
            "DisjointUnion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#c> <urn:test:test#c> <urn:test:test#iri> )");
        map.put(b.ec(),
            "EquivalentClasses(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#c> <urn:test:test#iri> )");
        map.put(b.eDp(),
            "EquivalentDataProperties(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#iri> )");
        map.put(b.eOp(),
            "EquivalentObjectProperties(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#iri> <urn:test:test#op> )");
        map.put(b.fdp(),
            "FunctionalDataProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp>)");
        map.put(b.fop(),
            "FunctionalObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)");
        map.put(b.ifp(),
            "InverseFunctionalObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)");
        map.put(b.iop(),
            "InverseObjectProperties(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> <urn:test:test#op>)");
        map.put(b.irr(),
            "IrreflexiveObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)");
        map.put(b.ndp(),
            "NegativeDataPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#dp> <urn:test:test#i> \"false\"^^xsd:boolean)");
        map.put(b.nop(),
            "NegativeObjectPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> <urn:test:test#i> <urn:test:test#i>)");
        map.put(b.opa(),
            "ObjectPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> <urn:test:test#i> <urn:test:test#i>)");
        map.put(b.opaInv(),
            "ObjectPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectInverseOf(<urn:test:test#op>) <urn:test:test#i> <urn:test:test#i>)");
        map.put(b.opaInvj(),
            "ObjectPropertyAssertion(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectInverseOf(<urn:test:test#op>) <urn:test:test#i> <urn:test:test#j>)");
        map.put(b.oDom(),
            "ObjectPropertyDomain(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> <urn:test:test#c>)");
        map.put(b.oRange(),
            "ObjectPropertyRange(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> <urn:test:test#c>)");
        map.put(b.chain(),
            "SubObjectPropertyOf(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) ObjectPropertyChain( <urn:test:test#iri> <urn:test:test#op> ) <urn:test:test#op>)");
        map.put(b.ref(),
            "ReflexiveObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)");
        map.put(b.same(),
            "SameIndividual(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#i> <urn:test:test#iri> )");
        map.put(b.subAnn(),
            "SubAnnotationPropertyOf(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#ann> rdfs:label)");
        map.put(b.subClass(),
            "SubClassOf(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#c> owl:Thing)");
        map.put(b.subData(), "SubDataPropertyOf(<urn:test:test#dp> owl:topDataProperty)");
        map.put(b.subObject(),
            "SubObjectPropertyOf(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op> owl:topObjectProperty)");
        map.put(b.symm(),
            "SymmetricObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)");
        map.put(b.trans(),
            "TransitiveObjectProperty(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#op>)");
        map.put(b.hasKey(),
            "HasKey(Annotation(<urn:test:test#ann> \"test\"^^xsd:string) <urn:test:test#c> (<urn:test:test#iri> <urn:test:test#op> ) (<urn:test:test#dp> ))");
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] {k, v}));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        String render = new SimpleRenderer().render(object);
        assertEquals(expected, render);
    }
}
