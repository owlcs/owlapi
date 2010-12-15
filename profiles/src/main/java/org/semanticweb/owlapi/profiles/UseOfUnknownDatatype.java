package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 * Use of an unknown datatype. That is, the datatype isn't in the OWL 2 datatype map,
 * the datatype IRI doesn't begin with the xsd: prefix, the datatype isn't rdfs:Literal, and the
 * datatype isn't defined with a DatatypeDefinition axiom
 */
public class UseOfUnknownDatatype extends OWLProfileViolation implements OWL2ProfileViolation {

    private OWLDatatype datatype;

    public UseOfUnknownDatatype(OWLOntology ontology, OWLAxiom axiom, OWLDatatype datatype) {
        super(ontology, axiom);
        this.datatype = datatype;
    }

    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Gets the datatype that is invalid
     * @return The invalid datatype
     */
    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of unknown datatype: ");
        sb.append(datatype);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
