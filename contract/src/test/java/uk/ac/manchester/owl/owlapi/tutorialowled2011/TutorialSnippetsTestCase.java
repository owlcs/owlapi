/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.search.EntitySearcher.getAnnotationObjects;
import static org.semanticweb.owlapi.search.Searcher.annotationObjects;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

import javax.annotation.Nonnull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

@SuppressWarnings({ "javadoc" })
public class TutorialSnippetsTestCase {

    @Nonnull
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Nonnull
    private static final Logger LOG = LoggerFactory.getLogger(TutorialSnippetsTestCase.class);
    @Nonnull
    private static final String KOALA = "<?xml version=\"1.0\"?>\n"
        + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\""
        + " xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\" xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n"
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
    @Nonnull
    public static final IRI KOALA_IRI = IRI.create("http://protege.stanford.edu/plugins/owl/owl-library/koala.owl");
    @Nonnull
    public static final IRI EXAMPLE_IRI = IRI.create("http://www.semanticweb.org/ontologies/ont.owl");
    @Nonnull
    public static final IRI EXAMPLE_SAVE_IRI = IRI.create("file:materializedOntologies/ont1290535967123.owl");
    @Nonnull
    OWLDataFactory df = OWLManager.getOWLDataFactory();
    @Nonnull
    OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();

    @Nonnull
    public OWLOntologyManager create() {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        PriorityCollection<OWLOntologyIRIMapper> iriMappers = m.getIRIMappers();
        iriMappers.add(new AutoIRIMapper(new File("materializedOntologies"), true));
        return m;
    }

    @Nonnull
    private static OWLOntology loadPizzaOntology(@Nonnull OWLOntologyManager m) throws OWLOntologyCreationException {
        return m.loadOntologyFromOntologyDocument(new StringDocumentSource(KOALA));
    }

