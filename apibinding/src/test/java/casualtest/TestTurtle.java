package casualtest;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class TestTurtle {
	public void testturtle() throws Exception{
		File folder=new File("/Users/ignazio/test-suite-archive/data-r2/basic");
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		for(File f:folder.listFiles()) {
			try {
				OWLOntology o=m.loadOntologyFromOntologyDocument(f);
				System.out.println("TestTurtle.testturtle() "+f.getName());
				for(OWLAxiom ax:o.getAxioms()) {
					System.out.println(ax);
				}
			} catch (Exception e) {
				System.out.println("TestTurtle.testturtle() "+f.getName());
				e.printStackTrace(System.out);
			}
		}
	}
}
