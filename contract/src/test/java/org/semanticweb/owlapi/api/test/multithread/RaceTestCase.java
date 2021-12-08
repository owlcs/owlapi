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
package org.semanticweb.owlapi.api.test.multithread;

import static org.junit.jupiter.api.Assertions.fail;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

class RaceTestCase extends TestBase {

    @Test
    void testSubClassLHS() throws OWLOntologyCreationException, InterruptedException {
        final int totalRepetitions = 200;
        int repetitions = 0;
        RaceTestCaseRunner r;
        do {
            repetitions++;
            r = new RaceTestCaseRunner();
            r.racing();
        } while (!r.callback.failed() && repetitions < totalRepetitions);
        if (r.callback.failed()) {
            r.callback.diagnose();
            fail("Failed after " + repetitions + " repetition(s).");
        }
    }

    static interface RaceCallback {

        void add();

        boolean failed();

        void diagnose();

        void race();
    }

    static class RaceTestCaseRunner {

        static final String NS = "http://www.race.org#";
        protected RaceCallback callback;
        final AtomicBoolean done = new AtomicBoolean(false);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        private final Runnable writer = () -> {
            while (!done.get()) {
                callback.add();
            }
            callback.add();
        };

        RaceTestCaseRunner() throws OWLOntologyCreationException {
            callback = new SubClassLHSCallback();
        }

        void racing() throws InterruptedException {
            exec.submit(writer);
            callback.race();
            done.set(true);
            exec.shutdown();
            exec.awaitTermination(5, TimeUnit.SECONDS);
        }

        static class SubClassLHSCallback implements RaceCallback {

            private final AtomicInteger counter = new AtomicInteger();
            OWLOntologyManager manager;
            OWLOntology ontology;
            OWLClass xClass = Class(NS, "X");
            OWLClass yClass = Class(NS, "Y");

            SubClassLHSCallback() throws OWLOntologyCreationException {
                manager = OWLManager.createConcurrentOWLOntologyManager();
                ontology = manager.createOntology();
            }

            @Override
            public void add() {
                OWLClass middle = createMiddleClass(counter.getAndIncrement());
                Set<OWLAxiom> axioms = computeChanges(middle);
                ontology.add(axioms);
            }

            @Override
            public boolean failed() {
                long size = computeSize();
                return size < counter.get();
            }

            public long computeSize() {
                return ontology.subClassAxiomsForSubClass(xClass).count();
            }

            public Set<OWLAxiom> computeChanges(OWLClass middle) {
                OWLAxiom axiom1 = SubClassOf(xClass, middle);
                OWLAxiom axiom2 = SubClassOf(middle, yClass);
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(axiom1);
                axioms.add(axiom2);
                return axioms;
            }

            @Override
            public void diagnose() {
                List<OWLSubClassOfAxiom> axiomsFound =
                    asList(ontology.subClassAxiomsForSubClass(xClass));
                System.out.println("Expected getSubClassAxiomsForSubClass to return " + counter
                    + " axioms but it only found " + axiomsFound.size());
                for (int index = 0; index < counter.get(); index++) {
                    OWLAxiom checkMe = SubClassOf(xClass, createMiddleClass(index));
                    if (!contains(ontology.subClassAxiomsForSubClass(xClass), checkMe)
                        && ontology.containsAxiom(checkMe)) {
                        System.out.println(checkMe.toString()
                            + " is an axiom in the ontology that is not found by getSubClassAxiomsForSubClass");
                        return;
                    }
                }
            }

            @Override
            public void race() {
                ontology.subClassAxiomsForSubClass(Class("http://www.race.org#", "testclass"))
                    .forEach(this::lose);
            }

            private void lose(@SuppressWarnings("unused") Object o) {
                // no op
            }

            public OWLClass createMiddleClass(int index) {
                return Class(NS, "P" + index);
            }
        }
    }
}
