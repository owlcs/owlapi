package uk.ac.manchester.cs.owlapi6.concurrent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataProperty;

import uk.ac.manchester.cs.owlapi6.OWLDataFactoryImpl;

@SuppressWarnings("javadoc")
public class InternalizedEntitiesTest {
    @Test
    public void shouldBeTop() {
        OWLDataFactoryImpl impl = new OWLDataFactoryImpl();
        assertTrue(impl.getTopDatatype().isTopDatatype());
        OWLDataProperty p = impl.getOWLDataProperty("urn:test:p");
        OWLDataExactCardinality exp = impl.getOWLDataExactCardinality(1, p);
        assertFalse(exp.isQualified());
    }
}
