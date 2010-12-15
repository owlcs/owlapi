package org.semanticweb.owlapi.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(IRI iri) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDatatype node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLObjectProperty property) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLAnonymousIndividual individual) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(SWRLClassAtom node) {
        return node.getPredicate().accept(this);
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLObjectInverseOf property) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(SWRLDataRangeAtom node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLAnnotation node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDataOneOf node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDataProperty property) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(SWRLObjectPropertyAtom node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDataIntersectionOf node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLNamedIndividual individual) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDataUnionOf node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLSubClassOfAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.addAll(axiom.getSubClass().accept(this));
        result.addAll(axiom.getSuperClass().accept(this));
        return result;
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDatatypeRestriction node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(SWRLBuiltInAtom node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLAnnotationProperty property) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLClass ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(SWRLVariable node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLFacetRestriction node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(SWRLLiteralArgument node) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectComplementOf ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getOperand().accept(this));
        return result;
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(SWRLSameIndividualAtom node) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectSomeValuesFrom ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLObjectOneOf ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDifferentIndividualsAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataSomeValuesFrom ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    public Set<OWLClassExpression> visit(OWLDataAllValuesFrom ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDisjointDataPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLDataHasValue ce) {
        return Collections.<OWLClassExpression>singleton(ce);
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLObjectPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLAnnotationAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDataPropertyRangeAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLFunctionalDataPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDataPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLSubDataPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLSameIndividualAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLSubPropertyChainOfAxiom axiom) {
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLInverseObjectPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    public Set<OWLClassExpression> visit(OWLHasKeyAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }
    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return Collections.emptySet();
    }
    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(OWLDataComplementOf node) {
        return Collections.emptySet();
    }
    @SuppressWarnings("unused")
    public Set<OWLClassExpression> visit(SWRLDataPropertyAtom node) {
        return Collections.emptySet();
    }
}
