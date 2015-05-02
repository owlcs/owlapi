package org.semanticweb.owlapi.change;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;

@SuppressWarnings("javadoc")
public class OWLOntologyChangeRecordTest {

    @Test
    public void testSerializeChangeRecord() throws Exception {
        OWLOntologyID id1 = new OWLOntologyID(optional(IRI.create("urn:a")),
            optional(IRI.create("urn:v1")));
        OWLOntologyID id2 = new OWLOntologyID(optional(IRI.create("urn:a")),
            optional(IRI.create("urn:v2")));
        OWLOntologyChangeRecord idChangeRecord = new OWLOntologyChangeRecord(
            id1, new SetOntologyIDData(id2));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(
            byteArrayOutputStream)) {
            out.writeObject(idChangeRecord);
            out.close();
        }
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(
            byteArrayOutputStream.toByteArray()));
        OWLOntologyChangeRecord recordIn = (OWLOntologyChangeRecord) in
            .readObject();
        assertEquals(idChangeRecord, recordIn);
    }
}
