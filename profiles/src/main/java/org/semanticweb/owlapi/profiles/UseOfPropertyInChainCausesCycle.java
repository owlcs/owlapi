package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfPropertyInChainCausesCycle extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLObjectPropertyExpression property;

    public UseOfPropertyInChainCausesCycle(OWLOntology ontology, OWLSubPropertyChainOfAxiom axiom, OWLObjectPropertyExpression property) {
        super(ontology, axiom);
        this.property = property;
    }

    public OWLObjectPropertyExpression getOWLObjectProperty() {
        return property;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }
}
