package org.semanticweb.owlapi.search;

import org.semanticweb.owlapi.model.*;

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
