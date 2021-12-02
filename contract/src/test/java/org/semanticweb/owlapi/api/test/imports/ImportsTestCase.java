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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ImportsDeclaration;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.Profiles;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
class ImportsTestCase extends TestBase {

    static final String loadRight = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl =\"http://www.w3.org/2002/07/owl#\">\n"
        + "    <owl:Ontology><owl:imports rdf:resource=\"urn:test#test\"/></owl:Ontology></rdf:RDF>";
    static final String unloadableImport = "<?xml version=\"1.0\"?>\n"
        + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
        + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
        + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
        + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
        + "    <owl:Ontology rdf:about=\"http://www.semanticweb.org/fake/ontologies/2012/8/1\">\n"
        + "        <owl:imports rdf:resource=\"http://localhost:1\"/>\n" + "    </owl:Ontology>\n"
        + "</rdf:RDF>";
    static final String remapImportRdfXml =
        "<?xml version=\"1.0\"?>\n" + "<rdf:RDF xmlns=\"urn:test#\"\n"
            + "     xml:base=\"urn:test\"\n" + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
            + "    <owl:Ontology rdf:about=\"urn:test\">\n"
            + "        <owl:imports rdf:resource=\"http://test.org/TestPizzaImport.owl\"/>\n"
            + "    </owl:Ontology>\n" + "</rdf:RDF>";
    static final String remapImport = "<?xml version=\"1.0\"?>\n"
        + "<Ontology  ontologyIRI=\"http://protege.org/ontologies/TestFunnyPizzaImport.owl\">\n"
        + "    <Import>http://test.org/TestPizzaImport.owl</Import>\n" + "</Ontology>";
    static final String IMPORTS = "imports";

    @Test
    void testImportsClosureUpdate() {
        IRI aIRI = IRI("http://a.com", "");
        OWLOntology ontA = getOWLOntology(aIRI);
        IRI bIRI = IRI("http://b.com", "");
        OWLOntology ontB = getOWLOntology(bIRI);
        ontA.getOWLOntologyManager()
            .applyChange(new AddImport(ontA, df.getOWLImportsDeclaration(bIRI)));
        assertEquals(2, m.getImportsClosure(ontA).size());
        m.removeOntology(ontB);
        assertEquals(1, m.getImportsClosure(ontA).size());
        getOWLOntology(bIRI);
        assertEquals(2, m.getImportsClosure(ontA).size());
    }

    @Test
    void shouldLoad() {
        File importsBothNameAndVersion = new File(folder, "tempimportsNameAndVersion.owl");
        File importsBothNameAndOther = new File(folder, "tempimportsNameAndOther.owl");
        File ontologyByName = new File(folder, "tempmain.owl");
        File ontologyByVersion = new File(folder, "tempversion.owl");
        File ontologyByOtherPath = new File(folder, "tempother.owl");
        OWLOntology ontology =
            getOWLOntology(new OWLOntologyID(iri(ontologyByName), iri(ontologyByVersion)));
        saveOntology(ontology, iri(ontologyByName));
        saveOntology(ontology, iri(ontologyByVersion));
        saveOntology(ontology, iri(ontologyByOtherPath));
        OWLOntology ontology1 = getOWLOntology(iri(importsBothNameAndVersion));
        OWLOntology ontology2 = getOWLOntology(iri(importsBothNameAndOther));
        List<AddImport> changes = new ArrayList<>();
        changes.add(new AddImport(ontology1, df.getOWLImportsDeclaration(iri(ontologyByName))));
        changes.add(new AddImport(ontology1, df.getOWLImportsDeclaration(iri(ontologyByVersion))));
        changes.add(new AddImport(ontology2, df.getOWLImportsDeclaration(iri(ontologyByName))));
        changes
            .add(new AddImport(ontology2, df.getOWLImportsDeclaration(iri(ontologyByOtherPath))));
        ontology1.getOWLOntologyManager().applyChanges(changes);
        ontology2.getOWLOntologyManager().applyChanges(changes);
        saveOntology(ontology1, iri(importsBothNameAndVersion));
        saveOntology(ontology2, iri(importsBothNameAndOther));
        // when
        OWLOntology o1 = loadOntology(iri(importsBothNameAndVersion), m);
        OWLOntology o2 = loadOntology(iri(importsBothNameAndOther), m);
        // then
        assertNotNull(o1);
        assertNotNull(o2);
    }

