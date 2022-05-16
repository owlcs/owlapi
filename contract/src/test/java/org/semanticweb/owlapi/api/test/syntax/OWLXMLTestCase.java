package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.TestFiles;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class OWLXMLTestCase extends TestBase {

    @Test
    public void shouldFindExpectedAxiomsForBlankNodes() throws OWLOntologyCreationException {
        OWLObjectProperty r = ObjectProperty(
            df.getIRI("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        OWLOntology o = m.loadOntologyFromOntologyDocument(new File(RESOURCES, "owlxml_anonloop.owx"));
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(expected + " not found", o.containsAxiom(expected));
        });
    }

    @Test
    public void shouldParseSWRLVariables() throws OWLOntologyStorageException {
        OWLOntology o = loadOntologyFromString(TestFiles.parseSWRLVariable, new OWLXMLDocumentFormat());
        o.axioms(AxiomType.SWRL_RULE)
            .forEach(r -> assertEquals(
                "DLSafeRule(Body(SameAsAtom(Variable(<urn:swrl:var#x>) Variable(<urn:swrl:var#y>))) Head())",
                r.toString()));
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        assertTrue(out, out.contains("<Variable IRI=\"urn:swrl:var#x\"/>"));
        assertTrue(out, out.contains("<Variable IRI=\"urn:swrl:var#y\"/>"));
    }
}
