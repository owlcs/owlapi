package org.semanticweb.owlapi.search;

import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;

@SuppressWarnings("unchecked")
class RangeVisitor<C extends OWLObject> implements OWLAxiomVisitorEx<C> {

    @Override
    public C visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return (C) axiom.getRange();
    }

    @Override
    public C visit(OWLDataPropertyRangeAxiom axiom) {
        return (C) axiom.getRange();
    }

    @Override
    public C visit(OWLObjectPropertyRangeAxiom axiom) {
        return (C) axiom.getRange();
    }
}
