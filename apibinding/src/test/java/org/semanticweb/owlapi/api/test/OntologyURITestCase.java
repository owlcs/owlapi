package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 07-Sep-2008<br><br>
 */
public class OntologyURITestCase extends AbstractOWLAPITestCase {

    public void testOntologyID() throws Exception {
        IRI iriA = IRI.create("http://www.another.com/ont");
        IRI iriB = IRI.create("http://www.another.com/ont/version");
        OWLOntologyID ontIDBoth = new OWLOntologyID(iriA, iriB);
        OWLOntologyID ontIDBoth2 = new OWLOntologyID(iriA, iriB);
        assertEquals(ontIDBoth, ontIDBoth2);
        OWLOntologyID ontIDURIOnly = new OWLOntologyID(iriA);
        assertFalse(ontIDBoth.equals(ontIDURIOnly));
        OWLOntologyID ontIDNoneA = new OWLOntologyID();
        OWLOntologyID ontIDNoneB = new OWLOntologyID();
        assertFalse(ontIDNoneA.equals(ontIDNoneB));
    }

    public void testOntologyURI() throws Exception {
        IRI iri = IRI.create("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(iri);
        assertEquals(ont.getOntologyID().getOntologyIRI(), iri);
        assertTrue(getManager().contains(iri));
        assertTrue(getManager().getOntologies().contains(ont));
        OWLOntologyID ontID = new OWLOntologyID(iri);
        assertEquals(ont.getOntologyID(), ontID);
    }

    public void testDuplicateOntologyURI() throws Exception{
        IRI uri = IRI.create("http://www.another.com/ont");
        getManager().createOntology(uri);
        boolean rightException=false;
        try {
            getManager().createOntology(uri);
        } catch (OWLOntologyAlreadyExistsException e) {
            // as expected
        	rightException=true;
        	//e.printStackTrace();
        } catch (OWLOntologyCreationException e) {

			e.printStackTrace();
		}
        assertTrue("an OntologyAlreadyExistsException has not been thrown",rightException);
    }


    public void testSetOntologyURI() throws Exception {
        IRI iri = IRI.create("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(iri);
        IRI newIRI = IRI.create("http://www.another.com/newont");
        SetOntologyID sou = new SetOntologyID(ont, new OWLOntologyID(newIRI));
        getManager().applyChange(sou);
        assertFalse(getManager().contains(iri));
        assertTrue(getManager().contains(newIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), newIRI);
    }

    public void testVersionURI() throws Exception {
        IRI ontIRI = IRI.create("http://www.another.com/ont");
        IRI verIRI = IRI.create("http://www.another.com/ont/versions/1.0.0");
        OWLOntology ont = getManager().createOntology(new OWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI(), verIRI);
    }

    public void testNullVersionURI() throws Exception {
        IRI ontIRI = IRI.create("http://www.another.com/ont");
        IRI verIRI = null;
        OWLOntology ont = getManager().createOntology(new OWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI(), verIRI);
    }
}
