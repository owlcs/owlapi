package org.semanticweb.owlapi.util;

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
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
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
import org.semanticweb.owlapi.model.SWRLDArgument;
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
 * Date: 04-Feb-2008<br><br>
 */
public class OWLObjectComponentCollector implements OWLObjectVisitor {

    private Set<OWLObject> result;


    public OWLObjectComponentCollector() {
        result = new HashSet<OWLObject>();
    }


    /**
     * A convenience method that obtains the components of an
     * OWL object. Note that by definition, the components of the
     * object include the object itself.
     *
     * @param object The object whose components are to be obtained.
     * @return The component of the specified object.
     */
    public Set<OWLObject> getComponents(OWLObject object) {
        result.clear();
        object.accept(this);
        return new HashSet<OWLObject>(result);
    }

    public Set<OWLObject> getResult() {
        return CollectionFactory.getCopyOnRequestSet(result);
    }

    private void process(Set<? extends OWLObject> objects) {
        for (OWLObject obj : objects) {
            obj.accept(this);
        }
    }


    /**
     * Handles an object.  By default, this method adds the object
     * to the result collection.  This method may be overriden to
     * do something else.
     *
     * @param obj The object being added.
     */
    protected void handleObject(OWLObject obj) {
        result.add(obj);
    }

    public void visit(OWLOntology ontology) {
        process(ontology.getAxioms());
    }


    public void visit(OWLClass cls) {
        handleObject(cls);
        cls.getIRI().accept(this);
    }


    public void visit(OWLObjectProperty property) {
        handleObject(property);
        property.getIRI().accept(this);
    }


    public void visit(OWLObjectInverseOf property) {
        handleObject(property);
        property.getInverse().accept(this);
    }


    public void visit(OWLDataProperty property) {
        handleObject(property);
        property.getIRI().accept(this);
    }


    public void visit(OWLDatatype datatype) {
        handleObject(datatype);
        datatype.getIRI().accept(this);
    }


    public void visit(OWLObjectIntersectionOf desc) {
        handleObject(desc);
        for (OWLClassExpression op : desc.getOperands()) {
            op.accept(this);
        }
    }


    public void visit(OWLObjectUnionOf desc) {
        handleObject(desc);
        process(desc.getOperands());
    }


    public void visit(OWLObjectComplementOf desc) {
        handleObject(desc);
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectHasValue desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
    }


    public void visit(OWLObjectMinCardinality desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectExactCardinality desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectMaxCardinality desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectHasSelf desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLObjectOneOf desc) {
        handleObject(desc);
        process(desc.getIndividuals());
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataAllValuesFrom desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataHasValue desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMinCardinality desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataExactCardinality desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMaxCardinality desc) {
        handleObject(desc);
        desc.getProperty().accept(this);
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getClassExpressions());
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        handleObject(axiom);
        process(axiom.getIndividuals());
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        handleObject(axiom);
        axiom.getOWLClass().accept(this);
        process(axiom.getClassExpressions());
    }


    public void visit(OWLDeclarationAxiom axiom) {
        handleObject(axiom);
        axiom.getEntity().accept(this);
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getClassExpression().accept(this);
        axiom.getIndividual().accept(this);
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getClassExpressions());
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        handleObject(axiom);
        axiom.getSubject().accept(this);
        axiom.getProperty().accept(this);
        axiom.getObject().accept(this);
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        handleObject(axiom);
        process(axiom.getIndividuals());
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        handleObject(axiom);
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        handleObject(axiom);
        process(axiom.getProperties());
    }


    public void visit(SWRLRule node) {
        handleObject(node);
        process(node.getBody());
        process(node.getHead());
    }


    public void visit(OWLDataComplementOf node) {
        handleObject(node);
        node.getDataRange().accept(this);
    }


    public void visit(OWLDataOneOf node) {
        handleObject(node);
        process(node.getValues());
    }


    public void visit(OWLDatatypeRestriction node) {
        handleObject(node);
        node.getDatatype().accept(this);
        process(node.getFacetRestrictions());
    }

    public void visit(OWLLiteral node) {
        handleObject(node);
        node.getDatatype().accept(this);
    }

    public void visit(OWLFacetRestriction node) {
        handleObject(node);
        node.getFacetValue().accept(this);
    }

    public void visit(OWLHasKeyAxiom axiom) {
        handleObject(axiom);
        axiom.getClassExpression().accept(this);
        for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions()) {
            prop.accept(this);
        }
        for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
            prop.accept(this);
        }
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        handleObject(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        handleObject(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    public void visit(OWLDataIntersectionOf node) {
        handleObject(node);
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
        }
    }

    public void visit(OWLDataUnionOf node) {
        handleObject(node);
        for (OWLDataRange rng : node.getOperands()) {
            rng.accept(this);
        }
    }

    public void visit(OWLNamedIndividual individual) {
        handleObject(individual);
        individual.getIRI().accept(this);
    }

    public void visit(OWLAnnotationProperty property) {
        handleObject(property);
        property.getIRI().accept(this);
    }

    public void visit(OWLAnonymousIndividual individual) {
        handleObject(individual);
    }

    public void visit(IRI iri) {
        handleObject(iri);
    }

    public void visit(OWLAnnotation node) {
    }

    public void visit(SWRLClassAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
    }


    public void visit(SWRLDataRangeAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
    }


    public void visit(SWRLObjectPropertyAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLDataPropertyAtom node) {
        handleObject(node);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLBuiltInAtom node) {
        handleObject(node);
        for (SWRLDArgument obj : node.getArguments()) {
            obj.accept(this);
        }
    }


    public void visit(SWRLVariable node) {
        handleObject(node);
    }


    public void visit(SWRLIndividualArgument node) {
        handleObject(node);
        node.getIndividual().accept(this);
    }


    public void visit(SWRLLiteralArgument node) {
        handleObject(node);
        node.getLiteral().accept(this);
    }


    public void visit(SWRLSameIndividualAtom node) {
        handleObject(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        handleObject(node);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        handleObject(axiom);
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
    }
}
