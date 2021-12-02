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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

class SerializationTestCase extends TestBase {

    private static final String NS = "urn:test#";
    private final OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    private final IRI iri = iri(NS, "iri");
    private final OWLLiteral owlliteral = df.getOWLLiteral(true);
    private final OWLAnnotationSubject as = iri(NS, "i");
    private final OWLDatatype owldatatype = df.getOWLDatatype(owl2datatype.getIRI());
    private final OWLDataRange dr = df.getOWLDatatypeRestriction(owldatatype);
    private final OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
    private final String string = "testString";
    private final PrefixManager prefixmanager = new DefaultPrefixManager();
    private final OWLIndividual ai = df.getOWLAnonymousIndividual();
    private final OWLIndividual i1 = df.getOWLNamedIndividual(iri(NS, "i1"));
    private final OWLIndividual i2 = df.getOWLNamedIndividual(iri(NS, "i2"));
    private final OWLAnnotationValue owlannotationvalue = owlliteral;
    private final Set<OWLObjectPropertyExpression> setop = set(op1, op2);
    private final Set<OWLDataPropertyExpression> setdp = set(dp1, dp2);
    private final List<OWLObjectPropertyExpression> listowlobjectproperties = Arrays.asList(P, op1);
    private final Set<OWLIndividual> setowlindividual = set(i1, i2);
    private final Set<OWLPropertyExpression> setowlpropertyexpression = set(P, op1);
    protected OWLOntology o;
    IRI ontologyIRI;

    @BeforeEach
    void setUp() throws URISyntaxException {
        m.getIRIMappers().add(new AutoIRIMapper(new File("."), false));
        o = loadOntologyFromFile(new File(getClass().getResource("/pizza.owl").toURI()), m);
        ontologyIRI = o.getOntologyID().getOntologyIRI().get();
    }

