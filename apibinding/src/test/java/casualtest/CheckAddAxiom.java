package casualtest;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class CheckAddAxiom extends TestCase{
	public void testAddAxiom() throws Exception{
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntology o=m.createOntology();
		OWLDataFactory f=m.getOWLDataFactory();
		OWLAxiom ax=f.getOWLSubClassOfAxiom(f.getOWLNothing(), f.getOWLThing());
		AddAxiom add=new AddAxiom(o, ax);
		assertTrue("Ontology should be empty",o.getLogicalAxiomCount()==0);
		m.applyChange(add);

		assertTrue("ontology should contain one axiom",o.getLogicalAxiomCount()==1);
	}
}
