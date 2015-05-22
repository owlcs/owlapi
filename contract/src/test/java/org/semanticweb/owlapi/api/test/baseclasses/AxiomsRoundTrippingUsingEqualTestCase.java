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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class AxiomsRoundTrippingUsingEqualTestCase extends AxiomsRoundTrippingBase {

    public AxiomsRoundTrippingUsingEqualTestCase(AxiomBuilder f) {
        super(f);
    }

    @Override
    public void roundTripRDFXMLAndFunctionalShouldBeSame()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
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
                    Set<OWLAxiom> axioms = new HashSet<>();
                    OWLAnonymousIndividual ind = AnonymousIndividual();
                    OWLClass cls = Class(iri("A"));
                    OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
                    OWLAnnotationAssertionAxiom ax = AnnotationAssertion(prop, cls.getIRI(), ind);
                    axioms.add(ax);
                    axioms.add(Declaration(cls));
                    return axioms;
                } ,
                // AnonymousIndividuals2
                () -> {
                    // Originally submitted by Timothy Redmond
                    String ns = "http://another.com/ont";
                    OWLClass a = Class(IRI(ns + "#A"));
                    OWLAnnotationProperty p = AnnotationProperty(IRI(ns + "#p"));
                    OWLObjectProperty q = ObjectProperty(IRI(ns + "#q"));
                    OWLAnonymousIndividual h = AnonymousIndividual();
                    OWLAnonymousIndividual i = AnonymousIndividual();
                    Set<OWLAxiom> axioms = new HashSet<>();
                    axioms.add(AnnotationAssertion(p, a.getIRI(), h));
                    axioms.add(ClassAssertion(a, h));
                    axioms.add(ObjectPropertyAssertion(q, h, i));
                    axioms.add(AnnotationAssertion(RDFSLabel(), h, Literal("Second", "en")));
                    return axioms;
                } ,
                // AnonymousIndividuals
                () -> {
                    Set<OWLAxiom> axioms = new HashSet<>();
                    OWLAnonymousIndividual ind = AnonymousIndividual();
                    axioms.add(ObjectPropertyAssertion(ObjectProperty(iri("p")), NamedIndividual(iri("i1")), ind));
                    axioms.add(ObjectPropertyAssertion(ObjectProperty(iri("p")), ind, NamedIndividual(iri("i2"))));
                    return axioms;
                } ,
                // ChainedAnonymousIndividuals
                () -> {
                    Set<OWLAxiom> axioms = new HashSet<>();
                    IRI annoPropIRI = IRI("http://owlapi.sourceforge.net/ontology#annoProp");
                    OWLAnnotationProperty property = AnnotationProperty(annoPropIRI);
                    IRI subject = IRI("http://owlapi.sourceforge.net/ontology#subject");
                    axioms.add(Declaration(NamedIndividual(subject)));
                    OWLAnonymousIndividual individual1 = AnonymousIndividual();
                    OWLAnonymousIndividual individual2 = AnonymousIndividual();
                    OWLAnonymousIndividual individual3 = AnonymousIndividual();
                    OWLAnnotationAssertionAxiom annoAssertion1 = AnnotationAssertion(property, subject, individual1);
                    OWLAnnotationAssertionAxiom annoAssertion2 = AnnotationAssertion(property, individual1,
                            individual2);
                    OWLAnnotationAssertionAxiom annoAssertion3 = AnnotationAssertion(property, individual2,
                            individual3);
                    axioms.add(annoAssertion1);
                    axioms.add(annoAssertion2);
                    axioms.add(annoAssertion3);
                    return axioms;
                } ,
                // ClassAssertionWithAnonymousIndividual
                () -> {
                    Set<OWLAxiom> axioms = new HashSet<>();
                    OWLIndividual ind = AnonymousIndividual("a");
                    OWLClass cls = Class(iri("A"));
                    axioms.add(ClassAssertion(cls, ind));
                    axioms.add(Declaration(cls));
                    return axioms;
                } ,
                // DifferentIndividualsAnonymous
                () -> {
                    Set<OWLAxiom> axioms = new HashSet<>();
                    axioms.add(
                            DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual(), AnonymousIndividual()));
                    return axioms;
                } ,
                // DifferentIndividualsPairwiseAnonymous
                () -> {
                    Set<OWLAxiom> axioms = new HashSet<>();
                    axioms.add(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual()));
                    return axioms;
                } ,
                // ObjectPropertyAssertionWithAnonymousIndividuals
                () -> {
                    OWLIndividual subject = AnonymousIndividual();
                    OWLIndividual object = AnonymousIndividual();
                    OWLObjectProperty prop = ObjectProperty(iri("prop"));
                    Set<OWLAxiom> axioms = new HashSet<>();
                    axioms.add(ObjectPropertyAssertion(prop, subject, object));
                    axioms.add(Declaration(prop));
                    return axioms;
                } ,
                // SameIndividualsAnonymous
                () -> {
                    Set<OWLAxiom> axioms = new HashSet<>();
                    // Can't round trip more than two in RDF! Also, same
                    // individuals
                    // axiom
                    // with anon individuals is not allowed
                    // in OWL 2, but it should at least round trip
                    axioms.add(SameIndividual(AnonymousIndividual(), AnonymousIndividual()));
                    return axioms;
                } );
    }
}
