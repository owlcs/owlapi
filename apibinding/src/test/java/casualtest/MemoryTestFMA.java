package casualtest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class MemoryTestFMA {
	public static void main(String[] args) throws Exception {
		File onto = new File(
				"/Users/ignazio/ontologies_memory_test/foundational-model-of-anatomy/foundational-model-of-anatomy.owl.xml");
		final AtomicLong totalannotationsCount = new AtomicLong();
		final AtomicLong totalannotationsSize = new AtomicLong();
		long axiomcount = 0;
		Map<File, Long> averages = new HashMap<File, Long>();
		Map<File, Long> averagetimes = new HashMap<File, Long>();
		Map<File, String> record = new HashMap<File, String>();
		int j = 0;
		System.out.print(j++);
		System.out.print("...");
		if (j % 10 == 0) {
			System.out.println();
		}
		final AtomicLong annotationsCount = new AtomicLong();
		final AtomicLong annotationsSize = new AtomicLong();
		final OWLAnnotationValueVisitor visitor = new OWLAnnotationValueVisitor() {
			public void visit(OWLLiteral literal) {
				annotationsCount.incrementAndGet();
				annotationsSize.addAndGet(literal.getLiteral().length());
			}

			public void visit(OWLAnonymousIndividual individual) {}

			public void visit(IRI iri) {}
		};
		long start = System.currentTimeMillis();
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.loadOntologyFromOntologyDocument(onto);
		for (OWLAnnotationAssertionAxiom ax : o.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
			ax.getAnnotation().getValue().accept(visitor);
		}
		for (OWLEntity e : o.getSignature()) {
			annotationsCount.incrementAndGet();
			annotationsSize.addAndGet(e.getIRI().toString().length());
		}
		long end = System.currentTimeMillis() - start;
		System.gc();
		//Thread.sleep(100);
		final long memsize = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		if (averages.containsKey(onto)) {
			long old = averages.get(onto);
			averages.put(onto, old + memsize);
			averagetimes.put(onto, averagetimes.get(onto) + end);
		} else {
			averages.put(onto, memsize);
			averagetimes.put(onto, end);
			record.put(onto, onto.getName() + " " + annotationsCount + " "
					+ annotationsSize);
		}
		totalannotationsCount.addAndGet(annotationsCount.get());
		totalannotationsSize.addAndGet(annotationsSize.get());
		axiomcount += o.getAxioms().size();
		System.out.println();
	}
}
