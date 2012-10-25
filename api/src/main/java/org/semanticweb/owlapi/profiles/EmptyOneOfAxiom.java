package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
@SuppressWarnings("javadoc")
public class EmptyOneOfAxiom extends OWLProfileViolation implements
		OWL2DLProfileViolation, OWL2ELProfileViolation, OWL2ProfileViolation,
		OWL2QLProfileViolation, OWL2RLProfileViolation {
	public EmptyOneOfAxiom(OWLOntology currentOntology, OWLAxiom currentAxiom) {
		super(currentOntology, currentAxiom);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Empty OneOf: at least one value needed: ");
		sb.append(getAxiom());
		return sb.toString();
	}

    @Override
    public void accept(OWL2RLProfileViolationVisitor visitor) {
		visitor.visit(this);

	}

    @Override
    public void accept(OWL2QLProfileViolationVisitor visitor) {
		visitor.visit(this);

	}

    @Override
    public void accept(OWL2ProfileViolationVisitor visitor) {
		visitor.visit(this);

	}

    @Override
    public void accept(OWL2ELProfileViolationVisitor visitor) {
		visitor.visit(this);

	}

    @Override
    public void accept(OWL2DLProfileViolationVisitor visitor) {
		visitor.visit(this);

	}
}
