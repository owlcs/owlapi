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

import static org.semanticweb.owlapi.model.AxiomType.*;
import static org.semanticweb.owlapi.util.CollectionFactory.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
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
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

public class OWLOntologyImplInternalsDefaultImpl implements OWLOntologyImplInternals {
	protected Set<OWLImportsDeclaration> importsDeclarations;
	protected Set<OWLAnnotation> ontologyAnnotations;
	protected Map<AxiomType, Set<OWLAxiom>> axiomsByType;
	protected Map<OWLAxiom, Set<OWLAxiom>> logicalAxiom2AnnotatedAxiomMap;
	protected Set<OWLClassAxiom> generalClassAxioms;
	protected Set<OWLSubPropertyChainOfAxiom> propertyChainSubPropertyAxioms;
	protected Map<OWLClass, Set<OWLAxiom>> owlClassReferences;
	protected Map<OWLObjectProperty, Set<OWLAxiom>> owlObjectPropertyReferences;
	protected Map<OWLDataProperty, Set<OWLAxiom>> owlDataPropertyReferences;
	protected Map<OWLNamedIndividual, Set<OWLAxiom>> owlIndividualReferences;
	protected Map<OWLAnonymousIndividual, Set<OWLAxiom>> owlAnonymousIndividualReferences;
	protected Map<OWLDatatype, Set<OWLAxiom>> owlDatatypeReferences;
	protected Map<OWLAnnotationProperty, Set<OWLAxiom>> owlAnnotationPropertyReferences;
	protected Map<OWLEntity, Set<OWLDeclarationAxiom>> declarationsByEntity;
	protected Map<OWLClass, Set<OWLClassAxiom>> classAxiomsByClass;
	protected Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByLHS;
	protected Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByRHS;
	protected Map<OWLClass, Set<OWLEquivalentClassesAxiom>> equivalentClassesAxiomsByClass;
	protected Map<OWLClass, Set<OWLDisjointClassesAxiom>> disjointClassesAxiomsByClass;
	protected Map<OWLClass, Set<OWLDisjointUnionAxiom>> disjointUnionAxiomsByClass;
	protected Map<OWLClass, Set<OWLHasKeyAxiom>> hasKeyAxiomsByClass;
	protected Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByLHS;
	protected Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByRHS;
	protected Map<OWLObjectPropertyExpression, Set<OWLEquivalentObjectPropertiesAxiom>> equivalentObjectPropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLDisjointObjectPropertiesAxiom>> disjointObjectPropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyDomainAxiom>> objectPropertyDomainAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyRangeAxiom>> objectPropertyRangeAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLFunctionalObjectPropertyAxiom>> functionalObjectPropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLInverseFunctionalObjectPropertyAxiom>> inverseFunctionalPropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLSymmetricObjectPropertyAxiom>> symmetricPropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLAsymmetricObjectPropertyAxiom>> asymmetricPropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLReflexiveObjectPropertyAxiom>> reflexivePropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLIrreflexiveObjectPropertyAxiom>> irreflexivePropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLTransitiveObjectPropertyAxiom>> transitivePropertyAxiomsByProperty;
	protected Map<OWLObjectPropertyExpression, Set<OWLInverseObjectPropertiesAxiom>> inversePropertyAxiomsByProperty;
	protected Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByLHS;
	protected Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByRHS;
	protected Map<OWLDataPropertyExpression, Set<OWLEquivalentDataPropertiesAxiom>> equivalentDataPropertyAxiomsByProperty;
	protected Map<OWLDataPropertyExpression, Set<OWLDisjointDataPropertiesAxiom>> disjointDataPropertyAxiomsByProperty;
	protected Map<OWLDataPropertyExpression, Set<OWLDataPropertyDomainAxiom>> dataPropertyDomainAxiomsByProperty;
	protected Map<OWLDataPropertyExpression, Set<OWLDataPropertyRangeAxiom>> dataPropertyRangeAxiomsByProperty;
	protected Map<OWLDataPropertyExpression, Set<OWLFunctionalDataPropertyAxiom>> functionalDataPropertyAxiomsByProperty;
	protected Map<OWLIndividual, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByIndividual;
	protected Map<OWLClass, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByClass;
	protected Map<OWLIndividual, Set<OWLObjectPropertyAssertionAxiom>> objectPropertyAssertionsByIndividual;
	protected Map<OWLIndividual, Set<OWLDataPropertyAssertionAxiom>> dataPropertyAssertionsByIndividual;
	protected Map<OWLIndividual, Set<OWLNegativeObjectPropertyAssertionAxiom>> negativeObjectPropertyAssertionAxiomsByIndividual;
	protected Map<OWLIndividual, Set<OWLNegativeDataPropertyAssertionAxiom>> negativeDataPropertyAssertionAxiomsByIndividual;
	protected Map<OWLIndividual, Set<OWLDifferentIndividualsAxiom>> differentIndividualsAxiomsByIndividual;
	protected Map<OWLIndividual, Set<OWLSameIndividualAxiom>> sameIndividualsAxiomsByIndividual;
	protected Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> annotationAssertionAxiomsBySubject;

