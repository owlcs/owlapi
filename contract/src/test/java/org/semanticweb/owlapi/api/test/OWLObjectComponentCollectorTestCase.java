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
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.OWLObjectComponentCollector;

import com.google.common.collect.Sets;

@SuppressWarnings({ "javadoc" })
@RunWith(Parameterized.class)
public class OWLObjectComponentCollectorTestCase {

    private static final String CI = "<urn:test#c>";
    private static final String C = "urn:test#c";
    private static final String DTD = "DatatypeDefinition(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#datatype> xsd:double)";
    private static final String DB = "http://www.w3.org/2001/XMLSchema#double";
    private static final String DT = "urn:test#datatype";
    private static final String DP = "urn:test#dp";
    private static final String DPI = "<urn:test#dp>";
    private static final String DTI = "<urn:test#datatype>";
    private static final String DPR = "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#datatype>)";
    private static final String DC = "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) Class(<urn:test#c>))";
    private static final String OP = "urn:test#op";
    private static final String OPI = "<urn:test#op>";
    private static final String DOP = "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectProperty(<urn:test#op>))";
    private static final String ANN = "urn:test#ann";
    private static final String ANNI = "<urn:test#ann>";
    private static final String DANN = "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) AnnotationProperty(<urn:test#ann>))";
    private static final String DDP = "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataProperty(<urn:test#dp>))";
    private static final String DD = "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) Datatype(<urn:test#datatype>))";
    private static final String FDP = "FunctionalDataProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp>)";
    private static final String FOP = "FunctionalObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)";
    private static final String IFP = "InverseFunctionalObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)";
    private static final String IOP = "InverseObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#op>)";
    private static final String DIND = "Declaration(Annotation(<urn:test#ann> \"test\"^^xsd:string) NamedIndividual(<urn:test#i>))";
    private static final String DIFF = "DifferentIndividuals(<urn:test#i> <urn:test#iri> )";
    private static final String DSJC = "DisjointClasses(<urn:test#c> <urn:test#iri>)";
    private static final String IRI = "urn:test#iri";
    private static final String IRII = "<urn:test#iri>";
    private static final String I = "urn:test#i";
    private static final String DISJDP = "DisjointDataProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#iri> )";
    private static final String DSJOP = "DisjointObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#iri> <urn:test#op> )";
    private static final String II = "<urn:test#i>";
    private static final String SYMM = "SymmetricObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)";
    private static final String plain = "http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral";
    private static final String adp = "DataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"string\"@en)";
    private static final String dpdomain = "DataPropertyDomain(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#c>)";
    private static final String VAR1 = "Variable(<urn:swrl#var1>)";
    private static final String DRA = "DataRangeAtom(<urn:test#datatype> Variable(<urn:swrl#var1>))";
    private static final String v1 = "BuiltInAtom(<urn:test#iri> Variable(<urn:swrl#var1>) )";
    private static final String v2 = "SameAsAtom(Variable(<urn:swrl#var2>) <urn:test#iri>)";
    private static final String var2 = "Variable(<urn:swrl#var2>)";
    private static final String diffvar2 = "DifferentFromAtom(Variable(<urn:swrl#var2>) <urn:test#i>)";
    private static final String opavar2 = "ObjectPropertyAtom(<urn:test#op> Variable(<urn:swrl#var2>) Variable(<urn:swrl#var2>))";
    private static final String dpvar2 = "DataPropertyAtom(<urn:test#dp> Variable(<urn:swrl#var2>) \"false\"^^xsd:boolean)";
    private static final String classvar2 = "ClassAtom(<urn:test#c> Variable(<urn:swrl#var2>))";
    private static final String rule = "DLSafeRule(Annotation(<urn:test#ann> \"test\"^^xsd:string)  Body(ClassAtom(<urn:test#c> Variable(<urn:swrl#var2>)) DataRangeAtom(<urn:test#datatype> Variable(<urn:swrl#var1>)) BuiltInAtom(<urn:swrl#v1> Variable(<urn:swrl#var3>) Variable(<urn:swrl#var4>) ) BuiltInAtom(<urn:test#iri> Variable(<urn:swrl#var1>) ) SameAsAtom(Variable(<urn:swrl#var2>) <urn:test#iri>) DifferentFromAtom(Variable(<urn:swrl#var2>) <urn:test#i>)) Head(ObjectPropertyAtom(<urn:test#op> Variable(<urn:swrl#var2>) Variable(<urn:swrl#var2>)) DataPropertyAtom(<urn:test#dp> Variable(<urn:swrl#var2>) \"false\"^^xsd:boolean) BuiltInAtom(<urn:swrl#v2> Variable(<urn:swrl#var5>) Variable(<urn:swrl#var6>) )) )";
    private static final String T = "TransitiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)";
    private static final String HASKEY = "HasKey(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> (<urn:test#iri> <urn:test#op> ) (<urn:test#dp> ))";
    private static final String AANN = "AnnotationAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#ann> <urn:test#iri> \"false\"^^xsd:boolean)";
    private static final String asymm = "AsymmetricObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)";
    private static final String APD = "AnnotationPropertyDomain(<urn:test#ann> <urn:test#iri>)";
    private static final String APR = "AnnotationPropertyRange(<urn:test#ann> <urn:test#iri>)";
    private static final String ACL = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#i>)";
    private static final String ACLAND = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectIntersectionOf(<urn:test#c> <urn:test#iri>) <urn:test#i>)";
    private static final String AND = "ObjectIntersectionOf(<urn:test#c> <urn:test#iri>)";
    private static final String ACLOR = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectUnionOf(<urn:test#c> <urn:test#iri>) <urn:test#i>)";
    private static final String OR = "ObjectUnionOf(<urn:test#c> <urn:test#iri>)";
    private static final String DPRAND = "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataIntersectionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ))";
    private static final String DONEOF = "DataOneOf(\"false\"^^xsd:boolean )";
    private static final String DAND = "DataIntersectionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) )";
    private static final String DOR = "DataUnionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) )";
    private static final String DPROR = "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataUnionOf(<urn:test#datatype> DataOneOf(\"false\"^^xsd:boolean ) ))";
    private static final String CNOT = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test#c>) <urn:test#i>)";
    private static final String NOT = "ObjectComplementOf(<urn:test#c>)";
    private static final String ID = "_:id";
    private static final String ACNOT = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectComplementOf(<urn:test#c>) _:id)";
    private static final String ACSOME = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectSomeValuesFrom(<urn:test#op> <urn:test#c>) <urn:test#i>)";
    private static final String SOME = "ObjectSomeValuesFrom(<urn:test#op> <urn:test#c>)";
    private static final String ACALL = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectAllValuesFrom(<urn:test#op> <urn:test#c>) <urn:test#i>)";
    private static final String ALL = "ObjectAllValuesFrom(<urn:test#op> <urn:test#c>)";
    private static final String ACHAS = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectHasValue(<urn:test#op> <urn:test#i>) <urn:test#i>)";
    private static final String HAS = "ObjectHasValue(<urn:test#op> <urn:test#i>)";
    private static final String AOMIN = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectMinCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>)";
    private static final String OMIN = "ObjectMinCardinality(1 <urn:test#op> <urn:test#c>)";
    private static final String AOMAX = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectMaxCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>)";
    private static final String MAX = "ObjectMaxCardinality(1 <urn:test#op> <urn:test#c>)";
    private static final String AOEQ = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectExactCardinality(1 <urn:test#op> <urn:test#c>) <urn:test#i>)";
    private static final String OEQ = "ObjectExactCardinality(1 <urn:test#op> <urn:test#c>)";
    private static final String ASELF = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectHasSelf(<urn:test#op>) <urn:test#i>)";
    private static final String SELF = "ObjectHasSelf(<urn:test#op>)";
    private static final String AONE = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectOneOf(<urn:test#i>) <urn:test#i>)";
    private static final String ONE = "ObjectOneOf(<urn:test#i>)";
    private static final String ADSOME = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataSomeValuesFrom(<urn:test#dp> <urn:test#datatype>) <urn:test#i>)";
    private static final String DSOME = "DataSomeValuesFrom(<urn:test#dp> <urn:test#datatype>)";
    private static final String ADALL = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataAllValuesFrom(<urn:test#dp> <urn:test#datatype>) <urn:test#i>)";
    private static final String DALL = "DataAllValuesFrom(<urn:test#dp> <urn:test#datatype>)";
    private static final String ADHAS = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataHasValue(<urn:test#dp> \"false\"^^xsd:boolean) <urn:test#i>)";
    private static final String DHAS = "DataHasValue(<urn:test#dp> \"false\"^^xsd:boolean)";
    private static final String ADMIN = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataMinCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>)";
    private static final String DMIN = "DataMinCardinality(1 <urn:test#dp> <urn:test#datatype>)";
    private static final String ADMAX = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataMaxCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>)";
    private static final String DMAX = "DataMaxCardinality(1 <urn:test#dp> <urn:test#datatype>)";
    private static final String ADEQ = "ClassAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) DataExactCardinality(1 <urn:test#dp> <urn:test#datatype>) <urn:test#i>)";
    private static final String DEQ = "DataExactCardinality(1 <urn:test#dp> <urn:test#datatype>)";
    private static final String ADONEOF = "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataOneOf(\"false\"^^xsd:boolean ))";
    private static final String DPRNOT = "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataComplementOf(DataOneOf(\"false\"^^xsd:boolean )))";
    private static final String DNOT = "DataComplementOf(DataOneOf(\"false\"^^xsd:boolean ))";
    private static final String FIVE = "\"5.0\"^^xsd:double";
    private static final String SIX = "\"6.0\"^^xsd:double";
    private static final String MINMAX = "DataPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> DataRangeRestriction(xsd:double facetRestriction(minExclusive \"5.0\"^^xsd:double) facetRestriction(maxExclusive \"6.0\"^^xsd:double)))";
    private static final String MINMXSIX = "DataRangeRestriction(xsd:double facetRestriction(minExclusive \"5.0\"^^xsd:double) facetRestriction(maxExclusive \"6.0\"^^xsd:double))";
    private static final String MIN5 = "facetRestriction(minExclusive \"5.0\"^^xsd:double)";
    private static final String MAXSIX = "facetRestriction(maxExclusive \"6.0\"^^xsd:double)";
    private static final String dpafalse = "DataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"false\"^^xsd:boolean)";
    private static final String EQC = "EquivalentClasses(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#iri> )";
    private static final String EQDP = "EquivalentDataProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#iri> )";
    private static final String EQOP = "EquivalentObjectProperties(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#iri> <urn:test#op> )";
    private static final String IRR = "IrreflexiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)";
    private static final String BLN = "http://www.w3.org/2001/XMLSchema#boolean";
    private static final String FALSE = "\"false\"^^xsd:boolean";
    private static final String DU = "DisjointUnion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> <urn:test#c> <urn:test#iri> )";
    private static final String ANDP = "NegativeDataPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#dp> <urn:test#i> \"false\"^^xsd:boolean)";
    private static final String ANOP = "NegativeObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#i> <urn:test#i>)";
    private static final String AOP = "ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#i> <urn:test#i>)";
    private static final String INVERSE = "InverseOf(<urn:test#op>)";
    private static final String AOINV = "ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) InverseOf(<urn:test#op>) <urn:test#i> <urn:test#i>)";
    private static final String JI = "<urn:test#j>";
    private static final String J = "urn:test#j";
    private static final String AOPJ = "ObjectPropertyAssertion(Annotation(<urn:test#ann> \"test\"^^xsd:string) InverseOf(<urn:test#op>) <urn:test#i> <urn:test#j>)";
    private static final String OPD = "ObjectPropertyDomain(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#c>)";
    private static final String OPR = "ObjectPropertyRange(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> <urn:test#c>)";
    private static final String SUBO = "SubObjectPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) ObjectPropertyChain( <urn:test#iri> <urn:test#op> ) <urn:test#op>)";
    private static final String R = "ReflexiveObjectProperty(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op>)";
    private static final String SAME = "SameIndividual(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#i> <urn:test#iri> )";
    private static final String LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
    private static final String LAB = "rdfs:label";
    private static final String SUBA = "SubAnnotationPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#ann> rdfs:label)";
    private static final String TOP = "http://www.w3.org/2002/07/owl#Thing";
    private static final String THING = "owl:Thing";
    private static final String SUBC = "SubClassOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#c> owl:Thing)";
    private static final String TOPDT = "http://www.w3.org/2002/07/owl#topDataProperty";
    private static final String TDT = "owl:topDataProperty";
    private static final String SUBD = "SubDataPropertyOf(<urn:test#dp> owl:topDataProperty)";
    private static final String TOPOP = "http://www.w3.org/2002/07/owl#topObjectProperty";
    private static final String TOPO = "owl:topObjectProperty";
    private static final String SUBOP = "SubObjectPropertyOf(Annotation(<urn:test#ann> \"test\"^^xsd:string) <urn:test#op> owl:topObjectProperty)";
    private static final String var6 = "Variable(<urn:swrl#var6>)";
    private static final String var5 = "Variable(<urn:swrl#var5>)";
    private static final String v4 = "Variable(<urn:swrl#var4>)";
    private static final String v34 = "BuiltInAtom(<urn:swrl#v1> Variable(<urn:swrl#var3>) Variable(<urn:swrl#var4>) )";
    private static final String v3 = "Variable(<urn:swrl#var3>)";
    private static final String var236 = "BuiltInAtom(<urn:swrl#v2> Variable(<urn:swrl#var5>) Variable(<urn:swrl#var6>) )";
    private static final String SHORTRULE = "DLSafeRule( Body(BuiltInAtom(<urn:swrl#v1> Variable(<urn:swrl#var3>) Variable(<urn:swrl#var4>) )) Head(BuiltInAtom(<urn:swrl#v2> Variable(<urn:swrl#var5>) Variable(<urn:swrl#var6>) )) )";
    private OWLAxiom object;
    private Set<String> expected;

    public OWLObjectComponentCollectorTestCase(OWLAxiom object,
        String[] expected) {
        this.object = object;
        this.expected = Sets.newHashSet(expected);
    }

    @Nonnull
    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        Builder b = new Builder();
        Map<OWLAxiom, String[]> map = new LinkedHashMap<>();
        map.put(b.dRange(), new String[] { DT, DP, DPI, DPR, DTI });
        map.put(b.dDef(), new String[] { DB, DT, DTD, DB, DTI });
        map.put(b.decC(), new String[] { C, CI, DC });
        map.put(b.decOp(), new String[] { OP, OPI, DOP });
        map.put(b.decDp(), new String[] { DP, DPI, DDP });
        map.put(b.decDt(), new String[] { DT, DD, DTI });
        map.put(b.decAp(), new String[] { ANN, ANNI, DANN });
        map.put(b.decI(), new String[] { I, II, DIND });
        map.put(b.assDi(), new String[] { I, IRI, II, IRII, DIFF });
        map.put(b.dc(), new String[] { C, IRI, CI, IRII, DSJC });
        map.put(b.dDp(), new String[] { DP, IRI, DPI, IRII, DISJDP });
        map.put(b.dOp(), new String[] { IRI, OP, IRII, OPI, DSJOP });
        map.put(b.du(), new String[] { C, IRI, CI, IRII, DU });
        map.put(b.ec(), new String[] { C, IRI, CI, IRII, EQC });
        map.put(b.eDp(), new String[] { DP, IRI, DPI, IRII, EQDP });
        map.put(b.eOp(), new String[] { IRI, OP, IRII, OPI, EQOP });
        map.put(b.fdp(), new String[] { DP, DPI, FDP });
        map.put(b.fop(), new String[] { OP, OPI, FOP });
        map.put(b.ifp(), new String[] { OP, OPI, IFP });
        map.put(b.iop(), new String[] { OP, OPI, IOP });
        map.put(b.irr(), new String[] { OP, OPI, IRR });
        map.put(b.ndp(),
            new String[] { BLN, DP, I, DPI, II, ANDP, FALSE, BLN });
        map.put(b.nop(), new String[] { I, OP, OPI, II, ANOP });
        map.put(b.opa(), new String[] { I, OP, OPI, II, AOP });
        map.put(b.opaInv(), new String[] { I, OP, OPI, INVERSE, II, AOINV });
        map.put(b.opaInvj(),
            new String[] { I, J, OP, OPI, INVERSE, II, JI, AOPJ });
        map.put(b.oDom(), new String[] { C, OP, CI, OPI, OPD });
        map.put(b.oRange(), new String[] { C, OP, CI, OPI, OPR });
        map.put(b.chain(), new String[] { IRI, OP, IRII, OPI, SUBO });
        map.put(b.ref(), new String[] { OP, OPI, R });
        map.put(b.same(), new String[] { I, IRI, II, IRII, SAME });
        map.put(b.subAnn(), new String[] { LABEL, ANN, LAB, ANNI, SUBA });
        map.put(b.subClass(), new String[] { TOP, C, THING, CI, SUBC });
        map.put(b.subData(), new String[] { TOPDT, DP, TDT, DPI, SUBD });
        map.put(b.subObject(), new String[] { TOPOP, OP, TOPO, OPI, SUBOP });
        map.put(b.rule(),
            new String[] { SHORTRULE, v34, var236, v3, v4, var5, var6 });
        map.put(b.symm(), new String[] { OP, OPI, SYMM });
        map.put(b.trans(), new String[] { OP, OPI, T });
        map.put(b.hasKey(),
            new String[] { C, DP, IRI, OP, CI, IRII, OPI, DPI, HASKEY });
        map.put(b.ann(), new String[] { IRI, AANN });
        map.put(b.asymm(), new String[] { OP, OPI, asymm });
        map.put(b.annDom(), new String[] { ANN, IRI, ANNI, APD });
        map.put(b.annRange(), new String[] { ANN, IRI, ANNI, APR });
        map.put(b.ass(), new String[] { C, I, CI, II, ACL });
        map.put(b.assAnd(),
            new String[] { C, I, IRI, CI, IRII, II, ACLAND, AND });
        map.put(b.assOr(), new String[] { C, I, IRI, CI, IRII, II, ACLOR, OR });
        map.put(b.dRangeAnd(), new String[] { BLN, DT, DP, DPI, DPRAND, DTI,
            DONEOF, DAND, FALSE, BLN });
        map.put(b.dRangeOr(), new String[] { BLN, DT, DP, DPI, DOR, DPROR, DTI,
            DONEOF, FALSE, BLN });
        map.put(b.assNot(), new String[] { C, I, CI, II, CNOT, NOT });
        map.put(b.assNotAnon(), new String[] { C, CI, ID, ACNOT, NOT });
        map.put(b.assSome(),
            new String[] { C, I, OP, CI, OPI, II, ACSOME, SOME });
        map.put(b.assAll(), new String[] { C, I, OP, CI, OPI, II, ACALL, ALL });
        map.put(b.assHas(), new String[] { I, OP, OPI, II, ACHAS, HAS });
        map.put(b.assMin(),
            new String[] { C, I, OP, CI, OPI, II, AOMIN, OMIN });
        map.put(b.assMax(), new String[] { C, I, OP, CI, OPI, II, AOMAX, MAX });
        map.put(b.assEq(), new String[] { C, I, OP, CI, OPI, II, AOEQ, OEQ });
        map.put(b.assHasSelf(), new String[] { I, OP, OPI, II, ASELF, SELF });
        map.put(b.assOneOf(), new String[] { I, II, AONE, ONE });
        map.put(b.assDSome(), new String[] { DP, I, DPI, II, ADSOME, DSOME });
        map.put(b.assDAll(), new String[] { DP, I, DPI, II, ADALL, DALL });
        map.put(b.assDHas(), new String[] { DP, I, DPI, II, ADHAS, DHAS });
        map.put(b.assDMin(), new String[] { DP, I, DPI, II, ADMIN, DMIN });
        map.put(b.assDMax(), new String[] { DP, I, DPI, II, ADMAX, DMAX });
        map.put(b.assDEq(), new String[] { DP, I, DPI, II, ADEQ, DEQ });
        map.put(b.dOneOf(),
            new String[] { BLN, DP, DPI, ADONEOF, FALSE, BLN, DONEOF });
        map.put(b.dNot(),
            new String[] { BLN, DP, DPI, DPRNOT, DNOT, FALSE, BLN, DONEOF });
        map.put(b.dRangeRestrict(), new String[] { DB, DP, DPI, MINMAX,
            MINMXSIX, MIN5, MAXSIX, FIVE, SIX, DB });
        map.put(b.assD(),
            new String[] { BLN, DP, I, DPI, II, dpafalse, FALSE, BLN });
        map.put(b.assDPlain(), new String[] { plain, DP, I, DPI, II, adp, plain,
            "\"string\"@en" });
        map.put(b.dDom(), new String[] { DP, DPI, dpdomain });
        map.put(b.bigRule(),
            new String[] { FALSE, var6, var5, v1, v4, v34, v3, v2, var2, OPI,
                var236, FALSE, diffvar2, DP, VAR1, CI, DT, BLN, IRII, opavar2,
                DRA, II, BLN, dpvar2, OP, C, IRI, classvar2, IRII, I, rule, DTI,
                II, DPI });
        Collection<Object[]> toReturn = new ArrayList<>();
        map.forEach((k, v) -> toReturn.add(new Object[] { k, v }));
        return toReturn;
    }

    @Test
    public void testAssertion() {
        OWLObjectComponentCollector testsubject = new OWLObjectComponentCollector();
        Set<OWLObject> components = testsubject.getComponents(object);
        Set<String> strings = asSet(components.stream().map(c -> c.toString()));
        assertEquals(expected, strings);
    }
}
