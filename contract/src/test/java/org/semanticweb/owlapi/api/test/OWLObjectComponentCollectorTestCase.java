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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectComponentCollector;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class OWLObjectComponentCollectorTestCase extends TestBase {

    private OWLAxiom object;
    private String expected;

    public OWLObjectComponentCollectorTestCase(OWLAxiom object, String expected) {
        this.object = object;
        this.expected = expected;
    }

    @Nonnull
    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String> map = new LinkedHashMap<>();
        map.put(b.dRange(),
                "[urn:test#datatype, urn:test#dp, <urn:test#dp>, DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#datatype>), <urn:test#datatype>]");
        map.put(b.dDef(),
                "[http://www.w3.org/2001/XMLSchema#double, urn:test#datatype, DatatypeDefinition(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#datatype> xsd:double), http://www.w3.org/2001/XMLSchema#double, <urn:test#datatype>]");
        map.put(b.decC(),
                "[urn:test#c, <urn:test#c>, Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) Class(<urn:test#c>))]");
        map.put(b.decOp(),
                "[urn:test#op, <urn:test#op>, Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectProperty(<urn:test#op>))]");
        map.put(b.decDp(),
                "[urn:test#dp, <urn:test#dp>, Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataProperty(<urn:test#dp>))]");
        map.put(b.decDt(),
                "[urn:test#datatype, Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) Datatype(<urn:test#datatype>)), <urn:test#datatype>]");
        map.put(b.decAp(),
                "[urn:test#ann, <urn:test#ann>, Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) AnnotationProperty(<urn:test#ann>))]");
        map.put(b.decI(),
                "[urn:test#i, <urn:test#i>, Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) NamedIndividual(<urn:test#i>))]");
        map.put(b.assDi(),
                "[urn:test#i, urn:test#iri, <urn:test#i>, <urn:test#iri>, DifferentIndividuals(<urn:test#i> <urn:test#iri> )]");
        map.put(b.dc(),
                "[urn:test#c, urn:test#iri, <urn:test#c>, <urn:test#iri>, DisjointClasses(<urn:test#c> <urn:test#iri>)]");
        map.put(b.dDp(),
                "[urn:test#dp, urn:test#iri, <urn:test#dp>, <urn:test#iri>, DisjointDataProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#iri> )]");
        map.put(b.dOp(),
                "[urn:test#iri, urn:test#op, <urn:test#iri>, <urn:test#op>, DisjointObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#iri> <urn:test#op> )]");
        map.put(b.du(),
                "[urn:test#c, urn:test#iri, <urn:test#c>, <urn:test#iri>, DisjointUnion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#c> <urn:test#iri> )]");
        map.put(b.ec(),
                "[urn:test#c, urn:test#iri, <urn:test#c>, <urn:test#iri>, EquivalentClasses(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#iri> )]");
        map.put(b.eDp(),
                "[urn:test#dp, urn:test#iri, <urn:test#dp>, <urn:test#iri>, EquivalentDataProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#iri> )]");
        map.put(b.eOp(),
                "[urn:test#iri, urn:test#op, <urn:test#iri>, <urn:test#op>, EquivalentObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#iri> <urn:test#op> )]");
        map.put(b.fdp(),
                "[urn:test#dp, <urn:test#dp>, FunctionalDataProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp>)]");
        map.put(b.fop(),
                "[urn:test#op, <urn:test#op>, FunctionalObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)]");
        map.put(b.ifp(),
                "[urn:test#op, <urn:test#op>, InverseFunctionalObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)]");
        map.put(b.iop(),
                "[urn:test#op, <urn:test#op>, InverseObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#op>)]");
        map.put(b.irr(),
                "[urn:test#op, <urn:test#op>, IrreflexiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)]");
        map.put(b.ndp(),
                "[http://www.w3.org/2001/XMLSchema#boolean, urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, NegativeDataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"false\"^^xsd:boolean), \"false\"^^xsd:boolean, http://www.w3.org/2001/XMLSchema#boolean]");
        map.put(b.nop(),
                "[urn:test#i, urn:test#op, <urn:test#op>, <urn:test#i>, NegativeObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#i> <urn:test#i>)]");
        map.put(b.opa(),
                "[urn:test#i, urn:test#op, <urn:test#op>, <urn:test#i>, ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#i> <urn:test#i>)]");
        map.put(b.opaInv(),
                "[urn:test#i, urn:test#op, <urn:test#op>, InverseOf(<urn:test#op>), <urn:test#i>, ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) InverseOf(<urn:test#op>) <urn:test#i> <urn:test#i>)]");
        map.put(b.opaInvj(),
                "[urn:test#i, urn:test#j, urn:test#op, <urn:test#op>, InverseOf(<urn:test#op>), <urn:test#i>, <urn:test#j>, ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) InverseOf(<urn:test#op>) <urn:test#i> <urn:test#j>)]");
        map.put(b.oDom(),
                "[urn:test#c, urn:test#op, <urn:test#c>, <urn:test#op>, ObjectPropertyDomain(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#c>)]");
        map.put(b.oRange(),
                "[urn:test#c, urn:test#op, <urn:test#c>, <urn:test#op>, ObjectPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#c>)]");
        map.put(b.chain(),
                "[urn:test#iri, urn:test#op, <urn:test#iri>, <urn:test#op>, SubObjectPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectPropertyChain( <urn:test#iri> <urn:test#op> ) <urn:test#op>)]");
        map.put(b.ref(),
                "[urn:test#op, <urn:test#op>, ReflexiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)]");
        map.put(b.same(),
                "[urn:test#i, urn:test#iri, <urn:test#i>, <urn:test#iri>, SameIndividual(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#i> <urn:test#iri> )]");
        map.put(b.subAnn(),
                "[http://www.w3.org/2000/01/rdf-schema#label, urn:test#ann, rdfs:label, <urn:test#ann>, SubAnnotationPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#ann> rdfs:label)]");
        map.put(b.subClass(),
                "[http://www.w3.org/2002/07/owl#Thing, urn:test#c, owl:Thing, <urn:test#c>, SubClassOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> owl:Thing)]");
        map.put(b.subData(),
                "[http://www.w3.org/2002/07/owl#topDataProperty, urn:test#dp, owl:topDataProperty, <urn:test#dp>, SubDataPropertyOf(<urn:test#dp> owl:topDataProperty)]");
        map.put(b.subObject(),
                "[http://www.w3.org/2002/07/owl#topObjectProperty, urn:test#op, owl:topObjectProperty, <urn:test#op>, SubObjectPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> owl:topObjectProperty)]");
        map.put(b.rule(),
                "[DLSafeRule( Body(BuiltInAtom(<urn:swrl#v1> Variable(<urn:swrl#var3>) Variable(<urn:swrl#var4>) )) Head(BuiltInAtom(<urn:swrl#v2> Variable(<urn:swrl#var5>) Variable(<urn:swrl#var6>) )) ), BuiltInAtom(<urn:swrl#v1> Variable(<urn:swrl#var3>) Variable(<urn:swrl#var4>) ), BuiltInAtom(<urn:swrl#v2> Variable(<urn:swrl#var5>) Variable(<urn:swrl#var6>) ), Variable(<urn:swrl#var3>), Variable(<urn:swrl#var4>), Variable(<urn:swrl#var5>), Variable(<urn:swrl#var6>)]");
        map.put(b.symm(),
                "[urn:test#op, <urn:test#op>, SymmetricObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)]");
        map.put(b.trans(),
                "[urn:test#op, <urn:test#op>, TransitiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)]");
        map.put(b.hasKey(),
                "[urn:test#c, urn:test#dp, urn:test#iri, urn:test#op, <urn:test#c>, <urn:test#iri>, <urn:test#op>, <urn:test#dp>, HasKey(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> (<urn:test#iri> <urn:test#op> ) (<urn:test#dp> ))]");
        map.put(b.ann(),
                "[urn:test#iri, AnnotationAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#ann> <urn:test#iri> \"false\"^^xsd:boolean)]");
        map.put(b.asymm(),
                "[urn:test#op, <urn:test#op>, AsymmetricObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)]");
        map.put(b.annDom(),
                "[urn:test#ann, urn:test#iri, <urn:test#ann>, AnnotationPropertyDomain(<urn:test#ann> <urn:test#iri>)]");
        map.put(b.annRange(),
                "[urn:test#ann, urn:test#iri, <urn:test#ann>, AnnotationPropertyRange(<urn:test#ann> <urn:test#iri>)]");
        map.put(b.ass(),
                "[urn:test#c, urn:test#i, <urn:test#c>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#i>)]");
        map.put(b.assAnd(),
                "[urn:test#c, urn:test#i, urn:test#iri, <urn:test#c>, <urn:test#iri>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectIntersectionOf(<urn:test#c> <urn:test#iri>) <urn:test#i>), ObjectIntersectionOf(<urn:test#c> <urn:test#iri>)]");
        map.put(b.assOr(),
                "[urn:test#c, urn:test#i, urn:test#iri, <urn:test#c>, <urn:test#iri>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectUnionOf(<urn:test#c> <urn:test#iri>) <urn:test#i>), ObjectUnionOf(<urn:test#c> <urn:test#iri>)]");
        map.put(b.dRangeAnd(),
                "[http://www.w3.org/2001/XMLSchema#boolean, urn:test#datatype, urn:test#dp, <urn:test#dp>, DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataIntersectionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) )), <urn:test#datatype>, DataOneOf(\"false\"^^xsd:boolean ), "
                        + "DataIntersectionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ), \"false\"^^xsd:boolean, http://www.w3.org/2001/XMLSchema#boolean]");
        map.put(b.dRangeOr(),
                "[http://www.w3.org/2001/XMLSchema#boolean, urn:test#datatype, urn:test#dp, <urn:test#dp>, DataUnionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ), DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataUnionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) )), "
                        + "<urn:test#datatype>, DataOneOf(\"false\"^^xsd:boolean ), \"false\"^^xsd:boolean, http://www.w3.org/2001/XMLSchema#boolean]");
        map.put(b.assNot(),
                "[urn:test#c, urn:test#i, <urn:test#c>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test#c>) <urn:test#i>), ObjectComplementOf(<urn:test#c>)]");
        map.put(b.assNotAnon(),
                "[urn:test#c, <urn:test#c>, _:id, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test#c>) _:id), ObjectComplementOf(<urn:test#c>)]");
        map.put(b.assSome(),
                "[urn:test#c, urn:test#i, urn:test#op, <urn:test#c>, <urn:test#op>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectSomeValuesFrom(<urn:test#op> <urn:test#c>) <urn:test#i>), ObjectSomeValuesFrom(<urn:test#op> <urn:test#c>)]");
        map.put(b.assAll(),
                "[urn:test#c, urn:test#i, urn:test#op, <urn:test#c>, <urn:test#op>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectAllValuesFrom(<urn:test#op> <urn:test#c>) <urn:test#i>), ObjectAllValuesFrom(<urn:test#op> <urn:test#c>)]");
        map.put(b.assHas(),
                "[urn:test#i, urn:test#op, <urn:test#op>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectHasValue(<urn:test#op> <urn:test#i>) <urn:test#i>), ObjectHasValue(<urn:test#op> <urn:test#i>)]");
        map.put(b.assMin(),
                "[urn:test#c, urn:test#i, urn:test#op, <urn:test#c>, <urn:test#op>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectMinCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>), ObjectMinCardinality(1 <urn:test#op> <urn:test#c>)]");
        map.put(b.assMax(),
                "[urn:test#c, urn:test#i, urn:test#op, <urn:test#c>, <urn:test#op>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectMaxCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>), ObjectMaxCardinality(1 <urn:test#op> <urn:test#c>)]");
        map.put(b.assEq(),
                "[urn:test#c, urn:test#i, urn:test#op, <urn:test#c>, <urn:test#op>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectExactCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>), ObjectExactCardinality(1 <urn:test#op> <urn:test#c>)]");
        map.put(b.assHasSelf(),
                "[urn:test#i, urn:test#op, <urn:test#op>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectHasSelf(<urn:test#op>) <urn:test#i>), ObjectHasSelf(<urn:test#op>)]");
        map.put(b.assOneOf(),
                "[urn:test#i, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectOneOf(<urn:test#i>) <urn:test#i>), ObjectOneOf(<urn:test#i>)]");
        map.put(b.assDSome(),
                "[urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataSomeValuesFrom(<urn:test#dp> <urn:test#datatype>) <urn:test#i>), DataSomeValuesFrom(<urn:test#dp> <urn:test#datatype>)]");
        map.put(b.assDAll(),
                "[urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataAllValuesFrom(<urn:test#dp> <urn:test#datatype>) <urn:test#i>), DataAllValuesFrom(<urn:test#dp> <urn:test#datatype>)]");
        map.put(b.assDHas(),
                "[urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataHasValue(<urn:test#dp> \"false\"^^xsd:boolean) <urn:test#i>), DataHasValue(<urn:test#dp> \"false\"^^xsd:boolean)]");
        map.put(b.assDMin(),
                "[urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataMinCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>), DataMinCardinality(1 <urn:test#dp> <urn:test#datatype>)]");
        map.put(b.assDMax(),
                "[urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataMaxCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>), DataMaxCardinality(1 <urn:test#dp> <urn:test#datatype>)]");
        map.put(b.assDEq(),
                "[urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataExactCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>), DataExactCardinality(1 <urn:test#dp> <urn:test#datatype>)]");
        map.put(b.dOneOf(),
                "[http://www.w3.org/2001/XMLSchema#boolean, urn:test#dp, <urn:test#dp>, DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataOneOf(\"false\"^^xsd:boolean )), \"false\"^^xsd:boolean, http://www.w3.org/2001/XMLSchema#boolean, DataOneOf(\"false\"^^xsd:boolean )]");
        map.put(b.dNot(),
                "[http://www.w3.org/2001/XMLSchema#boolean, urn:test#dp, <urn:test#dp>, DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataComplementOf(DataOneOf(\"false\"^^xsd:boolean ))), DataComplementOf(DataOneOf(\"false\"^^xsd:boolean )), \"false\"^^xsd:boolean, "
                        + "http://www.w3.org/2001/XMLSchema#boolean, DataOneOf(\"false\"^^xsd:boolean )]");
        map.put(b.dRangeRestrict(),
                "[http://www.w3.org/2001/XMLSchema#double, urn:test#dp, <urn:test#dp>, DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataRangeRestriction(xsd:double facetRestriction(minExclusive \"5.0\"^^xsd:double) facetRestriction(maxExclusive \"6.0\"^^xsd:double))), "
                        + "DataRangeRestriction(xsd:double facetRestriction(minExclusive \"5.0\"^^xsd:double) "
                        + "facetRestriction(maxExclusive \"6.0\"^^xsd:double)), facetRestriction(minExclusive \"5.0\"^^xsd:double), facetRestriction(maxExclusive \"6.0\"^^xsd:double), \"5.0\"^^xsd:double, \"6.0\"^^xsd:double, http://www.w3.org/2001/XMLSchema#double]");
        map.put(b.assD(),
                "[http://www.w3.org/2001/XMLSchema#boolean, urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, DataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"false\"^^xsd:boolean), \"false\"^^xsd:boolean, http://www.w3.org/2001/XMLSchema#boolean]");
        map.put(b.assDPlain(),
                "[http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral, urn:test#dp, urn:test#i, <urn:test#dp>, <urn:test#i>, DataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"string\"@en), http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral, \"string\"@en]");
        map.put(b.dDom(),
                "[urn:test#dp, <urn:test#dp>, DataPropertyDomain(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#c>)]");
        map.put(b.bigRule(),
                "[\"false\"^^xsd:boolean, Variable(<urn:swrl#var6>), Variable(<urn:swrl#var5>), BuiltInAtom(<urn:test#iri> Variable(<urn:swrl#var1>) ), Variable(<urn:swrl#var4>), BuiltInAtom(<urn:swrl#v1> Variable(<urn:swrl#var3>) Variable(<urn:swrl#var4>) ), Variable(<urn:swrl#var3>), SameAsAtom(Variable(<urn:swrl#var2>) <urn:test#iri>), Variable(<urn:swrl#var2>), <urn:test#op>, BuiltInAtom(<urn:swrl#v2> Variable(<urn:swrl#var5>) Variable(<urn:swrl#var6>) ), \"false\"^^xsd:boolean, DifferentFromAtom(Variable(<urn:swrl#var2>) <urn:test#i>), urn:test#dp, Variable(<urn:swrl#var1>), <urn:test#c>, urn:test#datatype, http://www.w3.org/2001/XMLSchema#boolean, <urn:test#iri>, ObjectPropertyAtom(<urn:test#op> Variable(<urn:swrl#var2>) Variable(<urn:swrl#var2>)), DataRangeAtom(<urn:test#datatype> Variable(<urn:swrl#var1>)), <urn:test#i>, http://www.w3.org/2001/XMLSchema#boolean, DataPropertyAtom(<urn:test#dp> Variable(<urn:swrl#var2>) \"false\"^^xsd:boolean), urn:test#op, urn:test#c, urn:test#iri, ClassAtom(<urn:test#c> Variable(<urn:swrl#var2>)), <urn:test#iri>, urn:test#i, DLSafeRule( Body(ClassAtom(<urn:test#c> Variable(<urn:swrl#var2>)) DataRangeAtom(<urn:test#datatype> Variable(<urn:swrl#var1>)) BuiltInAtom(<urn:swrl#v1> Variable(<urn:swrl#var3>) Variable(<urn:swrl#var4>) ) BuiltInAtom(<urn:test#iri> Variable(<urn:swrl#var1>) ) SameAsAtom(Variable(<urn:swrl#var2>) <urn:test#iri>) DifferentFromAtom(Variable(<urn:swrl#var2>) <urn:test#i>)) Head(ObjectPropertyAtom(<urn:test#op> Variable(<urn:swrl#var2>) Variable(<urn:swrl#var2>)) DataPropertyAtom(<urn:test#dp> Variable(<urn:swrl#var2>) \"false\"^^xsd:boolean) BuiltInAtom(<urn:swrl#v2> Variable(<urn:swrl#var5>) Variable(<urn:swrl#var6>) )) ), <urn:test#datatype>, <urn:test#i>, <urn:test#dp>]");
        Collection<Object[]> toReturn = new ArrayList<>();
        for (Map.Entry<OWLAxiom, String> e : map.entrySet()) {
            toReturn.add(new Object[] { e.getKey(), e.getValue() });
        }
        return toReturn;
    }

    @Test
    public void testAssertion() {
        OWLObjectComponentCollector testsubject = new OWLObjectComponentCollector();
        List<OWLObject> components = new ArrayList<>(
                testsubject.getComponents(object));
        try {
            Collections.sort(components);
            String result = components.toString();
            assertEquals(expected, result);
        } catch (IllegalArgumentException e) {
            // XXX a separate test to be written for this
            System.out.println("cannot sort: " + object);
        }
    }
}
