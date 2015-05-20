package org.semanticweb.owlapi.search;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;

@SuppressWarnings("unchecked")
class EquivalentVisitor<C extends OWLObject> implements OWLAxiomVisitorEx<Stream<C>> {

    private final boolean equiv;

    EquivalentVisitor(boolean equiv) {
        this.equiv = equiv;
    }

    @Override
    public Stream<C> doDefault(Object o) {
        return empty();
    }

    @Override
    public Stream<C> visit(OWLEquivalentClassesAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.classExpressions();
        }
        return doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLEquivalentDataPropertiesAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLDifferentIndividualsAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.individuals();
        }
        return doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLSameIndividualAxiom axiom) {
        if (equiv) {
            return (Stream<C>) axiom.individuals();
        }
        return doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLDisjointClassesAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.classExpressions();
        }
        return doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLDisjointDataPropertiesAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLDisjointObjectPropertiesAxiom axiom) {
        if (!equiv) {
            return (Stream<C>) axiom.properties();
        }
        return doDefault(axiom);
    }
}
