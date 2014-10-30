package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class RoundTripMultipleDefXrefTest extends RoundTripTest {

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("multiple_def_xref_test.obo", true);
    }

    @Test
    public void testDefinitions() {
        OWLAnnotationProperty hasDbXref = df
                .getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#hasDbXref");
        OWLOntology owlOnt = convertOBOFile("multiple_def_xref_test.obo");
        AtomicInteger n = new AtomicInteger(0);
        for (OWLAxiom ax : owlOnt.getAxioms()) {
            ax.annotations(hasDbXref).forEach(a -> {
                OWLLiteral v = (OWLLiteral) a.getValue();
                // expect this twice, as we have annotations on synonyms
                    if (v.getLiteral().equals("BTO:0001750")) {
                        n.incrementAndGet();
                    }
                    if (v.getLiteral().equals("Wikipedia:Mandibular_condyle")) {
                        n.incrementAndGet();
                    }
                });
        }
        assertEquals(3, n.intValue());
    }
}
