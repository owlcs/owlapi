package org.semanticweb.owlapi.api.test.dataproperties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
public class AddAxiomDataTestCase {

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
        new AddAxiomData(null);
    }

    @Test
    public void testEquals() {
        AddAxiomData data1 = new AddAxiomData(mockAxiom);
        AddAxiomData data2 = new AddAxiomData(mockAxiom);
        assertEquals(data1, data2);
    }

    @Test
    public void testOntologyChange() {
        AddAxiomData data = new AddAxiomData(mockAxiom);
        AddAxiom change = data.createOntologyChange(mockOntology);
        assertEquals(change.getOntology(), mockOntology);
        assertEquals(change.getAxiom(), mockAxiom);
    }

    @Test
    public void testRoundTripChange() {
        AddAxiomData data = new AddAxiomData(mockAxiom);
        AddAxiom change = new AddAxiom(mockOntology, mockAxiom);
        assertEquals(data, change.getChangeData());
    }
}
