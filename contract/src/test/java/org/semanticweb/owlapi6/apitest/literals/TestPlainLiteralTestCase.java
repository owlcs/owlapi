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
package org.semanticweb.owlapi6.apitest.literals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.RDFSComment;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AddOntologyAnnotation;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

public class TestPlainLiteralTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";
    private static final String TEST = "test";

    @Test
    public void testPlainLiteral() {
        IRI iri = IRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "PlainLiteral");
        assertTrue(iri.isPlainLiteral());
        assertNotNull(df.getRDFPlainLiteral());
        assertNotNull(OWL2Datatype.getDatatype(iri));
    }

    @Test
    public void shouldParsePlainLiteral() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.parsePlainLiteral, new RDFXMLDocumentFormat());
        IRI i = IRI(URN_TEST, "ind");
        assertEquals(o.annotationAssertionAxioms(i).iterator().next(),
            AnnotationAssertion(RDFSComment(), i, Literal(TEST, OWL2Datatype.RDF_PLAIN_LITERAL)));
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
        OWLDataProperty p = df.getOWLDataProperty(URN_TEST, "p");
        OWLIndividual i = df.getOWLNamedIndividual(URN_TEST, "ind");
        OWLLiteral l = df.getOWLLiteral(TEST, OWL2Datatype.RDF_PLAIN_LITERAL);
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
        OWLIndividual i = df.getOWLNamedIndividual(URN_TEST, "ind");
        OWLLiteral l = df.getOWLLiteral(TEST, OWL2Datatype.RDF_PLAIN_LITERAL);
        o.add(df.getOWLAnnotationAssertionAxiom(i.asOWLNamedIndividual().getIRI(),
            df.getRDFSComment(l)));
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
        OWLLiteral l = df.getOWLLiteral(TEST, OWL2Datatype.RDF_PLAIN_LITERAL);
        OWLAnnotation a = df.getRDFSComment(l);
        o.applyChange(new AddOntologyAnnotation(o, a));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        o.saveOntology(out);
        String expectedStart = "<rdfs:comment";
        String expectedEnd = ">test</rdfs:comment>";
        assertTrue(out.toString(), out.toString().contains(expectedStart));
        assertTrue(out.toString(), out.toString().contains(expectedEnd));
    }
}
