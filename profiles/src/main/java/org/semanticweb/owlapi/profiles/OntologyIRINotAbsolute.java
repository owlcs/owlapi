package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class OntologyIRINotAbsolute extends OWLProfileViolation implements OWL2ProfileViolation {

    public OntologyIRINotAbsolute(OWLOntology ontology) {
        super(ontology, null);
    }

    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ontology IRI not absolute: ");
        sb.append(getOntologyID());
        return sb.toString();
    }
}
