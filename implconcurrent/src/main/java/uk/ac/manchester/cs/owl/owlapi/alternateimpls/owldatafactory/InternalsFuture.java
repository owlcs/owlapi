package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import org.semanticweb.owlapi.apibinding.configurables.Computable;
import org.semanticweb.owlapi.apibinding.configurables.ComputableAdapter;
import org.semanticweb.owlapi.apibinding.configurables.MemoizingCache;
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

public class InternalsFuture implements OWLDataFactoryInternals {
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

	//	private abstract class AbstractComputable<T> implements Computable<T> {
	//		protected IRI iri;
	//		private RuntimeException thrownException;
	//
	//		AbstractComputable(IRI i) {
	//			this.iri = i;
	//		}
	//
	//		public Throwable thrownException() {
	//			return thrownException;
	//		}
	//
	//		public boolean hasThrownException() {
	//			return thrownException != null;
	//		}
	//
	//		public T compute() {
	//			try {
	//				return actualCompute();
	//			} catch (RuntimeException e) {
	//				thrownException = e;
	//			}
	//			return null;
	//		}
	//
	//		abstract T actualCompute();
	//	}
	//
	//	private final class ClassComputable extends AbstractComputable<OWLClass> {
	//		ClassComputable(IRI i) {
	//			super(i);
	//		}
	//
	//		OWLClass actualCompute() {
	//			return new OWLClassImpl(factory, this.iri);
	//		}
	//	}
	//
	//	private final class ObjectPropertyComputable extends
	//			AbstractComputable<OWLObjectProperty> {
	//		ObjectPropertyComputable(IRI i) {
	//			super(i);
	//		}
	//
	//		OWLObjectProperty actualCompute() {
	//			return new OWLObjectPropertyImpl(factory, this.iri);
	//		}
	//	}
	//
	//	private final class DataPropertyComputable extends
	//			AbstractComputable<OWLDataProperty> {
	//		DataPropertyComputable(IRI i) {
	//			super(i);
	//		}
	//
	//		@Override
	//		OWLDataProperty actualCompute() {
	//			return new OWLDataPropertyImpl(factory, iri);
	//		}
	//	}
	//
	//	private final class OWLNamedIndividualComputable extends
	//			AbstractComputable<OWLNamedIndividual> {
	//		public OWLNamedIndividualComputable(IRI i) {
	//			super(i);
	//		}
	//
	//		@Override
	//		OWLNamedIndividual actualCompute() {
	//			return new OWLNamedIndividualImpl(factory, iri);
	//		}
	//	}
	//
	//	private final class OWLDatatypeComputable extends
	//			AbstractComputable<OWLDatatype> {
	//		public OWLDatatypeComputable(IRI i) {
	//			super(i);
	//		}
	//
	//		@Override
	//		OWLDatatype actualCompute() {
	//			return new OWLDatatypeImpl(factory, iri);
	//		}
	//	}
	//
	//	private final class OWLAnnotationPropertyComputable extends
	//			AbstractComputable<OWLAnnotationProperty> {
	//		public OWLAnnotationPropertyComputable(IRI i) {
	//			super(i);
	//		}
	//
	//		@Override
	//		OWLAnnotationProperty actualCompute() {
	//			return new OWLAnnotationPropertyImpl(factory, iri);
	//		}
	//	}
	private final MemoizingCache<IRI, OWLClass> classesByURI;
	private final MemoizingCache<IRI, OWLObjectProperty> objectPropertiesByURI;
	private final MemoizingCache<IRI, OWLDataProperty> dataPropertiesByURI;
	private final MemoizingCache<IRI, OWLDatatype> datatypesByURI;
	private final MemoizingCache<IRI, OWLNamedIndividual> individualsByURI;
	private final MemoizingCache<IRI, OWLAnnotationProperty> annotationPropertiesByURI;
	final OWLDataFactory factory;

	public InternalsFuture(OWLDataFactory f) {
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

	public OWLClass getOWLClass(final IRI iri) {
		return classesByURI.get(new ComputableAdapter<OWLClass>() {
			public OWLClass compute() {
				return (OWLClass) BuildableObjects.OWLCLASS.build(factory, iri);
			}
		}, iri);
	}

	public OWLObjectProperty getOWLObjectProperty(final IRI iri) {
		return objectPropertiesByURI
				.get(new ComputableAdapter<OWLObjectProperty>() {
					public OWLObjectProperty compute() {
						return (OWLObjectProperty) BuildableObjects.OWLOBJECTPROPERTY.build(factory, iri);
					}
				}, iri);
	}

	public OWLDataProperty getOWLDataProperty(final IRI iri) {
		return dataPropertiesByURI.get(new ComputableAdapter<OWLDataProperty>() {
			public OWLDataProperty compute() {
				return (OWLDataProperty) BuildableObjects.OWLDATAPROPERTY.build(factory, iri);
			}
		}, iri);
	}

	public OWLNamedIndividual getOWLNamedIndividual(final IRI iri) {
		return individualsByURI.get(new ComputableAdapter<OWLNamedIndividual>() {
			public OWLNamedIndividual compute() {
				return (OWLNamedIndividual) BuildableObjects.OWLNAMEDINDIVIDUAL.build(factory, iri);
			}
		}, iri);
	}

	public OWLDatatype getOWLDatatype(final IRI iri) {
		return datatypesByURI.get(new ComputableAdapter<OWLDatatype>() {
			public OWLDatatype compute() {
				return (OWLDatatype) BuildableObjects.OWLDATATYPE.build(factory, iri);
			}
		}, iri);
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(final IRI iri) {
		return annotationPropertiesByURI.get(
				new ComputableAdapter<OWLAnnotationProperty>() {
					public OWLAnnotationProperty compute() {
						return (OWLAnnotationProperty) BuildableObjects.OWLANNOTATIONPROPERTY.build(factory, iri);
					}
				}, iri);
	}
}