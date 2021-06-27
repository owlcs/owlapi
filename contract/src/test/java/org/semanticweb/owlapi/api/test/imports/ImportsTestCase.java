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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
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
        ontA.applyChange(new AddImport(ontA, df.getOWLImportsDeclaration(bIRI)));
        assertEquals(2, m.importsClosure(ontA).count());
        m.removeOntology(ontB);
        assertEquals(1, m.importsClosure(ontA).count());
        getOWLOntology(bIRI);
        assertEquals(2, m.importsClosure(ontA).count());
    }

    @Test
    void shouldLoad() throws Exception {
        File importsBothNameAndVersion = new File(folder, "tempimportsNameAndVersion.owl");
        File importsBothNameAndOther = new File(folder, "tempimportsNameAndOther.owl");
        File ontologyByName = new File(folder, "tempmain.owl");
        File ontologyByVersion = new File(folder, "tempversion.owl");
        File ontologyByOtherPath = new File(folder, "tempother.owl");
        OWLOntology ontology = getOWLOntology(
            new OWLOntologyID(optional(iri(ontologyByName)), optional(iri(ontologyByVersion))));
        ontology.saveOntology(iri(ontologyByName));
        ontology.saveOntology(iri(ontologyByVersion));
        ontology.saveOntology(iri(ontologyByOtherPath));
        OWLOntology ontology1 = m1.createOntology(iri(importsBothNameAndVersion));
        OWLOntology ontology2 = m1.createOntology(iri(importsBothNameAndOther));
        List<AddImport> changes = new ArrayList<>();
        changes.add(new AddImport(ontology1, df.getOWLImportsDeclaration(iri(ontologyByName))));
        changes.add(new AddImport(ontology1, df.getOWLImportsDeclaration(iri(ontologyByVersion))));
        changes.add(new AddImport(ontology2, df.getOWLImportsDeclaration(iri(ontologyByName))));
        changes
            .add(new AddImport(ontology2, df.getOWLImportsDeclaration(iri(ontologyByOtherPath))));
        ontology1.applyChanges(changes);
        ontology2.applyChanges(changes);
        ontology1.saveOntology(iri(importsBothNameAndVersion));
        ontology2.saveOntology(iri(importsBothNameAndOther));
        // when
        OWLOntology o1 = m.loadOntology(iri(importsBothNameAndVersion));
        OWLOntology o2 = m1.loadOntology(iri(importsBothNameAndOther));
        // then
        assertNotNull(o1);
        assertNotNull(o2);
    }

    @Test
    public void shouldNotLoadWrong() throws OWLOntologyCreationException {
        m.createOntology(IRI.create("urn:test#", "test"));
        StringDocumentSource documentSource = new StringDocumentSource(TestFiles.loadRight);
        OWLOntology o = m.loadOntologyFromOntologyDocument(documentSource);
        assertTrue(o.isAnonymous());
        assertFalse(o.getOntologyID().getDefaultDocumentIRI().isPresent());
    }

    @Test
    void testManualImports() {
        OWLOntology baseOnt = getOWLOntology(IRI("http://semanticweb.org/ontologies/", "base"));
        IRI importedIRI = IRI("http://semanticweb.org/ontologies/", "imported");
        OWLOntology importedOnt = getOWLOntology(importedIRI);
        Set<OWLOntology> preImportsClosureCache = asUnorderedSet(baseOnt.importsClosure());
        assertTrue(preImportsClosureCache.contains(baseOnt));
        assertFalse(preImportsClosureCache.contains(importedOnt));
        baseOnt.applyChange(new AddImport(baseOnt, df.getOWLImportsDeclaration(importedIRI)));
        Set<OWLOntology> postImportsClosureCache = asUnorderedSet(baseOnt.importsClosure());
        assertTrue(postImportsClosureCache.contains(baseOnt));
        assertTrue(postImportsClosureCache.contains(importedOnt));
    }

    @Test
    void shouldThrowExceptionWithDefaultImportsconfig() {
        assertThrows(UnloadableImportException.class,
            () -> loadOntologyFromString(TestFiles.unloadableImport, new RDFXMLDocumentFormat()));
    }

    @Test
    void testImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, IMPORTS), true));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/imports/D.owl"));
    }

    @Test
    void testCyclicImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/importscyclic/D.owl"));
    }

    @Test
    void testCyclicImports2() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        m.loadOntologyFromOntologyDocument(iri(new File(RESOURCES, "importscyclic/D.owl")));
    }

    @Test
    void testTurtleGraphImport() throws OWLOntologyCreationException {
        // document without ontology declaration should be removed and
        // reimported
        File ontologyDirectory = new File(RESOURCES, "importNoOntology");
        String ns = "http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/";
        IRI bobsOntologyName = iri(ns, "subject-bob");
        OWLNamedIndividual bobsIndividual =
            df.getOWLNamedIndividual(ns + "subject-bob#", "subjectOnImmunosuppressantA2");
        m.getIRIMappers().add(new SimpleIRIMapper(iri(ns, "subject-amy"),
            iri(new File(ontologyDirectory, "subject-amy.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(bobsOntologyName,
            iri(new File(ontologyDirectory, "subject-bob.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(iri(ns, "subject-sue"),
            iri(new File(ontologyDirectory, "subject-sue.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(iri("http://www.w3.org/2013/12/FDA-TA/", "core"),
            iri(new File(ontologyDirectory, "core.ttl"))));
        OWLOntology topLevelImport =
            m.loadOntologyFromOntologyDocument(new File(ontologyDirectory, "subjects.ttl"));
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
        assertTrue(contains(ontA.importsClosure(), ontA));
        OWLImportsDeclaration importsDeclaration =
            ImportsDeclaration(get(ontB.getOntologyID().getOntologyIRI()));
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
    void shouldRemapImport() throws OWLOntologyCreationException {
        IRI testImport = iri("http://test.org/", "TestPizzaImport.owl");
        IRI remap = iri("urn:test:", "mockImport");
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        m.createOntology(remap);
        OWLOntology o =
            m.loadOntologyFromOntologyDocument(new StringDocumentSource(TestFiles.remapImport));
        assertEquals(1, o.importsDeclarations().count());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    void shouldRemapImportRdfXML() throws OWLOntologyCreationException {
        IRI testImport = iri("http://test.org/", "TestPizzaImport.owl");
        IRI remap = iri("urn:test:", "mockImport");
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        m.createOntology(remap);
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            new StringDocumentSource(TestFiles.remapImportRdfXml));
        assertEquals(1, o.importsDeclarations().count());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    void testImportOntologyByLocation() throws Exception {
        File f = new File(folder, "a.owl");
        createOntologyFile(IRI("http://a.com", ""), f);
        // have to load an ontology for it to get a document IRI
        OWLOntology a = m.loadOntologyFromOntologyDocument(f);
        IRI locA = m.getOntologyDocumentIRI(a);
        IRI bIRI = IRI("http://b.com", "");
        OWLOntology b = getOWLOntology(bIRI);
        // import from the document location of a.owl (rather than the
        // ontology IRI)
        b.applyChange(new AddImport(b, df.getOWLImportsDeclaration(locA)));
        assertEquals(1, b.importsDeclarations().count());
        assertEquals(1, b.imports().count());
    }

    @Test
    void shouldNotCreateIllegalPunning() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            iri(new File(RESOURCES, "importscyclic/relaMath.owl")));
        OWLProfileReport checkOntology = Profiles.OWL2_DL.checkOntology(o);
        assertTrue(checkOntology.isInProfile(), checkOntology.toString());
        o.directImports()
            .forEach(ont -> assertEquals(0, ont.annotationPropertiesInSignature().count()));
    }

    private OWLOntology createOntologyFile(IRI iri, File f) throws Exception {
        OWLOntology a = m1.createOntology(iri);
        try (OutputStream out = new FileOutputStream(f)) {
            a.saveOntology(out);
        }
        return a;
    }

    @Test
    void testImportsWhenRemovingAndReloading() throws Exception {
        OWLOntologyManager man = setupManager();
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true);
        man.getIRIMappers().add(mapper);
        String name = "/imports/thesubont.omn";
        File file = new File(getClass().getResource(name).toURI());
        OWLOntology root = man.loadOntologyFromOntologyDocument(file);
        assertEquals(1, root.imports().count());
        man.ontologies().forEach(o -> man.removeOntology(o));
        assertEquals(0, man.ontologies().count());
        root = man.loadOntologyFromOntologyDocument(file);
        assertEquals(1, root.imports().count());
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