	public OWLOntologyImplInternalsDefaultImpl() {
		this.importsDeclarations = createSet();
		this.ontologyAnnotations = createSet();
		this.axiomsByType = createMap();
		this.logicalAxiom2AnnotatedAxiomMap = createMap();
		this.generalClassAxioms = createSet();
		this.propertyChainSubPropertyAxioms = createSet();
		this.owlClassReferences = createMap();
		this.owlObjectPropertyReferences = createMap();
		this.owlDataPropertyReferences = createMap();
		this.owlIndividualReferences = createMap();
		this.owlAnonymousIndividualReferences = createMap();
		this.owlDatatypeReferences = createMap();
		this.owlAnnotationPropertyReferences = createMap();
		this.declarationsByEntity = createMap();
		if (owlClassReferences == null) {
			throw new OWLRuntimeException(
					"Internal Error: Class reference index is null after init");
		}
		if (owlObjectPropertyReferences == null) {
			throw new OWLRuntimeException(
					"Internal Error: Object property reference index is null after init");
		}
		if (owlDataPropertyReferences == null) {
			throw new OWLRuntimeException(
					"Internal Error: Data property reference index is null after init");
		}
		if (owlIndividualReferences == null) {
			throw new OWLRuntimeException(
					"Internal Error: Individual reference index is null after init");
		}
		if (owlAnnotationPropertyReferences == null) {
			throw new OWLRuntimeException(
					"Internal Error: Annotation property reference index is null after init");
		}
	}

	public 	Set<OWLImportsDeclaration> getImportsDeclarations() {
		return this.importsDeclarations;
	}

	public 	Set<OWLAnnotation> getOntologyAnnotations() {
		return this.ontologyAnnotations;
	}

	public Map<AxiomType, Set<OWLAxiom>> getAxiomsByType() {
		return this.axiomsByType;
	}

	public Map<OWLAxiom, Set<OWLAxiom>> getLogicalAxiom2AnnotatedAxiomMap() {
		return this.logicalAxiom2AnnotatedAxiomMap;
	}

	public Set<OWLClassAxiom> getGeneralClassAxioms() {
		return this.generalClassAxioms;
	}

	public Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxioms() {
		return this.propertyChainSubPropertyAxioms;
	}

	public Map<OWLClass, Set<OWLAxiom>> getOwlClassReferences() {
		return this.owlClassReferences;
	}

	public Map<OWLObjectProperty, Set<OWLAxiom>> getOwlObjectPropertyReferences() {
		return this.owlObjectPropertyReferences;
	}

	public Map<OWLDataProperty, Set<OWLAxiom>> getOwlDataPropertyReferences() {
		return this.owlDataPropertyReferences;
	}

	public Map<OWLNamedIndividual, Set<OWLAxiom>> getOwlIndividualReferences() {
		return this.owlIndividualReferences;
	}

	public 	Map<OWLAnonymousIndividual, Set<OWLAxiom>> getOwlAnonymousIndividualReferences() {
		return this.owlAnonymousIndividualReferences;
	}

	public Map<OWLDatatype, Set<OWLAxiom>> getOwlDatatypeReferences() {
		return this.owlDatatypeReferences;
	}

	public Map<OWLAnnotationProperty, Set<OWLAxiom>> getOwlAnnotationPropertyReferences() {
		return this.owlAnnotationPropertyReferences;
	}

	public Map<OWLEntity, Set<OWLDeclarationAxiom>> getDeclarationsByEntity() {
		return this.declarationsByEntity;
	}

	public Map<OWLClass, Set<OWLClassAxiom>> getClassAxiomsByClass() {
		return this.classAxiomsByClass;
	}

	public Map<OWLClass, Set<OWLSubClassOfAxiom>> getSubClassAxiomsByLHS() {
		return this.subClassAxiomsByLHS;
	}

