package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLRule;

class OWLXMLTestCase extends TestBase {

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() {
        OWLObjectProperty r = ObjectProperty(
            iri("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        OWLOntology o = loadOntologyFromFile(new File(RESOURCES, "owlxml_anonloop.owx"));
        for (OWLClassAssertionAxiom ax : o.getAxioms(AxiomType.CLASS_ASSERTION)) {
            OWLAxiom expected =
                df.getOWLObjectPropertyAssertionAxiom(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        }
    }

    @Test
    void shouldParseSWRLVariables() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.parseSWRLVariable, new OWLXMLDocumentFormat());
        for (SWRLRule r : o.getAxioms(AxiomType.SWRL_RULE)) {
            assertEquals(
                "DLSafeRule(Body(SameAsAtom(Variable(<urn:swrl:var#x>) Variable(<urn:swrl:var#y>))) Head())",
                r.toString());
        }
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        assertTrue(out.contains("<Variable IRI=\"urn:swrl:var#x\"/>"), out);
        assertTrue(out.contains("<Variable IRI=\"urn:swrl:var#y\"/>"), out);
    }

    @Test
    void shouldParseSwrlAnonIndividual() {
        OWLOntology o = loadOntologyFromFile(new File(RESOURCES, "swrl_individual.owx"));

        for (SWRLRule r : o.getAxioms(AxiomType.SWRL_RULE)) {
            String expected = "DLSafeRule\\(Body\\(ClassAtom\\(<http://www.example.com/iri#A> " +
                "_:genid\\d+\\)\\) Head\\(ClassAtom\\(<http://www.example.com/iri#B> " +
                "<http://www.example.com/iri#I>\\)\\)\\)";

            assertTrue(r.toString()
                       .matches(expected),
                       "No match between:\n\t" + r.toString() + " and:\n\t" + expected);
        }


        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        String expected = ".*<AnonymousIndividual nodeID=\"_:genid\\d+\"/>.*";
        Pattern pattern = Pattern.compile(expected, Pattern.DOTALL);
        assertTrue(pattern.matcher(out).matches(),
                   "No match between:\n\t" + out + " and:\n\t" + expected
                   );
    }
}
