package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
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
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
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

abstract class AbstractEntityRegistrationManager implements OWLObjectVisitor, SWRLObjectVisitor {

	//////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Axiom Visitor stuff
	//
	//////////////////////////////////////////////////////////////////////////////////////////////
	protected void processAxiomAnnotations(OWLAxiom ax) {
		for (OWLAnnotation anno : ax.getAnnotations()) {
			anno.accept(this);
		}
	}

	public void visit(OWLSubClassOfAxiom axiom) {
		axiom.getSubClass().accept(this);
		axiom.getSuperClass().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		axiom.getSubject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getObject().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDisjointClassesAxiom axiom) {
		for (OWLClassExpression desc : axiom.getClassExpressions()) {
			desc.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDataPropertyDomainAxiom axiom) {
		axiom.getDomain().accept(this);
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		axiom.getDomain().accept(this);
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
			prop.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		axiom.getSubject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getObject().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDifferentIndividualsAxiom axiom) {
		for (OWLIndividual ind : axiom.getIndividuals()) {
			ind.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		for (OWLDataPropertyExpression prop : axiom.getProperties()) {
			prop.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
			prop.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		axiom.getRange().accept(this);
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		axiom.getSubject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getObject().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		axiom.getSubProperty().accept(this);
		axiom.getSuperProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDisjointUnionAxiom axiom) {
		axiom.getOWLClass().accept((OWLEntityVisitor) this);
		for (OWLClassExpression desc : axiom.getClassExpressions()) {
			desc.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDeclarationAxiom axiom) {
		axiom.getEntity().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDataPropertyRangeAxiom axiom) {
		axiom.getProperty().accept(this);
		axiom.getRange().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
		for (OWLDataPropertyExpression prop : axiom.getProperties()) {
			prop.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLClassAssertionAxiom axiom) {
		axiom.getClassExpression().accept(this);
		axiom.getIndividual().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLEquivalentClassesAxiom axiom) {
		for (OWLClassExpression desc : axiom.getClassExpressions()) {
			desc.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		axiom.getSubject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getObject().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLSubDataPropertyOfAxiom axiom) {
		axiom.getSubProperty().accept(this);
		axiom.getSuperProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLSameIndividualAxiom axiom) {
		for (OWLIndividual ind : axiom.getIndividuals()) {
			ind.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLSubPropertyChainOfAxiom axiom) {
		for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
			prop.accept(this);
		}
		axiom.getSuperProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
		axiom.getFirstProperty().accept(this);
		axiom.getSecondProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLHasKeyAxiom axiom) {
		axiom.getClassExpression().accept(this);
		for (OWLPropertyExpression<?, ?> prop : axiom.getPropertyExpressions()) {
			prop.accept(this);
		}
		processAxiomAnnotations(axiom);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// OWLClassExpressionVisitor
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void visit(OWLObjectIntersectionOf desc) {
		for (OWLClassExpression operand : desc.getOperands()) {
			operand.accept(this);
		}
	}

	public void visit(OWLObjectUnionOf desc) {
		for (OWLClassExpression operand : desc.getOperands()) {
			operand.accept(this);
		}
	}

	public void visit(OWLObjectComplementOf desc) {
		desc.getOperand().accept(this);
	}

	public void visit(OWLObjectSomeValuesFrom desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLObjectAllValuesFrom desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLObjectHasValue desc) {
		desc.getProperty().accept(this);
		desc.getValue().accept(this);
	}

	public void visit(OWLObjectMinCardinality desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLObjectExactCardinality desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLObjectMaxCardinality desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLObjectHasSelf desc) {
		desc.getProperty().accept(this);
	}

	public void visit(OWLObjectOneOf desc) {
		for (OWLIndividual ind : desc.getIndividuals()) {
			ind.accept(this);
		}
	}

	public void visit(OWLDataSomeValuesFrom desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLDataAllValuesFrom desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLDataHasValue desc) {
		desc.getProperty().accept(this);
		desc.getValue().accept(this);
	}

	public void visit(OWLDataMinCardinality desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLDataExactCardinality desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	public void visit(OWLDataMaxCardinality desc) {
		desc.getProperty().accept(this);
		desc.getFiller().accept(this);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Data visitor
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void visit(OWLDataComplementOf node) {
		node.getDataRange().accept(this);
	}

	public void visit(OWLDataOneOf node) {
		for (OWLLiteral val : node.getValues()) {
			val.accept(this);
		}
	}

	public void visit(OWLDataIntersectionOf node) {
		for (OWLDataRange dr : node.getOperands()) {
			dr.accept(this);
		}
	}

	public void visit(OWLDataUnionOf node) {
		for (OWLDataRange dr : node.getOperands()) {
			dr.accept(this);
		}
	}

	public void visit(OWLDatatypeRestriction node) {
		node.getDatatype().accept(this);
		for (OWLFacetRestriction facetRestriction : node.getFacetRestrictions()) {
			facetRestriction.accept(this);
		}
	}

	public void visit(OWLFacetRestriction node) {
		node.getFacetValue().accept(this);
	}

	public void visit(OWLLiteral node) {
		node.getDatatype().accept(this);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Property expression visitor
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void visit(OWLObjectInverseOf expression) {
		expression.getInverse().accept(this);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Entity  visitor
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void visit(OWLAnnotation annotation) {
		annotation.getProperty().accept(this);
		annotation.getValue().accept(this);
		for (OWLAnnotation anno : annotation.getAnnotations()) {
			anno.accept(this);
		}
	}

	public void visit(OWLAnnotationAssertionAxiom axiom) {
		axiom.getSubject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getValue().accept(this);
		processAxiomAnnotations(axiom);
	}


	@SuppressWarnings("unused")
	public void visit(IRI iri) {}

	//    public void visit(OWLAnnotationValue value) {
	//        if(value.isLiteral()) {
	//            value.asLiteral().accept(this);
	//        }
	//        else if(value.isAnonymousIndividual()) {
	//            value.asOWLAnonymousIndividual().accept(this);
	//        }
	//    }
	public void visit(OWLOntology ontology) {}

	public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
		axiom.getProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		axiom.getSubProperty().accept(this);
		axiom.getSuperProperty().accept(this);
		processAxiomAnnotations(axiom);
	}

	public void visit(OWLDatatypeDefinitionAxiom axiom) {
		axiom.getDatatype().accept(this);
		axiom.getDataRange().accept(this);
		processAxiomAnnotations(axiom);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// SWRL Object Visitor
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void visit(SWRLRule rule) {
		for (SWRLAtom atom : rule.getBody()) {
			atom.accept(this);
		}
		for (SWRLAtom atom : rule.getHead()) {
			atom.accept(this);
		}
		processAxiomAnnotations(rule);
	}

	public void visit(SWRLClassAtom node) {
		node.getArgument().accept(this);
		node.getPredicate().accept(this);
	}

	public void visit(SWRLDataRangeAtom node) {
		node.getArgument().accept(this);
		node.getPredicate().accept(this);
	}

	public void visit(SWRLObjectPropertyAtom node) {
		node.getPredicate().accept(this);
		node.getFirstArgument().accept(this);
		node.getSecondArgument().accept(this);
	}

	public void visit(SWRLDataPropertyAtom node) {
		node.getPredicate().accept(this);
		node.getFirstArgument().accept(this);
		node.getSecondArgument().accept(this);
	}

	public void visit(SWRLBuiltInAtom node) {
		for (SWRLArgument obj : node.getAllArguments()) {
			obj.accept(this);
		}
	}

	@SuppressWarnings("unused")
	public void visit(SWRLVariable node) {}

	public void visit(SWRLIndividualArgument node) {
		node.getIndividual().accept(this);
	}

	public void visit(SWRLLiteralArgument node) {
		node.getLiteral().accept(this);
	}

	public void visit(SWRLDifferentIndividualsAtom node) {
		node.getFirstArgument().accept(this);
	}

	public void visit(SWRLSameIndividualAtom node) {
		node.getSecondArgument().accept(this);
	}
}