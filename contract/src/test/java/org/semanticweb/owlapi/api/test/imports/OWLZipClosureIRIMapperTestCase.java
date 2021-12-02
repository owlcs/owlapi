package org.semanticweb.owlapi.api.test.imports;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.OWLZipClosureIRIMapper;

class OWLZipClosureIRIMapperTestCase extends TestBase {
    static final IRI dIRI = iri("http://test.org/complexImports/", "D.owl");
    File imports = new File(RESOURCES, "imports");
    File d = new File(RESOURCES, "/imports/D.owl");

    @ParameterizedTest
    @ValueSource(strings = {"owlzipwithfolders.zip", "owlzipwithcatalog.zip", "owlzipnoindex.zip",})
    void shouldLoadClosure(String child) throws IOException {
        File file2 = new File(RESOURCES, child);
        m1.getIRIMappers().add(new AutoIRIMapper(imports, true));
        OWLOntology test = loadOntologyFromFile(d, m1);
        m.getIRIMappers().add(new OWLZipClosureIRIMapper(file2));
        OWLOntology loadOntology = loadOntology(dIRI, m);
        equal(loadOntology, test);
    }

    @Test
    void shouldMapIRIsWithAutoIRIMapper() {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports, true));
        OWLOntology test = loadOntologyFromFile(d, m1);
        m.getIRIMappers().add(new AutoIRIMapper(file2, false));
        OWLOntology loadOntology = loadOntology(dIRI, m);
        equal(loadOntology, test);
    }
}

