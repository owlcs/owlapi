package casualtest;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

public class MemTestRestricted {
	public static final List<String> fileNamesToUse=Arrays.asList("foundational-model-of-anatomy.owl.xml",
	"ncbi-organismal-classification.owl.xml",
	"cell-cycle-ontology.owl.xml",
	"gazetteer.owl.xml",
	"nifstd.owl.xml",
	"cardiac-electrophysiology-ontology.owl.xml",
	"nci-thesaurus.owl.xml",
	"coriell-cell-line-ontology.owl.xml",
	"suggested-ontology-for-pharmacogenomics.owl.xml",
	"gene-ontology-extension.owl.xml",
	"gene-ontology.owl.xml");

	public static void main(String[] args) throws Exception {
		File base = new File("/Users/ignazio/ontologies_memory_test");
		final AtomicLong totalannotationsCount = new AtomicLong();
		final AtomicLong totalannotationsSize = new AtomicLong();
		long axiomcount = 0;
		List<File> filesToUse = new ArrayList<File>();
		for (File f : base.listFiles()) {
			if (f.isDirectory()) {
				for (File onto : f.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return fileNamesToUse.contains(name);
					}
				})) {
					filesToUse.add(onto);
				}
			}
		}
		Map<File, Long> averages = new HashMap<File, Long>();
		Map<File, Long> averagetimes = new HashMap<File, Long>();
		Map<File, String> record = new HashMap<File, String>();
		final int rounds = 1;
		for (int i = 0; i < rounds; i++) {
			System.out.println("MemoryTest.main() round: " + i);
			//int j = 0;
			for (File onto : filesToUse) {
//				System.out.print(j++);
//				System.out.print("...");
//				if (j % 10 == 0) {
//					System.out.println();
//				}
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
				for (OWLAnnotationAssertionAxiom ax : o
						.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
					ax.getAnnotation().getValue().accept(visitor);
				}
				for (OWLEntity e : o.getSignature()) {
					annotationsCount.incrementAndGet();
					annotationsSize.addAndGet(e.getIRI().toString().length());
				}
				long end = System.currentTimeMillis() - start;
				System.gc();
				Thread.sleep(100);
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
			}
//			System.out.println();
		}
		for (File onto : filesToUse) {
			final long mem = averages.get(onto) / rounds;
			final long speed = averagetimes.get(onto) / rounds;
			System.out.println(record.get(onto) + " " + (mem < 50 ? 50 : mem) + " "
					+ (speed < 50 ? 50 : speed));
		}
	}

}
