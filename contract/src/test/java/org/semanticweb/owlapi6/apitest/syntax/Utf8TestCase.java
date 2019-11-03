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
package org.semanticweb.owlapi6.apitest.syntax;

import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi6.search.Searcher.getAnnotationObjects;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StreamDocumentSource;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.owlxml.parser.OWLXMLParser;

public class Utf8TestCase extends TestBase {

    @Test
    public void testUTF8roundTrip() throws Exception {
        saveOntology(loadOntologyFromString(TestFiles.roundtripUTF8String,
            new FunctionalSyntaxDocumentFormat()));
    }

    @Test(expected = Exception.class)
    public void testInvalidUTF8roundTripOWLXML() {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should fail parsing but is read
        // in as an owl/xml file
        ByteArrayInputStream in =
            new ByteArrayInputStream(TestFiles.INVALID_UTF8.getBytes(StandardCharsets.ISO_8859_1));
        OWLXMLParser parser = new OWLXMLParser();
        new StreamDocumentSource(in).acceptParser(parser, getOWLOntology(), config);
    }

    @Test
    public void testInvalidUTF8roundTripWithInputStream() throws OWLOntologyCreationException {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should work with an input stream,
        // not with a reader
        ByteArrayInputStream in =
            new ByteArrayInputStream(TestFiles.INVALID_UTF8.getBytes(StandardCharsets.ISO_8859_1));
        m.loadOntologyFromOntologyDocument(in);
    }

    @Test(expected = OWLRuntimeException.class)
    public void testInvalidUTF8roundTripFromReader() {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should work with an input stream,
        // not with a reader
        loadOntologyFromString(TestFiles.INVALID_UTF8, new RDFXMLDocumentFormat());
    }

    @Test
    public void testPositiveUTF8roundTrip() throws Exception {
        String ns = "http://protege.org/UTF8.owl";
        OWLOntology ontology = getOWLOntology();
        OWLClass a = Class(IRI(ns + "#", "A"));
        ontology.add(df.getOWLDeclarationAxiom(a));
        OWLAnnotation ann = df.getRDFSLabel("Chinese=處方");
        OWLAxiom axiom = df.getOWLAnnotationAssertionAxiom(a.getIRI(), ann);
        ontology.add(axiom);
        ontology = roundTrip(ontology, new FunctionalSyntaxDocumentFormat());
    }

    @Test
    public void testRoundTrip() throws Exception {
        String ns = "http://protege.org/ontologies/UTF8RoundTrip.owl";
        OWLClass c = Class(IRI(ns + "#", "C"));
        /*
         * The two unicode characters entered here are valid and can be found in the code chart
         * http://www.unicode.org/charts/PDF/U4E00.pdf. It has been said that they are chinese and
         * they do look the part. In UTF-8 these characters are encoded as \u8655 --> \350\231\225
         * \u65b9 --> \346\226\271 where the right hand side is in octal. (I chose octal because
         * this is how emacs represents it with find-file-literally).
         */
        String chinese = "Rx\u8655\u65b9";
        System.setProperty("file.encoding", "UTF-8");
        OWLOntology ontology = createOriginalOntology(ns, c, chinese);
        checkOntology(ontology, c, chinese);
        OWLOntology newOntology = roundTrip(ontology, new RDFXMLDocumentFormat());
        checkOntology(newOntology, c, chinese);
    }

    private OWLOntology createOriginalOntology(String ns, OWLClass c, String chinese) {
        OWLOntology ontology = getOWLOntology(IRI(ns, ""));
        OWLAxiom annotationAxiom = AnnotationAssertion(RDFSLabel(), c.getIRI(), Literal(chinese));
        ontology.add(annotationAxiom);
        return ontology;
    }

    private static boolean checkOntology(OWLOntology ontology, OWLClass c, String chinese) {
        return getAnnotationObjects(c, ontology)
            .anyMatch(a -> a.getValue().asLiteral().get().getLiteral().equals(chinese));
    }
}
