package org.semanticweb.owlapi.changetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi.change.SetOntologyIDData;
import org.semanticweb.owlapi.model.OWLOntologyID;

class OWLOntologyChangeRecordTest extends TestBase {

    @Test
    void testSerializeChangeRecord() throws Exception {
        OWLOntologyID id1 = OntologyID(iri(URN_TEST, "a"), iri(URN_TEST, "v1"));
        OWLOntologyID id2 = OntologyID(iri(URN_TEST, "a"), iri(URN_TEST, "v2"));
        OWLOntologyChangeRecord idChangeRecord =
            new OWLOntologyChangeRecord(id1, new SetOntologyIDData(id2));
        ByteArrayOutputStream outArray = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(outArray)) {
            out.writeObject(idChangeRecord);
            out.close();
        }
        var in = new ObjectInputStream(new ByteArrayInputStream(outArray.toByteArray()));
        OWLOntologyChangeRecord recordIn = (OWLOntologyChangeRecord) in.readObject();
        assertEquals(idChangeRecord, recordIn);
    }
}
