package org.semanticweb.owlapi.search;

import java.util.Collection;
import java.util.HashSet;
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
        return get(((HasAnnotations) object).getAnnotations());
    }

    @Nonnull
    private Set<C> get(@Nonnull Collection<OWLAnnotation> collection) {
        Set<C> toReturn = new HashSet<>();
        for (OWLAnnotation c : collection) {
            if (value) {
                toReturn.add((C) c.getValue());
            } else {
                toReturn.add((C) c);
            }
        }
        return toReturn;
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
