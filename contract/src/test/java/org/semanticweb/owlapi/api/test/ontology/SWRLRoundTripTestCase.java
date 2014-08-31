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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings("javadoc")
public class SWRLRoundTripTestCase extends TestBase {

    @Nonnull
    private static final String NS = "urn:test";

    @Test
    public void shouldDoCompleteRoundtrip() throws Exception {
        OWLClass a = Class(IRI(NS + "#A"));
        OWLDataProperty p = df.getOWLDataProperty(IRI(NS + "#P"));
        SWRLVariable x = df.getSWRLVariable(IRI(NS + "#X"));
        SWRLVariable y = df.getSWRLVariable(IRI(NS + "#Y"));
        OWLOntology ontology = m.createOntology(IRI(NS));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(df.getSWRLDataPropertyAtom(p, x, y));
        body.add(df.getSWRLDataRangeAtom(
                df.getOWLDatatype(XSDVocabulary.STRING.getIRI()), y));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(df.getSWRLClassAtom(a, x));
        SWRLRule rule = df.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        ontology = roundTrip(ontology, new OWLXMLDocumentFormat());
        OWLOntology onto2 = roundTrip(ontology, new OWLXMLDocumentFormat());
        equal(ontology, onto2);
    }

    @Test
    public void shouldDoCompleteRoundtripManchesterOWLSyntax() throws Exception {
        OWLClass a = Class(IRI(NS + "#A"));
        OWLDataProperty p = df.getOWLDataProperty(IRI(NS + "#P"));
        SWRLVariable x = df.getSWRLVariable(IRI(NS + "#X"));
        SWRLVariable y = df.getSWRLVariable(IRI(NS + "#Y"));
        OWLOntology ontology = m.createOntology(IRI(NS));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(df.getSWRLDataPropertyAtom(p, x, y));
        body.add(df.getSWRLDataRangeAtom(
                df.getOWLDatatype(XSDVocabulary.STRING.getIRI()), y));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(df.getSWRLClassAtom(a, x));
        SWRLRule rule = df.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        ontology = roundTrip(ontology, new ManchesterSyntaxDocumentFormat());
        OWLOntology onto2 = roundTrip(ontology,
                new ManchesterSyntaxDocumentFormat());
        equal(ontology, onto2);
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsOWLXML()
            throws Exception {
        OWLOntology ontology = prepareOntology();
        OWLXMLDocumentFormat f = new OWLXMLDocumentFormat();
        OWLOntology ontology2 = loadOntologyFromString(saveOntology(ontology, f));
        equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsTurtle()
            throws Exception {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat f = new TurtleDocumentFormat();
        OWLOntology ontology2 = loadOntologyFromString(saveOntology(ontology, f));
        equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsFunctional()
            throws Exception {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat f = new FunctionalSyntaxDocumentFormat();
        OWLOntology ontology2 = loadOntologyFromString(saveOntology(ontology, f));
        equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsRDFXML()
            throws Exception {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat f = new RDFXMLDocumentFormat();
        OWLOntology ontology2 = loadOntologyFromString(saveOntology(ontology, f));
        equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsDatatypeRDFXML()
            throws Exception {
        OWLOntology ontology = prepareOntology1();
        OWLDocumentFormat f = new RDFXMLDocumentFormat();
        OWLOntology ontology2 = loadOntologyFromString(saveOntology(ontology, f));
        equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
    }

    @Ignore("man syntax does not like annotations")
    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsMan() throws Exception {
        OWLOntology ontology = prepareOntology();
        OWLDocumentFormat f = new ManchesterSyntaxDocumentFormat();
        StringDocumentTarget save = saveOntology(ontology, f);
        OWLOntology ontology2 = loadOntologyFromString(save);
        equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(df.getRDFSLabel()).isEmpty());
        }
    }

    /**
     * @return
     * @throws OWLOntologyCreationException
     */
    @Nonnull
    OWLOntology prepareOntology() throws OWLOntologyCreationException {
        OWLClass a = Class(IRI(NS + "#A"));
        OWLDataProperty p = df.getOWLDataProperty(IRI(NS + "#P"));
        SWRLVariable x = df.getSWRLVariable(IRI(NS + "#X"));
        SWRLVariable y = df.getSWRLVariable(IRI(NS + "#Y"));
        OWLOntology ontology = m.createOntology(IRI(NS));
        Set<SWRLAtom> body = new TreeSet<>();
        body.add(df.getSWRLDataPropertyAtom(p, x, y));
        body.add(df.getSWRLDataRangeAtom(
                df.getOWLDatatype(XSDVocabulary.STRING.getIRI()), y));
        Set<SWRLAtom> head = new TreeSet<>();
        head.add(df.getSWRLClassAtom(a, x));
        OWLAnnotation ann = df.getOWLAnnotation(df.getRDFSLabel(),
                df.getOWLLiteral("test", ""));
        SWRLRule rule = df.getSWRLRule(body, head, singleton(ann));
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        OWLDatatypeDefinitionAxiom def = df.getOWLDatatypeDefinitionAxiom(
                df.getOWLDatatype(IRI.create("urn:mydatatype")),
                df.getOWLDatatypeMaxExclusiveRestriction(200D),
                singleton(df.getOWLAnnotation(df.getRDFSLabel(),
                        df.getOWLLiteral("datatype definition"))));
        ontology.getOWLOntologyManager().addAxiom(ontology, def);
        return ontology;
    }

    @Nonnull
    OWLOntology prepareOntology1() throws OWLOntologyCreationException {
        OWLOntology ontology = m.createOntology(IRI(NS));
        OWLDatatypeDefinitionAxiom def = df.getOWLDatatypeDefinitionAxiom(
                df.getOWLDatatype(IRI.create("urn:mydatatype")),
                df.getOWLDatatypeMaxExclusiveRestriction(200D),
                singleton(df.getOWLAnnotation(df.getRDFSLabel(),
                        df.getOWLLiteral("datatype definition"))));
        ontology.getOWLOntologyManager().addAxiom(ontology, def);
        return ontology;
    }

    @Test
    public void shouldParse() throws OWLOntologyCreationException {
        String s = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF xmlns=\"urn:test#\"\n"
                + "     xml:base=\"urn:test\"\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "     xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n"
                + "     xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\"\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                + "    <owl:Ontology rdf:about=\"urn:test\"/>\n"
                + "    <rdfs:Datatype rdf:about=\"urn:mydatatype\">\n"
                + "        <owl:equivalentClass>\n"
                + "            <rdfs:Datatype rdf:about=\"http://www.w3.org/2001/XMLSchema#double\"/>\n"
                + "        </owl:equivalentClass>\n"
                + "    </rdfs:Datatype>\n"
                + "    <owl:Axiom>\n"
                + "        <rdfs:label >datatype definition</rdfs:label>\n"
                + "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2002/07/owl#equivalentClass\"/>\n"
                + "        <owl:annotatedSource rdf:resource=\"urn:mydatatype\"/>\n"
                + "        <owl:annotatedTarget>\n"
                + "            <rdfs:Datatype rdf:about=\"http://www.w3.org/2001/XMLSchema#double\"/>\n"
                + "        </owl:annotatedTarget>\n"
                + "    </owl:Axiom></rdf:RDF>";
        OWLOntology o = loadOntologyFromString(s);
        OWLDatatypeDefinitionAxiom def = df.getOWLDatatypeDefinitionAxiom(
                df.getOWLDatatype(IRI.create("urn:mydatatype")),
                df.getDoubleOWLDatatype(),
                singleton(df.getOWLAnnotation(df.getRDFSLabel(),
                        df.getOWLLiteral("datatype definition", ""))));
        assertTrue(o.getAxioms().contains(def));
    }

    @Test
    public void shouldParse2() throws OWLOntologyCreationException {
        String s = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xml:base=\"http://www.w3.org/2002/07/owl\"\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                + "    <Class rdf:about=\"urn:myClass\">\n"
                + "        <rdfs:subClassOf rdf:resource=\"urn:test\"/>\n"
                + "    </Class>\n"
                + "    <Axiom>\n"
                + "        <rdfs:label>datatype definition</rdfs:label>\n"
                + "        <annotatedProperty rdf:resource=\"http://www.w3.org/2000/01/rdf-schema#subClassOf\"/>\n"
                + "        <annotatedSource rdf:resource=\"urn:myClass\"/>\n"
                + "        <annotatedTarget rdf:resource=\"urn:test\"/>\n"
                + "    </Axiom>\n" + "    <Class rdf:about=\"urn:test\"/>\n"
                + "</rdf:RDF>\n" + "";
        OWLOntology o = loadOntologyFromString(s);
        OWLSubClassOfAxiom def = df.getOWLSubClassOfAxiom(
                df.getOWLClass(IRI.create("urn:myClass")),
                df.getOWLClass(IRI.create("urn:test")),
                singleton(df.getOWLAnnotation(df.getRDFSLabel(),
                        df.getOWLLiteral("datatype definition", ""))));
        assertTrue(o.getAxioms().contains(def));
    }
}
