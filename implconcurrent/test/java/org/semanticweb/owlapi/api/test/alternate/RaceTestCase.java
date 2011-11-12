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

package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.TestCase;

import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.apibinding.configurables.ThreadSafeOWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.RaceCallback;
@SuppressWarnings("javadoc")
public class RaceTestCase extends TestCase {
	private static final String A_CLASS = "http://www.race.org#testclass";
	public static final String NS = "http://www.race.org#";
	protected RaceCallback callback;
	private Runnable writer = new Runnable() {
		public void run() {
			while (!done.get()) {
				callback.add();
			}
			callback.add();
		}
	};
	final AtomicBoolean done = new AtomicBoolean(false);
	ExecutorService exec = Executors.newFixedThreadPool(5);

	public RaceTestCase(RaceCallback c) throws OWLOntologyCreationException {
		callback = c;
	}

	public RaceTestCase() {
		try {
			callback= new SubClassLHSCallback();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	public void racing() throws InterruptedException {
		exec.submit(writer);
		callback.race();
		done.set( true);
		exec.shutdown();
		exec.awaitTermination(5, TimeUnit.SECONDS);
	}

	public void testSubClassLHS() throws Exception{

			final int totalRepetitions = 10000;
			int repetitions = 0;
			RaceTestCase r;
			do {
				repetitions++;
				r = new RaceTestCase(new SubClassLHSCallback());
				r.racing();
			} while (!r.callback.failed() && repetitions < totalRepetitions);
			if (r.callback.failed()) {
				r.callback.diagnose();
				fail("Failed after " + repetitions + " repetition(s).");
			} else {
				System.out.println("No race condition found in "
						+ totalRepetitions + " repetitiions");
			}
	}

	public static class SubClassLHSCallback implements RaceCallback {
		private volatile int counter = 0;
		OWLDataFactory factory;
		OWLOntologyManager manager;
		OWLOntology ontology;
		OWLClass x;
		OWLClass y;

		public SubClassLHSCallback() throws OWLOntologyCreationException {
			Factory.setFactory(new ThreadSafeOWLManager());
			manager = Factory.getManager();
			factory = manager.getOWLDataFactory();
			ontology = manager.createOntology();
			x = factory.getOWLClass(IRI.create(NS + "X"));
			y = factory.getOWLClass(IRI.create(NS + "Y"));
		}

		public void add() {
			OWLClass middle = createMiddleClass(counter);
			Set<OWLAxiom> axioms = computeChanges(middle);
			manager.addAxioms(ontology, axioms);
			counter++;
		}

		public boolean failed() {
			int size = computeSize();
			return size != counter;
		}

		public int computeSize() {
			return ontology.getSubClassAxiomsForSubClass(x).size();
		}

		public Set<OWLAxiom> computeChanges(OWLClass middle) {
			OWLAxiom axiom1 = factory.getOWLSubClassOfAxiom(x, middle);
			OWLAxiom axiom2 = factory.getOWLSubClassOfAxiom(middle, y);
			Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
			axioms.add(axiom1);
			axioms.add(axiom2);
			return axioms;
		}

		public void diagnose() {
			Set<OWLSubClassOfAxiom> axiomsFound = ontology
					.getSubClassAxiomsForSubClass(x);
			System.out
					.println("Expected getSubClassAxiomsForSubClass to return "
							+ counter + " axioms but it only found "
							+ axiomsFound.size());
			for (int i = 0; i < counter; i++) {
				OWLAxiom checkMe = factory.getOWLSubClassOfAxiom(x,
						createMiddleClass(i));
				if (!axiomsFound.contains(checkMe)
						&& ontology.containsAxiom(checkMe)) {
					System.out
							.println(checkMe.toString()
									+ " is an axiom in the ontology that is not found by getSubClassAxiomsForSubClass");
					return;
				}
			}
		}

		public void race() {
			ontology.getSubClassAxiomsForSubClass(factory.getOWLClass(IRI
					.create(A_CLASS)));
		}

		public OWLClass createMiddleClass(int i) {
			return factory.getOWLClass(IRI.create(NS + "P" + i));
		}
	}
}
