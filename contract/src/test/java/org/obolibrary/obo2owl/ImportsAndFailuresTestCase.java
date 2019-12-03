package org.obolibrary.obo2owl;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class ImportsAndFailuresTestCase extends TestBase {
    private static final IRI UBERON = IRI.create("urn:test:uberon");

    @Test
    public void shouldNotFailOnEmptyImport()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        m1.createOntology(IRI.create("http://purl.obolibrary.org/obo/uberon/test_import.owl"));
        String input = "ontology: uberon\n"
            + "import: http://purl.obolibrary.org/obo/uberon/test_import.owl\n\n"
            + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
            + "[Typedef]\nid: part_of\nxref: BFO:0000050";
        OWLOntology o = m1.loadOntologyFromOntologyDocument(new StringDocumentSource(input,
            UBERON, new OBODocumentFormat(), null));
        StringDocumentTarget target = new StringDocumentTarget();
        m1.saveOntology(o, new OBODocumentFormat(), target);
        m.createOntology(IRI.create("http://purl.obolibrary.org/obo/uberon/test_import.owl"));
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input,
            UBERON, new OBODocumentFormat(), null));
        equal(o, o1);
    }

    @Test
    public void shouldNotFailOnNoImport()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input =
            "ontology: uberon\n" + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
                + "[Typedef]\nid: part_of\nxref: BFO:0000050";
        OWLOntology o = m1.loadOntologyFromOntologyDocument(new StringDocumentSource(input,
            UBERON, new OBODocumentFormat(), null));
        roundTrip(o, new OBODocumentFormat());
    }

    @Test
    public void shouldNotFailOnPatoImport()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        loadOntology("pato_import.owl", m1);
        String input = "format-version: 1.2\n"
            + "import: http://purl.obolibrary.org/obo/uberon/pato_import.owl\n"
            + "ontology: uberon/core\n" + "\n" + "[Term]\n" + "id: UBERON:0004138\n"
            + "name: somitomeric trunk muscle\n" + "property_value: seeAlso \"string\"\n" + "\n"
            + "[Typedef]\n" + "id: seeAlso\n" + "name: seeAlso\n" + "is_metadata_tag: true\n"
            + "is_class_level: true";
        OWLOntology o = m1.loadOntologyFromOntologyDocument(new StringDocumentSource(input,
            UBERON, new OBODocumentFormat(), null));
        loadOntology("pato_import.owl", m);
        StringDocumentTarget target = new StringDocumentTarget();
        m1.saveOntology(o, new OBODocumentFormat(), target);
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input,
            UBERON, new OBODocumentFormat(), null));
        equal(o, o1);
    }

    @Test
    public void shouldNotFailOnNoPatoImport()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "format-version: 1.2\n" + "ontology: uberon/core\n" + "\n" + "[Term]\n"
            + "id: UBERON:0004138\n" + "name: somitomeric trunk muscle\n"
            + "property_value: seeAlso \"string\"\n" + "\n" + "[Typedef]\n" + "id: seeAlso\n"
            + "name: seeAlso\n" + "is_metadata_tag: true\n" + "is_class_level: true";
        OWLOntology o = m1.loadOntologyFromOntologyDocument(new StringDocumentSource(input,
            UBERON, new OBODocumentFormat(), null));
        roundTrip(o, new OBODocumentFormat());
    }
}
