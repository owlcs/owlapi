package org.semanticweb.owlapi6.apitest.annotations;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormat;

/**
 * Created by vincent on 20.08.15.
 */
public class DeclareAnnotatedEntitiesTestCase extends TestBase {

    @Test
    public void shouldDeclareAllDatatypes() throws Exception {
        OWLOntology ontology = loadOntologyFromString(TestFiles.declareDatatypes, new OWLXMLDocumentFormat());
        Set<OWLDeclarationAxiom> declarations = asSet(ontology.axioms(AxiomType.DECLARATION));
        Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms = asSet(
            ontology.axioms(AxiomType.ANNOTATION_ASSERTION));
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
        + "oio:source rdf:type owl:AnnotationProperty .\n\n\n" + "rdfs:comment rdf:type owl:AnnotationProperty .\n\n\n"
        + "obo:MONDO_0009025\n" + "    a owl:Class ;\n" + "    oio:hasDbXref \"UMLS:C1415737\" .\n" + "\n"
        + "[ a owl:Axiom ;\n" + "    rdfs:comment \"Some comment\";\n" + "  oio:source \"MONDO:notFoundInSource\" ;\n"
        + "  owl:annotatedProperty oio:hasDbXref ;\n" + "  owl:annotatedSource obo:MONDO_0009025 ;\n"
        + "  owl:annotatedTarget \"UMLS:C1415737\"\n" + "] .";
    private static final String inNoDeclaration = "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
        + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
        + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
        + "@prefix oio: <http://www.geneontology.org/formats/oboInOwl#> .\n"
        + "@prefix obo: <http://purl.obolibrary.org/obo/> .\n\n" + "obo:MONDO_0009025\n" + "    a owl:Class ;\n"
        + "    oio:hasDbXref \"UMLS:C1415737\" .\n" + "\n" + "[ a owl:Axiom ;\n"
        + "    rdfs:comment \"Some comment\";\n" + "   oio:source \"MONDO:notFoundInSource\" ;\n"
        + "  owl:annotatedProperty oio:hasDbXref ;\n" + "  owl:annotatedSource obo:MONDO_0009025 ;\n"
        + "  owl:annotatedTarget \"UMLS:C1415737\"\n" + "] .";

    @Test
    public void shouldRoundtripUndeclaredAnnotationPropertiesTurtle() {
        OWLOntology o = loadOntologyFromString(in, new TurtleDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(inNoDeclaration, new TurtleDocumentFormat());
        // this declaration was excluded on purpose - add it to be able to use
        // equal
        o1.add(df.getOWLDeclarationAxiom(
            df.getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#source")));
        equal(o, o1);
    }

    @Test
    public void shouldRoundtripUndeclaredAnnotationPropertiesRioTurtle() {
        OWLOntology o = loadOntologyFromString(in, new RioTurtleDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(inNoDeclaration, new RioTurtleDocumentFormat());
        // this declaration was excluded on purpose - add it to be able to use
        // equal
        o1.add(df.getOWLDeclarationAxiom(
            df.getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#source")));
        equal(o, o1);
    }
}
