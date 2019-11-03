package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.vocab.Namespaces;
import org.semanticweb.owlapi6.vocab.OWLRDFVocabulary;

/**
 * Created by ses on 6/23/14.
 */
public class IRIShorteningTestCase extends TestBase {

    @Test
    public void shouldAllowColonColon() throws OWLOntologyStorageException {
        OWLOntology o = ontForShortening();
        assertionOnShortening(o, new TurtleDocumentFormatFactory());
        assertionOnShortening(o, new FunctionalSyntaxDocumentFormatFactory());
        assertionOnShortening(o, new ManchesterSyntaxDocumentFormatFactory());
    }

    protected void assertionOnShortening(OWLOntology o, OWLDocumentFormatFactory f)
        throws OWLOntologyStorageException {
        OWLDocumentFormat turtle = f.createFormat();
        o.getPrefixManager().withPrefix("s", "urn:test:individual#");
        StringDocumentTarget saveOntology = saveOntology(o, turtle);
        OWLOntology loadOntologyFromString = loadOntologyFromString(saveOntology, f.createFormat());
        assertEquals(asSet(o.axioms()), asSet(loadOntologyFromString.axioms()));
        roundTrip(o, turtle);
    }

    protected OWLOntology ontForShortening() {
        OWLOntology o = getOWLOntology(df.getIRI("urn:ontology:testcolons"));
        o.addAxiom(df.getOWLDeclarationAxiom(
            df.getOWLNamedIndividual(df.getIRI("urn:test:individual#colona:colonb"))));
        return o;
    }

    @Test
    public void testIriEqualToPrefixNotShortenedInFSS() throws Exception {
        OWLOntology o = createTestOntology();
        String output = saveOntology(o, new FunctionalSyntaxDocumentFormat()).toString();
        matchExact(output, "NamedIndividual(rdf:)", false);
        matchExact(output, "NamedIndividual(rdf:type)", true);
    }

    public void matchExact(String output, String text, boolean expected) {
        String message = "should " + (expected ? "" : "not ") + "contain" + text + " - " + output;
        assertTrue(message, expected == output.contains(text));
    }

    @Test
    public void testIriEqualToPrefixShortenedInTurtle() throws Exception {
        OWLOntology o = createTestOntology();
        String output = saveOntology(o, new TurtleDocumentFormat()).toString();
        matchRegex(output, "rdf:\\s+rdf:type\\s+owl:NamedIndividual");
        matchRegex(output, "rdf:type\\s+rdf:type\\s+owl:NamedIndividual");
    }

    public void matchRegex(String output, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(output);
        boolean found = matcher.find();
        assertTrue("should  contain " + regex + " - " + output, found);
    }

    private OWLOntology createTestOntology() {
        OWLOntology o = getOWLOntology();
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI(Namespaces.RDF.getPrefixIRI(), ""));
        o.add(df.getOWLDeclarationAxiom(i));
        i = df.getOWLNamedIndividual(OWLRDFVocabulary.RDF_TYPE);
        o.add(df.getOWLDeclarationAxiom(i));
        return o;
    }

    @Test
    public void shouldOutputURNsCorrectly()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(df.getIRI("urn:ontology:", "test"));
        o.add(df.getOWLObjectPropertyAssertionAxiom(df.getOWLObjectProperty("urn:test#", "p"),
            df.getOWLNamedIndividual("urn:test#", "test"),
            df.getOWLNamedIndividual("urn:other:", "test")));
        equal(o, roundTrip(o));
    }
}
