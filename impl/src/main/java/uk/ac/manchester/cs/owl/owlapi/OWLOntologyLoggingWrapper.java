package uk.ac.manchester.cs.owl.owlapi;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLMutableOntology;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

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

	public boolean isEmpty() {
		System.out.println("isEmpty");
		return delegate.isEmpty();
	}

	public Set getAnnotations() {
		System.out.println("getAnnotations");
		return delegate.getAnnotations();
	}

	public Set getSignature(boolean o1) {
		System.out.println("getSignature" + o1);
		return delegate.getSignature(o1);
	}

	public Set getSignature() {
		System.out.println("getSignature");
		return delegate.getSignature();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		System.out.println("getOWLOntologyManager");
		return delegate.getOWLOntologyManager();
	}

	public OWLOntologyID getOntologyID() {
		System.out.println("getOntologyID");
		return delegate.getOntologyID();
	}

	public boolean isAnonymous() {
		System.out.println("isAnonymous");
		return delegate.isAnonymous();
	}

	public Set getDirectImportsDocuments() {
		System.out.println("getDirectImportsDocuments");
		return delegate.getDirectImportsDocuments();
	}

	public Set getDirectImports() {
		System.out.println("getDirectImports");
		return delegate.getDirectImports();
	}

	public Set getImports() {
		System.out.println("getImports");
		return delegate.getImports();
	}

	public Set getImportsClosure() {
		System.out.println("getImportsClosure");
		return delegate.getImportsClosure();
	}

	public Set getImportsDeclarations() {
		System.out.println("getImportsDeclarations");
		return delegate.getImportsDeclarations();
	}

	public Set getAxioms(OWLClass o1) {
		System.out.println("getAxioms" + o1);
		return delegate.getAxioms(o1);
	}

	public Set getAxioms() {
		System.out.println("getAxioms");
		return delegate.getAxioms();
	}

	public Set getAxioms(AxiomType o1) {
		System.out.println("getAxioms" + o1);
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(AxiomType o1, boolean o2) {
		System.out.println("getAxioms" + o1 + o2);
		return delegate.getAxioms(o1, o2);
	}

	public Set getAxioms(OWLDataProperty o1) {
		System.out.println("getAxioms" + o1);
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getAxioms" + o1);
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLIndividual o1) {
		System.out.println("getAxioms" + o1);
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLAnnotationProperty o1) {
		System.out.println("getAxioms" + o1);
		return delegate.getAxioms(o1);
	}

	public Set getAxioms(OWLDatatype o1) {
		System.out.println("getAxioms" + o1);
		return delegate.getAxioms(o1);
	}

	public int getAxiomCount(AxiomType o1, boolean o2) {
		System.out.println("getAxiomCount" + o1 + o2);
		return delegate.getAxiomCount(o1, o2);
	}

	public int getAxiomCount() {
		System.out.println("getAxiomCount");
		return delegate.getAxiomCount();
	}

	public int getAxiomCount(AxiomType o1) {
		System.out.println("getAxiomCount" + o1);
		return delegate.getAxiomCount(o1);
	}

	public Set getLogicalAxioms() {
		System.out.println("getLogicalAxioms");
		return delegate.getLogicalAxioms();
	}

	public int getLogicalAxiomCount() {
		System.out.println("getLogicalAxiomCount");
		return delegate.getLogicalAxiomCount();
	}

	public Set getTBoxAxioms(boolean o1) {
		System.out.println("getTBoxAxioms" + o1);
		return delegate.getTBoxAxioms(o1);
	}

	public Set getABoxAxioms(boolean o1) {
		System.out.println("getABoxAxioms" + o1);
		return delegate.getABoxAxioms(o1);
	}

	public Set getRBoxAxioms(boolean o1) {
		System.out.println("getRBoxAxioms" + o1);
		return delegate.getRBoxAxioms(o1);
	}

	public boolean containsAxiom(OWLAxiom o1) {
		System.out.println("containsAxiom" + o1);
		return delegate.containsAxiom(o1);
	}

	public boolean containsAxiom(OWLAxiom o1, boolean o2) {
		System.out.println("containsAxiom" + o1 + o2);
		return delegate.containsAxiom(o1, o2);
	}

	public boolean containsAxiomIgnoreAnnotations(OWLAxiom o1, boolean o2) {
		System.out.println("containsAxiomIgnoreAnnotations" + o1 + o2);
		return delegate.containsAxiomIgnoreAnnotations(o1, o2);
	}

	public boolean containsAxiomIgnoreAnnotations(OWLAxiom o1) {
		System.out.println("containsAxiomIgnoreAnnotations" + o1);
		return delegate.containsAxiomIgnoreAnnotations(o1);
	}

	public Set getAxiomsIgnoreAnnotations(OWLAxiom o1) {
		System.out.println("getAxiomsIgnoreAnnotations" + o1);
		return delegate.getAxiomsIgnoreAnnotations(o1);
	}

	public Set getAxiomsIgnoreAnnotations(OWLAxiom o1, boolean o2) {
		System.out.println("getAxiomsIgnoreAnnotations" + o1 + o2);
		return delegate.getAxiomsIgnoreAnnotations(o1, o2);
	}

	public Set getGeneralClassAxioms() {
		System.out.println("getGeneralClassAxioms");
		return delegate.getGeneralClassAxioms();
	}

	public Set getClassesInSignature() {
		System.out.println("getClassesInSignature");
		return delegate.getClassesInSignature();
	}

	public Set getClassesInSignature(boolean o1) {
		System.out.println("getClassesInSignature" + o1);
		return delegate.getClassesInSignature(o1);
	}

	public Set getObjectPropertiesInSignature(boolean o1) {
		System.out.println("getObjectPropertiesInSignature" + o1);
		return delegate.getObjectPropertiesInSignature(o1);
	}

	public Set getObjectPropertiesInSignature() {
		System.out.println("getObjectPropertiesInSignature");
		return delegate.getObjectPropertiesInSignature();
	}

	public Set getDataPropertiesInSignature(boolean o1) {
		System.out.println("getDataPropertiesInSignature" + o1);
		return delegate.getDataPropertiesInSignature(o1);
	}

	public Set getDataPropertiesInSignature() {
		System.out.println("getDataPropertiesInSignature");
		return delegate.getDataPropertiesInSignature();
	}

	public Set getIndividualsInSignature() {
		System.out.println("getIndividualsInSignature");
		return delegate.getIndividualsInSignature();
	}

	public Set getIndividualsInSignature(boolean o1) {
		System.out.println("getIndividualsInSignature" + o1);
		return delegate.getIndividualsInSignature(o1);
	}

	public Set getReferencedAnonymousIndividuals() {
		System.out.println("getReferencedAnonymousIndividuals");
		return delegate.getReferencedAnonymousIndividuals();
	}

	public Set getDatatypesInSignature(boolean o1) {
		System.out.println("getDatatypesInSignature" + o1);
		return delegate.getDatatypesInSignature(o1);
	}

	public Set getDatatypesInSignature() {
		System.out.println("getDatatypesInSignature");
		return delegate.getDatatypesInSignature();
	}

	public Set getAnnotationPropertiesInSignature() {
		System.out.println("getAnnotationPropertiesInSignature");
		return delegate.getAnnotationPropertiesInSignature();
	}

	public Set getReferencingAxioms(OWLEntity o1) {
		System.out.println("getReferencingAxioms" + o1);
		return delegate.getReferencingAxioms(o1);
	}

	public Set getReferencingAxioms(OWLEntity o1, boolean o2) {
		System.out.println("getReferencingAxioms" + o1 + o2);
		return delegate.getReferencingAxioms(o1, o2);
	}

	public Set getReferencingAxioms(OWLAnonymousIndividual o1) {
		System.out.println("getReferencingAxioms" + o1);
		return delegate.getReferencingAxioms(o1);
	}

	public boolean containsEntityInSignature(OWLEntity o1, boolean o2) {
		System.out.println("containsEntityInSignature" + o1 + o2);
		return delegate.containsEntityInSignature(o1, o2);
	}

	public boolean containsEntityInSignature(OWLEntity o1) {
		System.out.println("containsEntityInSignature" + o1);
		return delegate.containsEntityInSignature(o1);
	}

	public boolean containsEntityInSignature(IRI o1, boolean o2) {
		System.out.println("containsEntityInSignature" + o1 + o2);
		return delegate.containsEntityInSignature(o1, o2);
	}

	public boolean containsEntityInSignature(IRI o1) {
		System.out.println("containsEntityInSignature" + o1);
		return delegate.containsEntityInSignature(o1);
	}

	public boolean isDeclared(OWLEntity o1) {
		System.out.println("isDeclared" + o1);
		return delegate.isDeclared(o1);
	}

	public boolean isDeclared(OWLEntity o1, boolean o2) {
		System.out.println("isDeclared" + o1 + o2);
		return delegate.isDeclared(o1, o2);
	}

	public boolean containsClassInSignature(IRI o1, boolean o2) {
		System.out.println("containsClassInSignature" + o1 + o2);
		return delegate.containsClassInSignature(o1, o2);
	}

	public boolean containsClassInSignature(IRI o1) {
		System.out.println("containsClassInSignature" + o1);
		return delegate.containsClassInSignature(o1);
	}

	public boolean containsObjectPropertyInSignature(IRI o1, boolean o2) {
		System.out.println("containsObjectPropertyInSignature" + o1 + o2);
		return delegate.containsObjectPropertyInSignature(o1, o2);
	}

	public boolean containsObjectPropertyInSignature(IRI o1) {
		System.out.println("containsObjectPropertyInSignature" + o1);
		return delegate.containsObjectPropertyInSignature(o1);
	}

	public boolean containsDataPropertyInSignature(IRI o1) {
		System.out.println("containsDataPropertyInSignature" + o1);
		return delegate.containsDataPropertyInSignature(o1);
	}

	public boolean containsDataPropertyInSignature(IRI o1, boolean o2) {
		System.out.println("containsDataPropertyInSignature" + o1 + o2);
		return delegate.containsDataPropertyInSignature(o1, o2);
	}

	public boolean containsAnnotationPropertyInSignature(IRI o1) {
		System.out.println("containsAnnotationPropertyInSignature" + o1);
		return delegate.containsAnnotationPropertyInSignature(o1);
	}

	public boolean containsAnnotationPropertyInSignature(IRI o1, boolean o2) {
		System.out.println("containsAnnotationPropertyInSignature" + o1 + o2);
		return delegate.containsAnnotationPropertyInSignature(o1, o2);
	}

	public boolean containsIndividualInSignature(IRI o1) {
		System.out.println("containsIndividualInSignature" + o1);
		return delegate.containsIndividualInSignature(o1);
	}

	public boolean containsIndividualInSignature(IRI o1, boolean o2) {
		System.out.println("containsIndividualInSignature" + o1 + o2);
		return delegate.containsIndividualInSignature(o1, o2);
	}

	public boolean containsDatatypeInSignature(IRI o1) {
		System.out.println("containsDatatypeInSignature" + o1);
		return delegate.containsDatatypeInSignature(o1);
	}

	public boolean containsDatatypeInSignature(IRI o1, boolean o2) {
		System.out.println("containsDatatypeInSignature" + o1 + o2);
		return delegate.containsDatatypeInSignature(o1, o2);
	}

	public Set getEntitiesInSignature(IRI o1, boolean o2) {
		System.out.println("getEntitiesInSignature" + o1 + o2);
		return delegate.getEntitiesInSignature(o1, o2);
	}

	public Set getEntitiesInSignature(IRI o1) {
		System.out.println("getEntitiesInSignature" + o1);
		return delegate.getEntitiesInSignature(o1);
	}

	public Set getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty o1) {
		System.out.println("getSubAnnotationPropertyOfAxioms" + o1);
		return delegate.getSubAnnotationPropertyOfAxioms(o1);
	}

	public Set getAnnotationPropertyDomainAxioms(OWLAnnotationProperty o1) {
		System.out.println("getAnnotationPropertyDomainAxioms" + o1);
		return delegate.getAnnotationPropertyDomainAxioms(o1);
	}

	public Set getAnnotationPropertyRangeAxioms(OWLAnnotationProperty o1) {
		System.out.println("getAnnotationPropertyRangeAxioms" + o1);
		return delegate.getAnnotationPropertyRangeAxioms(o1);
	}

	public Set getDeclarationAxioms(OWLEntity o1) {
		System.out.println("getDeclarationAxioms" + o1);
		return delegate.getDeclarationAxioms(o1);
	}

	public Set getAnnotationAssertionAxioms(OWLAnnotationSubject o1) {
		System.out.println("getAnnotationAssertionAxioms" + o1);
		return delegate.getAnnotationAssertionAxioms(o1);
	}

	public Set getSubClassAxiomsForSubClass(OWLClass o1) {
		System.out.println("getSubClassAxiomsForSubClass" + o1);
		return delegate.getSubClassAxiomsForSubClass(o1);
	}

	public Set getSubClassAxiomsForSuperClass(OWLClass o1) {
		System.out.println("getSubClassAxiomsForSuperClass" + o1);
		return delegate.getSubClassAxiomsForSuperClass(o1);
	}

	public Set getEquivalentClassesAxioms(OWLClass o1) {
		System.out.println("getEquivalentClassesAxioms" + o1);
		return delegate.getEquivalentClassesAxioms(o1);
	}

	public Set getDisjointClassesAxioms(OWLClass o1) {
		System.out.println("getDisjointClassesAxioms" + o1);
		return delegate.getDisjointClassesAxioms(o1);
	}

	public Set getDisjointUnionAxioms(OWLClass o1) {
		System.out.println("getDisjointUnionAxioms" + o1);
		return delegate.getDisjointUnionAxioms(o1);
	}

	public Set getHasKeyAxioms(OWLClass o1) {
		System.out.println("getHasKeyAxioms" + o1);
		return delegate.getHasKeyAxioms(o1);
	}

	public Set getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression o1) {
		System.out.println("getObjectSubPropertyAxiomsForSubProperty" + o1);
		return delegate.getObjectSubPropertyAxiomsForSubProperty(o1);
	}

	public Set getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression o1) {
		System.out.println("getObjectSubPropertyAxiomsForSuperProperty" + o1);
		return delegate.getObjectSubPropertyAxiomsForSuperProperty(o1);
	}

	public Set getObjectPropertyDomainAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getObjectPropertyDomainAxioms" + o1);
		return delegate.getObjectPropertyDomainAxioms(o1);
	}

	public Set getObjectPropertyRangeAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getObjectPropertyRangeAxioms" + o1);
		return delegate.getObjectPropertyRangeAxioms(o1);
	}

	public Set getInverseObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getInverseObjectPropertyAxioms" + o1);
		return delegate.getInverseObjectPropertyAxioms(o1);
	}

	public Set getEquivalentObjectPropertiesAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getEquivalentObjectPropertiesAxioms" + o1);
		return delegate.getEquivalentObjectPropertiesAxioms(o1);
	}

	public Set getDisjointObjectPropertiesAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getDisjointObjectPropertiesAxioms" + o1);
		return delegate.getDisjointObjectPropertiesAxioms(o1);
	}

	public Set getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getFunctionalObjectPropertyAxioms" + o1);
		return delegate.getFunctionalObjectPropertyAxioms(o1);
	}

	public Set getInverseFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getInverseFunctionalObjectPropertyAxioms" + o1);
		return delegate.getInverseFunctionalObjectPropertyAxioms(o1);
	}

	public Set getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getSymmetricObjectPropertyAxioms" + o1);
		return delegate.getSymmetricObjectPropertyAxioms(o1);
	}

	public Set getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getAsymmetricObjectPropertyAxioms" + o1);
		return delegate.getAsymmetricObjectPropertyAxioms(o1);
	}

	public Set getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getReflexiveObjectPropertyAxioms" + o1);
		return delegate.getReflexiveObjectPropertyAxioms(o1);
	}

	public Set getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getIrreflexiveObjectPropertyAxioms" + o1);
		return delegate.getIrreflexiveObjectPropertyAxioms(o1);
	}

	public Set getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression o1) {
		System.out.println("getTransitiveObjectPropertyAxioms" + o1);
		return delegate.getTransitiveObjectPropertyAxioms(o1);
	}

	public Set getDataSubPropertyAxiomsForSubProperty(OWLDataProperty o1) {
		System.out.println("getDataSubPropertyAxiomsForSubProperty" + o1);
		return delegate.getDataSubPropertyAxiomsForSubProperty(o1);
	}

	public Set getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression o1) {
		System.out.println("getDataSubPropertyAxiomsForSuperProperty" + o1);
		return delegate.getDataSubPropertyAxiomsForSuperProperty(o1);
	}

	public Set getDataPropertyDomainAxioms(OWLDataProperty o1) {
		System.out.println("getDataPropertyDomainAxioms" + o1);
		return delegate.getDataPropertyDomainAxioms(o1);
	}

	public Set getDataPropertyRangeAxioms(OWLDataProperty o1) {
		System.out.println("getDataPropertyRangeAxioms" + o1);
		return delegate.getDataPropertyRangeAxioms(o1);
	}

	public Set getEquivalentDataPropertiesAxioms(OWLDataProperty o1) {
		System.out.println("getEquivalentDataPropertiesAxioms" + o1);
		return delegate.getEquivalentDataPropertiesAxioms(o1);
	}

	public Set getDisjointDataPropertiesAxioms(OWLDataProperty o1) {
		System.out.println("getDisjointDataPropertiesAxioms" + o1);
		return delegate.getDisjointDataPropertiesAxioms(o1);
	}

	public Set getFunctionalDataPropertyAxioms(OWLDataPropertyExpression o1) {
		System.out.println("getFunctionalDataPropertyAxioms" + o1);
		return delegate.getFunctionalDataPropertyAxioms(o1);
	}

	public Set getClassAssertionAxioms(OWLIndividual o1) {
		System.out.println("getClassAssertionAxioms" + o1);
		return delegate.getClassAssertionAxioms(o1);
	}

	public Set getClassAssertionAxioms(OWLClassExpression o1) {
		System.out.println("getClassAssertionAxioms" + o1);
		return delegate.getClassAssertionAxioms(o1);
	}

	public Set getDataPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("getDataPropertyAssertionAxioms" + o1);
		return delegate.getDataPropertyAssertionAxioms(o1);
	}

	public Set getObjectPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("getObjectPropertyAssertionAxioms" + o1);
		return delegate.getObjectPropertyAssertionAxioms(o1);
	}

	public Set getNegativeObjectPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("getNegativeObjectPropertyAssertionAxioms" + o1);
		return delegate.getNegativeObjectPropertyAssertionAxioms(o1);
	}

	public Set getNegativeDataPropertyAssertionAxioms(OWLIndividual o1) {
		System.out.println("getNegativeDataPropertyAssertionAxioms" + o1);
		return delegate.getNegativeDataPropertyAssertionAxioms(o1);
	}

	public Set getSameIndividualAxioms(OWLIndividual o1) {
		System.out.println("getSameIndividualAxioms" + o1);
		return delegate.getSameIndividualAxioms(o1);
	}

	public Set getDifferentIndividualAxioms(OWLIndividual o1) {
		System.out.println("getDifferentIndividualAxioms" + o1);
		return delegate.getDifferentIndividualAxioms(o1);
	}

	public Set getDatatypeDefinitions(OWLDatatype o1) {
		System.out.println("getDatatypeDefinitions" + o1);
		return delegate.getDatatypeDefinitions(o1);
	}

	public Object accept(OWLObjectVisitorEx o1) {
		System.out.println("accept" + o1);
		return delegate.accept(o1);
	}

	public void accept(OWLObjectVisitor o1) {
		System.out.println("accept" + o1);
		delegate.accept(o1);
	}

	public Set getAnonymousIndividuals() {
		System.out.println("getAnonymousIndividuals");
		return delegate.getAnonymousIndividuals();
	}

	public Set getNestedClassExpressions() {
		System.out.println("getNestedClassExpressions");
		return delegate.getNestedClassExpressions();
	}

	public boolean isTopEntity() {
		System.out.println("isTopEntity");
		return delegate.isTopEntity();
	}

	public boolean isBottomEntity() {
		System.out.println("isBottomEntity");
		return delegate.isBottomEntity();
	}

	public int compareTo(OWLObject o1) {
		System.out.println("compareTo" + o1);
		return delegate.compareTo(o1);
	}


	public List<OWLOntologyChange> applyChange(OWLOntologyChange change)
			throws OWLOntologyChangeException {
		System.out.println("applyChange" + change);
		return delegate.applyChange(change);
	}

	public List<OWLOntologyChange> applyChanges(List<OWLOntologyChange> changes)
			throws OWLOntologyChangeException {
		System.out.println("applyChanges" + changes);
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
