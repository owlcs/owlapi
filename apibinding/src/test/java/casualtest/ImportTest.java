package casualtest;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class ImportTest {
	public static void main(String[] args) throws Exception{
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();

		OWLOntology ont = m.loadOntology(IRI.create("http://bblfish.net/work/atom-owl/2004-08-12/Atom.old2.owl"));
	}

}
