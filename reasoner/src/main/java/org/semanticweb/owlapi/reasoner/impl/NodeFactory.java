package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class NodeFactory {


    
    public static DefaultNode<OWLClass> getOWLClassNode() {
        return new OWLClassNode();
    }
    
    public static DefaultNode<OWLClass> getOWLClassNode(OWLClass cls) {
        return new OWLClassNode(cls);
    }
    
    public static DefaultNode<OWLClass> getOWLClassNode(Set<OWLClass> clses) {
        return new OWLClassNode(clses);
    }
    
    public static DefaultNode<OWLClass> getOWLClassTopNode() {
        return OWLClassNode.getTopNode();
    }
    
    public static DefaultNode<OWLClass> getOWLClassBottomNode() {
        return OWLClassNode.getBottomNode();
    }
    
    
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyNode() {
        return new OWLObjectPropertyNode();
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyNode(OWLObjectPropertyExpression prop) {
        return new OWLObjectPropertyNode(prop);
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyNode(Set<OWLObjectPropertyExpression> properties) {
        return new OWLObjectPropertyNode(properties);
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyTopNode() {
        return OWLObjectPropertyNode.getTopNode();
    }
    
    public static DefaultNode<OWLObjectPropertyExpression> getOWLObjectPropertyBottomNode() {
        return OWLObjectPropertyNode.getBottomNode();
    }
    
    
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode() {
        return new OWLDataPropertyNode();
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode(OWLDataProperty prop) {
        return new OWLDataPropertyNode(prop);
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyNode(Set<OWLDataProperty> properties) {
        return new OWLDataPropertyNode(properties);
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyTopNode() {
        return OWLDataPropertyNode.getTopNode();
    }
    
    public static DefaultNode<OWLDataProperty> getOWLDataPropertyBottomNode() {
        return OWLDataPropertyNode.getBottomNode();
    }
    
    
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode() {
        return new OWLNamedIndividualNode();
    }
    
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode(OWLNamedIndividual ind) {
        return new OWLNamedIndividualNode(ind);
    }
    
    public static DefaultNode<OWLNamedIndividual> getOWLNamedIndividualNode(Set<OWLNamedIndividual> inds) {
        return new OWLNamedIndividualNode(inds);
    }

}
