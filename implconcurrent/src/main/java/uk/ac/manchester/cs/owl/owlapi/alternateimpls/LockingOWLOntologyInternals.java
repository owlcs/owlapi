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

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.MultiMap;

import uk.ac.manchester.cs.owl.owlapi.Internals;
import uk.ac.manchester.cs.owl.owlapi.InternalsImpl;


/**
 * @author ignazio threadsafe implementation
 */
public class LockingOWLOntologyInternals extends InternalsImpl {
	private Map<Object, ReadWriteLock> locks;

	@SuppressWarnings("javadoc")
	public LockingOWLOntologyInternals() {
		locks = new IdentityHashMap<Object, ReadWriteLock>();
		importsDeclarations = CollectionFactory.createSyncSet();
		locks.put(importsDeclarations, new ReentrantReadWriteLock());
		ontologyAnnotations = CollectionFactory.createSyncSet();
		locks.put(ontologyAnnotations, new ReentrantReadWriteLock());
		locks.put(axiomsByType, new ReentrantReadWriteLock());
		//locks.put(logicalAxiom2AnnotatedAxiomMap, new ReentrantReadWriteLock());
		generalClassAxioms = CollectionFactory.createSyncSet();
		locks.put(generalClassAxioms, new ReentrantReadWriteLock());
		propertyChainSubPropertyAxioms = CollectionFactory.createSyncSet();
		locks.put(propertyChainSubPropertyAxioms, new ReentrantReadWriteLock());
		locks.put(owlClassReferences, new ReentrantReadWriteLock());
		locks.put(owlObjectPropertyReferences, new ReentrantReadWriteLock());
		locks.put(owlDataPropertyReferences, new ReentrantReadWriteLock());
		locks.put(owlIndividualReferences, new ReentrantReadWriteLock());
		locks.put(owlAnonymousIndividualReferences, new ReentrantReadWriteLock());
		locks.put(owlDatatypeReferences, new ReentrantReadWriteLock());
		locks.put(owlAnnotationPropertyReferences, new ReentrantReadWriteLock());
		locks.put(declarationsByEntity, new ReentrantReadWriteLock());
		locks.put(getClassAxiomsByClass(), new ReentrantReadWriteLock());
		locks.put(getSubClassAxiomsByLHS(), new ReentrantReadWriteLock());
		locks.put(getSubClassAxiomsByRHS(), new ReentrantReadWriteLock());
		locks.put(getEquivalentClassesAxiomsByClass(), new ReentrantReadWriteLock());
		locks.put(getDisjointClassesAxiomsByClass(), new ReentrantReadWriteLock());
		locks.put(getDisjointUnionAxiomsByClass(), new ReentrantReadWriteLock());
		locks.put(getHasKeyAxiomsByClass(), new ReentrantReadWriteLock());
		locks.put(getObjectSubPropertyAxiomsByLHS(), new ReentrantReadWriteLock());
		locks.put(getObjectSubPropertyAxiomsByRHS(), new ReentrantReadWriteLock());
		locks.put(getEquivalentObjectPropertyAxiomsByProperty(),
				new ReentrantReadWriteLock());
		locks.put(getDisjointObjectPropertyAxiomsByProperty(),
				new ReentrantReadWriteLock());
		locks.put(getObjectPropertyDomainAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getObjectPropertyRangeAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getFunctionalObjectPropertyAxiomsByProperty(),
				new ReentrantReadWriteLock());
		locks.put(getInverseFunctionalPropertyAxiomsByProperty(),
				new ReentrantReadWriteLock());
		locks.put(getSymmetricPropertyAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getAsymmetricPropertyAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getReflexivePropertyAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getIrreflexivePropertyAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getTransitivePropertyAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getInversePropertyAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getDataSubPropertyAxiomsByLHS(), new ReentrantReadWriteLock());
		locks.put(getDataSubPropertyAxiomsByRHS(), new ReentrantReadWriteLock());
		locks.put(getEquivalentDataPropertyAxiomsByProperty(),
				new ReentrantReadWriteLock());
		locks.put(getDisjointDataPropertyAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getDataPropertyDomainAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getDataPropertyRangeAxiomsByProperty(), new ReentrantReadWriteLock());
		locks.put(getFunctionalDataPropertyAxiomsByProperty(),
				new ReentrantReadWriteLock());
		locks.put(getClassAssertionAxiomsByIndividual(), new ReentrantReadWriteLock());
		locks.put(getClassAssertionAxiomsByClass(), new ReentrantReadWriteLock());
		locks.put(getObjectPropertyAssertionsByIndividual(), new ReentrantReadWriteLock());
		locks.put(getDataPropertyAssertionsByIndividual(), new ReentrantReadWriteLock());
		locks.put(getNegativeObjectPropertyAssertionAxiomsByIndividual(),
				new ReentrantReadWriteLock());
		locks.put(getNegativeDataPropertyAssertionAxiomsByIndividual(),
				new ReentrantReadWriteLock());
		locks.put(getDifferentIndividualsAxiomsByIndividual(),
				new ReentrantReadWriteLock());
		locks.put(getSameIndividualsAxiomsByIndividual(), new ReentrantReadWriteLock());
		locks.put(getAnnotationAssertionAxiomsBySubject(), new ReentrantReadWriteLock());
	}

	@Override
	protected <K, V> MultiMap<K, V> buildMap() {
		return new MultiMap<K, V>(true, false);
	}

	@Override
	public <K, V extends OWLAxiom> Set<K> getKeyset(Pointer<K, V> pointer) {
		final MapPointer<K, V> mapPointer = (MapPointer<K, V>) pointer;
		if (mapPointer.getMap() == null) {
			Lock l = locks.get(mapPointer).writeLock();
			l.lock();
			try {
				mapPointer.init();
			}
			finally {
				l.unlock();
			}
		}
		Lock l = locks.get(mapPointer).readLock();
		l.lock();
		try {
			return CollectionFactory.getCopyOnRequestSet(mapPointer.getMap().keySet());
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public <K, V extends OWLAxiom> Set<V> getValues(Pointer<K, V> pointer, K key) {
		final MapPointer<K, V> mapPointer = (MapPointer<K, V>) pointer;
		if (mapPointer.getMap() == null) {
			Lock l = locks.get(mapPointer).writeLock();
			l.lock();
			try {
				mapPointer.init();
			}
			finally {
				l.unlock();
			}
		}
		Lock l = locks.get(mapPointer).readLock();
		l.lock();
		try {
			return CollectionFactory.getCopyOnRequestSet(mapPointer.getMap().get(key));
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public <K, V extends OWLAxiom> boolean remove(Internals.Pointer<K, V> pointer, K k,
			V v) {
		final MapPointer<K, V> mapPointer = (MapPointer<K, V>) pointer;
		Lock l = locks.get(mapPointer).writeLock();
		l.lock();
		try {
			return super.remove(pointer, k, v);
		}
		finally {
			l.unlock();
		}
	}

	@Override
	public <K, V extends OWLAxiom> boolean add(Pointer<K, V> p, K k, V v) {
		final MapPointer<K, V> mapPointer = (MapPointer<K, V>) p;
		if (locks.get(p) == null) {
			System.out.println("LockingOWLOntologyInternals.add()");
		}
		Lock l = locks.get(mapPointer).writeLock();
		l.lock();
		try {
			return super.add(p, k, v);
		}
		finally {
			l.unlock();
		}
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

	@Override
	public boolean isEmpty() {
		Lock l = locks.get(axiomsByType).readLock();
		l.lock();
		try {
			return super.isEmpty();
		}
		finally {
			l.unlock();
		}
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
}