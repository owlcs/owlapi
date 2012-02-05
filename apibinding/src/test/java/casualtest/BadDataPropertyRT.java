package casualtest;

import junit.framework.TestCase;

import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings("javadoc")
public class BadDataPropertyRT extends TestCase {
	private static final OWLDataFactory factory = Factory.getFactory();
	public static final String NS = "http://test.org/DataPropertyRestriction.owl";
	public static final OWLDataProperty P = factory.getOWLDataProperty(IRI.create(NS
			+ "#p"));
	public static final OWLClass A = factory.getOWLClass(IRI.create(NS + "#A"));

	public void testBadDataproperty() throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology(IRI.create(NS));
		OWLClassExpression restriction = factory.getOWLDataSomeValuesFrom(P,
				factory.getOWLDatatype(XSDVocabulary.DURATION.getIRI()));
		OWLAxiom axiom = factory.getOWLSubClassOfAxiom(A, restriction);
		manager.addAxiom(ontology, axiom);
		assertTrue(ontology.containsDataPropertyInSignature(P.getIRI()));
		StringDocumentTarget t = new StringDocumentTarget();
		manager.saveOntology(ontology,
				new RDFXMLOntologyFormat(), t);
		manager.removeOntology(ontology);
		ontology = manager.loadOntologyFromOntologyDocument(
				new StringDocumentSource(t.toString()));

		assertTrue(ontology.containsDataPropertyInSignature(P.getIRI()));
	}
}
