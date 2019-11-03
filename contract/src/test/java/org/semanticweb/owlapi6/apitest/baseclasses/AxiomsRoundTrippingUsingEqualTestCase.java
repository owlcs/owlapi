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
package org.semanticweb.owlapi6.apitest.baseclasses;

import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;
import static org.semanticweb.owlapi6.apitest.TestEntities.Q;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@RunWith(Parameterized.class)
public class AxiomsRoundTrippingUsingEqualTestCase extends AxiomsRoundTrippingBase {

    public AxiomsRoundTrippingUsingEqualTestCase(AxiomBuilder f) {
        super(f);
    }

    @Override
    public void roundTripRDFXMLAndFunctionalShouldBeSame() throws OWLOntologyStorageException {
        OWLOntology ont = createOntology();
        OWLOntology o1 = roundTrip(ont, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(ont, new FunctionalSyntaxDocumentFormat());
        equal(o1, o2);
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        return Arrays.asList(
            // AnonymousIndividualRoundtrip
            () -> {
                List<OWLAxiom> axioms = new ArrayList<>();
                OWLAnonymousIndividual ind = AnonymousIndividual();
                OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
                OWLAnnotationAssertionAxiom ax = AnnotationAssertion(prop, A.getIRI(), ind);
                axioms.add(ax);
                axioms.add(Declaration(A));
                axioms.add(Declaration(P));
                OWLAnonymousIndividual anon1 = AnonymousIndividual();
                OWLAnonymousIndividual anon2 = AnonymousIndividual();
                OWLNamedIndividual ind1 = NamedIndividual(iri("j"));
                OWLNamedIndividual ind2 = NamedIndividual(iri("i"));
                axioms.add(df.getOWLObjectPropertyAssertionAxiom(P, ind1, ind2));
                axioms.add(df.getOWLObjectPropertyAssertionAxiom(P, anon1, anon1));
                axioms.add(df.getOWLObjectPropertyAssertionAxiom(P, anon2, ind2));
                axioms.add(df.getOWLObjectPropertyAssertionAxiom(P, ind2, anon2));
                return axioms;
            },
            // AnonymousIndividuals2
            () -> {
                // Originally submitted by Timothy Redmond
                String ns = "http://another.com/ont";
                OWLAnnotationProperty p = AnnotationProperty(IRI(ns + "#", "p"));
                OWLAnonymousIndividual h = AnonymousIndividual();
                OWLAnonymousIndividual i = AnonymousIndividual();
                List<OWLAxiom> axioms = new ArrayList<>();
                axioms.add(AnnotationAssertion(p, A.getIRI(), h));
                axioms.add(ClassAssertion(A, h));
                axioms.add(ObjectPropertyAssertion(Q, h, i));
                axioms.add(AnnotationAssertion(RDFSLabel(), h, Literal("Second", "en")));
                return axioms;
            },
            // AnonymousIndividuals
            () -> {
                List<OWLAxiom> axioms = new ArrayList<>();
                OWLAnonymousIndividual ind = AnonymousIndividual();
                axioms.add(ObjectPropertyAssertion(P, NamedIndividual(iri("i1")), ind));
                axioms.add(ObjectPropertyAssertion(P, ind, NamedIndividual(iri("i2"))));
                return axioms;
            },
            // ChainedAnonymousIndividuals
            () -> {
                List<OWLAxiom> axioms = new ArrayList<>();
                IRI annoPropIRI = IRI("http://owlapi.sourceforge.net/ontology#", "annoProp");
                OWLAnnotationProperty property = AnnotationProperty(annoPropIRI);
                IRI subject = IRI("http://owlapi.sourceforge.net/ontology#", "subject");
                axioms.add(Declaration(NamedIndividual(subject)));
                axioms.add(AnnotationAssertion(property, subject, AnonymousIndividual()));
                axioms.add(
                    AnnotationAssertion(property, AnonymousIndividual(), AnonymousIndividual()));
                axioms.add(
                    AnnotationAssertion(property, AnonymousIndividual(), AnonymousIndividual()));
                return axioms;
            },
            // ClassAssertionWithAnonymousIndividual
            () -> Arrays.asList(ClassAssertion(A, AnonymousIndividual("a")), Declaration(A)),
            // DifferentIndividualsAnonymous
            () -> Arrays.asList(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual(),
                AnonymousIndividual())),
            // DifferentIndividualsPairwiseAnonymous
            () -> Arrays.asList(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual())),
            // ObjectPropertyAssertionWithAnonymousIndividuals
            () -> Arrays.asList(
                ObjectPropertyAssertion(P, AnonymousIndividual(), AnonymousIndividual()),
                Declaration(P)),
            // SameIndividualsAnonymous
            // Can't round trip more than two in RDF! Also, same individuals
            // axiom
            // with anon individuals is not allowed in OWL 2, but it should at
            // least round trip
            () -> Arrays.asList(SameIndividual(AnonymousIndividual(), AnonymousIndividual())));
    }
}
