package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfUndeclaredAnnotationProperty extends OWLProfileViolation implements OWL2DLProfileViolation {


    private OWLAnnotationProperty property;

    private OWLAnnotation annotation;

    public UseOfUndeclaredAnnotationProperty(OWLOntology ontology, OWLAxiom axiom, OWLAnnotation annotation, OWLAnnotationProperty prop) {
        super(ontology, axiom);
        this.property = prop;
        this.annotation = annotation;
    }

    public OWLAnnotationProperty getOWLAnnotationProperty() {
        return property;
    }

    public OWLAnnotation getOWLAnnotation() {
        return annotation;
    }

    public UseOfUndeclaredAnnotationProperty(OWLOntology ontology, OWLAxiom axiom, OWLAnnotation annotation) {
        super(ontology, axiom);
        this.annotation = annotation;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of undeclared annotation property: ");
        sb.append(property);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
