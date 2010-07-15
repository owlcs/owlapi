package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;

public class InternalsCSR
		implements OWLDataFactoryInternals {
	private ConcurrentHashMap<IRI, OWLEntity> classesByURI;
	private ConcurrentHashMap<IRI, OWLEntity> objectPropertiesByURI;
	private ConcurrentHashMap<IRI, OWLEntity> dataPropertiesByURI;
	private ConcurrentHashMap<IRI, OWLEntity> datatypesByURI;
	private ConcurrentHashMap<IRI, OWLEntity> individualsByURI;
	private ConcurrentHashMap<IRI, OWLEntity> annotationPropertiesByURI;
	private final OWLDataFactory factory;

	public InternalsCSR(
			OWLDataFactory f) {
		factory = f;
		classesByURI = new ConcurrentHashMap<IRI, OWLEntity>();
		objectPropertiesByURI = new ConcurrentHashMap<IRI, OWLEntity>();
		dataPropertiesByURI = new ConcurrentHashMap<IRI, OWLEntity>();
		datatypesByURI = new ConcurrentHashMap<IRI, OWLEntity>();
		individualsByURI = new ConcurrentHashMap<IRI, OWLEntity>();
		annotationPropertiesByURI = new ConcurrentHashMap<IRI, OWLEntity>();
	}

	private OWLEntity unwrap(Map<IRI, OWLEntity> map, IRI iri,
			BuildableObjects type) {
		OWLEntity toReturn = map.get(iri);
		if (toReturn == null) {
			toReturn = type.build(factory, iri);
			map.put(iri, toReturn);
		}
		return toReturn;
	}

	private enum BuildableObjects {
		OWLCLASS {
			@Override
			OWLEntity build(OWLDataFactory f, IRI iri) {
				return new OWLClassImpl(f, iri);
			}
		},
		OWLOBJECTPROPERTY {
			@Override
			OWLEntity build(OWLDataFactory f, IRI iri) {
				return new OWLObjectPropertyImpl(f, iri);
			}
		},
		OWLDATAPROPERTY {
			@Override
			OWLEntity build(OWLDataFactory f, IRI iri) {
				return new OWLDataPropertyImpl(f, iri);
			}
		},
		OWLNAMEDINDIVIDUAL {
			@Override
			OWLEntity build(OWLDataFactory f, IRI iri) {
				return new OWLNamedIndividualImpl(f, iri);
			}
		},
		OWLDATATYPE {
			@Override
			OWLEntity build(OWLDataFactory f, IRI iri) {
				return new OWLDatatypeImpl(f, iri);
			}
		},
		OWLANNOTATIONPROPERTY {
			@Override
			OWLEntity build(OWLDataFactory f, IRI iri) {
				return new OWLAnnotationPropertyImpl(f, iri);
			}
		};
		abstract OWLEntity build(OWLDataFactory f, IRI iri);
	}

	public OWLClass getOWLClass(IRI iri) {
		return (OWLClass) unwrap(classesByURI, iri, BuildableObjects.OWLCLASS);
	}

	public void purge() {
		classesByURI.clear();
		objectPropertiesByURI.clear();
		dataPropertiesByURI.clear();
		datatypesByURI.clear();
		individualsByURI.clear();
		annotationPropertiesByURI.clear();
	}

	public OWLObjectProperty getOWLObjectProperty(IRI iri) {
		return (OWLObjectProperty) unwrap(objectPropertiesByURI, iri,
				BuildableObjects.OWLOBJECTPROPERTY);
	}

	public OWLDataProperty getOWLDataProperty(IRI iri) {
		return (OWLDataProperty) unwrap(dataPropertiesByURI, iri,
				BuildableObjects.OWLDATAPROPERTY);
	}

	public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
		return (OWLNamedIndividual) unwrap(individualsByURI, iri,
				BuildableObjects.OWLNAMEDINDIVIDUAL);
	}

	public OWLDatatype getOWLDatatype(IRI iri) {
		return (OWLDatatype) unwrap(datatypesByURI, iri,
				BuildableObjects.OWLDATATYPE);
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
		return (OWLAnnotationProperty) unwrap(annotationPropertiesByURI, iri,
				BuildableObjects.OWLANNOTATIONPROPERTY);
	}
}