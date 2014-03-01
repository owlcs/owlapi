package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.AddOntologyAnnotationData;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
public class AddOntologyAnnotationDataTestCase {

    private OWLAnnotation mockAnnotation;
    private OWLOntology mockOntology;

    /**
     * Creates AddOntologyAnnotationData with the value of the
     * {@code mockAnnotation} field as a parameter.
     * 
     * @return The freshly created AddOntologyAnnotationData
     */
    private AddOntologyAnnotationData createData() {
        return new AddOntologyAnnotationData(mockAnnotation);
    }

    @Before
    public void setUp() {
        mockAnnotation = mock(OWLAnnotation.class);
        mockOntology = mock(OWLOntology.class);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullArgs() {
        new AddOntologyAnnotationData(null);
    }

    @Test
    public void testEquals() {
        AddOntologyAnnotationData data1 = createData();
        AddOntologyAnnotationData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersReturnNotNull() {
        AddOntologyAnnotationData data = createData();
        assertNotNull(data.getAnnotation());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    public void testGettersEquals() {
        AddOntologyAnnotationData data = createData();
        assertEquals(mockAnnotation, data.getAnnotation());
    }

    @Test
    public void testCreateOntologyChange() {
        AddOntologyAnnotationData data = createData();
        AddOntologyAnnotation change = data.createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockAnnotation, change.getAnnotation());
    }

    @Test
    public void testOntologyChangeSymmetry() {
        AddOntologyAnnotationData data = createData();
        AddOntologyAnnotation change = new AddOntologyAnnotation(mockOntology,
                mockAnnotation);
        assertEquals(change.getChangeData(), data);
    }
}
