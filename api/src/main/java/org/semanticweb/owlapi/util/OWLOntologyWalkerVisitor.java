package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 30-Jul-2008<br><br>
 */
public class OWLOntologyWalkerVisitor<E> extends OWLObjectVisitorExAdapter<E> {

    private OWLOntologyWalker walker;


    public OWLOntologyWalkerVisitor(OWLOntologyWalker walker) {
        this.walker = walker;
    }

    public OWLAxiom getCurrentAxiom() {
        return walker.getAxiom();
    }

    public OWLOntology getCurrentOntology() {
        return walker.getOntology();
    }

    public OWLAnnotation getCurrentAnnotation() {
        return walker.getAnnotation();
    }


}
