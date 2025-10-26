package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLRule;

class OWLXMLTestCase extends TestBase {

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() {
        OWLOntology o = loadFrom(new File(RESOURCES, "owlxml_anonloop.owx"), m);
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected =
                ObjectPropertyAssertion(OBJPROPS.ANON_R, ax.getIndividual(), ax.getIndividual());
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
        o.axioms(AxiomType.SWRL_RULE).forEach(r -> {
            List<SWRLAtom> body = r.body().toList();
            assertEquals(1, body.size());
            SWRLAtom element = body.get(0);
            assertTrue(element instanceof SWRLClassAtom);
            List<OWLAnonymousIndividual> anonymousIndividuals = ((SWRLClassAtom) element)
                .anonymousIndividuals().toList();
            assertEquals(1, anonymousIndividuals.size());
            String id = anonymousIndividuals.get(0).getID().getID();
            assertTrue(id.matches("_:genid\\d+"));
            idsInRules.add(id);
        });
        String out = saveOntology(o, new OWLXMLDocumentFormat()).toString();
        for (String id : idsInRules) {
            assertTrue(out.contains("<AnonymousIndividual nodeID=\"" + id
                + "\"/>"));
        }
    }
}
