package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apibinding.configurables.ThreadSafeOWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.SafeStrongRefsOWLDataFactoryImpl;

public class ConcurrentSafetyTest extends TestCase {
	private class CallBack1 implements TestMultithreadCallBack {
		private final OWLDataFactory f;
		private final Tester t;

		public CallBack1(OWLDataFactory factory, Tester t) {
			f = factory;
			this.t = t;
		}

		public void execute() throws Exception {
			t.run(f);
		}

		public String getId() {
			return "test for " + f.getClass().getSimpleName();
		}
	}

	private Tester tester = new Tester();

	@Override
	protected void tearDown() throws Exception {
		((OWLDataFactoryImpl) OWLManager.getOWLDataFactory()).purge();
		((OWLDataFactoryImpl) ThreadSafeOWLManager.getOWLDataFactory()).purge();
		SafeStrongRefsOWLDataFactoryImpl.getInstance().purge();
		System.gc();
	}

//	public void testSafeImplementation() {
//		MultiThreadChecker checker = new MultiThreadChecker();
//		checker.check(new CallBack1(ThreadSafeOWLManager.getOWLDataFactory(),
//				tester));
//		System.out.println(checker.getTrace());
//		assertTrue(checker.getTrace(), checker.isSuccessful());
//	}
	public void testSafeStrongRefsImplementation() {
		MultiThreadChecker checker = new MultiThreadChecker();
		checker.check(new CallBack1(SafeStrongRefsOWLDataFactoryImpl.getInstance(),
				tester));
		System.out.println(checker.getTrace());
		assertTrue(checker.getTrace(), checker.isSuccessful());
	}
	

//	public void testDefaultImplementation() {
//		MultiThreadChecker checker = new MultiThreadChecker();
//		checker.check(new CallBack1(OWLManager.getOWLDataFactory(), tester));
//		System.out.println(checker.getTrace());
//		assertTrue(checker.getTrace(), checker.isSuccessful());
//	}
}
