package casualtest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
@SuppressWarnings("javadoc")
public class MelanieTest_double_import {
	private File ontologyByName;
	private File ontologyByVersion;
	private File ontologyByOtherPath;
	private File importsBothNameAndVersion;
	private File importsBothNameAndOther;

	/**
	 * @param args
	 */
	public void testDoubleImport() throws Exception {
		makeOntologies();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		manager.loadOntology(IRI.create(importsBothNameAndVersion));
		System.out.println("Importing by name and version succeeded");
		manager = OWLManager.createOWLOntologyManager();
		manager.loadOntology(IRI.create(importsBothNameAndOther));
		System.out.println("Importing by name and other succeeded");
	}

	public void makeOntologies() throws OWLOntologyCreationException,
			OWLOntologyStorageException {
		makeImported();
		makeImporting();
	}

	private void makeImported() throws OWLOntologyCreationException,
			OWLOntologyStorageException {
		File tmpdir = new File(System.getProperty("java.io.tmpdir"));
		ontologyByName = new File(tmpdir, "main.owl");
		ontologyByVersion = new File(tmpdir, "version.owl");
		ontologyByOtherPath = new File(tmpdir, "other.owl");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology(new OWLOntologyID(IRI
				.create(ontologyByName), IRI.create(ontologyByVersion)));
		manager.saveOntology(ontology, IRI.create(ontologyByName));
		manager.saveOntology(ontology, IRI.create(ontologyByVersion));
		manager.saveOntology(ontology, IRI.create(ontologyByOtherPath));
	}

	private void makeImporting() throws OWLOntologyCreationException,
			OWLOntologyStorageException {
		File tmpdir = new File(System.getProperty("java.io.tmpdir"));
		importsBothNameAndVersion = new File(tmpdir, "importsNameAndVersion.owl");
		importsBothNameAndOther = new File(tmpdir, "importsNameAndOther.owl");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology1 = manager.createOntology(IRI
				.create(importsBothNameAndVersion));
		OWLOntology ontology2 = manager.createOntology(IRI
				.create(importsBothNameAndOther));
		List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
		changes.add(new AddImport(ontology1, factory.getOWLImportsDeclaration(IRI
				.create(ontologyByName))));
		changes.add(new AddImport(ontology1, factory.getOWLImportsDeclaration(IRI
				.create(ontologyByVersion))));
		changes.add(new AddImport(ontology2, factory.getOWLImportsDeclaration(IRI
				.create(ontologyByName))));
		changes.add(new AddImport(ontology2, factory.getOWLImportsDeclaration(IRI
				.create(ontologyByOtherPath))));
		manager.applyChanges(changes);
		manager.saveOntology(ontology1, IRI.create(importsBothNameAndVersion));
		manager.saveOntology(ontology2, IRI.create(importsBothNameAndOther));
	}
}
