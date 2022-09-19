package org.semanticweb.owlapi.apitest.imports;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.utility.AutoIRIMapper;
import org.semanticweb.owlapi.utility.OWLZipClosureIRIMapper;

class OWLZipClosureIRIMapperTestCase extends TestBase {
    static final IRI dIRI = iri("http://test.org/complexImports/", "D.owl");

    @ParameterizedTest
    @ValueSource(strings = {"owlzipwithyaml.zip", "owlzipwithfolders.zip", "owlzipwithcatalog.zip",
        "owlzipnoindex.zip",})
    void shouldLoadClosure(String child) throws OWLOntologyCreationException, IOException {
        File file2 = new File(RESOURCES, child);
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true, df));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        m.getIRIMappers().add(new OWLZipClosureIRIMapper(file2, df));
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    void shouldMapIRIsWithAutoIRIMapper() throws OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true, df));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        m.getIRIMappers().add(new AutoIRIMapper(file2, false, df));
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    private File imports() {
        return new File(RESOURCES, "imports");
    }

    private File d() {
        return new File(RESOURCES, "/imports/D.owl");
    }
}

