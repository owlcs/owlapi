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
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.fail;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.search.EntitySearcher.getAnnotationObjects;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParser;

@SuppressWarnings("javadoc")
public class Utf8TestCase extends TestBase {

    @Test
    public void testUTF8roundTrip() throws Exception {
        String onto = "Ontology(<http://protege.org/UTF8.owl>" + "Declaration(Class(<http://protege.org/UTF8.owl#A>))"
            + "AnnotationAssertion(<http://www.w3.org/2000/01/rdf-schema#label> <http://protege.org/UTF8.owl#A> "
            + "\"Chinese=處方\"^^<http://www.w3.org/2001/XMLSchema#string>))";
        saveOntology(loadOntologyFromString(onto));
        // ByteArrayInputStream in = new ByteArrayInputStream(onto.getBytes());
        // ByteArrayOutputStream out = new ByteArrayOutputStream();
        // manager.saveOntology(manager.loadOntologyFromOntologyDocument(in),
        // out);
    }

    @Test
    public void testInvalidUTF8roundTripOWLXML() {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should fail parsing but is read
        // in as an owl/xml file
        String onto = "<!DOCTYPE rdf:RDF [\n" + "<!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n" + "]>\n"
            + "<rdf:RDF \n" + "xml:base=\n" + "\"http://www.example.org/ISA14#\" \n"
            + "xmlns:owl =\"http://www.w3.org/2002/07/owl#\" \n"
            + "xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n"
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" \n"
            + "xmlns:xsd =\"http://www.w3.org/2001/XMLSchema#\" \n" + "xmlns:ibs =\"http://www.example.org/ISA14#\" >\n"
            + "<owl:Ontology rdf:about=\"#\" />\n" + (char) 0240
            + "<owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/>\n" + "</rdf:RDF>";
        ByteArrayInputStream in = new ByteArrayInputStream(onto.getBytes(StandardCharsets.ISO_8859_1));
        OWLXMLParser parser = new OWLXMLParser();
        try {
            parser.parse(new StreamDocumentSource(in), getOWLOntology(), config);
            fail("parsing should have failed, invalid input");
        } catch (@SuppressWarnings("unused") Exception ex) {
            // expected to fail, but actual exception depends on the parsers in
            // the classpath
        }
    }

    @Test
    public void testInvalidUTF8roundTripWithInputStream() throws OWLOntologyCreationException {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should work with an input stream,
        // not with a reader
        String onto = "<!DOCTYPE rdf:RDF [\n" + "<!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n" + "]>\n"
            + "<rdf:RDF \n" + "xml:base=\n" + "\"http://www.example.org/ISA14#\" \n"
            + "xmlns:owl =\"http://www.w3.org/2002/07/owl#\" \n"
            + "xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n"
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" \n"
            + "xmlns:xsd =\"http://www.w3.org/2001/XMLSchema#\" \n" + "xmlns:ibs =\"http://www.example.org/ISA14#\" >\n"
            + "<owl:Ontology rdf:about=\"#\" />\n" + (char) 0240
            + "<owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/>\n" + "</rdf:RDF>";
        ByteArrayInputStream in = new ByteArrayInputStream(onto.getBytes(StandardCharsets.ISO_8859_1));
        m.loadOntologyFromOntologyDocument(in);
    }

    @Test
    public void testInvalidUTF8roundTripFromReader() throws OWLOntologyCreationException {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should work with an input stream,
        // not with a reader
        String onto1 = "<!DOCTYPE rdf:RDF [\n" + "<!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n" + "]>\n"
            + "<rdf:RDF \n" + "xml:base=\n" + "\"http://www.example.org/ISA14#\" \n"
            + "xmlns:owl =\"http://www.w3.org/2002/07/owl#\" \n"
            + "xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n"
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" \n"
            + "xmlns:xsd =\"http://www.w3.org/2001/XMLSchema#\" \n" + "xmlns:ibs =\"http://www.example.org/ISA14#\" >\n"
            + "<owl:Ontology rdf:about=\"#\" />\n" + (char) 0240
            + "<owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/>\n" + "</rdf:RDF>";
        OWLOntology o1 = loadOntologyFromString(onto1);
        String onto2 = "<!DOCTYPE rdf:RDF [\n" + "<!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n" + "]>\n"
            + "<rdf:RDF \n" + "xml:base=\n" + "\"http://www.example.org/ISA14#\" \n"
            + "xmlns:owl =\"http://www.w3.org/2002/07/owl#\" \n"
            + "xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n"
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" \n"
            + "xmlns:xsd =\"http://www.w3.org/2001/XMLSchema#\" \n" + "xmlns:ibs =\"http://www.example.org/ISA14#\" >\n"
            + "<owl:Ontology rdf:about=\"#\" />\n"
            + "<owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/>\n" + "</rdf:RDF>";
        OWLOntology o2 = loadOntologyFromString(onto2);
        equal(o1, o2);
    }

    @Test
    public void testPositiveUTF8roundTrip() throws Exception {
        String ns = "http://protege.org/UTF8.owl";
        OWLOntology ontology = getOWLOntology();
        OWLClass a = Class(IRI(ns + "#A"));
        ontology.add(df.getOWLDeclarationAxiom(a));
        OWLAnnotation ann = df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral("Chinese=處方"));
        OWLAxiom axiom = df.getOWLAnnotationAssertionAxiom(a.getIRI(), ann);
        ontology.add(axiom);
        ontology = roundTrip(ontology, new FunctionalSyntaxDocumentFormat());
    }

    @Test
    public void testRoundTrip() throws Exception {
        String ns = "http://protege.org/ontologies/UTF8RoundTrip.owl";
        OWLClass c = Class(IRI(ns + "#C"));
        /*
         * The two unicode characters entered here are valid and can be found in
         * the code chart http://www.unicode.org/charts/PDF/U4E00.pdf. It has
         * been said that they are chinese and they do look the part. In UTF-8
         * these characters are encoded as \u8655 --> \350\231\225 \u65b9 -->
         * \346\226\271 where the right hand side is in octal. (I chose octal
         * because this is how emacs represents it with find-file-literally).
         */
        String chinese = "Rx\u8655\u65b9";
        System.setProperty("file.encoding", "UTF-8");
        OWLOntology ontology = createOriginalOntology(ns, c, chinese);
        checkOntology(ontology, c, chinese);
        OWLOntology newOntology = roundTrip(ontology, new RDFXMLDocumentFormat());
        checkOntology(newOntology, c, chinese);
    }

    private OWLOntology createOriginalOntology(String ns, OWLClass c, String chinese)
        throws OWLOntologyCreationException {
        OWLOntology ontology = getOWLOntology(IRI(ns));
        OWLAxiom annotationAxiom = AnnotationAssertion(RDFSLabel(), c.getIRI(), Literal(chinese));
        ontology.add(annotationAxiom);
        return ontology;
    }

    private static boolean checkOntology(OWLOntology ontology, OWLClass c, String chinese) {
        return getAnnotationObjects(c, ontology).anyMatch(a -> a.getValue().asLiteral().get().getLiteral().equals(
            chinese));
    }
}
