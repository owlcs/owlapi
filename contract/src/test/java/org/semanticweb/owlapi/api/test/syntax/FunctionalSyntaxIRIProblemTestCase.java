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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

@SuppressWarnings("javadoc")
public class FunctionalSyntaxIRIProblemTestCase extends TestBase {

    @Test
    public void testmain() throws Exception {
        OWLOntology ontology = getOWLOntology();
        OWLObjectProperty p = df.getOWLObjectProperty("http://example.org/A_#", "part_of");
        OWLClass a = Class(IRI("http://example.org/", "A_A"));
        OWLClass b = Class(IRI("http://example.org/", "A_B"));
        ontology.add(Declaration(p), Declaration(a), Declaration(b),
            SubClassOf(b, df.getOWLObjectSomeValuesFrom(p, a)));
        OWLOntology loadOntology = roundTrip(ontology, new RDFXMLDocumentFormat());
        FunctionalSyntaxDocumentFormat functionalFormat = new FunctionalSyntaxDocumentFormat();
        ontology.getPrefixManager().setPrefix("example", "http://example.org/");
        OWLOntology loadOntology2 = roundTrip(ontology, functionalFormat);
        // won't reach here if functional syntax fails - comment it out and
        // uncomment this to test Manchester
        ManchesterSyntaxDocumentFormat manchesterFormat = new ManchesterSyntaxDocumentFormat();
        ontology.getPrefixManager().setPrefix("example", "http://example.org/");
        OWLOntology loadOntology3 = roundTrip(ontology, manchesterFormat);
        assertEquals(ontology, loadOntology);
        assertEquals(ontology, loadOntology2);
        assertEquals(ontology, loadOntology3);
        assertTrue(ontology.equalAxioms(loadOntology));
        assertTrue(ontology.equalAxioms(loadOntology2));
        assertTrue(ontology.equalAxioms(loadOntology3));
    }

    @Test
    public void shouldRespectDefaultPrefix()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = m.createOntology(IRI.create("http://www.dis.uniroma1.it/example/"));
        PrefixManager pm = new DefaultPrefixManager();
        pm.setPrefix("example", "http://www.dis.uniroma1.it/example/");
        OWLClass pizza = df.getOWLClass("example:pizza", pm);
        OWLDeclarationAxiom declarationAxiom = df.getOWLDeclarationAxiom(pizza);
        m.addAxiom(ontology, declarationAxiom);
        FunctionalSyntaxDocumentFormat ontoFormat = new FunctionalSyntaxDocumentFormat();
        ontology.getPrefixManager().copyPrefixesFrom(pm);
        m.setOntologyFormat(ontology, ontoFormat);
        StringDocumentTarget documentTarget = new StringDocumentTarget();
        m.saveOntology(ontology, documentTarget);
        assertTrue(documentTarget.toString().contains("example:pizza"));
    }

    @Test
    public void shouldConvertToFunctionalCorrectly() throws OWLOntologyStorageException {
        String in = "Prefix: : <http://purl.obolibrary.org/obo/>\n"
            + "Ontology: <http://example.org/>\n" + "Class: :FOO_0000001";
        OWLOntology o = loadOntologyFromString(in, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 =
            loadOntologyFromString(saveOntology(o, new FunctionalSyntaxDocumentFormat()),
                new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
    }

    @Test
    public void shouldPreservePrefix()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String prefix = "http://www.dis.uniroma1.it/pizza";
        OWLOntology ontology = m.createOntology(IRI.create(prefix));
        PrefixManager pm = new DefaultPrefixManager();
        pm.setPrefix("pizza", prefix);
        OWLClass pizza = df.getOWLClass("pizza:PizzaBase", pm);
        assertEquals(prefix + "PizzaBase", pizza.getIRI().toString());
        OWLDeclarationAxiom declarationAxiom = df.getOWLDeclarationAxiom(pizza);
        m.addAxiom(ontology, declarationAxiom);
        FunctionalSyntaxDocumentFormat ontoFormat = new FunctionalSyntaxDocumentFormat();
        ontology.getPrefixManager().setPrefix("pizza", prefix);
        m.setOntologyFormat(ontology, ontoFormat);
        OWLOntologyDocumentTarget stream = new StringDocumentTarget();
        m.saveOntology(ontology, stream);
        assertTrue(stream.toString().contains("pizza:PizzaBase"));
    }

    @Test
    public void shouldRoundtripIRIsWithQueryString() throws OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF xmlns=\"http://purl.obolibrary.org/obo/TEMP#\" xml:base=\"http://purl.obolibrary.org/obo/TEMP\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:oboInOwl=\"http://www.geneontology.org/formats/oboInOwl#\" xmlns:obo1=\"http://purl.obolibrary.org/obo/\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "    <owl:Ontology rdf:about=\"http://purl.obolibrary.org/obo/TEMP\"/>\n"
            + "    <owl:Class rdf:about=\"obo1:X\"><rdfs:seeAlso rdf:resource=\"http://purl.obolibrary.org/obo/?func=detail&amp;\"/></owl:Class>\n"
            + "</rdf:RDF>";
        OWLOntology o = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        StringDocumentTarget saveOntology = saveOntology(o, new FunctionalSyntaxDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(saveOntology, new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
    }
}
