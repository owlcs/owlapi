package org.semanticweb.owlapi.apibinding.configurables;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public interface OWLImplementationBinding{
		OWLOntologyManager getOWLOntologyManager(OWLDataFactory d);
		OWLDataFactory getOWLDataFactory();
		OWLOntology getOWLOntology(OWLOntologyManager oom, OWLOntologyID id);
	}