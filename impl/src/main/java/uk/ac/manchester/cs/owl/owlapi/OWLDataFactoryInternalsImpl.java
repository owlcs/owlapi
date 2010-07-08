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


	public OWLClass getOWLClass(IRI iri) {
        WeakReference<OWLClass> cls = classesByURI.get(iri);
        if (cls == null) {
            cls = new WeakReference<OWLClass>(new OWLClassImpl(factory, iri));
            classesByURI.put(iri, cls);
        }
        return cls.get();
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
		WeakReference<OWLObjectProperty> prop = objectPropertiesByURI.get(iri);
        if (prop == null) {
            prop = new WeakReference<OWLObjectProperty>(new OWLObjectPropertyImpl(factory, iri));
            objectPropertiesByURI.put(iri, prop);
        }
        return prop.get();
    }

    public OWLDataProperty getOWLDataProperty(IRI iri) {
    	WeakReference<OWLDataProperty> prop = dataPropertiesByURI.get(iri);
        if (prop == null) {
            prop = new WeakReference<OWLDataProperty>(new OWLDataPropertyImpl(factory, iri));
            dataPropertiesByURI.put(iri, prop);
        }
        return prop.get();
    }

    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
    	WeakReference<OWLNamedIndividual> ind = individualsByURI.get(iri);
        if (ind == null) {
            ind = new WeakReference<OWLNamedIndividual>( new OWLNamedIndividualImpl(factory, iri));
            individualsByURI.put(iri, ind);
        }
        return ind.get();
    }

    public OWLDatatype getOWLDatatype(IRI iri) {
    	WeakReference<OWLDatatype> dt = datatypesByURI.get(iri);
        if (dt == null) {
            dt = new WeakReference<OWLDatatype>( new OWLDatatypeImpl(factory, iri));
            datatypesByURI.put(iri, dt);
        }
        return dt.get();
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
    	WeakReference<OWLAnnotationProperty> prop = annotationPropertiesByURI.get(iri);
        if (prop == null) {
            prop = new WeakReference<OWLAnnotationProperty>( new OWLAnnotationPropertyImpl(factory, iri));
            annotationPropertiesByURI.put(iri, prop);
        }
        return prop.get();
    }
}