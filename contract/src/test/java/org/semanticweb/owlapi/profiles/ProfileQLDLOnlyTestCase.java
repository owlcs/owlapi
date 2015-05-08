package org.semanticweb.owlapi.profiles;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class ProfileQLDLOnlyTestCase extends ProfileBase {

    private String premise;

    public ProfileQLDLOnlyTestCase(String premise) {
        this.premise = premise;
    }

    @Test
    public void testQLDLOnly() {
        test(premise, false, true, false, true);
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(
        "<rdf:RDF xml:base=\"http://example.org/\" xmlns=\"http://example.org/\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"hasName\" /><owl:DatatypeProperty rdf:about=\"hasAddress\" /><rdf:Description rdf:about=\"hasName\"><owl:propertyDisjointWith rdf:resource=\"hasAddress\" /></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasName>Peter Griffin</hasName><hasAddress>Peter Griffin</hasAddress></rdf:Description></rdf:RDF>",
        "Prefix( :=<http://example.org/> ) Ontology( Declaration( DataProperty( :hasName ) ) Declaration( DataProperty( :hasAddress ) ) DisjointDataProperties( :hasName :hasAddress ) DataPropertyAssertion( :hasName :Peter \"Peter Griffin\" ) DataPropertyAssertion( :hasAddress :Peter \"Peter Griffin\" ))",
        "<rdf:RDF xml:base=\"http://example.org/\" xmlns=\"http://example.org/\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"hasName\" /><owl:DatatypeProperty rdf:about=\"hasAddress\" /><owl:DatatypeProperty rdf:about=\"hasZip\" /><owl:AllDisjointProperties><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasName\" /><rdf:Description rdf:about=\"hasAddress\" /><rdf:Description rdf:about=\"hasZip\" /></owl:members></owl:AllDisjointProperties><rdf:Description rdf:about=\"Peter\"><hasName>Peter Griffin</hasName></rdf:Description><rdf:Description rdf:about=\"Peter_Griffin\"><hasAddress>Peter Griffin</hasAddress></rdf:Description><rdf:Description rdf:about=\"Petre\"><hasZip>Peter Griffin</hasZip></rdf:Description></rdf:RDF>",
        "Prefix( :=<http://example.org/> ) Ontology( Declaration( DataProperty( :hasName ) ) Declaration( DataProperty( :hasAddress ) ) Declaration( DataProperty( :hasZip ) ) DisjointDataProperties( :hasName :hasAddress :hasZip ) DataPropertyAssertion( :hasName :Peter \"Peter Griffin\" ) DataPropertyAssertion( :hasAddress :Peter_Griffin \"Peter Griffin\" ) DataPropertyAssertion( :hasZip :Petre \"Peter Griffin\" ))",
        "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Thing rdf:about=\"urn:test#Ghent\"><first:path><owl:Thing rdf:about=\"urn:test#Antwerp\"/></first:path></owl:Thing><owl:SymmetricProperty rdf:about=\"urn:test#path\"/></rdf:RDF>");
    }
}
