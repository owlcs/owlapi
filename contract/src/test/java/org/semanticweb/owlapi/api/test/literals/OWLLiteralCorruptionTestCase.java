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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings({ "javadoc" })
public class OWLLiteralCorruptionTestCase extends TestBase {

    @Test
    public void shouldroundTripLiteral() {
        String testString;
        StringBuilder sb = new StringBuilder();
        int count = 17;
        while (count-- > 0) {
            sb.append("200 \u00B5Liters + character above U+0FFFF = ");
            sb.appendCodePoint(0x10192);  // happens to be "ROMAN SEMUNCIA SIGN"
            sb.append('\n');
        }
        testString = sb.toString();
        OWLLiteral literal = Literal(testString);
        assertEquals("Out = in ? false", literal.getLiteral(), testString);
    }

    @Test
    public void shouldRoundTripXMLLiteral() throws OWLOntologyStorageException {
        String literal = "<div xmlns='http://www.w3.org/1999/xhtml'><h3>[unknown]</h3><p>(describe NameGroup \"[unknown]\")</p></div>";
        OWLOntology o = getOWLOntology();
        OWLDataProperty p = df.getOWLDataProperty(IRI.create("urn:test#p"));
        OWLLiteral l = df.getOWLLiteral(literal, OWL2Datatype.RDF_XML_LITERAL);
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI.create(
        "urn:test#i"));
        o.addAxiom(df.getOWLDataPropertyAssertionAxiom(p, i, l));
        String string = saveOntology(o).toString();
        assertTrue(string.contains(literal));
    }

    @Test
    public void shouldRoundtripPaddedLiterals()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String in = "Prefix(:=<urn:test#>)\n" + "Prefix(a:=<urn:test#>)\n"
        + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n"
        + "Prefix(owl2xml:=<http://www.w3.org/2006/12/owl2-xml#>)\n"
        + "Prefix(test:=<urn:test#>)\n"
        + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
        + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
        + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
        + "Ontology(<urn:test>\n"
        + "DataPropertyAssertion(:dp :c \"1\"^^xsd:integer) "
        + "DataPropertyAssertion(:dp :c \"01\"^^xsd:integer) "
        + "DataPropertyAssertion(:dp :c \"1\"^^xsd:short))";
        OWLOntology o = loadOntologyFromString(new StringDocumentSource(in, IRI
        .create("urn:test"), new FunctionalSyntaxDocumentFormat(), null));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
        OWLDataProperty p = df.getOWLDataProperty(IRI.create("urn:test#dp"));
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI.create(
        "urn:test#c"));
        assertTrue(o.getAxioms().contains(df.getOWLDataPropertyAssertionAxiom(p,
        i, df.getOWLLiteral("01", df.getIntegerOWLDatatype()))));
        assertTrue(o.getAxioms().contains(df.getOWLDataPropertyAssertionAxiom(p,
        i, df.getOWLLiteral("1", df.getIntegerOWLDatatype()))));
        assertTrue(o.getAxioms().contains(df.getOWLDataPropertyAssertionAxiom(p,
        i, df.getOWLLiteral("1", OWL2Datatype.XSD_SHORT.getDatatype(df)))));
    }

    @Test
    public void shouldNotFindPaddedLiteralsEqualToNonPadded() {
        assertNotEquals(df.getOWLLiteral("01", df.getIntegerOWLDatatype()), df
        .getOWLLiteral("1", df.getIntegerOWLDatatype()));
        assertNotEquals(df.getOWLLiteral("1", df.getIntegerOWLDatatype()), df
        .getOWLLiteral("01", df.getIntegerOWLDatatype()));
    }
}
