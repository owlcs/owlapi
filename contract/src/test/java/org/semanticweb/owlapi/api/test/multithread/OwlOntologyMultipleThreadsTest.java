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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;

@SuppressWarnings("javadoc")
public class OwlOntologyMultipleThreadsTest {

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
                asList(o1.signature(Imports.INCLUDED));
                asList(o1.signature(Imports.EXCLUDED));
                List<OWLEntity> entities = asList(o1.signature());
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
                asList(o1.classesInSignature(Imports.INCLUDED));
                asList(o1.classesInSignature(Imports.EXCLUDED));
                List<OWLObjectProperty> objectProperties = asList(o1
                    .objectPropertiesInSignature(Imports.INCLUDED));
                asList(o1.objectPropertiesInSignature(Imports.EXCLUDED));
                asList(o1.objectPropertiesInSignature());
                List<OWLDataProperty> dataProperties = asList(o1
                    .dataPropertiesInSignature());
                asList(o1.dataPropertiesInSignature(Imports.INCLUDED));
                asList(o1.dataPropertiesInSignature(Imports.EXCLUDED));
                List<OWLNamedIndividual> individuals = asList(o1
                    .individualsInSignature());
                asList(o1.individualsInSignature(Imports.INCLUDED));
                asList(o1.individualsInSignature(Imports.EXCLUDED));
                List<OWLAnonymousIndividual> anonIndividuals = asList(o1
                    .referencedAnonymousIndividuals(Imports.EXCLUDED));
                asList(o1.datatypesInSignature());
                asList(o1.datatypesInSignature(Imports.INCLUDED));
                asList(o1.datatypesInSignature(Imports.EXCLUDED));
                asList(o1.annotationPropertiesInSignature(Imports.EXCLUDED));
                for (OWLObjectProperty o : objectProperties) {
                    asList(o1.axioms(o, Imports.EXCLUDED));
                    o1.containsObjectPropertyInSignature(o.getIRI(),
                        Imports.EXCLUDED);
                    o1.containsObjectPropertyInSignature(o.getIRI(),
                        Imports.INCLUDED);
                    o1.containsObjectPropertyInSignature(o.getIRI(),
                        Imports.EXCLUDED);
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
                    asList(o1.axioms(c, Imports.EXCLUDED));
                    o1.containsClassInSignature(c.getIRI(), Imports.EXCLUDED);
                    o1.containsClassInSignature(c.getIRI(), Imports.INCLUDED);
                    o1.containsClassInSignature(c.getIRI(), Imports.EXCLUDED);
                    asList(o1.subClassAxiomsForSubClass(c));
                    asList(o1.subClassAxiomsForSuperClass(c));
                    asList(o1.equivalentClassesAxioms(c));
                    asList(o1.disjointClassesAxioms(c));
                    asList(o1.disjointUnionAxioms(c));
                    asList(o1.hasKeyAxioms(c));
                    asList(o1.classAssertionAxioms(c));
                }
                for (OWLDataProperty p : dataProperties) {
                    asList(o1.axioms(p, Imports.EXCLUDED));
                    o1.containsDataPropertyInSignature(p.getIRI(),
                        Imports.EXCLUDED);
                    o1.containsDataPropertyInSignature(p.getIRI(),
                        Imports.INCLUDED);
                    o1.containsDataPropertyInSignature(p.getIRI(),
                        Imports.EXCLUDED);
                    asList(o1.dataSubPropertyAxiomsForSubProperty(p));
                    asList(o1.dataSubPropertyAxiomsForSuperProperty(p));
                    asList(o1.dataPropertyDomainAxioms(p));
                    asList(o1.dataPropertyRangeAxioms(p));
                    asList(o1.equivalentDataPropertiesAxioms(p));
                    asList(o1.disjointDataPropertiesAxioms(p));
                    asList(o1.functionalDataPropertyAxioms(p));
                }
                for (OWLNamedIndividual i : individuals) {
                    asList(o1.axioms(i, Imports.EXCLUDED));
                    o1.containsIndividualInSignature(i.getIRI(),
                        Imports.EXCLUDED);
                    o1.containsIndividualInSignature(i.getIRI(),
                        Imports.INCLUDED);
                    o1.containsIndividualInSignature(i.getIRI(),
                        Imports.EXCLUDED);
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
                    asList(o1.axioms(i, Imports.EXCLUDED));
                }
                for (AxiomType<?> ax : AxiomType.AXIOM_TYPES) {
                    assert ax != null;
                    asList(o1.axioms(ax));
                    asList(o1.axioms(ax, Imports.INCLUDED));
                    asList(o1.axioms(ax, Imports.EXCLUDED));
                }
                for (OWLDatatype t : asList(o1.datatypesInSignature())) {
                    asList(o1.axioms(t, Imports.EXCLUDED));
                    o1.containsDatatypeInSignature(t.getIRI(), Imports.EXCLUDED);
                    o1.containsDatatypeInSignature(t.getIRI(), Imports.INCLUDED);
                    o1.containsDatatypeInSignature(t.getIRI(), Imports.EXCLUDED);
                    asList(o1.datatypeDefinitions(t));
                }
                for (OWLAnnotationProperty p : asList(o1
                    .annotationPropertiesInSignature(Imports.EXCLUDED))) {
                    assert p != null;
                    asList(o1.axioms(p, Imports.EXCLUDED));
                    o1.containsAnnotationPropertyInSignature(p.getIRI(),
                        Imports.EXCLUDED);
                    o1.containsAnnotationPropertyInSignature(p.getIRI(),
                        Imports.INCLUDED);
                    o1.containsAnnotationPropertyInSignature(p.getIRI(),
                        Imports.EXCLUDED);
                    asList(o1.subAnnotationPropertyOfAxioms(p));
                    asList(o1.annotationPropertyDomainAxioms(p));
                    asList(o1.annotationPropertyRangeAxioms(p));
                }
                for (AxiomType<?> ax : AxiomType.AXIOM_TYPES) {
                    assert ax != null;
                    o1.getAxiomCount(ax);
                    o1.getAxiomCount(ax, Imports.INCLUDED);
                    o1.getAxiomCount(ax, Imports.EXCLUDED);
                }
                asList(o1.logicalAxioms());
                o1.getLogicalAxiomCount();
                for (OWLAxiom ax : asList(o1.logicalAxioms())) {
                    assert ax != null;
                    o1.containsAxiom(ax);
                    o1.containsAxiom(ax, Imports.INCLUDED,
                        AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
                    o1.containsAxiom(ax, Imports.EXCLUDED,
                        AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
                }
                for (OWLAxiom ax : asList(o1.logicalAxioms())) {
                    assert ax != null;
                    o1.containsAxiom(ax, Imports.EXCLUDED,
                        AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
                    o1.containsAxiom(ax, Imports.INCLUDED,
                        AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
                    o1.containsAxiom(ax, Imports.EXCLUDED,
                        AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
                }
                for (OWLAxiom ax : asList(o1.logicalAxioms())) {
                    assert ax != null;
                    asList(o1.axiomsIgnoreAnnotations(ax, Imports.EXCLUDED));
                    asList(o1.axiomsIgnoreAnnotations(ax, Imports.INCLUDED));
                    asList(o1.axiomsIgnoreAnnotations(ax, Imports.EXCLUDED));
                }
                asList(o1.generalClassAxioms());
                for (OWLAnonymousIndividual i : anonIndividuals) {
                    assert i != null;
                    asList(o1.referencingAxioms(i, Imports.EXCLUDED));
                }
                for (OWLEntity e : entities) {
                    assert e != null;
                    asList(o1.referencingAxioms(e, Imports.EXCLUDED));
                    asList(o1.referencingAxioms(e, Imports.INCLUDED));
                    asList(o1.referencingAxioms(e, Imports.EXCLUDED));
                    asList(o1.declarationAxioms(e));
                    o1.containsEntityInSignature(e, Imports.INCLUDED);
                    o1.containsEntityInSignature(e, Imports.EXCLUDED);
                    o1.containsEntityInSignature(e);
                    o1.containsEntityInSignature(e.getIRI(), Imports.EXCLUDED);
                    o1.containsEntityInSignature(e.getIRI(), Imports.INCLUDED);
                    asList(o1.entitiesInSignature(e.getIRI()));
                    asList(o1.entitiesInSignature(e.getIRI(), Imports.EXCLUDED));
                    asList(o1.entitiesInSignature(e.getIRI(), Imports.INCLUDED));
                    o1.isDeclared(e);
                    o1.isDeclared(e, Imports.INCLUDED);
                    o1.isDeclared(e, Imports.EXCLUDED);
                    if (e instanceof OWLAnnotationSubject) {
                        asList(o1.annotationAssertionAxioms((OWLAnnotationSubject) e));
                    }
                }
                List<OWLAxiom> axioms = asList(o1.axioms());
                for (OWLAxiom ax : axioms) {
                    o1.add(ax);
                    o2.remove(ax);
                }
            }
        }
    }

    @Test
    public void testLockingOwlOntologyImpl() {
        String koala = "<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF\n"
            + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "    xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\"\n"
            + "  xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n"
            + "  <owl:Ontology rdf:about=\"\"/>\n"
            + "  <owl:Class rdf:ID=\"Female\">\n"
            + "    <owl:equivalentClass>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n"
            + "        </owl:onProperty>\n"
            + "        <owl:hasValue>\n"
            + "          <Gender rdf:ID=\"female\"/>\n"
            + "        </owl:hasValue>\n"
            + "      </owl:Restriction>\n"
            + "    </owl:equivalentClass>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Marsupials\">\n"
            + "    <owl:disjointWith>\n"
            + "      <owl:Class rdf:about=\"#Person\"/>\n"
            + "    </owl:disjointWith>\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Class rdf:about=\"#Animal\"/>\n"
            + "    </rdfs:subClassOf>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Student\">\n"
            + "    <owl:equivalentClass>\n"
            + "      <owl:Class>\n"
            + "        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
            + "          <owl:Class rdf:about=\"#Person\"/>\n"
            + "          <owl:Restriction>\n"
            + "            <owl:onProperty>\n"
            + "              <owl:FunctionalProperty rdf:about=\"#isHardWorking\"/>\n"
            + "            </owl:onProperty>\n"
            + "            <owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n"
            + "            >true</owl:hasValue>\n"
            + "          </owl:Restriction>\n"
            + "          <owl:Restriction>\n"
            + "            <owl:someValuesFrom>\n"
            + "              <owl:Class rdf:about=\"#University\"/>\n"
            + "            </owl:someValuesFrom>\n"
            + "            <owl:onProperty>\n"
            + "              <owl:ObjectProperty rdf:about=\"#hasHabitat\"/>\n"
            + "            </owl:onProperty>\n"
            + "          </owl:Restriction>\n"
            + "        </owl:intersectionOf>\n"
            + "      </owl:Class>\n"
            + "    </owl:equivalentClass>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"KoalaWithPhD\">\n"
            + "    <owl:versionInfo>1.2</owl:versionInfo>\n"
            + "    <owl:equivalentClass>\n"
            + "      <owl:Class>\n"
            + "        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
            + "          <owl:Restriction>\n"
            + "            <owl:hasValue>\n"
            + "              <Degree rdf:ID=\"PhD\"/>\n"
            + "            </owl:hasValue>\n"
            + "            <owl:onProperty>\n"
            + "              <owl:ObjectProperty rdf:about=\"#hasDegree\"/>\n"
            + "            </owl:onProperty>\n"
            + "          </owl:Restriction>\n"
            + "          <owl:Class rdf:about=\"#Koala\"/>\n"
            + "        </owl:intersectionOf>\n"
            + "      </owl:Class>\n"
            + "    </owl:equivalentClass>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"University\">\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Class rdf:ID=\"Habitat\"/>\n"
            + "    </rdfs:subClassOf>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Koala\">\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n"
            + "        >false</owl:hasValue>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:FunctionalProperty rdf:about=\"#isHardWorking\"/>\n"
            + "        </owl:onProperty>\n"
            + "      </owl:Restriction>\n"
            + "    </rdfs:subClassOf>\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:someValuesFrom>\n"
            + "          <owl:Class rdf:about=\"#DryEucalyptForest\"/>\n"
            + "        </owl:someValuesFrom>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:ObjectProperty rdf:about=\"#hasHabitat\"/>\n"
            + "        </owl:onProperty>\n"
            + "      </owl:Restriction>\n"
            + "    </rdfs:subClassOf>\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Marsupials\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Animal\">\n"
            + "    <rdfs:seeAlso>Male</rdfs:seeAlso>\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:ObjectProperty rdf:about=\"#hasHabitat\"/>\n"
            + "        </owl:onProperty>\n"
            + "        <owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n"
            + "        >1</owl:minCardinality>\n"
            + "      </owl:Restriction>\n"
            + "    </rdfs:subClassOf>\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n"
            + "        >1</owl:cardinality>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n"
            + "        </owl:onProperty>\n"
            + "      </owl:Restriction>\n"
            + "    </rdfs:subClassOf>\n"
            + "    <owl:versionInfo>1.1</owl:versionInfo>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Forest\">\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Habitat\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Rainforest\">\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Forest\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"GraduateStudent\">\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:ObjectProperty rdf:about=\"#hasDegree\"/>\n"
            + "        </owl:onProperty>\n"
            + "        <owl:someValuesFrom>\n"
            + "          <owl:Class>\n"
            + "            <owl:oneOf rdf:parseType=\"Collection\">\n"
            + "              <Degree rdf:ID=\"BA\"/>\n"
            + "              <Degree rdf:ID=\"BS\"/>\n"
            + "            </owl:oneOf>\n"
            + "          </owl:Class>\n"
            + "        </owl:someValuesFrom>\n"
            + "      </owl:Restriction>\n"
            + "    </rdfs:subClassOf>\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Student\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Parent\">\n"
            + "    <owl:equivalentClass>\n"
            + "      <owl:Class>\n"
            + "        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
            + "          <owl:Class rdf:about=\"#Animal\"/>\n"
            + "          <owl:Restriction>\n"
            + "            <owl:onProperty>\n"
            + "              <owl:ObjectProperty rdf:about=\"#hasChildren\"/>\n"
            + "            </owl:onProperty>\n"
            + "            <owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n"
            + "            >1</owl:minCardinality>\n"
            + "          </owl:Restriction>\n"
            + "        </owl:intersectionOf>\n"
            + "      </owl:Class>\n"
            + "    </owl:equivalentClass>\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Animal\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"DryEucalyptForest\">\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Forest\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Quokka\">\n"
            + "    <rdfs:subClassOf>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n"
            + "        >true</owl:hasValue>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:FunctionalProperty rdf:about=\"#isHardWorking\"/>\n"
            + "        </owl:onProperty>\n"
            + "      </owl:Restriction>\n"
            + "    </rdfs:subClassOf>\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Marsupials\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"TasmanianDevil\">\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Marsupials\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"MaleStudentWith3Daughters\">\n"
            + "    <owl:equivalentClass>\n"
            + "      <owl:Class>\n"
            + "        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
            + "          <owl:Class rdf:about=\"#Student\"/>\n"
            + "          <owl:Restriction>\n"
            + "            <owl:onProperty>\n"
            + "              <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n"
            + "            </owl:onProperty>\n"
            + "            <owl:hasValue>\n"
            + "              <Gender rdf:ID=\"male\"/>\n"
            + "            </owl:hasValue>\n"
            + "          </owl:Restriction>\n"
            + "          <owl:Restriction>\n"
            + "            <owl:onProperty>\n"
            + "              <owl:ObjectProperty rdf:about=\"#hasChildren\"/>\n"
            + "            </owl:onProperty>\n"
            + "            <owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n"
            + "            >3</owl:cardinality>\n"
            + "          </owl:Restriction>\n"
            + "          <owl:Restriction>\n"
            + "            <owl:allValuesFrom rdf:resource=\"#Female\"/>\n"
            + "            <owl:onProperty>\n"
            + "              <owl:ObjectProperty rdf:about=\"#hasChildren\"/>\n"
            + "            </owl:onProperty>\n"
            + "          </owl:Restriction>\n"
            + "        </owl:intersectionOf>\n"
            + "      </owl:Class>\n"
            + "    </owl:equivalentClass>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Degree\"/>\n"
            + "  <owl:Class rdf:ID=\"Male\">\n"
            + "    <owl:equivalentClass>\n"
            + "      <owl:Restriction>\n"
            + "        <owl:hasValue rdf:resource=\"#male\"/>\n"
            + "        <owl:onProperty>\n"
            + "          <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n"
            + "        </owl:onProperty>\n"
            + "      </owl:Restriction>\n"
            + "    </owl:equivalentClass>\n"
            + "  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Gender\"/>\n"
            + "  <owl:Class rdf:ID=\"Person\">\n"
            + "    <rdfs:subClassOf rdf:resource=\"#Animal\"/>\n"
            + "    <owl:disjointWith rdf:resource=\"#Marsupials\"/>\n"
            + "  </owl:Class>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasHabitat\">\n"
            + "    <rdfs:range rdf:resource=\"#Habitat\"/>\n"
            + "    <rdfs:domain rdf:resource=\"#Animal\"/>\n"
            + "  </owl:ObjectProperty>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasDegree\">\n"
            + "    <rdfs:domain rdf:resource=\"#Person\"/>\n"
            + "    <rdfs:range rdf:resource=\"#Degree\"/>\n"
            + "  </owl:ObjectProperty>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasChildren\">\n"
            + "    <rdfs:range rdf:resource=\"#Animal\"/>\n"
            + "    <rdfs:domain rdf:resource=\"#Animal\"/>\n"
            + "  </owl:ObjectProperty>\n"
            + "  <owl:FunctionalProperty rdf:ID=\"hasGender\">\n"
            + "    <rdfs:range rdf:resource=\"#Gender\"/>\n"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/>\n"
            + "    <rdfs:domain rdf:resource=\"#Animal\"/>\n"
            + "  </owl:FunctionalProperty>\n"
            + "  <owl:FunctionalProperty rdf:ID=\"isHardWorking\">\n"
            + "    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/>\n"
            + "    <rdfs:domain rdf:resource=\"#Person\"/>\n"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/>\n"
            + "  </owl:FunctionalProperty>\n"
            + "  <Degree rdf:ID=\"MA\"/>\n" + "</rdf:RDF>";
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o = null;
        try {
            o = m.loadOntologyFromOntologyDocument(new StringDocumentSource(
                koala));
            MultiThreadChecker checker = new MultiThreadChecker(10);
            checker.check(new TestCallback(o, m.createOntology()));
            String trace = checker.getTrace();
            System.out.println(trace);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            final ConcurrentLinkedQueue<Long> results = new ConcurrentLinkedQueue<>();
            final long start = System.currentTimeMillis();
            ExecutorService service = Executors.newFixedThreadPool(rep);
            for (int i = 0; i < rep; i++) {
                service.execute(() -> {
                    for (int j = 0; j < rep; j++) {
                        try {
                            cb.run();
                            results.offer(System.currentTimeMillis());
                        } catch (Throwable e) {
                            e.printStackTrace(p);
                            printout(start, results);
                        }
                    }
                });
            }
            service.shutdown();
            while (!service.isTerminated()) {
                try {
                    service.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printout(start, results);
        }

        protected void printout(long start,
            ConcurrentLinkedQueue<Long> results) {
            List<Object> list = new ArrayList<>();
            list.addAll(Arrays.asList(results.toArray()));
            Collections.sort(list, (o1, o2) -> ((Long) o1).compareTo((Long) o2));
            long end = System.currentTimeMillis();
            if (!list.isEmpty()) {
                end = (Long) list.get(list.size() - 1);
            }
            int expected = rep * rep;
            p.println("elapsed time (ms): " + (end - start));
            p.println("Successful threads: " + list.size() + "\t expected: "
                + expected);
            successful = list.size() == expected;
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
