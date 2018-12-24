package org.semanticweb.owlapi6.search;

import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;

@SuppressWarnings("unchecked")
class DomainVisitor<C extends OWLObject> implements OWLAxiomVisitorEx<C> {

    @Override
    public C visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return (C) axiom.getDomain();
    }

    @Override
    public C visit(OWLDataPropertyDomainAxiom axiom) {
        return (C) axiom.getDomain();
    }

    @Override
    public C visit(OWLObjectPropertyDomainAxiom axiom) {
        return (C) axiom.getDomain();
    }
}
