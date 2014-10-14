package org.semanticweb.owlapi.api.test.baseclasses;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
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
import org.semanticweb.owlapi.model.OWLObject;
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
import org.semanticweb.owlapi.model.OWLObjectVisitor;
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
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * Created by ses on 10/1/14.
 */
class LiteralFoldingHashCoder implements OWLObjectVisitor, SWRLObjectVisitor {

    protected int hashCode;
    protected static final int MULT = 37;

    public static int hashCode(@Nonnull OWLObject object) {
        checkNotNull(object, "object cannot be null");
        LiteralFoldingHashCoder hashCode = new LiteralFoldingHashCoder();
        object.accept(hashCode);
        return hashCode.hashCode;
    }

    /**
     * hash all literals with the same lexical form to the same value
     *
     * @param node
     *        node to check
     */
    @Override
    public void visit(OWLLiteral node) {
        addValueToHash(node.getLiteral().hashCode());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        addValueToHash(139);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLOntology ontology) {
        addValueToHash(ontology.getOntologyID().hashCode());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        addValueToHash(3);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        addValueToHash(7);
        axiom.getIndividual().accept(this);
        axiom.getClassExpression().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addValueToHash(11);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addValueToHash(13);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addValueToHash(17);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        addValueToHash(19);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        addValueToHash(23);
        axiom.getEntity().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        addValueToHash(29);
        visitCollection(axiom.getIndividuals());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        addValueToHash(31);
        visitCollection(axiom.getClassExpressions());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        addValueToHash(37);
        visitCollection(axiom.getProperties());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        addValueToHash(41);
        visitCollection(axiom.getProperties());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        addValueToHash(43);
        axiom.getOWLClass().accept(this);
        visitCollection(axiom.getClassExpressions());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        addValueToHash(47);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getValue().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        addValueToHash(53);
        visitCollection(axiom.getClassExpressions());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        addValueToHash(59);
        visitCollection(axiom.getProperties());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        addValueToHash(61);
        visitCollection(axiom.getProperties());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        addValueToHash(67);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        addValueToHash(71);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addValueToHash(79);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        addValueToHash(83);
        hashCode = hashCode * MULT + axiom.getFirstProperty().hashCode()
                + axiom.getSecondProperty().hashCode();
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        addValueToHash(89);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        addValueToHash(97);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        addValueToHash(101);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        addValueToHash(103);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        addValueToHash(107);
        visitCollection(axiom.getPropertyChain());
        axiom.getSuperProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addValueToHash(109);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addValueToHash(113);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        addValueToHash(127);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addValueToHash(131);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        addValueToHash(137);
        visitCollection(axiom.getIndividuals());
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        addValueToHash(149);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        addValueToHash(151);
        axiom.getProperty().accept(this);
        visitCollection(axiom.getAnnotations());
    }

    @Override
    public void visit(OWLClass ce) {
        addValueToHash(157);
        ce.getIRI().accept(this);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        addValueToHash(163);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        addValueToHash(167);
        ce.getProperty().accept(this);
        addValueToHash(ce.getCardinality());
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        addValueToHash(173);
        ce.getProperty().accept(this);
        addValueToHash(ce.getCardinality());
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        addValueToHash(179);
        ce.getProperty().accept(this);
        addValueToHash(ce.getCardinality());
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        addValueToHash(181);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        addValueToHash(191);
        addValueToHash(hashCode(ce.getProperty()));
        addValueToHash(hashCode(ce.getFiller()));
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        addValueToHash(193);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        addValueToHash(197);
        ce.getOperand().accept(this);
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        addValueToHash(199);
        ce.getProperty().accept(this);
        addValueToHash(ce.getCardinality());
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        addValueToHash(211);
        visitCollection(ce.getOperands());
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        addValueToHash(223);
        ce.getProperty().accept(this);
        addValueToHash(ce.getCardinality());
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        addValueToHash(227);
        ce.getProperty().accept(this);
        addValueToHash(ce.getCardinality());
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        addValueToHash(229);
        visitCollection(ce.getIndividuals());
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        addValueToHash(233);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        addValueToHash(239);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        addValueToHash(241);
        visitCollection(ce.getOperands());
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        addValueToHash(251);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        addValueToHash(257);
        node.getDataRange().accept(this);
    }

    @Override
    public void visit(OWLDataOneOf node) {
        addValueToHash(263);
        visitCollection(node.getValues());
    }

    @Override
    public void visit(OWLDatatype node) {
        addValueToHash(269);
        node.getIRI().accept(this);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        addValueToHash(271);
        node.getDatatype().accept(this);
        visitCollection(node.getFacetRestrictions());
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        addValueToHash(563);
        addValueToHash(node.getFacet().hashCode());
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(OWLDataProperty property) {
        addValueToHash(283);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        addValueToHash(293);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        addValueToHash(307);
        property.getInverse().accept(this);
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        addValueToHash(311);
        individual.getIRI().accept(this);
    }

    @Override
    public void visit(SWRLRule rule) {
        addValueToHash(631);
        visitCollection(rule.getBody());
        visitCollection(rule.getHead());
    }

    @Override
    public void visit(SWRLClassAtom node) {
        addValueToHash(641);
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        addValueToHash(643);
        node.getArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        addValueToHash(647);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        addValueToHash(653);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        addValueToHash(659);
        visitCollection(node.getAllArguments());
        node.getPredicate().accept(this);
    }

    @Override
    public void visit(SWRLVariable node) {
        addValueToHash(661);
        node.getIRI().accept(this);
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        addValueToHash(677);
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        addValueToHash(683);
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        addValueToHash(797);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        addValueToHash(811);
        int i = node.getFirstArgument().hashCode();
        addValueToHash(i);
        node.getSecondArgument().accept(this);
    }

    public void addValueToHash(int i) {
        hashCode = hashCode * MULT + i;
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        addValueToHash(821);
        axiom.getClassExpression().accept(this);
        visitCollection(axiom.getPropertyExpressions());
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        addValueToHash(823);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        addValueToHash(827);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        addValueToHash(829);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        addValueToHash(839);
        visitCollection(node.getOperands());
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        addValueToHash(853);
        visitCollection(node.getOperands());
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        addValueToHash(857);
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        addValueToHash(859);
        addValueToHash(individual.getID().hashCode());
    }

    @Override
    public void visit(IRI iri) {
        addValueToHash(863);
        addValueToHash(iri.toURI().hashCode());
    }

    @Override
    public void visit(OWLAnnotation node) {
        addValueToHash(877);
        node.getProperty().accept(this);
        node.getValue().accept(this);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        addValueToHash(897);
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
    }

    private void visitCollection(Collection<? extends OWLObject> collection) {
        for (OWLObject object : collection) {
            object.accept(this);
        }
    }
}
