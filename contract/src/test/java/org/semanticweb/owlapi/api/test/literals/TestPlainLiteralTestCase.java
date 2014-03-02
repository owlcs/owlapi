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
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
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
        assertEquals(
                o.getAnnotationAssertionAxioms(i).iterator().next(),
                AnnotationAssertion(RDFSComment(), i,
                        Literal("test", OWL2Datatype.RDF_PLAIN_LITERAL)));
    }

    @Test
    public void testPlainLiteralFromEvren() {
        OWLDatatype node = df.getRDFPlainLiteral();
        assertTrue(node.isBuiltIn());
        assertNotNull(node.getBuiltInDatatype());
    }

    @Test
    public void testPlainLiteralSerialization()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology();
        OWLDataProperty p = m.getOWLDataFactory().getOWLDataProperty(
                IRI("urn:test#p"));
        OWLIndividual i = m.getOWLDataFactory().getOWLNamedIndividual(
                IRI("urn:test#ind"));
        OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
                OWL2Datatype.RDF_PLAIN_LITERAL);
        m.addAxiom(o,
                m.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(p, i, l));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        m.saveOntology(o, out);
        String expected = "<test:p>test</test:p>";
        assertTrue(out.toString(), out.toString().contains(expected));
    }

    @Test
    public void testPlainLiteralSerializationComments()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology();
        OWLIndividual i = df.getOWLNamedIndividual(IRI("urn:test#ind"));
        OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
                OWL2Datatype.RDF_PLAIN_LITERAL);
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(df.getRDFSComment(), i
                .asOWLNamedIndividual().getIRI(), l));
        String expected = "<rdfs:comment>test</rdfs:comment>";
        assertTrue(saveOntology(o).toString().contains(expected));
    }

    @Test
    public void testPlainLiteralSerializationComments2()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology();
        OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
                OWL2Datatype.RDF_PLAIN_LITERAL);
        OWLAnnotation a = m.getOWLDataFactory().getOWLAnnotation(
                m.getOWLDataFactory().getRDFSComment(), l);
        m.applyChange(new AddOntologyAnnotation(o, a));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        m.saveOntology(o, out);
        String expected = "<rdfs:comment>test</rdfs:comment>";
        assertTrue(out.toString(), out.toString().contains(expected));
    }
}
