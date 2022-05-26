package org.semanticweb.owlapi.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.utilities.OWLAPIStreamUtils;
import org.semanticweb.owlapi.utility.OWLEntityURIConverter;
import org.semanticweb.owlapi.utility.OWLEntityURIConverterStrategy;

public class OWLEntityURIConverterTest extends TestBase {

    private static final String TEST_ONTOLOGY_RESOURCE = "testUriConverterOntology.owl";
    private static final String OLD_NAMESPACE = "http://www.example.org/testOntology#";
    private static final String NEW_NAMESPACE = "http://www.example.org/newTestOntology#";

    @Test
    public void test() {
        OWLOntology ontology = loadOntology(TEST_ONTOLOGY_RESOURCE);
        entities(ontology).forEach(OWLEntityURIConverterTest::assertOldName);
        OWLEntityURIConverter converter =
            getOWLEntityNamespaceConverter(ontology.getOWLOntologyManager());
        ontology.applyChanges(converter.getChanges());
        entities(ontology).forEach(OWLEntityURIConverterTest::assertCorrectRename);
    }

    protected Stream<OWLEntity> entities(OWLOntology ontology) {
        return ontology.signature().filter(x -> !x.getIRI().isReservedVocabulary());
    }

    private static OWLEntityURIConverter getOWLEntityNamespaceConverter(
        OWLOntologyManager manager) {
        return new OWLEntityURIConverter(manager, OWLAPIStreamUtils.asList(manager.ontologies()),
            OWLEntityURIConverterTest::rename);
    }

    protected static IRI rename(OWLEntity entity) {
        String iriString = entity.getIRI().getIRIString();
        if (iriString.contains(OLD_NAMESPACE)) {
            return df.getIRI(iriString.replace(OLD_NAMESPACE, NEW_NAMESPACE));
        }
        return entity.getIRI();
    }

    protected static void assertCorrectRename(OWLEntity x) {
        assertTrue(x.getIRI().toString(), x.getIRI().getIRIString().contains(NEW_NAMESPACE));
    }

    protected static void assertOldName(OWLEntity x) {
        assertTrue(x.getIRI().toString(), x.getIRI().getIRIString().contains(OLD_NAMESPACE));
    }
}
