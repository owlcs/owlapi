package org.semanticweb.owlapi6.apitest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

@RunWith(Parameterized.class)
public class PunnedDeclarationsNotAddedTestCase extends TestBase {

    @Parameters(name = "{0}")
    public static Collection<OWLDocumentFormat> data() {
        return Arrays.asList(new FunctionalSyntaxDocumentFormat(), new OWLXMLDocumentFormat(),
            new RDFXMLDocumentFormat(), new TurtleDocumentFormat());
    }

    private final OWLDocumentFormat format;

    public PunnedDeclarationsNotAddedTestCase(OWLDocumentFormat format) {
        this.format = format;
    }

    protected OWLOntology getOntologyWithPunnedInvalidDeclarations() {
        OWLOntology o = getOWLOntology();
        OWLObjectProperty op = df.getOWLObjectProperty(iri("testProperty"));
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri("testProperty"));
        o.add(df.getOWLDeclarationAxiom(op));
        o.add(df.getOWLTransitiveObjectPropertyAxiom(op));
        OWLAnnotationAssertionAxiom assertion = df.getOWLAnnotationAssertionAxiom(iri("test"),
            df.getOWLAnnotation(ap, iri("otherTest")));
        o.add(assertion);
        return o;
    }

    protected OWLOntology getOntologyWithMissingDeclarations() {
        OWLOntology o = getOWLOntology();
        OWLObjectProperty op = df.getOWLObjectProperty(iri("testObjectProperty"));
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri("testAnnotationProperty"));
        o.add(df.getOWLTransitiveObjectPropertyAxiom(op));
        OWLAnnotationAssertionAxiom assertion = df.getOWLAnnotationAssertionAxiom(iri("test"),
            df.getOWLAnnotation(ap, iri("otherTest")));
        o.add(assertion);
        return o;
    }

    @Test
    public void shouldNotAddDeclarationsForIllegalPunnings() throws OWLOntologyStorageException {
        OWLOntology o = getOntologyWithPunnedInvalidDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri("testProperty"));
        OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(ap);
        assertFalse("ap testProperty should not have been declared", reloaded.containsAxiom(ax));
    }

    @Test
    public void shouldDeclareMissingEntities() throws OWLOntologyStorageException {
        OWLOntology o = getOntologyWithMissingDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLObjectProperty op = df.getOWLObjectProperty(iri("testObjectProperty"));
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri("testAnnotationProperty"));
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(ap)));
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(op)));
    }
}
