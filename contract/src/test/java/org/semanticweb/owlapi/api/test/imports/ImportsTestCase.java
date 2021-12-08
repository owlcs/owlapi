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
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
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

    static final String IMPORTS = "imports";

    @Test
    void testImportsClosureUpdate() {
        OWLOntology ontA = create(iri("http://a.com", ""));
        IRI bIRI = iri("http://b.com", "");
        OWLOntology ontB = create(bIRI);
        ontA.applyChange(new AddImport(ontA, ImportsDeclaration(bIRI)));
        assertEquals(2, m.importsClosure(ontA).count());
        m.removeOntology(ontB);
        assertEquals(1, m.importsClosure(ontA).count());
        create(bIRI);
        assertEquals(2, m.importsClosure(ontA).count());
    }

    @Test
    void shouldLoad() {
        File importsBothNameAndVersion = new File(folder, "tempimportsNameAndVersion.owl");
        File importsBothNameAndOther = new File(folder, "tempimportsNameAndOther.owl");
        File ontologyByName = new File(folder, "tempmain.owl");
        File ontologyByVersion = new File(folder, "tempversion.owl");
        File ontologyByOtherPath = new File(folder, "tempother.owl");
        OWLOntology ontology = create(
            new OWLOntologyID(optional(iri(ontologyByName)), optional(iri(ontologyByVersion))));
        saveOntology(ontology, iri(ontologyByName));
        saveOntology(ontology, iri(ontologyByVersion));
        saveOntology(ontology, iri(ontologyByOtherPath));
        OWLOntology ontology1 = create(iri(importsBothNameAndVersion));
        OWLOntology ontology2 = create(iri(importsBothNameAndOther));
        List<AddImport> changes = new ArrayList<>();
        changes.add(new AddImport(ontology1, ImportsDeclaration(iri(ontologyByName))));
        changes.add(new AddImport(ontology1, ImportsDeclaration(iri(ontologyByVersion))));
        changes.add(new AddImport(ontology2, ImportsDeclaration(iri(ontologyByName))));
        changes.add(new AddImport(ontology2, ImportsDeclaration(iri(ontologyByOtherPath))));
        ontology1.applyChanges(changes);
        ontology2.applyChanges(changes);
        saveOntology(ontology1, iri(importsBothNameAndVersion));
        saveOntology(ontology2, iri(importsBothNameAndOther));
        // when
        OWLOntology o1 = loadFrom(iri(importsBothNameAndVersion), m1);
        OWLOntology o2 = loadFrom(iri(importsBothNameAndOther), m1);
        // then
        assertNotNull(o1);
        assertNotNull(o2);
    }

    @Test
    void shouldNotLoadWrong() {
        create(iriTest);
        StringDocumentSource documentSource = new StringDocumentSource(TestFiles.loadRight);
        OWLOntology o = loadFrom(documentSource, m);
        assertTrue(o.isAnonymous());
        assertFalse(o.getOntologyID().getDefaultDocumentIRI().isPresent());
    }

    @Test
    void testManualImports() {
        OWLOntology baseOnt = create(iri("http://semanticweb.org/ontologies/", "base"));
        IRI importedIRI = iri("http://semanticweb.org/ontologies/", "imported");
        OWLOntology importedOnt = create(importedIRI);
        Set<OWLOntology> preImportsClosureCache = asUnorderedSet(baseOnt.importsClosure());
        assertTrue(preImportsClosureCache.contains(baseOnt));
        assertFalse(preImportsClosureCache.contains(importedOnt));
        baseOnt.applyChange(new AddImport(baseOnt, ImportsDeclaration(importedIRI)));
        Set<OWLOntology> postImportsClosureCache = asUnorderedSet(baseOnt.importsClosure());
        assertTrue(postImportsClosureCache.contains(baseOnt));
        assertTrue(postImportsClosureCache.contains(importedOnt));
    }

    @Test
    void shouldThrowExceptionWithDefaultImportsconfig() {
        assertThrows(UnloadableImportException.class,
            () -> loadFrom(TestFiles.unloadableImport, new RDFXMLDocumentFormat()));
    }

    @Test
    void testImports() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, IMPORTS), true));
        loadFrom(new File(RESOURCES, "/imports/D.owl"), m);
    }

    @Test
    void testCyclicImports() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        loadFrom(new File(RESOURCES, "/importscyclic/D.owl"), m);
    }

    @Test
    void testCyclicImports2() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        loadFrom(iri(new File(RESOURCES, "importscyclic/D.owl")), m);
    }

    @Test
    void testTurtleGraphImport() {
        // document without ontology declaration should be removed and
        // reimported
        File ontologyDirectory = new File(RESOURCES, "importNoOntology");
        String ns = "http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/";
        IRI bobsOntologyName = iri(ns, "subject-bob");
        OWLNamedIndividual bobsIndividual =
            NamedIndividual(iri(ns + "subject-bob#", "subjectOnImmunosuppressantA2"));
        m.getIRIMappers().add(new SimpleIRIMapper(iri(ns, "subject-amy"),
            iri(new File(ontologyDirectory, "subject-amy.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(bobsOntologyName,
            iri(new File(ontologyDirectory, "subject-bob.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(iri(ns, "subject-sue"),
            iri(new File(ontologyDirectory, "subject-sue.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(iri("http://www.w3.org/2013/12/FDA-TA/", "core"),
            iri(new File(ontologyDirectory, "core.ttl"))));
        OWLOntology topLevelImport = loadFrom(new File(ontologyDirectory, "subjects.ttl"), m);
        assertTrue(topLevelImport.containsEntityInSignature(bobsIndividual, INCLUDED),
            "Individuals about Bob are missing...");
    }

    /**
     * Tests to see if the method which obtains the imports closure behaves correctly.
     */
    @Test
    void testImportsClosure() {
        OWLOntology ontA = create();
        OWLOntology ontB = create();
        assertTrue(contains(ontA.importsClosure(), ontA));
        OWLImportsDeclaration importsDeclaration =
            ImportsDeclaration(ontB.getOntologyID().getOntologyIRI().orElse(null));
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
    void shouldRemapImport() {
        IRI testImport = iri("http://test.org/", "TestPizzaImport.owl");
        IRI remap = iri("urn:test:", "mockImport");
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        create(remap);
        OWLOntology o = loadFrom(new StringDocumentSource(TestFiles.remapImport), m);
        assertEquals(1, o.importsDeclarations().count());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    void shouldRemapImportRdfXML() {
        IRI testImport = iri("http://test.org/", "TestPizzaImport.owl");
        IRI remap = iri("urn:test:", "mockImport");
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().set(mock);
        create(remap);
        OWLOntology o = loadFrom(new StringDocumentSource(TestFiles.remapImportRdfXml), m);
        assertEquals(1, o.importsDeclarations().count());
        verify(mock).getDocumentIRI(testImport);
    }

    @Test
    void testImportOntologyByLocation() {
        File file = new File(folder, "a.owl");
        createOntologyFile(iri("http://a.com", ""), file);
        // have to load an ontology for it to get a document IRI
        OWLOntology a = loadFrom(file, m);
        IRI locA = m.getOntologyDocumentIRI(a);
        IRI bIRI = iri("http://b.com", "");
        OWLOntology b = create(bIRI);
        // import from the document location of a.owl (rather than the
        // ontology IRI)
        b.applyChange(new AddImport(b, ImportsDeclaration(locA)));
        assertEquals(1, b.importsDeclarations().count());
        assertEquals(1, b.imports().count());
    }

    @Test
    void shouldNotCreateIllegalPunning() {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        OWLOntology o = loadFrom(iri(new File(RESOURCES, "importscyclic/relaMath.owl")), m);
        OWLProfileReport checkOntology = Profiles.OWL2_DL.checkOntology(o);
        assertTrue(checkOntology.isInProfile(), checkOntology.toString());
        o.directImports()
            .forEach(ont -> assertEquals(0, ont.annotationPropertiesInSignature().count()));
    }

    private OWLOntology createOntologyFile(IRI iri, File file) {
        OWLOntology a = create(iri);
        try (OutputStream out = new FileOutputStream(file)) {
            a.saveOntology(out);
        } catch (OWLOntologyStorageException | IOException ex) {
            throw new OWLRuntimeException(ex);
        }
        return a;
    }

    @Test
    void testImportsWhenRemovingAndReloading() throws URISyntaxException {
        OWLOntologyManager man = setupManager();
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true);
        man.getIRIMappers().add(mapper);
        String name = "/imports/thesubont.omn";
        File file = new File(getClass().getResource(name).toURI());
        OWLOntology root = loadFrom(file, man);
        assertEquals(1, root.imports().count());
        man.ontologies().forEach(o -> man.removeOntology(o));
        assertEquals(0, man.ontologies().count());
        root = loadFrom(file, man);
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
