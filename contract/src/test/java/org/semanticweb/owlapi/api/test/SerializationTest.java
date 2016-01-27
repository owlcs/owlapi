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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings({ "javadoc", "null" })
public class SerializationTest extends TestBase {

    @Nonnull private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();
    @Nonnull OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    @Nonnull OWLDataPropertyExpression dp = DF.getOWLDataProperty(IRI.create("urn:dp"));
    @Nonnull OWLObjectPropertyExpression op = DF.getOWLObjectProperty(IRI.create("urn:op"));
    @Nonnull IRI iri = IRI.create("urn:iri");
    @Nonnull OWLLiteral owlliteral = DF.getOWLLiteral(true);
    @Nonnull OWLAnnotationSubject as = IRI.create("urn:i");
    @Nonnull OWLDatatype owldatatype = DF.getOWLDatatype(owl2datatype.getIRI());
    @Nonnull OWLDataRange dr = DF.getOWLDatatypeRestriction(owldatatype);
    @Nonnull OWLAnnotationProperty ap = DF.getOWLAnnotationProperty(IRI.create("urn:ap"));
    @Nonnull OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
    @Nonnull OWLAnnotation owlannotation = DF.getOWLAnnotation(ap, owlliteral);
    @Nonnull String string = "testString";
    @Nonnull OWLClassExpression c = DF.getOWLClass(IRI.create("urn:classexpression"));
    @Nonnull PrefixManager prefixmanager = new DefaultPrefixManager();
    @Nonnull OWLIndividual ai = DF.getOWLAnonymousIndividual();
    @Nonnull OWLAnnotationValue owlannotationvalue = owlliteral;
    @Nonnull Set<OWLObjectPropertyExpression> setop = new HashSet<>();
    @Nonnull Set<OWLAnnotation> setowlannotation = new HashSet<>();
    @Nonnull Set<OWLDataPropertyExpression> setdp = new HashSet<>();
    @Nonnull List<OWLObjectPropertyExpression> listowlobjectpropertyexpression = new ArrayList<>();
    @Nonnull Set<OWLIndividual> setowlindividual = new HashSet<>();
    @Nonnull Set<OWLPropertyExpression> setowlpropertyexpression = new HashSet<>();
    @Nonnull OWLFacetRestriction[] lowlfacetrestriction = { DF.getOWLFacetRestriction(owlfacet, 1) };
    @Nonnull OWLFacetRestriction[] nulllowlfacetrestriction = { DF.getOWLFacetRestriction(owlfacet, 1) };
    @Nonnull Set<OWLClassExpression> setowlclassexpression = new HashSet<>();
    @Nonnull Set<OWLFacetRestriction> setowlfacetrestriction = new HashSet<>();
    @Nonnull OWLPropertyExpression[] owlpropertyexpression = {};

