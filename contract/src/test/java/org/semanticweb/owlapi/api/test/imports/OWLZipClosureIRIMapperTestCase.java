package org.semanticweb.owlapi.api.test.imports;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.OWLZipClosureIRIMapper;

@SuppressWarnings("javadoc")
public class OWLZipClosureIRIMapperTestCase extends TestBase {
    protected IRI dIRI = IRI.create("http://test.org/complexImports/D.owl");

    @Test
    public void shouldLoadClosureWithProperties()
        throws ZipException, IOException, OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipwithfolders.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    public void shouldLoadClosureWithCatalog()
        throws ZipException, IOException, OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipwithcatalog.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    public void shouldMapIRIsWithoutIndex()
        throws ZipException, IOException, OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    public void shouldMapIRIsWithAutoIRIMapper() throws OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        m.getIRIMappers().add(new AutoIRIMapper(file2, false));
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    protected File imports() {
        return new File(RESOURCES, "imports");
    }

    protected File d() {
        return new File(RESOURCES, "/imports/D.owl");
    }
}

