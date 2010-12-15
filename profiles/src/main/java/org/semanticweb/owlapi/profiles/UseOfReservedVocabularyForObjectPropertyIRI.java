package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfReservedVocabularyForObjectPropertyIRI extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLObjectProperty property;

    public UseOfReservedVocabularyForObjectPropertyIRI(OWLOntology ontology, OWLAxiom axiom, OWLObjectProperty property) {
        super(ontology, axiom);
        this.property = property;
    }

    public OWLObjectProperty getOWLObjectProperty() {
        return property;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of reserved vocabulary for object property IRI: ");
        sb.append(property);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
