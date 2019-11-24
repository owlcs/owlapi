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
package org.semanticweb.owlapi6.apitest.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ImportsDeclaration;
import static org.semanticweb.owlapi6.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AddImport;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.RemoveImport;
import org.semanticweb.owlapi6.model.UnloadableImportException;
import org.semanticweb.owlapi6.profiles.OWLProfileReport;
import org.semanticweb.owlapi6.profiles.Profiles;
import org.semanticweb.owlapi6.utility.AutoIRIMapper;
import org.semanticweb.owlapi6.utility.SimpleIRIMapper;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
public class ImportsTestCase extends TestBase {

    private static final String IMPORTS = "imports";

    @Test
    public void testImportsClosureUpdate() {
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
    public void shouldLoad() throws Exception {
        File importsBothNameAndVersion = folder.newFile("tempimportsNameAndVersion.owl");
        File importsBothNameAndOther = folder.newFile("tempimportsNameAndOther.owl");
        File ontologyByName = folder.newFile("tempmain.owl");
        File ontologyByVersion = folder.newFile("tempversion.owl");
        File ontologyByOtherPath = folder.newFile("tempother.owl");
        OWLOntology ontology = getOWLOntology(
            df.getOWLOntologyID(df.getIRI(ontologyByName), df.getIRI(ontologyByVersion)));
        ontology.saveOntology(df.getIRI(ontologyByName));
        ontology.saveOntology(df.getIRI(ontologyByVersion));
        ontology.saveOntology(df.getIRI(ontologyByOtherPath));
        OWLOntology ontology1 = m1.createOntology(df.getIRI(importsBothNameAndVersion));
        OWLOntology ontology2 = m1.createOntology(df.getIRI(importsBothNameAndOther));
        List<AddImport> changes = new ArrayList<>();
        changes
            .add(new AddImport(ontology1, df.getOWLImportsDeclaration(df.getIRI(ontologyByName))));
        changes.add(
            new AddImport(ontology1, df.getOWLImportsDeclaration(df.getIRI(ontologyByVersion))));
        changes
            .add(new AddImport(ontology2, df.getOWLImportsDeclaration(df.getIRI(ontologyByName))));
        changes.add(
            new AddImport(ontology2, df.getOWLImportsDeclaration(df.getIRI(ontologyByOtherPath))));
        ontology1.applyChanges(changes);
        ontology2.applyChanges(changes);
        ontology1.saveOntology(df.getIRI(importsBothNameAndVersion));
        ontology2.saveOntology(df.getIRI(importsBothNameAndOther));
        // when
        OWLOntology o1 = m.loadOntology(df.getIRI(importsBothNameAndVersion));
        OWLOntology o2 = m1.loadOntology(df.getIRI(importsBothNameAndOther));
        // then
        assertNotNull(o1);
        assertNotNull(o2);
    }

    @Test
    public void shouldNotLoadWrong() throws OWLOntologyCreationException {
        m.createOntology(df.getIRI("urn:test#", "test"));
        StringDocumentSource documentSource =
            new StringDocumentSource(TestFiles.loadRight, new RDFXMLDocumentFormat());
        OWLOntology o = m.loadOntologyFromOntologyDocument(documentSource);
        assertTrue(o.getOntologyID().toString(), o.isAnonymous());
        assertFalse(o.getOntologyID().getDefaultDocumentIRI().isPresent());
    }

    @Test
    public void testManualImports() {
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

    @Test(expected = UnloadableImportException.class)
    public void shouldThrowExceptionWithDefaultImportsconfig() {
        loadOntologyFromString(TestFiles.unloadableImport, new RDFXMLDocumentFormat());
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, IMPORTS), true, df));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/imports/D.owl"));
    }

    @Test
    public void testCyclicImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true, df));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/importscyclic/D.owl"));
    }

    @Test
    public void testCyclicImports2() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true, df));
        m.loadOntologyFromOntologyDocument(df.getIRI(new File(RESOURCES, "importscyclic/D.owl")));
    }

    @Test
    public void testTurtleGraphImport() throws OWLOntologyCreationException {
        // document without ontology declaration should be removed and
        // reimported
        File ontologyDirectory = new File(RESOURCES, "importNoOntology");
        String ns = "http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/";
        IRI bobsOntologyName = df.getIRI(ns, "subject-bob");
        OWLNamedIndividual bobsIndividual =
            df.getOWLNamedIndividual(ns + "subject-bob#", "subjectOnImmunosuppressantA2");
        m.getIRIMappers().add(new SimpleIRIMapper(df.getIRI(ns, "subject-amy"),
            df.getIRI(new File(ontologyDirectory, "subject-amy.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(bobsOntologyName,
            df.getIRI(new File(ontologyDirectory, "subject-bob.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(df.getIRI(ns, "subject-sue"),
            df.getIRI(new File(ontologyDirectory, "subject-sue.ttl"))));
        m.getIRIMappers()
            .add(new SimpleIRIMapper(df.getIRI("http://www.w3.org/2013/12/FDA-TA/", "core"),
                df.getIRI(new File(ontologyDirectory, "core.ttl"))));
        OWLOntology topLevelImport =
            m.loadOntologyFromOntologyDocument(new File(ontologyDirectory, "subjects.ttl"));
        assertTrue("Individuals about Bob are missing...",
            topLevelImport.containsEntityInSignature(bobsIndividual, INCLUDED));
    }

    /**
     * Tests to see if the method which obtains the imports closure behaves correctly.
     */
    @Test
    public void testImportsClosure() {
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
    public void shouldRemapImport() throws OWLOntologyCreationException {
        IRI testImport = df.getIRI("http://test.org/", "TestPizzaImport.owl");
        IRI remap = df.getIRI("urn:test:", "mockImport");
        StringDocumentSource source =
            new StringDocumentSource(TestFiles.remapImport, new OWLXMLDocumentFormat());
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
        IRI testImport = df.getIRI("http://test.org/", "TestPizzaImport.owl");
        IRI remap = df.getIRI("urn:test:", "mockImport");
        StringDocumentSource source =
            new StringDocumentSource(TestFiles.remapImportRdfXml, new RDFXMLDocumentFormat());
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
        File f = folder.newFile("a.owl");
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
    public void shouldNotCreateIllegalPunning() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true, df));
        OWLOntology o = m.loadOntologyFromOntologyDocument(
            df.getIRI(new File(RESOURCES, "importscyclic/relaMath.owl")));
        OWLProfileReport checkOntology = Profiles.OWL2_DL.checkOntology(o);
        assertTrue(checkOntology.toString(), checkOntology.isInProfile());
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
    public void testImportsWhenRemovingAndReloading() throws Exception {
        OWLOntologyManager man = setupManager();
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true, df);
        man.getIRIMappers().add(mapper);
        String name = "/imports/thesubont.omn";
        OWLOntology root =
            man.loadOntologyFromOntologyDocument(getClass().getResourceAsStream(name));
        assertEquals(1, root.imports().count());
        man.ontologies().forEach(o -> man.removeOntology(o));
        assertEquals(0, man.ontologies().count());
        root = man.loadOntologyFromOntologyDocument(getClass().getResourceAsStream(name));
        assertEquals(1, root.imports().count());
    }

    @Test
    public void testAutoIRIMapperShouldNotBeConfusedByPrefixes() {
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true, df);
        assertTrue(mapper.getOntologyIRIs()
            .contains(df.getIRI("http://owlapitestontologies.com/thesubont")));
    }

    @Test
    public void testAutoIRIMapperShouldRecogniseRdfAboutInOwlOntology() {
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, IMPORTS), true, df);
        assertTrue(
            mapper.getOntologyIRIs().contains(df.getIRI("http://test.org/compleximports/A.owl")));
    }
}
