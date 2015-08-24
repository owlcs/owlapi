package org.semanticweb.owlapi.profiles.test;

import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class Profile1TestCase extends ProfileBase {

    @Test
    public void testURIResolverDatatype2Drestrictions2Ddifferent2Dtypes() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"3\"^^xsd:integer \"4\"^^xsd:int)) ) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"2\"^^xsd:short \"3\"^^xsd:int)) ) ClassAssertion(:A :a) ClassAssertion(DataSomeValuesFrom(:dp DataOneOf(\"3\"^^xsd:integer)) :a ))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverConsistent2Ddataproperty2Ddisjointness() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp1)) Declaration(DataProperty(:dp2)) Declaration(Class(:A)) DisjointDataProperties(:dp1 :dp2) DataPropertyAssertion(:dp1 :a \"10\"^^xsd:integer) SubClassOf(:A DataSomeValuesFrom(:dp2 DatatypeRestriction(xsd:integer xsd:minInclusive \"18\"^^xsd:integer xsd:maxInclusive \"18\"^^xsd:integer) ) ) ClassAssertion(:A :a))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverContradicting2Ddatatype2Drestrictions() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"3\"^^xsd:integer \"4\"^^xsd:integer)) ) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"2\"^^xsd:integer \"3\"^^xsd:integer)) ) SubClassOf(:A DataSomeValuesFrom(:dp DatatypeRestriction(xsd:integer xsd:minInclusive \"4\"^^xsd:integer) ) ) ClassAssertion(:A :a))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverContradicting2DdateTime2Drestrictions() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataHasValue(:dp \"2007-10-08T20:44:11.656+01:00\"^^xsd:dateTime)) SubClassOf(:A DataAllValuesFrom(:dp DatatypeRestriction( xsd:dateTime xsd:minInclusive \"2008-07-08T20:44:11.656+01:00\"^^xsd:dateTime xsd:maxInclusive \"2008-10-08T20:44:11.656+01:00\"^^xsd:dateTime) ) ) ClassAssertion(:A :a))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverDatatype2DFloat2DDiscrete2D001() {
        String premiseOntology = head2
            + "<owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#dp\" />"
            + "<rdf:Description rdf:about=\"urn:test#a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#dp\" /><owl:someValuesFrom><rdfs:Datatype><owl:onDatatype rdf:resource=\"http://www.w3.org/2001/XMLSchema#float\" />"
            + "<owl:withRestrictions rdf:parseType=\"Collection\"><rdf:Description><xsd:minExclusive rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\">0.0</xsd:minExclusive></rdf:Description><rdf:Description><xsd:maxExclusive rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\">1.401298464324817e-45</xsd:maxExclusive></rdf:Description></owl:withRestrictions></rdfs:Datatype></owl:someValuesFrom></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( xsd:=<http://www.w3.org/2001/XMLSchema#> ) Prefix( ex:=<http://example.org/ontology/> ) Ontology( Declaration( DataProperty( ex:dp ) ) ClassAssertion( DataSomeValuesFrom( ex:dp DatatypeRestriction( xsd:float xsd:minExclusive \"0.0\"^^xsd:float xsd:maxExclusive \"1.401298464324817e-45\"^^xsd:float ) ) ex:a ))";
        test(premiseOntology, false, false, false, true);
        test(fspremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverDatatype2Drestriction2Dmin2Dmax2Dinconsistency() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataSomeValuesFrom(:dp DatatypeRestriction(xsd:integer xsd:minInclusive \"18\"^^xsd:integer)) ) SubClassOf(:A DataAllValuesFrom(:dp DatatypeRestriction(xsd:integer xsd:maxInclusive \"10\"^^xsd:integer)) ) ClassAssertion(:A :a))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverDifferent2Dtypes2Dplus2Dcomplement() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"3\"^^xsd:integer \"4\"^^xsd:int)) ) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"2\"^^xsd:short \"3\"^^xsd:integer)) ) ClassAssertion(:A :a) ClassAssertion(DataSomeValuesFrom(:dp DataComplementOf(DataOneOf(\"3\"^^xsd:integer))) :a ))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverDisjointClasses2D002() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"Boy\" />"
            + "<owl:Class rdf:about=\"Girl\" /><rdf:Description rdf:about=\"Boy\"><owl:disjointWith rdf:resource=\"Girl\" /></rdf:Description><Boy rdf:about=\"Stewie\" /><Girl rdf:about=\"Stewie\" /></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( Class( :Boy ) ) Declaration( Class( :Girl ) ) DisjointClasses( :Boy :Girl ) ClassAssertion( :Boy :Stewie ) ClassAssertion( :Girl :Stewie ))";
        test(premiseOntology, true, true, true, true);
        test(fspremiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverInconsistent2Dbyte2Dfiller() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp xsd:byte)) ClassAssertion(:A :a) ClassAssertion( DataSomeValuesFrom(:dp DataOneOf(\"6542145\"^^xsd:integer)) :a ))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverDatacomplement2Dplus2Drestrictions() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"3\"^^xsd:integer \"4\"^^xsd:integer)) ) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"2\"^^xsd:integer \"3\"^^xsd:integer)) )\n"
            + " ClassAssertion(:A :a) ClassAssertion(DataSomeValuesFrom(:dp DataComplementOf(DataOneOf(\"3\"^^xsd:integer))) :a))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverInconsistent_datatypes() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology(\n"
            + " Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp xsd:string)) SubClassOf(:A DataSomeValuesFrom(:dp xsd:integer)) ClassAssertion(:A :a))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverMinus2Dinf2Dnot2Dowlreal() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Prefix(owl:=<http://www.w3.org/2002/07/owl#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp owl:real)) SubClassOf(:A DataSomeValuesFrom(:dp DataOneOf(\"-INF\"^^xsd:float \"-0\"^^xsd:integer)) ) ClassAssertion(:A :a) NegativeDataPropertyAssertion(:dp :a \"0\"^^xsd:unsignedInt))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DAsymmetricProperty2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"parentOf\" /><owl:AsymmetricProperty rdf:about=\"parentOf\" /><rdf:Description rdf:about=\"Peter\"><parentOf rdf:resource=\"Stewie\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><parentOf rdf:resource=\"Peter\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :parentOf ) ) AsymmetricObjectProperty( :parentOf ) ObjectPropertyAssertion( :parentOf :Peter :Stewie ) ObjectPropertyAssertion( :parentOf :Stewie :Peter ))";
        test(premiseOntology, false, true, true, true);
        test(fspremiseOntology, false, true, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DDisjointObjectProperties2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"hasFather\" /><owl:ObjectProperty rdf:about=\"hasMother\" /><rdf:Description rdf:about=\"hasFather\"><owl:propertyDisjointWith rdf:resource=\"hasMother\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasFather rdf:resource=\"Peter\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasMother rdf:resource=\"Lois\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :hasFather ) ) Declaration( ObjectProperty( :hasMother ) ) DisjointObjectProperties( :hasFather :hasMother ) ObjectPropertyAssertion( :hasFather :Stewie :Peter ) ObjectPropertyAssertion( :hasMother :Stewie :Lois ))";
        test(premiseOntology, false, true, true, true);
        test(fspremiseOntology, false, true, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DDisjointObjectProperties2D002() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"hasFather\" /><owl:ObjectProperty rdf:about=\"hasMother\" /><owl:ObjectProperty rdf:about=\"hasChild\" /><owl:AllDisjointProperties><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasFather\" /><rdf:Description rdf:about=\"hasMother\" /><rdf:Description rdf:about=\"hasChild\" /></owl:members></owl:AllDisjointProperties><rdf:Description rdf:about=\"Stewie\"><hasFather rdf:resource=\"Peter\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasMother rdf:resource=\"Lois\" /></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasChild rdf:resource=\"StewieJr\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :hasFather ) ) Declaration( ObjectProperty( :hasMother ) ) Declaration( ObjectProperty( :hasChild ) ) DisjointObjectProperties( :hasFather :hasMother :hasChild ) ObjectPropertyAssertion( :hasFather :Stewie :Peter ) ObjectPropertyAssertion( :hasMother :Stewie :Lois ) ObjectPropertyAssertion( :hasChild :Stewie :StewieJr ))";
        test(premiseOntology, false, true, true, true);
        test(fspremiseOntology, false, true, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DDisjointUnion2D001() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"Child\" />"
            + "<owl:Class rdf:about=\"Boy\" />"
            + "<owl:Class rdf:about=\"Girl\" /><rdf:Description rdf:about=\"Child\"><owl:disjointUnionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"Boy\" /><rdf:Description rdf:about=\"Girl\" /></owl:disjointUnionOf></rdf:Description><Child rdf:about=\"Stewie\" /><rdf:Description rdf:about=\"Stewie\"><rdf:type>"
            + "<owl:Class><owl:complementOf rdf:resource=\"Girl\" /></owl:Class></rdf:type>\n"
            + "</rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology(\n"
            + " Declaration( Class( :Child ) ) Declaration( Class( :Boy ) ) Declaration( Class( :Girl ) ) DisjointUnion( :Child :Boy :Girl ) ClassAssertion( :Child :Stewie ) ClassAssertion( ObjectComplementOf( :Girl ) :Stewie ))";
        test(premiseOntology, false, false, false, true);
        test(fspremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DIrreflexiveProperty2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"marriedTo\" /><owl:IrreflexiveProperty rdf:about=\"marriedTo\" /><rdf:Description rdf:about=\"Peter\"><marriedTo rdf:resource=\"Peter\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :marriedTo ) ) IrreflexiveObjectProperty( :marriedTo ) ObjectPropertyAssertion( :marriedTo :Peter :Peter ))";
        test(premiseOntology, false, true, true, true);
        test(fspremiseOntology, false, true, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DNegativeDataPropertyAssertion2D001() {
        String premiseOntology = "Prefix( xsd:=<http://www.w3.org/2001/XMLSchema#> ) Prefix( :=<http://example.org/> ) Ontology( Declaration( DataProperty( :hasAge ) ) NegativeDataPropertyAssertion( :hasAge :Meg \"5\"^^xsd:integer ) DataPropertyAssertion( :hasAge :Meg \"5\"^^xsd:integer ))";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DNegativeObjectPropertyAssertion2D001() {
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :hasSon ) ) NegativeObjectPropertyAssertion( :hasSon :Peter :Meg ) ObjectPropertyAssertion( :hasSon :Peter :Meg ))";
        test(fspremiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DObjectPropertyChain2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"hasMother\" /><owl:ObjectProperty rdf:about=\"hasSister\" /><owl:ObjectProperty rdf:about=\"hasAunt\" /><rdf:Description rdf:about=\"hasAunt\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasMother\" /><rdf:Description rdf:about=\"hasSister\" /></owl:propertyChainAxiom></rdf:Description><rdf:Description rdf:about=\"Stewie\"><hasMother rdf:resource=\"Lois\" /></rdf:Description><rdf:Description rdf:about=\"Lois\"><hasSister rdf:resource=\"Carol\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :hasMother ) ) Declaration( ObjectProperty( :hasSister ) ) Declaration( ObjectProperty( :hasAunt ) ) SubObjectPropertyOf( ObjectPropertyChain( :hasMother :hasSister ) :hasAunt ) ObjectPropertyAssertion( :hasMother :Stewie :Lois ) ObjectPropertyAssertion( :hasSister :Lois :Carol ))";
        test(premiseOntology, true, false, true, true);
        test(fspremiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DObjectPropertyChain2DBJP2D003() {
        String premiseOntology = head3
            + "<owl:Ontology/><rdf:Description rdf:about=\"urn:test#p\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><owl:ObjectProperty rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:propertyChainAxiom></rdf:Description><rdf:Description rdf:about=\"urn:test#a\"><p rdf:resource=\"urn:test#b\"/></rdf:Description><rdf:Description rdf:about=\"urn:test#b\"><q rdf:resource=\"urn:test#c\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DObjectPropertyChain2DBJP2D004() {
        String premiseOntology = head3
            + "<owl:Ontology/><rdf:Description rdf:about=\"urn:test#p\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><owl:ObjectProperty rdf:about=\"urn:test#p\"/><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:propertyChainAxiom></rdf:Description><rdf:Description rdf:about=\"urn:test#a\"><q rdf:resource=\"urn:test#b\"/></rdf:Description><rdf:Description rdf:about=\"urn:test#b\"><q rdf:resource=\"urn:test#c\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DObjectQCR2D002() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"fatherOf\" />"
            + "<owl:Class rdf:about=\"Woman\" /><rdf:Description rdf:about=\"Peter\"><fatherOf rdf:resource=\"Stewie\" /><fatherOf rdf:resource=\"Meg\" /></rdf:Description><Woman rdf:about=\"Meg\" /><rdf:Description rdf:about=\"Peter\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"fatherOf\" /><owl:maxQualifiedCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxQualifiedCardinality><owl:onClass rdf:resource=\"Woman\" /></owl:Restriction></rdf:type></rdf:Description><rdf:Description rdf:about=\"Stewie\"><owl:differentFrom rdf:resource=\"Meg\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :fatherOf ) ) Declaration( Class( :Woman ) ) ObjectPropertyAssertion( :fatherOf :Peter :Stewie ) ObjectPropertyAssertion( :fatherOf :Peter :Meg ) ClassAssertion( :Woman :Meg ) ClassAssertion( ObjectMaxCardinality( 1 :fatherOf :Woman ) :Peter ) DifferentIndividuals( :Stewie :Meg ))";
        test(premiseOntology, false, false, true, true);
        test(fspremiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DRational2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:DatatypeProperty rdf:about=\"dp\" /><rdf:Description rdf:about=\"a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"dp\" /><owl:allValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#rational\" /></owl:Restriction></rdf:type></rdf:Description><rdf:Description rdf:about=\"a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"dp\" /><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:minCardinality></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Prefix( owl:=<http://www.w3.org/2002/07/owl#> ) Ontology( Declaration( DataProperty( :dp ) ) ClassAssertion( DataAllValuesFrom( :dp owl:rational ) :a ) ClassAssertion( DataMinCardinality( 2 :dp ) :a ))";
        test(premiseOntology, false, false, false, true);
        test(fspremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DRational2D002() {
        String premiseOntology = "<rdf:RDF xml:base=\"urn:test\" xmlns=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#dp\" /><rdf:Description rdf:about=\"urn:test#a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#dp\" /><owl:allValuesFrom><rdfs:Datatype><owl:oneOf><rdf:Description><rdf:first rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">0.5</rdf:first><rdf:rest><rdf:Description><rdf:first rdf:datatype=\"http://www.w3.org/2002/07/owl#rational\">1/2</rdf:first><rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/></rdf:Description></rdf:rest></rdf:Description></owl:oneOf></rdfs:Datatype></owl:allValuesFrom></owl:Restriction></rdf:type></rdf:Description><rdf:Description rdf:about=\"urn:test#a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#dp\" /><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:minCardinality></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Prefix( owl:=<http://www.w3.org/2002/07/owl#> ) Prefix( xsd:=<http://www.w3.org/2001/XMLSchema#> ) Ontology( Declaration( DataProperty( :dp ) ) ClassAssertion( DataAllValuesFrom( :dp DataOneOf( \"0.5\"^^xsd:decimal \"1/2\"^^owl:rational ) ) :a ) ClassAssertion( DataMinCardinality( 2 :dp ) :a ))";
        test(premiseOntology, false, false, false, true);
        test(fspremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DRational2D003() {
        String premiseOntology = "<rdf:RDF xml:base=\"urn:test\" xmlns=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#dp\" /><rdf:Description rdf:about=\"urn:test#a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#dp\" /><owl:allValuesFrom><rdfs:Datatype><owl:oneOf><rdf:Description><rdf:first rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">0.3333333333333333</rdf:first><rdf:rest><rdf:Description><rdf:first rdf:datatype=\"http://www.w3.org/2002/07/owl#rational\">1/3</rdf:first><rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/></rdf:Description></rdf:rest></rdf:Description></owl:oneOf></rdfs:Datatype></owl:allValuesFrom></owl:Restriction></rdf:type></rdf:Description><rdf:Description rdf:about=\"urn:test#a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#dp\" /><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:minCardinality></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Prefix( owl:=<http://www.w3.org/2002/07/owl#> ) Prefix( xsd:=<http://www.w3.org/2001/XMLSchema#> ) Ontology( Declaration( DataProperty( :dp ) ) ClassAssertion( DataAllValuesFrom( :dp DataOneOf( \"0.3333333333333333\"^^xsd:decimal \"1/3\"^^owl:rational ) ) :a ) ClassAssertion( DataMinCardinality( 2 :dp ) :a ))";
        test(premiseOntology, false, false, false, true);
        test(fspremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DReflexiveProperty2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"knows\" /><owl:NamedIndividual rdf:about=\"Peter\" /><owl:ReflexiveProperty rdf:about=\"knows\" /></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology(\n"
            + " Declaration( ObjectProperty( :knows ) ) Declaration( NamedIndividual( :Peter ) ) ReflexiveObjectProperty( :knows ))";
        test(premiseOntology, true, true, false, true);
        test(fspremiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DSelfRestriction2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"likes\" /><rdf:Description rdf:about=\"Peter\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"likes\" /><owl:hasSelf rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasSelf></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology(\n"
            + " Declaration( ObjectProperty( :likes ) ) ClassAssertion( ObjectHasSelf( :likes ) :Peter ))";
        test(premiseOntology, true, false, false, true);
        test(fspremiseOntology, true, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DKeys2D002() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:DatatypeProperty rdf:about=\"hasSSN\" /><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasSSN\" /></owl:hasKey></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasSSN>123-45-6789</hasSSN></rdf:Description><rdf:Description rdf:about=\"Peter_Griffin\"><hasSSN>123-45-6789</hasSSN></rdf:Description><rdf:Description rdf:about=\"Peter\"><owl:differentFrom rdf:resource=\"Peter_Griffin\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( owl:=<http://www.w3.org/2002/07/owl#> ) Prefix( :=<http://example.org/> ) Ontology( Declaration( DataProperty( :hasSSN ) ) HasKey( owl:Thing () ( :hasSSN ) ) DataPropertyAssertion( :hasSSN :Peter \"123-45-6789\" ) DataPropertyAssertion( :hasSSN :Peter_Griffin \"123-45-6789\" ) DifferentIndividuals( :Peter :Peter_Griffin ))";
        test(premiseOntology, true, false, false, true);
        test(fspremiseOntology, true, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DKeys2D003() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"GriffinFamilyMember\" /><owl:DatatypeProperty rdf:about=\"hasName\" /><rdf:Description rdf:about=\"GriffinFamilyMember\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasName\" /></owl:hasKey></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasName>Peter</hasName><rdf:type rdf:resource=\"GriffinFamilyMember\" /></rdf:Description><rdf:Description rdf:about=\"Peter_Griffin\"><hasName>Peter</hasName><rdf:type rdf:resource=\"GriffinFamilyMember\" /></rdf:Description><rdf:Description rdf:about=\"StPeter\"><hasName>Peter</hasName></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( Class( :GriffinFamilyMember ) ) Declaration( DataProperty( :hasName ) ) HasKey( :GriffinFamilyMember () ( :hasName ) ) DataPropertyAssertion( :hasName :Peter \"Peter\" ) ClassAssertion( :GriffinFamilyMember :Peter ) DataPropertyAssertion( :hasName :Peter_Griffin \"Peter\" ) ClassAssertion( :GriffinFamilyMember :Peter_Griffin ) DataPropertyAssertion( :hasName :StPeter \"Peter\" ))";
        test(premiseOntology, true, false, true, true);
        test(fspremiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DKeys2D004() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"GriffinFamilyMember\" /><owl:DatatypeProperty rdf:about=\"hasName\" /><rdf:Description rdf:about=\"GriffinFamilyMember\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasName\" /></owl:hasKey></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasName>Peter</hasName><rdf:type rdf:resource=\"GriffinFamilyMember\" /></rdf:Description><rdf:Description rdf:about=\"Peter_Griffin\"><hasName>Peter</hasName><rdf:type rdf:resource=\"GriffinFamilyMember\" /></rdf:Description><rdf:Description rdf:about=\"StPeter\"><hasName>Peter</hasName></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( Class( :GriffinFamilyMember ) ) Declaration( DataProperty( :hasName ) ) HasKey( :GriffinFamilyMember () ( :hasName ) ) DataPropertyAssertion( :hasName :Peter \"Peter\" ) ClassAssertion( :GriffinFamilyMember :Peter ) DataPropertyAssertion( :hasName :Peter_Griffin \"Peter\" ) ClassAssertion( :GriffinFamilyMember :Peter_Griffin ) DataPropertyAssertion( :hasName :StPeter \"Peter\" ))";
        test(premiseOntology, true, false, true, true);
        test(fspremiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DKeys2D006() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"GriffinFamilyMember\" /><owl:DatatypeProperty rdf:about=\"hasName\" /><rdf:Description rdf:about=\"GriffinFamilyMember\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasName\" /></owl:hasKey></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasName>Peter</hasName><hasName>Kichwa-Tembo</hasName><rdf:type rdf:resource=\"GriffinFamilyMember\" /></rdf:Description><rdf:Description rdf:about=\"hasName\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#FunctionalProperty\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( Class( :GriffinFamilyMember ) ) Declaration( DataProperty( :hasName ) ) HasKey( :GriffinFamilyMember () ( :hasName ) ) DataPropertyAssertion( :hasName :Peter \"Peter\" ) DataPropertyAssertion( :hasName :Peter \"Kichwa-Tembo\" ) ClassAssertion( :GriffinFamilyMember :Peter ) FunctionalDataProperty( :hasName ))";
        test(premiseOntology, true, false, true, true);
        test(fspremiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DKeys2D007() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"Person\" />"
            + "<owl:Class rdf:about=\"Man\" /><owl:DatatypeProperty rdf:about=\"hasSSN\" /><owl:ObjectProperty rdf:about=\"marriedTo\" /><rdf:Description rdf:about=\"Person\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasSSN\" /></owl:hasKey></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasSSN>123-45-6789</hasSSN><rdf:type rdf:resource=\"Person\" /></rdf:Description><rdf:Description rdf:about=\"Lois\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"marriedTo\" /><owl:someValuesFrom>"
            + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"Man\" /><owl:Restriction><owl:onProperty rdf:resource=\"hasSSN\" /><owl:hasValue>123-45-6789</owl:hasValue></owl:Restriction></owl:intersectionOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, false, true);
    }

    @Test
    public void testURIResolverInconsistent2Dinteger2Dfiller() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:hasAge)) Declaration(Class(:Eighteen)) SubClassOf(DataHasValue(:hasAge \"18\"^^xsd:integer) :Eighteen) ClassAssertion(DataHasValue(:hasAge \"18\"^^xsd:integer) :a) ClassAssertion(ObjectComplementOf(:Eighteen) :a))";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverFunctionality2Dclash() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:hasAge)) FunctionalDataProperty(:hasAge) ClassAssertion(DataHasValue(:hasAge \"18\"^^xsd:integer) :a) ClassAssertion(DataHasValue(:hasAge \"19\"^^xsd:integer) :a))";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverChain2trans() {
        String premiseOntology = "<rdf:RDF xml:base=\"urn:test\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\"><owl:Ontology rdf:about=\"\"/><owl:ObjectProperty rdf:about=\"urn:test#p\"/><rdf:Description rdf:about=\"urn:test#p\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"urn:test#p\"/><rdf:Description rdf:about=\"urn:test#p\"/></owl:propertyChainAxiom></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DSelfRestriction2D002() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"likes\" /><rdf:Description rdf:about=\"Peter\"><likes rdf:resource=\"Peter\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology(\n"
            + " Declaration( ObjectProperty( :likes ) ) ObjectPropertyAssertion( :likes :Peter :Peter ))";
        test(premiseOntology, true, true, true, true);
        test(fspremiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverDisjointClasses2D001() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"Boy\" />"
            + "<owl:Class rdf:about=\"Girl\" /><rdf:Description rdf:about=\"Boy\"><owl:disjointWith rdf:resource=\"Girl\" /></rdf:Description><Boy rdf:about=\"Stewie\" /></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> )Ontology( Declaration( Class( :Boy ) ) Declaration( Class( :Girl ) ) DisjointClasses( :Boy :Girl ) ClassAssertion( :Boy :Stewie ))";
        test(premiseOntology, true, true, true, true);
        test(fspremiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverDisjointClasses2D003() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"Boy\" />"
            + "<owl:Class rdf:about=\"Girl\" />"
            + "<owl:Class rdf:about=\"Dog\" /><owl:AllDisjointClasses><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"Boy\" /><rdf:Description rdf:about=\"Girl\" /><rdf:Description rdf:about=\"Dog\" /></owl:members></owl:AllDisjointClasses><Boy rdf:about=\"Stewie\" /></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology(\n"
            + " Declaration( Class( :Boy ) ) Declaration( Class( :Girl ) ) Declaration( Class( :Dog ) ) DisjointClasses( :Boy :Girl :Dog ) ClassAssertion( :Boy :Stewie ))";
        test(premiseOntology, true, true, true, true);
        test(fspremiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverBnode2somevaluesfrom() {
        String premiseOntology = head
            + "xmlns:ex=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#p\"/><owl:Thing rdf:about=\"urn:test#a\"><ex:p><rdf:Description rdf:nodeID=\"urn:test#x\"/></ex:p></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverConsistent2Dinteger2Dfiller() {
        String premiseOntology = "Prefix(:=<http://example.org/>)\nPrefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataHasValue(:dp \"18\"^^xsd:integer)) ClassAssertion(:A :a) ClassAssertion(DataAllValuesFrom(:dp xsd:integer) :a))";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DObjectQCR2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:ObjectProperty rdf:about=\"fatherOf\" />"
            + "<owl:Class rdf:about=\"Man\" /><rdf:Description rdf:about=\"Peter\"><fatherOf rdf:resource=\"Stewie\" /><fatherOf rdf:resource=\"Chris\" /></rdf:Description><Man rdf:about=\"Stewie\" /><Man rdf:about=\"Chris\" /><rdf:Description rdf:about=\"Stewie\"><owl:differentFrom rdf:resource=\"Chris\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( ObjectProperty( :fatherOf ) ) Declaration( Class( :Man ) ) ObjectPropertyAssertion( :fatherOf :Peter :Stewie ) ObjectPropertyAssertion( :fatherOf :Peter :Chris ) ClassAssertion( :Man :Stewie ) ClassAssertion( :Man :Chris ) DifferentIndividuals( :Chris :Stewie ))";
        test(premiseOntology, true, true, true, true);
        test(fspremiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DDisjointDataProperties2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:DatatypeProperty rdf:about=\"hasName\" /><owl:DatatypeProperty rdf:about=\"hasAddress\" /><rdf:Description rdf:about=\"hasName\"><owl:propertyDisjointWith rdf:resource=\"hasAddress\" /></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasName>Peter Griffin</hasName><hasAddress>Peter Griffin</hasAddress></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( DataProperty( :hasName ) ) Declaration( DataProperty( :hasAddress ) ) DisjointDataProperties( :hasName :hasAddress ) DataPropertyAssertion( :hasName :Peter \"Peter Griffin\" ) DataPropertyAssertion( :hasAddress :Peter \"Peter Griffin\" ))";
        test(premiseOntology, false, true, true, true);
        test(fspremiseOntology, false, true, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DDisjointDataProperties2D002() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:DatatypeProperty rdf:about=\"hasName\" /><owl:DatatypeProperty rdf:about=\"hasAddress\" /><owl:DatatypeProperty rdf:about=\"hasZip\" /><owl:AllDisjointProperties><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasName\" /><rdf:Description rdf:about=\"hasAddress\" /><rdf:Description rdf:about=\"hasZip\" /></owl:members></owl:AllDisjointProperties><rdf:Description rdf:about=\"Peter\"><hasName>Peter Griffin</hasName></rdf:Description><rdf:Description rdf:about=\"Peter_Griffin\"><hasAddress>Peter Griffin</hasAddress></rdf:Description><rdf:Description rdf:about=\"Petre\"><hasZip>Peter Griffin</hasZip></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( DataProperty( :hasName ) ) Declaration( DataProperty( :hasAddress ) ) Declaration( DataProperty( :hasZip ) ) DisjointDataProperties( :hasName :hasAddress :hasZip ) DataPropertyAssertion( :hasName :Peter \"Peter Griffin\" ) DataPropertyAssertion( :hasAddress :Peter_Griffin \"Peter Griffin\" ) DataPropertyAssertion( :hasZip :Petre \"Peter Griffin\" ))";
        test(premiseOntology, false, true, true, true);
        test(fspremiseOntology, false, true, true, true);
    }

    @Test
    public void testURIResolverDatatype2DDataComplementOf2D001() {
        String fspremiseOntology = "Prefix( xsd:=<http://www.w3.org/2001/XMLSchema#> ) Prefix( :=<http://example.org/> ) Ontology( Declaration( DataProperty( :p ) ) DataPropertyRange( :p DataComplementOf( xsd:positiveInteger ) ) DataPropertyAssertion( :p :i \"-1\"^^xsd:negativeInteger ) DataPropertyAssertion( :p :i \"A string\"^^xsd:string ))";
        test(fspremiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DKeys2D005() {
        String premiseOntology = example
            + "<owl:Ontology/>"
            + "<owl:Class rdf:about=\"GriffinFamilyMember\" /><owl:DatatypeProperty rdf:about=\"hasName\" /><rdf:Description rdf:about=\"GriffinFamilyMember\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasName\" /></owl:hasKey></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasName>Peter</hasName><hasName>Kichwa-Tembo</hasName><rdf:type rdf:resource=\"GriffinFamilyMember\" /></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( :=<http://example.org/> ) Ontology( Declaration( Class( :GriffinFamilyMember ) ) Declaration( DataProperty( :hasName ) ) HasKey( :GriffinFamilyMember () ( :hasName ) ) DataPropertyAssertion( :hasName :Peter \"Peter\" ) DataPropertyAssertion( :hasName :Peter \"Kichwa-Tembo\" ) ClassAssertion( :GriffinFamilyMember :Peter ))";
        test(premiseOntology, true, false, true, true);
        test(fspremiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverNew2DFeature2DKeys2D001() {
        String premiseOntology = example
            + "<owl:Ontology/><owl:DatatypeProperty rdf:about=\"hasSSN\" /><rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"hasSSN\" /></owl:hasKey></rdf:Description><rdf:Description rdf:about=\"Peter\"><hasSSN>123-45-6789</hasSSN></rdf:Description><rdf:Description rdf:about=\"Peter_Griffin\"><hasSSN>123-45-6789</hasSSN></rdf:Description></rdf:RDF>";
        String fspremiseOntology = "Prefix( owl:=<http://www.w3.org/2002/07/owl#> )\nPrefix( :=<http://example.org/> )\nOntology( Declaration( DataProperty( :hasSSN ) ) HasKey( owl:Thing () ( :hasSSN ) ) DataPropertyAssertion( :hasSSN :Peter \"123-45-6789\" ) DataPropertyAssertion( :hasSSN :Peter_Griffin \"123-45-6789\" ))";
        test(fspremiseOntology, true, false, false, true);
        test(premiseOntology, true, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI5242D003() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#prop\"><rdfs:range>"
            + "<owl:Class rdf:about=\"urn:test#A\"/></rdfs:range></owl:ObjectProperty></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI5262D001() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf></owl:Class><rdf:Description><rdf:type rdf:nodeID=\"B\"/></rdf:Description>"
            + "<owl:Class><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#C\"/><rdf:Description rdf:nodeID=\"B\"/></owl:intersectionOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testTestCase3AWebOnt2DI5262D003() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notB\"><owl:complementOf rdf:nodeID=\"B\"/></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#u\"><owl:unionOf rdf:parseType=\"Collection\"><rdf:Description rdf:nodeID=\"B\"/>"
            + "<owl:Class rdf:about=\"urn:test#A\"/></owl:unionOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testTestCase3AWebOnt2DI5262D005() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf><owl:disjointWith>"
            + "<owl:Class rdf:about=\"urn:test#C\"/></owl:disjointWith><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#D\"/></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testTestCase3AWebOnt2DI5262D009() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#p\" /></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI462D004() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#C1\"><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DAnnotationProperty2D002() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#A\">"
            + "<first:ap>"
            + "<owl:Class rdf:about=\"urn:test#B\"/></first:ap></owl:Class><owl:AnnotationProperty rdf:about=\"urn:test#ap\"/></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI582D008() {
        String premiseOntology = "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\nPrefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\nPrefix(xml:=<http://www.w3.org/XML/1998/namespace>)\nPrefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\nPrefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>) Ontology(\n Declaration(DataProperty(<http://www.w3.org/2002/03owlt/I5.8/premises008#p>))\n DataPropertyRange(<http://www.w3.org/2002/03owlt/I5.8/premises008#p> xsd:short))";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dasymmetric2Dterm() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:SymmetricProperty rdf:about=\"http://www.example.org#p\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#AsymmetricProperty\"/></owl:SymmetricProperty><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#y\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, true, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dtransitive2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:TransitiveProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p><rdf:Description rdf:about=\"http://www.example.org#y\"><ex:p rdf:resource=\"http://www.example.org#z\"/></rdf:Description></ex:p></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dclass2Dnothing2Dterm() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">"
            + "<owl:Class rdf:about=\"http://www.example.org#c\"/></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dndis2Dalldifferent2Dfw() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AllDifferent rdf:about=\"http://www.example.org#z\"><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#w1\"><owl:sameAs rdf:resource=\"http://www.example.org#w2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#w2\"/><rdf:Description rdf:about=\"http://www.example.org#w3\"/></owl:members></owl:AllDifferent></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Dsameas2Dsym() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x\"><owl:sameAs rdf:resource=\"http://www.example.org#y\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverPlus2Dminus2D02Dplus2Ddisjointness() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:Meg)) Declaration(DataProperty(:numberOfChildren)) DataPropertyAssertion(:numberOfChildren :Meg \"+0.0\"^^xsd:float) DataPropertyAssertion(:numberOfChildren :Meg \"-0.0\"^^xsd:float) FunctionalDataProperty(:numberOfChildren))";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Dsameas2Dtrans() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x\"><owl:sameAs><rdf:Description rdf:about=\"http://www.example.org#y\"><owl:sameAs rdf:resource=\"http://www.example.org#z\"/></rdf:Description></owl:sameAs></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dndis2Dalldifferent2Dfw2Ddistinctmembers() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AllDifferent rdf:about=\"http://www.example.org#z\"><owl:distinctMembers rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#w1\"><owl:sameAs rdf:resource=\"http://www.example.org#w2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#w2\"/><rdf:Description rdf:about=\"http://www.example.org#w3\"/></owl:distinctMembers></owl:AllDifferent></rdf:RDF>";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dinversefunc2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:InverseFunctionalProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x1\"><ex:p rdf:resource=\"http://www.example.org#y\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><ex:p rdf:resource=\"http://www.example.org#y\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DInverseFunctionalProperty2D001() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:InverseFunctionalProperty rdf:about=\"urn:test#prop\"/><rdf:Description rdf:about=\"urn:test#subject1\">"
            + "<first:prop rdf:resource=\"urn:test#object\" /></rdf:Description><rdf:Description rdf:about=\"urn:test#subject2\">"
            + "<first:prop rdf:resource=\"urn:test#object\" /></rdf:Description><rdf:Description rdf:about=\"http://www.w3.org/2002/03owlt/InverseFunctionalProperty/premises001#object\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></rdf:Description><rdf:Description rdf:about=\"http://www.w3.org/2002/03owlt/InverseFunctionalProperty/premises001#subject2\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></rdf:Description><rdf:Description rdf:about=\"http://www.w3.org/2002/03owlt/InverseFunctionalProperty/premises001#subject1\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dsymmetric2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:SymmetricProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#y\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, true, true, true);
    }

    @Ignore("An ontology missing the <Ontology> tag is not a valid ontology, but the current OWLOntology interface does not allow the profile checkers to know.")
    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqclass2Dsym() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:equivalentClass rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqprop2Drflxv() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:ObjectProperty rdf:about=\"http://www.example.org#op\"/><owl:DatatypeProperty rdf:about=\"http://www.example.org#dp\"/></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI5262D002() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:nodeID=\"B\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#B\"/></owl:intersectionOf><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#A\"/></owl:equivalentClass></owl:Class><rdf:Description><rdf:type rdf:nodeID=\"B\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D104() {
        String premiseOntology = head2
            + "<owl:Ontology rdf:about=\"\"/>"
            + "<owl:Class rdf:about=\"urn:test#Unsatisfiable\"><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#c1\"/></owl:equivalentClass></owl:Class>"
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
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DSymmetricProperty2D003() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Thing rdf:about=\"urn:test#Ghent\">"
            + "<first:path><owl:Thing rdf:about=\"urn:test#Antwerp\"/></first:path></owl:Thing><owl:SymmetricProperty rdf:about=\"urn:test#path\"/></rdf:RDF>";
        test(premiseOntology, false, true, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D901() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#p\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/><rdfs:range>"
            + "<owl:Class rdf:about=\"urn:test#A\"/></rdfs:range></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/><rdfs:range>"
            + "<owl:Class rdf:about=\"urn:test#B\"/></rdfs:range></owl:ObjectProperty>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:disjointWith rdf:resource=\"urn:test#B\"/></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Ddescription2Dlogic2D902() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#r\"/><owl:ObjectProperty rdf:about=\"urn:test#p\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/><rdfs:range>"
            + "<owl:Class rdf:about=\"urn:test#A\"/></rdfs:range></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"><rdfs:subPropertyOf rdf:resource=\"urn:test#r\"/><rdfs:range>"
            + "<owl:Class rdf:about=\"urn:test#B\"/></rdfs:range></owl:ObjectProperty>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:disjointWith rdf:resource=\"urn:test#B\"/></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DequivalentClass2D006() {
        String premiseOntology = rdf + " xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#A\"/>"
            + "<owl:Class rdf:about=\"urn:test#B\"/></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2Dmiscellaneous2D204() {
        String premiseOntology = head
            + "xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:FunctionalProperty rdf:about=\"urn:test#fp\"/><owl:DatatypeProperty rdf:about=\"urn:test#fp\"/><owl:Thing>"
            + "<first:fp rdf:parseType=\"Literal\"><span xml:lang='en'><b>Good!</b></span></first:fp>"
            + "<first:fp rdf:parseType=\"Literal\"><span xml:lang='en'><b>Bad!</b></span></first:fp></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DunionOf2D003() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#a\"/></owl:oneOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#B\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#b\"/></owl:oneOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A-and-B\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#a\"/><owl:Thing rdf:about=\"urn:test#b\"/></owl:oneOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DunionOf2D004() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xmlns:second=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#A-and-B\"><owl:unionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#A\"/>"
            + "<owl:Class rdf:about=\"urn:test#B\"/></owl:unionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#a\"/></owl:oneOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#B\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"urn:test#b\"/></owl:oneOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Danonymous2Dindividual() {
        String premiseOntology = head
            + "xmlns:j.0=\"http://owl2.test/rules#\"><owl:Ontology /><owl:ObjectProperty rdf:about=\"http://owl2.test/rules#op\"/><owl:NamedIndividual rdf:about=\"http://owl2.test/rules#I\"/><owl:NamedIndividual><j.0:op rdf:resource=\"http://owl2.test/rules#I\"/></owl:NamedIndividual></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Dleftside2Dallvaluesfrom() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C\"/>"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C1\"/><owl:ObjectProperty rdf:about=\"http://owl2.test/rules#op\"/><owl:Restriction><owl:allValuesFrom rdf:resource=\"http://owl2.test/rules#C1\"/><owl:onProperty rdf:resource=\"http://owl2.test/rules#op\"/><rdfs:subClassOf rdf:resource=\"http://owl2.test/rules#C\"/></owl:Restriction></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Dleftside2Dmaxcard() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C\"/><owl:ObjectProperty rdf:about=\"http://owl2.test/rules#op\"/><owl:Restriction><rdfs:subClassOf rdf:resource=\"http://owl2.test/rules#C\"/><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">2</owl:maxCardinality><owl:onProperty rdf:resource=\"http://owl2.test/rules#op\"/></owl:Restriction></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Doneof() {
        String premiseOntology = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\"><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#Cb\"><owl:oneOf rdf:parseType=\"Collection\"><owl:Thing rdf:about=\"http://owl2.test/rules#X\"/><owl:Thing rdf:about=\"http://owl2.test/rules#Y\"/></owl:oneOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Dowlreal() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C_Sub\"><rdfs:subClassOf><owl:Restriction><owl:allValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#real\"/><owl:onProperty><owl:DatatypeProperty rdf:about=\"http://owl2.test/rules#p\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Drightside2Dsomevaluesfrom() {
        String premiseOntology = rdf
            + "><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C_Sub\"><rdfs:subClassOf><owl:Restriction><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C1\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty rdf:about=\"http://owl2.test/rules#p\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Drightside2Dunionof() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C_Sub\"><rdfs:subClassOf>"
            + "<owl:Class><owl:unionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C1\"/>"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C2\"/></owl:unionOf></owl:Class></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Dunionof() {
        String premiseOntology = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\"><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C\"><owl:unionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C1\"/>"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C2\"/></owl:unionOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Drules2Dfp2DdifferentFrom() {
        String premiseOntology = head
            + "xmlns:j.0=\"http://owl2.test/rules/\"><owl:Ontology /><owl:FunctionalProperty rdf:about=\"http://owl2.test/rules/fp\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/></owl:FunctionalProperty><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/Y2\"><j.0:fp><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/X2\"/></j.0:fp></owl:NamedIndividual><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/X1\"><owl:differentFrom rdf:resource=\"http://owl2.test/rules/X2\"/></owl:NamedIndividual><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/Y1\"><j.0:fp rdf:resource=\"http://owl2.test/rules/X1\"/></owl:NamedIndividual></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Drules2Difp2DdifferentFrom() {
        String premiseOntology = head
            + "xmlns:j.0=\"http://owl2.test/rules/\"><owl:Ontology /><owl:InverseFunctionalProperty rdf:about=\"http://owl2.test/rules/ifp\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/></owl:InverseFunctionalProperty><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/Y2\"><j.0:ifp><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/X2\"/></j.0:ifp></owl:NamedIndividual><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/X1\"/><owl:NamedIndividual rdf:about=\"http://owl2.test/rules/Y1\"><owl:differentFrom rdf:resource=\"http://owl2.test/rules/Y2\"/><j.0:ifp rdf:resource=\"http://owl2.test/rules/X1\"/></owl:NamedIndividual></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dinvalid2Dmincard() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:j.0=\"http://owl2.test/rules#\"><owl:Ontology rdf:about=\"http://org.semanticweb.ontologies/Ontology1232054810511161000\"/>"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C\"><rdfs:subClassOf><owl:Restriction><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality><owl:onProperty><owl:ObjectProperty rdf:about=\"http://owl2.test/rules#OP\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/><j.0:C rdf:about=\"http://owl2.test/rules#c\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\"/></j.0:C></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dvalid2Doneof() {
        String premiseOntology = rdf
            + "><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#Cb\"/><owl:NamedIndividual rdf:about=\"http://owl2.test/rules#X\"/><owl:NamedIndividual rdf:about=\"http://owl2.test/rules#Y\"/><rdf:Description><rdfs:subClassOf rdf:resource=\"http://owl2.test/rules#Cb\"/><owl:oneOf rdf:parseType=\"Collection\"><owl:NamedIndividual rdf:about=\"http://owl2.test/rules#X\"/><owl:NamedIndividual rdf:about=\"http://owl2.test/rules#Y\"/></owl:oneOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverOwl22Drl2Dvalid2Drightside2Dallvaluesfrom() {
        String premiseOntology = rdf
            + "><owl:Ontology />"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"http://owl2.test/rules#op\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"http://owl2.test/rules#C1\"/></owl:allValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testURIResolverPlus2Dand2Dminus2D02Dintegers() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:dp)) Declaration(Class(:A)) SubClassOf(:A DataAllValuesFrom(:dp DataOneOf(\"0\"^^xsd:integer)) ) ClassAssertion(:A :a) ClassAssertion( DataSomeValuesFrom(:dp DataOneOf(\"-0\"^^xsd:integer)) :a ))";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dbool2Dcomplement2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#x\"><rdf:type rdf:resource=\"http://www.example.org#c2\"/></ex:c1><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:complementOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dbool2Dintersection2Dinst2Dcomp() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:x rdf:about=\"http://www.example.org#z\"><rdf:type rdf:resource=\"http://www.example.org#y\"/></ex:x><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:intersectionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:intersectionOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dbool2Dintersection2Dinst2Dexpr() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c rdf:about=\"http://www.example.org#z\"/><rdf:Description rdf:nodeID=\"A0\"><rdf:first rdf:resource=\"http://www.example.org#x\"/><rdf:rest rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#y\"/></rdf:rest></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:intersectionOf rdf:nodeID=\"A0\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dbool2Dintersection2Dterm() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:intersectionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:intersectionOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dbool2Dunion2Dinst2Dcomp() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:x rdf:about=\"http://www.example.org#z\"/><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:unionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:unionOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dbool2Dunion2Dterm() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:unionOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:unionOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchain2Ddef() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p1><rdf:Description rdf:about=\"http://www.example.org#y\"><ex:p2 rdf:resource=\"http://www.example.org#z\"/></rdf:Description></ex:p1></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><owl:propertyChainAxiom rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#p1\"/><rdf:Description rdf:about=\"http://www.example.org#p2\"/></owl:propertyChainAxiom></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dasymmetric2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AsymmetricProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p><rdf:Description rdf:about=\"http://www.example.org#y\"><ex:p rdf:resource=\"http://www.example.org#x\"/></rdf:Description></ex:p></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dfunctional2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:FunctionalProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#y1\"/><ex:p rdf:resource=\"http://www.example.org#y2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dinversefunc2Ddata() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:InverseFunctionalProperty rdf:about=\"http://www.example.org#p\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/></owl:InverseFunctionalProperty><rdf:Description rdf:about=\"http://www.example.org#x1\"><ex:p>data</ex:p></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><ex:p>data</ex:p></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dchar2Dirreflexive2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:IrreflexiveProperty rdf:about=\"http://www.example.org#p\"/><rdf:Description rdf:about=\"http://www.example.org#x\"><ex:p rdf:resource=\"http://www.example.org#x\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Denum2Dinst2Dincluded() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#e\"><owl:oneOf rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#y\"/></owl:oneOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Ddifferent2Dirrflxv() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x\"><owl:differentFrom rdf:resource=\"http://www.example.org#x\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Ddisclass2Deqclass() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#x\"/><ex:c2 rdf:about=\"http://www.example.org#y\"/><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:equivalentClass rdf:resource=\"http://www.example.org#c2\"/><owl:disjointWith rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Ddisclass2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#w\"><rdf:type rdf:resource=\"http://www.example.org#c2\"/></ex:c1><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:disjointWith rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Ddisclass2Dirrflxv() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c rdf:about=\"http://www.example.org#x\"/><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:disjointWith rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Ddisprop2Deqprop() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><ex:p2 rdf:resource=\"http://www.example.org#o2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p1 rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><owl:equivalentProperty rdf:resource=\"http://www.example.org#p2\"/><owl:propertyDisjointWith rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Ddisprop2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p1 rdf:resource=\"http://www.example.org#o\"/><ex:p2 rdf:resource=\"http://www.example.org#o\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><owl:propertyDisjointWith rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Ddisprop2Dirrflxv() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p rdf:resource=\"http://www.example.org#o\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><owl:propertyDisjointWith rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqclass2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#x\"/><ex:c2 rdf:about=\"http://www.example.org#y\"/><rdf:Description rdf:about=\"http://www.example.org#c1\"><owl:equivalentClass rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqclass2Dsubclass2D2() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c2\"><rdfs:subClassOf><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdfs:subClassOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqclass2Dsubst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf><rdf:Description rdf:about=\"http://www.example.org#c2\"><owl:equivalentClass rdf:resource=\"http://www.example.org#d2\"/></rdf:Description></rdfs:subClassOf><owl:equivalentClass rdf:resource=\"http://www.example.org#d1\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqprop2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><ex:p2 rdf:resource=\"http://www.example.org#o2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p1 rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><owl:equivalentProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqprop2Dsubprop2D2() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Deqprop2Dsubst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><owl:equivalentProperty rdf:resource=\"http://www.example.org#q2\"/></rdf:Description></rdfs:subPropertyOf><owl:equivalentProperty rdf:resource=\"http://www.example.org#q1\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Dsameas2Drflxv() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p rdf:resource=\"http://www.example.org#o\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Deqdis2Dsameas2Dsubst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><owl:sameAs><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p1 rdf:resource=\"http://www.example.org#o1\"/></rdf:Description></owl:sameAs></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#o2\"><owl:sameAs rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p2\"><owl:sameAs rdf:resource=\"http://www.example.org#p1\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dinv2Dinst() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s2\"><ex:q rdf:resource=\"http://www.example.org#o2\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#s1\"><ex:p rdf:resource=\"http://www.example.org#o1\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#q\"><owl:inverseOf rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dkey2Ddef() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c rdf:about=\"http://www.example.org#x\"><ex:p1 rdf:resource=\"http://www.example.org#z\"/><ex:p2>data</ex:p2></ex:c><ex:c rdf:about=\"http://www.example.org#y\"><ex:p1 rdf:resource=\"http://www.example.org#z\"/><ex:p2>data</ex:p2></ex:c><rdf:Description rdf:about=\"http://www.example.org#c\"><owl:hasKey rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#p1\"/><rdf:Description rdf:about=\"http://www.example.org#p2\"/></owl:hasKey></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dndis2Dalldisjointclasses2Dfw() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AllDisjointClasses rdf:about=\"http://www.example.org#z\"><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#c1\"/><rdf:Description rdf:about=\"http://www.example.org#c2\"/><rdf:Description rdf:about=\"http://www.example.org#c3\"/></owl:members></owl:AllDisjointClasses><ex:c1 rdf:about=\"http://www.example.org#w\"><rdf:type rdf:resource=\"http://www.example.org#c2\"/></ex:c1></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dndis2Dalldisjointproperties2Dfw() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:AllDisjointProperties rdf:about=\"http://www.example.org#z\"><owl:members rdf:parseType=\"Collection\"><rdf:Description rdf:about=\"http://www.example.org#p1\"/><rdf:Description rdf:about=\"http://www.example.org#p2\"/><rdf:Description rdf:about=\"http://www.example.org#p3\"/></owl:members></owl:AllDisjointProperties><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p1 rdf:resource=\"http://www.example.org#o\"/><ex:p2 rdf:resource=\"http://www.example.org#o\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dnpa2Ddat2Dfw() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:sourceIndividual><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p>data</ex:p></rdf:Description></owl:sourceIndividual><owl:assertionProperty rdf:resource=\"http://www.example.org#p\"/><owl:targetValue>data</owl:targetValue></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Dnpa2Dind2Dfw() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:sourceIndividual><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p rdf:resource=\"http://www.example.org#o\"/></rdf:Description></owl:sourceIndividual><owl:assertionProperty rdf:resource=\"http://www.example.org#p\"/><owl:targetIndividual rdf:resource=\"http://www.example.org#o\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfs2Ddomain2Dcond() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#u\"><ex:p rdf:resource=\"http://www.example.org#v\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:domain rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfs2Drange2Dcond() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#u\"><ex:p rdf:resource=\"http://www.example.org#v\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:range rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfs2Dsubclass2Dcond() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:c1 rdf:about=\"http://www.example.org#w\"/><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfs2Dsubclass2Dtrans() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf><rdf:Description rdf:about=\"http://www.example.org#c2\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c3\"/></rdf:Description></rdfs:subClassOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfs2Dsubprop2Dcond() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#s\"><ex:p1 rdf:resource=\"http://www.example.org#o\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfs2Dsubprop2Dtrans() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p3\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfsext2Ddomain2Dsubprop() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:domain rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfsext2Ddomain2Dsuperclass() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:domain><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdfs:domain></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfsext2Drange2Dsubprop() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf><rdf:Description rdf:about=\"http://www.example.org#p2\"><rdfs:range rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdfs:subPropertyOf></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drdfsext2Drange2Dsuperclass() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#p\"><rdfs:range><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></rdfs:range></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dallvalues2Dcmp2Dclass() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:allValuesFrom><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></owl:allValuesFrom><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c2\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dallvalues2Dcmp2Dprop() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></owl:onProperty></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dallvalues2Dinst2Dobj() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#x\"/></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:allValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dhasvalue2Dcmp2Dprop() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:hasValue rdf:resource=\"http://www.example.org#v\"/><owl:onProperty><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></owl:onProperty></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:hasValue rdf:resource=\"http://www.example.org#v\"/><owl:onProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dhasvalue2Dinst2Dobj() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"/><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:hasValue rdf:resource=\"http://www.example.org#u\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dhasvalue2Dinst2Dsubj() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:hasValue rdf:resource=\"http://www.example.org#u\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#u\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dmaxcard2Dinst2Dobj2Done() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#x1\"/><ex:p rdf:resource=\"http://www.example.org#x2\"/></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dmaxcard2Dinst2Dobj2Dzero() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p rdf:resource=\"http://www.example.org#x\"/></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dmaxqcr2Dinst2Dobj2Done() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p><ex:c rdf:about=\"http://www.example.org#x1\"/></ex:p><ex:p><ex:c rdf:about=\"http://www.example.org#x2\"/></ex:p></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxQualifiedCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:maxQualifiedCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/><owl:onClass rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dmaxqcr2Dinst2Dobj2Dzero() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><ex:z rdf:about=\"http://www.example.org#w\"><ex:p><ex:c rdf:about=\"http://www.example.org#x\"/></ex:p></ex:z><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:maxQualifiedCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxQualifiedCardinality><owl:onProperty rdf:resource=\"http://www.example.org#p\"/><owl:onClass rdf:resource=\"http://www.example.org#c\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dsomevalues2Dcmp2Dclass() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:someValuesFrom><rdf:Description rdf:about=\"http://www.example.org#c1\"><rdfs:subClassOf rdf:resource=\"http://www.example.org#c2\"/></rdf:Description></owl:someValuesFrom><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c2\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dsomevalues2Dcmp2Dprop() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#x1\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty><rdf:Description rdf:about=\"http://www.example.org#p1\"><rdfs:subPropertyOf rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></owl:onProperty></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#x2\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p2\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverRdfbased2Dsem2Drestrict2Dsomevalues2Dinst2Dsubj() {
        String premiseOntology = head
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:ex=\"http://www.example.org#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><rdf:Description rdf:about=\"http://www.example.org#z\"><owl:someValuesFrom rdf:resource=\"http://www.example.org#c\"/><owl:onProperty rdf:resource=\"http://www.example.org#p\"/></rdf:Description><rdf:Description rdf:about=\"http://www.example.org#w\"><ex:p><ex:c rdf:about=\"http://www.example.org#x\"/></ex:p></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, false);
    }

    @Test
    public void testURIResolverString2Dinteger2Dclash() {
        String premiseOntology = "Prefix(:=<http://example.org/>) Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>) Ontology( Declaration(NamedIndividual(:a)) Declaration(DataProperty(:hasAge)) DataPropertyRange(:hasAge xsd:integer) ClassAssertion(DataHasValue(:hasAge \"aString\"^^xsd:string) :a))";
        test(premiseOntology, true, false, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DAnnotationProperty2D003() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:AnnotationProperty rdf:about=\"urn:test#ap\"/>"
            + "<owl:Class rdf:about=\"urn:test#A\">"
            + "<first:ap><rdf:Description rdf:about=\"urn:test#B\"/></first:ap></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DAnnotationProperty2D004() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology /><owl:AnnotationProperty rdf:about=\"urn:test#ap\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#string\"/></owl:AnnotationProperty></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI452D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#EuropeanCountry\" />"
            + "<owl:Class rdf:about=\"urn:test#Person\" />"
            + "<owl:Class rdf:about=\"urn:test#EUCountry\"><owl:oneOf rdf:parseType=\"Collection\">"
            + "<first:EuropeanCountry rdf:about=\"urn:test#UK\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#BE\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#ES\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#FR\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#NL\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#PT\"/></owl:oneOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#hasEuroMP\"><rdfs:domain rdf:resource=\"urn:test#EUCountry\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#isEuroMPFrom\"><owl:inverseOf rdf:resource=\"urn:test#hasEuroMP\"/></owl:ObjectProperty>"
            + "<owl:Class rdf:about=\"urn:test#EuroMP\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#isEuroMPFrom\" /><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<first:Person rdf:about=\"urn:test#Kinnock\" />"
            + "<first:EuropeanCountry rdf:about=\"urn:test#UK\">"
            + "<first:hasEuroMP rdf:resource=\"urn:test#Kinnock\" /></first:EuropeanCountry></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI452D002() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#EuropeanCountry\" />"
            + "<owl:Class rdf:about=\"urn:test#Person\" />"
            + "<owl:Class rdf:about=\"urn:test#EUCountry\"><owl:oneOf rdf:parseType=\"Collection\">"
            + "<first:EuropeanCountry rdf:about=\"urn:test#UK\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#BE\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#ES\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#FR\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#NL\"/>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#PT\"/></owl:oneOf></owl:Class><owl:ObjectProperty rdf:about=\"urn:test#hasEuroMP\"><rdfs:domain rdf:resource=\"urn:test#EUCountry\"/></owl:ObjectProperty><owl:ObjectProperty rdf:about=\"urn:test#isEuroMPFrom\"><owl:inverseOf rdf:resource=\"urn:test#hasEuroMP\"/></owl:ObjectProperty>"
            + "<owl:Class rdf:about=\"urn:test#EuroMP\"><owl:equivalentClass><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#isEuroMPFrom\" /><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<first:Person rdf:about=\"urn:test#Kinnock\"><rdf:type>"
            + "<owl:Class><owl:complementOf rdf:resource=\"urn:test#EuroMP\"/></owl:Class></rdf:type></first:Person>"
            + "<first:EuropeanCountry rdf:about=\"urn:test#UK\">"
            + "<first:hasEuroMP rdf:resource=\"urn:test#Kinnock\" /></first:EuropeanCountry></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI462D0052DDirect() {
        String premiseOntology = head
            + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#C1\"><rdfs:comment>An example class.</rdfs:comment><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#C2\"/></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testSpecialURIResolverTestCase3AWebOnt2DI522D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI522D002() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI522D003() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notA\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI522D004() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xmlns:second=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notA\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI522D005() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notA\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#B\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notB\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notAorB\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#notA\"/>"
            + "<owl:Class rdf:about=\"urn:test#notB\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#AorB\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notAorB\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI522D006() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#p\"/></owl:onProperty><owl:maxCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">0</owl:maxCardinality></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#A\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notA\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#q\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#B\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notB\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#r\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notAorB\"><owl:intersectionOf rdf:parseType=\"Collection\">"
            + "<owl:Class rdf:about=\"urn:test#notA\"/>"
            + "<owl:Class rdf:about=\"urn:test#notB\"/></owl:intersectionOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#AorB\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:someValuesFrom>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"/></owl:someValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#notAorB\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#s\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#Nothing\"/></owl:allValuesFrom></owl:Restriction></owl:equivalentClass></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI5212D002() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"urn:test#Reptile\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:DatatypeProperty rdf:about=\"urn:test#family-name\"/></owl:onProperty><owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:cardinality></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Amphisbaenidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Amphisbaenidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Agamidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Agamidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Anomalepidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Anomalepidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Emydidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Emydidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Crocodylidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Crocodylidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Gekkonidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Gekkonidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Sphenodontidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Sphenodontidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Cordylidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Cordylidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Bipedidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Bipedidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Leptotyphlopidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Leptotyphlopidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Xantusiidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Xantusiidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class>"
            + "<owl:Class rdf:about=\"urn:test#Loxocemidae\"><rdfs:subClassOf rdf:resource=\"urn:test#Reptile\" /><rdfs:subClassOf><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#family-name\"/><owl:hasValue>Loxocemidae</owl:hasValue></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI5242D004() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/>"
            + "<owl:Class rdf:about=\"http://www.w3.org/2002/07/owl#Thing\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty rdf:about=\"urn:test#prop\"/></owl:onProperty><owl:allValuesFrom>"
            + "<owl:Class rdf:about=\"urn:test#A\"/></owl:allValuesFrom></owl:Restriction></rdfs:subClassOf></owl:Class></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI5262D010() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#p\" /></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI532D006() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Thing>"
            + "<first:p><owl:Thing/></first:p></owl:Thing><owl:ObjectProperty rdf:about=\"urn:test#p\" /></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI532D008() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:Thing>"
            + "<first:dp>value</first:dp></owl:Thing><owl:DatatypeProperty rdf:about=\"urn:test#dp\" /></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI532D010() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#p\"/><owl:Thing>"
            + "<first:p>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></first:p></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI532D011() {
        String premiseOntology = head
            + "xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:AnnotationProperty rdf:about=\"urn:test#p\"/><owl:Thing>"
            + "<first:p>"
            + "<owl:Class rdf:about=\"urn:test#c\"/></first:p></owl:Thing></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI552D005() {
        String premiseOntology = head + "xml:base=\"urn:test\">"
            + "<owl:Class rdf:about=\"urn:test#a\" /></rdf:RDF>";
        test(premiseOntology, true, true, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI582D006() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xmlns:second=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#p\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#byte\" /></owl:DatatypeProperty></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI582D007() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#p\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#short\" /></owl:DatatypeProperty></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI582D009() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#p\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\" /><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#nonPositiveInteger\" /></owl:DatatypeProperty></rdf:RDF>";
        test(premiseOntology, false, false, true, true);
    }

    @Test
    public void testTestCase3AWebOnt2DI582D010() {
        String premiseOntology = rdf
            + " xml:base=\"urn:test\"><owl:Ontology/><owl:DatatypeProperty rdf:about=\"urn:test#p\"><rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\" /></owl:DatatypeProperty><rdf:Description rdf:about=\"urn:test#john\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#p\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2001/XMLSchema#nonPositiveInteger\" /></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        test(premiseOntology, false, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DOntology2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology rdf:about=\"\" />"
            + "<owl:Class rdf:about=\"urn:test#Car\"><owl:equivalentClass>"
            + "<owl:Class rdf:about=\"urn:test#Automobile\"/></owl:equivalentClass></owl:Class>"
            + "<first:Car rdf:about=\"urn:test#car\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Car>"
            + "<first:Automobile rdf:about=\"urn:test#auto\"><rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Thing\" /></first:Automobile></rdf:RDF>";
        test(premiseOntology, true, true, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DRestriction2D001() {
        String premiseOntology = rdf
            + " xmlns:first=\"urn:test#\" xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#op\"/><rdf:Description rdf:about=\"urn:test#a\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#op\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Nothing\" /></owl:Restriction></rdf:type></rdf:Description><rdf:Description rdf:about=\"urn:test#b\"><rdf:type><owl:Restriction><owl:onProperty rdf:resource=\"urn:test#op\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Nothing\" /></owl:Restriction></rdf:type></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, false, true);
    }

    @Test
    public void testURIResolverTestCase3AWebOnt2DRestriction2D002() {
        String premiseOntology = head
            + "xml:base=\"urn:test\"><owl:Ontology/><owl:ObjectProperty rdf:about=\"urn:test#op\"/><rdf:Description rdf:about=\"urn:test#a\"><rdf:type><owl:Restriction rdf:nodeID=\"r\"><owl:onProperty rdf:resource=\"urn:test#op\"/><owl:someValuesFrom rdf:resource=\"http://www.w3.org/2002/07/owl#Nothing\" /></owl:Restriction></rdf:type></rdf:Description><rdf:Description rdf:about=\"urn:test#b\"><rdf:type rdf:nodeID=\"r\"/></rdf:Description></rdf:RDF>";
        test(premiseOntology, true, false, false, true);
    }
}
