package org.semanticweb.owlapi.profiles.test;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class Profile2TestCase extends ProfileBase {

    @Test
    public void testURIResolver_TestCase3AWebOnt2DRestriction2D003() {
        String PremiseOntology = rdf
                + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#dp\"/>"
                + "<owl:Class rdf:about=\"urn:test#C\"><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#superC\"/><owl:Restriction rdf:nodeID=\"r\"><owl:onProperty rdf:resource=\"urn:test#dp\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2001/XMLSchema#byte\" /></owl:Restriction></owl:intersectionOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#D\"><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#superD\"/><rdf:Description rdf:nodeID=\"r\"/></owl:intersectionOf></owl:Class></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DRestriction2D004() {
        String PremiseOntology = rdf
                + " xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#dp\"/>"
                + "<owl:Class rdf:about=\"urn:test#C\"><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#superC\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#dp\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2001/XMLSchema#byte\" /></owl:Restriction></owl:intersectionOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#D\"><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#superD\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#dp\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2001/XMLSchema#byte\" /></owl:Restriction></owl:intersectionOf></owl:Class></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DSymmetricProperty2D002() {
        String PremiseOntology = rdf
                + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:InverseFunctionalProperty rdf:about=\"urn:test#equalityOnA\"><rdfs:range>"
                + "<owl:Class rdf:about=\"urn:test#A\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#a\"/><owl:Thing rdf:about=\"urn:test#b\"/></owl:oneOf></owl:Class></rdfs:range></owl:InverseFunctionalProperty><owl:Thing rdf:about=\"urn:test#a\">"
                + "<first:equalityOnA rdf:resource=\"urn:test#a\"/></owl:Thing><owl:Thing rdf:about=\"urn:test#b\">"
                + "<first:equalityOnA rdf:resource=\"urn:test#b\"/></owl:Thing><owl:Thing rdf:about=\"urn:test#c\"/></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DThing2D003() {
        String PremiseOntology = head
                + "xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><owl:equivalentClass rdf:resource=\"http://www.w3.org/2002/07/owl#Nothing\"/></owl:Class></rdf:RDF>";
        test(PremiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DThing2D004() {
        String PremiseOntology = head
                + "xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#s\"/></owl:oneOf></owl:Class></rdf:RDF>";
        test(PremiseOntology, true, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DTransitiveProperty2D002() {
        String PremiseOntology = rdf
                + " xmlns:first=\"urn:test#\" xmlns:second=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:SymmetricProperty rdf:about=\"urn:test#symProp\"><rdfs:range>"
                + "<owl:Class><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#a\"/><owl:Thing rdf:about=\"urn:test#b\"/></owl:oneOf></owl:Class></rdfs:range></owl:SymmetricProperty><owl:Thing rdf:about=\"urn:test#a\">"
                + "<first:symProp rdf:resource=\"urn:test#a\"/></owl:Thing><owl:Thing rdf:about=\"urn:test#b\">"
                + "<first:symProp rdf:resource=\"urn:test#b\"/></owl:Thing></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DallValuesFrom2D001() {
        String PremiseOntology = rdf
                + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"urn:test#r\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:allValuesFrom rdf:resource=\"urn:test#c\"/></owl:Restriction></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<first:r rdf:about=\"urn:test#i\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/>"
                + "<first:p><owl:Thing rdf:about=\"urn:test#o\" /></first:p></first:r></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DallValuesFrom2D002() {
        String PremiseOntology = rdf
                + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"urn:test#r\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:allValuesFrom rdf:resource=\"urn:test#c\"/></owl:Restriction></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<first:r rdf:about=\"urn:test#i\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></first:r></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2DbackwardCompatibleWith2D002() {
        String PremiseOntology = rdf
                + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><rdf:Description><owl:backwardCompatibleWith><owl:Ontology rdf:about=\"http://www.example.org/\"/></owl:backwardCompatibleWith></rdf:Description></rdf:RDF>";
        test(PremiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Dcardinality2D001() {
        String PremiseOntology = rdf
                + " xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:cardinality></owl:Restriction></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"/></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Dcardinality2D002() {
        String PremiseOntology = rdf
                + " xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"/></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Dcardinality2D003() {
        String PremiseOntology = rdf
                + " xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:cardinality></owl:Restriction></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"/></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Dcardinality2D004() {
        String PremiseOntology = rdf
                + " xml:base=\"urn:test\"><owl:Ontology/>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:minCardinality></owl:Restriction></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"/></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D001() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#a\"/>"
                + "<owl:Class rdf:about=\"urn:test#b\"/></owl:intersectionOf></owl:Class>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#a\"/>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:intersectionOf></owl:Class>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#b\"/>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:intersectionOf></owl:Class></owl:unionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#a\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#b\"/>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#b\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D002() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:allValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:allValuesFrom><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Restriction><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D003() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f2\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f3\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:FunctionalProperty rdf:about=\"urn:test#f1\"/><owl:ObjectProperty rdf:about=\"urn:test#f2\"/><owl:FunctionalProperty rdf:about=\"urn:test#f2\"/><owl:ObjectProperty rdf:about=\"urn:test#f3\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f2\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#f3\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D004() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#rx3\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c1\"/>"
                + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#rx3\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#rx4\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c1\"/>"
                + "<owl:Class rdf:about=\"urn:test#c2\"/><owl:ObjectProperty rdf:about=\"urn:test#rx1\"/><owl:ObjectProperty rdf:about=\"urn:test#rx2\"/><owl:ObjectProperty rdf:about=\"urn:test#rx3\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx1\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rx\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx3\"/><owl:ObjectProperty rdf:about=\"urn:test#rx4\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx2\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rx\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx4\"/><owl:ObjectProperty rdf:about=\"urn:test#rxa\"/><owl:ObjectProperty rdf:about=\"urn:test#rx1a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx2a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx3a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx1a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx3a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx4a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx2a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx4a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx\"/><owl:FunctionalProperty rdf:about=\"urn:test#rx\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D005() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#rx3a\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c1\"/>"
                + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#rx3a\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#rx4a\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c1\"/>"
                + "<owl:Class rdf:about=\"urn:test#c2\"/><owl:ObjectProperty rdf:about=\"urn:test#rx1\"/><owl:ObjectProperty rdf:about=\"urn:test#rx2\"/><owl:ObjectProperty rdf:about=\"urn:test#rx3\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx1\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rx\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx3\"/><owl:ObjectProperty rdf:about=\"urn:test#rx4\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx2\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rx\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx4\"/><owl:ObjectProperty rdf:about=\"urn:test#rxa\"/><owl:ObjectProperty rdf:about=\"urn:test#rx1a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx2a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx3a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx1a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx3a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx4a\"><rdfs:subPropertyOf rdf:resource=\"urn:test#rx2a\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#rxa\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#rx4a\"/><owl:ObjectProperty rdf:about=\"urn:test#rx\"/><owl:FunctionalProperty rdf:about=\"urn:test#rx\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D006() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D007() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D008() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D009() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#s\"/><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:FunctionalProperty rdf:about=\"urn:test#f1\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D010() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invS\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#s\"/><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:FunctionalProperty rdf:about=\"urn:test#f1\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D011() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invS\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#s\"/><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:FunctionalProperty rdf:about=\"urn:test#f1\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D012() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#s\"/><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:FunctionalProperty rdf:about=\"urn:test#f1\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D013() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF1\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invF1\"><owl:inverseOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#s\"/><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:FunctionalProperty rdf:about=\"urn:test#f1\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D014() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invS\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D015() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#q\"/></owl:complementOf></owl:Class></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#q\"/><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D016() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f2\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f2\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:ObjectProperty rdf:about=\"urn:test#f2\"/><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f2\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D017() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f2\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"><rdfs:subPropertyOf rdf:resource=\"urn:test#f2\"/><rdfs:subPropertyOf rdf:resource=\"urn:test#f1\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f1\"/><owl:ObjectProperty rdf:about=\"urn:test#f2\"/><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f1\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f2\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D018() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">3</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D019() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">3</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D020() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">4</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D021() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">5</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p5\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D022() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p2\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">4</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/>"
                + "<owl:Class rdf:about=\"urn:test#p1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p2\"/>"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p3\"/>"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p4\"/>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:unionOf></owl:Class></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p4\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p5\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D023() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:allValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#a\"/>"
                + "<owl:Class rdf:about=\"urn:test#c\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invP\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invS\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:complementOf></owl:Class></owl:allValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invP\"><owl:inverseOf rdf:resource=\"urn:test#p\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invS\"><owl:inverseOf rdf:resource=\"urn:test#s\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/><owl:TransitiveProperty rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D024() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#a\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D025() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:someValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#a\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D026() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D027() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<owl:Class rdf:about=\"urn:test#d\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D028() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:allValuesFrom></owl:Restriction></owl:unionOf></owl:Class></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D029() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:complementOf></owl:Class></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D030() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description rdf:about='http://www.w3.org/2002/07/owl#Thing'><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></rdf:Description><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D031() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:complementOf></owl:Class><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:allValuesFrom></owl:Restriction></owl:unionOf></owl:Class></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D032() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:complementOf></owl:Class></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D033() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invF\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#f\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p1\"/><owl:ObjectProperty rdf:about=\"urn:test#invF\"><owl:inverseOf rdf:resource=\"urn:test#f\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#f\"/><owl:FunctionalProperty rdf:about=\"urn:test#f\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:TransitiveProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D034() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Satisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r1\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:allValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invR\"/><owl:allValuesFrom><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r1\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#p\"/></owl:complementOf></owl:Class></owl:allValuesFrom></owl:Restriction></owl:allValuesFrom></owl:Restriction></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#invR\"><owl:inverseOf rdf:resource=\"urn:test#r\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#r1\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Satisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D035() {
        String PremiseOntology = "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xml:base=\"urn:test\" xmlns:oiled=\"urn:test#\"><owl:Ontology rdf:about=\"\"><rdfs:comment>An ontology illustrating the use of a spy point that limits the cardinality of the interpretation domain to having only two objects.</rdfs:comment></owl:Ontology>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">3</owl:minCardinality></owl:Restriction></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#p\"><owl:inverseOf rdf:resource=\"urn:test#invP\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#invP\"/>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#spy\"/></owl:oneOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class><rdf:Description rdf:about=\"urn:test#spy\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#invP\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:maxCardinality></owl:Restriction></rdf:type></rdf:Description><oiled:Unsatisfiable/></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D040() {
        String PremiseOntology = "<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:eg=\"http://example.org/factkb#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xml:base=\"urn:test\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A0\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A1\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A2\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A3\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A4\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A5\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A6\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A7\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A8\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A9\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B0\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B1\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B2\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B3\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B4\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B5\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B6\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B7\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B8\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B9\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C1\"><rdfs:subClassOf>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A0\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B0\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A1\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B1\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A2\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B2\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A3\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B3\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A4\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B4\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A5\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B5\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A6\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B6\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A7\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B7\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A8\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B8\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A9\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B9\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A10\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B10\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A11\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B11\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A12\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B12\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A13\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B13\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A14\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B14\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A15\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B15\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A16\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B16\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A17\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B17\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A18\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B18\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A19\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B19\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A20\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B20\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A21\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B21\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A22\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B22\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A23\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B23\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A24\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B24\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A25\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B25\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A26\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B26\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A27\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B27\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A28\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B28\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A29\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B29\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A30\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B30\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A31\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B31\"/></owl:unionOf></owl:Class></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C2\"><rdfs:subClassOf>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A\"/>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B\"/></owl:complementOf></owl:Class></owl:unionOf></owl:Class></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C3\"><rdfs:subClassOf>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A\"/></owl:complementOf></owl:Class>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B\"/></owl:unionOf></owl:Class>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A\"/></owl:complementOf></owl:Class>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B\"/></owl:complementOf></owl:Class></owl:unionOf></owl:Class></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C4\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"http://example.org/factkb#R\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C2\"/></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C5\"><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"http://example.org/factkb#R\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C3\"/></owl:allValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A10\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A11\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A12\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A13\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A14\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A15\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A16\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A17\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A18\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A19\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A20\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A21\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A22\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A23\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A24\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A25\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A26\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A27\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A28\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A29\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A30\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#A31\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B10\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B11\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B12\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B13\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B14\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B15\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B16\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B17\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B18\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B19\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B20\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B21\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B22\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B23\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B24\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B25\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B26\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B27\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B28\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B29\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B30\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#B31\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#TEST\"><rdfs:subClassOf>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C1\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C4\"/>"
                + "<owl:Class rdf:about=\"http://example.org/factkb#C5\"/></owl:intersectionOf></owl:Class></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"http://example.org/factkb#R\"/><eg:TEST/></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D101() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/>"
                + "<owl:Class rdf:about=\"urn:test#f\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c1\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/></rdfs:subClassOf><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/>"
                + "<owl:Class rdf:about=\"urn:test#e3\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D102() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:intersectionOf></owl:Class></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/>"
                + "<owl:Class rdf:about=\"urn:test#f\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c1\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/></rdfs:subClassOf><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/>"
                + "<owl:Class rdf:about=\"urn:test#e3\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D103() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#e3\"/>"
                + "<owl:Class rdf:about=\"urn:test#f\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/>"
                + "<owl:Class rdf:about=\"urn:test#f\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c1\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/></rdfs:subClassOf><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d1\"/>"
                + "<owl:Class rdf:about=\"urn:test#e3\"><rdfs:subClassOf>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></rdfs:subClassOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D105() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:minCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D106() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#c\"/></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:someValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D109() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#tt\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#a\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#tt\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">3</owl:minCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#tt\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#tt\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#a\"><rdfs:subClassOf>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/><owl:ObjectProperty rdf:about=\"urn:test#tt\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D110() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#e\"/></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#e\"/>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></owl:unionOf></owl:Class></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/>"
                + "<owl:Class rdf:about=\"urn:test#e\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D111() {
        String PremiseOntology = head2
                + "<owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:someValuesFrom>"
                + "<owl:Class><owl:complementOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality></owl:Restriction></owl:complementOf></owl:Class></owl:someValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#c\"/>"
                + "<owl:Class><owl:complementOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#s\"/><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:minCardinality></owl:Restriction></owl:complementOf></owl:Class></owl:unionOf></owl:Class></owl:allValuesFrom></owl:Restriction><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#r\"/><owl:allValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:allValuesFrom></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#c\"><rdfs:subClassOf>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#d\"/></owl:complementOf></owl:Class></rdfs:subClassOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#d\"/>"
                + "<owl:Class rdf:about=\"urn:test#e\"/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#s\"/><rdf:Description><rdf:type rdf:resource=\"urn:test#Unsatisfiable\"/></rdf:Description></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolver_TestCase3AWebOnt2Ddescription2Dlogic2D207() {
        String PremiseOntology = "<rdf:RDF xml:base=\"urn:test\" xmlns:oiled=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Ontology rdf:about=\"\"/>"
                + "<owl:Class rdf:about=\"urn:test#C10\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class rdf:about=\"urn:test#C2\"/>"
                + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#C12\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#C10\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#C2\"/>"
                + "<owl:Class rdf:about=\"urn:test#C4\"/>"
                + "<owl:Class rdf:about=\"urn:test#C6\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:complementOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#C4\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#C8\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:someValuesFrom>"
                + "<owl:Class rdf:about=\"urn:test#C6\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#TEST\"><owl:equivalentClass>"
                + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#C8\"/></owl:complementOf></owl:Class>"
                + "<owl:Class rdf:about=\"urn:test#C12\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#R1\"/><owl:Thing rdf:about=\"urn:test#V21080\"><rdf:type>"
                + "<owl:Class rdf:about=\"urn:test#TEST\"/></rdf:type><rdf:type>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#C8\"/></owl:complementOf></owl:Class></rdf:type><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#R1\"/><owl:allValuesFrom>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#C6\"/></owl:complementOf></owl:Class></owl:allValuesFrom></owl:Restriction></rdf:type><oiled:R1 rdf:resource=\"urn:test#V21081\"/></owl:Thing><owl:Thing rdf:about=\"urn:test#V21081\"><rdf:type>"
                + "<owl:Class rdf:about=\"urn:test#C4\"/></rdf:type><rdf:type>"
                + "<owl:Class rdf:about=\"urn:test#C2\"/></rdf:type><rdf:type>"
                + "<owl:Class><owl:complementOf>"
                + "<owl:Class rdf:about=\"urn:test#C6\"/></owl:complementOf></owl:Class></rdf:type></owl:Thing></rdf:RDF>";
        test(PremiseOntology, false, false, false, true);
    }
}
