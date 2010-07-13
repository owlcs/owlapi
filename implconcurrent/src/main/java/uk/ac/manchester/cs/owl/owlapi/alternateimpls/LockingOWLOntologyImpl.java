package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;

public class LockingOWLOntologyImpl extends OWLOntologyImpl {
	public LockingOWLOntologyImpl(OWLOntologyManager manager,
			OWLOntologyID ontologyID) {
		super(manager, ontologyID);
		this.internals = new LockingOWLOntologyInternals();
	}
}
