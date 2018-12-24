package org.semanticweb.owlapi6.search;

import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;

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
