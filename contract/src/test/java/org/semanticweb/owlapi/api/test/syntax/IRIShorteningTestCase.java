package org.semanticweb.owlapi.api.test.syntax;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

/**
 * Created by ses on 6/23/14.
 */
public class IRIShorteningTestCase  extends TestBase {
    @Test
    public void testIriEqualToPrefixNotShortenedInFSS() throws Exception {
        OWLDataFactory df = m.getOWLDataFactory();
        OWLOntology o = createTestOntology(df);

        OWLFunctionalSyntaxOntologyFormat functionalSyntaxOntologyFormat = new OWLFunctionalSyntaxOntologyFormat();
        StringDocumentTarget target = new StringDocumentTarget();
        m.saveOntology(o, functionalSyntaxOntologyFormat, target);
        String output = target.toString();
        matchExact(output, "NamedIndividual(rdf:)", false);
        matchExact(output, "NamedIndividual(rdf:type)", true);

    }

    public void matchExact(String output, String text, boolean expected) {
        String message = "should " + (expected ? "" : "not ") + "contain" + text + " - " + output;
        assertEquals(message, expected, output.contains(text));
    }

    @Test
    public void testIriEqualToPrefixShortenedInTurtle() throws Exception {
        OWLDataFactory df = m.getOWLDataFactory();
        OWLOntology o = createTestOntology(df);

        StringDocumentTarget target = new StringDocumentTarget();
        TurtleOntologyFormat turtleOntologyFormat = new TurtleOntologyFormat();
        m.saveOntology(o, turtleOntologyFormat, target);
        String output = target.toString();
        matchRegex(output, "rdf:\\s+rdf:type\\s+owl:NamedIndividual");
        matchRegex(output, "rdf:type\\s+rdf:type\\s+owl:NamedIndividual");

    }

    public void matchRegex(String output, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(output);
        boolean found = matcher.find();
        String matched = matcher.group(0);
        assertTrue("should  contain " + regex + " - " + output, found);
    }

    private OWLOntology createTestOntology(OWLDataFactory df) throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI(Namespaces.RDF.getPrefixIRI()));
        m.addAxiom(o, df.getOWLDeclarationAxiom(i));
        i = df.getOWLNamedIndividual(OWLRDFVocabulary.RDF_TYPE.getIRI());
        m.addAxiom(o, df.getOWLDeclarationAxiom(i));
        return o;
    }


}
