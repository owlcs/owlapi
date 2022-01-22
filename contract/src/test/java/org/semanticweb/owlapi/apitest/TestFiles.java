package org.semanticweb.owlapi.apitest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

@SuppressWarnings("javadoc")
public class TestFiles {

    private static String string(String name) {
        try (InputStream in = TestFiles.class.getResourceAsStream(name)) {
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String[] strings(String name) {
        try (InputStream in = TestFiles.class.getResourceAsStream(name)) {
            Iterator<String> readLines = IOUtils.readLines(in, StandardCharsets.UTF_8).iterator();
            List<String> toReturn = new ArrayList<>();
            StringBuilder b = new StringBuilder();
            while (readLines.hasNext()) {
                String line = readLines.next();
                if (line.isEmpty()) {
                    if (b.length() != 0) {
                        toReturn.add(b.toString());
                    }
                    b = new StringBuilder();
                } else {
                    b.append(line).append("\n");
                }
            }
            if (b.length() != 0) {
                toReturn.add(b.toString());
            }
            assert !toReturn.isEmpty();
            return toReturn.toArray(new String[0]);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static final String CREATED_BY_WITH_SPACE =
        "ontology: test\n[Typedef]\nid: R:1\nname: r1\ncreated_by: John Doe";
    public static final String IRI = "http://www.semanticweb.org/owlapi/test#iri";
    public static final String IRII = "<http://www.semanticweb.org/owlapi/test#iri>";
    public static final String PREFIX = "http://example.com/ontology/";
    public static final String XY = "http://example.com/ontology/x,y";
    public static final String NS = "http://example.com/ontology";
    public static final String wrong =
        "rdf:datatype=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral\"";

    public static final String manShortOnto = "Prefix: : <urn:shortened#>\nOntology: Role1\nClass: Role";
    public static final String NO_INPUT =
        "ontology: uberon\n" + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
            + "[Typedef]\nid: part_of\nxref: BFO:0000050";

    public static final String UBERON_CORE = "format-version: 1.2\n" + "ontology: uberon/core\n\n"
        + "[Term]\nid: UBERON:0004138\n" + "name: somitomeric trunk muscle\n"
        + "property_value: seeAlso \"string\"\n" + "\n" + "[Typedef]\n" + "id: seeAlso\n"
        + "name: seeAlso\n" + "is_metadata_tag: true\n" + "is_class_level: true";

    public static final String UBERON_PATO =
        "format-version: 1.2\n" + "import: http://purl.obolibrary.org/obo/uberon/pato_import.owl\n"
            + "ontology: uberon/core\n" + "\n" + "[Term]\n" + "id: UBERON:0004138\n"
            + "name: somitomeric trunk muscle\n" + "property_value: seeAlso \"string\"\n" + "\n"
            + "[Typedef]\n" + "id: seeAlso\n" + "name: seeAlso\n" + "is_metadata_tag: true\n"
            + "is_class_level: true";

    public static final String EMPTY_IMPORT =
        "ontology: uberon\n" + "import: http://purl.obolibrary.org/obo/uberon/test_import.owl\n\n"
            + "[Term]\nid: X:1\nname: x1\nrelationship: part_of X:2\n\n"
            + "[Typedef]\nid: part_of\nxref: BFO:0000050";
    public static final String PROPERTY_VALUE_QUOTES_OBO = "format-version: 1.2\nontology: test\n\n"
        + "[Term]\nid: X:1\nproperty_value: rdfs:seeAlso \"xx\" xsd:string\n\n"
        + "[Term]\nid: X:2\nproperty_value: rdfs:seeAlso \"1\" xsd:int\n\n";
    public static final String PROPERTY_VALUE_QUOTES =
        "Prefix(:=<http://purl.obolibrary.org/obo/test.owl#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n\n"
            + "Ontology(<http://purl.obolibrary.org/obo/test.owl>\n"
            + "Declaration(Class(<http://purl.obolibrary.org/obo/X_1>))\n"
            + "Declaration(Class(<http://purl.obolibrary.org/obo/X_2>))\n"
            + "AnnotationAssertion(<http://purl.obolibrary.org/obo/rdfs_seeAlso> <http://purl.obolibrary.org/obo/X_1> \"xx\"^^xsd:string)\n\n"
            + "AnnotationAssertion(<http://purl.obolibrary.org/obo/rdfs_seeAlso> <http://purl.obolibrary.org/obo/X_2> \"1\"^^xsd:int)\n\n"
            + ")";
    public static String objectSomeTemplate =
        "ObjectSomeValuesFrom(<http://purl.obolibrary.org/obo/BFO_0000051> ObjectIntersectionOf(<http://purl.obolibrary.org/obo/GO_0005886> ObjectSomeValuesFrom(<http://purl.obolibrary.org/obo/BFO_0000051> <http://purl.obolibrary.org/obo/TEST_%s>)))";
    public static final String COMMENT_IN_DATE_1 = "format-version: 1.2\n"
        + "data-version: beta2 ! WSIO Beta 2\n" + "date: 19:06:2014 18:57 ! CE(S)T";
    public static final String COMMENT_IN_DATE_2 = "format-version: 1.2\n"
        + "date: 19:06:2014 18:57 ! CE(S)T" + "data-version: beta2 ! WSIO Beta 2\n";
    public static final String ROUND_TRIP_ALL =
        "Prefix(:=<http://purl.obolibrary.org/obo/uni.obo#>)\n"
            + "Ontology(<http://purl.obolibrary.org/obo/uni.obo.owl>\n" + "Declaration(Class(:A))\n"
            + "Declaration(Class(:B))\n" + "Declaration(ObjectProperty(:part_of))\n"
            + "SubClassOf(:A ObjectAllValuesFrom(:part_of :B)))";
    public static final String ROUND_TRIP_VERSION_INFO_OBO = "format-version: 1.2\n"
        + "ontology: myont\n" + "property_value: owl:versionInfo \"2020-06-30\" xsd:string";
    public static final String ROUND_TRIP_VERSION_INFO =
        "Prefix(:=<http://purl.obolibrary.org/obo/myont.owl#>)\n"
            + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
            + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n\n"
            + "Ontology(<http://purl.obolibrary.org/obo/myont.owl>\n"
            + "Annotation(<http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion> \"1.2\")\n"
            + "Annotation(owl:versionInfo \"2020-06-30\")\n"
            + "Declaration(AnnotationProperty(<http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion>))\n"
            + "AnnotationAssertion(<http://www.w3.org/2000/01/rdf-schema#label> <http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion> \"has_obo_format_version\")\n)";
    public static final String DECLARED_ANNOTATIONS =
        "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "@prefix oio: <http://www.geneontology.org/formats/oboInOwl#> .\n"
            + "@prefix obo: <http://purl.obolibrary.org/obo/> .\n\n"
            + "# omitting this line loses the axiom annotation in roundtrip\n"
            + "oio:source rdf:type owl:AnnotationProperty .\n\n\n"
            + "rdfs:comment rdf:type owl:AnnotationProperty .\n\n\n" + "obo:MONDO_0009025\n"
            + "    a owl:Class ;\n" + "    oio:hasDbXref \"UMLS:C1415737\" .\n" + "\n"
            + "[ a owl:Axiom ;\n" + "    rdfs:comment \"Some comment\";\n"
            + "  oio:source \"MONDO:notFoundInSource\" ;\n"
            + "  owl:annotatedProperty oio:hasDbXref ;\n"
            + "  owl:annotatedSource obo:MONDO_0009025 ;\n"
            + "  owl:annotatedTarget \"UMLS:C1415737\"\n" + "] .";
    public static final String UNDECLARED_ANNOTATIONS =
        "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix oio: <http://www.geneontology.org/formats/oboInOwl#> .\n"
            + "@prefix obo: <http://purl.obolibrary.org/obo/> .\n\n" + "obo:MONDO_0009025\n"
            + "    a owl:Class ;\n" + "    oio:hasDbXref \"UMLS:C1415737\" .\n" + "\n"
            + "[ a owl:Axiom ;\n" + "    rdfs:comment \"Some comment\";\n"
            + "   oio:source \"MONDO:notFoundInSource\" ;\n"
            + "  owl:annotatedProperty oio:hasDbXref ;\n"
            + "  owl:annotatedSource obo:MONDO_0009025 ;\n"
            + "  owl:annotatedTarget \"UMLS:C1415737\"\n" + "] .";
    public static final String INPUT_VERSION_LOAD_CHANGE = "<?xml version=\"1.0\"?>\n"
        + "<rdf:RDF\n" + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
        + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
        + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
        + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
        + "  <rdf:Description rdf:about=\"http://test.example.org/ontology/0139\">\n"
        + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\" />\n"
        + "    <owl:versionIRI rdf:resource=\"http://test.example.org/ontology/0139/version:1\" />\n"
        + "  </rdf:Description>  \n" + "</rdf:RDF>";
    public static final String plainOnto =
        "Prefix(:=<http://www.example.org/#>)\nOntology(<http://example.org/>\nSubClassOf(:a :b) )";
    public static final String ContainsDatatypesInHeader =
        "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix owl:   <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "<http://xxx>    a             owl:Ontology ;  " + "rdfs:comment  \"the_comment\" . ";
    public static final String opClassPunning =
        "Prefix: : <http://x.org/>\n" + "Ontology: <http://x.org>\n" + "Individual: x\n"
            + "Class: creator_of\n" + "ObjectProperty: creator_of\n" + "Class: Test\n"
            + "  EquivalentClasses: creator_of value x, creator_of";
    public static final String manSyntaxParserRuleTest =
        "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
            + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
            + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
            + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "Ontology: <http://www.owl-ontologies.com/Ontology1307394066.owl>\n"
            + "Datatype: xsd:decimal\n Datatype: xsd:int\n Datatype: xsd:dateTime\n"
            + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>\n Characteristics: \n Functional\n Range: \n xsd:int\n"
            + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasDate>\n Range: \n xsd:dateTime\n"
            + "Class: <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
            + "Individual: <http://www.owl-ontologies.com/Ontology1307394066.owl#p1>\n Types: \n <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
            + "Rule: \n xsd:decimal(?<urn:swrl:var#x>), <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>(?<urn:swrl:var#p>, ?<urn:swrl:var#x>) -> <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>(?<urn:swrl:var#p>)";
    public static final String SaveBaseIRINotOntologyInTurtle = "@prefix : <urn:test:base#> .\n"
        + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
        + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
        + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
        + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
        + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + "@base <urn:test:base#> .\n"
        + "<http://example.com/ontology> rdf:type owl:Ontology .\n"
        + ":i rdf:type owl:NamedIndividual .";
    public static final String ParseQuotedTripleQuotesLitvalue =
        "Diadenosine 5',5'''-P1,P4-tetraphosphate phosphorylase";
    public static final String ParseQuotedTripleQuotesWorking =
        "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n "
            + "@prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n foaf:fundedBy "
            + "rdfs:label \"\"\"" + ParseQuotedTripleQuotesLitvalue + "\"\"\"@en .";
    public static final String SaveBaseIRINotOntologyInRioTurtle = "@base <urn:test:base#> .\n"
        + "@prefix : <urn:test:base#> .\n" + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
        + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
        + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
        + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
        + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
        + "<http://example.com/ontology> a owl:Ontology .\n" + ":i a owl:NamedIndividual .";
    public static final String BAD_TURTLE =
        "@prefix : <http://x.org#> .\n" + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
            + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
            + "@base <http://x.org#> .\n" + "<http://x.org> rdf:type owl:Ontology .\n"
            + "<http://x.org/myprop> rdf:type owl:AnnotationProperty .\n"
            + "<http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .";
    public static final String BAD_FUNCTIONAL = "Prefix(:=<http://x.org#>)\n"
        + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
        + "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\n"
        + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\n"
        + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
        + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\n" + "Ontology(<http://x.org>\n"
        + "Declaration(AnnotationProperty(<http://x.org/myprop>))\n"
        + "AnnotationAssertion(<http://x.org/myprop> <http://x.org/myobj> < https://example.org/bad-url>)\n)";
    public static final String BAD_RDFXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<rdf:RDF xmlns=\"http://x.org#\"\n" + "     xml:base=\"http://x.org\"\n"
        + "     xmlns:x=\"http://x.org/\"\n" + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
        + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
        + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
        + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
        + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
        + "    <owl:Ontology rdf:about=\"http://x.org\"/>\n"
        + "    <owl:AnnotationProperty rdf:about=\"http://x.org/myprop\"/>\n"
        + "    <rdf:Description rdf:about=\"http://x.org/myobj\">\n"
        + "        <x:myprop rdf:resource=\" https://example.org/bad-url\"/>\n"
        + "    </rdf:Description>\n" + "</rdf:RDF>";
    public static final String BAD_OWLXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<Ontology xmlns=\"http://www.w3.org/2002/07/owl#\"\n"
        + "     xml:base=\"http://x.org\"\n"
        + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
        + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
        + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
        + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
        + "     ontologyIRI=\"http://x.org\">\n"
        + "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n"
        + "    <Prefix name=\"rdf\" IRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n"
        + "    <Prefix name=\"xml\" IRI=\"http://www.w3.org/XML/1998/namespace\"/>\n"
        + "    <Prefix name=\"xsd\" IRI=\"http://www.w3.org/2001/XMLSchema#\"/>\n"
        + "    <Prefix name=\"rdfs\" IRI=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n"
        + "    <Declaration>\n" + "        <AnnotationProperty IRI=\"/myprop\"/>\n"
        + "    </Declaration>\n" + "    <AnnotationAssertion>\n"
        + "        <AnnotationProperty IRI=\"/myprop\"/>\n" + "        <IRI>/myobj</IRI>\n"
        + "        <IRI> https://example.org/bad-url</IRI>\n" + "    </AnnotationAssertion>\n"
        + "</Ontology>";
    public static final String BAD_JSON_LD = "[ {\n" + "  \"@id\" : \"http://x.org\",\n"
        + "  \"@type\" : [ \"http://www.w3.org/2002/07/owl#Ontology\" ]\n" + "}, {\n"
        + "  \"@id\" : \"http://x.org/myobj\",\n" + "  \"http://x.org/myprop\" : [ {\n"
        + "    \"@id\" : \" https://example.org/bad-url\"\n" + "  } ]\n" + "}, {\n"
        + "  \"@id\" : \"http://x.org/myprop\",\n"
        + "  \"@type\" : [ \"http://www.w3.org/2002/07/owl#AnnotationProperty\" ]\n" + "} ]";
    public static final String BAD_RIO_TURTLE = "@prefix : <http://x.org#> .\n"
        + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
        + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
        + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
        + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
        + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
        + "<http://x.org> a owl:Ontology .\n" + "<http://x.org/myprop> a owl:AnnotationProperty .\n"
        + "<http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .";
    public static final String BAD_RIO_RDF = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<rdf:RDF\n" + "    xmlns=\"http://x.org#\"\n"
        + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
        + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
        + "    xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
        + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
        + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
        + "<rdf:Description rdf:about=\"http://x.org\">\n"
        + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\"/>\n"
        + "</rdf:Description>\n" + "<rdf:Description rdf:about=\"http://x.org/myprop\">\n"
        + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#AnnotationProperty\"/>\n"
        + "</rdf:Description>\n" + "<rdf:Description rdf:about=\"http://x.org/myobj\">\n"
        + "    <myprop xmlns=\"http://x.org/\" rdf:resource=\" https://example.org/bad-url\"/>\n"
        + "</rdf:Description>\n" + "</rdf:RDF>";
    public static final String BAD_NTRIPLES =
        "<http://x.org> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Ontology> .\n"
            + "<http://x.org/myprop> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#AnnotationProperty> .\n"
            + "<http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .";
    public static final String BAD_RDFJSON = "{\n" + "  \"http://x.org\" : {\n"
        + "    \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" : [\n" + "      {\n"
        + "        \"value\" : \"http://www.w3.org/2002/07/owl#Ontology\",\n"
        + "        \"type\" : \"uri\"\n" + "      }\n" + "    ]\n" + "  },\n"
        + "  \"http://x.org/myobj\" : {\n" + "    \"http://x.org/myprop\" : [\n" + "      {\n"
        + "        \"value\" : \" https://example.org/bad-url\",\n" + "        \"type\" : \"uri\"\n"
        + "      }\n" + "    ]\n" + "  },\n" + "  \"http://x.org/myprop\" : {\n"
        + "    \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" : [\n" + "      {\n"
        + "        \"value\" : \"http://www.w3.org/2002/07/owl#AnnotationProperty\",\n"
        + "        \"type\" : \"uri\"\n" + "      }\n" + "    ]\n" + "  }\n" + "}";
    public static final String BAD_TRIG = "@prefix : <http://x.org#> .\n"
        + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
        + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
        + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
        + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
        + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + "\n" + "{\n"
        + "  <http://x.org> a owl:Ontology .\n"
        + "  <http://x.org/myprop> a owl:AnnotationProperty .\n"
        + "  <http://x.org/myobj> <http://x.org/myprop> < https://example.org/bad-url> .\n" + "}";
    public static final String BAD_MANCHESTER = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
        + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
        + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
        + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
        + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n" + "Prefix: : <http://x.org>\n"
        + "Ontology: <http://x.org>\n" + "AnnotationProperty: < https://example.org/bad-url>";


    public static final String ontologyIRI = string("ontologyIRI.rdf");
    public static final String ontFirst = string("ontFirst.txt");
    public static final String labelFirst = string("labelFirst.txt");
    public static final String nestedAnnotations = string("nestedAnnotations.txt");
    public static final String annotatedAnnotation = string("annotatedAnnotation.txt");
    public static final String anonOneOf = string("anonOneOf.txt");
    public static final String anonTurtle = string("anonTurtle.txt");
    public static final String opa = string("opa.txt");
    public static final String nop = string("nop.txt");
    public static final String ndp = string("ndp.txt");
    public static final String irreflexive = string("irreflexive.txt");
    public static final String inverseop = string("inverseop.txt");
    public static final String inversefp = string("inversefp.txt");
    public static final String functionalop = string("functionalop.txt");
    public static final String functionaldp = string("functionaldp.txt");
    public static final String eOp = string("eOp.txt");
    public static final String eDp = string("eDp.txt");
    public static final String ec = string("ec.txt");
    public static final String disjointu = string("disjointu.txt");
    public static final String disjointOp = string("disjointOp.txt");
    public static final String disjointDp = string("disjointDp.txt");
    public static final String disjointClasses = string("disjointClasses.txt");
    public static final String assDi = string("assDi.txt");
    public static final String decI = string("decI.txt");
    public static final String decAp = string("decAp.txt");
    public static final String decDt = string("decDt.txt");
    public static final String decDp = string("decDp.txt");
    public static final String decOp = string("decOp.txt");
    public static final String decC = string("decC.txt");
    public static final String dDef = string("dDef.txt");
    public static final String dRange = string("dRange.txt");
    public static final String dDom = string("dDom.txt");
    public static final String assDPlain = string("assDPlain.txt");
    public static final String assD = string("assD.txt");
    public static final String dRangeRestrict = string("dRangeRestrict.txt");
    public static final String dataNot = string("dataNot.txt");
    public static final String dataOneOf = string("dataOneOf.txt");
    public static final String assDEq = string("assDEq.txt");
    public static final String assDMax = string("assDMax.txt");
    public static final String assDMin = string("assDMin.txt");
    public static final String assDHas = string("assDHas.txt");
    public static final String assDAll = string("assDAll.txt");
    public static final String assDSome = string("assDSome.txt");
    public static final String OPAINV = string("OPAINV.txt");
    public static final String OPAINVJ = string("OPAINVJ.txt");
    public static final String ODOM = string("ODOM.txt");
    public static final String ORANGE = string("ORANGE.txt");
    public static final String CHAIN = string("CHAIN.txt");
    public static final String REF = string("REF.txt");
    public static final String SAME = string("SAME.txt");
    public static final String SUBANN = string("SUBANN.txt");
    public static final String SUBCLASS = string("SUBCLASS.txt");
    public static final String SUBDATA = string("SUBDATA.txt");
    public static final String SUBOBJECT = string("SUBOBJECT.txt");
    public static final String SYMM = string("SYMM.txt");
    public static final String TRANS = string("TRANS.txt");
    public static final String HASKEY = string("HASKEY.txt");
    public static final String ASSONEOF = string("ASSONEOF.txt");
    public static final String ASSHASSELF = string("ASSHASSELF.txt");
    public static final String ASSEQ = string("ASSEQ.txt");
    public static final String ASSMAX = string("ASSMAX.txt");
    public static final String ASSMIN = string("ASSMIN.txt");
    public static final String ASSHAS = string("ASSHAS.txt");
    public static final String ASSALL = string("ASSALL.txt");
    public static final String ASSSOME = string("ASSSOME.txt");
    public static final String ASSNOTANON = string("ASSNOTANON.txt");
    public static final String ASSNOT = string("ASSNOT.txt");
    public static final String DRANGEOR = string("DRANGEOR.txt");
    public static final String DRANGEAND = string("DRANGEAND.txt");
    public static final String ASSOR = string("ASSOR.txt");
    public static final String ASSAND = string("ASSAND.txt");
    public static final String ASS = string("ASS.txt");
    public static final String ANNRANGE = string("ANNRANGE.txt");
    public static final String ANNDOM = string("ANNDOM.txt");
    public static final String ASYMM = string("ASYMM.txt");
    public static final String ANN = string("ANN.txt");
    public static final String ONTO = string("ONTO.txt");
    public static final String BIGRULE = string("BIGRULE.txt");
    public static final String RULE = string("RULE.txt");
    public static final String C = string("C.txt");
    public static final String DTD = string("DTD.txt");
    public static final String DB = string("DB.txt");
    public static final String DT = string("DT.txt");
    public static final String DP = string("DP.txt");
    public static final String DPR = string("DPR.txt");
    public static final String DC = string("DC.txt");
    public static final String OP = string("OP.txt");
    public static final String DOP = string("DOP.txt");
    public static final String ANNSHORT = string("ANNSHORT.txt");
    public static final String ANNI = string("ANNI.txt");
    public static final String DANN = string("DANN.txt");
    public static final String DDP = string("DDP.txt");
    public static final String DD = string("DD.txt");
    public static final String FDP = string("FDP.txt");
    public static final String FOP = string("FOP.txt");
    public static final String IFP = string("IFP.txt");
    public static final String IOP = string("IOP.txt");
    public static final String DIND = string("DIND.txt");
    public static final String DIFF = string("DIFF.txt");
    public static final String DSJC = string("DSJC.txt");
    public static final String I = string("I.txt");
    public static final String DISJDP = string("DISJDP.txt");
    public static final String DSJOP = string("DSJOP.txt");
    public static final String plain = string("plain.txt");
    public static final String adp = string("adp.txt");
    public static final String dpdomain = string("dpdomain.txt");
    public static final String VAR1 = string("VAR1.txt");
    public static final String DRA = string("DRA.txt");
    public static final String v1 = string("v1.txt");
    public static final String v2 = string("v2.txt");
    public static final String var2 = string("var2.txt");
    public static final String diffvar2 = string("diffvar2.txt");
    public static final String opavar2 = string("opavar2.txt");
    public static final String dpvar2 = string("dpvar2.txt");
    public static final String classvar2 = string("classvar2.txt");
    public static final String dlsaferule = string("dlsaferule.txt");
    public static final String T = string("T.txt");
    public static final String AANN = string("AANN.txt");
    public static final String noDC = string("noDC.txt");
    public static final String lazyDC = string("lazyDC.txt");
    public static final String doubleOntology = string("doubleOntology.txt");
    public static final String asymmetric = string("asymmetric.txt");
    public static final String APD = string("APD.txt");
    public static final String APR = string("APR.txt");
    public static final String ACL = string("ACL.txt");
    public static final String ACLAND = string("ACLAND.txt");
    public static final String AND = string("AND.txt");
    public static final String ACLOR = string("ACLOR.txt");
    public static final String OR = string("OR.txt");
    public static final String DPRAND = string("DPRAND.txt");
    public static final String DONEOF = string("DONEOF.txt");
    public static final String DAND = string("DAND.txt");
    public static final String DOR = string("DOR.txt");
    public static final String DPROR = string("DPROR.txt");
    public static final String CNOT = string("CNOT.txt");
    public static final String NOT = string("NOT.txt");
    public static final String ACNOT = string("ACNOT.txt");
    public static final String ACSOME = string("ACSOME.txt");
    public static final String SOME = string("SOME.txt");
    public static final String ACALL = string("ACALL.txt");
    public static final String ALL = string("ALL.txt");
    public static final String ACHAS = string("ACHAS.txt");
    public static final String HAS = string("HAS.txt");
    public static final String AOMIN = string("AOMIN.txt");
    public static final String OMIN = string("OMIN.txt");
    public static final String AOMAX = string("AOMAX.txt");
    public static final String MAX = string("MAX.txt");
    public static final String AOEQ = string("AOEQ.txt");
    public static final String OEQ = string("OEQ.txt");
    public static final String ASELF = string("ASELF.txt");
    public static final String SELF = string("SELF.txt");
    public static final String AONE = string("AONE.txt");
    public static final String ONE = string("ONE.txt");
    public static final String ADSOME = string("ADSOME.txt");
    public static final String DSOME = string("DSOME.txt");
    public static final String ADALL = string("ADALL.txt");
    public static final String DALL = string("DALL.txt");
    public static final String ADHAS = string("ADHAS.txt");
    public static final String DHAS = string("DHAS.txt");
    public static final String ADMIN = string("ADMIN.txt");
    public static final String DMIN = string("DMIN.txt");
    public static final String ADMAX = string("ADMAX.txt");
    public static final String DMAX = string("DMAX.txt");
    public static final String ADEQ = string("ADEQ.txt");
    public static final String DEQ = string("DEQ.txt");
    public static final String ADONEOF = string("ADONEOF.txt");
    public static final String DPRNOT = string("DPRNOT.txt");
    public static final String DNOT = string("DNOT.txt");
    public static final String MINMAX = string("MINMAX.txt");
    public static final String MINMXSIX = string("MINMXSIX.txt");
    public static final String MIN5 = string("MIN5.txt");
    public static final String MAXSIX = string("MAXSIX.txt");
    public static final String dpafalse = string("dpafalse.txt");
    public static final String EQC = string("EQC.txt");
    public static final String EQDP = string("EQDP.txt");
    public static final String EQOP = string("EQOP.txt");
    public static final String IRR = string("IRR.txt");
    public static final String BLN = string("BLN.txt");
    public static final String DU = string("DU.txt");
    public static final String ANDP = string("ANDP.txt");
    public static final String ANOP = string("ANOP.txt");
    public static final String AOP = string("AOP.txt");
    public static final String INVERSE = string("INVERSE.txt");
    public static final String AOINV = string("AOINV.txt");
    public static final String AOPJ = string("AOPJ.txt");
    public static final String OPD = string("OPD.txt");
    public static final String OPR = string("OPR.txt");
    public static final String SUBO = string("SUBO.txt");
    public static final String R = string("R.txt");
    public static final String SUBA = string("SUBA.txt");
    public static final String SUBC = string("SUBC.txt");
    public static final String SUBD = string("SUBD.txt");
    public static final String SUBOP = string("SUBOP.txt");
    public static final String v34 = string("v34.txt");
    public static final String v3 = string("v3.txt");
    public static final String var236 = string("var236.txt");
    public static final String SHORTRULE = string("SHORTRULE.txt");
    public static final String BFO = string("BFO.txt");
    public static final String declareDatatypes = string("declareDatatypes.txt");
    public static final String original = string("original.txt");
    public static final String HEAD = string("HEAD.txt");
    public static final String TAIL = string("TAIL.txt");
    public static final String DL_RULE = string("DL_RULE.txt");
    public static final String STRING9 = string("STRING9.txt");
    public static final String STRING8 = string("STRING8.txt");
    public static final String STRING7 = string("STRING7.txt");
    public static final String STRING6 = string("STRING6.txt");
    public static final String STRING5 = string("STRING5.txt");
    public static final String STRING4 = string("STRING4.txt");
    public static final String STRING3 = string("STRING3.txt");
    public static final String STRING2 = string("STRING2.txt");
    public static final String STRING1 = string("STRING1.txt");
    public static final String BROKEN = string("BROKEN.txt");
    public static final String FIXED = string("FIXED.txt");
    public static final String conditionalId = string("conditionalId.txt");
    public static final String unconditionalId = string("unconditionalId.txt");
    public static final String noRemapOnRead = string("noRemapOnRead.txt");
    public static final String oneAnonIndividuall = string("oneAnonIndividuall.txt");
    public static final String remapOnReading = string("remapOnReading.txt");
    public static final String oneIndividual = string("oneIndividual.txt");
    public static final String remapImportRdfXml = string("remapImportRdfXml.txt");
    public static final String remapImport = string("remapImport.txt");
    public static final String unloadableImport = string("unloadableImport.txt");
    public static final String loadRight = string("loadRight.txt");
    public static final String strtransdRange = string("strtransdRange.txt");
    public static final String strtransdDef = string("strtransdDef.txt");
    public static final String strtransdecC = string("strtransdecC.txt");
    public static final String strtransdecOp = string("strtransdecOp.txt");
    public static final String strtransdecDp = string("strtransdecDp.txt");
    public static final String strtransdecDt = string("strtransdecDt.txt");
    public static final String strtransdecAp = string("strtransdecAp.txt");
    public static final String strtransdecI = string("strtransdecI.txt");
    public static final String strtransdDp = string("strtransdDp.txt");
    public static final String strtransdOp = string("strtransdOp.txt");
    public static final String strtranseDp = string("strtranseDp.txt");
    public static final String strtranseOp = string("strtranseOp.txt");
    public static final String strtransfdp = string("strtransfdp.txt");
    public static final String strtransfop = string("strtransfop.txt");
    public static final String strtransifp = string("strtransifp.txt");
    public static final String strtransiop = string("strtransiop.txt");
    public static final String strtransirr = string("strtransirr.txt");
    public static final String strtransopa = string("strtransopa.txt");
    public static final String strtransopaInv = string("strtransopaInv.txt");
    public static final String strtransopaInvj = string("strtransopaInvj.txt");
    public static final String strtransoDom = string("strtransoDom.txt");
    public static final String strtransoRange = string("strtransoRange.txt");
    public static final String strtranschain = string("strtranschain.txt");
    public static final String strtransref = string("strtransref.txt");
    public static final String strtranssubAnn = string("strtranssubAnn.txt");
    public static final String strtranssubClass = string("strtranssubClass.txt");
    public static final String strtranssubData = string("strtranssubData.txt");
    public static final String strtranssubObject = string("strtranssubObject.txt");
    public static final String strtransrule = string("strtransrule.txt");
    public static final String strtranssymm = string("strtranssymm.txt");
    public static final String strtranstrans = string("strtranstrans.txt");
    public static final String strtranshasKey = string("strtranshasKey.txt");
    public static final String strtransbigRule = string("strtransbigRule.txt");
    public static final String strtransann = string("strtransann.txt");
    public static final String strtransasymm = string("strtransasymm.txt");
    public static final String strtransannDom = string("strtransannDom.txt");
    public static final String strtransannRange = string("strtransannRange.txt");
    public static final String strtransdRangeAnd = string("strtransdRangeAnd.txt");
    public static final String strtransdRangeOr = string("strtransdRangeOr.txt");
    public static final String strtransdOneOf = string("strtransdOneOf.txt");
    public static final String strtransdNot = string("strtransdNot.txt");
    public static final String strtransdRangeRestrict = string("strtransdRangeRestrict.txt");
    public static final String strtransassD = string("strtransassD.txt");
    public static final String strtransassDPlain = string("strtransassDPlain.txt");
    public static final String strtransdDom = string("strtransdDom.txt");
    public static final String strtransdc = string("strtransdc.txt");
    public static final String strtransdu = string("strtransdu.txt");
    public static final String strtransec = string("strtransec.txt");
    public static final String RDFXML = string("RDFXML.txt");
    public static final String OWLXML = string("OWLXML.txt");
    public static final String FUNCTIONAL = string("FUNCTIONAL.txt");
    public static final String MANCHESTER = string("MANCHESTER.txt");
    public static final String TURTLE = string("TURTLE.txt");
    public static final String[] inputSorting = strings("inputSorting.txt");
    public static final String quotes1 = string("quotes1.txt");
    public static final String quotes6 = string("quotes6.txt");
    public static final String quotes2 = string("quotes2.txt");
    public static final String quotes3 = string("quotes3.txt");
    public static final String quotes4 = string("quotes4.txt");
    public static final String quotes5 = string("quotes5.txt");
    public static final String workingOnto = string("workingOnto.txt");
    public static final String brokenOnto = string("brokenOnto.txt");
    public static final String resolveAgainstBase = string("resolveAgainstBase.txt");
    public static final String turtleWithShared = string("turtleWithShared.txt");
    public static final String scientificNotationPlus = string("scientificNotationPlus.txt");
    public static final String scientificNotationWithMinus =
        string("scientificNotationWithMinus.txt");
    public static final String scientificNotation = string("scientificNotation.txt");
    public static final String parseTwo = string("parseTwo.txt");
    public static final String parseOne = string("parseOne.txt");
    public static final String emptySpaceInBnode = string("emptySpaceInBnode.txt");
    public static final String axiomAnnotations = string("axiomAnnotations.txt");
    public static final String slashOntologyIRI = string("slashOntologyIRI.txt");
    public static final String presentDeclaration = string("presentDeclaration.txt");
    public static final String missingDeclaration = string("missingDeclaration.txt");
    public static final String prefixAbbreviations = string("prefixAbbreviations.txt");
    public static final String axiomsForBlankNodes = string("axiomsForBlankNodes.txt");
    public static final String correctPrefix = string("correctPrefix.txt");
    public static final String slashPrefix = string("slashPrefix.txt");
    public static final String nnfdRange = string("nnfdRange.txt");
    public static final String nnfdDef = string("nnfdDef.txt");
    public static final String nnfdecC = string("nnfdecC.txt");
    public static final String nnfdecOp = string("nnfdecOp.txt");
    public static final String nnfdecDp = string("nnfdecDp.txt");
    public static final String nnfdecDt = string("nnfdecDt.txt");
    public static final String nnfdecAp = string("nnfdecAp.txt");
    public static final String nnfdecI = string("nnfdecI.txt");
    public static final String nnfassDi = string("nnfassDi.txt");
    public static final String nnfdc = string("nnfdc.txt");
    public static final String nnfdDp = string("nnfdDp.txt");
    public static final String nnfdOp = string("nnfdOp.txt");
    public static final String nnfdu = string("nnfdu.txt");
    public static final String nnfec = string("nnfec.txt");
    public static final String nnfeDp = string("nnfeDp.txt");
    public static final String nnfeOp = string("nnfeOp.txt");
    public static final String nnffdp = string("nnffdp.txt");
    public static final String nnffop = string("nnffop.txt");
    public static final String nnfifp = string("nnfifp.txt");
    public static final String nnfiop = string("nnfiop.txt");
    public static final String nnfirr = string("nnfirr.txt");
    public static final String nnfndp = string("nnfndp.txt");
    public static final String nnfnop = string("nnfnop.txt");
    public static final String nnfopa = string("nnfopa.txt");
    public static final String nnfopaInv = string("nnfopaInv.txt");
    public static final String nnfopaInvj = string("nnfopaInvj.txt");
    public static final String nnfoDom = string("nnfoDom.txt");
    public static final String nnfoRange = string("nnfoRange.txt");
    public static final String nnfchain = string("nnfchain.txt");
    public static final String nnfref = string("nnfref.txt");
    public static final String nnfsame = string("nnfsame.txt");
    public static final String nnfsubAnn = string("nnfsubAnn.txt");
    public static final String nnfsubClass = string("nnfsubClass.txt");
    public static final String nnfsubData = string("nnfsubData.txt");
    public static final String nnfsubObject = string("nnfsubObject.txt");
    public static final String nnfrule = string("nnfrule.txt");
    public static final String nnfsymm = string("nnfsymm.txt");
    public static final String nnftrans = string("nnftrans.txt");
    public static final String nnfhasKey = string("nnfhasKey.txt");
    public static final String nnfbigRule = string("nnfbigRule.txt");
    public static final String nnfann = string("nnfann.txt");
    public static final String nnfasymm = string("nnfasymm.txt");
    public static final String nnfannDom = string("nnfannDom.txt");
    public static final String nnfannRange = string("nnfannRange.txt");
    public static final String nnfass = string("nnfass.txt");
    public static final String nnfassAnd = string("nnfassAnd.txt");
    public static final String nnfassOr = string("nnfassOr.txt");
    public static final String nnfdRangeAnd = string("nnfdRangeAnd.txt");
    public static final String nnfdRangeOr = string("nnfdRangeOr.txt");
    public static final String nnfassNot = string("nnfassNot.txt");
    public static final String nnfassNotAnon = string("nnfassNotAnon.txt");
    public static final String nnfassSome = string("nnfassSome.txt");
    public static final String nnfassAll = string("nnfassAll.txt");
    public static final String nnfassHas = string("nnfassHas.txt");
    public static final String nnfassMin = string("nnfassMin.txt");
    public static final String nnfassMax = string("nnfassMax.txt");
    public static final String nnfassEq = string("nnfassEq.txt");
    public static final String nnfassHasSelf = string("nnfassHasSelf.txt");
    public static final String nnfassOneOf = string("nnfassOneOf.txt");
    public static final String nnfassDSome = string("nnfassDSome.txt");
    public static final String nnfassDAll = string("nnfassDAll.txt");
    public static final String nnfassDHas = string("nnfassDHas.txt");
    public static final String nnfassDMin = string("nnfassDMin.txt");
    public static final String nnfassDMax = string("nnfassDMax.txt");
    public static final String nnfassDEq = string("nnfassDEq.txt");
    public static final String nnfdOneOf = string("nnfdOneOf.txt");
    public static final String nnfdNot = string("nnfdNot.txt");
    public static final String nnfdRangeRestrict = string("nnfdRangeRestrict.txt");
    public static final String nnfassD = string("nnfassD.txt");
    public static final String nnfassDPlain = string("nnfassDPlain.txt");
    public static final String nnfdDom = string("nnfdDom.txt");
    public static final String KOALA = string("KOALA.txt");
    public static final String allowInstanceStanza = string("allowInstanceStanza.txt");
    public static final String forbiddenInput1 = string("forbiddenInput1.txt");
    public static final String forbiddenInput2 = string("forbiddenInput2.txt");
    public static final String ontology = string("ontology.txt");
    public static final String galenFragment = string("galenFragment.txt");
    public static final String violation = string("violation.txt");
    public static final String[] profileFullTestCases = strings("profileFullTestCases.txt");
    public static final String[] profileDLTestCases = strings("profileDLTestCases.txt");
    public static final String[] profileAllTestCases = strings("profileAllTestCases.txt");
    public static final String[] profileELDLTestCases = strings("profileELDLTestCases.txt");
    public static final String[] profileRLDLTestCases = strings("profileRLDLTestCases.txt");
    public static final String[] profileQLDLTestCases = strings("profileQLDLTestCases.txt");
    public static final String[] profileNOQLTestCases = strings("profileNOQLTestCases.txt");
    public static final String[] profileNORLTestCases = strings("profileNORLTestCases.txt");
    public static final String[] profileNOELTestCases = strings("profileNOELTestCases.txt");
    public static final String disjoint4InHTML = string("disjoint4InHTML.txt");
    public static final String disjointTwoInHTML = string("disjointTwoInHTML.txt");
    public static final String disjointInHTML = string("disjointInHTML.txt");
    public static final String renameApUsages = string("renameApUsages.txt");
    public static final String distinctMembers1 = string("distinctMembers1.txt");
    public static final String distinctMembers2 = string("distinctMembers2.txt");
    public static final String IRITestCase = string("IRITestCase.txt");
    public static final String parseSWRL = string("parseSWRL.txt");
    public static final String parseSWRL2 = string("parseSWRL2.txt");
    public static final String individualSWRLTest = string("individualSWRLTest.txt");
    public static final String parseDataProperty = string("parseDataProperty.txt");
    public static final String subPropertiesAsObjectProperties =
        string("subPropertiesAsObjectProperties.txt");
    public static String lhsSubsetofRhs = string("lhsSubsetofRhs.txt");
    public static String swrlParser = string("swrlParser.txt");
    public static final String cardMultipleDigits = string("cardMultipleDigits.txt");
    public static final String parseMultilineComment = string("parseMultilineComment.txt");
    public static final String parseComment = string("parseComment.txt");
    public static final String annotationTestCase = string("annotationTestCase.txt");
    public static final String roundtripTest = string("roundtripTest.txt");
    public static final String manSyntaxInput = string("manSyntaxInput.txt");
    public static final String manSyntaxRule = string("manSyntaxRule.txt");
    public static final String manSyntaxParserTest = string("manSyntaxParserTest.txt");
    public static final String annotatedAnnotationMansyntax =
        string("annotatedAnnotationMansyntax.txt");
    public static final String parseSWRLVariable = string("parseSWRLVariable.txt");
    public static final String error1OnStrictParsing = string("error1OnStrictParsing.txt");
    public static final String undeclaredAnnotationPropertyTurtle =
        string("undeclaredAnnotationPropertyTurtle.txt");
    public static final String undeclaredAnnotationProperty =
        string("undeclaredAnnotationProperty.txt");
    public static final String largeInteger = string("largeInteger.txt");
    public static final String minusInf = string("minusInf.txt");
    public static final String webOnt = string("webOnt.txt");
    public static final String correct = "rdf:parseType=\"Literal\"";
    public static final String preamble = string("preamble.txt");
    public static final String closure = string("closure.txt");
    public static final String literalMalformedXML = string("literalMalformedXML.txt");
    public static final String literalXMl = string("literalXMl.txt");
    public static final String roundtripPaddedLiterals = string("roundtripPaddedLiterals.txt");
    public static final String missingTypes = string("missingTypes.txt");
    public static final String loadOboAnonymous = string("loadOboAnonymous.txt");
    public static final String inputManSyntax = string("inputManSyntax.txt");
    public static final String failWhenTrixBanned = string("failWhenTrixBanned.txt");
    public static final String roundtripUTF8String = string("roundtripUTF8String.txt");
    public static final String INVALID_UTF8 = string("INVALID_UTF8.txt");
    public static final String latexExpectedOutput = string("latexExpectedOutput.txt");
    public static final String latexWithInverse = string("latexWithInverse.txt");
    public static final String latexWithUnderscores = string("latexWithUnderscores.txt");
    public static final String doNotIncludeExternalEntities =
        string("doNotIncludeExternalEntities.txt");
    public static final String roundtripEntities = string("roundtripEntities.txt");
    public static final String convertToFunctional = string("convertToFunctional.txt");
    public static final String roundtripRIWithQuery = string("roundtripRIWithQuery.txt");
    public static final String moveTest = string("moveTest.txt");
    public static final String parsePlainLiteral = string("parsePlainLiteral.txt");
    public static final String rdfContentForException = string("rdfContentForException.txt");
    public static final String parseDisjointClasses = string("parseDisjointClasses.txt");
    public static final String ontologyVersionParse = string("ontologyVersionParse.txt");
    public static final String uniquePropertiesTurtle = string("uniquePropertiesTurtle.txt");
    public static final String equivalentAndSubclasses = string("equivalentAndSubclasses.txt");
    public static final String premiseOntology = string("premiseOntology.txt");
}
