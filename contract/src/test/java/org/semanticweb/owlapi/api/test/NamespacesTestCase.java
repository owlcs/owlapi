package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.EnumSet;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.Namespaces;

@SuppressWarnings("javadoc")
public class NamespacesTestCase {
    @Test
    public void shouldFindInNamespace() {
        EnumSet<Namespaces> reserved = EnumSet.of(Namespaces.OWL, Namespaces.RDF,
                Namespaces.RDFS, Namespaces.XSD);
        for (Namespaces n : Namespaces.values()) {
            IRI iri = IRI.create(n.getPrefixIRI(), "test");
            boolean reservedVocabulary = iri.isReservedVocabulary();
            assertEquals(iri + " reserved? Should be " + reserved.contains(n)
                    + " but is " + reservedVocabulary, reservedVocabulary,
                    reserved.contains(n));
        }
    }
}
