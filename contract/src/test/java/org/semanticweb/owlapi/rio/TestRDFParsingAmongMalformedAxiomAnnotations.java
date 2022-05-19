package org.semanticweb.owlapi.rio;


import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestRDFParsingAmongMalformedAxiomAnnotations {

    @Test
    public void testAxiomParsing() throws OWLOntologyCreationException, IOException {
        try (InputStream ttlStream = this.getClass().getResourceAsStream("/rdfParserReservedPropertiesAnnotations.ttl")) {
            OWLOntology ontTTL = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(ttlStream);
            assertEquals(14, ontTTL.getAxiomCount(), "Test file should have 14 axioms");
            Optional<OWLAnnotation> annotationOpt = ontTTL.getAxioms().stream()
                    .flatMap(ax -> ax.getAnnotations().stream())
                    .filter(ann -> ann.getValue().isLiteral())
                    .filter(ann -> ann.getValue().asLiteral().get().getLiteral().equals("This assertion is annotated"))
                    .findAny();
            assertFalse(annotationOpt.isPresent(), "Annotation should not have been added to any axiom.");
        }
    }

}
