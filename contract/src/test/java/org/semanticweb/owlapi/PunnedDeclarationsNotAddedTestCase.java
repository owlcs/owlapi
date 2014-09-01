package org.semanticweb.owlapi;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class PunnedDeclarationsNotAddedTestCase extends TestBase {

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new FunctionalSyntaxDocumentFormat() },
                { new OWLXMLDocumentFormat() }, { new RDFXMLDocumentFormat() },
                { new TurtleDocumentFormat() }, });
    }

    @Nonnull
    private final OWLDocumentFormat format;

    public PunnedDeclarationsNotAddedTestCase(@Nonnull OWLDocumentFormat format) {
        this.format = format;
    }

    @Nonnull
    protected OWLOntology getOntologyWithPunnedInvalidDeclarations()
            throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology(IRI
                .create("urn:forbiddenPunningNotRedeclared"));
        OWLObjectProperty op = df.getOWLObjectProperty(iri("testProperty"));
        OWLAnnotationProperty ap = df
                .getOWLAnnotationProperty(iri("testProperty"));
        m.addAxiom(o, df.getOWLDeclarationAxiom(op));
        m.addAxiom(o, df.getOWLTransitiveObjectPropertyAxiom(op));
        OWLAnnotationAssertionAxiom assertion = df
                .getOWLAnnotationAssertionAxiom(iri("test"),
                        df.getOWLAnnotation(ap, iri("otherTest")));
        m.addAxiom(o, assertion);
        return o;
    }

    @Nonnull
    protected OWLOntology getOntologyWithMissingDeclarations()
            throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology(IRI.create("urn:missingDeclarations"));
        OWLObjectProperty op = df
                .getOWLObjectProperty(iri("testObjectProperty"));
        OWLAnnotationProperty ap = df
                .getOWLAnnotationProperty(iri("testAnnotationProperty"));
        m.addAxiom(o, df.getOWLTransitiveObjectPropertyAxiom(op));
        OWLAnnotationAssertionAxiom assertion = df
                .getOWLAnnotationAssertionAxiom(iri("test"),
                        df.getOWLAnnotation(ap, iri("otherTest")));
        m.addAxiom(o, assertion);
        return o;
    }

    @Test
    public void shouldNotAddDeclarationsForIllegalPunnings()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = getOntologyWithPunnedInvalidDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLAnnotationProperty ap = df
                .getOWLAnnotationProperty(iri("testProperty"));
        OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(ap);
        assertFalse("ap testProperty should not have been declared",
                reloaded.containsAxiom(ax));
    }

    @Test
    public void shouldDeclareMissingEntities()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = getOntologyWithMissingDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLObjectProperty op = df
                .getOWLObjectProperty(iri("testObjectProperty"));
        OWLAnnotationProperty ap = df
                .getOWLAnnotationProperty(iri("testAnnotationProperty"));
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(ap)));
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(op)));
    }
}
