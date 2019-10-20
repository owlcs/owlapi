package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Created by vincent on 20.08.15.
 */
@SuppressWarnings("javadoc")
public class DeclareAnnotatedEntitiesTestCase extends TestBase {

    @Test
    public void shouldDeclareAllDatatypes() throws Exception {
        String input = "<?xml version=\"1.0\"?>\n" + "<!DOCTYPE Ontology [\n"
            + "    <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n    <!ENTITY xml \"http://www.w3.org/XML/1998/namespace\" >\n    <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n    <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n]>\n"
            + "<Ontology xmlns=\"http://www.w3.org/2002/07/owl#\"\n"
            + "     xml:base=\"http://www.semanticweb.org/owlapi-datatypes\"\n"
            + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n"
            + "     ontologyIRI=\"http://www.semanticweb.org/owlapi-datatypes\">\n"
            + "    <Prefix name=\"rdf\" IRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n"
            + "    <Prefix name=\"rdfs\" IRI=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n"
            + "    <Prefix name=\"xsd\" IRI=\"http://www.w3.org/2001/XMLSchema#\"/>\n"
            + "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n"
            + "    <Declaration>\n        <Datatype IRI=\"#myDatatype\"/>\n    </Declaration>\n"
            + "    <Declaration>\n        <Datatype IRI=\"#myDatatype2\"/>\n    </Declaration>\n"
            + "    <AnnotationAssertion>\n"
            + "        <AnnotationProperty abbreviatedIRI=\"rdfs:comment\"/>\n"
            + "        <IRI>#myDatatype2</IRI>\n"
            + "        <Literal datatypeIRI=\"&rdf;PlainLiteral\">myDatatype2 has a comment. It causes the all serializers except the OWLXML serializer to omit the declaration of myDatatype2 when saving a new ontology created from the annotation axioms. Interesting is that they do not omit the declaration of myDatatype that has no comment.</Literal>\n"
            + "    </AnnotationAssertion>\n" + "    <AnnotationAssertion>\n"
            + "        <AnnotationProperty abbreviatedIRI=\"rdfs:comment\"/>\n"
            + "        <AbbreviatedIRI>owl:Thing</AbbreviatedIRI>\n"
            + "        <Literal datatypeIRI=\"http://www.semanticweb.org/owlapi-datatypes#myDatatype\">comment with datatype myDatatype</Literal>\n"
            + "    </AnnotationAssertion>\n" + "    <AnnotationAssertion>\n"
            + "        <AnnotationProperty abbreviatedIRI=\"rdfs:comment\"/>\n"
            + "        <AbbreviatedIRI>owl:Thing</AbbreviatedIRI>\n"
            + "        <Literal datatypeIRI=\"http://www.semanticweb.org/owlapi-datatypes#myDatatype2\">comment with datatype myDatatype2</Literal>\n"
            + "    </AnnotationAssertion>\n</Ontology>";
        OWLOntology ontology = loadOntologyFromString(input);
        Set<OWLDeclarationAxiom> declarations = asSet(ontology.axioms(AxiomType.DECLARATION));
        Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms =
            asSet(ontology.axioms(AxiomType.ANNOTATION_ASSERTION));
        OWLOntology ontology2 = m1.createOntology();
        ontology2.add(annotationAssertionAxioms);
        OWLOntology o3 = roundTrip(ontology2, new RDFXMLDocumentFormat());
        Set<OWLDeclarationAxiom> reloadedDeclarations = asSet(o3.axioms(AxiomType.DECLARATION));
        assertEquals(declarations, reloadedDeclarations);
    }

    private static final String in = "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
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
    private static final String inNoDeclaration =
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

    @Test
    public void shouldRoundtripUndeclaredAnnotationPropertiesTurtle() {
        OWLOntology o = loadOntologyFromString(in, new TurtleDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(inNoDeclaration, new TurtleDocumentFormat());
        // this declaration was excluded on purpose - add it to be able to use equal
        o1.add(df.getOWLDeclarationAxiom(
            df.getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#source")));
        equal(o, o1);
    }

    @Test
    public void shouldRoundtripUndeclaredAnnotationPropertiesRioTurtle() {
        OWLOntology o = loadOntologyFromString(in, new RioTurtleDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(inNoDeclaration, new RioTurtleDocumentFormat());
        // this declaration was excluded on purpose - add it to be able to use equal
        o1.add(df.getOWLDeclarationAxiom(
            df.getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#source")));
        equal(o, o1);
    }
}
