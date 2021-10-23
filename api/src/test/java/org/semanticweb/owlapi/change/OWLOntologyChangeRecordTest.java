package org.semanticweb.owlapi.change;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;

class OWLOntologyChangeRecordTest {

    private static final String URN_TEST = "urn:test#";

    @Test
    void testSerializeChangeRecord() throws Exception {
        OWLOntologyID id1 = new OWLOntologyID(IRI.create(URN_TEST, "a"), IRI.create(URN_TEST, "v1"));
        OWLOntologyID id2 = new OWLOntologyID(IRI.create(URN_TEST, "a"), IRI.create(URN_TEST, "v2"));
        OWLOntologyChangeRecord idChangeRecord =
            new OWLOntologyChangeRecord(id1, new SetOntologyIDData(id2));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(idChangeRecord);
        out.close();
        ObjectInputStream in =
            new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        OWLOntologyChangeRecord recordIn = (OWLOntologyChangeRecord) in.readObject();
        assertEquals(idChangeRecord, recordIn);
    }
}
