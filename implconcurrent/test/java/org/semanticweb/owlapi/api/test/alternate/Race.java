package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.configurables.ThreadSafeOWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.RaceCallback;

public class Race extends TestCase {
	private static final String A_CLASS = "http://www.race.org#testclass";
	public static final String NS = "http://www.race.org#";
	protected RaceCallback callback;
	private Runnable writer = new Runnable() {
		public void run() {
			while (!done) {
				callback.add();
			}
			callback.add();
		}
	};
	private volatile boolean done = false;
	ExecutorService exec = Executors.newFixedThreadPool(5);

	public Race(RaceCallback c) throws OWLOntologyCreationException {
		callback = c;
	}
	
	public Race() {
		try {
			callback= new SubClassLHSCallback();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	public void racing() throws InterruptedException {
		exec.submit(writer);
		callback.race();
		done = true;
		exec.shutdown();
		exec.awaitTermination(5, TimeUnit.SECONDS);
	}

	/**
	 * @param args
	 */
	public void testSubClassLHS() {
		try {
			final int totalRepetitions = 10000;
			int repetitions = 0;
			Race r;
			do {
				repetitions++;
				r = new Race(new SubClassLHSCallback());
				r.racing();
			} while (!r.callback.failed() && repetitions < totalRepetitions);
			if (r.callback.failed()) {
				r.callback.diagnose();
				fail("Failed after " + repetitions + " repetition(s).");
			} else {
				System.out.println("No race condition found in "
						+ totalRepetitions + " repetitiions");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static class SubClassLHSCallback implements RaceCallback {
		private volatile int counter = 0;
		OWLDataFactory factory;
		OWLOntologyManager manager;
		OWLOntology ontology;
		OWLClass x;
		OWLClass y;

		public SubClassLHSCallback() throws OWLOntologyCreationException {
			manager = ThreadSafeOWLManager.createOWLOntologyManager();
			factory = manager.getOWLDataFactory();
			ontology = manager.createOntology();
			x = factory.getOWLClass(IRI.create(NS + "X"));
			y = factory.getOWLClass(IRI.create(NS + "Y"));
		}

		public void add() {
			OWLClass middle = createMiddleClass(counter);
			Set<OWLAxiom> axioms = computeChanges(middle);
			manager.addAxioms(ontology, axioms);
			counter++;
		}

		public boolean failed() {
			int size = computeSize();
			if (size != counter) {
				return true;
			} else {
				return false;
			}
		}

		public int computeSize() {
			return ontology.getSubClassAxiomsForSubClass(x).size();
		}

		public Set<OWLAxiom> computeChanges(OWLClass middle) {
			OWLAxiom axiom1 = factory.getOWLSubClassOfAxiom(x, middle);
			OWLAxiom axiom2 = factory.getOWLSubClassOfAxiom(middle, y);
			Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
			axioms.add(axiom1);
			axioms.add(axiom2);
			return axioms;
		}

		public void diagnose() {
			Set<OWLSubClassOfAxiom> axiomsFound = ontology
					.getSubClassAxiomsForSubClass(x);
			System.out
					.println("Expected getSubClassAxiomsForSubClass to return "
							+ counter + " axioms but it only found "
							+ axiomsFound.size());
			for (int i = 0; i < counter; i++) {
				OWLAxiom checkMe = factory.getOWLSubClassOfAxiom(x,
						createMiddleClass(i));
				if (!axiomsFound.contains(checkMe)
						&& ontology.containsAxiom(checkMe)) {
					System.out
							.println(checkMe.toString()
									+ " is an axiom in the ontology that is not found by getSubClassAxiomsForSubClass");
					return;
				}
			}
		}

		public void race() {
			ontology.getSubClassAxiomsForSubClass(factory.getOWLClass(IRI
					.create(A_CLASS)));
		}

		public OWLClass createMiddleClass(int i) {
			return factory.getOWLClass(IRI.create(NS + "P" + i));
		}
	}
}
