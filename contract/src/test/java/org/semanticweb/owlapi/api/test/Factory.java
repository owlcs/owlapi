package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyManagerFactoryRegistry;

@SuppressWarnings("javadoc")
public class Factory {
	public static OWLOntologyManager getManager() {
		return OWLOntologyManagerFactoryRegistry.createOWLOntologyManager();
	}
	public static OWLDataFactory getFactory() {
		return OWLOntologyManagerFactoryRegistry.getOWLDataFactory();
	}
}
