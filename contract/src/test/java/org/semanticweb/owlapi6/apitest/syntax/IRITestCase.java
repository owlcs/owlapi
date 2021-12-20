package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.io.UnparsableOntologyException;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.rioformats.NQuadsDocumentFormat;
import org.semanticweb.owlapi6.rioformats.NTriplesDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi6.rioformats.TrigDocumentFormat;

class IRITestCase extends TestBase {
    @Test
    void shouldParseIRIAndSkipPrefixedSpaceManchesterSyntax() {
        roundTrip(new ManchesterSyntaxDocumentFormat(), TestFiles.BAD_MANCHESTER);
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceTriG() {
        assertThrows(OWLRuntimeException.class,
            () -> loadFrom(TestFiles.BAD_TRIG, new TrigDocumentFormat()));
    }

    protected void shouldFail(String bad, OWLDocumentFormat f2) {
        assertThrows(OWLRuntimeException.class, () -> loadFrom(bad, f2));
    }

    @Test
    void shouldOutputNamedGraph() {
        IRI iri = iri("urn:test:", "ontology");
        OWLOntology o = create(iri);
        o.getOWLOntologyManager().getOntologyConfigurator().withNamedGraphIRIEnabled(true);
        StringDocumentTarget saved = saveOntology(o, new TrigDocumentFormat());
        assertTrue(saved.toString().contains(iri.toQuotedString() + " {"));
    }

    @Test
    void shouldOutputOverriddenNamedGraph() throws OWLOntologyStorageException {
        OWLDocumentFormat format = new TrigDocumentFormat();
        String value = "urn:test:onto";
        StringDocumentTarget target = new StringDocumentTarget();
        target.getStorerParameters().setParameter("namedGraphOverride", value);
        OWLOntology o = create(iri("urn:test:", "ontology"));
        o.saveOntology(format, target);
        assertTrue(target.toString().contains("<" + value + ">"));
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceRDFJSON() {
        roundTrip(new RDFJsonDocumentFormat(), TestFiles.BAD_RDFJSON);
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceNTriples() {
        assertThrows(Exception.class,
            () -> roundTrip(new NTriplesDocumentFormat(), TestFiles.BAD_NTRIPLES));
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceRioRDFXML() {
        assertThrows(OWLRuntimeException.class,
            () -> loadFrom(TestFiles.BAD_RIO_RDF, new RioRDFXMLDocumentFormat()));
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceRioTurtle() {
        // no crash on this version
        loadFrom(TestFiles.BAD_RIO_TURTLE, new RioTurtleDocumentFormat());
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceJSONLD() {
        roundTrip(new RDFJsonLDDocumentFormat(), TestFiles.BAD_JSON_LD);
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceNQUADS() {
        assertThrows(Exception.class,
            () -> roundTrip(new NQuadsDocumentFormat(), TestFiles.BAD_NTRIPLES));
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceOWLXML() {
        roundTrip(new OWLXMLDocumentFormat(), TestFiles.BAD_OWLXML);
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceRDFXML() {
        shouldFail(TestFiles.BAD_RDFXML, new RDFXMLDocumentFormat());
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceTurtle() {
        shouldFail(TestFiles.BAD_TURTLE, new TurtleDocumentFormat());
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceFunctionalSyntax() {
        roundTrip(new FunctionalSyntaxDocumentFormat(), TestFiles.BAD_FUNCTIONAL);
    }

    protected void roundTrip(OWLDocumentFormat format, String bad) {
        String good = bad.replace(" https://example.org/bad-url", "https://example.org/bad-url");
        OWLOntology o1 = loadFrom(bad, format);
        OWLOntology o2 = loadFrom(good, format);
        equal(o1, o2);
    }
}
