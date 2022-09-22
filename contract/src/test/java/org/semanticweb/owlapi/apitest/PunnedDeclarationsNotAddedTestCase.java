package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
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
        OWLAnnotationProperty pun = AnnotationProperty(OBJPROPS.P.getIRI());
        o.add(Declaration(OBJPROPS.P));
        o.add(TransitiveObjectProperty(OBJPROPS.P));
        OWLAnnotationAssertionAxiom assertion =
            AnnotationAssertion(pun, IRIS.iriTest1, IRIS.iriTest2);
        o.add(assertion);
        return o;
    }

    protected OWLOntology getOntologyWithMissingDeclarations() {
        OWLOntology o = create(iri("urn:test:", "missingDeclarations"));
        o.add(TransitiveObjectProperty(OBJPROPS.P));
        OWLAnnotationAssertionAxiom assertion =
            AnnotationAssertion(ANNPROPS.AP, IRIS.iriTest1, IRIS.iriTest2);
        o.add(assertion);
        return o;
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldNotAddDeclarationsForIllegalPunnings(OWLDocumentFormat format) {
        OWLOntology o = getOntologyWithPunnedInvalidDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        OWLDeclarationAxiom ax = Declaration(ANNPROPS.AP);
        assertFalse(reloaded.containsAxiom(ax), "Property should not have been declared");
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldDeclareMissingEntities(OWLDocumentFormat format) {
        OWLOntology o = getOntologyWithMissingDeclarations();
        OWLOntology reloaded = roundTrip(o, format);
        assertTrue(reloaded.containsAxiom(Declaration(ANNPROPS.AP)));
        assertTrue(reloaded.containsAxiom(Declaration(OBJPROPS.P)));
    }
}
