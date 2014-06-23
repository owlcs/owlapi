package org.semanticweb.owlapi.api.test.syntax;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.Namespaces;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        OWLOntology o = m.createOntology();
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI(Namespaces.RDF.getPrefixIRI()));
        m.addAxiom(o, df.getOWLDeclarationAxiom(i));

        OWLFunctionalSyntaxOntologyFormat functionalSyntaxOntologyFormat = new OWLFunctionalSyntaxOntologyFormat();
        StringDocumentTarget target = new StringDocumentTarget();
        m.saveOntology(o, functionalSyntaxOntologyFormat, target);
        String output = target.toString();
        assertFalse("should not contain NamedIndividual(rdf:) - " + output,
                output.contains("NamedIndividual(rdf:)"));

    }

    @Test
    public void testIriEqualToPrefixShortenedInTurtle() throws Exception {
        OWLDataFactory df = m.getOWLDataFactory();
        OWLOntology o = m.createOntology();
        OWLNamedIndividual i = df.getOWLNamedIndividual(IRI(Namespaces.RDF.getPrefixIRI()));
        m.addAxiom(o, df.getOWLDeclarationAxiom(i));

        StringDocumentTarget target = new StringDocumentTarget();
        TurtleOntologyFormat turtleOntologyFormat = new TurtleOntologyFormat();
        m.saveOntology(o, turtleOntologyFormat, target);
        String output = target.toString();
        String regex = "rdf:\\s+rdf:type\\s+owl:NamedIndividual";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(output);
        boolean found = matcher.find();
        String matched = matcher.group(0);
        assertTrue("should  contain " + regex + " - " + output, found);

    }

}
