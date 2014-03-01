package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.RemoveImportData;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveImport;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
public class RemoveImportDataTestCase {

    private OWLImportsDeclaration mockDeclaration;
    private OWLOntology mockOntology;

    /**
     * Creates RemoveImportData with the value of the {@code mockDeclaration}
     * field as a parameter.
     * 
     * @return The freshly created RemoveImportData
     */
    private RemoveImportData createData() {
        return new RemoveImportData(mockDeclaration);
    }

    @Before
    public void setUp() {
        mockDeclaration = mock(OWLImportsDeclaration.class);
        mockOntology = mock(OWLOntology.class);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullArgs() {
        new RemoveImportData(null);
    }

    @Test
    public void testEquals() {
        RemoveImportData data1 = createData();
        RemoveImportData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersReturnNotNull() {
        RemoveImportData data = createData();
        assertNotNull(data.getDeclaration());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    public void testGettersEquals() {
        RemoveImportData data = createData();
        assertEquals(mockDeclaration, data.getDeclaration());
    }

    @Test
    public void testCreateOntologyChange() {
        RemoveImportData data = createData();
        RemoveImport change = data.createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockDeclaration, change.getImportDeclaration());
    }

    @Test
    public void testOntologyChangeSymmetry() {
        RemoveImportData data = createData();
        RemoveImport change = new RemoveImport(mockOntology, mockDeclaration);
        assertEquals(change.getChangeData(), data);
    }
}
