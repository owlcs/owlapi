package org.semanticweb.owlapi.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLEntityURIConverterTest {

	private static final String TEST_ONTOLOGY_RESOURCE = "testUriConverterOntology.owl";
	private static final String OLD_NAMESPACE = "http://www.example.org/testOntology#";
	private static final String NEW_NAMESPACE = "http://www.example.org/newTestOntology#";

	@Test
	public void test() throws OWLOntologyCreationException {
		File ontologyFile = new File(this.getClass().getClassLoader().getResource(TEST_ONTOLOGY_RESOURCE).getFile());
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
		this.checkEntityNamespace(ontology, OLD_NAMESPACE);
		OWLEntityURIConverter converter = this.getOWLEntityNamespaceConverter(manager, OLD_NAMESPACE, NEW_NAMESPACE);
		manager.applyChanges(converter.getChanges());
		this.checkEntityNamespace(ontology, NEW_NAMESPACE);
	}

	private OWLEntityURIConverter getOWLEntityNamespaceConverter(OWLOntologyManager manager, String oldNamespace, String newNamespace) {
		OWLEntityURIConverterStrategy strategy = new OWLEntityURIConverterStrategy() {
			@Override
			public IRI getConvertedIRI(OWLEntity entity) {
				IRI newIRI = entity.getIRI();
				if (!entity.isAnonymous()) {
					newIRI = IRI.create(entity.getIRI().getIRIString().replace(oldNamespace, newNamespace));
				}
				return newIRI;
			}
		};
		Collection<OWLOntology> ontologies = manager.ontologies().collect(Collectors.toCollection(TreeSet::new));
		OWLEntityURIConverter converter = new OWLEntityURIConverter(manager, ontologies, strategy);
		return converter;
	}

	private void checkEntityNamespace(OWLOntology ontology, String namespace) {
		ontology.classesInSignature().forEach(x -> {
			assertTrue(x.getIRI().getIRIString().contains(namespace));
		});
		ontology.individualsInSignature().forEach(x -> {
			assertTrue(x.getIRI().getIRIString().contains(namespace));
		});
		ontology.objectPropertiesInSignature().filter(x -> !(x.asOWLObjectProperty().isOWLTopObjectProperty()))
				.forEach(x -> {
					assertTrue(x.getIRI().getIRIString().contains(namespace));
				});
		ontology.dataPropertiesInSignature().filter(x -> !(x.asOWLDataProperty().isOWLTopDataProperty())).forEach(x -> {
			assertTrue(x.getIRI().getIRIString().contains(namespace));
		});
		ontology.annotationPropertiesInSignature().forEach(x -> {
			assertTrue(x.getIRI().getIRIString().contains(namespace));
		});
	}

}
