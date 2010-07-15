package org.semanticweb.owlapi.apibinding.configurables;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.alternateimpls.LockingOWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.LockingOWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryLWR;

public final class ThreadSafeWeakRefsBinding implements OWLImplementationBinding {
	public OWLOntologyManager getOWLOntologyManager(OWLDataFactory d) {
		return new LockingOWLOntologyManagerImpl(d);
	}

	public OWLOntology getOWLOntology(OWLOntologyManager oom, OWLOntologyID id) {
		return new LockingOWLOntologyImpl(oom, id);
	}

	public OWLDataFactory getOWLDataFactory() {
		return DataFactoryLWR.getInstance();
	}
}