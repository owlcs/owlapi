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

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class TurtleTestCase extends TestBase {

    @Test
    public void testLoadingUTF8BOM() throws Exception {
        IRI uri = IRI.create(getClass().getResource("/ttl-with-bom.ttl").toURI());
        m.loadOntologyFromOntologyDocument(uri);
    }

    @Nonnull private final IRI iri = IRI.create("urn:testliterals");
    @Nonnull private final TurtleDocumentFormat tf = new TurtleDocumentFormat();
    @Nonnull private final IRI s = IRI.create("urn:test#s");

    @Test
    public void shouldParseFixedQuotesLiterals1() throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(new StringDocumentSource("<urn:test#s> <urn:test#p> ''' ''\\' ''' .",
            iri, tf, null));
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals(" ''' ", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals2() throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(new StringDocumentSource(
            "<urn:test#s> <urn:test#p> \"\"\" \"\"\\\" \"\"\" .", iri, tf, null));
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals(" \"\"\" ", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals3() throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(new StringDocumentSource(
            "<urn:test#s> <urn:test#p> \"\"\" \"\"\\u0061 \"\"\" .", iri, tf, null));
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals(" \"\"a ", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals4() throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(new StringDocumentSource(
            "<urn:test#s> <urn:test#p> \"\"\"\"\"\\\"\"\"\" .", iri, tf, null));
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals("\"\"\"", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals5() throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(new StringDocumentSource(
            "<urn:test#s> <urn:test#p> \"\"\"\"\"\\u0061\"\"\" .", iri, tf, null));
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals("\"\"a", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseOntologyThatworked() throws OWLOntologyCreationException {
        // given
        String working = "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n @prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n foaf:fundedBy rdfs:isDefinedBy <http://xmlns.com/foaf/0.1/> .";
        OWLAxiom expected = AnnotationAssertion(df.getRDFSIsDefinedBy(), IRI("http://xmlns.com/foaf/0.1/fundedBy"), IRI(
            "http://xmlns.com/foaf/0.1/"));
        // when
        OWLOntology o = loadOntologyFromString(working);
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    @Test
    public void shouldParseQuotedTripleQuotes() throws OWLOntologyCreationException {
        // given
        String literal = "Diadenosine 5',5'''-P1,P4-tetraphosphate phosphorylase";
        String working = "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n "
            + "@prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n foaf:fundedBy " + "rdfs:label \"\"\"" + literal
            + "\"\"\"@en .";
        OWLAxiom expected = AnnotationAssertion(df.getRDFSLabel(), IRI("http://xmlns.com/foaf/0.1/fundedBy"), Literal(
            literal, "en"));
        // when
        OWLOntology o = loadOntologyFromString(working);
        // then
        assertEquals(expected, o.getAxioms().iterator().next());
    }

    @Test
    public void shouldParseOntologyThatBroke() throws OWLOntologyCreationException {
        // given
        String input = "@prefix f:    <urn:test/> . f:r f:p f: .";
        OWLAxiom expected = df.getOWLAnnotationAssertionAxiom(df.getOWLAnnotationProperty(IRI("urn:test/p")), IRI(
            "urn:test/r"), IRI("urn:test/"));
        // when
        OWLOntology o = loadOntologyFromString(input);
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    @Test
    public void shouldResolveAgainstBase() throws OWLOntologyCreationException {
        // given
        String input = "@base <http://test.org/path#> .\n <a1> <b1> <c1> .";
        // when
        OWLOntology o = loadOntologyFromString(input);
        // then
        String axioms = o.getAxioms().toString();
        assertTrue(axioms.contains("http://test.org/a1"));
        assertTrue(axioms.contains("http://test.org/b1"));
        assertTrue(axioms.contains("http://test.org/c1"));
    }

    // test for 3543488
    @Test
    public void shouldRoundTripTurtleWithsharedBnodes() throws Exception {
        AnonymousIndividualProperties.setRemapAllAnonymousIndividualsIds(false);
        try {
            String input = "@prefix ex: <http://example.com/test> .\n ex:ex1 a ex:Something ; ex:prop1 _:a .\n _:a a ex:Something1 ; ex:prop2 _:b .\n _:b a ex:Something ; ex:prop3 _:a .";
            OWLOntology ontology = loadOntologyFromString(input);
            OWLOntology onto2 = roundTrip(ontology, new TurtleDocumentFormat());
            equal(ontology, onto2);
        } finally {
            AnonymousIndividualProperties.resetToDefault();
        }
    }

    // test for 335
    @Test
    public void shouldParseScientificNotation() throws OWLOntologyCreationException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1e+07 .";
        OWLOntology ontology = loadOntologyFromString(input);
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(p));
        IRI i = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, i, Literal("1.0E7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    public void shouldParseScientificNotationWithMinus() throws OWLOntologyCreationException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1e-07 .";
        OWLOntology ontology = loadOntologyFromString(input);
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(p));
        IRI i = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, i, Literal("1.0E-7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    public void shouldParseScientificNotationWithMinusFromBug() throws OWLOntologyCreationException {
        String input = "<http://www.example.com/ontologies/2014/6/medicine#m.0hycptl> <http://www.example.com/ontologies/2014/6/medicine#medicine.drug_strength.strength_value> 8e-05 . \n"
            + "    <http://www.example.com/ontologies/2014/6/medicine#m.0hyckjg> <http://www.example.com/ontologies/2014/6/medicine#medicine.drug_strength.strength_value> 0.03 . \n"
            + "    <http://www.example.com/ontologies/2014/6/medicine#m.0hyckjg> <http://www.example.com/ontologies/2014/6/medicine#medicine.drug_strength.strength_value> 20.0 . \n"
            + "    <http://www.example.com/ontologies/2014/6/medicine#m.0hyckjg> <http://www.example.com/ontologies/2014/6/medicine#medicine.drug_strength.strength_value> 30.0 . \n"
            + "    <http://www.example.com/ontologies/2014/6/medicine#m.0hyckjg> <http://www.example.com/ontologies/2014/6/medicine#medicine.drug_strength.strength_value> 3.5 . ";
        loadOntologyFromString(input);
    }

    @Test
    public void shouldParseTwo() throws OWLOntologyCreationException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1 .";
        OWLOntology ontology = loadOntologyFromString(input);
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(p));
        IRI i = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, i, Literal(1))));
    }

    @Test
    public void shouldParseOne() throws OWLOntologyCreationException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1.0.";
        OWLOntology ontology = loadOntologyFromString(input);
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(p));
        IRI i = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, i, Literal("1.0", OWL2Datatype.XSD_DECIMAL))));
    }

    @Test
    public void shouldParseEmptySpaceInBnode() throws OWLOntologyCreationException {
        String input = "<http://taxonomy.wolterskluwer.de/practicearea/10112>\n a <http://schema.wolterskluwer.de/TaxonomyTerm> , <http://www.w3.org/2004/02/skos/core#Concept> ;\n"
            + "      <http://www.w3.org/2004/02/skos/core#broader>\n [] ;\n"
            + "      <http://www.w3.org/2004/02/skos/core#broader>\n [] .";
        OWLOntology ontology = loadOntologyFromString(input);
        OWLIndividual i = NamedIndividual(IRI("http://taxonomy.wolterskluwer.de/practicearea/10112"));
        OWLAnnotationProperty ap = AnnotationProperty(IRI("http://www.w3.org/2004/02/skos/core#broader"));
        OWLClass c = Class(IRI("http://www.w3.org/2004/02/skos/core#Concept"));
        OWLClass term = Class(IRI("http://schema.wolterskluwer.de/TaxonomyTerm"));
        assertTrue(ontology.containsAxiom(ClassAssertion(c, i)));
        assertTrue(ontology.containsAxiom(ClassAssertion(term, i)));
        assertTrue(ontology.containsEntityInSignature(ap));
    }

    @Test
    public void shouldRoundTripAxiomAnnotation() throws Exception {
        String input = "@prefix : <urn:fm2#> .\n" + "@prefix fm:    <urn:fm2#> .\n"
            + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n" + "@prefix prov: <urn:prov#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + "@base <urn:fm2> .\n\n"
            + "<http://www.ida.org/fm2.owl> rdf:type owl:Ontology.\n" + ":prov rdf:type owl:AnnotationProperty .\n\n"
            + ":Manage rdf:type owl:Class ; rdfs:subClassOf :ManagementType .\n" + "[ rdf:type owl:Axiom ;\n"
            + "  owl:annotatedSource :Manage ;\n" + "  owl:annotatedTarget :ManagementType ;\n"
            + "  owl:annotatedProperty rdfs:subClassOf ;\n"
            + "  :prov [\n prov:gen :FMDomain ;\n prov:att :DM .\n ]\n ] .\n" + ":ManagementType rdf:type owl:Class .\n"
            + ":DM rdf:type owl:NamedIndividual , prov:Person .\n"
            + ":FMDomain rdf:type owl:NamedIndividual , prov:Activity ; prov:ass :DM .";
        OWLOntology ontology = loadOntologyFromString(input);
        OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
        Set<OWLSubClassOfAxiom> axioms = o.getAxioms(AxiomType.SUBCLASS_OF);
        assertEquals(1, axioms.size());
        OWLAnnotation next = axioms.iterator().next().getAnnotations().iterator().next();
        assertTrue(next.getValue() instanceof OWLAnonymousIndividual);
        OWLAnonymousIndividual ind = (OWLAnonymousIndividual) next.getValue();
        Set<OWLAxiom> anns = new HashSet<>();
        for (OWLAxiom ax : o.getAxioms()) {
            if (ax.getAnonymousIndividuals().contains(ind)) {
                anns.add(ax);
            }
        }
        assertEquals(1, anns.size());
    }

    @Test
    public void shouldRoundTripAxiomAnnotationWithSlashOntologyIRI() throws Exception {
        String input = "@prefix : <urn:test#test.owl/> .\n" + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + "@base <urn:test#test.owl/> .\n"
            + "<urn:test#test.owl/> rdf:type owl:Ontology .\n" + ":q rdf:type owl:Class .\n"
            + ":t rdf:type owl:Class ; rdfs:subClassOf :q .";
        OWLOntology in = loadOntologyFromString(input);
        String string = "urn:test#test.owl/";
        OWLOntology ontology = m.createOntology(IRI.create(string));
        m.addAxiom(ontology, df.getOWLSubClassOfAxiom(df.getOWLClass(IRI.create(string + 't')), df.getOWLClass(IRI
            .create(string + 'q'))));
        OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
        equal(o, in);
    }

    @Test
    public void presentDeclaration() throws OWLOntologyCreationException {
        // given
        String input = "<urn:test#Settlement> rdf:type owl:Class.\n <urn:test#fm2.owl> rdf:type owl:Ontology.\n <urn:test#numberOfPads> rdf:type owl:ObjectProperty ;\n rdfs:domain <urn:test#Settlement> .";
        // when
        OWLOntology o = loadOntologyFromString(input);
        // then
        for (OWLLogicalAxiom ax : o.getLogicalAxioms()) {
            assertTrue(ax instanceof OWLObjectPropertyDomainAxiom);
        }
    }

    @Test
    public void whenMissingClassDeclarationCausesIllegalPunningAssertionsShouldBeFixedAfterParsing()
        throws OWLOntologyCreationException {
        // given
        String input = "<urn:test#fm2.owl> rdf:type owl:Ontology.\n <urn:test#numberOfPads> rdf:type owl:ObjectProperty ;\n rdfs:domain <urn:test#Settlement> .";
        // when
        OWLOntology o = loadOntologyFromString(input);
        // then
        // Parsed as annotation domain, fixed in post parsing to object property
        // domain
        for (OWLLogicalAxiom ax : o.getLogicalAxioms()) {
            assertTrue(ax instanceof OWLObjectPropertyDomainAxiom);
        }
    }

    @Test
    public void shouldReloadSamePrefixAbbreviations() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "@prefix : <http://www.hbp.FIXME.org/hbp_abam_ontology/> .\n"
            + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "@prefix nsu: <http://www.FIXME.org/nsupper#> .\n"
            + "@prefix ABA: <http://api.brain-map.org/api/v2/data/Structure/> .\n"
            + "@base <http://www.hbp.FIXME.org/hbp_abam_ontology> .\n"
            + "<http://www.hbp.FIXME.org/hbp_abam_ontology> rdf:type owl:Ontology .\n" + "ABA:1 rdf:type owl:Class ;\n"
            + "      rdfs:subClassOf [ rdf:type owl:Restriction ; owl:onProperty nsu:part_of ; owl:someValuesFrom ABA:10 ] .\n"
            + "ABA:10 rdf:type owl:Class ;\n"
            + "       rdfs:subClassOf [ rdf:type owl:Restriction ; owl:onProperty nsu:part_of ; owl:someValuesFrom owl:Thing ] .\n";
        OWLOntology o = loadOntologyFromString(input);
        StringDocumentTarget t = saveOntology(o);
        assertTrue(t.toString().contains("ABA:10"));
    }

    @Test
    public void shouldFindExpectedAxiomsForBlankNodes() throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLObjectProperty r = ObjectProperty(IRI.create(
            "http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        String input = "@prefix : <http://www.derivo.de/ontologies/examples/anonymous-individuals#> .\n"
            + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "<http://www.derivo.de/ontologies/examples/anonymous-individuals> a owl:Ontology .\n"
            + ":r a owl:ObjectProperty .\n" + ":C a owl:Class .\n" + "_:genid1 a :C ; :r _:genid1 .";
        OWLOntology o = loadOntologyFromString(input);
        // assertEquals(input, saveOntology(o, new
        // TurtleDocumentFormat()).toString().replaceAll("\\#.*\\n", ""));
        for (OWLClassAssertionAxiom ax : o.getAxioms(AxiomType.CLASS_ASSERTION)) {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(expected + " not found", o.containsAxiom(expected));
        }
        OWLOntology test = roundTrip(o, new TurtleDocumentFormat());
        equal(o, test);
    }

    @Test
    public void shouldAllowMultipleDotsInIRIs() throws OWLOntologyCreationException, OWLOntologyStorageException {
        IRI test1 = IRI.create("http://www.semanticweb.org/ontology#A...");
        IRI test2 = IRI.create("http://www.semanticweb.org/ontology#A...B");
        OWLOntology o = m.createOntology(IRI.create("http://www.semanticweb.org/ontology"));
        m.addAxiom(o, df.getOWLDeclarationAxiom(df.getOWLClass(test1)));
        m.addAxiom(o, df.getOWLDeclarationAxiom(df.getOWLClass(test2)));
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setDefaultPrefix("http://www.semanticweb.org/ontology#");
        roundTrip(o, format);
    }

    @Test
    public void shouldSaveWithCorrectPrefixes() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String in="@prefix OBO: <http://purl.obolibrary.org/obo/> .\n" + 
            "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n" + 
            "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" + 
            "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n" + 
            "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n" + 
            "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n\n" + 
            "<http://purl.obolibrary.org/obo/test.owl> rdf:type owl:Ontology .\n\n" + 
            "<http://purl.obolibrary.org/obo/test#foo> rdf:type owl:ObjectProperty .\n\n" + 
            "OBO:TEST_1 rdf:type owl:Class ;\n    rdfs:label \"foo\"@en .";
        OWLOntology ont = loadOntologyFromString(in);
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLOntologyFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result);
        equal(ont, o1);
    }
    @Test
    public void shouldSaveWithCorrectSlashPrefixes() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String in="@prefix OBO: <http://purl.obolibrary.org/obo/> .\n" + 
            "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n" + 
            "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" + 
            "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n" + 
            "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n" + 
            "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n\n" + 
            "<http://purl.obolibrary.org/obo/test.owl> rdf:type owl:Ontology .\n\n" + 
            "<http://purl.obolibrary.org/obo/test/foo> rdf:type owl:ObjectProperty .\n\n" + 
            "OBO:TEST_1 rdf:type owl:Class ;\n    rdfs:label \"foo\"@en .";
        OWLOntology ont = loadOntologyFromString(in);
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLOntologyFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result);
        equal(ont, o1);
    }
}
