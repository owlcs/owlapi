package org.semanticweb.owlapi.util;

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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03-Jan-2007<br><br>
 */
public class HashCode implements OWLObjectVisitor, SWRLObjectVisitor {

    private int hashCode;

    private static final int MULT = 37;


    public static int hashCode(OWLObject object) {
        HashCode hashCode = new HashCode();
        object.accept(hashCode);
        return hashCode.hashCode;
    }

    public void visit(OWLOntology ontology) {
        hashCode = ontology.getOntologyID().hashCode();
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        hashCode = 3;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        hashCode = 7;
        hashCode = hashCode * MULT + axiom.getIndividual().hashCode();
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        hashCode = 11;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        hashCode = 13;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        hashCode = 17;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        hashCode = 19;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        hashCode = 23;
        hashCode = hashCode * MULT + axiom.getEntity().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        hashCode = 29;
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        hashCode = 31;
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        hashCode = 37;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        hashCode = 41;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        hashCode = 43;
        hashCode = hashCode * MULT + axiom.getOWLClass().hashCode();
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        hashCode = 47;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getValue().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        hashCode = 53;
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        hashCode = 59;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        hashCode = 61;
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        hashCode = 67;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        hashCode = 71;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        hashCode = 79;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        hashCode = 83;
        hashCode = hashCode * MULT + axiom.getFirstProperty().hashCode() + axiom.getSecondProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        hashCode = 89;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        hashCode = 97;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        hashCode = 101;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        hashCode = 103;
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        hashCode = 107;
        hashCode = hashCode * MULT + axiom.getPropertyChain().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        hashCode = 109;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        hashCode = 113;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        hashCode = 127;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        hashCode = 131;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        hashCode = 137;
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        hashCode = 139;
        hashCode = hashCode * MULT + axiom.getSubClass().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperClass().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        hashCode = 149;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        hashCode = 151;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }


    public void visit(OWLClass desc) {
        hashCode = 157;
        hashCode = hashCode * MULT + desc.getIRI().hashCode();
    }


    public void visit(OWLDataAllValuesFrom desc) {
        hashCode = 163;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataExactCardinality desc) {
        hashCode = 167;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataMaxCardinality desc) {
        hashCode = 173;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataMinCardinality desc) {
        hashCode = 179;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        hashCode = 181;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLDataHasValue desc) {
        hashCode = 191;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getValue().hashCode();
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        hashCode = 193;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectComplementOf desc) {
        hashCode = 197;
        hashCode = hashCode * MULT + desc.getOperand().hashCode();
    }


    public void visit(OWLObjectExactCardinality desc) {
        hashCode = 199;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectIntersectionOf desc) {
        hashCode = 211;
        hashCode = hashCode * MULT + desc.getOperands().hashCode();
    }


    public void visit(OWLObjectMaxCardinality desc) {
        hashCode = 223;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectMinCardinality desc) {
        hashCode = 227;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getCardinality();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectOneOf desc) {
        hashCode = 229;
        hashCode = hashCode * MULT + desc.getIndividuals().hashCode();
    }


    public void visit(OWLObjectHasSelf desc) {
        hashCode = 233;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        hashCode = 239;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getFiller().hashCode();
    }


    public void visit(OWLObjectUnionOf desc) {
        hashCode = 241;
        hashCode = hashCode * MULT + desc.getOperands().hashCode();
    }


    public void visit(OWLObjectHasValue desc) {
        hashCode = 251;
        hashCode = hashCode * MULT + desc.getProperty().hashCode();
        hashCode = hashCode * MULT + desc.getValue().hashCode();
    }


    public void visit(OWLDataComplementOf node) {
        hashCode = 257;
        hashCode = hashCode * MULT + node.getDataRange().hashCode();
    }


    public void visit(OWLDataOneOf node) {
        hashCode = 263;
        hashCode = hashCode * MULT + node.getValues().hashCode();
    }


    public void visit(OWLDatatype node) {
        hashCode = 269;
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }


    public void visit(OWLDatatypeRestriction node) {
        hashCode = 271;
        hashCode = hashCode * MULT + node.getDatatype().hashCode();
        hashCode = hashCode * MULT + node.getFacetRestrictions().hashCode();
    }


    public void visit(OWLFacetRestriction node) {
        hashCode = 563;
        hashCode = hashCode * MULT + node.getFacet().hashCode();
        hashCode = hashCode * MULT + node.getFacetValue().hashCode();
    }


    public void visit(OWLLiteral node) {
        hashCode = 277;
        hashCode = hashCode * MULT + node.getDatatype().hashCode();
        hashCode = hashCode * MULT + node.getLiteral().hashCode();
        if(node.hasLang()) {
            hashCode = hashCode * MULT + node.getLang().hashCode();
        }
    }

    public void visit(OWLDataProperty property) {
        hashCode = 283;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }


    public void visit(OWLObjectProperty property) {
        hashCode = 293;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }


    public void visit(OWLObjectInverseOf property) {
        hashCode = 307;
        hashCode = hashCode * MULT + property.getInverse().hashCode();
    }


    public void visit(OWLNamedIndividual individual) {
        hashCode = 311;
        hashCode = hashCode * MULT + individual.getIRI().hashCode();
    }

    public void visit(SWRLRule rule) {
        hashCode = 631;
        hashCode = hashCode * MULT + rule.getBody().hashCode();
        hashCode = hashCode * MULT + rule.getHead().hashCode();
    }


    public void visit(SWRLClassAtom node) {
        hashCode = 641;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLDataRangeAtom node) {
        hashCode = 643;
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLObjectPropertyAtom node) {
        hashCode = 647;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLDataPropertyAtom node) {
        hashCode = 653;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLBuiltInAtom node) {
        hashCode = 659;
        hashCode = hashCode * MULT + node.getAllArguments().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }


    public void visit(SWRLVariable node) {
        hashCode = 661;
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }


    public void visit(SWRLIndividualArgument node) {
        hashCode = 677;
        hashCode = hashCode * MULT + node.getIndividual().hashCode();
    }


    public void visit(SWRLLiteralArgument node) {
        hashCode = 683;
        hashCode = hashCode * MULT + node.getLiteral().hashCode();
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        hashCode = 797;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }


    public void visit(SWRLSameIndividualAtom node) {
        hashCode = 811;
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }

    public void visit(OWLHasKeyAxiom axiom) {
        hashCode = 821;
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + axiom.getPropertyExpressions().hashCode();
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        hashCode = 823;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        hashCode = 827;
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        hashCode = 829;
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
    }

    public void visit(OWLDataIntersectionOf node) {
        hashCode = 839;
        hashCode = hashCode * MULT + node.getOperands().hashCode();
    }

    public void visit(OWLDataUnionOf node) {
        hashCode = 853;
        hashCode = hashCode * MULT + node.getOperands().hashCode();
    }

    public void visit(OWLAnnotationProperty property) {
        hashCode = 857;
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    public void visit(OWLAnonymousIndividual individual) {
        hashCode = 859;
        hashCode = hashCode * MULT + individual.getID().hashCode();
    }

    public void visit(IRI iri) {
        hashCode = 863;
        hashCode = hashCode * MULT + iri.toURI().hashCode();
    }

    public void visit(OWLAnnotation node) {
        hashCode = 877;
        hashCode = hashCode * MULT + node.getProperty().hashCode();
        hashCode = hashCode * MULT + node.getValue().hashCode();
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        hashCode = 897;
        hashCode = hashCode * MULT + axiom.getDatatype().hashCode();
        hashCode = hashCode * MULT + axiom.getDataRange().hashCode();
    }
}
