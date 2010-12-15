package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class OntologyVersionIRINotAbsolute extends OWLProfileViolation implements OWL2ProfileViolation {

    public OntologyVersionIRINotAbsolute(OWLOntology ontology) {
        super(ontology, null);
    }

    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ontology version IRI not absolute: ");
        sb.append(getOntologyID());
        return sb.toString();
    }
}
