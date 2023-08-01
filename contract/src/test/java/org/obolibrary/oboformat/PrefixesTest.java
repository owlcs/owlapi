package org.obolibrary.oboformat;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.EntitySearcher;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PrefixesTest extends TestBase {

    @Test
    void testPrefixesRoundtrip() throws OWLOntologyStorageException {
        OWLDataFactory factory = OWLManager.getOWLDataFactory();
        OWLAnnotationProperty termReplacedBy = factory.getOWLAnnotationProperty(IRI.create("http://purl.obolibrary.org/obo/IAO_0100001"));
        OWLAnnotationProperty consider = factory.getOWLAnnotationProperty(IRI.create("http://www.geneontology.org/formats/oboInOwl#consider"));
        OWLOntology oboOnt = loadOntology("obo/test_prefixes.obo", OWLManager.createOWLOntologyManager());
        assertTrue(oboOnt.containsEntityInSignature(factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/FOO_1234"))));
        assertTrue(oboOnt.containsEntityInSignature(factory.getOWLClass(IRI.create("http://somewhere.org/MyClass"))));
        Map<String, String> prefixMap = oboOnt.getOWLOntologyManager().getOntologyFormat(oboOnt).asPrefixOWLOntologyFormat().getPrefixName2PrefixMap();
        assertEquals("http://somewhere.org/", prefixMap.get("sw:"));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        oboOnt.getOWLOntologyManager().saveOntology(oboOnt, stream);
        String roundtripOBO = new String(stream.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(roundtripOBO.contains("idspace: sw http://somewhere.org/"));
        assertTrue(roundtripOBO.contains("[Term]\nid: FOO:1234\nis_a: sw:MyClass"));

        OWLOntology replacementsOnt = loadOntology("obo/iris_for_obsoletes_replacements.obo", OWLManager.createOWLOntologyManager());
        assertTrue(EntitySearcher.getAnnotationAssertionAxioms(factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/GO_0000108")), replacementsOnt).stream()
                .filter(ax -> ax.getProperty().equals(termReplacedBy))
                .anyMatch(ax -> ax.getValue().asIRI().get().equals(IRI.create("http://purl.obolibrary.org/obo/GO_0000109"))),
                "Values for replaced_by should be IRIs rather than strings");
        assertTrue(EntitySearcher.getAnnotationAssertionAxioms(factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/GO_0000114")), replacementsOnt).stream()
                        .filter(ax -> ax.getProperty().equals(consider))
                        .anyMatch(ax -> ax.getValue().asIRI().get().equals(IRI.create("http://purl.obolibrary.org/obo/GO_0000083"))),
                "Values for consider should be IRIs rather than strings");
        assertTrue(EntitySearcher.getAnnotationAssertionAxioms(factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/GO_0010553")), replacementsOnt).stream()
                        .filter(ax -> ax.getProperty().equals(termReplacedBy))
                        .anyMatch(ax -> ax.getValue().asIRI().get().equals(IRI.create("http://purl.obolibrary.org/obo/GO_0000122"))),
                "Values for replaced_by on alt_ids should be IRIs");
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        replacementsOnt.getOWLOntologyManager().saveOntology(replacementsOnt, stream2);
        String roundtripReplacementsOnt = new String(stream2.toByteArray(), StandardCharsets.UTF_8);
        assertFalse(roundtripReplacementsOnt.contains("idspace:"));
        assertTrue(roundtripReplacementsOnt.contains("replaced_by: GO:0000109"));
    }

}
