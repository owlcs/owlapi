package org.semanticweb.owlapi6.apitest.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.model.SWRLAtom;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.model.SWRLVariable;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
public class SWRLAtomOrderingRoundTripTestCase extends TestBase {

    private final Set<SWRLAtom> body = new LinkedHashSet<>();
    private final Set<SWRLAtom> head = new LinkedHashSet<>();
    private SWRLRule rule;

    @Before
    public void setUpPrefixes() {
        PrefixManager pm = new PrefixManagerImpl().withDefaultPrefix("http://stuff.com/A/");
        OWLClass clsA = Class("A", pm);
        OWLClass clsB = Class("B", pm);
        OWLClass clsC = Class("C", pm);
        OWLClass clsD = Class("D", pm);
        OWLClass clsE = Class("E", pm);
        // XXX
        SWRLVariable varA = df.getSWRLVariable("http://other.com/A/", "VarA");
        SWRLVariable varB = df.getSWRLVariable("http://other.com/A/", "VarA");
        SWRLVariable varC = df.getSWRLVariable("http://other.com/A/", "VarA");
        body.add(df.getSWRLClassAtom(clsC, varA));
        body.add(df.getSWRLClassAtom(clsB, varB));
        body.add(df.getSWRLClassAtom(clsA, varC));
        head.add(df.getSWRLClassAtom(clsE, varA));
        head.add(df.getSWRLClassAtom(clsD, varA));
        rule = df.getSWRLRule(body, head);
    }

    @Test
    public void individualsShouldNotGetSWRLVariableTypes() throws OWLOntologyStorageException {
        OWLOntology o = loadOntologyFromString(TestFiles.individualSWRLTest,
            df.getIRI("urn:test#", "test"), new RDFXMLDocumentFormat());
        String string = saveOntology(o).toString();
        assertFalse(string, string
            .contains("<rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#Variable\"/>"));
    }

    @Test
    public void shouldPreserveOrderingInRDFXMLRoundTrip() throws Exception {
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
        List<SWRLAtom> parsedBody = parsedRule.bodyList();
        assertThat(parsedBody, is(equalTo(originalBody)));
        List<SWRLAtom> originalHead = new ArrayList<>(head);
        List<SWRLAtom> parsedHead = parsedRule.headList();
        assertThat(originalHead, is(equalTo(parsedHead)));
    }

    @Test
    public void shouldPreserveOrderingInTurtleRoundTrip() throws OWLOntologyStorageException {
        roundTrip(new TurtleDocumentFormat());
    }

    @Test
    public void shouldPreserveOrderingInManchesterSyntaxRoundTrip()
        throws OWLOntologyStorageException {
        roundTrip(new ManchesterSyntaxDocumentFormat());
    }

    @Test
    public void shouldPreserveOrderingInOWLXMLRoundTrip() throws OWLOntologyStorageException {
        roundTrip(new OWLXMLDocumentFormat());
    }
}
