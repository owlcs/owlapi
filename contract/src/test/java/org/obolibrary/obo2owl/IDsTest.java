package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class IDsTest extends TestBase {

    @Test
    public void testIDs() throws OWLOntologyCreationException {
        OBODoc doc = new OBODoc();
        Frame header = new Frame(FrameType.HEADER);
        Clause c = new Clause(OboFormatTag.TAG_ONTOLOGY.getTag());
        c.setValue("test");
        header.addClause(c);
        doc.setHeaderFrame(header);
        OWLAPIObo2Owl obo2owl = new OWLAPIObo2Owl(m);
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(m);
        OWLOntology ontology = obo2owl.convert(doc);
        owl2Obo.convert(ontology);
        // Obo 2 OWL
        IRI iri = obo2owl.oboIdToIRI("GO:001");
        assertEquals("http://purl.obolibrary.org/obo/GO_001", iri.toString());
        // OWL 2 obo
        String oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("GO:001", oboId);
        iri = obo2owl.oboIdToIRI("My_Ont:FOO_002");
        assertEquals("http://purl.obolibrary.org/obo/My_Ont#_FOO_002", iri.toString());
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("My_Ont:FOO_002", oboId);
        iri = obo2owl.oboIdToIRI("My_Ont:002");
        assertEquals("http://purl.obolibrary.org/obo/My_Ont_002", iri.toString());
        // OWL 2 obo
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("My_Ont:002", oboId);
        // unprefixed IDs are prefixed with the current ontology ID
        iri = obo2owl.oboIdToIRI("003");
        assertEquals("http://purl.obolibrary.org/obo/test#003", iri.toString());
        // OWL 2 obo
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("003", oboId);
        // arbitrary URL to obo ID
        oboId = OWLAPIOwl2Obo.getIdentifier(IRI.create("http://purl.obolibrary.org/obo/alternate#abcdef"));
        // todo - test this
        // System.out.println("== "+oboId);
        iri = obo2owl.oboIdToIRI("part_of");
        assertEquals("http://purl.obolibrary.org/obo/test#part_of", iri.toString());
        // OWL 2 obo
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("part_of", oboId);
        iri = obo2owl.oboIdToIRI("OBO_REL:part_of");
        assertEquals("http://purl.obolibrary.org/obo/OBO_REL#_part_of", iri.toString());
        // OWL 2 obo
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("OBO_REL:part_of", oboId);
        iri = obo2owl.oboIdToIRI("http://purl.obolibrary.org/testont");
        assertEquals("http://purl.obolibrary.org/testont", iri.toString());
        // OWL 2 obo
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("http://purl.obolibrary.org/testont", oboId);
        iri = obo2owl.oboIdToIRI("http://purl.obolibrary.org/obo/BFO_0000050");
        assertEquals("http://purl.obolibrary.org/obo/BFO_0000050", iri.toString());
        // OWL 2 obo
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("BFO:0000050", oboId);
        // MGI IDs are perverse - they have a double-separator
        iri = obo2owl.oboIdToIRI("MGI:MGI:1");
        assertEquals("http://purl.obolibrary.org/obo/MGI_MGI%3A1", iri.toString());
        // OWL 2 obo
        oboId = OWLAPIOwl2Obo.getIdentifier(iri);
        assertEquals("MGI:MGI:1", oboId);
    }
}
