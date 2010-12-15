package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfReservedVocabularyForClassIRI extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLClass cls;

    public UseOfReservedVocabularyForClassIRI(OWLOntology ontology, OWLAxiom axiom, OWLClass cls) {
        super(ontology, axiom);
        this.cls = cls;
    }

    public OWLClass getOWLClass() {
        return cls;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of reserved vocabulary for class IRI: ");
        sb.append(cls);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
