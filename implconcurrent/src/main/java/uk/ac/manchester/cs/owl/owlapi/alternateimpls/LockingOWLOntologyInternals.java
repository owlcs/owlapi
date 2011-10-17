/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import static org.semanticweb.owlapi.model.AxiomType.DECLARATION;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.util.CollectionFactory;

import uk.ac.manchester.cs.owl.owlapi.InternalsImpl;

/**
 * @author ignazio
 * threadsafe implementation
 */
public class LockingOWLOntologyInternals extends InternalsImpl {
	private Map<Object, ReadWriteLock> locks;
	// to be used to lock on access to all lazily built indexes
	private final ReadWriteLock lazyIndexLock;

	/**
	 * @return lock for lazy index
	 */
	public ReadWriteLock getAxiomTypeLock() {
		return lazyIndexLock;
	}

	@SuppressWarnings("javadoc")
	public LockingOWLOntologyInternals() {
		//This LOOKS like an unitialized read. Actually the locks map is
		//initialized in the super() constructor that calls this.initMaps()
		lazyIndexLock = locks.get(axiomsByType);
	}

	@Override
	protected void initMaps() {
		locks = new IdentityHashMap<Object, ReadWriteLock>();
		importsDeclarations = CollectionFactory.createSyncSet();
		locks.put(importsDeclarations, new ReentrantReadWriteLock());
		ontologyAnnotations = CollectionFactory.createSyncSet();
		locks.put(ontologyAnnotations, new ReentrantReadWriteLock());
		// guarded by lazyIndexLock
		axiomsByType = CollectionFactory.createSyncMap();
		locks.put(axiomsByType, new ReentrantReadWriteLock());
		// guarded by
		logicalAxiom2AnnotatedAxiomMap = CollectionFactory.createSyncMap();
		locks.put(logicalAxiom2AnnotatedAxiomMap, new ReentrantReadWriteLock());
		// guarded by
		generalClassAxioms = CollectionFactory.createSyncSet();
		locks.put(generalClassAxioms, new ReentrantReadWriteLock());
		// guarded by
		propertyChainSubPropertyAxioms = CollectionFactory.createSyncSet();
		locks.put(propertyChainSubPropertyAxioms, new ReentrantReadWriteLock());
		// guarded by
		owlClassReferences = CollectionFactory.createSyncMap();
		locks.put(owlClassReferences, new ReentrantReadWriteLock());
		// guarded by
		owlObjectPropertyReferences = CollectionFactory.createSyncMap();
		locks.put(owlObjectPropertyReferences, new ReentrantReadWriteLock());
		// guarded by
		owlDataPropertyReferences = CollectionFactory.createSyncMap();
		locks.put(owlDataPropertyReferences, new ReentrantReadWriteLock());
		// guarded by
		owlIndividualReferences = CollectionFactory.createSyncMap();
		locks.put(owlIndividualReferences, new ReentrantReadWriteLock());
		// guarded by
		owlAnonymousIndividualReferences = CollectionFactory.createSyncMap();
		locks.put(owlAnonymousIndividualReferences, new ReentrantReadWriteLock());
		// guarded by
		owlDatatypeReferences = CollectionFactory.createSyncMap();
		locks.put(owlDatatypeReferences, new ReentrantReadWriteLock());
		// guarded by
		owlAnnotationPropertyReferences = CollectionFactory.createSyncMap();
		locks.put(owlAnnotationPropertyReferences, new ReentrantReadWriteLock());
		// guarded by
		declarationsByEntity = CollectionFactory.createSyncMap();
		locks.put(declarationsByEntity, new ReentrantReadWriteLock());
	}

