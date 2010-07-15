package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apibinding.configurables.ThreadSafeOWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryCSR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryCWR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryFuture;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryLSR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryLWR;

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
	private OWLDataFactory[] factories = new OWLDataFactory[] {
			 new DataFactoryCSR(),
			new DataFactoryCWR(), new DataFactoryFuture(),
			new DataFactoryLSR(), new DataFactoryLWR(),new OWLDataFactoryImpl() };

	public void testSafeImplementation() {
		for (OWLDataFactory d : factories) {
			actualrun(d);
			d.purge();
		}
	}

	private void actualrun(OWLDataFactory d) {
		MultiThreadChecker checker = new MultiThreadChecker();
		checker.check(new CallBack1(d, tester));
		System.out.println(checker.getTrace());
		assertTrue(checker.getTrace(), checker.isSuccessful());
	}
}
