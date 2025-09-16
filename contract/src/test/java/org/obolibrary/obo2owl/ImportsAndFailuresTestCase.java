package org.obolibrary.obo2owl;

import static org.semanticweb.owlapi.apitest.TestFiles.EMPTY_IMPORT;
import static org.semanticweb.owlapi.apitest.TestFiles.NO_INPUT;
import static org.semanticweb.owlapi.apitest.TestFiles.UBERON_CORE;
import static org.semanticweb.owlapi.apitest.TestFiles.UBERON_PATO;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFilenames;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;

class ImportsAndFailuresTestCase extends TestBase {

    protected StringDocumentSource inputSource(String input) {
        return new StringDocumentSource(input, UBERON, new OBODocumentFormat(), null);
    }

    @Test
    void shouldNotFailOnEmptyImport() throws OWLOntologyCreationException {
        m1.createOntology(TEST_IMPORT);
        OWLOntology o = loadFrom(inputSource(EMPTY_IMPORT), m1);
        saveOntology(o, new OBODocumentFormat());
        create(TEST_IMPORT);
        OWLOntology o1 = loadFrom(inputSource(EMPTY_IMPORT), m);
        equal(o, o1);
    }

    @Test
    void shouldNotFailOnBadImportWhenSilent() throws OWLOntologyCreationException {
        org.semanticweb.owlapi.model.IRI badIRI = iri("bad_iri.obo");
        OWLOntology o = create(TEST_IMPORT);
        OWLImportsDeclaration owlImportsDeclaration = o.getOWLOntologyManager().getOWLDataFactory().getOWLImportsDeclaration(badIRI);
        o.applyChange( new AddImport(o, owlImportsDeclaration));
        StringDocumentTarget saveOntology = saveOntology(o, new OBODocumentFormat());
        OWLOntologyLoaderConfiguration conf = new OWLOntologyLoaderConfiguration().setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        loadWithConfig(saveOntology, conf);
    }

    @Test
    void shouldNotFailOnNoImport() {
        OWLOntology o = loadFrom(inputSource(NO_INPUT));
        roundTrip(o, new OBODocumentFormat());
    }

    @Test
    void shouldNotFailOnPatoImport() {
        load(TestFilenames.PATO_IMPORT_OWL, m1);
        OWLOntology o = loadFrom(inputSource(UBERON_PATO), m1);
        load(TestFilenames.PATO_IMPORT_OWL, m);
        saveOntology(o, new OBODocumentFormat());
        OWLOntology o1 = loadFrom(inputSource(UBERON_PATO), m);
        equal(o, o1);
    }

    @Test
    void shouldNotFailOnNoPatoImport() {
        OWLOntology o = loadFrom(inputSource(UBERON_CORE));
        roundTrip(o, new OBODocumentFormat());
    }
}
