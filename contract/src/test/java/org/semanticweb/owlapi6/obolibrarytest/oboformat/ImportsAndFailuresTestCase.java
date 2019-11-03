package org.semanticweb.owlapi6.obolibrarytest.oboformat;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.OBODocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

public class ImportsAndFailuresTestCase extends TestBase {

    @Test
    public void shouldNotFailOnEmptyImport()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        m1.createOntology(df.getIRI("http://purl.obolibrary.org/obo/uberon/test_import.owl"));
        String input = "ontology: uberon\n"
            + "import: http://purl.obolibrary.org/obo/uberon/test_import.owl\n\n"
            + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
            + "[Typedef]\nid: part_of\nxref: BFO:0000050";
        OWLOntology o = m1.loadOntologyFromOntologyDocument(
            new StringDocumentSource(input, new OBODocumentFormat()));
        StringDocumentTarget target = new StringDocumentTarget();
        o.saveOntology(new OBODocumentFormat(), target);
        m.createOntology(df.getIRI("http://purl.obolibrary.org/obo/uberon/test_import.owl"));
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(
            new StringDocumentSource(input, new OBODocumentFormat()));
        equal(o, o1);
    }

    @Test
    public void shouldNotFailOnNoImport()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input =
            "ontology: uberon\n" + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
                + "[Typedef]\nid: part_of\nxref: BFO:0000050";
        OWLOntology o = m1.loadOntologyFromOntologyDocument(
            new StringDocumentSource(input, new OBODocumentFormat()));
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
        OWLOntology o = m1.loadOntologyFromOntologyDocument(
            new StringDocumentSource(input, new OBODocumentFormat()));
        loadOntology("pato_import.owl", m);
        StringDocumentTarget target = new StringDocumentTarget();
        o.saveOntology(new OBODocumentFormat(), target);
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(
            new StringDocumentSource(input, new OBODocumentFormat()));
        equal(o, o1);
    }

    @Test
    public void shouldNotFailOnNoPatoImport()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "format-version: 1.2\n" + "ontology: uberon/core\n" + "\n" + "[Term]\n"
            + "id: UBERON:0004138\n" + "name: somitomeric trunk muscle\n"
            + "property_value: seeAlso \"string\"\n" + "\n" + "[Typedef]\n" + "id: seeAlso\n"
            + "name: seeAlso\n" + "is_metadata_tag: true\n" + "is_class_level: true";
        OWLOntology o = m1.loadOntologyFromOntologyDocument(
            new StringDocumentSource(input, new OBODocumentFormat()));
        roundTrip(o, new OBODocumentFormat());
    }
}
