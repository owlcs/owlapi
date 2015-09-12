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
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings({ "javadoc" })
public class SerializationTestCase extends TestBase {

    private final @Nonnull OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    private final @Nonnull OWLDataProperty dp = df.getOWLDataProperty("urn:dp");
    private final @Nonnull OWLObjectProperty op = df.getOWLObjectProperty("urn:op");
    private final @Nonnull IRI iri = IRI.create("urn:iri");
    private final @Nonnull OWLLiteral owlliteral = df.getOWLLiteral(true);
    private final @Nonnull OWLAnnotationSubject as = IRI.create("urn:i");
    private final @Nonnull OWLDatatype owldatatype = df.getOWLDatatype(owl2datatype);
    private final @Nonnull OWLDataRange dr = df.getOWLDatatypeRestriction(owldatatype);
    private final @Nonnull OWLAnnotationProperty ap = df.getOWLAnnotationProperty("urn:ap");
    private final @Nonnull OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
    private final @Nonnull String string = "testString";
    private final @Nonnull OWLClassExpression c = df.getOWLClass("urn:classexpression");
    private final @Nonnull PrefixManager prefixmanager = new DefaultPrefixManager();
    private final @Nonnull OWLIndividual ai = df.getOWLAnonymousIndividual();
    private final @Nonnull OWLAnnotationValue owlannotationvalue = owlliteral;
    private final @Nonnull Set<OWLObjectPropertyExpression> setop = new HashSet<>();
    private final @Nonnull Set<OWLDataPropertyExpression> setdp = new HashSet<>();
    private final @Nonnull List<OWLObjectPropertyExpression> listowlobjectproperties = new ArrayList<>();
    private final @Nonnull Set<OWLIndividual> setowlindividual = new HashSet<>();
    private final @Nonnull Set<OWLPropertyExpression> setowlpropertyexpression = new HashSet<>();

