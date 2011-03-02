package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class TestDubiousCaseMinusInf extends TestCase{
public void testMinusInf() throws Exception{
	String input="Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"+
"Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"+
"Prefix(:=<http://test.org/test#>)\n"+
"Ontology(\nDeclaration(NamedIndividual(:a))\n" +
"Declaration(DataProperty(:dp))\n"+
"Declaration(Class(:A))\n" +
"SubClassOf(:A DataAllValuesFrom(:dp owl:real))" +
"\nSubClassOf(:A \n" +
"DataSomeValuesFrom(:dp DataOneOf(\"-INF\"^^xsd:float \"-0\"^^xsd:integer))" +
"\n)" +
"\n" +
"ClassAssertion(:A :a)" +
"\n)";
	StringDocumentSource in=new StringDocumentSource(input);
	OWLOntologyManager m=OWLManager.createOWLOntologyManager();
	OWLOntology o=m.loadOntologyFromOntologyDocument(in);
	StringDocumentTarget t=new StringDocumentTarget();
	m.saveOntology(o, t);
	assertTrue(t.toString().contains("-INF"));
	OWLOntology o1=m.loadOntologyFromOntologyDocument(new StringDocumentSource(t.toString()));
	assertEquals("Obtologies were supposed to be the same",o.getLogicalAxioms(), o1.getLogicalAxioms());
}
}
