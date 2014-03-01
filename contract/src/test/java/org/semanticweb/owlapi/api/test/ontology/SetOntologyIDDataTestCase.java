package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.SetOntologyIDData;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
public class SetOntologyIDDataTestCase {

    private OWLOntology mockOntology;
    private OWLOntologyID mockOntologyID;

    @Before
    public void setUp() {
        mockOntology = mock(OWLOntology.class);
        mockOntologyID = new OWLOntologyID();
    }

    /**
     * Creates SetOntologyIDData with the value of the {@code mockDeclaration}
     * field as a parameter.
     * 
     * @return The freshly created SetOntologyIDData
     */
    private SetOntologyIDData createData() {
        return new SetOntologyIDData(mockOntologyID);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullArgs() {
        new SetOntologyIDData(null);
    }

    @Test
    public void testEquals() {
        SetOntologyIDData data1 = createData();
        SetOntologyIDData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersReturnNotNull() {
        SetOntologyIDData data = createData();
        assertNotNull(data.getNewId());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    public void testGettersEquals() {
        SetOntologyIDData data = createData();
        assertEquals(mockOntologyID, data.getNewId());
    }

    @Test
    public void testCreateOntologyChange() {
        SetOntologyIDData data = createData();
        SetOntologyID change = data.createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockOntologyID, change.getNewOntologyID());
    }

    @Test
    public void testOntologyChangeSymmetry() {
        SetOntologyIDData data = createData();
        SetOntologyID change = new SetOntologyID(mockOntology, mockOntologyID);
        assertEquals(change.getChangeData(), data);
    }
}
