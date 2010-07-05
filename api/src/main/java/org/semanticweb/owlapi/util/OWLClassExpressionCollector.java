package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Jun-2010
 * <br>
 * Collects all of the nested class expression that are used in some OWLObject.  For example, given
 * SubClassOf(ObjectUnionOf(D C) ObjectSomeValuesFrom(R F)) the collector could be used to obtain
 * ObjectUnionOf(D C), D, C, ObjectSomeValuesFrom(R F), F
 */
public class OWLClassExpressionCollector implements OWLObjectVisitorEx<Set<OWLClassExpression>> {

    public Set<OWLClassExpression> visit(IRI iri) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDatatype node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectProperty property) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLAnonymousIndividual individual) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLClassAtom node) {
        return node.getPredicate().accept(this);
    }

    public Set<OWLClassExpression> visit(OWLObjectInverseOf property) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLDataRangeAtom node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLAnnotation node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataOneOf node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataProperty property) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLObjectPropertyAtom node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataIntersectionOf node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLNamedIndividual individual) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataUnionOf node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLSubClassOfAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.addAll(axiom.getSubClass().accept(this));
        result.addAll(axiom.getSuperClass().accept(this));
        return result;
    }

    public Set<OWLClassExpression> visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLOntology ontology) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for(OWLAxiom ax : ontology.getLogicalAxioms()) {
            result.addAll(ax.accept(this));
        }
        return result;
    }

    public Set<OWLClassExpression> visit(OWLDatatypeRestriction node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLBuiltInAtom node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLAnnotationProperty property) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLClass ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(SWRLVariable node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLLiteral node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectIntersectionOf ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        for(OWLClassExpression op : ce.getOperands()) {
            result.addAll(op.accept(this));
        }
        return result;
    }

    public Set<OWLClassExpression> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLIndividualArgument node) {
        return Collections.emptySet();
    }


    public Set<OWLClassExpression> visit(OWLObjectUnionOf ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        for(OWLClassExpression op : ce.getOperands()) {
            result.addAll(op.accept(this));
        }
        return result;
    }

    public Set<OWLClassExpression> visit(OWLFacetRestriction node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLLiteralArgument node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectComplementOf ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getOperand().accept(this));
        return result;
    }

    public Set<OWLClassExpression> visit(SWRLSameIndividualAtom node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectSomeValuesFrom ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    public Set<OWLClassExpression> visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLDifferentIndividualsAtom node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectAllValuesFrom ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    public Set<OWLClassExpression> visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for(OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    public Set<OWLClassExpression> visit(OWLObjectHasValue ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    public Set<OWLClassExpression> visit(OWLObjectMinCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    public Set<OWLClassExpression> visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    public Set<OWLClassExpression> visit(OWLObjectExactCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    public Set<OWLClassExpression> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectMaxCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    public Set<OWLClassExpression> visit(OWLObjectHasSelf ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectOneOf ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLDifferentIndividualsAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataSomeValuesFrom ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLDataAllValuesFrom ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLDisjointDataPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataHasValue ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataMinCardinality ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLObjectPropertyRangeAxiom axiom) {
        return axiom.getRange().accept(this);
    }

    public Set<OWLClassExpression> visit(OWLDataExactCardinality ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLDataMaxCardinality ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLObjectPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLSubObjectPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(axiom.getOWLClass());
        for(OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    public Set<OWLClassExpression> visit(OWLDeclarationAxiom axiom) {
        return axiom.getEntity().accept(this);
    }

    public Set<OWLClassExpression> visit(OWLAnnotationAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataPropertyRangeAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLFunctionalDataPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLClassAssertionAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    public Set<OWLClassExpression> visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for(OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    public Set<OWLClassExpression> visit(OWLDataPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLSubDataPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLSameIndividualAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLSubPropertyChainOfAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLInverseObjectPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLHasKeyAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    public Set<OWLClassExpression> visit(OWLDatatypeDefinitionAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLRule rule) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for(SWRLAtom atom : rule.getBody()) {
            result.addAll(atom.accept(this));
        }
        for(SWRLAtom atom : rule.getHead()) {
            result.addAll(atom.accept(this));
        }
        return result;
    }

    public Set<OWLClassExpression> visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataComplementOf node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLDataPropertyAtom node) {
        return Collections.emptySet();
    }
}
