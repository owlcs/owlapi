package org.semanticweb.owlapi6.search;

import org.semanticweb.owlapi6.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;

@SuppressWarnings("unchecked")
class SupSubVisitor<C extends OWLObject> implements OWLAxiomVisitorEx<C> {

    private final boolean sup;

    SupSubVisitor(boolean sup) {
        this.sup = sup;
    }

    @Override
    public C visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return (C) (sup ? axiom.getSuperProperty() : axiom.getSubProperty());
    }

    @Override
    public C visit(OWLSubClassOfAxiom axiom) {
        return (C) (sup ? axiom.getSuperClass() : axiom.getSubClass());
    }

    @Override
    public C visit(OWLSubDataPropertyOfAxiom axiom) {
        return (C) (sup ? axiom.getSuperProperty() : axiom.getSubProperty());
    }

    @Override
    public C visit(OWLSubObjectPropertyOfAxiom axiom) {
        return (C) (sup ? axiom.getSuperProperty() : axiom.getSubProperty());
    }
}
