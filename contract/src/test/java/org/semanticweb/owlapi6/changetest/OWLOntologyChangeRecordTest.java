package org.semanticweb.owlapi6.changetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi6.change.SetOntologyIDData;
import org.semanticweb.owlapi6.model.OWLOntologyID;

public class OWLOntologyChangeRecordTest extends TestBase {

    private static final String URN_TEST = "urn:test#";

    @Test
    public void testSerializeChangeRecord() throws Exception {
        OWLOntologyID id1 = OntologyID(iri(URN_TEST, "a"), iri(URN_TEST, "v1"));
        OWLOntologyID id2 = OntologyID(iri(URN_TEST, "a"), iri(URN_TEST, "v2"));
        OWLOntologyChangeRecord idChangeRecord =
            new OWLOntologyChangeRecord(id1, new SetOntologyIDData(id2));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
            out.writeObject(idChangeRecord);
            out.close();
        }
        ObjectInputStream in =
            new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        OWLOntologyChangeRecord recordIn = (OWLOntologyChangeRecord) in.readObject();
        assertEquals(idChangeRecord, recordIn);
    }
}
