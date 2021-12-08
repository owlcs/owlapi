package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Created by ses on 6/23/14.
 */
class IRIShorteningTestCase extends TestBase {

    @Test
    void shouldAllowColonColon() {
        OWLOntology o = ontForShortening();
        assertionOnShortening(o, new TurtleDocumentFormatFactory());
        assertionOnShortening(o, new FunctionalSyntaxDocumentFormatFactory());
        assertionOnShortening(o, new ManchesterSyntaxDocumentFormatFactory());
    }

    protected void assertionOnShortening(OWLOntology o, OWLDocumentFormatFactory format) {
        OWLDocumentFormat turtle = format.createFormat();
        turtle.asPrefixOWLDocumentFormat().setPrefix("s", "urn:test:individual#");
        StringDocumentTarget saveOntology = saveOntology(o, turtle);
        OWLOntology loadOntologyFromString = loadFrom(saveOntology, format.createFormat());
        assertEquals(asSet(o.axioms()), asSet(loadOntologyFromString.axioms()));
        roundTrip(o, turtle);
    }

    protected OWLOntology ontForShortening() {
        OWLOntology o = create(iri("urn:ontology:", "testcolons"));
        o.addAxiom(Declaration(NamedIndividual(iri("urn:test:individual#colona:", "colonb"))));
        return o;
    }

    @Test
    void testIriEqualToPrefixNotShortenedInFSS() {
        OWLOntology o = createTestOntology();
        String output = saveOntology(o, new FunctionalSyntaxDocumentFormat()).toString();
        matchExact(output, "NamedIndividual(rdf:)", false);
        matchExact(output, "NamedIndividual(rdf:type)", true);
    }

    void matchExact(String output, String text, boolean expected) {
        String message = "should " + (expected ? "" : "not ") + "contain" + text + " - " + output;
        assertTrue(expected == output.contains(text), message);
    }

    @Test
    void testIriEqualToPrefixShortenedInTurtle() {
        OWLOntology o = createTestOntology();
        String output = saveOntology(o, new TurtleDocumentFormat()).toString();
        matchRegex(output, "rdf:\\s+rdf:type\\s+owl:NamedIndividual");
        matchRegex(output, "rdf:type\\s+rdf:type\\s+owl:NamedIndividual");
    }

    void matchRegex(String output, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(output);
        boolean found = matcher.find();
        assertTrue(found, "should  contain " + regex + " - " + output);
    }

    private OWLOntology createTestOntology() {
        return o(Declaration(NamedIndividual(iri(Namespaces.RDF.getPrefixIRI(), ""))),
            Declaration(NamedIndividual(OWLRDFVocabulary.RDF_TYPE.getIRI())));
    }

    @Test
    void shouldOutputURNsCorrectly() {
        OWLOntology o = createTestOntology();
        o.add(ObjectPropertyAssertion(P, NamedIndividual(iriTest),
            NamedIndividual(iri("urn:other:", "test"))));
        equal(o, roundTrip(o));
    }
}
