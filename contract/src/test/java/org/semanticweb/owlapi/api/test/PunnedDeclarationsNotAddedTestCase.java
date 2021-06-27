package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

class PunnedDeclarationsNotAddedTestCase extends TestBase {

    static Collection<OWLDocumentFormat> data() {
        return Arrays.asList(new FunctionalSyntaxDocumentFormat(), new OWLXMLDocumentFormat(),
            new RDFXMLDocumentFormat(), new TurtleDocumentFormat());
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

    @ParameterizedTest
    @MethodSource("data")
    void shouldNotAddDeclarationsForIllegalPunnings(OWLDocumentFormat format)
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = getOntologyWithPunnedInvalidDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri("testProperty"));
        OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(ap);
        assertFalse(reloaded.containsAxiom(ax), "Property should not have been declared");
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldDeclareMissingEntities(OWLDocumentFormat format)
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = getOntologyWithMissingDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLObjectProperty op = df.getOWLObjectProperty(iri("testObjectProperty"));
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri("testAnnotationProperty"));
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(ap)));
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(op)));
    }
}
