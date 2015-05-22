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
package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class ImportsTestCase extends TestBase {

    @Test
    public void testImportsClosureUpdate() throws OWLOntologyCreationException {
        IRI aIRI = IRI("http://a.com");
        OWLOntology ontA = getOWLOntology(aIRI);
        IRI bIRI = IRI("http://b.com");
        OWLOntology ontB = getOWLOntology(bIRI);
        ontA.applyChange(new AddImport(ontA, df.getOWLImportsDeclaration(bIRI)));
        assertEquals(2, m.importsClosure(ontA).count());
        m.removeOntology(ontB);
        assertEquals(1, m.importsClosure(ontA).count());
        getOWLOntology(bIRI);
        assertEquals(2, m.importsClosure(ontA).count());
    }

    @Test
    public void shouldLoad() throws Exception {
        @Nonnull
        File importsBothNameAndVersion = folder.newFile("tempimportsNameAndVersion.owl");
        @Nonnull
        File importsBothNameAndOther = folder.newFile("tempimportsNameAndOther.owl");
        @Nonnull
        File ontologyByName = folder.newFile("tempmain.owl");
        @Nonnull
        File ontologyByVersion = folder.newFile("tempversion.owl");
        @Nonnull
        File ontologyByOtherPath = folder.newFile("tempother.owl");
        OWLOntology ontology = getOWLOntology(
                new OWLOntologyID(optional(IRI.create(ontologyByName)), optional(IRI.create(ontologyByVersion))));
        ontology.saveOntology(IRI.create(ontologyByName));
        ontology.saveOntology(IRI.create(ontologyByVersion));
        ontology.saveOntology(IRI.create(ontologyByOtherPath));
        OWLOntology ontology1 = m1.createOntology(IRI.create(importsBothNameAndVersion));
        OWLOntology ontology2 = m1.createOntology(IRI.create(importsBothNameAndOther));
        List<AddImport> changes = new ArrayList<>();
        changes.add(new AddImport(ontology1, df.getOWLImportsDeclaration(IRI.create(ontologyByName))));
        changes.add(new AddImport(ontology1, df.getOWLImportsDeclaration(IRI.create(ontologyByVersion))));
        changes.add(new AddImport(ontology2, df.getOWLImportsDeclaration(IRI.create(ontologyByName))));
        changes.add(new AddImport(ontology2, df.getOWLImportsDeclaration(IRI.create(ontologyByOtherPath))));
        ontology1.applyChanges(changes);
        ontology2.applyChanges(changes);
        ontology1.saveOntology(IRI.create(importsBothNameAndVersion));
        ontology2.saveOntology(IRI.create(importsBothNameAndOther));
        // when
        OWLOntology o1 = m.loadOntology(IRI.create(importsBothNameAndVersion));
        OWLOntology o2 = m1.loadOntology(IRI.create(importsBothNameAndOther));
        // then
        assertNotNull(o1);
        assertNotNull(o2);
    }

    @Test
    public void shouldNotLoadWrong() throws OWLOntologyCreationException {
        m.createOntology(IRI.create("urn:test"));
        StringDocumentSource documentSource = new StringDocumentSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl =\"http://www.w3.org/2002/07/owl#\">\n"
                + "    <owl:Ontology><owl:imports rdf:resource=\"urn:test\"/></owl:Ontology></rdf:RDF>");
        OWLOntology o = m.loadOntologyFromOntologyDocument(documentSource);
        assertTrue(o.getOntologyID().toString(), o.isAnonymous());
        assertFalse(o.getOntologyID().getDefaultDocumentIRI().isPresent());
    }

    @Test
    public void testManualImports() throws OWLOntologyCreationException {
        OWLOntology baseOnt = getOWLOntology(IRI("http://semanticweb.org/ontologies/base"));
        IRI importedIRI = IRI("http://semanticweb.org/ontologies/imported");
        OWLOntology importedOnt = getOWLOntology(importedIRI);
        Set<OWLOntology> preImportsClosureCache = asSet(baseOnt.importsClosure());
        assertTrue(preImportsClosureCache.contains(baseOnt));
        assertFalse(preImportsClosureCache.contains(importedOnt));
        baseOnt.applyChange(new AddImport(baseOnt, df.getOWLImportsDeclaration(importedIRI)));
        Set<OWLOntology> postImportsClosureCache = asSet(baseOnt.importsClosure());
        assertTrue(postImportsClosureCache.contains(baseOnt));
        assertTrue(postImportsClosureCache.contains(importedOnt));
    }

    @Test(expected = UnloadableImportException.class)
    public void shouldThrowExceptionWithDefaultImportsconfig() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n" + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                + "    <owl:Ontology rdf:about=\"http://www.semanticweb.org/fake/ontologies/2012/8/1\">\n"
                + "        <owl:imports rdf:resource=\"http://localhost:1\"/>\n" + "    </owl:Ontology>\n"
                + "</rdf:RDF>";
        loadOntologyFromString(input);
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "imports"), true));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/imports/D.owl"));
    }

    @Test
    public void testCyclicImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/importscyclic/D.owl"));
    }

    @Test
    public void testCyclicImports2() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        m.loadOntologyFromOntologyDocument(IRI.create(new File(RESOURCES, "importscyclic/D.owl")));
    }

    @Test
    public void testTurtleGraphImport() throws OWLOntologyCreationException {
        // document without ontology declaration should be removed and
        // reimported
        File ontologyDirectory = new File(RESOURCES, "importNoOntology");
        String ns = "http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/";
        IRI bobsOntologyName = IRI.create(ns + "subject-bob");
        OWLNamedIndividual bobsIndividual = df.getOWLNamedIndividual(ns + "subject-bob#subjectOnImmunosuppressantA2");
        m.getIRIMappers().add(new SimpleIRIMapper(IRI.create(ns + "subject-amy"),
                IRI.create(new File(ontologyDirectory, "subject-amy.ttl"))));
        m.getIRIMappers()
                .add(new SimpleIRIMapper(bobsOntologyName, IRI.create(new File(ontologyDirectory, "subject-bob.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(IRI.create(ns + "subject-sue"),
                IRI.create(new File(ontologyDirectory, "subject-sue.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(IRI.create("http://www.w3.org/2013/12/FDA-TA/core"),
                IRI.create(new File(ontologyDirectory, "core.ttl"))));
        OWLOntology topLevelImport = m.loadOntologyFromOntologyDocument(new File(ontologyDirectory, "subjects.ttl"));
        assertTrue("Individuals about Bob are missing...",
                topLevelImport.containsEntityInSignature(bobsIndividual, INCLUDED));
    }

    /**
     * Tests to see if the method which obtains the imports closure behaves
     * correctly.
     */
    @Test
    public void testImportsClosure() {
        OWLOntology ontA = getOWLOntology();
        OWLOntology ontB = getOWLOntology();
        assertTrue(contains(ontA.importsClosure(), ontA));
        OWLImportsDeclaration importsDeclaration = ImportsDeclaration(get(ontB.getOntologyID().getOntologyIRI()));
        ontA.applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(contains(ontA.importsClosure(), ontB));
        ontA.applyChange(new RemoveImport(ontA, importsDeclaration));
        assertFalse(contains(ontA.importsClosure(), ontB));
        ontA.applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(contains(ontA.importsClosure(), ontB));
        ontB.getOWLOntologyManager().removeOntology(ontB);
        assertFalse(contains(ontA.importsClosure(), ontB));
    }

    @Test
    public void shouldRemapImport() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n"
                + "<Ontology  ontologyIRI=\"http://protege.org/ontologies/TestFunnyPizzaImport.owl\">\n"
                + "    <Import>http://test.org/TestPizzaImport.owl</Import>\n" + "</Ontology>";
        IRI testImport = IRI.create("http://test.org/TestPizzaImport.owl");
        IRI remap = IRI.create("urn:test:mockImport");
        StringDocumentSource source = new StringDocumentSource(input);
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        m.createOntology(remap);
        OWLOntology o = m.loadOntologyFromOntologyDocument(source);
        assertEquals(1, o.importsDeclarations().count());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    public void shouldRemapImportRdfXML() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n" + "<rdf:RDF xmlns=\"urn:test#\"\n" + "     xml:base=\"urn:test\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                + "    <owl:Ontology rdf:about=\"urn:test\">\n"
                + "        <owl:imports rdf:resource=\"http://test.org/TestPizzaImport.owl\"/>\n"
                + "    </owl:Ontology>\n" + "</rdf:RDF>";
        IRI testImport = IRI.create("http://test.org/TestPizzaImport.owl");
        IRI remap = IRI.create("urn:test:mockImport");
        StringDocumentSource source = new StringDocumentSource(input);
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        m.createOntology(remap);
        OWLOntology o = m.loadOntologyFromOntologyDocument(source);
        assertEquals(1, o.importsDeclarations().count());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    public void testImportOntologyByLocation() throws Exception {
        @Nonnull
        File f = folder.newFile("a.owl");
        createOntologyFile(IRI("http://a.com"), f);
        // have to load an ontology for it to get a document IRI
        OWLOntology a = m.loadOntologyFromOntologyDocument(f);
        IRI locA = m.getOntologyDocumentIRI(a);
        IRI bIRI = IRI("http://b.com");
        OWLOntology b = getOWLOntology(bIRI);
        // import from the document location of a.owl (rather than the
        // ontology IRI)
        b.applyChange(new AddImport(b, df.getOWLImportsDeclaration(locA)));
        assertEquals(1, b.importsDeclarations().count());
        assertEquals(1, b.imports().count());
    }

    private OWLOntology createOntologyFile(IRI iri, File f) throws Exception {
        OWLOntology a = m1.createOntology(iri);
        try (OutputStream out = new FileOutputStream(f)) {
            a.saveOntology(out);
        }
        return a;
    }
}
