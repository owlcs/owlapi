package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import org.semanticweb.owlapi.model.IRI;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;

public class InternalsTester extends Tester {
	public void run(OWLDataFactoryInternals toTest) {
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

	private void singleRunClasses(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLClass(iri);
	}

	private void singleRunObjectProp(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLObjectProperty(iri);
	}

	private void singleRunDataprop(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLDataProperty(iri);
	}

	private void singleRunDatatype(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLDatatype(iri);
	}

	private void singleRunIndividuals(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLNamedIndividual(iri);
	}

	private void singleRunAnnotations(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLAnnotationProperty(iri);
	}
}