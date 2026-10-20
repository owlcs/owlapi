package org.semanticweb.owlapi.modularity.locality;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;


class SemanticLocalityModuleExtractorTestCase extends TestBase {

    @Test
    void semanticExtractor() {
        OWLAxiom ax=SubClassOf(A, B);
        SemanticLocalityModuleExtractor extractor = 
            new SemanticLocalityModuleExtractor(
                LocalityClass.STAR, 
                Stream.of(ax), 
                m, 
                new StructuralReasonerFactory());
        Stream<OWLAxiom> axioms = extractor.extract(Stream.of(A));
        assertTrue(axioms.collect(Collectors.toSet()).contains(ax));
    }
}
