package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

public class OWLXMLTestCase extends TestBase {

    @Test
    public void shouldFindExpectedAxiomsForBlankNodes() throws OWLOntologyCreationException {
        OWLObjectProperty r = ObjectProperty(
            df.getIRI("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        OWLOntology o =
            m.loadOntologyFromOntologyDocument(new File(RESOURCES, "owlxml_anonloop.owx"));
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected =
                df.getOWLObjectPropertyAssertionAxiom(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(expected + " not found", o.containsAxiom(expected));
        });
    }

    @Test
    public void shouldParseSWRLVariables() throws OWLOntologyStorageException {
        OWLOntology o =
            loadOntologyFromString(TestFiles.parseSWRLVariable, new OWLXMLDocumentFormat());
        o.axioms(AxiomType.SWRL_RULE).forEach(r -> assertEquals(
            "DLSafeRule(Body(SameAsAtom(Variable(<urn:swrl:var#x>) Variable(<urn:swrl:var#y>))) Head())",
            r.toString()));
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        assertTrue(out, out.contains("<Variable IRI=\"urn:swrl:var#x\"/>"));
        assertTrue(out, out.contains("<Variable IRI=\"urn:swrl:var#y\"/>"));
    }
}
