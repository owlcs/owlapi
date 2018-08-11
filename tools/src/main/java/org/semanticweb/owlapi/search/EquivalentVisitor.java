package org.semanticweb.owlapi.search;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.util.stream.Stream;

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
class EquivalentVisitor<C extends OWLObject> implements OWLAxiomVisitorEx<Stream<C>> {

    private final boolean equiv;

    EquivalentVisitor(boolean equiv) {
        this.equiv = equiv;
    }

    @Override
    public Stream<C> doDefault(OWLObject o) {
        return empty();
    }

    @Override
    public Stream<C> visit(OWLEquivalentClassesAxiom axiom) {
        return equiv ? (Stream<C>) axiom.classExpressions() : doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return equiv ? (Stream<C>) axiom.properties() : doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return equiv ? (Stream<C>) axiom.properties() : doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLDifferentIndividualsAxiom axiom) {
        return equiv ? doDefault(axiom) : (Stream<C>) axiom.individuals();
    }

    @Override
    public Stream<C> visit(OWLSameIndividualAxiom axiom) {
        return equiv ? (Stream<C>) axiom.individuals() : doDefault(axiom);
    }

    @Override
    public Stream<C> visit(OWLDisjointClassesAxiom axiom) {
        return equiv ? doDefault(axiom) : (Stream<C>) axiom.classExpressions();
    }

    @Override
    public Stream<C> visit(OWLDisjointDataPropertiesAxiom axiom) {
        return equiv ? doDefault(axiom) : (Stream<C>) axiom.properties();
    }

    @Override
    public Stream<C> visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return equiv ? doDefault(axiom) : (Stream<C>) axiom.properties();
    }
}
