package org.obolibrary.obo2owl;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings({"javadoc", "null"})
public class OBOWithUntranslatableAxiomTest {

    @Test
    public void testNoDeadlock() throws OWLOntologyStorageException, OWLOntologyCreationException {
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        OWLOntology o = OWLManager.createConcurrentOWLOntologyManager()
            .createOntology(IRI.create("urn:test:ontology"));
        o.getOWLOntologyManager().addAxiom(o,
            df.getOWLSubClassOfAxiom(df.getOWLNothing(), df.getOWLThing()));
        OWLOntologyDocumentTarget target = new StringDocumentTarget();
        o.saveOntology(new OBODocumentFormat(), target);
    }
}
