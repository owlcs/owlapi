package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
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

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullOntologyID() {
        new OWLOntologyChangeRecord(null, mockChangeData);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullChangeData() {
        new OWLOntologyChangeRecord(mockOntologyID, null);
    }

    @Test
    public void testEquals() {
        OWLOntologyChangeRecord record1 = new OWLOntologyChangeRecord(
                mockOntologyID, mockChangeData);
        OWLOntologyChangeRecord record2 = new OWLOntologyChangeRecord(
                mockOntologyID, mockChangeData);
        assertEquals(record1, record2);
    }

    @Test
    public void testGettersNotNull() {
        OWLOntologyChangeRecord record = new OWLOntologyChangeRecord(
                mockOntologyID, mockChangeData);
        assertNotNull(record.getOntologyID());
    }

    @Test
    public void testGetterEqual() {
        OWLOntologyChangeRecord record = new OWLOntologyChangeRecord(
                mockOntologyID, mockChangeData);
        assertEquals(mockOntologyID, record.getOntologyID());
        assertEquals(mockChangeData, record.getData());
    }

    @Test(expected = UnknownOWLOntologyException.class)
    public void testCreateOntologyChange() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyChangeRecord changeRecord = new OWLOntologyChangeRecord(
                mockOntologyID, mockChangeData);
        changeRecord.createOntologyChange(manager);
    }

    @Test
    public void testCreateOntologyChangeEquals() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology();
        OWLOntologyID ontologyID = ontology.getOntologyID();
        AddAxiomData addAxiomData = new AddAxiomData(mockAxiom);
        OWLOntologyChangeRecord changeRecord = new OWLOntologyChangeRecord(
                ontologyID, addAxiomData);
        OWLOntologyChange change = changeRecord.createOntologyChange(manager);
        assertNotNull(change);
        assertEquals(change.getOntology().getOntologyID(), ontologyID);
        assertEquals(mockAxiom, change.getAxiom());
    }
}
