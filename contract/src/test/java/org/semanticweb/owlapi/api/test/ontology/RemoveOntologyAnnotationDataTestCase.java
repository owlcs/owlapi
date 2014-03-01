package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.RemoveOntologyAnnotationData;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
public class RemoveOntologyAnnotationDataTestCase {

    private OWLAnnotation mockAnnotation;
    private OWLOntology mockOntology;

    /**
     * Creates RemoveOntologyAnnotationData with the value of the
     * {@code mockAnnotation} field as a parameter.
     * 
     * @return The freshly created RemoveOntologyAnnotationData
     */
    private RemoveOntologyAnnotationData createData() {
        return new RemoveOntologyAnnotationData(mockAnnotation);
    }

    @Before
    public void setUp() {
        mockAnnotation = mock(OWLAnnotation.class);
        mockOntology = mock(OWLOntology.class);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullArgs() {
        new RemoveOntologyAnnotationData(null);
    }

    @Test
    public void testEquals() {
        RemoveOntologyAnnotationData data1 = createData();
        RemoveOntologyAnnotationData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersReturnNotNull() {
        RemoveOntologyAnnotationData data = createData();
        assertNotNull(data.getAnnotation());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    public void testGettersEquals() {
        RemoveOntologyAnnotationData data = createData();
        assertEquals(mockAnnotation, data.getAnnotation());
    }

    @Test
    public void testCreateOntologyChange() {
        RemoveOntologyAnnotationData data = createData();
        RemoveOntologyAnnotation change = data
                .createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockAnnotation, change.getAnnotation());
    }

    @Test
    public void testGetChangeData() {
        RemoveOntologyAnnotationData data = createData();
        RemoveOntologyAnnotation change = new RemoveOntologyAnnotation(
                mockOntology, mockAnnotation);
        assertEquals(change.getChangeData(), data);
    }
}
