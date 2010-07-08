package uk.ac.manchester.cs.owl.owlapi;

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
	private Map<IRI, OWLClass> classesByURI;
	private Map<IRI, OWLObjectProperty> objectPropertiesByURI;
	private Map<IRI, OWLDataProperty> dataPropertiesByURI;
	private Map<IRI, OWLDatatype> datatypesByURI;
	private Map<IRI, OWLNamedIndividual> individualsByURI;
	private Map<IRI, OWLAnnotationProperty> annotationPropertiesByURI;
	
	private final OWLDataFactory factory;

	public OWLDataFactoryInternalsImpl(OWLDataFactory f) {
		factory = f;
		classesByURI = new WeakHashMap<IRI, OWLClass>();
		objectPropertiesByURI = new HashMap<IRI, OWLObjectProperty>();
		dataPropertiesByURI = new HashMap<IRI, OWLDataProperty>();
		datatypesByURI = new HashMap<IRI, OWLDatatype>();
		individualsByURI = new HashMap<IRI, OWLNamedIndividual>();
		annotationPropertiesByURI = new HashMap<IRI, OWLAnnotationProperty>();
	}


	public OWLClass getOWLClass(IRI iri) {
        OWLClass cls = classesByURI.get(iri);
        if (cls == null) {
            cls = new OWLClassImpl(factory, iri);
            classesByURI.put(iri, cls);
        }
        return cls;
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
        OWLObjectProperty prop = objectPropertiesByURI.get(iri);
        if (prop == null) {
            prop = new OWLObjectPropertyImpl(factory, iri);
            objectPropertiesByURI.put(iri, prop);
        }
        return prop;
    }

    public OWLDataProperty getOWLDataProperty(IRI iri) {
        OWLDataProperty prop = dataPropertiesByURI.get(iri);
        if (prop == null) {
            prop = new OWLDataPropertyImpl(factory, iri);
            dataPropertiesByURI.put(iri, prop);
        }
        return prop;
    }

    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        OWLNamedIndividual ind = individualsByURI.get(iri);
        if (ind == null) {
            ind = new OWLNamedIndividualImpl(factory, iri);
            individualsByURI.put(iri, ind);
        }
        return ind;
    }

    public OWLDatatype getOWLDatatype(IRI iri) {
        OWLDatatype dt = datatypesByURI.get(iri);
        if (dt == null) {
            dt = new OWLDatatypeImpl(factory, iri);
            datatypesByURI.put(iri, dt);
        }
        return dt;
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        OWLAnnotationProperty prop = annotationPropertiesByURI.get(iri);
        if (prop == null) {
            prop = new OWLAnnotationPropertyImpl(factory, iri);
            annotationPropertiesByURI.put(iri, prop);
        }
        return prop;
    }
}