package org.semanticweb.owlapi.profiles;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class ProfileNoRLTestCase extends ProfileBase {

    private final String premise;

    public ProfileNoRLTestCase(String premise) {
        this.premise = premise;
    }

    @Test
    public void testNoRL() {
        test(premise, true, true, false, true);
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" ><owl:Ontology /><owl:Class rdf:about=\"http://owl2.test/rules#C_Sub\"><rdfs:subClassOf><owl:Restriction><owl:someValuesFrom><owl:Class rdf:about=\"http://owl2.test/rules#C1\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"http://owl2.test/rules#p\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\"  xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\" /><owl:Class rdf:about=\"urn:test#Car\"><owl:equivalentClass><owl:Class rdf:about=\"urn:test#Automobile\"/></owl:equivalentClass></owl:Class><first:Car rdf:about=\"urn:test#car\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Car><first:Automobile rdf:about=\"urn:test#auto\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Automobile></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><owl:equivalentClass rdf:resource=\"http://www.w3.org/2002/07/owl#Nothing\"/></owl:Class></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\"  xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Class rdf:about=\"urn:test#Car\"><owl:equivalentClass><owl:Class rdf:about=\"urn:test#Automobile\"/></owl:equivalentClass></owl:Class><first:Car rdf:about=\"urn:test#car\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Car><first:Automobile rdf:about=\"urn:test#auto\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Automobile></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\"  xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#hasHead\"><owl:equivalentProperty><owl:ObjectProperty rdf:about=\"urn:test#hasLeader\"/></owl:equivalentProperty></owl:ObjectProperty><owl:Thing rdf:about=\"urn:test#X\"><first:hasLeader><owl:Thing rdf:about=\"urn:test#Y\"/></first:hasLeader></owl:Thing></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:AnnotationProperty rdf:about=\"urn:test#prop\" /><owl:Thing rdf:about=\"urn:test#a\"><first:prop>foo</first:prop></owl:Thing></rdf:RDF>",
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Class rdf:about=\"urn:test#A\"><owl:disjointWith><owl:Class rdf:about=\"urn:test#B\"/></owl:disjointWith></owl:Class><first:A rdf:about=\"urn:test#a\"/><owl:Thing rdf:about=\"urn:test#a\"/><first:B rdf:about=\"urn:test#b\"/><owl:Thing rdf:about=\"urn:test#b\"/></rdf:RDF>");
    }
}
