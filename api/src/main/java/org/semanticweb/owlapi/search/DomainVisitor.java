package org.semanticweb.owlapi.search;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;

@SuppressWarnings("unchecked")
class DomainVisitor<C extends OWLObject> implements OWLAxiomVisitorEx<C> {

    @Override
    public C visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        return (C) axiom.getDomain();
    }

    @Override
    public C visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        return (C) axiom.getDomain();
    }

    @Override
    public C visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        return (C) axiom.getDomain();
    }
}
