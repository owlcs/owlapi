package org.semanticweb.owlapi.api.test.imports;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.OWLZipClosureIRIMapper;

class OWLZipClosureIRIMapperTestCase extends TestBase {
    static final IRI dIRI = iri("http://test.org/complexImports/", "D.owl");

    @ParameterizedTest
    @ValueSource(strings = {"owlzipwithfolders.zip", "owlzipwithcatalog.zip", "owlzipnoindex.zip",})
    void shouldLoadClosure(String child) throws OWLOntologyCreationException, IOException {
        File file2 = new File(RESOURCES, child);
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        m.getIRIMappers().add(new OWLZipClosureIRIMapper(file2));
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    void shouldLoadClosureWithCatalog() throws OWLOntologyCreationException, IOException {
        File file2 = new File(RESOURCES, "owlzipwithcatalog.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    void shouldMapIRIsWithoutIndex() throws OWLOntologyCreationException, IOException {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    void shouldMapIRIsWithAutoIRIMapper() throws OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        m.getIRIMappers().add(new AutoIRIMapper(file2, false));
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    private static File imports() {
        return new File(RESOURCES, "imports");
    }

    private static File d() {
        return new File(RESOURCES, "/imports/D.owl");
    }
}

