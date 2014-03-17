package org.semanticweb.owlapi.search;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;

@SuppressWarnings("unchecked")
class SupSubVisitor<C extends OWLObject> extends OWLAxiomVisitorExAdapter<C> {

    private static final long serialVersionUID = 40000L;
    private boolean sup;

    public SupSubVisitor(boolean sup) {
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