	@Override
	public Set<OWLImportsDeclaration> getImportsDeclarations() {
		Lock l = locks.get(importsDeclarations).readLock();
		l.lock();
		try {
			return super.getImportsDeclarations();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean addImportsDeclaration(OWLImportsDeclaration importDeclaration) {
		Lock l = locks.get(importsDeclarations).writeLock();
		l.lock();
		try {
			return super.addImportsDeclaration(importDeclaration);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean removeImportsDeclaration(OWLImportsDeclaration importDeclaration) {
		Lock l = locks.get(importsDeclarations).writeLock();
		l.lock();
		try {
			return super.removeImportsDeclaration(importDeclaration);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	protected <K, V> Map<K, V> createMap() {
		return CollectionFactory.createSyncMap();
	}

	//	protected <K, V> void initMapEntry(Map<K, Set<V>> map, K key, V... elements) {
	//		Lock l = locks.get(map).writeLock();
	//		l.lock();
	//		try {
	//			if (!map.containsKey(key)) {
	//				Set<V> set = CollectionFactory.createSyncSet();
	//				for (V v : elements) {
	//					set.add(v);
	//				}
	//				map.put(key, set);
	//			}
	//		} finally {
	//			l.unlock();
	//		}
	//	}
	@Override
	public <K, V extends OWLAxiom> void addToIndexedSet(K key, Map<K, Set<V>> map, V axiom) {
		if (map == null) {
			return;
		}
		if (locks.containsKey(map)) {
			Lock l = locks.get(map).writeLock();
			l.lock();
			try {
				super.addToIndexedSet(key, map, axiom);
			}
			finally {
				l.unlock();
			}
		} else {
			super.addToIndexedSet(key, map, axiom);
		}
	}

	@Override
	public <K, V extends OWLAxiom> void removeAxiomFromSet(K key, Map<K, Set<V>> map,
			V axiom, boolean removeSetIfEmpty) {
		if (map == null) {
			return;
		}
		if (locks.containsKey(map)) {
			Lock l = locks.get(map).writeLock();
			l.lock();
			try {
				super.removeAxiomFromSet(key, map, axiom, removeSetIfEmpty);
			}
			finally {
				l.unlock();
			}
		} else {
			super.removeAxiomFromSet(key, map, axiom, removeSetIfEmpty);
		}
	}




//	@Override
//	public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
//		//TODO lock
//		return getReturnSet(getAxioms(entity, declarationsByEntity, false));
//	}
//
//	@Override
//	public Set<OWLAxiom> getReferencingAxioms(OWLAnonymousIndividual individual) {
//		//TODO lock
//		return getReturnSet(getAxioms(individual, owlAnonymousIndividualReferences, false));
//	}
//

	@Override
	public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
		Lock l = locks.get(declarationsByEntity).readLock();
		l.lock();
		try {
			return super.getDeclarationAxioms(entity);
		}
		finally {
			l.unlock();
		}
	}

//	@Override
//	public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity) {
//		//TODO lock
//		Set<OWLAxiom> axioms;
//		if (owlEntity instanceof OWLClass) {
//			axioms = getAxioms(owlEntity.asOWLClass(), owlClassReferences, false);
//		} else if (owlEntity instanceof OWLObjectProperty) {
//			axioms = getAxioms(owlEntity.asOWLObjectProperty(),
//					owlObjectPropertyReferences, false);
//		} else if (owlEntity instanceof OWLDataProperty) {
//			axioms = getAxioms(owlEntity.asOWLDataProperty(), owlDataPropertyReferences,
//					false);
//		} else if (owlEntity instanceof OWLNamedIndividual) {
//			axioms = getAxioms(owlEntity.asOWLNamedIndividual(), owlIndividualReferences,
//					false);
//		} else if (owlEntity instanceof OWLDatatype) {
//			axioms = getAxioms(owlEntity.asOWLDatatype(), owlDatatypeReferences, false);
//		} else if (owlEntity instanceof OWLAnnotationProperty) {
//			axioms = getAxioms(owlEntity.asOWLAnnotationProperty(),
//					owlAnnotationPropertyReferences, false);
//		} else {
//			axioms = Collections.emptySet();
//		}
//		return getReturnSet(axioms);
//	}

	@Override
	protected <K, V extends OWLAxiom> Set<V> getAxioms(K key, Map<K, Set<V>> map,
			boolean create) {
		if (locks.containsKey(map)) {
			Lock l = locks.get(map).readLock();
			l.lock();
			try {
				return super.getAxioms(key, map, create);
			}
			finally {
				l.unlock();
			}
		} else {
			return super.getAxioms(key, map, create);
		}
	}

	@Override
	public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
		Maps.SubClassAxiomsByLHS.initMap(this, lazyIndexLock.writeLock(),
				subClassAxiomsByLHS);
		return super.getSubClassAxiomsForSubClass(cls);
	}

	@Override
	public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
		Maps.SubClassAxiomsByRHS.initMap(this, lazyIndexLock.writeLock(),
				subClassAxiomsByRHS);
		return super.getSubClassAxiomsForSuperClass(cls);
	}

	@Override
	public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls) {
		Maps.EquivalentClassesAxiomsByClass.initMap(this, lazyIndexLock.writeLock(),
				equivalentClassesAxiomsByClass);
		return super.getEquivalentClassesAxioms(cls);
	}

	@Override
	public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
		Maps.DisjointClassesAxiomsByClass.initMap(this, lazyIndexLock.writeLock(),
				disjointClassesAxiomsByClass);
		return super.getDisjointClassesAxioms(cls);
	}

	@Override
	public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
		Maps.DisjointUnionAxiomsByClass.initMap(this, lazyIndexLock.writeLock(),
				disjointUnionAxiomsByClass);
		return super.getDisjointUnionAxioms(owlClass);
	}

