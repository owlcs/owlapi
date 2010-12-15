package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;
import org.semanticweb.owlapi.model.SetOntologyID;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 */
@SuppressWarnings("unused")
public class OWLOntologyChangeVisitorAdapter implements OWLOntologyChangeVisitor {

    public void visit(RemoveAxiom change) {
    }
    

    public void visit(SetOntologyID change) {
    }


    public void visit(AddAxiom change) {
    }


    public void visit(AddImport change) {
    }


    public void visit(RemoveImport change) {
    }


    public void visit(AddOntologyAnnotation change) {
    }


    public void visit(RemoveOntologyAnnotation change) {
    }


}
