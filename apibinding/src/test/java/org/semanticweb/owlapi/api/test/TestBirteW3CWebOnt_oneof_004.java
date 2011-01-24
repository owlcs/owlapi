package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import junit.framework.TestCase;

public class TestBirteW3CWebOnt_oneof_004 extends TestCase {
	public void testWebOnt() throws Exception {
		String s = "<!DOCTYPE rdf:RDF [\n"
				+ "   <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\">\n"
				+ "   <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
				+ "]>\n"
				+ "<rdf:RDF\n"
				+ " xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
				+ " xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
				+ " xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
				+ " xmlns:first=\"http://www.w3.org/2002/03owlt/oneOf/premises004#\"\n"
				+ " xml:base=\"http://www.w3.org/2002/03owlt/oneOf/premises004\" >\n"
				+ " <owl:Ontology/>\n"
				+ " <owl:DatatypeProperty rdf:ID=\"p\">\n"
				+ "  <rdfs:range>\n"
				+ "   <owl:DataRange>\n"
				+ "    <owl:oneOf>\n"
				+ "     <rdf:List>\n"
				+ "      <rdf:first rdf:datatype=\"&xsd;integer\">1</rdf:first>\n"
				+ "      <rdf:rest>\n"
				+ "       <rdf:List>\n"
				+ "        <rdf:first rdf:datatype=\"&xsd;integer\">2</rdf:first>\n"
				+ "        <rdf:rest>\n"
				+ "         <rdf:List>\n"
				+ "          <rdf:first rdf:datatype=\"&xsd;integer\">3</rdf:first>\n"
				+ "          <rdf:rest>\n"
				+ "           <rdf:List>\n"
				+ "            <rdf:first rdf:datatype=\"&xsd;integer\">4</rdf:first>\n"
				+ "            <rdf:rest rdf:resource=\"&rdf;nil\"/>\n"
				+ "           </rdf:List>\n"
				+ "          </rdf:rest>\n"
				+ "         </rdf:List>\n"
				+ "        </rdf:rest>\n"
				+ "       </rdf:List>\n"
				+ "      </rdf:rest>\n"
				+ "     </rdf:List>\n"
				+ "    </owl:oneOf>\n"
				+ "   </owl:DataRange>\n"
				+ "  </rdfs:range>\n"
				+ "  <rdfs:range>\n"
				+ "   <owl:DataRange>\n"
				+ "    <owl:oneOf>\n"
				+ "     <rdf:List>\n"
				+ "      <rdf:first rdf:datatype=\"&xsd;integer\">4</rdf:first>\n"
				+ "      <rdf:rest>\n"
				+ "       <rdf:List>\n"
				+ "        <rdf:first rdf:datatype=\"&xsd;integer\">5</rdf:first>\n"
				+ "        <rdf:rest>\n"
				+ "         <rdf:List>\n"
				+ "          <rdf:first rdf:datatype=\"&xsd;integer\">6</rdf:first>\n"
				+ "          <rdf:rest rdf:resource=\"&rdf;nil\"/>\n"
				+ "         </rdf:List>\n"
				+ "        </rdf:rest>\n"
				+ "       </rdf:List>\n"
				+ "      </rdf:rest>\n"
				+ "     </rdf:List>\n"
				+ "    </owl:oneOf>\n"
				+ "   </owl:DataRange>\n"
				+ "  </rdfs:range>\n"
				+ " </owl:DatatypeProperty>\n"
				+ " <owl:Thing rdf:ID=\"i\">\n"
				+ "  <rdf:type>\n"
				+ "   <owl:Restriction>\n"
				+ "    <owl:onProperty rdf:resource=\"#p\"/>\n"
				+ "    <owl:minCardinality rdf:datatype=\"&xsd;int\">1</owl:minCardinality>\n"
				+ "   </owl:Restriction>\n" + "  </rdf:type>\n"
				+ " </owl:Thing>\n" + "</rdf:RDF>";
		Set<String> expectedResult = new TreeSet<String>();
		expectedResult
				.add("DataPropertyRange(<http://www.w3.org/2002/03owlt/oneOf/premises004#p> DataOneOf(\"1\"^^xsd:integer \"2\"^^xsd:integer \"3\"^^xsd:integer \"4\"^^xsd:integer ))");
		expectedResult
				.add("Declaration(DataProperty(<http://www.w3.org/2002/03owlt/oneOf/premises004#p>))");
		expectedResult
				.add("ClassAssertion(owl:Thing <http://www.w3.org/2002/03owlt/oneOf/premises004#i>)");
		expectedResult
				.add("DataPropertyRange(<http://www.w3.org/2002/03owlt/oneOf/premises004#p> DataOneOf(\"4\"^^xsd:integer \"5\"^^xsd:integer \"6\"^^xsd:integer ))");

		expectedResult
				.add("ClassAssertion(DataMinCardinality(1 <http://www.w3.org/2002/03owlt/oneOf/premises004#p> rdfs:Literal) <http://www.w3.org/2002/03owlt/oneOf/premises004#i>)");
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(s));
//		StringDocumentTarget t=new StringDocumentTarget();
//		m.saveOntology(o, t);
//		System.out.println(t);
//		for(OWLAxiom ax:o.getAxioms()) {
//			System.out.println(ax);
//		}
		Set<String> result=new TreeSet<String>();
		for (OWLAxiom ax : o.getAxioms()) {
			result.add(ax.toString());
		}
		if(!result.equals(expectedResult)) {
		Set<String> intersection=new TreeSet<String>(result);
		intersection.retainAll(expectedResult);
		Set<String> s1=new TreeSet<String>(result);
		s1.removeAll(intersection);
		Set<String> s2=new TreeSet<String>(expectedResult);
		s2.removeAll(intersection);
		System.out.println("results:\n"+s1.toString().replace("), ", "),\n"));
		System.out.println("expected results:\n"+s2.toString().replace("), ", "),\n"));
		}
		assertEquals("Sets were supposed to be equal",result, expectedResult);
	}
}
