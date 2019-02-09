package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class RoundTripUniversalRestrictionTestCase extends RoundTripTest {

    @Test
    public void shouldRoundtripAll()
        throws OWLOntologyStorageException, OWLOntologyCreationException {
        String in = "Prefix(:=<http://purl.obolibrary.org/obo/uni.obo#>)\n"
            + "Ontology(<http://purl.obolibrary.org/obo/uni.obo.owl>\n" + "Declaration(Class(:A))\n"
            + "Declaration(Class(:B))\n" + "Declaration(ObjectProperty(:part_of))\n"
            + "SubClassOf(:A ObjectAllValuesFrom(:part_of :B)))";
        OWLOntology o1 = loadOntologyFromString(in, new FunctionalSyntaxDocumentFormat());
        StringDocumentTarget saveOntology = saveOntology(o1, new OBODocumentFormat());
        OWLOntology o2 = loadOntologyFromString(saveOntology, new OBODocumentFormat());
        assertEquals(o1.getLogicalAxioms(), o2.getLogicalAxioms());
    }
}