	@Override
	public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
		Maps.HasKeyAxiomsByClass.initMap(this, lazyIndexLock.writeLock(),
				hasKeyAxiomsByClass);
		return super.getHasKeyAxioms(cls);
	}

	// Object properties
	@Override
	public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
			OWLObjectPropertyExpression property) {
		Maps.ObjectSubPropertyAxiomsByLHS.initMap(this, lazyIndexLock.writeLock(),
				objectSubPropertyAxiomsByLHS);
		return super.getObjectSubPropertyAxiomsForSubProperty(property);
	}

	@Override
	public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(
			OWLObjectPropertyExpression property) {
		Maps.ObjectSubPropertyAxiomsByRHS.initMap(this, lazyIndexLock.writeLock(),
				objectSubPropertyAxiomsByRHS);
		return super.getObjectSubPropertyAxiomsForSuperProperty(property);
	}

	@Override
	public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
			OWLObjectPropertyExpression property) {
		Maps.ObjectPropertyDomainAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), objectPropertyDomainAxiomsByProperty);
		return super.getObjectPropertyDomainAxioms(property);
	}

	@Override
	public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
			OWLObjectPropertyExpression property) {
		Maps.ObjectPropertyRangeAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				objectPropertyRangeAxiomsByProperty);
		return super.getObjectPropertyRangeAxioms(property);
	}

	@Override
	public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.InversePropertyAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				inversePropertyAxiomsByProperty);
		return super.getInverseObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
			OWLObjectPropertyExpression property) {
		Maps.EquivalentObjectPropertyAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), equivalentObjectPropertyAxiomsByProperty);
		return super.getEquivalentObjectPropertiesAxioms(property);
	}

	@Override
	public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
			OWLObjectPropertyExpression property) {
		Maps.DisjointObjectPropertyAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), disjointObjectPropertyAxiomsByProperty);
		return super.getDisjointObjectPropertiesAxioms(property);
	}

	@Override
	public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.FunctionalObjectPropertyAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), functionalObjectPropertyAxiomsByProperty);
		return super.getFunctionalObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.InverseFunctionalPropertyAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), inverseFunctionalPropertyAxiomsByProperty);
		return super.getInverseFunctionalObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.SymmetricPropertyAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				symmetricPropertyAxiomsByProperty);
		return super.getSymmetricObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.AsymmetricPropertyAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				asymmetricPropertyAxiomsByProperty);
		return super.getAsymmetricObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.ReflexivePropertyAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				reflexivePropertyAxiomsByProperty);
		return super.getReflexiveObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.IrreflexivePropertyAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				irreflexivePropertyAxiomsByProperty);
		return super.getIrreflexiveObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		Maps.TransitivePropertyAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				transitivePropertyAxiomsByProperty);
		return super.getTransitiveObjectPropertyAxioms(property);
	}

	@Override
	public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
			OWLDataPropertyExpression property) {
		Maps.FunctionalDataPropertyAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), functionalDataPropertyAxiomsByProperty);
		return super.getFunctionalDataPropertyAxioms(property);
	}

	@Override
	public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(
			OWLDataProperty lhsProperty) {
		Maps.DataSubPropertyAxiomsByLHS.initMap(this, lazyIndexLock.writeLock(),
				dataSubPropertyAxiomsByLHS);
		return super.getDataSubPropertyAxiomsForSubProperty(lhsProperty);
	}

	@Override
	public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
			OWLDataPropertyExpression property) {
		Maps.DataSubPropertyAxiomsByRHS.initMap(this, lazyIndexLock.writeLock(),
				dataSubPropertyAxiomsByRHS);
		return super.getDataSubPropertyAxiomsForSuperProperty(property);
	}

	@Override
	public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
			OWLDataProperty property) {
		Maps.DataPropertyDomainAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				dataPropertyDomainAxiomsByProperty);
		return super.getDataPropertyDomainAxioms(property);
	}

	@Override
	public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
			OWLDataProperty property) {
		Maps.DataPropertyRangeAxiomsByProperty.initMap(this, lazyIndexLock.writeLock(),
				dataPropertyRangeAxiomsByProperty);
		return super.getDataPropertyRangeAxioms(property);
	}

	@Override
	public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(
			OWLDataProperty property) {
		Maps.EquivalentDataPropertyAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), equivalentDataPropertyAxiomsByProperty);
		return super.getEquivalentDataPropertiesAxioms(property);
	}

	@Override
	public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
			OWLDataProperty property) {
		Maps.DisjointDataPropertyAxiomsByProperty.initMap(this,
				lazyIndexLock.writeLock(), disjointDataPropertyAxiomsByProperty);
		return super.getDisjointDataPropertiesAxioms(property);
	}

	////
	@Override
	public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual) {
		Maps.ClassAssertionAxiomsByIndividual.initMap(this, lazyIndexLock.writeLock(),
				classAssertionAxiomsByIndividual);
		return super.getClassAssertionAxioms(individual);
	}

	@Override
	public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClassExpression type) {
		Maps.ClassAssertionAxiomsByClass.initMap(this, lazyIndexLock.writeLock(),
				classAssertionAxiomsByClass);
		return super.getClassAssertionAxioms(type);
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
			OWLIndividual individual) {
		Maps.DataPropertyAssertionsByIndividual.initMap(this, lazyIndexLock.writeLock(),
				dataPropertyAssertionsByIndividual);
		return super.getDataPropertyAssertionAxioms(individual);
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(
			OWLIndividual individual) {
		Maps.ObjectPropertyAssertionsByIndividual.initMap(this,
				lazyIndexLock.writeLock(), objectPropertyAssertionsByIndividual);
		return super.getObjectPropertyAssertionAxioms(individual);
	}

	@Override
	public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
			OWLIndividual individual) {
		Maps.NegativeObjectPropertyAssertionAxiomsByIndividual.initMap(this,
				lazyIndexLock.writeLock(),
				negativeObjectPropertyAssertionAxiomsByIndividual);
		return super.getNegativeObjectPropertyAssertionAxioms(individual);
	}

	@Override
	public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(
			OWLIndividual individual) {
		Maps.NegativeDataPropertyAssertionAxiomsByIndividual.initMap(this,
				lazyIndexLock.writeLock(),
				negativeDataPropertyAssertionAxiomsByIndividual);
		return super.getNegativeDataPropertyAssertionAxioms(individual);
	}

	@Override
	public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual) {
		Maps.SameIndividualsAxiomsByIndividual.initMap(this, lazyIndexLock.writeLock(),
				sameIndividualsAxiomsByIndividual);
		return super.getSameIndividualAxioms(individual);
	}

	@Override
	public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
			OWLIndividual individual) {
		Maps.DifferentIndividualsAxiomsByIndividual.initMap(this,
				lazyIndexLock.writeLock(), differentIndividualsAxiomsByIndividual);
		return super.getDifferentIndividualAxioms(individual);
	}

	@Override
	public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxiomsBySubject(
			OWLAnnotationSubject subject) {
		Maps.AnnotationAssertionAxiomsBySubject.initMap(this, lazyIndexLock.writeLock(),
				annotationAssertionAxiomsBySubject);
		return super.getAnnotationAssertionAxiomsBySubject(subject);
	}

	@Override
	public Set<OWLClassAxiom> getAxioms(OWLClass cls) {
		Maps.ClassAxiomsByClass.initMap(this, lazyIndexLock.writeLock(),
				classAxiomsByClass);
		return super.getAxioms(cls);
	}

	@Override
	public boolean isEmpty() {
		Lock l = lazyIndexLock.readLock();
		l.lock();
		try {
			for (Set<OWLAxiom> axiomSet : axiomsByType.values()) {
				if (!axiomSet.isEmpty()) {
					return false;
				}
			}
			return ontologyAnnotations.isEmpty();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype datatype) {
		Set<OWLDatatypeDefinitionAxiom> result = createSet();
		Set<OWLDatatypeDefinitionAxiom> axioms = getAxiomsInternal(AxiomType.DATATYPE_DEFINITION);
		for (OWLDatatypeDefinitionAxiom ax : axioms) {
			if (ax.getDatatype().equals(datatype)) {
				result.add(ax);
			}
		}
		return result;
	}

	@Override
	public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(
			OWLAnnotationProperty subProperty) {
		Set<OWLSubAnnotationPropertyOfAxiom> result = createSet();
		for (OWLSubAnnotationPropertyOfAxiom ax : getAxiomsInternal(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
			if (ax.getSubProperty().equals(subProperty)) {
				result.add(ax);
			}
		}
		return result;
	}

	@Override
	public boolean isDeclared(OWLDeclarationAxiom ax) {
		return getAxiomsInternal(DECLARATION).contains(ax);
	}

	@Override
	public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(
			OWLAnnotationProperty property) {
		Set<OWLAnnotationPropertyDomainAxiom> result = createSet();
		for (OWLAnnotationPropertyDomainAxiom ax : getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
			if (ax.getProperty().equals(property)) {
				result.add(ax);
			}
		}
		return result;
	}

	@Override
	public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(
			OWLAnnotationProperty property) {
		Set<OWLAnnotationPropertyRangeAxiom> result = createSet();
		for (OWLAnnotationPropertyRangeAxiom ax : getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
			if (ax.getProperty().equals(property)) {
				result.add(ax);
			}
		}
		return result;
	}

	@Override
	public Set<OWLAnnotation> getOntologyAnnotations() {
		Lock l = locks.get(ontologyAnnotations).readLock();
		l.lock();
		try {
			return super.getOntologyAnnotations();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean addOntologyAnnotation(OWLAnnotation ann) {
		Lock l = locks.get(ontologyAnnotations).writeLock();
		l.lock();
		try {
			return super.addOntologyAnnotation(ann);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean removeOntologyAnnotation(OWLAnnotation ann) {
		Lock l = locks.get(ontologyAnnotations).writeLock();
		l.lock();
		try {
			return super.removeOntologyAnnotation(ann);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsAxiom(OWLAxiom axiom) {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.containsAxiom(axiom);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public int getAxiomCount() {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.getAxiomCount();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Set<OWLAxiom> getAxioms() {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.getAxioms();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.getAxioms(axiomType);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType,
			Collection<OWLOntology> importsClosure) {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.getAxioms(axiomType, importsClosure);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.getAxiomCount(axiomType);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Set<OWLLogicalAxiom> getLogicalAxioms() {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.getLogicalAxioms();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public int getLogicalAxiomCount() {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.getLogicalAxiomCount();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLAxiom, Set<OWLAxiom>> getLogicalAxiom2AnnotatedAxiomMap() {
		Lock l = locks.get(logicalAxiom2AnnotatedAxiomMap).readLock();
		l.lock();
		try {
			return super.getLogicalAxiom2AnnotatedAxiomMap();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Set<OWLAxiom> getLogicalAxiom2AnnotatedAxiom(OWLAxiom ax) {
		Lock l = locks.get(logicalAxiom2AnnotatedAxiomMap).readLock();
		l.lock();
		try {
			return super.getLogicalAxiom2AnnotatedAxiom(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax) {
		Lock l = locks.get(logicalAxiom2AnnotatedAxiomMap).writeLock();
		l.lock();
		try {
			super.addLogicalAxiom2AnnotatedAxiomMap(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax) {
		Lock l = locks.get(logicalAxiom2AnnotatedAxiomMap).writeLock();
		l.lock();
		try {
			super.removeLogicalAxiom2AnnotatedAxiomMap(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax) {
		Lock l = locks.get(logicalAxiom2AnnotatedAxiomMap).readLock();
		l.lock();
		try {
			return super.containsLogicalAxiom2AnnotatedAxiomMap(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Set<OWLClassAxiom> getGeneralClassAxioms() {
		Lock l = locks.get(generalClassAxioms).readLock();
		l.lock();
		try {
			return super.getGeneralClassAxioms();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addGeneralClassAxioms(OWLClassAxiom ax) {
		Lock l = locks.get(generalClassAxioms).writeLock();
		l.lock();
		try {
			super.addGeneralClassAxioms(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeGeneralClassAxioms(OWLClassAxiom ax) {
		Lock l = locks.get(generalClassAxioms).writeLock();
		l.lock();
		try {
			super.removeGeneralClassAxioms(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxioms() {
		Lock l = locks.get(propertyChainSubPropertyAxioms).readLock();
		l.lock();
		try {
			return super.getPropertyChainSubPropertyAxioms();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addPropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax) {
		Lock l = locks.get(propertyChainSubPropertyAxioms).readLock();
		l.lock();
		try {
			super.addPropertyChainSubPropertyAxioms(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removePropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax) {
		Lock l = locks.get(propertyChainSubPropertyAxioms).writeLock();
		l.lock();
		try {
			super.removePropertyChainSubPropertyAxioms(ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLClass, Set<OWLAxiom>> getOwlClassReferences() {
		Lock l = locks.get(owlClassReferences).readLock();
		l.lock();
		try {
			return super.getOwlClassReferences();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeOwlClassReferences(OWLClass c, OWLAxiom ax) {
		Lock l = locks.get(owlClassReferences).writeLock();
		l.lock();
		try {
			super.removeOwlClassReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addOwlClassReferences(OWLClass c, OWLAxiom ax) {
		Lock l = locks.get(owlClassReferences).writeLock();
		l.lock();
		try {
			super.addOwlClassReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsOwlClassReferences(OWLClass c) {
		Lock l = locks.get(owlClassReferences).readLock();
		l.lock();
		try {
			return super.containsOwlClassReferences(c);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLObjectProperty, Set<OWLAxiom>> getOwlObjectPropertyReferences() {
		Lock l = locks.get(owlObjectPropertyReferences).readLock();
		l.lock();
		try {
			return super.getOwlObjectPropertyReferences();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeOwlObjectPropertyReferences(OWLObjectProperty p, OWLAxiom ax) {
		Lock l = locks.get(owlObjectPropertyReferences).writeLock();
		l.lock();
		try {
			super.removeOwlObjectPropertyReferences(p, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addOwlObjectPropertyReferences(OWLObjectProperty p, OWLAxiom ax) {
		Lock l = locks.get(owlObjectPropertyReferences).writeLock();
		l.lock();
		try {
			super.addOwlObjectPropertyReferences(p, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsOwlObjectPropertyReferences(OWLObjectProperty c) {
		Lock l = locks.get(owlObjectPropertyReferences).readLock();
		l.lock();
		try {
			return super.containsOwlObjectPropertyReferences(c);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLDataProperty, Set<OWLAxiom>> getOwlDataPropertyReferences() {
		Lock l = locks.get(owlDataPropertyReferences).readLock();
		l.lock();
		try {
			return super.getOwlDataPropertyReferences();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeOwlDataPropertyReferences(OWLDataProperty c, OWLAxiom ax) {
		Lock l = locks.get(owlDataPropertyReferences).writeLock();
		l.lock();
		try {
			super.removeOwlDataPropertyReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addOwlDataPropertyReferences(OWLDataProperty c, OWLAxiom ax) {
		Lock l = locks.get(owlDataPropertyReferences).writeLock();
		l.lock();
		try {
			super.addOwlDataPropertyReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsOwlDataPropertyReferences(OWLDataProperty c) {
		Lock l = locks.get(owlDataPropertyReferences).readLock();
		l.lock();
		try {
			return super.containsOwlDataPropertyReferences(c);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLNamedIndividual, Set<OWLAxiom>> getOwlIndividualReferences() {
		Lock l = locks.get(owlIndividualReferences).readLock();
		l.lock();
		try {
			return super.getOwlIndividualReferences();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeOwlIndividualReferences(OWLNamedIndividual c, OWLAxiom ax) {
		Lock l = locks.get(owlIndividualReferences).writeLock();
		l.lock();
		try {
			super.removeOwlIndividualReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addOwlIndividualReferences(OWLNamedIndividual c, OWLAxiom ax) {
		Lock l = locks.get(owlIndividualReferences).writeLock();
		l.lock();
		try {
			super.addOwlIndividualReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsOwlIndividualReferences(OWLNamedIndividual c) {
		Lock l = locks.get(owlIndividualReferences).readLock();
		l.lock();
		try {
			return super.containsOwlIndividualReferences(c);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLAnonymousIndividual, Set<OWLAxiom>> getOwlAnonymousIndividualReferences() {
		Lock l = locks.get(owlAnonymousIndividualReferences).readLock();
		l.lock();
		try {
			return super.getOwlAnonymousIndividualReferences();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeOwlAnonymousIndividualReferences(OWLAnonymousIndividual c,
			OWLAxiom ax) {
		Lock l = locks.get(owlAnonymousIndividualReferences).writeLock();
		l.lock();
		try {
			super.removeOwlAnonymousIndividualReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addOwlAnonymousIndividualReferences(OWLAnonymousIndividual c, OWLAxiom ax) {
		Lock l = locks.get(owlAnonymousIndividualReferences).writeLock();
		l.lock();
		try {
			super.addOwlAnonymousIndividualReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsOwlAnonymousIndividualReferences(OWLAnonymousIndividual c) {
		Lock l = locks.get(owlAnonymousIndividualReferences).readLock();
		l.lock();
		try {
			return super.containsOwlAnonymousIndividualReferences(c);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLDatatype, Set<OWLAxiom>> getOwlDatatypeReferences() {
		Lock l = locks.get(owlDatatypeReferences).readLock();
		l.lock();
		try {
			return super.getOwlDatatypeReferences();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeOwlDatatypeReferences(OWLDatatype c, OWLAxiom ax) {
		Lock l = locks.get(owlDatatypeReferences).writeLock();
		l.lock();
		try {
			super.removeOwlDatatypeReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addOwlDatatypeReferences(OWLDatatype c, OWLAxiom ax) {
		Lock l = locks.get(owlDatatypeReferences).writeLock();
		l.lock();
		try {
			super.addOwlDatatypeReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsOwlDatatypeReferences(OWLDatatype c) {
		Lock l = locks.get(owlDatatypeReferences).readLock();
		l.lock();
		try {
			return super.containsOwlDatatypeReferences(c);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLAnnotationProperty, Set<OWLAxiom>> getOwlAnnotationPropertyReferences() {
		Lock l = locks.get(owlAnnotationPropertyReferences).readLock();
		l.lock();
		try {
			return super.getOwlAnnotationPropertyReferences();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeOwlAnnotationPropertyReferences(OWLAnnotationProperty c, OWLAxiom ax) {
		Lock l = locks.get(owlAnnotationPropertyReferences).writeLock();
		l.lock();
		try {
			super.removeOwlAnnotationPropertyReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addOwlAnnotationPropertyReferences(OWLAnnotationProperty c, OWLAxiom ax) {
		Lock l = locks.get(owlAnnotationPropertyReferences).writeLock();
		l.lock();
		try {
			super.addOwlAnnotationPropertyReferences(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsOwlAnnotationPropertyReferences(OWLAnnotationProperty c) {
		Lock l = locks.get(owlAnnotationPropertyReferences).readLock();
		l.lock();
		try {
			return super.containsOwlAnnotationPropertyReferences(c);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public Map<OWLEntity, Set<OWLDeclarationAxiom>> getDeclarationsByEntity() {
		Lock l = locks.get(declarationsByEntity).readLock();
		l.lock();
		try {
			return super.getDeclarationsByEntity();
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void removeDeclarationsByEntity(OWLEntity c, OWLDeclarationAxiom ax) {
		Lock l = locks.get(declarationsByEntity).writeLock();
		l.lock();
		try {
			super.removeDeclarationsByEntity(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public void addDeclarationsByEntity(OWLEntity c, OWLDeclarationAxiom ax) {
		Lock l = locks.get(declarationsByEntity).writeLock();
		l.lock();
		try {
			super.addDeclarationsByEntity(c, ax);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public boolean containsDeclarationsByEntity(OWLEntity c) {
		Lock l = locks.get(declarationsByEntity).readLock();
		l.lock();
		try {
			return super.containsDeclarationsByEntity(c);
		}
		finally {
			l.unlock();
		}
	}
}