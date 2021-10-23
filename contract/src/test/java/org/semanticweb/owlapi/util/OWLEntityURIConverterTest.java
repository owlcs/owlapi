package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

class OWLEntityURIConverterTest extends TestBase {

    private static final String TEST_ONTOLOGY_RESOURCE = "testUriConverterOntology.owl";
    private static final String OLD_NAMESPACE = "http://www.example.org/testOntology#";
    private static final String NEW_NAMESPACE = "http://www.example.org/newTestOntology#";

    @Test
    public void test() throws OWLOntologyCreationException {
        File ontologyFile = new File(
            this.getClass().getClassLoader().getResource(TEST_ONTOLOGY_RESOURCE).getFile());
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
        checkEntityNamespace(ontology, OLD_NAMESPACE);
        OWLEntityURIConverter converter =
            getOWLEntityNamespaceConverter(manager, OLD_NAMESPACE, NEW_NAMESPACE);
        manager.applyChanges(converter.getChanges());
        checkEntityNamespace(ontology, NEW_NAMESPACE);
    }

    private static OWLEntityURIConverter getOWLEntityNamespaceConverter(OWLOntologyManager manager,
        String oldNamespace, String newNamespace) {
        OWLEntityURIConverterStrategy strategy = entity -> {
            IRI newIRI = entity.getIRI();
            if (!entity.isAnonymous()) {
                newIRI = IRI.create(entity.getIRI().toString().replace(oldNamespace, newNamespace));
            }
            return newIRI;
        };
        return new OWLEntityURIConverter(manager, manager.getOntologies(), strategy);
    }

    private static void checkEntityNamespace(OWLOntology ontology, String namespace) {
        ontology.getClassesInSignature()
            .forEach(x -> assertTrue(x.getIRI().toString().contains(namespace)));
        ontology.getIndividualsInSignature()
            .forEach(x -> assertTrue(x.getIRI().toString().contains(namespace)));
        ontology.getObjectPropertiesInSignature().stream()
            .filter(x -> !x.asOWLObjectProperty().isOWLTopObjectProperty())
            .forEach(x -> assertTrue(x.getIRI().toString().contains(namespace)));
        ontology.getDataPropertiesInSignature().stream()
            .filter(x -> !x.asOWLDataProperty().isOWLTopDataProperty())
            .forEach(x -> assertTrue(x.getIRI().toString().contains(namespace)));
        ontology.getAnnotationPropertiesInSignature()
            .forEach(x -> assertTrue(x.getIRI().toString().contains(namespace)));
        ontology.getDatatypesInSignature().stream().filter(x -> !x.asOWLDatatype().isBuiltIn())
            .forEach(x -> assertTrue(x.getIRI().toString().contains(namespace)));
    }
}
