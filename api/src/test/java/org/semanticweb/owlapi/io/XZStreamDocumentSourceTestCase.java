package org.semanticweb.owlapi.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;

/**
 * Created by ses on 3/12/15.
 */
@SuppressWarnings("javadoc")
public class XZStreamDocumentSourceTestCase {

    @SuppressWarnings("null")
    @Test
    public void testReadKoalaDoc() {
        XZStreamDocumentSource source =
            new XZStreamDocumentSource(getClass().getResourceAsStream("/koala.owl.xz"));
        String input = "<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF\n    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n    xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\"\n  xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n"
            + "  <owl:Ontology rdf:about=\"\"/>\n"
            + "  <owl:Class rdf:ID=\"Female\">\n    <owl:equivalentClass>\n      <owl:Restriction>\n        <owl:onProperty>\n          <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n        </owl:onProperty>\n        <owl:hasValue>\n          <Gender rdf:ID=\"female\"/>\n        </owl:hasValue>\n      </owl:Restriction>\n    </owl:equivalentClass>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Marsupials\">\n    <owl:disjointWith>\n      <owl:Class rdf:about=\"#Person\"/>\n    </owl:disjointWith>\n    <rdfs:subClassOf>\n      <owl:Class rdf:about=\"#Animal\"/>\n    </rdfs:subClassOf>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Student\">\n    <owl:equivalentClass>\n      <owl:Class>\n        <owl:intersectionOf rdf:parseType=\"Collection\">\n          <owl:Class rdf:about=\"#Person\"/>\n          <owl:Restriction>\n            <owl:onProperty>\n              <owl:FunctionalProperty rdf:about=\"#isHardWorking\"/>\n            </owl:onProperty>\n            <owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n            >true</owl:hasValue>\n          </owl:Restriction>\n          <owl:Restriction>\n            <owl:someValuesFrom>\n              <owl:Class rdf:about=\"#University\"/>\n            </owl:someValuesFrom>\n            <owl:onProperty>\n              <owl:ObjectProperty rdf:about=\"#hasHabitat\"/>\n            </owl:onProperty>\n          </owl:Restriction>\n        </owl:intersectionOf>\n      </owl:Class>\n    </owl:equivalentClass>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"KoalaWithPhD\">\n    <owl:versionInfo>1.2</owl:versionInfo>\n    <owl:equivalentClass>\n      <owl:Class>\n        <owl:intersectionOf rdf:parseType=\"Collection\">\n          <owl:Restriction>\n            <owl:hasValue>\n              <Degree rdf:ID=\"PhD\"/>\n            </owl:hasValue>\n            <owl:onProperty>\n              <owl:ObjectProperty rdf:about=\"#hasDegree\"/>\n            </owl:onProperty>\n          </owl:Restriction>\n          <owl:Class rdf:about=\"#Koala\"/>\n        </owl:intersectionOf>\n      </owl:Class>\n    </owl:equivalentClass>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"University\">\n    <rdfs:subClassOf>\n      <owl:Class rdf:ID=\"Habitat\"/>\n    </rdfs:subClassOf>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Koala\">\n    <rdfs:subClassOf>\n      <owl:Restriction>\n        <owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n        >false</owl:hasValue>\n        <owl:onProperty>\n          <owl:FunctionalProperty rdf:about=\"#isHardWorking\"/>\n        </owl:onProperty>\n      </owl:Restriction>\n    </rdfs:subClassOf>\n    <rdfs:subClassOf>\n      <owl:Restriction>\n        <owl:someValuesFrom>\n          <owl:Class rdf:about=\"#DryEucalyptForest\"/>\n        </owl:someValuesFrom>\n        <owl:onProperty>\n          <owl:ObjectProperty rdf:about=\"#hasHabitat\"/>\n        </owl:onProperty>\n      </owl:Restriction>\n    </rdfs:subClassOf>\n    <rdfs:subClassOf rdf:resource=\"#Marsupials\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Animal\">\n    <rdfs:seeAlso>Male</rdfs:seeAlso>\n    <rdfs:subClassOf>\n      <owl:Restriction>\n        <owl:onProperty>\n          <owl:ObjectProperty rdf:about=\"#hasHabitat\"/>\n        </owl:onProperty>\n        <owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n        >1</owl:minCardinality>\n      </owl:Restriction>\n    </rdfs:subClassOf>\n    <rdfs:subClassOf>\n      <owl:Restriction>\n        <owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n        >1</owl:cardinality>\n        <owl:onProperty>\n          <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n        </owl:onProperty>\n      </owl:Restriction>\n    </rdfs:subClassOf>\n    <owl:versionInfo>1.1</owl:versionInfo>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Forest\">\n    <rdfs:subClassOf rdf:resource=\"#Habitat\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Rainforest\">\n    <rdfs:subClassOf rdf:resource=\"#Forest\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"GraduateStudent\">\n    <rdfs:subClassOf>\n      <owl:Restriction>\n        <owl:onProperty>\n          <owl:ObjectProperty rdf:about=\"#hasDegree\"/>\n        </owl:onProperty>\n        <owl:someValuesFrom>\n          <owl:Class>\n            <owl:oneOf rdf:parseType=\"Collection\">\n              <Degree rdf:ID=\"BA\"/>\n              <Degree rdf:ID=\"BS\"/>\n            </owl:oneOf>\n          </owl:Class>\n        </owl:someValuesFrom>\n      </owl:Restriction>\n    </rdfs:subClassOf>\n    <rdfs:subClassOf rdf:resource=\"#Student\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Parent\">\n    <owl:equivalentClass>\n      <owl:Class>\n        <owl:intersectionOf rdf:parseType=\"Collection\">\n          <owl:Class rdf:about=\"#Animal\"/>\n          <owl:Restriction>\n            <owl:onProperty>\n              <owl:ObjectProperty rdf:about=\"#hasChildren\"/>\n            </owl:onProperty>\n            <owl:minCardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n            >1</owl:minCardinality>\n          </owl:Restriction>\n        </owl:intersectionOf>\n      </owl:Class>\n    </owl:equivalentClass>\n    <rdfs:subClassOf rdf:resource=\"#Animal\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"DryEucalyptForest\">\n    <rdfs:subClassOf rdf:resource=\"#Forest\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Quokka\">\n    <rdfs:subClassOf>\n      <owl:Restriction>\n        <owl:hasValue rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n        >true</owl:hasValue>\n        <owl:onProperty>\n          <owl:FunctionalProperty rdf:about=\"#isHardWorking\"/>\n        </owl:onProperty>\n      </owl:Restriction>\n    </rdfs:subClassOf>\n    <rdfs:subClassOf rdf:resource=\"#Marsupials\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"TasmanianDevil\">\n    <rdfs:subClassOf rdf:resource=\"#Marsupials\"/>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"MaleStudentWith3Daughters\">\n    <owl:equivalentClass>\n      <owl:Class>\n        <owl:intersectionOf rdf:parseType=\"Collection\">\n          <owl:Class rdf:about=\"#Student\"/>\n          <owl:Restriction>\n            <owl:onProperty>\n              <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n            </owl:onProperty>\n            <owl:hasValue>\n              <Gender rdf:ID=\"male\"/>\n            </owl:hasValue>\n          </owl:Restriction>\n          <owl:Restriction>\n            <owl:onProperty>\n              <owl:ObjectProperty rdf:about=\"#hasChildren\"/>\n            </owl:onProperty>\n            <owl:cardinality rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\"\n            >3</owl:cardinality>\n          </owl:Restriction>\n          <owl:Restriction>\n            <owl:allValuesFrom rdf:resource=\"#Female\"/>\n            <owl:onProperty>\n              <owl:ObjectProperty rdf:about=\"#hasChildren\"/>\n            </owl:onProperty>\n          </owl:Restriction>\n        </owl:intersectionOf>\n      </owl:Class>\n    </owl:equivalentClass>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Degree\"/>\n"
            + "  <owl:Class rdf:ID=\"Male\">\n    <owl:equivalentClass>\n      <owl:Restriction>\n        <owl:hasValue rdf:resource=\"#male\"/>\n        <owl:onProperty>\n          <owl:FunctionalProperty rdf:about=\"#hasGender\"/>\n        </owl:onProperty>\n      </owl:Restriction>\n    </owl:equivalentClass>\n  </owl:Class>\n"
            + "  <owl:Class rdf:ID=\"Gender\"/>\n"
            + "  <owl:Class rdf:ID=\"Person\">\n    <rdfs:subClassOf rdf:resource=\"#Animal\"/>\n    <owl:disjointWith rdf:resource=\"#Marsupials\"/>\n  </owl:Class>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasHabitat\">\n    <rdfs:range rdf:resource=\"#Habitat\"/>\n    <rdfs:domain rdf:resource=\"#Animal\"/>\n  </owl:ObjectProperty>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasDegree\">\n    <rdfs:domain rdf:resource=\"#Person\"/>\n    <rdfs:range rdf:resource=\"#Degree\"/>\n  </owl:ObjectProperty>\n"
            + "  <owl:ObjectProperty rdf:ID=\"hasChildren\">\n    <rdfs:range rdf:resource=\"#Animal\"/>\n    <rdfs:domain rdf:resource=\"#Animal\"/>\n  </owl:ObjectProperty>\n"
            + "  <owl:FunctionalProperty rdf:ID=\"hasGender\">\n    <rdfs:range rdf:resource=\"#Gender\"/>\n    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/>\n    <rdfs:domain rdf:resource=\"#Animal\"/>\n  </owl:FunctionalProperty>\n"
            + "  <owl:FunctionalProperty rdf:ID=\"isHardWorking\">\n    <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/>\n    <rdfs:domain rdf:resource=\"#Person\"/>\n    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/>\n  </owl:FunctionalProperty>\n"
            + "  <Degree rdf:ID=\"MA\"/>\n</rdf:RDF>\n\n"
            + "<!-- Created with Protege (with OWL Plugin, Build 60)  http://protege.stanford.edu -->\n";
        StringWriter w = new StringWriter();
        OWLParser mockParser = new OWLParser() {

            @Override
            public OWLDocumentFormat parse(Reader r, OWLParserParameters p) {
                try {
                    IOUtils.copy(r, w);
                } catch (IOException e) {
                    e.printStackTrace();
                    fail(e.getMessage());
                }
                return null;
            }

            @Override
            public OWLDocumentFormatFactory getSupportedFormat() {
                return new RDFXMLDocumentFormatFactory();
            }
        };
        source.acceptParser(mockParser, null, null);
        assertEquals(input, w.toString());
    }
}
