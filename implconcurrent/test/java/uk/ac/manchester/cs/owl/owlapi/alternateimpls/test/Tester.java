package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;

public class Tester {
		public static final int _10 = 10;
		public static final int _10000 = 10000;
		protected List<IRI> iriClasses = new ArrayList<IRI>();
		protected List<IRI> iriDataproperties = new ArrayList<IRI>();
		protected List<IRI> iriObjectProperties = new ArrayList<IRI>();
		protected List<IRI> iriIndividuals = new ArrayList<IRI>();
		protected List<IRI> iriDatatypes = new ArrayList<IRI>();
		protected List<IRI> iriAnnotations = new ArrayList<IRI>();

		public Tester() {
			init();
		}

		private void init() {
			String uriClasses = "urn:test:classes#A";
			add(iriClasses, uriClasses);
			String objects = "urn:test:objectproperties#B";
			add(iriObjectProperties, objects);
			String datas = "urn:test:dataproperties#C";
			add(iriDataproperties, datas);
			String inds = "urn:test:individuals#D";
			add(iriIndividuals, inds);
			String datatypes = "urn:test:datatypes#E";
			add(iriDatatypes, datatypes);
			String annotations = "urn:test:annotations#F";
			add(iriAnnotations, annotations);
		}

		public void run(OWLDataFactory toTest) {
			for (int i = 0; i < _10; i++) {
				for (IRI iri : iriClasses) {
					singleRunClasses(toTest, iri);
				}
				for (IRI iri : iriObjectProperties) {
					singleRunObjectProp(toTest, iri);
				}
				for (IRI iri : iriDataproperties) {
					singleRunDataprop(toTest, iri);
				}
				for (IRI iri : iriIndividuals) {
					singleRunIndividuals(toTest, iri);
				}
				for (IRI iri : iriDatatypes) {
					singleRunDatatype(toTest, iri);
				}
				for (IRI iri : iriAnnotations) {
					singleRunAnnotations(toTest, iri);
				}
			}
		}

		private void add(List<IRI> l, String s) {
			for (int i = 0; i < _10000; i++) {
				l.add(IRI.create(s + i));
			}
		}

		private void singleRunClasses(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLClass(iri);
		}

		private void singleRunObjectProp(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLObjectProperty(iri);
		}

		private void singleRunDataprop(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLDataProperty(iri);
		}

		private void singleRunDatatype(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLDatatype(iri);
		}

		private void singleRunIndividuals(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLNamedIndividual(iri);
		}

		private void singleRunAnnotations(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLAnnotationProperty(iri);
		}
	}