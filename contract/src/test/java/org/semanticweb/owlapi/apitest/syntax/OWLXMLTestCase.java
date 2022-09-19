package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

class OWLXMLTestCase extends TestBase {

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() throws OWLOntologyCreationException {
        OWLObjectProperty r = ObjectProperty(
            iri("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        OWLOntology o =
            m.loadOntologyFromOntologyDocument(new File(RESOURCES, "owlxml_anonloop.owx"));
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected =
                df.getOWLObjectPropertyAssertionAxiom(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        });
    }

    @Test
    void shouldParseSWRLVariables() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.parseSWRLVariable, new OWLXMLDocumentFormat());
        o.axioms(AxiomType.SWRL_RULE).forEach(r -> assertEquals(
            "DLSafeRule(Body(SameAsAtom(Variable(<urn:swrl:var#x>) Variable(<urn:swrl:var#y>))) Head())",
            r.toString()));
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        assertTrue(out.contains("<Variable IRI=\"urn:swrl:var#x\"/>"), out);
        assertTrue(out.contains("<Variable IRI=\"urn:swrl:var#y\"/>"), out);
    }
}
