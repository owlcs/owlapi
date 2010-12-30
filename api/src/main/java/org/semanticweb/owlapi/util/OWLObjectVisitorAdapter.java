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
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Nov-2006<br><br>
 */
public class OWLObjectVisitorAdapter implements OWLObjectVisitor {
	  @SuppressWarnings("unused")
    protected void handleDefault(OWLObject owlObject) {

    }

    public void visit(OWLOntology ontology) {
        handleDefault(ontology);
    }

    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLClassAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDataPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDeclarationAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointClassesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointUnionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLHasKeyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSameIndividualAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubClassOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(SWRLRule rule) {
        handleDefault(rule);
    }

    public void visit(OWLClass desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataAllValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataExactCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataMaxCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataMinCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataSomeValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataHasValue desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectAllValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectComplementOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectExactCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectHasSelf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectHasValue desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectIntersectionOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectMaxCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectMinCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectOneOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectSomeValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectUnionOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLFacetRestriction node) {
        handleDefault(node);
    }

    public void visit(OWLLiteral node) {
        handleDefault(node);
    }

    public void visit(OWLDataComplementOf node) {
        handleDefault(node);
    }

    public void visit(OWLDataIntersectionOf node) {
        handleDefault(node);
    }

    public void visit(OWLDataOneOf node) {
        handleDefault(node);
    }

    public void visit(OWLDatatype node) {
        handleDefault(node);
    }

    public void visit(OWLDatatypeRestriction node) {
        handleDefault(node);
    }

    public void visit(OWLDataUnionOf node) {
        handleDefault(node);
    }

    public void visit(OWLDataProperty property) {
        handleDefault(property);
    }

    public void visit(OWLObjectProperty property) {
        handleDefault(property);
    }

    public void visit(OWLObjectInverseOf property) {
        handleDefault(property);
    }

    public void visit(OWLNamedIndividual individual) {
        handleDefault(individual);
    }

    public void visit(OWLAnnotationProperty property) {
        handleDefault(property);
    }

    public void visit(OWLAnnotationAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLAnonymousIndividual individual) {
        handleDefault(individual);
    }

    public void visit(IRI iri) {
        handleDefault(iri);
    }

    public void visit(OWLAnnotation node) {
        handleDefault(node);
    }

    public void visit(SWRLLiteralArgument node) {
        handleDefault(node);
    }

    public void visit(SWRLIndividualArgument node) {
        handleDefault(node);
    }

    public void visit(SWRLVariable node) {
        handleDefault(node);
    }

    public void visit(SWRLBuiltInAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLClassAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLDataRangeAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLDataPropertyAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLDifferentIndividualsAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLObjectPropertyAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLSameIndividualAtom node) {
        handleDefault(node);
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        handleDefault(axiom);
    }
}
