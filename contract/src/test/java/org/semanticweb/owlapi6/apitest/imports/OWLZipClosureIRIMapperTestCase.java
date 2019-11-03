package org.semanticweb.owlapi6.apitest.imports;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.utility.AutoIRIMapper;
import org.semanticweb.owlapi6.utility.OWLZipClosureIRIMapper;

public class OWLZipClosureIRIMapperTestCase extends TestBase {
    protected IRI dIRI = df.getIRI("http://test.org/complexImports/D.owl");

    @Test
    public void shouldLoadClosureWithYaml()
        throws ZipException, IOException, OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipwithyaml.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true, df));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2, df);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    public void shouldLoadClosureWithProperties()
        throws ZipException, IOException, OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipwithfolders.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true, df));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2, df);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    public void shouldLoadClosureWithCatalog()
        throws ZipException, IOException, OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipwithcatalog.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true, df));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2, df);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    public void shouldMapIRIsWithoutIndex()
        throws ZipException, IOException, OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true, df));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        OWLZipClosureIRIMapper source = new OWLZipClosureIRIMapper(file2, df);
        m.getIRIMappers().add(source);
        OWLOntology loadOntology = m.loadOntology(dIRI);
        equal(loadOntology, test);
    }

    @Test
    public void shouldMapIRIsWithAutoIRIMapper() throws OWLOntologyCreationException {
        File file2 = new File(RESOURCES, "owlzipnoindex.zip");
        m1.getIRIMappers().add(new AutoIRIMapper(imports(), true, df));
        OWLOntology test = m1.loadOntologyFromOntologyDocument(d());
        m.getIRIMappers().add(new AutoIRIMapper(file2, false, df));
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

