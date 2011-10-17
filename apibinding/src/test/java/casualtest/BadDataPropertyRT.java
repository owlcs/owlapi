package casualtest;

import java.io.File;
import java.io.IOException;

import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentTarget;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
@SuppressWarnings("javadoc")
public class BadDataPropertyRT {
	private static final OWLDataFactory factory = Factory.getFactory();
	public static final String NS = "http://test.org/DataPropertyRestriction.owl";
	public static final OWLDataProperty P = factory.getOWLDataProperty(IRI.create(NS + "#p"));
	public static final OWLClass A = factory.getOWLClass(IRI.create(NS + "#A"));

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		OWLOntology ontology = createOntology();
		checkOntology(ontology);
		File f = saveOntology(ontology);
		ontology = loadOntology(f);
		for(OWLAxiom ax:ontology.getAxioms()) {
			System.out.println("BadDataPropertyRT.main() "+ax);
		}
		checkOntology(ontology);
	}

	private static OWLOntology createOntology() throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology(IRI.create(NS));
		OWLClassExpression restriction = factory.getOWLDataSomeValuesFrom(P, factory.getOWLDatatype(XSDVocabulary.DURATION.getIRI()));
		OWLAxiom axiom = factory.getOWLSubClassOfAxiom(A, restriction);
		manager.addAxiom(ontology, axiom);
		return ontology;
	}

	private static void checkOntology(OWLOntology ontology) throws OWLOntologyStorageException {
		System.out.println("Here is the ontology ----------------\n");
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		OWLFunctionalSyntaxOntologyFormat format = new OWLFunctionalSyntaxOntologyFormat();
		format.setPrefix(":", NS + "#");
		manager.saveOntology(ontology, format, new SystemOutDocumentTarget());

		System.out.println("\n\nIs the property p found in the signature of the ontology? " + ontology.containsDataPropertyInSignature(P.getIRI()));
		System.out.println("\n--------------------------------------------------------------------------------------------------------------------------");

	}

	private static File saveOntology(OWLOntology ontology) throws IOException, OWLOntologyStorageException {
		File tmp = File.createTempFile("RoundTrip", ".owl");
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		manager.saveOntology(ontology, new SystemOutDocumentTarget());
		manager.saveOntology(ontology, new RDFXMLOntologyFormat(), new FileDocumentTarget(tmp));
		System.out.println("Ontology saved to the file " + tmp);
		return tmp;
	}

	private static OWLOntology loadOntology(File f) throws OWLOntologyCreationException {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			return manager.loadOntologyFromOntologyDocument(f);
		}
		finally {
			System.out.println("Ontology loaded from the file " + f);
		}
	}

}
