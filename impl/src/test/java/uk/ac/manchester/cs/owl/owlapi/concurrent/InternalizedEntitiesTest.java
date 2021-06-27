package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

class InternalizedEntitiesTest {

    @Test
    public void shouldBeTop() {
        OWLDataFactoryImpl impl = new OWLDataFactoryImpl();
        assertTrue(impl.getTopDatatype().isTopDatatype());
        OWLDataProperty p = impl.getOWLDataProperty("urn:test:p");
        OWLDataExactCardinality exp = impl.getOWLDataExactCardinality(1, p);
        assertFalse(exp.isQualified());
    }
}
