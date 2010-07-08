package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import org.semanticweb.owlapi.apibinding.configurables.Computable;
import org.semanticweb.owlapi.apibinding.configurables.MemoizingCache;
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

public class FastLockingOWLDataFactoryInternals implements
		OWLDataFactoryInternals {
	private abstract class AbstractComputable<T> implements Computable<T> {
		protected IRI iri;
		private RuntimeException thrownException;

		AbstractComputable(IRI i) {
			this.iri = i;
		}

		public Throwable thrownException() {
			return thrownException;
		}

		public boolean hasThrownException() {
			return thrownException != null;
		}

		public T compute() {
			try {
				return actualCompute();
			} catch (RuntimeException e) {
				thrownException = e;
			}
			return null;
		}

		abstract T actualCompute();
	}

	private final class ClassComputable extends AbstractComputable<OWLClass> {
		ClassComputable(IRI i) {
			super(i);
		}

		OWLClass actualCompute() {
			return new OWLClassImpl(factory, this.iri);
		}
	}

	private final class ObjectPropertyComputable extends
			AbstractComputable<OWLObjectProperty> {
		ObjectPropertyComputable(IRI i) {
			super(i);
		}

		OWLObjectProperty actualCompute() {
			return new OWLObjectPropertyImpl(factory, this.iri);
		}
	}

	private final class DataPropertyComputable extends
			AbstractComputable<OWLDataProperty> {
		DataPropertyComputable(IRI i) {
			super(i);
		}

		@Override
		OWLDataProperty actualCompute() {
			return new OWLDataPropertyImpl(factory, iri);
		}
	}

	private final class OWLNamedIndividualComputable extends
			AbstractComputable<OWLNamedIndividual> {
		public OWLNamedIndividualComputable(IRI i) {
			super(i);
		}

		@Override
		OWLNamedIndividual actualCompute() {
			return new OWLNamedIndividualImpl(factory, iri);
		}
	}

	private final class OWLDatatypeComputable extends
			AbstractComputable<OWLDatatype> {
		public OWLDatatypeComputable(IRI i) {
			super(i);
		}

		@Override
		OWLDatatype actualCompute() {
			return new OWLDatatypeImpl(factory, iri);
		}
	}

	private final class OWLAnnotationPropertyComputable extends
			AbstractComputable<OWLAnnotationProperty> {
		public OWLAnnotationPropertyComputable(IRI i) {
			super(i);
		}

		@Override
		OWLAnnotationProperty actualCompute() {
			return new OWLAnnotationPropertyImpl(factory, iri);
		}
	}

	private final MemoizingCache<IRI, OWLClass> classesByURI;
	private final MemoizingCache<IRI, OWLObjectProperty> objectPropertiesByURI;
	private final MemoizingCache<IRI, OWLDataProperty> dataPropertiesByURI;
	private final MemoizingCache<IRI, OWLDatatype> datatypesByURI;
	private final MemoizingCache<IRI, OWLNamedIndividual> individualsByURI;
	private final MemoizingCache<IRI, OWLAnnotationProperty> annotationPropertiesByURI;
	final OWLDataFactory factory;

	public FastLockingOWLDataFactoryInternals(OWLDataFactory f) {
		factory = f;
		classesByURI = new MemoizingCache<IRI, OWLClass>();
		objectPropertiesByURI = new MemoizingCache<IRI, OWLObjectProperty>();
		dataPropertiesByURI = new MemoizingCache<IRI, OWLDataProperty>();
		datatypesByURI = new MemoizingCache<IRI, OWLDatatype>();
		individualsByURI = new MemoizingCache<IRI, OWLNamedIndividual>();
		annotationPropertiesByURI = new MemoizingCache<IRI, OWLAnnotationProperty>();
	}

	public void purge() {
		classesByURI.clear();
		objectPropertiesByURI.clear();
		dataPropertiesByURI.clear();
		datatypesByURI.clear();
		individualsByURI.clear();
		annotationPropertiesByURI.clear();
	}

	public OWLClass getOWLClass(IRI iri) {
		return classesByURI.get(new ClassComputable(iri), iri);
	}

	public OWLObjectProperty getOWLObjectProperty(IRI iri) {
		return objectPropertiesByURI
				.get(new ObjectPropertyComputable(iri), iri);
	}

	public OWLDataProperty getOWLDataProperty(IRI iri) {
		return dataPropertiesByURI.get(new DataPropertyComputable(iri), iri);
	}

	public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
		return individualsByURI.get(new OWLNamedIndividualComputable(iri), iri);
	}

	public OWLDatatype getOWLDatatype(IRI iri) {
		return datatypesByURI.get(new OWLDatatypeComputable(iri), iri);
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
		return annotationPropertiesByURI.get(
				new OWLAnnotationPropertyComputable(iri), iri);
	}
}