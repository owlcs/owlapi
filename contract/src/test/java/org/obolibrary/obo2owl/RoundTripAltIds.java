package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLOntology;

import com.google.common.base.Optional;

@SuppressWarnings("javadoc")
public class RoundTripAltIds extends RoundTripTest {

    @Test
    public void testAltIds() throws Exception {
        OBODoc input = parseOBOFile("alt_id_test.obo");
        OWLOntology owl = convert(input);
        // check round trip
        OBODoc output = convert(owl);
        String outObo = renderOboToString(output);
        assertEquals(readResource("alt_id_test.obo").trim(), outObo.trim());
        // check owl
        // check that both alt_id is declared as deprecated class and has
        // appropriate annotations
        IRI alt_id_t1 = IRI.create("http://purl.obolibrary.org/obo/TEST_1000");
        IRI alt_id_r1 = IRI
                .create("http://purl.obolibrary.org/obo/TEST_REL_1000");
        checkAsAltId(alt_id_t1, owl, "TEST:0001");
        checkAsAltId(alt_id_r1, owl, "TEST_REL:0001");
    }

    private static void checkAsAltId(@Nonnull IRI iri, OWLOntology ont,
            String replacedBy) {
        String altId = null;
        boolean isMerged = false;
        boolean isDeprecated = false;
        for (OWLAnnotationAssertionAxiom axiom : ont
                .getAnnotationAssertionAxioms(iri)) {
            OWLAnnotationProperty prop = axiom.getProperty();
            if (prop.isDeprecated()) {
                isDeprecated = true;
            } else if (Obo2OWLConstants.IRI_IAO_0000231.equals(prop.getIRI())) {
                OWLAnnotationValue value = axiom.getValue();
                Optional<IRI> asIRI = value.asIRI();
                if (asIRI.isPresent()) {
                    isMerged = Obo2OWLConstants.IRI_IAO_0000227.equals(asIRI
                            .get());
                }
            } else if (Obo2OWLVocabulary.IRI_IAO_0100001.iri.equals(prop
                    .getIRI())) {
                OWLAnnotationValue value = axiom.getValue();
                Optional<IRI> asIRI = value.asIRI();
                if (asIRI.isPresent()) {
                    altId = OWLAPIOwl2Obo.getIdentifier(asIRI.get());
                }
            }
        }
        assertTrue(isMerged);
        assertTrue(isDeprecated);
        assertEquals(replacedBy, altId);
    }
}
