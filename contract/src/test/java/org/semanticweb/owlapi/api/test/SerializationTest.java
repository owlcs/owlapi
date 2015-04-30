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
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
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

@SuppressWarnings({ "javadoc" })
public class SerializationTest extends TestBase {

    @Nonnull
    OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    @Nonnull
    OWLDataPropertyExpression dp = df.getOWLDataProperty("urn:dp");
    @Nonnull
    OWLObjectPropertyExpression op = df.getOWLObjectProperty("urn:op");
    @Nonnull
    IRI iri = IRI.create("urn:iri");
    @Nonnull
    OWLLiteral owlliteral = df.getOWLLiteral(true);
    @Nonnull
    OWLAnnotationSubject as = IRI.create("urn:i");
    @Nonnull
    OWLDatatype owldatatype = df.getOWLDatatype(owl2datatype);
    @Nonnull
    OWLDataRange dr = df.getOWLDatatypeRestriction(owldatatype);
    @Nonnull
    OWLAnnotationProperty ap = df.getOWLAnnotationProperty("urn:ap");
    @Nonnull
    OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
    @Nonnull
    OWLAnnotation owlannotation = df.getOWLAnnotation(ap, owlliteral);
    @Nonnull
    String string = "testString";
    @Nonnull
    OWLClassExpression c = df.getOWLClass("urn:classexpression");
    @Nonnull
    PrefixManager prefixmanager = new DefaultPrefixManager();
    @Nonnull
    OWLIndividual ai = df.getOWLAnonymousIndividual();
    @Nonnull
    OWLAnnotationValue owlannotationvalue = owlliteral;
    @Nonnull
    Set<OWLObjectPropertyExpression> setop = new HashSet<>();
    @Nonnull
    Set<OWLAnnotation> setowlannotation = new HashSet<>();
    @Nonnull
    Set<OWLDataPropertyExpression> setdp = new HashSet<>();
    @Nonnull
    List<OWLObjectPropertyExpression> listowlobjectpropertyexpression = new ArrayList<>();
    @Nonnull
    Set<OWLIndividual> setowlindividual = new HashSet<>();
    @Nonnull
    Set<OWLPropertyExpression> setowlpropertyexpression = new HashSet<>();
    @Nonnull
    OWLFacetRestriction[] lowlfacetrestriction = {
        df.getOWLFacetRestriction(owlfacet, 1) };
    @Nonnull
    OWLFacetRestriction[] nulllowlfacetrestriction = {
        df.getOWLFacetRestriction(owlfacet, 1) };
    @Nonnull
    Set<OWLClassExpression> setowlclassexpression = new HashSet<>();
    @Nonnull
    Set<OWLFacetRestriction> setowlfacetrestriction = new HashSet<>();
    @Nonnull
    OWLPropertyExpression[] owlpropertyexpression = {};

