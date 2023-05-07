package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.StringDocumentTarget;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.rioformats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.rioformats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.rioformats.TrigDocumentFormat;

class IRITestCase extends TestBase {
    @Test
    void shouldParseIRIAndSkipPrefixedSpaceManchesterSyntax() {
        roundTrip(new ManchesterSyntaxDocumentFormat(), TestFiles.BAD_MANCHESTER);
    }

    @Test
    void shouldParseIRIAndSkipPrefixedSpaceTriG() {
        var format = new TrigDocumentFormat();
        assertThrows(OWLRuntimeException.class, () -> loadFrom(TestFiles.BAD_TRIG, format));
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
        RioRDFXMLDocumentFormat format = new RioRDFXMLDocumentFormat();
        assertThrows(OWLRuntimeException.class, () -> loadFrom(TestFiles.BAD_RIO_RDF, format));
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
        var format = new NQuadsDocumentFormat();
        assertThrows(Exception.class, () -> roundTrip(format, TestFiles.BAD_NTRIPLES));
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
