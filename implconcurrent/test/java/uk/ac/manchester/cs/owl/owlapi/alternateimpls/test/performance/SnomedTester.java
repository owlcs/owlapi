package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;

import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class SnomedTester {
	public static void main(String[] args) throws Exception{
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		long start=System.currentTimeMillis();
		OWLOntology o=m.loadOntologyFromOntologyDocument(new File("/Users/ignazio/nci.owl"));
		System.out.println("SnomedTester.main() "+(System.currentTimeMillis()-start));
		Set<OWLAxiom> axioms = o.getAxioms();
		System.out.println("SnomedTester.main() axioms: "+axioms.size());
		for(OWLAxiom ax:axioms) {
			m.removeAxiom(o, ax);
		}
		System.out.println("SnomedTester.main() done: capture memory snapshot");
		while(true) {
			Thread.sleep(1000);
		}
		
	}
}
