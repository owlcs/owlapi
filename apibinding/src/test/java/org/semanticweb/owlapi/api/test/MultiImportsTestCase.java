package org.semanticweb.owlapi.api.test;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class MultiImportsTestCase extends AbstractOWLAPITestCase {
	public void _testImports() throws Exception {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			manager.addIRIMapper(new AutoIRIMapper(new File(
					"apibinding/src/test/resources/imports"), true));
			OWLOntology o = manager.loadOntologyFromOntologyDocument(this
					.getClass().getResourceAsStream("/imports/D.owl"));
		} catch (OWLOntologyCreationException e) {
			//Thread.dumpStack();
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testCyclicImports() throws Exception {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			manager.addIRIMapper(new AutoIRIMapper(new File(
					"apibinding/src/test/resources/importscyclic"), true));
			OWLOntology o = manager.loadOntologyFromOntologyDocument(this
					.getClass().getResourceAsStream("/importscyclic/D.owl"));
		} catch (OWLOntologyCreationException e) {
			//Thread.dumpStack();
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testCyclicImports2() throws Exception {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			manager.addIRIMapper(new AutoIRIMapper(new File(
					"apibinding/src/test/resources/importscyclic"), true));
			OWLOntology o = manager
					.loadOntologyFromOntologyDocument(IRI
							.create(new File(
									"apibinding/src/test/resources/importscyclic/D.owl")));
		} catch (OWLOntologyCreationException e) {
			//Thread.dumpStack();
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