    @Test
    public void testOntologyLoading() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        assertNotNull(o);
    }

    @Test
    public void testOntologyLoadingFromStringSource() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        assertNotNull(o);
        // save an ontology to a document target which holds all data in memory
        StringDocumentTarget target = new StringDocumentTarget();
        m.saveOntology(o, target);
        // remove the ontology from the manager, so it can be loaded again
        m.removeOntology(o);
        // create a document source from a string
        StringDocumentSource documentSource = new StringDocumentSource(target);
        // load the ontology from a document source
        OWLOntology o2 = m.loadOntologyFromOntologyDocument(documentSource);
        assertNotNull(o2);
    }

    @Test
    public void testOntologyCreation() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(EXAMPLE_IRI);
        assertNotNull(o);
    }

    @Test
    public void testShowClasses() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        assertNotNull(o);
        // These are the named classes referenced by axioms in the ontology.
        for (OWLClass cls : o.getClassesInSignature()) {
            // use the class for whatever purpose
            assertNotNull(cls);
        }
    }

    @Test
    public void testSaveOntology() throws Exception {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        assertNotNull(o);
        File output = temporaryFolder.newFile("saved_pizza.owl");
        // Output will be deleted on exit; to keep temporary file replace
        // previous line with the following
        // File output = File.createTempFile("saved_pizza", ".owl");
        IRI documentIRI2 = IRI.create(output);
        // save in OWL/XML format
        m.saveOntology(o, new OWLXMLDocumentFormat(), documentIRI2);
        // save in RDF/XML
        m.saveOntology(o, documentIRI2);
        // print out the ontology
        StringDocumentTarget target = new StringDocumentTarget();
        m.saveOntology(o, target);
        // Remove the ontology from the manager
        m.removeOntology(o);
    }

    @Test
    public void testIRIMapper() throws Exception {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        // map the ontology IRI to a physical IRI (files for example)
        // Create the document IRI for our ontology
        File output = temporaryFolder.newFile("saved_pizza.owl");
        // Output will be deleted on exit; to keep temporary file replace
        // previous line with the following
        // File output = File.createTempFile("saved_pizza", ".owl");
        IRI documentIRI = IRI.create(output);
        // Set up a mapping, which maps the ontology to the document IRI
        SimpleIRIMapper mapper = new SimpleIRIMapper(EXAMPLE_SAVE_IRI, documentIRI);
        m.getIRIMappers().add(mapper);
        // set up a mapper to read local copies of ontologies
        File localFolder = new File("materializedOntologies");
        // the manager will look up an ontology IRI by checking
        // localFolder first for a local copy, checking its subfolders as well
        m.getIRIMappers().add(new AutoIRIMapper(localFolder, true));
        // Create the ontology - we use the ontology IRI (not the physical URI)
        OWLOntology o = m.createOntology(EXAMPLE_SAVE_IRI);
        // save the ontology to its physical location - documentIRI
        m.saveOntology(o);
        assertNotNull(o);
    }

    @Test
    public void testAddAxioms() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(KOALA_IRI);
        // class A and class B
        OWLClass clsA = df.getOWLClass(IRI.create(KOALA_IRI + "#A"));
        OWLClass clsB = df.getOWLClass(IRI.create(KOALA_IRI + "#B"));
        // Now create the axiom
        OWLAxiom axiom = df.getOWLSubClassOfAxiom(clsA, clsB);
        // add the axiom to the ontology.
        AddAxiom addAxiom = new AddAxiom(o, axiom);
        // We now use the manager to apply the change
        m.applyChange(addAxiom);
        // remove the axiom from the ontology
        RemoveAxiom removeAxiom = new RemoveAxiom(o, axiom);
        m.applyChange(removeAxiom);
    }

    @Test
    public void testAssertedSuperclasses() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        OWLClass quokkaCls = df.getOWLClass(IRI.create(KOALA_IRI + "#Quokka"));
        Collection<OWLSubClassOfAxiom> classes = o.getSubClassAxiomsForSubClass(quokkaCls);
        // for each superclass there will be a corresponding axiom
        // the ontology indexes axioms in a variety of ways
        assertEquals(2, classes.size());
    }

    @Test
    public void testSWRL() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(EXAMPLE_IRI);
        // Get hold of references to class A and class B.
        OWLClass clsA = df.getOWLClass(IRI.create(EXAMPLE_IRI + "#A"));
        OWLClass clsB = df.getOWLClass(IRI.create(EXAMPLE_IRI + "#B"));
        SWRLVariable var = df.getSWRLVariable(IRI.create(EXAMPLE_IRI + "#x"));
        Set<SWRLClassAtom> body = Collections.singleton(df.getSWRLClassAtom(clsA, var));
        Set<SWRLClassAtom> head = Collections.singleton(df.getSWRLClassAtom(clsB, var));
        SWRLRule rule = df.getSWRLRule(body, head);
        m.applyChange(new AddAxiom(o, rule));
        OWLObjectProperty prop = df.getOWLObjectProperty(IRI.create(EXAMPLE_IRI + "#propA"));
        OWLObjectProperty propB = df.getOWLObjectProperty(IRI.create(EXAMPLE_IRI + "#propB"));
        SWRLObjectPropertyAtom propAtom = df.getSWRLObjectPropertyAtom(prop, var, var);
        SWRLObjectPropertyAtom propAtom2 = df.getSWRLObjectPropertyAtom(propB, var, var);
        Set<SWRLAtom> antecedent = new HashSet<>();
        antecedent.add(propAtom);
        antecedent.add(propAtom2);
        SWRLRule rule2 = df.getSWRLRule(antecedent, Collections.singleton(propAtom));
        m.applyChange(new AddAxiom(o, rule2));
    }

    @Test
    public void testIndividualAssertions() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(EXAMPLE_IRI);
        // We want to state that matthew has a father who is peter.
        OWLIndividual matthew = df.getOWLNamedIndividual(IRI.create(EXAMPLE_IRI + "#matthew"));
        OWLIndividual peter = df.getOWLNamedIndividual(IRI.create(EXAMPLE_IRI + "#peter"));
        // We need the hasFather property
        OWLObjectProperty hasFather = df.getOWLObjectProperty(IRI.create(EXAMPLE_IRI + "#hasFather"));
        // matthew --> hasFather --> peter
        OWLObjectPropertyAssertionAxiom assertion = df.getOWLObjectPropertyAssertionAxiom(hasFather, matthew, peter);
        // Finally, add the axiom to our ontology and save
        AddAxiom addAxiomChange = new AddAxiom(o, assertion);
        m.applyChange(addAxiomChange);
        // matthew is an instance of Person
        OWLClass personClass = df.getOWLClass(IRI.create(EXAMPLE_IRI + "#Person"));
        OWLClassAssertionAxiom ax = df.getOWLClassAssertionAxiom(personClass, matthew);
        // Add this axiom to our ontology - with a convenience method
        m.addAxiom(o, ax);
    }

    @Test
    public void testDelete() throws OWLException {
        // Delete individuals representing countries
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Ontologies don't directly contain entities but axioms
        // OWLEntityRemover will remove an entity (class, property or
        // individual)
        // from a set of ontologies by removing all referencing axioms
        OWLEntityRemover remover = new OWLEntityRemover(Collections.singleton(o));
        int previousNumberOfIndividuals = o.getIndividualsInSignature().size();
        // Visit all individuals with the remover
        // Changes needed for removal will be prepared
        for (OWLNamedIndividual ind : o.getIndividualsInSignature()) {
            ind.accept(remover);
        }
        // Now apply the changes
        m.applyChanges(remover.getChanges());
        int size = o.getIndividualsInSignature().size();
        assertTrue(previousNumberOfIndividuals + " supposed to be larger than " + size,
            previousNumberOfIndividuals > size);
    }

    @Test
    public void testAddSomeRestriction() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(EXAMPLE_IRI);
        // all Heads have parts that are noses (at least one)
        // We do this by creating an existential (some) restriction
        OWLObjectProperty hasPart = df.getOWLObjectProperty(IRI.create(EXAMPLE_IRI + "#hasPart"));
        OWLClass nose = df.getOWLClass(IRI.create(EXAMPLE_IRI + "#Nose"));
        // Now let's describe the class of individuals that have at
        // least one part that is a kind of nose
        OWLClassExpression hasPartSomeNose = df.getOWLObjectSomeValuesFrom(hasPart, nose);
        OWLClass head = df.getOWLClass(IRI.create(EXAMPLE_IRI + "#Head"));
        // Head subclass of our restriction
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(head, hasPartSomeNose);
        // Add the axiom to our ontology
        AddAxiom addAx = new AddAxiom(o, ax);
        m.applyChange(addAx);
    }

    @Test
    public void testDatatypeRestriction() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(EXAMPLE_IRI);
        // Adults have an age greater than 18.
        OWLDataProperty hasAge = df.getOWLDataProperty(IRI.create(EXAMPLE_IRI + "hasAge"));
        // Create the restricted data range by applying the facet restriction
        // with a value of 18 to int
        OWLDataRange greaterThan18 = df.getOWLDatatypeRestriction(df.getIntegerOWLDatatype(), OWLFacet.MIN_INCLUSIVE, df
            .getOWLLiteral(18));
        // Now we can use this in our datatype restriction on hasAge
        OWLClassExpression adultDefinition = df.getOWLDataSomeValuesFrom(hasAge, greaterThan18);
        OWLClass adult = df.getOWLClass(IRI.create(EXAMPLE_IRI + "#Adult"));
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(adult, adultDefinition);
        m.applyChange(new AddAxiom(o, ax));
    }

    @Test
    public void testUnsatisfiableClasses() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Create a console progress monitor. This will print the reasoner
        // progress out to the console.
        ReasonerProgressMonitor progressMonitor = new LoggingReasonerProgressMonitor(LOG, "testUnsatisfiableClasses");
        OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
        // Create a reasoner that will reason over our ontology and its imports
        // closure. Pass in the configuration.
        // not using it in tests, we don't need the output
        // OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
        OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
        // Ask the reasoner to precompute some inferences
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        // We can determine if the ontology is actually consistent (in this
        // case, it should be).
        assertTrue(reasoner.isConsistent());
        // get a list of unsatisfiable classes
        Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses();
        // leave owl:Nothing out
        Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom();
        if (!unsatisfiable.isEmpty()) {
            for (OWLClass cls : unsatisfiable) {
                assertNotNull(cls);
                // deal with unsatisfiable classes
            }
        }
    }

    @Test
    public void testDescendants() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Create a console progress monitor. This will print the reasoner
        // progress out to the console.
        ReasonerProgressMonitor progressMonitor = new LoggingReasonerProgressMonitor(LOG, "testDescendants");
        OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
        // Create a reasoner that will reason over our ontology and its imports
        // closure. Pass in the configuration.
        // not using it in tests, we don't need the output
        // OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
        OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
        // Ask the reasoner to precompute some inferences
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        // Look up and print all direct subclasses for all classes
        for (OWLClass c : o.getClassesInSignature()) {
            assert c != null;
            // the boolean argument specifies direct subclasses; false would
            // specify all subclasses
            // a NodeSet represents a set of Nodes.
            // a Node represents a set of equivalent classes
            NodeSet<OWLClass> subClasses = reasoner.getSubClasses(c, true);
            for (OWLClass subClass : subClasses.getFlattened()) {
                assertNotNull(subClass);
                // process all subclasses no matter what node they're in
            }
        }
    }

    @Test
    public void testPetInstances() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Create a console progress monitor. This will print the reasoner
        // progress out to the console.
        ReasonerProgressMonitor progressMonitor = new LoggingReasonerProgressMonitor(LOG, "testPetInstances");
        OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
        // Create a reasoner that will reason over our ontology and its imports
        // closure. Pass in the configuration.
        // not using it in tests, we don't need the output
        // OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
        OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
        // Ask the reasoner to precompute some inferences
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        // for each class, look up the instances
        for (OWLClass c : o.getClassesInSignature()) {
            assert c != null;
            // the boolean argument specifies direct subclasses; false would
            // specify all subclasses
            // a NodeSet represents a set of Nodes.
            // a Node represents a set of equivalent classes/or sameAs
            // individuals
            NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, true);
            for (OWLNamedIndividual i : instances.getFlattened()) {
                assert i != null;
                // look up all property assertions
                for (OWLObjectProperty op : o.getObjectPropertiesInSignature()) {
                    assert op != null;
                    NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(i, op);
                    for (OWLNamedIndividual value : petValuesNodeSet.getFlattened()) {
                        assertNotNull(value);
                        // use the value individuals
                    }
                }
            }
        }
    }

    @Test
    public void testLookupRestrictions() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // We want to examine the restrictions on all classes.
        for (OWLClass c : o.getClassesInSignature()) {
            assert c != null;
            // collect the properties which are used in existential restrictions
            RestrictionVisitor visitor = new RestrictionVisitor(Collections.singleton(o));
            for (OWLSubClassOfAxiom ax : o.getSubClassAxiomsForSubClass(c)) {
                // Ask our superclass to accept a visit from the
                // RestrictionVisitor
                ax.getSuperClass().accept(visitor);
            }
            // Our RestrictionVisitor has now collected all of the properties
            // that have been restricted in existential
            // restrictions - print them out.
            Set<OWLObjectPropertyExpression> restrictedProperties = visitor.getRestrictedProperties();
            // System.out.println("Restricted properties for " + labelFor(c, o)
            // + ": " + restrictedProperties.size());
            for (OWLObjectPropertyExpression prop : restrictedProperties) {
                assertNotNull(prop);
                // System.out.println(" " + prop);
            }
        }
    }

    /**
     * Visits existential restrictions and collects the properties which are
     * restricted
     */
    private static class RestrictionVisitor extends OWLClassExpressionVisitorAdapter {

        @Nonnull
        private final Set<OWLClass> processedClasses;
        @Nonnull
        private final Set<OWLObjectPropertyExpression> restrictedProperties;
        private final Set<OWLOntology> onts;

        RestrictionVisitor(Set<OWLOntology> onts) {
            restrictedProperties = new HashSet<>();
            processedClasses = new HashSet<>();
            this.onts = onts;
        }

        @Nonnull
        public Set<OWLObjectPropertyExpression> getRestrictedProperties() {
            return restrictedProperties;
        }

        @Override
        public void visit(OWLClass ce) {
            // avoid cycles
            if (!processedClasses.contains(ce)) {
                // If we are processing inherited restrictions then
                // we recursively visit named supers.
                processedClasses.add(ce);
                for (OWLOntology ont : onts) {
                    for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(ce)) {
                        ax.getSuperClass().accept(this);
                    }
                }
            }
        }

        @Override
        public void visit(@Nonnull OWLObjectSomeValuesFrom ce) {
            // This method gets called when a class expression is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedProperties.add(ce.getProperty());
        }
    }

    @Test
    public void testComment() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // We want to add a comment to the pizza class.
        OWLClass quokkaCls = df.getOWLClass(IRI.create(KOALA_IRI + "#Quokka"));
        // the content of our comment: a string and a language tag
        OWLAnnotation commentAnno = df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral(
            "A class which represents Quokkas", "en"));
        // Specify that the pizza class has an annotation
        OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(quokkaCls.getIRI(), commentAnno);
        // Add the axiom to the ontology
        m.applyChange(new AddAxiom(o, ax));
        // add a version info annotation to the ontology
    }

    @Test
    public void testVersionInfo() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // We want to add a comment to the pizza class.
        OWLLiteral lit = df.getOWLLiteral("Added a comment to the pizza class");
        // create an annotation to pair a URI with the constant
        OWLAnnotationProperty owlAnnotationProperty = df.getOWLAnnotationProperty(OWLRDFVocabulary.OWL_VERSION_INFO
            .getIRI());
        OWLAnnotation anno = df.getOWLAnnotation(owlAnnotationProperty, lit);
        // Now we can add this as an ontology annotation
        // Apply the change in the usual way
        m.applyChange(new AddOntologyAnnotation(o, anno));
    }

    @Test
    public void testReadAnnotations() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        for (OWLClass cls : o.getClassesInSignature()) {
            // Get the annotations on the class that use the label property
            for (OWLAnnotation annotation : annotationObjects(o.getAnnotationAssertionAxioms(cls.getIRI()), df
                .getRDFSLabel())) {
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                    // look for portuguese labels
                    if (val.hasLang("pt")) {
                        assertNotNull(val.getLiteral());
                        // System.out.println(cls + " labelled " +
                        // val.getLiteral());
                    }
                }
            }
        }
    }

    @Test
    public void testInferredOntology() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Create the reasoner and classify the ontology
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(o);
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        // To generate an inferred ontology, use implementations of inferred
        // axiom generators
        List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<>();
        gens.add(new InferredSubClassAxiomGenerator());
        OWLOntology infOnt = m.createOntology();
        // create the inferred ontology generator
        InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, gens);
        iog.fillOntology(m.getOWLDataFactory(), infOnt);
    }

    @Test
    public void testMergedOntology() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o1 = loadPizzaOntology(m);
        OWLOntology o2 = m.createOntology(EXAMPLE_IRI);
        m.addAxiom(o2, df.getOWLDeclarationAxiom(df.getOWLClass(IRI.create(EXAMPLE_IRI + "#Weasel"))));
        // Create our ontology merger
        OWLOntologyMerger merger = new OWLOntologyMerger(m);
        // We merge all of the loaded ontologies. Since an OWLOntologyManager is
        // an OWLOntologySetProvider we
        // just pass this in. We also need to specify the URI of the new
        // ontology that will be created.
        IRI mergedOntologyIRI = IRI.create("http://www.semanticweb.com/mymergedont");
        OWLOntology merged = merger.createMergedOntology(m, mergedOntologyIRI);
        assertTrue(merged.getAxiomCount() > o1.getAxiomCount());
        assertTrue(merged.getAxiomCount() > o2.getAxiomCount());
    }

    @Test
    public void testOntologyWalker() throws OWLException {
        // How to use an ontology walker to walk the asserted structure of an
        // ontology.
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Create the walker
        OWLOntologyWalker walker = new OWLOntologyWalker(Collections.singleton(o));
        // Now ask our walker to walk over the ontology
        OWLOntologyWalkerVisitorEx<Object> visitor = new OWLOntologyWalkerVisitorEx<Object>(walker) {

            @Override
            public Object visit(OWLObjectSomeValuesFrom ce) {
                assertNotNull(ce);
                // Print out the restriction
                // System.out.println(desc);
                // Print out the axiom where the restriction is used
                // System.out.println(" " + getCurrentAxiom());
                // System.out.println();
                // We don't need to return anything here.
                return "";
            }
        };
        // Now ask the walker to walk over the ontology structure using our
        // visitor instance.
        walker.walkStructure(visitor);
    }

    @Test
    public void testMargherita() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // For this particular ontology, we know that all class, properties
        // names etc. have
        // URIs that is made up of the ontology IRI plus # plus the local name
        String prefix = KOALA_IRI + "#";
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(o);
        // Now we can query the reasoner, suppose we want to determine the
        // properties that
        // instances of Margherita pizza must have
        OWLClass margherita = df.getOWLClass(IRI.create(prefix + "Margherita"));
        printProperties(o, reasoner, margherita);
    }

    /**
     * Prints out the properties that instances of a class expression must have
     * 
     * @param o
     *        The ontology
     * @param reasoner
     *        The reasoner
     * @param cls
     *        The class expression
     */
    private void printProperties(@Nonnull OWLOntology o, @Nonnull OWLReasoner reasoner, OWLClass cls) {
        for (OWLObjectPropertyExpression prop : o.getObjectPropertiesInSignature()) {
            assert prop != null;
            // To test whether an instance of A MUST have a property p with a
            // filler, check for the satisfiability of A and not (some p Thing)
            // if this is satisfiable, then there might be instances with no
            // p-filler
            OWLClassExpression restriction = df.getOWLObjectSomeValuesFrom(prop, df.getOWLThing());
            OWLClassExpression intersection = df.getOWLObjectIntersectionOf(cls, df.getOWLObjectComplementOf(
                restriction));
            boolean sat = !reasoner.isSatisfiable(intersection);
            if (sat) {
                assertNotNull(prop);
                // System.out.println("Instances of " + cls +
                // " necessarily have the property " + prop);
            }
        }
    }

    @Test
    public void testModularization() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // extract a module for all toppings.
        // start by creating a signature that consists of "Quokka".
        OWLClass quokkaCls = df.getOWLClass(IRI.create(KOALA_IRI + "#Quokka"));
        Set<OWLEntity> sig = new HashSet<>();
        sig.add(quokkaCls);
        // We now add all subclasses (direct and indirect) of the chosen
        // classes.
        Set<OWLEntity> seedSig = new HashSet<>();
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(o);
        for (OWLEntity ent : sig) {
            seedSig.add(ent);
            if (ent.isOWLClass()) {
                NodeSet<OWLClass> subClasses = reasoner.getSubClasses(ent.asOWLClass(), false);
                seedSig.addAll(subClasses.getFlattened());
            }
        }
        // We now extract a locality-based module. STAR provides the smallest
        // ones
        SyntacticLocalityModuleExtractor sme = new SyntacticLocalityModuleExtractor(m, o, ModuleType.STAR);
        Set<OWLAxiom> mod = sme.extract(seedSig);
        assertNotNull(mod);
    }

    @Test
    public void testIndividual() throws OWLException {
        // :Mary is an instance of the class :Person.
        OWLOntologyManager m = create();
        // The IRIs used here are taken from the OWL 2 Primer
        String base = "http://example.com/owl/families/";
        PrefixManager pm = new DefaultPrefixManager(null, null, base);
        OWLClass person = df.getOWLClass(":Person", pm);
        OWLNamedIndividual mary = df.getOWLNamedIndividual(":Mary", pm);
        // create a ClassAssertion to specify that :Mary is an instance of
        // :Person
        OWLClassAssertionAxiom classAssertion = df.getOWLClassAssertionAxiom(person, mary);
        OWLOntology o = m.createOntology(IRI.create(base));
        // Add the class assertion
        m.addAxiom(o, classAssertion);
        // Dump the ontology
        StreamDocumentTarget target = new StreamDocumentTarget(new ByteArrayOutputStream());
        m.saveOntology(o, target);
    }

    @SuppressWarnings("unused")
    @Test
    public void testDataRanges() throws OWLException {
        // Data ranges are used as the types of literals, as the ranges for data
        // properties
        OWLOntologyManager m = create();
        // OWLDatatype represents named datatypes in OWL
        // The OWL2Datatype enum defines built in OWL 2 Datatypes
        OWLDatatype integer = df.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());
        // For common data types there are some convenience methods of
        // OWLDataFactory
        OWLDatatype integerDatatype = df.getIntegerOWLDatatype();
        OWLDatatype floatDatatype = df.getFloatOWLDatatype();
        OWLDatatype doubleDatatype = df.getDoubleOWLDatatype();
        OWLDatatype booleanDatatype = df.getBooleanOWLDatatype();
        // The top datatype is rdfs:Literal
        OWLDatatype rdfsLiteral = df.getTopDatatype();
        // Custom data ranges can be built up from these basic datatypes
        // Get hold of a literal that is an integer value 18
        OWLLiteral eighteen = df.getOWLLiteral(18);
        OWLDatatypeRestriction integerGE18 = df.getOWLDatatypeRestriction(integer, OWLFacet.MIN_INCLUSIVE, eighteen);
        OWLDataProperty hasAge = df.getOWLDataProperty(IRI.create(
            "http://www.semanticweb.org/ontologies/dataranges#hasAge"));
        OWLDataPropertyRangeAxiom rangeAxiom = df.getOWLDataPropertyRangeAxiom(hasAge, integerGE18);
        OWLOntology o = m.createOntology(IRI.create("http://www.semanticweb.org/ontologies/dataranges"));
        // Add the range axiom to our ontology
        m.addAxiom(o, rangeAxiom);
        // Now create a datatype definition axiom
        OWLDatatypeDefinitionAxiom datatypeDef = df.getOWLDatatypeDefinitionAxiom(df.getOWLDatatype(IRI.create(
            "http://www.semanticweb.org/ontologies/dataranges#age")), integerGE18);
        // Add the definition to our ontology
        m.addAxiom(o, datatypeDef);
        // Dump our ontology
        StreamDocumentTarget target = new StreamDocumentTarget(new ByteArrayOutputStream());
        m.saveOntology(o, target);
    }

    @Test
    public void testPropertyAssertions() throws OWLException {
        // how to specify various property assertions for individuals
        OWLOntologyManager m = create();
        IRI ontologyIRI = IRI.create("http://example.com/owl/families/");
        OWLOntology o = m.createOntology(ontologyIRI);
        PrefixManager pm = new DefaultPrefixManager(null, null, ontologyIRI.toString());
        // Let's specify the :John has a wife :Mary
        // Get hold of the necessary individuals and object property
        OWLNamedIndividual john = df.getOWLNamedIndividual(":John", pm);
        OWLNamedIndividual mary = df.getOWLNamedIndividual(":Mary", pm);
        OWLObjectProperty hasWife = df.getOWLObjectProperty(":hasWife", pm);
        // To specify that :John is related to :Mary via the :hasWife property
        // we create an object property
        // assertion and add it to the ontology
        OWLObjectPropertyAssertionAxiom propertyAssertion = df.getOWLObjectPropertyAssertionAxiom(hasWife, john, mary);
        m.addAxiom(o, propertyAssertion);
        // Now let's specify that :John is aged 51.
        // Get hold of a data property called :hasAge
        OWLDataProperty hasAge = df.getOWLDataProperty(":hasAge", pm);
        // To specify that :John has an age of 51 we create a data property
        // assertion and add it to the ontology
        OWLDataPropertyAssertionAxiom dataPropertyAssertion = df.getOWLDataPropertyAssertionAxiom(hasAge, john, 51);
        m.addAxiom(o, dataPropertyAssertion);
    }

    /**
     * Print the class hierarchy for the given ontology from this class down,
     * assuming this class is at the given level. Makes no attempt to deal
     * sensibly with multiple inheritance.
     */
    public void printHierarchy(@Nonnull OWLOntology o, @Nonnull OWLClass clazz) {
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(o);
        printHierarchy(reasoner, clazz, 0, new HashSet<OWLClass>());
        /* Now print out any unsatisfiable classes */
        for (OWLClass cl : o.getClassesInSignature()) {
            assert cl != null;
            if (!reasoner.isSatisfiable(cl)) {
                assertNotNull(labelFor(cl, o));
                // System.out.println("XXX: " + labelFor(cl, o));
            }
        }
        reasoner.dispose();
    }

    public static class LoggingReasonerProgressMonitor implements ReasonerProgressMonitor {

        private static final long serialVersionUID = 40000L;
        private static Logger logger;

        public LoggingReasonerProgressMonitor(Logger log) {
            logger = log;
        }

        public LoggingReasonerProgressMonitor(@Nonnull Logger log, String methodName) {
            String loggerName = log.getName() + '.' + methodName;
            logger = LoggerFactory.getLogger(loggerName);
        }

        @Override
        public void reasonerTaskStarted(String taskName) {
            logger.info("Reasoner Task Started: {}.", taskName);
        }

        @Override
        public void reasonerTaskStopped() {
            logger.info("Task stopped.");
        }

        @Override
        public void reasonerTaskProgressChanged(int value, int max) {
            logger.info("Reasoner Task made progress: {}/{}", value, max);
        }

        @Override
        public void reasonerTaskBusy() {
            logger.info("Reasoner Task is busy");
        }
    }

    // a visitor to extract label annotations
    @Nonnull
    LabelExtractor le = new LabelExtractor();

    private String labelFor(@Nonnull OWLEntity clazz, @Nonnull OWLOntology o) {
        Iterable<OWLAnnotation> annotations = getAnnotationObjects(clazz, o);
        for (OWLAnnotation anno : annotations) {
            String result = anno.accept(le);
            if (!result.isEmpty()) {
                return result;
            }
        }
        return clazz.getIRI().toString();
    }

    public void printHierarchy(@Nonnull OWLReasoner reasoner, @Nonnull OWLClass clazz, int level,
        @Nonnull Set<OWLClass> visited) {
        // Only print satisfiable classes to skip Nothing
        if (!visited.contains(clazz) && reasoner.isSatisfiable(clazz)) {
            visited.add(clazz);
            // for (int i = 0; i < level * 4; i++) {
            // System.out.print(" ");
            // }
            // System.out.println(labelFor(clazz, reasoner.getRootOntology()));
            /* Find the children and recurse */
            NodeSet<OWLClass> subClasses = reasoner.getSubClasses(clazz, true);
            for (OWLClass child : subClasses.getFlattened()) {
                printHierarchy(reasoner, child, level + 1, visited);
            }
        }
    }

    @Test
    public void testHierarchy() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Get Thing
        OWLClass clazz = df.getOWLThing();
        // System.out.println("Class : " + clazz);
        // Print the hierarchy below thing
        printHierarchy(o, clazz);
    }

    @Test
    public void testRendering() throws OWLException {
        // Simple Rendering Example. Reads an ontology and then renders it.
        OWLOntologyManager m = create();
        OWLOntology o = loadPizzaOntology(m);
        // Register the ontology storer with the manager
        m.getOntologyStorers().add(new TutorialSyntaxStorerFactory());
        // Save using a different format
        StreamDocumentTarget target = new StreamDocumentTarget(new ByteArrayOutputStream());
        m.saveOntology(o, new OWLTutorialSyntaxOntologyFormat(), target);
    }

    @Test
    public void testCheckProfile() throws OWLException {
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(KOALA_IRI);
        // Available profiles: DL, EL, QL, RL, OWL2 (Full)
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(o);
        for (OWLProfileViolation v : report.getViolations()) {
            // deal with violations
            System.out.println(v);
        }
    }
}
