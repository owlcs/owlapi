package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 *
 * Specifies that a datatype is not declared
 *
 */
public class UseOfUndeclaredDatatype extends OWLProfileViolation implements OWL2ProfileViolation {

    private OWLDatatype datatype;

    public UseOfUndeclaredDatatype(OWLOntology ontology, OWLAxiom axiom, OWLDatatype datatype) {
        super(ontology, axiom);
        this.datatype = datatype;
    }
    
    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of undeclared datatype: ");
        sb.append(datatype);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
