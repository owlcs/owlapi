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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFilenames;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

class SerializationTestCase extends TestBase {

    private final OWLDatatype owldatatype = Datatype(OWL2Datatype.XSD_INT.getIRI());
    private final OWLDataRange dr = DatatypeRestriction(owldatatype);
    private final String string = "testString";
    private final PrefixManager prefixmanager = new DefaultPrefixManager();
    private final OWLIndividual ai = AnonymousIndividual();
    private final List<OWLObjectPropertyExpression> listowlobjectproperties = l(P, op1);
    protected OWLOntology o;
    IRI ontologyIRI;

    @BeforeEach
    void setUp() throws URISyntaxException {
        m.getIRIMappers().add(new AutoIRIMapper(new File("."), false));
        o = loadFrom(new File(getClass().getResource("/" + TestFilenames.PIZZA_OWL).toURI()), m);
        ontologyIRI = o.getOntologyID().getOntologyIRI().get();
    }

    @Test
    void testrun() throws IOException, ClassNotFoundException {
        add(Declaration(Class(iriTest)));
        add(SubClassOf(C, Class(string, prefixmanager)));
        add(EquivalentClasses(Class(iriTest), C));
        add(DisjointClasses(Class(iriTest), C));
        add(SubObjectPropertyOf(op1, op2));
        add(SubPropertyChainOf(listowlobjectproperties, op1));
        add(EquivalentObjectProperties(op1, op2));
        add(DisjointObjectProperties(op1, op2));
        add(InverseObjectProperties(op1, op2));
        add(ObjectPropertyDomain(P, C));
        add(ObjectPropertyRange(P, C));
        add(FunctionalObjectProperty(P));
        add(AnnotationAssertion(AP, I.getIRI(), LIT_TRUE));
        o.applyChange(new AddImport(o, ImportsDeclaration(iriTest)));
        add(AnnotationPropertyDomain(AP, iriTest));
        add(AnnotationPropertyRange(AP, iriTest));
        add(SubAnnotationPropertyOf(AP, AP));
        add(InverseFunctionalObjectProperty(P));
        add(ReflexiveObjectProperty(P));
        add(IrreflexiveObjectProperty(P));
        add(SymmetricObjectProperty(P));
        add(AsymmetricObjectProperty(P));
        add(TransitiveObjectProperty(P));
        add(SubDataPropertyOf(DP, DP));
        add(EquivalentDataProperties(dp1, dp2));
        add(DisjointDataProperties(dp1, dp2));
        add(DataPropertyDomain(DP, C));
        add(DataPropertyRange(DP, dr));
        add(FunctionalDataProperty(DP));
        add(HasKey(C, P, op1));
        add(DatatypeDefinition(owldatatype, dr));
        add(SameIndividual(I, J));
        add(DifferentIndividuals(I, J));
        add(ClassAssertion(C, ai));
        add(ObjectPropertyAssertion(P, ai, ai));
        add(NegativeObjectPropertyAssertion(P, ai, ai));
        add(DataPropertyAssertion(DP, ai, LIT_TRUE));
        add(NegativeDataPropertyAssertion(DP, ai, LIT_TRUE));
        add(InverseObjectProperties(P, ObjectInverseOf(P)));
        add(SubClassOf(C, DataExactCardinality(1, DP)));
        add(SubClassOf(C, DataMaxCardinality(1, DP)));
        add(SubClassOf(C, DataMinCardinality(1, DP)));
        add(SubClassOf(C, ObjectExactCardinality(1, P, OWLThing())));
        add(SubClassOf(C, ObjectMaxCardinality(1, P, OWLThing())));
        add(SubClassOf(C, ObjectMinCardinality(1, P, OWLThing())));
        add(DataPropertyRange(DP, Datatype(prefixmanager.getIRI(string))));
        add(DataPropertyAssertion(DP, ai, Literal(string, owldatatype)));
        add(DataPropertyRange(DP, DataOneOf(LIT_TRUE)));
        add(DataPropertyRange(DP, DataUnionOf(dr)));
        add(DataPropertyRange(DP, DataIntersectionOf(dr)));
        add(DataPropertyRange(DP,
            DatatypeRestriction(owldatatype, FacetRestriction(OWLFacet.MIN_EXCLUSIVE, LIT_TRUE))));
        add(DataPropertyRange(DP,
            DatatypeRestriction(owldatatype, FacetRestriction(OWLFacet.MIN_EXCLUSIVE, LIT_ONE))));
        add(SubClassOf(C, ObjectIntersectionOf(C, Class(string, prefixmanager))));
        add(SubClassOf(C, DataSomeValuesFrom(DP, dr)));
        add(SubClassOf(C, DataAllValuesFrom(DP, dr)));
        add(SubClassOf(C, DataHasValue(DP, LIT_TRUE)));
        add(SubClassOf(C, ObjectComplementOf(Class(iriTest))));
        add(SubClassOf(C, ObjectOneOf(NamedIndividual(iriTest))));
        add(SubClassOf(C, ObjectAllValuesFrom(P, C)));
        add(SubClassOf(C, ObjectSomeValuesFrom(P, C)));
        add(SubClassOf(C, ObjectHasValue(P, ai)));
        add(SubClassOf(C, ObjectUnionOf(Class(iriTest))));
        add(AnnotationAssertion(AP, iriTest, LIT_TRUE));
        add(AnnotationAssertion(AP, NamedIndividual(iriTest).getIRI(), LIT_TRUE));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(m);
        stream.flush();
        // System.out.println(out.toString());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(in);
        OWLOntologyManager copy = (OWLOntologyManager) inStream.readObject();
        OWLOntology o1 = copy.getOntology(ontologyIRI);
        assertNotNull(o1);
        assertEquals(asSet(o.axioms()), asSet(o1.axioms()));
        assertEquals(o.getAxiomCount(), o1.getAxiomCount());
        assertEquals(o.getLogicalAxiomCount(), o1.getLogicalAxiomCount());
        assertEquals(asSet(o.annotations()), asSet(o1.annotations()));
        assertEquals(o.getOntologyID(), o1.getOntologyID());
    }

    protected void add(OWLAxiom ax) {
        o.add(ax);
    }
}
