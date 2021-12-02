package org.semanticweb.owlapi.api.test.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
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

    @Nonnull
    final Set<SWRLAtom> body = new LinkedHashSet<>();
    @Nonnull
    final Set<SWRLAtom> head = new LinkedHashSet<>();
    @Nonnull
    SWRLRule rule;

    @BeforeEach
    void setUpPrefixes() {
        PrefixManager pm = new DefaultPrefixManager(null, null, "http://stuff.com/A/");
        OWLClass clsA = Class("A", pm);
        OWLClass clsB = Class("B", pm);
        OWLClass clsC = Class("C", pm);
        OWLClass clsD = Class("D", pm);
        OWLClass clsE = Class("E", pm);
        SWRLVariable varA = df.getSWRLVariable(iri("http://other.com/A/", "VarA"));
        SWRLVariable varB = df.getSWRLVariable(iri("http://other.com/A/", "VarB"));
        SWRLVariable varC = df.getSWRLVariable(iri("http://other.com/A/", "VarC"));
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
    void individualsShouldNotGetSWRLVariableTypes() {
        OWLOntology o = loadOntologyFromString(TestFiles.individualSWRLTest,
            iri("urn:test#", "test"), new RDFXMLDocumentFormat());
        String string = saveOntology(o).toString();
        assertFalse(
            string.contains("<rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#Variable\"/>"),
            string);
    }

    @Test
    void shouldPreserveOrderingInRDFXMLRoundTrip() {
        roundTrip(new RDFXMLDocumentFormat());
    }

    private void roundTrip(@Nonnull OWLDocumentFormat ontologyFormat) {
        OWLOntology ont = getAnonymousOWLOntology();
        m.addAxiom(ont, rule);
        StringDocumentTarget documentTarget = saveOntology(ont, ontologyFormat);
        OWLOntology ont2 =
            loadOntologyFromSource(new StringDocumentSource(documentTarget.toString(),
                OWLOntologyDocumentSourceBase.getNextDocumentIRI("string:ontology"), ontologyFormat,
                null));
        Set<SWRLRule> rules = ont2.getAxioms(AxiomType.SWRL_RULE);
        assertThat(rules.size(), is(1));
        SWRLRule parsedRule = rules.iterator().next();
        assertThat(parsedRule, is(equalTo(rule)));
        List<SWRLAtom> originalBody = new ArrayList<>(body);
        List<SWRLAtom> parsedBody = new ArrayList<>(parsedRule.getBody());
        assertThat(parsedBody, is(equalTo(originalBody)));
        List<SWRLAtom> originalHead = new ArrayList<>(head);
        List<SWRLAtom> parsedHead = new ArrayList<>(parsedRule.getHead());
        assertThat(originalHead, is(equalTo(parsedHead)));
    }

    @Test
    void shouldPreserveOrderingInTurtleRoundTrip() {
        roundTrip(new TurtleDocumentFormat());
    }

    @Test
    void shouldPreserveOrderingInManchesterSyntaxRoundTrip() {
        roundTrip(new ManchesterSyntaxDocumentFormat());
    }

    @Test
    void shouldPreserveOrderingInOWLXMLRoundTrip() {
        roundTrip(new OWLXMLDocumentFormat());
    }
}
