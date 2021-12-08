package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

class OWLEntityURIConverterTest extends TestBase {

    private static final String TEST_ONTOLOGY_RESOURCE = "testUriConverterOntology.owl";
    private static final String OLD_NAMESPACE = "http://www.example.org/testOntology#";
    private static final String NEW_NAMESPACE = "http://www.example.org/newTestOntology#";

    @Test
    void test() {
        File ontologyFile = new File(
            this.getClass().getClassLoader().getResource(TEST_ONTOLOGY_RESOURCE).getFile());
        OWLOntology ontology = loadFrom(ontologyFile, m);
        checkEntityNamespace(ontology, OLD_NAMESPACE);
        OWLEntityURIConverter converter =
            getOWLEntityNamespaceConverter(m, OLD_NAMESPACE, NEW_NAMESPACE);
        m.applyChanges(converter.getChanges());
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
        return new OWLEntityURIConverter(manager, asList(manager.ontologies()), strategy);
    }

    private static void checkEntityNamespace(OWLOntology ontology, String namespace) {
        ontology.classesInSignature()
            .forEach(cl -> assertTrue(cl.getIRI().toString().contains(namespace)));
        ontology.individualsInSignature()
            .forEach(ind -> assertTrue(ind.getIRI().toString().contains(namespace)));
        ontology.objectPropertiesInSignature()
            .filter(op -> !op.asOWLObjectProperty().isOWLTopObjectProperty())
            .forEach(op -> assertTrue(op.getIRI().toString().contains(namespace)));
        ontology.dataPropertiesInSignature()
            .filter(dp -> !dp.asOWLDataProperty().isOWLTopDataProperty())
            .forEach(dp -> assertTrue(dp.getIRI().toString().contains(namespace)));
        ontology.annotationPropertiesInSignature()
            .forEach(ap -> assertTrue(ap.getIRI().toString().contains(namespace)));
        ontology.datatypesInSignature().filter(dtype -> !dtype.asOWLDatatype().isBuiltIn())
            .forEach(dtype -> assertTrue(dtype.getIRI().toString().contains(namespace)));
    }
}
