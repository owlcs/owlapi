package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Created by ses on 6/23/14.
 */
@SuppressWarnings("javadoc")
public class IRIShorteningTestCase extends TestBase {
    @Test
    public void shouldAllowColonColon()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = ontForShortening();
        assertionOnShortening(o, new TurtleDocumentFormatFactory());
        assertionOnShortening(o, new FunctionalSyntaxDocumentFormatFactory());
        assertionOnShortening(o, new ManchesterSyntaxDocumentFormatFactory());
    }

    protected void assertionOnShortening(OWLOntology o, OWLDocumentFormatFactory f)
        throws OWLOntologyStorageException, OWLOntologyCreationException {
        OWLDocumentFormat turtle = f.createFormat();
        turtle.asPrefixOWLDocumentFormat().setPrefix("s", "urn:test:individual#");
        StringDocumentTarget saveOntology = saveOntology(o, turtle);
        OWLOntology loadOntologyFromString = loadOntologyFromString(saveOntology, f.createFormat());
        assertEquals(asSet(o.axioms()), asSet(loadOntologyFromString.axioms()));
        roundTrip(o, turtle);
    }

    protected OWLOntology ontForShortening() {
        OWLOntology o = getOWLOntology(IRI.create("urn:ontology:testcolons"));
        o.getOWLOntologyManager().addAxiom(o, df.getOWLDeclarationAxiom(
            df.getOWLNamedIndividual(IRI.create("urn:test:individual#colona:colonb"))));
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
        assertEquals(message, Boolean.valueOf(expected), Boolean.valueOf(output.contains(text)));
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
        OWLOntology o = m.createOntology(IRI.create("urn:ontology:", "test"));
        o.add(df.getOWLObjectPropertyAssertionAxiom(df.getOWLObjectProperty("urn:test#", "p"),
            df.getOWLNamedIndividual("urn:test#", "test"),
            df.getOWLNamedIndividual("urn:other:", "test")));
        equal(o, roundTrip(o));
    }
}