    @SuppressWarnings("null")
    @Test
    public void testrun() throws Exception {
        m.getIRIMappers().set(new AutoIRIMapper(new File("."), false));
        OWLOntology o = m.loadOntologyFromOntologyDocument(getClass().getResourceAsStream("/pizza.owl"));
        o.applyChange(new AddImport(o, df.getOWLImportsDeclaration(iri)));
        o.add(df.getOWLDeclarationAxiom(df.getOWLClass(iri)),
            sub(c, df.getOWLClass(string, prefixmanager)),
            df.getOWLEquivalentClassesAxiom(df.getOWLClass(iri), c),
            df.getOWLDisjointClassesAxiom(df.getOWLClass(iri), c),
            df.getOWLSubObjectPropertyOfAxiom(op, op),
            df.getOWLSubPropertyChainOfAxiom(listowlobjectproperties, op),
            df.getOWLEquivalentObjectPropertiesAxiom(setop),
            df.getOWLDisjointObjectPropertiesAxiom(setop),
            df.getOWLInverseObjectPropertiesAxiom(op, op),
            df.getOWLObjectPropertyDomainAxiom(op, c),
            df.getOWLObjectPropertyRangeAxiom(op, c),
            df.getOWLFunctionalObjectPropertyAxiom(op),
            df.getOWLAnnotationAssertionAxiom(ap, as, owlannotationvalue),
            df.getOWLAnnotationPropertyDomainAxiom(ap, iri),
            df.getOWLAnnotationPropertyRangeAxiom(ap, iri),
            df.getOWLSubAnnotationPropertyOfAxiom(ap, ap),
            df.getOWLInverseFunctionalObjectPropertyAxiom(op),
            df.getOWLReflexiveObjectPropertyAxiom(op),
            df.getOWLIrreflexiveObjectPropertyAxiom(op),
            df.getOWLSymmetricObjectPropertyAxiom(op),
            df.getOWLAsymmetricObjectPropertyAxiom(op),
            df.getOWLTransitiveObjectPropertyAxiom(op),
            df.getOWLSubDataPropertyOfAxiom(dp, dp),
            df.getOWLEquivalentDataPropertiesAxiom(setdp),
            df.getOWLDisjointDataPropertiesAxiom(setdp), df.getOWLDataPropertyDomainAxiom(dp, c),
            df.getOWLDataPropertyRangeAxiom(dp, dr),
            df.getOWLFunctionalDataPropertyAxiom(dp),
            df.getOWLHasKeyAxiom(c, setowlpropertyexpression),
            df.getOWLDatatypeDefinitionAxiom(owldatatype, dr),
            df.getOWLSameIndividualAxiom(setowlindividual),
            df.getOWLDifferentIndividualsAxiom(setowlindividual),
            df.getOWLClassAssertionAxiom(c, ai),
            df.getOWLObjectPropertyAssertionAxiom(op, ai, ai),
            df.getOWLNegativeObjectPropertyAssertionAxiom(op, ai, ai),
            df.getOWLDataPropertyAssertionAxiom(dp, ai, owlliteral),
            df.getOWLNegativeDataPropertyAssertionAxiom(dp, ai, owlliteral),
            df.getOWLInverseObjectPropertiesAxiom(op, df.getOWLObjectInverseOf(op)),
            sub(c, df.getOWLDataExactCardinality(1, dp)),
            sub(c, df.getOWLDataMaxCardinality(1, dp)),
            sub(c, df.getOWLDataMinCardinality(1, dp)),
            sub(c, df.getOWLObjectExactCardinality(1, op)),
            sub(c, df.getOWLObjectMaxCardinality(1, op)),
            sub(c, df.getOWLObjectMinCardinality(1, op)),
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDatatype(string, prefixmanager)),
            df.getOWLDataPropertyAssertionAxiom(dp, ai, df.getOWLLiteral(string, owldatatype)),
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDataOneOf(owlliteral)),
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDataUnionOf(dr)),
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDataIntersectionOf(dr)),
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDatatypeRestriction(owldatatype, owlfacet, owlliteral)),
            df.getOWLDataPropertyRangeAxiom(dp, df.getOWLDatatypeRestriction(owldatatype, df.getOWLFacetRestriction(
                owlfacet, 1))),
            sub(c, df.getOWLObjectIntersectionOf(c, df.getOWLClass(string, prefixmanager))),
            sub(c, df.getOWLDataSomeValuesFrom(dp, dr)),
            sub(c, df.getOWLDataAllValuesFrom(dp, dr)),
            sub(c, df.getOWLDataHasValue(dp, owlliteral)),
            sub(c, df.getOWLObjectComplementOf(df.getOWLClass(iri))),
            sub(c, df.getOWLObjectOneOf(df.getOWLNamedIndividual(iri))),
            sub(c, df.getOWLObjectAllValuesFrom(op, c)),
            sub(c, df.getOWLObjectSomeValuesFrom(op, c)),
            sub(c, df.getOWLObjectHasValue(op, ai)),
            sub(c, df.getOWLObjectUnionOf(df.getOWLClass(iri))),
            df.getOWLAnnotationAssertionAxiom(iri, df.getOWLAnnotation(ap, owlannotationvalue)),
            df.getOWLAnnotationAssertionAxiom(df.getOWLNamedIndividual(iri).getIRI(), df.getOWLAnnotation(ap,
                owlannotationvalue)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(m);
        stream.flush();
        // System.out.println(out.toString());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(in);
        OWLOntologyManager copy = (OWLOntologyManager) inStream.readObject();
        copy.ontologies().forEach(ont -> assertEquals("Troubles with ontology " + ont.getOntologyID(), asSet(m
            .getOntology(get(ont.getOntologyID().getOntologyIRI())).axioms()), asSet(ont.axioms())));
    }

    protected OWLAxiom sub(OWLClassExpression cl, OWLClassExpression d) {
        return df.getOWLSubClassOfAxiom(cl, d);
    }
}
