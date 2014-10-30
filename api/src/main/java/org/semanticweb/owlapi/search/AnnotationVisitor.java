package org.semanticweb.owlapi.search;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.HasAnnotations;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;

@SuppressWarnings("unchecked")
class AnnotationVisitor<C> implements OWLAxiomVisitorEx<Set<C>> {

    private final boolean value;

    AnnotationVisitor(boolean value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public Set<C> doDefault(@Nonnull Object object) {
        return ((HasAnnotations) object).annotations().map(a -> get(a))
                .collect(toSet());
    }

    @Nonnull
    private C get(OWLAnnotation a) {
        if (value) {
            return (C) a.getValue();
        }
        return (C) a;
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        if (value) {
            return CollectionFactory.createSet((C) axiom.getValue());
        }
        return CollectionFactory.createSet((C) axiom.getAnnotation());
    }
}
