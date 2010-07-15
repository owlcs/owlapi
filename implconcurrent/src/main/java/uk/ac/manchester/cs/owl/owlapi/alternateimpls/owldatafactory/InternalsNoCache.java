package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;

public class InternalsNoCache implements
		OWLDataFactoryInternals {
	private final OWLDataFactory factory;

	public InternalsNoCache(OWLDataFactory f) {
		factory = f;
	}

	public void purge() {
	}

	public OWLClass getOWLClass(IRI iri) {
		return new OWLClassImpl(factory, iri);
	}

	public OWLObjectProperty getOWLObjectProperty(IRI iri) {
		return new OWLObjectPropertyImpl(factory, iri);
	}

	public OWLDataProperty getOWLDataProperty(IRI iri) {
		return new OWLDataPropertyImpl(factory, iri);
	}

	public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
		return new OWLNamedIndividualImpl(factory, iri);
	}

	public OWLDatatype getOWLDatatype(IRI iri) {
		return new OWLDatatypeImpl(factory, iri);
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
		return new OWLAnnotationPropertyImpl(factory, iri);
	}
}