package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import org.coode.owlapi.rdf.renderer.TripleComparator;
import org.junit.Test;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;

@SuppressWarnings("javadoc")
public class RdfGraphComparatorTestCase {
    @Test
    public void shouldSort() {
        RDFResourceIRI iri1 = new RDFResourceIRI(IRI.create("urn:test:1"));
        RDFTriple first = new RDFTriple(iri1, iri1, iri1);
        RDFTriple second = new RDFTriple(iri1, iri1, iri1);
        assertEquals(0, new TripleComparator().compare(first, second));
    }
}
