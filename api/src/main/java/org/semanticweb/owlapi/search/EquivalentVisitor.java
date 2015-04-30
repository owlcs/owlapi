package org.semanticweb.owlapi.search;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

@SuppressWarnings("unchecked")
class EquivalentVisitor<C extends OWLObject>
    implements OWLAxiomVisitorEx<Stream<C>> {

    private final boolean equiv;

    EquivalentVisitor(boolean equiv) {
        this.equiv = equiv;
    }

    @Override
    public Stream<C> doDefault(Object o) {
        return empty();
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.classExpressions();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.individuals();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLSameIndividualAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.individuals();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.classExpressions();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }

    @Nonnull
    @Override
    public Stream<C> visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }
}