    @Test
    public void testrun() throws Exception {
        m.getIRIMappers().add(new AutoIRIMapper(new File("."), false));
        OWLOntology o = m.loadOntologyFromOntologyDocument(getClass().getResourceAsStream("/pizza.owl"));
        m.addAxiom(o, DF.getOWLDeclarationAxiom(DF.getOWLClass(iri)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLClass(string, prefixmanager)));
        m.addAxiom(o, DF.getOWLEquivalentClassesAxiom(DF.getOWLClass(iri), c));
        m.addAxiom(o, DF.getOWLDisjointClassesAxiom(DF.getOWLClass(iri), c));
        m.addAxiom(o, DF.getOWLSubObjectPropertyOfAxiom(op, op));
        m.addAxiom(o, DF.getOWLSubPropertyChainOfAxiom(listowlobjectpropertyexpression, op));
        m.addAxiom(o, DF.getOWLEquivalentObjectPropertiesAxiom(setop));
        m.addAxiom(o, DF.getOWLDisjointObjectPropertiesAxiom(setop));
        m.addAxiom(o, DF.getOWLInverseObjectPropertiesAxiom(op, op));
        m.addAxiom(o, DF.getOWLObjectPropertyDomainAxiom(op, c));
        m.addAxiom(o, DF.getOWLObjectPropertyRangeAxiom(op, c));
        m.addAxiom(o, DF.getOWLFunctionalObjectPropertyAxiom(op));
        m.addAxiom(o, DF.getOWLAnnotationAssertionAxiom(ap, as, owlannotationvalue));
        m.applyChange(new AddImport(o, DF.getOWLImportsDeclaration(iri)));
        m.addAxiom(o, DF.getOWLAnnotationPropertyDomainAxiom(ap, iri));
        m.addAxiom(o, DF.getOWLAnnotationPropertyRangeAxiom(ap, iri));
        m.addAxiom(o, DF.getOWLSubAnnotationPropertyOfAxiom(ap, ap));
        m.addAxiom(o, DF.getOWLInverseFunctionalObjectPropertyAxiom(op));
        m.addAxiom(o, DF.getOWLReflexiveObjectPropertyAxiom(op));
        m.addAxiom(o, DF.getOWLIrreflexiveObjectPropertyAxiom(op));
        m.addAxiom(o, DF.getOWLSymmetricObjectPropertyAxiom(op));
        m.addAxiom(o, DF.getOWLAsymmetricObjectPropertyAxiom(op));
        m.addAxiom(o, DF.getOWLTransitiveObjectPropertyAxiom(op));
        m.addAxiom(o, DF.getOWLSubDataPropertyOfAxiom(dp, dp));
        m.addAxiom(o, DF.getOWLEquivalentDataPropertiesAxiom(setdp));
        m.addAxiom(o, DF.getOWLDisjointDataPropertiesAxiom(setdp));
        m.addAxiom(o, DF.getOWLDataPropertyDomainAxiom(dp, c));
        m.addAxiom(o, DF.getOWLDataPropertyRangeAxiom(dp, dr));
        m.addAxiom(o, DF.getOWLFunctionalDataPropertyAxiom(dp));
        m.addAxiom(o, DF.getOWLHasKeyAxiom(c, setowlpropertyexpression));
        m.addAxiom(o, DF.getOWLDatatypeDefinitionAxiom(owldatatype, dr));
        m.addAxiom(o, DF.getOWLSameIndividualAxiom(setowlindividual));
        m.addAxiom(o, DF.getOWLDifferentIndividualsAxiom(setowlindividual));
        m.addAxiom(o, DF.getOWLClassAssertionAxiom(c, ai));
        m.addAxiom(o, DF.getOWLObjectPropertyAssertionAxiom(op, ai, ai));
        m.addAxiom(o, DF.getOWLNegativeObjectPropertyAssertionAxiom(op, ai, ai));
        m.addAxiom(o, DF.getOWLDataPropertyAssertionAxiom(dp, ai, owlliteral));
        m.addAxiom(o, DF.getOWLNegativeDataPropertyAssertionAxiom(dp, ai, owlliteral));
        m.addAxiom(o, DF.getOWLInverseObjectPropertiesAxiom(op, DF.getOWLObjectInverseOf(op)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLDataExactCardinality(1, dp)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLDataMaxCardinality(1, dp)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLDataMinCardinality(1, dp)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectExactCardinality(1, op)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectMaxCardinality(1, op)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectMinCardinality(1, op)));
        m.addAxiom(o, DF.getOWLDataPropertyRangeAxiom(dp, DF.getOWLDatatype(string, prefixmanager)));
        m.addAxiom(o, DF.getOWLDataPropertyAssertionAxiom(dp, ai, DF.getOWLLiteral(string, owldatatype)));
        m.addAxiom(o, DF.getOWLDataPropertyRangeAxiom(dp, DF.getOWLDataOneOf(owlliteral)));
        m.addAxiom(o, DF.getOWLDataPropertyRangeAxiom(dp, DF.getOWLDataUnionOf(dr)));
        m.addAxiom(o, DF.getOWLDataPropertyRangeAxiom(dp, DF.getOWLDataIntersectionOf(dr)));
        m.addAxiom(o, DF.getOWLDataPropertyRangeAxiom(dp, DF.getOWLDatatypeRestriction(owldatatype, owlfacet,
            owlliteral)));
        m.addAxiom(o, DF.getOWLDataPropertyRangeAxiom(dp, DF.getOWLDatatypeRestriction(owldatatype, DF
            .getOWLFacetRestriction(owlfacet, 1))));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectIntersectionOf(c, DF.getOWLClass(string,
            prefixmanager))));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLDataSomeValuesFrom(dp, dr)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLDataAllValuesFrom(dp, dr)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLDataHasValue(dp, owlliteral)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectComplementOf(DF.getOWLClass(iri))));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectOneOf(DF.getOWLNamedIndividual(iri))));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectAllValuesFrom(op, c)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectSomeValuesFrom(op, c)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectHasValue(op, ai)));
        m.addAxiom(o, DF.getOWLSubClassOfAxiom(c, DF.getOWLObjectUnionOf(DF.getOWLClass(iri))));
        m.addAxiom(o, DF.getOWLAnnotationAssertionAxiom(iri, DF.getOWLAnnotation(ap, owlannotationvalue)));
        m.addAxiom(o, DF.getOWLAnnotationAssertionAxiom(DF.getOWLNamedIndividual(iri).getIRI(), DF.getOWLAnnotation(ap,
            owlannotationvalue)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(m);
        stream.flush();
        // System.out.println(out.toString());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(in);
        OWLOntologyManager copy = (OWLOntologyManager) inStream.readObject();
        for (OWLOntology onto : copy.getOntologies()) {
            OWLOntology original = m.getOntology(onto.getOntologyID().getOntologyIRI().get());
            assertEquals("Troubles with ontology " + onto.getOntologyID(), original.getAxioms(), onto.getAxioms());
        }
    }
}
