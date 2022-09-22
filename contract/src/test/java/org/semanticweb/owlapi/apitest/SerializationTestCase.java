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
package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asSet;

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
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi.utility.AutoIRIMapper;
import org.semanticweb.owlapi.vocab.OWLFacet;

class SerializationTestCase extends TestBase {

    private final OWLDataRange dr = DatatypeRestriction(Integer());
    private final String string = "testString";
    private final PrefixManager prefixmanager = new PrefixManagerImpl();
    private final OWLIndividual ai = AnonymousIndividual();
    private final List<OWLObjectPropertyExpression> listowlobjectproperties =
        l(OBJPROPS.P, OBJPROPS.op1);
    protected OWLOntology o;
    IRI ontologyIRI;

    @BeforeEach
    void setUp() throws URISyntaxException {
        m.getIRIMappers().add(new AutoIRIMapper(new File("."), false, df));
        o = loadFrom(new File(getClass().getResource("/" + TestFilenames.PIZZA_OWL).toURI()), m);
        ontologyIRI = o.getOntologyID().getOntologyIRI().get();
    }

    @Test
    void testrun() throws IOException, ClassNotFoundException {
        add(Declaration(Class(IRIS.iriTest)));
        add(SubClassOf(CLASSES.C, Class(string, prefixmanager)));
        add(EquivalentClasses(Class(IRIS.iriTest), CLASSES.C));
        add(DisjointClasses(Class(IRIS.iriTest), CLASSES.C));
        add(SubObjectPropertyOf(OBJPROPS.op1, OBJPROPS.op2));
        add(SubPropertyChainOf(listowlobjectproperties, OBJPROPS.op1));
        add(EquivalentObjectProperties(OBJPROPS.op1, OBJPROPS.op2));
        add(DisjointObjectProperties(OBJPROPS.op1, OBJPROPS.op2));
        add(InverseObjectProperties(OBJPROPS.op1, OBJPROPS.op2));
        add(ObjectPropertyDomain(OBJPROPS.P, CLASSES.C));
        add(ObjectPropertyRange(OBJPROPS.P, CLASSES.C));
        add(FunctionalObjectProperty(OBJPROPS.P));
        add(AnnotationAssertion(ANNPROPS.AP, INDIVIDUALS.I.getIRI(), LITERALS.LIT_TRUE));
        o.applyChange(new AddImport(o, ImportsDeclaration(IRIS.iriTest)));
        add(AnnotationPropertyDomain(ANNPROPS.AP, IRIS.iriTest));
        add(AnnotationPropertyRange(ANNPROPS.AP, IRIS.iriTest));
        add(SubAnnotationPropertyOf(ANNPROPS.AP, ANNPROPS.AP));
        add(InverseFunctionalObjectProperty(OBJPROPS.P));
        add(ReflexiveObjectProperty(OBJPROPS.P));
        add(IrreflexiveObjectProperty(OBJPROPS.P));
        add(SymmetricObjectProperty(OBJPROPS.P));
        add(AsymmetricObjectProperty(OBJPROPS.P));
        add(TransitiveObjectProperty(OBJPROPS.P));
        add(SubDataPropertyOf(DATAPROPS.DP, DATAPROPS.DP));
        add(EquivalentDataProperties(DATAPROPS.dp1, DATAPROPS.dp2));
        add(DisjointDataProperties(DATAPROPS.dp1, DATAPROPS.dp2));
        add(DataPropertyDomain(DATAPROPS.DP, CLASSES.C));
        add(DataPropertyRange(DATAPROPS.DP, dr));
        add(FunctionalDataProperty(DATAPROPS.DP));
        add(HasKey(CLASSES.C, OBJPROPS.P, OBJPROPS.op1));
        add(DatatypeDefinition(DATATYPES.DT, dr));
        add(SameIndividual(INDIVIDUALS.I, INDIVIDUALS.J));
        add(DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.J));
        add(ClassAssertion(CLASSES.C, ai));
        add(ObjectPropertyAssertion(OBJPROPS.P, ai, ai));
        add(NegativeObjectPropertyAssertion(OBJPROPS.P, ai, ai));
        add(DataPropertyAssertion(DATAPROPS.DP, ai, LITERALS.LIT_TRUE));
        add(NegativeDataPropertyAssertion(DATAPROPS.DP, ai, LITERALS.LIT_TRUE));
        add(InverseObjectProperties(OBJPROPS.P, ObjectInverseOf(OBJPROPS.P)));
        add(SubClassOf(CLASSES.C, DataExactCardinality(1, DATAPROPS.DP)));
        add(SubClassOf(CLASSES.C, DataMaxCardinality(1, DATAPROPS.DP)));
        add(SubClassOf(CLASSES.C, DataMinCardinality(1, DATAPROPS.DP)));
        add(SubClassOf(CLASSES.C, ObjectExactCardinality(1, OBJPROPS.P, OWLThing())));
        add(SubClassOf(CLASSES.C, ObjectMaxCardinality(1, OBJPROPS.P, OWLThing())));
        add(SubClassOf(CLASSES.C, ObjectMinCardinality(1, OBJPROPS.P, OWLThing())));
        add(DataPropertyRange(DATAPROPS.DP, Datatype(prefixmanager.getIRI(string, df))));
        add(DataPropertyAssertion(DATAPROPS.DP, ai, Literal(string, DATATYPES.DT)));
        add(DataPropertyRange(DATAPROPS.DP, DataOneOf(LITERALS.LIT_TRUE)));
        add(DataPropertyRange(DATAPROPS.DP, DataUnionOf(dr)));
        add(DataPropertyRange(DATAPROPS.DP, DataIntersectionOf(dr)));
        add(DataPropertyRange(DATAPROPS.DP, DatatypeRestriction(DATATYPES.DT,
            FacetRestriction(OWLFacet.MIN_EXCLUSIVE, LITERALS.LIT_TRUE))));
        add(DataPropertyRange(DATAPROPS.DP, DatatypeRestriction(DATATYPES.DT,
            FacetRestriction(OWLFacet.MIN_EXCLUSIVE, LITERALS.LIT_ONE))));
        add(SubClassOf(CLASSES.C, ObjectIntersectionOf(CLASSES.C, Class(string, prefixmanager))));
        add(SubClassOf(CLASSES.C, DataSomeValuesFrom(DATAPROPS.DP, dr)));
        add(SubClassOf(CLASSES.C, DataAllValuesFrom(DATAPROPS.DP, dr)));
        add(SubClassOf(CLASSES.C, DataHasValue(DATAPROPS.DP, LITERALS.LIT_TRUE)));
        add(SubClassOf(CLASSES.C, ObjectComplementOf(Class(IRIS.iriTest))));
        add(SubClassOf(CLASSES.C, ObjectOneOf(NamedIndividual(IRIS.iriTest))));
        add(SubClassOf(CLASSES.C, ObjectAllValuesFrom(OBJPROPS.P, CLASSES.C)));
        add(SubClassOf(CLASSES.C, ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.C)));
        add(SubClassOf(CLASSES.C, ObjectHasValue(OBJPROPS.P, ai)));
        add(SubClassOf(CLASSES.C, ObjectUnionOf(Class(IRIS.iriTest))));
        add(AnnotationAssertion(ANNPROPS.AP, IRIS.iriTest, LITERALS.LIT_TRUE));
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
