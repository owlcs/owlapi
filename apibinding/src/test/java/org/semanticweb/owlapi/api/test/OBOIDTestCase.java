package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.coode.owlapi.obo.parser.OBOVocabulary;
import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03/02/2011
 */
public class OBOIDTestCase extends TestCase {


    public void testID() {
        String id = "go:123";
        IRI iri = OBOVocabulary.ID2IRI(id);
        assertEquals(iri, IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_123"));
    }

    public void testIDEx() {
        String id = "go:X-123_Y";
        IRI iri = OBOVocabulary.ID2IRI(id);
        assertEquals(iri, IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_X-123_Y"));
    }

    public void testIDWithoutIDSpace() {
        String id = "123";
        IRI iri = OBOVocabulary.ID2IRI(id);
        assertEquals(iri, IRI.create(OBOVocabulary.OBO_IRI_BASE + "123"));
    }
    
    public void testIRI() {
        IRI iri = IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_123");
        String id = OBOVocabulary.IRI2ID(iri);
        assertEquals(id, "go:123");
    }

    public void testIRIEx() {
        IRI iri = IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_X-123_Y");
        String id = OBOVocabulary.IRI2ID(iri);
        assertEquals(id, "go:X-123_Y");
    }

    public void testIRIWithoutIDSpace() {
        IRI iri = IRI.create(OBOVocabulary.OBO_IRI_BASE + "123");
        String id = OBOVocabulary.IRI2ID(iri);
        assertEquals(id, "123");
    }
    
}
