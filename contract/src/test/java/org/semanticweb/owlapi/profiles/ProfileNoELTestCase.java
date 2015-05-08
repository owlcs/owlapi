package org.semanticweb.owlapi.profiles;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class ProfileNoELTestCase extends ProfileBase {

    private String premise;

    public ProfileNoELTestCase(String premise) {
        this.premise = premise;
    }

    @Test
    public void testNoEL() {
        test(premise, false, true, true, true);
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(
        "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:SymmetricProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#y\"/></rdf:Description></rdf:RDF>",
        "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:SymmetricProperty rdf:about=\"http://www.example.org#p\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#AsymmetricProperty\"/></owl:SymmetricProperty><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#y\"/></rdf:Description></rdf:RDF>",
        "<rdf:RDF xml:base=\"http://example.org/\" xmlns=\"http://example.org/\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"parentOf\" /><owl:AsymmetricProperty rdf:about=\"parentOf\" /><rdf:Description rdf:about=\"Peter\"><parentOf rdf:resource=\"Stewie\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><parentOf rdf:resource=\"Peter\" /></rdf:Description></rdf:RDF>",
        "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :parentOf ) ) AsymmetricObjectProperty( :parentOf ) ObjectPropertyAssertion( :parentOf :Peter :Stewie ) ObjectPropertyAssertion( :parentOf :Stewie :Peter ))",
        "<rdf:RDF xml:base=\"http://example.org/\" xmlns=\"http://example.org/\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"hasFather\" /><owl:ObjectProperty rdf:about=\"hasMother\" /><rdf:Description rdf:about=\"hasFather\"><owl:propertyDisjointWith rdf:resource=\"hasMother\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasFather rdf:resource=\"Peter\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasMother rdf:resource=\"Lois\" /></rdf:Description></rdf:RDF>",
        "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :hasFather ) ) Declaration( ObjectProperty( :hasMother ) ) DisjointObjectProperties( :hasFather :hasMother ) ObjectPropertyAssertion( :hasFather :Stewie :Peter ) ObjectPropertyAssertion( :hasMother :Stewie :Lois ))",
        "<rdf:RDF xml:base=\"http://example.org/\" xmlns=\"http://example.org/\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"hasFather\" /><owl:ObjectProperty rdf:about=\"hasMother\" /><owl:ObjectProperty rdf:about=\"hasChild\" /><owl:AllDisjointProperties><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasFather\" /><rdf:Description rdf:about=\"hasMother\" /><rdf:Description rdf:about=\"hasChild\" /></owl:members></owl:AllDisjointProperties><rdf:Description rdf:about=\"Stewie\"><hasFather rdf:resource=\"Peter\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasMother rdf:resource=\"Lois\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasChild rdf:resource=\"StewieJr\" /></rdf:Description></rdf:RDF>",
        "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :hasFather ) ) Declaration( ObjectProperty( :hasMother ) ) Declaration( ObjectProperty( :hasChild ) ) DisjointObjectProperties( :hasFather :hasMother :hasChild ) ObjectPropertyAssertion( :hasFather :Stewie :Peter ) ObjectPropertyAssertion( :hasMother :Stewie :Lois ) ObjectPropertyAssertion( :hasChild :Stewie :StewieJr ))");
    }
}
