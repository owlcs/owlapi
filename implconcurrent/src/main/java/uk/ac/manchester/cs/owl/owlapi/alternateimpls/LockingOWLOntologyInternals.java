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

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;

import uk.ac.manchester.cs.owl.owlapi.ClassAxiomByClassPointer;
import uk.ac.manchester.cs.owl.owlapi.InternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.MapPointer;

/**
 * @author ignazio threadsafe implementation
 */
public class LockingOWLOntologyInternals extends InternalsImpl {
	private static final long serialVersionUID = -6742647487412288043L;

	@Override
	protected <K, V extends OWLAxiom> MapPointer<K, V> build(AxiomType<?> t,
			OWLAxiomVisitorEx<?> v) {
		return new SyncMapPointer<K, V>(t, v, true, this);
	}

	@Override
	protected <K, V extends OWLAxiom> MapPointer<K, V> buildLazy(AxiomType<?> t,
			OWLAxiomVisitorEx<?> v) {
		return new SyncMapPointer<K, V>(t, v, false, this);
	}

	@Override
	protected ClassAxiomByClassPointer buildClassAxiomByClass() {
		return new ClassAxiomByClassPointer(null, null, false, this) {

			private static final long serialVersionUID = -7264777242566648150L;

			@Override
			public synchronized boolean contains(OWLClass key, OWLClassAxiom value) {
				return super.contains(key, value);
			}

			@Override
			public synchronized boolean containsKey(OWLClass key) {
				return super.containsKey(key);
			}

			@Override
			public synchronized Set<OWLClassAxiom> getAllValues() {
				return super.getAllValues();
			}

			@Override
			public synchronized Set<OWLClassAxiom> getValues(OWLClass key) {
				return super.getValues(key);
			}

			@Override
			public synchronized void init() {
				super.init();
			}

			@Override
			public synchronized boolean isInitialized() {
				return super.isInitialized();
			}

			@Override
			public synchronized Set<OWLClass> keySet() {
				return super.keySet();
			}

			@Override
			public synchronized boolean put(OWLClass key, OWLClassAxiom value) {
				return super.put(key, value);
			}

			@Override
			public synchronized boolean remove(OWLClass key, OWLClassAxiom value) {
				return super.remove(key, value);
			}

			@Override
			public synchronized int size() {
				return super.size();
			}
		};
	}

	@Override
	protected <K> SetPointer<K> buildSet() {
		return new SetPointer<K>(CollectionFactory.<K> createSet()) {
			private static final long serialVersionUID = 5035692713549943611L;

			@Override
			public synchronized boolean add(K k) {
				return super.add(k);
			}

			@Override
			public synchronized boolean contains(K k) {
				return super.contains(k);
			}

			@Override
			public synchronized Set<K> copy() {
				return super.copy();
			}

			@Override
			public synchronized boolean isEmpty() {
				return super.isEmpty();
			}

			@Override
			public synchronized boolean remove(K k) {
				return super.remove(k);
			}
		};
	}

	@SuppressWarnings("javadoc")
	public LockingOWLOntologyInternals() {}
}