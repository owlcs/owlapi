package org.semanticweb.owlapi6.obolibrarytest.oboformat;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import org.junit.Test;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OBODocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

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
