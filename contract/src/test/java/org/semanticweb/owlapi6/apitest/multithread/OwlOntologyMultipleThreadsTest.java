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
package org.semanticweb.owlapi6.apitest.multithread;

import static org.semanticweb.owlapi6.model.parameters.AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi6.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi6.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;

public class OwlOntologyMultipleThreadsTest extends TestBase {

    private static class TestCallback implements Runnable {

        private final OWLOntology o1;
        private final OWLOntology o2;

        TestCallback(OWLOntology o1, OWLOntology o2) {
            this.o1 = o1;
            this.o2 = o2;
        }

        void consume(Object o) {}

        @Override
        public void run() {
            for (int index = 0; index < 100; index++) {
                o1.isEmpty();
                o1.annotationsAsList().forEach(this::consume);
                o1.signature(INCLUDED).forEach(this::consume);
                o1.signature(EXCLUDED).forEach(this::consume);
                o1.getOWLOntologyManager();
                o1.getOntologyID();
                o1.isAnonymous();
                o1.directImportsDocuments().forEach(this::consume);
                o1.directImports().forEach(this::consume);
                o1.imports().forEach(this::consume);
                o1.importsClosure().forEach(this::consume);
                o1.importsDeclarations().forEach(this::consume);
                o1.axioms().forEach(this::consume);
                o1.getAxiomCount();
                List<OWLClass> classes = asList(o1.classesInSignature());
                o1.classesInSignature(INCLUDED).forEach(this::consume);
                o1.classesInSignature(EXCLUDED).forEach(this::consume);
                List<OWLObjectProperty> objectProperties = asList(o1.objectPropertiesInSignature(INCLUDED));
                o1.objectPropertiesInSignature(EXCLUDED).forEach(this::consume);
                o1.objectPropertiesInSignature().forEach(this::consume);
                List<OWLDataProperty> dataProperties = asList(o1.dataPropertiesInSignature());
                o1.dataPropertiesInSignature(INCLUDED).forEach(this::consume);
                o1.dataPropertiesInSignature(EXCLUDED).forEach(this::consume);
                List<OWLNamedIndividual> individuals = asList(o1.individualsInSignature());
                o1.individualsInSignature(INCLUDED).forEach(this::consume);
                o1.individualsInSignature(EXCLUDED).forEach(this::consume);
                List<OWLAnonymousIndividual> anonIndividuals = asList(o1.referencedAnonymousIndividuals(EXCLUDED));
                o1.datatypesInSignature().forEach(this::consume);
                o1.datatypesInSignature(INCLUDED).forEach(this::consume);
                o1.datatypesInSignature(EXCLUDED).forEach(this::consume);
                o1.annotationPropertiesInSignature(EXCLUDED).forEach(this::consume);
                for (OWLObjectProperty o : objectProperties) {
                    o1.axioms(o, EXCLUDED).forEach(this::consume);
                    o1.containsObjectPropertyInSignature(o.getIRI(), EXCLUDED);
                    o1.containsObjectPropertyInSignature(o.getIRI(), INCLUDED);
                    o1.containsObjectPropertyInSignature(o.getIRI(), EXCLUDED);
                    o1.objectSubPropertyAxiomsForSubProperty(o).forEach(this::consume);
                    o1.objectSubPropertyAxiomsForSuperProperty(o).forEach(this::consume);
                    o1.objectPropertyDomainAxioms(o).forEach(this::consume);
                    o1.objectPropertyRangeAxioms(o).forEach(this::consume);
                    o1.inverseObjectPropertyAxioms(o).forEach(this::consume);
                    o1.equivalentObjectPropertiesAxioms(o).forEach(this::consume);
                    o1.disjointObjectPropertiesAxioms(o).forEach(this::consume);
                    o1.functionalObjectPropertyAxioms(o).forEach(this::consume);
                    o1.inverseFunctionalObjectPropertyAxioms(o).forEach(this::consume);
                    o1.symmetricObjectPropertyAxioms(o).forEach(this::consume);
                    o1.asymmetricObjectPropertyAxioms(o).forEach(this::consume);
                    o1.reflexiveObjectPropertyAxioms(o).forEach(this::consume);
                    o1.irreflexiveObjectPropertyAxioms(o).forEach(this::consume);
                    o1.transitiveObjectPropertyAxioms(o).forEach(this::consume);
                }
                for (OWLClass c : classes) {
                    o1.axioms(c, EXCLUDED).forEach(this::consume);
                    o1.containsClassInSignature(c.getIRI(), EXCLUDED);
                    o1.containsClassInSignature(c.getIRI(), INCLUDED);
                    o1.containsClassInSignature(c.getIRI(), EXCLUDED);
                    o1.subClassAxiomsForSubClass(c).forEach(this::consume);
                    o1.subClassAxiomsForSuperClass(c).forEach(this::consume);
                    o1.equivalentClassesAxioms(c).forEach(this::consume);
                    o1.disjointClassesAxioms(c).forEach(this::consume);
                    o1.disjointUnionAxioms(c).forEach(this::consume);
                    o1.hasKeyAxioms(c).forEach(this::consume);
                    o1.classAssertionAxioms(c).forEach(this::consume);
                }
                for (OWLDataProperty p : dataProperties) {
                    o1.axioms(p, EXCLUDED).forEach(this::consume);
                    o1.containsDataPropertyInSignature(p.getIRI(), EXCLUDED);
                    o1.containsDataPropertyInSignature(p.getIRI(), INCLUDED);
                    o1.containsDataPropertyInSignature(p.getIRI(), EXCLUDED);
                    o1.dataSubPropertyAxiomsForSubProperty(p).forEach(this::consume);
                    o1.dataSubPropertyAxiomsForSuperProperty(p).forEach(this::consume);
                    o1.dataPropertyDomainAxioms(p).forEach(this::consume);
                    o1.dataPropertyRangeAxioms(p).forEach(this::consume);
                    o1.equivalentDataPropertiesAxioms(p).forEach(this::consume);
                    o1.disjointDataPropertiesAxioms(p).forEach(this::consume);
                    o1.functionalDataPropertyAxioms(p).forEach(this::consume);
                }
                for (OWLNamedIndividual i : individuals) {
                    o1.axioms(i, EXCLUDED).forEach(this::consume);
                    o1.containsIndividualInSignature(i.getIRI(), EXCLUDED);
                    o1.containsIndividualInSignature(i.getIRI(), INCLUDED);
                    o1.containsIndividualInSignature(i.getIRI(), EXCLUDED);
                    o1.classAssertionAxioms(i).forEach(this::consume);
                    o1.dataPropertyAssertionAxioms(i).forEach(this::consume);
                    o1.objectPropertyAssertionAxioms(i).forEach(this::consume);
                    o1.negativeObjectPropertyAssertionAxioms(i).forEach(this::consume);
                    o1.negativeDataPropertyAssertionAxioms(i).forEach(this::consume);
                    o1.sameIndividualAxioms(i).forEach(this::consume);
                    o1.differentIndividualAxioms(i).forEach(this::consume);
                }
                for (OWLAnonymousIndividual i : anonIndividuals) {
                    assert i != null;
                    o1.axioms(i, EXCLUDED).forEach(this::consume);
                }
                for (AxiomType<?> ax : AxiomType.AXIOM_TYPES) {
                    assert ax != null;
                    o1.axioms(ax).forEach(this::consume);
                    o1.axioms(ax, INCLUDED).forEach(this::consume);
                    o1.axioms(ax, EXCLUDED).forEach(this::consume);
                }
                for (OWLDatatype t : asList(o1.datatypesInSignature())) {
                    o1.axioms(t, EXCLUDED).forEach(this::consume);
                    o1.containsDatatypeInSignature(t.getIRI(), EXCLUDED);
                    o1.containsDatatypeInSignature(t.getIRI(), INCLUDED);
                    o1.containsDatatypeInSignature(t.getIRI(), EXCLUDED);
                    o1.datatypeDefinitions(t).forEach(this::consume);
                }
                for (OWLAnnotationProperty p : asList(o1.annotationPropertiesInSignature(EXCLUDED))) {
                    assert p != null;
                    o1.axioms(p, EXCLUDED).forEach(this::consume);
                    o1.containsAnnotationPropertyInSignature(p.getIRI(), EXCLUDED);
                    o1.containsAnnotationPropertyInSignature(p.getIRI(), INCLUDED);
                    o1.containsAnnotationPropertyInSignature(p.getIRI(), EXCLUDED);
                    o1.subAnnotationPropertyOfAxioms(p).forEach(this::consume);
                    o1.annotationPropertyDomainAxioms(p).forEach(this::consume);
                    o1.annotationPropertyRangeAxioms(p).forEach(this::consume);
                }
                for (AxiomType<?> ax : AxiomType.AXIOM_TYPES) {
                    assert ax != null;
                    o1.getAxiomCount(ax);
                    o1.getAxiomCount(ax, INCLUDED);
                    o1.getAxiomCount(ax, EXCLUDED);
                }
                o1.logicalAxioms().forEach(this::consume);
                o1.getLogicalAxiomCount();
                for (OWLAxiom ax : asList(o1.logicalAxioms())) {
                    assert ax != null;
                    o1.containsAxiom(ax);
                    o1.containsAxiom(ax, INCLUDED, IGNORE_AXIOM_ANNOTATIONS);
                    o1.containsAxiom(ax, EXCLUDED, IGNORE_AXIOM_ANNOTATIONS);
                }
                for (OWLAxiom ax : asList(o1.logicalAxioms())) {
                    assert ax != null;
                    o1.containsAxiom(ax, EXCLUDED, IGNORE_AXIOM_ANNOTATIONS);
                    o1.containsAxiom(ax, INCLUDED, IGNORE_AXIOM_ANNOTATIONS);
                    o1.containsAxiom(ax, EXCLUDED, IGNORE_AXIOM_ANNOTATIONS);
                }
                for (OWLAxiom ax : asList(o1.logicalAxioms())) {
                    assert ax != null;
                    o1.axiomsIgnoreAnnotations(ax, EXCLUDED).forEach(this::consume);
                    o1.axiomsIgnoreAnnotations(ax, INCLUDED).forEach(this::consume);
                    o1.axiomsIgnoreAnnotations(ax, EXCLUDED).forEach(this::consume);
                }
                o1.generalClassAxioms().forEach(this::consume);
                anonIndividuals.forEach(i -> o1.referencingAxioms(i, EXCLUDED));
                o1.signature().forEach(e -> {
                    assert e != null;
                    o1.referencingAxioms(e, EXCLUDED).forEach(this::consume);
                    o1.referencingAxioms(e, INCLUDED).forEach(this::consume);
                    o1.referencingAxioms(e, EXCLUDED).forEach(this::consume);
                    o1.declarationAxioms(e).forEach(this::consume);
                    o1.containsEntityInSignature(e, INCLUDED);
                    o1.containsEntityInSignature(e, EXCLUDED);
                    o1.containsEntityInSignature(e);
                    o1.containsEntityInSignature(e.getIRI(), EXCLUDED);
                    o1.containsEntityInSignature(e.getIRI(), INCLUDED);
                    o1.entitiesInSignature(e.getIRI()).forEach(this::consume);
                    o1.entitiesInSignature(e.getIRI(), EXCLUDED).forEach(this::consume);
                    o1.entitiesInSignature(e.getIRI(), INCLUDED).forEach(this::consume);
                    o1.isDeclared(e);
                    o1.isDeclared(e, INCLUDED);
                    o1.isDeclared(e, EXCLUDED);
                    if (e instanceof OWLAnnotationSubject) {
                        o1.annotationAssertionAxioms((OWLAnnotationSubject) e).forEach(this::consume);
                    }
                });
                List<OWLAxiom> axioms = asList(o1.axioms());
                for (OWLAxiom ax : axioms) {
                    o1.add(ax);
                    o2.remove(ax);
                }
            }
        }
    }

