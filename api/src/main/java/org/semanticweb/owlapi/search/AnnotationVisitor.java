package org.semanticweb.owlapi.search;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

import org.semanticweb.owlapi.model.HasAnnotations;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLObject;

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
        if (value) {
            return (C) a.getValue();
        }
        return (C) a;
    }

    @Override
    public Set<C> visit(OWLAnnotationAssertionAxiom axiom) {
        if (value) {
            return asUnorderedSet((C) axiom.getValue());
        }
        return asUnorderedSet((C) axiom.getAnnotation());
    }
}
