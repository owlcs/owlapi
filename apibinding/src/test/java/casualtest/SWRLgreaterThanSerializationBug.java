package casualtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;

public class SWRLgreaterThanSerializationBug extends TestCase {
	public void testserialization() throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		// Pathes and IRIs
		IRI ontologyIRI = IRI.create("urn:test.owl");
		// Create ontology
		OWLOntology ontology = manager.createOntology(ontologyIRI);
		OWLDataFactory factory = manager.getOWLDataFactory();
		// Create some varaiables and a property
		SWRLVariable varY = factory.getSWRLVariable(IRI.create(ontologyIRI + "#y"));
		SWRLVariable varZ = factory.getSWRLVariable(IRI.create(ontologyIRI + "#z"));
		OWLObjectProperty prop = factory.getOWLObjectProperty(IRI.create(ontologyIRI
				+ "#propA"));
		// Add variables to an ArrayList ... notice the order: First varY, then varZ !
		List<SWRLDArgument> vars = new ArrayList<SWRLDArgument>();
		vars.add(varY);
		vars.add(varZ);
		// Create greaterThan-Builtin with the variables
		SWRLBuiltInAtom bAtom = factory.getSWRLBuiltInAtom(
				IRI.create("http://www.w3.org/2003/11/swrlb#greaterThan"), vars);
		// Create something for the head of the SWRLrule
		SWRLObjectPropertyAtom propAtom = factory.getSWRLObjectPropertyAtom(prop, varY,
				varY);
		// Create SWRLrule
		SWRLRule rule = factory.getSWRLRule(Collections.singleton(bAtom),
				Collections.singleton(propAtom));
		// Add SWRLrule to ontology
		manager.applyChange(new AddAxiom(ontology, rule));
		// Now save the ontology in functional syntax format.
		// BUG: In the output file you'll see, that the variable-order in the greaterThan-rule-atom is switched.
		// It should be y-z but is z-y instead
		StringDocumentTarget t = new StringDocumentTarget();
		manager.saveOntology(ontology, new OWLFunctionalSyntaxOntologyFormat(), t);
		Set<SWRLRule> oldrules = ontology.getAxioms(AxiomType.SWRL_RULE);
		manager.removeOntology(ontology);
		OWLOntology test = manager
				.loadOntologyFromOntologyDocument(new StringDocumentSource(t.toString()));
		assertEquals(oldrules, test.getAxioms(AxiomType.SWRL_RULE));
	}
}
