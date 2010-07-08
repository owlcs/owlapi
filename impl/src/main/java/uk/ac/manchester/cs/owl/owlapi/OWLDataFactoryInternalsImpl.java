package uk.ac.manchester.cs.owl.owlapi;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class OWLDataFactoryInternalsImpl implements OWLDataFactoryInternals {
	private Map<IRI, WeakReference<OWLClass>> classesByURI;
	private Map<IRI, WeakReference<OWLObjectProperty>> objectPropertiesByURI;
	private Map<IRI, WeakReference<OWLDataProperty>> dataPropertiesByURI;
	private Map<IRI, WeakReference<OWLDatatype>> datatypesByURI;
	private Map<IRI, WeakReference<OWLNamedIndividual>> individualsByURI;
	private Map<IRI, WeakReference<OWLAnnotationProperty>> annotationPropertiesByURI;
	private final OWLDataFactory factory;

	public OWLDataFactoryInternalsImpl(OWLDataFactory f) {
		factory = f;
		classesByURI = new WeakHashMap<IRI, WeakReference<OWLClass>>();
		objectPropertiesByURI = new WeakHashMap<IRI, WeakReference<OWLObjectProperty>>();
		dataPropertiesByURI = new WeakHashMap<IRI, WeakReference<OWLDataProperty>>();
		datatypesByURI = new WeakHashMap<IRI, WeakReference<OWLDatatype>>();
		individualsByURI = new WeakHashMap<IRI, WeakReference<OWLNamedIndividual>>();
		annotationPropertiesByURI = new WeakHashMap<IRI, WeakReference<OWLAnnotationProperty>>();
	}

	private <T> T unwrap(Map<IRI, WeakReference<T>> map, IRI iri,
			BuildableObjects type) {
		T toReturn = null;
		while (toReturn == null) {
			WeakReference<T> r = map.get(iri);
			if (r == null || r.get() == null) {
				toReturn = type.build(factory, iri);
				r = new WeakReference<T>(toReturn);
				map.put(iri, r);
			} else {
				toReturn = r.get();
			}
		}
		return toReturn;
	}

	private enum BuildableObjects {
		OWLCLASS {
			@Override
			<T> T build(OWLDataFactory f, IRI iri) {
				return (T) new OWLClassImpl(f, iri);
			}
		},
		OWLOBJECTPROPERTY {
			@Override
			<T> T build(OWLDataFactory f, IRI iri) {
				return (T) new OWLObjectPropertyImpl(f, iri);
			}
		},
		OWLDATAPROPERTY {
			@Override
			<T> T build(OWLDataFactory f, IRI iri) {
				return (T) new OWLDataPropertyImpl(f, iri);
			}
		},
		OWLNAMEDINDIVIDUAL {
			@Override
			<T> T build(OWLDataFactory f, IRI iri) {
				return (T) new OWLNamedIndividualImpl(f, iri);
			}
		},
		OWLDATATYPE {
			@Override
			<T> T build(OWLDataFactory f, IRI iri) {
				return (T) new OWLDatatypeImpl(f, iri);
			}
		},
		OWLANNOTATIONPROPERTY {
			@Override
			<T> T build(OWLDataFactory f, IRI iri) {
				return (T) new OWLAnnotationPropertyImpl(f, iri);
			}
		};
		abstract <T> T build(OWLDataFactory f, IRI iri);
	}

	public OWLClass getOWLClass(IRI iri) {
		return unwrap(classesByURI, iri, BuildableObjects.OWLCLASS);
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
		return unwrap(objectPropertiesByURI, iri, BuildableObjects.OWLOBJECTPROPERTY);
	}

	public OWLDataProperty getOWLDataProperty(IRI iri) {
		return unwrap(dataPropertiesByURI, iri, BuildableObjects.OWLDATAPROPERTY);
	}

	public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
		return unwrap(individualsByURI, iri, BuildableObjects.OWLNAMEDINDIVIDUAL);
	}

	public OWLDatatype getOWLDatatype(IRI iri) {
		return unwrap(datatypesByURI, iri, BuildableObjects.OWLDATATYPE);
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
		return unwrap(annotationPropertiesByURI, iri, BuildableObjects.OWLANNOTATIONPROPERTY);
	}
}