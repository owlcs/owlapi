package uk.ac.manchester.cs.owl.owlapi;

/*
 * Copyright (C) 2010, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
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

public interface Internals {
	Set<OWLImportsDeclaration> getImportsDeclarations();

	/**
	 * @param importDeclaration
	 *            declaration to be added
	 * @return true if the import declaration was not already present, false
	 *         otherwise
	 */
	boolean addImportsDeclaration(OWLImportsDeclaration importDeclaration);

	/**
	 * @param importDeclaration
	 *            declaration to be added
	 * @return true if the import declaration was present, false otherwise
	 */
	boolean removeImportsDeclaration(OWLImportsDeclaration importDeclaration);

	boolean isEmpty();

	Set<OWLAnnotation> getOntologyAnnotations();

	boolean addOntologyAnnotation(OWLAnnotation ann);

	boolean removeOntologyAnnotation(OWLAnnotation ann);

	public void addAxiomsByType(AxiomType<?> type, OWLAxiom axiom);

	public void removeAxiomsByType(AxiomType<?> type, OWLAxiom axiom);

	Map<OWLAxiom, Set<OWLAxiom>> getLogicalAxiom2AnnotatedAxiomMap();

	Set<OWLAxiom> getLogicalAxiom2AnnotatedAxiom(OWLAxiom ax);

	void addLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax);

	void removeLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax);

	boolean containsLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax);

	Set<OWLClassAxiom> getGeneralClassAxioms();

	void addGeneralClassAxioms(OWLClassAxiom ax);

	void removeGeneralClassAxioms(OWLClassAxiom ax);

	Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxioms();

	void addPropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax);

	void removePropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax);

	Map<OWLClass, Set<OWLAxiom>> getOwlClassReferences();

	void removeOwlClassReferences(OWLClass c, OWLAxiom ax);

	void addOwlClassReferences(OWLClass c, OWLAxiom ax);

	boolean containsOwlClassReferences(OWLClass c);

	Map<OWLObjectProperty, Set<OWLAxiom>> getOwlObjectPropertyReferences();

	void addOwlObjectPropertyReferences(OWLObjectProperty p, OWLAxiom ax);

	void removeOwlObjectPropertyReferences(OWLObjectProperty p, OWLAxiom ax);

	boolean containsOwlObjectPropertyReferences(OWLObjectProperty c);

	Map<OWLDataProperty, Set<OWLAxiom>> getOwlDataPropertyReferences();

	void removeOwlDataPropertyReferences(OWLDataProperty c, OWLAxiom ax);

	void addOwlDataPropertyReferences(OWLDataProperty c, OWLAxiom ax);

	boolean containsOwlDataPropertyReferences(OWLDataProperty c);

	Map<OWLNamedIndividual, Set<OWLAxiom>> getOwlIndividualReferences();

	void removeOwlIndividualReferences(OWLNamedIndividual c, OWLAxiom ax);

	void addOwlIndividualReferences(OWLNamedIndividual c, OWLAxiom ax);

	boolean containsOwlIndividualReferences(OWLNamedIndividual c);

	Map<OWLAnonymousIndividual, Set<OWLAxiom>> getOwlAnonymousIndividualReferences();

	void removeOwlAnonymousIndividualReferences(OWLAnonymousIndividual c,
			OWLAxiom ax);

	void addOwlAnonymousIndividualReferences(OWLAnonymousIndividual c,
			OWLAxiom ax);

	boolean containsOwlAnonymousIndividualReferences(OWLAnonymousIndividual c);

	Map<OWLDatatype, Set<OWLAxiom>> getOwlDatatypeReferences();

	void removeOwlDatatypeReferences(OWLDatatype c, OWLAxiom ax);

	void addOwlDatatypeReferences(OWLDatatype c, OWLAxiom ax);

	boolean containsOwlDatatypeReferences(OWLDatatype c);

	Map<OWLAnnotationProperty, Set<OWLAxiom>> getOwlAnnotationPropertyReferences();

	void removeOwlAnnotationPropertyReferences(OWLAnnotationProperty c,
			OWLAxiom ax);

	void addOwlAnnotationPropertyReferences(OWLAnnotationProperty c, OWLAxiom ax);

	boolean containsOwlAnnotationPropertyReferences(OWLAnnotationProperty c);

	Map<OWLEntity, Set<OWLDeclarationAxiom>> getDeclarationsByEntity();

	void removeDeclarationsByEntity(OWLEntity c, OWLDeclarationAxiom ax);

	void addDeclarationsByEntity(OWLEntity c, OWLDeclarationAxiom ax);

	boolean containsDeclarationsByEntity(OWLEntity c);

	Map<OWLClass, Set<OWLClassAxiom>> getClassAxiomsByClass();

	Map<OWLClass, Set<OWLSubClassOfAxiom>> getSubClassAxiomsByLHS();

	Map<OWLClass, Set<OWLSubClassOfAxiom>> getSubClassAxiomsByRHS();

	Map<OWLClass, Set<OWLEquivalentClassesAxiom>> getEquivalentClassesAxiomsByClass();

	Map<OWLClass, Set<OWLDisjointClassesAxiom>> getDisjointClassesAxiomsByClass();

	Map<OWLClass, Set<OWLDisjointUnionAxiom>> getDisjointUnionAxiomsByClass();

	Map<OWLClass, Set<OWLHasKeyAxiom>> getHasKeyAxiomsByClass();

	Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> getObjectSubPropertyAxiomsByLHS();

	Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> getObjectSubPropertyAxiomsByRHS();

	Map<OWLObjectPropertyExpression, Set<OWLEquivalentObjectPropertiesAxiom>> getEquivalentObjectPropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLDisjointObjectPropertiesAxiom>> getDisjointObjectPropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyDomainAxiom>> getObjectPropertyDomainAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyRangeAxiom>> getObjectPropertyRangeAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLFunctionalObjectPropertyAxiom>> getFunctionalObjectPropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLInverseFunctionalObjectPropertyAxiom>> getInverseFunctionalPropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLSymmetricObjectPropertyAxiom>> getSymmetricPropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLAsymmetricObjectPropertyAxiom>> getAsymmetricPropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLReflexiveObjectPropertyAxiom>> getReflexivePropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLIrreflexiveObjectPropertyAxiom>> getIrreflexivePropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLTransitiveObjectPropertyAxiom>> getTransitivePropertyAxiomsByProperty();

	Map<OWLObjectPropertyExpression, Set<OWLInverseObjectPropertiesAxiom>> getInversePropertyAxiomsByProperty();

	Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> getDataSubPropertyAxiomsByLHS();

	Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> getDataSubPropertyAxiomsByRHS();

	Map<OWLDataPropertyExpression, Set<OWLEquivalentDataPropertiesAxiom>> getEquivalentDataPropertyAxiomsByProperty();

	Map<OWLDataPropertyExpression, Set<OWLDisjointDataPropertiesAxiom>> getDisjointDataPropertyAxiomsByProperty();

	Map<OWLDataPropertyExpression, Set<OWLDataPropertyDomainAxiom>> getDataPropertyDomainAxiomsByProperty();

	Map<OWLDataPropertyExpression, Set<OWLDataPropertyRangeAxiom>> getDataPropertyRangeAxiomsByProperty();

	Map<OWLDataPropertyExpression, Set<OWLFunctionalDataPropertyAxiom>> getFunctionalDataPropertyAxiomsByProperty();

	Map<OWLIndividual, Set<OWLClassAssertionAxiom>> getClassAssertionAxiomsByIndividual();

	Map<OWLClass, Set<OWLClassAssertionAxiom>> getClassAssertionAxiomsByClass();

	Map<OWLIndividual, Set<OWLObjectPropertyAssertionAxiom>> getObjectPropertyAssertionsByIndividual();

	Map<OWLIndividual, Set<OWLDataPropertyAssertionAxiom>> getDataPropertyAssertionsByIndividual();

	Map<OWLIndividual, Set<OWLNegativeObjectPropertyAssertionAxiom>> getNegativeObjectPropertyAssertionAxiomsByIndividual();

	Map<OWLIndividual, Set<OWLNegativeDataPropertyAssertionAxiom>> getNegativeDataPropertyAssertionAxiomsByIndividual();

	Map<OWLIndividual, Set<OWLDifferentIndividualsAxiom>> getDifferentIndividualsAxiomsByIndividual();

	Map<OWLIndividual, Set<OWLSameIndividualAxiom>> getSameIndividualsAxiomsByIndividual();

	Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> getAnnotationAssertionAxiomsBySubject();

	boolean isDeclared(OWLDeclarationAxiom ax);

	Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype datatype);

	Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(
			OWLAnnotationProperty subProperty);

	Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(
			OWLAnnotationProperty property);

	Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(
			OWLAnnotationProperty property);

	/**
	 * A convenience method that adds an axiom to a set, but checks that the set
	 * isn't null before the axiom is added. This is needed because many of the
	 * indexing sets are built lazily.
	 * 
	 * @param axiom
	 *            The axiom to be added.
	 * @param axioms
	 *            The set of axioms that the axiom should be added to. May be
	 *            <code>null</code>.
	 */
	<K extends OWLAxiom> void addAxiomToSet(K axiom, Set<K> axioms);

	<K extends OWLAxiom> void removeAxiomFromSet(K axiom, Set<K> axioms);

	/**
	 * Adds an axiom to a set contained in a map, which maps some key (e.g. an
	 * entity such as and individual, class etc.) to the set of axioms.
	 * 
	 * @param key
	 *            The key that indexes the set of axioms
	 * @param map
	 *            The map, which maps the key to a set of axioms, to which the
	 *            axiom will be added.
	 * @param axiom
	 *            The axiom to be added
	 */
	<K, V extends OWLAxiom> void addToIndexedSet(K key, Map<K, Set<V>> map,
			V axiom);

	/**
	 * Removes an axiom from a set of axioms, which is the value for a specified
	 * key in a specified map.
	 * 
	 * @param key
	 *            The key that indexes the set of axioms.
	 * @param map
	 *            The map, which maps keys to sets of axioms.
	 * @param axiom
	 *            The axiom to remove from the set of axioms.
	 * @param removeSetIfEmpty
	 *            Specifies whether or not the indexed set should be removed
	 *            from the map if it is empty after removing the specified axiom
	 */
	<K, V extends OWLAxiom> void removeAxiomFromSet(K key, Map<K, Set<V>> map,
			V axiom, boolean removeSetIfEmpty);

	<E> Set<E> getReturnSet(Set<E> set);

	<K extends OWLObject, V extends OWLAxiom> Set<V> getAxioms(K key,
			Map<K, Set<V>> map);
	
	Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity);
	
	Set<OWLAxiom> getReferencingAxioms(OWLAnonymousIndividual individual);
	
	Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity);

	Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls);

	Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls);

	Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls);

	Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls);

	Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass);

	Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls);

	// Object properties
	Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
			OWLObjectPropertyExpression property);

	Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(
			OWLObjectPropertyExpression property);

	Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(
			OWLObjectPropertyExpression property);

	Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
			OWLDataPropertyExpression property);

	Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(
			OWLDataProperty lhsProperty);

	Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
			OWLDataPropertyExpression property);

	Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
			OWLDataProperty property);

	Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
			OWLDataProperty property);

	Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(
			OWLDataProperty property);

	Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
			OWLDataProperty property);

	Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual);

	Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type);

	Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
			OWLIndividual individual);

	Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(
			OWLIndividual individual);

	Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
			OWLIndividual individual);

	Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(
			OWLIndividual individual);

	Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual);

	Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
			OWLIndividual individual);

	Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxiomsBySubject(
			OWLAnnotationSubject subject);

	Set<OWLClassAxiom> getAxioms(OWLClass cls);

	boolean containsAxiom(OWLAxiom axiom);

	int getAxiomCount();

	Set<OWLAxiom> getAxioms();

	<T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType);

	<T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType,
			Collection<OWLOntology> importsClosure);

	<T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType);

	Set<OWLLogicalAxiom> getLogicalAxioms();

	int getLogicalAxiomCount();
}