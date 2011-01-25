package org.semanticweb.owlapi.api.test;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class DisjointUnionTest extends TestCase{
	public static final String NS = "http://protege.org/protege/DisjointUnion.owl";
	private static OWLDataFactory factory = OWLManager.getOWLDataFactory();
	public static final OWLClass A = factory.getOWLClass(IRI.create(NS + "#A"));
	public static final OWLClass B = factory.getOWLClass(IRI.create(NS + "#B"));
	public static final OWLClass C = factory.getOWLClass(IRI.create(NS + "#C"));

	/**
	 * @param args
	 * @throws OWLOntologyCreationException 
	 * @throws OWLOntologyStorageException 
	 */
	public void testDisjointUnion() throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology(IRI.create(NS));
		Set<OWLClassExpression> disjoints = new HashSet<OWLClassExpression>();
		disjoints.add(B);
		disjoints.add(C);
		manager.addAxiom(ontology, factory.getOWLDisjointUnionAxiom(A, disjoints));
		assertTrue(ontology.getDisjointUnionAxioms(A).size()==1);
		assertTrue(ontology.getDisjointUnionAxioms(B).size()==0);
	}
	

}