    @Test
    public void testLockingOwlOntologyImpl() throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(TestFiles.KOALA, new RDFXMLDocumentFormat());
        MultiThreadChecker checker = new MultiThreadChecker(5);
        checker.check(new TestCallback(o, m.createOntology()));
        String trace = checker.getTrace();
        System.out.println(trace);
    }

    static class MultiThreadChecker {

        public static final int defaultRep = 10;
        protected int rep = defaultRep;
        protected final PrintStream p;
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();
        private boolean successful = false;

        public MultiThreadChecker(int i) {
            this();
            if (i > 0) {
                rep = i;
            }
        }

        public MultiThreadChecker() {
            p = new PrintStream(out);
        }

        public void check(Runnable cb) {
            AtomicLong counter = new AtomicLong(0);
            final long start = System.currentTimeMillis();
            ExecutorService service = Executors.newFixedThreadPool(rep);
            List<Callable<Object>> list = new ArrayList<>();
            for (int i = 0; i < rep * rep; i++) {
                list.add(() -> {
                    try {
                        cb.run();
                        counter.incrementAndGet();
                    } catch (Throwable e) {
                        e.printStackTrace(p);
                        printout(start, counter);
                    }
                    return null;
                });
            }
            long end = System.currentTimeMillis();
            try {
                service.invokeAll(list);
                end = System.currentTimeMillis() - end;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printout(end, counter);
        }

        protected void printout(long end, AtomicLong counter) {
            long expected = rep * rep;
            p.println(
                "elapsed time (ms): " + end + "\nSuccessful threads: " + counter.get() + "\t expected: " + expected);
            successful = counter.get() == expected;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public String getTrace() {
            p.flush();
            return out.toString();
        }
    }
}
