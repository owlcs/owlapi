package casualtest;
import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class OntologyHashCode extends TestCase{
	public static final String ONTOLOGY_LOC = "http://www.co-ode.org/ontologies/pizza/pizza.owl";

	public void testmain() throws OWLOntologyCreationException {
		OWLOntology ontology1 = createOntology();
		OWLOntology ontology2 = createOntology();
		assertTrue("The two ontologies are equal? " , ontology1.equals(ontology2));
		assertEquals(ontology1.hashCode(), ontology2.hashCode());
	}

	private static OWLOntology createOntology() throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(IRI.create(ONTOLOGY_LOC));
	}

}


