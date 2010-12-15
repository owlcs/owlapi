package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfIllegalFacetRestriction extends OWLProfileViolation implements OWL2ProfileViolation {

    private OWLDatatypeRestriction datatypeRestriction;

    private OWLFacet facet;

    public UseOfIllegalFacetRestriction(OWLOntology ontology, OWLAxiom axiom, OWLDatatypeRestriction dtr, OWLFacet facet) {
        super(ontology, axiom);
        this.datatypeRestriction = dtr;
        this.facet = facet;
    }

    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLDatatypeRestriction getDatatypeRestriction() {
        return datatypeRestriction;
    }

    public OWLFacet getFacet() {
        return facet;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Facet in datatype restriction does not belong to restricted datatype: ");
        sb.append(facet);
        sb.append(" in ");
        sb.append(datatypeRestriction);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