	public Map<OWLClass, Set<OWLSubClassOfAxiom>> getSubClassAxiomsByRHS() {
		return this.subClassAxiomsByRHS;
	}

	public Map<OWLClass, Set<OWLEquivalentClassesAxiom>> getEquivalentClassesAxiomsByClass() {
		return this.equivalentClassesAxiomsByClass;
	}

	public Map<OWLClass, Set<OWLDisjointClassesAxiom>> getDisjointClassesAxiomsByClass() {
		return this.disjointClassesAxiomsByClass;
	}

	public Map<OWLClass, Set<OWLDisjointUnionAxiom>> getDisjointUnionAxiomsByClass() {
		return this.disjointUnionAxiomsByClass;
	}

	public Map<OWLClass, Set<OWLHasKeyAxiom>> getHasKeyAxiomsByClass() {
		return this.hasKeyAxiomsByClass;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> getObjectSubPropertyAxiomsByLHS() {
		return this.objectSubPropertyAxiomsByLHS;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> getObjectSubPropertyAxiomsByRHS() {
		return this.objectSubPropertyAxiomsByRHS;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLEquivalentObjectPropertiesAxiom>> getEquivalentObjectPropertyAxiomsByProperty() {
		return this.equivalentObjectPropertyAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLDisjointObjectPropertiesAxiom>> getDisjointObjectPropertyAxiomsByProperty() {
		return this.disjointObjectPropertyAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyDomainAxiom>> getObjectPropertyDomainAxiomsByProperty() {
		return this.objectPropertyDomainAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyRangeAxiom>> getObjectPropertyRangeAxiomsByProperty() {
		return this.objectPropertyRangeAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLFunctionalObjectPropertyAxiom>> getFunctionalObjectPropertyAxiomsByProperty() {
		return this.functionalObjectPropertyAxiomsByProperty;
	}

	public 	Map<OWLObjectPropertyExpression, Set<OWLInverseFunctionalObjectPropertyAxiom>> getInverseFunctionalPropertyAxiomsByProperty() {
		return this.inverseFunctionalPropertyAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLSymmetricObjectPropertyAxiom>> getSymmetricPropertyAxiomsByProperty() {
		return this.symmetricPropertyAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLAsymmetricObjectPropertyAxiom>> getAsymmetricPropertyAxiomsByProperty() {
		return this.asymmetricPropertyAxiomsByProperty;
	}

	public 	Map<OWLObjectPropertyExpression, Set<OWLReflexiveObjectPropertyAxiom>> getReflexivePropertyAxiomsByProperty() {
		return this.reflexivePropertyAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLIrreflexiveObjectPropertyAxiom>> getIrreflexivePropertyAxiomsByProperty() {
		return this.irreflexivePropertyAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLTransitiveObjectPropertyAxiom>> getTransitivePropertyAxiomsByProperty() {
		return this.transitivePropertyAxiomsByProperty;
	}

	public Map<OWLObjectPropertyExpression, Set<OWLInverseObjectPropertiesAxiom>> getInversePropertyAxiomsByProperty() {
		return this.inversePropertyAxiomsByProperty;
	}

	public Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> getDataSubPropertyAxiomsByLHS() {
		return this.dataSubPropertyAxiomsByLHS;
	}

	public Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> getDataSubPropertyAxiomsByRHS() {
		return this.dataSubPropertyAxiomsByRHS;
	}

	public Map<OWLDataPropertyExpression, Set<OWLEquivalentDataPropertiesAxiom>> getEquivalentDataPropertyAxiomsByProperty() {
		return this.equivalentDataPropertyAxiomsByProperty;
	}

	public Map<OWLDataPropertyExpression, Set<OWLDisjointDataPropertiesAxiom>> getDisjointDataPropertyAxiomsByProperty() {
		return this.disjointDataPropertyAxiomsByProperty;
	}

	public Map<OWLDataPropertyExpression, Set<OWLDataPropertyDomainAxiom>> getDataPropertyDomainAxiomsByProperty() {
		return this.dataPropertyDomainAxiomsByProperty;
	}

	public Map<OWLDataPropertyExpression, Set<OWLDataPropertyRangeAxiom>> getDataPropertyRangeAxiomsByProperty() {
		return this.dataPropertyRangeAxiomsByProperty;
	}

	public Map<OWLDataPropertyExpression, Set<OWLFunctionalDataPropertyAxiom>> getFunctionalDataPropertyAxiomsByProperty() {
		return this.functionalDataPropertyAxiomsByProperty;
	}

	public Map<OWLIndividual, Set<OWLClassAssertionAxiom>> getClassAssertionAxiomsByIndividual() {
		return this.classAssertionAxiomsByIndividual;
	}

	public Map<OWLClass, Set<OWLClassAssertionAxiom>> getClassAssertionAxiomsByClass() {
		return this.classAssertionAxiomsByClass;
	}

	public Map<OWLIndividual, Set<OWLObjectPropertyAssertionAxiom>> getObjectPropertyAssertionsByIndividual() {
		return this.objectPropertyAssertionsByIndividual;
	}

	public Map<OWLIndividual, Set<OWLDataPropertyAssertionAxiom>> getDataPropertyAssertionsByIndividual() {
		return this.dataPropertyAssertionsByIndividual;
	}

	public Map<OWLIndividual, Set<OWLNegativeObjectPropertyAssertionAxiom>> getNegativeObjectPropertyAssertionAxiomsByIndividual() {
		return this.negativeObjectPropertyAssertionAxiomsByIndividual;
	}

	public Map<OWLIndividual, Set<OWLNegativeDataPropertyAssertionAxiom>> getNegativeDataPropertyAssertionAxiomsByIndividual() {
		return this.negativeDataPropertyAssertionAxiomsByIndividual;
	}

	public 	Map<OWLIndividual, Set<OWLDifferentIndividualsAxiom>> getDifferentIndividualsAxiomsByIndividual() {
		return this.differentIndividualsAxiomsByIndividual;
	}

	public Map<OWLIndividual, Set<OWLSameIndividualAxiom>> getSameIndividualsAxiomsByIndividual() {
		return this.sameIndividualsAxiomsByIndividual;
	}

	public Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> getAnnotationAssertionAxiomsBySubject() {
		return this.annotationAssertionAxiomsBySubject;
	}

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
	public <K extends OWLAxiom> void addAxiomToSet(K axiom, Set<K> axioms) {
		if (axioms != null && axiom != null) {
			axioms.add(axiom);
		}
	}

	public <K extends OWLAxiom> void removeAxiomFromSet(K axiom, Set<K> axioms) {
		if (axioms != null) {
			axioms.remove(axiom);
		}
	}

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
	public <K, V extends OWLAxiom> void addToIndexedSet(K key, Map<K, Set<V>> map,
			V axiom) {
		if (map == null) {
			return;
		}
		Set<V> axioms = map.get(key);
		if (axioms == null) {
			axioms = createSet();
			map.put(key, axioms);
		}
		axioms.add(axiom);
	}

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
	public <K, V extends OWLAxiom> void removeAxiomFromSet(K key, Map<K, Set<V>> map,
			V axiom, boolean removeSetIfEmpty) {
		if (map == null) {
			return;
		}
		Set<V> axioms = map.get(key);
		if (axioms != null) {
			axioms.remove(axiom);
			if (removeSetIfEmpty) {
				if (axioms.isEmpty()) {
					map.remove(key);
				}
			}
		}
	}

	public <E> Set<E> getReturnSet(Set<E> set) {
		return createSet(set);
	}

	public <K extends OWLObject, V extends OWLAxiom> Set<V> getAxioms(K key,
			Map<K, Set<V>> map) {
		Set<V> axioms = map.get(key);
		if (axioms != null) {
			return Collections.unmodifiableSet(axioms);
		} else {
			return Collections.emptySet();
		}
	}

	public <K, V extends OWLAxiom> Set<V> getAxioms(K key, Map<K, Set<V>> map,
			boolean create) {
		Set<V> axioms = map.get(key);
		if (axioms == null) {
			if (create) {
				axioms = createSet();
				map.put(key, axioms);
			} else {
				axioms=Collections.emptySet();
			}
		} else {
			axioms = Collections.unmodifiableSet(axioms);
		}
		return axioms;
	}
	
	public <T extends OWLAxiom> Set<T> getAxiomsInternal(AxiomType<T> axiomType) {
	        return (Set<T>) getAxioms(axiomType, axiomsByType, false);
	    }
	
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        if (subClassAxiomsByLHS == null) {
            Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByLHS = createMap(); // masks member declaration
            for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
                if (!axiom.getSubClass().isAnonymous()) {
                	addToIndexedSet(axiom.getSubClass().asOWLClass(), subClassAxiomsByLHS, axiom);
                }
            }
            this.subClassAxiomsByLHS=subClassAxiomsByLHS;
        }
        return getReturnSet(getAxioms(cls, subClassAxiomsByLHS));
    }


    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        if (subClassAxiomsByRHS == null) {
            Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByRHS = createMap();  // masks member declaration
            for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
                if (!axiom.getSuperClass().isAnonymous()) {
                	addToIndexedSet(axiom.getSuperClass().asOWLClass(), subClassAxiomsByRHS, axiom);
                }
            }
            this.subClassAxiomsByRHS=subClassAxiomsByRHS;
        }
        return getReturnSet(getAxioms(cls, subClassAxiomsByRHS));
    }


    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls) {
        if (equivalentClassesAxiomsByClass == null) {
            Map<OWLClass, Set<OWLEquivalentClassesAxiom>> equivalentClassesAxiomsByClass = createMap();  // masks member declaration
            for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), equivalentClassesAxiomsByClass, axiom);
                    }
                }
            }
            this.equivalentClassesAxiomsByClass=equivalentClassesAxiomsByClass;
        }
        return getReturnSet(getAxioms(cls, equivalentClassesAxiomsByClass));
    }


    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        if (disjointClassesAxiomsByClass == null) {
            Map<OWLClass, Set<OWLDisjointClassesAxiom>> disjointClassesAxiomsByClass = createMap(); // masks member declaration
            for (OWLDisjointClassesAxiom axiom : getAxiomsInternal(DISJOINT_CLASSES)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), disjointClassesAxiomsByClass, axiom);
                    }
                }
            }
            this.disjointClassesAxiomsByClass=disjointClassesAxiomsByClass;
        }
        return getReturnSet(getAxioms(cls, disjointClassesAxiomsByClass));
    }


    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        if (getDisjointUnionAxiomsByClass() == null) {
            Map<OWLClass, Set<OWLDisjointUnionAxiom>> disjointUnionAxiomsByClass = createMap(); // masks member declaration
            for (OWLDisjointUnionAxiom axiom : getAxiomsInternal(DISJOINT_UNION)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), disjointUnionAxiomsByClass, axiom);
                    }
                }
            }
            this.disjointUnionAxiomsByClass=disjointUnionAxiomsByClass;
        }
        return getReturnSet(getAxioms(owlClass, getDisjointUnionAxiomsByClass()));
    }


    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        if(getHasKeyAxiomsByClass() == null) {
            Map<OWLClass, Set<OWLHasKeyAxiom>> hasKeyAxiomsByClass = createMap(); // masks member declaration
            for(OWLHasKeyAxiom axiom : getAxiomsInternal(HAS_KEY)) {
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet(axiom.getClassExpression().asOWLClass(), hasKeyAxiomsByClass, axiom);
                }
            }
            this.hasKeyAxiomsByClass=hasKeyAxiomsByClass;
        }
        return getReturnSet(getAxioms(cls, getHasKeyAxiomsByClass()));
    }



    // Object properties
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression property) {
        if (getObjectSubPropertyAxiomsByLHS() == null) {
            Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByLHS = createMap(); // masks member declaration
            for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
                addToIndexedSet(axiom.getSubProperty(), objectSubPropertyAxiomsByLHS, axiom);
            }
            this.objectSubPropertyAxiomsByLHS=objectSubPropertyAxiomsByLHS;
        }
        return getReturnSet(getAxioms(property, getObjectSubPropertyAxiomsByLHS()));
    }


    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression property) {
        if (getObjectSubPropertyAxiomsByRHS() == null) {
            Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByRHS = createMap(); // masks member declaration
            for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
                addToIndexedSet(axiom.getSuperProperty(), objectSubPropertyAxiomsByRHS, axiom);
            }
            this.objectSubPropertyAxiomsByRHS=objectSubPropertyAxiomsByRHS;
        }
        return getReturnSet(getAxioms(property, getObjectSubPropertyAxiomsByRHS()));
    }


    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        if (getObjectPropertyDomainAxiomsByProperty() == null) {
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyDomainAxiom>> objectPropertyDomainAxiomsByProperty = createMap(); // masks member declaration
            for (OWLObjectPropertyDomainAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_DOMAIN)) {
                addToIndexedSet(axiom.getProperty(), objectPropertyDomainAxiomsByProperty, axiom);
            }
            this.objectPropertyDomainAxiomsByProperty=objectPropertyDomainAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getObjectPropertyDomainAxiomsByProperty()));
    }


    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        if (getObjectPropertyRangeAxiomsByProperty() == null) {
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyRangeAxiom>> objectPropertyRangeAxiomsByProperty = createMap(); // masks member declaration
            for (OWLObjectPropertyRangeAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_RANGE)) {
                addToIndexedSet(axiom.getProperty(), objectPropertyRangeAxiomsByProperty, axiom);
            }
            this.objectPropertyRangeAxiomsByProperty=objectPropertyRangeAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getObjectPropertyRangeAxiomsByProperty()));
    }


    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (getInversePropertyAxiomsByProperty() == null) {
            Map<OWLObjectPropertyExpression, Set<OWLInverseObjectPropertiesAxiom>> inversePropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLInverseObjectPropertiesAxiom axiom : getAxiomsInternal(INVERSE_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, inversePropertyAxiomsByProperty, axiom);
                }
            }
            this.inversePropertyAxiomsByProperty=inversePropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getInversePropertyAxiomsByProperty()));
    }


    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
            OWLObjectPropertyExpression property) {
        if (getEquivalentObjectPropertyAxiomsByProperty() == null) {
            Map<OWLObjectPropertyExpression, Set<OWLEquivalentObjectPropertiesAxiom>> equivalentObjectPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLEquivalentObjectPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentObjectPropertyAxiomsByProperty, axiom);
                }
            }
            this.equivalentObjectPropertyAxiomsByProperty=equivalentObjectPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getEquivalentObjectPropertyAxiomsByProperty()));
    }


    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
            OWLObjectPropertyExpression property) {
        if (getDisjointObjectPropertyAxiomsByProperty() == null) {
            Map<OWLObjectPropertyExpression, Set<OWLDisjointObjectPropertiesAxiom>> disjointObjectPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDisjointObjectPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointObjectPropertyAxiomsByProperty, axiom);
                }
            }
            this.disjointObjectPropertyAxiomsByProperty=disjointObjectPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getDisjointObjectPropertyAxiomsByProperty()));
    }

    protected <T extends OWLObjectPropertyCharacteristicAxiom> Map<OWLObjectPropertyExpression, Set<T>> buildObjectPropertyCharacteristicsIndex(AxiomType<T> type) {
        Map<OWLObjectPropertyExpression, Set<T>> map = createMap();
        for(T ax : getAxiomsInternal(type)) {
            addToIndexedSet(ax.getProperty(), map, ax);
        }
        return map;
    }

    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (getFunctionalObjectPropertyAxiomsByProperty() == null) {
            functionalObjectPropertyAxiomsByProperty=buildObjectPropertyCharacteristicsIndex(FUNCTIONAL_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, getFunctionalObjectPropertyAxiomsByProperty()));
    }


    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
            OWLObjectPropertyExpression property) {
        if (getInverseFunctionalPropertyAxiomsByProperty() == null) {
            inverseFunctionalPropertyAxiomsByProperty=buildObjectPropertyCharacteristicsIndex(INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, getInverseFunctionalPropertyAxiomsByProperty()));
    }


    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (getSymmetricPropertyAxiomsByProperty() == null) {
            symmetricPropertyAxiomsByProperty=buildObjectPropertyCharacteristicsIndex(SYMMETRIC_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, getSymmetricPropertyAxiomsByProperty()));
    }


    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (getAsymmetricPropertyAxiomsByProperty() == null) {
            asymmetricPropertyAxiomsByProperty=buildObjectPropertyCharacteristicsIndex(ASYMMETRIC_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, getAsymmetricPropertyAxiomsByProperty()));
    }


    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (getReflexivePropertyAxiomsByProperty() == null) {
            reflexivePropertyAxiomsByProperty=buildObjectPropertyCharacteristicsIndex(REFLEXIVE_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, getReflexivePropertyAxiomsByProperty()));
    }


    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (getIrreflexivePropertyAxiomsByProperty() == null) {
            irreflexivePropertyAxiomsByProperty=buildObjectPropertyCharacteristicsIndex(IRREFLEXIVE_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, getIrreflexivePropertyAxiomsByProperty()));
    }


    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (getTransitivePropertyAxiomsByProperty() == null) {
            transitivePropertyAxiomsByProperty=buildObjectPropertyCharacteristicsIndex(TRANSITIVE_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, getTransitivePropertyAxiomsByProperty()));
    }


    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        if (getFunctionalDataPropertyAxiomsByProperty() == null) {
            Map<OWLDataPropertyExpression, Set<OWLFunctionalDataPropertyAxiom>> functionalDataPropertyAxiomsByProperty = createMap(); // masks member declaration
            for(OWLFunctionalDataPropertyAxiom ax : getAxiomsInternal(FUNCTIONAL_DATA_PROPERTY)) {
                addToIndexedSet(ax.getProperty(), functionalDataPropertyAxiomsByProperty, ax);
            }
            this.functionalDataPropertyAxiomsByProperty=functionalDataPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getFunctionalDataPropertyAxiomsByProperty()));
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        if (getDataSubPropertyAxiomsByLHS() == null) {
            Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByLHS = createMap(); // masks member declaration
            for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
                addToIndexedSet(axiom.getSubProperty(), dataSubPropertyAxiomsByLHS, axiom);
            }
            this.dataSubPropertyAxiomsByLHS=dataSubPropertyAxiomsByLHS;
        }
        return getReturnSet(getAxioms(lhsProperty, getDataSubPropertyAxiomsByLHS()));
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression property) {
        if (getDataSubPropertyAxiomsByRHS() == null) {
            Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByRHS = createMap(); // masks member declaration
            for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
                addToIndexedSet(axiom.getSuperProperty(), dataSubPropertyAxiomsByRHS, axiom);
            }
            this.dataSubPropertyAxiomsByRHS=dataSubPropertyAxiomsByRHS;
        }
        return getReturnSet(getAxioms(property, getDataSubPropertyAxiomsByRHS()));
    }


    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property) {
        if (getDataPropertyDomainAxiomsByProperty() == null) {
            Map<OWLDataPropertyExpression, Set<OWLDataPropertyDomainAxiom>> dataPropertyDomainAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDataPropertyDomainAxiom axiom : getAxiomsInternal(DATA_PROPERTY_DOMAIN)) {
                addToIndexedSet(axiom.getProperty(), dataPropertyDomainAxiomsByProperty, axiom);
            }
            this.dataPropertyDomainAxiomsByProperty=dataPropertyDomainAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getDataPropertyDomainAxiomsByProperty()));
    }


    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty property) {
        if (getDataPropertyRangeAxiomsByProperty() == null) {
            Map<OWLDataPropertyExpression, Set<OWLDataPropertyRangeAxiom>> dataPropertyRangeAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDataPropertyRangeAxiom axiom : getAxiomsInternal(DATA_PROPERTY_RANGE)) {
                addToIndexedSet(axiom.getProperty(), dataPropertyRangeAxiomsByProperty, axiom);
            }
            this.dataPropertyRangeAxiomsByProperty=dataPropertyRangeAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getDataPropertyRangeAxiomsByProperty()));
    }


    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        if (getEquivalentDataPropertyAxiomsByProperty() == null) {
            Map<OWLDataPropertyExpression, Set<OWLEquivalentDataPropertiesAxiom>> equivalentDataPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLEquivalentDataPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_DATA_PROPERTIES)) {
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentDataPropertyAxiomsByProperty, axiom);
                }
            }
            this.equivalentDataPropertyAxiomsByProperty=equivalentDataPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getEquivalentDataPropertyAxiomsByProperty()));
    }


    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty property) {
        if (getDisjointDataPropertyAxiomsByProperty() == null) {
            Map<OWLDataPropertyExpression, Set<OWLDisjointDataPropertiesAxiom>> disjointDataPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDisjointDataPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_DATA_PROPERTIES)) {
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointDataPropertyAxiomsByProperty, axiom);
                }
            }
            this.disjointDataPropertyAxiomsByProperty=disjointDataPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, getDisjointDataPropertyAxiomsByProperty()));
    }

    ////


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual) {
        if (getClassAssertionAxiomsByIndividual() == null) {
            Map<OWLIndividual, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
                addToIndexedSet(axiom.getIndividual(), classAssertionAxiomsByIndividual, axiom);
            }
            this.classAssertionAxiomsByIndividual=classAssertionAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, getClassAssertionAxiomsByIndividual()));
    }


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type) {
        if (getClassAssertionAxiomsByClass() == null) {
            Map<OWLClass, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByClass = createMap(); // masks member declaration
            for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet((OWLClass) axiom.getClassExpression(), classAssertionAxiomsByClass, axiom);
                }
            }
            this.classAssertionAxiomsByClass=classAssertionAxiomsByClass;
        }
        return getReturnSet(getAxioms(type, getClassAssertionAxiomsByClass()));
    }


    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual) {
        if (getDataPropertyAssertionsByIndividual() == null) {
            Map<OWLIndividual, Set<OWLDataPropertyAssertionAxiom>> dataPropertyAssertionsByIndividual = createMap(); // masks member declaration
            for (OWLDataPropertyAssertionAxiom axiom : getAxiomsInternal(DATA_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), dataPropertyAssertionsByIndividual, axiom);
            }
            this.dataPropertyAssertionsByIndividual=dataPropertyAssertionsByIndividual;
        }
        return getReturnSet(getAxioms(individual, getDataPropertyAssertionsByIndividual()));
    }


    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        if (getObjectPropertyAssertionsByIndividual() == null) {
            Map<OWLIndividual, Set<OWLObjectPropertyAssertionAxiom>> objectPropertyAssertionsByIndividual = createMap(); // masks member declaration
            for (OWLObjectPropertyAssertionAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), objectPropertyAssertionsByIndividual, axiom);
            }
            this.objectPropertyAssertionsByIndividual=objectPropertyAssertionsByIndividual;
        }
        return getReturnSet(getAxioms(individual, getObjectPropertyAssertionsByIndividual()));
    }


    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
            OWLIndividual individual) {
        if (getNegativeObjectPropertyAssertionAxiomsByIndividual() == null) {
            Map<OWLIndividual, Set<OWLNegativeObjectPropertyAssertionAxiom>> negativeObjectPropertyAssertionAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLNegativeObjectPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), negativeObjectPropertyAssertionAxiomsByIndividual, axiom);
            }
            this.negativeObjectPropertyAssertionAxiomsByIndividual=negativeObjectPropertyAssertionAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, getNegativeObjectPropertyAssertionAxiomsByIndividual()));
    }


    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        if (getNegativeDataPropertyAssertionAxiomsByIndividual() == null) {
            Map<OWLIndividual, Set<OWLNegativeDataPropertyAssertionAxiom>> negativeDataPropertyAssertionAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLNegativeDataPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_DATA_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), negativeDataPropertyAssertionAxiomsByIndividual, axiom);
            }
            this
					.negativeDataPropertyAssertionAxiomsByIndividual=negativeDataPropertyAssertionAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, getNegativeDataPropertyAssertionAxiomsByIndividual()));
    }


    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual) {
        if (getSameIndividualsAxiomsByIndividual() == null) {
            Map<OWLIndividual, Set<OWLSameIndividualAxiom>> sameIndividualsAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLSameIndividualAxiom axiom : getAxiomsInternal(SAME_INDIVIDUAL)) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, sameIndividualsAxiomsByIndividual, axiom);
                }
            }
            this.sameIndividualsAxiomsByIndividual=sameIndividualsAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, getSameIndividualsAxiomsByIndividual()));
    }


    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual) {
        if (getDifferentIndividualsAxiomsByIndividual() == null) {
            Map<OWLIndividual, Set<OWLDifferentIndividualsAxiom>> differentIndividualsAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLDifferentIndividualsAxiom axiom : getAxiomsInternal(DIFFERENT_INDIVIDUALS)) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, differentIndividualsAxiomsByIndividual, axiom);
                }
            }
            this.differentIndividualsAxiomsByIndividual=differentIndividualsAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, getDifferentIndividualsAxiomsByIndividual()));
    }

    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxiomsBySubject(OWLAnnotationSubject subject){
        if (annotationAssertionAxiomsBySubject == null) {
            Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> annotationAssertionAxiomsBySubject = createMap(); // masks member declaration
            for (OWLAnnotationAssertionAxiom axiom : getAxiomsInternal(ANNOTATION_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), annotationAssertionAxiomsBySubject, axiom);
            }
            this.annotationAssertionAxiomsBySubject=annotationAssertionAxiomsBySubject;
        }
        return getReturnSet(getAxioms(subject, annotationAssertionAxiomsBySubject, false));
    }
    
    public  void buildClassAxiomsByClassIndex() {
        Map<OWLClass, Set<OWLClassAxiom>> classAxiomsByClass = createMap(); // masks member declaration
        for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    addToIndexedSet((OWLClass) desc, classAxiomsByClass, axiom);
                }
            }
        }

        for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
            if (!axiom.getSubClass().isAnonymous()) {
                addToIndexedSet((OWLClass) axiom.getSubClass(), classAxiomsByClass, axiom);
            }
        }

        for (OWLDisjointClassesAxiom axiom : getAxiomsInternal(DISJOINT_CLASSES)) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    addToIndexedSet((OWLClass) desc, classAxiomsByClass, axiom);
                }
            }
        }

        for (OWLDisjointUnionAxiom axiom : getAxiomsInternal(DISJOINT_UNION)) {
            addToIndexedSet(axiom.getOWLClass(), classAxiomsByClass, axiom);
        }
        this.classAxiomsByClass=classAxiomsByClass;
    }
}