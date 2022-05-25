package org.semanticweb.owlapi.oboformattest;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asSet;

import org.junit.Test;
import org.semanticweb.owlapi.documents.StringDocumentTarget;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class RoundTripUniversalRestrictionTestCase extends RoundTripTestCase {

    @Test
    public void shouldRoundtripAll() throws OWLOntologyStorageException {
        String in = "Prefix(:=<http://purl.obolibrary.org/obo/uni.obo#>)\n"
            + "Ontology(<http://purl.obolibrary.org/obo/uni.obo.owl>\n" + "Declaration(Class(:A))\n"
            + "Declaration(Class(:B))\n" + "Declaration(ObjectProperty(:part_of))\n"
            + "SubClassOf(:A ObjectAllValuesFrom(:part_of :B)))";
        OWLOntology o1 = loadOntologyFromString(in, new FunctionalSyntaxDocumentFormat());
        StringDocumentTarget saveOntology = saveOntology(o1, new OBODocumentFormat());
        OWLOntology o2 = loadOntologyFromString(saveOntology, new OBODocumentFormat());
        assertEquals(asSet(o1.logicalAxioms()), asSet(o2.logicalAxioms()));
    }
}
