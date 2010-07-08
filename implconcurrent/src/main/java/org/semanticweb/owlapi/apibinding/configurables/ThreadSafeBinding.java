package org.semanticweb.owlapi.apibinding.configurables;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.LockingOWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.LockingOWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.LockingOWLOntologyManagerImpl;

public final class ThreadSafeBinding implements
			OWLImplementationBinding {
		public OWLOntologyManager getOWLOntologyManager(OWLDataFactory d) {
			return new LockingOWLOntologyManagerImpl(d);
		}

		public OWLOntology getOWLOntology(OWLOntologyManager oom,
				OWLOntologyID id) {
			
			return new LockingOWLOntologyImpl(oom, id);
		}

		public OWLDataFactory getOWLDataFactory() {
			return LockingOWLDataFactoryImpl.getInstance();
		}
	}