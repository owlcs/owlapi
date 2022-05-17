package org.semanticweb.owlapi.impl.concurrent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.impl.OWLDataFactoryImpl;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;

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
