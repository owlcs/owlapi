package org.semanticweb.owlapi.api.test.dataproperties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveAxiom;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
public class RemoveAxiomDataTestCase {

    private OWLAxiom mockAxiom;
    private OWLOntology mockOntology;

    @Before
    public void setUp() {
        mockAxiom = mock(OWLAxiom.class);
        mockOntology = mock(OWLOntology.class);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullArgs() {
        new RemoveAxiomData(null);
    }

    @Test
    public void testEquals() {
        RemoveAxiomData data1 = new RemoveAxiomData(mockAxiom);
        RemoveAxiomData data2 = new RemoveAxiomData(mockAxiom);
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersNotNull() {
        RemoveAxiomData data = new RemoveAxiomData(mockAxiom);
        assertNotNull(data.getAxiom());
    }

    @Test
    public void testCreateOntologyChange() {
        RemoveAxiomData data = new RemoveAxiomData(mockAxiom);
        RemoveAxiom change = data.createOntologyChange(mockOntology);
        assertNotNull(change);
        assertEquals(change.getOntology(), mockOntology);
        assertEquals(change.getAxiom(), mockAxiom);
    }

    @Test
    public void testRoundTripChange() {
        RemoveAxiomData data = new RemoveAxiomData(mockAxiom);
        RemoveAxiom change = new RemoveAxiom(mockOntology, mockAxiom);
        assertEquals(data, change.getChangeData());
    }
}
