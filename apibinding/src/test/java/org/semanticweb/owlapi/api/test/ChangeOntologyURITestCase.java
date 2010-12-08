package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLOntologyURIChanger;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-May-2007<br><br>
 */
public class ChangeOntologyURITestCase extends AbstractOWLAPITestCase {

    public void testChangeURI() throws Exception {
        OWLOntologyManager man = getManager();
        IRI oldIRI = IRI.create("http://www.semanticweb.org/ontologies/ontA");
        IRI newIRI = IRI.create("http://www.semanticweb.org/ontologies/ontB");
        OWLOntology ont = man.createOntology(oldIRI);
        OWLOntology importingOnt = man.createOntology(IRI.create("http://www.semanticweb.org/ontologies/ontC"));
        man.applyChange(new AddImport(importingOnt, man.getOWLDataFactory().getOWLImportsDeclaration(ont.getOntologyID().getOntologyIRI())));
        assertTrue(man.contains(oldIRI));
        OWLOntologyURIChanger changer = new OWLOntologyURIChanger(man);
        man.applyChanges(changer.getChanges(ont, newIRI));
        assertFalse(man.contains(oldIRI));
        assertTrue(man.contains(newIRI));
        assertTrue(man.getOntologies().contains(ont));
        assertTrue(man.getDirectImports(importingOnt).contains(ont));
        assertNotNull(man.getOntology(newIRI));
        assertEquals(man.getOntology(newIRI), ont);
        assertEquals(man.getOntology(newIRI).getOntologyID().getOntologyIRI(), newIRI);
        assertTrue(man.getImportsClosure(importingOnt).contains(ont));
        assertNotNull(man.getOntologyDocumentIRI(ont));
        // Document IRI will still be the same (in this case the old ont URI)
        assertEquals(man.getOntologyDocumentIRI(ont), oldIRI);
        assertNotNull(man.getOntologyFormat(ont));

    }
}
