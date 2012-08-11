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

package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;

import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.reasoner.*;

import org.semanticweb.owlapi.util.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

@Ignore
public class TutorialSnippets  {
	public static final IRI pizza_iri = IRI
			.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
	public static final IRI example_iri = IRI
			.create("http://www.semanticweb.org/ontologies/ont.owl");
	public static final IRI example_save_iri = IRI
			.create("file:materializedOntologies/ont1290535967123.owl");
	OWLDataFactory df = OWLManager.getOWLDataFactory();

	public OWLOntologyManager create() {
		OWLOntologyManager m = OWLManager
				.createOWLOntologyManager();
		m.addIRIMapper(new AutoIRIMapper(new File(
				"materializedOntologies"), true));
		return m;
	}
    @Test
	public void testOntologyLoading() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		assertNotNull(o);
	}
    @Test
	public void testOntologyLoadingFromStringSource()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		assertNotNull(o);
		StringDocumentTarget target = new StringDocumentTarget();
		m.saveOntology(o, target);
		m.removeOntology(o);
		OWLOntology o2 = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(
						target.toString()));
		assertNotNull(o2);
	}
    @Test
	public void testOntologyCreation()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(example_iri);
		assertNotNull(o);
	}
    @Test
	public void testShowClasses() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		assertNotNull(o);
		// These are the named classes referenced by axioms in the ontology.
		for (OWLClass cls : o.getClassesInSignature()) {
			System.out.println(cls);
		}
	}
    @Test
	public void testSaveOntology() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		assertNotNull(o);
		File output = File.createTempFile(
				"saved_pizza", "owl");
		IRI documentIRI2 = IRI.create(output);
		// save in OWL/XML format
		m.saveOntology(o, new OWLXMLOntologyFormat(),
				documentIRI2);
		// save in RDF/XML
		m.saveOntology(o, documentIRI2);
		// print out the ontology on System.out
		m.saveOntology(o,
				new SystemOutDocumentTarget());
		// Remove the ontology from the manager
		m.removeOntology(o);
	}
    @Test
	public void testIRIMapper() throws Exception {
		OWLOntologyManager m = OWLManager
				.createOWLOntologyManager();
		// map the ontology IRI to a physical IRI (files for example)
		// Create the document IRI for our ontology
		File output = File.createTempFile(
				"saved_pizza", "owl");
		IRI documentIRI = IRI.create(output);
		// Set up a mapping, which maps the ontology to the document IRI
		SimpleIRIMapper mapper = new SimpleIRIMapper(
				example_save_iri, documentIRI);
		m.addIRIMapper(mapper);
		// set up a mapper to read local copies of ontologies
		File localFolder = new File(
				"materializedOntologies");
		// the manager will look up an ontology IRI by checking
		// localFolder first for a local copy, checking its subfolders as well
		m.addIRIMapper(new AutoIRIMapper(localFolder,
				true));
		// Now create the ontology - we use the ontology IRI (not the physical URI)
		OWLOntology o = m
				.createOntology(example_save_iri);
		// save the ontology to its physical location - documentIRI
		m.saveOntology(o);
		assertNotNull(o);
	}
    @Test
	public void testAddAxioms() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(pizza_iri);
		// class A and class B
		OWLClass clsA = df.getOWLClass(IRI
				.create(pizza_iri + "#A"));
		OWLClass clsB = df.getOWLClass(IRI
				.create(pizza_iri + "#B"));
		// Now create the axiom
		OWLAxiom axiom = df.getOWLSubClassOfAxiom(
				clsA, clsB);
		// add the axiom to the ontology.
		AddAxiom addAxiom = new AddAxiom(o, axiom);
		// We now use the manager to apply the change
		m.applyChange(addAxiom);
		// remove the axiom from the ontology
		RemoveAxiom removeAxiom = new RemoveAxiom(o,
				axiom);
		m.applyChange(removeAxiom);
	}
    @Test
	public void testAssertedSuperclasses()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(example_iri);
		OWLClass clsA = df.getOWLClass(IRI
				.create(example_iri + "#A"));
		Set<OWLClassExpression> superClasses = clsA
				.getSuperClasses(o);
		// for each superclass there will be a corresponding axiom
		// the ontology indexes axioms in a variety of ways
		Set<OWLSubClassOfAxiom> sameSuperClasses = o
				.getSubClassAxiomsForSubClass(clsA);
		assertEquals(superClasses.size(),
				sameSuperClasses.size());
	}
    @Test
	public void testSWRL() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(example_iri);
		// Get hold of references to class A and class B.
		OWLClass clsA = df.getOWLClass(IRI
				.create(example_iri + "#A"));
		OWLClass clsB = df.getOWLClass(IRI
				.create(example_iri + "#B"));
		SWRLVariable var = df.getSWRLVariable(IRI
				.create(example_iri + "#x"));
		Set<SWRLClassAtom> body = Collections
				.singleton(df.getSWRLClassAtom(clsA,
						var));
		Set<SWRLClassAtom> head = Collections
				.singleton(df.getSWRLClassAtom(clsB,
						var));
		SWRLRule rule = df.getSWRLRule(body, head);
		m.applyChange(new AddAxiom(o, rule));
		OWLObjectProperty prop = df
				.getOWLObjectProperty(IRI
						.create(example_iri + "#propA"));
		OWLObjectProperty propB = df
				.getOWLObjectProperty(IRI
						.create(example_iri + "#propB"));
		SWRLObjectPropertyAtom propAtom = df
				.getSWRLObjectPropertyAtom(prop, var,
						var);
		SWRLObjectPropertyAtom propAtom2 = df
				.getSWRLObjectPropertyAtom(propB, var,
						var);
		Set<SWRLAtom> antecedent = new HashSet<SWRLAtom>();
		antecedent.add(propAtom);
		antecedent.add(propAtom2);
		SWRLRule rule2 = df.getSWRLRule(antecedent,
				Collections.singleton(propAtom));
		m.applyChange(new AddAxiom(o, rule2));
	}
    @Test
	public void testIndividualAssertions()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(example_iri);
		// We want to state that matthew has a father who is peter.
		OWLIndividual matthew = df
				.getOWLNamedIndividual(IRI
						.create(example_iri
								+ "#matthew"));
		OWLIndividual peter = df
				.getOWLNamedIndividual(IRI
						.create(example_iri + "#peter"));
		// We need the hasFather property
		OWLObjectProperty hasFather = df
				.getOWLObjectProperty(IRI
						.create(example_iri
								+ "#hasFather"));
		// matthew --> hasFather --> peter
		OWLObjectPropertyAssertionAxiom assertion = df
				.getOWLObjectPropertyAssertionAxiom(
						hasFather, matthew, peter);
		// Finally, add the axiom to our ontology and save
		AddAxiom addAxiomChange = new AddAxiom(o,
				assertion);
		m.applyChange(addAxiomChange);
		// matthew is an instance of Person
		OWLClass personClass = df.getOWLClass(IRI
				.create(example_iri + "#Person"));
		OWLClassAssertionAxiom ax = df
				.getOWLClassAssertionAxiom(
						personClass, matthew);
		// Add this axiom to our ontology - with a convenience method
		m.addAxiom(o, ax);
	}
    @Test
	public void testDelete() throws Exception {
		// Delete individuals representing countries
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Ontologies don't directly contain entities but axioms
		// OWLEntityRemover will remove an entity (class, property or individual)
		// from a set of ontologies by removing all referencing axioms
		OWLEntityRemover remover = new OWLEntityRemover(
				m, Collections.singleton(o));
		int previousNumberOfIndividuals = o
				.getIndividualsInSignature().size();
		// Visit all individuals with the remover
		// Changes needed for removal will be prepared
		for (OWLNamedIndividual ind : o
				.getIndividualsInSignature()) {
			ind.accept(remover);
		}
		// Now apply the changes
		m.applyChanges(remover.getChanges());
		assertTrue(previousNumberOfIndividuals > o
				.getIndividualsInSignature().size());
	}
    @Test
	public void testAddSomeRestriction()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(example_iri);
		// all Heads have parts that are noses (at least one)
		// We do this by creating an existential (some) restriction
		OWLObjectProperty hasPart = df
				.getOWLObjectProperty(IRI
						.create(example_iri
								+ "#hasPart"));
		OWLClass nose = df.getOWLClass(IRI
				.create(example_iri + "#Nose"));
		// Now let's describe the class of individuals that have at
		// least one part that is a kind of nose
		OWLClassExpression hasPartSomeNose = df
				.getOWLObjectSomeValuesFrom(hasPart,
						nose);
		OWLClass head = df.getOWLClass(IRI
				.create(example_iri + "#Head"));
		// Head subclass of our restriction
		OWLSubClassOfAxiom ax = df
				.getOWLSubClassOfAxiom(head,
						hasPartSomeNose);
		// Add the axiom to our ontology
		AddAxiom addAx = new AddAxiom(o, ax);
		m.applyChange(addAx);
	}
    @Test
	public void testDatatypeRestriction()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(example_iri);
		// Adults have an age greater than 18.
		OWLDataProperty hasAge = df
				.getOWLDataProperty(IRI
						.create(example_iri + "hasAge"));
		// Create the restricted data range by applying the facet restriction with a value of 18 to int
		OWLDataRange greaterThan18 = df
				.getOWLDatatypeRestriction(
						df.getIntegerOWLDatatype(),
						OWLFacet.MIN_INCLUSIVE,
						df.getOWLLiteral(18));
		// Now we can use this in our datatype restriction on hasAge
		OWLClassExpression adultDefinition = df
				.getOWLDataSomeValuesFrom(hasAge,
						greaterThan18);
		OWLClass adult = df.getOWLClass(IRI
				.create(example_iri + "#Adult"));
		OWLSubClassOfAxiom ax = df
				.getOWLSubClassOfAxiom(adult,
						adultDefinition);
		m.applyChange(new AddAxiom(o, ax));
	}

	OWLReasonerFactory reasonerFactory=null;// = new JFactFactory();
    @Test
	public void testUnsatisfiableClasses()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Create a console progress monitor.  This will print the reasoner progress out to the console.
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(
				progressMonitor);
		// Create a reasoner that will reason over our ontology and its imports closure.  Pass in the configuration.
		OWLReasoner reasoner = reasonerFactory
				.createReasoner(o, config);
		// Ask the reasoner to precompute some inferences
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		// We can determine if the ontology is actually consistent (in this case, it should be).
		assertTrue(reasoner.isConsistent());
		// get a list of unsatisfiable classes
		Node<OWLClass> bottomNode = reasoner
				.getUnsatisfiableClasses();
		// leave owl:Nothing out
		Set<OWLClass> unsatisfiable = bottomNode
				.getEntitiesMinusBottom();
		if (!unsatisfiable.isEmpty()) {
			System.out
					.println("The following classes are unsatisfiable: ");
			for (OWLClass cls : unsatisfiable) {
				System.out.println("    " + cls);
			}
		} else {
			System.out
					.println("There are no unsatisfiable classes");
		}
	}
    @Test
	public void testDescendants() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Create a console progress monitor.  This will print the reasoner progress out to the console.
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(
				progressMonitor);
		// Create a reasoner that will reason over our ontology and its imports closure.  Pass in the configuration.
		OWLReasoner reasoner = reasonerFactory
				.createReasoner(o, config);
		// Ask the reasoner to precompute some inferences
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		// Look up and print all direct subclasses for all classes
		for (OWLClass c : o.getClassesInSignature()) {
			// the boolean argument specifies direct subclasses; false would specify all subclasses
			// a NodeSet represents a set of Nodes.
			// a Node represents a set of equivalent classes
			NodeSet<OWLClass> subClasses = reasoner
					.getSubClasses(c, true);
			for (OWLClass subClass : subClasses
					.getFlattened()) {
				System.out.println(labelFor(subClass,
						o)
						+ "\tsubclass of\t"
						+ labelFor(c, o));
			}
		}
	}
    @Test
	public void testPetInstances() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Create a console progress monitor.  This will print the reasoner progress out to the console.
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(
				progressMonitor);
		// Create a reasoner that will reason over our ontology and its imports closure.  Pass in the configuration.
		OWLReasoner reasoner = reasonerFactory
				.createReasoner(o, config);
		// Ask the reasoner to precompute some inferences
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		// for each class, look up the instances
		for (OWLClass c : o.getClassesInSignature()) {
			// the boolean argument specifies direct subclasses; false would specify all subclasses
			// a NodeSet represents a set of Nodes.
			// a Node represents a set of equivalent classes/or sameAs individuals
			NodeSet<OWLNamedIndividual> instances = reasoner
					.getInstances(c, true);
			for (OWLNamedIndividual i : instances
					.getFlattened()) {
				System.out.println(labelFor(i, o)
						+ "\tinstance of\t"
						+ labelFor(c, o));
				// look up all property assertions
				for (OWLObjectProperty op : o
						.getObjectPropertiesInSignature()) {
					NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner
							.getObjectPropertyValues(
									i, op);
					for (OWLNamedIndividual value : petValuesNodeSet
							.getFlattened()) {
						System.out.println(labelFor(i,
								o)
								+ "\t"
								+ labelFor(op, o)
								+ "\t"
								+ labelFor(value, o));
					}
				}
			}
		}
	}
    @Test
	public void testLookupRestrictions()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// We want to examine the restrictions on all classes.
		for (OWLClass c : o.getClassesInSignature()) {
			// collect the properties which are used in existential restrictions
			RestrictionVisitor visitor = new RestrictionVisitor(
					Collections.singleton(o));
			for (OWLSubClassOfAxiom ax : o
					.getSubClassAxiomsForSubClass(c)) {
				// Ask our superclass to accept a visit from the RestrictionVisitor
				ax.getSuperClass().accept(visitor);
			}
			// Our RestrictionVisitor has now collected all of the properties that have been restricted in existential
			// restrictions - print them out.
			Set<OWLObjectPropertyExpression> restrictedProperties = visitor
					.getRestrictedProperties();
			System.out
					.println("Restricted properties for "
							+ labelFor(c, o)
							+ ": "
							+ restrictedProperties
									.size());
			for (OWLObjectPropertyExpression prop : restrictedProperties) {
				System.out.println("    " + prop);
			}
		}
	}

	/**
	 * Visits existential restrictions and collects the properties which are
	 * restricted
	 */
	private static class RestrictionVisitor extends
			OWLClassExpressionVisitorAdapter {
		private Set<OWLClass> processedClasses;
		private Set<OWLObjectPropertyExpression> restrictedProperties;
		private Set<OWLOntology> onts;

		public RestrictionVisitor(Set<OWLOntology> onts) {
			restrictedProperties = new HashSet<OWLObjectPropertyExpression>();
			processedClasses = new HashSet<OWLClass>();
			this.onts = onts;
		}

		public Set<OWLObjectPropertyExpression> getRestrictedProperties() {
			return restrictedProperties;
		}

		@Override
		public void visit(OWLClass desc) {
			// avoid cycles
			if (!processedClasses.contains(desc)) {
				// If we are processing inherited restrictions then
				// we recursively visit named supers.
				processedClasses.add(desc);
				for (OWLOntology ont : onts) {
					for (OWLSubClassOfAxiom ax : ont
							.getSubClassAxiomsForSubClass(desc)) {
						ax.getSuperClass()
								.accept(this);
					}
				}
			}
		}

		@Override
		public void visit(OWLObjectSomeValuesFrom desc) {
			// This method gets called when a class expression is an
			// existential (someValuesFrom) restriction and it asks us to visit it
			restrictedProperties.add(desc
					.getProperty());
		}
	}
    @Test
	public void testComment() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// We want to add a comment to the pizza class.
		OWLClass pizzaCls = df.getOWLClass(IRI
				.create(pizza_iri + "#Pizza"));
		// the content of our comment: a string and a language tag
		OWLAnnotation commentAnno = df
				.getOWLAnnotation(
						df.getRDFSComment(),
						df.getOWLLiteral(
								"A class which represents pizzas",
								"en"));
		// Specify that the pizza class has an annotation
		OWLAxiom ax = df
				.getOWLAnnotationAssertionAxiom(
						pizzaCls.getIRI(), commentAnno);
		// Add the axiom to the ontology
		m.applyChange(new AddAxiom(o, ax));
		// add a version info annotation to the ontology
	}
    @Test
	public void testVersionInfo() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// We want to add a comment to the pizza class.
		OWLLiteral lit = df
				.getOWLLiteral("Added a comment to the pizza class");
		// create an annotation to pair a URI with the constant
		OWLAnnotationProperty owlAnnotationProperty = df
				.getOWLAnnotationProperty(OWLRDFVocabulary.OWL_VERSION_INFO
						.getIRI());
		OWLAnnotation anno = df.getOWLAnnotation(
				owlAnnotationProperty, lit);
		// Now we can add this as an ontology annotation
		// Apply the change in the usual way
		m.applyChange(new AddOntologyAnnotation(o,
				anno));
	}
    @Test
	public void testReadAnnotations() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		for (OWLClass cls : o.getClassesInSignature()) {
			// Get the annotations on the class that use the label property
			for (OWLAnnotation annotation : cls
					.getAnnotations(o,
							df.getRDFSLabel())) {
				if (annotation.getValue() instanceof OWLLiteral) {
					OWLLiteral val = (OWLLiteral) annotation
							.getValue();
					// look for portuguese labels
					if (val.hasLang("pt")) {
						System.out.println(cls
								+ " labelled "
								+ val.getLiteral());
					}
				}
			}
		}
	}
    @Test
	public void testInferredOntology()
			throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Create the reasoner and classify the ontology
		OWLReasoner reasoner = reasonerFactory
				.createNonBufferingReasoner(o);
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		// To generate an inferred ontology, use implementations of inferred axiom generators
		List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
		gens.add(new InferredSubClassAxiomGenerator());
		OWLOntology infOnt = m.createOntology();
		// create the inferred ontology generator
		InferredOntologyGenerator iog = new InferredOntologyGenerator(
				reasoner, gens);
		iog.fillOntology(m, infOnt);
	}
    @Test
	public void testMergedOntology() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o1 = m.loadOntology(pizza_iri);
		OWLOntology o2 = m.loadOntology(example_iri);
		// Create our ontology merger
		OWLOntologyMerger merger = new OWLOntologyMerger(
				m);
		// We merge all of the loaded ontologies.  Since an OWLOntologyManager is an OWLOntologySetProvider we
		// just pass this in.  We also need to specify the URI of the new ontology that will be created.
		IRI mergedOntologyIRI = IRI
				.create("http://www.semanticweb.com/mymergedont");
		OWLOntology merged = merger
				.createMergedOntology(m,
						mergedOntologyIRI);
		assertTrue(merged.getAxiomCount() > o1
				.getAxiomCount());
		assertTrue(merged.getAxiomCount() > o2
				.getAxiomCount());
	}
    @Test
	public void testOntologyWalker() throws Exception {
		// How to use an ontology walker to walk the asserted structure of an ontology.
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Create the walker
		OWLOntologyWalker walker = new OWLOntologyWalker(
				Collections.singleton(o));
		// Now ask our walker to walk over the ontology
		OWLOntologyWalkerVisitor<Object> visitor = new OWLOntologyWalkerVisitor<Object>(
				walker) {
			@Override
			public Object visit(
					OWLObjectSomeValuesFrom desc) {
				// Print out the restriction
				System.out.println(desc);
				// Print out the axiom where the restriction is used
				System.out.println("         "
						+ getCurrentAxiom());
				System.out.println();
				// We don't need to return anything here.
				return null;
			}
		};
		// Now ask the walker to walk over the ontology structure using our visitor instance.
		walker.walkStructure(visitor);
	}
    @Test
	public void testMargherita() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// For this particular ontology, we know that all class, properties names etc. have
		// URIs that is made up of the ontology IRI plus # plus the local name
		String prefix = pizza_iri + "#";
		OWLReasoner reasoner = reasonerFactory
				.createNonBufferingReasoner(o);
		// Now we can query the reasoner, suppose we want to determine the properties that
		// instances of Margherita pizza must have
		OWLClass margherita = df.getOWLClass(IRI
				.create(prefix + "Margherita"));
		printProperties(m, o, reasoner, margherita);
	}

	/**
	 * Prints out the properties that instances of a class expression must have
	 *
	 * @param man
	 *            The manager
	 * @param o
	 *            The ontology
	 * @param reasoner
	 *            The reasoner
	 * @param cls
	 *            The class expression
	 */
	private void printProperties(
			OWLOntologyManager man, OWLOntology o,
			OWLReasoner reasoner, OWLClass cls) {
		if (!o.containsClassInSignature(cls.getIRI())) {
			throw new RuntimeException(
					"Class not in signature of the ontology");
		}
		System.out.println("Properties of " + cls);
		for (OWLObjectPropertyExpression prop : o
				.getObjectPropertiesInSignature()) {
			// To test whether an instance of A MUST have a property p with a filler,
			// check for the satisfiability of A and not (some p Thing)
			// if this is satisfiable, then there might be instances with no p-filler
			OWLClassExpression restriction = df
					.getOWLObjectSomeValuesFrom(prop,
							df.getOWLThing());
			OWLClassExpression intersection = df
					.getOWLObjectIntersectionOf(
							cls,
							df.getOWLObjectComplementOf(restriction));
			boolean sat = !reasoner
					.isSatisfiable(intersection);
			if (sat) {
				System.out
						.println("Instances of "
								+ cls
								+ " necessarily have the property "
								+ prop);
			}
		}
	}
    @Test
	public void testModularization() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		System.out.println("Loaded: "
				+ o.getOntologyID());
		// extract a module for all toppings.
		// start by creating a signature that consists of "PizzaTopping".
		OWLClass toppingCls = df.getOWLClass(IRI
				.create(pizza_iri + "#PizzaTopping"));
		Set<OWLEntity> sig = new HashSet<OWLEntity>();
		sig.add(toppingCls);
		// We now add all subclasses (direct and indirect) of the chosen classes.
		Set<OWLEntity> seedSig = new HashSet<OWLEntity>();
		OWLReasoner reasoner = reasonerFactory
				.createNonBufferingReasoner(o);
		for (OWLEntity ent : sig) {
			seedSig.add(ent);
			if (ent.isOWLClass()) {
				NodeSet<OWLClass> subClasses = reasoner
						.getSubClasses(
								ent.asOWLClass(),
								false);
				seedSig.addAll(subClasses
						.getFlattened());
			}
		}
		// We now extract a locality-based module. STAR provides the smallest ones
		SyntacticLocalityModuleExtractor sme = new SyntacticLocalityModuleExtractor(
				m, o, ModuleType.STAR);
		Set<OWLAxiom> mod = sme.extract(seedSig);
		System.out
				.println("TutorialSnippets.testModularization() "
						+ mod.size());
	}
    @Test
	public void testIndividual() throws Exception {
		// :Mary is an instance of the class :Person.
		OWLOntologyManager m = create();
		// The IRIs used here are taken from the OWL 2 Primer
		String base = "http://example.com/owl/families/";
		PrefixManager pm = new DefaultPrefixManager(
				base);
		OWLClass person = df
				.getOWLClass(":Person", pm);
		OWLNamedIndividual mary = df
				.getOWLNamedIndividual(":Mary", pm);
		// create a ClassAssertion to specify that :Mary is an instance of :Person
		OWLClassAssertionAxiom classAssertion = df
				.getOWLClassAssertionAxiom(person,
						mary);
		OWLOntology o = m.createOntology(IRI
				.create(base));
		// Add the class assertion
		m.addAxiom(o, classAssertion);
		// Dump the ontology to stdout
		m.saveOntology(o,
				new SystemOutDocumentTarget());
	}
    @Test
	public void testDataRanges() throws Exception {
		// Data ranges are used as the types of literals, as the ranges for data properties
		OWLOntologyManager m = create();
		// OWLDatatype represents named datatypes in OWL
		// The OWL2Datatype enum defines built in OWL 2 Datatypes
		OWLDatatype integer = df
				.getOWLDatatype(OWL2Datatype.XSD_INTEGER
						.getIRI());
		// For common data types there are some convenience methods of OWLDataFactory
		OWLDatatype integerDatatype = df
				.getIntegerOWLDatatype();
		OWLDatatype floatDatatype = df
				.getFloatOWLDatatype();
		OWLDatatype doubleDatatype = df
				.getDoubleOWLDatatype();
		OWLDatatype booleanDatatype = df
				.getBooleanOWLDatatype();
		// The top datatype is rdfs:Literal
		OWLDatatype rdfsLiteral = df.getTopDatatype();
		// Custom data ranges can be built up from these basic datatypes
		// Get hold of a literal that is an integer value 18
		OWLLiteral eighteen = df.getOWLLiteral(18);
		OWLDatatypeRestriction integerGE18 = df
				.getOWLDatatypeRestriction(integer,
						OWLFacet.MIN_INCLUSIVE,
						eighteen);
		OWLDataProperty hasAge = df
				.getOWLDataProperty(IRI
						.create("http://www.semanticweb.org/ontologies/dataranges#hasAge"));
		OWLDataPropertyRangeAxiom rangeAxiom = df
				.getOWLDataPropertyRangeAxiom(hasAge,
						integerGE18);
		OWLOntology o = m
				.createOntology(IRI
						.create("http://www.semanticweb.org/ontologies/dataranges"));
		// Add the range axiom to our ontology
		m.addAxiom(o, rangeAxiom);
		// Now create a datatype definition axiom
		OWLDatatypeDefinitionAxiom datatypeDef = df
				.getOWLDatatypeDefinitionAxiom(
						df.getOWLDatatype(IRI
								.create("http://www.semanticweb.org/ontologies/dataranges#age")),
						integerGE18);
		// Add the definition to our ontology
		m.addAxiom(o, datatypeDef);
		// Dump our ontology
		m.saveOntology(o,
				new SystemOutDocumentTarget());
	}
    @Test
	public void testPropertyAssertions()
			throws Exception {
		//how to specify various property assertions for individuals
		OWLOntologyManager m = create();
		IRI ontologyIRI = IRI
				.create("http://example.com/owl/families/");
		OWLOntology o = m.createOntology(ontologyIRI);
		PrefixManager pm = new DefaultPrefixManager(
				ontologyIRI.toString());
		// Let's specify the :John has a wife :Mary
		// Get hold of the necessary individuals and object property
		OWLNamedIndividual john = df
				.getOWLNamedIndividual(":John", pm);
		OWLNamedIndividual mary = df
				.getOWLNamedIndividual(":Mary", pm);
		OWLObjectProperty hasWife = df
				.getOWLObjectProperty(":hasWife", pm);
		// To specify that :John is related to :Mary via the :hasWife property we create an object property
		// assertion and add it to the ontology
		OWLObjectPropertyAssertionAxiom propertyAssertion = df
				.getOWLObjectPropertyAssertionAxiom(
						hasWife, john, mary);
		m.addAxiom(o, propertyAssertion);
		// Now let's specify that :John is aged 51.
		// Get hold of a data property called :hasAge
		OWLDataProperty hasAge = df
				.getOWLDataProperty(":hasAge", pm);
		// To specify that :John has an age of 51 we create a data property assertion and add it to the ontology
		OWLDataPropertyAssertionAxiom dataPropertyAssertion = df
				.getOWLDataPropertyAssertionAxiom(
						hasAge, john, 51);
		m.addAxiom(o, dataPropertyAssertion);
	}

	/**
	 * Print the class hierarchy for the given ontology from this class down,
	 * assuming this class is at the given level. Makes no attempt to deal
	 * sensibly with multiple inheritance.
	 */
	public void printHierarchy(OWLOntology o,
			OWLClass clazz) throws OWLException {
		OWLReasoner reasoner = reasonerFactory
				.createNonBufferingReasoner(o);
		printHierarchy(reasoner, clazz, 0, new HashSet<OWLClass>());
		/* Now print out any unsatisfiable classes */
		for (OWLClass cl : o.getClassesInSignature()) {
			if (!reasoner.isSatisfiable(cl)) {
				System.out.println("XXX: "
						+ labelFor(cl, o));
			}
		}
		reasoner.dispose();
	}

	class LabelExtractor extends OWLObjectVisitorExAdapter<String> implements
			OWLAnnotationObjectVisitorEx<String> {

@Override
		public String visit(OWLAnnotation annotation) {
			/*
			* If it's a label, grab it as the result. Note that if there are
			* multiple labels, the last one will be used.
			*/
			if (annotation.getProperty().isLabel()) {
				OWLLiteral c = (OWLLiteral) annotation
						.getValue();
				return c.getLiteral();
			}
			return null;
		}

	}

	// a visitor to extract label annotations
	LabelExtractor le = new LabelExtractor();

	private String labelFor(OWLEntity clazz,
			OWLOntology o) {
		Set<OWLAnnotation> annotations = clazz
				.getAnnotations(o);
		for (OWLAnnotation anno : annotations) {
			String result = anno.accept(le);
			if (result != null) {
				return result;
			}
		}
		return clazz.getIRI().toString();
	}

	public void printHierarchy(OWLReasoner reasoner,
			OWLClass clazz, int level, Set<OWLClass> visited)
			throws OWLException {
		//Only print satisfiable classes to skip Nothing
		if (!visited.contains(clazz) && reasoner.isSatisfiable(clazz)) {
			visited.add(clazz);
			for (int i = 0; i < level * 4; i++) {
				System.out.print(" ");
			}
			System.out.println(labelFor(clazz,
					reasoner.getRootOntology()));
			/* Find the children and recurse */
			NodeSet<OWLClass> subClasses = reasoner
					.getSubClasses(clazz, true);
			for (OWLClass child : subClasses
					.getFlattened()) {

					printHierarchy(reasoner, child,
							level + 1, visited);

			}
		}
	}
    @Test
	public void testHierarchy() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Get Thing
		OWLClass clazz = df.getOWLThing();
		System.out.println("Class       : " + clazz);
		// Print the hierarchy below thing
		printHierarchy(o, clazz);
	}
    @Test
	public void testRendering() throws Exception {
		//Simple Rendering Example. Reads an ontology and then renders it.
		OWLOntologyManager m = create();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(pizza_iri);
		// Register the ontology storer with the manager
		m.addOntologyStorer(new OWLTutorialSyntaxOntologyStorer());
		// Save using a different format
		m.saveOntology(o,
				new OWLTutorialSyntaxOntologyFormat(),
				new SystemOutDocumentTarget());
	}
    @Test
	public void testCheckProfile() throws Exception {
		OWLOntologyManager m = create();
		OWLOntology o = m.createOntology(pizza_iri);
		// Available profiles: DL, EL, QL, RL, OWL2 (Full)
		OWL2DLProfile profile = new OWL2DLProfile();
		OWLProfileReport report = profile
				.checkOntology(o);
		for (OWLProfileViolation v : report
				.getViolations()) {
			System.out.println(v);
		}
	}
//	public final class ThreadSafeBinding implements OWLImplementationBinding {
//		public OWLOntologyManager getOWLOntologyManager(OWLDataFactory d) {
//			return new LockingOWLOntologyManagerImpl(d);
//		}
//
//		public OWLOntology getOWLOntology(OWLOntologyManager oom, OWLOntologyID id) {
//			return new LockingOWLOntologyImpl(oom, id);
//		}
//
//		public OWLDataFactory getOWLDataFactory() {
//			return DataFactoryCSR.getInstance();
//		}
//	}
}
