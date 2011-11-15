package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;

public class ClassAxiomByClassPointer extends MapPointer<OWLClass, OWLClassAxiom> {
	public ClassAxiomByClassPointer(
			AxiomType<?> t, OWLAxiomVisitorEx<?> v, boolean initialized, Internals i) {
		super(t, v, initialized, i);
	}

	@Override
	public void init() {
		if (isInitialized()) {
			return;
		}
		super.init();
		// special case: this map needs other maps to be initialized first
		for (OWLClass c : this.i.getKeyset(this.i.getEquivalentClassesAxiomsByClass())) {
			for (OWLClassAxiom ax : this.i.getValues(
					this.i.getEquivalentClassesAxiomsByClass(), c)) {
				put(c, ax);
			}
		}
		for (OWLClass c : this.i.getKeyset(this.i.getSubClassAxiomsByLHS())) {
			for (OWLClassAxiom ax : this.i.getValues(this.i.getSubClassAxiomsByLHS(), c)) {
				put(c, ax);
			}
		}
		for (OWLClass c : this.i.getKeyset(this.i.getDisjointClassesAxiomsByClass())) {
			for (OWLClassAxiom ax : this.i.getValues(
					this.i.getDisjointClassesAxiomsByClass(), c)) {
				put(c, ax);
			}
		}
		for (OWLClass c : this.i.getKeyset(this.i.getDisjointUnionAxiomsByClass())) {
			for (OWLClassAxiom ax : this.i.getValues(
					this.i.getDisjointUnionAxiomsByClass(), c)) {
				put(c, ax);
			}
		}
	}
}