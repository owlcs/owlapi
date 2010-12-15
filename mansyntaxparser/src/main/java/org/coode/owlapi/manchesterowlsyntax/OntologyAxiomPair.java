package org.coode.owlapi.manchesterowlsyntax;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 18-Feb-2009
 */
public class OntologyAxiomPair {

    private OWLOntology ontology;

    private OWLAxiom axiom;

    public OntologyAxiomPair(OWLOntology ontology, OWLAxiom axiom) {
        this.ontology = ontology;
        this.axiom = axiom;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public OWLAxiom getAxiom() {
        return axiom;
    }

    @Override
	public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OntologyAxiomPair)) {
            return false;
        }
        OntologyAxiomPair other = (OntologyAxiomPair) obj;
        if (ontology != null && other.ontology != null) {
            return ontology.equals(other.ontology) && axiom.equals(other.axiom);
        }
        else if(ontology != null && other.ontology == null) {
            return false;
        }
        else if(ontology == null && other.ontology != null) {
            return false;
        }
        else {
            return axiom.equals(other.axiom);
        }
    }

    @Override
	public int hashCode() {
        if (ontology != null) {
            return ontology.hashCode() + axiom.hashCode();
        }
        else {
            return 37 + axiom.hashCode();
        }
    }

    @Override
    public String toString() {
        return axiom.toString() + " in " + (ontology != null ? ontology.toString() : "");
    }
}