    @Test
    public void shouldNotLoadWrong() {
        OWLOntology first = getOWLOntology(iri("urn:test#", "test"));
        StringDocumentSource documentSource = new StringDocumentSource(TestFiles.loadRight);
        OWLOntology o = loadOntologyFromSource(documentSource, first.getOWLOntologyManager());
        assertTrue(o.isAnonymous());
        assertFalse(o.getOntologyID().getDefaultDocumentIRI().isPresent());
    }

    @Test
    void testManualImports() {
        OWLOntology baseOnt = getOWLOntology(IRI("http://semanticweb.org/ontologies/", "base"));
        IRI importedIRI = IRI("http://semanticweb.org/ontologies/", "imported");
        OWLOntology importedOnt = getOWLOntology(importedIRI);
        Set<OWLOntology> preImportsClosureCache = baseOnt.getImportsClosure();
        assertTrue(preImportsClosureCache.contains(baseOnt));
        assertFalse(preImportsClosureCache.contains(importedOnt));
        baseOnt.getOWLOntologyManager()
            .applyChange(new AddImport(baseOnt, df.getOWLImportsDeclaration(importedIRI)));
        Set<OWLOntology> postImportsClosureCache = baseOnt.getImportsClosure();
        assertTrue(postImportsClosureCache.contains(baseOnt));
        assertTrue(postImportsClosureCache.contains(importedOnt));
    }

    @Test
    void shouldThrowExceptionWithDefaultImportsconfig() {
        assertThrows(UnloadableImportException.class,
            () -> loadOntologyFromString(TestFiles.unloadableImport, new RDFXMLDocumentFormat()));
    }

