package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLRule;

class OWLXMLTestCase extends TestBase {

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() {
        OWLObjectProperty r = ObjectProperty(iri(
            "http://www.derivo.de/ontologies/examples/anonymous-individuals#",
            "r"));
        OWLOntology o = loadOntologyFromFile(new File(RESOURCES,
            "owlxml_anonloop.owx"));
        for (OWLClassAssertionAxiom ax : o.getAxioms(
            AxiomType.CLASS_ASSERTION)) {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(r, ax
                .getIndividual(), ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        }
    }

    @Test
    void shouldParseSWRLVariables() {
        OWLOntology o = loadOntologyFromString(TestFiles.parseSWRLVariable,
            new OWLXMLDocumentFormat());
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
        OWLOntology o = loadOntologyFromFile(new File(RESOURCES,
            "swrl_individual.owx"));
        Set<String> idsInRules = new HashSet<>();
        for (SWRLRule r : o.getAxioms(AxiomType.SWRL_RULE)) {
            Set<SWRLAtom> body = r.getBody();
            assertEquals(1, body.size());
            SWRLAtom element = body.iterator().next();
            assertTrue(element instanceof SWRLClassAtom);
            Set<OWLAnonymousIndividual> anonymousIndividuals = ((SWRLClassAtom) element)
                .getAnonymousIndividuals();
            assertEquals(1, anonymousIndividuals.size());
            String id = anonymousIndividuals.iterator().next().getID().getID();
            assertTrue(id.matches("_:genid\\d+"));
            idsInRules.add(id);
        }
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        for (String id : idsInRules) {
            assertTrue(out.contains("<AnonymousIndividual nodeID=\"" + id
                + "\"/>"));
        }
    }
}