    @Test
    void testrun() throws IOException, ClassNotFoundException {
        add(df.getOWLDeclarationAxiom(df.getOWLClass(iri)));
        add(sub(C, df.getOWLClass(string, prefixmanager)));
        add(df.getOWLEquivalentClassesAxiom(df.getOWLClass(iri), C));
        add(df.getOWLDisjointClassesAxiom(df.getOWLClass(iri), C));
        add(df.getOWLSubObjectPropertyOfAxiom(op1, op2));
        add(df.getOWLSubPropertyChainOfAxiom(listowlobjectproperties, op1));
        add(df.getOWLEquivalentObjectPropertiesAxiom(setop));
        add(df.getOWLDisjointObjectPropertiesAxiom(setop));
        add(df.getOWLInverseObjectPropertiesAxiom(op1, op2));
        add(df.getOWLObjectPropertyDomainAxiom(P, C));
        add(df.getOWLObjectPropertyRangeAxiom(P, C));
        add(df.getOWLFunctionalObjectPropertyAxiom(P));
        add(df.getOWLAnnotationAssertionAxiom(AP, as, owlannotationvalue));
        m.applyChange(new AddImport(o, df.getOWLImportsDeclaration(iri)));
        add(df.getOWLAnnotationPropertyDomainAxiom(AP, iri));
        add(df.getOWLAnnotationPropertyRangeAxiom(AP, iri));
        add(df.getOWLSubAnnotationPropertyOfAxiom(AP, AP));
        add(df.getOWLInverseFunctionalObjectPropertyAxiom(P));
        add(df.getOWLReflexiveObjectPropertyAxiom(P));
        add(df.getOWLIrreflexiveObjectPropertyAxiom(P));
        add(df.getOWLSymmetricObjectPropertyAxiom(P));
        add(df.getOWLAsymmetricObjectPropertyAxiom(P));
        add(df.getOWLTransitiveObjectPropertyAxiom(P));
        add(df.getOWLSubDataPropertyOfAxiom(DP, DP));
        add(df.getOWLEquivalentDataPropertiesAxiom(setdp));
        add(df.getOWLDisjointDataPropertiesAxiom(setdp));
        add(df.getOWLDataPropertyDomainAxiom(DP, C));
        add(df.getOWLDataPropertyRangeAxiom(DP, dr));
        add(df.getOWLFunctionalDataPropertyAxiom(DP));
        add(df.getOWLHasKeyAxiom(C, setowlpropertyexpression));
        add(df.getOWLDatatypeDefinitionAxiom(owldatatype, dr));
        add(df.getOWLSameIndividualAxiom(setowlindividual));
        add(df.getOWLDifferentIndividualsAxiom(setowlindividual));
        add(df.getOWLClassAssertionAxiom(C, ai));
        add(df.getOWLObjectPropertyAssertionAxiom(P, ai, ai));
        add(df.getOWLNegativeObjectPropertyAssertionAxiom(P, ai, ai));
        add(df.getOWLDataPropertyAssertionAxiom(DP, ai, owlliteral));
        add(df.getOWLNegativeDataPropertyAssertionAxiom(DP, ai, owlliteral));
        add(df.getOWLInverseObjectPropertiesAxiom(P, df.getOWLObjectInverseOf(P)));
        add(sub(C, df.getOWLDataExactCardinality(1, DP)));
        add(sub(C, df.getOWLDataMaxCardinality(1, DP)));
        add(sub(C, df.getOWLDataMinCardinality(1, DP)));
        add(sub(C, df.getOWLObjectExactCardinality(1, P)));
        add(sub(C, df.getOWLObjectMaxCardinality(1, P)));
        add(sub(C, df.getOWLObjectMinCardinality(1, P)));
        add(df.getOWLDataPropertyRangeAxiom(DP, df.getOWLDatatype(string, prefixmanager)));
        add(df.getOWLDataPropertyAssertionAxiom(DP, ai, df.getOWLLiteral(string, owldatatype)));
        add(df.getOWLDataPropertyRangeAxiom(DP, df.getOWLDataOneOf(owlliteral)));
        add(df.getOWLDataPropertyRangeAxiom(DP, df.getOWLDataUnionOf(dr)));
        add(df.getOWLDataPropertyRangeAxiom(DP, df.getOWLDataIntersectionOf(dr)));
        add(df.getOWLDataPropertyRangeAxiom(DP,
            df.getOWLDatatypeRestriction(owldatatype, owlfacet, owlliteral)));
        add(df.getOWLDataPropertyRangeAxiom(DP,
            df.getOWLDatatypeRestriction(owldatatype, df.getOWLFacetRestriction(owlfacet, 1))));
        add(sub(C, df.getOWLObjectIntersectionOf(C, df.getOWLClass(string, prefixmanager))));
        add(sub(C, df.getOWLDataSomeValuesFrom(DP, dr)));
        add(sub(C, df.getOWLDataAllValuesFrom(DP, dr)));
        add(sub(C, df.getOWLDataHasValue(DP, owlliteral)));
        add(sub(C, df.getOWLObjectComplementOf(df.getOWLClass(iri))));
        add(sub(C, df.getOWLObjectOneOf(df.getOWLNamedIndividual(iri))));
        add(sub(C, df.getOWLObjectAllValuesFrom(P, C)));
        add(sub(C, df.getOWLObjectSomeValuesFrom(P, C)));
        add(sub(C, df.getOWLObjectHasValue(P, ai)));
        add(sub(C, df.getOWLObjectUnionOf(df.getOWLClass(iri))));
        add(df.getOWLAnnotationAssertionAxiom(iri, df.getOWLAnnotation(AP, owlannotationvalue)));
        add(df.getOWLAnnotationAssertionAxiom(df.getOWLNamedIndividual(iri).getIRI(),
            df.getOWLAnnotation(AP, owlannotationvalue)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(m);
        stream.flush();
        // System.out.println(out.toString());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(in);
        OWLOntologyManager copy = (OWLOntologyManager) inStream.readObject();
        OWLOntology o1 = copy.getOntology(ontologyIRI);
        assertEquals(o.getAxioms(), o1.getAxioms());
        assertEquals(o.getAxiomCount(), o1.getAxiomCount());
        assertEquals(o.getLogicalAxiomCount(), o1.getLogicalAxiomCount());
        assertEquals(o.getAnnotations(), o1.getAnnotations());
        assertEquals(o.getOntologyID(), o1.getOntologyID());
    }

    protected void add(OWLAxiom ax) {
        m.addAxiom(o, ax);
    }

    protected OWLAxiom sub(OWLClassExpression cl, OWLClassExpression d) {
        return df.getOWLSubClassOfAxiom(cl, d);
    }
}
