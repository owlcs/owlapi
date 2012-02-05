package casualtest;

import java.util.Collections;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
@SuppressWarnings("javadoc")
public class ManchesterRuleRT  {
	public static final String NS = "http://protege.stanford.edu/ontologies/ManchesterRule.owl";
	public static final OWLClass A = Factory.getFactory().getOWLClass(
			IRI.create(NS + "#A"));
	public static final OWLClass B = Factory.getFactory().getOWLClass(
			IRI.create(NS + "#B"));
	public static final OWLObjectProperty P = Factory.getFactory()
			.getOWLObjectProperty(IRI.create(NS + "#p"));

	public void testMain() throws Exception {
		OWLOntologyManager manager = Factory.getManager();
		OWLOntology ontology = manager.createOntology(IRI.create(NS));
		OWLDataFactory factory = manager.getOWLDataFactory();
		SWRLVariable i = factory.getSWRLVariable(IRI.create(NS + "#i"));
		SWRLClassAtom headAtom = factory.getSWRLClassAtom(
				factory.getOWLObjectSomeValuesFrom(P, A), i);
		SWRLObjectPropertyAtom bodyAtom = factory.getSWRLObjectPropertyAtom(P, i, i);
		manager.addAxiom(ontology,  factory.getSWRLRule(
				Collections.singleton(headAtom), Collections.singleton(bodyAtom)));

		PrefixOWLOntologyFormat format = new ManchesterOWLSyntaxOntologyFormat();
		format.setDefaultPrefix(NS+"#");
		StringDocumentTarget output = new StringDocumentTarget();
		manager.saveOntology(ontology, format, output);
		ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(
				output.toString()));
		manager.saveOntology(ontology, format, new StringDocumentTarget());

	}

}
