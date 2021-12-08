package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.semanticweb.owlapi.model.OWLOntology;

class PunnedDeclarationsNotAddedTestCase extends TestBase {

    static Collection<OWLDocumentFormat> data() {
        return l(new FunctionalSyntaxDocumentFormat(), new OWLXMLDocumentFormat(),
            new RDFXMLDocumentFormat(), new TurtleDocumentFormat());
    }

    protected OWLOntology getOntologyWithPunnedInvalidDeclarations() {
        OWLOntology o = create(iri("urn:test:", "forbiddenPunningNotRedeclared"));
        OWLAnnotationProperty ap = AnnotationProperty(P.getIRI());
        o.add(Declaration(P));
        o.add(TransitiveObjectProperty(P));
        OWLAnnotationAssertionAxiom assertion = AnnotationAssertion(ap, iriTest, iri("otherTest"));
        o.add(assertion);
        return o;
    }

    protected OWLOntology getOntologyWithMissingDeclarations() {
        OWLOntology o = create(iri("urn:test:", "missingDeclarations"));
        o.add(TransitiveObjectProperty(P));
        OWLAnnotationAssertionAxiom assertion = AnnotationAssertion(AP, iriTest, iri("otherTest"));
        o.add(assertion);
        return o;
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldNotAddDeclarationsForIllegalPunnings(OWLDocumentFormat format) {
        OWLOntology o = getOntologyWithPunnedInvalidDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLDeclarationAxiom ax = Declaration(AP);
        assertFalse(reloaded.containsAxiom(ax), "Property should not have been declared");
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldDeclareMissingEntities(OWLDocumentFormat format) {
        OWLOntology o = getOntologyWithMissingDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        assertTrue(reloaded.containsAxiom(Declaration(AP)));
        assertTrue(reloaded.containsAxiom(Declaration(P)));
    }
}
