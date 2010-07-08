package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public interface OWLDataFactoryInternals {
	
	OWLClass getOWLClass(IRI iri);

	void purge();

	OWLObjectProperty getOWLObjectProperty(IRI iri);

	OWLDataProperty getOWLDataProperty(IRI iri);

	OWLNamedIndividual getOWLNamedIndividual(IRI iri);

	OWLDatatype getOWLDatatype(IRI iri);

	OWLAnnotationProperty getOWLAnnotationProperty(IRI iri);
}