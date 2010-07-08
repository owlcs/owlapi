package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationSubjectVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationSubjectVisitorEx;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.FastLockingOWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.LockingOWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.NonCachingOWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.NonCachingOWLDataFactoryInternals;
import junit.framework.TestCase;

public class OWLDataFactoryInternalsPerformanceTest extends TestCase {
	private static final int _10000 = 10000;
	private final static OWLDataFactory factory = new NonCachingOWLDataFactoryImpl();

	public OWLDataFactoryInternalsPerformanceTest() {
		init();
	}

	public void testBaseline() {
		OWLDataFactoryInternals i = new NonCachingOWLDataFactoryInternals(
				factory);
		run(i);
	}

	public void testDefault() {
		OWLDataFactoryInternals i = new OWLDataFactoryInternalsImpl(factory);
		run(i);
	}

	public void testFastLock() {
		OWLDataFactoryInternals i = new FastLockingOWLDataFactoryInternals(
				factory);
		run(i);
	}

	public void testLock() {
		OWLDataFactoryInternals i = new LockingOWLDataFactoryInternals(factory);
		run(i);
	}

	public static void main(String[] args) {
		OWLDataFactoryInternalsPerformanceTest t = new OWLDataFactoryInternalsPerformanceTest();
		//for (int i = 0; i < 3; i++) {
		t.testBaseline();
		t.testDefault();
		t.testFastLock();
		t.testLock();
		//}
		long start = System.currentTimeMillis();
		//		for (int i = 0; i < 10; i++) {
		t.testBaseline();
		//		}
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() baseline:\t"
						+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		//for (int i = 0; i < 10; i++) {
		t.testDefault();
		//}
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() default:\t"
						+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		//for (int i = 0; i < 10; i++) {
		t.testFastLock();
		//}
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() fastlock:\t"
						+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		//for (int i = 0; i < 10; i++) {
		t.testLock();
		//}
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() reglock:\t"
						+ (System.currentTimeMillis() - start));
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() waiting for you to capture the memory snapshot");
		while (true) {
		}
	}

	private List<IRI> iriClasses = new ArrayList<IRI>();
	private List<IRI> iriDataproperties = new ArrayList<IRI>();
	private List<IRI> iriObjectProperties = new ArrayList<IRI>();
	private List<IRI> iriIndividuals = new ArrayList<IRI>();
	private List<IRI> iriDatatypes = new ArrayList<IRI>();
	private List<IRI> iriAnnotations = new ArrayList<IRI>();

	private void init() {
		String uriClasses = "urn:test:classes#A";
		add(iriClasses, uriClasses);
		String objects = "urn:test:objectproperties#B";
		add(iriObjectProperties, objects);
		String datas = "urn:test:dataproperties#C";
		add(iriDataproperties, datas);
		String inds = "urn:test:individuals#D";
		add(iriIndividuals, inds);
		String datatypes = "urn:test:datatypes#E";
		add(iriDatatypes, datatypes);
		String annotations = "urn:test:annotations#F";
		add(iriAnnotations, annotations);
	}

	private void run(OWLDataFactoryInternals toTest) {
		for (int i = 0; i < 10; i++) {
			for (IRI iri : iriClasses) {
				singleRunClasses(toTest, iri);
			}
			for (IRI iri : iriObjectProperties) {
				singleRunObjectProp(toTest, iri);
			}
			for (IRI iri : iriDataproperties) {
				singleRunDataprop(toTest, iri);
			}
			for (IRI iri : iriIndividuals) {
				singleRunIndividuals(toTest, iri);
			}
			for (IRI iri : iriDatatypes) {
				singleRunDatatype(toTest, iri);
			}
			for (IRI iri : iriAnnotations) {
				singleRunAnnotations(toTest, iri);
			}
		}
	}

	public void add(List<IRI> l, String s) {
		for (int i = 0; i < _10000; i++) {
			l.add(IRI.create(s + i));
		}
	}

	private void singleRunClasses(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLClass(iri);
	}

	private void singleRunObjectProp(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLObjectProperty(iri);
	}

	private void singleRunDataprop(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLDataProperty(iri);
	}

	private void singleRunDatatype(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLDatatype(iri);
	}

	private void singleRunIndividuals(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLNamedIndividual(iri);
	}

	private void singleRunAnnotations(OWLDataFactoryInternals toTest, IRI iri) {
		toTest.getOWLAnnotationProperty(iri);
	}
}
