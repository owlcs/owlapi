package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitorEx;
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
public class OWLOntologyChangeVisitorAdapterEx<O> implements OWLOntologyChangeVisitorEx<O> {

    public O visit(RemoveAxiom change) {
    	return null;
    }
    

    public O visit(SetOntologyID change) {
    	return null;
    }


    public O visit(AddAxiom change) {
    	return null;
    }


    public O visit(AddImport change) {
    	return null;
    }


    public O visit(RemoveImport change) {
    	return null;
    }


    public O visit(AddOntologyAnnotation change) {
    	return null;
    }


    public O visit(RemoveOntologyAnnotation change) {
    	return null;
    }


}
