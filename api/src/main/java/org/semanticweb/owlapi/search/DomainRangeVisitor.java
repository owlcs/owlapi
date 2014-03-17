package org.semanticweb.owlapi.search;

import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;

@SuppressWarnings("unchecked")
class DomainRangeVisitor<C extends OWLObject> extends
        OWLAxiomVisitorExAdapter<C> {

    private static final long serialVersionUID = 40000L;
    private boolean range;

    public DomainRangeVisitor(boolean range) {
        this.range = range;
    }

    @Override
    public C visit(OWLAnnotationPropertyDomainAxiom axiom) {
        if (!range) {
            return (C) axiom.getDomain();
        }
        return null;
    }

    @Override
    public C visit(OWLAnnotationPropertyRangeAxiom axiom) {
        if (range) {
            return (C) axiom.getRange();
        }
        return null;
    }

    @Override
    public C visit(OWLDataPropertyDomainAxiom axiom) {
        if (!range) {
            return (C) axiom.getDomain();
        }
        return null;
    }

    @Override
    public C visit(OWLDataPropertyRangeAxiom axiom) {
        if (range) {
            return (C) axiom.getRange();
        }
        return null;
    }

    @Override
    public C visit(OWLObjectPropertyDomainAxiom axiom) {
        if (!range) {
            return (C) axiom.getDomain();
        }
        return null;
    }

    @Override
    public C visit(OWLObjectPropertyRangeAxiom axiom) {
        if (range) {
            return (C) axiom.getRange();
        }
        return null;
    }
}
