package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.ConcurrentHashMapStrongReferencesOWLDataFactoryInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.FastLockingOWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.LockingWeakReferencesOwlDataFactoryInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.NonCachingOWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.NonCachingOWLDataFactoryInternals;

public class OWLDataFactoryInternalsPerformanceTest extends TestCase {
	private final static OWLDataFactory factory = new NonCachingOWLDataFactoryImpl();
	private final InternalsTester tester = new InternalsTester();

	public void testBaseline() {
		OWLDataFactoryInternals i = new NonCachingOWLDataFactoryInternals(
				factory);
		tester.run(i);
	}

	public void testDefault() {
		OWLDataFactoryInternals i = new OWLDataFactoryInternalsImpl(factory);
		tester.run(i);
	}

	public void testFastLock() {
		OWLDataFactoryInternals i = new FastLockingOWLDataFactoryInternals(
				factory);
		tester.run(i);
	}

	public void testLockWeakReferences() {
		OWLDataFactoryInternals i = new LockingWeakReferencesOwlDataFactoryInternalsImpl(
				factory);
		tester.run(i);
	}

	//	public void testConcurrentHashMaps() {
	//		OWLDataFactoryInternals i = new ConcurrentHashMapOWLDataFactoryInternalsImpl(
	//				factory);
	//		tester.run(i);
	//	}
	public void testConcurrentHashMapsStrongRefs() {
		OWLDataFactoryInternals i = new ConcurrentHashMapStrongReferencesOWLDataFactoryInternalsImpl(
				factory);
		tester.run(i);
	}

	@Override
	protected void tearDown() throws Exception {
		System.gc();
	}

	public static void main(String[] args) {
		OWLDataFactoryInternalsPerformanceTest t = new OWLDataFactoryInternalsPerformanceTest();
		//for (int i = 0; i < 3; i++) {
		t.testBaseline();
		System.gc();
		t.testDefault();
		System.gc();
		t.testFastLock();
		System.gc();
		t.testConcurrentHashMapsStrongRefs();
		System.gc();
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
		start = System.currentTimeMillis();
		t.testLockWeakReferences();
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() lockweak:\t"
						+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testConcurrentHashMapsStrongRefs();
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() strongrefs:\t"
						+ (System.currentTimeMillis() - start));
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() waiting for you to capture the memory snapshot");
		//		while (true) {
		//		}
	}
}
