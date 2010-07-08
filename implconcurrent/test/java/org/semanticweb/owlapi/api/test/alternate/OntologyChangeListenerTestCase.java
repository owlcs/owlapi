package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.ImpendingOWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 */
public class OntologyChangeListenerTestCase extends AbstractOWLAPITestCase {
	public void testOntologyChangeListener() {
		OWLOntology ont = getOWLOntology("ont");
		OWLClass clsA = getOWLClass("ClsA");
		OWLClass clsB = getOWLClass("ClsB");
		OWLSubClassOfAxiom ax = getFactory().getOWLSubClassOfAxiom(clsA, clsB);
		final Set<OWLAxiom> impendingAdditions = new HashSet<OWLAxiom>();
		final Set<OWLAxiom> impendingRemovals = new HashSet<OWLAxiom>();
		final Set<OWLAxiom> additions = new HashSet<OWLAxiom>();
		final Set<OWLAxiom> removals = new HashSet<OWLAxiom>();
		getManager().addImpendingOntologyChangeListener(
				new ImpendingOWLOntologyChangeListener() {
					public void handleImpendingOntologyChanges(
							List<? extends OWLOntologyChange> impendingChanges) {
						for (OWLOntologyChange change : impendingChanges) {
							if (change instanceof AddAxiom) {
								impendingAdditions.add(change.getAxiom());
							} else if (change instanceof RemoveAxiom) {
								impendingRemovals.add(change.getAxiom());
							}
						}
					}
				});
		getManager().addOntologyChangeListener(new OWLOntologyChangeListener() {
			public void ontologiesChanged(
					List<? extends OWLOntologyChange> changes)
					throws OWLException {
				for (OWLOntologyChange change : changes) {
					if (change instanceof AddAxiom) {
						additions.add(change.getAxiom());
					} else if (change instanceof RemoveAxiom) {
						removals.add(change.getAxiom());
					}
				}
			}
		});
		getManager().addAxiom(ont, ax);
		assertTrue(additions.contains(ax));
		assertTrue(impendingAdditions.contains(ax));
		getManager().removeAxiom(ont, ax);
		assertTrue(removals.contains(ax));
		assertTrue(impendingRemovals.contains(ax));
	}
}
