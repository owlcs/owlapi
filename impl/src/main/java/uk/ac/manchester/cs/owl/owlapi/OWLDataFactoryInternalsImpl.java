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
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class OWLDataFactoryInternalsImpl implements OWLDataFactoryInternals {
	private Map<IRI, WeakReference<? extends OWLEntity>> classesByURI;
	private Map<IRI, WeakReference<? extends OWLEntity>> objectPropertiesByURI;
	private Map<IRI, WeakReference<? extends OWLEntity>> dataPropertiesByURI;
	private Map<IRI, WeakReference<? extends OWLEntity>> datatypesByURI;
	private Map<IRI, WeakReference<? extends OWLEntity>> individualsByURI;
	private Map<IRI, WeakReference<? extends OWLEntity>> annotationPropertiesByURI;
	private final OWLDataFactory factory;

	public OWLDataFactoryInternalsImpl(OWLDataFactory f) {
		factory = f;
		classesByURI = new WeakHashMap<IRI, WeakReference<? extends OWLEntity>>();
		objectPropertiesByURI = new WeakHashMap<IRI, WeakReference<? extends OWLEntity>>();
		dataPropertiesByURI = new WeakHashMap<IRI, WeakReference<? extends OWLEntity>>();
		datatypesByURI = new WeakHashMap<IRI, WeakReference<? extends OWLEntity>>();
		individualsByURI = new WeakHashMap<IRI, WeakReference<? extends OWLEntity>>();
		annotationPropertiesByURI = new WeakHashMap<IRI, WeakReference<? extends OWLEntity>>();
	}

	private OWLEntity unwrap(Map<IRI, WeakReference<? extends OWLEntity>> map,
			IRI iri, BuildableObjects type) {
		OWLEntity toReturn = null;
		while (toReturn == null) {
			WeakReference<? extends OWLEntity> r = map.get(iri);
			if (r == null || r.get() == null) {
				toReturn = type.build(factory, iri);
				r = new WeakReference<OWLEntity>(toReturn);
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