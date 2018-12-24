package org.semanticweb.owlapi6.search;

import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

import org.semanticweb.owlapi6.model.HasAnnotations;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi6.model.OWLObject;

@SuppressWarnings("unchecked")
class AnnotationVisitor<C> implements OWLAxiomVisitorEx<Set<C>> {

    private final boolean value;

    AnnotationVisitor(boolean value) {
        this.value = value;
    }

    @Override
    public Set<C> doDefault(OWLObject object) {
        return asUnorderedSet(((HasAnnotations) object).annotations().map(this::get));
    }

    private C get(OWLAnnotation a) {
        return (C) (value ? a.getValue() : a);
    }

    @Override
    public Set<C> visit(OWLAnnotationAssertionAxiom axiom) {
        return asUnorderedSet((C) (value ? axiom.getValue() : axiom.getAnnotation()));
    }
}
