package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Aug-2010
 */
public class ImportsClosureTestCase extends AbstractOWLAPITestCase {

    public void testImportsClosureUpdate() throws Exception {

        OWLOntologyManager manager = getManager();


        IRI aIRI = IRI.create("http://a.com");
        OWLOntology ontA = manager.createOntology(aIRI);

        IRI bIRI = IRI.create("http://b.com");
        OWLOntology ontB = manager.createOntology(bIRI);

        OWLDataFactory df = getFactory();
        manager.applyChange(new AddImport(ontA, df.getOWLImportsDeclaration(bIRI)));

        assertEquals(2, manager.getImportsClosure(ontA).size());

        manager.removeOntology(ontB);

        assertEquals(1, manager.getImportsClosure(ontA).size());

        manager.createOntology(bIRI);

        assertEquals(2, manager.getImportsClosure(ontA).size());



    }
}
