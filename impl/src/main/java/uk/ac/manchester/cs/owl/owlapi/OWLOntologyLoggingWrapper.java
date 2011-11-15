package uk.ac.manchester.cs.owl.owlapi;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.*;

public class OWLOntologyLoggingWrapper implements OWLMutableOntology {
	private final OWLMutableOntology delegate;

	public OWLOntologyLoggingWrapper(OWLMutableOntology o) {
		this.delegate = o;
	}

	public static void main(String[] args) {
		for (Method m : OWLOntology.class.getMethods()) {
			System.out.print("public " + m.getReturnType().getSimpleName() + " "
					+ m.getName() + "(");
			int i = 0;
			for (Class c : m.getParameterTypes()) {
				if (i > 0) {
					System.out.print(",");
				}
				i++;
				System.out.print(c.getSimpleName() + " o" + i);
			}
			System.out.print(") {System.out.println(\"" + m.getName() + "\" ");
			for (i = 0; i < m.getParameterTypes().length; i++) {
				System.out.print("+ o" + (i + 1));
			}
			System.out.print(");\ndelegate." + m.getName() + "(");
			for (i = 0; i < m.getParameterTypes().length; i++) {
				if (i > 0) {
					System.out.print(",");
				}
				System.out.print(" o" + (i + 1));
			}
			System.out.print(");}");
		}
	}

	private final OWLObjectVisitorEx<String> stringer=new OWLObjectVisitorEx<String>() {
		public String visit(OWLAnnotationProperty property) {

			return "f.getOWLAnnotationProperty(IRI.create(\""+property.getIRI()+"\"))";
		}

		public String visit(OWLDatatype datatype) {
			return "f.getOWLDatatype(IRI.create(\""+datatype.getIRI()+"\"))";
		}

		public String visit(OWLNamedIndividual individual) {
			return "f.getOWLNamedIndividual(IRI.create(\""+individual.getIRI()+"\"))";
		}

		public String visit(OWLDataProperty property) {
			return "f.getOWLDataProperty(IRI.create(\""+property.getIRI()+"\"))";
		}

		public String visit(OWLObjectProperty property) {
			return "f.getOWLObjectProperty(IRI.create(\""+property.getIRI()+"\"))";
		}

		public String visit(OWLClass cls) {
			return "f.getOWLClass(IRI.create(\""+cls.getIRI()+"\"))";
		}

		public String visit(OWLSubClassOfAxiom axiom) {

			return axiom.toString();
		}

		public String visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLAsymmetricObjectPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLReflexiveObjectPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLDisjointClassesAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLDataPropertyDomainAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLObjectPropertyDomainAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLEquivalentObjectPropertiesAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLDifferentIndividualsAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLDisjointDataPropertiesAxiom axiom) {
			return axiom.toString();		}

		public String visit(OWLDisjointObjectPropertiesAxiom axiom) {
			return axiom.toString();		}

		public String visit(OWLObjectPropertyRangeAxiom axiom) {
			return axiom.toString();		}

		public String visit(OWLObjectPropertyAssertionAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLFunctionalObjectPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLSubObjectPropertyOfAxiom axiom) {
			return axiom.toString();		}

		public String visit(OWLDisjointUnionAxiom axiom) {
			return axiom.toString();		}

		public String visit(OWLDeclarationAxiom axiom) {
			return axiom.toString();		}

		public String visit(OWLAnnotationAssertionAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLSymmetricObjectPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLDataPropertyRangeAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLFunctionalDataPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLEquivalentDataPropertiesAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLClassAssertionAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLEquivalentClassesAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLDataPropertyAssertionAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLTransitiveObjectPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLSubDataPropertyOfAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLSameIndividualAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLSubPropertyChainOfAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLInverseObjectPropertiesAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLHasKeyAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLDatatypeDefinitionAxiom axiom) {
			return axiom.toString();
		}

		public String visit(SWRLRule rule) {
			return rule.toString();
		}

		public String visit(OWLSubAnnotationPropertyOfAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLAnnotationPropertyDomainAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLAnnotationPropertyRangeAxiom axiom) {
			return axiom.toString();
		}

		public String visit(OWLObjectIntersectionOf ce) {
			return ce.toString();
		}

		public String visit(OWLObjectUnionOf ce) {
			return ce.toString();
		}

