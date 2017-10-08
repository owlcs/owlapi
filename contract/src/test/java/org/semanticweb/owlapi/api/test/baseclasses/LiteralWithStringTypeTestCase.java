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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.3.0
 */
public class LiteralWithStringTypeTestCase extends AbstractRoundTrippingTestCase {

    @Override
    protected OWLOntology createOntology() {
        try {
            return m.createOntology(Stream.of(df.getOWLDataPropertyAssertionAxiom(
                df.getOWLDataProperty("http://owlapi.sourceforge.net/ontology#prop"),
                df.getOWLNamedIndividual("http://owlapi.sourceforge.net/ontology#A"),
                df.getOWLLiteral("test url"))));
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OWLOntology roundTripOntology(OWLOntology ont, OWLDocumentFormat format)
        throws OWLOntologyStorageException, OWLOntologyCreationException {
        String string = saveOntology(ont, format).toString();
        assertTrue(format.getKey() + "\n" + string,
            format.getKey().equals("RDF/JSON") || !string.contains("^^xsd:string"));
        return super.roundTripOntology(ont, format);
    }

    @Test
    public void shouldParseInputWithoutExplicitString() throws OWLOntologyCreationException {
        List<String> list = Arrays.asList(
            "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
                + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
                + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                + "Ontology: <http://www.semanticweb.org/owlapi/test224612089629142>\n"
                + "Datatype: xsd:string\n"
                + "DataProperty: <http://owlapi.sourceforge.net/ontology#prop>\n"
                + "Class: owl:Thing\n" + "Individual: <http://owlapi.sourceforge.net/ontology#A>\n"
                + "    Types: \n" + "        owl:Thing\n" + "    Facts:  \n"
                + "     <http://owlapi.sourceforge.net/ontology#prop>  \"test url\"",
            "@prefix : <http://www.semanticweb.org/owlapi/test224612089629142#> .\n"
                + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + "{\n"
                + "    <http://www.semanticweb.org/owlapi/test224612089629142> a owl:Ontology .\n"
                + "    <http://owlapi.sourceforge.net/ontology#prop> a owl:DatatypeProperty .\n"
                + "    <http://owlapi.sourceforge.net/ontology#A> a owl:NamedIndividual , owl:Thing ;\n"
                + "        <http://owlapi.sourceforge.net/ontology#prop> \"test url\" .\n" + "}",
            "{\"http://owlapi.sourceforge.net/ontology#A\":{\"http://owlapi.sourceforge.net/ontology#prop\":[{\"value\":\"test url\",\"type\":\"literal\",\"datatype\":\"http://www.w3.org/2001/XMLSchema#string\"}],"
                + "\"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\":[{\"value\":\"http://www.w3.org/2002/07/owl#NamedIndividual\",\"type\":\"uri\"},{\"value\":\"http://www.w3.org/2002/07/owl#Thing\","
                + "\"type\":\"uri\"}]},\"http://owlapi.sourceforge.net/ontology#prop\":{\"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\":[{\"value\":\"http://www.w3.org/2002/07/owl#DatatypeProperty\","
                + "\"type\":\"uri\"}]},\"http://www.semanticweb.org/owlapi/test224612089629142\":{\"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\":[{\"value\":\"http://www.w3.org/2002/07/owl#Ontology\","
                + "\"type\":\"uri\"}]}}",
            "<http://www.semanticweb.org/owlapi/test224612089629142> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Ontology> .\n"
                + "<http://owlapi.sourceforge.net/ontology#prop> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#DatatypeProperty> .\n"
                + "<http://owlapi.sourceforge.net/ontology#A> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#NamedIndividual> .\n"
                + "<http://owlapi.sourceforge.net/ontology#A> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Thing> .\n"
                + "<http://owlapi.sourceforge.net/ontology#A> <http://owlapi.sourceforge.net/ontology#prop> \"test url\" .",
            "[{\"@id\":\"http://owlapi.sourceforge.net/ontology#A\",\"@type\":[\"http://www.w3.org/2002/07/owl#NamedIndividual\",\"http://www.w3.org/2002/07/owl#Thing\"],"
                + "\"http://owlapi.sourceforge.net/ontology#prop\":[{\"@value\":\"test url\"}]},{\"@id\":\"http://owlapi.sourceforge.net/ontology#prop\",\"@type\":[\"http://www.w3.org/2002/07/owl#DatatypeProperty\"]},"
                + "{\"@id\":\"http://www.semanticweb.org/owlapi/test224612089629142\",\"@type\":[\"http://www.w3.org/2002/07/owl#Ontology\"]}]",
            "<http://www.semanticweb.org/owlapi/test224612089629142> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Ontology> .\n"
                + "<http://owlapi.sourceforge.net/ontology#prop> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#DatatypeProperty> .\n"
                + "<http://owlapi.sourceforge.net/ontology#A> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#NamedIndividual> .\n"
                + "<http://owlapi.sourceforge.net/ontology#A> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Thing> .\n"
                + "<http://owlapi.sourceforge.net/ontology#A> <http://owlapi.sourceforge.net/ontology#prop> \"test url\" .",
            "<?xml version=\"1.0\"?>\n" + "<Ontology xmlns=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xml:base=\"http://www.semanticweb.org/owlapi/test224612089629142\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "     ontologyIRI=\"http://www.semanticweb.org/owlapi/test224612089629142\">\n"
                + "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n"
                + "    <Prefix name=\"rdf\" IRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n"
                + "    <Prefix name=\"xml\" IRI=\"http://www.w3.org/XML/1998/namespace\"/>\n"
                + "    <Prefix name=\"xsd\" IRI=\"http://www.w3.org/2001/XMLSchema#\"/>\n"
                + "    <Prefix name=\"rdfs\" IRI=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n"
                + "    <Declaration><DataProperty IRI=\"http://owlapi.sourceforge.net/ontology#prop\"/></Declaration>\n"
                + "    <Declaration><NamedIndividual IRI=\"http://owlapi.sourceforge.net/ontology#A\"/></Declaration>\n"
                + "    <ClassAssertion><Class abbreviatedIRI=\"owl:Thing\"/><NamedIndividual IRI=\"http://owlapi.sourceforge.net/ontology#A\"/></ClassAssertion>\n"
                + "    <DataPropertyAssertion>\n"
                + "        <DataProperty IRI=\"http://owlapi.sourceforge.net/ontology#prop\"/>\n"
                + "        <NamedIndividual IRI=\"http://owlapi.sourceforge.net/ontology#A\"/>\n"
                + "        <Literal>test url</Literal>\n" + "    </DataPropertyAssertion>\n"
                + "</Ontology>",
            "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF xmlns=\"http://www.semanticweb.org/owlapi/test224612089629142#\"\n"
                + "     xml:base=\"http://www.semanticweb.org/owlapi/test224612089629142\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "     xmlns:ontology=\"http://owlapi.sourceforge.net/ontology#\">\n"
                + "    <owl:Ontology rdf:about=\"http://www.semanticweb.org/owlapi/test224612089629142\"/>\n"
                + "    <owl:DatatypeProperty rdf:about=\"http://owlapi.sourceforge.net/ontology#prop\"/>\n"
                + "    <owl:Thing rdf:about=\"http://owlapi.sourceforge.net/ontology#A\">\n"
                + "        <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#NamedIndividual\"/>\n"
                + "        <ontology:prop>test url</ontology:prop>\n" + "    </owl:Thing>\n"
                + "</rdf:RDF>",
            "@prefix : <http://www.semanticweb.org/owlapi/test224612089629142#> .\n"
                + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@base <http://www.semanticweb.org/owlapi/test224612089629142> .\n"
                + "<http://www.semanticweb.org/owlapi/test224612089629142> rdf:type owl:Ontology .\n"
                + "<http://owlapi.sourceforge.net/ontology#prop> rdf:type owl:DatatypeProperty .\n"
                + "<http://owlapi.sourceforge.net/ontology#A> rdf:type owl:NamedIndividual , owl:Thing ; <http://owlapi.sourceforge.net/ontology#prop> \"test url\" .",
            "Prefix(:=<http://www.semanticweb.org/owlapi/test224612089629142#>)\n"
                + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
                + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
                + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
                + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
                + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n"
                + "Prefix(ontology:=<http://owlapi.sourceforge.net/ontology#>)\n"
                + "Ontology(<http://www.semanticweb.org/owlapi/test224612089629142>\n"
                + "Declaration(DataProperty(ontology:prop))\n"
                + "Declaration(NamedIndividual(ontology:A))\n"
                + "# Individual: ontology:A (ontology:A)\n"
                + "ClassAssertion(owl:Thing ontology:A)\n"
                + "DataPropertyAssertion(ontology:prop ontology:A \"test url\"))");
        for (String s : list) {
            assertTrue(loadOntologyFromString(s).containsAxiom(df.getOWLDataPropertyAssertionAxiom(
                df.getOWLDataProperty("http://owlapi.sourceforge.net/ontology#prop"),
                df.getOWLNamedIndividual("http://owlapi.sourceforge.net/ontology#A"),
                df.getOWLLiteral("test url"))));
        }
    }
}
