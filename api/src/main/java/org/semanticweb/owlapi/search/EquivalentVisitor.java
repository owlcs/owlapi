package org.semanticweb.owlapi.search;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;

@SuppressWarnings("unchecked")
class EquivalentVisitor<C extends OWLObject> extends
        OWLAxiomVisitorExAdapter<Set<C>> {

    private static final long serialVersionUID = 40000L;
    private boolean equiv;

    public EquivalentVisitor(boolean equiv) {
        this.equiv = equiv;
    }

    @SuppressWarnings("null")
    @Nonnull
    @Override
    protected Set<C> doDefault(@SuppressWarnings("unused") OWLAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getClassExpressions();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getIndividuals();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLSameIndividualAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getIndividuals();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getClassExpressions();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Set<C> visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return doDefault(axiom);
    }
}