		public String visit(OWLObjectComplementOf ce) {
			return ce.toString();
		}

		public String visit(OWLObjectSomeValuesFrom ce) {
			return ce.toString();
		}

		public String visit(OWLObjectAllValuesFrom ce) {
			return ce.toString();
		}

		public String visit(OWLObjectHasValue ce) {
			return ce.toString();
		}

		public String visit(OWLObjectMinCardinality ce) {
			return ce.toString();
		}

		public String visit(OWLObjectExactCardinality ce) {
			return ce.toString();
		}

		public String visit(OWLObjectMaxCardinality ce) {
			return ce.toString();
		}

		public String visit(OWLObjectHasSelf ce) {
			return ce.toString();
		}

		public String visit(OWLObjectOneOf ce) {
			return ce.toString();
		}

		public String visit(OWLDataSomeValuesFrom ce) {
			return ce.toString();
		}

		public String visit(OWLDataAllValuesFrom ce) {
			return ce.toString();
		}

		public String visit(OWLDataHasValue ce) {
			return ce.toString();
		}

		public String visit(OWLDataMinCardinality ce) {
			return ce.toString();
		}

		public String visit(OWLDataExactCardinality ce) {
			return ce.toString();
		}

		public String visit(OWLDataMaxCardinality ce) {
			return ce.toString();
		}

		public String visit(OWLDataComplementOf node) {
			return node.toString();
		}

		public String visit(OWLDataOneOf node) {
			return node.toString();
		}

		public String visit(OWLDataIntersectionOf node) {
			return node.toString();
		}

		public String visit(OWLDataUnionOf node) {
			return node.toString();
		}

		public String visit(OWLDatatypeRestriction node) {
			return node.toString();
		}

		public String visit(OWLLiteral node) {
			return node.toString();
		}

		public String visit(OWLFacetRestriction node) {
			return node.toString();
		}

		public String visit(OWLObjectInverseOf property) {
			return property.toString();
		}

		public String visit(OWLAnnotation node) {
			return node.toString();
		}

		public String visit(IRI iri) {
			return "IRI.create(\""+iri.toString()+"\")";
		}

		public String visit(OWLAnonymousIndividual individual) {
			return individual.toString();
		}

		public String visit(SWRLClassAtom node) {
			return node.toString();
		}

		public String visit(SWRLDataRangeAtom node) {
			return node.toString();
		}

		public String visit(SWRLObjectPropertyAtom node) {
			return node.toString();
		}

		public String visit(SWRLDataPropertyAtom node) {
			return node.toString();
		}

		public String visit(SWRLBuiltInAtom node) {
			return node.toString();
		}

		public String visit(SWRLVariable node) {
			return node.toString();
		}

		public String visit(SWRLIndividualArgument node) {
			return node.toString();
		}

		public String visit(SWRLLiteralArgument node) {
			return node.toString();
		}

		public String visit(SWRLSameIndividualAtom node) {
			return node.toString();
		}

		public String visit(SWRLDifferentIndividualsAtom node) {
			return node.toString();
		}

