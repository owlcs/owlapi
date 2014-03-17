package org.semanticweb.owlapi.search;

import java.util.Collections;
import java.util.Set;

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

    @Override
    protected Set<C> doDefault(OWLAxiom axiom) {
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLEquivalentClassesAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getClassExpressions();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLEquivalentDataPropertiesAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLDifferentIndividualsAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getIndividuals();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLSameIndividualAxiom axiom) {
        if (equiv) {
            return (Set<C>) axiom.getIndividuals();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLDisjointClassesAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getClassExpressions();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLDisjointDataPropertiesAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<C> visit(OWLDisjointObjectPropertiesAxiom axiom) {
        if (!equiv) {
            return (Set<C>) axiom.getProperties();
        }
        return Collections.emptySet();
    }
}
