package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.*;

class OWLXMLTestCase extends TestBase {

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() {
        OWLObjectProperty r = ObjectProperty(
            iri("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        OWLOntology o = loadFrom(new File(RESOURCES, "owlxml_anonloop.owx"), m);
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected = ObjectPropertyAssertion(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        });
    }

    @Test
    void shouldParseSWRLVariables() {
        OWLOntology o = loadFrom(TestFiles.parseSWRLVariable, new OWLXMLDocumentFormat());
        o.axioms(AxiomType.SWRL_RULE).forEach(r -> assertEquals(
            "DLSafeRule(Body(SameAsAtom(Variable(<urn:swrl:var#x>) Variable(<urn:swrl:var#y>))) Head())",
            r.toString()));
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        assertTrue(out.contains("<Variable IRI=\"urn:swrl:var#x\"/>"), out);
        assertTrue(out.contains("<Variable IRI=\"urn:swrl:var#y\"/>"), out);
    }

    @Test
    void shouldParseSwrlAnonIndividual() {
        OWLOntology o = loadFrom(new File(RESOURCES,
            "swrl_individual.owx"), new OWLXMLDocumentFormat(), m);
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
