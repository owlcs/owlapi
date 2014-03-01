package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.EnumSet;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings("javadoc")
public class NamespacesTestCase {

    @Test
    public void shouldFindInNamespace() {
        EnumSet<Namespaces> reserved = EnumSet.of(Namespaces.OWL,
                Namespaces.RDF, Namespaces.RDFS, Namespaces.XSD);
        for (Namespaces n : Namespaces.values()) {
            IRI iri = IRI.create(n.getPrefixIRI(), "test");
            boolean reservedVocabulary = iri.isReservedVocabulary();
            assertEquals(iri + " reserved? Should be " + reserved.contains(n)
                    + " but is " + reservedVocabulary, reservedVocabulary,
                    reserved.contains(n));
        }
    }

    @Test
    public void shouldParseXSDSTRING() {
        // given
        OWLDataFactory df = Factory.getFactory();
        String s = "xsd:string";
        // when
        XSDVocabulary v = XSDVocabulary.parseShortName(s);
        // then
        assertEquals(XSDVocabulary.STRING, v);
        assertEquals(OWL2Datatype.XSD_STRING.getDatatype(df),
                df.getOWLDatatype(v.getIRI()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToParseInvalidString() {
        // given
        String s = "xsd:st";
        // when
        XSDVocabulary.parseShortName(s);
        // then
        // an exception should have been thrown
    }
}
