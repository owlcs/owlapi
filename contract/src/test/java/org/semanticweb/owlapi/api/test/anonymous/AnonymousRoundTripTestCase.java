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
package org.semanticweb.owlapi.api.test.anonymous;

import static org.junit.Assert.assertNull;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;

import java.util.Collections;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractRoundTrippingTestCase;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;

@SuppressWarnings("javadoc")
public class AnonymousRoundTripTestCase extends AbstractRoundTrippingTestCase {

    @Test
    public void shouldNotFailOnAnonymousOntologySearch() throws OWLOntologyCreationException {
        m.createOntology(new OWLOntologyID());
        assertNull(m.getOntology(new OWLOntologyID()));
    }

    @Test
    public void testRoundTrip() throws Exception {
        String ns = "http://smi-protege.stanford.edu/ontologies/AnonymousIndividuals.owl";
        OWLClass a = Class(IRI(ns + "#", "A"));
        OWLAnonymousIndividual h = AnonymousIndividual();
        OWLAnonymousIndividual i = AnonymousIndividual();
        OWLAnnotationProperty p = AnnotationProperty(IRI(ns + "#", "p"));
        OWLObjectProperty q = ObjectProperty(IRI(ns + "#", "q"));
        OWLOntology ontology = getOWLOntology();
        OWLAnnotation annotation1 = df.getOWLAnnotation(p, h);
        OWLAnnotation annotation2 = df.getRDFSLabel(Literal("Second", "en"));
        ontology.add(df.getOWLAnnotationAssertionAxiom(a.getIRI(), annotation1),
            ClassAssertion(a, h), ObjectPropertyAssertion(q, h, i),
            df.getOWLAnnotationAssertionAxiom(h, annotation2));
        OWLOntology o = roundTrip(ontology, new ManchesterSyntaxDocumentFormat());
        equal(ontology, o);
    }

    @Override
    protected OWLOntology createOntology() {

        OWLDataProperty dp = df.getOWLDataProperty("urn:test:anon#D");
        OWLObjectProperty op = df.getOWLObjectProperty("urn:test:anon#O");
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty("urn:test:anon#A2");
        OWLAnonymousIndividual i = df.getOWLAnonymousIndividual("_:b0");
        OWLAnnotation sub1 = df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral("z1"));
        OWLAnnotation an1 = df.getOWLAnnotation(ap, i, Collections.singletonList(sub1));

        OWLClassExpression c1 = df.getOWLDataAllValuesFrom(dp, df.getBooleanOWLDatatype());
        OWLClassExpression c2 = df.getOWLObjectSomeValuesFrom(op, df.getOWLThing());

        OWLAxiom ax1 = df.getOWLSubClassOfAxiom(c1, c2, Collections.singletonList(an1));

        OWLOntology ont1 = getOWLOntology();
        ont1.add(ax1);
        return ont1;
    }
    //
    // @Override
    // public void testTurtle() throws Exception {
    // StringDocumentTarget saveOntology =
    // saveOntology(createOntology(), new TurtleDocumentFormat());
    // String s = saveOntology.toString().replaceAll("\\n#[\\w\\:\\. #]+\\n", "\n")
    // .replaceAll("\\n#[\\w\\:\\. #]+\\n", "\n").replaceAll("\\n[ ]*\\n", "\n");
    // System.out.println("AnonymousRoundTripTestCase.testTurtle() " + s);
    // super.testTurtle();
    // }
}
