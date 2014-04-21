package org.semanticweb.owlapi.change;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.*;

public class OWLOntologyChangeRecordTest {

    @Test public void testSerializeChangeRecord() throws IOException, ClassNotFoundException {
        OWLOntologyID id1 = new OWLOntologyID(IRI.create("urn:a"),IRI.create("urn:v1"));
        OWLOntologyID id2 = new OWLOntologyID(IRI.create("urn:a"),IRI.create("urn:v2"));
        OWLOntologyChangeRecord<OWLOntologyID>  idChangeRecord = new OWLOntologyChangeRecord<OWLOntologyID>(id1,new SetOntologyIDData(id2));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(idChangeRecord);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        OWLOntologyChangeRecord<OWLOntologyID> recordIn = (OWLOntologyChangeRecord<OWLOntologyID>) in.readObject();
        assertEquals(idChangeRecord,recordIn);
    }
}