package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Apr-2007<br><br>
 * <p/>
 * Tests that the isOWLThing and isOWLNothing methods
 * return correct values.
 */
public class BuiltInClassTestCase extends TestCase {

    private OWLDataFactory dataFactory;


    @Override
	protected void setUp() throws Exception {
        super.setUp();
        dataFactory = OWLDataFactoryImpl.getInstance();
    }


    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
        dataFactory = null;
    }


    public void testOWLThing() {
        OWLClass thing = dataFactory.getOWLThing();
        assertTrue(thing.isOWLThing());
        assertFalse(thing.isOWLNothing());
    }

    public void testOWLThingFromURI() {
        OWLClassExpression desc = dataFactory.getOWLClass(OWLRDFVocabulary.OWL_THING.getIRI());
        assertTrue(desc.isOWLThing());
        assertFalse(desc.isOWLNothing());
    }

    public void testOWLNothing() {
        OWLClass nothing = dataFactory.getOWLNothing();
        assertTrue(nothing.isOWLNothing());
        assertFalse(nothing.isOWLThing());
    }

    public void testOWLNothingFromURI() {
        OWLClassExpression desc = dataFactory.getOWLClass(OWLRDFVocabulary.OWL_NOTHING.getIRI());
        assertTrue(desc.isOWLNothing());
        assertFalse(desc.isOWLThing());
    }

    public void testAnonymousClass() {
        OWLClassExpression desc = dataFactory.getOWLObjectHasSelf(dataFactory.getOWLObjectProperty(TestUtils.createIRI()));
        assertFalse(desc.isOWLThing());
        assertFalse(desc.isOWLNothing());
    }


}
