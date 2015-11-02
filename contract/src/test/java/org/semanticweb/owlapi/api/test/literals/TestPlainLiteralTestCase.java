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
package org.semanticweb.owlapi.api.test.literals;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class TestPlainLiteralTestCase extends TestBase {

    @Test
    public void testPlainLiteral() {
        IRI iri = IRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral");
        assertTrue(iri.isPlainLiteral());
        assertNotNull(df.getRDFPlainLiteral());
        assertNotNull(OWL2Datatype.getDatatype(iri));
    }

    @Test
    public void shouldParsePlainLiteral() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n"
                + "    <rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\" xml:base=\"http://www.w3.org/2002/07/owl\"\n"
                + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "         xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n\n"
                + "        <rdf:Description rdf:about=\"urn:test#ind\">\n"
                + "            <rdfs:comment rdf:datatype=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">test</rdfs:comment>\n"
                + "        </rdf:Description>\n" + "    </rdf:RDF>";
        OWLOntology o = loadOntologyFromString(input);
        IRI i = IRI("urn:test#ind");
        assertEquals(o.annotationAssertionAxioms(i).iterator().next(),
                AnnotationAssertion(RDFSComment(), i, Literal("test", OWL2Datatype.RDF_PLAIN_LITERAL)));
    }

    @Test
    public void testPlainLiteralFromEvren() {
        OWLDatatype node = df.getRDFPlainLiteral();
        assertTrue(node.isBuiltIn());
        assertNotNull(node.getBuiltInDatatype());
    }

    @Test
    public void testPlainLiteralSerialization() throws Exception {
        OWLOntology o = getOWLOntology();
        OWLDataProperty p = df.getOWLDataProperty("urn:test#p");
        OWLIndividual i = df.getOWLNamedIndividual("urn:test#ind");
        OWLLiteral l = df.getOWLLiteral("test", OWL2Datatype.RDF_PLAIN_LITERAL);
        o.add(df.getOWLDataPropertyAssertionAxiom(p, i, l));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        o.saveOntology(out);
        String expectedStart = "<test:p";
        String expectedEnd = ">test</test:p>";
        assertTrue(out.toString(), out.toString().contains(expectedStart));
        assertTrue(out.toString(), out.toString().contains(expectedEnd));
    }

    @Test
    public void testPlainLiteralSerializationComments() throws Exception {
        OWLOntology o = getOWLOntology();
        OWLIndividual i = df.getOWLNamedIndividual("urn:test#ind");
        OWLLiteral l = df.getOWLLiteral("test", OWL2Datatype.RDF_PLAIN_LITERAL);
        o.add(df.getOWLAnnotationAssertionAxiom(df.getRDFSComment(), i.asOWLNamedIndividual().getIRI(), l));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        o.saveOntology(out);
        String expectedStart = "<rdfs:comment";
   		String expectedEnd = ">test</rdfs:comment>";
        assertTrue(out.toString(), out.toString().contains(expectedStart));
        assertTrue(out.toString(), out.toString().contains(expectedEnd));
    }

    @Test
    public void testPlainLiteralSerializationComments2() throws Exception {
        OWLOntology o = getOWLOntology();
        OWLLiteral l = df.getOWLLiteral("test", OWL2Datatype.RDF_PLAIN_LITERAL);
        OWLAnnotation a = df.getOWLAnnotation(df.getRDFSComment(), l);
        o.applyChange(new AddOntologyAnnotation(o, a));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        o.saveOntology(out);
        String expectedStart = "<rdfs:comment";
        String expectedEnd = ">test</rdfs:comment>";
        assertTrue(out.toString(), out.toString().contains(expectedStart));
        assertTrue(out.toString(), out.toString().contains(expectedEnd));
    }
}
