package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;

import org.coode.owlapi.latex.LatexOntologyFormat;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class LatexRendererTestCase {

    String input = "<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF xmlns=\"http://namespace.owl#\"\n"
            + "     xml:base=\"http://namespace.owl\"\n"
            + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
            + "    <owl:Ontology rdf:about=\"http://namespace.owl\"/>\n"
            + "    <owl:Class rdf:about=\"http://namespace.owl#C_Test\"/>"
            + "<owl:ObjectProperty rdf:about=\"http://namespace.owl#p\"/>"
            + "</rdf:RDF>";

    @Test
    public void shouldRenderEscapingUnderscores() throws Exception {
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        StringDocumentTarget target = new StringDocumentTarget();
        LatexOntologyFormat ontologyFormat = new LatexOntologyFormat();
        o.getOWLOntologyManager().saveOntology(o, ontologyFormat, target);
        assertTrue(target.toString().contains("C\\_Test"));
    }
}
