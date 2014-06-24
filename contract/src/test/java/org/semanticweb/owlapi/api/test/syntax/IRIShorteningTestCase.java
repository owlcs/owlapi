package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Created by ses on 6/23/14.
 */
@SuppressWarnings("javadoc")
public class IRIShorteningTestCase extends TestBase {

    @Test
    public void testIriEqualToPrefixNotShortenedInFSS() throws Exception {
        OWLOntology o = createTestOntology();
        String output = saveOntology(o, new OWLFunctionalSyntaxOntologyFormat())
                .toString();
        matchExact(output, "NamedIndividual(rdf:)", false);
        matchExact(output, "NamedIndividual(rdf:type)", true);
    }

    public void matchExact(String output, String text, boolean expected) {
        String message = "should " + (expected ? "" : "not ") + "contain"
                + text + " - " + output;
        assertEquals(message, expected, output.contains(text));
    }

    @Test
    public void testIriEqualToPrefixShortenedInTurtle() throws Exception {
        OWLOntology o = createTestOntology();
        String output = saveOntology(o, new TurtleOntologyFormat()).toString();
        matchRegex(output, "rdf:\\s+rdf:type\\s+owl:NamedIndividual");
        matchRegex(output, "rdf:type\\s+rdf:type\\s+owl:NamedIndividual");
    }

    public void matchRegex(String output, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(output);
        boolean found = matcher.find();
        assertTrue("should  contain " + regex + " - " + output, found);
    }

    @Nonnull
    private OWLOntology createTestOntology()
            throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI(Namespaces.RDF
                .getPrefixIRI()));
        m.addAxiom(o, df.getOWLDeclarationAxiom(i));
        i = df.getOWLNamedIndividual(OWLRDFVocabulary.RDF_TYPE.getIRI());
        m.addAxiom(o, df.getOWLDeclarationAxiom(i));
        return o;
    }
}