    @Test
    void testImports() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, IMPORTS), true));
        loadOntologyFromFile(new File(RESOURCES, "/imports/D.owl"), m);
    }

    @Test
    void testCyclicImports() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        loadOntologyFromFile(new File(RESOURCES, "/importscyclic/D.owl"), m);
    }

    @Test
    void testCyclicImports2() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        loadOntology(iri(new File(RESOURCES, "importscyclic/D.owl")), m);
    }

    @Test
    void testTurtleGraphImport() {
        // document without ontology declaration should be removed and
        // reimported
        File ontologyDirectory = new File(RESOURCES, "importNoOntology");
        String ns = "http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/";
        IRI bobsOntologyName = iri(ns, "subject-bob");
        OWLNamedIndividual bobsIndividual =
            df.getOWLNamedIndividual(iri(ns + "subject-bob#", "subjectOnImmunosuppressantA2"));
        m.getIRIMappers().add(new SimpleIRIMapper(iri(ns, "subject-amy"),
            iri(new File(ontologyDirectory, "subject-amy.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(bobsOntologyName,
            iri(new File(ontologyDirectory, "subject-bob.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(iri(ns, "subject-sue"),
            iri(new File(ontologyDirectory, "subject-sue.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(iri("http://www.w3.org/2013/12/FDA-TA/", "core"),
            iri(new File(ontologyDirectory, "core.ttl"))));
        OWLOntology topLevelImport =
            loadOntologyFromFile(new File(ontologyDirectory, "subjects.ttl"), m);
        assertTrue(topLevelImport.containsEntityInSignature(bobsIndividual, INCLUDED),
            "Individuals about Bob are missing...");
    }

    /**
     * Tests to see if the method which obtains the imports closure behaves correctly.
     */
    @Test
    void testImportsClosure() {
        OWLOntology ontA = getOWLOntology();
        OWLOntology ontB = getOWLOntology();
        assertTrue(ontA.getImportsClosure().contains(ontA));
        OWLImportsDeclaration importsDeclaration =
            ImportsDeclaration(ontB.getOntologyID().getOntologyIRI().get());
        ontA.getOWLOntologyManager().applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(ontA.getImportsClosure().contains(ontB));
        ontA.getOWLOntologyManager().applyChange(new RemoveImport(ontA, importsDeclaration));
        assertFalse(ontA.getImportsClosure().contains(ontB));
        ontA.getOWLOntologyManager().applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(ontA.getImportsClosure().contains(ontB));
        ontB.getOWLOntologyManager().removeOntology(ontB);
        assertFalse(ontA.getImportsClosure().contains(ontB));
    }

    @Test
    void shouldRemapImport() {
        IRI testImport = iri("http://test.org/", "TestPizzaImport.owl");
        IRI remap = iri("urn:test:", "mockImport");
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        getOWLOntology(remap);
        OWLOntology o = loadOntologyFromSource(new StringDocumentSource(TestFiles.remapImport), m);
        assertEquals(1, o.getImportsDeclarations().size());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    void shouldRemapImportRdfXML() {
        IRI testImport = iri("http://test.org/", "TestPizzaImport.owl");
        IRI remap = iri("urn:test:", "mockImport");
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        getOWLOntology(remap);
        OWLOntology o =
            loadOntologyFromSource(new StringDocumentSource(TestFiles.remapImportRdfXml), m);
        assertEquals(1, o.getImportsDeclarations().size());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    void testImportOntologyByLocation() {
        File f = new File(folder, "a.owl");
        createOntologyFile(IRI("http://a.com", ""), f);
        // have to load an ontology for it to get a document IRI
        OWLOntology a = loadOntologyFromFile(f, m);
        IRI locA = m.getOntologyDocumentIRI(a);
        IRI bIRI = IRI("http://b.com", "");
        OWLOntology b = getOWLOntology(bIRI);
        // import from the document location of a.owl (rather than the
        // ontology IRI)
        b.getOWLOntologyManager().applyChange(new AddImport(b, df.getOWLImportsDeclaration(locA)));
        assertEquals(1, b.getImportsDeclarations().size());
        assertEquals(1, b.getImports().size());
    }

    @Test
    void shouldNotCreateIllegalPunning() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        OWLOntology o = loadOntology(iri(new File(RESOURCES, "importscyclic/relaMath.owl")), m);
        OWLProfileReport checkOntology = Profiles.OWL2_DL.checkOntology(o);
        assertTrue(checkOntology.isInProfile(), checkOntology.toString());
        o.getDirectImports()
            .forEach(ont -> assertEquals(0, ont.getAnnotationPropertiesInSignature().size()));
    }

    @Test
    void testImportsWhenRemovingAndReloading() throws URISyntaxException {
        OWLOntologyManager man = setupManager();
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true);
        man.getIRIMappers().add(mapper);
        String name = "/imports/thesubont.omn";
        File file = new File(getClass().getResource(name).toURI());
        OWLOntology root = loadOntologyFromFile(file, man);
        assertEquals(1, root.getImports().size());
        man.getOntologies().forEach(o -> man.removeOntology(o));
        assertEquals(0, man.getOntologies().size());
        root = loadOntologyFromFile(file, man);
        assertEquals(1, root.getImports().size());
    }

    @Test
    void testAutoIRIMapperShouldNotBeConfusedByPrefixes() {
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true);
        assertTrue(mapper.getOntologyIRIs()
            .contains(iri("http://owlapitestontologies.com/", "thesubont")));
    }

    @Test
    void testAutoIRIMapperShouldRecogniseRdfAboutInOwlOntology() {
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true);
        assertTrue(
            mapper.getOntologyIRIs().contains(iri("http://test.org/compleximports/", "A.owl")));
    }
}
