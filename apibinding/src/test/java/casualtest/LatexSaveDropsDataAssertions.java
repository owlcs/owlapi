package casualtest;

import junit.framework.TestCase;

import org.coode.owlapi.latex.LatexOntologyFormat;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class LatexSaveDropsDataAssertions extends TestCase {
	public static final String NS = "http://protege.org/ontologies/Latex.owl";
	public static final OWLIndividual I = Factory.getFactory()
			.getOWLNamedIndividual(IRI.create(NS + "#i"));
	public static final OWLIndividual J = Factory.getFactory()
			.getOWLNamedIndividual(IRI.create(NS + "#j"));
	public static final OWLObjectProperty P = Factory.getFactory()
			.getOWLObjectProperty(IRI.create(NS + "#p"));
	public static final OWLDataProperty Q = Factory.getFactory()
			.getOWLDataProperty(IRI.create(NS + "#q"));

	public void testcreateOntology() throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology = manager.createOntology(IRI.create(NS));
		manager.addAxiom(ontology,
				factory.getOWLObjectPropertyAssertionAxiom(P, I, J));
		manager.addAxiom(
				ontology,
				factory.getOWLDataPropertyAssertionAxiom(Q, I,
						factory.getOWLLiteral("hello world")));
//		System.out
//				.println("The ontology below should have both a data property assertion and an object property assertion.\n\n");
		manager.saveOntology(ontology, new LatexOntologyFormat(),
				new StringDocumentTarget());
	}
}
