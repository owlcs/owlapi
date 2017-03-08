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

import static org.semanticweb.owlapi.model.parameters.AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class OwlOntologyMultipleThreadsTest extends TestBase {

    private static final @Nonnull String KOALA = "<?xml version=\"1.0\"?>\n"
                    + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\" xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n"
                    + "  <owl:Ontology rdf:about=\"\"/>\n"
                    + "  <owl:Class rdf:ID=\"Female\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender rdf:ID=\"female\"/></owl:hasValue></owl:Restriction></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Marsupials\"><owl:disjointWith><owl:Class rdf:about=\"#Person\"/></owl:disjointWith><rdfs:subClassOf><owl:Class rdf:about=\"#Animal\"/></rdfs:subClassOf></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Student\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Person\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue></owl:Restriction><owl:Restriction><owl:someValuesFrom><owl:Class rdf:about=\"#University\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"KoalaWithPhD\"><owl:versionInfo>1.2</owl:versionInfo><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:hasValue><Degree rdf:ID=\"PhD\"/></owl:hasValue><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasDegree\"/></owl:onProperty></owl:Restriction><owl:Class rdf:about=\"#Koala\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"University\"><rdfs:subClassOf><owl:Class rdf:ID=\"Habitat\"/></rdfs:subClassOf></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Koala\"><rdfs:subClassOf><owl:Restriction><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</owl:hasValue><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:someValuesFrom><owl:Class rdf:about=\"#DryEucalyptForest\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Animal\"><rdfs:seeAlso>Male</rdfs:seeAlso><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasHabitat\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:cardinality><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><owl:versionInfo>1.1</owl:versionInfo></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Forest\"><rdfs:subClassOf rdf:resource=\"#Habitat\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Rainforest\"><rdfs:subClassOf rdf:resource=\"#Forest\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"GraduateStudent\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasDegree\"/></owl:onProperty><owl:someValuesFrom><owl:Class><owl:oneOf rdf:parseType=\"Collection\"><Degree rdf:ID=\"BA\"/><Degree rdf:ID=\"BS\"/></owl:oneOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Student\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Parent\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Animal\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass><rdfs:subClassOf rdf:resource=\"#Animal\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"DryEucalyptForest\"><rdfs:subClassOf rdf:resource=\"#Forest\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Quokka\"><rdfs:subClassOf><owl:Restriction><owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue><owl:onProperty><owl:FunctionalProperty rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"TasmanianDevil\"><rdfs:subClassOf rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"MaleStudentWith3Daughters\"><owl:equivalentClass><owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Class rdf:about=\"#Student\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender rdf:ID=\"male\"/></owl:hasValue></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">3</owl:cardinality></owl:Restriction><owl:Restriction><owl:allValuesFrom rdf:resource=\"#Female\"/><owl:onProperty><owl:ObjectProperty rdf:about=\"#hasChildren\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Degree\"/>\n  <owl:Class rdf:ID=\"Gender\"/>\n"
                    + "  <owl:Class rdf:ID=\"Male\"><owl:equivalentClass><owl:Restriction><owl:hasValue rdf:resource=\"#male\"/><owl:onProperty><owl:FunctionalProperty rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></owl:equivalentClass></owl:Class>\n"
                    + "  <owl:Class rdf:ID=\"Person\"><rdfs:subClassOf rdf:resource=\"#Animal\"/><owl:disjointWith rdf:resource=\"#Marsupials\"/></owl:Class>\n"
                    + "  <owl:ObjectProperty rdf:ID=\"hasHabitat\"><rdfs:range rdf:resource=\"#Habitat\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n"
                    + "  <owl:ObjectProperty rdf:ID=\"hasDegree\"><rdfs:domain rdf:resource=\"#Person\"/><rdfs:range rdf:resource=\"#Degree\"/></owl:ObjectProperty>\n"
                    + "  <owl:ObjectProperty rdf:ID=\"hasChildren\"><rdfs:range rdf:resource=\"#Animal\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n"
                    + "  <owl:FunctionalProperty rdf:ID=\"hasGender\"><rdfs:range rdf:resource=\"#Gender\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/><rdfs:domain rdf:resource=\"#Animal\"/></owl:FunctionalProperty>\n"
                    + "  <owl:FunctionalProperty rdf:ID=\"isHardWorking\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/><rdfs:domain rdf:resource=\"#Person\"/><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/></owl:FunctionalProperty>\n"
                    + "  <Degree rdf:ID=\"MA\"/>\n</rdf:RDF>";

    private static class TestCallback implements Runnable {

        private final OWLOntology o1;
        private final OWLOntology o2;

        TestCallback(OWLOntology o1, OWLOntology o2) {
            this.o1 = o1;
            this.o2 = o2;
        }

        @Override
        public void run() {
            for (int index = 0; index < 100; index++) {
                o1.isEmpty();
                asList(o1.annotations());
                asList(o1.signature(INCLUDED));
                asList(o1.signature(EXCLUDED));
                o1.getOWLOntologyManager();
                o1.getOntologyID();
                o1.isAnonymous();
                asList(o1.directImportsDocuments());
                asList(o1.directImports());
                asList(o1.imports());
                asList(o1.importsClosure());
                asList(o1.importsDeclarations());
                asList(o1.axioms());
                o1.getAxiomCount();
                List<OWLClass> classes = asList(o1.classesInSignature());
                asList(o1.classesInSignature(INCLUDED));
                asList(o1.classesInSignature(EXCLUDED));
                List<OWLObjectProperty> objectProperties =
                                asList(o1.objectPropertiesInSignature(INCLUDED));
                asList(o1.objectPropertiesInSignature(EXCLUDED));
                asList(o1.objectPropertiesInSignature());
                List<OWLDataProperty> dataProperties = asList(o1.dataPropertiesInSignature());
                asList(o1.dataPropertiesInSignature(INCLUDED));
                asList(o1.dataPropertiesInSignature(EXCLUDED));
                List<OWLNamedIndividual> individuals = asList(o1.individualsInSignature());
                asList(o1.individualsInSignature(INCLUDED));
                asList(o1.individualsInSignature(EXCLUDED));
                List<OWLAnonymousIndividual> anonIndividuals =
                                asList(o1.referencedAnonymousIndividuals(EXCLUDED));
                asList(o1.datatypesInSignature());
                asList(o1.datatypesInSignature(INCLUDED));
                asList(o1.datatypesInSignature(EXCLUDED));
                asList(o1.annotationPropertiesInSignature(EXCLUDED));
                for (OWLObjectProperty o : objectProperties) {
                    asList(o1.axioms(o, EXCLUDED));
                    o1.containsObjectPropertyInSignature(o.getIRI(), EXCLUDED);
                    o1.containsObjectPropertyInSignature(o.getIRI(), INCLUDED);
                    o1.containsObjectPropertyInSignature(o.getIRI(), EXCLUDED);
                    asList(o1.objectSubPropertyAxiomsForSubProperty(o));
                    asList(o1.objectSubPropertyAxiomsForSuperProperty(o));
                    asList(o1.objectPropertyDomainAxioms(o));
                    asList(o1.objectPropertyRangeAxioms(o));
                    asList(o1.inverseObjectPropertyAxioms(o));
                    asList(o1.equivalentObjectPropertiesAxioms(o));
                    asList(o1.disjointObjectPropertiesAxioms(o));
                    asList(o1.functionalObjectPropertyAxioms(o));
                    asList(o1.inverseFunctionalObjectPropertyAxioms(o));
                    asList(o1.symmetricObjectPropertyAxioms(o));
                    asList(o1.asymmetricObjectPropertyAxioms(o));
                    asList(o1.reflexiveObjectPropertyAxioms(o));
                    asList(o1.irreflexiveObjectPropertyAxioms(o));
                    asList(o1.transitiveObjectPropertyAxioms(o));
                }
                for (OWLClass c : classes) {
                    asList(o1.axioms(c, EXCLUDED));
                    o1.containsClassInSignature(c.getIRI(), EXCLUDED);
                    o1.containsClassInSignature(c.getIRI(), INCLUDED);
                    o1.containsClassInSignature(c.getIRI(), EXCLUDED);
                    asList(o1.subClassAxiomsForSubClass(c));
                    asList(o1.subClassAxiomsForSuperClass(c));
                    asList(o1.equivalentClassesAxioms(c));
                    asList(o1.disjointClassesAxioms(c));
                    asList(o1.disjointUnionAxioms(c));
                    asList(o1.hasKeyAxioms(c));
                    asList(o1.classAssertionAxioms(c));
                }
                for (OWLDataProperty p : dataProperties) {
                    asList(o1.axioms(p, EXCLUDED));
                    o1.containsDataPropertyInSignature(p.getIRI(), EXCLUDED);
                    o1.containsDataPropertyInSignature(p.getIRI(), INCLUDED);
                    o1.containsDataPropertyInSignature(p.getIRI(), EXCLUDED);
                    asList(o1.dataSubPropertyAxiomsForSubProperty(p));
                    asList(o1.dataSubPropertyAxiomsForSuperProperty(p));
                    asList(o1.dataPropertyDomainAxioms(p));
                    asList(o1.dataPropertyRangeAxioms(p));
                    asList(o1.equivalentDataPropertiesAxioms(p));
                    asList(o1.disjointDataPropertiesAxioms(p));
                    asList(o1.functionalDataPropertyAxioms(p));
                }
                for (OWLNamedIndividual i : individuals) {
                    asList(o1.axioms(i, EXCLUDED));
                    o1.containsIndividualInSignature(i.getIRI(), EXCLUDED);
                    o1.containsIndividualInSignature(i.getIRI(), INCLUDED);
                    o1.containsIndividualInSignature(i.getIRI(), EXCLUDED);
                    asList(o1.classAssertionAxioms(i));
                    asList(o1.dataPropertyAssertionAxioms(i));
                    asList(o1.objectPropertyAssertionAxioms(i));
                    asList(o1.negativeObjectPropertyAssertionAxioms(i));
                    asList(o1.negativeDataPropertyAssertionAxioms(i));
                    asList(o1.sameIndividualAxioms(i));
                    asList(o1.differentIndividualAxioms(i));
                }
                for (OWLAnonymousIndividual i : anonIndividuals) {
                    assert i != null;
                    asList(o1.axioms(i, EXCLUDED));
                }
                for (AxiomType<?> ax : AxiomType.AXIOM_TYPES) {
                    assert ax != null;
                    asList(o1.axioms(ax));
                    asList(o1.axioms(ax, INCLUDED));
                    asList(o1.axioms(ax, EXCLUDED));
                }
                for (OWLDatatype t : asList(o1.datatypesInSignature())) {
                    asList(o1.axioms(t, EXCLUDED));
                    o1.containsDatatypeInSignature(t.getIRI(), EXCLUDED);
                    o1.containsDatatypeInSignature(t.getIRI(), INCLUDED);
                    o1.containsDatatypeInSignature(t.getIRI(), EXCLUDED);
                    asList(o1.datatypeDefinitions(t));
                }
                for (OWLAnnotationProperty p : asList(
                                o1.annotationPropertiesInSignature(EXCLUDED))) {
                    assert p != null;
                    asList(o1.axioms(p, EXCLUDED));
                    o1.containsAnnotationPropertyInSignature(p.getIRI(), EXCLUDED);
                    o1.containsAnnotationPropertyInSignature(p.getIRI(), INCLUDED);
                    o1.containsAnnotationPropertyInSignature(p.getIRI(), EXCLUDED);
                    asList(o1.subAnnotationPropertyOfAxioms(p));
                    asList(o1.annotationPropertyDomainAxioms(p));
                    asList(o1.annotationPropertyRangeAxioms(p));
                }
                for (AxiomType<?> ax : AxiomType.AXIOM_TYPES) {
                    assert ax != null;
                    o1.getAxiomCount(ax);
                    o1.getAxiomCount(ax, INCLUDED);
                    o1.getAxiomCount(ax, EXCLUDED);
                }
                asList(o1.logicalAxioms());
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
                    asList(o1.axiomsIgnoreAnnotations(ax, EXCLUDED));
                    asList(o1.axiomsIgnoreAnnotations(ax, INCLUDED));
                    asList(o1.axiomsIgnoreAnnotations(ax, EXCLUDED));
                }
                asList(o1.generalClassAxioms());
                anonIndividuals.forEach(i -> asList(o1.referencingAxioms(i, EXCLUDED)));
                o1.signature().forEach(e -> {
                    assert e != null;
                    asList(o1.referencingAxioms(e, EXCLUDED));
                    asList(o1.referencingAxioms(e, INCLUDED));
                    asList(o1.referencingAxioms(e, EXCLUDED));
                    asList(o1.declarationAxioms(e));
                    o1.containsEntityInSignature(e, INCLUDED);
                    o1.containsEntityInSignature(e, EXCLUDED);
                    o1.containsEntityInSignature(e);
                    o1.containsEntityInSignature(e.getIRI(), EXCLUDED);
                    o1.containsEntityInSignature(e.getIRI(), INCLUDED);
                    asList(o1.entitiesInSignature(e.getIRI()));
                    asList(o1.entitiesInSignature(e.getIRI(), EXCLUDED));
                    asList(o1.entitiesInSignature(e.getIRI(), INCLUDED));
                    o1.isDeclared(e);
                    o1.isDeclared(e, INCLUDED);
                    o1.isDeclared(e, EXCLUDED);
                    if (e instanceof OWLAnnotationSubject) {
                        asList(o1.annotationAssertionAxioms((OWLAnnotationSubject) e));
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
        OWLOntology o = loadOntologyFromString(KOALA, new RDFXMLDocumentFormat());
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
            p.println("elapsed time (ms): " + end + "\nSuccessful threads: " + counter.get()
                            + "\t expected: " + expected);
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
