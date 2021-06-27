package org.semanticweb.owlapi.api.test.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
class SWRLAtomOrderingRoundTripTestCase extends TestBase {

    final Set<SWRLAtom> body = new LinkedHashSet<>();
    final Set<SWRLAtom> head = new LinkedHashSet<>();
    SWRLRule rule;

    @BeforeEach
    void setUpPrefixes() {
        PrefixManager pm = new DefaultPrefixManager(null, null, "http://stuff.com/A/");
        OWLClass clsA = Class("A", pm);
        OWLClass clsB = Class("B", pm);
        OWLClass clsC = Class("C", pm);
        OWLClass clsD = Class("D", pm);
        OWLClass clsE = Class("E", pm);
        SWRLVariable varA = df.getSWRLVariable("http://other.com/A/", "VarA");
        SWRLVariable varB = df.getSWRLVariable("http://other.com/A/", "VarB");
        SWRLVariable varC = df.getSWRLVariable("http://other.com/A/", "VarC");
        SWRLClassAtom t = df.getSWRLClassAtom(clsC, varA);
        body.add(t);
        body.add(df.getSWRLClassAtom(clsB, varB));
        body.add(df.getSWRLClassAtom(clsA, varC));
        head.add(df.getSWRLClassAtom(clsE, varA));
        head.add(df.getSWRLClassAtom(clsD, varA));
        head.add(t);
        rule = df.getSWRLRule(body, head);
    }

    @Test
    void individualsShouldNotGetSWRLVariableTypes() throws OWLOntologyStorageException {
        OWLOntology o = loadOntologyFromString(TestFiles.individualSWRLTest,
            iri("urn:test#", "test"), new RDFXMLDocumentFormat());
        String string = saveOntology(o).toString();
        assertFalse(
            string.contains("<rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#Variable\"/>"),
            string);
    }

    @Test
    void shouldPreserveOrderingInRDFXMLRoundTrip() throws Exception {
        roundTrip(new RDFXMLDocumentFormat());
    }

    private void roundTrip(OWLDocumentFormat ontologyFormat) throws OWLOntologyStorageException {
        OWLOntology ont = getOWLOntology();
        ont.add(rule);
        StringDocumentTarget documentTarget = new StringDocumentTarget();
        ont.saveOntology(ontologyFormat, documentTarget);
        OWLOntology ont2 = loadOntologyFromString(documentTarget, ontologyFormat);
        Set<SWRLRule> rules = asUnorderedSet(ont2.axioms(AxiomType.SWRL_RULE));
        assertEquals(1, rules.size());
        SWRLRule parsedRule = rules.iterator().next();
        assertThat(parsedRule, is(equalTo(rule)));
        List<SWRLAtom> originalBody = new ArrayList<>(body);
        List<SWRLAtom> parsedBody = asList(parsedRule.body());
        assertThat(parsedBody, is(equalTo(originalBody)));
        List<SWRLAtom> originalHead = new ArrayList<>(head);
        List<SWRLAtom> parsedHead = asList(parsedRule.head());
        assertThat(originalHead, is(equalTo(parsedHead)));
    }

    @Test
    void shouldPreserveOrderingInTurtleRoundTrip() throws OWLOntologyStorageException {
        roundTrip(new TurtleDocumentFormat());
    }

    @Test
    void shouldPreserveOrderingInManchesterSyntaxRoundTrip() throws OWLOntologyStorageException {
        roundTrip(new ManchesterSyntaxDocumentFormat());
    }

    @Test
    void shouldPreserveOrderingInOWLXMLRoundTrip() throws OWLOntologyStorageException {
        roundTrip(new OWLXMLDocumentFormat());
    }
}
