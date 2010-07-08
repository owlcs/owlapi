package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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


public class LockingOWLDataFactoryInternals implements
		OWLDataFactoryInternals {
	private final Map<IRI, OWLClass> classesByURI;
	private final Lock classesLock = new ReentrantLock();
	private final Map<IRI, OWLObjectProperty> objectPropertiesByURI;
	private final Lock objectLock = new ReentrantLock();
	private final Map<IRI, OWLDataProperty> dataPropertiesByURI;
	private final Lock dataLock = new ReentrantLock();
	private final Map<IRI, OWLDatatype> datatypesByURI;
	private final Lock dataTypeLock = new ReentrantLock();
	private final Map<IRI, OWLNamedIndividual> individualsByURI;
	private final Lock individualsLock = new ReentrantLock();
	private final Map<IRI, OWLAnnotationProperty> annotationPropertiesByURI;
	private final Lock annotationPropertiesLock = new ReentrantLock();
	private final OWLDataFactory factory;

	public LockingOWLDataFactoryInternals(OWLDataFactory f) {
		factory = f;
		classesByURI = new WeakHashMap<IRI, OWLClass>();
		objectPropertiesByURI = new HashMap<IRI, OWLObjectProperty>();
		dataPropertiesByURI = new HashMap<IRI, OWLDataProperty>();
		datatypesByURI = new HashMap<IRI, OWLDatatype>();
		individualsByURI = new HashMap<IRI, OWLNamedIndividual>();
		annotationPropertiesByURI = new HashMap<IRI, OWLAnnotationProperty>();
	}

	public void purge() {
		boolean done = false;
		while (!done) {
			if (classesLock.tryLock()) {
				try {
					classesByURI.clear();
					done = true;
				} finally {
					classesLock.unlock();
				}
			}
		}
		done = false;
		while (!done) {
			if (objectLock.tryLock()) {
				try {
					objectPropertiesByURI.clear();
					done = true;
				} finally {
					objectLock.unlock();
				}
			}
		}
		done = false;
		while (!done) {
			if (dataLock.tryLock()) {
				try {
					dataPropertiesByURI.clear();
					done = true;
				} finally {
					dataLock.unlock();
				}
			}
		}
		done = false;
		while (!done) {
			if (dataTypeLock.tryLock()) {
				try {
					datatypesByURI.clear();
					done = true;
				} finally {
					dataTypeLock.unlock();
				}
			}
		}
		done = false;
		while (!done) {
			if (individualsLock.tryLock()) {
				try {
					individualsByURI.clear();
					done = true;
				} finally {
					individualsLock.unlock();
				}
			}
		}
		done = false;
		while (!done) {
			if (annotationPropertiesLock.tryLock()) {
				try {
					annotationPropertiesByURI.clear();
					done = true;
				} finally {
					annotationPropertiesLock.unlock();
				}
			}
		}
	}

	public OWLClass getOWLClass(IRI iri) {
		while (true) {
			if (classesLock.tryLock()) {
				try {
					OWLClass cls = classesByURI.get(iri);
					if (cls == null) {
						cls = new OWLClassImpl(factory, iri);
						classesByURI.put(iri, cls);
					}
					return cls;
				} finally {
					classesLock.unlock();
				}
			}
		}
	}

	public OWLObjectProperty getOWLObjectProperty(IRI iri) {
		while (true) {
			if (objectLock.tryLock()) {
				try {
					OWLObjectProperty prop = objectPropertiesByURI.get(iri);
					if (prop == null) {
						prop = new OWLObjectPropertyImpl(factory, iri);
						objectPropertiesByURI.put(iri, prop);
					}
					return prop;
				} finally {
					objectLock.unlock();
				}
			}
		}
	}

	public OWLDataProperty getOWLDataProperty(IRI iri) {
		while (true) {
			if (dataLock.tryLock()) {
				try {
					OWLDataProperty prop = dataPropertiesByURI.get(iri);
					if (prop == null) {
						prop = new OWLDataPropertyImpl(factory, iri);
						dataPropertiesByURI.put(iri, prop);
					}
					return prop;
				} finally {
					dataLock.unlock();
				}
			}
		}
	}

	public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
		while (true) {
			if (individualsLock.tryLock()) {
				try {
					OWLNamedIndividual ind = individualsByURI.get(iri);
					if (ind == null) {
						ind = new OWLNamedIndividualImpl(factory, iri);
						individualsByURI.put(iri, ind);
					}
					return ind;
				} finally {
					individualsLock.unlock();
				}
			}
		}
	}

	public OWLDatatype getOWLDatatype(IRI iri) {
		while (true) {
			if (dataTypeLock.tryLock()) {
				try {
					OWLDatatype dt = datatypesByURI.get(iri);
					if (dt == null) {
						dt = new OWLDatatypeImpl(factory, iri);
						datatypesByURI.put(iri, dt);
					}
					return dt;
				} finally {
					dataTypeLock.unlock();
				}
			}
		}
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
		while (true) {
			if (annotationPropertiesLock.tryLock()) {
				try {
					OWLAnnotationProperty prop = annotationPropertiesByURI
							.get(iri);
					if (prop == null) {
						prop = new OWLAnnotationPropertyImpl(factory, iri);
						annotationPropertiesByURI.put(iri, prop);
					}
					return prop;
				} finally {
					annotationPropertiesLock.unlock();
				}
			}
		}
	}
}