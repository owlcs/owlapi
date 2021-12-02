package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

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
import org.semanticweb.owlapi.model.OWLOntology;

class PunnedDeclarationsNotAddedTestCase extends TestBase {

    static Collection<OWLDocumentFormat> data() {
        return Arrays.asList(new FunctionalSyntaxDocumentFormat(), new OWLXMLDocumentFormat(),
            new RDFXMLDocumentFormat(), new TurtleDocumentFormat());
    }

    protected OWLOntology getOntologyWithPunnedInvalidDeclarations() {
        OWLOntology o = create(iri("urn:test:", "forbiddenPunningNotRedeclared"));
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(P.getIRI());
        m.addAxiom(o, df.getOWLDeclarationAxiom(P));
        m.addAxiom(o, df.getOWLTransitiveObjectPropertyAxiom(P));
        OWLAnnotationAssertionAxiom assertion = df.getOWLAnnotationAssertionAxiom(iri("test"),
            df.getOWLAnnotation(ap, iri("otherTest")));
        m.addAxiom(o, assertion);
        return o;
    }

    @Nonnull
    protected OWLOntology getOntologyWithMissingDeclarations() {
        OWLOntology o = create(iri("urn:test:", "missingDeclarations"));
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(AP.getIRI());
        m.addAxiom(o, df.getOWLTransitiveObjectPropertyAxiom(P));
        OWLAnnotationAssertionAxiom assertion = df.getOWLAnnotationAssertionAxiom(iri("test"),
            df.getOWLAnnotation(ap, iri("otherTest")));
        m.addAxiom(o, assertion);
        return o;
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldNotAddDeclarationsForIllegalPunnings(OWLDocumentFormat format) {
        OWLOntology o = getOntologyWithPunnedInvalidDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(AP);
        assertFalse(reloaded.containsAxiom(ax), "Property should not have been declared");
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldDeclareMissingEntities(OWLDocumentFormat format) {
        OWLOntology o = getOntologyWithMissingDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(AP)));
        assertTrue(reloaded.containsAxiom(df.getOWLDeclarationAxiom(P)));
    }
}
