package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfReservedVocabularyForOntologyIRI extends OWLProfileViolation implements OWL2DLProfileViolation {

    public UseOfReservedVocabularyForOntologyIRI(OWLOntology ontology) {
        super(ontology, null);
    }


    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of reserved vocabulary for ontology IRI: ");
        sb.append(getOntologyID());
        return sb.toString();
    }
}
