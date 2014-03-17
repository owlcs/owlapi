package org.semanticweb.owlapi.search;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;

@SuppressWarnings("unchecked")
class AnnotationVisitor<C> extends OWLAxiomVisitorExAdapter<Set<C>> {

    private static final long serialVersionUID = 40000L;
    private boolean value;

    public AnnotationVisitor(boolean value) {
        this.value = value;
    }

    @Override
    protected Set<C> doDefault(OWLAxiom axiom) {
        return get(axiom.getAnnotations());
    }

    private Set<C> get(Collection<OWLAnnotation> collection) {
        Set<C> toReturn = new HashSet<C>();
        for (OWLAnnotation c : collection) {
            if (value) {
                toReturn.add((C) c.getValue());
            } else {
                toReturn.add((C) c);
            }
        }
        return toReturn;
    }

    @Override
    public Set<C> visit(OWLAnnotationAssertionAxiom axiom) {
        if (value) {
            return Collections.singleton((C) axiom.getValue());
        }
        return Collections.singleton((C) axiom.getAnnotation());
    }
}