		public String visit(OWLOntology ontology) {
			return "wholeOntology";
		}
	};

	public boolean isEmpty() {

		return delegate.isEmpty();
	}

	public Set getAnnotations() {

		return delegate.getAnnotations();
	}

	public Set getSignature(boolean o1) {
		System.out.println("System.out.println(o1.getSignature(" + o1+");");
		return delegate.getSignature(o1);
	}

	public Set getSignature() {
		System.out.println("System.out.println(o1.getSignature());");
		return delegate.getSignature();
	}

	public OWLOntologyManager getOWLOntologyManager() {

		return delegate.getOWLOntologyManager();
	}

	public OWLOntologyID getOntologyID() {

		return delegate.getOntologyID();
	}

	public boolean isAnonymous() {

		return delegate.isAnonymous();
	}

	public Set getDirectImportsDocuments() {

		return delegate.getDirectImportsDocuments();
	}

	public Set getDirectImports() {

		return delegate.getDirectImports();
	}

	public Set getImports() {

		return delegate.getImports();
	}

	public Set getImportsClosure() {

		return delegate.getImportsClosure();
	}

	public Set getImportsDeclarations() {

		return delegate.getImportsDeclarations();
	}

	public Set getAxioms(OWLClass o1) {
		System.out.println("System.out.println(o1.getAxioms(" + o1.accept(stringer)+"));");
		return delegate.getAxioms(o1);
	}

	public Set getAxioms() {
		System.out.println("System.out.println(o1.getAxioms());");
		return delegate.getAxioms();
	}

	public Set getAxioms(AxiomType o1) {
		System.out.println("System.out.println(o1.getAxioms(" + o1.toString()+"));");
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(AxiomType o1, boolean o2) {
		System.out.println("System.out.println(o1.getAxioms(" + o1.toString()+","+o2+"));" );
		return delegate.getAxioms(o1, o2);
	}

	public Set getAxioms(OWLDataProperty o1) {
		System.out.println("System.out.println(o1.getAxioms(" + o1.accept(stringer)+"));");
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getAxioms(" + o1.accept(stringer)+"));");
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getAxioms(" + o1.accept(stringer)+"));");
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLAnnotationProperty o1) {
		System.out.println("System.out.println(o1.getAxioms" + o1.accept(stringer)+");");
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLDatatype o1) {
		System.out.println("System.out.println(o1.getAxioms(" + o1.accept(stringer)+"));");
		return delegate.getAxioms(o1);
	}

	public int getAxiomCount(AxiomType o1, boolean o2) {
		System.out.println("System.out.println(o1.getAxiomCount(AxiomType" + o1+"," + o2+");");
		return delegate.getAxiomCount(o1, o2);
	}

	public int getAxiomCount() {
		System.out.println("System.out.println(o1.getAxiomCount());");
		return delegate.getAxiomCount();
	}

	public int getAxiomCount(AxiomType o1) {
		System.out.println("System.out.println(o1.getAxiomCount(" + o1+"));");
		return delegate.getAxiomCount(o1);
	}

	public Set getLogicalAxioms() {
		System.out.println("System.out.println(o1.getLogicalAxioms());");
		return delegate.getLogicalAxioms();
	}

	public int getLogicalAxiomCount() {
		System.out.println("System.out.println(o1.getLogicalAxiomCount());");
		return delegate.getLogicalAxiomCount();
	}

	public Set getTBoxAxioms(boolean o1) {
		System.out.println("System.out.println(o1.getTBoxAxioms" + o1);
		return delegate.getTBoxAxioms(o1);
	}

	public Set getABoxAxioms(boolean o1) {
		System.out.println("System.out.println(o1.getABoxAxioms" + o1);
		return delegate.getABoxAxioms(o1);
	}

	public Set getRBoxAxioms(boolean o1) {
		System.out.println("System.out.println(o1.getRBoxAxioms" + o1);
		return delegate.getRBoxAxioms(o1);
	}

	public boolean containsAxiom(OWLAxiom o1) {
		System.out.println("System.out.println(o1.containsAxiom(" + o1.accept(stringer)+"));");
		return delegate.containsAxiom(o1);
	}

	public boolean containsAxiom(OWLAxiom o1, boolean o2) {
		System.out.println("System.out.println(o1.containsAxiom(" + o1.accept(stringer)+","+o2+"));");
		return delegate.containsAxiom(o1, o2);
	}

	public boolean containsAxiomIgnoreAnnotations(OWLAxiom o1, boolean o2) {
		System.out.println("System.out.println(o1.containsAxiomIgnoreAnnotations(" + o1.accept(stringer)+","+o2+"));");
		return delegate.containsAxiomIgnoreAnnotations(o1, o2);
	}

	public boolean containsAxiomIgnoreAnnotations(OWLAxiom o1) {
		System.out.println("System.out.println(o1.containsAxiomIgnoreAnnotations(" + o1.accept(stringer)+"));");
		return delegate.containsAxiomIgnoreAnnotations(o1);
	}

	public Set getAxiomsIgnoreAnnotations(OWLAxiom o1) {
		System.out.println("System.out.println(o1.getAxiomsIgnoreAnnotations(" + o1.accept(stringer)+"));");
		return delegate.getAxiomsIgnoreAnnotations(o1);
	}

	public Set getAxiomsIgnoreAnnotations(OWLAxiom o1, boolean o2) {
		System.out.println("System.out.println(o1.getAxiomsIgnoreAnnotations(" + o1.accept(stringer)+","+o2+"));");
		return delegate.getAxiomsIgnoreAnnotations(o1, o2);
	}

	public Set getGeneralClassAxioms() {
		System.out.println("System.out.println(o1.getGeneralClassAxioms());");
		return delegate.getGeneralClassAxioms();
	}

	public Set getClassesInSignature() {
		System.out.println("System.out.println(o1.getClassesInSignature());");
		return delegate.getClassesInSignature();
	}

	public Set getClassesInSignature(boolean o1) {
		System.out.println("System.out.println(o1.getClassesInSignature("+o1+"));");
		return delegate.getClassesInSignature(o1);
	}

	public Set getObjectPropertiesInSignature(boolean o1) {
		System.out.println("System.out.println(o1.getObjectPropertiesInSignature("+o1+"));");
		return delegate.getObjectPropertiesInSignature(o1);
	}

	public Set getObjectPropertiesInSignature() {
		System.out.println("System.out.println(o1.getObjectPropertiesInSignature());");
		return delegate.getObjectPropertiesInSignature();
	}

	public Set getDataPropertiesInSignature(boolean o1) {
		System.out.println("System.out.println(o1.getDataPropertiesInSignature(" + o1+"));");
		return delegate.getDataPropertiesInSignature(o1);
	}

	public Set getDataPropertiesInSignature() {
		System.out.println("System.out.println(o1.getDataPropertiesInSignature());");
		return delegate.getDataPropertiesInSignature();
	}

	public Set getIndividualsInSignature() {
		System.out.println("System.out.println(o1.getIndividualsInSignature());");
		return delegate.getIndividualsInSignature();
	}

	public Set getIndividualsInSignature(boolean o1) {
		System.out.println("System.out.println(o1.getIndividualsInSignature("+o1+"));" );
		return delegate.getIndividualsInSignature(o1);
	}

	public Set getReferencedAnonymousIndividuals() {
		System.out.println("System.out.println(o1.getReferencedAnonymousIndividuals());");
		return delegate.getReferencedAnonymousIndividuals();
	}

	public Set getDatatypesInSignature(boolean o1) {
		System.out.println("System.out.println(o1.getDatatypesInSignature("+o1+"));" );
		return delegate.getDatatypesInSignature(o1);
	}

	public Set getDatatypesInSignature() {
		System.out.println("System.out.println(o1.getDatatypesInSignature());");
		return delegate.getDatatypesInSignature();
	}

	public Set getAnnotationPropertiesInSignature() {
		System.out.println("System.out.println(o1.getAnnotationPropertiesInSignature());");
		return delegate.getAnnotationPropertiesInSignature();
	}

	public Set getReferencingAxioms(OWLEntity o1) {
		System.out.println("System.out.println(o1.getReferencingAxioms" + o1);
		return delegate.getReferencingAxioms(o1);
	}

	public Set getReferencingAxioms(OWLEntity o1, boolean o2) {
		System.out.println("System.out.println(o1.getReferencingAxioms" + o1 + o2);
		return delegate.getReferencingAxioms(o1, o2);
	}

	public Set getReferencingAxioms(OWLAnonymousIndividual o1) {
		System.out.println("System.out.println(o1.getReferencingAxioms" + o1);
		return delegate.getReferencingAxioms(o1);
	}

	public boolean containsEntityInSignature(OWLEntity o1, boolean o2) {
		System.out.println("System.out.println(o1.containsEntityInSignature());" + o1 + o2);
		return delegate.containsEntityInSignature(o1, o2);
	}

	public boolean containsEntityInSignature(OWLEntity o1) {
		System.out.println("System.out.println(o1.containsEntityInSignature("+o1+"));");
		return delegate.containsEntityInSignature(o1);
	}

	public boolean containsEntityInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.containsEntityInSignature());" + o1 + o2);
		return delegate.containsEntityInSignature(o1, o2);
	}

	public boolean containsEntityInSignature(IRI o1) {
		System.out.println("System.out.println(o1.containsEntityInSignature("+o1+"));");
		return delegate.containsEntityInSignature(o1);
	}

	public boolean isDeclared(OWLEntity o1) {
		System.out.println("System.out.println(o1.isDeclared" + o1);
		return delegate.isDeclared(o1);
	}

	public boolean isDeclared(OWLEntity o1, boolean o2) {
		System.out.println("System.out.println(o1.isDeclared" + o1 + o2);
		return delegate.isDeclared(o1, o2);
	}

	public boolean containsClassInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.containsClassInSignature());" + o1 + o2);
		return delegate.containsClassInSignature(o1, o2);
	}

	public boolean containsClassInSignature(IRI o1) {
		System.out.println("System.out.println(o1.containsClassInSignature("+o1+"));");
		return delegate.containsClassInSignature(o1);
	}

	public boolean containsObjectPropertyInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.containsObjectPropertyInSignature());" + o1 + o2);
		return delegate.containsObjectPropertyInSignature(o1, o2);
	}

	public boolean containsObjectPropertyInSignature(IRI o1) {
		System.out.println("System.out.println(o1.containsObjectPropertyInSignature("+o1.accept(stringer)+"));" );
		return delegate.containsObjectPropertyInSignature(o1);
	}

	public boolean containsDataPropertyInSignature(IRI o1) {
		System.out.println("System.out.println(o1.containsDataPropertyInSignature("+o1.accept(stringer)+"));" );
		return delegate.containsDataPropertyInSignature(o1);
	}

	public boolean containsDataPropertyInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.containsDataPropertyInSignature("+o1.accept(stringer)+","+o2+"));" );
		return delegate.containsDataPropertyInSignature(o1, o2);
	}

	public boolean containsAnnotationPropertyInSignature(IRI o1) {
		System.out.println("System.out.println(o1.containsAnnotationPropertyInSignature("+o1.accept(stringer)+"));" );
		return delegate.containsAnnotationPropertyInSignature(o1);
	}

	public boolean containsAnnotationPropertyInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.containsAnnotationPropertyInSignature());" + o1 + o2);
		return delegate.containsAnnotationPropertyInSignature(o1, o2);
	}

	public boolean containsIndividualInSignature(IRI o1) {
		System.out.println("System.out.println(o1.containsIndividualInSignature());" + o1);
		return delegate.containsIndividualInSignature(o1);
	}

	public boolean containsIndividualInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.containsIndividualInSignature());" + o1 + o2);
		return delegate.containsIndividualInSignature(o1, o2);
	}

	public boolean containsDatatypeInSignature(IRI o1) {
		System.out.println("System.out.println(o1.containsDatatypeInSignature());" + o1);
		return delegate.containsDatatypeInSignature(o1);
	}

	public boolean containsDatatypeInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.containsDatatypeInSignature());" + o1 + o2);
		return delegate.containsDatatypeInSignature(o1, o2);
	}

	public Set getEntitiesInSignature(IRI o1, boolean o2) {
		System.out.println("System.out.println(o1.getEntitiesInSignature());" + o1 + o2);
		return delegate.getEntitiesInSignature(o1, o2);
	}

	public Set getEntitiesInSignature(IRI o1) {
		System.out.println("System.out.println(o1.getEntitiesInSignature());" + o1);
		return delegate.getEntitiesInSignature(o1);
	}

	public Set getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty o1) {
		System.out.println("System.out.println(o1.getSubAnnotationPropertyOfAxioms" + o1);
		return delegate.getSubAnnotationPropertyOfAxioms(o1);
	}

	public Set getAnnotationPropertyDomainAxioms(OWLAnnotationProperty o1) {
		System.out.println("System.out.println(o1.getAnnotationPropertyDomainAxioms" + o1);
		return delegate.getAnnotationPropertyDomainAxioms(o1);
	}

	public Set getAnnotationPropertyRangeAxioms(OWLAnnotationProperty o1) {
		System.out.println("System.out.println(o1.getAnnotationPropertyRangeAxioms" + o1);
		return delegate.getAnnotationPropertyRangeAxioms(o1);
	}

	public Set getDeclarationAxioms(OWLEntity o1) {
		System.out.println("System.out.println(o1.getDeclarationAxioms(f.getOWLEntity("+o1.getEntityType()+", IRI.create(\"" + o1.getIRI()+"\"))));");
		return delegate.getDeclarationAxioms(o1);
	}

	public Set getAnnotationAssertionAxioms(OWLAnnotationSubject o1) {
		System.out.println("System.out.println(o1.getAnnotationAssertionAxioms(IRI.create(\"" + o1+"\")));");
		return delegate.getAnnotationAssertionAxioms(o1);
	}

	public Set getSubClassAxiomsForSubClass(OWLClass o1) {
		System.out.println("System.out.println(o1.getSubClassAxiomsForSubClass(" + o1.accept(stringer)+"));");
		return delegate.getSubClassAxiomsForSubClass(o1);
	}

	public Set getSubClassAxiomsForSuperClass(OWLClass o1) {
		System.out.println("System.out.println(o1.getSubClassAxiomsForSuperClass(" + o1.accept(stringer)+"));");
		return delegate.getSubClassAxiomsForSuperClass(o1);
	}

	public Set getEquivalentClassesAxioms(OWLClass o1) {
		System.out.println("System.out.println(o1.getEquivalentClassesAxioms(" + o1.accept(stringer)+"));");
		return delegate.getEquivalentClassesAxioms(o1);
	}

	public Set getDisjointClassesAxioms(OWLClass o1) {
		System.out.println("System.out.println(o1.getDisjointClassesAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDisjointClassesAxioms(o1);
	}

	public Set getDisjointUnionAxioms(OWLClass o1) {
		System.out.println("System.out.println(o1.getDisjointUnionAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDisjointUnionAxioms(o1);
	}

	public Set getHasKeyAxioms(OWLClass o1) {
		System.out.println("System.out.println(o1.getHasKeyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getHasKeyAxioms(o1);
	}

	public Set getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getObjectSubPropertyAxiomsForSubProperty(" + o1.accept(stringer)+"));");
		return delegate.getObjectSubPropertyAxiomsForSubProperty(o1);
	}

	public Set getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getObjectSubPropertyAxiomsForSuperProperty(" + o1.accept(stringer)+"));");
		return delegate.getObjectSubPropertyAxiomsForSuperProperty(o1);
	}

	public Set getObjectPropertyDomainAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getObjectPropertyDomainAxioms(" + o1.accept(stringer)+"));");
		return delegate.getObjectPropertyDomainAxioms(o1);
	}

	public Set getObjectPropertyRangeAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getObjectPropertyRangeAxioms(" + o1.accept(stringer)+"));");
		return delegate.getObjectPropertyRangeAxioms(o1);
	}

	public Set getInverseObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getInverseObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getInverseObjectPropertyAxioms(o1);
	}

	public Set getEquivalentObjectPropertiesAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getEquivalentObjectPropertiesAxioms(" + o1.accept(stringer)+"));");
		return delegate.getEquivalentObjectPropertiesAxioms(o1);
	}

	public Set getDisjointObjectPropertiesAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getDisjointObjectPropertiesAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDisjointObjectPropertiesAxioms(o1);
	}

	public Set getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getFunctionalObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getFunctionalObjectPropertyAxioms(o1);
	}

	public Set getInverseFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getInverseFunctionalObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getInverseFunctionalObjectPropertyAxioms(o1);
	}

	public Set getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getSymmetricObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getSymmetricObjectPropertyAxioms(o1);
	}

	public Set getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getAsymmetricObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getAsymmetricObjectPropertyAxioms(o1);
	}

	public Set getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getReflexiveObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getReflexiveObjectPropertyAxioms(o1);
	}

	public Set getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getIrreflexiveObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getIrreflexiveObjectPropertyAxioms(o1);
	}

	public Set getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("System.out.println(o1.getTransitiveObjectPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getTransitiveObjectPropertyAxioms(o1);
	}

	public Set getDataSubPropertyAxiomsForSubProperty(OWLDataProperty o1) {
		System.out.println("System.out.println(o1.getDataSubPropertyAxiomsForSubProperty(" + o1.accept(stringer)+"));");
		return delegate.getDataSubPropertyAxiomsForSubProperty(o1);
	}

	public Set getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression o1) {
		System.out.println("System.out.println(o1.getDataSubPropertyAxiomsForSuperProperty(" + o1.accept(stringer)+"));");
		return delegate.getDataSubPropertyAxiomsForSuperProperty(o1);
	}

	public Set getDataPropertyDomainAxioms(OWLDataProperty o1) {
		System.out.println("System.out.println(o1.getDataPropertyDomainAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDataPropertyDomainAxioms(o1);
	}

	public Set getDataPropertyRangeAxioms(OWLDataProperty o1) {
		System.out.println("System.out.println(o1.getDataPropertyRangeAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDataPropertyRangeAxioms(o1);
	}

	public Set getEquivalentDataPropertiesAxioms(OWLDataProperty o1) {
		System.out.println("System.out.println(o1.getEquivalentDataPropertiesAxioms(" + o1.accept(stringer)+"));");
		return delegate.getEquivalentDataPropertiesAxioms(o1);
	}

	public Set getDisjointDataPropertiesAxioms(OWLDataProperty o1) {
		System.out.println("System.out.println(o1.getDisjointDataPropertiesAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDisjointDataPropertiesAxioms(o1);
	}

	public Set getFunctionalDataPropertyAxioms(OWLDataPropertyExpression o1) {
		System.out.println("System.out.println(o1.getFunctionalDataPropertyAxioms(" + o1.accept(stringer)+"));");
		return delegate.getFunctionalDataPropertyAxioms(o1);
	}

	public Set getClassAssertionAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getClassAssertionAxioms(f.getOWLNamedIndividual(IRI.create(\"" + o1+"\")));");
		return delegate.getClassAssertionAxioms(o1);
	}

	public Set getClassAssertionAxioms(OWLClassExpression o1) {
		System.out.println("System.out.println(o1.getClassAssertionAxioms(" + o1.accept(stringer)+"));");
		return delegate.getClassAssertionAxioms(o1);
	}

	public Set getDataPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getDataPropertyAssertionAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDataPropertyAssertionAxioms(o1);
	}

	public Set getObjectPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getObjectPropertyAssertionAxioms(" + o1.accept(stringer)+"));");
		return delegate.getObjectPropertyAssertionAxioms(o1);
	}

	public Set getNegativeObjectPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getNegativeObjectPropertyAssertionAxioms(" + o1.accept(stringer)+"));");
		return delegate.getNegativeObjectPropertyAssertionAxioms(o1);
	}

	public Set getNegativeDataPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getNegativeDataPropertyAssertionAxioms(" + o1.accept(stringer)+"));");
		return delegate.getNegativeDataPropertyAssertionAxioms(o1);
	}

	public Set getSameIndividualAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getSameIndividualAxioms(" + o1.accept(stringer)+"));");
		return delegate.getSameIndividualAxioms(o1);
	}

	public Set getDifferentIndividualAxioms(OWLIndividual o1) {
		System.out.println("System.out.println(o1.getDifferentIndividualAxioms(" + o1.accept(stringer)+"));");
		return delegate.getDifferentIndividualAxioms(o1);
	}

	public Set getDatatypeDefinitions(OWLDatatype o1) {
		System.out.println("System.out.println(o1.getDatatypeDefinitions(" + o1.accept(stringer)+"));");
		return delegate.getDatatypeDefinitions(o1);
	}

	public Object accept(OWLObjectVisitorEx o1) {
		return delegate.accept(o1);
	}

	public void accept(OWLObjectVisitor o1) {
		delegate.accept(o1);
	}

	public Set getAnonymousIndividuals() {
		System.out.println("System.out.println(o1.getAnonymousIndividuals());");
		return delegate.getAnonymousIndividuals();
	}

	public Set getNestedClassExpressions() {
		return delegate.getNestedClassExpressions();
	}

	public boolean isTopEntity() {
		return delegate.isTopEntity();
	}

	public boolean isBottomEntity() {
		return delegate.isBottomEntity();
	}

	public int compareTo(OWLObject o1) {

		return delegate.compareTo(o1);
	}


	public List<OWLOntologyChange> applyChange(OWLOntologyChange change)
			throws OWLOntologyChangeException {

		return delegate.applyChange(change);
	}

	public List<OWLOntologyChange> applyChanges(List<OWLOntologyChange> changes)
			throws OWLOntologyChangeException {

		return delegate.applyChanges(changes);
	}
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			return true;
		}
		if(obj instanceof OWLOntologyLoggingWrapper) {
		return delegate.equals(((OWLOntologyLoggingWrapper) obj).delegate);
		}return false;
	}
	@Override
	public int hashCode() {

		return delegate.hashCode();
	}
}
