package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
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

public final class InternalsCWR implements
		OWLDataFactoryInternals {
	private ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> classesByURI;
	private ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> objectPropertiesByURI;
	private ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> dataPropertiesByURI;
	private ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> datatypesByURI;
	private ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> individualsByURI;
	private ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> annotationPropertiesByURI;
	private final OWLDataFactory factory;
	private final Thread reaper = new Thread() {
		public void run() {
			this.setPriority(MIN_PRIORITY);
			while (true) {
				clean(classesByURI);
				clean(dataPropertiesByURI);
				clean(annotationPropertiesByURI);
				clean(datatypesByURI);
				clean(individualsByURI);
				clean(objectPropertiesByURI);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void clean(Map<IRI, WeakReference<? extends OWLEntity>> map) {
			if (map != null) {
				List<IRI> l = new ArrayList<IRI>();
				for (Map.Entry<IRI, WeakReference<? extends OWLEntity>> w : map
						.entrySet()) {
					if (w.getValue().get() == null) {
						l.add(w.getKey());
					}
				}
				for (IRI i : l) {
					map.remove(i);
				}
				
			}
		}
	};

	public InternalsCWR(OWLDataFactory f) {
		factory = f;
		classesByURI = new ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>>();
		objectPropertiesByURI = new ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>>();
		dataPropertiesByURI = new ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>>();
		datatypesByURI = new ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>>();
		individualsByURI = new ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>>();
		annotationPropertiesByURI = new ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>>();
		reaper.setDaemon(true);
		reaper.start();
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