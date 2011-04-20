package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import junit.framework.TestCase;

public class OWLOntologyManagerRemoveAxiomsTest extends TestCase{
	public void testRemove() throws Exception{
		String premise = "Prefix(:=<http://example.org/>)\n"
			+ "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
			+ "Ontology(\n" + "  Declaration(NamedIndividual(:a))\n"
			+ "  Declaration(DataProperty(:dp1))\n"
			+ "  Declaration(DataProperty(:dp2))\n"
			+ "  Declaration(Class(:A))\n"
			+ "  DisjointDataProperties(:dp1 :dp2) \n"
			+ "  DataPropertyAssertion(:dp1 :a \"10\"^^xsd:integer)\n"
			+ "  SubClassOf(:A DataSomeValuesFrom(:dp2 \n"
			+ "    DatatypeRestriction(xsd:integer \n"
			+ "      xsd:minInclusive \"18\"^^xsd:integer \n"
			+ "      xsd:maxInclusive \"18\"^^xsd:integer)\n" + "    )\n"
			+ "  )\n" + "  ClassAssertion(:A :a)\n" + ")";
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntology o=m.loadOntologyFromOntologyDocument(new StringDocumentSource(premise));
		m.removeAxioms(o, o.getAxioms(AxiomType.DECLARATION));
	}
}
