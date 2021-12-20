package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.vocab.Namespaces;
import org.semanticweb.owlapi6.vocab.OWLRDFVocabulary;

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
        o.getPrefixManager().withPrefix("s", "urn:test:individual#");
        StringDocumentTarget saveOntology = saveOntology(o, turtle);
        OWLOntology loadFrom = loadFrom(saveOntology, format.createFormat());
        assertEquals(asSet(o.axioms()), asSet(loadFrom.axioms()));
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
        o.add(ObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.IND_TEST, INDIVIDUALS.IND_OTHER));
        equal(o, roundTrip(o));
    }
}
