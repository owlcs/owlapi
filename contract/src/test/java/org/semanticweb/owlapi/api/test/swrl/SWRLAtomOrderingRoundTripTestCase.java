package org.semanticweb.owlapi.api.test.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 04/04/2014
 */
@SuppressWarnings({ "javadoc", "null" })
public class SWRLAtomOrderingRoundTripTestCase extends TestBase {

    private final @Nonnull Set<SWRLAtom> body = new LinkedHashSet<>();
    private final @Nonnull Set<SWRLAtom> head = new LinkedHashSet<>();
    private @Nonnull SWRLRule rule;

    @Before
    public void setUpPrefixes() {
        PrefixManager pm = new DefaultPrefixManager(null, null, "http://stuff.com/A/");
        OWLClass clsA = Class("A", pm);
        OWLClass clsB = Class("B", pm);
        OWLClass clsC = Class("C", pm);
        OWLClass clsD = Class("D", pm);
        OWLClass clsE = Class("E", pm);
        SWRLVariable varA = df.getSWRLVariable("http://other.com/A/VarA");
        SWRLVariable varB = df.getSWRLVariable("http://other.com/A/VarA");
        SWRLVariable varC = df.getSWRLVariable("http://other.com/A/VarA");
        body.add(df.getSWRLClassAtom(clsC, varA));
        body.add(df.getSWRLClassAtom(clsB, varB));
        body.add(df.getSWRLClassAtom(clsA, varC));
        head.add(df.getSWRLClassAtom(clsE, varA));
        head.add(df.getSWRLClassAtom(clsD, varA));
        rule = df.getSWRLRule(body, head);
    }

    @Test
    public void individualsShouldNotGetSWRLVariableTypes() throws OWLOntologyStorageException {
        String in = "<rdf:RDF xmlns=\"urn:test#\" xml:base=\"urn:test\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\" xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\" xmlns:protege=\"urn:test#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
            + "    <owl:Ontology rdf:about=\"urn:test\"/>\n"
            + "    <owl:ObjectProperty rdf:about=\"urn:test#drives\"/>\n"
            + "    <owl:ObjectProperty rdf:about=\"urn:test#hasDriver\"/>\n"
            + "    <owl:NamedIndividual rdf:about=\"urn:test#i61\"/>\n"
            + "    <owl:NamedIndividual rdf:about=\"urn:test#i62\"/>\n" + "    <rdf:Description>\n"
            + "        <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#Imp\"/>\n"
            + "        <swrl:body rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
            + "        <swrl:head>\n" + "            <rdf:Description>\n"
            + "                <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#AtomList\"/>\n"
            + "                <rdf:first>\n" + "                    <rdf:Description>\n"
            + "                        <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#IndividualPropertyAtom\"/>\n"
            + "                        <swrl:argument1 rdf:resource=\"urn:test#i61\"/>\n"
            + "                        <swrl:argument2 rdf:resource=\"urn:test#i62\"/>\n"
            + "                        <swrl:propertyPredicate rdf:resource=\"urn:test#drives\"/>\n"
            + "                    </rdf:Description>\n" + "                </rdf:first>\n"
            + "                <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
            + "            </rdf:Description>\n" + "        </swrl:head>\n" + "    </rdf:Description>\n"
            + "    <rdf:Description>\n"
            + "        <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#Imp\"/>\n"
            + "        <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">:i62, :i61</rdfs:comment>\n"
            + "        <swrl:body>\n" + "            <rdf:Description>\n"
            + "                <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#AtomList\"/>\n"
            + "                <rdf:first>\n" + "                    <rdf:Description>\n"
            + "                        <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#IndividualPropertyAtom\"/>\n"
            + "                        <swrl:argument1 rdf:resource=\"urn:test#i62\"/>\n"
            + "                        <swrl:argument2 rdf:resource=\"urn:test#i61\"/>\n"
            + "                        <swrl:propertyPredicate rdf:resource=\"urn:test#hasDriver\"/>\n"
            + "                    </rdf:Description>\n" + "                </rdf:first>\n"
            + "                <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
            + "            </rdf:Description>\n" + "        </swrl:body>\n" + "        <swrl:head>\n"
            + "            <rdf:Description>\n"
            + "                <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#AtomList\"/>\n"
            + "                <rdf:first>\n" + "                    <rdf:Description>\n"
            + "                        <rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#BuiltinAtom\"/>\n"
            + "                        <swrl:arguments rdf:parseType=\"Collection\">\n"
            + "                            <rdf:Description rdf:about=\"urn:test#i62\"/>\n"
            + "                            <rdf:Description rdf:about=\"urn:test#i61\"/>\n"
            + "                        </swrl:arguments>\n"
            + "                        <swrl:builtin rdf:resource=\"http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#select\"/>\n"
            + "                    </rdf:Description>\n" + "                </rdf:first>\n"
            + "                <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
            + "            </rdf:Description>\n" + "        </swrl:head>\n" + "    </rdf:Description>\n"
            + "</rdf:RDF>";
        OWLOntology o = loadOntologyFromString(in, IRI.create("urn:test"), new RDFXMLDocumentFormat());
        String string = saveOntology(o).toString();
        assertFalse(string, string.contains("<rdf:type rdf:resource=\"http://www.w3.org/2003/11/swrl#Variable\"/>"));
    }

    @Test
    public void shouldPreserveOrderingInRDFXMLRoundTrip() throws Exception {
        roundTrip(new RDFXMLDocumentFormat());
    }

    private void roundTrip(OWLDocumentFormat ontologyFormat)
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ont = getOWLOntology();
        ont.add(rule);
        StringDocumentTarget documentTarget = new StringDocumentTarget();
        ont.saveOntology(ontologyFormat, documentTarget);
        OWLOntology ont2 = loadOntologyFromString(documentTarget);
        Set<SWRLRule> rules = asSet(ont2.axioms(AxiomType.SWRL_RULE));
        assertThat(rules.size(), is(1));
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
    public void shouldPreserveOrderingInTurtleRoundTrip()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        roundTrip(new TurtleDocumentFormat());
    }

    @Test
    public void shouldPreserveOrderingInManchesterSyntaxRoundTrip()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        roundTrip(new ManchesterSyntaxDocumentFormat());
    }

    @Test
    public void shouldPreserveOrderingInOWLXMLRoundTrip()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        roundTrip(new OWLXMLDocumentFormat());
    }
}
