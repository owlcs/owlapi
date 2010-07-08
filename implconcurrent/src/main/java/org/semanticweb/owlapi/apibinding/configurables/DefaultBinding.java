package org.semanticweb.owlapi.apibinding.configurables;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

public final class DefaultBinding implements
			OWLImplementationBinding {
		public OWLOntologyManager getOWLOntologyManager(OWLDataFactory d) {
			return new OWLOntologyManagerImpl(d);
		}

		public OWLOntology getOWLOntology(OWLOntologyManager oom,
				OWLOntologyID id) {
			
			return new OWLOntologyImpl(oom, id);
		}

		public OWLDataFactory getOWLDataFactory() {
			return OWLDataFactoryImpl.getInstance();
		}
	}