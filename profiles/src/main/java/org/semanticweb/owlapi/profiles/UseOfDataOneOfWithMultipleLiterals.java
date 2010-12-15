package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfDataOneOfWithMultipleLiterals extends UseOfIllegalDataRange {

    private OWLDataOneOf dataOneOf;

    public UseOfDataOneOfWithMultipleLiterals(OWLOntology ontology, OWLAxiom axiom, OWLDataOneOf dataOneOf) {
        super(ontology, axiom, dataOneOf);
        this.dataOneOf = dataOneOf;
    }

    public OWLDataOneOf getDataOneOf() {
        return dataOneOf;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of DataOneOf with multiple literals: ");
        sb.append(getAxiom());
        sb.append(" [in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
