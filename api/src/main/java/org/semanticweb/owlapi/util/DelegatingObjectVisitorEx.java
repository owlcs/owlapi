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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 29-Jul-2008<br><br>
 * <p/>
 * A delegating visitor.  This utility class can be used to override visiting
 * a particular type of object.
 */
public class DelegatingObjectVisitorEx<O> implements OWLObjectVisitorEx<O> {

    private OWLObjectVisitorEx<O> delegate;


    /**
     * Constructs a visitor where the specified delegate will be used to
     * visit all objects unless one of the visit methods in this visitor
     * is overriden.
     * @param delegate The delegate.
     */
    public DelegatingObjectVisitorEx(OWLObjectVisitorEx<O> delegate) {
        this.delegate = delegate;
    }


    public O visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLClassAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDataPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDataPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDataPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSubDataPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDeclarationAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDifferentIndividualsAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointClassesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointDataPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLDisjointUnionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLAnnotationAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLEquivalentClassesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLFunctionalDataPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLInverseObjectPropertiesAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyAssertionAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSubPropertyChainOfAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLObjectPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSubObjectPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSameIndividualAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSubClassOfAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return delegate.visit(axiom);
    }


    public O visit(SWRLRule rule) {
        return delegate.visit(rule);
    }


    public O visit(OWLClass desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataAllValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataExactCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataMaxCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataMinCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataSomeValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataHasValue desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectAllValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectComplementOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectExactCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectIntersectionOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectMaxCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectMinCardinality desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectOneOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectHasSelf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectSomeValuesFrom desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectUnionOf desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLObjectHasValue desc) {
        return delegate.visit(desc);
    }


    public O visit(OWLDataComplementOf node) {
        return delegate.visit(node);
    }


    public O visit(OWLDataOneOf node) {
        return delegate.visit(node);
    }


    public O visit(OWLFacetRestriction node) {
        return delegate.visit(node);
    }


    public O visit(OWLDatatypeRestriction node) {
        return delegate.visit(node);
    }


    public O visit(OWLDatatype node) {
        return delegate.visit(node);
    }


    public O visit(OWLLiteral node) {
        return delegate.visit(node);
    }

    public O visit(OWLDataProperty property) {
        return delegate.visit(property);
    }


    public O visit(OWLObjectProperty property) {
        return delegate.visit(property);
    }


    public O visit(OWLObjectInverseOf property) {
        return delegate.visit(property);
    }


    public O visit(SWRLLiteralArgument node) {
        return delegate.visit(node);
    }


    public O visit(SWRLVariable node) {
        return delegate.visit(node);
    }


    public O visit(SWRLIndividualArgument node) {
        return delegate.visit(node);
    }

    public O visit(SWRLBuiltInAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLClassAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLDataRangeAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLDataPropertyAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLDifferentIndividualsAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLObjectPropertyAtom node) {
        return delegate.visit(node);
    }


    public O visit(SWRLSameIndividualAtom node) {
        return delegate.visit(node);
    }


    public O visit(OWLOntology ontology) {
        return delegate.visit(ontology);
    }

    public O visit(OWLAnnotation annotation) {
        return delegate.visit(annotation);
    }

    public O visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLAnnotationProperty property) {
        return delegate.visit(property);
    }

    public O visit(OWLHasKeyAxiom axiom) {
        return delegate.visit(axiom);
    }

    public O visit(OWLDataIntersectionOf node) {
        return delegate.visit(node);
    }

    public O visit(OWLDataUnionOf node) {
        return delegate.visit(node);
    }

    public O visit(OWLNamedIndividual individual) {
        return delegate.visit(individual);
    }

    public O visit(OWLAnonymousIndividual individual) {
        return delegate.visit(individual);
    }

    public O visit(IRI iri) {
        return delegate.visit(iri);
    }


    public O visit(OWLDatatypeDefinitionAxiom axiom) {
        return delegate.visit(axiom);
    }
}
