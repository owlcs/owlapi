package org.semanticweb.owlapi.api.test;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi.model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 22/10/2012
 */
public class OWLOntologyChangeRecordTestCase {

    private OWLOntologyID mockOntologyID;
    
    private OWLOntologyChangeData mockChangeData;

    private OWLAxiom mockAxiom;
    
    @Before
    public void setUp() {
        mockOntologyID = new OWLOntologyID();
        mockChangeData = mock(OWLOntologyChangeData.class);
        mockAxiom = mock(OWLAxiom.class);
    }
    
    @Test(expected = NullPointerException.class)
    public void testNewWithNullOntologyID() {
        new OWLOntologyChangeRecord(null, mockChangeData);
    }

    @Test(expected = NullPointerException.class)
    public void testNewWithNullChangeData() {
        new OWLOntologyChangeRecord(mockOntologyID, null);
    }
    
    @Test
    public void testEquals() {
        OWLOntologyChangeRecord record1 = new OWLOntologyChangeRecord(mockOntologyID, mockChangeData);
        OWLOntologyChangeRecord record2 = new OWLOntologyChangeRecord(mockOntologyID, mockChangeData);
        assertEquals(record1, record2);
    }
    
    @Test
    public void testGettersNotNull() {
        OWLOntologyChangeRecord record = new OWLOntologyChangeRecord(mockOntologyID, mockChangeData);
        assertNotNull(record.getOntologyID());
    }
    
    @Test
    public void testGetterEqual() {
        OWLOntologyChangeRecord record = new OWLOntologyChangeRecord(mockOntologyID, mockChangeData);
        assertEquals(mockOntologyID, record.getOntologyID());
        assertEquals(mockChangeData, record.getData());
    }

    @Test(expected = UnknownOWLOntologyException.class)
    public void testCreateOntologyChange() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyChangeRecord changeRecord = new OWLOntologyChangeRecord(mockOntologyID, mockChangeData);
        changeRecord.createOntologyChange(manager);
    }
    
    @Test
    public void testCreateOntologyChangeEquals() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology();
        OWLOntologyID ontologyID = ontology.getOntologyID();
        AddAxiomData addAxiomData = new AddAxiomData(mockAxiom);
        OWLOntologyChangeRecord changeRecord = new OWLOntologyChangeRecord(ontologyID, addAxiomData);
        OWLOntologyChange change = changeRecord.createOntologyChange(manager);
        assertNotNull(change);
        assertEquals(change.getOntology().getOntologyID(), ontologyID);
        assertEquals(mockAxiom, change.getAxiom());
    }
}
