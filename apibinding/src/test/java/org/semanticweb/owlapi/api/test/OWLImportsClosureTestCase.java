package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.RemoveImport;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 31-Jul-2007<br><br>
 */
public class OWLImportsClosureTestCase extends AbstractOWLAPITestCase {

    /**
     * Tests to see if the method which obtains the imports closure behaves correctly.
     */
    public void testImportsClosure() throws Exception {
        OWLOntology ontA = getManager().createOntology(TestUtils.createIRI());
        OWLOntology ontB = getManager().createOntology(TestUtils.createIRI());
        assertTrue(getManager().getImportsClosure(ontA).contains(ontA));
        OWLImportsDeclaration importsDeclaration = getFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        getManager().applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(getManager().getImportsClosure(ontA).contains(ontB));
        getManager().applyChange(new RemoveImport(ontA, importsDeclaration));
        assertFalse(getManager().getImportsClosure(ontA).contains(ontB));
        getManager().applyChange(new AddImport(ontA, importsDeclaration));
        assertTrue(getManager().getImportsClosure(ontA).contains(ontB));
        getManager().removeOntology(ontB);
        assertFalse(getManager().getImportsClosure(ontA).contains(ontB));

    }
}