    @Test
    public void testrun() throws Exception {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getIRIMappers().add(new AutoIRIMapper(new File("."), false));
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            getClass().getResourceAsStream("/pizza.owl"));
        m.addAxiom(o, df.getOWLDeclarationAxiom(df.getOWLClass(iri)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLClass(string, prefixmanager)));
        m.addAxiom(o, df.getOWLEquivalentClassesAxiom(df.getOWLClass(iri), c));
        m.addAxiom(o, df.getOWLDisjointClassesAxiom(df.getOWLClass(iri), c));
        m.addAxiom(o, df.getOWLSubObjectPropertyOfAxiom(op, op));
        m.addAxiom(o, df.getOWLSubPropertyChainOfAxiom(
            listowlobjectpropertyexpression, op));
        m.addAxiom(o, df.getOWLEquivalentObjectPropertiesAxiom(setop));
        m.addAxiom(o, df.getOWLDisjointObjectPropertiesAxiom(setop));
        m.addAxiom(o, df.getOWLInverseObjectPropertiesAxiom(op, op));
        m.addAxiom(o, df.getOWLObjectPropertyDomainAxiom(op, c));
        m.addAxiom(o, df.getOWLObjectPropertyRangeAxiom(op, c));
        m.addAxiom(o, df.getOWLFunctionalObjectPropertyAxiom(op));
        m.addAxiom(o,
            df.getOWLAnnotationAssertionAxiom(ap, as, owlannotationvalue));
        m.applyChange(new AddImport(o, df.getOWLImportsDeclaration(iri)));
        m.addAxiom(o, df.getOWLAnnotationPropertyDomainAxiom(ap, iri));
        m.addAxiom(o, df.getOWLAnnotationPropertyRangeAxiom(ap, iri));
        m.addAxiom(o, df.getOWLSubAnnotationPropertyOfAxiom(ap, ap));
        m.addAxiom(o, df.getOWLInverseFunctionalObjectPropertyAxiom(op));
        m.addAxiom(o, df.getOWLReflexiveObjectPropertyAxiom(op));
        m.addAxiom(o, df.getOWLIrreflexiveObjectPropertyAxiom(op));
        m.addAxiom(o, df.getOWLSymmetricObjectPropertyAxiom(op));
        m.addAxiom(o, df.getOWLAsymmetricObjectPropertyAxiom(op));
        m.addAxiom(o, df.getOWLTransitiveObjectPropertyAxiom(op));
        m.addAxiom(o, df.getOWLSubDataPropertyOfAxiom(dp, dp));
        m.addAxiom(o, df.getOWLEquivalentDataPropertiesAxiom(setdp));
        m.addAxiom(o, df.getOWLDisjointDataPropertiesAxiom(setdp));
        m.addAxiom(o, df.getOWLDataPropertyDomainAxiom(dp, c));
        m.addAxiom(o, df.getOWLDataPropertyRangeAxiom(dp, dr));
        m.addAxiom(o, df.getOWLFunctionalDataPropertyAxiom(dp));
        m.addAxiom(o, df.getOWLHasKeyAxiom(c, setowlpropertyexpression));
        m.addAxiom(o, df.getOWLDatatypeDefinitionAxiom(owldatatype, dr));
        m.addAxiom(o, df.getOWLSameIndividualAxiom(setowlindividual));
        m.addAxiom(o, df.getOWLDifferentIndividualsAxiom(setowlindividual));
        m.addAxiom(o, df.getOWLClassAssertionAxiom(c, ai));
        m.addAxiom(o, df.getOWLObjectPropertyAssertionAxiom(op, ai, ai));
        m.addAxiom(o,
            df.getOWLNegativeObjectPropertyAssertionAxiom(op, ai, ai));
        m.addAxiom(o, df.getOWLDataPropertyAssertionAxiom(dp, ai, owlliteral));
        m.addAxiom(o,
            df.getOWLNegativeDataPropertyAssertionAxiom(dp, ai, owlliteral));
        m.addAxiom(o, df.getOWLInverseObjectPropertiesAxiom(op,
            df.getOWLObjectInverseOf(op)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLDataExactCardinality(1, dp)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLDataMaxCardinality(1, dp)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLDataMinCardinality(1, dp)));
        m.addAxiom(o, df.getOWLSubClassOfAxiom(c,
            df.getOWLObjectExactCardinality(1, op)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLObjectMaxCardinality(1, op)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLObjectMinCardinality(1, op)));
        m.addAxiom(o, df.getOWLDataPropertyRangeAxiom(dp,
            df.getOWLDatatype(string, prefixmanager)));
        m.addAxiom(o, df.getOWLDataPropertyAssertionAxiom(dp, ai,
            df.getOWLLiteral(string, owldatatype)));
        m.addAxiom(o, df.getOWLDataPropertyRangeAxiom(dp,
            df.getOWLDataOneOf(owlliteral)));
        m.addAxiom(o,
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDataUnionOf(dr)));
        m.addAxiom(o, df.getOWLDataPropertyRangeAxiom(dp,
            df.getOWLDataIntersectionOf(dr)));
        m.addAxiom(o, df.getOWLDataPropertyRangeAxiom(dp,
            df.getOWLDatatypeRestriction(owldatatype, owlfacet, owlliteral)));
        m.addAxiom(o,
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDatatypeRestriction(
                owldatatype, df.getOWLFacetRestriction(owlfacet, 1))));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLObjectIntersectionOf(c,
                df.getOWLClass(string, prefixmanager))));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLDataSomeValuesFrom(dp, dr)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLDataAllValuesFrom(dp, dr)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLDataHasValue(dp, owlliteral)));
        m.addAxiom(o, df.getOWLSubClassOfAxiom(c,
            df.getOWLObjectComplementOf(df.getOWLClass(iri))));
        m.addAxiom(o, df.getOWLSubClassOfAxiom(c,
            df.getOWLObjectOneOf(df.getOWLNamedIndividual(iri))));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLObjectAllValuesFrom(op, c)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLObjectSomeValuesFrom(op, c)));
        m.addAxiom(o,
            df.getOWLSubClassOfAxiom(c, df.getOWLObjectHasValue(op, ai)));
        m.addAxiom(o, df.getOWLSubClassOfAxiom(c,
            df.getOWLObjectUnionOf(df.getOWLClass(iri))));
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(iri,
            df.getOWLAnnotation(ap, owlannotationvalue)));
        m.addAxiom(o,
            df.getOWLAnnotationAssertionAxiom(
                df.getOWLNamedIndividual(iri).getIRI(),
                df.getOWLAnnotation(ap, owlannotationvalue)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(m);
        stream.flush();
        // System.out.println(out.toString());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(in);
        OWLOntologyManager copy = (OWLOntologyManager) inStream.readObject();
        copy.ontologies().forEach(
            ont -> assertEquals("Troubles with ontology " + ont.getOntologyID(),
                asSet(m.getOntology(get(ont.getOntologyID().getOntologyIRI()))
                    .axioms()),
                asSet(ont.axioms())));
    }
}
