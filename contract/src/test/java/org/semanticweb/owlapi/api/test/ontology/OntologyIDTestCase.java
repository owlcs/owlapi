package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;

@SuppressWarnings("javadoc")
public class OntologyIDTestCase {

    @Test
    public void shouldFindSameHashCode() {
        IRI iri1 = IRI("http://test.it/check1");
        IRI iri2 = IRI("http://test.it/check1");
        assertEquals(iri1.hashCode(), iri2.hashCode());
    }

    @Test
    public void shouldFindSameHashCodeForIDs() {
        IRI iri1 = IRI("http://test.it/check1");
        IRI iri2 = IRI("http://test.it/check1");
        assertEquals(iri1.hashCode(), iri2.hashCode());
        OWLOntologyID id1 = new OWLOntologyID(iri1);
        OWLOntologyID id2 = new OWLOntologyID(iri2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void shouldFindSameHashCodeForIDs2() {
        IRI iri1 = IRI("http://test.it/check1");
        IRI iri2 = IRI("http://test.it/check1");
        assertEquals(iri1.hashCode(), iri2.hashCode());
        OWLOntologyID id1 = new OWLOntologyID(iri1, null);
        OWLOntologyID id2 = new OWLOntologyID(iri2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Ignore
    // this is an experiment, if the manager were to keep all versions of an
    // ontology together in a multimap or something
            @Test
            public
            void shouldFindSameHashCodeForIDs3() {
        IRI iri1 = IRI("http://test.it/check1");
        IRI iri2 = IRI("http://test.it/check1");
        assertEquals(iri1.hashCode(), iri2.hashCode());
        OWLOntologyID id1 = new OWLOntologyID(iri1, null);
        OWLOntologyID id2 = new OWLOntologyID(iri2,
                IRI("http://test.it/check1test"));
        assertEquals(id1.hashCode(), id2.hashCode());
    }
}
