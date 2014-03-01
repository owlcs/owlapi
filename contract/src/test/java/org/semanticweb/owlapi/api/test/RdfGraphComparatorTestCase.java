package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

@SuppressWarnings("javadoc")
public class RdfGraphComparatorTestCase {

    @Test
    public void shouldSort() {
        RDFResourceNode iri1 = new RDFResourceNode(IRI.create("urn:test:1"));
        RDFTriple first = new RDFTriple(iri1, iri1, iri1);
        RDFTriple second = new RDFTriple(iri1, iri1, iri1);
        assertEquals(0, first.compareTo(second));
    }
}
