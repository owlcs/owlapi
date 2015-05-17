package org.semanticweb.owlapi.api.test.syntax;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class PunningAndDomainsRangesTestCase extends TestBase {

    @Test
    public void shouldKeepDomainsInFSS() throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLOntology o = m.createOntology(IRI.create("urn:testontology"));
        OWLAnnotationProperty p1 = df.getOWLAnnotationProperty(IRI.create(
            "urn:property:p"));
        OWLDataProperty p2 = df.getOWLDataProperty(IRI.create(
            "urn:property:p"));
        m.addAxiom(o, df.getOWLAnnotationPropertyRangeAxiom(p1,
            OWL2Datatype.RDFS_LITERAL.getIRI()));
        m.addAxiom(o, df.getOWLDataPropertyRangeAxiom(p2,
            OWL2Datatype.RDFS_LITERAL.getDatatype(df)));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
    }
}
