package casualtest;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class OntologyHashCode {
	public static final String ONTOLOGY_LOC = "http://www.co-ode.org/ontologies/pizza/pizza.owl";

	public static void main(String[] args) throws OWLOntologyCreationException {
		OWLOntology ontology1 = createOntology();
		OWLOntology ontology2 = createOntology();
		System.out.println("The two ontologies are equal? " + ontology1.equals(ontology2));
		System.out.println("The hash code for ontology 1 = " + ontology1.hashCode());
		System.out.println("The hash code for ontology 2 = " + ontology2.hashCode());
	}

	private static OWLOntology createOntology() throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(IRI.create(ONTOLOGY_LOC));
	}

}


