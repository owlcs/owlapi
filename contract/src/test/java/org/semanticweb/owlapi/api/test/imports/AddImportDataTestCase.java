package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.AddImportData;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 22/10/2012
 */
@SuppressWarnings("javadoc")
public class AddImportDataTestCase {

    private OWLImportsDeclaration mockDeclaration;
    private OWLOntology mockOntology;

    /**
     * Creates AddImportData with the value of the {@code mockDeclaration} field
     * as a parameter.
     * 
     * @return The freshly created AddImportData
     */
    private AddImportData createData() {
        return new AddImportData(mockDeclaration);
    }

    @Before
    public void setUp() {
        mockDeclaration = mock(OWLImportsDeclaration.class);
        mockOntology = mock(OWLOntology.class);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testNewWithNullArgs() {
        new AddImportData(null);
    }

    @Test
    public void testEquals() {
        AddImportData data1 = createData();
        AddImportData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersReturnNotNull() {
        AddImportData data = createData();
        assertNotNull(data.getDeclaration());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    public void testGettersEquals() {
        AddImportData data = createData();
        assertEquals(mockDeclaration, data.getDeclaration());
    }

    @Test
    public void testCreateOntologyChange() {
        AddImportData data = createData();
        AddImport change = data.createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockDeclaration, change.getImportDeclaration());
    }

    @Test
    public void testOntologyChangeSymmetry() {
        AddImportData data = createData();
        AddImport change = new AddImport(mockOntology, mockDeclaration);
        assertEquals(change.getChangeData(), data);
    }
}
